package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.util.UtilComponent265;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

@ModuleRegister(name = "Anti Bot", description = "Hides fake players appearing in the world", category = Category.Combat)
public class AntiBot extends Module {
   private final List<UUID> listField1 = new ArrayList<>();

   @Override
   public void onEnable() {
      super.onEnable();
      this.listField1.clear();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.listField1.clear();
   }

   @EventTarget
   public void onPacketEvent_1500(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013()) {
         EntitySpawnS2CPacket entitySpawnS2CPacket = (EntitySpawnS2CPacket)packetEvent.getPacket_1015();
         if (entitySpawnS2CPacket instanceof EntitySpawnS2CPacket entitySpawnS2CPacket1 && entitySpawnS2CPacket1.getEntityType() == EntityType.PLAYER) {
            PlayerListEntry playerListEntry = minecraft.getNetworkHandler().getPlayerListEntry(entitySpawnS2CPacket1.getUuid());
            boolean bl = playerListEntry != null && playerListEntry.getSkinTextures() != null;
            boolean bl1 = playerListEntry != null && !playerListEntry.getProfile().properties().get("textures").isEmpty();
            boolean bl2 = playerListEntry == null || playerListEntry.getLatency() == 0;
            if (!bl && !bl1 && bl2) {
               this.listField1.add(entitySpawnS2CPacket1.getUuid());
            }
         }
      }
   }

   @EventTarget
   public void onEventComponent074_1499(EventComponent074 eventComponent074) {
      ArrayList arrayList = new ArrayList();

      for (UUID uUID : this.listField1) {
         for (PlayerEntity playerEntity : minecraft.world.getPlayers()) {
            if (playerEntity.getUuid().equals(uUID)) {
               boolean bl = !playerEntity.getEquippedStack(EquipmentSlot.HEAD).isEmpty()
                  && !playerEntity.getEquippedStack(EquipmentSlot.CHEST).isEmpty()
                  && !playerEntity.getEquippedStack(EquipmentSlot.LEGS).isEmpty()
                  && !playerEntity.getEquippedStack(EquipmentSlot.FEET).isEmpty();
               if (bl) {
                  UtilComponent265.performAction_3505("The fake player was detected and removed from the world.");
                  minecraft.world.removeEntity(playerEntity.getId(), RemovalReason.DISCARDED);
               }

               arrayList.add(uUID);
               break;
            }
         }
      }

      this.listField1.removeAll(arrayList);
   }
}
