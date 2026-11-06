package aethereal.render;

import aethereal.core.MinecraftAccess;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;

public class RenderComponent192 implements MinecraftAccess {
   private RenderComponent192() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static void performAction_2676(DrawContext drawContext, int i) {
      Window window = minecraft.getWindow();
      int j = window.calculateScaleFactor(i, minecraft.forcesUnicodeFont());
      window.setScaleFactor(j);
      drawContext.getMatrices().pushMatrix();
   }

   public static void performAction_2675(DrawContext drawContext) {
      drawContext.getMatrices().popMatrix();
      minecraft.getWindow()
         .setScaleFactor(minecraft.getWindow().calculateScaleFactor((Integer)minecraft.options.getGuiScale().getValue(), minecraft.forcesUnicodeFont()));
   }

   public static void performAction_2673(DrawContext drawContext) {
      performAction_2676(drawContext, (Integer)minecraft.options.getGuiScale().getValue());
   }

   public static void performAction_2674(DrawContext drawContext) {
      drawContext.getMatrices().popMatrix();
      minecraft.getWindow().setScaleFactor(minecraft.getWindow().calculateScaleFactor(2, minecraft.forcesUnicodeFont()));
   }
}
