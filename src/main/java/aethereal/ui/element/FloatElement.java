package aethereal.ui.element;

import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.NeneClient;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent188;
import aethereal.setting.FloatSetting;
import aethereal.util.UtilComponent279;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;
import org.joml.Vector4f;

public class FloatElement extends SettingElement<FloatSetting> {
   private boolean flagField1;

   @Override
   public boolean checkCondition_2915(double d, double e, int i) {
      Vector4f vector4f = this.vector4fField3;
      FloatSetting floatSetting = this.setting;
      if (!UtilComponent279.checkCondition_3576(
         d, e, vector4f.x, vector4f.y + RenderComponent188.renderComponent187Field3.computeValue_2622(6.5F), vector4f.z, 11.0F
      )) {
         return false;
      }

      if (i == 0) {
         this.flagField1 = true;
         this.performAction_3037(d);
         return true;
      }

      if (i != 2) {
         return false;
      }

      if (!(floatSetting instanceof FloatSetting)) {
         throw new ClassCastException();
      }

      floatSetting.reset();
      return true;
   }

   @Override
   public boolean checkCondition_2911(double d, double e, int i) {
      this.flagField1 = false;
      return false;
   }

   @Override
   public boolean checkCondition_3026(double d, double e, double f) {
      FloatSetting floatSetting = this.setting;
      Vector4f vector4f = this.vector4fField3;
      if (!(floatSetting instanceof FloatSetting floatSetting1)) {
         throw new ClassCastException();
      } else if (floatSetting1.scroll
         && UtilComponent279.checkCondition_3576(
            d, e, vector4f.x, vector4f.y, vector4f.z, vector4f.y + RenderComponent188.renderComponent187Field3.computeValue_2622(6.5F) + 9.5F - vector4f.y
         )) {
         Float float_ = floatSetting1.getCurrentValue();
         if (!(float_ instanceof Float)) {
            throw new ClassCastException();
         }

         floatSetting1.setValue(
            UtilComponent279.computeValue_3577(
               Math.round((float_ + (float)Math.signum(f) * floatSetting1.increment) / floatSetting1.increment) * floatSetting1.increment,
               floatSetting1.minimum,
               floatSetting1.maximum
            )
         );
         return true;
      } else {
         return false;
      }
   }

   public FloatElement(FloatSetting floatSetting) {
      super(floatSetting);
      this.vector4fField3.w = 22.0F;
   }

   @Override
   public void performAction_2918(DrawContext drawContext, double d, double e, float f, float g) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      this.vector4fField3.w = 18.0F;
      if (this.flagField1) {
         this.performAction_3037(d);
      }

      this.getAnimationField1_3029()
         .setFloatValueField1_2526(
            UtilComponent279.computeValue_3586(
               this.getAnimationField1_3029().getFloatValueField1_2527(),
               (this.setting.getCurrentValue() - this.setting.minimum) / (this.setting.maximum - this.setting.minimum),
               1.0F
            )
         );
      float h = this.getAnimationField1_3029().getFloatValueField1_2527();
      boolean bl = UtilComponent279.checkCondition_3576(d, e, this.vector4fField3.x, this.vector4fField3.y, this.vector4fField3.z, this.vector4fField3.w)
         && g >= 1.0F;
      float i = this.setting.minimum + (this.setting.maximum - this.setting.minimum) * h;
      String string = this.setting.increment % 1.0F == 0.0F ? String.valueOf(Math.round(i)) : String.valueOf(Math.round(i * 100.0F) / 100.0F);
      float j = RenderComponent188.renderComponent187Field3.computeValue_2626(string, 6.25F) + 6.0F;
      float k = RenderComponent188.renderComponent187Field3.computeValue_2622(6.25F) + 2.0F;
      float l = this.vector4fField3.x + this.vector4fField3.z - j;
      this.performAction_3028(
         matrix3x2fStack,
         RenderComponent188.renderComponent187Field3,
         this.setting.getName(),
         this.vector4fField3.x,
         this.vector4fField3.y + 0.5F,
         RenderComponent188.renderComponent187Field3.computeValue_2622(6.5F),
         6.5F,
         themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(),
         l - this.vector4fField3.x - 4.0F,
         bl,
         g,
         f
      );
      renderComponent184.performAction_2577(
         matrix3x2fStack,
         l,
         this.vector4fField3.y,
         j,
         k,
         3.0F,
         RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), 0.03137255F * g)
      );
      renderComponent184.performAction_2576(
         matrix3x2fStack,
         l,
         this.vector4fField3.y,
         j,
         k,
         3.0F,
         0.5F,
         RenderComponent183.computeValue_2549(
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0547(),
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0548() * g
         )
      );
      RenderComponent188.renderComponent187Field3
         .performAction_2620(
            matrix3x2fStack,
            string,
            l + j / 2.0F,
            this.vector4fField3.y + (k - RenderComponent188.renderComponent187Field3.computeValue_2622(6.25F)) / 2.0F - 0.5F,
            6.25F,
            RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(), g)
         );
      float m = this.vector4fField3.y + RenderComponent188.renderComponent187Field3.computeValue_2622(6.5F) + 6.5F;
      renderComponent184.performAction_2577(
         matrix3x2fStack,
         this.vector4fField3.x,
         m,
         this.vector4fField3.z,
         3.0F,
         1.5F,
         RenderComponent183.computeValue_2549(RenderComponent183.computeValue_2558(50, 52, 60, 255), g * 0.35F)
      );
      renderComponent184.performAction_2577(
         matrix3x2fStack,
         this.vector4fField3.x,
         m,
         this.vector4fField3.z * h,
         3.0F,
         1.5F,
         RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), g)
      );
      renderComponent184.performAction_2577(
         matrix3x2fStack,
         this.vector4fField3.x + (this.vector4fField3.z - 6.0F) * h,
         m + 1.5F - 3.0F,
         6.0F,
         6.0F,
         3.0F,
         RenderComponent183.computeValue_2549(RenderComponent183.computeValue_2558(255, 255, 255, 255), g)
      );
   }

   private void performAction_3037(double d) {
      float f = UtilComponent279.computeValue_3577((float)(d - this.vector4fField3.x) / this.vector4fField3.z, 0.0F, 1.0F);
      float g = this.setting.minimum + (this.setting.maximum - this.setting.minimum) * f;
      this.setting
         .setValue(
            UtilComponent279.computeValue_3577(Math.round(g / this.setting.increment) * this.setting.increment, this.setting.minimum, this.setting.maximum)
         );
   }
}
