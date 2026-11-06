package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.core.MinecraftAccess;
import aethereal.event.EventComponent093;
import aethereal.module.client.Screenshare;
import aethereal.ui.screen.MainMenuScreen;
import aethereal.ui.screen.ScreenComponent213;
import aethereal.ui.screen.ScreenComponent224;
import aethereal.ui.screen.ScreenComponent225;
import net.minecraft.client.Keyboard;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.input.KeyInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
   @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
   private void onKey(long l, int i, KeyInput keyInput, CallbackInfo callbackInfo) {
      if (MinecraftAccess.minecraft.currentScreen == null
         || MinecraftAccess.minecraft.currentScreen instanceof ScreenComponent225
         || MinecraftAccess.minecraft.currentScreen instanceof ScreenComponent213
         || MinecraftAccess.minecraft.currentScreen instanceof ScreenComponent224
         || MinecraftAccess.minecraft.currentScreen instanceof HandledScreen
         || MinecraftAccess.minecraft.currentScreen instanceof MainMenuScreen
         || Screenshare.isActive_1470() && MinecraftAccess.minecraft.currentScreen instanceof TitleScreen) {
         EventComponent093 eventComponent093 = new EventComponent093(keyInput.key(), keyInput.scancode(), i, keyInput.comp_4797());
         EventBus.post(eventComponent093);
         if (eventComponent093.checkCondition_0596()) {
            callbackInfo.cancel();
         }
      }
   }
}
