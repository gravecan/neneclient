package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.ClientTickEvent;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent091;
import aethereal.event.PacketEvent;
import aethereal.setting.ChoiceSetting;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import platform.inject.accessors.EntityInvoker;

@ModuleRegister(name = "Air Stuck", description = "Allows you to hover in the air in place", category = Category.Movement)
public class AirStuck extends Module implements MinecraftAccess {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Hover mode", "Normal", "Normal", "Removing player");
   private Vec3d vec3dField2;

   public AirStuck() {
      this.addSettings(this.choiceSettingField1);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (minecraft.player != null) {
         this.vec3dField2 = minecraft.player.getEntityPos();
         if (this.choiceSettingField1.checkCondition_2850("Removing player")) {
            minecraft.player.setRemoved(RemovalReason.DISCARDED);
         }
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (minecraft.player != null && this.choiceSettingField1.checkCondition_2850("Removing player")) {
         ((EntityInvoker)minecraft.player).unset();
         minecraft.world.addEntity(minecraft.player);
         minecraft.player.refreshPositionAfterTeleport(this.vec3dField2);
      }

      this.vec3dField2 = null;
   }

   @EventTarget
   public void onEventComponent074_2015(EventComponent074 eventComponent074) {
      if (this.vec3dField2 != null) {
         minecraft.player.setVelocity(0.0, 0.0, 0.0);
         minecraft.player.setPosition(this.vec3dField2.x, this.vec3dField2.y, this.vec3dField2.z);
      }
   }

   @EventTarget
   public void performAction_2014(ClientTickEvent clientTickEvent) {
      if (minecraft.player != null && minecraft.player.isRemoved()) {
         ((EntityInvoker)minecraft.player).baseTickInvoker();
      }
   }

   @EventTarget
   public void onEventComponent091_2017(EventComponent091 eventComponent091) {
      if (this.choiceSettingField1.checkCondition_2850("Normal")) {
         eventComponent091.performAction_0597(true);
      }
   }

   @EventTarget
   public void onPacketEvent_2016(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013()
         && (
            packetEvent.getPacket_1015() instanceof PlayerMoveC2SPacket
               || packetEvent.getPacket_1015() instanceof PlayerInputC2SPacket
               || packetEvent.getPacket_1015() instanceof PlayerActionC2SPacket
         )) {
         packetEvent.performAction_0597(true);
      }
   }
}
