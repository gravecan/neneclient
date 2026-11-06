package aethereal.ui.widget;

import aethereal.config.ThemeInfo;
import aethereal.core.ClientTickEvent;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.module.misc.StreamerMode;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.render.RenderComponent193;
import aethereal.setting.BooleanSetting;
import aethereal.ui.element.ElementComponent210;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent279;
import aethereal.util.UtilComponent284;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;

public class WidgetComponent247 extends WidgetComponent249 {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Item Visualization", true);
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Show on hover", true);
   private final Animation animationField3 = new Animation();
   private final Animation animationField4 = new Animation();
   private LivingEntity livingEntityField5;
   private String stringField6 = "";

   public WidgetComponent247() {
      super(new ElementComponent210("Target HUD", 0.0F, 0.0F, 100.0F, 24.0F));
      this.getDragInfo_3360().setWidgetComponent249Field1_3016(this);
      this.performAction_3362(this.booleanSettingField2, this.booleanSettingField1);
   }

   @EventTarget
   @Override
   public void onDrawEvent_3315(DrawEvent drawEvent) {
      this.getDragInfo_3360().performAction_3006(116.0F);
      this.getDragInfo_3360().performAction_3018(this.booleanSettingField1.getCurrentValue() ? 44.0F : 32.0F);
      if (MinecraftAccess.minecraft.currentScreen instanceof ChatScreen && this.livingEntityField5 == null) {
         this.livingEntityField5 = MinecraftAccess.minecraft.player;
         this.getAnimationField3_3363().performAction_2528(true);
      }

      this.getAnimationField3_3363().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      if (this.computeValue_3367() > 0.0F && this.livingEntityField5 != null) {
         float f = this.getDragInfo_3360().computeValue_3007();
         float g = this.getDragInfo_3360().computeValue_3009();
         this.performAction_3372(
            drawEvent, f, g, this.getDragInfo_3360().getWidth_3005(), this.getDragInfo_3360().getHeight_3002(), true, this.computeValue_3367()
         );
         float h = Math.min(24.0F, this.getDragInfo_3360().getHeight_3002() / 1.35F);
         float i = g + (this.getDragInfo_3360().getHeight_3002() - h) / 2.0F;
         if (this.livingEntityField5 instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
            drawEvent.getRenderComponent184Field1_0998()
               .performAction_2579(
                  drawEvent.getStack_1005(),
                  f + 5.0F,
                  i,
                  h,
                  h,
                  2.0F,
                  RenderComponent183.computeValue_2549(-1, this.computeValue_3367()),
                  0.125F,
                  0.125F,
                  0.125F,
                  0.125F,
                  UtilComponent284.computeValue_3617(abstractClientPlayerEntity.getSkin().body().comp_3626())
               );
         } else if (this.livingEntityField5 != null) {
            RenderComponent188.renderComponent187Field1
               .performAction_2630(
                  drawEvent.getStack_1005(),
                  "B",
                  f + 6.5F + (h - 24.0F) / 2.0F,
                  i + (h - 24.0F) / 2.0F,
                  24.0F,
                  RenderComponent183.computeValue_2549(-1, this.computeValue_3367())
               );
         }

         float n = f + 5.0F + h + 5.0F;
         StreamerMode streamerMode = NeneClient.getInstance().getProcessors().getModules().getStreamerModeField7_0439();
         String string;
         if (streamerMode.isEnabled() && streamerMode.getBooleanSettingField2_2001().getCurrentValue()) {
            string = streamerMode.computeValue_2004(this.livingEntityField5.getName().getString());
         } else {
            string = this.livingEntityField5.getName().getString();
         }

         String string1 = string;
         if (string1.length() > 12) {
            RenderComponent188.renderComponent187Field5
               .performAction_2631(
                  drawEvent.getStack_1005(),
                  string1,
                  n,
                  i,
                  7.5F,
                  RenderComponent183.computeValue_2549(-1, this.computeValue_3367()),
                  RenderComponent188.renderComponent187Field5.computeValue_2626(string1.substring(0, 12), 7.5F)
               );
         } else {
            RenderComponent188.renderComponent187Field5
               .performAction_2630(drawEvent.getStack_1005(), string1, n, i, 7.5F, RenderComponent183.computeValue_2549(-1, this.computeValue_3367()));
         }

         if (this.booleanSettingField1.getCurrentValue()) {
            int j = 0;

            for (ItemStack itemStack : new ItemStack[]{
               this.livingEntityField5.getEquippedStack(EquipmentSlot.FEET),
               this.livingEntityField5.getEquippedStack(EquipmentSlot.LEGS),
               this.livingEntityField5.getEquippedStack(EquipmentSlot.CHEST),
               this.livingEntityField5.getEquippedStack(EquipmentSlot.HEAD),
               this.livingEntityField5.getOffHandStack(),
               this.livingEntityField5.getMainHandStack()
            }) {
               if (!itemStack.isEmpty()) {
                  drawEvent.getRenderComponent185Field2_0999()
                     .performAction_2592(
                        drawEvent.getContext_1000(),
                        UtilComponent270.computeValue_3541(itemStack),
                        f + this.getDragInfo_3360().getWidth_3005() - 10.0F - j * 9,
                        g + this.getDragInfo_3360().getHeight_3002() - 13.0F,
                        0,
                        this.computeValue_3367(),
                        0.55F,
                        true
                     );
                  j++;
               }
            }
         }

         int o = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
         float p = Math.max(1.0F, this.livingEntityField5.getMaxHealth());
         float q = UtilComponent279.computeValue_3577(UtilComponent252.NestedType400.computeValue_3409(this.livingEntityField5), 0.0F, p);
         String string2 = (int)q + "/" + (int)p;
         if (this.stringField6.isEmpty()) {
            this.stringField6 = string2;
         }

         float r = string2.equals(this.stringField6) ? 1.0F : this.animationField4.computeValue_2534(0.0F, 1.0F, 0.75F);
         this.performAction_3353(
            drawEvent,
            string2,
            this.stringField6,
            f + this.getDragInfo_3360().getWidth_3005() - 5.0F - RenderComponent188.renderComponent187Field5.computeValue_2626(string2, 7.0F),
            i + 0.5F,
            7.0F,
            o,
            r
         );
         if (r >= 0.99F) {
            this.stringField6 = string2;
            this.animationField4.setFloatValueField1_2526(0.0F);
         }

         float k = q / p;
         float l = this.animationField3.computeValue_2534(k, k, 0.5F);
         float m = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.BACKGROUND_HUD).computeValue_0548()
            * this.computeValue_3367();
         drawEvent.getRenderComponent184Field1_0998()
            .performAction_2577(
               drawEvent.getStack_1005(),
               n,
               i + 12.5F,
               54.0F,
               3.0F,
               1.5F,
               RenderComponent183.computeValue_2549(RenderComponent183.computeValue_2552(o, 0.3F), this.computeValue_3367())
            );
         drawEvent.getRenderComponent184Field1_0998()
            .performAction_2577(drawEvent.getStack_1005(), n, i + 12.5F, 54.0F * l, 3.0F, 1.5F, RenderComponent183.computeValue_2549(o, m));
      }

      super.onDrawEvent_3315(drawEvent);
   }

   private void performAction_3353(DrawEvent drawEvent, String string, String string1, float f, float g, float h, int i, float j) {
      float k = RenderComponent188.renderComponent187Field5.computeValue_2622(h);
      float l = f + RenderComponent188.renderComponent187Field5.computeValue_2626(string, h);

      for (int m = 0; m < string.length(); m++) {
         char c = string.charAt(string.length() - 1 - m);
         char d = m < string1.length() ? string1.charAt(string1.length() - 1 - m) : ' ';
         String string2 = String.valueOf(c);
         l -= RenderComponent188.renderComponent187Field5.computeValue_2626(string2, h);
         if (c != d && !(j >= 1.0F)) {
            RenderComponent193.performAction_2681(
               drawEvent.getStack_1005(), l - 0.5F, g, RenderComponent188.renderComponent187Field5.computeValue_2626(string2, h) + 1.0F, k + 1.0F
            );
            RenderComponent188.renderComponent187Field5
               .performAction_2630(
                  drawEvent.getStack_1005(), String.valueOf(d), l, g - k * j, h, RenderComponent183.computeValue_2549(i, (1.0F - j) * this.computeValue_3367())
               );
            RenderComponent188.renderComponent187Field5
               .performAction_2630(
                  drawEvent.getStack_1005(), string2, l, g + k * (1.0F - j), h, RenderComponent183.computeValue_2549(i, j * this.computeValue_3367())
               );
            RenderComponent193.performAction_2680(drawEvent.getStack_1005());
         } else {
            RenderComponent188.renderComponent187Field5
               .performAction_2630(drawEvent.getStack_1005(), string2, l, g, h, RenderComponent183.computeValue_2549(i, this.computeValue_3367()));
         }
      }
   }

   @EventTarget
   @Override
   public void performAction_3316(ClientTickEvent clientTickEvent) {
      LivingEntity livingEntity = NeneClient.getInstance().getProcessors().getModules().getAuraField3_0402().getLivingEntityField2_1528() != null
         ? NeneClient.getInstance().getProcessors().getModules().getAuraField3_0402().getLivingEntityField2_1528()
         : NeneClient.getInstance().getProcessors().getModules().getTriggerBotField4_0418().getLivingEntityField6_1669();
      PlayerEntity playerEntity;
      if (this.booleanSettingField2.getCurrentValue()) {
         if ((MinecraftAccess.minecraft.crosshairTarget instanceof EntityHitResult ? (EntityHitResult)MinecraftAccess.minecraft.crosshairTarget : null) instanceof EntityHitResult entityHitResult
            )
          {
            if ((entityHitResult.getEntity() instanceof LivingEntity ? (LivingEntity)entityHitResult.getEntity() : null) instanceof PlayerEntity playerEntity1) {
               if (playerEntity1 != MinecraftAccess.minecraft.player) {
                  playerEntity = playerEntity1;
               } else {
                  playerEntity = null;
               }
            } else {
               playerEntity = null;
            }
         } else {
            playerEntity = null;
         }
      } else {
         playerEntity = null;
      }

      PlayerEntity playerEntity2 = playerEntity;
      Object object;
      if (livingEntity != null) {
         object = livingEntity;
      } else if (playerEntity2 != null) {
         object = playerEntity2;
      } else {
         object = MinecraftAccess.minecraft.currentScreen instanceof ChatScreen ? MinecraftAccess.minecraft.player : null;
      }

      Object object1 = object;
      boolean bl = object1 != null;
      if (object1 != null) {
         this.livingEntityField5 = (LivingEntity)object1;
      }

      this.getAnimationField3_3363().performAction_2528(bl);
      if (!bl && this.getAnimationField3_3363().getFloatValueField1_2527() <= 0.0F) {
         this.livingEntityField5 = null;
      }

      super.performAction_3316(clientTickEvent);
   }
}
