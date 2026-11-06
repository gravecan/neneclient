package aethereal.render.pipeline;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import java.util.OptionalInt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.lwjgl.system.MemoryUtil;

public final class PipelineComponent194 {
   private static final int MIP_COUNT = 4;
   private static final int MAX_PASS_OFFSET = 24;
   private static final int UNIFORM_SIZE = 16;
   private static final int UNIFORM_SLOTS = 8;
   private static final RenderPipeline DOWN_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET})
         .withLocation(Identifier.of("neneclient", "pipeline/kawase_down"))
         .withVertexShader(Identifier.of("neneclient", "core/kawase_down"))
         .withFragmentShader(Identifier.of("neneclient", "core/kawase_down"))
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("KawaseData", UniformType.UNIFORM_BUFFER)
         .withSampler("Sampler0")
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static final RenderPipeline UP_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET})
         .withLocation(Identifier.of("neneclient", "pipeline/kawase_up"))
         .withVertexShader(Identifier.of("neneclient", "core/kawase_up"))
         .withFragmentShader(Identifier.of("neneclient", "core/kawase_up"))
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("KawaseData", UniformType.UNIFORM_BUFFER)
         .withSampler("Sampler0")
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private GpuTexture[] textures;
   private GpuTextureView[] views;
   private GpuBuffer[] uniformBuffers;
   private GpuBuffer dummyVertexBuffer;
   private int width;
   private int height;

   public void performAction_2690() {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient != null && minecraftClient.getFramebuffer() != null) {
         int i = minecraftClient.getWindow().getFramebufferWidth();
         int j = minecraftClient.getWindow().getFramebufferHeight();
         if (i > 0 && j > 0) {
            this.performAction_2697(i, j);
            GpuTextureView gpuTextureView = minecraftClient.getFramebuffer().getColorAttachmentView();
            CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
            this.performAction_2695(commandEncoder, DOWN_PIPELINE, gpuTextureView, this.views[0], 0, 0.0F);

            for (int k = 0; k < 3; k++) {
               this.performAction_2695(commandEncoder, DOWN_PIPELINE, this.views[k], this.views[k + 1], k + 1, 24 * (k + 1) / 3.0F);
            }

            for (int l = 3; l > 0; l--) {
               this.performAction_2695(commandEncoder, UP_PIPELINE, this.views[l], this.views[l - 1], l, 24 * l / 3.0F);
            }
         }
      }
   }

   public GpuTextureView computeValue_2693() {
      return this.views != null ? this.views[0] : null;
   }

   private void performAction_2695(
      CommandEncoder commandEncoder, RenderPipeline renderPipeline, GpuTextureView gpuTextureView, GpuTextureView gpuTextureView1, int i, float f
   ) {
      ByteBuffer byteBuffer = MemoryUtil.memAlloc(16);
      byteBuffer.putFloat(this.width).putFloat(this.height).putFloat(f).putFloat(0.0F);
      byteBuffer.flip();
      commandEncoder.writeToBuffer(this.computeValue_2694(i).slice(0L, 16L), byteBuffer);
      MemoryUtil.memFree(byteBuffer);
      RenderPass renderPass = commandEncoder.createRenderPass(() -> "neneclient:kawase_pass", gpuTextureView1, OptionalInt.empty());

      try {
         renderPass.setPipeline(renderPipeline);
         renderPass.bindTexture("Sampler0", gpuTextureView, RenderSystem.getSamplerCache().get(FilterMode.LINEAR));
         renderPass.setUniform("KawaseData", this.computeValue_2694(i));
         renderPass.setVertexBuffer(0, this.computeValue_2692());
         renderPass.draw(0, 6);
      } catch (Throwable throwable1) {
         if (renderPass != null) {
            try {
               renderPass.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (renderPass != null) {
         renderPass.close();
      }
   }

   private void performAction_2697(int i, int j) {
      if (this.views == null || this.width != i || this.height != j) {
         this.performAction_2691();
         this.width = i;
         this.height = j;
         this.textures = new GpuTexture[4];
         this.views = new GpuTextureView[4];

         for (int k = 0; k < 4; k++) {
            int l = k;
            this.textures[k] = RenderSystem.getDevice().createTexture(() -> "neneclient:blur_mip_" + l, 12, TextureFormat.RGBA8, i, j, 1, 1);
            this.views[k] = RenderSystem.getDevice().createTextureView(this.textures[k]);
         }
      }
   }

   private GpuBuffer computeValue_2694(int i) {
      if (this.uniformBuffers == null) {
         this.uniformBuffers = new GpuBuffer[8];
      }

      if (this.uniformBuffers[i] == null) {
         int j = i;
         this.uniformBuffers[i] = RenderSystem.getDevice().createBuffer(() -> "neneclient:blur_uniform_" + j, 136, 16L);
      }

      return this.uniformBuffers[i];
   }

   private GpuBuffer computeValue_2692() {
      if (this.dummyVertexBuffer == null) {
         ByteBuffer byteBuffer = MemoryUtil.memAlloc(4);
         byteBuffer.putInt(0);
         byteBuffer.flip();
         this.dummyVertexBuffer = RenderSystem.getDevice().createBuffer(() -> "neneclient:blur_dummy_vertex", 32, byteBuffer);
         MemoryUtil.memFree(byteBuffer);
      }

      return this.dummyVertexBuffer;
   }

   private void performAction_2691() {
      if (this.views != null) {
         for (GpuTextureView gpuTextureView : this.views) {
            if (gpuTextureView != null && !gpuTextureView.isClosed()) {
               gpuTextureView.close();
            }
         }

         this.views = null;
      }

      if (this.textures != null) {
         for (GpuTexture gpuTexture : this.textures) {
            if (gpuTexture != null && !gpuTexture.isClosed()) {
               gpuTexture.close();
            }
         }

         this.textures = null;
      }
   }

   public void close() {
      this.performAction_2691();
      if (this.uniformBuffers != null) {
         for (GpuBuffer gpuBuffer : this.uniformBuffers) {
            if (gpuBuffer != null) {
               gpuBuffer.close();
            }
         }

         this.uniformBuffers = null;
      }

      if (this.dummyVertexBuffer != null) {
         this.dummyVertexBuffer.close();
         this.dummyVertexBuffer = null;
      }

      this.width = 0;
      this.height = 0;
   }
}
