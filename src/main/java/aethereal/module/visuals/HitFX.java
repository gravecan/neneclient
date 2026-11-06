package aethereal.module.visuals;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent077;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.setting.ColorSetting;
import aethereal.ui.screen.MenuRenderer;

@ModuleRegister(name = "HitFX", description = "Shows a clean hit effect when an attack connects", category = Category.Visuals)
public class HitFX extends Module {
   private final ColorSetting color = new ColorSetting("Color", RenderComponent183.computeValue_2558(255, 255, 255, 255));
   private float life;

   public HitFX() {
      this.addSettings(this.color);
   }

   @EventTarget
   public void setLife_2339(EventComponent077 eventComponent077) {
      this.life = 1.0F;
   }

   @EventTarget
   public void onDrawEvent_2338(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001() && !minecraft.options.hudHidden && !(this.life <= 0.0F)) {
         this.life = Math.max(0.0F, this.life - MenuRenderer.floatValueField1 * 4.0F);
         int i = minecraft.getWindow().getScaledWidth();
         int j = minecraft.getWindow().getScaledHeight();
         float f = i / 2.0F;
         float g = j / 2.0F;
         int k = RenderComponent183.computeValue_2549(this.color.getCurrentValue(), this.life);
         RenderComponent184 renderComponent184 = drawEvent.getRenderComponent184Field1_0998();
         renderComponent184.performAction_2574(drawEvent.getContext_1000(), f - 9.0F, g - 9.0F, 2.0F, 5.0F, k);
         renderComponent184.performAction_2574(drawEvent.getContext_1000(), f - 9.0F, g + 4.0F, 2.0F, 5.0F, k);
         renderComponent184.performAction_2574(drawEvent.getContext_1000(), f + 7.0F, g - 9.0F, 2.0F, 5.0F, k);
         renderComponent184.performAction_2574(drawEvent.getContext_1000(), f + 7.0F, g + 4.0F, 2.0F, 5.0F, k);
         renderComponent184.performAction_2574(drawEvent.getContext_1000(), f - 9.0F, g - 9.0F, 5.0F, 2.0F, k);
         renderComponent184.performAction_2574(drawEvent.getContext_1000(), f + 4.0F, g - 9.0F, 5.0F, 2.0F, k);
         renderComponent184.performAction_2574(drawEvent.getContext_1000(), f - 9.0F, g + 7.0F, 5.0F, 2.0F, k);
         renderComponent184.performAction_2574(drawEvent.getContext_1000(), f + 4.0F, g + 7.0F, 5.0F, 2.0F, k);
      }
   }
}
