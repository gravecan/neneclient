package aethereal.ui.shader;

import aethereal.render.RenderComponent183;
import java.awt.Color;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class ShaderComponent231 {
   private ShaderComponent231() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static MutableText computeValue_3296(String string, int i, int j, int k, float f) {
      MutableText mutableText = Text.literal("");
      if (string != null && !string.isEmpty()) {
         float g = (float)(System.currentTimeMillis() % 10000L) / 1000.0F * (100.0F / k);
         int l = string.length();

         for (int m = 0; m < l; m++) {
            char c = string.charAt(m);
            int n = RenderComponent183.computeValue_2557(i, j, m, l, g, f);
            Style style = Style.EMPTY.withColor(n & 16777215);
            mutableText.append(Text.literal(String.valueOf(c)).setStyle(style));
         }

         return mutableText;
      } else {
         return mutableText;
      }
   }

   public static MutableText computeValue_3295(String string, int i, float f, float g) {
      MutableText mutableText = Text.literal("");
      if (string != null && !string.isEmpty()) {
         float h = (float)(System.currentTimeMillis() % (long)(f * 1000.0F)) / (f * 1000.0F);
         float[] j = Color.RGBtoHSB(i >> 16 & 0xFF, i >> 8 & 0xFF, i & 0xFF, (float[])null);

         for (int k = 0; k < string.length(); k++) {
            float l = (float)(Math.sin((h + k * g / string.length()) * 3.1415926649985018 * 2.0) * 0.5 + 0.5);
            int m = Color.HSBtoRGB(j[0], j[1], j[2] * (0.5F + 0.5F * l)) & 16777215;
            mutableText.append(Text.literal(String.valueOf(string.charAt(k))).setStyle(Style.EMPTY.withColor(m)));
         }

         return mutableText;
      } else {
         return mutableText;
      }
   }

   public static int computeValue_3294(int i, int j, int k, int l, long m) {
      float f = (float)((j + m / 10L) % 360L);
      float g = f / 360.0F;
      return RenderComponent183.computeValue_2553(k, l, (float)((Math.sin(g * 3.1415926649985018 * 2.0 * i) + 1.0) / 2.0));
   }
}
