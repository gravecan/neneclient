import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/** Structural checks only: does not load or execute mod classes. */
public class ValidateRemap {
    static Map<String,ClassNode> read(String path) throws Exception {
        Map<String,ClassNode> result = new TreeMap<>();
        try (JarFile jar = new JarFile(path)) {
            for (JarEntry entry : Collections.list(jar.entries())) if (entry.getName().endsWith(".class")) {
                ClassNode cls = new ClassNode(); new ClassReader(jar.getInputStream(entry)).accept(cls,0); result.put(cls.name,cls);
            }
        }
        return result;
    }
    static boolean exists(Map<String,ClassNode> classes,String owner,String name,String desc,boolean method,Set<String> seen) {
        if (!seen.add(owner) || !classes.containsKey(owner)) return false;
        ClassNode cls = classes.get(owner);
        if (method) {
            if (cls.methods.stream().anyMatch(m -> m.name.equals(name) && m.desc.equals(desc))) return true;
        } else if (cls.fields.stream().anyMatch(f -> f.name.equals(name) && f.desc.equals(desc))) return true;
        if (cls.superName != null && exists(classes,cls.superName,name,desc,method,seen)) return true;
        for (String iface : cls.interfaces) if (exists(classes,iface,name,desc,method,seen)) return true;
        return false;
    }
    public static void main(String[] args) throws Exception {
        Map<String,ClassNode> before = read(args[0]), after = read(args[1]);
        Deobfuscate.classes.putAll(before); Deobfuscate.readYarn(Path.of(args[2]));
        List<String> mappings = Files.readAllLines(Path.of(args[3]));
        for (String line : mappings.subList(1,mappings.size())) {
            String[] p = line.split("\t",-1);
            if (p[0].equals("class")) Deobfuscate.classNames.put(p[1],p[4]);
            else (p[0].equals("field") ? Deobfuscate.fields : Deobfuscate.methods).put(Deobfuscate.key(p[1],p[2],p[3]),p[4]);
        }
        Deobfuscate.Names names = new Deobfuscate.Names();
        List<String> failures = new ArrayList<>(); int methodCount = 0, references = 0, recordCount = 0, externalReferences = 0;
        if (before.size() != after.size()) failures.add("Class count mismatch");
        for (ClassNode old : before.values()) {
            ClassNode renamed = after.get(names.map(old.name));
            if (renamed == null) { failures.add("Missing class: " + old.name); continue; }
            if (old.fields.size() != renamed.fields.size() || old.methods.size() != renamed.methods.size()) failures.add("Member count mismatch: " + old.name);
            Set<String> signatures = new HashSet<>();
            for (MethodNode m : renamed.methods) if (!signatures.add(m.name + m.desc)) failures.add("Duplicate method: " + renamed.name + "." + m.name + m.desc);
            for (MethodNode method : old.methods) {
                methodCount++;
                String expectedName = names.mapMethodName(old.name,method.name,method.desc), expectedDesc = names.mapMethodDesc(method.desc);
                MethodNode target = renamed.methods.stream().filter(m -> m.name.equals(expectedName) && m.desc.equals(expectedDesc)).findFirst().orElse(null);
                if (target == null) { failures.add("Missing method: " + old.name + "." + method.name + method.desc); continue; }
                int[] oldOpcodes = Arrays.stream(method.instructions.toArray()).mapToInt(AbstractInsnNode::getOpcode).filter(op -> op >= 0).toArray();
                int[] newOpcodes = Arrays.stream(target.instructions.toArray()).mapToInt(AbstractInsnNode::getOpcode).filter(op -> op >= 0).toArray();
                if (!Arrays.equals(oldOpcodes,newOpcodes)) failures.add("Instruction opcode change: " + old.name + "." + method.name + method.desc);
                for (AbstractInsnNode instruction : method.instructions) {
                    String owner, name, desc; boolean isMethod;
                    if (instruction instanceof MethodInsnNode m) { owner=m.owner; name=m.name; desc=m.desc; isMethod=true; }
                    else if (instruction instanceof FieldInsnNode f) { owner=f.owner; name=f.name; desc=f.desc; isMethod=false; }
                    else continue;
                    if (!before.containsKey(owner) && !owner.startsWith("net/minecraft/") && !Deobfuscate.classNames.containsKey(owner)) {
                        externalReferences++;
                        String mapped = isMethod ? names.mapMethodName(owner,name,desc) : names.mapFieldName(owner,name,desc);
                        if (!mapped.equals(name)) failures.add("Unrelated external member renamed: " + owner + "." + name + desc);
                    }
                    if (!exists(before,owner,name,desc,isMethod,new HashSet<>())) continue;
                    references++;
                    String newName = isMethod ? names.mapMethodName(owner,name,desc) : names.mapFieldName(owner,name,desc);
                    String newDesc = isMethod ? names.mapMethodDesc(desc) : names.mapDesc(desc);
                    if (!exists(after,names.map(owner),newName,newDesc,isMethod,new HashSet<>())) failures.add("Unresolved remapped reference: " + owner + "." + name + desc);
                }
            }
            if (renamed.recordComponents != null) {
                recordCount++;
                Set<String> components = new HashSet<>();
                for (RecordComponentNode component : renamed.recordComponents) {
                    if (!components.add(component.name)) failures.add("Duplicate record component: " + renamed.name);
                    if (!exists(after,renamed.name,component.name,component.descriptor,false,new HashSet<>())) failures.add("Missing record field: " + renamed.name + "." + component.name);
                    if (!exists(after,renamed.name,component.name,"()" + component.descriptor,true,new HashSet<>())) failures.add("Missing record accessor: " + renamed.name + "." + component.name);
                }
            }
        }
        List<String> report = new ArrayList<>(List.of(
            "Classes retained: " + before.size(), "Methods checked: " + methodCount,
            "Originally resolvable in-JAR member references checked: " + references,
            "Non-Minecraft external member names checked for preservation: " + externalReferences,
            "Records checked (component/field/accessor consistency): " + recordCount,
            "Method opcode sequences: unchanged", "Failures: " + failures.size(),
            "These are structural checks, not compilation or runtime equivalence tests."));
        report.addAll(failures); Files.write(Path.of(args[4]),report); report.forEach(System.out::println);
        if (!failures.isEmpty()) throw new IllegalStateException("Remapping validation failed");
    }
}
