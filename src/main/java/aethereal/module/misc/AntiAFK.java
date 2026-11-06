package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent091;
import aethereal.event.PacketEvent;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.MultiSelectSetting;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.util.Hand;

@ModuleRegister(name = "Anti AFK", description = "Prevents the server from kicking you for inaction", category = Category.Player)
public class AntiAFK extends Module {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Use mode", "Normal", "Normal", "FunTime");
   private final MultiSelectSetting multiSelectSettingField2 = new MultiSelectSetting(
         "Perform actions", new BooleanSetting("Bounce", true), new BooleanSetting("Swing", true), new BooleanSetting("Move", true)
      )
      .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("Normal"));
   private final BooleanSetting booleanSettingField3 = new BooleanSetting("React to unavailability", false)
      .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("FunTime"));

   public AntiAFK() {
      this.addSettings(this.choiceSettingField1, this.multiSelectSettingField2, this.booleanSettingField3);
   }

   @EventTarget
   public void onEventComponent091_1814(EventComponent091 eventComponent091) {
      if (minecraft.player.age % 600 == 0) {
         if (this.choiceSettingField1.checkCondition_2850("Normal")) {
            if (this.multiSelectSettingField2.computeValue_2852("Bounce").getCurrentValue() && minecraft.player.isOnGround()) {
               eventComponent091.setJump_1066(true);
            }

            if (this.multiSelectSettingField2.computeValue_2852("Swing").getCurrentValue()) {
               minecraft.player.swingHand(Hand.MAIN_HAND);
            }

            if (this.multiSelectSettingField2.computeValue_2852("Move").getCurrentValue()) {
               NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getMiscComponent167Field4_1155().setIntegerField1_1806(7);
               return;
            }

            return;
         }

         if (this.choiceSettingField1.checkCondition_2850("FunTime") && !this.booleanSettingField3.getCurrentValue()) {
            NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getMiscComponent167Field4_1155().setIntegerField1_1806(7);
         }
      }
   }

   @EventTarget
   public void onPacketEvent_1813(PacketEvent packetEvent) {
      if (this.choiceSettingField1.checkCondition_2850("FunTime") && this.booleanSettingField3.getCurrentValue() && packetEvent.checkCondition_1013()) {
         GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packetEvent.getPacket_1015();
         if (gameMessageS2CPacket instanceof GameMessageS2CPacket gameMessageS2CPacket1
            && gameMessageS2CPacket1.content().getString().equals("This command is not available in AFK")) {
            NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getMiscComponent167Field4_1155().setIntegerField1_1806(7);
         }
      }
   }
}
