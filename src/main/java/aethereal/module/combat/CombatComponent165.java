package aethereal.module.combat;

import aethereal.core.MinecraftAccess;
import aethereal.handler.RotationProcessor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class CombatComponent165 implements MinecraftAccess {
   private CombatComponent165() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static double computeValue_1558(Vec3d vec3d, Entity entity) {
      Box box = entity.getBoundingBox();
      double d = MathHelper.clamp(vec3d.x, box.minX, box.maxX);
      double e = MathHelper.clamp(vec3d.y, box.minY, box.maxY);
      double f = MathHelper.clamp(vec3d.z, box.minZ, box.maxZ);
      double g = d - vec3d.x;
      double h = e - vec3d.y;
      double i = f - vec3d.z;
      return g * g + h * h + i * i;
   }

   public static double computeValue_1554(Entity entity) {
      return minecraft.player == null ? Double.POSITIVE_INFINITY : computeValue_1558(minecraft.player.getEyePos(), entity);
   }

   public static boolean checkCondition_1555(Entity entity, double d) {
      return computeValue_1554(entity) <= d * d;
   }

   public static boolean checkCondition_1556(LivingEntity livingEntity, double d) {
      Vec3d vec3d = minecraft.player.getEyePos();
      Box box = livingEntity.getBoundingBox();
      double e = MathHelper.clamp(vec3d.x, box.minX, box.maxX);
      double f = MathHelper.clamp(vec3d.y, box.minY, box.maxY);
      double g = MathHelper.clamp(vec3d.z, box.minZ, box.maxZ);
      Vec3d vec3d1 = new Vec3d(e - vec3d.x, f - vec3d.y, g - vec3d.z);
      float h = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d1.z, vec3d1.x)) - 90.0);
      float i = (float)(-Math.toDegrees(Math.atan2(vec3d1.y, Math.hypot(vec3d1.x, vec3d1.z))));
      return checkCondition_1551(h, i, d, livingEntity, true);
   }

   public static boolean checkCondition_1551(float f, float g, double d, Entity entity, boolean bl) {
      return minecraft.player != null && minecraft.world != null ? checkCondition_1557(minecraft.player.getEyePos(), f, g, d, entity, bl) : false;
   }

   public static boolean checkCondition_1557(Vec3d vec3d, float f, float g, double d, Entity entity, boolean bl) {
      if (minecraft.player != null && minecraft.world != null) {
         Vec3d vec3d1 = Vec3d.fromPolar(g, f).multiply(d);
         Optional optional = entity.getBoundingBox().contains(vec3d) ? Optional.of(vec3d) : entity.getBoundingBox().raycast(vec3d, vec3d.add(vec3d1));
         return optional.isEmpty()
            ? false
            : bl
               || minecraft.world.raycast(new RaycastContext(vec3d, (Vec3d)optional.get(), ShapeType.OUTLINE, FluidHandling.NONE, minecraft.player)).getType()
                  == Type.MISS;
      } else {
         return false;
      }
   }

   public static boolean checkCondition_1559(Vec3d vec3d, LivingEntity livingEntity, double d) {
      Box box = livingEntity.getBoundingBox();
      double[] e = new double[]{0.0, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1.0};
      int i = e.length - 1;
      double f = d * d;

      for (int j = 0; j <= i; j++) {
         for (int k = 0; k <= i; k++) {
            for (int l = 0; l <= i; l++) {
               if (j <= 0 || j >= i || k <= 0 || k >= i || l <= 0 || l >= i) {
                  Vec3d vec3d1 = new Vec3d(
                     MathHelper.lerp(e[j], box.minX, box.maxX), MathHelper.lerp(e[k], box.minY, box.maxY), MathHelper.lerp(e[l], box.minZ, box.maxZ)
                  );
                  double g = vec3d.squaredDistanceTo(vec3d1);
                  if (!(g > f)) {
                     Vec3d vec3d2 = vec3d1.add(vec3d.subtract(vec3d1).multiply(0.05000000993895991 / Math.sqrt(g)));
                     if (minecraft.world.raycast(new RaycastContext(vec3d, vec3d2, ShapeType.OUTLINE, FluidHandling.NONE, minecraft.player)).getType()
                        == Type.MISS) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   public static boolean checkCondition_1553(int i, LivingEntity livingEntity, boolean bl) {
      return !bl && i >= 7 && checkCondition_1556(livingEntity, 3.0) && minecraft.player.getAttackCooldownProgress(0.5F) > 0.7F ? checkCondition_1548() : false;
   }

   public static boolean checkCondition_1548() {
      if (minecraft.player != null && minecraft.world != null) {
         double d = (minecraft.player.getVelocity().y - 0.08000000049877275) * 0.9799995837206814;
         if (d >= 0.0) {
            return false;
         }

         Box box = minecraft.player.getBoundingBox().offset(0.0, d, 0.0);
         Box box1 = new Box(box.minX, box.minY - 0.010000001417203743, box.minZ, box.maxX, box.minY, box.maxZ);
         return minecraft.world.isBlockSpaceEmpty(minecraft.player, box1);
      } else {
         return false;
      }
   }

   public static Vec3d computeValue_1560(Vec3d vec3d, LivingEntity livingEntity, double d, boolean bl) {
      Box box = livingEntity.getBoundingBox();
      boolean bl1 = CombatComponent166.checkCondition_1612();
      Vec3d vec3d1 = bl1 && minecraft.player != null ? vec3d.add(minecraft.player.getVelocity()) : vec3d;
      double e = (box.minX + box.maxX) * 0.5;
      double f = (box.minZ + box.maxZ) * 0.5;
      Vec3d vec3d2 = livingEntity.getEntityPos().add(0.0, livingEntity.getStandingEyeHeight(), 0.0);
      double g = vec3d1.distanceTo(vec3d2);
      Vec3d vec3d3 = vec3d1;
      if (bl1 && g > 3.0) {
         vec3d3 = new Vec3d(vec3d1.x, vec3d2.y, vec3d1.z);
      }

      double h = bl1 ? Math.min(g, 3.0) : g;
      double i = vec3d1.y;
      if (bl1 && g > 3.0) {
         i = vec3d2.y;
      }

      double j = MathHelper.lerp(MathHelper.clamp(h / 3.0, 0.0, 1.0), box.minY, MathHelper.clamp(i, box.minY, box.maxY));
      Vec3d vec3d4 = new Vec3d(e, j, f);
      ArrayList arrayList = new ArrayList();
      arrayList.add(vec3d4);
      double[] k = new double[]{0.0, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1.0};
      int l = k.length - 1;

      for (int m = 0; m < k.length; m++) {
         for (int n = 0; n < k.length; n++) {
            for (int o = 0; o < k.length; o++) {
               if (m == 0 || m == l || n == 0 || n == l || o == 0 || o == l) {
                  arrayList.add(
                     new Vec3d(MathHelper.lerp(k[m], box.minX, box.maxX), MathHelper.lerp(k[n], box.minY, box.maxY), MathHelper.lerp(k[o], box.minZ, box.maxZ))
                  );
               }
            }
         }
      }

      for (double p : new double[]{0.0, 0.20000001551382535}) {
         ArrayList arrayList1 = new ArrayList();

         for (Vec3d vec3d5 : arrayList) {
            Vec3d vec3d6 = vec3d5.subtract(vec3d3);
            double q = vec3d6.length();
            double s = d + p;
            if (bl1 || q <= s) {
               float u = (float)(bl1 ? q + p + 0.010000001417203743 : s);
               if (checkCondition_1557(
                  vec3d3,
                  (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d6.z, vec3d6.x)) - 90.0),
                  (float)(-Math.toDegrees(Math.atan2(vec3d6.y, Math.hypot(vec3d6.x, vec3d6.z)))),
                  u,
                  livingEntity,
                  false
               )) {
                  arrayList1.add(vec3d5);
               }
            }
         }

         if (!arrayList1.isEmpty()) {
            Vec3d vec3d7 = arrayList1.stream().reduce(Vec3d.ZERO, (vec3d11, vec3d12) -> vec3d11.add(vec3d12)).multiply(1.0 / arrayList1.size());
            return arrayList1.stream().min(Comparator.comparingDouble(vec3d12 -> vec3d12.squaredDistanceTo(vec3d7))).get().subtract(vec3d3);
         }

         if (bl) {
            ArrayList arrayList2 = new ArrayList();

            for (Vec3d vec3d9 : arrayList) {
               Vec3d vec3d10 = vec3d9.subtract(vec3d3);
               double r = vec3d10.length();
               double t = d + p;
               if (bl1 || r <= t) {
                  float v = (float)(bl1 ? r + p + 0.010000001417203743 : t);
                  if (checkCondition_1557(
                     vec3d3,
                     (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d10.z, vec3d10.x)) - 90.0),
                     (float)(-Math.toDegrees(Math.atan2(vec3d10.y, Math.hypot(vec3d10.x, vec3d10.z)))),
                     v,
                     livingEntity,
                     true
                  )) {
                     arrayList2.add(vec3d9);
                  }
               }
            }

            if (!arrayList2.isEmpty()) {
               Vec3d vec3d8 = arrayList2.stream().reduce(Vec3d.ZERO, (vec3d11, vec3d12) -> vec3d11.add(vec3d12)).multiply(1.0 / arrayList2.size());
               return arrayList2.stream().min(Comparator.comparingDouble(vec3d12 -> vec3d12.squaredDistanceTo(vec3d8))).get().subtract(vec3d3);
            }
         }
      }

      return Vec3d.ZERO;
   }

   public static boolean checkCondition_1549() {
      if (minecraft.player != null && minecraft.player.getEntityWorld() != null) {
         World world = minecraft.player.getEntityWorld();
         BlockPos blockPos = BlockPos.ofFloored(minecraft.player.getEyePos());
         FluidState fluidState = world.getFluidState(blockPos);
         return !minecraft.player.hasStatusEffect(StatusEffects.LEVITATION)
            && !minecraft.player.hasStatusEffect(StatusEffects.BLINDNESS)
            && !fluidState.isIn(FluidTags.WATER)
            && !fluidState.isIn(FluidTags.LAVA)
            && !minecraft.player.getAbilities().flying
            && !minecraft.player.isGliding()
            && !minecraft.player.isClimbing()
            && !minecraft.player.hasVehicle();
      } else {
         return false;
      }
   }

   public static boolean checkCondition_1550() {
      return minecraft.player != null && checkCondition_1549() && minecraft.player.fallDistance > 0.0 && !minecraft.player.isOnGround();
   }

   public static float computeValue_1552(float f, float g, float h) {
      float i = MathHelper.clamp(h, 0.0F, 1.0F);
      float j = MathHelper.wrapDegrees(g - f);
      if (Math.abs(j) < 0.5F) {
         return g;
      }

      float k = MathHelper.wrapDegrees(f + j * i);
      float l = RotationProcessor.computeValue_1137(f, k);
      float m = MathHelper.wrapDegrees(g - l);
      return Math.abs(m) < 0.5F ? g : l;
   }
}
