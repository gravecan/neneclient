package aethereal.ui.screen;

import aethereal.config.ThemeInfo;
import aethereal.core.NeneClient;
import aethereal.render.RenderComponent183;
import java.util.List;

public final class ScreenComponent226 {
   public static final List<ScreenComponent226.NestedType395> ALL = List.of(
      new ScreenComponent226.NestedType395("CRIMSON", 16722474, 789008, 0.58F),
      new ScreenComponent226.NestedType395("ROSE", 16736162, 1182224, 0.62F),
      new ScreenComponent226.NestedType395("VIOLET", 10316799, 854550, 0.58F),
      new ScreenComponent226.NestedType395("OCEAN", 3057919, 527380, 0.56F),
      new ScreenComponent226.NestedType395("EMERALD", 3073437, 528909, 0.58F),
      new ScreenComponent226.NestedType395("SUNSET", 16747069, 1313800, 0.62F),
      new ScreenComponent226.NestedType395("GLASS", 9425919, 659480, 0.48F),
      new ScreenComponent226.NestedType395("VOID", 11893759, 0, 0.42F)
   );

   private ScreenComponent226() {
   }

   public static void performAction_3259(ScreenComponent226.NestedType395 nestedType395) {
      ScreenComponent228.NestedType396 nestedType396 = ScreenComponent228.nestedType396Field1;
      nestedType396.theme = nestedType395.name();
      nestedType396.accent = nestedType395.accent();
      nestedType396.panel = nestedType395.panel();
      nestedType396.glass = nestedType395.glass();
      nestedType396.hue = -1;
      performAction_3257(false);
      ScreenComponent228.performAction_3282();
   }

   public static void performAction_3262(int i) {
      ScreenComponent228.NestedType396 nestedType396 = ScreenComponent228.nestedType396Field1;
      nestedType396.theme = "CUSTOM";
      nestedType396.hue = i;
      nestedType396.accent = computeValue_3258(i, 0.82F, 1.0F);
      performAction_3257(false);
      ScreenComponent228.performAction_3282();
   }

   public static void performAction_3260(float f) {
      ScreenComponent228.nestedType396Field1.glass = Math.max(0.3F, Math.min(0.9F, f));
      MenuRenderer.glass = ScreenComponent228.nestedType396Field1.glass;
      ScreenComponent228.performAction_3282();
   }

   public static void performAction_3261(boolean bl) {
      ScreenComponent228.nestedType396Field1.particles = bl;
      MenuRenderer.particlesOn = bl;
      ScreenComponent228.performAction_3282();
   }

   static void performAction_3257(boolean bl) {
      ScreenComponent228.NestedType396 nestedType396 = ScreenComponent228.nestedType396Field1;
      MenuRenderer.setPanelColor(nestedType396.panel);
      MenuRenderer.glass = nestedType396.glass;
      MenuRenderer.particlesOn = nestedType396.particles;
      MenuRenderer.setAccent(nestedType396.accent, bl);

      try {
         NeneClient.getInstance()
            .getProcessors()
            .getThemes()
            .computeValue_0557(ThemeInfo.PRIMARY)
            .performAction_0543(
               RenderComponent183.computeValue_2558(nestedType396.accent >> 16 & 0xFF, nestedType396.accent >> 8 & 0xFF, nestedType396.accent & 0xFF, 255)
            );
      } catch (Throwable throwable) {
      }
   }

   public static int computeValue_3258(float f, float g, float h) {
      float i = (f % 360.0F + 360.0F) % 360.0F / 60.0F;
      int j = (int)Math.floor(i);
      float k = i - j;
      float l = h * (1.0F - g);
      float m = h * (1.0F - g * k);
      float n = h * (1.0F - g * (1.0F - k));
      float o;
      float p;
      float q;
      switch (j) {
         case 0:
            o = h;
            p = n;
            q = l;
            break;
         case 1:
            o = m;
            p = h;
            q = l;
            break;
         case 2:
            o = l;
            p = h;
            q = n;
            break;
         case 3:
            o = l;
            p = m;
            q = h;
            break;
         case 4:
            o = n;
            p = l;
            q = h;
            break;
         default:
            o = h;
            p = l;
            q = m;
      }

      return (int)(o * 255.0F) << 16 | (int)(p * 255.0F) << 8 | (int)(q * 255.0F);
   }

   public static float computeValue_3256(int i) {
      float f = (i >> 16 & 0xFF) / 255.0F;
      float g = (i >> 8 & 0xFF) / 255.0F;
      float h = (i & 0xFF) / 255.0F;
      float j = Math.max(f, Math.max(g, h));
      float k = Math.min(f, Math.min(g, h));
      float l = j - k;
      if (l < 1.0E-4F) {
         return 0.0F;
      }

      float m = j == f ? (g - h) / l % 6.0F : (j == g ? (h - f) / l + 2.0F : (f - g) / l + 4.0F);
      m *= 60.0F;
      return m < 0.0F ? m + 360.0F : m;
   }

   public record NestedType395(String name, int accent, int panel, float glass) {
   }
}
