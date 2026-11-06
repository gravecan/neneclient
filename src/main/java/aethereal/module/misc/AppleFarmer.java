package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent279;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import platform.inject.accessors.SlotAccessor;

@ModuleRegister(name = "Apple Farmer", description = "Automatically farms apples", category = Category.Misc)
public class AppleFarmer extends Module {
   @EventTarget
   public void onEventComponent074_1826(EventComponent074 eventComponent074) {
      if (minecraft.player.age % 600 < 10) {
         if (minecraft.player.getMainHandStack().getItem() instanceof AxeItem) {
            minecraft.player.getInventory().setSelectedSlot((int)UtilComponent279.computeValue_3582(0.0F, 8.0F));
         }
      } else {
         NeneClient.getInstance().getProcessors().getModules().getAutoEatField55_0462().performAction_2137(19);
         if (!NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getHandlerComponent109Field8_1145().checkCondition_1163()
            && NeneClient.getInstance()
               .getProcessors()
               .getHandlerComponent106Field14_0673()
               .getHandlerComponent114Field6_1148()
               .checkCondition_1210(minecraft.player.getMainHandStack(), 10.0, 98.0)) {
            BlockPos blockPos = this.computeValue_1828(5.0, blockState -> blockState.isIn(BlockTags.LEAVES), this::computeValue_1816);
            if (blockPos != null) {
               this.performAction_1838(blockPos, itemStack -> itemStack.getItem() instanceof HoeItem);
            } else {
               BlockPos blockPos1 = this.computeValue_1828(5.0, blockState -> blockState.isIn(BlockTags.LOGS), blockPos3 -> blockPos3.getY());
               if (blockPos1 != null) {
                  this.performAction_1838(blockPos1, itemStack -> itemStack.getItem() instanceof AxeItem);
               } else if (minecraft.currentScreen instanceof GenericContainerScreen) {
                  this.performAction_1843();
               } else if (UtilComponent270.computeValue_3538(Items.BONE) > 0 && UtilComponent270.computeValue_3538(Items.BONE_MEAL) == 0) {
                  this.performAction_1847();
               } else if (!this.checkCondition_1844() && !this.checkCondition_1845()) {
                  BlockPos blockPos2 = this.computeValue_1828(16.0, blockState -> blockState.isIn(BlockTags.DIRT), this::computeValue_1836);
                  if (blockPos2 != null) {
                     if (UtilComponent270.computeValue_3538(Items.STICK) <= 128 && UtilComponent270.computeValue_3538(Items.OAK_SAPLING) <= 128) {
                        this.performAction_1837(blockPos2);
                     } else {
                        this.performAction_1846();
                     }
                  }
               } else {
                  this.performAction_1843();
               }
            }
         }
      }
   }

   private void performAction_1838(BlockPos blockPos, Predicate<ItemStack> predicate) {
      if (!predicate.test(minecraft.player.getMainHandStack())) {
         this.checkCondition_1830(predicate);
      } else if (!(minecraft.player.getMainHandStack().getItem() instanceof AxeItem) || !(minecraft.player.getAttackCooldownProgress(0.0F) <= 0.15F)) {
         Vec3d vec3d = blockPos.toCenterPos();
         if (this.checkCondition_1818(vec3d)) {
            Vec3d vec3d1 = new Box(blockPos).raycast(minecraft.player.getEyePos(), vec3d).orElse(vec3d);
            minecraft.interactionManager.updateBlockBreakingProgress(blockPos, Direction.getFacing(vec3d1.subtract(vec3d)));
            minecraft.player.swingHand(Hand.MAIN_HAND);
         }
      }
   }

   private void performAction_1837(BlockPos blockPos) {
      boolean bl = minecraft.world.getBlockState(blockPos.up()).getBlock() instanceof SaplingBlock;
      Predicate predicate = bl ? itemStack -> itemStack.isOf(Items.BONE_MEAL) : itemStack -> {
         BlockItem blockItem = (BlockItem)itemStack.getItem();
         return blockItem instanceof BlockItem blockItem1 && blockItem1.getBlock() instanceof SaplingBlock;
      };
      if (!predicate.test(minecraft.player.getMainHandStack())) {
         this.checkCondition_1830(predicate);
      } else {
         Vec3d vec3d = new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5);
         if (this.checkCondition_1818(vec3d) && minecraft.player.age % 4 == 0) {
            minecraft.interactionManager
               .interactBlock(minecraft.player, Hand.MAIN_HAND, new BlockHitResult(vec3d, Direction.UP, bl ? blockPos.up() : blockPos, false));
            minecraft.player.swingHand(Hand.MAIN_HAND);
         }
      }
   }

   private void performAction_1843() {
      GenericContainerScreen genericContainerScreen = (GenericContainerScreen)minecraft.currentScreen;
      if (!(genericContainerScreen instanceof GenericContainerScreen genericContainerScreen1)) {
         this.performAction_1817(
            this.computeValue_1828(5.0, blockState -> blockState.isOf(this.checkCondition_1844() ? Blocks.CHEST : Blocks.BARREL), this::computeValue_1836)
         );
      } else if (minecraft.player.age % 50 == 0) {
         minecraft.player.closeHandledScreen();
      } else if (minecraft.player.age % 2 == 0) {
         ScreenHandler screenHandler = genericContainerScreen1.getScreenHandler();
         TranslatableTextContent translatableTextContent = (TranslatableTextContent)genericContainerScreen1.getTitle().getContent();
         boolean bl;
         if (translatableTextContent instanceof TranslatableTextContent translatableTextContent1) {
            if (translatableTextContent1.getKey().contains("chest")) {
               bl = true;
            } else {
               bl = false;
            }
         } else {
            bl = false;
         }

         boolean bl1 = bl;
         int i;
         if (bl1) {
            i = this.computeValue_1833(screenHandler, false, 5, itemStack -> itemStack.isOf(Items.APPLE) || itemStack.isOf(Items.OAK_LOG));
         } else {
            i = this.checkCondition_1845() ? this.computeValue_1833(screenHandler, true, 3, this::checkCondition_1827) : 0;
         }

         int j = i;
         if (j == 0) {
            minecraft.player.closeHandledScreen();
         }
      }
   }

   private void performAction_1847() {
      if (minecraft.player.age % 2 == 0) {
         PlayerScreenHandler playerScreenHandler = minecraft.player.playerScreenHandler;
         if (playerScreenHandler.getSlot(0).getStack().isOf(Items.BONE_MEAL)) {
            this.performAction_1832(playerScreenHandler, 0, 0, SlotActionType.QUICK_MOVE);
            minecraft.player.closeHandledScreen();
         } else {
            int i = this.computeValue_1834(Items.BONE);
            if (i >= 0) {
               this.performAction_1832(playerScreenHandler, i, 0, SlotActionType.PICKUP);
               this.performAction_1832(playerScreenHandler, 1, 0, SlotActionType.PICKUP);
            }
         }
      }
   }

   private void performAction_1846() {
      float f = this.computeValue_1820();
      this.performAction_1829(new UtilComponent250(f * 10.0F, UtilComponent279.computeValue_3577(f / 4.0F, -30.0F, 30.0F)));
      if (minecraft.player.age % 5 == 0) {
         PlayerScreenHandler playerScreenHandler = minecraft.player.playerScreenHandler;
         int i = -1;
         int j = -1;

         for (Slot slot : playerScreenHandler.slots) {
            if (this.checkCondition_1822(slot) && slot.getStack().isOf(Items.OAK_SAPLING) && slot.getStack().getCount() > j) {
               j = slot.getStack().getCount();
               i = slot.id;
            }
         }

         for (Slot slot1 : playerScreenHandler.slots) {
            if (this.checkCondition_1822(slot1) && (slot1.getStack().isOf(Items.STICK) || slot1.getStack().isOf(Items.OAK_SAPLING) && slot1.id != i)) {
               this.performAction_1832(playerScreenHandler, slot1.id, 1, SlotActionType.THROW);
            }
         }
      }
   }

   private void performAction_1817(BlockPos blockPos) {
      if (blockPos != null && this.checkCondition_1818(blockPos.toCenterPos()) && minecraft.player.age % 4 == 0) {
         Vec3d vec3d = blockPos.toCenterPos();
         Vec3d vec3d1 = new Box(blockPos).raycast(minecraft.player.getEyePos(), vec3d).orElse(vec3d);
         minecraft.interactionManager
            .interactBlock(minecraft.player, Hand.MAIN_HAND, new BlockHitResult(vec3d1, Direction.getFacing(vec3d1.subtract(vec3d)), blockPos, false));
      }
   }

   private boolean checkCondition_1830(Predicate<ItemStack> predicate) {
      int i = IntStream.range(0, 36).filter(k -> predicate.test(minecraft.player.getInventory().getStack(k))).findFirst().orElse(-1);
      if (i < 0) {
         return false;
      }

      if (i < 9) {
         minecraft.player.getInventory().setSelectedSlot(i);
         return true;
      }

      if (minecraft.player.age % 4 != 0) {
         return true;
      }

      int j = IntStream.range(0, 9)
         .filter(k -> minecraft.player.getInventory().getStack(k).isEmpty())
         .findFirst()
         .orElse(minecraft.player.getInventory().getSelectedSlot());
      NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().computeValue_1146().performAction_1179(i, j, 1);
      minecraft.player.getInventory().setSelectedSlot(j);
      return true;
   }

   private boolean checkCondition_1818(Vec3d vec3d) {
      UtilComponent250 utilComponent250 = UtilComponent250.computeValue_3390(minecraft.player.getEyePos(), vec3d);
      float f = this.computeValue_1820();
      this.performAction_1829(
         new UtilComponent250(
            utilComponent250.getYaw_3386() + f / 2.0F, UtilComponent279.computeValue_3577(utilComponent250.getPitch_3387() + f / 4.0F, -90.0F, 90.0F)
         )
      );
      return UtilComponent250.computeValue_3385().computeValue_3389(utilComponent250) < 20.0;
   }

   private void performAction_1829(UtilComponent250 utilComponent250) {
      NeneClient.getInstance().getProcessors().computeValue_0672().performAction_1141(utilComponent250, 180.0F, 1, 1);
   }

   private float computeValue_1820() {
      float f = minecraft.player.age + minecraft.getRenderTickCounter().getTickProgress(false);
      return (float)((Math.sin(f * 0.31F) * 0.5 + Math.sin(f * 0.73F + 1.1F) * 0.30000003042305273 + Math.sin(f * 1.7F + 2.6F) * 0.2000000149681302) * 8.0);
   }

   private int computeValue_1833(ScreenHandler screenHandler, boolean bl, int i, Predicate<ItemStack> predicate) {
      int j = 0;

      for (Slot slot : screenHandler.slots) {
         if (j >= i) {
            break;
         }

         if (this.checkCondition_1822(slot) != bl && predicate.test(slot.getStack())) {
            this.performAction_1832(screenHandler, slot.id, 0, SlotActionType.QUICK_MOVE);
            j++;
         }
      }

      return j;
   }

   private void performAction_1832(ScreenHandler screenHandler, int i, int j, SlotActionType slotActionType) {
      minecraft.interactionManager.clickSlot(screenHandler.syncId, i, j, slotActionType, minecraft.player);
   }

   private boolean checkCondition_1822(Slot slot) {
      return ((SlotAccessor)slot).getInventory() == minecraft.player.getInventory();
   }

   private int computeValue_1834(Item item) {
      for (Slot slot : minecraft.player.playerScreenHandler.slots) {
         if (this.checkCondition_1822(slot) && slot.getStack().isOf(item)) {
            return slot.id;
         }
      }

      return -1;
   }

   private boolean checkCondition_1827(ItemStack itemStack) {
      BlockItem blockItem = (BlockItem)itemStack.getItem();
      if (blockItem instanceof BlockItem blockItem1) {
         if (!(blockItem1.getBlock() instanceof SaplingBlock)
            && itemStack.isOf(Items.BONE_MEAL)
            && (!itemStack.isOf(Items.BONE) || UtilComponent270.computeValue_3538(Items.BONE_MEAL) != 0)) {
            return false;
         }
      } else if (itemStack.isOf(Items.BONE_MEAL)) {
      }

      return true;
   }

   private boolean checkCondition_1844() {
      return UtilComponent270.computeValue_3538(Items.APPLE) > 128 || UtilComponent270.computeValue_3538(Items.OAK_LOG) > 192;
   }

   private boolean checkCondition_1845() {
      return UtilComponent270.computeValue_3538(Items.OAK_SAPLING) == 0 || UtilComponent270.computeValue_3538(Items.BONE_MEAL) == 0;
   }

   private double computeValue_1836(BlockPos blockPos) {
      return minecraft.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(blockPos));
   }

   private double computeValue_1816(BlockPos blockPos) {
      Vec3d vec3d = minecraft.player.getEyePos();
      Vec3d vec3d1 = minecraft.player.getRotationVec(1.0F);
      Vec3d vec3d2 = Vec3d.ofCenter(blockPos).subtract(vec3d);
      double d = vec3d2.dotProduct(vec3d1);
      return d <= 0.0 ? 1.7976922776554332E308 : vec3d2.subtract(vec3d1.multiply(d)).lengthSquared();
   }

   private BlockPos computeValue_1828(double d, Predicate<BlockState> predicate, ToDoubleFunction<BlockPos> toDoubleFunction) {
      BlockPos blockPos = minecraft.player.getBlockPos();
      BlockPos blockPos1 = null;
      double e = 1.7976922776554332E308;
      double f = d * d;
      int i = (int)Math.ceil(d);
      Mutable mutable = new Mutable();

      for (int j = -i; j <= i; j++) {
         for (int k = -i; k <= i; k++) {
            for (int l = -i; l <= i; l++) {
               mutable.set(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
               if (predicate.test(minecraft.world.getBlockState(mutable)) && this.computeValue_1836(mutable) <= f) {
                  double g = toDoubleFunction.applyAsDouble(mutable);
                  if (g < e) {
                     e = g;
                     blockPos1 = mutable.toImmutable();
                  }
               }
            }
         }
      }

      return blockPos1;
   }
}
