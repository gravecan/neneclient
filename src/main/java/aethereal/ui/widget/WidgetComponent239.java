package aethereal.ui.widget;

import aethereal.core.NeneClient;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.util.UtilComponent279;
import java.util.List;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;

public class WidgetComponent239 {
   private static final Identifier WHITE_TEX = Identifier.of("neneclient", "textures/white.png");

   private WidgetComponent239() {
   }

   public static void performAction_3317(List<WidgetComponent239.NestedType397> list, float f, float g) {
      if (list != null) {
         list.add(new WidgetComponent239.NestedType397(f, g));
      }
   }

   public static void performAction_3318(Matrix3x2fStack matrix3x2fStack, float f, List<WidgetComponent239.NestedType397> list) {
      if (list != null && !list.isEmpty()) {
         for (int i = list.size() - 1; i >= 0; i--) {
            if (((WidgetComponent239.NestedType397)list.get(i)).checkCondition_3320(matrix3x2fStack, f)) {
               list.remove(i);
            }
         }
      }
   }

   public static final class NestedType397 {
      private final Animation animationField1 = new Animation();
      private final long longValueField2 = System.nanoTime() + 300000000L;
      private final float floatValueField3;
      private final float floatValueField4;
      private boolean flagField5;

      NestedType397(float f, float g) {
         this.floatValueField3 = f;
         this.floatValueField4 = g;
      }

      boolean checkCondition_3320(Matrix3x2fStack matrix3x2fStack, float f) {
         this.animationField1.performAction_2535(0.0F, 1.0F, 0.2F, RenderComponent186.contract372Field8, f);
         if (!this.flagField5 && System.nanoTime() >= this.longValueField2) {
            this.flagField5 = true;
         }

         this.animationField1.performAction_2528(!this.flagField5);
         float g = UtilComponent279.computeValue_3577(this.animationField1.getFloatValueField4_2530(), 0.0F, 1.0F);
         float h = this.flagField5 ? g : this.computeValue_3319(g);
         float i = 4.0F * Math.max(1.0E-4F, h);
         int j = RenderComponent183.computeValue_2558(255, 255, 255, Math.round(250.0F * g));
         float k = matrix3x2fStack != null ? (float)Math.hypot(matrix3x2fStack.m00, matrix3x2fStack.m01) : 1.0F;
         float l = matrix3x2fStack != null ? (float)Math.toDegrees(Math.atan2(matrix3x2fStack.m01, matrix3x2fStack.m00)) : 0.0F;
         float m = 0.5F;

         for (int n = 0; n < 4; n++) {
            float o = 45.0F + 90.0F * n;
            float p = (float)Math.toRadians(o);
            float q = i;
            float r = this.floatValueField3 + (float)Math.cos(p) * q;
            float s = this.floatValueField4 + (float)Math.sin(p) * q;
            float t = matrix3x2fStack != null ? matrix3x2fStack.m00 * r + matrix3x2fStack.m10 * s + matrix3x2fStack.m20 : r;
            float u = matrix3x2fStack != null ? matrix3x2fStack.m01 * r + matrix3x2fStack.m11 * s + matrix3x2fStack.m21 : s;
            float v = i * k;
            float w = m * k;
            float x = w * 0.5F;
            NeneClient.getInstance()
               .getProcessors()
               .getRenderComponent184Field3_0677()
               .getTexturePipeline_2566()
               .performAction_2816(
                  WidgetComponent239.WHITE_TEX, t - v * 0.5F, u - w * 0.5F, v, w, 0.0F, 0.0F, 1.0F, 1.0F, new int[]{j}, new float[]{x, x, x, x}, 0.5F, o + l
               );
         }

         return this.flagField5 && g <= 0.01F;
      }

      private float computeValue_3319(float f) {
         if (f <= 0.0F) {
            return 0.0F;
         } else if (f < 0.6F) {
            return f / 0.6F;
         } else {
            return f < 0.8F ? 1.0F + (f - 0.6F) / 0.5F * 0.5F : 1.2F - (f - 0.8F) / 0.2F * 0.2F;
         }
      }
   }
}
