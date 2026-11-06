package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent070;
import aethereal.setting.ChoiceSetting;

@ModuleRegister(name = "No Slow Down", description = "Removes ", category = Category.Movement)
public class NoSlowDown extends Module {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Use mode", "Vanilla", "Vanilla");

   public NoSlowDown() {
      this.addSettings(this.choiceSettingField1);
   }

   @EventTarget
   public void onEventComponent070_2048(EventComponent070 eventComponent070) {
      if (this.choiceSettingField1.checkCondition_2850("Vanilla")) {
         eventComponent070.performAction_0597(true);
      }
   }
}
