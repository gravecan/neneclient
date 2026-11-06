package aethereal.discord;

import com.google.gson.JsonObject;
import java.util.Optional;

public final class DiscordComponent045 {
   private final String largeImage;
   private final String largeText;
   private final String smallImage;
   private final String smallText;

   public DiscordComponent045(String string, String string1, String string2, String string3) {
      this.largeImage = string;
      this.largeText = string1;
      this.smallImage = string2;
      this.smallText = string3;
   }

   public String getLargeImage_0777() {
      return this.largeImage;
   }

   public String getLargeText_0778() {
      return this.largeText;
   }

   public String getSmallImage_0780() {
      return this.smallImage;
   }

   public String getSmallText_0774() {
      return this.smallText;
   }

   public JsonObject computeValue_0773() {
      JsonObject jsonObject = new JsonObject();
      Optional.ofNullable(this.largeImage).ifPresent(string -> jsonObject.addProperty("large_image", string));
      Optional.ofNullable(this.largeText).ifPresent(string -> jsonObject.addProperty("large_text", string));
      Optional.ofNullable(this.smallImage).ifPresent(string -> jsonObject.addProperty("small_image", string));
      Optional.ofNullable(this.smallText).ifPresent(string -> jsonObject.addProperty("small_text", string));
      return jsonObject;
   }
}
