package aethereal.render;

import aethereal.render.pipeline.PipelineComponent197;
import aethereal.render.pipeline.PipelineComponent199;
import aethereal.render.pipeline.PipelineComponent200;
import java.util.Optional;
import net.minecraft.text.Text;
import org.joml.Matrix3x2f;

public class RenderComponent187 {
   private static PipelineComponent199 renderer;
   private final String name;

   public static PipelineComponent199 computeValue_2623() {
      if (renderer == null) {
         renderer = new PipelineComponent199();
         renderer.performAction_2789("icons", "icons");
         renderer.performAction_2789("sf_regular", "sf_regular");
         renderer.performAction_2789("onest_regular", "onest_regular");
         renderer.performAction_2789("sf_medium", "sf_medium");
         renderer.performAction_2789("gt_regular", "gt_regular");
         renderer.performAction_2788();
      }

      return renderer;
   }

   public RenderComponent187(String string) {
      this.name = string;
   }

   public String getName_2636() {
      return this.name;
   }

   public float computeValue_2626(String string, float f) {
      return string != null && !string.isEmpty() ? computeValue_2623().computeValue_2782(this.name, string, f) : 0.0F;
   }

   public float computeValue_2628(Text text, float f) {
      return text == null ? 0.0F : computeValue_2623().computeValue_2782(this.name, text.getString(), f);
   }

   public float computeValue_2622(float f) {
      return computeValue_2623().computeValue_2799(this.name, f);
   }

   public float computeValue_2618(String string, float f) {
      return this.computeValue_2626(string, f);
   }

   public float computeValue_2627(String string, float f, float g) {
      return this.computeValue_2626(string, f);
   }

   public float computeValue_2619(String string, float f, float g) {
      return g - this.computeValue_2622(f) / 2.0F - 0.5F;
   }

   public float computeValue_2624(float f, float g) {
      return g - this.computeValue_2622(f) / 2.0F - 0.5F;
   }

   public static float[] computeValue_2629(Matrix3x2f matrix3x2f, float f, float g, float h) {
      if (matrix3x2f == null) {
         return new float[]{f, g, h, 0.0F, 1.0F};
      }

      float i = matrix3x2f.m00 * f + matrix3x2f.m10 * g + matrix3x2f.m20;
      float j = matrix3x2f.m01 * f + matrix3x2f.m11 * g + matrix3x2f.m21;
      float k = matrix3x2f.m00 * (f + 1.0F) + matrix3x2f.m10 * g + matrix3x2f.m20;
      float l = matrix3x2f.m01 * (f + 1.0F) + matrix3x2f.m11 * g + matrix3x2f.m21;
      float m = k - i;
      float n = l - j;
      float o = (float)Math.hypot(m, n);
      if (o == 0.0F) {
         o = 1.0F;
      }

      float p = (float)Math.toDegrees(Math.atan2(n, m));
      return new float[]{i, j, h * o, p, o};
   }

   public void performAction_2630(Matrix3x2f matrix3x2f, String string, float f, float g, float h, int i) {
      if (string != null && !string.isEmpty()) {
         float[] j = computeValue_2629(matrix3x2f, f, g, h);
         computeValue_2623().performAction_2795(this.name, string, j[0], j[1], j[2], i, j[3]);
      }
   }

   public void performAction_2635(Matrix3x2f matrix3x2f, Text text, float f, float g, float h, float n, float i) {
      if (text != null) {
         int j = (int)(Math.min(1.0F, Math.max(0.0F, i)) * 255.0F);
         if (j > 0) {
            float k = f;
            String string = text.getContent().visit(string2 -> Optional.of(string2)).orElse("");
            if (!string.isEmpty()) {
               int l = text.getStyle().getColor() != null ? text.getStyle().getColor().getRgb() : -1;
               l = l & 16777215 | j << 24;
               this.performAction_2630(matrix3x2f, string, k, g, h, l);
               k += this.computeValue_2626(string, h);
            }

            for (Text text1 : text.getSiblings()) {
               String string1 = text1.getString();
               int m = text1.getStyle().getColor() != null ? text1.getStyle().getColor().getRgb() : -1;
               m = m & 16777215 | j << 24;
               this.performAction_2630(matrix3x2f, string1, k, g, h, m);
               k += this.computeValue_2626(string1, h);
            }
         }
      }
   }

   public void performAction_2634(Matrix3x2f matrix3x2f, Text text, float f, float g, float h) {
      this.performAction_2635(matrix3x2f, text, f, g, h, 0.0F, 1.0F);
   }

   public void performAction_2631(Matrix3x2f matrix3x2f, String string, float f, float g, float h, int i, float j) {
      if (string != null && !string.isEmpty() && !(j <= 0.0F)) {
         float[] k = computeValue_2629(matrix3x2f, f, g, h);
         computeValue_2623().performAction_2793(this.name, string, k[0], k[1], k[2], j * k[4], i);
      }
   }

   public void performAction_2632(Matrix3x2f matrix3x2f, String string, float f, float g, float h, int i, float k, float j) {
      this.performAction_2631(matrix3x2f, string, f, g, h, i, j);
   }

   public float computeValue_2633(Matrix3x2f matrix3x2f, String string, float f, float g, float h, int i, float j, boolean bl, float k, float l) {
      this.performAction_2631(matrix3x2f, string, f, g, h, i, j);
      return k;
   }

   public void performAction_2620(Matrix3x2f matrix3x2f, String string, float f, float g, float h, int i) {
      float j = this.computeValue_2626(string, h);
      this.performAction_2630(matrix3x2f, string, f - j / 2.0F, g, h, i);
   }

   public void performAction_2621(Matrix3x2f matrix3x2f, String string, float f, float g, float h, int i, float j) {
      if (string != null && !string.isEmpty()) {
         PipelineComponent197 pipelineComponent197 = computeValue_2623().computeValue_2791(this.name);
         if (pipelineComponent197 != null) {
            pipelineComponent197.performAction_2740();
            float k = f;
            float l = g;
            float m = 1.0F;
            float n = 0.0F;
            if (matrix3x2f != null) {
               k = matrix3x2f.m00 * f + matrix3x2f.m10 * g + matrix3x2f.m20;
               l = matrix3x2f.m01 * f + matrix3x2f.m11 * g + matrix3x2f.m21;
               m = (float)Math.hypot(matrix3x2f.m00, matrix3x2f.m01);
               n = (float)Math.toDegrees(Math.atan2(matrix3x2f.m01, matrix3x2f.m00));
            }

            float o = h * m;
            float p = j + n;
            float q = computeValue_2623().computeValue_2782(this.name, string, o);
            float r = pipelineComponent197.getLineHeight_2749() * (o / pipelineComponent197.getFontSize_2742());
            float s = q * 0.5F;
            float t = r * 0.5F;
            PipelineComponent200 pipelineComponent200 = pipelineComponent197.computeValue_2745(string.codePointAt(0));
            if (string.length() == 1 && pipelineComponent200 != null) {
               float u = o / pipelineComponent197.getFontSize_2742();
               s = (pipelineComponent200.xOffset + pipelineComponent200.width * 0.5F) * u;
               t = (pipelineComponent200.yOffset + pipelineComponent200.height * 0.5F) * u;
            }

            computeValue_2623().getPipeline_2790().performAction_2766(pipelineComponent197, string, k - s, l - t, o, i, 0.0F, 0, p, k, l);
         }
      }
   }
}
