package aethereal.ui.screen;

import aethereal.config.ThemeInfo;
import aethereal.core.Action;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.render.RenderComponent189;
import aethereal.util.UtilComponent270;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import org.joml.Vector2f;
import org.lwjgl.system.MemoryUtil;

public class ScreenComponent223 implements MinecraftAccess {
   private ScreenComponent223.NestedType394[] nestedType394ArrayField1;
   private final float inner;
   private final float outer;
   private int integerField2 = -1;
   private final int count;

   public int getIntegerField2_3231() {
      return this.integerField2;
   }

   public void setIntegerField2_3229(int i) {
      this.integerField2 = i;
   }

   public ScreenComponent223(int i, float f, float g) {
      this.nestedType394ArrayField1 = new ScreenComponent223.NestedType394[i];
      this.count = i;
      this.inner = f;
      this.outer = g;
   }

   public int getNestedType394ArrayField1_3232() {
      return this.nestedType394ArrayField1.length;
   }

   public void performAction_3228(int i) {
      ScreenComponent223.NestedType394[] nestedType394s = new ScreenComponent223.NestedType394[this.nestedType394ArrayField1.length - 1];
      System.arraycopy(this.nestedType394ArrayField1, 0, nestedType394s, 0, i);
      System.arraycopy(this.nestedType394ArrayField1, i + 1, nestedType394s, i, this.nestedType394ArrayField1.length - i - 1);
      this.nestedType394ArrayField1 = nestedType394s;
      if (this.integerField2 >= this.nestedType394ArrayField1.length) {
         this.integerField2 = -1;
      }
   }

   public void performAction_3237(int i) {
      ScreenComponent223.NestedType394[] nestedType394s = new ScreenComponent223.NestedType394[i];
      System.arraycopy(this.nestedType394ArrayField1, 0, nestedType394s, 0, Math.min(this.nestedType394ArrayField1.length, i));
      this.nestedType394ArrayField1 = nestedType394s;
   }

   public void performAction_3240(int i, ItemStack itemStack, Action action, boolean bl) {
      this.nestedType394ArrayField1[i] = new ScreenComponent223.NestedType394(itemStack, null, action, bl);
   }

   public void performAction_3241(int i, ItemStack itemStack, String string, Action action, boolean bl) {
      this.nestedType394ArrayField1[i] = new ScreenComponent223.NestedType394(itemStack, string, action, bl);
   }

   public boolean checkCondition_3233(double d, double e, int i, Vector2f vector2f) {
      int j = this.computeValue_3234(d, e, vector2f);
      if (j < 0) {
         return false;
      }

      ScreenComponent223.NestedType394 nestedType394 = this.nestedType394ArrayField1[j];
      if (nestedType394 != null) {
         if (i == 1 && nestedType394.editable) {
            if (!nestedType394.icon.isEmpty()) {
               nestedType394.icon = ItemStack.EMPTY;
               return true;
            }

            if (this.nestedType394ArrayField1.length - 1 >= this.count) {
               this.performAction_3228(j);
               this.integerField2 = -1;
               return true;
            }

            return true;
         }

         if (i == 0 && nestedType394.action != null) {
            nestedType394.action.execute();
         }
      }

      this.integerField2 = j;
      return true;
   }

   public void performAction_3242(DrawContext drawContext, int i, int j, Vector2f vector2f) {
      float f = vector2f.x;
      float g = vector2f.y;
      float h = (this.inner + this.outer) / 2.0F;
      int k = this.nestedType394ArrayField1.length;

      for (int l = 0; l < k; l++) {
         double d = -1.5707963285412472 + 6.2831872368967865 / k * l;
         double e = d + 6.2831872368967865 / k;
         double n = d + (e - d) * 0.010000000036845655 / 2.0;
         double o = e - (e - d) * 0.010000000036845655 / 2.0;
         boolean bl = l == this.integerField2 && !(minecraft.currentScreen instanceof ScreenComponent213);
         ScreenComponent223.NestedType394 nestedType394 = this.nestedType394ArrayField1[l];
         if (nestedType394 != null) {
            nestedType394.getAnimationField1_3243()
               .performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, minecraft.getRenderTickCounter().getTickProgress(false));
            nestedType394.getAnimationField1_3243().performAction_2528(l == this.computeValue_3234(i, j, vector2f));
            int p = RenderComponent183.computeValue_2556(
               NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), 80
            );
            int q = RenderComponent183.computeValue_2550(
               RenderComponent183.computeValue_2558(255, 255, 255, 80), p, nestedType394.getAnimationField1_3243().getFloatValueField4_2530()
            );
            boolean bl1 = minecraft.currentScreen instanceof ScreenComponent213;
            int r = bl1 && !nestedType394.icon.isEmpty() ? UtilComponent270.computeValue_3532(nestedType394.icon, false) : -1;
            int s = (bl || r == 0) && !nestedType394.icon.isEmpty() ? RenderComponent183.computeValue_2558(255, 128, 128, 80) : q;
            double t = (d + e) / 2.0;
            float u = nestedType394.getAnimationField1_3243().getFloatValueField4_2530() * 4.0F;
            float v = (float)Math.cos(t) * u;
            float w = (float)Math.sin(t) * u;
            this.performAction_3235(f + v, g + w, this.inner, this.outer, n, o, s);
            float x = f + v + (float)Math.cos(t) * h;
            float y = g + w + (float)Math.sin(t) * h;
            if (!nestedType394.icon.isEmpty()) {
               NeneClient.getInstance()
                  .getProcessors()
                  .getRenderComponent185Field4_0678()
                  .performAction_2592(drawContext, nestedType394.icon, x - 8.0F, y - 8.0F, 0, 1.0F, 1.0F, false);
               if (bl1 && r > 0) {
                  String string2 = String.valueOf(r);
                  RenderComponent188.renderComponent187Field5
                     .performAction_2630(
                        drawContext.getMatrices(),
                        string2,
                        x + 8.0F - RenderComponent188.renderComponent187Field5.computeValue_2626(string2, 10.0F),
                        y + 10.0F - RenderComponent188.renderComponent187Field5.computeValue_2622(10.0F),
                        10.0F,
                        RenderComponent183.computeValue_2558(255, 255, 255, r > 0 ? 235 : 140)
                     );
               }
            } else {
               RenderComponent188.renderComponent187Field5
                  .performAction_2630(
                     drawContext.getMatrices(),
                     "✗",
                     x - RenderComponent188.renderComponent187Field5.computeValue_2626("✗", 14.0F) / 2.0F,
                     y - RenderComponent188.renderComponent187Field5.computeValue_2622(14.0F) / 2.0F,
                     14.0F,
                     RenderComponent183.computeValue_2558(255, 255, 255, 255)
                  );
            }
         }
      }

      int z = this.computeValue_3234(i, j, vector2f);
      float m = g + this.outer + 8.0F;
      boolean bl2 = z >= 0 && this.nestedType394ArrayField1[z] != null && !this.nestedType394ArrayField1[z].icon.isEmpty();
      String string;
      if (bl2) {
         string = this.nestedType394ArrayField1[z].label != null
            ? this.nestedType394ArrayField1[z].label
            : this.nestedType394ArrayField1[z].icon.getName().getString();
      } else {
         string = null;
      }

      String string1 = string;
      if (bl2) {
         RenderComponent188.renderComponent187Field5
            .performAction_2630(
               drawContext.getMatrices(),
               string1,
               f - RenderComponent188.renderComponent187Field5.computeValue_2626(string1, 10.0F) / 2.0F,
               m,
               10.0F,
               RenderComponent183.computeValue_2558(255, 255, 255, 255)
            );
         if (minecraft.currentScreen instanceof ScreenComponent213) {
            RenderComponent188.renderComponent187Field4
               .performAction_2630(
                  drawContext.getMatrices(),
                  "SCM – adding a slot",
                  f - RenderComponent188.renderComponent187Field4.computeValue_2626("SCM – adding a slot", 7.0F) / 2.0F,
                  m + RenderComponent188.renderComponent187Field4.computeValue_2622(10.0F) + 3.0F,
                  7.0F,
                  RenderComponent183.computeValue_2558(255, 255, 255, 125)
               );
         }
      } else {
         if (minecraft.currentScreen instanceof ScreenComponent213) {
            RenderComponent188.renderComponent187Field4
               .performAction_2630(
                  drawContext.getMatrices(),
                  "SCM – adding a slot",
                  f - RenderComponent188.renderComponent187Field4.computeValue_2626("SCM – adding a slot", 7.0F) / 2.0F,
                  m,
                  7.0F,
                  RenderComponent183.computeValue_2558(255, 255, 255, 125)
               );
         }
      }
   }

   public ItemStack computeValue_3236(int i) {
      ScreenComponent223.NestedType394 nestedType394 = this.nestedType394ArrayField1[Math.floorMod(i, this.nestedType394ArrayField1.length)];
      return nestedType394 != null ? nestedType394.icon : ItemStack.EMPTY;
   }

   public void performAction_3238(int i, Action action) {
      ScreenComponent223.NestedType394 nestedType394 = this.nestedType394ArrayField1[Math.floorMod(i, this.nestedType394ArrayField1.length)];
      if (nestedType394 != null) {
         nestedType394.action = action;
      }
   }

   public Action computeValue_3227(int i) {
      ScreenComponent223.NestedType394 nestedType394 = this.nestedType394ArrayField1[Math.floorMod(i, this.nestedType394ArrayField1.length)];
      return nestedType394 != null ? nestedType394.action : null;
   }

   public void performAction_3239(int i, ItemStack itemStack) {
      ScreenComponent223.NestedType394 nestedType394 = this.nestedType394ArrayField1[Math.floorMod(i, this.nestedType394ArrayField1.length)];
      if (nestedType394 != null) {
         nestedType394.icon = itemStack.isEmpty() ? ItemStack.EMPTY : itemStack.copy();
      }
   }

   public void performAction_3230(int i, int j) {
      ScreenComponent223.NestedType394 nestedType394 = this.nestedType394ArrayField1[Math.floorMod(i, this.nestedType394ArrayField1.length)];
      ScreenComponent223.NestedType394 nestedType3941 = this.nestedType394ArrayField1[Math.floorMod(j, this.nestedType394ArrayField1.length)];
      if (nestedType394 != null && nestedType3941 != null && nestedType394 != nestedType3941) {
         ItemStack itemStack = nestedType394.icon;
         String string = nestedType394.label;
         nestedType394.icon = nestedType3941.icon;
         nestedType394.label = nestedType3941.label;
         nestedType3941.icon = itemStack;
         nestedType3941.label = string;
      }
   }

   public int computeValue_3234(double d, double e, Vector2f vector2f) {
      double f = Math.hypot(d - vector2f.x, e - vector2f.y);
      if (f < this.inner * 0.25) {
         return -1;
      }

      double g = Math.atan2(e - vector2f.y, d - vector2f.x) + 1.5707964162115484;
      if (g < 0.0) {
         g += 6.2831872368967865;
      }

      return Math.min((int)(g / (6.2831872368967865 / this.nestedType394ArrayField1.length)), this.nestedType394ArrayField1.length - 1);
   }

   private void performAction_3235(float f, float g, float h, float i, double d, double e, int j) {
      int[] k = RenderComponent183.computeValue_2555(j);
      ByteBuffer byteBuffer = MemoryUtil.memAlloc(2080);
      double l = (e - d) / 64.0;

      for (int m = 0; m <= 64; m++) {
         double n = d + l * m;
         float o = (float)Math.cos(n);
         float p = (float)Math.sin(n);
         RenderComponent189.performAction_2652(byteBuffer, f + o * i, g + p * i, 0.0F, k[0], k[1], k[2], k[3]);
         RenderComponent189.performAction_2652(byteBuffer, f + o * h, g + p * h, 0.0F, k[0], k[1], k[2], k[3]);
      }

      byteBuffer.flip();
      RenderComponent189.performAction_2647(DrawMode.TRIANGLE_STRIP, byteBuffer, byteBuffer.remaining() / 16);
      MemoryUtil.memFree(byteBuffer);
      int t = RenderComponent183.computeValue_2549(j, 0.8627451F);
      int[] u = RenderComponent183.computeValue_2555(t);
      ByteBuffer byteBuffer1 = MemoryUtil.memAlloc(3136);
      double v = (e - d) / 48.0;

      for (int q = 0; q < 48; q++) {
         double r = d + v * q;
         double s = d + v * (q + 1);
         RenderComponent189.performAction_2652(byteBuffer1, f + (float)Math.cos(r) * i, g + (float)Math.sin(r) * i, 0.0F, u[0], u[1], u[2], u[3]);
         RenderComponent189.performAction_2652(byteBuffer1, f + (float)Math.cos(s) * i, g + (float)Math.sin(s) * i, 0.0F, u[0], u[1], u[2], u[3]);
      }

      for (int w = 0; w < 48; w++) {
         double x = d + v * w;
         double y = d + v * (w + 1);
         RenderComponent189.performAction_2652(byteBuffer1, f + (float)Math.cos(x) * h, g + (float)Math.sin(x) * h, 0.0F, u[0], u[1], u[2], u[3]);
         RenderComponent189.performAction_2652(byteBuffer1, f + (float)Math.cos(y) * h, g + (float)Math.sin(y) * h, 0.0F, u[0], u[1], u[2], u[3]);
      }

      RenderComponent189.performAction_2652(byteBuffer1, f + (float)Math.cos(d) * h, g + (float)Math.sin(d) * h, 0.0F, u[0], u[1], u[2], u[3]);
      RenderComponent189.performAction_2652(byteBuffer1, f + (float)Math.cos(d) * i, g + (float)Math.sin(d) * i, 0.0F, u[0], u[1], u[2], u[3]);
      RenderComponent189.performAction_2652(byteBuffer1, f + (float)Math.cos(e) * h, g + (float)Math.sin(e) * h, 0.0F, u[0], u[1], u[2], u[3]);
      RenderComponent189.performAction_2652(byteBuffer1, f + (float)Math.cos(e) * i, g + (float)Math.sin(e) * i, 0.0F, u[0], u[1], u[2], u[3]);
      byteBuffer1.flip();
      RenderComponent189.performAction_2647(DrawMode.DEBUG_LINES, byteBuffer1, byteBuffer1.remaining() / 16);
      MemoryUtil.memFree(byteBuffer1);
   }

   public static class NestedType394 {
      private final Animation animationField1 = new Animation();
      ItemStack icon;
      String label;
      final boolean editable;
      Action action;

      public Animation getAnimationField1_3243() {
         return this.animationField1;
      }

      public NestedType394(ItemStack itemStack, String string, Action action, boolean bl) {
         this.icon = itemStack;
         this.label = string;
         this.action = actionx;
         this.editable = bl;
      }
   }
}
