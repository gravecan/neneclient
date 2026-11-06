package aethereal.ui.element;

import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.NeneClient;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.setting.ChoiceSetting;
import aethereal.util.UtilComponent279;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;
import org.joml.Vector4f;

public class ChoiceElement extends SettingElement<ChoiceSetting> {
   private final Animation[] animationArrayField1;

   @Override
   public boolean checkCondition_2915(double d, double e, int i) {
      Vector4f vector4f = this.vector4fField3;
      ChoiceSetting choiceSetting = this.setting;
      if (i != 0) {
         if (i != 2 || !UtilComponent279.checkCondition_3576(d, e, vector4f.x, vector4f.y, vector4f.z, vector4f.w)) {
            return false;
         }

         if (!(choiceSetting instanceof ChoiceSetting)) {
            throw new ClassCastException();
         }

         choiceSetting.reset();
         return true;
      } else {
         float f = vector4f.x;
         float g = vector4f.y + RenderComponent188.renderComponent187Field3.computeValue_2622(6.5F) + 5.0F;
         if (!(choiceSetting instanceof ChoiceSetting choiceSetting1)) {
            throw new ClassCastException();
         } else {
            for (String string : choiceSetting1.getListField1_2849()) {
               if (!(string instanceof String string1)) {
                  throw new ClassCastException();
               }

               float h = RenderComponent188.renderComponent187Field3.computeValue_2626(string1, 6.25F) + 6.0F;
               if (f + h > vector4f.x + vector4f.z) {
                  f = vector4f.x;
                  g += 12.0F;
               }

               if (UtilComponent279.checkCondition_3576(d, e, f, g, h, 9.0F)) {
                  choiceSetting1.setValue(string1);
                  return true;
               }

               f += h + 3.0F;
            }

            return false;
         }
      }
   }

   public ChoiceElement(ChoiceSetting choiceSetting) {
      super(choiceSetting);
      this.animationArrayField1 = new Animation[choiceSetting.getListField1_2849().size()];

      for (int i = 0; i < this.animationArrayField1.length; i++) {
         this.animationArrayField1[i] = new Animation();
      }
   }

   @Override
   public void performAction_2918(DrawContext drawContext, double d, double e, float f, float g) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      RenderComponent188.renderComponent187Field3
         .performAction_2630(
            matrix3x2fStack,
            this.setting.getName(),
            this.vector4fField3.x,
            this.vector4fField3.y,
            6.5F,
            RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(), g)
         );
      float h = this.vector4fField3.x;
      float i = this.vector4fField3.y + RenderComponent188.renderComponent187Field3.computeValue_2622(6.5F) + 5.0F;
      int j = 0;

      for (String string : this.setting.getListField1_2849()) {
         float k = RenderComponent188.renderComponent187Field3.computeValue_2626(string, 6.25F) + 6.0F;
         if (h + k > this.vector4fField3.x + this.vector4fField3.z) {
            h = this.vector4fField3.x;
            i += 12.0F;
         }

         this.animationArrayField1[j].performAction_2528(this.setting.checkCondition_2850(string));
         this.animationArrayField1[j].performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field9, f);
         float l = this.animationArrayField1[j].getFloatValueField4_2530();
         renderComponent184.performAction_2577(
            matrix3x2fStack,
            h,
            i,
            k,
            9.0F,
            2.0F,
            RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), (5.0F + 65.0F * l) / 255.0F * g)
         );
         renderComponent184.performAction_2576(
            matrix3x2fStack,
            h,
            i,
            k,
            9.0F,
            2.0F,
            0.5F,
            RenderComponent183.computeValue_2549(
               themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0547(),
               themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0548() * g
            )
         );
         int m = RenderComponent183.computeValue_2550(
            themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(), themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(), l
         );
         RenderComponent188.renderComponent187Field3
            .performAction_2620(
               matrix3x2fStack,
               string,
               h + k / 2.0F,
               i + (9.0F - RenderComponent188.renderComponent187Field3.computeValue_2622(6.25F)) / 2.0F - 0.75F,
               6.25F,
               RenderComponent183.computeValue_2549(m, g)
            );
         h += k + 3.0F;
         j++;
      }

      this.vector4fField3.w = i + 9.0F - this.vector4fField3.y;
   }
}
