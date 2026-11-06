package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.FloatSetting;
import aethereal.util.UtilComponent268;
import platform.inject.invokers.MinecraftClientInvoker;

@ModuleRegister(name = "Tape Mouse", description = "Automatically clicks the selected mouse button at specified intervals", category = Category.Combat)
public class TapeMouse extends Module implements MinecraftAccess {
   private final FloatSetting floatSettingField1 = new FloatSetting("Delay between clicks", 1000.0F, 10.0F, 5000.0F, 10.0F);
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Don't click while eating", true);
   private final ChoiceSetting choiceSettingField3 = new ChoiceSetting("Mouse button", "Right", "Right", "Left");
   private final UtilComponent268 utilComponent268Field4 = new UtilComponent268();

   public FloatSetting getFloatSettingField1_1646() {
      return this.floatSettingField1;
   }

   public BooleanSetting getBooleanSettingField2_1642() {
      return this.booleanSettingField2;
   }

   public ChoiceSetting getChoiceSettingField3_1643() {
      return this.choiceSettingField3;
   }

   public UtilComponent268 getUtilComponent268Field4_1644() {
      return this.utilComponent268Field4;
   }

   public TapeMouse() {
      this.addSettings(this.choiceSettingField3, this.booleanSettingField2, this.floatSettingField1);
   }

   @EventTarget
   public void onEventComponent074_1645(EventComponent074 eventComponent074) {
      if ((!this.booleanSettingField2.getCurrentValue() || !minecraft.player.isUsingItem())
         && this.utilComponent268Field4.checkCondition_3522(this.floatSettingField1.getCurrentValue().intValue())) {
         switch ((String)this.choiceSettingField3.getCurrentValue()) {
            case "Right":
               ((MinecraftClientInvoker)minecraft).invokeDoItemUse();
               break;
            case "Left":
               ((MinecraftClientInvoker)minecraft).invokeDoAttack();
         }

         this.utilComponent268Field4.performAction_3524();
      }
   }
}
