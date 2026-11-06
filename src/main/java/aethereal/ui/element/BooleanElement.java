package aethereal.ui.element;

import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.setting.BooleanSetting;
import aethereal.util.UtilComponent279;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;
import org.joml.Vector4f;

public class BooleanElement extends SettingElement<BooleanSetting> {
   @Override
   public boolean checkCondition_2915(double d, double e, int i) {
      Vector4f vector4f = this.vector4fField3;
      BooleanSetting booleanSetting = this.setting;
      if (!UtilComponent279.checkCondition_3576(d, e, vector4f.x, vector4f.y, vector4f.z, vector4f.w)) {
         return false;
      }

      if (i != 0) {
         if (i != 2) {
            return false;
         }

         if (!(booleanSetting instanceof BooleanSetting)) {
            throw new ClassCastException();
         }

         booleanSetting.reset();
         return true;
      } else if (!(booleanSetting instanceof BooleanSetting booleanSetting1)) {
         throw new ClassCastException();
      } else {
         Boolean boolean_ = booleanSetting1.getCurrentValue();
         if (!(boolean_ instanceof Boolean)) {
            throw new ClassCastException();
         }

         booleanSetting1.setValue(!boolean_);
         return true;
      }
   }

   public BooleanElement(BooleanSetting booleanSetting) {
      super(booleanSetting);
      this.vector4fField3.w = 11.0F;
   }

   @Override
   public void performAction_2918(DrawContext drawContext, double d, double e, float f, float g) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      this.getAnimationField1_3029().performAction_2528(this.setting.getCurrentValue());
      this.getAnimationField1_3029().performAction_2535(0.0F, 1.0F, 0.5F, RenderComponent186.contract372Field9, f);
      float h = this.getAnimationField1_3029().getFloatValueField4_2530();
      float i = 1.0F - h;
      float j = this.vector4fField3.y + this.vector4fField3.w / 2.0F + 0.5F;
      boolean bl = UtilComponent279.checkCondition_3576(d, e, this.vector4fField3.x, this.vector4fField3.y, this.vector4fField3.z, this.vector4fField3.w)
         && g >= 1.0F;
      this.performAction_3028(
         matrix3x2fStack,
         RenderComponent188.renderComponent187Field3,
         this.setting.getName(),
         this.vector4fField3.x,
         this.vector4fField3.y,
         this.vector4fField3.w,
         6.5F,
         themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(),
         this.vector4fField3.z - 11.0F - 4.0F,
         bl,
         g,
         f
      );
      float k = this.vector4fField3.x + this.vector4fField3.z - 11.0F;
      float l = j - 5.5F;
      renderComponent184.performAction_2577(
         matrix3x2fStack,
         k,
         l,
         11.0F,
         11.0F,
         3.0F,
         RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), 0.039215688F * g)
      );
      renderComponent184.performAction_2576(
         matrix3x2fStack,
         k,
         l,
         11.0F,
         11.0F,
         3.0F,
         0.5F,
         RenderComponent183.computeValue_2549(
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0547(),
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0548() * g
         )
      );
      if (i > 0.0F) {
         RenderComponent188.renderComponent187Field1
            .performAction_2630(
               matrix3x2fStack,
               "u",
               k + (11.0F - RenderComponent188.renderComponent187Field1.computeValue_2618("u", 6.0F)) / 2.0F + 0.25F,
               RenderComponent188.renderComponent187Field1.computeValue_2619("u", 6.0F, j),
               6.0F,
               RenderComponent183.computeValue_2549(RenderComponent183.computeValue_2558(200, 25, 25, 255), g * i)
            );
      }

      if (h > 0.0F) {
         RenderComponent188.renderComponent187Field1
            .performAction_2630(
               matrix3x2fStack,
               "m",
               k + (11.0F - RenderComponent188.renderComponent187Field1.computeValue_2618("m", 9.0F)) / 2.0F,
               RenderComponent188.renderComponent187Field1.computeValue_2619("m", 9.0F, j),
               9.0F,
               RenderComponent183.computeValue_2549(RenderComponent183.computeValue_2558(130, 220, 150, 255), g * h)
            );
      }
   }

   @Override
   public void performAction_2984(DrawEvent drawEvent, float f, float g, float h, float i) {
      this.getAnimationField1_3029().performAction_2528(this.setting.getCurrentValue());
      this.getAnimationField1_3029().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      float j = f + 19.5F;
      float k = f + h - 11.0F - 5.0F;
      float l = g + 2.25F;
      int m = themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
      RenderComponent188.renderComponent187Field1
         .performAction_2630(
            drawEvent.getStack_1005(),
            "g",
            f + 5.0F,
            g + (12.0F - RenderComponent188.renderComponent187Field1.computeValue_2622(6.5F)) / 2.0F,
            6.5F,
            RenderComponent183.computeValue_2549(m, i)
         );
      drawEvent.getRenderComponent184Field1_0998()
         .performAction_2577(
            drawEvent.getContext_1000().getMatrices(),
            f + 15.5F,
            g + 3.0F,
            0.75F,
            6.0F,
            0.0F,
            RenderComponent183.computeValue_2549(RenderComponent183.computeValue_2558(200, 200, 200, 255), 0.5F * i)
         );
      RenderComponent188.renderComponent187Field5
         .performAction_2630(
            drawEvent.getStack_1005(),
            this.setting.getName(),
            j,
            g + (12.0F - RenderComponent188.renderComponent187Field5.computeValue_2622(6.5F)) / 2.0F - 0.5F,
            6.5F,
            RenderComponent183.computeValue_2549(-1, i)
         );
      float n = this.getAnimationField1_3029().getFloatValueField4_2530();
      drawEvent.getRenderComponent184Field1_0998()
         .performAction_2577(drawEvent.getStack_1005(), k, l, 11.0F, 7.5F, 2.5F, RenderComponent183.computeValue_2549(m, n * i));
      drawEvent.getRenderComponent184Field1_0998()
         .performAction_2576(
            drawEvent.getStack_1005(),
            k,
            l,
            11.0F,
            7.5F,
            2.5F,
            0.3F,
            RenderComponent183.computeValue_2549(
               themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0547(),
               themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0548() * i
            )
         );
      drawEvent.getRenderComponent184Field1_0998()
         .performAction_2577(
            drawEvent.getStack_1005(),
            k + 1.5F + 3.5F * n,
            l + 1.5F,
            4.5F,
            4.5F,
            1.25F,
            RenderComponent183.computeValue_2549(
               RenderComponent183.computeValue_2550(
                  RenderComponent183.computeValue_2558(150, 150, 155, 255), RenderComponent183.computeValue_2558(255, 255, 255, 255), n
               ),
               i
            )
         );
   }
}
