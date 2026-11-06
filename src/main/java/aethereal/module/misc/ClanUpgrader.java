package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent277;
import aethereal.util.UtilComponent279;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import platform.inject.invokers.MinecraftClientInvoker;

@ModuleRegister(name = "Clan Upgrader", description = "Quickly upgrade your clan with redstone and a torch", category = Category.Misc)
public class ClanUpgrader extends Module {
   private int integerField1;

   @Override
   public void onDisable() {
      super.onDisable();
      if (this.integerField1 != -1) {
         minecraft.player.getInventory().setSelectedSlot(this.integerField1);
         this.integerField1 = -1;
      }
   }

   @EventTarget
   public void onEventComponent074_1861(EventComponent074 eventComponent074) {
      int i = UtilComponent270.computeValue_3539(Items.REDSTONE, true);
      int j = UtilComponent270.computeValue_3539(Items.TORCH, true);
      int k = i != -1 ? i : j;
      if (i == -1 && j == -1) {
         UtilComponent265.performAction_3505("You need to have a torch or redstone in your hotbar");
         this.toggle();
      } else {
         float f = (float)(Math.sin(System.currentTimeMillis() / 1220.0) * (Math.abs(90.0F - minecraft.player.getPitch()) / 8.0F))
            + UtilComponent279.computeValue_3582(-0.1F, 0.1F);
         UtilComponent250 utilComponent250 = new UtilComponent250(
            UtilComponent277.getFloatValueField2_3570() + UtilComponent279.computeValue_3582(-1.0F, 1.0F),
            UtilComponent279.computeValue_3577(88.0F + f, -90.0F, 90.0F)
         );
         NeneClient.getInstance().getProcessors().computeValue_0672().performAction_1141(utilComponent250, 90.0F, 1, 1);
         if (this.integerField1 == -1) {
            this.integerField1 = minecraft.player.getInventory().getSelectedSlot();
         }

         if (minecraft.player.getInventory().getSelectedSlot() != k) {
            minecraft.player.getInventory().setSelectedSlot(k);
         }

         if (UtilComponent250.computeValue_3385().computeValue_3389(utilComponent250) <= 1.0) {
            BlockPos blockPos = minecraft.player.getBlockPos();
            if (!minecraft.world.getBlockState(blockPos).isOf(Blocks.REDSTONE_WIRE) && !minecraft.world.getBlockState(blockPos).isOf(Blocks.TORCH)) {
               ((MinecraftClientInvoker)minecraft).invokeDoItemUse();
            } else {
               minecraft.interactionManager.attackBlock(blockPos, Direction.UP);
               minecraft.player.swingHand(Hand.MAIN_HAND);
            }
         }
      }
   }
}
