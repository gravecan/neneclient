package aethereal.discord;

public enum DiscordBuild {
   STABLE("//discord.com/api"),
   PTB("//ptb.discord.com/api"),
   CANARY("//canary.discord.com/api"),
   DEVELOPMENT("//discordapp.com/api"),
   ANY(null);

   private final String endpoint;

   public String getEndpoint_0716() {
      return this.endpoint;
   }

   DiscordBuild(String string1) {
      this.endpoint = string1;
   }

   public static DiscordBuild computeValue_0717(String string) {
      if (string == null) {
         return ANY;
      }

      for (DiscordBuild discordBuild : values()) {
         if (discordBuild.endpoint != null && string.contains(discordBuild.endpoint)) {
            return discordBuild;
         }
      }

      if (string.contains("canary")) {
         return CANARY;
      } else if (string.contains("ptb")) {
         return PTB;
      } else {
         return string.contains("discord") ? STABLE : ANY;
      }
   }
}
