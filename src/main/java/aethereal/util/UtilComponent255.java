package aethereal.util;

public final class UtilComponent255 {
   private String remaining;

   public UtilComponent255(String string) {
      this.remaining = string == null ? "" : string;
   }

   public String computeValue_3456(String string) {
      int i = this.remaining.indexOf(string);
      if (i < 0) {
         String string2 = this.remaining;
         this.remaining = "";
         return string2;
      } else {
         String string1 = this.remaining.substring(0, i);
         this.remaining = this.remaining.substring(i + string.length());
         return string1;
      }
   }

   public String computeValue_3457(String string) {
      int i = this.remaining.indexOf(string);
      if (i < 0) {
         String string2 = this.remaining;
         this.remaining = "";
         return string2;
      } else {
         String string1 = this.remaining.substring(0, i);
         this.remaining = this.remaining.substring(i + string.length());
         return string1;
      }
   }
}
