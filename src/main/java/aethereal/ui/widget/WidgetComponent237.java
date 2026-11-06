package aethereal.ui.widget;

import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent192;
import aethereal.ui.element.ElementComponent210;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

public class WidgetComponent237 extends WidgetComponent249 implements MinecraftAccess {
   public WidgetComponent237() {
      super(new ElementComponent210("Armor", 0.0F, 0.0F, 0.0F, 0.0F));
      this.getDragInfo_3360().setWidgetComponent249Field1_3016(this);
   }

   @Override
   public void onDrawEvent_3315(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001() && !minecraft.options.hudHidden && !minecraft.player.isSpectator()) {
         EquipmentSlot[] equipmentSlots = new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD};
         int i = 0;

         for (EquipmentSlot equipmentSlot : equipmentSlots) {
            if (!minecraft.player.getEquippedStack(equipmentSlot).isEmpty()) {
               i++;
            }
         }

         if (i > 0) {
            RenderComponent192.performAction_2673(drawEvent.getContext_1000());
            drawEvent.getContext_1000().getMatrices().pushMatrix();
            drawEvent.getContext_1000()
               .getMatrices()
               .translate(
                  0.0F,
                  -16.0F
                     * NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502().getAnimationField5_2546().getFloatValueField4_2530()
               );
            int l = minecraft.getWindow().getScaledWidth() / 2 - 91 + 182 + 4;
            int m = minecraft.getWindow().getScaledHeight() - 22;
            int n = l + (minecraft.player.getMainArm() == Arm.LEFT && !minecraft.player.getOffHandStack().isEmpty() ? 30 : 0);
            drawEvent.getContext_1000().drawGuiTexture(RenderPipelines.GUI_TEXTURED, Identifier.ofVanilla("hud/hotbar"), 182, 22, 0, 0, n, m, i * 20 + 1, 22);
            int o = 0;

            for (EquipmentSlot equipmentSlot1 : equipmentSlots) {
               ItemStack itemStack = minecraft.player.getEquippedStack(equipmentSlot1);
               if (!itemStack.isEmpty()) {
                  int j = n + 3 + o * 20;
                  int k = m + 3;
                  drawEvent.getContext_1000().drawItem(itemStack, j, k);
                  drawEvent.getContext_1000().drawStackOverlay(minecraft.textRenderer, itemStack, j, k);
                  o++;
               }
            }

            drawEvent.getContext_1000().getMatrices().popMatrix();
            RenderComponent192.performAction_2674(drawEvent.getContext_1000());
         }
      }

      super.onDrawEvent_3315(drawEvent);
   }
}
