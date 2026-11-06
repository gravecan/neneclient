package aethereal.ui.widget;

import aethereal.config.ThemeInfo;
import aethereal.core.ClientTickEvent;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.event.PacketEvent;
import aethereal.notification.NotificationComponent181;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.setting.BooleanSetting;
import aethereal.ui.element.ElementComponent210;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.text.Text;

public class WidgetComponent244 extends WidgetComponent249 implements MinecraftAccess {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Notify about the raising of donation items", true);
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Updates and notifications to friends", true);

   public WidgetComponent244() {
      super(new ElementComponent210("Notifications", 0.0F, 0.0F, 0.0F, 0.0F));
      this.getDragInfo_3360().setWidgetComponent249Field1_3016(this);
      this.getDragInfo_3360().setIntegerField6_3004(1);
      this.performAction_3362(this.booleanSettingField2, this.booleanSettingField1);
   }

   @Override
   public void performAction_3316(ClientTickEvent clientTickEvent) {
      this.getAnimationField3_3363()
         .performAction_2528(
            minecraft.currentScreen instanceof ChatScreen
               || !NeneClient.getInstance().getProcessors().getNotificationComponent182Field5_0676().getListField2_2520().isEmpty()
         );
      super.performAction_3316(clientTickEvent);
   }

   @Override
   public void onDrawEvent_3315(DrawEvent drawEvent) {
      this.getAnimationField3_3363().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      float g = this.getDragInfo_3360().computeValue_3009();

      for (NotificationComponent181 notificationComponent181 : NeneClient.getInstance()
         .getProcessors()
         .getNotificationComponent182Field5_0676()
         .getListField2_2520()) {
         float h = notificationComponent181.getAnimationField1_2515().getFloatValueField4_2530() * this.computeValue_3367();
         if (h > 0.0F) {
            Object object = notificationComponent181.getMessage_2514();
            float f;
            if (object instanceof Text text) {
               f = RenderComponent188.renderComponent187Field5.computeValue_2628(text, this.floatValueField8);
            } else {
               f = RenderComponent188.renderComponent187Field5.computeValue_2626(String.valueOf(object), this.floatValueField8);
            }

            float k = 17.5F + f + 4.0F;
            float i = (minecraft.getWindow().getScaledWidth() - k) / 2.0F;
            int j = notificationComponent181.getColor_2516() == -1
               ? NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547()
               : notificationComponent181.getColor_2516();
            if (notificationComponent181.getSymbol_2510() instanceof ItemStack itemStack) {
               this.performAction_3375(drawEvent, i, g, itemStack, object, k, h, j);
            } else {
               this.performAction_3373(drawEvent, i, g, (String)notificationComponent181.getSymbol_2510(), object, k, h, j);
            }

            this.getDragInfo_3360().performAction_3015(i);
            this.getDragInfo_3360().performAction_3006(k);
            this.getDragInfo_3360().performAction_3018(this.floatValueField7);
            g += (this.floatValueField7 + 3.5F) * h;
         }
      }

      super.onDrawEvent_3315(drawEvent);
   }

   @EventTarget
   public void onPacketEvent_3341(PacketEvent packetEvent) {
      if (this.booleanSettingField1.getCurrentValue() && packetEvent.checkCondition_1013()) {
         ItemPickupAnimationS2CPacket itemPickupAnimationS2CPacket = (ItemPickupAnimationS2CPacket)packetEvent.getPacket_1015();
         if (itemPickupAnimationS2CPacket instanceof ItemPickupAnimationS2CPacket itemPickupAnimationS2CPacket1) {
            ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity)minecraft.world.getEntityById(itemPickupAnimationS2CPacket1.getCollectorEntityId());
            if (clientPlayerEntity instanceof PlayerEntity) {
               ClientPlayerEntity clientPlayerEntity1 = clientPlayerEntity;
               ItemEntity itemEntity = (ItemEntity)minecraft.world.getEntityById(itemPickupAnimationS2CPacket1.getEntityId());
               if (itemEntity instanceof ItemEntity itemEntity1
                  && clientPlayerEntity1 != minecraft.player
                  && !itemEntity1.getStack().getName().getString().contains("Oops.")
                  && (
                     itemEntity1.getStack().contains(DataComponentTypes.CUSTOM_NAME) && itemEntity1.getStack().contains(DataComponentTypes.LORE)
                        || itemEntity1.getStack().isOf(Items.ENCHANTED_GOLDEN_APPLE)
                  )) {
                  NeneClient.getInstance()
                     .getProcessors()
                     .getNotificationComponent182Field5_0676()
                     .performAction_2523(
                        new NotificationComponent181(
                           itemEntity1.getStack().copy(),
                           clientPlayerEntity1.getName()
                              .copy()
                              .append("picked up")
                              .append(itemEntity1.getStack().getName())
                              .append(itemPickupAnimationS2CPacket1.getStackAmount() > 1 ? " x" + itemPickupAnimationS2CPacket1.getStackAmount() : ""),
                           1500
                        )
                     );
               }
            }
         }
      }
   }
}
