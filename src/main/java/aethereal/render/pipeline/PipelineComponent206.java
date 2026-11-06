package aethereal.render.pipeline;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.system.MemoryUtil;

public abstract class PipelineComponent206 implements PipelineComponent196.Contract376 {
   protected static final float FIXED_GUI_SCALE = 2.0F;
   private static final int HEADER_SIZE = 16;
   private final String debugName;
   private final RenderPipeline renderPipeline;
   private final String uniformName;
   private final int maxItems;
   private final int bufferSize;
   private final int uniformRing;
   private GpuBuffer[] uniformBuffers;
   private int uniformRingIndex = 0;
   private GpuBuffer dummyVertexBuffer;
   private ByteBuffer dataBuffer;
   private boolean initialized = false;
   private int batchedItems = 0;
   private final ArrayList<PipelineComponent206.NestedType380> chunks = new ArrayList<>();

   protected PipelineComponent206(String string, RenderPipeline renderPipeline, String string1, int i, int j, int k) {
      this.debugName = string;
      this.renderPipeline = renderPipelinex;
      this.uniformName = string1;
      this.maxItems = i;
      this.bufferSize = 16 + i * j;
      this.uniformRing = k;
   }

   private void performAction_2834() {
      if (!this.initialized) {
         this.dataBuffer = MemoryUtil.memAlloc(this.bufferSize);
         ByteBuffer byteBuffer = MemoryUtil.memAlloc(4);
         byteBuffer.putInt(0);
         byteBuffer.flip();
         this.dummyVertexBuffer = RenderSystem.getDevice().createBuffer(() -> "neneclient:" + this.debugName + "_dummy_vertex", 32, byteBuffer);
         MemoryUtil.memFree(byteBuffer);
         this.uniformBuffers = new GpuBuffer[this.uniformRing];
         this.initialized = true;
      }
   }

   private GpuBuffer computeValue_2832() {
      GpuBuffer gpuBuffer = this.uniformBuffers[this.uniformRingIndex];
      if (gpuBuffer == null) {
         int i = this.uniformRingIndex;
         gpuBuffer = RenderSystem.getDevice().createBuffer(() -> "neneclient:" + this.debugName + "_uniform_" + i, 136, this.bufferSize);
         this.uniformBuffers[this.uniformRingIndex] = gpuBuffer;
      }

      this.uniformRingIndex = (this.uniformRingIndex + 1) % this.uniformRing;
      return gpuBuffer;
   }

   protected final ByteBuffer computeValue_2838() {
      this.performAction_2834();
      if (this.batchedItems == 0) {
         this.dataBuffer.clear();
         this.dataBuffer.position(16);
      }

      return this.dataBuffer;
   }

   protected final void performAction_2836() {
      this.batchedItems++;
      if (PipelineComponent196.isEnabled_2723()) {
         PipelineComponent196.performAction_2730(this);
         if (this.batchedItems >= this.maxItems) {
            if (this.chunks.size() >= this.uniformRing - 1) {
               PipelineComponent196.performAction_2724(this);
            } else {
               this.performAction_2835();
            }
         }
      } else {
         PipelineComponent196.performAction_2724(this);
      }
   }

   private void performAction_2835() {
      if (this.batchedItems != 0) {
         MinecraftClient minecraftClient = MinecraftClient.getInstance();
         float f = minecraftClient.getWindow().getFramebufferWidth() / 2.0F;
         float g = minecraftClient.getWindow().getFramebufferHeight() / 2.0F;
         int i = this.dataBuffer.position();
         this.dataBuffer.position(0);
         this.dataBuffer.putFloat(f);
         this.dataBuffer.putFloat(g);
         this.dataBuffer.putFloat(2.0F);
         this.dataBuffer.putFloat(0.0F);
         this.dataBuffer.position(0);
         this.dataBuffer.limit(i);
         GpuBuffer gpuBuffer = this.computeValue_2832();
         RenderSystem.getDevice().createCommandEncoder().writeToBuffer(gpuBuffer.slice(0L, this.dataBuffer.remaining()), this.dataBuffer);
         this.dataBuffer.limit(this.dataBuffer.capacity());
         this.chunks.add(new PipelineComponent206.NestedType380(gpuBuffer, this.batchedItems));
         this.batchedItems = 0;
      }
   }

   @Override
   public final void performAction_2734(CommandEncoder commandEncoder) {
      this.performAction_2835();
   }

   @Override
   public final void performAction_2735(RenderPass renderPass) {
      if (!this.chunks.isEmpty()) {
         renderPass.setPipeline(this.renderPipeline);
         renderPass.setVertexBuffer(0, this.dummyVertexBuffer);

         for (PipelineComponent206.NestedType380 nestedType380 : this.chunks) {
            renderPass.setUniform(this.uniformName, nestedType380.buffer());
            renderPass.draw(0, nestedType380.count() * 6);
         }

         this.chunks.clear();
      }
   }

   @Override
   public final void performAction_2732() {
      this.chunks.clear();
   }

   public final void performAction_2837() {
      if (this.batchedItems != 0 || !this.chunks.isEmpty()) {
         PipelineComponent196.performAction_2724(this);
      }
   }

   public void close() {
      this.batchedItems = 0;
      this.performAction_2732();
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

   private record NestedType380(GpuBuffer buffer, int count) {
   }
}
