package aethereal.ui.shader;

import aethereal.core.EventBus;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.event.EventComponent099;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import java.util.OptionalInt;
import net.minecraft.client.gl.GpuSampler;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.lwjgl.system.MemoryUtil;

public class ShaderComponent233 extends ShaderComponent235 {
   private static final Identifier PIPELINE_ID = Identifier.of("neneclient", "pipeline/noise");
   private static final Identifier SHADER_ID = Identifier.of("neneclient", "core/noise/noise_shader");
   private static final RenderPipeline PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[0])
         .withLocation(PIPELINE_ID)
         .withVertexShader(SHADER_ID)
         .withFragmentShader(SHADER_ID)
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("NoiseData", UniformType.UNIFORM_BUFFER)
         .withSampler("Sampler1")
         .withSampler("Sampler2")
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private GpuTexture depthCopy;
   private GpuTextureView depthCopyView;
   private GpuBuffer uniformBuffer;
   private ByteBuffer uniformData;
   private GpuBuffer dummyVertexBuffer;
   private int targetWidth;
   private int targetHeight;

   public ShaderComponent233() {
      EventBus.register(this);
   }

   @EventTarget
   public void onEventComponent099_3302(EventComponent099 eventComponent099) {
      this.performAction_3303();
   }

   public void performAction_3306() {
      if (MinecraftAccess.minecraft.getFramebuffer() != null) {
         GpuTexture gpuTexture = MinecraftAccess.minecraft.getFramebuffer().getDepthAttachment();
         if (gpuTexture != null) {
            int i = MinecraftAccess.minecraft.getFramebuffer().textureWidth;
            int j = MinecraftAccess.minecraft.getFramebuffer().textureHeight;
            if (i > 0 && j > 0) {
               if (this.checkCondition_3304(i, j)) {
                  CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
                  commandEncoder.copyTextureToTexture(gpuTexture, this.depthCopy, 0, 0, 0, 0, 0, i, j);
               }
            }
         }
      }
   }

   public void performAction_3305(float[] f) {
      if (this.depthCopy != null && this.uniformBuffer != null && MinecraftAccess.minecraft.getFramebuffer() != null) {
         if (this.uniformData != null && this.dummyVertexBuffer != null) {
            this.uniformData.clear();
            this.uniformData.putFloat(f[0]).putFloat(f[1]).putFloat(f[2]).putFloat(f[3]);
            this.uniformData.putFloat((float)(System.currentTimeMillis() % 100000L) / 1000.0F);
            this.uniformData.putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
            this.uniformData.rewind();
            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(this.uniformBuffer.slice(0L, this.uniformData.remaining()), this.uniformData);
            GpuSampler gpuSampler = RenderSystem.getSamplerCache().get(FilterMode.NEAREST);
            CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
            RenderPass renderPass = commandEncoder.createRenderPass(
               () -> "neneclient:noise_pass", MinecraftAccess.minecraft.getFramebuffer().getColorAttachmentView(), OptionalInt.empty()
            );

            try {
               renderPass.setPipeline(PIPELINE);
               renderPass.setVertexBuffer(0, this.dummyVertexBuffer);
               renderPass.bindTexture("Sampler1", MinecraftAccess.minecraft.getFramebuffer().getDepthAttachmentView(), gpuSampler);
               renderPass.bindTexture("Sampler2", this.depthCopyView, gpuSampler);
               renderPass.setUniform("NoiseData", this.uniformBuffer);
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
      }
   }

   private boolean checkCondition_3304(int i, int j) {
      if (this.depthCopy != null && this.targetWidth == i && this.targetHeight == j) {
         return true;
      }

      this.performAction_3303();
      GpuDevice gpuDevice = RenderSystem.getDevice();
      this.depthCopy = gpuDevice.createTexture(() -> "neneclient:noise_depth_copy", 7, TextureFormat.DEPTH32, i, j, 1, 1);
      this.depthCopyView = gpuDevice.createTextureView(this.depthCopy);
      this.uniformBuffer = gpuDevice.createBuffer(() -> "neneclient:noise_uniform", 136, 32L);
      this.uniformData = MemoryUtil.memAlloc(32);
      ByteBuffer byteBuffer = MemoryUtil.memAlloc(4);
      byteBuffer.putInt(0);
      byteBuffer.flip();
      this.dummyVertexBuffer = gpuDevice.createBuffer(() -> "neneclient:noise_dummy_vertex", 32, byteBuffer);
      MemoryUtil.memFree(byteBuffer);
      this.targetWidth = i;
      this.targetHeight = j;
      return true;
   }

   private void performAction_3303() {
      if (this.depthCopyView != null) {
         this.depthCopyView.close();
         this.depthCopyView = null;
      }

      if (this.depthCopy != null) {
         this.depthCopy.close();
         this.depthCopy = null;
      }

      if (this.uniformBuffer != null) {
         this.uniformBuffer.close();
         this.uniformBuffer = null;
      }

      if (this.uniformData != null) {
         MemoryUtil.memFree(this.uniformData);
         this.uniformData = null;
      }

      if (this.dummyVertexBuffer != null) {
         this.dummyVertexBuffer.close();
         this.dummyVertexBuffer = null;
      }

      this.targetWidth = 0;
      this.targetHeight = 0;
   }

   public void close() {
      this.performAction_3303();
   }
}
