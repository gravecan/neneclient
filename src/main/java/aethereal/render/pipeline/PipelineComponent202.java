package aethereal.render.pipeline;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class PipelineComponent202 extends PipelineComponent206 {
   private static final Identifier PIPELINE_ID = Identifier.of("neneclient", "pipeline/rect");
   private static final Identifier VERTEX_SHADER = Identifier.of("neneclient", "core/rect");
   private static final Identifier FRAGMENT_SHADER = Identifier.of("neneclient", "core/rect");
   private static final RenderPipeline PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET})
         .withLocation(PIPELINE_ID)
         .withVertexShader(VERTEX_SHADER)
         .withFragmentShader(FRAGMENT_SHADER)
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("RectData", UniformType.UNIFORM_BUFFER)
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static final int MAX_RECTS = 64;
   private static final int RECT_SIZE = 224;
   private static final int UNIFORM_RING = 32;
   private final int[] colors9 = new int[9];

   public PipelineComponent202() {
      super("rect", PIPELINE, "RectData", 64, 224, 32);
   }

   @Override
   public int computeValue_2733() {
      return 0;
   }

   public void performAction_2805(float f, float g, float h, float i, int[] j, float[] k) {
      this.performAction_2806(f, g, h, i, j, k, 0.0F);
   }

   public void performAction_2806(float f, float g, float h, float i, int[] j, float[] k, float l) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient.getFramebuffer() != null) {
         this.performAction_2808(j);
         this.performAction_2802(f, g, h, i, f, g, h, i, l, k, 0.0F, 0.0F, 1.0F, 0);
         this.performAction_2836();
      }
   }

   public void performAction_2803(float f, float g, float h, float i, int j, float[] k, float l, float m) {
      this.performAction_2804(f, g, h, i, j, k, l, m, 1.0F);
   }

   public void performAction_2804(float f, float g, float h, float i, int j, float[] k, float l, float m, float n) {
      if (!(h <= 0.0F) && !(i <= 0.0F) && !(l <= 0.0F) && !(m <= 0.0F)) {
         MinecraftClient minecraftClient = MinecraftClient.getInstance();
         if (minecraftClient.getFramebuffer() != null) {
            for (int o = 0; o < 9; o++) {
               this.colors9[o] = j;
            }

            float p = Math.max(0.5F, l);
            this.performAction_2802(f - p, g - p, h + p * 2.0F, i + p * 2.0F, f, g, h, i, 0.0F, k, l, m, n, j);
            this.performAction_2836();
         }
      }
   }

   private void performAction_2808(int[] i) {
      if (i.length == 1) {
         for (int j = 0; j < 9; j++) {
            this.colors9[j] = i[0];
         }
      } else if (i.length == 4) {
         this.colors9[0] = i[0];
         this.colors9[1] = this.computeValue_2807(i[0], i[1]);
         this.colors9[2] = i[1];
         this.colors9[3] = this.computeValue_2807(i[0], i[3]);
         this.colors9[4] = this.computeValue_2807(i[0], i[1], i[2], i[3]);
         this.colors9[5] = this.computeValue_2807(i[1], i[2]);
         this.colors9[6] = i[3];
         this.colors9[7] = this.computeValue_2807(i[3], i[2]);
         this.colors9[8] = i[2];
      } else if (i.length >= 9) {
         System.arraycopy(i, 0, this.colors9, 0, 9);
      } else {
         for (int k = 0; k < 9; k++) {
            this.colors9[k] = i[k % i.length];
         }
      }
   }

   private int computeValue_2807(int... i) {
      int j = 0;
      int k = 0;
      int l = 0;
      int m = 0;

      for (int n : i) {
         m += n >> 24 & 0xFF;
         j += n >> 16 & 0xFF;
         k += n >> 8 & 0xFF;
         l += n & 0xFF;
      }

      int o = i.length;
      return m / o << 24 | j / o << 16 | k / o << 8 | l / o;
   }

   private void performAction_2802(float f, float g, float h, float i, float j, float k, float l, float m, float n, float[] o, float p, float q, float r, int s) {
      ByteBuffer byteBuffer = this.computeValue_2838();
      byteBuffer.putFloat(f);
      byteBuffer.putFloat(g);
      byteBuffer.putFloat(h);
      byteBuffer.putFloat(i);
      byteBuffer.putFloat(o[0]);
      byteBuffer.putFloat(o[1]);
      byteBuffer.putFloat(o[2]);
      byteBuffer.putFloat(o[3]);

      for (int t = 0; t < 9; t++) {
         int u = this.colors9[t];
         float v = (u >> 24 & 0xFF) / 255.0F;
         float w = (u >> 16 & 0xFF) / 255.0F;
         float x = (u >> 8 & 0xFF) / 255.0F;
         float y = (u & 0xFF) / 255.0F;
         byteBuffer.putFloat(w);
         byteBuffer.putFloat(x);
         byteBuffer.putFloat(y);
         byteBuffer.putFloat(v);
      }

      byteBuffer.putFloat(j);
      byteBuffer.putFloat(k);
      byteBuffer.putFloat(l);
      byteBuffer.putFloat(m);
      byteBuffer.putFloat(p);
      byteBuffer.putFloat(q);
      byteBuffer.putFloat(Math.max(0.05F, r));
      byteBuffer.putFloat(n);
      byteBuffer.putFloat((s >> 16 & 0xFF) / 255.0F);
      byteBuffer.putFloat((s >> 8 & 0xFF) / 255.0F);
      byteBuffer.putFloat((s & 0xFF) / 255.0F);
      byteBuffer.putFloat((s >> 24 & 0xFF) / 255.0F);
   }
}
