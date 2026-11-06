package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent076;
import aethereal.event.EventComponent091;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.ui.screen.ScreenComponent213;
import aethereal.ui.screen.ScreenComponent218;
import aethereal.util.UtilComponent268;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent277;
import aethereal.util.UtilComponent279;
import aethereal.util.UtilComponent280;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import platform.inject.accessors.ClientPlayerEntityAccessor;
import platform.inject.invokers.MinecraftClientInvoker;

@ModuleRegister(name = "Trigger Bot", description = "Automatically strikes when aiming at a target", category = Category.Combat)
public class TriggerBot extends Module {
   private int integerField1;
   boolean flagField2;
   public int integerField3;
   private int integerField4;
   private float floatValueField5;
   private LivingEntity livingEntityField6;
   private final MultiSelectSetting multiSelectSettingField7 = new MultiSelectSetting(
      "Targets to attack",
      new BooleanSetting("Players", true),
      new BooleanSetting("Animals", false),
      new BooleanSetting("Mobs", false),
      new BooleanSetting("Friends", true)
   );
   private final MultiSelectSetting multiSelectSettingField8 = new MultiSelectSetting(
      "Additional", new BooleanSetting("Critical hits only", true), new BooleanSetting("Adaptive strikes", true), new BooleanSetting("Random misses", true)
   );
   private final MultiSelectSetting multiSelectSettingField9 = new MultiSelectSetting(
      "Don't hit when",
      new BooleanSetting("Item used", true),
      new BooleanSetting("Container open", true),
      new BooleanSetting("The enemy is behind the wall", false)
   );
   private final ChoiceSetting choiceSettingField10 = new ChoiceSetting("Sprint reset", "Legitim", "Legitim", "Rage");
   private final ChoiceSetting choiceSettingField11 = new ChoiceSetting("Target selection", "Free", "Free", "Fixing");
   BooleanSetting booleanSettingField12 = new BooleanSetting("Pursuit of target", false);
   private final BooleanSetting targetIndicator = new BooleanSetting("Target indicator", true);
   private final UtilComponent268 utilComponent268Field13 = new UtilComponent268();
   boolean flagField14 = false;

   public int getIntegerField3_1653() {
      return this.integerField3;
   }

   public LivingEntity getLivingEntityField6_1669() {
      return this.livingEntityField6;
   }

   public boolean checkCondition_1659() {
      return this.targetIndicator.getCurrentValue();
   }

   public TriggerBot() {
      this.addSettings(
         this.multiSelectSettingField7,
         this.multiSelectSettingField8,
         this.multiSelectSettingField9,
         this.choiceSettingField10,
         this.choiceSettingField11,
         this.booleanSettingField12,
         this.targetIndicator
      );
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.livingEntityField6 = null;
      this.integerField1 = 0;
      this.integerField3 = 0;
      this.utilComponent268Field13.performAction_3524();
   }

   @EventTarget
   public void onEventComponent091_1658(EventComponent091 eventComponent091) {
      if (this.booleanSettingField12.getCurrentValue() && this.livingEntityField6 != null) {
         UtilComponent280.performAction_3592(eventComponent091, this.floatValueField5, 3);
      }

      if (this.livingEntityField6 != null) {
         Vec3d vec3d = CombatComponent165.computeValue_1560(minecraft.player.getEyePos(), this.livingEntityField6, 3.0, true);
         this.floatValueField5 = vec3d == Vec3d.ZERO
            ? UtilComponent277.getFloatValueField2_3570()
            : (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d.z, vec3d.x)) - 90.0);
      }

      if (this.choiceSettingField10.checkCondition_2850("Legitim") && this.integerField1 > 0) {
         eventComponent091.setForward_1065(0.0F);
         eventComponent091.setStrafe_1060(0.0F);
         this.integerField1--;
      }
   }

   @EventTarget
   public void onEventComponent074_1656(EventComponent074 eventComponent074) {
      this.performAction_1662();
   }

   @EventTarget
   public void onEventComponent076_1657(EventComponent076 eventComponent076) {
      this.flagField2 = eventComponent076.isWillLand_1008() && !minecraft.player.isOnGround();
   }

   private void performAction_1662() {
      this.integerField3++;
      this.performAction_1675();
      if (this.livingEntityField6 != null
         && this.multiSelectSettingField8.computeValue_2852("Random misses").getCurrentValue()
         && this.integerField3 >= 2
         && this.integerField4 >= 30
         && (Math.random() > 0.5 && this.integerField3 >= 1 || this.integerField3 == 4)
         && (
            !this.flagField14
               || !CombatComponent165.checkCondition_1551(minecraft.player.getYaw(), minecraft.player.getPitch(), 3.0, this.livingEntityField6, false)
         )) {
         ((MinecraftClientInvoker)minecraft).invokeDoAttack();
         this.flagField14 = !this.flagField14;
         this.integerField4 = (int)UtilComponent279.computeValue_3582(-10.0F, 10.0F);
      }

      if (this.livingEntityField6 != null && CombatComponent165.checkCondition_1553(this.integerField3, this.livingEntityField6, false)) {
         this.integerField1 = 1;
      }

      this.performAction_1663();
   }

   private void performAction_1663() {
      if (this.livingEntityField6 != null && this.checkCondition_1660()) {
         if (CombatComponent165.checkCondition_1551(
            minecraft.player.getYaw(),
            minecraft.player.getPitch(),
            3.0,
            this.livingEntityField6,
            !this.multiSelectSettingField9.computeValue_2852("The enemy is behind the wall").getCurrentValue()
         )) {
            boolean bl = false;
            if ((
                  NeneClient.getInstance().getProcessors().getModules().getMaceHelperField11_0410().flagField2
                     || minecraft.player.fallDistance > 2.0
                        && NeneClient.getInstance().getProcessors().getModules().getMaceHelperField11_0410().booleanSettingField4.getCurrentValue()
               )
               && UtilComponent270.computeValue_3526(Items.MACE) != -1) {
               if (minecraft.player.fallDistance < 1.5) {
                  return;
               }

               double d = CombatComponent166.computeValue_1615(minecraft.player, minecraft.world)
                  .map(vec3d -> vec3d.distanceTo(this.livingEntityField6.getEntityPos()))
                  .orElse(33.0);
               boolean bl1 = d > 2.0;
               if (!this.flagField2
                     && !CombatComponent166.checkCondition_1613()
                     && NeneClient.getInstance().getProcessors().getModules().getMaceHelperField11_0410().booleanSettingField3.getCurrentValue()
                     && !bl1
                  || !CombatComponent166.checkCondition_1612()
                  || minecraft.player.isGliding()) {
                  return;
               }

               bl = true;
            }

            if (bl || !this.checkCondition_1673()) {
               minecraft.interactionManager.attackEntity(minecraft.player, this.livingEntityField6);
               minecraft.player.swingHand(Hand.MAIN_HAND);
               if (Math.random() <= 0.89999974F) {
                  this.integerField4++;
               }

               this.integerField3 = 0;
            }
         }
      }
   }

   private void performAction_1675() {
      if (!this.choiceSettingField11.checkCondition_2850("Fixing")) {
         LivingEntity livingEntity = this.computeValue_1668(true);
         if (livingEntity != null) {
            this.livingEntityField6 = livingEntity;
            this.utilComponent268Field13.performAction_3524();
         } else if (this.livingEntityField6 != null && this.utilComponent268Field13.checkCondition_3522(1000L)) {
            this.livingEntityField6 = null;
         }
      } else if (!this.checkCondition_1672(this.livingEntityField6)
         || CombatComponent166.checkCondition_1612()
            && !this.flagField2
            && !minecraft.player.getItemCooldownManager().isCoolingDown(Items.MACE.getDefaultStack())) {
         this.livingEntityField6 = this.computeValue_1668(false);
      }
   }

   private boolean checkCondition_1672(LivingEntity livingEntity) {
      return livingEntity != null
         && livingEntity.isAlive()
         && !livingEntity.isSpectator()
         && livingEntity != minecraft.player
         && this.checkCondition_1652(livingEntity)
         && this.checkCondition_1661(livingEntity);
   }

   private boolean checkCondition_1652(LivingEntity livingEntity) {
      return NeneClient.getInstance().getProcessors().getModules().getElytraTargetField18_0443().isEnabled() && minecraft.player.isGliding()
         ? true
         : CombatComponent165.checkCondition_1555(
            livingEntity,
            4.0
               + minecraft.player.getVelocity().length() * 3.0
               + (UtilComponent270.computeValue_3526(Items.MACE) != -1 && !(minecraft.player.fallDistance <= 1.5) ? 1.5F : 0.0F)
               + (
                  NeneClient.getInstance().getProcessors().getModules().getMaceHelperField11_0410().isEnabled()
                        && UtilComponent270.computeValue_3526(Items.MACE) != -1
                        && CombatComponent166.computeValue_1615(minecraft.player, minecraft.world)
                           .map(vec3d -> minecraft.player.getY() - vec3d.getY() > 2.0)
                           .orElse(false)
                     ? 10
                     : 0
               )
         );
   }

   private boolean checkCondition_1671(LivingEntity livingEntity) {
      return Stream.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)
         .anyMatch(equipmentSlot -> livingEntity.getEquippedStack(equipmentSlot).contains(DataComponentTypes.EQUIPPABLE));
   }

   private boolean checkCondition_1673() {
      if (!((ClientPlayerEntityAccessor)minecraft.player).getWasSprinting()
         || minecraft.player.isTouchingWater()
         || minecraft.player.isInLava()
         || minecraft.player.isSwimming()
         || minecraft.player.isOnGround()) {
         return false;
      } else if (this.choiceSettingField10.checkCondition_2850("Rage")) {
         ((ClientPlayerEntityAccessor)minecraft.player).setWasSprinting(false);
         minecraft.player.setSprinting(false);
         minecraft.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraft.player, Mode.STOP_SPRINTING));
         this.integerField1 = 1;
         return false;
      } else {
         this.integerField1 = 1;
         return ((ClientPlayerEntityAccessor)minecraft.player).getWasSprinting();
      }
   }

   public boolean checkCondition_1660() {
      if (this.multiSelectSettingField9.computeValue_2852("Item used") != null
         && this.multiSelectSettingField9.computeValue_2852("Item used").getCurrentValue()
         && minecraft.player.isUsingItem()
         && minecraft.player.getItemUseTimeLeft() > 0
         && this.integerField3 >= 8) {
         this.integerField3 = 8;
         return false;
      }

      if ((
            this.multiSelectSettingField9.computeValue_2852("Container open") == null
               || !this.multiSelectSettingField9.computeValue_2852("Container open").getCurrentValue()
               || minecraft.currentScreen == null
               || minecraft.currentScreen instanceof ScreenComponent218
               || minecraft.currentScreen instanceof ScreenComponent213
         )
         && CombatComponent165.checkCondition_1556(this.livingEntityField6, 3.0)) {
         if (NeneClient.getInstance().getProcessors().getModules().getMaceHelperField11_0410().flagField2) {
            if (minecraft.player.getItemCooldownManager().isCoolingDown(minecraft.player.getMainHandStack())) {
               return false;
            }
         } else if (minecraft.player.fallDistance > 1.5) {
            if (minecraft.player.getItemCooldownManager().isCoolingDown(minecraft.player.getMainHandStack()) || this.integerField3 <= 3) {
               return false;
            }
         } else if (CombatComponent166.checkCondition_1612()) {
            if (minecraft.player.getItemCooldownManager().isCoolingDown(minecraft.player.getMainHandStack())
               || minecraft.player.getAttackCooldownProgress(0.5F) < 0.9F) {
               return false;
            }
         } else if (minecraft.player.getAttackCooldownProgress(0.5F) < 0.9F || this.integerField3 < 10) {
            return false;
         }

         return CombatComponent165.checkCondition_1550()
            || this.multiSelectSettingField8.computeValue_2852("Adaptive strikes").getCurrentValue()
               && minecraft.player.isOnGround()
               && !minecraft.player.input.playerInput.jump()
            || !CombatComponent165.checkCondition_1549();
      } else {
         return false;
      }
   }

   private LivingEntity computeValue_1668(boolean bl) {
      if (!bl) {
         return (this.multiSelectSettingField9.computeValue_2852("The enemy is behind the wall").getCurrentValue()
               ? this.computeValue_1650(false).or(() -> this.computeValue_1650(true))
               : this.computeValue_1650(true))
            .orElse(null);
      }

      float f = minecraft.player.getYaw();
      float g = minecraft.player.getPitch();
      return this.computeValue_1664()
         .filter(
            livingEntity -> CombatComponent165.checkCondition_1551(
               f, g, 3.0, livingEntity, !this.multiSelectSettingField9.computeValue_2852("The enemy is behind the wall").getCurrentValue()
            )
         )
         .min(Comparator.comparingDouble(livingEntity -> CombatComponent165.computeValue_1554(livingEntity)))
         .orElse(null);
   }

   private Optional<LivingEntity> computeValue_1650(boolean bl) {
      Vec3d vec3d = minecraft.player.getEyePos();
      Comparator comparator;
      if (CombatComponent166.checkCondition_1612()) {
         Vec3d vec3d1 = CombatComponent166.computeValue_1615(minecraft.player, minecraft.world).orElse(null);
         Vec3d vec3d2 = vec3d1 != null ? vec3d1.add(0.0, minecraft.player.getStandingEyeHeight(), 0.0) : null;
         comparator = Comparator.<LivingEntity, Boolean>comparing(
               livingEntity -> !CombatComponent165.checkCondition_1559(vec3d, livingEntity, 4.0)
                  && (vec3d2 == null || !CombatComponent165.checkCondition_1559(vec3d2, livingEntity, 4.0))
            )
            .thenComparing(livingEntity -> minecraft.player.fallDistance > 1.0 && !this.checkCondition_1671(livingEntity))
            .thenComparingDouble(livingEntity -> CombatComponent165.computeValue_1554(livingEntity));
      } else {
         comparator = Comparator.comparingDouble(
            livingEntity -> Math.acos(
               MathHelper.clamp(
                  Vec3d.fromPolar(minecraft.player.getPitch(), minecraft.player.getYaw())
                     .dotProduct(livingEntity.getBoundingBox().getCenter().subtract(vec3d).normalize()),
                  -1.0,
                  1.0
               )
            )
         );
      }

      Stream stream = this.computeValue_1664();
      if (!bl) {
         stream = stream.filter(livingEntity -> CombatComponent165.checkCondition_1559(vec3d, livingEntity, 4.0));
      }

      return stream.min(comparator);
   }

   private Stream<LivingEntity> computeValue_1664() {
      return StreamSupport.<Entity>stream(minecraft.world.getEntities().spliterator(), false)
         .filter(LivingEntity.class::isInstance)
         .map(LivingEntity.class::cast)
         .filter(this::checkCondition_1672);
   }

   private boolean checkCondition_1661(LivingEntity livingEntity) {
      if (!(livingEntity instanceof PlayerEntity playerEntity)) {
         if (livingEntity instanceof MobEntity) {
            return this.multiSelectSettingField7.computeValue_2852("Mobs").getCurrentValue();
         } else {
            return livingEntity instanceof AnimalEntity ? this.multiSelectSettingField7.computeValue_2852("Animals").getCurrentValue() : false;
         }
      } else {
         return this.multiSelectSettingField7.computeValue_2852("Players").getCurrentValue()
            && (
               this.multiSelectSettingField7.computeValue_2852("Friends").getCurrentValue()
                  || !NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(playerEntity.getName().getString())
            );
      }
   }
}
