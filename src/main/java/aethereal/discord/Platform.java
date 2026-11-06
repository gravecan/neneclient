package aethereal.discord;

import java.util.Locale;

public enum Platform {
   WINDOWS,
   MACOS,
   LINUX;

   public static final Platform platformField1 = computeValue_0721();

   private static Platform computeValue_0721() {
      String string = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
      if (string.contains("win")) {
         return WINDOWS;
      } else {
         return !string.contains("mac") && !string.contains("darwin") ? LINUX : MACOS;
      }
   }
}
