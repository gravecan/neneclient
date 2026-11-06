package aethereal.autobuy;

import aethereal.api.ApiAnnotation001;
import aethereal.config.PersistentProcessor;
import aethereal.core.MinecraftAccess;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent189;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

public class AutobuyComponent008 extends PersistentProcessor {
   private static final float PIXEL_WORLD_SCALE = 0.0016F;
   private final List<AutobuyComponent008.NestedType288> listField1 = new ArrayList<>();
   private final List<AutobuyComponent008.NestedType287> listField2 = new ArrayList<>();

   @ApiAnnotation001
   @Override
   public void load() {
      this.listField1.clear();
      this.listField2.clear();
   }

   @Override
   public void save() {
      this.listField1.clear();
      this.listField2.clear();
   }

   public void performAction_0056(AutobuyComponent008.NestedType288 nestedType288) {
      this.listField1.add(nestedType288);
   }

   public void performAction_0055(AutobuyComponent008.NestedType287 nestedType287) {
      this.listField2.add(nestedType287);
   }

   public void performAction_0053() {
      if (!this.listField2.isEmpty()) {
         int i = 0;

         for (AutobuyComponent008.NestedType287 nestedType287 : this.listField2) {
            i += nestedType287.computeValue_0058();
         }

         if (i == 0) {
            this.listField2.clear();
         } else {
            ByteBuffer byteBuffer = MemoryUtil.memAlloc(i * 6 * 16);

            for (AutobuyComponent008.NestedType287 nestedType2871 : this.listField2) {
               nestedType2871.performAction_0059(byteBuffer);
            }

            byteBuffer.flip();
            RenderComponent189.performAction_2647(DrawMode.TRIANGLES, byteBuffer, byteBuffer.remaining() / 16);
            MemoryUtil.memFree(byteBuffer);
            this.listField2.clear();
         }
      }
   }

   public void performAction_0057() {
      if (!this.listField1.isEmpty()) {
         Camera camera = minecraft.gameRenderer.getCamera();
         Vec3d vec3d = camera.getCameraPos();
         Matrix4f matrix4f = this.listField1.getFirst().entry.getPositionMatrix();
         int i = 0;

         for (AutobuyComponent008.NestedType288 nestedType288 : this.listField1) {
            i += nestedType288.computeValue_0062();
         }

         if (i == 0) {
            this.listField1.clear();
         } else {
            ByteBuffer byteBuffer = MemoryUtil.memAlloc(i * 16);

            for (AutobuyComponent008.NestedType288 nestedType2881 : this.listField1) {
               nestedType2881.performAction_0065(byteBuffer, vec3d);
            }

            byteBuffer.flip();
            RenderComponent189.performAction_2648(DrawMode.TRIANGLES, matrix4f, byteBuffer, byteBuffer.remaining() / 16);
            MemoryUtil.memFree(byteBuffer);
            this.listField1.clear();
         }
      }
   }

   public static class NestedType287 {
      private static final int integerField1 = RenderComponent183.computeValue_2558(0, 0, 0, 255);
      private static final float floatValueField2 = 0.5F;
      final Matrix4f matrix;
      final float minX;
      final float minY;
      final float maxX;
      final float maxY;
      final int color;
      final boolean corners;
      final boolean healthBar;
      final float healthPercent;
      final int healthColor;

      public NestedType287(Matrix4f matrix4f, float f, float g, float h, float i, int j, boolean bl, boolean bl1, float k, int l) {
         this.matrix = matrix4f;
         this.minX = f;
         this.minY = g;
         this.maxX = h;
         this.maxY = i;
         this.color = j;
         this.corners = bl;
         this.healthBar = bl1;
         this.healthPercent = k;
         this.healthColor = l;
      }

      private int computeValue_0058() {
         return 16 + (this.healthBar ? 2 : 0);
      }

      void performAction_0059(ByteBuffer byteBuffer) {
         float f = this.corners
            ? Math.min(this.maxX - this.minX, this.maxY - this.minY) * 0.25F
            : Math.min(this.maxX - this.minX, this.maxY - this.minY) * 0.5F;
         float g = f;

         for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
               float h = (j & 1) == 0 ? this.minX : this.maxX;
               float k = (j & 2) == 0 ? this.minY : this.maxY;
               float l = (j & 1) == 0 ? 1.0F : -1.0F;
               float m = (j & 2) == 0 ? 1.0F : -1.0F;
               this.performAction_0061(byteBuffer, h, k, l * g, 0.0F, i == 0);
               this.performAction_0061(byteBuffer, h, k, 0.0F, m * g, i == 0);
            }
         }

         if (this.healthBar) {
            float n = this.maxY - this.minY;
            float o = this.minX - 2.0F - 0.5F;
            this.performAction_0060(byteBuffer, o - 0.5F, this.minY - 0.5F, 1.5F, n + 1.5F, integerField1);
            this.performAction_0060(byteBuffer, o, this.minY + n * (1.0F - this.healthPercent), 0.5F, n * this.healthPercent + 0.5F, this.healthColor);
         }
      }

      private void performAction_0061(ByteBuffer byteBuffer, float f, float g, float h, float i, boolean bl) {
         float j = Math.min(f, f + h) - (h == 0.0F ? 0.25F : 0.0F);
         float k = Math.min(g, g + i) - (i == 0.0F ? 0.25F : 0.0F);
         float l = h == 0.0F ? 0.5F : Math.abs(h);
         float m = i == 0.0F ? 0.5F : Math.abs(i);
         if (bl) {
            this.performAction_0060(byteBuffer, j - 0.5F, k - 0.5F, l + 1.0F, m + 1.0F, integerField1);
         } else {
            this.performAction_0060(byteBuffer, j, k, l, m, this.color);
         }
      }

      private void performAction_0060(ByteBuffer byteBuffer, float f, float g, float h, float i, int j) {
         int[] k = RenderComponent183.computeValue_2555(j);
         RenderComponent189.performAction_2652(byteBuffer, f, g, 0.0F, k[0], k[1], k[2], k[3]);
         RenderComponent189.performAction_2652(byteBuffer, f, g + i, 0.0F, k[0], k[1], k[2], k[3]);
         RenderComponent189.performAction_2652(byteBuffer, f + h, g + i, 0.0F, k[0], k[1], k[2], k[3]);
         RenderComponent189.performAction_2652(byteBuffer, f, g, 0.0F, k[0], k[1], k[2], k[3]);
         RenderComponent189.performAction_2652(byteBuffer, f + h, g + i, 0.0F, k[0], k[1], k[2], k[3]);
         RenderComponent189.performAction_2652(byteBuffer, f + h, g, 0.0F, k[0], k[1], k[2], k[3]);
      }
   }

   public static class NestedType288 {
      final Entry entry;
      final Box boxField1;
      final int color;
      final float width;
      final Vec3d start;
      final Vec3d end;
      final Vec3d control;
      final boolean flagField2;

      public NestedType288(Entry entry, Box box, int i, float f) {
         this.entry = entryx;
         this.boxField1 = box.expand(9.999995420800828E-4);
         this.color = i;
         this.width = f;
         this.start = null;
         this.end = null;
         this.control = null;
         this.flagField2 = false;
      }

      public NestedType288(Entry entry, Vec3d vec3d, Vec3d vec3d1, Vec3d vec3d2, int i, float f) {
         this.entry = entryx;
         this.boxField1 = null;
         this.color = i;
         this.width = f;
         this.start = vec3d;
         this.end = vec3d1;
         this.control = vec3d2;
         this.flagField2 = true;
      }

      public static AutobuyComponent008.NestedType288 computeValue_0066(MatrixStack matrixStack, Box box, int i, float f) {
         return new AutobuyComponent008.NestedType288(matrixStack.peek(), box, i, f);
      }

      public static AutobuyComponent008.NestedType288 computeValue_0067(MatrixStack matrixStack, Vec3d vec3d, Vec3d vec3d1, Vec3d vec3d2, int i, float f) {
         return new AutobuyComponent008.NestedType288(matrixStack.peek(), vec3d, vec3d1, vec3d2, i, f);
      }

      private int computeValue_0062() {
         return this.flagField2 ? 192 : (this.boxField1 == null ? 72 : 108);
      }

      private void performAction_0065(ByteBuffer byteBuffer, Vec3d vec3d) {
         if (this.flagField2) {
            this.performAction_0063(byteBuffer, vec3d);
         } else {
            float f = (float)(this.boxField1.minX - vec3d.x);
            float g = (float)(this.boxField1.minY - vec3d.y);
            float h = (float)(this.boxField1.minZ - vec3d.z);
            float i = (float)(this.boxField1.maxX - vec3d.x);
            float j = (float)(this.boxField1.maxY - vec3d.y);
            float k = (float)(this.boxField1.maxZ - vec3d.z);
            float l = MathHelper.sqrt((f + i) * (f + i) + (g + j) * (g + j) + (h + k) * (h + k)) * 0.5F;
            float m = Math.max(0.004F, this.width * 0.0016F * l * 0.5F);
            float[][] n = new float[][]{
               {f, g, h, i, g, h},
               {f, g, h, f, j, h},
               {f, g, h, f, g, k},
               {i, j, k, f, j, k},
               {i, j, k, i, g, k},
               {i, j, k, i, j, h},
               {i, g, h, i, j, h},
               {i, g, h, f, g, k},
               {f, j, h, i, j, h},
               {f, j, h, f, j, k},
               {f, g, k, i, g, k},
               {f, g, k, f, j, k}
            };

            for (float[] o : n) {
               performAction_0064(byteBuffer, o[0], o[1], o[2], o[3], o[4], o[5], m, this.color);
            }
         }
      }

      private void performAction_0063(ByteBuffer byteBuffer, Vec3d vec3d) {
         Vec3d vec3d1 = this.start;

         for (int i = 1; i <= 32; i++) {
            float f = i / 32.0F;
            float g = 1.0F - f;
            Vec3d vec3d2 = new Vec3d(
               g * g * this.start.x + 2.0F * g * f * this.control.x + f * f * this.end.x,
               g * g * this.start.y + 2.0F * g * f * this.control.y + f * f * this.end.y,
               g * g * this.start.z + 2.0F * g * f * this.control.z + f * f * this.end.z
            );
            float h = (float)Math.max(3.0, vec3d1.distanceTo(MinecraftAccess.minecraft.player.getEntityPos()));
            float j = Math.max(0.004F, this.width * 0.0016F * h * 0.5F);
            performAction_0064(
               byteBuffer,
               (float)(vec3d1.x - vec3d.x),
               (float)(vec3d1.y - vec3d.y),
               (float)(vec3d1.z - vec3d.z),
               (float)(vec3d2.x - vec3d.x),
               (float)(vec3d2.y - vec3d.y),
               (float)(vec3d2.z - vec3d.z),
               j,
               this.color
            );
            vec3d1 = vec3d2;
         }
      }

      private static void performAction_0064(ByteBuffer byteBuffer, float f, float g, float h, float i, float j, float k, float l, int m) {
         int[] n = RenderComponent183.computeValue_2555(m);
         float o = i - f;
         float p = j - g;
         float q = k - h;
         float r = MathHelper.sqrt(o * o + p * p + q * q);
         if (!(r < 1.0E-5F)) {
            float s = (f + i) * 0.5F;
            float t = (g + j) * 0.5F;
            float u = (h + k) * 0.5F;
            float v = s;
            float w = t;
            float x = u;
            float y = MathHelper.sqrt(v * v + w * w + x * x);
            if (y < 1.0E-5F) {
               v = 0.0F;
               w = -1.0F;
               x = 0.0F;
               y = 1.0F;
            }

            float z = (p * x - q * w) / r;
            float ba = (q * v - o * x) / r;
            float bb = (o * w - p * v) / r;
            float bc = MathHelper.sqrt(z * z + ba * ba + bb * bb);
            if (bc < 1.0E-5F) {
               z = 1.0F;
               ba = 0.0F;
               bb = 0.0F;
               bc = 1.0F;
            }

            z = z / bc * l;
            ba = ba / bc * l;
            bb = bb / bc * l;
            RenderComponent189.performAction_2652(byteBuffer, f + z, g + ba, h + bb, n[0], n[1], n[2], n[3]);
            RenderComponent189.performAction_2652(byteBuffer, i + z, j + ba, k + bb, n[0], n[1], n[2], n[3]);
            RenderComponent189.performAction_2652(byteBuffer, i - z, j - ba, k - bb, n[0], n[1], n[2], n[3]);
            RenderComponent189.performAction_2652(byteBuffer, f + z, g + ba, h + bb, n[0], n[1], n[2], n[3]);
            RenderComponent189.performAction_2652(byteBuffer, i - z, j - ba, k - bb, n[0], n[1], n[2], n[3]);
            RenderComponent189.performAction_2652(byteBuffer, f - z, g - ba, h - bb, n[0], n[1], n[2], n[3]);
         }
      }
   }
}
