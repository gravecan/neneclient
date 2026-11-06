package aethereal.handler;

import aethereal.api.ApiAnnotation001;
import aethereal.config.PersistentProcessor;
import aethereal.core.ClientTickEvent;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.core.ObfuscatorRuntime;
import aethereal.event.EventComponent091;
import aethereal.module.combat.CombatComponent165;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent277;
import aethereal.util.UtilComponent279;
import aethereal.util.UtilComponent280;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;

public class RotationProcessor extends PersistentProcessor implements MinecraftAccess {
   private final UtilComponent277 utilComponent277Field1 = new UtilComponent277();
   private static RotationProcessor.State321 state321Field2 = RotationProcessor.State321.IDLE;
   private static float floatValueField3;
   private static int integerField4;
   private static int integerField5;
   private static int integerField6;
   private static int integerField7;
   private static int integerField8;

   @ApiAnnotation001
   @Override
   public void load() {
   }

   public UtilComponent277 getUtilComponent277Field1_1136() {
      return this.utilComponent277Field1;
   }

   public static RotationProcessor.State321 getState321Field2_1134() {
      return state321Field2;
   }

   public static int getIntegerField8_1143() {
      return integerField8;
   }

   @Override
   public void save() {
   }

   @EventTarget
   private void onEventComponent091_1132(EventComponent091 eventComponent091) {
      if (this.checkCondition_1128()) {
         UtilComponent280.performAction_3592(eventComponent091, UtilComponent277.getFloatValueField2_3570(), 10);
      }
   }

   @EventTarget
   private void performAction_1131(ClientTickEvent clientTickEvent) {
      integerField7++;
      if (this.checkCondition_1128()) {
         if (checkCondition_1133()) {
            this.checkCondition_1142(computeValue_1135(), floatValueField3, false);
         } else {
            this.checkCondition_1142(computeValue_1138(integerField5, integerField7), floatValueField3, false);
         }
      }

      if (state321Field2 == RotationProcessor.State321.AIM && integerField7 > integerField6) {
         state321Field2 = RotationProcessor.State321.RESET;
      }

      if (state321Field2 == RotationProcessor.State321.RESET && this.checkCondition_1142(UtilComponent250.computeValue_3383(), floatValueField3, true)) {
         this.utilComponent277Field1.performAction_3569(false);
         state321Field2 = RotationProcessor.State321.IDLE;
         integerField4 = 0;
      }
   }

   private boolean checkCondition_1128() {
      return integerField7 >= 2 && integerField7 <= integerField6 && state321Field2 != RotationProcessor.State321.IDLE;
   }

   public void performAction_1129(int i) {
      integerField8 = Math.max(i, 0);
   }

   public void performAction_1141(UtilComponent250 utilComponent250, float f, int i, int j) {
      this.performAction_1140(utilComponent250, f, f, i, j);
   }

   public void performAction_1140(UtilComponent250 utilComponent250, float f, float g, int i, int j) {
      if (integerField4 <= j) {
         if (state321Field2 != RotationProcessor.State321.IDLE && checkCondition_1133()) {
            utilComponent250 = computeValue_1135();
         }

         if (state321Field2 == RotationProcessor.State321.IDLE) {
            this.utilComponent277Field1.performAction_3569(true);
         }

         floatValueField3 = g;
         integerField5 = i;
         integerField6 = computeValue_1130(i);
         integerField4 = j;
         state321Field2 = RotationProcessor.State321.AIM;
         integerField7 = 0;
         this.checkCondition_1142(utilComponent250, f, true);
      }
   }

   private static boolean checkCondition_1133() {
      List list = HandlerComponent108.getUSEABLE_1160().getListField1_1222();
      if (!list.isEmpty() && ((HandlerComponent117.NestedType327)list.getFirst()).getIntegerField3_1232() < 1) {
         Item item = ((HandlerComponent117.NestedType327)list.getFirst()).getItemStack_1229().getItem();
         return item == Items.WIND_CHARGE || item == Items.ENDER_PEARL || item == Items.SNOWBALL || item == Items.SPLASH_POTION || item == Items.DRIED_KELP;
      } else {
         return false;
      }
   }

   private static UtilComponent250 computeValue_1135() {
      return computeValue_1139(new UtilComponent250(UtilComponent277.getFloatValueField2_3570(), UtilComponent277.getFloatValueField3_3571()));
   }

   public static UtilComponent250 computeValue_1139(UtilComponent250 utilComponent250) {
      float f = minecraft.player.age + minecraft.getRenderTickCounter().getTickProgress(false);
      float g = (float)(
            Math.sin(f * 0.8F) * 11.0
               + Math.sin(f * 0.04000001502137623 + 17.200010267039897) * 1.5
               + Math.sin(f * 0.10999997113093289 + 5.8000000238651515) * 3.0
               + Math.sin(f * 0.07000004685868849 + 12.300000009313816) * 1.0
         )
         / 6.0F;
      float h = (float)(Math.sin(f * 0.09999998815548458) + Math.sin(f * 0.029999993539464892 + 54.10000012300467) * 0.5) / 4.0F;
      return new UtilComponent250(
         utilComponent250.getYaw_3386() + MathHelper.clamp(g, -0.15F, 0.15F), utilComponent250.getPitch_3387() + MathHelper.clamp(h, -0.15F, 0.15F)
      );
   }

   private static int computeValue_1130(int i) {
      switch (i) {
         case 0:
            return 1;
         case 1:
            return 9;
         case 7:
            return 30;
         default:
            return 10;
      }
   }

   private static UtilComponent250 computeValue_1138(int i, int j) {
      float f = UtilComponent277.getFloatValueField2_3570();
      float g = UtilComponent277.getFloatValueField3_3571();
      float h = minecraft.player.age + minecraft.getRenderTickCounter().getTickProgress(false);
      float k = (float)((Math.sin(h * 0.31F) * 0.5 + Math.sin(h * 0.73F + 1.1F) * 0.3000000002422922 + Math.sin(h * 1.7F + 2.6F) * 0.19999998556632664) * 12.0)
         / 4.0F;
      switch (i) {
         case 1:
            float l = CombatComponent165.computeValue_1552(
               minecraft.player.getYaw(), UtilComponent277.getFloatValueField2_3570(), UtilComponent279.computeValue_3582(0.1F, 0.45F)
            );
            float m = CombatComponent165.computeValue_1552(
               minecraft.player.getPitch(), UtilComponent277.getFloatValueField3_3571(), UtilComponent279.computeValue_3582(0.1F, 0.45F)
            );
            return new UtilComponent250(l + k, MathHelper.clamp(m + k, -90.0F, 90.0F));
         case 7:
            if (!NeneClient.getInstance().getProcessors().getModules().getScaffoldField26_0452().isEnabled()) {
               j = 25;
            }

            if (j <= 20) {
               return new UtilComponent250(minecraft.player.getYaw() + k, MathHelper.clamp(minecraft.player.getPitch() + k, -90.0F, 90.0F));
            }

            float n = CombatComponent165.computeValue_1552(
               minecraft.player.getYaw(), UtilComponent277.getFloatValueField2_3570(), UtilComponent279.computeValue_3582(0.2F, 0.35F)
            );
            float o = CombatComponent165.computeValue_1552(
               minecraft.player.getPitch(), UtilComponent277.getFloatValueField3_3571(), UtilComponent279.computeValue_3582(0.2F, 0.35F)
            );
            return new UtilComponent250(n + k, MathHelper.clamp(o + k, -90.0F, 90.0F));
         default:
            return new UtilComponent250(f + k, g + k);
      }
   }

   private boolean checkCondition_1142(UtilComponent250 utilComponent250, float f, boolean bl) {
      UtilComponent250 utilComponent2501 = new UtilComponent250(minecraft.player);
      float g = MathHelper.wrapDegrees(utilComponent250.getYaw_3386() - utilComponent2501.getYaw_3386());
      float h = utilComponent250.getPitch_3387() - utilComponent2501.getPitch_3387();
      float i = Math.abs(g) + Math.abs(h);
      float j = i == 0.0F ? 0.0F : Math.abs(g / i) * f;
      float k = i == 0.0F ? 0.0F : Math.abs(h / i) * f;
      float l = minecraft.player.getYaw() + MathHelper.clamp(g, -j, j);
      float m = minecraft.player.getPitch() + MathHelper.clamp(h, -k, k);
      float n = computeValue_1137(minecraft.player.getYaw(), l);
      float o = MathHelper.clamp(computeValue_1137(minecraft.player.getPitch(), m), -90.0F, 90.0F);
      if (integerField8 > 0) {
         o = minecraft.player.getPitch();
         n = minecraft.player.getYaw();
         integerField8--;
      }

      minecraft.player.setYaw(n);
      minecraft.player.setPitch(o);
      UtilComponent250 utilComponent2502 = new UtilComponent250(minecraft.player);
      if (bl) {
         integerField7 = 0;
      }

      return utilComponent2502.computeValue_3389(utilComponent250) < f;
   }

   public static float computeValue_1137(float f, float g) {
      double d = (Double)minecraft.options.getMouseSensitivity().getValue() * 0.6000000498956214 + 0.19999998556632664;
      double e = d * d * d * 8.0;
      return (float)(f + Math.ceil((g - f) / e / 0.15000007F) * e * 0.15000007F);
   }

   static {
      ObfuscatorRuntime.registerClass(RotationProcessor.class, 36);
   }

   public enum State321 {
      AIM,
      RESET,
      IDLE;
   }
}
