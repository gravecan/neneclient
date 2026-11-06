package aethereal.module.client;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.ui.screen.ScreenComponent218;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

@ModuleRegister(name = "Panel GUI", description = "Opens the classic panel module GUI", category = Category.Client)
public final class PanelGUI extends Module {
   private static PanelGUI.NestedType337 active;

   @Override
   public void onEnable() {
      super.onEnable();
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient != null) {
         if (active != null) {
            active.close();
         }

         active = new PanelGUI.NestedType337(minecraftClient.currentScreen, this);
         minecraftClient.setScreen(active);
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      PanelGUI.NestedType337 nestedType337 = active;
      active = null;
      if (nestedType337 != null) {
         nestedType337.close();
      }
   }

   private static final class NestedType337 extends ScreenComponent218 {
      private final PanelGUI module;

      private NestedType337(Screen screen, PanelGUI panelGUI) {
         super(Text.literal("GUI"), screen);
         this.module = panelGUI;
      }

      @Override
      public void close() {
         super.close();
         if (PanelGUI.active == this) {
            PanelGUI.active = null;
            if (this.module.isEnabled()) {
               this.module.setEnabled(false);
            }
         }
      }
   }
}
