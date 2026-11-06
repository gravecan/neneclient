package platform.inject.mixin;

import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.CookieStorage;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public abstract class DisconnectedScreenMixin extends Screen {
   @Unique
   private ButtonWidget buttonWidget;

   private DisconnectedScreenMixin(Text text) {
      super(text);
   }

   @Inject(
      method = "init",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/DisconnectedScreen;refreshWidgetPositions()V", shift = Shift.BEFORE)
   )
   private void init(CallbackInfo callbackInfo) {
      ServerInfo serverInfo = NeneClient.getInstance()
         .getProcessors()
         .getHandlerComponent106Field14_0673()
         .getHandlerComponent111Field5_1147()
         .getServerInfoField1_1196();
      if (serverInfo != null) {
         this.buttonWidget = (ButtonWidget)this.addDrawableChild(
            ButtonWidget.builder(
                  Text.literal("Reconnect"),
                  buttonWidget -> ConnectScreen.connect(
                     new MultiplayerScreen((Screen)null),
                     MinecraftAccess.minecraft,
                     ServerAddress.parse(serverInfo.address),
                     serverInfo,
                     false,
                     (CookieStorage)null
                  )
               )
               .dimensions(0, 0, 200, 20)
               .build()
         );
      }
   }

   @Inject(method = "refreshWidgetPositions", at = @At("TAIL"))
   private void refreshWidgetPositions(CallbackInfo callbackInfo) {
      if (this.buttonWidget != null) {
         int i = this.width / 2 - 100;
         int j = this.children()
            .stream()
            .filter(element -> element instanceof ButtonWidget && element != this.buttonWidget)
            .map(element -> ((ButtonWidget)element).getY())
            .max((integer, integer1) -> integer.compareTo(integer1))
            .orElse(this.height / 2);
         this.buttonWidget.setPosition(i, j + 24);
      }
   }
}
