package aethereal.util;

import aethereal.core.MinecraftAccess;
import org.lwjgl.glfw.GLFW;

public class CursorUtil implements MinecraftAccess {
   private CursorUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static void performAction_3422(CursorUtil.State404 state404) {
      if (minecraft.getWindow() != null) {
         long l = GLFW.glfwCreateStandardCursor(state404.getGlfwType_3423());
         if (l != 0L) {
            GLFW.glfwSetCursor(minecraft.getWindow().getHandle(), l);
         }
      }
   }

   public enum State404 {
      DEFAULT(221185),
      HAND(221188),
      ARROW_HORIZONTAL(221189),
      ARROW_VERTICAL(221190),
      TEXT(221186),
      CROSSHAIR(221187),
      BLOCK(221194),
      RESIZE_ALL(221193);

      private final int glfwType;

      State404(final int j) {
         this.glfwType = j;
      }

      public int getGlfwType_3423() {
         return this.glfwType;
      }
   }
}
