package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent085;
import aethereal.setting.BooleanSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;

@ModuleRegister(name = "Lock Slot", description = "Prohibits dropping items from selected slots", category = Category.Player)
public class LockSlot extends Module {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Blocked slots",
      new BooleanSetting("1", false),
      new BooleanSetting("2", false),
      new BooleanSetting("3", false),
      new BooleanSetting("4", false),
      new BooleanSetting("5", false),
      new BooleanSetting("6", false),
      new BooleanSetting("7", false),
      new BooleanSetting("8", false),
      new BooleanSetting("9", false)
   );
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Block only in PVP", true);

   public LockSlot() {
      this.addSettings(this.booleanSettingField2, this.multiSelectSettingField1);
   }

   @EventTarget
   public void onEventComponent085_2201(EventComponent085 eventComponent085) {
      if ((!this.booleanSettingField2.getCurrentValue() || UtilComponent252.checkCondition_3401())
         && this.multiSelectSettingField1.computeValue_2851(eventComponent085.getSlot_1039()).getCurrentValue()) {
         UtilComponent265.performAction_3505("Attempt to eject from slot \"&c" + (eventComponent085.getSlot_1039() + 1) + "&7\" was blocked");
         eventComponent085.performAction_0597(true);
      }
   }
}
