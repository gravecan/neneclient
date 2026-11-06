package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.setting.BooleanSetting;

@ModuleRegister(name = "Item Physic", description = "Adds physics to objects lying on the ground", category = Category.Render)
public class ItemPhysic extends Module {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Reduce the size of items", false);

   public BooleanSetting getBooleanSettingField1_2261() {
      return this.booleanSettingField1;
   }

   public ItemPhysic() {
      this.addSettings(this.booleanSettingField1);
   }
}
