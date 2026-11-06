package aethereal.module.visuals;

import aethereal.core.Category;
import aethereal.core.EventBus;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.ui.widget.WidgetComponent247;

@ModuleRegister(name = "Target HUD", description = "Shows the target's head, health and equipment", category = Category.Visuals)
public final class TargetHUD extends Module {
   private final WidgetComponent247 widget = new WidgetComponent247();

   @Override
   public void onEnable() {
      super.onEnable();
      EventBus.register(this.widget);
   }

   @Override
   public void onDisable() {
      EventBus.unregister(this.widget);
      super.onDisable();
   }
}
