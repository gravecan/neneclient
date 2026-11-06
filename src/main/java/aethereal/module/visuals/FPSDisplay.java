package aethereal.module.visuals;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.setting.BooleanSetting;

@ModuleRegister(name = "FPS Display", description = "Shows the current client FPS", category = Category.Visuals)
public class FPSDisplay extends Module {
   private final BooleanSetting background = new BooleanSetting("Background", true);

   public FPSDisplay() {
      this.addSettings(this.background);
   }

   @EventTarget
   public void onDrawEvent_2337(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001() && !minecraft.options.hudHidden) {
         String string = minecraft.getCurrentFps() + " FPS";
         int i = minecraft.textRenderer.getWidth(string) + 10;
         int j = minecraft.getWindow().getScaledWidth() - i - 5;
         byte b = 5;
         RenderComponent184 renderComponent184 = drawEvent.getRenderComponent184Field1_0998();
         if (this.background.getCurrentValue()) {
            renderComponent184.performAction_2574(drawEvent.getContext_1000(), j, b, i, 14.0F, RenderComponent183.computeValue_2558(12, 10, 18, 190));
         }

         drawEvent.getContext_1000().drawText(minecraft.textRenderer, string, j + 5, b + 3, RenderComponent183.computeValue_2558(255, 255, 255, 235), false);
      }
   }
}
