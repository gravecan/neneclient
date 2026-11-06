package aethereal.render;

import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix4f;
import platform.client.processors.draw.fonts.FontsComponent286;

public class RenderComponent190 {
   private final int integerField1;
   private final float floatValueField2;
   private final float floatValueField3;
   private final float floatValueField4;
   private final float floatValueField5;
   private final float floatValueField6;
   private final float floatValueField7;
   private final float floatValueField8;
   private final float floatValueField9;

   public RenderComponent190(FontsComponent286.NestedType414 nestedType414, float f, float g) {
      this.integerField1 = nestedType414.getUnicode_3637();
      this.floatValueField6 = nestedType414.getAdvance_3635();
      FontsComponent286.NestedType413 nestedType413 = nestedType414.getAtlasBounds_3634();
      if (nestedType413 != null) {
         this.floatValueField2 = nestedType413.getLeft_3630() / f;
         this.floatValueField3 = nestedType413.getRight_3632() / f;
         this.floatValueField4 = 1.0F - nestedType413.getTop_3631() / g;
         this.floatValueField5 = 1.0F - nestedType413.getBottom_3633() / g;
      } else {
         this.floatValueField2 = 0.0F;
         this.floatValueField3 = 0.0F;
         this.floatValueField4 = 0.0F;
         this.floatValueField5 = 0.0F;
      }

      FontsComponent286.NestedType413 nestedType4131 = nestedType414.getPlaneBounds_3636();
      if (nestedType4131 != null) {
         this.floatValueField8 = nestedType4131.getRight_3632() - nestedType4131.getLeft_3630();
         this.floatValueField9 = nestedType4131.getTop_3631() - nestedType4131.getBottom_3633();
         this.floatValueField7 = nestedType4131.getTop_3631();
      } else {
         this.floatValueField8 = 0.0F;
         this.floatValueField9 = 0.0F;
         this.floatValueField7 = 0.0F;
      }
   }

   public float computeValue_2667(Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float g, float h, float i, int j) {
      float k = h - this.floatValueField7 * f;
      float l = this.floatValueField8 * f;
      float m = this.floatValueField9 * f;
      vertexConsumer.vertex(matrix4f, g, k, i).texture(this.floatValueField2, this.floatValueField4).color(j);
      vertexConsumer.vertex(matrix4f, g, k + m, i).texture(this.floatValueField2, this.floatValueField5).color(j);
      vertexConsumer.vertex(matrix4f, g + l, k + m, i).texture(this.floatValueField3, this.floatValueField5).color(j);
      vertexConsumer.vertex(matrix4f, g + l, k, i).texture(this.floatValueField3, this.floatValueField4).color(j);
      return this.floatValueField6 * f;
   }

   public float computeValue_2665(float f) {
      return this.floatValueField6 * f;
   }

   public float computeValue_2666(float f) {
      return this.floatValueField8 * f;
   }

   public float getFloatValueField7_2663() {
      return this.floatValueField7;
   }

   public float getFloatValueField9_2664() {
      return this.floatValueField9;
   }

   public int getIntegerField1_2668() {
      return this.integerField1;
   }

   public static final class NestedType373 {
      private final char characterField1;
      private final int color;

      public NestedType373(char c, int i) {
         this.characterField1 = c;
         this.color = i;
      }

      public char getCharacterField1_2670() {
         return this.characterField1;
      }

      public int getColor_2669() {
         return this.color;
      }
   }
}
