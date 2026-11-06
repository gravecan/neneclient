package aethereal.render;

import java.awt.Color;

public class RenderComponent183 {
   private RenderComponent183() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static float[] computeValue_2559(Color color) {
      return new float[]{color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F};
   }

   public static float[] computeValue_2554(int i) {
      int[] j = computeValue_2555(i);
      return new float[]{j[0] / 255.0F, j[1] / 255.0F, j[2] / 255.0F, j[3] / 255.0F};
   }

   public static int[] computeValue_2555(int i) {
      return new int[]{i >> 16 & 0xFF, i >> 8 & 0xFF, i & 0xFF, i >> 24 & 0xFF};
   }

   public static int computeValue_2557(int i, int j, float f, float g, float h, float k) {
      float l = 18.0F / k;
      float m = (h + f / (g * l)) % 1.0F;
      float n = (float)Math.sin(m * 3.1415938456874706 * 2.0) * 0.5F + 0.5F;
      int o = i >> 24 & 0xFF;
      int p = i >> 16 & 0xFF;
      int q = i >> 8 & 0xFF;
      int r = i & 0xFF;
      int s = j >> 24 & 0xFF;
      int t = j >> 16 & 0xFF;
      int u = j >> 8 & 0xFF;
      int v = j & 0xFF;
      int w = (int)(o + (s - o) * n);
      int x = (int)(p + (t - p) * n);
      int y = (int)(q + (u - q) * n);
      int z = (int)(r + (v - r) * n);
      return w << 24 | x << 16 | y << 8 | z;
   }

   public static int computeValue_2549(int i, float f) {
      return i & 16777215 | Math.round(f * 255.0F) << 24;
   }

   public static int computeValue_2556(int i, int j) {
      return i & 16777215 | j << 24;
   }

   public static int computeValue_2558(int i, int j, int k, int l) {
      return l << 24 | i << 16 | j << 8 | k;
   }

   public static int computeValue_2551(int i, int j, int k) {
      return 0xFF000000 | i << 16 | j << 8 | k;
   }

   public static int computeValue_2550(int i, int j, float f) {
      int[] k = computeValue_2555(i);
      int[] l = computeValue_2555(j);
      return computeValue_2558(
         (int)(k[0] + (l[0] - k[0]) * f), (int)(k[1] + (l[1] - k[1]) * f), (int)(k[2] + (l[2] - k[2]) * f), (int)(k[3] + (l[3] - k[3]) * f)
      );
   }

   public static int computeValue_2552(int i, float f) {
      float[] g = computeValue_2554(i);
      float[] h = Color.RGBtoHSB((int)(g[0] * 255.0F), (int)(g[1] * 255.0F), (int)(g[2] * 255.0F), (float[])null);
      h[2] *= f;
      h[2] = Math.max(0.0F, Math.min(1.0F, h[2]));
      int j = Color.HSBtoRGB(h[0], h[1], h[2]);
      return computeValue_2556(j, (int)(g[3] * 255.0F));
   }

   public static int computeValue_2553(int i, int j, float f) {
      int[] k = computeValue_2555(i);
      int[] l = computeValue_2555(j);
      return computeValue_2558(
         (int)(k[0] + (l[0] - k[0]) * f), (int)(k[1] + (l[1] - k[1]) * f), (int)(k[2] + (l[2] - k[2]) * f), (int)(k[3] + (l[3] - k[3]) * f)
      );
   }
}
