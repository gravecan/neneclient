package aethereal.module.visuals;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.AmbienceEvent;
import aethereal.render.RenderComponent183;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.ColorSetting;

@ModuleRegister(name = "Custom Sky", description = "Applies clean custom sky and horizon presets", category = Category.Visuals)
public class CustomSky extends Module {
   private final ChoiceSetting style = new ChoiceSetting("Sky style", "Aurora", "Aurora", "Sunset", "Midnight", "Ocean", "Vanilla");
   private final ColorSetting horizon = new ColorSetting("Horizon color", RenderComponent183.computeValue_2558(80, 145, 255, 255));

   public CustomSky() {
      this.addSettings(this.style, this.horizon);
   }

   @EventTarget
   public void onNestedType312_2336(AmbienceEvent.NestedType312 nestedType312) {
      switch ((String)this.style.getCurrentValue()) {
         case "Sunset":
            nestedType312.setTime_0963(12500L);
            break;
         case "Midnight":
         case "Aurora":
            nestedType312.setTime_0963(18000L);
            break;
         case "Ocean":
            nestedType312.setTime_0963(1000L);
            break;
         default:
            return;
      }
   }

   @EventTarget
   public void onNestedType311_2335(AmbienceEvent.NestedType311 nestedType311) {
      if (!this.style.checkCondition_2850("Vanilla")) {
         int i = this.horizon.getCurrentValue();
         float[] f = RenderComponent183.computeValue_2554(i);
         nestedType311.performAction_0961(f[0], f[1], f[2], f[3]);
      }
   }
}
