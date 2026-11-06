package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.PacketEvent;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

@ModuleRegister(name = "No Server Desync", description = "Prevents the server from forcibly resetting your camera's rotation", category = Category.Combat)
public class NoServerDesync extends Module {
   @EventTarget
   public void onPacketEvent_1617(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013()
         && packetEvent.getPacket_1015() instanceof PlayerPositionLookS2CPacket playerPositionLookS2CPacket
         && playerPositionLookS2CPacket.change().pitch() != 0.0F
         && playerPositionLookS2CPacket.change().yaw() != 0.0F) {
         packetEvent.performAction_0597(true);
      }
   }
}
