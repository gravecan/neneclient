package aethereal.module.combat;

import aethereal.core.MinecraftAccess;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import platform.inject.accessors.LivingEntityGravityInvoker;
import platform.inject.invokers.EntityCollisionPredictionInvoker;
import platform.inject.invokers.EntityMovementInvoker;

public class CombatComponent166 implements MinecraftAccess {
   private CombatComponent166() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static boolean checkCondition_1612() {
      return minecraft.player != null && minecraft.player.getMainHandStack().isOf(Items.MACE);
   }

   public static Optional<Vec3d> computeValue_1615(ClientPlayerEntity clientPlayerEntity, World world) {
      if (world == null || clientPlayerEntity.isOnGround()) {
         return Optional.empty();
      }

      if (clientPlayerEntity.getAbilities().flying || clientPlayerEntity.isGliding() || clientPlayerEntity.hasVehicle() || clientPlayerEntity.isClimbing()) {
         return Optional.empty();
      }

      if (clientPlayerEntity.hasStatusEffect(StatusEffects.LEVITATION)) {
         return Optional.empty();
      }

      if (!clientPlayerEntity.isTouchingWater() && !clientPlayerEntity.isInLava()) {
         LivingEntityGravityInvoker livingEntityGravityInvoker = (LivingEntityGravityInvoker)clientPlayerEntity;
         double d = livingEntityGravityInvoker.getGravityInvoker();
         Box box = clientPlayerEntity.getBoundingBox();
         double e = 0.0;
         double f = 0.0;
         double g = 0.0;
         Vec3d vec3d = clientPlayerEntity.getVelocity();
         ArrayList arrayList = new ArrayList();

         for (int i = 0; i < 400; i++) {
            double h = vec3d.x * 0.9799995613681012;
            double j = (vec3d.y - d) * 0.9799995613681012;
            double k = vec3d.z * 0.9799995613681012;
            Vec3d vec3d1 = new Vec3d(h, j, k);
            Box box1 = box.offset(e, f, g);
            arrayList.clear();
            EntityCollisionPredictionInvoker.findCollisionsForMovement(clientPlayerEntity, world, arrayList, box1);
            Vec3d vec3d2 = EntityCollisionPredictionInvoker.adjustMovementForCollisions(clientPlayerEntity, vec3d1, box1, world, arrayList);
            if (vec3d1.y < -9.999995680744685E-5 && vec3d2.y > vec3d1.y + 9.999995999686725E-5) {
               return Optional.of(clientPlayerEntity.getEntityPos().add(e + vec3d2.x, f + vec3d2.y, g + vec3d2.z));
            }

            e += vec3d2.x;
            f += vec3d2.y;
            g += vec3d2.z;
            if (checkCondition_1614(world, box.offset(e, f, g))) {
               return Optional.of(clientPlayerEntity.getEntityPos().add(e, f, g));
            }

            if (Math.abs(vec3d2.x - vec3d1.x) > 9.999995999686725E-5) {
               h = 0.0;
            }

            if (Math.abs(vec3d2.y - vec3d1.y) > 9.999995999686725E-5) {
               j = 0.0;
            }

            if (Math.abs(vec3d2.z - vec3d1.z) > 9.999995999686725E-5) {
               k = 0.0;
            }

            vec3d = new Vec3d(h, j, k);
            if (vec3d.lengthSquared() < 1.0000000090069629E-12 && vec3d1.y >= -9.999995680744685E-5) {
               break;
            }
         }

         return Optional.empty();
      } else {
         return Optional.empty();
      }
   }

   public static boolean checkCondition_1613() {
      if (minecraft.world == null) {
         return false;
      }

      ClientPlayerEntity clientPlayerEntity = minecraft.player;
      if (clientPlayerEntity == null) {
         return false;
      }

      LivingEntityGravityInvoker livingEntityGravityInvoker = (LivingEntityGravityInvoker)clientPlayerEntity;
      EntityMovementInvoker entityMovementInvoker = (EntityMovementInvoker)clientPlayerEntity;
      double d = 0.0;
      Vec3d vec3d1 = clientPlayerEntity.getVelocity();

      for (int i = 0; i < 2; i++) {
         double e = (vec3d1.y - livingEntityGravityInvoker.getGravityInvoker()) * 0.9799995613681012;
         Vec3d vec3d2 = new Vec3d(0.0, e, 0.0);
         Box box = clientPlayerEntity.getBoundingBox().offset(0.0, d, 0.0);
         Vec3d vec3d;
         if (i == 0 && d == 0.0) {
            vec3d = entityMovementInvoker.getAdjustMovementForCollisions(vec3d2);
         } else {
            ArrayList arrayList = new ArrayList();
            EntityCollisionPredictionInvoker.findCollisionsForMovement(clientPlayerEntity, minecraft.world, arrayList, box);
            vec3d = EntityCollisionPredictionInvoker.adjustMovementForCollisions(clientPlayerEntity, vec3d2, box, minecraft.world, arrayList);
         }

         if (e < 0.0 && checkCondition_1611(minecraft.world, box.offset(vec3d.x, vec3d.y, vec3d.z))) {
            return true;
         }

         d += vec3d.y;
         vec3d1 = new Vec3d(vec3d1.x, vec3d.y, vec3d1.z);
      }

      return false;
   }

   private static boolean checkCondition_1614(World world, Box box) {
      for (BlockPos blockPos : BlockPos.iterate(BlockPos.ofFloored(box.minX, box.minY, box.minZ), BlockPos.ofFloored(box.maxX, box.maxY, box.maxZ))) {
         FluidState fluidState = world.getBlockState(blockPos).getFluidState();
         if (!fluidState.isEmpty() && (fluidState.isIn(FluidTags.WATER) || fluidState.isIn(FluidTags.LAVA))) {
            return true;
         }
      }

      return false;
   }

   private static boolean checkCondition_1611(World world, Box box) {
      BlockPos blockPos = BlockPos.ofFloored(box.minX, box.minY, box.minZ);
      BlockPos blockPos1 = BlockPos.ofFloored(box.maxX, box.maxY, box.maxZ);

      for (BlockPos blockPos2 : BlockPos.iterate(blockPos, blockPos1)) {
         BlockState blockState = world.getBlockState(blockPos2);
         if (!blockState.isOf(Blocks.COBWEB) && !blockState.isOf(Blocks.SWEET_BERRY_BUSH)) {
            FluidState fluidState = blockState.getFluidState();
            if (fluidState.isEmpty() || !fluidState.isIn(FluidTags.WATER) && !fluidState.isIn(FluidTags.LAVA)) {
               continue;
            }

            return true;
         }

         return true;
      }

      return false;
   }
}
