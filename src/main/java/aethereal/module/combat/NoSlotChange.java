package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.PacketEvent;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;

@ModuleRegister(name = "No Slot Change", description = "Prevents the server from forcibly changing the active slot in the hotbar", category = Category.Combat)
public class NoSlotChange extends Module {
   @EventTarget
   public void onPacketEvent_1618(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013() && packetEvent.getPacket_1015() instanceof UpdateSelectedSlotS2CPacket) {
         minecraft.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(minecraft.player.getInventory().getSelectedSlot()));
         packetEvent.performAction_0597(true);
      }
   }
}
