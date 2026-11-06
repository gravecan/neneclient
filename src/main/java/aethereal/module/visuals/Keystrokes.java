package aethereal.module.visuals;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.setting.BooleanSetting;

@ModuleRegister(name = "Keystrokes", description = "Shows movement and jump key states", category = Category.Visuals)
public class Keystrokes extends Module {
   private final BooleanSetting background = new BooleanSetting("Background", true);

   public Keystrokes() {
      this.addSettings(this.background);
   }

   @EventTarget
   public void onDrawEvent_2340(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001() && !minecraft.options.hudHidden) {
         byte b = 5;
         int i = minecraft.getWindow().getScaledHeight() - 56;
         this.performAction_2341(drawEvent, "W", b + 20, i, minecraft.options.forwardKey.isPressed());
         this.performAction_2341(drawEvent, "A", b, i + 18, minecraft.options.leftKey.isPressed());
         this.performAction_2341(drawEvent, "S", b + 20, i + 18, minecraft.options.backKey.isPressed());
         this.performAction_2341(drawEvent, "D", b + 40, i + 18, minecraft.options.rightKey.isPressed());
         this.performAction_2341(drawEvent, "J", b + 20, i + 36, minecraft.options.jumpKey.isPressed());
      }
   }

   private void performAction_2341(DrawEvent drawEvent, String string, int i, int j, boolean bl) {
      RenderComponent184 renderComponent184 = drawEvent.getRenderComponent184Field1_0998();
      int k = bl ? RenderComponent183.computeValue_2558(255, 42, 42, 220) : RenderComponent183.computeValue_2558(12, 10, 18, 190);
      if (this.background.getCurrentValue() || bl) {
         renderComponent184.performAction_2574(drawEvent.getContext_1000(), i, j, 18.0F, 16.0F, k);
      }

      int l = bl ? RenderComponent183.computeValue_2558(255, 255, 255, 255) : RenderComponent183.computeValue_2558(210, 205, 220, 220);
      int m = minecraft.textRenderer.getWidth(string);
      drawEvent.getContext_1000().drawText(minecraft.textRenderer, string, i + (18 - m) / 2, j + 4, l, false);
   }
}
