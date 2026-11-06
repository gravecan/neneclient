package aethereal.render;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Builder;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.OptionalInt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

public final class RenderComponent189 {
   private static final Matrix4f WORLD_PROJECTION = new Matrix4f();
   private static final Map<String, RenderPipeline> PIPELINES = new HashMap<>();
   private static final VertexFormat WORLD_LINE_FORMAT = VertexFormat.builder()
      .add("Position", VertexFormatElement.POSITION)
      .add("Color", VertexFormatElement.COLOR)
      .add("Normal", VertexFormatElement.NORMAL)
      .add("LineWidth", VertexFormatElement.LINE_WIDTH)
      .build();
   private static final int LINE_NORMAL_OFFSET;
   private static final int LINE_WIDTH_OFFSET;
   private static final int LINE_NORMAL_PAD;
   private static final int LINE_VERTEX_SIZE;
   private static final RenderPipeline WORLD_LINE_PIPELINE;
   private static boolean a_projectionReady;
   private static final Vector4f COLOR_MODULATOR;
   private static final Vector3f MODEL_OFFSET;
   private static final Matrix4f TEXTURE_MATRIX;

   private static RenderPipeline computeValue_2658(boolean bl, boolean bl1, DrawMode drawMode, boolean bl2) {
      String string = (bl ? "3d" : "2d") + "_" + (bl1 ? "tex_" : "") + drawMode.name().toLowerCase(Locale.ROOT) + (bl2 ? "_add" : "");
      return PIPELINES.computeIfAbsent(
         string,
         string1 -> {
            Identifier identifier = Identifier.of(
               "neneclient", bl1 ? "core/immediate_world_texture" : (bl ? "core/immediate_world_color" : "core/immediate_color")
            );
            Builder builder = RenderPipeline.builder(new Snippet[0])
               .withLocation(Identifier.of("neneclient", "pipeline/immediate_" + string1))
               .withVertexShader(identifier)
               .withFragmentShader(identifier)
               .withUniform("ImmediateData", UniformType.UNIFORM_BUFFER);
            builder.withBlend(bl2 ? (bl ? BlendFunction.LIGHTNING : BlendFunction.ADDITIVE) : BlendFunction.TRANSLUCENT)
               .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
               .withDepthWrite(false)
               .withCull(false);
            if (bl1) {
               builder.withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, drawMode).withSampler("Sampler0");
            } else {
               builder.withVertexFormat(VertexFormats.POSITION_COLOR, drawMode);
            }

            RenderPipeline renderPipeline = builder.build();
            return RenderPipelines.register(renderPipeline);
         }
      );
   }

   public static void performAction_2643() {
      computeValue_2658(false, false, DrawMode.TRIANGLE_STRIP, false);
      computeValue_2658(false, false, DrawMode.DEBUG_LINES, false);
      computeValue_2658(false, false, DrawMode.TRIANGLES, false);
      computeValue_2658(false, false, DrawMode.LINES, false);
      computeValue_2658(true, false, DrawMode.TRIANGLES, false);
      computeValue_2658(true, false, DrawMode.DEBUG_LINES, false);
   }

   public static void performAction_2654(Matrix4f matrix4f) {
      WORLD_PROJECTION.set(matrix4f);
      a_projectionReady = true;
   }

   public static Matrix4f computeValue_2653(Matrix4f matrix4f) {
      Matrix4f matrix4f1 = new Matrix4f(WORLD_PROJECTION);
      Quaternionf quaternionf = MinecraftClient.getInstance().gameRenderer.getCamera().getRotation();
      if (quaternionf != null) {
         matrix4f1.mul(new Matrix4f().rotation(quaternionf.conjugate(new Quaternionf())));
      }

      matrix4f1.mul(matrix4f);
      return matrix4f1;
   }

   public static void performAction_2655(Matrix4f matrix4f, ByteBuffer byteBuffer, int i) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient != null && minecraftClient.getFramebuffer() != null && i > 0) {
         byteBuffer.position(0);
         if (byteBuffer.hasRemaining()) {
            Matrix4f matrix4f1 = computeValue_2653(matrix4f);
            FloatBuffer floatBuffer = FloatBuffer.allocate(4);
            floatBuffer.put(minecraftClient.getWindow().getFramebufferWidth());
            floatBuffer.put(minecraftClient.getWindow().getFramebufferHeight());
            floatBuffer.put(0.0F);
            floatBuffer.put(0.0F);
            floatBuffer.rewind();
            GpuBuffer gpuBuffer = null;
            GpuBuffer gpuBuffer1 = null;

            try {
               ByteBuffer byteBuffer1 = MemoryUtil.memAlloc(80);
               matrix4f1.get(0, byteBuffer1);
               byteBuffer1.putFloat(64, floatBuffer.get(0));
               byteBuffer1.putFloat(68, floatBuffer.get(1));
               byteBuffer1.putFloat(72, 0.0F);
               byteBuffer1.putFloat(76, 0.0F);
               gpuBuffer = RenderSystem.getDevice().createBuffer(() -> "neneclient:world_line_uniform", 136, byteBuffer1);
               MemoryUtil.memFree(byteBuffer1);
               byteBuffer.position(0);
               gpuBuffer1 = RenderSystem.getDevice().createBuffer(() -> "neneclient:world_line_vertices", 32, byteBuffer);
               CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
               RenderPass renderPass = commandEncoder.createRenderPass(
                  () -> "neneclient:world_lines_pass", minecraftClient.getFramebuffer().getColorAttachmentView(), OptionalInt.empty()
               );

               try {
                  renderPass.setPipeline(WORLD_LINE_PIPELINE);
                  renderPass.setVertexBuffer(0, gpuBuffer1);
                  renderPass.setUniform("LineData", gpuBuffer);
                  renderPass.draw(0, i * 6);
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
               if (gpuBuffer != null) {
                  gpuBuffer.close();
               }

               if (gpuBuffer1 != null) {
                  gpuBuffer1.close();
               }
            }
         }
      }
   }

   public static void performAction_2650(ByteBuffer byteBuffer, float f, float g, float h, float i, float j, float k, int l, int m, int n, int o, float p) {
      byteBuffer.putFloat(f).putFloat(g).putFloat(h);
      byteBuffer.put((byte)l).put((byte)m).put((byte)n).put((byte)o);
      byteBuffer.put((byte)Math.round(i * 127.0F)).put((byte)Math.round(j * 127.0F)).put((byte)Math.round(k * 127.0F));

      for (int q = 0; q < LINE_NORMAL_PAD; q++) {
         byteBuffer.put((byte)0);
      }

      byteBuffer.putFloat(p);
   }

   public static int getLINE_VERTEX_SIZE_2644() {
      return LINE_VERTEX_SIZE;
   }

   public static boolean isA_projectionReady_2662() {
      return a_projectionReady;
   }

   public static void performAction_2647(DrawMode drawMode, ByteBuffer byteBuffer, int i) {
      performAction_2656(false, false, drawMode, null, null, byteBuffer, i);
   }

   public static void performAction_2642(DrawMode drawMode, ByteBuffer byteBuffer, int i) {
      performAction_2657(false, false, drawMode, null, null, byteBuffer, i, true);
   }

   public static void performAction_2648(DrawMode drawMode, Matrix4f matrix4f, ByteBuffer byteBuffer, int i) {
      performAction_2649(drawMode, matrix4f, byteBuffer, i, false);
   }

   public static void performAction_2649(DrawMode drawMode, Matrix4f matrix4f, ByteBuffer byteBuffer, int i, boolean bl) {
      performAction_2657(true, false, drawMode, matrix4f, null, byteBuffer, i, bl);
   }

   public static void performAction_2646(GpuTextureView gpuTextureView, Matrix4f matrix4f, ByteBuffer byteBuffer, int i, boolean bl) {
      performAction_2657(true, true, DrawMode.TRIANGLES, matrix4f, gpuTextureView, byteBuffer, i, bl);
   }

   private static void performAction_2656(
      boolean bl, boolean bl1, DrawMode drawMode, Matrix4f matrix4f, GpuTextureView gpuTextureView, ByteBuffer byteBuffer, int i
   ) {
      performAction_2657(bl, bl1, drawMode, matrix4f, gpuTextureView, byteBuffer, i, false);
   }

   private static void performAction_2657(
      boolean bl, boolean bl1, DrawMode drawMode, Matrix4f matrix4f, GpuTextureView gpuTextureView, ByteBuffer byteBuffer, int i, boolean bl2
   ) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient != null && minecraftClient.getFramebuffer() != null && i > 0) {
         byteBuffer.position(0);
         if (byteBuffer.hasRemaining()) {
            if (bl || !bl1) {
               RenderPipeline renderPipeline = computeValue_2658(bl, bl1, drawMode, bl2);
               byteBuffer.position(0);
               GpuBuffer gpuBuffer = RenderSystem.getDevice().createBuffer(() -> "neneclient:immediate_vertices", 32, byteBuffer);
               GpuBuffer gpuBuffer1 = null;
               if (bl) {
                  Matrix4f matrix4f1 = new Matrix4f(WORLD_PROJECTION);
                  Quaternionf quaternionf = minecraftClient.gameRenderer.getCamera().getRotation();
                  if (quaternionf != null) {
                     matrix4f1.mul(new Matrix4f().rotation(quaternionf.conjugate(new Quaternionf())));
                  }

                  matrix4f1.mul(matrix4f);
                  ByteBuffer byteBuffer1 = MemoryUtil.memAlloc(64);
                  matrix4f1.get(byteBuffer1);
                  byteBuffer1.rewind();
                  gpuBuffer1 = RenderSystem.getDevice().createBuffer(() -> "neneclient:immediate_uniform", 136, byteBuffer1);
                  MemoryUtil.memFree(byteBuffer1);
               } else {
                  Matrix4f matrix4f2 = new Matrix4f()
                     .ortho(0.0F, minecraftClient.getWindow().getScaledWidth(), minecraftClient.getWindow().getScaledHeight(), 0.0F, -1000.0F, 1000.0F);
                  ByteBuffer byteBuffer2 = MemoryUtil.memAlloc(64);
                  matrix4f2.get(byteBuffer2);
                  byteBuffer2.rewind();
                  gpuBuffer1 = RenderSystem.getDevice().createBuffer(() -> "neneclient:immediate_uniform_2d", 136, byteBuffer2);
                  MemoryUtil.memFree(byteBuffer2);
               }

               try {
                  CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
                  RenderPass renderPass = commandEncoder.createRenderPass(
                     () -> "neneclient:immediate_pass", minecraftClient.getFramebuffer().getColorAttachmentView(), OptionalInt.empty()
                  );

                  try {
                     renderPass.setPipeline(renderPipeline);
                     renderPass.setVertexBuffer(0, gpuBuffer);
                     if (gpuBuffer1 != null) {
                        renderPass.setUniform("ImmediateData", gpuBuffer1);
                     }

                     if (bl1) {
                        renderPass.bindTexture("Sampler0", gpuTextureView, RenderSystem.getSamplerCache().get(FilterMode.LINEAR));
                     }

                     renderPass.draw(0, i);
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
                  gpuBuffer.close();
                  if (gpuBuffer1 != null) {
                     gpuBuffer1.close();
                  }
               }
            }
         }
      }
   }

   private static GpuBufferSlice computeValue_2645() {
      return RenderSystem.getDynamicUniforms().write(RenderSystem.getModelViewMatrix(), COLOR_MODULATOR, MODEL_OFFSET, TEXTURE_MATRIX);
   }

   public static void performAction_2652(ByteBuffer byteBuffer, float f, float g, float h, int i, int j, int k, int l) {
      byteBuffer.putFloat(f).putFloat(g).putFloat(h);
      byteBuffer.put((byte)i).put((byte)j).put((byte)k).put((byte)l);
   }

   public static void performAction_2651(ByteBuffer byteBuffer, float f, float g, float h, float i, float j, int k, int l, int m, int n) {
      byteBuffer.putFloat(f).putFloat(g).putFloat(h);
      byteBuffer.putFloat(i).putFloat(j);
      byteBuffer.put((byte)k).put((byte)l).put((byte)m).put((byte)n);
   }

   private RenderComponent189() {
   }

   static {
      performAction_2643();
      int i = -1;
      int j = -1;
      int k = 0;

      for (VertexFormatElement vertexFormatElement : WORLD_LINE_FORMAT.getElements()) {
         if (vertexFormatElement == VertexFormatElement.NORMAL) {
            i = k;
         }

         if (vertexFormatElement == VertexFormatElement.LINE_WIDTH) {
            j = k;
         }

         k += vertexFormatElement.byteSize();
      }

      LINE_NORMAL_OFFSET = i;
      LINE_WIDTH_OFFSET = j;
      LINE_NORMAL_PAD = i >= 0 && j >= 0 ? j - i - 3 : 0;
      LINE_VERTEX_SIZE = WORLD_LINE_FORMAT.getVertexSize();
      WORLD_LINE_PIPELINE = RenderPipelines.register(
         RenderPipeline.builder(new Snippet[0])
            .withLocation(Identifier.of("neneclient", "pipeline/world_line"))
            .withVertexShader(Identifier.of("neneclient", "core/world_line"))
            .withFragmentShader(Identifier.of("neneclient", "core/world_line"))
            .withVertexFormat(WORLD_LINE_FORMAT, DrawMode.TRIANGLES)
            .withUniform("LineData", UniformType.UNIFORM_BUFFER)
            .withBlend(BlendFunction.TRANSLUCENT)
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withDepthWrite(false)
            .withCull(false)
            .build()
      );
      a_projectionReady = false;
      COLOR_MODULATOR = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
      MODEL_OFFSET = new Vector3f(0.0F, 0.0F, 0.0F);
      TEXTURE_MATRIX = new Matrix4f();
   }
}
