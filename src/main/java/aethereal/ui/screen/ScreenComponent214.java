package aethereal.ui.screen;

import aethereal.util.UtilComponent279;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import org.joml.Matrix3x2fStack;

public final class ScreenComponent214 {
   private ScreenComponent214() {
   }

   public static void performAction_3077(Matrix3x2fStack matrix3x2fStack, String string, float f, float g, float h, float i) {
      MenuRenderer.drawText(matrix3x2fStack, string, f, g, 5.5F, MenuRenderer.withAlpha(16777215, 0.4F * i));
      float j = MenuRenderer.textWidth(string, 5.5F);
      MenuRenderer.fillRoundedRect(matrix3x2fStack, f + j + 6.0F, g, h - j - 6.0F, 1.0F, 0.0F, MenuRenderer.withAlpha(16777215, 0.06F * i));
   }

   public static final class NestedType386 extends ScreenComponent214.NestedType387 {
      private final String label;
      private final String[] opts;
      private final IntSupplier get;
      private final IntConsumer set;
      private final float[] selA;
      private final float[] floatValueArrayField1;
      private final float[] floatValueArrayField2;

      public NestedType386(String string, String[] strings, IntSupplier intSupplier, IntConsumer intConsumer) {
         this.label = string;
         this.opts = strings;
         this.get = intSupplier;
         this.set = intConsumer;
         this.selA = new float[strings.length];
         this.floatValueArrayField1 = new float[strings.length];
         this.floatValueArrayField2 = new float[strings.length];
      }

      @Override
      public void performAction_3079(Matrix3x2fStack matrix3x2fStack, float f, int i, int j) {
         this.performAction_3083(matrix3x2fStack, f, i, j, false);
         int k = MenuRenderer.getAccent();
         MenuRenderer.drawText(
            matrix3x2fStack,
            this.label,
            this.floatValueField2 + 10.0F,
            this.floatValueField3 + 15.0F,
            6.5F,
            MenuRenderer.replaceAlpha(MenuRenderer.getTextColor(), f)
         );
         float g = 0.0F;

         for (String string : this.opts) {
            g += MenuRenderer.textWidth(string, 5.5F) + 12.0F + 3.0F;
         }

         g -= 3.0F;
         float h = this.floatValueField2 + this.floatValueField4 - 10.0F - g;
         int l = this.get.getAsInt();

         for (int m = 0; m < this.opts.length; m++) {
            float n = MenuRenderer.textWidth(this.opts[m], 5.5F) + 12.0F;
            this.floatValueArrayField1[m] = h;
            this.floatValueArrayField2[m] = n;
            boolean bl = UtilComponent279.checkCondition_3576(i, j, h, this.floatValueField3 + 7.0F, n, 16.0F);
            this.selA[m] = MenuRenderer.approach(this.selA[m], m == l ? 1.0F : 0.0F, 14.0F);
            MenuRenderer.fillRoundedRect(
               matrix3x2fStack, h, this.floatValueField3 + 7.0F, n, 16.0F, 4.0F, MenuRenderer.withAlpha(16777215, (0.05F + (bl ? 0.05F : 0.0F)) * f)
            );
            MenuRenderer.fillRoundedRect(
               matrix3x2fStack, h, this.floatValueField3 + 7.0F, n, 16.0F, 4.0F, MenuRenderer.replaceAlpha(k, 0.85F * this.selA[m] * f)
            );
            MenuRenderer.drawCenteredText(
               matrix3x2fStack,
               this.opts[m],
               h + n / 2.0F,
               this.floatValueField3 + 15.0F,
               5.5F,
               MenuRenderer.withAlpha(MenuRenderer.blendRgb(12103872, 16777215, Math.max(this.selA[m], bl ? 0.6F : 0.0F)), f)
            );
            h += n + 3.0F;
         }
      }

      @Override
      public boolean checkCondition_3078(double d, double e, int i) {
         if (i == 0 && this.checkCondition_3080(d, e)) {
            for (int j = 0; j < this.opts.length; j++) {
               if (d >= this.floatValueArrayField1[j] && d <= this.floatValueArrayField1[j] + this.floatValueArrayField2[j]) {
                  if (this.get.getAsInt() != j) {
                     this.set.accept(j);
                     MenuRenderer.playClickSound(1.35F, 0.7F);
                  }

                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      }
   }

   public abstract static class NestedType387 {
      public static final float floatValueField1 = 30.0F;
      public float floatValueField2;
      public float floatValueField3;
      public float floatValueField4;
      protected float hover;

      public void performAction_3082(float f, float g, float h) {
         this.floatValueField2 = f;
         this.floatValueField3 = g;
         this.floatValueField4 = h;
      }

      protected boolean checkCondition_3080(double d, double e) {
         return UtilComponent279.checkCondition_3576(d, e, this.floatValueField2, this.floatValueField3, this.floatValueField4, 30.0F);
      }

      protected void performAction_3083(Matrix3x2fStack matrix3x2fStack, float f, int i, int j, boolean bl) {
         boolean bl1 = UtilComponent279.checkCondition_3576(i, j, this.floatValueField2, this.floatValueField3, this.floatValueField4, 30.0F);
         this.hover = MenuRenderer.approach(this.hover, !bl1 && !bl ? 0.0F : 1.0F, 14.0F);
         MenuRenderer.fillRoundedRect(
            matrix3x2fStack,
            this.floatValueField2,
            this.floatValueField3,
            this.floatValueField4,
            30.0F,
            7.0F,
            MenuRenderer.withAlpha(MenuRenderer.blendRgb(1446428, 2300717, this.hover), 0.92F * f)
         );
         MenuRenderer.strokeRoundedRect(
            matrix3x2fStack,
            this.floatValueField2,
            this.floatValueField3,
            this.floatValueField4,
            30.0F,
            7.0F,
            0.5F,
            MenuRenderer.withAlpha(16777215, (0.05F + 0.08F * this.hover) * f)
         );
      }

      public abstract void performAction_3079(Matrix3x2fStack matrix3x2fStack, float f, int i, int j);

      public boolean checkCondition_3078(double d, double e, int i) {
         return false;
      }

      public boolean checkCondition_3084(double d, double e) {
         return false;
      }

      public void performAction_3081() {
      }
   }

   public static final class NestedType388 extends ScreenComponent214.NestedType387 {
      private final String label;
      private final double min;
      private final double max;
      private final double step;
      private final DoubleSupplier get;
      private final DoubleConsumer set;
      private final DoubleFunction<String> fmt;
      private final boolean rainbow;
      private float shown = -1.0F;
      private float trackX;
      private float trackW;
      private boolean dragging;

      public NestedType388(
         String string,
         double d,
         double e,
         double f,
         DoubleSupplier doubleSupplier,
         DoubleConsumer doubleConsumer,
         DoubleFunction<String> doubleFunction,
         boolean bl
      ) {
         this.label = string;
         this.min = d;
         this.max = e;
         this.step = f;
         this.get = doubleSupplier;
         this.set = doubleConsumer;
         this.fmt = doubleFunction;
         this.rainbow = bl;
      }

      @Override
      public void performAction_3079(Matrix3x2fStack matrix3x2fStack, float f, int i, int j) {
         this.performAction_3083(matrix3x2fStack, f, i, j, this.dragging);
         double d = this.get.getAsDouble();
         float g = MenuRenderer.clamp01((float)((d - this.min) / (this.max - this.min)));
         if (this.shown < 0.0F) {
            this.shown = g;
         }

         this.shown = MenuRenderer.approach(this.shown, g, this.dragging ? 30.0F : 14.0F);
         int k = MenuRenderer.getAccent();
         MenuRenderer.drawText(
            matrix3x2fStack,
            this.label,
            this.floatValueField2 + 10.0F,
            this.floatValueField3 + 9.0F,
            6.0F,
            MenuRenderer.replaceAlpha(MenuRenderer.getTextColor(), f)
         );
         MenuRenderer.drawRightAlignedText(
            matrix3x2fStack,
            this.fmt.apply(d),
            this.floatValueField2 + this.floatValueField4 - 10.0F,
            this.floatValueField3 + 9.0F,
            6.0F,
            MenuRenderer.replaceAlpha(k, f)
         );
         this.trackX = this.floatValueField2 + 10.0F;
         this.trackW = this.floatValueField4 - 20.0F;
         float h = this.floatValueField3 + 30.0F - 9.0F;
         MenuRenderer.fillRoundedRect(matrix3x2fStack, this.trackX, h - 1.5F, this.trackW, 3.0F, 1.5F, MenuRenderer.withAlpha(16777215, 0.1F * f));
         int l = MenuRenderer.withAlpha(16777215, f);
         if (this.rainbow) {
            byte b = 40;
            float m = this.trackW / b;

            for (int n = 0; n < b; n++) {
               MenuRenderer.fillRoundedRect(
                  matrix3x2fStack,
                  this.trackX + n * m,
                  h - 1.5F,
                  m + 0.6F,
                  3.0F,
                  0.0F,
                  MenuRenderer.withAlpha(ScreenComponent226.computeValue_3258(n * 360.0F / b, 0.82F, 1.0F), 0.9F * f)
               );
            }

            l = MenuRenderer.withAlpha(ScreenComponent226.computeValue_3258(this.shown * 360.0F, 0.82F, 1.0F), f);
         } else if (this.shown > 0.001F) {
            MenuRenderer.fillRoundedRect(
               matrix3x2fStack, this.trackX, h - 1.5F, Math.max(3.0F, this.trackW * this.shown), 3.0F, 1.5F, MenuRenderer.replaceAlpha(k, f)
            );
         }

         float o = this.trackX + this.trackW * this.shown;
         MenuRenderer.fillCircle(matrix3x2fStack, o, h, 4.6F + this.hover * 0.8F, MenuRenderer.withAlpha(0, 0.35F * f));
         MenuRenderer.fillCircle(matrix3x2fStack, o, h, 3.6F + this.hover * 0.6F, MenuRenderer.withAlpha(16777215, f));
         MenuRenderer.fillCircle(matrix3x2fStack, o, h, 2.2F, l);
      }

      private void performAction_3085(double d) {
         float f = MenuRenderer.clamp01((float)((d - this.trackX) / this.trackW));
         double e = this.min + (this.max - this.min) * f;
         if (this.step > 0.0) {
            e = Math.round(e / this.step) * this.step;
         }

         this.set.accept(Math.max(this.min, Math.min(this.max, e)));
      }

      @Override
      public boolean checkCondition_3078(double d, double e, int i) {
         if (i == 0 && this.checkCondition_3080(d, e)) {
            this.dragging = true;
            this.performAction_3085(d);
            MenuRenderer.playClickSound(1.5F, 0.35F);
            return true;
         } else {
            return false;
         }
      }

      @Override
      public boolean checkCondition_3084(double d, double e) {
         if (!this.dragging) {
            return false;
         }

         this.performAction_3085(d);
         return true;
      }

      @Override
      public void performAction_3081() {
         this.dragging = false;
      }
   }

   public static final class NestedType389 extends ScreenComponent214.NestedType387 {
      private final String label;
      private final BooleanSupplier get;
      private final Consumer<Boolean> set;
      private float tog = -1.0F;

      public NestedType389(String string, BooleanSupplier booleanSupplier, Consumer<Boolean> consumer) {
         this.label = string;
         this.get = booleanSupplier;
         this.set = consumer;
      }

      @Override
      public void performAction_3079(Matrix3x2fStack matrix3x2fStack, float f, int i, int j) {
         this.performAction_3083(matrix3x2fStack, f, i, j, false);
         boolean bl = this.get.getAsBoolean();
         if (this.tog < 0.0F) {
            this.tog = bl ? 1.0F : 0.0F;
         }

         this.tog = MenuRenderer.approach(this.tog, bl ? 1.0F : 0.0F, 12.0F);
         int k = MenuRenderer.getAccent();
         MenuRenderer.drawText(
            matrix3x2fStack,
            this.label,
            this.floatValueField2 + 10.0F,
            this.floatValueField3 + 15.0F,
            6.5F,
            MenuRenderer.replaceAlpha(MenuRenderer.blendArgb(MenuRenderer.getTextColor(), k, this.tog * 0.4F), f)
         );
         float g = 22.0F;
         float h = 11.0F;
         float l = this.floatValueField2 + this.floatValueField4 - 10.0F - g;
         float m = this.floatValueField3 + 15.0F - h / 2.0F;
         MenuRenderer.fillRoundedRect(matrix3x2fStack, l - 2.0F, m - 2.0F, g + 4.0F, h + 4.0F, 7.0F, MenuRenderer.replaceAlpha(k, 0.16F * this.tog * f));
         MenuRenderer.fillRoundedRect(
            matrix3x2fStack, l, m, g, h, 5.5F, MenuRenderer.withAlpha(MenuRenderer.blendRgb(3814213, k & 16777215, this.tog), 0.95F * f)
         );
         float n = 6.5F + 2.5F * (float)Math.sin(Math.PI * this.tog);
         float o = l + 2.0F + (g - 4.0F - n) * this.tog;
         MenuRenderer.fillRoundedRect(matrix3x2fStack, o, m + 2.0F, n, h - 4.0F, 3.5F, MenuRenderer.withAlpha(16777215, f));
      }

      @Override
      public boolean checkCondition_3078(double d, double e, int i) {
         if (i == 0 && this.checkCondition_3080(d, e)) {
            boolean bl = !this.get.getAsBoolean();
            this.set.accept(bl);
            MenuRenderer.playToggleSound(bl);
            return true;
         } else {
            return false;
         }
      }
   }
}
