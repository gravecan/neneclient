package aethereal.ui.element;

import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.NeneClient;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.setting.BooleanSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.util.UtilComponent279;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;
import org.joml.Vector4f;

public class MultiSelectElement extends SettingElement<MultiSelectSetting> {
   private final Animation[] animationArrayField1;

   @Override
   public boolean checkCondition_2915(double d, double e, int i) {
      Vector4f vector4f = this.vector4fField3;
      MultiSelectSetting multiSelectSetting = this.setting;
      if (i == 0) {
         float g = vector4f.x;
         float h = vector4f.y + RenderComponent188.renderComponent187Field3.computeValue_2622(6.5F) + 5.0F;
         if (!(multiSelectSetting instanceof MultiSelectSetting multiSelectSetting1)) {
            throw new ClassCastException();
         } else {
            for (BooleanSetting booleanSetting1 : multiSelectSetting1.getCurrentValue()) {
               float f = RenderComponent188.renderComponent187Field3.computeValue_2626(booleanSetting1.getName(), 6.25F) + 6.0F;
               if (g + f > vector4f.x + vector4f.z) {
                  g = vector4f.x;
                  h += 12.0F;
               }

               if (UtilComponent279.checkCondition_3576(d, e, g, h, f, 9.0F)) {
                  booleanSetting1.setValue(!booleanSetting1.getCurrentValue());
                  return true;
               }

               g += f + 3.0F;
            }

            return false;
         }
      } else {
         if (i != 2 || !UtilComponent279.checkCondition_3576(d, e, vector4f.x, vector4f.y, vector4f.z, vector4f.w)) {
            return false;
         }

         if (!(multiSelectSetting instanceof MultiSelectSetting)) {
            throw new ClassCastException();
         }

         for (BooleanSetting booleanSetting : multiSelectSetting.getCurrentValue()) {
            booleanSetting.reset();
         }

         return true;
      }
   }

   public MultiSelectElement(MultiSelectSetting multiSelectSetting) {
      super(multiSelectSetting);
      this.animationArrayField1 = new Animation[multiSelectSetting.getCurrentValue().size()];

      for (int i = 0; i < this.animationArrayField1.length; i++) {
         this.animationArrayField1[i] = new Animation();
      }
   }

   @Override
   public void performAction_2918(DrawContext drawContext, double d, double e, float f, float g) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      long l = this.setting.getCurrentValue().stream().filter(booleanSetting1 -> booleanSetting1.getCurrentValue()).count();
      int i = this.setting.getCurrentValue().size();
      String string = l + "from" + i;
      float h = RenderComponent188.renderComponent187Field3.computeValue_2626(string, 6.5F);
      boolean bl = UtilComponent279.checkCondition_3576(d, e, this.vector4fField3.x, this.vector4fField3.y, this.vector4fField3.z, this.vector4fField3.w)
         && g >= 1.0F;
      this.performAction_3028(
         matrix3x2fStack,
         RenderComponent188.renderComponent187Field3,
         this.setting.getName(),
         this.vector4fField3.x,
         this.vector4fField3.y,
         RenderComponent188.renderComponent187Field3.computeValue_2622(6.5F) + 1.0F,
         6.5F,
         themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(),
         this.vector4fField3.z - h - 4.0F,
         bl,
         g,
         f
      );
      RenderComponent188.renderComponent187Field3
         .performAction_2630(
            matrix3x2fStack,
            string,
            this.vector4fField3.x + this.vector4fField3.z - h,
            this.vector4fField3.y + 0.25F,
            6.5F,
            RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(), g)
         );
      float j = this.vector4fField3.x;
      float k = this.vector4fField3.y + RenderComponent188.renderComponent187Field3.computeValue_2622(6.5F) + 5.0F;
      int m = 0;

      for (BooleanSetting booleanSetting : this.setting.getCurrentValue()) {
         float n = RenderComponent188.renderComponent187Field3.computeValue_2626(booleanSetting.getName(), 6.25F) + 6.0F;
         if (j + n > this.vector4fField3.x + this.vector4fField3.z) {
            j = this.vector4fField3.x;
            k += 12.0F;
         }

         this.animationArrayField1[m].performAction_2528(booleanSetting.getCurrentValue());
         this.animationArrayField1[m].performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field9, f);
         float o = this.animationArrayField1[m].getFloatValueField4_2530();
         renderComponent184.performAction_2577(
            matrix3x2fStack,
            j,
            k,
            n,
            9.0F,
            2.0F,
            RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), (5.0F + 65.0F * o) / 255.0F * g)
         );
         renderComponent184.performAction_2576(
            matrix3x2fStack,
            j,
            k,
            n,
            9.0F,
            2.0F,
            0.5F,
            RenderComponent183.computeValue_2549(
               themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0547(),
               themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0548() * g
            )
         );
         int p = RenderComponent183.computeValue_2550(
            themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(), themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(), o
         );
         RenderComponent188.renderComponent187Field3
            .performAction_2620(
               matrix3x2fStack,
               booleanSetting.getName(),
               j + n / 2.0F,
               k + (9.0F - RenderComponent188.renderComponent187Field3.computeValue_2622(6.25F)) / 2.0F - 0.75F,
               6.25F,
               RenderComponent183.computeValue_2549(p, g)
            );
         j += n + 3.0F;
         m++;
      }

      this.vector4fField3.w = k + 9.0F - this.vector4fField3.y;
   }
}
