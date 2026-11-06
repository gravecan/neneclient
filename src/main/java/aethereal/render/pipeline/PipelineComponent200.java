package aethereal.render.pipeline;

public class PipelineComponent200 {
   public final int integerField1;
   public final float floatValueField2;
   public final float floatValueField3;
   public final float width;
   public final float height;
   public final float xOffset;
   public final float yOffset;
   public final float xAdvance;
   public final float u0;
   public final float v0;
   public final float u1;
   public final float v1;

   public PipelineComponent200(int i, float f, float g, float h, float j, float k, float l, float m, float n, float o) {
      this.integerField1 = i;
      this.floatValueField2 = f;
      this.floatValueField3 = g;
      this.width = h;
      this.height = j;
      this.xOffset = k;
      this.yOffset = l;
      this.xAdvance = m;
      this.u0 = f / n;
      this.v0 = g / o;
      this.u1 = (f + h) / n;
      this.v1 = (g + j) / o;
   }
}
