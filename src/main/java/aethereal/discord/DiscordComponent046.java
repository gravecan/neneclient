package aethereal.discord;

import aethereal.util.UtilComponent256;
import com.google.gson.JsonObject;

public final class DiscordComponent046 {
   private final String label;
   private final String url;

   public String getLabel_0783() {
      return this.label;
   }

   public String getUrl_0784() {
      return this.url;
   }

   public DiscordComponent046(String string, String string1) {
      if (string == null || string.isEmpty() || string.length() > 32) {
         throw new IllegalArgumentException("Button label must be 1-32 characters, got: " + (string == null ? "null" : string.length()));
      }

      if (string1 != null && string1.length() <= 256) {
         UtilComponent256.performAction_3458(string1, "Button URL", 256);
         this.label = string;
         this.url = string1;
      } else {
         throw new IllegalArgumentException("Button URL must be at most 256 characters");
      }
   }

   public JsonObject computeValue_0782() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("label", this.label);
      jsonObject.addProperty("url", this.url);
      return jsonObject;
   }
}
