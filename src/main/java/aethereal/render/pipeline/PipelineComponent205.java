package aethereal.render.pipeline;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GpuSampler;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.util.Identifier;
import org.lwjgl.system.MemoryUtil;

public class PipelineComponent205 implements PipelineComponent196.Contract376 {
   private static final Identifier PIPELINE_ID = Identifier.of("neneclient", "pipeline/texture");
   private static final Identifier VERTEX_SHADER = Identifier.of("neneclient", "core/texture");
   private static final Identifier FRAGMENT_SHADER = Identifier.of("neneclient", "core/texture");
   private static final float FIXED_GUI_SCALE = 2.0F;
   private static final RenderPipeline PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET})
         .withLocation(PIPELINE_ID)
         .withVertexShader(VERTEX_SHADER)
         .withFragmentShader(FRAGMENT_SHADER)
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("TextureData", UniformType.UNIFORM_BUFFER)
         .withSampler("Sampler0")
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static final RenderPipeline GLOW_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET})
         .withLocation(Identifier.of("neneclient", "pipeline/texture_glow"))
         .withVertexShader(VERTEX_SHADER)
         .withFragmentShader(FRAGMENT_SHADER)
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("TextureData", UniformType.UNIFORM_BUFFER)
         .withSampler("Sampler0")
         .withBlend(BlendFunction.LIGHTNING)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static final int BUFFER_SIZE = 256;
   private static final int UNIFORM_RING = 64;
   private GpuBuffer[] uniformBuffers;
   private int uniformRingIndex = 0;
   private GpuBuffer dummyVertexBuffer;
   private ByteBuffer dataBuffer;
   private boolean initialized = false;
   private final ArrayList<PipelineComponent205.NestedType379> pendingDraws = new ArrayList<>();
   private final int batchLayer;
   private final Map<GpuTexture, GpuTextureView> textureViewCache = new HashMap<>();

   public PipelineComponent205() {
      this(2);
   }

   public PipelineComponent205(int i) {
      this.batchLayer = i;
   }

   private void performAction_2818() {
      if (!this.initialized) {
         this.dataBuffer = MemoryUtil.memAlloc(256);
         ByteBuffer byteBuffer = MemoryUtil.memAlloc(4);
         byteBuffer.putInt(0);
         byteBuffer.flip();
         this.dummyVertexBuffer = RenderSystem.getDevice().createBuffer(() -> "minecraft:texture_dummy_vertex", 32, byteBuffer);
         MemoryUtil.memFree(byteBuffer);
         this.uniformBuffers = new GpuBuffer[64];
         this.initialized = true;
      }
   }

   private GpuBuffer computeValue_2815() {
      GpuBuffer gpuBuffer = this.uniformBuffers[this.uniformRingIndex];
      if (gpuBuffer == null) {
         int i = this.uniformRingIndex;
         gpuBuffer = RenderSystem.getDevice().createBuffer(() -> "minecraft:texture_uniform_" + i, 136, 256L);
         this.uniformBuffers[this.uniformRingIndex] = gpuBuffer;
      }

      this.uniformRingIndex = (this.uniformRingIndex + 1) % 64;
      return gpuBuffer;
   }

   public void performAction_2825(
      Identifier identifier, float f, float g, float h, float i, float j, float k, float l, float m, int[] n, float[] o, float p, float q
   ) {
      this.performAction_2826(identifier, f, g, h, i, j, k, l, m, n, o, p, q, GLOW_PIPELINE);
   }

   public void performAction_2824(Identifier identifier, float f, float g, float h, float i, float j, float k, float l, float m, int[] n, float[] o, float p) {
      this.performAction_2816(identifier, f, g, h, i, j, k, l, m, n, o, p, 0.0F);
   }

   public void performAction_2816(
      Identifier identifier, float f, float g, float h, float i, float j, float k, float l, float m, int[] n, float[] o, float p, float q
   ) {
      this.performAction_2826(identifier, f, g, h, i, j, k, l, m, n, o, p, q, PIPELINE);
   }

   private void performAction_2826(
      Identifier identifier,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      int[] n,
      float[] o,
      float p,
      float q,
      RenderPipeline renderPipeline
   ) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient.getFramebuffer() != null) {
         AbstractTexture abstractTexture = minecraftClient.getTextureManager().getTexture(identifier);
         if (abstractTexture != null) {
            GpuTexture gpuTexture = abstractTexture.getGlTexture();
            if (gpuTexture != null) {
               this.performAction_2821(this.computeValue_2820(gpuTexture), f, g, h, i, j, k, l, m, n, o, p, q, renderPipeline);
            }
         }
      }
   }

   private void performAction_2821(
      GpuTextureView gpuTextureView,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      int[] n,
      float[] o,
      float p,
      float q,
      RenderPipeline renderPipeline
   ) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient.getFramebuffer() != null) {
         this.performAction_2818();
         float r = minecraftClient.getWindow().getFramebufferWidth() / 2.0F;
         float s = minecraftClient.getWindow().getFramebufferHeight() / 2.0F;
         this.performAction_2819(f, g, h, i, j, k, l, m, r, s, 2.0F, n, o, p, q);
         GpuBuffer gpuBuffer = this.computeValue_2815();
         RenderSystem.getDevice().createCommandEncoder().writeToBuffer(gpuBuffer.slice(0L, this.dataBuffer.remaining()), this.dataBuffer);
         this.pendingDraws.add(new PipelineComponent205.NestedType379(renderPipeline, gpuTextureView, gpuBuffer));
         if (PipelineComponent196.isEnabled_2723()) {
            PipelineComponent196.performAction_2730(this);
            if (this.pendingDraws.size() >= 64) {
               PipelineComponent196.performAction_2724(this);
            }
         } else {
            PipelineComponent196.performAction_2728();
            PipelineComponent196.performAction_2724(this);
         }
      }
   }

   public void performAction_2822(GpuTextureView gpuTextureView, float f, float g, float h, float i, float[] j, float k) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient.getFramebuffer() != null && gpuTextureView != null && !(k <= 0.0F)) {
         float l = minecraftClient.getWindow().getFramebufferWidth();
         float m = minecraftClient.getWindow().getFramebufferHeight();
         if (!(l <= 0.0F) && !(m <= 0.0F)) {
            float n = f * 2.0F / l;
            float o = (f + h) * 2.0F / l;
            float p = 1.0F - g * 2.0F / m;
            float q = 1.0F - (g + i) * 2.0F / m;
            int r = (int)(Math.min(1.0F, Math.max(0.0F, k)) * 255.0F);
            this.performAction_2821(gpuTextureView, f, g, h, i, n, p, o, q, new int[]{r << 24 | 16777215}, j, 1.0F, 0.0F, PIPELINE);
         }
      }
   }

   @Override
   public int computeValue_2733() {
      return this.batchLayer;
   }

   @Override
   public void performAction_2734(CommandEncoder commandEncoder) {
   }

   @Override
   public void performAction_2735(RenderPass renderPass) {
      if (!this.pendingDraws.isEmpty()) {
         GpuSampler gpuSampler = RenderSystem.getSamplerCache().get(FilterMode.LINEAR);
         RenderPipeline renderPipeline = null;

         for (PipelineComponent205.NestedType379 nestedType379 : this.pendingDraws) {
            if (!nestedType379.view().isClosed()) {
               if (nestedType379.pipeline() != renderPipeline) {
                  renderPass.setPipeline(nestedType379.pipeline());
                  renderPass.setVertexBuffer(0, this.dummyVertexBuffer);
                  renderPipeline = nestedType379.pipeline();
               }

               renderPass.bindTexture("Sampler0", nestedType379.view(), gpuSampler);
               renderPass.setUniform("TextureData", nestedType379.uniformBuffer());
               renderPass.draw(0, 6);
            }
         }

         this.pendingDraws.clear();
      }
   }

   @Override
   public void performAction_2732() {
      this.pendingDraws.clear();
   }

   public void performAction_2828() {
      if (!this.pendingDraws.isEmpty()) {
         PipelineComponent196.performAction_2724(this);
      }
   }

   private void performAction_2819(
      float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, float p, int[] q, float[] r, float s, float t
   ) {
      this.dataBuffer.clear();
      this.dataBuffer.putFloat(n);
      this.dataBuffer.putFloat(o);
      this.dataBuffer.putFloat(s);
      this.dataBuffer.putFloat(p);
      this.dataBuffer.putFloat(f);
      this.dataBuffer.putFloat(g);
      this.dataBuffer.putFloat(h);
      this.dataBuffer.putFloat(i);
      this.dataBuffer.putFloat(j);
      this.dataBuffer.putFloat(k);
      this.dataBuffer.putFloat(l);
      this.dataBuffer.putFloat(m);
      this.dataBuffer.putFloat(r[0]);
      this.dataBuffer.putFloat(r[1]);
      this.dataBuffer.putFloat(r[2]);
      this.dataBuffer.putFloat(r[3]);
      float u = (float)Math.toRadians(t);
      this.dataBuffer.putFloat(u);
      this.dataBuffer.putFloat(0.0F);
      this.dataBuffer.putFloat(0.0F);
      this.dataBuffer.putFloat(0.0F);

      for (int v = 0; v < 4; v++) {
         int w = v < q.length ? q[v] : q[q.length - 1];
         float x = (w >> 24 & 0xFF) / 255.0F;
         float y = (w >> 16 & 0xFF) / 255.0F;
         float z = (w >> 8 & 0xFF) / 255.0F;
         float ba = (w & 0xFF) / 255.0F;
         this.dataBuffer.putFloat(y);
         this.dataBuffer.putFloat(z);
         this.dataBuffer.putFloat(ba);
         this.dataBuffer.putFloat(x);
      }

      this.dataBuffer.flip();
   }

   private GpuTextureView computeValue_2820(GpuTexture gpuTexture) {
      GpuTextureView gpuTextureView = this.textureViewCache.get(gpuTexture);
      if (gpuTextureView == null || gpuTextureView.isClosed()) {
         this.textureViewCache.entrySet().removeIf(entry -> {
            if (!entry.getKey().isClosed() && !entry.getValue().isClosed()) {
               return false;
            }

            if (!entry.getValue().isClosed()) {
               entry.getValue().close();
            }

            return true;
         });
         gpuTextureView = RenderSystem.getDevice().createTextureView(gpuTexture);
         this.textureViewCache.put(gpuTexture, gpuTextureView);
      }

      return gpuTextureView;
   }

   public void close() {
      this.pendingDraws.clear();

      for (GpuTextureView gpuTextureView : this.textureViewCache.values()) {
         if (!gpuTextureView.isClosed()) {
            gpuTextureView.close();
         }
      }

      this.textureViewCache.clear();
      if (this.uniformBuffers != null) {
         for (GpuBuffer gpuBuffer : this.uniformBuffers) {
            if (gpuBuffer != null) {
               gpuBuffer.close();
            }
         }

         this.uniformBuffers = null;
      }

      this.uniformRingIndex = 0;
      if (this.dummyVertexBuffer != null) {
         this.dummyVertexBuffer.close();
         this.dummyVertexBuffer = null;
      }

      if (this.dataBuffer != null) {
         MemoryUtil.memFree(this.dataBuffer);
         this.dataBuffer = null;
      }

      this.initialized = false;
   }

   private record NestedType379(RenderPipeline pipeline, GpuTextureView view, GpuBuffer uniformBuffer) {
   }
}
