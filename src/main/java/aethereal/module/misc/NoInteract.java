package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.setting.BooleanSetting;

@ModuleRegister(name = "No Interact", description = "Blocks accidental interaction with containers and blocks", category = Category.Misc)
public class NoInteract extends Module {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Consider enabled Aura", true);

   public BooleanSetting getBooleanSettingField1_1940() {
      return this.booleanSettingField1;
   }

   public NoInteract() {
      this.addSettings(this.booleanSettingField1);
   }
}
