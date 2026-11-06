package aethereal.ui.widget;

import aethereal.core.ClientTickEvent;
import aethereal.core.MinecraftAccess;
import aethereal.event.DrawEvent;
import aethereal.mixin.MixinContract158;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.ui.element.ElementComponent210;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent279;
import java.util.Locale;
import java.util.Map.Entry;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import platform.inject.accessors.ItemCooldownEntryAccessor;
import platform.inject.accessors.ItemCooldownManagerAccessor;

public class WidgetComponent238 extends WidgetComponent249 implements MinecraftAccess {
   public WidgetComponent238() {
      super(new ElementComponent210("Delays", 0.0F, 0.0F, 0.0F, 0.0F));
      this.getDragInfo_3360().setWidgetComponent249Field1_3016(this);
   }

   @Override
   public void onDrawEvent_3315(DrawEvent drawEvent) {
      this.getAnimationField3_3363().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      float f = this.getDragInfo_3360().computeValue_3007();
      float g = this.getDragInfo_3360().computeValue_3009();
      float h = 14.5F + RenderComponent188.renderComponent187Field5.computeValue_2626("Cooldowns", this.floatValueField8) + 5.0F + 2.0F;
      float i = g + this.floatValueField7 + 3.0F;
      ItemCooldownManagerAccessor itemCooldownManagerAccessor = (ItemCooldownManagerAccessor)minecraft.player.getItemCooldownManager();
      boolean bl = false;

      for (Entry entry : itemCooldownManagerAccessor.getEntries().entrySet()) {
         MixinContract158 mixinContract158 = (MixinContract158)entry.getValue();
         ItemCooldownEntryAccessor itemCooldownEntryAccessor = (ItemCooldownEntryAccessor)entry.getValue();
         if (mixinContract158.getAnimation().getFloatValueField4_2530() > 0.0F) {
            Item item = (Item)Registries.ITEM.get((Identifier)entry.getKey());
            bl = true;
            int j = Math.max(itemCooldownEntryAccessor.getEndTick() - itemCooldownManagerAccessor.getTick(), 0);
            h = Math.max(
               h,
               19.0F
                  + RenderComponent188.renderComponent187Field5.computeValue_2626(item.getName().getString(), 6.5F)
                  + 8.0F
                  + RenderComponent188.renderComponent187Field5.computeValue_2626(String.format("%.1fс", j / 20.0F), 6.5F)
                  + 5.0F
                  + 2.0F
            );
         }
      }

      float r = UtilComponent279.computeValue_3586(this.getDragInfo_3360().getWidth_3005(), h, 0.5F);
      this.getDragInfo_3360().performAction_3006(r);
      if (this.computeValue_3367() > 0.0F) {
         this.performAction_3376(drawEvent, "d", "Cooldowns", r, this.computeValue_3367());
      }

      for (Entry entry1 : itemCooldownManagerAccessor.getEntries().entrySet()) {
         MixinContract158 mixinContract1581 = (MixinContract158)entry1.getValue();
         ItemCooldownEntryAccessor itemCooldownEntryAccessor1 = (ItemCooldownEntryAccessor)entry1.getValue();
         Animation animation = mixinContract1581.getAnimation();
         animation.performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
         float k = animation.getFloatValueField4_2530() * this.computeValue_3367();
         if (k > 0.0F) {
            Item item1 = (Item)Registries.ITEM.get((Identifier)entry1.getKey());
            int l = Math.max(itemCooldownEntryAccessor1.getEndTick() - itemCooldownManagerAccessor.getTick(), 0);
            String string = String.format(Locale.US, "%.1fс", l / 20.0F);
            float m = -8.0F * (1.0F - k);
            float n = -(1.0F - k);
            float o = i + n;
            float p = RenderComponent188.renderComponent187Field5.computeValue_2626(string, 6.5F);
            float q = o + (11.5F - RenderComponent188.renderComponent187Field5.computeValue_2622(6.5F)) / 2.0F - 0.5F;
            this.performAction_3372(drawEvent, f + m, o, r, 11.5F, false, k);
            this.performAction_3371(drawEvent, f + m + 15.0F, o, 11.5F, k);
            drawEvent.getRenderComponent185Field2_0999()
               .performAction_2592(drawEvent.getContext_1000(), item1.getDefaultStack(), f + m + 5.0F, o + 2.0F, 0, k, 0.45F, false);
            RenderComponent188.renderComponent187Field5
               .performAction_2630(drawEvent.getStack_1005(), item1.getName().getString(), f + m + 19.0F, q, 6.5F, RenderComponent183.computeValue_2549(-1, k));
            RenderComponent188.renderComponent187Field5
               .performAction_2630(drawEvent.getStack_1005(), string, f + m + r - 5.0F - p - 1.0F, q, 6.5F, RenderComponent183.computeValue_2549(-1, 0.55F * k));
            i += 13.5F * k;
         }
      }

      this.getDragInfo_3360().performAction_3018(bl ? i - g - 2.0F : this.floatValueField7);
      super.onDrawEvent_3315(drawEvent);
   }

   @Override
   public void performAction_3316(ClientTickEvent clientTickEvent) {
      if (minecraft.player != null) {
         boolean bl = minecraft.currentScreen instanceof ChatScreen;
         ItemCooldownManagerAccessor itemCooldownManagerAccessor = (ItemCooldownManagerAccessor)minecraft.player.getItemCooldownManager();
         int i = itemCooldownManagerAccessor.getTick();

         for (Entry entry : itemCooldownManagerAccessor.getEntries().entrySet()) {
            MixinContract158 mixinContract158 = (MixinContract158)entry.getValue();
            ItemCooldownEntryAccessor itemCooldownEntryAccessor = (ItemCooldownEntryAccessor)entry.getValue();
            boolean bl1 = UtilComponent270.computeValue_3526((Item)Registries.ITEM.get((Identifier)entry.getKey())) != -1;
            mixinContract158.getAnimation().performAction_2528(bl1 && itemCooldownEntryAccessor.getEndTick() - 5 > i);
            if (bl1 && mixinContract158.getAnimation().getFloatValueField4_2530() > 0.0) {
               bl = true;
            }
         }

         this.getAnimationField3_3363().performAction_2528(bl);
         super.performAction_3316(clientTickEvent);
      }
   }
}
