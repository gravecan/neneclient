package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.setting.StringSetting;
import aethereal.util.UtilComponent252;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;

@ModuleRegister(name = "Auto Auth", description = "Automatically enters a password during authorization and registration", category = Category.Player)
public class AutoAuth extends Module implements MinecraftAccess {
   private final StringSetting stringSettingField1 = new StringSetting("Authorization password", "").withoutPersistence();
   private final StringSetting stringSettingField2 = new StringSetting("Registration password", "").withoutPersistence();
   private String stringField3;

   public StringSetting getStringSettingField1_2133() {
      return this.stringSettingField1;
   }

   public StringSetting getStringSettingField2_2130() {
      return this.stringSettingField2;
   }

   public String getStringField3_2134() {
      return this.stringField3;
   }

   public AutoAuth() {
      this.addSettings(this.stringSettingField1, this.stringSettingField2);
   }

   @EventTarget
   public void onPacketEvent_2132(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013()) {
         GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packetEvent.getPacket_1015();
         if (gameMessageS2CPacket instanceof GameMessageS2CPacket gameMessageS2CPacket1) {
            String string = gameMessageS2CPacket1.content().getString();
            if ((string.contains("Register") || string.contains("/reg") || string.contains("/register"))
               && !this.stringSettingField2.getCurrentValue().isEmpty()) {
               this.stringField3 = "/reg " + this.stringSettingField2.getCurrentValue();
            }

            if ((string.contains("Log in") || string.contains("Enter the game") || string.contains("/login"))
               && !this.stringSettingField1.getCurrentValue().isEmpty()) {
               this.stringField3 = "/login " + this.stringSettingField1.getCurrentValue();
            }
         }
      }
   }

   @EventTarget
   public void onEventComponent074_2131(EventComponent074 eventComponent074) {
      if (this.stringField3 != null) {
         if (UtilComponent252.NestedType400.checkCondition_3406() && UtilComponent252.NestedType400.checkCondition_3408()) {
            return;
         }

         minecraft.player.networkHandler.sendChatMessage(this.stringField3);
         this.stringField3 = null;
      }
   }
}
