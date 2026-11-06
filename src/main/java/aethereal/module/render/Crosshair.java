package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent083;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.setting.BooleanSetting;
import aethereal.setting.FloatSetting;
import aethereal.setting.MultiSelectSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.hit.EntityHitResult;

@ModuleRegister(name = "Crosshair", description = "Displays customizable crosshair on screen", category = Category.Render)
public class Crosshair extends Module {
   private final FloatSetting floatSettingField1 = new FloatSetting("Distance from center", 0.0F, 0.0F, 6.0F, 0.5F);
   private final FloatSetting floatSettingField2 = new FloatSetting("Segment length", 2.5F, 2.0F, 5.0F, 0.5F);
   private final MultiSelectSetting multiSelectSettingField3 = new MultiSelectSetting(
      "Sight parameters", new BooleanSetting("Adaptability", false), new BooleanSetting("Contour", true), new BooleanSetting("Central Mark", false)
   );

   public Crosshair() {
      this.addSettings(this.floatSettingField1, this.floatSettingField2, this.multiSelectSettingField3);
   }

   @EventTarget
   public void onEventComponent083_2240(EventComponent083 eventComponent083) {
      if (minecraft.options.getPerspective().isFirstPerson() && !minecraft.options.hudHidden) {
         eventComponent083.performAction_0597(true);
      }
   }

   @EventTarget
   public void onDrawEvent_2239(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001() && minecraft.options.getPerspective().isFirstPerson() && !minecraft.options.hudHidden) {
         this.performAction_2241(
            drawEvent,
            minecraft.getWindow().getScaledWidth() / 2.0F,
            minecraft.getWindow().getScaledHeight() / 2.0F,
            1.0F - minecraft.player.getAttackCooldownProgress(drawEvent.getTickNeneClient_0997())
         );
      }
   }

   private void performAction_2241(DrawEvent drawEvent, float f, float g, float h) {
      RenderComponent184 renderComponent184 = drawEvent.getRenderComponent184Field1_0998();
      DrawContext drawContext = drawEvent.getContext_1000();
      float i = this.multiSelectSettingField3.computeValue_2852("Adaptability").getCurrentValue()
         ? this.floatSettingField1.getCurrentValue() + 8.0F * h
         : this.floatSettingField1.getCurrentValue();
      int j = minecraft.crosshairTarget instanceof EntityHitResult ? RenderComponent183.computeValue_2551(255, 64, 64) : -1;
      if (this.multiSelectSettingField3.computeValue_2852("Contour").getCurrentValue()) {
         renderComponent184.performAction_2574(
            drawContext, f + i - 0.5F, g - 0.5F - 0.5F, this.floatSettingField2.getCurrentValue() + 1.0F, 2.0F, RenderComponent183.computeValue_2551(0, 0, 0)
         );
         renderComponent184.performAction_2574(
            drawContext,
            f - i - this.floatSettingField2.getCurrentValue() - 0.5F,
            g - 0.5F - 0.5F,
            this.floatSettingField2.getCurrentValue() + 1.0F,
            2.0F,
            RenderComponent183.computeValue_2551(0, 0, 0)
         );
         renderComponent184.performAction_2574(
            drawContext,
            f - 0.5F - 0.5F,
            g - i - this.floatSettingField2.getCurrentValue() - 0.5F,
            2.0F,
            this.floatSettingField2.getCurrentValue() + 1.0F,
            RenderComponent183.computeValue_2551(0, 0, 0)
         );
         renderComponent184.performAction_2574(
            drawContext, f - 0.5F - 0.5F, g + i - 0.5F, 2.0F, this.floatSettingField2.getCurrentValue() + 1.0F, RenderComponent183.computeValue_2551(0, 0, 0)
         );
         renderComponent184.performAction_2574(drawContext, f + i, g - 0.5F, this.floatSettingField2.getCurrentValue(), 1.0F, j);
         renderComponent184.performAction_2574(
            drawContext, f - i - this.floatSettingField2.getCurrentValue(), g - 0.5F, this.floatSettingField2.getCurrentValue(), 1.0F, j
         );
         renderComponent184.performAction_2574(
            drawContext, f - 0.5F, g - i - this.floatSettingField2.getCurrentValue(), 1.0F, this.floatSettingField2.getCurrentValue(), j
         );
         renderComponent184.performAction_2574(drawContext, f - 0.5F, g + i, 1.0F, this.floatSettingField2.getCurrentValue(), j);
      } else {
         renderComponent184.performAction_2574(drawContext, f + i, g - 0.5F, this.floatSettingField2.getCurrentValue(), 1.0F, j);
         renderComponent184.performAction_2574(
            drawContext, f - i - this.floatSettingField2.getCurrentValue(), g - 0.5F, this.floatSettingField2.getCurrentValue(), 1.0F, j
         );
         renderComponent184.performAction_2574(
            drawContext, f - 0.5F, g - i - this.floatSettingField2.getCurrentValue(), 1.0F, this.floatSettingField2.getCurrentValue(), j
         );
         renderComponent184.performAction_2574(drawContext, f - 0.5F, g + i, 1.0F, this.floatSettingField2.getCurrentValue(), j);
      }

      if (this.multiSelectSettingField3.computeValue_2852("Central Mark").getCurrentValue() && i > 0.0F) {
         float k = f - 0.5F;
         float l = g - 0.5F;
         if (this.multiSelectSettingField3.computeValue_2852("Contour").getCurrentValue()) {
            renderComponent184.performAction_2574(drawContext, k - 0.5F, l - 0.5F, 2.0F, 2.0F, RenderComponent183.computeValue_2551(0, 0, 0));
            renderComponent184.performAction_2574(drawContext, k, l, 1.0F, 1.0F, j);
         } else {
            renderComponent184.performAction_2574(drawContext, k, l, 1.0F, 1.0F, j);
         }
      }
   }
}
