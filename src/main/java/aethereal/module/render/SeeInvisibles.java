package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.setting.FloatSetting;

@ModuleRegister(name = "See Invisibles", description = "Makes invisible players visible", category = Category.Render)
public class SeeInvisibles extends Module {
   private final FloatSetting floatSettingField1 = new FloatSetting("Transparency", 0.5F, 0.1F, 1.0F, 0.1F);

   public FloatSetting getFloatSettingField1_2295() {
      return this.floatSettingField1;
   }

   public SeeInvisibles() {
      this.addSettings(this.floatSettingField1);
   }

   public float computeValue_2297() {
      return this.floatSettingField1.getCurrentValue();
   }

   @EventTarget
   public void onEventComponent074_2296(EventComponent074 eventComponent074) {
   }
}
