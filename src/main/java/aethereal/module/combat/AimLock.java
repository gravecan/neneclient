package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent094;
import aethereal.setting.BooleanSetting;
import aethereal.setting.FloatSetting;
import aethereal.setting.MultiSelectSetting;
import java.util.Comparator;
import java.util.stream.StreamSupport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@ModuleRegister(name = "Aim Lock", description = "Locks camera rotation onto the selected target", category = Category.Combat)
public final class AimLock extends Module {
   private final MultiSelectSetting targets = new MultiSelectSetting(
      "Targets",
      new BooleanSetting("Players", true),
      new BooleanSetting("Animals", false),
      new BooleanSetting("Mobs", false),
      new BooleanSetting("Include friends", false)
   );
   private final BooleanSetting throughWalls = new BooleanSetting("Through walls", false);
   private final BooleanSetting weaponOnly = new BooleanSetting("Only with weapons", false);
   private final FloatSetting range = new FloatSetting("Range", 4.0F, 1.0F, 6.0F, 0.25F);
   private final FloatSetting acquisitionAngle = new FloatSetting("Lock angle", 75.0F, 5.0F, 180.0F, 5.0F);
   private final FloatSetting lockSpeed = new FloatSetting("Lock speed", 360.0F, 30.0F, 1080.0F, 30.0F);
   private final FloatSetting aimHeight = new FloatSetting("Hitbox height", 0.5F, 0.1F, 0.9F, 0.05F);
   private final FloatSetting prediction = new FloatSetting("Prediction", 1.5F, 0.0F, 4.0F, 0.5F);
   private LivingEntity lockedTarget;
   private Vec3d smoothedPoint;

   public AimLock() {
      this.addSettings(this.targets, this.throughWalls, this.weaponOnly, this.range, this.acquisitionAngle, this.lockSpeed, this.aimHeight, this.prediction);
   }

   public LivingEntity getLockedTarget_1489() {
      return this.lockedTarget;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.performAction_1493();
   }

   @EventTarget
   public void onTick(EventComponent074 eventComponent074) {
      if (minecraft.player == null || minecraft.world == null || minecraft.currentScreen != null) {
         this.performAction_1493();
      } else if (!this.checkCondition_1490(this.lockedTarget)) {
         this.performAction_1493();
         Vec3d vec3d = minecraft.player.getEyePos();
         Vec3d vec3d1 = Vec3d.fromPolar(minecraft.player.getPitch(), minecraft.player.getYaw());
         this.lockedTarget = StreamSupport.<Entity>stream(minecraft.world.getEntities().spliterator(), false)
            .filter(LivingEntity.class::isInstance)
            .map(LivingEntity.class::cast)
            .filter(this::checkCondition_1490)
            .filter(livingEntity -> this.computeValue_1488(livingEntity, vec3d, vec3d1) <= this.acquisitionAngle.getCurrentValue().doubleValue())
            .min(Comparator.comparingDouble(livingEntity -> this.computeValue_1491(livingEntity, vec3d, vec3d1)))
            .orElse(null);
      }
   }

   @EventTarget
   public void onLook(EventComponent094 eventComponent094) {
      if (minecraft.player != null
         && minecraft.world != null
         && minecraft.currentScreen == null
         && this.checkCondition_1490(this.lockedTarget)
         && (!this.weaponOnly.getCurrentValue() || this.checkCondition_1492())) {
         Vec3d vec3d = this.computeValue_1494(this.lockedTarget);
         if (!this.throughWalls.getCurrentValue() && this.checkCondition_1496(vec3d)) {
            this.smoothedPoint = null;
         } else {
            float f = 0.35F;
            this.smoothedPoint = this.smoothedPoint == null ? vec3d : this.smoothedPoint.lerp(vec3d, f);
            this.smoothedPoint = this.computeValue_1495(this.lockedTarget, this.smoothedPoint);
            double d = this.smoothedPoint.x - minecraft.player.getX();
            double e = this.smoothedPoint.y - minecraft.player.getEyeY();
            double g = this.smoothedPoint.z - minecraft.player.getZ();
            float h = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(g, d)) - 90.0);
            float i = (float)(-Math.toDegrees(Math.atan2(e, Math.hypot(d, g))));
            float j = MathHelper.wrapDegrees(h - minecraft.player.getYaw());
            float k = i - minecraft.player.getPitch();
            float l = (float)Math.hypot(j, k);
            if (!(l < 0.03F)) {
               float m = MathHelper.clamp(minecraft.getRenderTickCounter().getDynamicDeltaTicks(), 0.0F, 2.0F);
               float n = this.lockSpeed.getCurrentValue() * m / 20.0F;
               float o = Math.min(1.0F, n / l);
               minecraft.player.setYaw(minecraft.player.getYaw() + j * o);
               minecraft.player.setPitch(MathHelper.clamp(minecraft.player.getPitch() + k * o, -90.0F, 90.0F));
            }
         }
      } else {
         this.smoothedPoint = null;
      }
   }

   private boolean checkCondition_1490(LivingEntity livingEntity) {
      if (livingEntity == null
         || minecraft.player == null
         || minecraft.world == null
         || !livingEntity.isAlive()
         || livingEntity.isRemoved()
         || livingEntity == minecraft.player
         || !this.checkCondition_1487(livingEntity)
         || !CombatComponent165.checkCondition_1556(livingEntity, this.range.getCurrentValue().doubleValue())) {
         return false;
      } else {
         return this.throughWalls.getCurrentValue() ? true : !this.checkCondition_1496(this.computeValue_1494(livingEntity));
      }
   }

   private boolean checkCondition_1487(LivingEntity livingEntity) {
      if (livingEntity instanceof PlayerEntity playerEntity) {
         if (!this.targets.computeValue_2852("Players").getCurrentValue()) {
            return false;
         } else {
            return !this.targets.computeValue_2852("Include friends").getCurrentValue()
                  && NeneClient.getInstance() != null
                  && NeneClient.getInstance().getProcessors() != null
                  && NeneClient.getInstance().getProcessors().getFriends() != null
               ? !NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(playerEntity.getName().getString())
               : true;
         }
      } else {
         return livingEntity instanceof MobEntity
            ? this.targets.computeValue_2852("Mobs").getCurrentValue()
            : livingEntity instanceof AnimalEntity && this.targets.computeValue_2852("Animals").getCurrentValue();
      }
   }

   private Vec3d computeValue_1494(LivingEntity livingEntity) {
      Box box = livingEntity.getBoundingBox();
      Vec3d vec3d = livingEntity.getVelocity().multiply(this.prediction.getCurrentValue().doubleValue());
      double d = Math.min(0.025, (box.maxX - box.minX) * 0.1);
      double e = Math.min(0.025, (box.maxY - box.minY) * 0.1);
      double f = Math.min(0.025, (box.maxZ - box.minZ) * 0.1);
      Vec3d vec3d1 = new Vec3d(
         MathHelper.clamp((box.minX + box.maxX) * 0.5 + vec3d.x, box.minX + d, box.maxX - d),
         MathHelper.clamp(MathHelper.lerp(this.aimHeight.getCurrentValue().doubleValue(), box.minY, box.maxY), box.minY + e, box.maxY - e),
         MathHelper.clamp((box.minZ + box.maxZ) * 0.5 + vec3d.z, box.minZ + f, box.maxZ - f)
      );
      return this.computeValue_1495(livingEntity, vec3d1);
   }

   private Vec3d computeValue_1495(LivingEntity livingEntity, Vec3d vec3d) {
      Box box = livingEntity.getBoundingBox();
      return new Vec3d(
         MathHelper.clamp(vec3d.x, box.minX, box.maxX), MathHelper.clamp(vec3d.y, box.minY, box.maxY), MathHelper.clamp(vec3d.z, box.minZ, box.maxZ)
      );
   }

   private boolean checkCondition_1496(Vec3d vec3d) {
      BlockHitResult blockHitResult = minecraft.world
         .raycast(new RaycastContext(minecraft.player.getEyePos(), vec3d, ShapeType.OUTLINE, FluidHandling.NONE, minecraft.player));
      return blockHitResult.getType() != Type.MISS;
   }

   private double computeValue_1488(LivingEntity livingEntity, Vec3d vec3d, Vec3d vec3d1) {
      Vec3d vec3d2 = livingEntity.getBoundingBox().getCenter().subtract(vec3d).normalize();
      return Math.toDegrees(Math.acos(MathHelper.clamp(vec3d1.dotProduct(vec3d2), -1.0, 1.0)));
   }

   private double computeValue_1491(LivingEntity livingEntity, Vec3d vec3d, Vec3d vec3d1) {
      return this.computeValue_1488(livingEntity, vec3d, vec3d1) + Math.sqrt(CombatComponent165.computeValue_1558(vec3d, livingEntity)) * 0.15;
   }

   private boolean checkCondition_1492() {
      return minecraft.player.getMainHandStack().isIn(ItemTags.SWORDS)
         || minecraft.player.getMainHandStack().isIn(ItemTags.AXES)
         || minecraft.player.getMainHandStack().isOf(Items.MACE);
   }

   private void performAction_1493() {
      this.lockedTarget = null;
      this.smoothedPoint = null;
   }
}
