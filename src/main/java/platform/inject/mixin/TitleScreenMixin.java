package platform.inject.mixin;

import aethereal.module.client.Screenshare;
import aethereal.ui.screen.MainMenuScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
   @Inject(method = "init", at = @At("HEAD"), cancellable = true)
   private void init(CallbackInfo callback) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (Screenshare.isActive_1470() || client.currentScreen instanceof MainMenuScreen) {
         return;
      }

      callback.cancel();
      client.setScreen(new MainMenuScreen());
   }
}
