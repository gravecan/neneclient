package aethereal.render;

import aethereal.config.PersistentProcessor;
import aethereal.core.MinecraftAccess;
import aethereal.render.pipeline.PipelineComponent201;
import aethereal.render.pipeline.PipelineComponent202;
import aethereal.render.pipeline.PipelineComponent205;
import aethereal.ui.shader.ShaderComponent229;
import aethereal.ui.shader.ShaderComponent230;
import aethereal.ui.shader.ShaderComponent233;
import aethereal.ui.shader.ShaderComponent234;
import aethereal.ui.shader.ShaderComponent236;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2f;
import org.joml.Vector4f;

public class RenderComponent184 extends PersistentProcessor implements MinecraftAccess {
   private float floatValueField1 = 1.0F;
   private final ShaderComponent234 shaderComponent234Field2 = new ShaderComponent234();
   private final ShaderComponent236 shaderComponent236Field3 = new ShaderComponent236();
   private final ShaderComponent230 shaderComponent230Field4 = new ShaderComponent230();
   private final ShaderComponent229 shaderComponent229Field5 = new ShaderComponent229();
   private final ShaderComponent233 shaderComponent233Field6 = new ShaderComponent233();
   private final PipelineComponent202 rectPipeline = new PipelineComponent202();
   private final PipelineComponent201 outlinePipeline = new PipelineComponent201();
   private final PipelineComponent205 texturePipeline = new PipelineComponent205();
   private final float[] transformScratch = new float[6];
   private final int[] colorScratch = new int[1];
   private final int[] gradientScratch = new int[4];
   private final float[] radiusScratch = new float[4];
   private final float[] thicknessScratch = new float[4];

   @Override
   public void load() {
   }

   public void setFloatValueField1_2572(float f) {
      this.floatValueField1 = f;
   }

   public float getFloatValueField1_2563() {
      return this.floatValueField1;
   }

   public ShaderComponent234 getShaderComponent234Field2_2570() {
      return this.shaderComponent234Field2;
   }

   public ShaderComponent236 getShaderComponent236Field3_2571() {
      return this.shaderComponent236Field3;
   }

   public ShaderComponent230 getShaderComponent230Field4_2568() {
      return this.shaderComponent230Field4;
   }

   public ShaderComponent229 getShaderComponent229Field5_2567() {
      return this.shaderComponent229Field5;
   }

   public ShaderComponent233 getShaderComponent233Field6_2569() {
      return this.shaderComponent233Field6;
   }

   public PipelineComponent202 getRectPipeline_2565() {
      return this.rectPipeline;
   }

   public PipelineComponent201 getOutlinePipeline_2564() {
      return this.outlinePipeline;
   }

   public PipelineComponent205 getTexturePipeline_2566() {
      return this.texturePipeline;
   }

   @Override
   public void save() {
      this.rectPipeline.close();
      this.outlinePipeline.close();
      this.texturePipeline.close();
   }

   public static float[] computeValue_2575(Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j) {
      if (matrix3x2f == null) {
         return new float[]{f, g, h, i, j, 1.0F};
      }

      float k = matrix3x2f.m00 * f + matrix3x2f.m10 * g + matrix3x2f.m20;
      float l = matrix3x2f.m01 * f + matrix3x2f.m11 * g + matrix3x2f.m21;
      float m = matrix3x2f.m00 * (f + h) + matrix3x2f.m10 * (g + i) + matrix3x2f.m20;
      float n = matrix3x2f.m01 * (f + h) + matrix3x2f.m11 * (g + i) + matrix3x2f.m21;
      float o = Math.min(k, m);
      float p = Math.min(l, n);
      float q = Math.abs(m - k);
      float r = Math.abs(n - l);
      float s = h > 0.0F ? q / h : 1.0F;
      return new float[]{o, p, q, r, j * s, s};
   }

   private float[] computeValue_2560(Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j) {
      float[] k = this.transformScratch;
      if (matrix3x2f == null) {
         k[0] = f;
         k[1] = g;
         k[2] = h;
         k[3] = i;
         k[4] = j;
         k[5] = 1.0F;
         return k;
      } else {
         float l = matrix3x2f.m00 * f + matrix3x2f.m10 * g + matrix3x2f.m20;
         float m = matrix3x2f.m01 * f + matrix3x2f.m11 * g + matrix3x2f.m21;
         float n = matrix3x2f.m00 * (f + h) + matrix3x2f.m10 * (g + i) + matrix3x2f.m20;
         float o = matrix3x2f.m01 * (f + h) + matrix3x2f.m11 * (g + i) + matrix3x2f.m21;
         k[0] = Math.min(l, n);
         k[1] = Math.min(m, o);
         k[2] = Math.abs(n - l);
         k[3] = Math.abs(o - m);
         k[5] = h > 0.0F ? k[2] / h : 1.0F;
         k[4] = j * k[5];
         return k;
      }
   }

   private float[] computeValue_2573(float f, float g, float h, float i) {
      this.radiusScratch[0] = f;
      this.radiusScratch[1] = g;
      this.radiusScratch[2] = h;
      this.radiusScratch[3] = i;
      return this.radiusScratch;
   }

   public void performAction_2577(Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j, int k) {
      float[] l = this.computeValue_2560(matrix3x2f, f, g, h, i, j);
      this.colorScratch[0] = k;
      this.computeValue_2573(l[4], l[4], l[4], l[4]);
      this.rectPipeline.performAction_2805(l[0], l[1], l[2], l[3], this.colorScratch, this.radiusScratch);
   }

   public void performAction_2583(Matrix3x2f matrix3x2f, float f, float g, float h, float i, Vector4f vector4f, int j) {
      float[] k = this.computeValue_2560(matrix3x2f, f, g, h, i, vector4f.x);
      float l = k[5];
      this.colorScratch[0] = j;
      this.computeValue_2573(vector4f.x * l, vector4f.y * l, vector4f.z * l, vector4f.w * l);
      this.rectPipeline.performAction_2805(k[0], k[1], k[2], k[3], this.colorScratch, this.radiusScratch);
   }

   public void performAction_2576(Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j, float k, int l) {
      float[] m = this.computeValue_2560(matrix3x2f, f, g, h, i, j);
      float n = m[5];
      this.colorScratch[0] = l;
      this.thicknessScratch[0] = this.thicknessScratch[1] = this.thicknessScratch[2] = this.thicknessScratch[3] = k * n;
      this.outlinePipeline
         .performAction_2801(m[0], m[1], m[2], m[3], this.colorScratch, this.thicknessScratch, this.computeValue_2573(m[4], m[4], m[4], m[4]), 0.5F);
   }

   public void performAction_2582(Matrix3x2f matrix3x2f, float f, float g, float h, float i, Vector4f vector4f, float j, int k) {
      float[] l = this.computeValue_2560(matrix3x2f, f, g, h, i, vector4f.x);
      float m = l[5];
      this.colorScratch[0] = k;
      this.thicknessScratch[0] = this.thicknessScratch[1] = this.thicknessScratch[2] = this.thicknessScratch[3] = j * m;
      this.outlinePipeline
         .performAction_2801(
            l[0],
            l[1],
            l[2],
            l[3],
            this.colorScratch,
            this.thicknessScratch,
            this.computeValue_2573(vector4f.x * m, vector4f.y * m, vector4f.z * m, vector4f.w * m),
            0.5F
         );
   }

   public void performAction_2585(Matrix3x2f matrix3x2f, Identifier identifier, float f, float g, float h, float i, float j, int k) {
      float[] l = this.computeValue_2560(matrix3x2f, f, g, h, i, j);
      this.colorScratch[0] = k;
      this.computeValue_2573(l[4], l[4], l[4], l[4]);
      this.texturePipeline.performAction_2824(identifier, l[0], l[1], l[2], l[3], 0.0F, 0.0F, 1.0F, 1.0F, this.colorScratch, this.radiusScratch, 0.5F);
   }

   public void performAction_2579(Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j, int k, float m, float n, float o, float p, int q) {
      float[] l = this.computeValue_2560(matrix3x2f, f, g, h, i, j);
      this.colorScratch[0] = k;
      this.computeValue_2573(l[4], l[4], l[4], l[4]);
      this.rectPipeline.performAction_2805(l[0], l[1], l[2], l[3], this.colorScratch, this.radiusScratch);
   }

   public void performAction_2586(Matrix3x2f matrix3x2f, Identifier identifier, LivingEntity livingEntity, float f, float g, float h, float i, float j, float k) {
      if (identifier != null) {
         int l = RenderComponent183.computeValue_2558(255, 255, 255, (int)(k * 255.0F));
         float[] m = this.computeValue_2560(matrix3x2f, f, g, h, i, j);
         this.colorScratch[0] = l;
         this.computeValue_2573(m[4], m[4], m[4], m[4]);
         this.texturePipeline.performAction_2824(identifier, m[0], m[1], m[2], m[3], 0.125F, 0.125F, 0.25F, 0.25F, this.colorScratch, this.radiusScratch, 0.5F);
      }
   }

   public void performAction_2581(Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j, int k, int l, int m, int n) {
      float[] o = this.computeValue_2560(matrix3x2f, f, g, h, i, j);
      this.gradientScratch[0] = k;
      this.gradientScratch[1] = l;
      this.gradientScratch[2] = n;
      this.gradientScratch[3] = m;
      this.computeValue_2573(o[4], o[4], o[4], o[4]);
      this.rectPipeline.performAction_2805(o[0], o[1], o[2], o[3], this.gradientScratch, this.radiusScratch);
   }

   public void performAction_2584(Matrix3x2f matrix3x2f, float f, float g, float h, float i, Vector4f vector4f, int j, int k, int l, int m) {
      float[] n = this.computeValue_2560(matrix3x2f, f, g, h, i, vector4f.x);
      float o = n[5];
      this.gradientScratch[0] = j;
      this.gradientScratch[1] = k;
      this.gradientScratch[2] = m;
      this.gradientScratch[3] = l;
      this.computeValue_2573(vector4f.x * o, vector4f.y * o, vector4f.z * o, vector4f.w * o);
      this.rectPipeline.performAction_2805(n[0], n[1], n[2], n[3], this.gradientScratch, this.radiusScratch);
   }

   public void performAction_2561(Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j, int k) {
      this.performAction_2578(matrix3x2f, f, g, h, i, j, k, 0.8F);
   }

   public void performAction_2578(Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j, int k, float l) {
      this.performAction_2577(matrix3x2f, f, g, h, i, j, k);
   }

   public void performAction_2562(Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j, int k, float l) {
      float[] m = this.computeValue_2560(matrix3x2f, f, g, h, i, j);
      float n = m[5];
      int o = RenderComponent183.computeValue_2549(k, (k >>> 24 & 0xFF) / 255.0F * l);
      this.computeValue_2573(m[4], m[4], m[4], m[4]);
      this.rectPipeline
         .performAction_2803(m[0], m[1], m[2], m[3], RenderComponent183.computeValue_2558(0, 0, 0, (int)(30.0F * l)), this.radiusScratch, 2.5F * n, 0.6F);
      this.colorScratch[0] = o;
      this.rectPipeline.performAction_2805(m[0], m[1], m[2], m[3], this.colorScratch, this.radiusScratch);
   }

   public void performAction_2580(Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j, int k, float l, int m, float n) {
      float[] o = this.computeValue_2560(matrix3x2f, f, g, h, i, j);
      float p = o[5];
      this.computeValue_2573(o[4], o[4], o[4], o[4]);
      if (n > 0.0F) {
         this.rectPipeline.performAction_2803(o[0], o[1], o[2], o[3], m, this.radiusScratch, n * p, 0.5F);
      }

      int q = RenderComponent183.computeValue_2549(k, (k >>> 24 & 0xFF) / 255.0F * l);
      this.colorScratch[0] = q;
      this.rectPipeline.performAction_2805(o[0], o[1], o[2], o[3], this.colorScratch, this.radiusScratch);
   }

   public void performAction_2574(DrawContext drawContext, float f, float g, float h, float i, int j) {
      drawContext.fill((int)f, (int)g, (int)(f + h), (int)(g + i), j);
   }
}
