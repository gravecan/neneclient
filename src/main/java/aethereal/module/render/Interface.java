package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventBus;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.ui.widget.WidgetComponent243;

@ModuleRegister(name = "Interface", description = "Displays the selected interface elements on screen", category = Category.Client)
public class Interface extends Module {
   private final WidgetComponent243 moduleList = new WidgetComponent243();

   public Interface() {
      this.setEnabled(true);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      EventBus.register(this.moduleList);
   }

   @Override
   public void onDisable() {
      EventBus.unregister(this.moduleList);
      super.onDisable();
   }
}
