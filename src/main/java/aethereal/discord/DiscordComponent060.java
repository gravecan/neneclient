package aethereal.discord;

public final class DiscordComponent060 {
   private final String stringField1;
   private final String stringField2;

   public String getStringField1_0889() {
      return this.stringField1;
   }

   public String getStringField2_0890() {
      return this.stringField2;
   }

   public DiscordComponent060(String string, String string1) {
      String string2 = string != null && !string.isBlank() ? string : "unknown";
      String string3 = string1 == null ? "" : string1;
      this.stringField1 = string2;
      this.stringField2 = string3;
   }

   public static DiscordComponent060 computeValue_0891(Throwable throwable) {
      if (throwable == null) {
         return new DiscordComponent060("unknown", "");
      }

      String string = throwable.getMessage();
      if (string == null || string.isBlank()) {
         string = throwable.toString();
      }

      return new DiscordComponent060(throwable.getClass().getName(), string);
   }
}
