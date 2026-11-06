package aethereal.ui.element;

import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.NeneClient;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.setting.ActionSetting;
import aethereal.util.UtilComponent279;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;
import org.joml.Vector4f;

public class ActionElement extends SettingElement<ActionSetting> {
   @Override
   public boolean checkCondition_2915(double d, double e, int i) {
      if (i != 0) {
         return false;
      }

      Vector4f vector4f = this.vector4fField3;
      ActionSetting actionSetting = this.setting;
      if (!UtilComponent279.checkCondition_3576(d, e, vector4f.x, vector4f.y, vector4f.z, vector4f.w)) {
         return false;
      }

      if (!(actionSetting instanceof ActionSetting)) {
         throw new ClassCastException();
      }

      actionSetting.performAction_2848();
      return true;
   }

   public ActionElement(ActionSetting actionSetting) {
      super(actionSetting);
      this.vector4fField3.w = 14.0F;
   }

   @Override
   public void performAction_2918(DrawContext drawContext, double d, double e, float f, float g) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      this.getAnimationField1_3029()
         .performAction_2528(
            UtilComponent279.checkCondition_3576(d, e, this.vector4fField3.x, this.vector4fField3.y, this.vector4fField3.z, this.vector4fField3.w) && g >= 1.0F
         );
      this.getAnimationField1_3029().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field9, f);
      float h = this.getAnimationField1_3029().getFloatValueField4_2530();
      int i = RenderComponent183.computeValue_2558(255, 255, 255, 255);
      renderComponent184.performAction_2577(
         matrix3x2fStack,
         this.vector4fField3.x,
         this.vector4fField3.y,
         this.vector4fField3.z,
         this.vector4fField3.w,
         4.0F,
         RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), (10.0F + 20.0F * h) / 255.0F * g)
      );
      renderComponent184.performAction_2576(
         matrix3x2fStack,
         this.vector4fField3.x,
         this.vector4fField3.y,
         this.vector4fField3.z,
         this.vector4fField3.w,
         4.0F,
         0.5F,
         RenderComponent183.computeValue_2549(
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0547(),
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0548() * g
         )
      );
      RenderComponent188.renderComponent187Field3
         .performAction_2620(
            matrix3x2fStack,
            this.setting.getName(),
            this.vector4fField3.x + this.vector4fField3.z / 2.0F,
            this.vector4fField3.y + (this.vector4fField3.w - RenderComponent188.renderComponent187Field3.computeValue_2622(7.0F)) / 2.0F - 0.5F,
            7.0F,
            RenderComponent183.computeValue_2549(i, g)
         );
   }
}
