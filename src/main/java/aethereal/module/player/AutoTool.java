package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.handler.HandlerComponent110;
import java.util.Comparator;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;

@ModuleRegister(name = "Auto Tool", description = "Automatically selects the appropriate tool for the block", category = Category.Player)
public class AutoTool extends Module implements MinecraftAccess {
   private final int[] integerArrayField1 = new int[]{-1, -1};

   @EventTarget
   public void onEventComponent074_2149(EventComponent074 eventComponent074) {
      HandlerComponent110 handlerComponent110 = NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().computeValue_1146();
      if (handlerComponent110.getListField1_1177().isEmpty()) {
         if (minecraft.crosshairTarget instanceof BlockHitResult blockHitResult) {
            BlockHitResult blockHitResult1 = blockHitResult;
            if (minecraft.options.attackKey.isPressed()) {
               int i;
               if (this.integerArrayField1[0] == -1 && (i = this.computeValue_2154(minecraft.world.getBlockState(blockHitResult1.getBlockPos()))) != -1) {
                  this.integerArrayField1[0] = minecraft.player.getInventory().getSelectedSlot();
                  if (i > 8) {
                     this.integerArrayField1[1] = i;
                     handlerComponent110.performAction_1179(i, this.integerArrayField1[0], 1);
                     return;
                  }

                  minecraft.player.getInventory().setSelectedSlot(i);
                  return;
               }

               return;
            }
         }

         if (this.integerArrayField1[0] == -1) {
            return;
         }

         if (this.integerArrayField1[1] == -1) {
            minecraft.player.getInventory().setSelectedSlot(this.integerArrayField1[0]);
         } else {
            handlerComponent110.performAction_1179(this.integerArrayField1[1], this.integerArrayField1[0], 1);
         }

         this.integerArrayField1[0] = -1;
         this.integerArrayField1[1] = -1;
      }
   }

   private int computeValue_2154(BlockState blockState) {
      PlayerInventory playerInventory = minecraft.player.getInventory();
      int i;
      return blockState.isOf(Blocks.COBWEB)
            && (
                  i = IntStream.range(0, playerInventory.getMainStacks().size())
                     .filter(j -> playerInventory.getStack(j).isOf(Items.SHEARS))
                     .findFirst()
                     .orElse(-1)
               )
               != -1
         ? i
         : IntStream.range(0, playerInventory.getMainStacks().size())
            .filter(
               j -> playerInventory.getStack(j).getMiningSpeedMultiplier(blockState) > 1.0F
                  && this.checkCondition_2153(playerInventory.getStack(j), blockState)
            )
            .boxed()
            .max(Comparator.comparingDouble(integer -> playerInventory.getStack(integer).getMiningSpeedMultiplier(blockState)))
            .orElse(-1);
   }

   private boolean checkCondition_2153(ItemStack itemStack, BlockState blockState) {
      if (blockState.isIn(BlockTags.AXE_MINEABLE)) {
         return itemStack.getItem() instanceof AxeItem;
      } else if (blockState.isIn(BlockTags.PICKAXE_MINEABLE)) {
         return Registries.ITEM.getId(itemStack.getItem()).getPath().endsWith("pickaxe");
      } else if (blockState.isIn(BlockTags.SHOVEL_MINEABLE)) {
         return itemStack.getItem() instanceof ShovelItem;
      } else {
         return blockState.isIn(BlockTags.HOE_MINEABLE) ? itemStack.getItem() instanceof HoeItem : true;
      }
   }
}
