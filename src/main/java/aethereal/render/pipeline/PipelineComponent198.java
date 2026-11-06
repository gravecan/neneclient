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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GpuSampler;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.util.Identifier;
import org.lwjgl.system.MemoryUtil;

public class PipelineComponent198 implements PipelineComponent196.Contract376 {
   private static final Identifier PIPELINE_ID = Identifier.of("neneclient", "pipeline/msdf");
   private static final Identifier SHADER_ID = Identifier.of("neneclient", "core/msdf");
   private static final float FIXED_GUI_SCALE = 2.0F;
   private static final RenderPipeline PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET})
         .withLocation(PIPELINE_ID)
         .withVertexShader(SHADER_ID)
         .withFragmentShader(SHADER_ID)
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("FontData", UniformType.UNIFORM_BUFFER)
         .withSampler("Sampler0")
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static final int[] LEGACY_COLORS = new int[32];
   private static final String LEGACY_CODE_CHARS;
   private static final int MAX_CHARS = 256;
   private static final int BUFFER_SIZE = 16448;
   private static final int PREPROCESS_CACHE_SIZE = 256;
   private static final Map<String, String> preprocessCache;
   private static final int UNIFORM_RING = 32;
   private GpuBuffer[] uniformBuffers;
   private int uniformRingIndex = 0;
   private GpuBuffer dummyVertexBuffer;
   private ByteBuffer dataBuffer;
   private boolean initialized = false;
   private final Map<GpuTexture, GpuTextureView> textureViewCache = new HashMap<>();
   private static final int HEADER_SIZE = 64;
   private int batchedChars = 0;
   private PipelineComponent197 currentAtlas = null;
   private float currentOutlineWidth = 0.0F;
   private int currentOutlineColor = 0;
   private final ArrayList<PipelineComponent198.NestedType378> chunks = new ArrayList<>();
   private static final Map<Integer, Integer> SMALL_CAPS_MAP;
   private static final int LIGHTNING_CP;

   private void performAction_2760(float f, float g, float h, float i, float j, float k, float l, float m, int n, float o, float p, float q, float r) {
      if (this.batchedChars == 0) {
         this.dataBuffer.clear();
         this.dataBuffer.position(64);
      }

      this.dataBuffer.putFloat(f);
      this.dataBuffer.putFloat(g);
      this.dataBuffer.putFloat(h);
      this.dataBuffer.putFloat(i);
      this.dataBuffer.putFloat(j);
      this.dataBuffer.putFloat(k);
      this.dataBuffer.putFloat(l);
      this.dataBuffer.putFloat(m);
      float s = (n >> 24 & 0xFF) / 255.0F;
      float t = (n >> 16 & 0xFF) / 255.0F;
      float u = (n >> 8 & 0xFF) / 255.0F;
      float v = (n & 0xFF) / 255.0F;
      this.dataBuffer.putFloat(t);
      this.dataBuffer.putFloat(u);
      this.dataBuffer.putFloat(v);
      this.dataBuffer.putFloat(s);
      this.dataBuffer.putFloat(o);
      this.dataBuffer.putFloat(p);
      this.dataBuffer.putFloat(q);
      this.dataBuffer.putFloat(r);
      this.batchedChars++;
   }

   private int computeValue_2773() {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      return minecraftClient != null && minecraftClient.getWindow() != null ? (int)Math.ceil(minecraftClient.getWindow().getFramebufferWidth() / 2.0) : 960;
   }

   private int computeValue_2774() {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      return minecraftClient != null && minecraftClient.getWindow() != null ? (int)Math.ceil(minecraftClient.getWindow().getFramebufferHeight() / 2.0) : 540;
   }

   private void performAction_2758() {
      if (!this.initialized) {
         this.dataBuffer = MemoryUtil.memAlloc(16448);
         ByteBuffer byteBuffer = MemoryUtil.memAlloc(4);
         byteBuffer.putInt(0);
         byteBuffer.flip();
         this.dummyVertexBuffer = RenderSystem.getDevice().createBuffer(() -> "neneclient:font_dummy_vertex", 32, byteBuffer);
         MemoryUtil.memFree(byteBuffer);
         this.uniformBuffers = new GpuBuffer[32];
         this.initialized = true;
      }
   }

   private GpuBuffer computeValue_2752() {
      GpuBuffer gpuBuffer = this.uniformBuffers[this.uniformRingIndex];
      if (gpuBuffer == null) {
         int i = this.uniformRingIndex;
         gpuBuffer = RenderSystem.getDevice().createBuffer(() -> "neneclient:font_uniform_" + i, 136, 16448L);
         this.uniformBuffers[this.uniformRingIndex] = gpuBuffer;
      }

      this.uniformRingIndex = (this.uniformRingIndex + 1) % 32;
      return gpuBuffer;
   }

   public void performAction_2764(PipelineComponent197 pipelineComponent197, String string, float f, float g, float h, int i) {
      this.performAction_2765(pipelineComponent197, string, f, g, h, i, 0.0F, 0, 0.0F);
   }

   public void performAction_2765(PipelineComponent197 pipelineComponent197, String string, float f, float g, float h, int i, float j, int k, float l) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient.getFramebuffer() != null) {
         if (string != null && !string.isEmpty()) {
            string = this.computeValue_2755(string);
            string = PipelineComponent195.computeValue_2704(string);
            pipelineComponent197.performAction_2740();
            if (pipelineComponent197.getGlyphs_2744() != 0) {
               this.performAction_2758();
               if (PipelineComponent196.isEnabled_2723()) {
                  PipelineComponent196.performAction_2730(this);
               }

               if (this.currentAtlas != null && (this.currentAtlas != pipelineComponent197 || this.currentOutlineWidth != j || this.currentOutlineColor != k)) {
                  this.performAction_2772();
               }

               this.currentAtlas = pipelineComponent197;
               this.currentOutlineWidth = j;
               this.currentOutlineColor = k;
               float m = h / pipelineComponent197.getFontSize_2742();
               float n = f;
               float o = g;
               float p = (float)Math.toRadians(l);
               float q = 0.0F;
               float r = 0.0F;
               if (l != 0.0F) {
                  q = f + this.computeValue_2762(pipelineComponent197, string, h) / 2.0F;
                  r = g + this.computeValue_2753(pipelineComponent197, string, h) / 2.0F;
               }

               int s = i;
               int t = 0;

               while (t < string.length()) {
                  PipelineComponent198.NestedType377 nestedType377 = this.computeValue_2769(string, t, i, s);
                  if (nestedType377.matched()) {
                     s = nestedType377.color();
                     t += nestedType377.skip();
                  } else {
                     int u = string.codePointAt(t);
                     int v = Character.charCount(u);
                     if (u == 10) {
                        n = f;
                        o += pipelineComponent197.getLineHeight_2749() * m;
                        t += v;
                     } else {
                        PipelineComponent200 pipelineComponent200 = pipelineComponent197.computeValue_2745(u);
                        if (pipelineComponent200 == null) {
                           PipelineComponent200 pipelineComponent2001 = pipelineComponent197.computeValue_2745(63);
                           if (pipelineComponent2001 != null) {
                              n += pipelineComponent2001.xAdvance * m;
                           } else {
                              n += h * 0.5F;
                           }

                           t += v;
                        } else {
                           float z = n + pipelineComponent200.xOffset * m;
                           float w = o + pipelineComponent200.yOffset * m;
                           float x = pipelineComponent200.width * m;
                           float y = pipelineComponent200.height * m;
                           if (pipelineComponent200.width > 0.0F && pipelineComponent200.height > 0.0F) {
                              this.performAction_2760(
                                 z, w, x, y, pipelineComponent200.u0, pipelineComponent200.v0, pipelineComponent200.u1, pipelineComponent200.v1, s, p, q, r, m
                              );
                           }

                           n += pipelineComponent200.xAdvance * m;
                           if (this.batchedChars >= 256) {
                              this.performAction_2772();
                              this.currentAtlas = pipelineComponent197;
                           }

                           t += v;
                        }
                     }
                  }
               }

               if (!PipelineComponent196.isEnabled_2723()) {
                  this.performAction_2772();
               }
            }
         }
      }
   }

   public void performAction_2763(PipelineComponent197 pipelineComponent197, String string, float f, float g, float h, float i, int j) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient.getFramebuffer() != null) {
         if (string != null && !string.isEmpty()) {
            if (this.computeValue_2762(pipelineComponent197, string, h) <= i) {
               this.performAction_2765(pipelineComponent197, string, f, g, h, j, 0.0F, 0, 0.0F);
            } else {
               int k = j >> 24 & 0xFF;
               if (k != 0) {
                  int l = j & 16777215;
                  float m = i - 40.0F;
                  string = this.computeValue_2755(string);
                  string = PipelineComponent195.computeValue_2704(string);
                  pipelineComponent197.performAction_2740();
                  if (pipelineComponent197.getGlyphs_2744() != 0) {
                     this.performAction_2758();
                     if (PipelineComponent196.isEnabled_2723()) {
                        PipelineComponent196.performAction_2730(this);
                     }

                     if (this.currentAtlas != null
                        && (this.currentAtlas != pipelineComponent197 || this.currentOutlineWidth != 0.0F || this.currentOutlineColor != 0)) {
                        this.performAction_2772();
                     }

                     this.currentAtlas = pipelineComponent197;
                     this.currentOutlineWidth = 0.0F;
                     this.currentOutlineColor = 0;
                     float n = h / pipelineComponent197.getFontSize_2742();
                     float o = 0.0F;
                     int p = 0;

                     while (p < string.length()) {
                        PipelineComponent198.NestedType377 nestedType377 = this.computeValue_2768(string, p);
                        if (nestedType377.matched()) {
                           p += nestedType377.skip();
                        } else {
                           int q = string.codePointAt(p);
                           int r = Character.charCount(q);
                           PipelineComponent200 pipelineComponent200 = pipelineComponent197.computeValue_2745(q);
                           float s;
                           if (pipelineComponent200 != null) {
                              s = pipelineComponent200.xAdvance * n;
                           } else {
                              PipelineComponent200 pipelineComponent2001 = pipelineComponent197.computeValue_2745(63);
                              s = pipelineComponent2001 != null ? pipelineComponent2001.xAdvance * n : h * 0.5F;
                           }

                           if (o + s > i) {
                              break;
                           }

                           int u = k;
                           if (o > m) {
                              float t = (o - m) / (i - m);
                              u = (int)(k * (1.0F - t));
                           }

                           if (u > 0 && pipelineComponent200 != null && pipelineComponent200.width > 0.0F && pipelineComponent200.height > 0.0F) {
                              this.performAction_2760(
                                 f + o + pipelineComponent200.xOffset * n,
                                 g + pipelineComponent200.yOffset * n,
                                 pipelineComponent200.width * n,
                                 pipelineComponent200.height * n,
                                 pipelineComponent200.u0,
                                 pipelineComponent200.v0,
                                 pipelineComponent200.u1,
                                 pipelineComponent200.v1,
                                 u << 24 | l,
                                 0.0F,
                                 0.0F,
                                 0.0F,
                                 n
                              );
                              if (this.batchedChars >= 256) {
                                 this.performAction_2772();
                                 this.currentAtlas = pipelineComponent197;
                              }
                           }

                           o += s;
                           p += r;
                        }
                     }

                     if (!PipelineComponent196.isEnabled_2723()) {
                        this.performAction_2772();
                     }
                  }
               }
            }
         }
      }
   }

   public void performAction_2754(PipelineComponent197 pipelineComponent197, String string, float f, float g, float h, float i, int j) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient.getFramebuffer() != null) {
         if (string != null && !string.isEmpty()) {
            if (this.computeValue_2762(pipelineComponent197, string, h) <= i) {
               this.performAction_2765(pipelineComponent197, string, f, g, h, j, 0.0F, 0, 0.0F);
            } else {
               int k = j >> 24 & 0xFF;
               if (k != 0) {
                  int l = j & 16777215;
                  float m = 40.0F;
                  string = this.computeValue_2755(string);
                  string = PipelineComponent195.computeValue_2704(string);
                  pipelineComponent197.performAction_2740();
                  if (pipelineComponent197.getGlyphs_2744() != 0) {
                     this.performAction_2758();
                     if (PipelineComponent196.isEnabled_2723()) {
                        PipelineComponent196.performAction_2730(this);
                     }

                     if (this.currentAtlas != null
                        && (this.currentAtlas != pipelineComponent197 || this.currentOutlineWidth != 0.0F || this.currentOutlineColor != 0)) {
                        this.performAction_2772();
                     }

                     this.currentAtlas = pipelineComponent197;
                     this.currentOutlineWidth = 0.0F;
                     this.currentOutlineColor = 0;
                     float n = h / pipelineComponent197.getFontSize_2742();
                     float o = this.computeValue_2762(pipelineComponent197, string, h);
                     float p = o - i;
                     float q = 0.0F;
                     float r = 0.0F;
                     int s = 0;

                     while (s < string.length()) {
                        PipelineComponent198.NestedType377 nestedType377 = this.computeValue_2768(string, s);
                        if (nestedType377.matched()) {
                           s += nestedType377.skip();
                        } else {
                           int t = string.codePointAt(s);
                           int u = Character.charCount(t);
                           PipelineComponent200 pipelineComponent200 = pipelineComponent197.computeValue_2745(t);
                           float v;
                           if (pipelineComponent200 != null) {
                              v = pipelineComponent200.xAdvance * n;
                           } else {
                              PipelineComponent200 pipelineComponent2001 = pipelineComponent197.computeValue_2745(63);
                              v = pipelineComponent2001 != null ? pipelineComponent2001.xAdvance * n : h * 0.5F;
                           }

                           if (q < p) {
                              q += v;
                              s += u;
                           } else {
                              if (r + v > i) {
                                 break;
                              }

                              int w = k;
                              if (r < m) {
                                 w = (int)(k * (r / m));
                              }

                              if (w > 0 && pipelineComponent200 != null && pipelineComponent200.width > 0.0F && pipelineComponent200.height > 0.0F) {
                                 this.performAction_2760(
                                    f + r + pipelineComponent200.xOffset * n,
                                    g + pipelineComponent200.yOffset * n,
                                    pipelineComponent200.width * n,
                                    pipelineComponent200.height * n,
                                    pipelineComponent200.u0,
                                    pipelineComponent200.v0,
                                    pipelineComponent200.u1,
                                    pipelineComponent200.v1,
                                    w << 24 | l,
                                    0.0F,
                                    0.0F,
                                    0.0F,
                                    n
                                 );
                                 if (this.batchedChars >= 256) {
                                    this.performAction_2772();
                                    this.currentAtlas = pipelineComponent197;
                                 }
                              }

                              r += v;
                              s += u;
                           }
                        }
                     }

                     if (!PipelineComponent196.isEnabled_2723()) {
                        this.performAction_2772();
                     }
                  }
               }
            }
         }
      }
   }

   public void performAction_2757(PipelineComponent197 pipelineComponent197, String string, float f, float g, float h, float i, int j) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient.getFramebuffer() != null) {
         if (string != null && !string.isEmpty()) {
            float k = this.computeValue_2762(pipelineComponent197, string, h);
            float l = f - k;
            if (l >= i) {
               this.performAction_2765(pipelineComponent197, string, l, g, h, j, 0.0F, 0, 0.0F);
            } else {
               int m = j >> 24 & 0xFF;
               if (m != 0) {
                  int n = j & 16777215;
                  float o = Math.min(32.0F, k * 0.45F);
                  if (o < 1.0F) {
                     o = 1.0F;
                  }

                  string = this.computeValue_2755(string);
                  string = PipelineComponent195.computeValue_2704(string);
                  pipelineComponent197.performAction_2740();
                  if (pipelineComponent197.getGlyphs_2744() != 0) {
                     this.performAction_2758();
                     if (PipelineComponent196.isEnabled_2723()) {
                        PipelineComponent196.performAction_2730(this);
                     }

                     if (this.currentAtlas != null
                        && (this.currentAtlas != pipelineComponent197 || this.currentOutlineWidth != 0.0F || this.currentOutlineColor != 0)) {
                        this.performAction_2772();
                     }

                     this.currentAtlas = pipelineComponent197;
                     this.currentOutlineWidth = 0.0F;
                     this.currentOutlineColor = 0;
                     float p = h / pipelineComponent197.getFontSize_2742();
                     float q = 0.0F;
                     int r = 0;

                     while (r < string.length()) {
                        PipelineComponent198.NestedType377 nestedType377 = this.computeValue_2768(string, r);
                        if (nestedType377.matched()) {
                           r += nestedType377.skip();
                        } else {
                           int s = string.codePointAt(r);
                           int t = Character.charCount(s);
                           PipelineComponent200 pipelineComponent200 = pipelineComponent197.computeValue_2745(s);
                           float u;
                           if (pipelineComponent200 != null) {
                              u = pipelineComponent200.xAdvance * p;
                           } else {
                              PipelineComponent200 pipelineComponent2001 = pipelineComponent197.computeValue_2745(63);
                              u = pipelineComponent2001 != null ? pipelineComponent2001.xAdvance * p : h * 0.5F;
                           }

                           float y = l + q;
                           float v = y + u;
                           int w = m;
                           if (v <= i) {
                              w = 0;
                           } else if (y < i + o) {
                              float x = (v - i) / o;
                              if (x < 0.0F) {
                                 x = 0.0F;
                              }

                              if (x > 1.0F) {
                                 x = 1.0F;
                              }

                              w = (int)(m * x);
                           }

                           if (w > 0 && pipelineComponent200 != null && pipelineComponent200.width > 0.0F && pipelineComponent200.height > 0.0F) {
                              this.performAction_2760(
                                 y + pipelineComponent200.xOffset * p,
                                 g + pipelineComponent200.yOffset * p,
                                 pipelineComponent200.width * p,
                                 pipelineComponent200.height * p,
                                 pipelineComponent200.u0,
                                 pipelineComponent200.v0,
                                 pipelineComponent200.u1,
                                 pipelineComponent200.v1,
                                 w << 24 | n,
                                 0.0F,
                                 0.0F,
                                 0.0F,
                                 p
                              );
                              if (this.batchedChars >= 256) {
                                 this.performAction_2772();
                                 this.currentAtlas = pipelineComponent197;
                              }
                           }

                           q += u;
                           r += t;
                        }
                     }

                     if (!PipelineComponent196.isEnabled_2723()) {
                        this.performAction_2772();
                     }
                  }
               }
            }
         }
      }
   }

   public void performAction_2766(
      PipelineComponent197 pipelineComponent197, String string, float f, float g, float h, int i, float j, int k, float l, float m, float n
   ) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient.getFramebuffer() != null) {
         if (string != null && !string.isEmpty()) {
            string = this.computeValue_2755(string);
            string = PipelineComponent195.computeValue_2704(string);
            pipelineComponent197.performAction_2740();
            if (pipelineComponent197.getGlyphs_2744() != 0) {
               this.performAction_2758();
               if (PipelineComponent196.isEnabled_2723()) {
                  PipelineComponent196.performAction_2730(this);
               }

               if (this.currentAtlas != null && (this.currentAtlas != pipelineComponent197 || this.currentOutlineWidth != j || this.currentOutlineColor != k)) {
                  this.performAction_2772();
               }

               this.currentAtlas = pipelineComponent197;
               this.currentOutlineWidth = j;
               this.currentOutlineColor = k;
               float o = h / pipelineComponent197.getFontSize_2742();
               float p = f;
               float q = g;
               float r = (float)Math.toRadians(l);
               int s = i;
               int t = 0;

               while (t < string.length()) {
                  PipelineComponent198.NestedType377 nestedType377 = this.computeValue_2769(string, t, i, s);
                  if (nestedType377.matched()) {
                     s = nestedType377.color();
                     t += nestedType377.skip();
                  } else {
                     int u = string.codePointAt(t);
                     int v = Character.charCount(u);
                     if (u == 10) {
                        p = f;
                        q += pipelineComponent197.getLineHeight_2749() * o;
                        t += v;
                     } else {
                        PipelineComponent200 pipelineComponent200 = pipelineComponent197.computeValue_2745(u);
                        if (pipelineComponent200 == null) {
                           PipelineComponent200 pipelineComponent2001 = pipelineComponent197.computeValue_2745(63);
                           if (pipelineComponent2001 != null) {
                              p += pipelineComponent2001.xAdvance * o;
                           } else {
                              p += h * 0.5F;
                           }

                           t += v;
                        } else {
                           float z = p + pipelineComponent200.xOffset * o;
                           float w = q + pipelineComponent200.yOffset * o;
                           float x = pipelineComponent200.width * o;
                           float y = pipelineComponent200.height * o;
                           if (pipelineComponent200.width > 0.0F && pipelineComponent200.height > 0.0F) {
                              this.performAction_2760(
                                 z, w, x, y, pipelineComponent200.u0, pipelineComponent200.v0, pipelineComponent200.u1, pipelineComponent200.v1, s, r, m, n, o
                              );
                           }

                           p += pipelineComponent200.xAdvance * o;
                           if (this.batchedChars >= 256) {
                              this.performAction_2772();
                              this.currentAtlas = pipelineComponent197;
                           }

                           t += v;
                        }
                     }
                  }
               }

               if (!PipelineComponent196.isEnabled_2723()) {
                  this.performAction_2772();
               }
            }
         }
      }
   }

   @Override
   public int computeValue_2733() {
      return 3;
   }

   private void performAction_2759() {
      if (this.batchedChars != 0 && this.currentAtlas != null) {
         MinecraftClient minecraftClient = MinecraftClient.getInstance();
         if (minecraftClient.getFramebuffer() == null) {
            this.batchedChars = 0;
            this.currentAtlas = null;
         } else {
            AbstractTexture abstractTexture = minecraftClient.getTextureManager().getTexture(this.currentAtlas.getTextureId_2737());
            if (abstractTexture == null) {
               this.batchedChars = 0;
               this.currentAtlas = null;
            } else {
               GpuTexture gpuTexture;
               try {
                  gpuTexture = abstractTexture.getGlTexture();
               } catch (Exception exception) {
                  this.batchedChars = 0;
                  this.currentAtlas = null;
                  return;
               }

               if (this.chunks.size() >= 31) {
                  PipelineComponent196.performAction_2731(this, false);
               }

               int i = this.batchedChars;
               int j = this.dataBuffer.position();
               this.dataBuffer.position(0);
               this.dataBuffer.putFloat(this.computeValue_2773());
               this.dataBuffer.putFloat(this.computeValue_2774());
               this.dataBuffer.putFloat(2.0F);
               this.dataBuffer.putFloat(this.currentOutlineWidth);
               this.dataBuffer.putFloat((this.currentOutlineColor >> 16 & 0xFF) / 255.0F);
               this.dataBuffer.putFloat((this.currentOutlineColor >> 8 & 0xFF) / 255.0F);
               this.dataBuffer.putFloat((this.currentOutlineColor & 0xFF) / 255.0F);
               this.dataBuffer.putFloat((this.currentOutlineColor >> 24 & 0xFF) / 255.0F);
               this.dataBuffer.putFloat(this.currentAtlas.getAtlasWidth_2743());
               this.dataBuffer.putFloat(this.currentAtlas.getAtlasHeight_2750());
               this.dataBuffer.putFloat(this.currentAtlas.getDistanceRange_2751());
               this.dataBuffer.putFloat(this.currentAtlas.getFontSize_2742());
               this.dataBuffer.putInt(i);
               this.dataBuffer.putInt(0);
               this.dataBuffer.putInt(0);
               this.dataBuffer.putInt(0);
               this.dataBuffer.position(0);
               this.dataBuffer.limit(j);
               GpuBuffer gpuBuffer = this.computeValue_2752();
               RenderSystem.getDevice().createCommandEncoder().writeToBuffer(gpuBuffer.slice(0L, this.dataBuffer.remaining()), this.dataBuffer);
               this.dataBuffer.limit(this.dataBuffer.capacity());
               this.chunks.add(new PipelineComponent198.NestedType378(gpuBuffer, this.computeValue_2767(gpuTexture), i));
               this.batchedChars = 0;
               this.currentAtlas = null;
            }
         }
      } else {
         this.batchedChars = 0;
         this.currentAtlas = null;
      }
   }

   @Override
   public void performAction_2734(CommandEncoder commandEncoder) {
      this.performAction_2759();
   }

   @Override
   public void performAction_2735(RenderPass renderPass) {
      if (!this.chunks.isEmpty()) {
         GpuSampler gpuSampler = RenderSystem.getSamplerCache().get(FilterMode.LINEAR);
         renderPass.setPipeline(PIPELINE);
         renderPass.setVertexBuffer(0, this.dummyVertexBuffer);

         for (PipelineComponent198.NestedType378 nestedType378 : this.chunks) {
            if (!nestedType378.view().isClosed()) {
               renderPass.bindTexture("Sampler0", nestedType378.view(), gpuSampler);
               renderPass.setUniform("FontData", nestedType378.buffer());
               renderPass.draw(0, nestedType378.count() * 6);
            }
         }

         this.chunks.clear();
      }
   }

   @Override
   public void performAction_2732() {
      this.chunks.clear();
   }

   public void performAction_2772() {
      this.performAction_2759();
      if (!PipelineComponent196.isEnabled_2723() && !this.chunks.isEmpty()) {
         PipelineComponent196.performAction_2731(this, false);
      }
   }

   private GpuTextureView computeValue_2767(GpuTexture gpuTexture) {
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

   public float computeValue_2762(PipelineComponent197 pipelineComponent197, String string, float f) {
      pipelineComponent197.performAction_2740();
      float g = f / pipelineComponent197.getFontSize_2742();
      string = this.computeValue_2755(string);
      Float float1 = pipelineComponent197.widthCache.get(string);
      if (float1 != null) {
         return float1 * g;
      }

      String string1 = PipelineComponent195.computeValue_2701(string);
      Float float2 = string1.equals(string) ? null : pipelineComponent197.widthCache.get(string1);
      if (float2 == null) {
         float2 = this.computeValue_2761(pipelineComponent197, string1);
         pipelineComponent197.widthCache.put(string1, float2);
      }

      pipelineComponent197.widthCache.put(string, float2);
      return float2 * g;
   }

   private float computeValue_2761(PipelineComponent197 pipelineComponent197, String string) {
      float f = 0.0F;
      float g = 0.0F;
      int i = 0;

      while (i < string.length()) {
         PipelineComponent198.NestedType377 nestedType377 = this.computeValue_2768(string, i);
         if (nestedType377.matched()) {
            i += nestedType377.skip();
         } else {
            int j = string.codePointAt(i);
            int k = Character.charCount(j);
            if (j == 10) {
               g = Math.max(g, f);
               f = 0.0F;
               i += k;
            } else {
               PipelineComponent200 pipelineComponent200 = pipelineComponent197.computeValue_2745(j);
               if (pipelineComponent200 != null) {
                  f += pipelineComponent200.xAdvance;
               } else {
                  PipelineComponent200 pipelineComponent2001 = pipelineComponent197.computeValue_2745(63);
                  if (pipelineComponent2001 != null) {
                     f += pipelineComponent2001.xAdvance;
                  } else {
                     f += pipelineComponent197.getFontSize_2742() * 0.5F;
                  }
               }

               i += k;
            }
         }
      }

      return Math.max(g, f);
   }

   public float computeValue_2753(PipelineComponent197 pipelineComponent197, String string, float f) {
      pipelineComponent197.performAction_2740();
      float g = f / pipelineComponent197.getFontSize_2742();
      int i = 1;

      for (int j = 0; j < string.length(); j++) {
         if (string.charAt(j) == '\n') {
            i++;
         }
      }

      return i * pipelineComponent197.getLineHeight_2749() * g;
   }

   private String computeValue_2755(String string) {
      String string1 = preprocessCache.get(string);
      if (string1 != null) {
         return string1;
      }

      StringBuilder stringBuilder = null;
      int i = 0;

      while (i < string.length()) {
         int j = string.codePointAt(i);
         int k = Character.charCount(j);
         if (j == LIGHTNING_CP) {
            if (stringBuilder == null) {
               stringBuilder = new StringBuilder(string.substring(0, i));
            }
         } else {
            Integer integer = SMALL_CAPS_MAP.get(j);
            if (integer != null) {
               if (stringBuilder == null) {
                  stringBuilder = new StringBuilder(string.substring(0, i));
               }

               stringBuilder.appendCodePoint(integer);
            } else if (stringBuilder != null) {
               stringBuilder.appendCodePoint(j);
            }
         }

         i += k;
      }

      String string2 = stringBuilder != null ? stringBuilder.toString() : string;
      preprocessCache.put(string, string2);
      return string2;
   }

   private PipelineComponent198.NestedType377 computeValue_2769(String string, int i, int j, int k) {
      PipelineComponent195.NestedType375 nestedType375 = PipelineComponent195.computeValue_2714(string, i, j);
      if (nestedType375 != null) {
         return new PipelineComponent198.NestedType377(nestedType375.length(), nestedType375.color(), true);
      }

      int l = string.codePointAt(i);
      int m = Character.charCount(l);
      if ((l == 167 || l == 38) && i + m < string.length()) {
         int n = string.codePointAt(i + m);
         if (n == 35 && i + m + 6 < string.length()) {
            try {
               String string1 = string.substring(i + m + 1, i + m + 7);
               int q = 0xFF000000 | Integer.parseInt(string1, 16);
               return new PipelineComponent198.NestedType377(m + 7, q, true);
            } catch (Exception exception) {
            }
         }

         int o = LEGACY_CODE_CHARS.indexOf(Character.toLowerCase((char)n));
         if (o >= 0) {
            int p = o < 16 ? LEGACY_COLORS[o] : (o == 21 ? j : k);
            return new PipelineComponent198.NestedType377(m + Character.charCount(n), p, true);
         }
      }

      return new PipelineComponent198.NestedType377(0, k, false);
   }

   private PipelineComponent198.NestedType377 computeValue_2768(String string, int i) {
      return this.computeValue_2769(string, i, 0, 0);
   }

   public void close() {
      this.batchedChars = 0;
      this.currentAtlas = null;
      this.performAction_2732();

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

   static {
      for (int i = 0; i < 16; i++) {
         int j = (i >> 3 & 1) * 85;
         int k = (i >> 2 & 1) * 170 + j;
         int l = (i >> 1 & 1) * 170 + j;
         int m = (i & 1) * 170 + j;
         if (i == 6) {
            k += 85;
         }

         LEGACY_COLORS[i] = 0xFF000000 | k << 16 | l << 8 | m;
         LEGACY_COLORS[i + 16] = (k & 16579836) >> 2 << 24 | k << 16 | l << 8 | m;
      }

      LEGACY_CODE_CHARS = new String(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'k', 'l', 'm', 'n', 'o', 'r'});
      preprocessCache = new LinkedHashMap<String, String>(257, 0.75F, true) {
         @Override
         protected boolean removeEldestEntry(Entry<String, String> entry) {
            return this.size() > 256;
         }
      };
      SMALL_CAPS_MAP = new HashMap<>();
      SMALL_CAPS_MAP.put(7424, 97);
      SMALL_CAPS_MAP.put(665, 98);
      SMALL_CAPS_MAP.put(7428, 99);
      SMALL_CAPS_MAP.put(7429, 100);
      SMALL_CAPS_MAP.put(7431, 101);
      SMALL_CAPS_MAP.put(1171, 102);
      SMALL_CAPS_MAP.put(610, 103);
      SMALL_CAPS_MAP.put(668, 104);
      SMALL_CAPS_MAP.put(618, 105);
      SMALL_CAPS_MAP.put(7434, 106);
      SMALL_CAPS_MAP.put(7435, 107);
      SMALL_CAPS_MAP.put(671, 108);
      SMALL_CAPS_MAP.put(7437, 109);
      SMALL_CAPS_MAP.put(628, 110);
      SMALL_CAPS_MAP.put(7439, 111);
      SMALL_CAPS_MAP.put(7448, 112);
      SMALL_CAPS_MAP.put(491, 113);
      SMALL_CAPS_MAP.put(640, 114);
      SMALL_CAPS_MAP.put(7451, 116);
      SMALL_CAPS_MAP.put(7452, 117);
      SMALL_CAPS_MAP.put(7456, 118);
      SMALL_CAPS_MAP.put(7457, 119);
      SMALL_CAPS_MAP.put(655, 121);
      SMALL_CAPS_MAP.put(7458, 122);
      LIGHTNING_CP = "⚡".codePointAt(0);
   }

   private record NestedType377(int skip, int color, boolean matched) {
   }

   private record NestedType378(GpuBuffer buffer, GpuTextureView view, int count) {
   }
}
