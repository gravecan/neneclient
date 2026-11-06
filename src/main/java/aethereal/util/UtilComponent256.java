package aethereal.util;

import java.net.URI;

public class UtilComponent256 {
   private UtilComponent256() {
   }

   public static void performAction_3458(String string, String string1, int i) {
      if (string != null && !string.isBlank()) {
         if (i > 0 && string.length() > i) {
            throw new IllegalArgumentException(string1 + " must be at most " + i + " characters");
         }

         try {
            URI uRI = URI.create(string);
            if (!uRI.isAbsolute() || uRI.getScheme() == null || uRI.getHost() == null || uRI.getHost().isBlank()) {
               throw new IllegalArgumentException(string1 + " must be a valid absolute HTTPS URL: " + string);
            }

            if (!"https".equalsIgnoreCase(uRI.getScheme())) {
               throw new IllegalArgumentException(string1 + " must use https://, got: " + string);
            }
         } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException(string1 + " must be a valid absolute HTTPS URL: " + string, illegalArgumentException);
         }
      } else {
         throw new IllegalArgumentException(string1 + " must not be blank");
      }
   }
}
