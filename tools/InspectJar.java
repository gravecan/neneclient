import java.io.*;
import java.util.*;
import java.util.jar.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

public class InspectJar {
    public static void main(String[] args) throws Exception {
        try (JarFile jar = new JarFile(args[0]); PrintWriter out = new PrintWriter(args[1], java.nio.charset.StandardCharsets.UTF_8)) {
            for (JarEntry entry : Collections.list(jar.entries())) {
                if (!entry.getName().endsWith(".class")) continue;
                ClassNode cls = new ClassNode();
                new ClassReader(jar.getInputStream(entry)).accept(cls, 0);
                out.println("CLASS\t" + cls.name + "\t" + cls.superName + "\t" + cls.interfaces);
                for (FieldNode field : cls.fields) out.println("FIELD\t" + field.name + "\t" + field.desc);
                for (MethodNode method : cls.methods) {
                    List<String> strings = new ArrayList<>();
                    for (AbstractInsnNode insn : method.instructions) {
                        if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof String text && text.length() < 140) strings.add(text.replace('\n', ' ').replace('\t', ' '));
                    }
                    if (!strings.isEmpty()) out.println("STRINGS\t" + method.name + "\t" + String.join(" | ", strings));
                }
            }
        }
    }
}
