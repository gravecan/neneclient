package aethereal.ui.widget;

import aethereal.core.ClientTickEvent;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.module.misc.ServerAssistant;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.ui.element.ElementComponent210;
import aethereal.util.KeyUtil;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent279;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import platform.inject.accessors.ItemCooldownEntryAccessor;
import platform.inject.accessors.ItemCooldownManagerAccessor;

public class WidgetComponent242 extends WidgetComponent249 implements MinecraftAccess {
   public WidgetComponent242() {
      super(new ElementComponent210("Items", 0.0F, 0.0F, 0.0F, 0.0F));
      this.getDragInfo_3360().setWidgetComponent249Field1_3016(this);
   }

   @Override
   public void onDrawEvent_3315(DrawEvent drawEvent) {
      this.getAnimationField3_3363().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      ServerAssistant serverAssistant = NeneClient.getInstance().getProcessors().getModules().getServerAssistantField6_0436();
      List list = serverAssistant.getListField1_1962();
      boolean bl = false;

      for (ServerAssistant.NestedType346 nestedType346 : list) {
         nestedType346.getAnimationUtil_1981().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
         bl |= nestedType346.getAnimationUtil_1981().getFloatValueField4_2530() > 0.0F;
      }

      boolean bl1 = !bl && this.computeValue_3367() > 0.0F;
      float m = this.getDragInfo_3360().computeValue_3009();
      float f = this.getDragInfo_3360().computeValue_3007();
      float g = f;
      ItemCooldownManagerAccessor itemCooldownManagerAccessor = (ItemCooldownManagerAccessor)minecraft.player.getItemCooldownManager();

      for (int i = 0; i < list.size(); i++) {
         ServerAssistant.NestedType346 nestedType3461 = (ServerAssistant.NestedType346)list.get(i);
         float h = bl1
            ? (i < 3 ? this.computeValue_3367() : 0.0F)
            : nestedType3461.getAnimationUtil_1981().getFloatValueField4_2530() * this.computeValue_3367();
         if (h > 0.0F) {
            Identifier identifier = Registries.ITEM.getId(nestedType3461.getItem_1983());
            Object object = itemCooldownManagerAccessor.getEntries().get(identifier);
            int j = object != null ? Math.max(((ItemCooldownEntryAccessor)object).getEndTick() - itemCooldownManagerAccessor.getTick(), 0) : 0;
            String string = j > 0 ? String.format(Locale.US, "%.1f", j / 20.0F) : KeyUtil.getLabel_3478(nestedType3461.getBind_1982().getCurrentValue());
            float k = 19.5F + RenderComponent188.renderComponent187Field5.computeValue_2626(string, 6.5F) + 5.0F;
            float l = m + (this.floatValueField7 - RenderComponent188.renderComponent187Field5.computeValue_2622(6.5F)) / 2.0F - 0.5F;
            this.performAction_3372(drawEvent, g, m, k, this.floatValueField7, true, h);
            NeneClient.getInstance()
               .getProcessors()
               .getRenderComponent185Field4_0678()
               .performAction_2592(
                  drawEvent.getContext_1000(),
                  nestedType3461.getItem_1983().getDefaultStack(),
                  g + 3.0F,
                  m + (this.floatValueField7 - 16.0F) / 2.0F + 3.0F,
                  200,
                  h,
                  0.6F,
                  false
               );
            this.performAction_3371(drawEvent, g + 15.5F, m, this.floatValueField7, h);
            RenderComponent188.renderComponent187Field5
               .performAction_2630(drawEvent.getStack_1005(), string, g + 19.5F, l, 6.5F, RenderComponent183.computeValue_2549(-1, h));
            g += (k + 2.0F) * h;
         }
      }

      this.getDragInfo_3360()
         .performAction_3006(UtilComponent279.computeValue_3586(this.getDragInfo_3360().getWidth_3005(), Math.max(0.0F, g - f - 2.0F), 0.5F));
      this.getDragInfo_3360().performAction_3018(!bl && !bl1 ? 0.0F : this.floatValueField7);
      super.onDrawEvent_3315(drawEvent);
   }

   @Override
   public void performAction_3316(ClientTickEvent clientTickEvent) {
      if (minecraft.player != null) {
         ServerAssistant serverAssistant = NeneClient.getInstance().getProcessors().getModules().getServerAssistantField6_0436();
         boolean bl = minecraft.currentScreen instanceof ChatScreen;

         for (ServerAssistant.NestedType346 nestedType346 : serverAssistant.getListField1_1962()) {
            nestedType346.getAnimationUtil_1981()
               .performAction_2528(
                  serverAssistant.isEnabled()
                     && nestedType346.getBind_1982().getCurrentValue() != -1
                     && nestedType346.getBind_1982().getVisibility().get()
                     && UtilComponent270.computeValue_3526(nestedType346.getItem_1983()) != -1
               );
            if (nestedType346.getAnimationUtil_1981().getFloatValueField4_2530() > 0.0F) {
               bl = true;
            }
         }

         this.getAnimationField3_3363().performAction_2528(bl);
         super.performAction_3316(clientTickEvent);
      }
   }
}
