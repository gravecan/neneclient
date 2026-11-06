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
import aethereal.util.KeyUtil;
import aethereal.util.UtilComponent279;
import net.minecraft.client.gui.screen.ChatScreen;

public class WidgetComponent241 extends WidgetComponent249 implements MinecraftAccess {
   public WidgetComponent241() {
      super(new ElementComponent210("Keys", 0.0F, 0.0F, 0.0F, 0.0F));
      this.getDragInfo_3360().setWidgetComponent249Field1_3016(this);
   }

   @Override
   public void onDrawEvent_3315(DrawEvent drawEvent) {
      this.getAnimationField3_3363().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      float f = this.getDragInfo_3360().computeValue_3007();
      float g = this.getDragInfo_3360().computeValue_3009();
      float h = 14.5F + RenderComponent188.renderComponent187Field5.computeValue_2626("Hot-keys", this.floatValueField8) + 5.0F + 2.0F;
      float i = g + this.floatValueField7 + 3.0F;
      float j = RenderComponent188.renderComponent187Field1.computeValue_2626("Q", 6.5F);
      boolean bl = false;

      for (Module module : NeneClient.getInstance().getProcessors().getModules().getEntries()) {
         if (module.getKeybind() != -1 && module.getEnableAnimation().getFloatValueField4_2530() > 0.0F) {
            bl = true;
            h = Math.max(
               h,
               19.0F
                  + RenderComponent188.renderComponent187Field5.computeValue_2626(module.getName(), 6.5F)
                  + 8.0F
                  + RenderComponent188.renderComponent187Field5.computeValue_2626(KeyUtil.getLabel_3478(module.getKeybind()), 6.5F)
                  + 4.0F
                  + j
                  + 5.0F
                  + 2.0F
            );
         }
      }

      float r = UtilComponent279.computeValue_3586(this.getDragInfo_3360().getWidth_3005(), h, 0.5F);
      this.getDragInfo_3360().performAction_3006(r);
      this.performAction_3376(drawEvent, "Q", "Hot-keys", r, this.computeValue_3367());

      for (Module module1 : NeneClient.getInstance().getProcessors().getModules().getEntries()) {
         module1.getEnableAnimation().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
         float k = module1.getKeybind() != -1 ? module1.getEnableAnimation().getFloatValueField4_2530() * this.computeValue_3367() : 0.0F;
         if (k > 0.0F) {
            float l = -8.0F * (1.0F - k);
            float m = -(1.0F - k);
            float n = i + m;
            float o = RenderComponent188.renderComponent187Field5.computeValue_2626(KeyUtil.getLabel_3478(module1.getKeybind()), 6.5F);
            float p = f + l + r - 5.0F - j - 1.0F;
            float q = n + (11.5F - RenderComponent188.renderComponent187Field5.computeValue_2622(6.5F)) / 2.0F - 0.5F;
            this.performAction_3372(drawEvent, f + l, n, r, 11.5F, false, k);
            this.performAction_3371(drawEvent, f + l + 15.0F, n, 11.5F, k);
            RenderComponent188.renderComponent187Field1
               .performAction_2630(
                  drawEvent.getStack_1005(),
                  module1.getCategory().getIcon_0560(),
                  f + l + 5.0F,
                  n + (11.5F - RenderComponent188.renderComponent187Field1.computeValue_2622(6.5F)) / 2.0F - 0.15F,
                  6.5F,
                  RenderComponent183.computeValue_2549(
                     NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), k
                  )
               );
            RenderComponent188.renderComponent187Field5
               .performAction_2630(drawEvent.getStack_1005(), module1.getName(), f + l + 19.0F, q, 6.5F, RenderComponent183.computeValue_2549(-1, k));
            RenderComponent188.renderComponent187Field5
               .performAction_2630(
                  drawEvent.getStack_1005(),
                  KeyUtil.getLabel_3478(module1.getKeybind()),
                  p - 4.0F - o,
                  q,
                  6.5F,
                  RenderComponent183.computeValue_2549(-1, 0.55F * k)
               );
            RenderComponent188.renderComponent187Field1
               .performAction_2630(
                  drawEvent.getStack_1005(),
                  "C",
                  p,
                  n + (11.5F - RenderComponent188.renderComponent187Field1.computeValue_2622(6.5F)) / 2.0F + 0.15F,
                  6.5F,
                  RenderComponent183.computeValue_2549(
                     NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), k
                  )
               );
            i += 13.5F * k;
         }
      }

      this.getDragInfo_3360().performAction_3018(bl ? i - g - 2.0F : this.floatValueField7);
      super.onDrawEvent_3315(drawEvent);
   }

   @Override
   public void performAction_3316(ClientTickEvent clientTickEvent) {
      boolean bl = minecraft.currentScreen instanceof ChatScreen;

      for (Module module : NeneClient.getInstance().getProcessors().getModules().getEntries()) {
         if (module.getKeybind() != -1 && module.getEnableAnimation().getFloatValueField4_2530() > 0.0F) {
            bl = true;
            break;
         }
      }

      this.getAnimationField3_3363().performAction_2528(bl);
      super.performAction_3316(clientTickEvent);
   }
}
