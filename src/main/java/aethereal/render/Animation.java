package aethereal.render;

import aethereal.core.MinecraftAccess;
import aethereal.util.UtilComponent279;

public class Animation implements MinecraftAccess {
   private float floatValueField1;
   private float floatValueField2;
   private float floatValueField3;
   private float floatValueField4;
   private float floatValueField5 = 0.0F;
   private float floatValueField6 = 1.0F;
   private long longValueField7 = System.currentTimeMillis();

   public void setFloatValueField1_2526(float f) {
      this.floatValueField1 = f;
   }

   public void setFloatValueField2_2536(float f) {
      this.floatValueField2 = f;
   }

   public float getFloatValueField1_2527() {
      return this.floatValueField1;
   }

   public float getFloatValueField2_2529() {
      return this.floatValueField2;
   }

   public float getFloatValueField4_2530() {
      return this.floatValueField4;
   }

   public void setFloatValueField4_2525(float f) {
      this.floatValueField4 = f;
   }

   public void performAction_2528(boolean bl) {
      this.floatValueField2 = this.floatValueField1;
      float f = bl ? 1.0F : -1.0F;
      this.floatValueField1 = UtilComponent279.computeValue_3577(
         this.floatValueField1 + f * this.floatValueField3 * 20.0F * this.computeValue_2531(), this.floatValueField5, this.floatValueField6
      );
   }

   public void performAction_2535(float f, float g, float h, RenderComponent186.Contract372 contract372, float i) {
      this.floatValueField3 = h;
      this.floatValueField5 = f;
      this.floatValueField6 = g;
      this.floatValueField4 = UtilComponent279.computeValue_3583(this.floatValueField2, this.floatValueField1, i);
   }

   public void performAction_2532(float f) {
      this.floatValueField6 += f;
   }

   public void performAction_2524(float f) {
      this.floatValueField6 = f;
      this.floatValueField1 = f;
   }

   public float computeValue_2534(float f, float g, float h) {
      this.floatValueField6 = UtilComponent279.computeValue_3577(this.floatValueField6, f, g);
      this.floatValueField1 = UtilComponent279.computeValue_3586(this.floatValueField1, this.floatValueField6, h);
      return this.floatValueField1;
   }

   public float computeValue_2533(float f, float g) {
      return this.computeValue_2534(f, f, g);
   }

   private float computeValue_2531() {
      long l = System.currentTimeMillis();
      float f = (float)(l - this.longValueField7) / 1000.0F;
      this.longValueField7 = l;
      return f;
   }
}
