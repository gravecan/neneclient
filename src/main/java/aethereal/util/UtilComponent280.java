package aethereal.util;

import aethereal.core.MinecraftAccess;
import aethereal.event.EventComponent091;
import net.minecraft.util.math.MathHelper;

public class UtilComponent280 implements MinecraftAccess {
   private static int integerField1 = Integer.MAX_VALUE;
   private static float floatValueField2;

   private UtilComponent280() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static boolean checkCondition_3587() {
      return minecraft.player.input.hasForwardMovement();
   }

   public static void performAction_3592(EventComponent091 eventComponent091, float f, int i) {
      if (i < integerField1) {
         integerField1 = i;
         floatValueField2 = f;
      }
   }

   public static void onEventComponent091_3588(EventComponent091 eventComponent091) {
      if (integerField1 != Integer.MAX_VALUE) {
         float f = eventComponent091.getForward_1062();
         float g = eventComponent091.getStrafe_1063();
         if (f != 0.0F || g != 0.0F) {
            float h = floatValueField2;
            double d = MathHelper.wrapDegrees(Math.toDegrees(computeValue_3590(h, f, g)));
            float i = 0.0F;
            float j = 0.0F;
            float k = Float.MAX_VALUE;

            for (float l = -1.0F; l <= 1.0F; l++) {
               for (float m = -1.0F; m <= 1.0F; m++) {
                  if (l != 0.0F || m != 0.0F) {
                     double e = MathHelper.wrapDegrees(Math.toDegrees(computeValue_3590(minecraft.player.getYaw(), l, m)));
                     float n = Math.abs(MathHelper.wrapDegrees((float)(d - e)));
                     if (n < k) {
                        k = n;
                        i = l;
                        j = m;
                     }
                  }
               }
            }

            integerField1 = Integer.MAX_VALUE;
            eventComponent091.setForward_1065(i);
            eventComponent091.setStrafe_1060(j);
         }
      }
   }

   private static double computeValue_3590(float f, double d, double e) {
      float j = d < 0.0 ? f + 180.0F : f;
      float g;
      if (e > 0.0) {
         float i;
         if (d < 0.0) {
            i = -0.5F;
         } else {
            i = d > 0.0 ? 0.5F : 1.0F;
         }

         g = -90.0F * i;
      } else if (e < 0.0) {
         float h;
         if (d < 0.0) {
            h = -0.5F;
         } else {
            h = d > 0.0 ? 0.5F : 1.0F;
         }

         g = 90.0F * h;
      } else {
         g = 0.0F;
      }

      return Math.toRadians(j + g);
   }

   public static void onEventComponent091_3591(EventComponent091 eventComponent091) {
      eventComponent091.setForward_1065(0.0F);
      eventComponent091.setStrafe_1060(0.0F);
   }

   public static boolean checkCondition_3589(float f) {
      return minecraft.player.getY() < 0.0
         ? false
         : minecraft.world.getCollisions(minecraft.player, minecraft.player.getBoundingBox().offset(0.0, -f, 0.0)).iterator().hasNext();
   }
}
