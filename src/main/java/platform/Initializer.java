package platform;

import aethereal.api.ApiAnnotation001;
import aethereal.core.NeneClient;
import aethereal.core.ObfuscatorRuntime;
import aethereal.render.RenderComponent187;
import aethereal.render.RenderComponent189;
import aethereal.ui.screen.ScreenComponent228;
import net.fabricmc.api.ClientModInitializer;

public class Initializer implements ClientModInitializer {
   @ApiAnnotation001
   public void onInitializeClient() {
      new NeneClient();
      ScreenComponent228.performAction_3280();

      try {
         RenderComponent189.performAction_2643();
         RenderComponent187.computeValue_2623();
         System.out.println("[NeneClient] Client mod initialized successfully, pipelines and fonts loaded!");
      } catch (Throwable throwable) {
         System.err.println("[NeneClient] Warning during font preload: " + throwable.getMessage());
         throwable.printStackTrace();
      }
   }

   static {
      ObfuscatorRuntime.registerClass(Initializer.class, 1);
   }
}
