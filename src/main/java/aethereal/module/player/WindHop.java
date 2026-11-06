package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent091;
import aethereal.event.PacketEvent;
import aethereal.setting.BooleanSetting;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;

@ModuleRegister(name = "Wind Hop", description = "Automatically jumps after using a wind charge", category = Category.Player)
public class WindHop extends Module implements MinecraftAccess {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Turn your head down", true);
   private int integerField2 = -1;

   public BooleanSetting getBooleanSettingField1_2215() {
      return this.booleanSettingField1;
   }

   public WindHop() {
      this.addSettings(this.booleanSettingField1);
   }

   @EventTarget
   public void onPacketEvent_2213(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1012()) {
         PlayerInteractItemC2SPacket playerInteractItemC2SPacket = (PlayerInteractItemC2SPacket)packetEvent.getPacket_1015();
         if (playerInteractItemC2SPacket instanceof PlayerInteractItemC2SPacket playerInteractItemC2SPacket1
            && minecraft.player.getStackInHand(playerInteractItemC2SPacket1.getHand()).isOf(Items.WIND_CHARGE)) {
            this.integerField2 = 2;
         }
      }
   }

   @EventTarget
   public void onEventComponent074_2212(EventComponent074 eventComponent074) {
      if (this.integerField2 > 0) {
         this.integerField2--;
      }
   }

   @EventTarget
   public void onEventComponent091_2214(EventComponent091 eventComponent091) {
      if (this.integerField2 == 0) {
         eventComponent091.setJump_1066(true);
         this.integerField2 = -1;
      }
   }
}
