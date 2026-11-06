package aethereal.module.client;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.ui.screen.ScreenComponent215;

@ModuleRegister(name = "Dropdown GUI", description = "Opens the dropdown module GUI", category = Category.Client)
public final class DropdownGUI extends Module {
   @Override
   public void onEnable() {
      super.onEnable();
      ScreenComponent215.performAction_3096(() -> {
         if (this.isEnabled()) {
            this.toggle();
         }
      });
   }

   @Override
   public void onDisable() {
      super.onDisable();
      ScreenComponent215.performAction_3089();
   }
}
