package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent095;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.FloatSetting;
import aethereal.util.UtilComponent268;

@ModuleRegister(name = "Wall Climb", description = "Allows you to climb walls", category = Category.Movement)
public class WallClimb extends Module {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Select bypass type", "Matrix", "Matrix");
   private final FloatSetting floatSettingField2 = new FloatSetting("Speed \u200b\u200bmode", 20.0F, 1.0F, 100.0F, 1.0F);
   private final UtilComponent268 utilComponent268Field3 = new UtilComponent268();

   public WallClimb() {
      this.addSettings(this.choiceSettingField1, this.floatSettingField2);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.utilComponent268Field3.performAction_3524();
   }

   @EventTarget
   public void onEventComponent095_2090(EventComponent095 eventComponent095) {
      if (this.choiceSettingField1.checkCondition_2850("Matrix")) {
         this.performAction_2091(eventComponent095, this.floatSettingField2.getCurrentValue().longValue());
      }
   }

   private void performAction_2091(EventComponent095 eventComponent095, long l) {
      if (this.utilComponent268Field3.checkCondition_3522(l * 5L) && minecraft.player.horizontalCollision) {
         eventComponent095.setOnGround_1087(true);
         minecraft.player.setOnGround(true);
         minecraft.player.verticalCollision = true;
         minecraft.player.horizontalCollision = true;
         minecraft.player.jump();
         this.utilComponent268Field3.performAction_3524();
      }
   }
}
