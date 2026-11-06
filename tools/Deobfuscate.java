import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import java.util.regex.*;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;
import org.objectweb.asm.tree.*;

/** Offline, descriptor-aware readability remapper. Never loads the input classes. */
public class Deobfuscate {
    static final Map<String, ClassNode> classes = new TreeMap<>();
    static final Map<String, String> classNames = new HashMap<>();
    static final Map<String, String> fields = new HashMap<>(), methods = new HashMap<>();
    static final Map<String, String> mcFields = new HashMap<>(), mcMethods = new HashMap<>();
    static final Map<String, String> parent = new HashMap<>();
    static final List<String> audit = new ArrayList<>();
    static final Set<String> occupiedClasses = new HashSet<>();
    static final Set<String> touchedStrings = new TreeSet<>();
    static final Map<String, String> typeAliases = Map.ofEntries(
        Map.entry("I", "integer"), Map.entry("J", "longValue"), Map.entry("Z", "flag"),
        Map.entry("F", "floatValue"), Map.entry("D", "doubleValue"), Map.entry("B", "byteValue"),
        Map.entry("C", "character"), Map.entry("S", "shortValue"));
    static String key(String owner, String name, String desc) { return owner + "\t" + name + "\t" + desc; }
    static boolean obfuscated(String name) {
        return (name.matches("[A-Za-z]{1,2}") && !Set.of("of", "is", "or", "as").contains(name)) || name.matches("[lIOoi]{3,}") || name.matches("[a-zA-Z][A-Z]_") || name.startsWith("jc$");
    }
    static String upper(String s) { return s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1); }
    static String lower(String s) { return s.isEmpty() ? s : Character.toLowerCase(s.charAt(0)) + s.substring(1); }
    static String identifier(String s) {
        StringBuilder result = new StringBuilder();
        for (String word : s.split("[^A-Za-z0-9]+")) if (!word.isEmpty()) result.append(upper(word));
        if (result.isEmpty()) return "Recovered";
        if (Character.isDigit(result.charAt(0))) result.insert(0, "Recovered");
        return result.toString();
    }
    static String typeName(String desc) {
        Type type = Type.getType(desc);
        if (type.getSort() == Type.ARRAY) return typeName(type.getElementType().getDescriptor()) + "Array";
        if (type.getSort() != Type.OBJECT) return typeAliases.getOrDefault(desc, "value");
        String name = classNames.getOrDefault(type.getInternalName(), type.getInternalName());
        name = name.substring(Math.max(name.lastIndexOf('/'), name.lastIndexOf('$')) + 1);
        return lower(name.replaceAll("[^A-Za-z0-9_]", ""));
    }
    static String allocateClass(String old, String suggested) {
        String result = suggested;
        for (int i = 2; occupiedClasses.contains(result.toLowerCase(Locale.ROOT)); i++) result = suggested + i;
        occupiedClasses.add(result.toLowerCase(Locale.ROOT));
        classNames.put(old, result);
        return result;
    }
    static void readYarn(Path path) throws IOException {
        String owner = null;
        for (String line : Files.readAllLines(path)) {
            String[] parts = line.split("\t", -1);
            if (parts[0].equals("c")) { owner = parts[1]; classNames.put(owner, parts[2]); }
            else if (parts.length >= 5 && parts[0].isEmpty() && (parts[1].equals("f") || parts[1].equals("m"))) {
                Map<String,String> global = parts[1].equals("f") ? mcFields : mcMethods;
                String old = parts[3], named = parts[4];
                if (!old.equals(named)) {
                    Map<String,String> exact = parts[1].equals("f") ? fields : methods;
                    exact.put(key(owner, old, parts[2]), named);
                    // Only intermediary IDs are globally unique. Ordinary names
                    // such as get() must NEVER rename unrelated library APIs.
                    if (old.matches("(?:method|field)_\\d+"))
                        global.merge(old, named, (a,b) -> a.equals(b) ? a : "");
                }
            }
        }
    }
    static String annotationName(ClassNode cls) {
        List<AnnotationNode> annotations = new ArrayList<>();
        if (cls.visibleAnnotations != null) annotations.addAll(cls.visibleAnnotations);
        if (cls.invisibleAnnotations != null) annotations.addAll(cls.invisibleAnnotations);
        for (AnnotationNode ann : annotations) {
            if (!ann.desc.equals("Laethereal/core/ModuleRegister;") && !ann.desc.equals("Laethereal/command/f;")) continue;
            for (int i = 0; i < ann.values.size(); i += 2)
                if (ann.values.get(i).equals("a") && ann.values.get(i + 1) instanceof String name) return identifier(name) + (ann.desc.equals("Laethereal/command/f;") ? "Command" : "");
        }
        return null;
    }
    static void nameClasses(Path overrides) throws IOException {
        Map<String,String> explicit = new HashMap<>();
        for (String line : Files.readAllLines(overrides)) {
            if (line.isBlank() || line.startsWith("#")) continue;
            String[] p = line.split("\t"); explicit.put(p[0], p[1]);
        }
        // Reserve existing descriptive names before allocating replacements.
        for (String name : classes.keySet()) {
            String leaf = name.substring(name.lastIndexOf('/') + 1);
            if (!leaf.contains("$") && !obfuscated(leaf) && !explicit.containsKey(name)) {
                classNames.put(name, name); occupiedClasses.add(name.toLowerCase(Locale.ROOT));
            }
        }
        List<String> order = new ArrayList<>(classes.keySet());
        order.sort(Comparator.comparingInt((String n) -> n.split("\\$", -1).length).thenComparing(n -> n));
        int classSerial = 0;
        for (String name : order) {
            ClassNode cls = classes.get(name);
            String pkg = name.substring(0, name.lastIndexOf('/') + 1);
            String leaf = name.substring(name.lastIndexOf('/') + 1);
            String suggestion;
            if (explicit.containsKey(name)) suggestion = explicit.get(name);
            else if (name.contains("$")) {
                int index = name.lastIndexOf('$');
                String outer = name.substring(0, index), inner = name.substring(index + 1);
                String outerName = classNames.getOrDefault(outer, outer);
                if (inner.matches("[0-9]+")) suggestion = outerName + "$" + inner;
                else if (obfuscated(inner)) {
                    String role = (cls.access & Opcodes.ACC_ENUM) != 0 ? "State" : (cls.access & Opcodes.ACC_INTERFACE) != 0 ? "Contract" : "NestedType";
                    suggestion = outerName + "$" + role + String.format(Locale.ROOT,"%03d",++classSerial);
                } else suggestion = outerName + "$" + inner;
            } else if (classNames.containsKey(name)) continue;
            else if (annotationName(cls) != null) suggestion = pkg + annotationName(cls);
            else {
                String lastPackage = pkg.substring(0, pkg.length() - 1);
                lastPackage = lastPackage.substring(lastPackage.lastIndexOf('/') + 1);
                String role = (cls.access & Opcodes.ACC_ANNOTATION) != 0 ? "Annotation" : (cls.access & Opcodes.ACC_ENUM) != 0 ? "Enum" : (cls.access & Opcodes.ACC_INTERFACE) != 0 ? "Contract" : "Component";
                suggestion = pkg + upper(lastPackage) + role + String.format(Locale.ROOT,"%03d",++classSerial);
            }
            allocateClass(name, suggestion);
        }
    }
    static String root(String k) {
        String p = parent.get(k);
        if (p == null || p.equals(k)) return k;
        String r = root(p); parent.put(k,r); return r;
    }
    static void union(String a, String b) {
        a = root(a); b = root(b);
        if (!a.equals(b)) parent.put(a.compareTo(b) < 0 ? b : a, a.compareTo(b) < 0 ? a : b);
    }
    static void ancestors(String owner, Set<String> result) {
        ClassNode cls = classes.get(owner);
        if (cls == null) return;
        List<String> bases = new ArrayList<>(cls.interfaces);
        if (cls.superName != null) bases.add(cls.superName);
        for (String base : bases) if (result.add(base)) ancestors(base, result);
    }
    static String declaring(String owner, String name, String desc, boolean method, Set<String> seen) {
        if (!seen.add(owner)) return null;
        ClassNode cls = classes.get(owner);
        if (cls == null) return null;
        if (method) {
            for (MethodNode m : cls.methods) if (m.name.equals(name) && m.desc.equals(desc)) return key(owner,name,desc);
        } else for (FieldNode f : cls.fields) if (f.name.equals(name) && f.desc.equals(desc)) return key(owner,name,desc);
        if (cls.superName != null) {
            String found = declaring(cls.superName,name,desc,method,seen);
            if (found != null) return found;
        }
        for (String iface : cls.interfaces) {
            String found = declaring(iface,name,desc,method,seen);
            if (found != null) return found;
        }
        return null;
    }
    static String fieldName(String owner, String name, String desc) {
        String result = fields.get(key(owner,name,desc));
        if (result == null) {
            String declaration = declaring(owner,name,desc,false,new HashSet<>());
            if (declaration != null) result = fields.get(declaration);
        }
        if ((result == null || result.isEmpty()) && (owner.startsWith("net/minecraft/") || classNames.containsKey(owner))) result = mcFields.get(name);
        return result == null || result.isEmpty() ? name : result;
    }
    static String methodName(String owner, String name, String desc) {
        String result = methods.get(key(owner,name,desc));
        if (result == null) {
            String declaration = declaring(owner,name,desc,true,new HashSet<>());
            if (declaration != null) result = methods.get(declaration);
        }
        if ((result == null || result.isEmpty()) && (owner.startsWith("net/minecraft/") || classNames.containsKey(owner))) result = mcMethods.get(name);
        return result == null || result.isEmpty() ? name : result;
    }
    static void nameMembers(Path overrides) throws IOException {
        Map<String,String> explicit = new HashMap<>();
        for (String line : Files.readAllLines(overrides)) {
            if (line.isBlank() || line.startsWith("#")) continue;
            String[] p = line.split("\t");
            ClassNode owner = classes.get(p[1]);
            boolean exists = owner != null && (p[0].equals("field")
                ? owner.fields.stream().anyMatch(f -> f.name.equals(p[2]) && f.desc.equals(p[3]))
                : owner.methods.stream().anyMatch(m -> m.name.equals(p[2]) && m.desc.equals(p[3])));
            if (!exists) throw new IllegalArgumentException("Override does not match a declared member: " + line);
            explicit.put(key(p[1],p[2],p[3]),p[4]);
        }
        Map<String,String> parameterFields = new HashMap<>();
        for (ClassNode cls : classes.values()) for (MethodNode method : cls.methods) if (method.name.equals("<init>")) {
            Type[] parameters = Type.getArgumentTypes(method.desc);
            Map<Integer,String> parameterNames = new HashMap<>();
            int slot = 1;
            for (int i = 0; i < parameters.length; i++) {
                String name = method.parameters != null && i < method.parameters.size() ? method.parameters.get(i).name : null;
                if (name == null && method.localVariables != null) for (LocalVariableNode local : method.localVariables)
                    if (local.index == slot) { name = local.name; break; }
                if (name != null && !obfuscated(name) && !name.matches("(?:var|arg)\\d+")) parameterNames.put(slot,name);
                slot += parameters[i].getSize();
            }
            for (AbstractInsnNode instruction : method.instructions) if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.PUTFIELD && field.owner.equals(cls.name)) {
                AbstractInsnNode previous = instruction.getPrevious();
                while (previous != null && previous.getOpcode() < 0) previous = previous.getPrevious();
                if (previous instanceof VarInsnNode variable && parameterNames.containsKey(variable.var))
                    parameterFields.putIfAbsent(key(field.owner,field.name,field.desc),parameterNames.get(variable.var));
            }
        }
        for (ClassNode cls : classes.values()) {
            Set<String> used = new HashSet<>();
            for (FieldNode f : cls.fields) if (!obfuscated(f.name)) used.add(f.name);
            int count = 0;
            for (FieldNode f : cls.fields) {
                String k = key(cls.name,f.name,f.desc);
                String proposed = explicit.get(k);
                if (proposed == null && !obfuscated(f.name)) continue;
                if (proposed == null) proposed = parameterFields.get(k);
                if (proposed == null) proposed = typeName(f.desc) + "Field" + (++count);
                String result = proposed;
                for (int i = 2; used.contains(result); i++) result = proposed + i;
                used.add(result); fields.put(k,result);
            }
        }
        Map<String, MethodNode> nodes = new TreeMap<>();
        for (ClassNode cls : classes.values()) for (MethodNode m : cls.methods) {
            if (m.name.startsWith("<")) continue;
            String k = key(cls.name,m.name,m.desc); parent.put(k,k); nodes.put(k,m);
        }
        for (ClassNode cls : classes.values()) {
            Set<String> bases = new HashSet<>(); ancestors(cls.name,bases);
            // Connect implementations to every available superclass/interface declaration.
            for (MethodNode m : cls.methods) if (!m.name.startsWith("<") && (m.access & (Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE)) == 0) {
                String k = key(cls.name,m.name,m.desc);
                for (String base : bases) {
                    ClassNode ancestor = classes.get(base);
                    if (ancestor == null) continue;
                    for (MethodNode candidate : ancestor.methods)
                        if (candidate.name.equals(m.name) && candidate.desc.equals(m.desc) && (candidate.access & (Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE)) == 0)
                            union(k,key(base,m.name,m.desc));
                }
            }
            // An inherited superclass implementation can satisfy a new interface on this class.
            Map<String,String> inherited = new HashMap<>();
            for (String base : bases) {
                ClassNode ancestor = classes.get(base);
                if (ancestor == null) continue;
                for (MethodNode m : ancestor.methods) if (!m.name.startsWith("<") && (m.access & (Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE)) == 0) {
                    String signature = m.name + m.desc, k = key(base,m.name,m.desc);
                    if (inherited.containsKey(signature)) union(k,inherited.get(signature)); else inherited.put(signature,k);
                }
            }
        }
        Map<String,List<String>> groups = new TreeMap<>();
        for (String k : nodes.keySet()) groups.computeIfAbsent(root(k), ignored -> new ArrayList<>()).add(k);
        int serial = 0;
        for (List<String> group : groups.values()) {
            String selected = null;
            for (String k : group) if (explicit.containsKey(k)) {
                if (selected != null && !selected.equals(explicit.get(k))) throw new IllegalStateException("Conflicting explicit method names: " + group);
                selected = explicit.get(k);
            }
            String[] parts = group.get(0).split("\t");
            if (selected == null && !obfuscated(parts[1])) continue;
            String suffix = String.format(Locale.ROOT,"%04d",++serial);
            if (selected == null) {
                MethodNode m = nodes.get(group.get(0));
                List<AbstractInsnNode> code = new ArrayList<>();
                for (AbstractInsnNode insn : m.instructions) if (insn.getOpcode() >= 0) code.add(insn);
                if (code.size() <= 4) {
                    for (AbstractInsnNode insn : code) if (insn instanceof FieldInsnNode field) {
                        String fieldName = fieldName(field.owner,field.name,field.desc);
                        if (field.getOpcode() == Opcodes.GETFIELD || field.getOpcode() == Opcodes.GETSTATIC)
                            selected = (Type.getReturnType(m.desc).equals(Type.BOOLEAN_TYPE) ? "is" : "get") + upper(fieldName) + "_" + suffix;
                        else if (Type.getArgumentTypes(m.desc).length == 1) selected = "set" + upper(fieldName) + "_" + suffix;
                    }
                }
                if (selected == null) {
                    Type result = Type.getReturnType(m.desc);
                    Type[] args = Type.getArgumentTypes(m.desc);
                    if (args.length == 1 && args[0].getSort() == Type.OBJECT && args[0].getInternalName().startsWith("aethereal/event/"))
                        selected = "on" + upper(typeName(args[0].getDescriptor())) + "_" + suffix;
                    else selected = (result.equals(Type.BOOLEAN_TYPE) ? "checkCondition" : result.equals(Type.VOID_TYPE) ? "performAction" : "computeValue") + "_" + suffix;
                }
            }
            for (String k : group) methods.put(k,selected);
        }
        // Record component metadata, backing fields and accessors must agree.
        for (ClassNode cls : classes.values()) if (cls.recordComponents != null) {
            for (RecordComponentNode component : cls.recordComponents) {
                String accessor = key(cls.name,component.name,"()" + component.descriptor);
                // Obfuscators can rename accessors independently of Record metadata.
                if (!nodes.containsKey(accessor)) for (MethodNode candidate : cls.methods) {
                    if (!candidate.desc.equals("()" + component.descriptor)) continue;
                    List<AbstractInsnNode> code = Arrays.stream(candidate.instructions.toArray()).filter(n -> n.getOpcode() >= 0).toList();
                    if (code.size() == 3 && code.get(1) instanceof FieldInsnNode field && field.getOpcode() == Opcodes.GETFIELD
                        && field.owner.equals(cls.name) && field.name.equals(component.name) && field.desc.equals(component.descriptor)) {
                        accessor = key(cls.name,candidate.name,candidate.desc); break;
                    }
                }
                String named = fieldName(cls.name,component.name,component.descriptor);
                for (String k : nodes.keySet()) if (root(k).equals(root(accessor))) methods.put(k,named);
            }
            String descriptor = "(" + cls.recordComponents.stream().map(c -> c.descriptor).reduce("",String::concat) + ")V";
            for (MethodNode method : cls.methods) if (method.name.equals("<init>") && method.desc.equals(descriptor)) {
                int slot = 1;
                for (int i = 0; i < cls.recordComponents.size(); i++) {
                    RecordComponentNode component = cls.recordComponents.get(i);
                    String named = fieldName(cls.name,component.name,component.descriptor);
                    if (method.parameters != null && i < method.parameters.size()) method.parameters.get(i).name = named;
                    if (method.localVariables != null) for (LocalVariableNode local : method.localVariables)
                        if (local.index == slot && local.desc.equals(component.descriptor)) local.name = named;
                    slot += Type.getType(component.descriptor).getSize();
                }
            }
        }
    }
    static String rewriteString(String value) {
        if (classNames.containsKey(value)) return classNames.get(value);
        String internal = value.replace('.','/');
        if (classNames.containsKey(internal)) return classNames.get(internal).replace('/','.');
        // Published intermediary IDs are globally unique unless Yarn explicitly disagrees.
        Matcher matcher = Pattern.compile("(?:method|field)_\\d+").matcher(value);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String id = matcher.group(), named = id.startsWith("method_") ? mcMethods.get(id) : mcFields.get(id);
            matcher.appendReplacement(result,Matcher.quoteReplacement(named == null || named.isEmpty() ? id : named));
        }
        matcher.appendTail(result);
        String rewritten = result.toString();
        // Class descriptors in mixin selectors and reflection metadata.
        matcher = Pattern.compile("L([^;]+);").matcher(rewritten); result = new StringBuffer();
        while (matcher.find()) matcher.appendReplacement(result,Matcher.quoteReplacement("L" + classNames.getOrDefault(matcher.group(1),matcher.group(1)) + ";"));
        matcher.appendTail(result);
        rewritten = result.toString();
        if (!rewritten.equals(value)) touchedStrings.add(value + "\t" + rewritten);
        return rewritten;
    }
    static class Names extends Remapper {
        Names() { super(Opcodes.ASM9); }
        @Override public String map(String name) { return classNames.getOrDefault(name,name); }
        @Override public String mapFieldName(String owner,String name,String desc) { return fieldName(owner,name,desc); }
        @Override public String mapRecordComponentName(String owner,String name,String desc) { return fieldName(owner,name,desc); }
        @Override public String mapMethodName(String owner,String name,String desc) { return methodName(owner,name,desc); }
        @Override public String mapAnnotationAttributeName(String desc,String name) {
            String owner = Type.getType(desc).getInternalName();
            ClassNode cls = classes.get(owner);
            if (cls != null) for (MethodNode method : cls.methods) if (method.name.equals(name)) return methodName(owner,name,method.desc);
            return name;
        }
        @Override public Object mapValue(Object value) {
            if (value instanceof String text) return rewriteString(text);
            return super.mapValue(value);
        }
    }
    static void writeJar(Path input, Path output) throws IOException {
        Names names = new Names();
        Set<String> entries = new HashSet<>();
        try (JarFile jar = new JarFile(input.toFile()); JarOutputStream out = new JarOutputStream(Files.newOutputStream(output))) {
            for (JarEntry entry : Collections.list(jar.entries())) {
                if (entry.isDirectory()) continue;
                byte[] data = jar.getInputStream(entry).readAllBytes();
                String name = entry.getName();
                if (name.endsWith(".class")) {
                    ClassReader reader = new ClassReader(data); ClassWriter writer = new ClassWriter(0);
                    classes.get(reader.getClassName()).accept(new ClassRemapper(writer,names) {
                        @Override protected MethodVisitor createMethodRemapper(MethodVisitor visitor) {
                            return new MethodRemapper(visitor,names) {
                                @Override public void visitInvokeDynamicInsn(String name,String descriptor,Handle bootstrap,Object... arguments) {
                                    Type result = Type.getReturnType(descriptor);
                                    if (result.getSort() == Type.OBJECT && arguments.length > 0 && arguments[0] instanceof Type sam && sam.getSort() == Type.METHOD)
                                        name = methodName(result.getInternalName(),name,sam.getDescriptor());
                                    super.visitInvokeDynamicInsn(name,descriptor,bootstrap,arguments);
                                }
                                @Override public void visitLocalVariable(String name,String descriptor,String signature,Label start,Label end,int index) {
                                    if (obfuscated(name) && !name.equals("this")) name = typeName(descriptor) + "Local" + index;
                                    super.visitLocalVariable(name,descriptor,signature,start,end,index);
                                }
                            };
                        }
                    });
                    data = writer.toByteArray(); name = names.map(reader.getClassName()) + ".class";
                } else if (name.endsWith(".json") || name.endsWith(".accesswidener")) {
                    String text = new String(data,StandardCharsets.UTF_8);
                    for (String old : classes.keySet()) {
                        String mapped = classNames.getOrDefault(old,old);
                        if (!old.equals(mapped)) text = text.replace("\"" + old.replace('/','.') + "\"", "\"" + mapped.replace('/','.') + "\"");
                    }
                    data = rewriteString(text).getBytes(StandardCharsets.UTF_8);
                } else if (name.matches("META-INF/[^/]+\\.(SF|RSA|DSA|EC)")) continue;
                if (!entries.add(name.toLowerCase(Locale.ROOT))) throw new IllegalStateException("Case-insensitive archive entry collision: " + name);
                out.putNextEntry(new JarEntry(name)); out.write(data); out.closeEntry();
            }
        }
    }
    static void report(Path directory) throws IOException {
        Files.createDirectories(directory);
        try (PrintWriter out = new PrintWriter(directory.resolve("name-mappings.tsv").toFile(),StandardCharsets.UTF_8)) {
            out.println("kind\toriginal_owner\toriginal_name\tdescriptor\treadable_name");
            for (String owner : classes.keySet()) {
                out.println("class\t" + owner + "\t\t\t" + classNames.getOrDefault(owner,owner));
                ClassNode cls = classes.get(owner);
                for (FieldNode f : cls.fields) if (!fieldName(owner,f.name,f.desc).equals(f.name))
                    out.println("field\t" + owner + "\t" + f.name + "\t" + f.desc + "\t" + fieldName(owner,f.name,f.desc));
                for (MethodNode m : cls.methods) if (!methodName(owner,m.name,m.desc).equals(m.name))
                    out.println("method\t" + owner + "\t" + m.name + "\t" + m.desc + "\t" + methodName(owner,m.name,m.desc));
            }
        }
        Files.write(directory.resolve("rewritten-string-selectors.tsv"),touchedStrings,StandardCharsets.UTF_8);
        long renamedClasses = classes.keySet().stream().filter(n -> !n.equals(classNames.get(n))).count();
        System.out.println("Input classes: " + classes.size() + "; renamed classes: " + renamedClasses);
        System.out.println("Minecraft class mappings loaded: " + classNames.keySet().stream().filter(n -> n.startsWith("net/minecraft/")).count());
        System.out.println("Member mappings and string selector changes written to " + directory);
    }
    public static void main(String[] args) throws Exception {
        Path input = Path.of(args[0]);
        try (JarFile jar = new JarFile(input.toFile())) {
            for (JarEntry entry : Collections.list(jar.entries())) if (entry.getName().endsWith(".class")) {
                ClassNode cls = new ClassNode(); new ClassReader(jar.getInputStream(entry)).accept(cls,0); classes.put(cls.name,cls);
            }
        }
        readYarn(Path.of(args[1])); nameClasses(Path.of(args[2])); nameMembers(Path.of(args[3]));
        writeJar(input,Path.of(args[4])); report(Path.of(args[5]));
    }
}
