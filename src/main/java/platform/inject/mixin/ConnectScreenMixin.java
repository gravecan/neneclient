package platform.inject.mixin;

import aethereal.core.NeneClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.CookieStorage;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public class ConnectScreenMixin {
   @Inject(method = "connect", at = @At("HEAD"))
   private static void connect(
      Screen screen,
      MinecraftClient minecraftClient,
      ServerAddress serverAddress,
      ServerInfo serverInfo,
      boolean bl,
      @Nullable CookieStorage cookieStorage,
      CallbackInfo callbackInfo
   ) {
      NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getHandlerComponent111Field5_1147().setServerInfoField1_1198(serverInfo);
   }
}
