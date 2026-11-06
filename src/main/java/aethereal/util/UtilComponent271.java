package aethereal.util;

import com.google.gson.JsonObject;
import java.util.Optional;

public class UtilComponent271 {
   private UtilComponent271() {
   }

   public static Optional<String> computeValue_3548(JsonObject jsonObject, String string) {
      return jsonObject != null && jsonObject.has(string) && !jsonObject.get(string).isJsonNull()
         ? Optional.of(jsonObject.get(string).getAsString())
         : Optional.empty();
   }

   public static String computeValue_3550(JsonObject jsonObject, String string, String string1) {
      return computeValue_3548(jsonObject, string).orElse(string1);
   }

   public static Optional<JsonObject> computeValue_3547(JsonObject jsonObject, String string) {
      return jsonObject != null && jsonObject.has(string) && jsonObject.get(string).isJsonObject()
         ? Optional.of(jsonObject.getAsJsonObject(string))
         : Optional.empty();
   }

   public static boolean checkCondition_3551(JsonObject jsonObject, String string, boolean bl) {
      return jsonObject != null && jsonObject.has(string) && !jsonObject.get(string).isJsonNull() ? jsonObject.get(string).getAsBoolean() : bl;
   }

   public static int computeValue_3549(JsonObject jsonObject, String string, int i) {
      return jsonObject != null && jsonObject.has(string) && !jsonObject.get(string).isJsonNull() ? jsonObject.get(string).getAsInt() : i;
   }
}
