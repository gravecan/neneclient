package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.ClickEvent;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent076;
import aethereal.handler.HandlerComponent108;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent277;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.StreamSupport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@ModuleRegister(name = "Projectile Helper", description = "Helps to aim at the enemy when shooting with a bow or trident", category = Category.Combat)
public class ProjectileHelper extends Module {
   private int integerField1;
   private int integerField2;
   private boolean flagField3;
   private boolean flagField4;
   private final LivingEntity[] livingEntityArrayField5 = new LivingEntity[2];
   private final Vec3d[] vec3dArrayField6 = new Vec3d[5];
   private boolean flagField7 = true;

   public LivingEntity computeValue_1619() {
      return this.livingEntityArrayField5[0];
   }

   public boolean checkCondition_1627() {
      if (this.livingEntityArrayField5[0] != null && minecraft.player != null && minecraft.player.isUsingItem()) {
         ItemStack itemStack = minecraft.player.getActiveItem();
         return itemStack.getItem().getMaxUseTime(itemStack, minecraft.player) - minecraft.player.getItemUseTimeLeft() > 2;
      } else {
         return false;
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.performAction_1638();
   }

   @EventTarget
   public void onEventComponent076_1626(EventComponent076 eventComponent076) {
      this.flagField3 = eventComponent076.isWillLand_1008();
   }

   @EventTarget
   public void onEventComponent074_1625(EventComponent074 eventComponent074) {
      if (minecraft.player != null && minecraft.world != null) {
         ItemStack itemStack = minecraft.player.getStackInHand(Hand.MAIN_HAND);
         if (!(itemStack.getItem() instanceof BowItem) && !(itemStack.getItem() instanceof TridentItem)) {
            this.performAction_1638();
         } else {
            if (!minecraft.player.isUsingItem()) {
               this.flagField7 = true;
            }

            if (!this.flagField7) {
               this.performAction_1638();
            } else {
               this.flagField4 = minecraft.player.input.playerInput.jump() && (minecraft.player.isOnGround() || this.flagField3);
               this.performAction_1629(this.computeValue_1621());
               if (this.livingEntityArrayField5[0] != null) {
                  Vec3d[] vec3ds = this.vec3dArrayField6;
                  int i = this.integerField2++;
                  vec3ds[i % this.vec3dArrayField6.length] = new Vec3d(
                     this.livingEntityArrayField5[0].getX() - this.livingEntityArrayField5[0].lastRenderX,
                     0.0,
                     this.livingEntityArrayField5[0].getZ() - this.livingEntityArrayField5[0].lastRenderZ
                  );
               }

               UtilComponent250 utilComponent250;
               if (this.checkCondition_1627() && (utilComponent250 = this.computeValue_1631(itemStack)) != null) {
                  HandlerComponent108.getROTATION_1157().performAction_1141(utilComponent250, 180.0F, 1, 1);
               }
            }
         }
      }
   }

   @EventTarget
   public void onClickEvent_1624(ClickEvent clickEvent) {
      if (clickEvent.checkCondition_0983() && clickEvent.getButton_0980() == 0 && minecraft.player != null && minecraft.player.isUsingItem()) {
         this.flagField7 = !this.flagField7;
      }
   }

   private void performAction_1638() {
      LivingEntity[] livingEntitys = this.livingEntityArrayField5;
      this.livingEntityArrayField5[1] = null;
      livingEntitys[0] = null;
      this.integerField1 = 0;
      Arrays.fill(this.vec3dArrayField6, null);
   }

   private LivingEntity computeValue_1621() {
      Vec3d vec3d = minecraft.player.getEyePos();
      Vec3d vec3d1 = Vec3d.fromPolar(UtilComponent277.getFloatValueField3_3571(), UtilComponent277.getFloatValueField2_3570());
      return StreamSupport.<Entity>stream(minecraft.world.getEntities().spliterator(), false)
         .filter(PlayerEntity.class::isInstance)
         .map(entity -> (PlayerEntity)entity)
         .filter(
            playerEntity -> playerEntity != minecraft.player
               && playerEntity.isAlive()
               && !NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(playerEntity.getName().getString())
               && vec3d.squaredDistanceTo(playerEntity.getBoundingBox().getCenter()) <= 14400.0
         )
         .min(Comparator.comparingDouble(playerEntity -> -vec3d1.dotProduct(playerEntity.getBoundingBox().getCenter().subtract(vec3d).normalize())))
         .map(playerEntity -> (LivingEntity)playerEntity)
         .orElse(null);
   }

   private void performAction_1629(LivingEntity livingEntity) {
      if (livingEntity != this.livingEntityArrayField5[1]) {
         this.livingEntityArrayField5[1] = livingEntity;
         this.integerField1 = 0;
      } else {
         this.integerField1++;
      }

      if (this.livingEntityArrayField5[0] != this.livingEntityArrayField5[1] && (this.livingEntityArrayField5[0] == null || this.integerField1 >= 4)) {
         this.livingEntityArrayField5[0] = this.livingEntityArrayField5[1];
         Arrays.fill(this.vec3dArrayField6, null);
      }
   }

   private Vec3d computeValue_1622() {
      Vec3d vec3d = Vec3d.ZERO;
      int i = 0;

      for (Vec3d vec3d1 : this.vec3dArrayField6) {
         if (vec3d1 != null && vec3d1.horizontalLengthSquared() > 1.000000229429758E-6) {
            vec3d = vec3d.add(vec3d1);
            i++;
         }
      }

      return i == 0 ? Vec3d.ZERO : vec3d.multiply(1.0 / i);
   }

   private UtilComponent250 computeValue_1631(ItemStack itemStack) {
      Vec3d vec3d = this.computeValue_1637();
      Vec3d vec3d1 = minecraft.player.getEyePos().add(0.0, -0.1000000074661073, 0.0);
      double d = itemStack.getItem() instanceof BowItem ? this.computeValue_1623(itemStack) : 2.5;
      Box box = this.livingEntityArrayField5[0].getBoundingBox();
      Vec3d vec3d2 = this.computeValue_1622();
      Vec3d vec3d3 = box.getCenter();
      float f = 0.0F;
      float g = 0.0F;

      for (int i = 0; i < 6; i++) {
         f = this.computeValue_1633(vec3d1, vec3d3);
         g = this.computeValue_1636(vec3d1, vec3d3, vec3d, d);
         double[] e = this.computeValue_1634(
            vec3d1, Vec3d.fromPolar(g, f).multiply(d).add(vec3d), Math.hypot(vec3d3.x - vec3d1.getX(), vec3d3.z - vec3d1.getZ()), true
         );
         if (e == null) {
            return null;
         }

         Vec3d vec3d4 = box.getCenter().add(vec3d2.multiply(Math.min(e[1] + 6.0, 13.0)));
         if (vec3d4.squaredDistanceTo(vec3d3) < 9.999996044721066E-5) {
            break;
         }

         vec3d3 = vec3d4;
      }

      UtilComponent250 utilComponent250 = new UtilComponent250(MathHelper.wrapDegrees(f), g);
      float j = minecraft.player.age + minecraft.getRenderTickCounter().getTickProgress(false);
      float k = (float)(
            Math.sin(j * 0.8F) * 11.0
               + Math.sin(j * 0.04000001688754603 + 17.200001527756587) * 1.5
               + Math.sin(j * 0.11000000003049541 + 5.800002923050999) * 3.0
               + Math.sin(j * 0.07000000374109333 + 12.300000031704212) * 1.0
         )
         / 4.0F;
      float h = (float)(Math.sin(j * 0.1000000001867308) + Math.sin(j * 0.029999988014174556 + 54.09998474500903) * 0.5) / 2.0F;
      boolean bl = itemStack.getItem() instanceof TridentItem
         && itemStack.getItem().getMaxUseTime(itemStack, minecraft.player) - minecraft.player.getItemUseTimeLeft() < 9;
      if (!bl) {
         k = MathHelper.clamp(k, -0.3F, 0.3F);
         h = MathHelper.clamp(h, -0.3F, 0.3F);
      }

      utilComponent250.setYaw_3388(utilComponent250.getYaw_3386() + k);
      utilComponent250.setPitch_3384(utilComponent250.getPitch_3387() + h);
      return utilComponent250;
   }

   private float computeValue_1636(Vec3d vec3d, Vec3d vec3d1, Vec3d vec3d2, double d) {
      float f = -90.0F;
      float g = 90.0F;
      float h = this.computeValue_1633(vec3d, vec3d1);
      double e = Math.hypot(vec3d1.x - vec3d.getX(), vec3d1.z - vec3d.getZ());
      double i = vec3d1.y - vec3d.getY();

      for (int j = 0; j < 24; j++) {
         float k = (f + g) / 2.0F;
         double[] l = this.computeValue_1634(vec3d, Vec3d.fromPolar(k, h).multiply(d).add(vec3d2), e, false);
         if (l != null && !(l[0] >= i)) {
            g = k;
         } else {
            f = k;
         }
      }

      return (f + g) / 2.0F;
   }

   private double[] computeValue_1634(Vec3d vec3d, Vec3d vec3d1, double d, boolean bl) {
      Vec3d vec3d2 = vec3d;
      Vec3d vec3d3 = vec3d1;
      double e = 0.0;

      for (int i = 1; i <= 100; i++) {
         Vec3d vec3d4 = vec3d2.add(vec3d3);
         if (bl && minecraft.world.raycast(new RaycastContext(vec3d2, vec3d4, ShapeType.COLLIDER, FluidHandling.NONE, minecraft.player)).getType() != Type.MISS
            )
          {
            return null;
         }

         double f = Math.hypot(vec3d4.x - vec3d.getX(), vec3d4.z - vec3d.getZ());
         if (f >= d) {
            double g = f == e ? 1.0 : (d - e) / (f - e);
            return new double[]{MathHelper.lerp(g, vec3d2.y, vec3d4.y) - vec3d.getY(), i - 1 + g};
         }

         vec3d2 = vec3d4;
         e = f;
         vec3d3 = vec3d3.multiply(this.checkCondition_1620(vec3d2) ? 0.6000002908794272 : 0.9900000228356232).add(0.0, -0.050000001868616015, 0.0);
      }

      return null;
   }

   private boolean checkCondition_1620(Vec3d vec3d) {
      return minecraft.world.getBlockState(BlockPos.ofFloored(vec3d)).getFluidState().isIn(FluidTags.WATER);
   }

   private Vec3d computeValue_1637() {
      Vec3d vec3d = new Vec3d(
         minecraft.player.getX() - minecraft.player.lastRenderX,
         minecraft.player.getY() - minecraft.player.lastRenderY,
         minecraft.player.getZ() - minecraft.player.lastRenderZ
      );
      if (!this.flagField4) {
         return new Vec3d(vec3d.x, minecraft.player.isOnGround() ? 0.0 : vec3d.y, vec3d.z);
      }

      float f = minecraft.player.getYaw() * (float) (Math.PI / 180.0);
      double d = minecraft.player.isSprinting() ? 0.19999997617511883 : 0.0;
      return new Vec3d(
         vec3d.x - MathHelper.sin(f) * d, Math.max(0.42F + minecraft.player.getJumpBoostVelocityModifier(), vec3d.y), vec3d.z + MathHelper.cos(f) * d
      );
   }

   private double computeValue_1623(ItemStack itemStack1) {
      float f = 1.0F;
      ItemStack itemStack = minecraft.player.getActiveItem();
      if (minecraft.player.isUsingItem() && itemStack.getItem() instanceof BowItem) {
         float g = (itemStack.getItem().getMaxUseTime(itemStack, minecraft.player) - minecraft.player.getItemUseTimeLeft() + 1.5F) / 20.0F;
         f = Math.min((g * g + g * 2.0F) / 3.0F, 1.0F);
      }

      return f * 3.0;
   }

   private float computeValue_1633(Vec3d vec3d, Vec3d vec3d1) {
      return (float)Math.toDegrees(Math.atan2(-(vec3d1.getX() - vec3d.getX()), vec3d1.getZ() - vec3d.getZ()));
   }
}
