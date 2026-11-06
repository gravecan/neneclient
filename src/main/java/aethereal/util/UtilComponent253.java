package aethereal.util;

import aethereal.lib.log4j.Log4jComponent149;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.stream.Collector;

public class UtilComponent253 {
   private static final int integerField1 = 1024;
   private static final int integerField2 = 8192;
   static final String[] stringArrayField3 = new String[]{
      "",
      "",
      "  ",
      "   ",
      "    ",
      "     ",
      "      ",
      "       ",
      "        ",
      "         ",
      "          ",
      "           ",
      "            ",
      "             ",
      "              ",
      "               ",
      "                ",
      "                 ",
      "                  ",
      "                   ",
      "                    "
   };
   private static final Pattern patternField4 = Pattern.compile("^/(?>(?>\\.\\.?/)+)");
   private static final Pattern patternField5 = Pattern.compile("^[a-zA-Z][a-zA-Z0-9+-.]*:");
   private static final Pattern patternField6 = Pattern.compile("[\\x00-\\x1f]*");
   private static final Log4jComponent149<StringBuilder> log4jComponent149Field7 = new Log4jComponent149<>(() -> new StringBuilder(1024));

   public static String computeValue_3447(Collection<?> collection, String string) {
      return computeValue_3448(collection.iterator(), string);
   }

   public static String computeValue_3448(Iterator<?> iterator, String string) {
      if (!iterator.hasNext()) {
         return "";
      }

      String string1 = iterator.next().toString();
      if (!iterator.hasNext()) {
         return string1;
      }

      UtilComponent253.NestedType405 nestedType405 = new UtilComponent253.NestedType405(string);
      nestedType405.computeValue_3453(string1);

      while (iterator.hasNext()) {
         nestedType405.computeValue_3453(iterator.next());
      }

      return nestedType405.computeValue_3452();
   }

   public static String computeValue_3449(String[] strings, String string) {
      return computeValue_3447(Arrays.asList(strings), string);
   }

   public static String computeValue_3436(int i) {
      return computeValue_3437(i, 30);
   }

   public static String computeValue_3437(int i, int j) {
      UtilComponent259.performAction_3473(i >= 0, "width must be >= 0");
      UtilComponent259.performAction_3471(j >= -1);
      if (j != -1) {
         i = Math.min(i, j);
      }

      if (i < stringArrayField3.length) {
         return stringArrayField3[i];
      }

      char[] c = new char[i];

      for (int k = 0; k < i; k++) {
         c[k] = ' ';
      }

      return String.valueOf(c);
   }

   public static boolean checkCondition_3450(String string) {
      if (string != null && string.length() != 0) {
         int i = string.length();

         for (int j = 0; j < i; j++) {
            if (!checkCondition_3429(string.codePointAt(j))) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   public static boolean checkCondition_3435(String string) {
      return string != null && string.length() != 0 && string.charAt(0) == '\n';
   }

   public static boolean checkCondition_3425(String string) {
      if (string != null && string.length() != 0) {
         int i = string.length();

         for (int j = 0; j < i; j++) {
            if (!Character.isDigit(string.codePointAt(j))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean checkCondition_3429(int i) {
      return i == 32 || i == 9 || i == 10 || i == 12 || i == 13;
   }

   public static boolean checkCondition_3428(int i) {
      return i == 32 || i == 9 || i == 10 || i == 12 || i == 13 || i == 160;
   }

   public static boolean checkCondition_3434(int i) {
      return i == 8203 || i == 173;
   }

   public static String computeValue_3432(String string) {
      StringBuilder stringBuilder = getLog4jComponent149Field7_3424();
      performAction_3445(stringBuilder, string, false);
      return computeValue_3444(stringBuilder);
   }

   public static void performAction_3445(StringBuilder stringBuilder, String string, boolean bl) {
      boolean bl1 = false;
      boolean bl2 = false;
      int i = string.length();
      int j = 0;

      while (true) {
         int k = j;
         if (k >= i) {
            return;
         }

         int l = string.codePointAt(k);
         if (checkCondition_3428(l)) {
            if ((!bl || bl2) && !bl1) {
               stringBuilder.append(' ');
               bl1 = true;
            }
         } else if (!checkCondition_3434(l)) {
            stringBuilder.appendCodePoint(l);
            bl1 = false;
            bl2 = true;
         }

         j = k + Character.charCount(l);
      }
   }

   public static boolean checkCondition_3443(String string, String... strings) {
      for (String string1 : strings) {
         if (string1.equals(string)) {
            return true;
         }
      }

      return false;
   }

   public static boolean checkCondition_3427(String string, String[] strings) {
      return Arrays.binarySearch(strings, string) >= 0;
   }

   public static boolean checkCondition_3430(String string) {
      UtilComponent259.performAction_3472(string);

      for (int i = 0; i < string.length(); i++) {
         char c = string.charAt(i);
         if (c > 127) {
            return false;
         }
      }

      return true;
   }

   public static URL computeValue_3446(URL uRL, String string) throws MalformedURLException {
      String string1 = computeValue_3433(string);
      if (string1.startsWith("?")) {
         string1 = uRL.getPath() + string1;
      }

      URL uRL1 = new URL(uRL, string1);
      String string2 = patternField4.matcher(uRL1.getFile()).replaceFirst("/");
      if (uRL1.getRef() != null) {
         string2 = string2 + "#" + uRL1.getRef();
      }

      return new URL(uRL1.getProtocol(), uRL1.getHost(), uRL1.getPort(), string2);
   }

   public static String computeValue_3426(String string, String string1) {
      String string2 = computeValue_3433(string);
      String string3 = computeValue_3433(string1);

      try {
         try {
            URL uRL = new URL(string2);
            return computeValue_3446(uRL, string3).toExternalForm();
         } catch (MalformedURLException malformedURLException) {
            URL uRL1 = new URL(string3);
            return uRL1.toExternalForm();
         }
      } catch (MalformedURLException malformedURLException1) {
         return patternField5.matcher(string3).find() ? string3 : "";
      }
   }

   private static String computeValue_3433(String string) {
      return patternField6.matcher(string).replaceAll("");
   }

   public static StringBuilder getLog4jComponent149Field7_3424() {
      return log4jComponent149Field7.getLocal_1321();
   }

   public static String computeValue_3444(StringBuilder stringBuilder) {
      UtilComponent259.performAction_3472(stringBuilder);
      String string = stringBuilder.toString();
      if (stringBuilder.length() <= 8192) {
         stringBuilder.delete(0, stringBuilder.length());
         log4jComponent149Field7.performAction_1322(stringBuilder);
      }

      return string;
   }

   public static Collector<CharSequence, ?, String> computeValue_3442(String string) {
      return Collector.of(
         () -> new UtilComponent253.NestedType405(string),
         (nestedType405, charSequence) -> nestedType405.computeValue_3453(charSequence),
         (nestedType405, nestedType4051) -> {
            nestedType405.computeValue_3451(nestedType4051.computeValue_3452());
            return nestedType405;
         },
         nestedType405 -> nestedType405.computeValue_3452()
      );
   }

   public static class NestedType405 {
      final String separator;
      StringBuilder stringBuilderField1 = UtilComponent253.getLog4jComponent149Field7_3424();
      boolean flagField2 = true;

      public NestedType405(String string) {
         this.separator = string;
      }

      public UtilComponent253.NestedType405 computeValue_3453(Object object) {
         UtilComponent259.performAction_3472(this.stringBuilderField1);
         if (!this.flagField2) {
            this.stringBuilderField1.append(this.separator);
         }

         this.stringBuilderField1.append(object);
         this.flagField2 = false;
         return this;
      }

      public UtilComponent253.NestedType405 computeValue_3451(Object object) {
         UtilComponent259.performAction_3472(this.stringBuilderField1);
         this.stringBuilderField1.append(object);
         return this;
      }

      public String computeValue_3452() {
         String string = UtilComponent253.computeValue_3444(this.stringBuilderField1);
         this.stringBuilderField1 = null;
         return string;
      }
   }
}
