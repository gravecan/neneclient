package aethereal.discord;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.CodeSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import java.util.jar.JarEntry;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DiscordComponent064 {
   private static final String PROP_LOG = "aethereal.loader.log";
   private static final String PROP_DIR = "aethereal.loader.dir";
   private static final String PROP_PURGE = "aethereal.loader.purge";
   private static final AtomicLong STAGING_SEQ = new AtomicLong();

   private DiscordComponent064() {
   }

   public static void performAction_0898(String string) {
      DiscordComponent064.NestedType307 nestedType307 = new DiscordComponent064.NestedType307();
      nestedType307.performAction_0917("mode=absolute");
      nestedType307.performAction_0917("path=" + string);
      Path path = Paths.get(string);
      nestedType307.performAction_0917("present=" + Files.isRegularFile(path) + " size=" + computeValue_0907(path));
      performAction_0903(string, nestedType307);
   }

   public static void performAction_0911(String string, String string1) {
      DiscordComponent064.NestedType307 nestedType307 = new DiscordComponent064.NestedType307();
      nestedType307.performAction_0917("mode=bundled");
      nestedType307.performAction_0917("resource=" + string + " buildId=" + string1);
      Path path = computeValue_0897(nestedType307);

      try {
         Path path1 = computeValue_0901(computeValue_0905(string, string1, nestedType307), string, path, nestedType307);
         performAction_0903(path1.toAbsolutePath().toString(), nestedType307);
      } catch (Error | RuntimeException error) {
         nestedType307.performAction_0918();
         throw error;
      }
   }

   public static String computeValue_0899(long l, long m) {
      return Long.toHexString(l) + "-" + Long.toHexString(m & 4294967295L);
   }

   static Path computeValue_0901(DiscordComponent064.Contract306 contract306, String string, Path path, DiscordComponent064.NestedType307 nestedType307) {
      String string1 = computeValue_0904(string, contract306.computeValue_0912());
      Path path1 = path.resolve(string1);
      nestedType307.performAction_0917("target=" + path1);
      if (checkCondition_0909(path1, contract306.computeValue_0915())) {
         nestedType307.performAction_0917("already extracted (" + computeValue_0907(path1) + " bytes) - nothing written");
         return path1;
      }

      long l = ProcessHandle.current().pid();
      STAGING_SEQ.incrementAndGet();
      Path path2 = path.resolve(string1 + "." + l + "." + path + ".tmp");

      try {
         Files.createDirectories(path);
         InputStream inputStream = contract306.computeValue_0914();

         try {
            long m = Files.copy(inputStream, path2, StandardCopyOption.REPLACE_EXISTING);
            String string2 = contract306.computeValue_0913();
            path2.getFileName();
            nestedType307.performAction_0917("staged " + m + " bytes from " + nestedType307 + " -> " + string2);
            if (inputStream != null) {
               inputStream.close();
            }

            Files.move(path2, path1);
            nestedType307.performAction_0917("published");
         } catch (Throwable throwable1) {
            if (inputStream != null) {
               try {
                  inputStream.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }
      } catch (FileAlreadyExistsException fileAlreadyExistsException) {
         if (!checkCondition_0909(path1, contract306.computeValue_0915())) {
            nestedType307.performAction_0917("a file of the wrong size holds the name - loading the staged copy instead");
            return path2;
         }

         nestedType307.performAction_0917("another process published it first - using that copy");
         performAction_0908(path2);
      } catch (Throwable throwable2) {
         nestedType307.performAction_0917("extraction failed: " + throwable2);
         performAction_0908(path2);
         if (!Files.isRegularFile(path1)) {
            IllegalStateException illegalStateException = new IllegalStateException(
               "aethereal: cannot extract native library '" + string + "' into " + path + nestedType307.computeValue_0916()
            );
            illegalStateException.initCause(throwable2);
            throw illegalStateException;
         }

         nestedType307.performAction_0917("a copy is on disk - loading that one");
      }

      performAction_0910(path, string, path1, nestedType307);
      return path1;
   }

   static String computeValue_0904(String string, String string1) {
      int i = Math.max(string.lastIndexOf(47), string.lastIndexOf(92));
      String string2 = string.substring(i + 1);
      int j = string2.lastIndexOf(46);
      return j < 0 ? string2 + "-" + string1 : string2.substring(0, j) + "-" + string1 + string2.substring(j);
   }

   private static boolean checkCondition_0909(Path path, long l) {
      try {
         return Files.isRegularFile(path) && (l < 0L || Files.size(path) == l);
      } catch (IOException iOException) {
         return false;
      }
   }

   private static void performAction_0910(Path path, String string, Path path1, DiscordComponent064.NestedType307 nestedType307) {
      if (Boolean.parseBoolean(System.getProperty("aethereal.loader.purge"))) {
         int i = string.lastIndexOf(46);
         String string1 = i < 0 ? string : string.substring(0, i);
         String string2 = i < 0 ? "" : string.substring(i);

         try {
            Stream stream = Files.list(path);

            try {
               for (Path path2 : stream::iterator) {
                  String string3 = path2.getFileName().toString();
                  if (!path2.equals(path1) && string3.startsWith(string1 + "-") && string3.endsWith(string2) && Files.isRegularFile(path2)) {
                     nestedType307.performAction_0917("purge " + string3 + " -> " + Files.deleteIfExists(path2));
                  }
               }

               if (stream != null) {
                  stream.close();
               }
            } catch (Throwable throwable1) {
               if (stream != null) {
                  try {
                     stream.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }
         } catch (Throwable throwable2) {
            nestedType307.performAction_0917("purge skipped: " + throwable2);
         }
      }
   }

   private static DiscordComponent064.Contract306 computeValue_0905(String string, String string1, DiscordComponent064.NestedType307 nestedType307) {
      Path path = computeValue_0902(nestedType307);
      if (path != null) {
         try {
            ZipFile zipFile = new ZipFile(path.toFile());

            try {
               ZipEntry zipEntry = zipFile.getEntry(string);
               if (zipEntry == null) {
                  nestedType307.performAction_0917("no entry '" + string + "' in " + path);
               } else {
                  if (zipEntry.getSize() >= 0L && zipEntry.getCrc() >= 0L) {
                     DiscordComponent064.Contract306 contract306 = computeValue_0900(
                        new DiscordComponent064.NestedType309(path, string, zipEntry.getSize(), computeValue_0899(zipEntry.getSize(), zipEntry.getCrc())),
                        string1,
                        nestedType307
                     );
                     zipFile.close();
                     return contract306;
                  }

                  nestedType307.performAction_0917("entry '" + string + "' has no size/crc in the directory");
               }

               zipFile.close();
            } catch (Throwable throwable2) {
               try {
                  zipFile.close();
               } catch (Throwable throwable1) {
                  throwable2.addSuppressed(throwable1);
               }

               throw throwable2;
            }
         } catch (Throwable throwable3) {
            nestedType307.performAction_0917("cannot read own jar " + path + ": " + throwable3);
         }
      }

      URL uRL = DiscordComponent064.class.getResource("/" + string);
      nestedType307.performAction_0917("resource url=" + uRL);
      if (uRL == null) {
         throw new IllegalStateException("aethereal: bundled native library '" + string + "' is missing from the jar" + nestedType307.computeValue_0916());
      }

      try {
         if (uRL.openConnection() instanceof JarURLConnection jarURLConnection) {
            JarEntry jarEntry = jarURLConnection.getJarEntry();
            if (jarEntry != null && jarEntry.getSize() >= 0L && jarEntry.getCrc() >= 0L) {
               return computeValue_0900(
                  new DiscordComponent064.NestedType308(uRL, jarEntry.getSize(), computeValue_0899(jarEntry.getSize(), jarEntry.getCrc())),
                  string1,
                  nestedType307
               );
            }
         }
      } catch (Throwable throwable) {
         nestedType307.performAction_0917("cannot inspect " + uRL + ": " + throwable);
      }

      nestedType307.performAction_0917("identity unavailable at runtime - falling back to the baked buildId");
      return new DiscordComponent064.NestedType308(uRL, -1L, string1);
   }

   private static DiscordComponent064.Contract306 computeValue_0900(
      DiscordComponent064.Contract306 contract306, String string, DiscordComponent064.NestedType307 nestedType307
   ) {
      String string1 = contract306.computeValue_0912();
      long l = contract306.computeValue_0915();
      contract306.computeValue_0913();
      nestedType307.performAction_0917("bundled id=" + string1 + " size=" + l + " (" + nestedType307 + ")");
      if (!contract306.computeValue_0912().equals(string)) {
         nestedType307.performAction_0917(
            "NOTE: this is not the library the jar was built with (baked id "
               + string
               + ") - the jar was repacked, e.g. with the protected build. That is supported: identity is read from the jar, not from the baked value."
         );
      }

      return contract306;
   }

   private static Path computeValue_0902(DiscordComponent064.NestedType307 nestedType307) {
      try {
         CodeSource codeSource = DiscordComponent064.class.getProtectionDomain().getCodeSource();
         if (codeSource != null && codeSource.getLocation() != null) {
            Path path = Paths.get(codeSource.getLocation().toURI());
            return Files.isRegularFile(path) ? path : null;
         } else {
            return null;
         }
      } catch (Throwable throwable) {
         nestedType307.performAction_0917("code source unavailable: " + throwable);
         return null;
      }
   }

   private static void performAction_0903(String string, DiscordComponent064.NestedType307 nestedType307) {
      try {
         System.load(string);
         nestedType307.performAction_0917("System.load OK");
         nestedType307.performAction_0918();
      } catch (Throwable throwable) {
         nestedType307.performAction_0917("System.load FAILED: " + throwable);
         nestedType307.performAction_0918();
         UnsatisfiedLinkError unsatisfiedLinkError = new UnsatisfiedLinkError(
            "aethereal: failed to load native library " + string + computeValue_0906(throwable) + nestedType307.computeValue_0916()
         );
         unsatisfiedLinkError.initCause(throwable);
         throw unsatisfiedLinkError;
      }
   }

   private static String computeValue_0906(Throwable throwable) {
      String string = String.valueOf(throwable.getMessage()).toLowerCase(Locale.ROOT);
      if (string.contains("dependent libraries")) {
         return "\n  hint: the library imports another DLL that is not on the process search path.\n        A build carrying VMProtect markers imports VMProtectSDK64.dll until the\n        protector removes it - protect the library and put the protected file in\n        the jar (identity is read from the jar, so nothing has to be rebuilt).";
      } else {
         return !string.contains("not a valid win32 application")
               && !string.contains("wrong elf class")
               && !string.contains("bad magic")
               && !string.contains("invalid elf")
            ? ""
            : "\n  hint: the file is not a library for this platform/architecture (a 32-bit JVM on a 64-bit library, or a truncated copy).";
      }
   }

   private static Path computeValue_0897(DiscordComponent064.NestedType307 nestedType307) {
      String string = System.getProperty("aethereal.loader.dir");
      Path path = Paths.get(string != null && !string.isBlank() ? string : System.getProperty("java.io.tmpdir"));
      nestedType307.performAction_0917("dir=" + path + (string == null ? "" : " [aethereal.loader.dir]"));
      return path;
   }

   private static long computeValue_0907(Path path) {
      try {
         return Files.size(path);
      } catch (IOException iOException) {
         return -1L;
      }
   }

   private static void performAction_0908(Path path) {
      try {
         Files.deleteIfExists(path);
      } catch (IOException iOException) {
      }
   }

   interface Contract306 {
      String computeValue_0912();

      long computeValue_0915();

      InputStream computeValue_0914() throws IOException;

      String computeValue_0913();
   }

   private static final class NestedType305 extends FilterInputStream {
      private final ZipFile jar;

      NestedType305(InputStream inputStream, ZipFile zipFile) {
         super(inputStream);
         this.jar = zipFile;
      }

      @Override
      public void close() throws IOException {
         try {
            super.close();
         } finally {
            this.jar.close();
         }
      }
   }

   static final class NestedType307 {
      private static final DateTimeFormatter STAMP = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
      private final List<String> lines = new ArrayList<>();
      private final long startNanos = System.nanoTime();

      void performAction_0917(String string) {
         this.lines.add(string);
      }

      String computeValue_0916() {
         StringBuilder stringBuilder = new StringBuilder("\n  loader trace:");

         for (String string : this.lines) {
            stringBuilder.append("\n    ").append(string);
         }

         return stringBuilder.toString();
      }

      void performAction_0918() {
         String string = System.getProperty("aethereal.loader.log");
         if (string != null && !string.isBlank() && !"0".equals(string) && !"false".equalsIgnoreCase(string)) {
            StringBuilder stringBuilder = new StringBuilder();
            String string1 = LocalDateTime.now().format(STAMP);

            for (String string2 : this.lines) {
               stringBuilder.append(string1).append(" [aethereal-loader] ").append(string2).append(System.lineSeparator());
            }

            stringBuilder.append(string1)
               .append(" [aethereal-loader] done in ")
               .append((System.nanoTime() - this.startNanos) / 1000000L)
               .append(" ms")
               .append(System.lineSeparator());
            this.lines.clear();
            if (!"1".equals(string) && !"true".equalsIgnoreCase(string)) {
               try {
                  Files.writeString(Paths.get(string), stringBuilder.toString(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
               } catch (Throwable throwable) {
                  System.err.println("[aethereal-loader] cannot write " + string + ": " + throwable);
                  System.err.print(stringBuilder);
               }
            } else {
               PrintStream printStream = System.err;
               printStream.print(stringBuilder);
               printStream.flush();
            }
         }
      }
   }

   private static final class NestedType308 implements DiscordComponent064.Contract306 {
      private final URL url;
      private final long size;
      private final String identity;

      private NestedType308(URL uRL, long l, String string) {
         this.url = uRL;
         this.size = l;
         this.identity = string;
      }

      public URL getUrl_0919() {
         return this.url;
      }

      @Override
      public long computeValue_0915() {
         return this.size;
      }

      @Override
      public String computeValue_0912() {
         return this.identity;
      }

      @Override
      public InputStream computeValue_0914() throws IOException {
         return this.url.openStream();
      }

      @Override
      public String computeValue_0913() {
         return this.url.toString();
      }
   }

   private static final class NestedType309 implements DiscordComponent064.Contract306 {
      private final Path jar;
      private final String entry;
      private final long size;
      private final String identity;

      private NestedType309(Path path, String string, long l, String string1) {
         this.jar = path;
         this.entry = string;
         this.size = l;
         this.identity = string1;
      }

      public Path getJar_0920() {
         return this.jar;
      }

      public String getEntry_0921() {
         return this.entry;
      }

      @Override
      public long computeValue_0915() {
         return this.size;
      }

      @Override
      public String computeValue_0912() {
         return this.identity;
      }

      @Override
      public InputStream computeValue_0914() throws IOException {
         ZipFile zipFile = new ZipFile(this.jar.toFile());
         ZipEntry zipEntry = zipFile.getEntry(this.entry);
         if (zipEntry == null) {
            zipFile.close();
            throw new IOException("entry '" + this.entry + "' vanished from " + this.jar);
         } else {
            return new DiscordComponent064.NestedType305(zipFile.getInputStream(zipEntry), zipFile);
         }
      }

      @Override
      public String computeValue_0913() {
         return this.jar + "!/" + this.entry;
      }
   }
}
