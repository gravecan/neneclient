package aethereal.module.visuals;

import aethereal.config.ThemeInfo;
import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent188;
import aethereal.setting.BooleanSetting;
import aethereal.ui.element.ElementComponent210;
import aethereal.ui.screen.MenuRenderer;
import aethereal.util.UtilComponent252;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.Identifier;

@ModuleRegister(name = "Watermark", description = "Shows the Nene logo and client information", category = Category.Visuals)
public class Watermark extends Module {
   private static final Identifier LOGO = Identifier.of("neneclient", "pictures/logo.png");
   private static final DateTimeFormatter CLOCK_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
   private static final float HEIGHT = 24.0F;
   private static final float TEXT_SIZE = 7.2F;
   private final BooleanSetting showServer = new BooleanSetting("Server", true);
   private final BooleanSetting showFps = new BooleanSetting("FPS", true);
   private final BooleanSetting showClock = new BooleanSetting("Clock", true);
   private final BooleanSetting showBackground = new BooleanSetting("Background", true);
   private final ElementComponent210 drag = new ElementComponent210("Watermark", 5.0F, 5.0F, 220.0F, 24.0F);
   private int cachedFps;
   private String cachedClock = "00:00";
   private String cachedServer = "Singleplayer";
   private long lastCacheUpdate;

   public Watermark() {
      this.addSettings(this.showServer, this.showFps, this.showClock, this.showBackground);
   }

   @EventTarget
   public void onDrawEvent_2346(DrawEvent drawEvent) {
      boolean bl = minecraft.currentScreen instanceof ChatScreen;
      if (drawEvent.checkCondition_1001() && (!minecraft.options.hudHidden || bl) && (minecraft.currentScreen == null || bl)) {
         this.performAction_2345();
         String string = MenuRenderer.getDisplayName();
         String[] strings = new String[]{
            string,
            this.showServer.getCurrentValue() ? this.cachedServer : null,
            this.showFps.getCurrentValue() ? this.cachedFps + " FPS" : null,
            this.showClock.getCurrentValue() ? this.cachedClock : null
         };
         float f = 30.0F;

         for (String string1 : strings) {
            if (string1 != null) {
               f += RenderComponent188.renderComponent187Field5.computeValue_2626(string1, 7.2F) + 17.0F;
            }
         }

         float h = this.drag.computeValue_3007();
         float l = this.drag.computeValue_3009();
         this.drag.performAction_3006(f);
         this.drag.performAction_3018(24.0F);
         RenderComponent184 renderComponent184 = drawEvent.getRenderComponent184Field1_0998();
         int m = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
         int i = RenderComponent183.computeValue_2549(591890, 0.88F);
         int j = RenderComponent183.computeValue_2549(-1, 0.94F);
         if (this.showBackground.getCurrentValue() || bl) {
            renderComponent184.performAction_2577(drawEvent.getStack_1005(), h, l, f, 24.0F, 5.0F, i);
            renderComponent184.performAction_2577(drawEvent.getStack_1005(), h, l + 2.0F, 2.0F, 20.0F, 1.0F, RenderComponent183.computeValue_2549(m, 0.95F));
            renderComponent184.performAction_2577(drawEvent.getStack_1005(), h, l + 24.0F - 1.0F, f, 1.0F, 0.0F, RenderComponent183.computeValue_2549(m, 0.48F));
         }

         float g = h + 7.0F;
         renderComponent184.performAction_2585(
            drawEvent.getStack_1005(), LOGO, g, l + 4.0F, 16.0F, 16.0F, 0.0F, RenderComponent183.computeValue_2549(-1, 0.98F)
         );
         g += 23.0F;

         for (int k = 0; k < strings.length; k++) {
            String string2 = strings[k];
            if (string2 != null) {
               if (k > 0) {
                  renderComponent184.performAction_2577(
                     drawEvent.getStack_1005(), g, l + 6.0F, 1.0F, 12.0F, 0.0F, RenderComponent183.computeValue_2549(m, 0.38F)
                  );
                  g += 9.0F;
               }

               RenderComponent188.renderComponent187Field5
                  .performAction_2630(
                     drawEvent.getStack_1005(),
                     string2,
                     g,
                     l + (24.0F - RenderComponent188.renderComponent187Field5.computeValue_2622(7.2F)) / 2.0F - 0.5F,
                     7.2F,
                     j
                  );
               g += RenderComponent188.renderComponent187Field5.computeValue_2626(string2, 7.2F) + 8.0F;
            }
         }
      }
   }

   private void performAction_2345() {
      long l = System.currentTimeMillis();
      if (l - this.lastCacheUpdate >= 250L) {
         this.lastCacheUpdate = l;
         this.cachedFps = minecraft.getCurrentFps();
         this.cachedClock = LocalTime.now().format(CLOCK_FORMAT);
         String string = minecraft.player == null ? "Menu" : UtilComponent252.computeValue_3400();
         this.cachedServer = string != null && !string.isBlank() ? string : "Singleplayer";
      }
   }
}
