package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.render.RenderComponent183;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent268;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

@ModuleRegister(name = "X Ray", description = "Highlights ancient debris found during a dynamite explosion", category = Category.Misc)
public class XRay extends Module implements MinecraftAccess {
   private final List<BlockPos> listField1 = new ArrayList<>();
   private final UtilComponent268 utilComponent268Field2 = new UtilComponent268();
   private boolean flagField3;

   public List<BlockPos> getListField1_2012() {
      return this.listField1;
   }

   public UtilComponent268 getUtilComponent268Field2_2005() {
      return this.utilComponent268Field2;
   }

   public boolean isFlagField3_2009() {
      return this.flagField3;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.listField1.clear();
      this.flagField3 = false;
      this.utilComponent268Field2.performAction_3524();
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.listField1.clear();
      this.flagField3 = false;
      this.utilComponent268Field2.performAction_3524();
   }

   @EventTarget
   public void onDrawEvent_2006(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1002()) {
         this.listField1
            .removeIf(
               blockPos -> minecraft.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)
                  || blockPos.getSquaredDistance(minecraft.player.getEntityPos()) >= 6400.0
                  || !minecraft.world.getChunkManager().isChunkLoaded(blockPos.getX() >> 4, blockPos.getZ() >> 4)
            );
         this.listField1
            .forEach(
               blockPos -> drawEvent.getRenderComponent185Field2_0999()
                  .performAction_2594(drawEvent.getStack2_1004(), new Box(blockPos), RenderComponent183.computeValue_2558(255, 165, 0, 150), 1.0F)
            );
      }
   }

   @EventTarget
   public void onPacketEvent_2008(PacketEvent packetEvent) {
      ChunkDeltaUpdateS2CPacket chunkDeltaUpdateS2CPacket = (ChunkDeltaUpdateS2CPacket)packetEvent.getPacket_1015();
      if (chunkDeltaUpdateS2CPacket instanceof ChunkDeltaUpdateS2CPacket chunkDeltaUpdateS2CPacket1) {
         chunkDeltaUpdateS2CPacket1.visitUpdates((blockPos, blockState) -> {
            if (blockState.getBlock().equals(Blocks.ANCIENT_DEBRIS)) {
               BlockPos blockPos1 = blockPos.toImmutable();
               if (!this.listField1.contains(blockPos1)) {
                  this.listField1.add(blockPos1);
                  this.flagField3 = true;
                  this.utilComponent268Field2.performAction_3524();
               }
            }
         });
      }
   }

   @EventTarget
   public void onEventComponent074_2007(EventComponent074 eventComponent074) {
      this.utilComponent268Field2.performAction_3518();
      if (this.flagField3 && this.utilComponent268Field2.checkCondition_3516(5L)) {
         UtilComponent265.performAction_3505("Discovered &c" + this.listField1.size() + "&7 Ancient Debris");
         this.flagField3 = false;
      }
   }
}
