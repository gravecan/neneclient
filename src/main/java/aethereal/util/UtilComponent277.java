package aethereal.util;

import aethereal.core.EventBus;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.event.EventComponent094;
import aethereal.event.EventComponent100;
import net.minecraft.util.math.MathHelper;

public class UtilComponent277 implements MinecraftAccess {
   private boolean flagField1;
   private static float floatValueField2;
   private static float floatValueField3;
   private static UtilComponent277 utilComponent277Field4;

   public boolean isFlagField1_3566() {
      return this.flagField1;
   }

   public static boolean checkCondition_3572() {
      return utilComponent277Field4 != null && utilComponent277Field4.flagField1;
   }

   public static float getFloatValueField2_3570() {
      return floatValueField2;
   }

   public static float getFloatValueField3_3571() {
      return floatValueField3;
   }

   public static void setFloatValueField2_3574(float f) {
      floatValueField2 = f;
   }

   public static void setFloatValueField3_3565(float f) {
      floatValueField3 = f;
   }

   public UtilComponent277() {
      utilComponent277Field4 = this;
      EventBus.register(this);
   }

   @EventTarget
   private void onEventComponent094_3567(EventComponent094 eventComponent094) {
      if (this.flagField1) {
         this.performAction_3573(eventComponent094.yaw, eventComponent094.pitch);
         eventComponent094.performAction_0597(true);
      }
   }

   @EventTarget
   private void onEventComponent100_3568(EventComponent100 eventComponent100) {
      if (this.flagField1) {
         eventComponent100.setYaw_1098(floatValueField2);
         eventComponent100.setPitch_1095(floatValueField3);
      } else {
         floatValueField2 = eventComponent100.getYaw_1096();
         floatValueField3 = eventComponent100.getPitch_1097();
      }
   }

   public void performAction_3569(boolean bl) {
      if (this.flagField1 != bl) {
         this.flagField1 = bl;
         performAction_3575();
      }
   }

   private void performAction_3573(double d, double e) {
      double f = e * 0.15000001238751678;
      double g = d * 0.15000001238751678;
      floatValueField3 = (float)(floatValueField3 + f);
      floatValueField2 = (float)(floatValueField2 + g);
      floatValueField3 = MathHelper.clamp(floatValueField3, -90.0F, 90.0F);
   }

   private static void performAction_3575() {
      if (minecraft.player != null) {
         float f = minecraft.player.getYaw();
         float g = floatValueField2;
         minecraft.player.setYaw(f + MathHelper.wrapDegrees(g - f));
         minecraft.player.setPitch(floatValueField3);
      }
   }
}
