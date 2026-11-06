package aethereal.render;

import aethereal.api.ApiAnnotation001;
import aethereal.config.PersistentProcessor;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.event.DrawEvent;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

public class RenderComponent185 extends PersistentProcessor implements MinecraftAccess {
   private static final float PIXEL_WORLD_SCALE = 0.0016F;

   @ApiAnnotation001
   @Override
   public void load() {
   }

   @Override
   public void save() {
   }

   @EventTarget(priority = 4)
   public void onDrawEvent_2589(DrawEvent drawEvent) {
   }

   public void performAction_2594(MatrixStack matrixStack, Box box, int i, float f) {
      this.performAction_2595(matrixStack, box, i, f, false);
   }

   public void performAction_2595(MatrixStack matrixStack, Box box, int i, float f, boolean bl) {
      Camera camera = minecraft.gameRenderer.getCamera();
      Vec3d vec3d = camera.getCameraPos();
      ByteBuffer byteBuffer = MemoryUtil.memAlloc((72 + (bl ? 36 : 0)) * 16);
      float g = (float)(box.minX - vec3d.x);
      float h = (float)(box.minY - vec3d.y);
      float j = (float)(box.minZ - vec3d.z);
      float k = (float)(box.maxX - vec3d.x);
      float l = (float)(box.maxY - vec3d.y);
      float m = (float)(box.maxZ - vec3d.z);
      float n = (float)Math.sqrt((g + k) * (g + k) + (h + l) * (h + l) + (j + m) * (j + m)) * 0.5F;
      float o = Math.max(0.004F, f * 0.0016F * n * 0.5F);
      float[][] p = new float[][]{
         {g, h, j, k, h, j},
         {k, h, j, k, h, m},
         {k, h, m, g, h, m},
         {g, h, m, g, h, j},
         {g, l, j, k, l, j},
         {k, l, j, k, l, m},
         {k, l, m, g, l, m},
         {g, l, m, g, l, j},
         {g, h, j, g, l, j},
         {k, h, j, k, l, j},
         {k, h, m, k, l, m},
         {g, h, m, g, l, m}
      };

      for (float[] q : p) {
         performAction_2587(byteBuffer, q[0], q[1], q[2], q[3], q[4], q[5], o, i);
      }

      if (bl) {
         int[] u = RenderComponent183.computeValue_2555(i);
         int v = RenderComponent183.computeValue_2558(u[0], u[1], u[2], (int)(u[3] * 0.28F));
         float[][][] w = new float[][][]{
            {{g, h, j}, {k, h, j}, {k, l, j}, {g, l, j}},
            {{g, h, m}, {k, h, m}, {k, l, m}, {g, l, m}},
            {{g, h, j}, {g, l, j}, {g, l, m}, {g, h, m}},
            {{k, h, j}, {k, l, j}, {k, l, m}, {k, h, m}},
            {{g, h, j}, {k, h, j}, {k, h, m}, {g, h, m}},
            {{g, l, j}, {k, l, j}, {k, l, m}, {g, l, m}}
         };

         for (float[][] r : w) {
            for (int s = 0; s < 6; s++) {
               int t = new int[]{0, 1, 2, 2, 3, 0}[s];
               RenderComponent189.performAction_2652(byteBuffer, r[t][0], r[t][1], r[t][2], v >> 16 & 0xFF, v >> 8 & 0xFF, v & 0xFF, v >> 24 & 0xFF);
            }
         }
      }

      byteBuffer.flip();
      RenderComponent189.performAction_2648(DrawMode.TRIANGLES, matrixStack.peek().getPositionMatrix(), byteBuffer, byteBuffer.remaining() / 16);
      MemoryUtil.memFree(byteBuffer);
   }

   public void performAction_2597(Matrix4f matrix4f, float f, float g, float h, float i, int j, boolean bl, boolean bl1, float k, int l) {
      float m = Math.min(f, h);
      float n = Math.max(f, h);
      float o = Math.min(g, i);
      float p = Math.max(g, i);
      float q = n - m;
      float r = p - o;
      float s = MinecraftAccess.minecraft.getWindow().getScaledWidth();
      float t = MinecraftAccess.minecraft.getWindow().getScaledHeight();
      if (!(q <= 0.0F) && !(r <= 0.0F) && !(q > s * 1.5F) && !(r > t * 1.5F)) {
         if (!(n < 0.0F) && !(m > s) && !(p < 0.0F) && !(o > t)) {
            float u = bl ? q * 0.25F : q * 0.5F;
            float v = bl ? r * 0.25F : r * 0.5F;
            int w = 16 + (bl1 ? 2 : 0);
            ByteBuffer byteBuffer = MemoryUtil.memAlloc(w * 6 * 16);
            int x = RenderComponent183.computeValue_2558(0, 0, 0, 255);

            for (int y = 0; y < 2; y++) {
               boolean bl2 = y == 0;

               for (int z = 0; z < 4; z++) {
                  float ba = (z & 1) == 0 ? m : n;
                  float bb = (z & 2) == 0 ? o : p;
                  float bc = (z & 1) == 0 ? 1.0F : -1.0F;
                  float bd = (z & 2) == 0 ? 1.0F : -1.0F;
                  performAction_2590(byteBuffer, ba, bb, bc * u, 0.0F, bl2, j, x);
                  performAction_2590(byteBuffer, ba, bb, 0.0F, bd * v, bl2, j, x);
               }
            }

            if (bl1) {
               float be = 0.5F;
               float bf = p - o;
               float bg = m - 2.0F - be;
               performAction_2588(byteBuffer, bg - be, o - be, 1.5F, bf + 1.5F, x);
               performAction_2588(byteBuffer, bg, o + bf * (1.0F - k), be, bf * k + be, l);
            }

            byteBuffer.flip();
            RenderComponent189.performAction_2647(DrawMode.TRIANGLES, byteBuffer, byteBuffer.remaining() / 16);
            MemoryUtil.memFree(byteBuffer);
         }
      }
   }

   private static void performAction_2590(ByteBuffer byteBuffer, float f, float g, float h, float i, boolean bl, int j, int k) {
      float l = 0.5F;
      float m = Math.min(f, f + h) - (h == 0.0F ? 0.25F : 0.0F);
      float n = Math.min(g, g + i) - (i == 0.0F ? 0.25F : 0.0F);
      float o = h == 0.0F ? l : Math.abs(h);
      float p = i == 0.0F ? l : Math.abs(i);
      if (bl) {
         performAction_2588(byteBuffer, m - l, n - l, o + 1.0F, p + 1.0F, k);
      } else {
         performAction_2588(byteBuffer, m, n, o, p, j);
      }
   }

   private static void performAction_2588(ByteBuffer byteBuffer, float f, float g, float h, float i, int j) {
      int[] k = RenderComponent183.computeValue_2555(j);
      float l = f;
      float m = g;
      float n = f + h;
      float o = g + i;
      RenderComponent189.performAction_2652(byteBuffer, l, m, 0.0F, k[0], k[1], k[2], k[3]);
      RenderComponent189.performAction_2652(byteBuffer, l, o, 0.0F, k[0], k[1], k[2], k[3]);
      RenderComponent189.performAction_2652(byteBuffer, n, o, 0.0F, k[0], k[1], k[2], k[3]);
      RenderComponent189.performAction_2652(byteBuffer, l, m, 0.0F, k[0], k[1], k[2], k[3]);
      RenderComponent189.performAction_2652(byteBuffer, n, o, 0.0F, k[0], k[1], k[2], k[3]);
      RenderComponent189.performAction_2652(byteBuffer, n, m, 0.0F, k[0], k[1], k[2], k[3]);
   }

   public void performAction_2596(MatrixStack matrixStack, Vec3d vec3d, Vec3d vec3d1, Vec3d vec3d2, int i, float f) {
      Vec3d vec3d3 = vec3d2 != null ? vec3d2 : vec3d.add(vec3d1).multiply(0.5);
      Camera camera = minecraft.gameRenderer.getCamera();
      Vec3d vec3d4 = camera.getCameraPos();
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      int j = vec3d2 != null ? 40 : 1;
      int k = i >> 16 & 0xFF;
      int l = i >> 8 & 0xFF;
      int m = i & 0xFF;
      int n = i >> 24 & 0xFF;
      int o = RenderComponent189.getLINE_VERTEX_SIZE_2644();
      ByteBuffer byteBuffer = MemoryUtil.memAlloc(j * 6 * o);
      Matrix4f matrix4f1 = RenderComponent189.computeValue_2653(matrix4f);
      Vector4f vector4f = new Vector4f();
      float g = 0.05F;
      Vec3d vec3d5 = computeValue_2591(vec3d, vec3d3, vec3d1, 0.0);
      float h = (float)(vec3d5.x - vec3d4.x);
      float p = (float)(vec3d5.y - vec3d4.y);
      float q = (float)(vec3d5.z - vec3d4.z);
      vector4f.set(h, p, q, 1.0F);
      matrix4f1.transform(vector4f);
      float r = vector4f.w;

      for (int s = 1; s <= j; s++) {
         double d = (double)s / j;
         Vec3d vec3d6 = computeValue_2591(vec3d, vec3d3, vec3d1, d);
         float t = (float)(vec3d6.x - vec3d4.x);
         float u = (float)(vec3d6.y - vec3d4.y);
         float v = (float)(vec3d6.z - vec3d4.z);
         vector4f.set(t, u, v, 1.0F);
         matrix4f1.transform(vector4f);
         float w = vector4f.w;
         if (r > 0.05F || w > 0.05F) {
            float x = h;
            float y = p;
            float z = q;
            float ba = t;
            float bb = u;
            float bc = v;
            float bd = r;
            float be = w;
            if (bd < 0.05F) {
               float bf = (0.05F - bd) / (be - bd);
               x += (ba - x) * bf;
               y += (bb - y) * bf;
               z += (bc - z) * bf;
            }

            if (be < 0.05F) {
               float bj = (0.05F - be) / (bd - be);
               ba += (x - ba) * bj;
               bb += (y - bb) * bj;
               bc += (z - bc) * bj;
            }

            float bk = ba - x;
            float bg = bb - y;
            float bh = bc - z;
            float bi = MathHelper.sqrt(bk * bk + bg * bg + bh * bh);
            if (bi > 1.0E-5F) {
               bk /= bi;
               bg /= bi;
               bh /= bi;
               RenderComponent189.performAction_2650(byteBuffer, x, y, z, bk, bg, bh, k, l, m, n, f);
               RenderComponent189.performAction_2650(byteBuffer, ba, bb, bc, -bk, -bg, -bh, k, l, m, n, f);
               RenderComponent189.performAction_2650(byteBuffer, ba, bb, bc, -bk, -bg, -bh, k, l, m, n, -f);
               RenderComponent189.performAction_2650(byteBuffer, x, y, z, bk, bg, bh, k, l, m, n, f);
               RenderComponent189.performAction_2650(byteBuffer, ba, bb, bc, -bk, -bg, -bh, k, l, m, n, -f);
               RenderComponent189.performAction_2650(byteBuffer, x, y, z, bk, bg, bh, k, l, m, n, -f);
            }
         }

         h = t;
         p = u;
         q = v;
         r = w;
      }

      byteBuffer.flip();
      RenderComponent189.performAction_2655(matrix4f, byteBuffer, byteBuffer.remaining() / (6 * o));
      MemoryUtil.memFree(byteBuffer);
   }

   public void performAction_2592(DrawContext drawContext, ItemStack itemStack, float f, float g, int i, float j, float h, boolean bl) {
      if (!itemStack.isEmpty()) {
         drawContext.getMatrices().pushMatrix();
         drawContext.getMatrices().translate(f, g);
         drawContext.getMatrices().scale(h, h);
         drawContext.drawItem(itemStack, 0, 0);
         if (bl) {
            drawContext.drawStackOverlay(minecraft.textRenderer, itemStack, 0, 0);
         }

         drawContext.getMatrices().popMatrix();
      }
   }

   public void performAction_2593(DrawContext drawContext, RegistryEntry<StatusEffect> registryEntry, float f, float g, float j, float h, float i) {
      Identifier identifier = InGameHud.getEffectTexture(registryEntry);
      if (identifier != null) {
         drawContext.getMatrices().pushMatrix();
         drawContext.getMatrices().translate(f, g);
         drawContext.getMatrices().scale(h, h);
         drawContext.drawGuiTexture(RenderPipelines.GUI_TEXTURED, identifier, 0, 0, 18, 18, RenderComponent183.computeValue_2549(-1, i));
         drawContext.getMatrices().popMatrix();
      }
   }

   private static Vec3d computeValue_2591(Vec3d vec3d, Vec3d vec3d1, Vec3d vec3d2, double d) {
      double e = 1.0 - d;
      double f = e * e;
      double g = 2.0 * e * d;
      double h = d * d;
      return new Vec3d(vec3d.x * f + vec3d1.x * g + vec3d2.x * h, vec3d.y * f + vec3d1.y * g + vec3d2.y * h, vec3d.z * f + vec3d1.z * g + vec3d2.z * h);
   }

   private static void performAction_2587(ByteBuffer byteBuffer, float f, float g, float h, float i, float j, float k, float l, int m) {
      float n = i - f;
      float o = j - g;
      float p = k - h;
      float q = MathHelper.sqrt(n * n + o * o + p * p);
      if (!(q < 1.0E-5F)) {
         float r = (f + i) * 0.5F;
         float s = (g + j) * 0.5F;
         float t = (h + k) * 0.5F;
         float u = r;
         float v = s;
         float w = t;
         float x = MathHelper.sqrt(u * u + v * v + w * w);
         if (x < 1.0E-5F) {
            u = 0.0F;
            v = -1.0F;
            w = 0.0F;
            x = 1.0F;
         }

         float y = (o * w - p * v) / q;
         float z = (p * u - n * w) / q;
         float ba = (n * v - o * u) / q;
         float bb = MathHelper.sqrt(y * y + z * z + ba * ba);
         if (bb < 1.0E-5F) {
            y = 1.0F;
            z = 0.0F;
            ba = 0.0F;
            bb = 1.0F;
         }

         y = y / bb * l;
         z = z / bb * l;
         ba = ba / bb * l;
         int[] bc = RenderComponent183.computeValue_2555(m);
         RenderComponent189.performAction_2652(byteBuffer, f + y, g + z, h + ba, bc[0], bc[1], bc[2], bc[3]);
         RenderComponent189.performAction_2652(byteBuffer, i + y, j + z, k + ba, bc[0], bc[1], bc[2], bc[3]);
         RenderComponent189.performAction_2652(byteBuffer, i - y, j - z, k - ba, bc[0], bc[1], bc[2], bc[3]);
         RenderComponent189.performAction_2652(byteBuffer, f + y, g + z, h + ba, bc[0], bc[1], bc[2], bc[3]);
         RenderComponent189.performAction_2652(byteBuffer, i - y, j - z, k - ba, bc[0], bc[1], bc[2], bc[3]);
         RenderComponent189.performAction_2652(byteBuffer, f - y, g - z, h - ba, bc[0], bc[1], bc[2], bc[3]);
      }
   }
}
