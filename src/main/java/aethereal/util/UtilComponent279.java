package aethereal.util;

import aethereal.core.MinecraftAccess;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class UtilComponent279 implements MinecraftAccess {
   private UtilComponent279() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static float computeValue_3583(float f, float g, float h) {
      return f + (g - f) * h;
   }

   public static Vec3d computeValue_3585(Entity entity, float f) {
      return new Vec3d(
         entity.lastRenderX + (entity.getX() - entity.lastRenderX) * f,
         entity.lastRenderY + (entity.getY() - entity.lastRenderY) * f,
         entity.lastRenderZ + (entity.getZ() - entity.lastRenderZ) * f
      );
   }

   public static float computeValue_3577(float f, float g, float h) {
      return Math.min(Math.max(f, g), h);
   }

   public static double computeValue_3580(double d, int i) {
      return d * minecraft.getWindow().getScaleFactor() / minecraft.getWindow().calculateScaleFactor(i, minecraft.forcesUnicodeFont());
   }

   public static float computeValue_3579(float f) {
      float g = computeValue_3577(f, 0.0F, 1.0F);
      return g * g * (3.0F - 2.0F * g);
   }

   public static float computeValue_3582(float f, float g) {
      return (float)(Math.random() * (g - f) + f);
   }

   public static float[] computeValue_3581(float f) {
      float g = -f / 2.0F + f * 2.0F;
      float h = f / 2.0F + f;
      return new float[]{g, h};
   }

   public static String computeValue_3584(int i) {
      String[] strings = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
      return i >= 0 && i < strings.length ? strings[i] : String.valueOf(i + 1);
   }

   public static int computeValue_3578(String string) {
      if (string != null && !string.isBlank()) {
         String string1 = string.replaceAll("§.", "").toLowerCase().replaceAll("\\s+", "").trim();
         String[] strings = new String[]{"i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix", "x"};

         for (int i = strings.length - 1; i >= 0; i--) {
            if (string1.contains(strings[i])) {
               return i + 1;
            }
         }

         String string2 = string1.replaceAll("[^0-9]", "");
         return !string2.isEmpty() && Integer.parseInt(string2) >= 1 && Integer.parseInt(string2) <= 10 ? Integer.parseInt(string2) : 0;
      } else {
         return 0;
      }
   }

   public static boolean checkCondition_3576(double d, double e, float f, float g, float h, float i) {
      return d >= f && d <= f + h && e >= g && e <= g + i;
   }

   public static float computeValue_3586(float f, float g, float h) {
      float i = 0.05F;
      return f + (g - f) * (1.0F - (float)Math.exp(-h * i));
   }
}
