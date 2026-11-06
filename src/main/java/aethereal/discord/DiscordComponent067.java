package aethereal.discord;

import aethereal.lib.log4j.Log4jComponent143;
import aethereal.lib.log4j.Log4jComponent144;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiscordComponent067 {
   private static final Log4jComponent144 log4jComponent144Field1 = Log4jComponent143.computeValue_1311(DiscordComponent067.class);
   private static final List<String> listField2 = List.of("XDG_RUNTIME_DIR", "TMPDIR", "TMP", "TEMP");
   private static final Path pathField3 = computeValue_0939(System.getenv(), System.getProperty("java.io.tmpdir"), System.getProperty("user.name", ""));
   static final int integerField4 = 10;

   private DiscordComponent067() {
   }

   static List<String> computeValue_0933() {
      ArrayList arrayList = new ArrayList(10);

      for (int i = 0; i < 10; i++) {
         arrayList.add(computeValue_0934(i));
      }

      return arrayList;
   }

   public static List<String> locateAll() {
      return computeValue_0933();
   }

   static String computeValue_0934(int i) {
      switch (Platform.platformField1) {
         case WINDOWS:
            return "\\\\.\\pipe\\discord-ipc-" + i;
         case MACOS:
         case LINUX:
            return pathField3.resolve("discord-ipc-" + i).toString();
         default:
            throw new IncompatibleClassChangeError();
      }
   }

   static Path computeValue_0939(Map<String, String> map, String string, String string1) {
      for (String string2 : listField2) {
         Path path = computeValue_0935((String)map.get(string2), string1);
         if (path != null) {
            log4jComponent144Field1.performAction_1316("Using {} = {} for pipe directory", string2, path);
            return path;
         }
      }

      Path path1 = computeValue_0935(string, string1);
      if (path1 != null) {
         log4jComponent144Field1.performAction_1316("Using java.io.tmpdir = {} for pipe directory", path1);
         return path1;
      } else {
         Path path2 = computeValue_0935("/tmp", string1);
         return path2 != null ? path2 : Path.of("/tmp").toAbsolutePath().normalize();
      }
   }

   static Path computeValue_0935(String string, String string1) {
      if (string != null && !string.isBlank()) {
         try {
            return computeValue_0937(Path.of(string), string1);
         } catch (InvalidPathException invalidPathException) {
            log4jComponent144Field1.performAction_1316("Ignoring invalid pipe directory path: {}", string, invalidPathException);
            return null;
         }
      } else {
         return null;
      }
   }

   static Path computeValue_0937(Path path, String string) {
      if (path == null) {
         return null;
      }

      try {
         if (!Files.isSymbolicLink(path) && Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            Path path1 = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
            if (!Files.isDirectory(path1, LinkOption.NOFOLLOW_LINKS)) {
               return null;
            } else if (checkCondition_0936(path1) && !checkCondition_0938(path1, string)) {
               log4jComponent144Field1.performAction_1316("Ignoring pipe directory not owned by trusted user: {}", path1);
               return null;
            } else {
               return path1;
            }
         } else {
            return null;
         }
      } catch (IOException iOException) {
         log4jComponent144Field1.performAction_1316("Ignoring unusable pipe directory: {}", path, iOException);
         return null;
      }
   }

   private static boolean checkCondition_0936(Path path) {
      return path.getFileSystem().supportedFileAttributeViews().contains("posix");
   }

   private static boolean checkCondition_0938(Path path, String string) throws IOException {
      if (string != null && !string.isBlank()) {
         String string1 = Files.getOwner(path, LinkOption.NOFOLLOW_LINKS).getName();
         return string.equals(string1) || "root".equals(string1);
      } else {
         return true;
      }
   }
}
