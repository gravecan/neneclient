package aethereal.util;

public class UtilComponent258 {
   private static UtilComponent258.NestedType406 nestedType406Field1;
   private static boolean flagField2 = false;

   private UtilComponent258() {
   }

   public static String computeValue_3470(String string) {
      if (string == null) {
         throw new IllegalArgumentException("null input");
      }

      String string1 = null;

      try {
         string1 = System.getProperty(string);
      } catch (SecurityException securityException) {
      }

      return string1;
   }

   public static boolean checkCondition_3467(String string) {
      String string1 = computeValue_3470(string);
      return string1 == null ? false : string1.equalsIgnoreCase("true");
   }

   private static UtilComponent258.NestedType406 computeValue_3468() {
      if (nestedType406Field1 != null) {
         return nestedType406Field1;
      }

      if (flagField2) {
         return null;
      }

      nestedType406Field1 = computeValue_3464();
      flagField2 = true;
      return nestedType406Field1;
   }

   private static UtilComponent258.NestedType406 computeValue_3464() {
      try {
         return new UtilComponent258.NestedType406();
      } catch (SecurityException securityException) {
         return null;
      }
   }

   public static Class<?> computeValue_3469() {
      UtilComponent258.NestedType406 nestedType406 = computeValue_3468();
      if (nestedType406 == null) {
         return null;
      }

      Class[] class_ = nestedType406.getClassContext();
      String string = UtilComponent258.class.getName();
      int i = 0;

      while (i < class_.length && !string.equals(class_[i].getName())) {
         i++;
      }

      if (i < class_.length && i + 2 < class_.length) {
         return class_[i + 2];
      } else {
         throw new IllegalStateException("Failed to find org.slf4j.helpers.Util or its caller in the stack; this should not happen");
      }
   }

   public static final void performAction_3465(String string, Throwable throwable) {
      System.err.println(string);
      System.err.println("Reported exception:");
      throwable.printStackTrace();
   }

   public static final void performAction_3466(String string) {
      System.err.println("SLF4J: " + string);
   }

   static final class NestedType406 extends SecurityManager {
      private NestedType406() {
      }

      @Override
      protected Class<?>[] getClassContext() {
         return super.getClassContext();
      }
   }
}
