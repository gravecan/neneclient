package aethereal.ui.element;

import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.NeneClient;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.render.RenderComponent193;
import aethereal.setting.KeybindSetting;
import aethereal.util.KeyUtil;
import aethereal.util.UtilComponent279;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;
import org.joml.Vector4f;

public class KeybindElement extends SettingElement<KeybindSetting> {
   private boolean flagField1;

   public boolean isFlagField1_2981() {
      return this.flagField1;
   }

   public void performAction_2982() {
      this.flagField1 = false;
   }

   @Override
   public boolean checkCondition_2915(double d, double e, int i) {
      Vector4f vector4f = this.vector4fField3;
      KeybindSetting keybindSetting = this.setting;
      if (this.flagField1) {
         if (!(keybindSetting instanceof KeybindSetting)) {
            throw new ClassCastException();
         }

         keybindSetting.setValue(-100 + i);
         this.flagField1 = false;
         return true;
      } else {
         if (!UtilComponent279.checkCondition_3576(d, e, vector4f.x, vector4f.y, vector4f.z, vector4f.w)) {
            return false;
         }

         if (i == 0) {
            this.flagField1 = true;
            return true;
         }

         if (i != 2) {
            return false;
         }

         if (!(keybindSetting instanceof KeybindSetting)) {
            throw new ClassCastException();
         }

         keybindSetting.reset();
         return true;
      }
   }

   @Override
   public boolean checkCondition_2983(int i, int j, int k) {
      if (!this.flagField1) {
         return false;
      }

      KeybindSetting keybindSetting = this.setting;
      if (!(keybindSetting instanceof KeybindSetting)) {
         throw new ClassCastException();
      }

      if (i == 261 || i == 259) {
         keybindSetting.setValue(-1);
      } else if (i == 256) {
         keybindSetting.setValue(-1);
      } else {
         keybindSetting.setValue(i);
      }

      this.flagField1 = false;
      return true;
   }

   public KeybindElement(KeybindSetting keybindSetting) {
      super(keybindSetting);
      this.vector4fField3.w = 11.0F;
   }

   @Override
   public void performAction_2918(DrawContext drawContext, double d, double e, float f, float g) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      this.getAnimationField1_3029().performAction_2528(this.flagField1);
      this.getAnimationField1_3029().performAction_2535(0.0F, 1.0F, 0.4F, RenderComponent186.contract372Field16, f);
      float h = this.vector4fField3.y + this.vector4fField3.w / 2.0F + 0.5F;
      boolean bl = UtilComponent279.checkCondition_3576(d, e, this.vector4fField3.x, this.vector4fField3.y, this.vector4fField3.z, this.vector4fField3.w)
         && g >= 1.0F;
      float i = this.getAnimationField1_3029().getFloatValueField4_2530();
      float j = 1.0F - i;
      String string = this.setting.getCurrentValue() == -1 ? "None" : KeyUtil.getLabel_3478(this.setting.getCurrentValue());
      float k = RenderComponent188.renderComponent187Field3.computeValue_2626(string, 6.5F) * j
         + RenderComponent188.renderComponent187Field3.computeValue_2626("...", 6.5F) * i;
      float l = k + 8.0F;
      float m = RenderComponent188.renderComponent187Field3.computeValue_2622(6.5F) + 3.0F;
      float n = this.vector4fField3.x + this.vector4fField3.z - l;
      float o = h - m / 2.0F;
      float p = o + (m - RenderComponent188.renderComponent187Field3.computeValue_2622(6.5F)) / 2.0F - 0.75F;
      this.performAction_3028(
         matrix3x2fStack,
         RenderComponent188.renderComponent187Field3,
         this.setting.getName(),
         this.vector4fField3.x,
         this.vector4fField3.y,
         this.vector4fField3.w,
         6.5F,
         themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(),
         n - this.vector4fField3.x - 4.0F,
         bl,
         g,
         f
      );
      renderComponent184.performAction_2577(
         matrix3x2fStack,
         n,
         o,
         l,
         m,
         2.0F,
         RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), 0.039215688F * g)
      );
      renderComponent184.performAction_2576(
         matrix3x2fStack,
         n,
         o,
         l,
         m,
         2.0F,
         0.5F,
         RenderComponent183.computeValue_2549(
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0547(),
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0548() * g
         )
      );
      RenderComponent193.performAction_2681(matrix3x2fStack, n, o, l, m);
      if (j > 0.0F) {
         RenderComponent188.renderComponent187Field3
            .performAction_2630(
               matrix3x2fStack,
               string,
               n + 4.0F,
               p,
               6.5F,
               RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(), g * j)
            );
      }

      if (i > 0.0F) {
         RenderComponent188.renderComponent187Field3
            .performAction_2630(
               matrix3x2fStack,
               "...",
               n + 4.0F,
               p,
               6.5F,
               RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(), g * i)
            );
      }

      RenderComponent193.performAction_2680(matrix3x2fStack);
   }
}
