package aethereal.ui.element;

import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.setting.ColorSetting;
import aethereal.util.UtilComponent279;
import java.awt.Color;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;
import org.joml.Vector4f;

public class ColorElement extends SettingElement<ColorSetting> {
   private final Vector4f vector4fField1 = new Vector4f();
   private final Vector4f vector4fField2 = new Vector4f();
   private final Vector4f vector4fField3 = new Vector4f();
   private final Vector4f vector4fField4 = new Vector4f();
   private float floatValueField5;
   private float floatValueField6;
   private float floatValueField7;
   private float floatValueField8;
   private ColorElement.DragMode dragModeField9 = ColorElement.DragMode.NONE;
   private boolean flagField10;
   private int integerField11;

   public boolean isFlagField10_2912() {
      return this.flagField10;
   }

   public void performAction_2913() {
      this.flagField10 = false;
      this.dragModeField9 = ColorElement.DragMode.NONE;
   }

   @Override
   public boolean checkCondition_2915(double d, double e, int i) {
      Vector4f vector4f = this.vector4fField3;
      Vector4f vector4f1 = this.vector4fField2;
      Vector4f vector4f2 = this.vector4fField3;
      Vector4f vector4f3 = this.vector4fField4;
      if (this.flagField10) {
         if (i == 0) {
            if (UtilComponent279.checkCondition_3576(d, e, vector4f1.x, vector4f1.y, vector4f1.z, vector4f1.w)) {
               this.dragModeField9 = ColorElement.DragMode.AREA;
               this.performAction_2914(d, e);
               return true;
            }

            if (UtilComponent279.checkCondition_3576(d, e, vector4f2.x, vector4f2.y, vector4f2.z, vector4f2.w)) {
               this.dragModeField9 = ColorElement.DragMode.HUE;
               this.performAction_2914(d, e);
               return true;
            }

            if (UtilComponent279.checkCondition_3576(d, e, vector4f3.x, vector4f3.y, vector4f3.z, vector4f3.w)) {
               this.dragModeField9 = ColorElement.DragMode.ALPHA;
               this.performAction_2914(d, e);
               return true;
            }
         }

         if (UtilComponent279.checkCondition_3576(d, e, this.vector4fField1.x, this.vector4fField1.y, this.vector4fField1.z, this.vector4fField1.w)) {
            return true;
         }
      }

      float f = this.vector4fField3.y + this.vector4fField3.w / 2.0F + 0.5F;
      float g = this.vector4fField3.x + this.vector4fField3.z - 11.0F;
      float h = f - 5.5F;
      if (i != 0
         || !UtilComponent279.checkCondition_3576(d, e, g, h, 11.0F, 11.0F)
            && !UtilComponent279.checkCondition_3576(d, e, vector4f.x, vector4f.y, vector4f.z, vector4f.w)) {
         if (i == 2 && UtilComponent279.checkCondition_3576(d, e, vector4f.x, vector4f.y, vector4f.z, vector4f.w)) {
            this.setting.reset();
            this.performAction_2919();
            return true;
         } else if (this.flagField10) {
            this.flagField10 = false;
            return true;
         } else {
            return false;
         }
      } else {
         this.flagField10 = !this.flagField10;
         if (this.flagField10) {
            this.performAction_2919();
         }

         return true;
      }
   }

   @Override
   public boolean checkCondition_2911(double d, double e, int i) {
      if (this.dragModeField9 != ColorElement.DragMode.NONE) {
         this.dragModeField9 = ColorElement.DragMode.NONE;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean checkCondition_2916(double d, double e, int i, double f, double g) {
      if (this.flagField10 && this.dragModeField9 != ColorElement.DragMode.NONE) {
         this.performAction_2914(d, e);
         return true;
      } else {
         return false;
      }
   }

   public ColorElement(ColorSetting colorSetting) {
      super(colorSetting);
      this.vector4fField3.w = 11.0F;
      this.performAction_2919();
   }

   @Override
   public void performAction_2918(DrawContext drawContext, double d, double e, float f, float g) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      int i = this.setting.getCurrentValue();
      if (i != this.integerField11 && this.dragModeField9 == ColorElement.DragMode.NONE) {
         this.performAction_2919();
      }

      float h = this.vector4fField3.y + this.vector4fField3.w / 2.0F + 0.5F;
      float j = this.vector4fField3.x + this.vector4fField3.z - 11.0F;
      float k = h - 5.5F;
      float l = 84.0F;
      float m = 66.0F;
      float n = this.vector4fField3.x + this.vector4fField3.z + 11.0F;
      if (n + l > MinecraftAccess.minecraft.getWindow().getScaledWidth() - 10.0F) {
         n = this.vector4fField3.x - l - 11.0F;
      }

      this.vector4fField1.set(n, k - 5.0F, l, m);
      this.vector4fField2.set(this.vector4fField1.x + 5.0F, this.vector4fField1.y + 5.0F, 56.0F, 56.0F);
      this.vector4fField3.set(this.vector4fField2.x + 56.0F + 5.0F, this.vector4fField2.y, 4.0F, 56.0F);
      this.vector4fField4.set(this.vector4fField3.x + 4.0F + 5.0F, this.vector4fField2.y, 4.0F, 56.0F);
      boolean bl = UtilComponent279.checkCondition_3576(d, e, this.vector4fField3.x, this.vector4fField3.y, this.vector4fField3.z, this.vector4fField3.w)
         && g >= 0.5F;
      if (g < 0.1F) {
         this.flagField10 = false;
      }

      this.performAction_3028(
         matrix3x2fStack,
         RenderComponent188.renderComponent187Field3,
         this.setting.getName(),
         this.vector4fField3.x,
         this.vector4fField3.y,
         this.vector4fField3.w,
         6.5F,
         themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(),
         j - this.vector4fField3.x - 4.0F,
         bl,
         g,
         f
      );
      renderComponent184.performAction_2577(
         matrix3x2fStack,
         j,
         k,
         11.0F,
         11.0F,
         2.0F,
         RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), 0.039215688F * g)
      );
      renderComponent184.performAction_2576(
         matrix3x2fStack,
         j,
         k,
         11.0F,
         11.0F,
         2.0F,
         0.5F,
         RenderComponent183.computeValue_2549(
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0547(),
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0548() * g
         )
      );
      RenderComponent188.renderComponent187Field1
         .performAction_2630(
            matrix3x2fStack,
            "J",
            j + (11.0F - RenderComponent188.renderComponent187Field1.computeValue_2618("J", 6.5F)) / 2.0F,
            RenderComponent188.renderComponent187Field1.computeValue_2619("J", 6.5F, h),
            6.5F,
            RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), g)
         );
      renderComponent184.performAction_2577(
         matrix3x2fStack,
         j + 11.0F - 3.0F - 1.25F,
         k + 11.0F - 3.0F - 1.25F,
         3.0F,
         3.0F,
         0.5F,
         RenderComponent183.computeValue_2549(this.setting.getCurrentValue(), g)
      );
   }

   @Override
   public void performAction_2917(DrawContext drawContext, double d, double e, float f) {
      this.getAnimationField1_3029().performAction_2535(0.0F, 1.0F, 0.25F, RenderComponent186.contract372Field16, f);
      this.getAnimationField1_3029().performAction_2528(this.flagField10);
      float g = RenderComponent186.contract372Field16.ease(this.getAnimationField1_3029().getFloatValueField4_2530());
      if (g > 0.0F) {
         Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
         RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
         ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
         this.performAction_2914(d, e);
         int i = Color.HSBtoRGB(this.floatValueField5, 1.0F, 1.0F);
         int j = this.setting.getCurrentValue() & 16777215;
         int k = RenderComponent183.computeValue_2549(16777215, g);
         int l = RenderComponent183.computeValue_2549(
            RenderComponent183.computeValue_2550(
               themeManager.computeValue_0557(ThemeInfo.BACKGROUND_GUI).computeValue_0547(),
               themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(),
               0.05F
            ),
            0.8235294F * g
         );
         float h = 0.85F + 0.15F * RenderComponent186.contract372Field19.ease(this.getAnimationField1_3029().getFloatValueField4_2530());
         float m = this.vector4fField1.x + this.vector4fField1.z / 2.0F;
         float n = this.vector4fField1.y + this.vector4fField1.w / 2.0F;
         matrix3x2fStack.pushMatrix();
         matrix3x2fStack.translate(m, n + (1.0F - g) * 6.0F);
         matrix3x2fStack.scale(h, h);
         matrix3x2fStack.translate(-m, -n);
         renderComponent184.performAction_2562(
            matrix3x2fStack, this.vector4fField1.x, this.vector4fField1.y, this.vector4fField1.z, this.vector4fField1.w, 4.0F, l, g
         );
         renderComponent184.performAction_2576(
            matrix3x2fStack,
            this.vector4fField1.x,
            this.vector4fField1.y,
            this.vector4fField1.z,
            this.vector4fField1.w,
            4.0F,
            0.5F,
            RenderComponent183.computeValue_2549(
               themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0547(),
               themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0548() * g
            )
         );
         renderComponent184.performAction_2581(
            matrix3x2fStack,
            this.vector4fField2.x,
            this.vector4fField2.y,
            this.vector4fField2.z,
            this.vector4fField2.w,
            2.0F,
            RenderComponent183.computeValue_2549(16777215, g),
            RenderComponent183.computeValue_2549(i, g),
            RenderComponent183.computeValue_2549(0, g),
            RenderComponent183.computeValue_2549(0, g)
         );
         renderComponent184.performAction_2576(
            matrix3x2fStack,
            this.vector4fField2.x,
            this.vector4fField2.y,
            this.vector4fField2.z,
            this.vector4fField2.w,
            2.0F,
            0.5F,
            RenderComponent183.computeValue_2549(
               themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0547(),
               themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0548() * g
            )
         );
         float o = UtilComponent279.computeValue_3577(
            this.vector4fField2.x + this.floatValueField6 * this.vector4fField2.z,
            this.vector4fField2.x + 2.0F,
            this.vector4fField2.x + this.vector4fField2.z - 2.0F
         );
         float p = UtilComponent279.computeValue_3577(
            this.vector4fField2.y + (1.0F - this.floatValueField7) * this.vector4fField2.w,
            this.vector4fField2.y + 2.0F,
            this.vector4fField2.y + this.vector4fField2.w - 2.0F
         );
         renderComponent184.performAction_2576(matrix3x2fStack, o - 2.5F, p - 2.5F, 5.0F, 5.0F, 2.5F, 0.75F, k);
         renderComponent184.performAction_2576(matrix3x2fStack, o - 2.5F, p - 2.5F, 5.0F, 5.0F, 2.5F, 0.5F, RenderComponent183.computeValue_2549(0, 0.5F * g));
         float q = this.vector4fField3.z + 2.0F;
         renderComponent184.performAction_2585(
            matrix3x2fStack,
            Identifier.of("neneclient", "pictures/color.png"),
            this.vector4fField3.x,
            this.vector4fField3.y,
            this.vector4fField3.z,
            this.vector4fField3.w,
            this.vector4fField3.z / 4.0F,
            RenderComponent183.computeValue_2549(16777215, g)
         );
         renderComponent184.performAction_2577(
            matrix3x2fStack, this.vector4fField3.x - 1.0F, this.vector4fField3.y + this.floatValueField5 * this.vector4fField3.w - 0.5F, q, 1.0F, 0.5F, k
         );
         renderComponent184.performAction_2585(
            matrix3x2fStack,
            Identifier.of("neneclient", "pictures/opacity.png"),
            this.vector4fField4.x,
            this.vector4fField4.y,
            this.vector4fField4.z,
            this.vector4fField4.w,
            this.vector4fField4.z / 4.0F,
            RenderComponent183.computeValue_2549(16777215, 0.019607844F * g)
         );
         renderComponent184.performAction_2581(
            matrix3x2fStack,
            this.vector4fField4.x,
            this.vector4fField4.y,
            this.vector4fField4.z,
            this.vector4fField4.w,
            this.vector4fField4.z / 4.0F,
            RenderComponent183.computeValue_2549(j, g),
            RenderComponent183.computeValue_2549(j, g),
            RenderComponent183.computeValue_2549(j, 0.0F),
            RenderComponent183.computeValue_2549(j, 0.0F)
         );
         renderComponent184.performAction_2577(
            matrix3x2fStack,
            this.vector4fField4.x - 1.0F,
            this.vector4fField4.y + (1.0F - this.floatValueField8) * this.vector4fField4.w - 0.5F,
            q,
            1.0F,
            0.5F,
            k
         );
         matrix3x2fStack.popMatrix();
      }
   }

   private void performAction_2914(double d, double e) {
      switch (this.dragModeField9) {
         case NONE:
            return;
         case AREA:
            this.floatValueField6 = UtilComponent279.computeValue_3577((float)(d - this.vector4fField2.x) / this.vector4fField2.z, 0.0F, 1.0F);
            this.floatValueField7 = 1.0F - UtilComponent279.computeValue_3577((float)(e - this.vector4fField2.y) / this.vector4fField2.w, 0.0F, 1.0F);
            break;
         case HUE:
            this.floatValueField5 = UtilComponent279.computeValue_3577((float)(e - this.vector4fField3.y) / this.vector4fField3.w, 0.0F, 1.0F);
            break;
         case ALPHA:
            this.floatValueField8 = 1.0F - UtilComponent279.computeValue_3577((float)(e - this.vector4fField4.y) / this.vector4fField4.w, 0.0F, 1.0F);
      }

      int i = RenderComponent183.computeValue_2549(Color.HSBtoRGB(this.floatValueField5, this.floatValueField6, this.floatValueField7), this.floatValueField8);
      this.integerField11 = i;
      this.setting.setValue(i);
   }

   private void performAction_2919() {
      int i = this.setting.getCurrentValue();
      this.integerField11 = i;
      float[] f = Color.RGBtoHSB(i >> 16 & 0xFF, i >> 8 & 0xFF, i & 0xFF, (float[])null);
      this.floatValueField5 = f[0];
      this.floatValueField6 = f[1];
      this.floatValueField7 = f[2];
      this.floatValueField8 = (i >> 24 & 0xFF) / 255.0F;
   }

   enum DragMode {
      NONE,
      AREA,
      HUE,
      ALPHA;
   }
}
