package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent079;
import aethereal.setting.BooleanSetting;
import aethereal.setting.FloatSetting;
import aethereal.setting.MultiSelectSetting;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.math.Box;

@ModuleRegister(name = "Hit Boxes", description = "Enlarges entity hitboxes, making them easier to hit", category = Category.Combat)
public class HitBoxes extends Module {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Targets", new BooleanSetting("Players", true), new BooleanSetting("Hostile mobs", true), new BooleanSetting("Animals", false)
   );
   private final FloatSetting floatSettingField2 = new FloatSetting("X and Z expansion", 0.0F, 0.0F, 1.0F, 0.1F);
   private final FloatSetting floatSettingField3 = new FloatSetting("Y extension", 0.0F, 0.0F, 1.0F, 0.1F);

   public HitBoxes() {
      this.addSettings(this.multiSelectSettingField1, this.floatSettingField2, this.floatSettingField3);
   }

   @EventTarget
   public void onEventComponent079_1594(EventComponent079 eventComponent079) {
      if (!(this.floatSettingField2.getValue() <= 0.0F) || !(this.floatSettingField3.getValue() <= 0.0F)) {
         if (minecraft.player != null && minecraft.world != null) {
            Entity entity = eventComponent079.getEntity_1025();
            if (entity != minecraft.player && entity != minecraft.getCameraEntity() && entity instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
               if (this.checkCondition_1595(livingEntity)) {
                  Box box = eventComponent079.getBox_1026();
                  double d = this.floatSettingField2.getValue().floatValue() / 2.0;
                  double e = this.floatSettingField3.getValue().floatValue() / 2.0;
                  eventComponent079.setBox_1028(new Box(box.minX - d, box.minY - e, box.minZ - d, box.maxX + d, box.maxY + e, box.maxZ + d));
               }
            }
         }
      }
   }

   private boolean checkCondition_1595(LivingEntity livingEntity) {
      if (!(livingEntity instanceof PlayerEntity)) {
         if (livingEntity instanceof HostileEntity
            || livingEntity instanceof SlimeEntity
            || livingEntity instanceof Monster
            || livingEntity instanceof EnderDragonEntity) {
            return this.checkCondition_1596("Hostile mobs");
         } else {
            return !(livingEntity instanceof PassiveEntity)
                  && !(livingEntity instanceof GolemEntity)
                  && !(livingEntity instanceof AllayEntity)
                  && !(livingEntity instanceof AmbientEntity)
               ? false
               : this.checkCondition_1596("Animals");
         }
      } else {
         return this.checkCondition_1596("Players")
            && !NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(livingEntity.getName().getString());
      }
   }

   private boolean checkCondition_1596(String string) {
      BooleanSetting booleanSetting = this.multiSelectSettingField1.computeValue_2852(string);
      return booleanSetting != null && booleanSetting.getCurrentValue();
   }
}
