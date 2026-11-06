package aethereal.module.visuals;

import aethereal.core.Category;
import aethereal.core.EventBus;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.ui.widget.WidgetComponent246;

@ModuleRegister(name = "Staff List", description = "Automatically shows staff detected from tab-list ranks", category = Category.Visuals)
public final class StaffList extends Module {
   private final WidgetComponent246 widget = new WidgetComponent246();

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
