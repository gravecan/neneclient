import com.sun.source.util.JavacTask;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import javax.tools.*;

/** Parse only: dependencies need not be present. Does not assert compilability. */
public class CheckJavaSyntax {
    public static void main(String[] args) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        try (StandardJavaFileManager files = compiler.getStandardFileManager(diagnostics,Locale.ROOT,StandardCharsets.UTF_8);
             var paths = Files.walk(Path.of(args[0]))) {
            List<Path> java = paths.filter(p -> p.toString().endsWith(".java")).sorted().toList();
            JavacTask task = (JavacTask)compiler.getTask(null,files,diagnostics,
                List.of("--release","21","-proc:none","-Xmaxerrs","10000"),null,files.getJavaFileObjectsFromPaths(java));
            int count = 0;
            for (var ignored : task.parse()) count++;
            List<String> report = new ArrayList<>();
            report.add("Java source files parsed: " + count);
            List<Diagnostic<? extends JavaFileObject>> errors = diagnostics.getDiagnostics().stream().filter(d -> d.getKind() == Diagnostic.Kind.ERROR).toList();
            report.add("Java 21 syntax errors: " + errors.size());
            report.add("Parsing only; type resolution, dependencies and full compilation were not checked.");
            for (var error : errors) report.add(error.toString());
            Files.write(Path.of(args[1]),report); report.forEach(System.out::println);
            if (!errors.isEmpty()) throw new IllegalStateException("Java syntax errors found");
        }
    }
}
