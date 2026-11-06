package aethereal.module.client;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;

@ModuleRegister(name = "Screenshare", description = "Hides the client GUI and command suggestions for screenshots", category = Category.Client)
public final class Screenshare extends Module {
   private static volatile boolean active;

   public static boolean isActive_1470() {
      return active;
   }

   public static void performAction_1468() {
      active = true;
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient != null) {
         minecraftClient.execute(() -> minecraftClient.setScreen(minecraftClient.world == null ? new TitleScreen() : null));
      }
   }

   public static void performAction_1469() {
      active = false;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      performAction_1468();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      performAction_1469();
   }
}
