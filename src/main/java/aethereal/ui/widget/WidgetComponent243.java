package aethereal.ui.widget;

import aethereal.config.ThemeInfo;
import aethereal.core.ClientTickEvent;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.ui.element.ElementComponent210;
import aethereal.ui.screen.MenuRenderer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WidgetComponent243 extends WidgetComponent249 implements MinecraftAccess {
   private static final int MAX_VISIBLE_MODULES = 14;
   private final List<Module> enabledModules = new ArrayList<>();

   public WidgetComponent243() {
      super(new ElementComponent210("Enabled Modules", 5.0F, 5.0F, 120.0F, 18.0F));
      this.getDragInfo_3360().setWidgetComponent249Field1_3016(this);
   }

   @Override
   public void performAction_3316(ClientTickEvent clientTickEvent) {
      this.getAnimationField3_3363().performAction_2528(true);
      super.performAction_3316(clientTickEvent);
   }

   @Override
   public void onDrawEvent_3315(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001() && !minecraft.options.hudHidden) {
         this.getAnimationField3_3363().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
         float f = this.computeValue_3367();
         if (!(f <= 0.001F)) {
            this.enabledModules.clear();

            for (Module module : NeneClient.getInstance().getProcessors().getModules().getEntries()) {
               if (module.isEnabled() && !"Interface".equals(module.getName())) {
                  this.enabledModules.add(module);
               }
            }

            this.enabledModules.sort(Comparator.comparing(Module::getName, String.CASE_INSENSITIVE_ORDER));
            String string = MenuRenderer.getDisplayName();
            int n = Math.min(this.enabledModules.size(), 14);
            float g = Math.max(108.0F, RenderComponent188.renderComponent187Field5.computeValue_2626(string, 7.0F) + 12.0F);

            for (int i = 0; i < n; i++) {
               g = Math.max(g, RenderComponent188.renderComponent187Field5.computeValue_2626(this.enabledModules.get(i).getName(), 6.5F) + 22.0F);
            }

            if (this.enabledModules.size() > 14) {
               g = Math.max(g, RenderComponent188.renderComponent187Field5.computeValue_2626("+" + (this.enabledModules.size() - 14) + " more", 6.5F) + 22.0F);
            }

            float o = this.getDragInfo_3360().computeValue_3007();
            float h = this.getDragInfo_3360().computeValue_3009();
            float j = 17.0F + n * 11.5F + (this.enabledModules.size() > 14 ? 11.5F : 0.0F);
            this.getDragInfo_3360().performAction_3006(g);
            this.getDragInfo_3360().performAction_3018(j);
            int k = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
            this.performAction_3372(drawEvent, o, h, g, j, false, f);
            RenderComponent188.renderComponent187Field5
               .performAction_2630(drawEvent.getStack_1005(), string, o + 6.0F, h + 4.0F, 7.0F, RenderComponent183.computeValue_2549(k, f));
            drawEvent.getRenderComponent184Field1_0998()
               .performAction_2577(
                  drawEvent.getContext_1000().getMatrices(), o + 6.0F, h + 14.5F, g - 12.0F, 0.75F, 0.0F, RenderComponent183.computeValue_2549(k, 0.35F * f)
               );
            float l = h + 19.0F;

            for (int m = 0; m < n; m++) {
               Module module1 = this.enabledModules.get(m);
               RenderComponent188.renderComponent187Field1
                  .performAction_2630(
                     drawEvent.getStack_1005(), module1.getCategory().getIcon_0560(), o + 6.0F, l + 1.0F, 6.5F, RenderComponent183.computeValue_2549(k, f)
                  );
               RenderComponent188.renderComponent187Field5
                  .performAction_2630(drawEvent.getStack_1005(), module1.getName(), o + 18.0F, l + 1.0F, 6.5F, RenderComponent183.computeValue_2549(-1, f));
               l += 11.5F;
            }

            if (this.enabledModules.size() > 14) {
               RenderComponent188.renderComponent187Field5
                  .performAction_2630(
                     drawEvent.getStack_1005(),
                     "+" + (this.enabledModules.size() - 14) + " more",
                     o + 18.0F,
                     l + 1.0F,
                     6.5F,
                     RenderComponent183.computeValue_2549(-1, 0.55F * f)
                  );
            }

            super.onDrawEvent_3315(drawEvent);
         }
      }
   }
}
