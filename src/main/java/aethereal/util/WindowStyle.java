package aethereal.util;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT.HRESULT;
import com.sun.jna.win32.StdCallLibrary;
import java.util.Locale;
import org.lwjgl.glfw.GLFWNativeWin32;

public final class WindowStyle {
   private static final int DWMWA_USE_IMMERSIVE_DARK_MODE = 20;

   private WindowStyle() {
   }

   public static void performAction_3491(long l, boolean bl) {
      if (checkCondition_3490() && l != 0L) {
         long m = GLFWNativeWin32.glfwGetWin32Window(l);
         if (m != 0L) {
            Memory memory = new Memory(4L);
            memory.setInt(0L, bl ? 1 : 0);
            WindowStyle.DwmApi.INSTANCE.DwmSetWindowAttribute(new HWND(new Pointer(m)), 20, memory, 4);
         }
      }
   }

   private static boolean checkCondition_3490() {
      String string = System.getProperty("os.name", "");
      return string.toLowerCase(Locale.ROOT).contains("win");
   }

   public interface DwmApi extends StdCallLibrary {
      WindowStyle.DwmApi INSTANCE = (WindowStyle.DwmApi)Native.load("dwmapi", WindowStyle.DwmApi.class);

      HRESULT DwmSetWindowAttribute(HWND hWND, int i, Pointer pointer, int j);
   }
}
