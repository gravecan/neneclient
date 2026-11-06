package aethereal.render.pipeline;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import net.minecraft.client.MinecraftClient;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public final class PipelineComponent196 {
   private static final Vector4f COLOR_MODULATOR = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
   private static final Vector3f MODEL_OFFSET = new Vector3f(0.0F, 0.0F, 0.0F);
   private static final Matrix4f TEXTURE_MATRIX = new Matrix4f();
   private static final ArrayList<PipelineComponent196.Contract376> active = new ArrayList<>(8);
   private static boolean enabled = false;

   private PipelineComponent196() {
   }

   public static boolean isEnabled_2723() {
      return enabled;
   }

   public static void performAction_2725(boolean bl) {
      if (!bl) {
         performAction_2728();
      }

      enabled = bl;
   }

   public static void performAction_2730(PipelineComponent196.Contract376 contract376) {
      if (!active.contains(contract376)) {
         active.add(contract376);
      }
   }

   public static void performAction_2728() {
      if (!active.isEmpty()) {
         PipelineComponent196.Contract376[] contract376s = active.toArray(new PipelineComponent196.Contract376[0]);
         active.clear();
         Arrays.sort(contract376s, Comparator.comparingInt(PipelineComponent196.Contract376::computeValue_2733));
         MinecraftClient minecraftClient = MinecraftClient.getInstance();
         if (minecraftClient != null && minecraftClient.getFramebuffer() != null) {
            CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();

            for (PipelineComponent196.Contract376 contract3761 : contract376s) {
               contract3761.performAction_2734(commandEncoder);
            }

            GpuBufferSlice gpuBufferSlice = computeValue_2729();

            try {
               RenderPass renderPass = commandEncoder.createRenderPass(
                  () -> "neneclient:2d_batch",
                  minecraftClient.getFramebuffer().getColorAttachmentView(),
                  OptionalInt.empty(),
                  minecraftClient.getFramebuffer().getDepthAttachmentView(),
                  OptionalDouble.empty()
               );

               try {
                  RenderSystem.bindDefaultUniforms(renderPass);
                  renderPass.setUniform("DynamicTransforms", gpuBufferSlice);

                  for (PipelineComponent196.Contract376 contract3762 : contract376s) {
                     contract3762.performAction_2735(renderPass);
                  }
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
            } finally {
               for (PipelineComponent196.Contract376 contract3763 : contract376s) {
                  contract3763.performAction_2732();
               }
            }
         } else {
            for (PipelineComponent196.Contract376 contract376 : contract376s) {
               contract376.performAction_2732();
            }
         }
      }
   }

   public static void performAction_2724(PipelineComponent196.Contract376 contract376) {
      performAction_2731(contract376, true);
   }

   public static void performAction_2731(PipelineComponent196.Contract376 contract376, boolean bl) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient != null && minecraftClient.getFramebuffer() != null) {
         CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
         if (bl) {
            contract376.performAction_2734(commandEncoder);
         }

         GpuBufferSlice gpuBufferSlice = computeValue_2729();

         try {
            RenderPass renderPass = commandEncoder.createRenderPass(
               () -> "neneclient:2d_immediate",
               minecraftClient.getFramebuffer().getColorAttachmentView(),
               OptionalInt.empty(),
               minecraftClient.getFramebuffer().getDepthAttachmentView(),
               OptionalDouble.empty()
            );

            try {
               RenderSystem.bindDefaultUniforms(renderPass);
               renderPass.setUniform("DynamicTransforms", gpuBufferSlice);
               contract376.performAction_2735(renderPass);
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
         } finally {
            contract376.performAction_2732();
         }
      } else {
         contract376.performAction_2732();
      }
   }

   private static GpuBufferSlice computeValue_2729() {
      return RenderSystem.getDynamicUniforms().write(RenderSystem.getModelViewMatrix(), COLOR_MODULATOR, MODEL_OFFSET, TEXTURE_MATRIX);
   }

   public interface Contract376 {
      default int computeValue_2733() {
         return 0;
      }

      void performAction_2734(CommandEncoder commandEncoder);

      void performAction_2735(RenderPass renderPass);

      void performAction_2732();
   }
}
