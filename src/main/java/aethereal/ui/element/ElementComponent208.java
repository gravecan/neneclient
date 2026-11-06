package aethereal.ui.element;

import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent188;
import aethereal.util.UtilComponent279;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix3x2fStack;

public class ElementComponent208 {
   private final float[] floatValueArrayField1 = new float[3];
   private boolean flagField2;
   private float floatValueField3;
   private float floatValueField4;
   private final List<ElementComponent209> listField5;

   public ElementComponent208(ElementComponent209... elementComponent209s) {
      this.listField5 = Arrays.asList(elementComponent209s);
   }

   public void performAction_2988(int i, int j) {
      float f = j * 0.85F;
      this.floatValueField3 = (i - 79.0F) / 2.0F;
      this.floatValueField4 = f - 19.5F - 5.0F;
      this.performAction_2991(i, f, 5.0F, this.listField5.toArray(new ElementComponent209[0]));
   }

   public void performAction_2987(DrawContext drawContext, int i, int j, float f) {
      this.performAction_2992(drawContext, i, j, f, 1.0F);
   }

   public void performAction_2992(DrawContext drawContext, int i, int j, float f, float g) {
      this.performAction_2986(drawContext, g);

      for (ElementComponent209 elementComponent209 : this.listField5) {
         elementComponent209.performAction_3000(drawContext, i, j, f, g);
      }
   }

   private void performAction_2986(DrawContext drawContext, float f) {
      float[] g = this.floatValueArrayField1;
      g[1] += (this.floatValueArrayField1[0] - this.floatValueArrayField1[1]) * 0.25F;
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      float h = this.floatValueField3 + 1.75F + this.floatValueArrayField1[1] * Math.max(1.0F, 59.5F);
      float i = this.floatValueField4 + 1.75F;
      float j = h + 8.0F;
      float k = i + 8.0F;
      renderComponent184.performAction_2562(
         matrix3x2fStack,
         this.floatValueField3,
         this.floatValueField4,
         79.0F,
         19.5F,
         8.0F,
         RenderComponent183.computeValue_2558(11, 11, 13, (int)(150.0F * f)),
         1.0F
      );
      renderComponent184.performAction_2576(
         matrix3x2fStack,
         this.floatValueField3,
         this.floatValueField4,
         79.0F,
         19.5F,
         8.0F,
         0.5F,
         RenderComponent183.computeValue_2558(255, 255, 255, (int)(15.0F * f))
      );
      float l = this.floatValueField3 + 9.0F;
      float m = h - 3.0F - l;
      RenderComponent188.renderComponent187Field5
         .performAction_2631(
            matrix3x2fStack,
            "Quit the game",
            l,
            this.floatValueField4 + 6.25F - 0.5F,
            7.0F,
            RenderComponent183.computeValue_2549(RenderComponent183.computeValue_2558(220, 80, 80, 255), this.floatValueArrayField1[1] * f),
            m
         );
      int n = RenderComponent183.computeValue_2550(
         RenderComponent183.computeValue_2558(255, 255, 255, 13), RenderComponent183.computeValue_2558(220, 80, 80, 40), this.floatValueArrayField1[1]
      );
      renderComponent184.performAction_2577(
         matrix3x2fStack, h, i, 16.0F, 16.0F, 7.0F, RenderComponent183.computeValue_2549(n, RenderComponent183.computeValue_2555(n)[3] / 255.0F * f)
      );
      matrix3x2fStack.pushMatrix();
      matrix3x2fStack.translate(j, k);
      matrix3x2fStack.rotate((float)Math.toRadians(-90.0F + 180.0F * this.floatValueArrayField1[1]));
      matrix3x2fStack.translate(-j, -k);
      RenderComponent188.renderComponent187Field1
         .performAction_2630(
            matrix3x2fStack,
            "c",
            j - RenderComponent188.renderComponent187Field1.computeValue_2626("c", 8.5F) / 2.0F + 1.0F,
            k - 4.5F,
            8.5F,
            RenderComponent183.computeValue_2549(
               RenderComponent183.computeValue_2550(-1, RenderComponent183.computeValue_2558(220, 80, 80, 255), this.floatValueArrayField1[1]), f
            )
         );
      matrix3x2fStack.popMatrix();
   }

   public boolean checkCondition_2985(double d, double e) {
      float f = this.floatValueField3 + 1.75F + this.floatValueArrayField1[0] * Math.max(1.0F, 59.5F);
      float g = this.floatValueField4 + 1.75F;
      if (UtilComponent279.checkCondition_3576(d, e, f, g, 16.0F, 16.0F)) {
         this.floatValueArrayField1[2] = (float)d - f;
         this.flagField2 = true;
         return true;
      }

      for (ElementComponent209 elementComponent209 : this.listField5) {
         if (elementComponent209.getAction_2998() != null
            && UtilComponent279.checkCondition_3576(
               d,
               e,
               elementComponent209.getFloatValueField2_2994(),
               elementComponent209.getFloatValueField3_2993(),
               elementComponent209.getWidth_2995(),
               elementComponent209.getHeight_2996()
            )) {
            elementComponent209.getAction_2998().run();
            return true;
         }
      }

      return false;
   }

   public boolean checkCondition_2990(double d) {
      if (this.flagField2) {
         this.floatValueArrayField1[0] = MathHelper.clamp(
            ((float)d - this.floatValueArrayField1[2] - this.floatValueField3 - 1.75F) / Math.max(1.0F, 59.5F), 0.0F, 1.0F
         );
         return false;
      } else {
         return false;
      }
   }

   public boolean checkCondition_2989() {
      if (this.flagField2) {
         this.flagField2 = false;
         this.floatValueArrayField1[0] = this.floatValueArrayField1[0] >= 0.95F ? this.floatValueArrayField1[0] : 0.0F;
         if (this.floatValueArrayField1[0] > 0.0F) {
            MinecraftAccess.minecraft.scheduleStop();
            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void performAction_2991(int i, float f, float g, ElementComponent209... elementComponent209s) {
      float h = 0.0F;

      for (ElementComponent209 elementComponent209 : elementComponent209s) {
         h += elementComponent209.getWidth_2995();
      }

      float j = (i - (h + g * (elementComponent209s.length - 1))) / 2.0F;

      for (ElementComponent209 elementComponent2091 : elementComponent209s) {
         elementComponent2091.performAction_2999(j, f);
         j += elementComponent2091.getWidth_2995() + g;
      }
   }
}
