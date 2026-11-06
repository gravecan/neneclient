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
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.gui.screen.ChatScreen;

@ModuleRegister(name = "ArrayList", description = "Shows enabled modules on the HUD", category = Category.Visuals)
public class ArrayList extends Module {
   private static final float TEXT_SIZE = 7.0F;
   private static final float ROW_HEIGHT = 13.0F;
   private static final float SLIDE_DISTANCE = 10.0F;
   private final BooleanSetting background = new BooleanSetting("Background", true);
   private final List<Module> visibleModules = new java.util.ArrayList<>();
   private final ElementComponent210 drag = new ElementComponent210("ArrayList", 5.0F, 5.0F, 100.0F, 20.0F);

   public ArrayList() {
      this.addSettings(this.background);
   }

   @EventTarget
   public void onDrawEvent_2342(DrawEvent drawEvent) {
      boolean bl = minecraft.currentScreen instanceof ChatScreen;
      if (drawEvent.checkCondition_1001() && (!minecraft.options.hudHidden || bl) && (minecraft.currentScreen == null || bl)) {
         this.visibleModules.clear();

         for (Module module : NeneClient.getInstance().getProcessors().getModules().getEntries()) {
            if (module != this && module.getEnableAnimation().getFloatValueField4_2530() > 0.001F) {
               this.visibleModules.add(module);
            }
         }

         if (!this.visibleModules.isEmpty() || bl) {
            this.visibleModules
               .sort(Comparator.comparingDouble(module3 -> -RenderComponent188.renderComponent187Field5.computeValue_2626(module3.getName(), 7.0F)));
            RenderComponent184 renderComponent184 = drawEvent.getRenderComponent184Field1_0998();
            int o = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
            int i = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.BACKGROUND_HUD).computeValue_0547();
            float f = 80.0F;

            for (Module module1 : this.visibleModules) {
               f = Math.max(f, RenderComponent188.renderComponent187Field5.computeValue_2626(module1.getName(), 7.0F) + 8.0F);
            }

            float p = this.drag.computeValue_3007();
            float q = this.drag.computeValue_3009();
            float g = Math.max(13.0F, this.visibleModules.size() * 13.0F);
            this.drag.performAction_3006(f);
            this.drag.performAction_3018(g);
            if (bl) {
               renderComponent184.performAction_2577(drawEvent.getStack_1005(), p, q, f, g, 3.0F, RenderComponent183.computeValue_2549(i, 0.38F));
            }

            for (Module module2 : this.visibleModules) {
               float h = Math.max(0.0F, Math.min(1.0F, module2.getEnableAnimation().getFloatValueField4_2530()));
               float j = RenderComponent188.renderComponent187Field5.computeValue_2626(module2.getName(), 7.0F);
               float k = j + 8.0F;
               float l = p + f - k + (1.0F - h) * 10.0F;
               int m = RenderComponent183.computeValue_2549(i, h * 0.42F);
               int n = RenderComponent183.computeValue_2549(-1, h);
               if (this.background.getCurrentValue()) {
                  renderComponent184.performAction_2577(drawEvent.getStack_1005(), l, q, k, 13.0F, 2.5F, m);
               }

               RenderComponent188.renderComponent187Field5
                  .performAction_2630(
                     drawEvent.getStack_1005(),
                     module2.getName(),
                     l + 4.0F,
                     q + (13.0F - RenderComponent188.renderComponent187Field5.computeValue_2622(7.0F)) / 2.0F - 0.5F,
                     7.0F,
                     n
                  );
               renderComponent184.performAction_2577(drawEvent.getStack_1005(), l + k - 1.0F, q, 1.0F, 13.0F, 0.0F, RenderComponent183.computeValue_2549(o, h));
               q += 13.0F * h;
            }

            if (this.visibleModules.isEmpty() && bl) {
               RenderComponent188.renderComponent187Field5
                  .performAction_2630(drawEvent.getStack_1005(), "ArrayList", p + 4.0F, q + 4.0F, 7.0F, RenderComponent183.computeValue_2549(-1, 0.8F));
            }
         }
      }
   }
}
