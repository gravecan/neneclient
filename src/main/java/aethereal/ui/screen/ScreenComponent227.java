package aethereal.ui.screen;

import aethereal.util.UtilComponent279;
import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix3x2fStack;

public final class ScreenComponent227 {
   private static final int COLS = 4;
   private static final float CELL_H = 54.0F;
   private final List<ScreenComponent214.NestedType387> ctls = new ArrayList<>();
   private final float[] hov = new float[ScreenComponent226.ALL.size()];
   private final float[] floatValueArrayField1 = new float[ScreenComponent226.ALL.size()];
   private final float[] floatValueArrayField2 = new float[ScreenComponent226.ALL.size()];
   private float cellW;
   private ScreenComponent214.NestedType387 active;
   private float lastDraw = -10.0F;
   private float floatValueField3;

   public ScreenComponent227() {
      this.ctls
         .add(
            new ScreenComponent214.NestedType388(
               "CUSTOM COLOR",
               0.0,
               360.0,
               1.0,
               () -> ScreenComponent228.nestedType396Field1.hue >= 0
                  ? ScreenComponent228.nestedType396Field1.hue
                  : ScreenComponent226.computeValue_3256(ScreenComponent228.nestedType396Field1.accent),
               d -> ScreenComponent226.performAction_3262((int)d),
               d -> (int)d + "°",
               true
            )
         );
      this.ctls
         .add(
            new ScreenComponent214.NestedType388(
               "GLASS",
               30.0,
               90.0,
               1.0,
               () -> ScreenComponent228.nestedType396Field1.glass * 100.0F,
               d -> ScreenComponent226.performAction_3260((float)(d / 100.0)),
               d -> (int)d + "%",
               false
            )
         );
      this.ctls
         .add(new ScreenComponent214.NestedType389("PARTICLES", () -> ScreenComponent228.nestedType396Field1.particles, ScreenComponent226::performAction_3261));
   }

   public void performAction_3273(Matrix3x2fStack matrix3x2fStack, float f, float g, float h, float i, float j, int k, int l) {
      if (MenuRenderer.time - this.lastDraw > 0.25F) {
         this.floatValueField3 = 0.0F;
      }

      this.lastDraw = MenuRenderer.time;
      this.floatValueField3 = this.floatValueField3 + MenuRenderer.floatValueField1;
      float m = MenuRenderer.easeOutCubic(MenuRenderer.clamp01(this.floatValueField3 / 0.35F));
      float n = j * m;
      float o = g + (1.0F - m) * 10.0F;
      ScreenComponent214.performAction_3077(matrix3x2fStack, "TRANSPARENT THEMES", f, o + 4.0F, h, n);
      this.cellW = (h - 24.0F) / 4.0F;

      for (int p = 0; p < ScreenComponent226.ALL.size(); p++) {
         ScreenComponent226.NestedType395 nestedType395 = ScreenComponent226.ALL.get(p);
         float q = MenuRenderer.easeOutCubic(MenuRenderer.clamp01((this.floatValueField3 - 0.05F - p * 0.04F) / 0.35F)) * j;
         float r = f + p % 4 * (this.cellW + 8.0F);
         float s = o + 14.0F + p / 4 * 62.0F + (1.0F - q) * 8.0F;
         this.floatValueArrayField1[p] = r;
         this.floatValueArrayField2[p] = s;
         boolean bl = UtilComponent279.checkCondition_3576(k, l, r, s, this.cellW, 54.0F);
         this.hov[p] = MenuRenderer.approach(this.hov[p], bl ? 1.0F : 0.0F, 14.0F);
         boolean bl1 = nestedType395.name().equals(ScreenComponent228.nestedType396Field1.theme);
         int t = MenuRenderer.withAlpha(nestedType395.accent(), 1.0F);
         float u = this.hov[p] * 1.5F;
         MenuRenderer.fillRoundedRect(
            matrix3x2fStack,
            r,
            s - u,
            this.cellW,
            54.0F,
            8.0F,
            MenuRenderer.withAlpha(MenuRenderer.blendRgb(1446428, 2300717, this.hov[p]), 0.92F * q)
         );
         MenuRenderer.strokeRoundedRect(
            matrix3x2fStack,
            r,
            s - u,
            this.cellW,
            54.0F,
            8.0F,
            bl1 ? 0.9F : 0.5F,
            bl1 ? MenuRenderer.replaceAlpha(t, 0.9F * q) : MenuRenderer.withAlpha(16777215, (0.07F + 0.1F * this.hov[p]) * q)
         );
         float v = r + 6.0F;
         float w = s - u + 6.0F;
         float x = this.cellW - 12.0F;
         float y = 26.0F;

         for (int z = 0; z < (int)Math.ceil(x / 8.0F); z++) {
            MenuRenderer.fillRoundedRect(
               matrix3x2fStack, v + z * 8, w, Math.min(8.0F, x - z * 8), y, 0.0F, MenuRenderer.withAlpha(z % 2 == 0 ? 4868696 : 3026490, q)
            );
         }

         MenuRenderer.fillRoundedRect(matrix3x2fStack, v, w, x, y, 0.0F, MenuRenderer.withAlpha(nestedType395.panel(), nestedType395.glass() * q));
         MenuRenderer.fillRoundedRect(matrix3x2fStack, v + 5.0F, w + y / 2.0F - 1.5F, x * 0.45F, 3.0F, 1.5F, MenuRenderer.replaceAlpha(t, q));
         MenuRenderer.fillCircle(matrix3x2fStack, v + x - 8.0F, w + y / 2.0F, 3.0F, MenuRenderer.replaceAlpha(t, q));
         MenuRenderer.drawText(
            matrix3x2fStack,
            nestedType395.name(),
            r + 8.0F,
            s - u + 54.0F - 9.0F,
            5.5F,
            MenuRenderer.withAlpha(MenuRenderer.blendRgb(12103872, 16777215, Math.max(this.hov[p], bl1 ? 1.0F : 0.0F)), q)
         );
         if (bl1) {
            MenuRenderer.fillCircle(matrix3x2fStack, r + this.cellW - 9.0F, s - u + 54.0F - 9.0F, 3.0F, MenuRenderer.replaceAlpha(t, q));
         }
      }

      float ba = o + 14.0F + 124.0F + 6.0F;
      ScreenComponent214.performAction_3077(matrix3x2fStack, "CUSTOMIZE", f, ba, h, n);
      float bb = (h - 8.0F) / 2.0F;

      for (int bc = 0; bc < this.ctls.size(); bc++) {
         ScreenComponent214.NestedType387 nestedType387 = this.ctls.get(bc);
         float bd = MenuRenderer.easeOutCubic(MenuRenderer.clamp01((this.floatValueField3 - 0.35F - bc * 0.05F) / 0.35F)) * j;
         nestedType387.performAction_3082(f + bc % 2 * (bb + 8.0F), ba + 8.0F + bc / 2 * 34 + (1.0F - bd) * 6.0F, bb);
         nestedType387.performAction_3079(matrix3x2fStack, bd, k, l);
      }

      MenuRenderer.drawText(
         matrix3x2fStack,
         "CHANGES APPLY TO THE WHOLE CLIENT INSTANTLY AND ARE SAVED AUTOMATICALLY",
         f,
         g + i - 6.0F,
         5.0F,
         MenuRenderer.withAlpha(16777215, 0.25F * n)
      );
   }

   public boolean checkCondition_3276(double d, double e, int i) {
      if (i != 0) {
         return false;
      }

      for (int j = 0; j < ScreenComponent226.ALL.size(); j++) {
         if (UtilComponent279.checkCondition_3576(d, e, this.floatValueArrayField1[j], this.floatValueArrayField2[j], this.cellW, 54.0F)) {
            ScreenComponent226.performAction_3259(ScreenComponent226.ALL.get(j));
            MenuRenderer.playClickSound(1.3F, 0.8F);
            return true;
         }
      }

      for (ScreenComponent214.NestedType387 nestedType387 : this.ctls) {
         if (nestedType387.checkCondition_3078(d, e, i)) {
            this.active = nestedType387;
            return true;
         }
      }

      return false;
   }

   public boolean checkCondition_3274(double d, double e) {
      return this.active != null && this.active.checkCondition_3084(d, e);
   }

   public void performAction_3270() {
      if (this.active != null) {
         this.active.performAction_3081();
      }

      this.active = null;
   }
}
