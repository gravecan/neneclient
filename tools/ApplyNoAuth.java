import com.sun.source.tree.*;
import com.sun.source.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import javax.tools.*;

/** Reproducible, source-only removal of NeneClient's own activation subsystem. */
public class ApplyNoAuth {
    static final List<String> deleted = List.of(
        "aethereal/license/LicenseManager.java",
        "aethereal/license/LicenseScreen.java",
        "aethereal/license/LicenseComponent157.java",
        "aethereal/ui/screen/ScreenComponent219.java");
    static String renameTokens(String text, Map<String,String> names) {
        StringBuilder result = new StringBuilder(text.length());
        for (int index = 0; index < text.length();) {
            int start = index; char current = text.charAt(index);
            if (text.startsWith("//",index)) {
                index = text.indexOf('\n',index + 2); if (index < 0) index = text.length();
            } else if (text.startsWith("/*",index)) {
                int end = text.indexOf("*/",index + 2); index = end < 0 ? text.length() : end + 2;
            } else if (text.startsWith("\"\"\"",index)) {
                int end = text.indexOf("\"\"\"",index + 3); index = end < 0 ? text.length() : end + 3;
            } else if (current == '"' || current == '\'') {
                index++;
                while (index < text.length()) {
                    char next = text.charAt(index++);
                    if (next == '\\' && index < text.length()) index++;
                    else if (next == current) break;
                }
            } else if (Character.isJavaIdentifierStart(current)) {
                index++;
                while (index < text.length() && Character.isJavaIdentifierPart(text.charAt(index))) index++;
                String token = text.substring(start,index);
                result.append(names.getOrDefault(token,token)); continue;
            } else index++;
            result.append(text,start,index);
        }
        return result.toString();
    }

    static String replaceExactlyOnce(String text, String old, String replacement) {
        int index = text.indexOf(old);
        if (index < 0 || text.indexOf(old,index + old.length()) >= 0)
            throw new IllegalStateException("Expected one occurrence of: " + old);
        return text.substring(0,index) + replacement + text.substring(index + old.length());
    }
    static String replaceMethod(Path file, String name, String replacement) throws Exception {
        String text = Files.readString(file);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        try (StandardJavaFileManager manager = compiler.getStandardFileManager(null,Locale.ROOT,StandardCharsets.UTF_8)) {
            JavacTask task = (JavacTask)compiler.getTask(null,manager,null,List.of("--release","21","-proc:none"),null,manager.getJavaFileObjects(file));
            CompilationUnitTree unit = task.parse().iterator().next();
            SourcePositions positions = Trees.instance(task).getSourcePositions();
            List<long[]> found = new ArrayList<>();
            new TreeScanner<Void,Void>() {
                @Override public Void visitMethod(MethodTree method,Void ignored) {
                    if (method.getName().contentEquals(name)) found.add(new long[]{positions.getStartPosition(unit,method),positions.getEndPosition(unit,method)});
                    return super.visitMethod(method,ignored);
                }
            }.scan(unit,null);
            if (found.size() != 1) throw new IllegalStateException("Expected one method " + name + " in " + file);
            return text.substring(0,(int)found.get(0)[0]) + replacement.strip() + text.substring((int)found.get(0)[1]);
        }
    }
    public static void main(String[] args) throws Exception {
        Path root = Path.of(args[0]), tools = Path.of(args[1]);
        Path source = root.resolve("src/main/java"), reports = root.resolve("reports");
        Files.createDirectories(reports);
        if (Files.exists(reports.resolve("no-auth-changes.tsv"))) throw new IllegalStateException("Already edited. Regenerate into a new directory first.");
        Map<String,String> baseline = new TreeMap<>();
        try (var paths = Files.walk(source)) {
            for (Path file : paths.filter(p -> p.toString().endsWith(".java")).toList())
                baseline.put(source.relativize(file).toString().replace('\\','/'),Files.readString(file));
        }
        for (String relative : deleted) if (!baseline.containsKey(relative)) throw new IllegalStateException("Expected generated source: " + relative);
        Map<String,String> edited = new TreeMap<>(baseline);

        Path title = source.resolve("platform/inject/mixin/TitleScreenMixin.java");
        edited.put("platform/inject/mixin/TitleScreenMixin.java",Files.readString(tools.resolve("no-auth/TitleScreenMixin.java.txt")));
        Path panel = source.resolve("aethereal/ui/screen/ProfilePanel.java");
        edited.put("aethereal/ui/screen/ProfilePanel.java",Files.readString(tools.resolve("no-auth/ProfilePanel.java.txt")));
        Path settings = source.resolve("aethereal/ui/ClientSettingsScreen.java");
        String settingsText = replaceMethod(settings,"performAction_2902",Files.readString(tools.resolve("no-auth/SettingsProfileMethod.java.txt")));
        settingsText = settingsText.replace("import aethereal.license.LicenseManager;\n","")
            .replace("import aethereal.license.LicenseComponent157;\n","");
        settingsText = replaceExactlyOnce(settingsText,"public static String USERNAME = \"flexxbabba\";",
            "public static String USERNAME = MinecraftClient.getInstance().getSession().getUsername();");
        settingsText = replaceExactlyOnce(settingsText,"public static String RANK = \"OWNER\";","public static String RANK = \"\";");
        edited.put("aethereal/ui/ClientSettingsScreen.java",settingsText);
        Path renderer = source.resolve("aethereal/ui/screen/MenuRenderer.java");
        String rendererText = replaceExactlyOnce(Files.readString(renderer),"public static String USERNAME = \"flexxbabba\";",
            "public static String USERNAME = MinecraftClient.getInstance().getSession().getUsername();");
        rendererText = replaceExactlyOnce(rendererText,"public static String RANK = \"Owner\";","public static String RANK = \"\";");
        edited.put("aethereal/ui/screen/MenuRenderer.java",rendererText);
        Path client = source.resolve("aethereal/core/NeneClient.java");
        String clientText = replaceExactlyOnce(Files.readString(client),
            "new UserProfile(\"1\", \"By Ded\", \"By Ded\", \"Owner\", \"01.01.2099 00:00\", \"\")",
            "new UserProfile(\"\", MinecraftClient.getInstance().getSession().getUsername(), \"\", \"Local\", \"\", \"\")");
        edited.put("aethereal/core/NeneClient.java",clientText);
        for (String relative : deleted) edited.remove(relative);

        Map<String,String> names = new HashMap<>();
        for (String line : Files.readAllLines(tools.resolve("source-renames.tsv"))) if (!line.isBlank() && !line.startsWith("#")) {
            String[] parts = line.split("\t"); names.put(parts[0],parts[1]);
        }
        List<String> changes = new ArrayList<>(List.of("source\tchange"));
        for (String relative : baseline.keySet()) {
            if (!edited.containsKey(relative)) { changes.add(relative + "\tremoved client activation source"); continue; }
            String updated = renameTokens(edited.get(relative),names);
            edited.put(relative,updated);
            if (!updated.equals(baseline.get(relative))) changes.add(relative + "\tmodified");
        }
        // Compute all transformations before touching the source tree.
        for (String relative : deleted) Files.delete(source.resolve(relative));
        for (Map.Entry<String,String> entry : edited.entrySet())
            if (!entry.getValue().equals(baseline.get(entry.getKey()))) Files.writeString(source.resolve(entry.getKey()),entry.getValue());
        Files.write(reports.resolve("no-auth-changes.tsv"),changes);
        Files.copy(tools.resolve("source-renames.tsv"),reports.resolve("additional-source-renames.tsv"));
        int untouched = baseline.size() - (changes.size() - 1);
        System.out.println("Client activation removed; " + (changes.size() - 1) + " source files changed/removed; " + untouched + " untouched.");
    }
}
