package aethereal.discord;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.Optional;

public final class DiscordComponent047 {
   private final String stringField1;
   private final int currentSize;
   private final int maxSize;
   private final Integer privacy;
   public static final int integerField2 = 0;
   public static final int integerField3 = 1;

   public DiscordComponent047(String string, int i, int j, Integer integer) {
      this.stringField1 = string;
      this.currentSize = i;
      this.maxSize = j;
      this.privacy = integer;
   }

   public String getStringField1_0787() {
      return this.stringField1;
   }

   public int getCurrentSize_0792() {
      return this.currentSize;
   }

   public int getMaxSize_0793() {
      return this.maxSize;
   }

   public Integer getPrivacy_0788() {
      return this.privacy;
   }

   public static DiscordComponent047 computeValue_0790(String string, int i, int j) {
      return computeValue_0791(string, i, j, null);
   }

   public static DiscordComponent047 computeValue_0791(String string, int i, int j, Integer integer) {
      if (i < 0) {
         throw new IllegalArgumentException("currentSize must be non-negative, got " + i);
      } else if (j < 0) {
         throw new IllegalArgumentException("maxSize must be non-negative, got " + j);
      } else if (i > j) {
         throw new IllegalArgumentException("currentSize (" + i + ") must be <= maxSize (" + j + ")");
      } else {
         return new DiscordComponent047(string, i, j, integer);
      }
   }

   public JsonObject computeValue_0785() {
      JsonObject jsonObject = new JsonObject();
      Optional.ofNullable(this.stringField1).ifPresent(string -> jsonObject.addProperty("id", string));
      if (this.maxSize > 0) {
         JsonArray jsonArray = new JsonArray();
         jsonArray.add(new JsonPrimitive(this.currentSize));
         jsonArray.add(new JsonPrimitive(this.maxSize));
         jsonObject.add("size", jsonArray);
      }

      Optional.ofNullable(this.privacy).ifPresent(integer -> jsonObject.addProperty("privacy", integer));
      return jsonObject;
   }
}
