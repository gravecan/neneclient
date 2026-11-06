package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.ClientTickEvent;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent076;
import aethereal.event.EventComponent091;
import aethereal.handler.HandlerComponent108;
import aethereal.lib.log4j.Log4jComponent146;
import aethereal.lib.log4j.Log4jComponent147;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.FloatSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.ui.screen.ScreenComponent213;
import aethereal.ui.screen.ScreenComponent218;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent277;
import aethereal.util.UtilComponent279;
import aethereal.util.UtilComponent280;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import platform.inject.accessors.ClientPlayerEntityAccessor;

@ModuleRegister(name = "Aura", description = "Automatically attacks targets near you", category = Category.Combat)
public class Aura extends Module {
   private static final Log4jComponent147 log4jComponent147Field1 = Log4jComponent146.computeValue_1319(Aura.class);
   private LivingEntity livingEntityField2;
   boolean flagField3;
   private final ChoiceSetting choiceSettingField4 = new ChoiceSetting("Select hover type", "FanTime", "FanTime", "FanTime FOV", "Legit");
   private final MultiSelectSetting multiSelectSettingField5 = new MultiSelectSetting(
      "Targets to attack",
      new BooleanSetting("Without reservation", true),
      new BooleanSetting("Hostile mobs", false),
      new BooleanSetting("Animals", false),
      new BooleanSetting("Friends", false),
      new BooleanSetting("Players", true)
   );
   private final FloatSetting floatSettingField6 = new FloatSetting("Attack distance", 3.0F, 0.1F, 6.0F, 0.1F);
   private final FloatSetting floatSettingField7 = new FloatSetting("Additional distance", 0.5F, 0.1F, 3.0F, 0.1F);
   private final BooleanSetting booleanSettingField8 = new BooleanSetting("Critical hits only", true);
   private final BooleanSetting booleanSettingField9 = new BooleanSetting("Adaptive strikes", true)
      .withVisibility(() -> this.booleanSettingField8.getCurrentValue());
   private final MultiSelectSetting multiSelectSettingField10 = new MultiSelectSetting(
      "Don't hit when",
      new BooleanSetting("Item used", true),
      new BooleanSetting("Container open", true),
      new BooleanSetting("The enemy is behind the wall", true)
   );
   private final BooleanSetting booleanSettingField11 = new BooleanSetting("Shield Breaking", true);
   private final BooleanSetting booleanSettingField12 = new BooleanSetting("Smart Sprint", false);
   private final ChoiceSetting choiceSettingField13 = new ChoiceSetting("Target priority", "Aim", "Aim", "Distance", "HP");
   private final ChoiceSetting choiceSettingField14 = new ChoiceSetting("Motion correction", "Focus", "Focus", "Free");
   private final ChoiceSetting choiceSettingField15 = new ChoiceSetting("Target Visualization", "Spheres", "Spheres", "Circle");
   private final BooleanSetting targetIndicator = new BooleanSetting("Target indicator", true);
   public int integerField16 = 0;
   float[] floatValueArrayField17 = new float[]{-1.0F, -1.0F, -1.0F, -1.0F, 0.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F};
   private final float[] floatValueArrayField18 = new float[30];
   int[] integerArrayField19 = new int[]{-1, -1};
   boolean flagField20 = false;

   public ChoiceSetting getChoiceSettingField15_1517() {
      return this.choiceSettingField15;
   }

   public LivingEntity getLivingEntityField2_1528() {
      return this.livingEntityField2;
   }

   public boolean checkCondition_1512() {
      return this.targetIndicator.getCurrentValue();
   }

   public Aura() {
      this.addSettings(
         this.floatSettingField6,
         this.floatSettingField7,
         this.choiceSettingField4,
         this.choiceSettingField14,
         this.choiceSettingField15,
         this.choiceSettingField13,
         this.multiSelectSettingField5,
         this.multiSelectSettingField10,
         this.booleanSettingField8,
         this.booleanSettingField9,
         this.booleanSettingField11,
         this.booleanSettingField12,
         this.targetIndicator
      );
   }

   @Override
   public void onEnable() {
      if (this.floatValueArrayField17[9] == -1.0F) {
         this.floatValueArrayField17[9] = (int)UtilComponent279.computeValue_3582(9.0F, 13.0F);
      }

      super.onEnable();
      this.floatValueArrayField17[8] = 2.0F;
      this.floatValueArrayField17[10] = 0.0F;
      this.floatValueArrayField17[11] = 0.0F;
      Arrays.fill(this.floatValueArrayField18, minecraft.player != null ? minecraft.player.getPitch() : 0.0F);
      this.livingEntityField2 = null;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.floatValueArrayField17[10] = 0.0F;
      this.livingEntityField2 = null;
   }

   @EventTarget
   public void onEventComponent091_1511(EventComponent091 eventComponent091) {
      if (this.livingEntityField2 != null) {
         float f = UtilComponent277.getFloatValueField2_3570();
         if (this.choiceSettingField14.checkCondition_2850("Focus")) {
            f = (float)MathHelper.wrapDegrees(
               Math.toDegrees(Math.atan2(this.livingEntityField2.getZ() - minecraft.player.getZ(), this.livingEntityField2.getX() - minecraft.player.getX()))
                  - 90.0
            );
         }

         UtilComponent280.performAction_3592(eventComponent091, f, 2);
      }

      if (this.floatValueArrayField17[0] > 0.0F
         && this.livingEntityField2 != null
         && CombatComponent165.checkCondition_1556(this.livingEntityField2, this.floatSettingField6.getCurrentValue().floatValue())) {
         eventComponent091.setForward_1065(0.0F);
         eventComponent091.setStrafe_1060(0.0F);
         float[] g = this.floatValueArrayField17;
         g[0]--;
      }
   }

   @EventTarget
   public void performAction_1509(ClientTickEvent clientTickEvent) {
      if (this.livingEntityField2 == null
         || !this.checkCondition_1507(this.livingEntityField2)
         || CombatComponent166.checkCondition_1612()
            && !this.flagField3
            && !minecraft.player.getItemCooldownManager().isCoolingDown(Items.MACE.getDefaultStack())) {
         LivingEntity livingEntity = this.livingEntityField2;
         boolean bl = livingEntity == null || !this.checkCondition_1507(livingEntity);
         this.livingEntityField2 = bl && this.multiSelectSettingField10.computeValue_2852("The enemy is behind the wall").getCurrentValue()
            ? this.computeValue_1527(false).or(() -> this.computeValue_1527(true)).orElse(null)
            : this.computeValue_1514().orElse(null);
         if (this.livingEntityField2 != livingEntity && this.livingEntityField2 != null) {
            this.floatValueArrayField17[10] = 0.0F;
            this.floatValueArrayField17[11] = 0.0F;
            Arrays.fill(this.floatValueArrayField18, minecraft.player != null ? minecraft.player.getPitch() : 0.0F);
         }
      }

      this.performAction_1515();
      if (this.livingEntityField2 != null) {
         this.performAction_1533();
         this.performAction_1516();
      } else {
         this.floatValueArrayField17[8] = 1.0F;
      }
   }

   @EventTarget
   public void onEventComponent076_1510(EventComponent076 eventComponent076) {
      this.flagField3 = eventComponent076.isWillLand_1008() && !minecraft.player.isOnGround();
   }

   private int computeValue_1519(int i, int j) {
      for (int k = i; k < j; k++) {
         if (minecraft.player.getInventory().getStack(k).getItem() instanceof AxeItem) {
            return k;
         }
      }

      return -1;
   }

   private void performAction_1515() {
      if (!this.booleanSettingField11.getCurrentValue() || this.livingEntityField2 == null || !this.livingEntityField2.isBlocking()) {
         if (this.integerArrayField19[0] != -1) {
            minecraft.player.getInventory().setSelectedSlot(this.integerArrayField19[0]);
            this.integerArrayField19[0] = -1;
         }

         if (this.integerArrayField19[1] != -1 && HandlerComponent108.getINVENTORY_1158().getListField1_1177().isEmpty()) {
            HandlerComponent108.getINVENTORY_1158().performAction_1179(minecraft.player.getInventory().getSelectedSlot(), this.integerArrayField19[1], 1);
            this.integerArrayField19[1] = -1;
         }
      }
   }

   private void performAction_1516() {
      if (this.livingEntityField2 != null
         && this.livingEntityField2.isAlive()
         && !this.livingEntityField2.isRemoved()
         && CombatComponent165.checkCondition_1551(
            minecraft.player.getYaw(),
            minecraft.player.getPitch(),
            this.computeValue_1530(),
            this.livingEntityField2,
            !this.multiSelectSettingField10.computeValue_2852("The enemy is behind the wall").getCurrentValue()
         )) {
         if (this.booleanSettingField11.getCurrentValue() && this.livingEntityField2.isBlocking()) {
            if (minecraft.player.getMainHandStack().getItem() instanceof AxeItem) {
               minecraft.interactionManager.attackEntity(minecraft.player, this.livingEntityField2);
               minecraft.player.swingHand(Hand.MAIN_HAND);
            }

            int i = this.computeValue_1519(0, 9);
            if (i != -1) {
               if (minecraft.player.getInventory().getSelectedSlot() != i) {
                  if (this.integerArrayField19[0] == -1) {
                     this.integerArrayField19[0] = minecraft.player.getInventory().getSelectedSlot();
                  }

                  minecraft.player.getInventory().setSelectedSlot(i);
               }
            } else {
               int j = this.computeValue_1519(9, 36);
               if (j != -1 && this.integerArrayField19[1] == -1 && HandlerComponent108.getINVENTORY_1158().getListField1_1177().isEmpty()) {
                  this.integerArrayField19[1] = j;
                  HandlerComponent108.getINVENTORY_1158().performAction_1179(j, minecraft.player.getInventory().getSelectedSlot(), 1);
               }
            }
         }

         if (this.checkCondition_1513()) {
            boolean bl1 = false;
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
                  .map(vec3d -> vec3d.distanceTo(this.livingEntityField2.getEntityPos()))
                  .orElse(33.0);
               boolean bl = d > 2.0;
               if (!this.flagField3
                     && !CombatComponent166.checkCondition_1613()
                     && NeneClient.getInstance().getProcessors().getModules().getMaceHelperField11_0410().booleanSettingField3.getCurrentValue()
                     && !bl
                  || !CombatComponent166.checkCondition_1612()
                  || minecraft.player.isGliding()) {
                  return;
               }

               bl1 = true;
            }

            if (((ClientPlayerEntityAccessor)minecraft.player).getWasSprinting()
               && !minecraft.player.isTouchingWater()
               && !minecraft.player.isInLava()
               && !minecraft.player.isSwimming()
               && !minecraft.player.isOnGround()
               && !bl1) {
               if (!this.booleanSettingField12.getCurrentValue()) {
                  ((ClientPlayerEntityAccessor)minecraft.player).setWasSprinting(false);
                  minecraft.player.setSprinting(false);
                  minecraft.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraft.player, Mode.STOP_SPRINTING));
                  this.floatValueArrayField17[0] = 1.0F;
               } else {
                  this.floatValueArrayField17[0] = 1.0F;
                  if (((ClientPlayerEntityAccessor)minecraft.player).getWasSprinting()) {
                     return;
                  }
               }
            }

            if (minecraft.interactionManager != null) {
               this.floatValueArrayField17[3] = 0.0F;
               minecraft.interactionManager.attackEntity(minecraft.player, this.livingEntityField2);
               minecraft.player.swingHand(Hand.MAIN_HAND);
               this.integerField16 = 0;
               this.floatValueArrayField17[5] = UtilComponent279.computeValue_3582(8.0F, 10.0F);
               this.floatValueArrayField17[9] = (int)UtilComponent279.computeValue_3582(9.0F, 13.0F);
               if (this.floatValueArrayField17[2] == -1.0F) {
                  this.floatValueArrayField17[4] = (int)UtilComponent279.computeValue_3582(30.0F, 35.0F);
               }

               float[] f = this.floatValueArrayField17;
               f[2]++;
            }
         }
      }
   }

   public boolean checkCondition_1513() {
      if (this.multiSelectSettingField10.computeValue_2852("Item used") != null
         && this.multiSelectSettingField10.computeValue_2852("Item used").getCurrentValue()
         && minecraft.player.isUsingItem()
         && minecraft.player.getItemUseTimeLeft() > 0
         && this.integerField16 >= 8) {
         this.integerField16 = 8;
         return false;
      }

      if ((
            this.multiSelectSettingField10.computeValue_2852("Container open") == null
               || !this.multiSelectSettingField10.computeValue_2852("Container open").getCurrentValue()
               || minecraft.currentScreen == null
               || minecraft.currentScreen instanceof ScreenComponent218
               || minecraft.currentScreen instanceof ScreenComponent213
         )
         && CombatComponent165.checkCondition_1556(this.livingEntityField2, this.computeValue_1530())) {
         if (NeneClient.getInstance().getProcessors().getModules().getMaceHelperField11_0410().flagField2) {
            if (minecraft.player.getItemCooldownManager().isCoolingDown(minecraft.player.getMainHandStack())) {
               return false;
            }
         } else if (minecraft.player.fallDistance > 1.5) {
            if (minecraft.player.getItemCooldownManager().isCoolingDown(minecraft.player.getMainHandStack()) || this.integerField16 <= 3) {
               return false;
            }
         } else if (CombatComponent166.checkCondition_1612()) {
            if (minecraft.player.getItemCooldownManager().isCoolingDown(minecraft.player.getMainHandStack())
               || minecraft.player.getAttackCooldownProgress(0.5F) < 0.9F) {
               return false;
            }
         } else if (minecraft.player.getAttackCooldownProgress(0.5F) < 0.9F || this.integerField16 < 10) {
            return false;
         }

         return CombatComponent165.checkCondition_1550()
            || this.booleanSettingField9.getCurrentValue() && minecraft.player.isOnGround() && !minecraft.player.input.playerInput.jump()
            || !CombatComponent165.checkCondition_1549();
      } else {
         return false;
      }
   }

   private float computeValue_1530() {
      return Math.min(this.floatSettingField6.getCurrentValue(), 2.9F);
   }

   private boolean checkCondition_1532(LivingEntity livingEntity) {
      return NeneClient.getInstance().getProcessors().getModules().getElytraTargetField18_0443().isEnabled() && minecraft.player.isGliding()
         ? true
         : CombatComponent165.checkCondition_1555(
            livingEntity,
            this.floatSettingField6.getCurrentValue() + this.floatSettingField7.getCurrentValue()
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

   private Optional<LivingEntity> computeValue_1514() {
      return this.computeValue_1527(true);
   }

   private Optional<LivingEntity> computeValue_1527(boolean bl) {
      if (minecraft.world != null && minecraft.player != null) {
         Vec3d vec3d = minecraft.player.getEyePos();
         double d = this.floatSettingField6.getCurrentValue() + this.floatSettingField7.getCurrentValue();
         Comparator comparator;
         if (CombatComponent166.checkCondition_1612()) {
            Vec3d vec3d1 = CombatComponent166.computeValue_1615(minecraft.player, minecraft.world).orElse(null);
            Vec3d vec3d2 = vec3d1 != null ? vec3d1.add(0.0, minecraft.player.getStandingEyeHeight(), 0.0) : null;
            comparator = Comparator.<LivingEntity, Boolean>comparing(
                  livingEntity -> !CombatComponent165.checkCondition_1559(vec3d, livingEntity, d)
                     && (vec3d2 == null || !CombatComponent165.checkCondition_1559(vec3d2, livingEntity, d))
               )
               .thenComparing(livingEntity -> minecraft.player.fallDistance > 1.0 && !this.checkCondition_1529(livingEntity))
               .thenComparingDouble(livingEntity -> CombatComponent165.computeValue_1554(livingEntity));
         } else {
            comparator = switch ((String)this.choiceSettingField13.getCurrentValue()) {
               case "Distance" -> Comparator.comparingDouble(livingEntity -> CombatComponent165.computeValue_1554(livingEntity));
               case "HP" -> Comparator.comparingDouble(livingEntity -> livingEntity.getHealth());
               default -> Comparator.comparingDouble(
                  livingEntity -> Math.acos(
                     MathHelper.clamp(
                        Vec3d.fromPolar(minecraft.player.getPitch(), minecraft.player.getYaw())
                           .dotProduct(livingEntity.getBoundingBox().getCenter().subtract(vec3d).normalize()),
                        -1.0,
                        1.0
                     )
                  )
               );
            };
         }

         Stream stream = StreamSupport.<Entity>stream(minecraft.world.getEntities().spliterator(), false)
            .filter(LivingEntity.class::isInstance)
            .map(LivingEntity.class::cast)
            .filter(livingEntity -> livingEntity != minecraft.player && livingEntity.isAlive())
            .filter(this::checkCondition_1532)
            .filter(this::checkCondition_1507);
         if (!bl) {
            stream = stream.filter(livingEntity -> CombatComponent165.checkCondition_1559(vec3d, livingEntity, d));
         }

         return stream.min(comparator);
      } else {
         return Optional.empty();
      }
   }

   private boolean checkCondition_1507(LivingEntity livingEntity) {
      if (livingEntity == null || !livingEntity.isAlive() || !this.checkCondition_1532(livingEntity)) {
         return false;
      }

      if (livingEntity instanceof PlayerEntity) {
         boolean bl = NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(livingEntity.getName().getString());
         boolean bl1 = Stream.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)
            .noneMatch(equipmentSlot -> livingEntity.getEquippedStack(equipmentSlot).contains(DataComponentTypes.EQUIPPABLE));
         if (!this.checkCondition_1535("Players")) {
            return false;
         } else {
            return bl ? this.checkCondition_1535("Friends") : !bl1 || this.checkCondition_1535("Without reservation");
         }
      } else if (livingEntity instanceof HostileEntity
         || livingEntity instanceof SlimeEntity
         || livingEntity instanceof Monster
         || livingEntity instanceof EnderDragonEntity) {
         return this.checkCondition_1535("Hostile mobs");
      } else {
         return !(livingEntity instanceof PassiveEntity)
               && !(livingEntity instanceof GolemEntity)
               && !(livingEntity instanceof AllayEntity)
               && !(livingEntity instanceof AmbientEntity)
            ? false
            : this.checkCondition_1535("Animals");
      }
   }

   private boolean checkCondition_1535(String string) {
      BooleanSetting booleanSetting = this.multiSelectSettingField5.computeValue_2852(string);
      return booleanSetting != null && booleanSetting.getCurrentValue();
   }

   private boolean checkCondition_1529(LivingEntity livingEntity) {
      return Stream.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)
         .anyMatch(equipmentSlot -> livingEntity.getEquippedStack(equipmentSlot).contains(DataComponentTypes.EQUIPPABLE));
   }

   private void performAction_1533() {
      Vec3d vec3d = minecraft.player.getEyePos();
      LivingEntity livingEntity = this.livingEntityField2;
      double d = this.floatSettingField6.getCurrentValue().floatValue();
      boolean bl = this.choiceSettingField4.getCurrentValue().contains("FanTime")
         || !this.multiSelectSettingField10.computeValue_2852("The enemy is behind the wall").getCurrentValue();
      Vec3d vec3d1 = CombatComponent165.computeValue_1560(vec3d, livingEntity, d, bl);
      float f = vec3d1 == Vec3d.ZERO
         ? UtilComponent277.getFloatValueField2_3570()
         : (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d1.z, vec3d1.x)) - 90.0);
      float g = vec3d1 == Vec3d.ZERO
         ? UtilComponent277.getFloatValueField3_3571()
         : (float)(-Math.toDegrees(Math.atan2(vec3d1.y, Math.hypot(vec3d1.x, vec3d1.z))));
      System.arraycopy(this.floatValueArrayField18, 0, this.floatValueArrayField18, 1, 29);
      this.floatValueArrayField18[0] = g;
      boolean bl1 = this.multiSelectSettingField10.computeValue_2852("Item used").getCurrentValue()
            && minecraft.player.isUsingItem()
            && minecraft.player.getItemUseTimeLeft() > 0
            && this.integerField16 >= 8
         || this.multiSelectSettingField10.computeValue_2852("Container open") != null
            && this.multiSelectSettingField10.computeValue_2852("Container open").getCurrentValue()
            && minecraft.currentScreen != null
            && !(minecraft.currentScreen instanceof ScreenComponent218)
            && !(minecraft.currentScreen instanceof ScreenComponent213);
      if (this.floatValueArrayField17[3] <= 0.0F && this.checkCondition_1513()
         || CombatComponent165.checkCondition_1553(this.integerField16, this.livingEntityField2, bl1)) {
         this.floatValueArrayField17[3] = 1.0F;
         if (!minecraft.player.isTouchingWater() && this.booleanSettingField12.getCurrentValue() && !minecraft.player.isOnGround()) {
            this.floatValueArrayField17[0] = 1.0F;
         }
      }

      if (NeneClient.getInstance().getProcessors().getModules().getElytraHelperField68_0471().isEnabled()
         && this.checkCondition_1513()
         && CombatComponent165.checkCondition_1556(this.livingEntityField2, 3.0)
         && minecraft.player.isGliding()) {
         HandlerComponent108.getROTATION_1157().performAction_1141(new UtilComponent250(f, g), 180.0F, 0, 3);
      }

      if (!this.choiceSettingField4.getCurrentValue().contains("FanTime")
         && (
            UtilComponent270.computeValue_3526(Items.MACE) != -1
               || NeneClient.getInstance().getProcessors().getModules().getMaceHelperField11_0410().flagField2
                  && minecraft.player.fallDistance > 3.0
                  && CombatComponent166.computeValue_1615(minecraft.player, minecraft.world)
                        .map(vec3d2 -> vec3d2.distanceTo(minecraft.player.getEntityPos()))
                        .orElse(0.0)
                     > 2.0
                  && CombatComponent165.checkCondition_1556(this.livingEntityField2, 4.0 + minecraft.player.getVelocity().length() * 3.0)
         )) {
         float h = minecraft.player.age + minecraft.getRenderTickCounter().getTickProgress(false);
         float i = (float)((Math.sin(h * 0.31F) * 0.5 + Math.sin(h * 0.73F + 1.1F) * 0.3000000314327426 + Math.sin(h * 1.7F + 2.6F) * 0.2000000098386085) * 8.0)
            / 8.0F;
         float j = CombatComponent165.computeValue_1552(minecraft.player.getYaw(), f, 0.8F);
         float k = CombatComponent165.computeValue_1552(minecraft.player.getPitch(), g, 0.8F);
         HandlerComponent108.getROTATION_1157().performAction_1141(new UtilComponent250(j + i, k + i), 180.0F, 1, 2);
      }

      switch ((String)this.choiceSettingField4.getCurrentValue()) {
         case "FanTime":
         case "FanTime FOV":
            this.performAction_1518(f, g, vec3d1);
            break;
         case "Legit":
            this.performAction_1502(f, g, vec3d1);
      }

      float[] l = this.floatValueArrayField17;
      l[3]--;
      float[] m = this.floatValueArrayField17;
      m[5]--;
      float[] n = this.floatValueArrayField17;
      n[8]--;
      this.floatValueArrayField17[1] = (float)MathHelper.wrapDegrees(
         Math.toDegrees(Math.atan2(this.livingEntityField2.getZ() - minecraft.player.getZ(), this.livingEntityField2.getX() - minecraft.player.getX())) - 90.0
      );
   }

   private void performAction_1518(float f, float g, Vec3d vec3d) {
      float h = minecraft.player.age + minecraft.getRenderTickCounter().getTickProgress(false);
      float i = (float)(Math.sin(h * 0.4000000008323731) * 3.0 + Math.sin(h * 0.9500002390239708 + 1.4000004888461306) * 2.0);
      float j = (float)(Math.cos(h * 0.5 + 0.7000001555309916) * 0.5 + Math.cos(h * 0.7800000620494261 + 3.10000031689524) * 1.5);
      float k = CombatComponent165.computeValue_1552(
         minecraft.player.getPitch(),
         this.floatValueArrayField18[MathHelper.clamp(10 - this.integerField16, 0, 29)] + j * 1.5F,
         UtilComponent279.computeValue_3582(0.1F, 0.5F)
      );
      float l = CombatComponent165.computeValue_1552(minecraft.player.getYaw(), f + i, UtilComponent279.computeValue_3582(0.1F, 0.4F));
      if (this.floatValueArrayField17[3] >= 0.0F) {
         if (!CombatComponent165.checkCondition_1551(
               minecraft.player.getYaw(), minecraft.player.getPitch(), this.floatSettingField6.getCurrentValue().floatValue(), this.livingEntityField2, true
            )
            && this.floatValueArrayField17[8] <= 0.0F) {
            l = f;
         }

         if (!CombatComponent165.checkCondition_1551(f, k, this.floatSettingField6.getCurrentValue().floatValue(), this.livingEntityField2, true)
            && this.floatValueArrayField17[8] <= 0.0F) {
            k = g;
         }

         if (!CombatComponent165.checkCondition_1551(
               minecraft.player.getYaw() + i,
               minecraft.player.getYaw() + j,
               this.floatSettingField6.getCurrentValue().floatValue(),
               this.livingEntityField2,
               true
            )
            && CombatComponent165.checkCondition_1551(
               minecraft.player.getYaw(), minecraft.player.getPitch(), this.floatSettingField6.getCurrentValue().floatValue(), this.livingEntityField2, true
            )) {
            i = MathHelper.clamp(i, -0.05F, 0.05F);
            j = MathHelper.clamp(j, -0.05F, 0.05F);
         }
      }

      if (this.integerField16 <= 4 && this.floatValueArrayField17[2] % 2.0F == 0.0F) {
         l = minecraft.player.getYaw();
      }

      HandlerComponent108.getROTATION_1157()
         .performAction_1141(
            new UtilComponent250(l + i, (this.choiceSettingField4.getCurrentValue().equals("FanTime") ? k : UtilComponent277.getFloatValueField3_3571()) + j),
            220.0F,
            1,
            1
         );
   }

   private void performAction_1502(float f, float g, Vec3d vec3d) {
      float h = minecraft.player.age + minecraft.getRenderTickCounter().getTickProgress(false);
      float i = (float)((Math.sin(h * 0.31F) * 0.5 + Math.sin(h * 1.7F + 2.6F) * 0.2000000098386085) * 8.0) / 4.0F;
      float j = i;
      float k = i;
      float l = CombatComponent165.computeValue_1552(minecraft.player.getYaw(), f, UtilComponent279.computeValue_3582(0.2F, 0.35F));
      float m = CombatComponent165.computeValue_1552(minecraft.player.getPitch(), g, UtilComponent279.computeValue_3582(0.15F, 0.25F));
      if (this.floatValueArrayField17[3] >= 0.0F) {
         m = CombatComponent165.computeValue_1552(minecraft.player.getPitch(), g, 0.35F);
         k /= 3.0F;
         j /= 3.0F;
         if (!CombatComponent165.checkCondition_1551(
            minecraft.player.getYaw(), minecraft.player.getPitch(), this.floatSettingField6.getCurrentValue().floatValue(), this.livingEntityField2, true
         )) {
            l = CombatComponent165.computeValue_1552(minecraft.player.getYaw(), f, UtilComponent279.computeValue_3582(0.7F, 1.0F));
         }
      }

      if (!CombatComponent165.checkCondition_1551(l + j, m + k, this.floatSettingField6.getCurrentValue().floatValue(), this.livingEntityField2, true)
         && CombatComponent165.checkCondition_1551(f, g, this.floatSettingField6.getCurrentValue().floatValue(), this.livingEntityField2, true)) {
         j = MathHelper.clamp(j, -0.15F, 0.15F);
         k = MathHelper.clamp(k, -0.15F, 0.15F);
      }

      if (this.floatValueArrayField17[5] >= 0.0F) {
         j *= 8.0F;
         if (this.integerField16 >= 1 && this.floatValueArrayField17[2] % 5.0F == 0.0F) {
            m = CombatComponent165.computeValue_1552(minecraft.player.getPitch(), -g, 0.05F);
         }
      }

      HandlerComponent108.getROTATION_1157().performAction_1141(new UtilComponent250(l + j, m + k), 180.0F, 1, 1);
   }
}
