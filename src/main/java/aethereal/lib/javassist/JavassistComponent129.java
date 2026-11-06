package aethereal.lib.javassist;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;

public final class JavassistComponent129 {
   private static final Gson GSON = new Gson();
   private static final int MAX_FRAME_SIZE = 1048576;

   private JavassistComponent129() {
   }

   public static void performAction_1245(int i) throws JavassistComponent129.NestedType328 {
      if (i < 0 || i > 1048576) {
         throw new JavassistComponent129.NestedType328("Invalid frame length: " + i);
      }
   }

   public static JsonObject computeValue_1246(byte[] b) {
      return b.length == 0 ? null : (JsonObject)GSON.fromJson(new String(b, StandardCharsets.UTF_8), JsonObject.class);
   }

   public static class NestedType328 extends Exception {
      public NestedType328(String string) {
         super(string);
      }
   }
}
