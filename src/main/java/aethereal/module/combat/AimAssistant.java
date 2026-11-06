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
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.MaceItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@ModuleRegister(name = "Aim Assistant", description = "Smoothly assists aim toward a valid nearby target", category = Category.Combat)
public class AimAssistant extends Module {
   private final MultiSelectSetting targets = new MultiSelectSetting(
      "Targets to aim",
      new BooleanSetting("Players", true),
      new BooleanSetting("Animals", false),
      new BooleanSetting("Mobs", false),
      new BooleanSetting("Friends", true)
   );
   private final BooleanSetting throughWalls = new BooleanSetting("Point behind the wall", false);
   private final FloatSetting turnSpeed = new FloatSetting("Threshold", 5.0F, 1.0F, 5.0F, 0.25F);
   private final BooleanSetting onlyWithWeapons = new BooleanSetting("Only with weapons", true);
   private final FloatSetting range = new FloatSetting("Range", 4.0F, 2.0F, 6.0F, 0.25F);
   private final FloatSetting maxAngle = new FloatSetting("Max angle", 35.0F, 5.0F, 90.0F, 5.0F);
   private final FloatSetting smoothing = new FloatSetting("Smoothing", 0.2F, 0.05F, 0.6F, 0.05F);
   private LivingEntity target;
   private Vec3d smoothedAim;

   public AimAssistant() {
      this.addSettings(this.targets, this.throughWalls, this.turnSpeed, this.onlyWithWeapons, this.range, this.maxAngle, this.smoothing);
   }

   public LivingEntity getTarget_1472() {
      return this.target;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.target = null;
      this.smoothedAim = null;
   }

   @EventTarget
   public void onEventComponent074_1475(EventComponent074 eventComponent074) {
      if (minecraft.player != null && minecraft.world != null && minecraft.currentScreen == null) {
         LivingEntity livingEntity = this.computeValue_1474();
         if (!this.checkCondition_1480(livingEntity)) {
            livingEntity = this.computeValue_1482();
         }

         if (this.checkCondition_1480(this.target)
            && livingEntity != null
            && livingEntity != this.target
            && this.computeValue_1479(this.target) <= this.computeValue_1479(livingEntity) + 6.0) {
            livingEntity = this.target;
         }

         if (livingEntity != this.target) {
            this.smoothedAim = null;
         }

         this.target = livingEntity;
         if (this.target == null) {
            this.smoothedAim = null;
         }
      } else {
         this.performAction_1478();
      }
   }

   @EventTarget
   public void onEventComponent094_1476(EventComponent094 eventComponent094) {
      if (this.checkCondition_1480(this.target) && minecraft.currentScreen == null && (!this.onlyWithWeapons.getCurrentValue() || this.checkCondition_1477())) {
         Vec3d vec3d = CombatComponent165.computeValue_1560(
            minecraft.player.getEyePos(), this.target, this.range.getCurrentValue().doubleValue(), this.throughWalls.getCurrentValue()
         );
         if (vec3d != null && !vec3d.equals(Vec3d.ZERO)) {
            float f = MathHelper.clamp(this.smoothing.getCurrentValue(), 0.05F, 0.6F);
            this.smoothedAim = this.smoothedAim == null ? vec3d : this.smoothedAim.lerp(vec3d, f);
            float g = (float)MathHelper.wrapDegrees(
               Math.toDegrees(Math.atan2(this.smoothedAim.z - minecraft.player.getZ(), this.smoothedAim.x - minecraft.player.getX())) - 90.0
            );
            float h = (float)(
               -Math.toDegrees(
                  Math.atan2(
                     this.smoothedAim.y - minecraft.player.getEyeY(),
                     Math.hypot(this.smoothedAim.x - minecraft.player.getX(), this.smoothedAim.z - minecraft.player.getZ())
                  )
               )
            );
            float i = MathHelper.wrapDegrees(g - minecraft.player.getYaw());
            float j = h - minecraft.player.getPitch();
            float k = (float)Math.hypot(i, j);
            if (!(k < 0.05F)) {
               float l = MathHelper.clamp(minecraft.getRenderTickCounter().getDynamicDeltaTicks(), 0.0F, 2.0F);
               float m = this.turnSpeed.getCurrentValue() * l;
               float n = Math.min(1.0F, m / k);
               minecraft.player.setYaw(minecraft.player.getYaw() + i * n);
               minecraft.player.setPitch(MathHelper.clamp(minecraft.player.getPitch() + j * n, -90.0F, 90.0F));
            }
         }
      } else {
         this.smoothedAim = null;
      }
   }

   private LivingEntity computeValue_1474() {
      if (NeneClient.getInstance() != null && NeneClient.getInstance().getProcessors() != null && NeneClient.getInstance().getProcessors().getModules() != null
         )
       {
         TriggerBot triggerBot = NeneClient.getInstance().getProcessors().getModules().getTriggerBotField4_0418();
         return triggerBot != null && triggerBot.isEnabled() ? triggerBot.getLivingEntityField6_1669() : null;
      } else {
         return null;
      }
   }

   private LivingEntity computeValue_1482() {
      return StreamSupport.<Entity>stream(minecraft.world.getEntities().spliterator(), false)
         .filter(LivingEntity.class::isInstance)
         .map(LivingEntity.class::cast)
         .filter(this::checkCondition_1480)
         .min(Comparator.comparingDouble(this::computeValue_1479))
         .orElse(null);
   }

   private boolean checkCondition_1480(LivingEntity livingEntity) {
      if (livingEntity != null
         && minecraft.player != null
         && minecraft.world != null
         && livingEntity.isAlive()
         && !livingEntity.isRemoved()
         && livingEntity != minecraft.player
         && this.checkCondition_1473(livingEntity)) {
         double d = this.range.getCurrentValue().doubleValue();
         if (!CombatComponent165.checkCondition_1556(livingEntity, d)) {
            return false;
         }

         Vec3d vec3d = minecraft.player.getEyePos();
         Vec3d vec3d1 = Vec3d.fromPolar(minecraft.player.getPitch(), minecraft.player.getYaw());
         if (this.computeValue_1481(livingEntity, vec3d, vec3d1) > this.maxAngle.getCurrentValue().doubleValue()) {
            return false;
         }

         if (this.throughWalls.getCurrentValue()) {
            return true;
         }

         Vec3d vec3d2 = CombatComponent165.computeValue_1560(vec3d, livingEntity, d, false);
         return vec3d2 != null && !vec3d2.equals(Vec3d.ZERO);
      } else {
         return false;
      }
   }

   private boolean checkCondition_1473(LivingEntity livingEntity) {
      if (livingEntity instanceof PlayerEntity playerEntity) {
         boolean bl = this.targets.computeValue_2852("Players").getCurrentValue();
         boolean bl1 = this.targets.computeValue_2852("Friends").getCurrentValue();
         boolean bl2 = NeneClient.getInstance() != null
            && NeneClient.getInstance().getProcessors() != null
            && NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(playerEntity.getName().getString());
         return bl && (bl1 || !bl2);
      } else {
         return livingEntity instanceof MobEntity
            ? this.targets.computeValue_2852("Mobs").getCurrentValue()
            : livingEntity instanceof AnimalEntity && this.targets.computeValue_2852("Animals").getCurrentValue();
      }
   }

   private double computeValue_1479(LivingEntity livingEntity) {
      Vec3d vec3d = minecraft.player.getEyePos();
      Vec3d vec3d1 = Vec3d.fromPolar(minecraft.player.getPitch(), minecraft.player.getYaw());
      double d = this.computeValue_1481(livingEntity, vec3d, vec3d1);
      double e = Math.sqrt(CombatComponent165.computeValue_1558(vec3d, livingEntity));
      return d + e * 0.15;
   }

   private double computeValue_1481(LivingEntity livingEntity, Vec3d vec3d, Vec3d vec3d1) {
      Vec3d vec3d2 = livingEntity.getBoundingBox().getCenter().subtract(vec3d).normalize();
      double d = MathHelper.clamp(vec3d1.dotProduct(vec3d2), -1.0, 1.0);
      return Math.toDegrees(Math.acos(d));
   }

   private boolean checkCondition_1477() {
      Item item = minecraft.player.getMainHandStack().getItem();
      return item.getRegistryEntry().isIn(ItemTags.SWORDS) || item instanceof AxeItem || item instanceof MaceItem;
   }

   private void performAction_1478() {
      this.target = null;
      this.smoothedAim = null;
   }
}
