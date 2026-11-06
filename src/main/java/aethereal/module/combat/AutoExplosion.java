package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.handler.HandlerComponent108;
import aethereal.setting.BooleanSetting;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent279;
import net.minecraft.block.Blocks;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@ModuleRegister(name = "Auto Explosion", description = "Places a crystal on obsidian and instantly detonates it", category = Category.Combat)
public class AutoExplosion extends Module {
   private BlockPos blockPosField1;
   private int integerField2;
   private final BooleanSetting booleanSettingField3 = new BooleanSetting("Install double crystal", false);
   private int integerField4 = -1;

   public AutoExplosion() {
      this.addSettings(this.booleanSettingField3);
   }

   @EventTarget
   public void onPacketEvent_1576(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1012()) {
         PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket = (PlayerInteractBlockC2SPacket)packetEvent.getPacket_1015();
         if (playerInteractBlockC2SPacket instanceof PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket1
            && minecraft.player.getMainHandStack().getItem() == Items.OBSIDIAN) {
            BlockHitResult blockHitResult = playerInteractBlockC2SPacket1.getBlockHitResult();
            this.blockPosField1 = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
            this.integerField4 = -1;
            this.integerField2 = 0;
         }
      }
   }

   @EventTarget
   public void onEventComponent074_1575(EventComponent074 eventComponent074) {
      if (this.blockPosField1 != null) {
         int i = UtilComponent270.computeValue_3539(Items.END_CRYSTAL, true);
         if (i != -1
            && minecraft.world.getBlockState(this.blockPosField1).isOf(Blocks.OBSIDIAN)
            && minecraft.player.canInteractWithBlockAt(this.blockPosField1, 0.0)) {
            if (this.integerField2 == 1 || this.booleanSettingField3.getCurrentValue() && this.integerField2 == 5) {
               this.performAction_1577(i);
            }

            for (EndCrystalEntity endCrystalEntity : minecraft.world
               .getEntitiesByClass(EndCrystalEntity.class, new Box(this.blockPosField1.up()).expand(0.5), endCrystalEntity1 -> true)) {
               this.performAction_1580(this.computeValue_1579(endCrystalEntity.getBoundingBox()));
               if (this.integerField2 >= 3 && minecraft.player.canInteractWithEntity(endCrystalEntity, 0.0)) {
                  minecraft.interactionManager.attackEntity(minecraft.player, endCrystalEntity);
                  minecraft.player.swingHand(Hand.MAIN_HAND);
                  if (!this.booleanSettingField3.getCurrentValue() || this.integerField2 >= 7) {
                     this.performAction_1581();
                     return;
                  }
                  break;
               }
            }

            int j = this.integerField2 + 1;
            this.integerField2 = j;
            if (j > (this.booleanSettingField3.getCurrentValue() ? 8 : 4)) {
               this.performAction_1581();
            }

            return;
         } else {
            this.performAction_1581();
         }
      }
   }

   private void performAction_1577(int i) {
      if (minecraft.player.getInventory().getSelectedSlot() != i) {
         this.integerField4 = minecraft.player.getInventory().getSelectedSlot();
         minecraft.player.getInventory().setSelectedSlot(i);
      }

      Vec3d vec3d = this.blockPosField1.toCenterPos();
      Vec3d vec3d1 = new Box(this.blockPosField1).raycast(minecraft.player.getEyePos(), vec3d).orElse(vec3d);
      this.performAction_1580(vec3d);
      minecraft.interactionManager
         .interactBlock(minecraft.player, Hand.MAIN_HAND, new BlockHitResult(vec3d1, Direction.getFacing(vec3d1.subtract(vec3d)), this.blockPosField1, false));
      minecraft.player.swingHand(Hand.MAIN_HAND);
   }

   private Vec3d computeValue_1579(Box box) {
      Vec3d vec3d = minecraft.player.getEyePos();
      return new Vec3d(
         MathHelper.clamp(vec3d.getX(), box.minX, box.maxX) + UtilComponent279.computeValue_3582(-0.1F, 0.1F),
         MathHelper.clamp(vec3d.getY(), box.minY, box.maxY) + UtilComponent279.computeValue_3582(-0.1F, 0.1F),
         MathHelper.clamp(vec3d.z, box.minZ, box.maxZ) + UtilComponent279.computeValue_3582(-0.1F, 0.1F)
      );
   }

   private void performAction_1580(Vec3d vec3d) {
      UtilComponent250 utilComponent250 = UtilComponent250.computeValue_3390(minecraft.player.getEyePos(), vec3d);
      HandlerComponent108.getROTATION_1157()
         .performAction_1141(
            new UtilComponent250(
               utilComponent250.getYaw_3386() + UtilComponent279.computeValue_3582(-3.0F, 3.0F),
               utilComponent250.getPitch_3387() + UtilComponent279.computeValue_3582(-3.0F, 3.0F)
            ),
            120.0F,
            1,
            2
         );
   }

   private void performAction_1581() {
      if (this.integerField4 != -1) {
         minecraft.player.getInventory().setSelectedSlot(this.integerField4);
      }

      this.integerField4 = -1;
      this.blockPosField1 = null;
      this.integerField2 = 0;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.performAction_1581();
   }
}
