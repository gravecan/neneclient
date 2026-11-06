package aethereal.module.visuals;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.setting.BooleanSetting;

@ModuleRegister(name = "Coordinates", description = "Shows your current XYZ position", category = Category.Visuals)
public class Coordinates extends Module {
   private final BooleanSetting background = new BooleanSetting("Background", true);

   public Coordinates() {
      this.addSettings(this.background);
   }

   @EventTarget
   public void onDrawEvent_2334(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001() && !minecraft.options.hudHidden && minecraft.player != null) {
         String string = String.format("XYZ %d %d %d", (int)minecraft.player.getX(), (int)minecraft.player.getY(), (int)minecraft.player.getZ());
         int i = minecraft.textRenderer.getWidth(string) + 10;
         byte b = 5;
         byte c = 5;
         RenderComponent184 renderComponent184 = drawEvent.getRenderComponent184Field1_0998();
         if (this.background.getCurrentValue()) {
            renderComponent184.performAction_2574(drawEvent.getContext_1000(), b, c, i, 14.0F, RenderComponent183.computeValue_2558(12, 10, 18, 190));
         }

         drawEvent.getContext_1000().drawText(minecraft.textRenderer, string, b + 5, c + 3, RenderComponent183.computeValue_2558(255, 255, 255, 235), false);
      }
   }
}
