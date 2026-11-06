package aethereal.ui.screen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.client.MinecraftClient;

public final class ScreenComponent228 {
   public static ScreenComponent228.NestedType396 nestedType396Field1 = new ScreenComponent228.NestedType396();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static boolean dirty;
   private static float dirtyAt;

   private ScreenComponent228() {
   }

   private static Path computeValue_3281() {
      return MinecraftClient.getInstance().runDirectory.toPath().resolve("config").resolve("client-ui").resolve("ui_settings.json");
   }

   public static void performAction_3280() {
      try {
         Path path = computeValue_3281();
         if (Files.exists(path)) {
            ScreenComponent228.NestedType396 nestedType396 = (ScreenComponent228.NestedType396)GSON.fromJson(
               Files.readString(path, StandardCharsets.UTF_8), ScreenComponent228.NestedType396.class
            );
            if (nestedType396 != null) {
               nestedType396Field1 = nestedType396;
            }
         }
      } catch (Exception exception) {
      }
      float f = switch (nestedType396Field1.theme == null ? "" : nestedType396Field1.theme) {
         case "CRIMSON" -> 0.12F;
         case "ROSE", "SUNSET" -> 0.22F;
         case "VIOLET", "EMERALD" -> 0.18F;
         case "OCEAN" -> 0.16F;
         case "GLASS" -> 0.08F;
         case "VOID" -> 0.04F;
         default -> -1.0F;
      };
      if (f >= 0.0F && Math.abs(nestedType396Field1.glass - f) < 0.001F
         || "CRIMSON".equals(nestedType396Field1.theme) && Math.abs(nestedType396Field1.glass - 0.84F) < 0.001F) {
         ScreenComponent228.NestedType396 nestedType3961 = nestedType396Field1;

         nestedType3961.glass = switch (nestedType396Field1.theme) {
            case "CRIMSON" -> 0.58F;
            case "ROSE", "SUNSET" -> 0.62F;
            case "VIOLET", "EMERALD" -> 0.58F;
            case "OCEAN" -> 0.56F;
            case "GLASS" -> 0.48F;
            case "VOID" -> 0.42F;
            default -> 0.58F;
         };
      }

      nestedType396Field1.glass = Math.max(0.3F, Math.min(0.9F, nestedType396Field1.glass));
      ScreenComponent226.performAction_3257(true);
   }

   public static void performAction_3282() {
      dirty = true;
      dirtyAt = MenuRenderer.time;
   }

   public static void performAction_3279(float f) {
      if (dirty && f - dirtyAt > 0.6F) {
         performAction_3278();
      }
   }

   public static void performAction_3278() {
      dirty = false;

      try {
         Path path = computeValue_3281();
         Files.createDirectories(path.getParent());
         Files.writeString(path, GSON.toJson(nestedType396Field1), StandardCharsets.UTF_8);
      } catch (IOException iOException) {
      }
   }

   public static final class NestedType396 {
      public String theme = "CRIMSON";
      public int accent = 16722474;
      public int panel = 789008;
      public float glass = 0.58F;
      public boolean particles = true;
      public int hue = -1;
   }
}
