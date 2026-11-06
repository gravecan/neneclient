package aethereal.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class UtilComponent251 {
   private static boolean checkCondition_3394(String string) {
      return Files.exists(Paths.get(string));
   }

   public static Boolean computeValue_3393() {
      return checkCondition_3397("");
   }

   static boolean checkCondition_3397(String string) {
      String string1 = computeValue_3391(string + "/proc/1/environ", "container");
      return string1 != null ? !string1.isEmpty() : checkCondition_3394(string + "/.dockerenv") || checkCondition_3394(string + "/run/.containerenv");
   }

   private static String computeValue_3391(String string, String string1) {
      try {
         byte[] b = Files.readAllBytes(Paths.get(string));
         String string2 = new String(b, Charset.defaultCharset());
         String[] strings = string2.split(String.valueOf('\u0000'));
         String string3 = string1 + "=";
         return Arrays.stream(strings)
            .filter(string5 -> string5.startsWith(string3))
            .map(string4 -> string4.split("=", 2))
            .map(strings1 -> (String)strings1[1])
            .findFirst()
            .orElse(null);
      } catch (IOException iOException) {
         return null;
      }
   }
}
