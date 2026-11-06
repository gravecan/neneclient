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

public class PipelineComponent201 extends PipelineComponent206 {
   private static final Identifier PIPELINE_ID = Identifier.of("neneclient", "pipeline/outline");
   private static final Identifier VERTEX_SHADER = Identifier.of("neneclient", "core/outline");
   private static final Identifier FRAGMENT_SHADER = Identifier.of("neneclient", "core/outline");
   private static final RenderPipeline PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET})
         .withLocation(PIPELINE_ID)
         .withVertexShader(VERTEX_SHADER)
         .withFragmentShader(FRAGMENT_SHADER)
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("OutlineData", UniformType.UNIFORM_BUFFER)
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static final int MAX_OUTLINES = 64;
   private static final int OUTLINE_SIZE = 208;
   private static final int UNIFORM_RING = 32;

   public PipelineComponent201() {
      super("outline", PIPELINE, "OutlineData", 64, 208, 32);
   }

   @Override
   public int computeValue_2733() {
      return 1;
   }

   public void performAction_2801(float f, float g, float h, float i, int[] j, float[] k, float[] l, float m) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient.getFramebuffer() != null) {
         ByteBuffer byteBuffer = this.computeValue_2838();
         byteBuffer.putFloat(f);
         byteBuffer.putFloat(g);
         byteBuffer.putFloat(h);
         byteBuffer.putFloat(i);
         byteBuffer.putFloat(l[0]);
         byteBuffer.putFloat(l[1]);
         byteBuffer.putFloat(l[2]);
         byteBuffer.putFloat(l[3]);

         for (int n = 0; n < 8; n++) {
            int o = n < j.length ? j[n] : j[j.length - 1];
            float p = (o >> 24 & 0xFF) / 255.0F;
            float q = (o >> 16 & 0xFF) / 255.0F;
            float r = (o >> 8 & 0xFF) / 255.0F;
            float s = (o & 0xFF) / 255.0F;
            byteBuffer.putFloat(q);
            byteBuffer.putFloat(r);
            byteBuffer.putFloat(s);
            byteBuffer.putFloat(p);
         }

         for (int t = 0; t < 8; t++) {
            float u = t < k.length ? k[t] : k[k.length - 1];
            byteBuffer.putFloat(u);
         }

         byteBuffer.putFloat(m);
         byteBuffer.putFloat(0.0F);
         byteBuffer.putFloat(0.0F);
         byteBuffer.putFloat(0.0F);
         this.performAction_2836();
      }
   }
}
