package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent097;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.FloatSetting;

@ModuleRegister(name = "Aspect Ratio", description = "Changes the screen aspect ratio", category = Category.Render)
public class AspectRatio extends Module {
   public final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Aspect Ratio", "Custom", "4:3", "16:9", "1:1", "16:10", "Custom");
   public final FloatSetting floatSettingField2 = new FloatSetting("Ratio", 1.9F, 0.1F, 5.0F, 0.1F)
      .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("Custom"));

   public AspectRatio() {
      this.addSettings(this.choiceSettingField1, this.floatSettingField2);
   }

   @EventTarget
   public void onEventComponent097_2217(EventComponent097 eventComponent097) {
      eventComponent097.setRatio_1094(this.computeValue_2218());
   }

   public float computeValue_2218() {
      switch ((String)this.choiceSettingField1.getCurrentValue()) {
         case "4:3":
            return 1.3333334F;
         case "16:9":
            return 1.7777778F;
         case "1:1":
            return 1.0F;
         case "16:10":
            return 1.6F;
         default:
            return this.floatSettingField2.getCurrentValue();
      }
   }
}
