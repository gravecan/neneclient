package aethereal.core;

import aethereal.util.UtilComponent254;
import aethereal.util.UtilComponent271;
import aethereal.util.UtilComponent282;
import com.google.gson.JsonObject;
import java.util.Objects;
import java.util.Optional;

public final class CoreComponent040 {
   private final String stringField1;
   private final String username;
   private final String stringField2;
   private final String globalName;
   private final String avatar;
   private final boolean bot;

   public String getStringField1_0706() {
      return this.stringField1;
   }

   public String getUsername_0703() {
      return this.username;
   }

   public String getStringField2_0695() {
      return this.stringField2;
   }

   public String getGlobalName_0694() {
      return this.globalName;
   }

   public String getAvatar_0693() {
      return this.avatar;
   }

   public boolean isBot_0698() {
      return this.bot;
   }

   public CoreComponent040(String string, String string1, String string2, String string3, String string4, boolean bl) {
      this.stringField1 = string;
      this.username = string1;
      this.stringField2 = UtilComponent254.computeValue_3455(string2, "0");
      this.globalName = string3;
      this.avatar = string4;
      this.bot = bl;
   }

   public static CoreComponent040 computeValue_0702(JsonObject jsonObject) {
      return new CoreComponent040(
         UtilComponent271.computeValue_3550(jsonObject, "id", "0"),
         UtilComponent271.computeValue_3550(jsonObject, "username", "Unknown"),
         UtilComponent271.computeValue_3550(jsonObject, "discriminator", "0"),
         UtilComponent271.computeValue_3548(jsonObject, "global_name").orElse(null),
         UtilComponent271.computeValue_3548(jsonObject, "avatar").orElse(null),
         UtilComponent271.checkCondition_3551(jsonObject, "bot", false)
      );
   }

   public Optional<String> getGlobalName_0701() {
      return Optional.ofNullable(this.globalName);
   }

   public Optional<String> getAvatar_0691() {
      return Optional.ofNullable(this.avatar);
   }

   public long computeValue_0699() {
      try {
         return Long.parseLong(this.stringField1);
      } catch (NumberFormatException numberFormatException) {
         throw new IllegalStateException("User ID is not a numeric Discord snowflake: " + this.stringField1, numberFormatException);
      }
   }

   public String computeValue_0704() {
      return UtilComponent282.computeValue_3613(UtilComponent254.computeValue_3454(this.globalName), this.username);
   }

   public String getUsername_0690() {
      return this.username;
   }

   public String computeValue_0696() {
      return "0".equals(this.stringField2) ? this.username : this.username + "#" + this.stringField2;
   }

   public Optional<String> computeValue_0697() {
      return this.getAvatar_0691().map(string -> {
         String string1 = string.startsWith("a_") ? "gif" : "png";
         return "https://cdn.discordapp.com/avatars/" + this.stringField1 + "/" + string + "." + string1;
      });
   }

   public String computeValue_0692() {
      int i;
      try {
         if ("0".equals(this.stringField2)) {
            i = (int)((this.computeValue_0699() >> 22) % 6L);
         } else {
            i = Integer.parseInt(this.stringField2) % 5;
         }
      } catch (RuntimeException runtimeException) {
         i = 0;
      }

      return "https://cdn.discordapp.com/embed/avatars/" + i + ".png";
   }

   public String computeValue_0700() {
      return this.computeValue_0697().orElseGet(this::computeValue_0692);
   }

   public String getStringField1_0707() {
      return "<@" + this.stringField1 + ">";
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return !(object instanceof CoreComponent040 coreComponent0401) ? false : Objects.equals(this.stringField1, coreComponent0401.stringField1);
      }
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(this.stringField1);
   }

   @Override
   public String toString() {
      return "User:" + this.computeValue_0696() + "(" + this.stringField1 + ")";
   }
}
