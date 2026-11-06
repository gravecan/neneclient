package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent090;
import aethereal.event.PacketEvent;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent268;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;

@ModuleRegister(name = "Auto Fish", description = "Automatically catches fish in AFK mode", category = Category.Player)
public class AutoFish extends Module implements MinecraftAccess {
   private final UtilComponent268 utilComponent268Field1 = new UtilComponent268();
   private boolean flagField2;

   public UtilComponent268 getUtilComponent268Field1_2138() {
      return this.utilComponent268Field1;
   }

   public boolean isFlagField2_2142() {
      return this.flagField2;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (minecraft.player != null
         && minecraft.player.getInventory().getStack(minecraft.player.getInventory().getSelectedSlot()).getItem() == Items.FISHING_ROD) {
         if (minecraft.player.fishHook == null) {
            this.performAction_2143(false);
         }

         UtilComponent265.performAction_3505(this.getName() + "activated, happy fishing!");
      }
   }

   @EventTarget
   public void onPacketEvent_2140(PacketEvent packetEvent) {
      PlaySoundS2CPacket playSoundS2CPacket = (PlaySoundS2CPacket)packetEvent.getPacket_1015();
      if (playSoundS2CPacket instanceof PlaySoundS2CPacket playSoundS2CPacket1
         && ((SoundEvent)playSoundS2CPacket1.getSound().value()).id().equals(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH.id())
         && minecraft.player.fishHook.squaredDistanceTo(playSoundS2CPacket1.getX(), playSoundS2CPacket1.getY(), playSoundS2CPacket1.getZ())
            <= 0.48999979194765847
         && minecraft.player.fishHook != null) {
         this.performAction_2143(true);
         this.utilComponent268Field1.performAction_3524();
      }
   }

   @EventTarget
   public void onEventComponent074_2139(EventComponent074 eventComponent074) {
      if (this.utilComponent268Field1.checkCondition_3522(450L) && this.flagField2) {
         this.performAction_2143(false);
      }
   }

   @EventTarget
   public void onEventComponent090_2141(EventComponent090 eventComponent090) {
      if (this.flagField2) {
         eventComponent090.performAction_0597(true);
      }
   }

   public void performAction_2143(boolean bl) {
      minecraft.interactionManager.interactItem(minecraft.player, Hand.MAIN_HAND);
      minecraft.player.swingHand(Hand.MAIN_HAND);
      this.flagField2 = bl;
   }
}
