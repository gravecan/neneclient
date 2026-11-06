package platform.inject.mixin;

import aethereal.core.MinecraftAccess;
import aethereal.util.UtilComponent252;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.client.network.CookieStorage;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import platform.inject.accessors.ButtonWidgetAccessor;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
   @Unique
   private boolean reconnect = false;
   @Unique
   private boolean disconnect = false;

   private GameMenuScreenMixin(Text text) {
      super(text);
   }

   @Inject(method = "init", at = @At("TAIL"))
   private void init(CallbackInfo callbackInfo) {
      ServerInfo serverInfo = MinecraftAccess.minecraft.getCurrentServerEntry();
      this.children()
         .stream()
         .filter(element -> element instanceof ButtonWidget)
         .map(element -> (ButtonWidget)element)
         .filter(
            buttonWidget -> {
               TranslatableTextContent translatableTextContent = (TranslatableTextContent)buttonWidget.getMessage().getContent();
               return translatableTextContent instanceof TranslatableTextContent translatableTextContent1
                  && translatableTextContent1.getKey().equals("menu.disconnect");
            }
         )
         .findFirst()
         .ifPresent(buttonWidget -> {
            ButtonWidgetAccessor buttonWidgetAccessor = (ButtonWidgetAccessor)buttonWidget;
            PressAction pressAction = buttonWidgetAccessor.getOnPress();
            buttonWidgetAccessor.setOnPress(buttonWidget2 -> {
               if (UtilComponent252.checkCondition_3401() && !this.disconnect) {
                  buttonWidget2.setMessage(buttonWidget.getMessage().copy().setStyle(Style.EMPTY.withColor(Formatting.RED)));
                  this.disconnect = true;
               } else {
                  pressAction.onPress(buttonWidget2);
               }
            });
         });
      if (serverInfo != null) {
         int i = this.children()
            .stream()
            .filter(element -> element instanceof ButtonWidget)
            .map(element -> ((ButtonWidget)element).getY())
            .max((integer, integer1) -> integer.compareTo(integer1))
            .orElse(this.height / 2);
         this.addDrawableChild(
            ButtonWidget.builder(
                  Text.literal("Reconnect"),
                  buttonWidget -> {
                     if (UtilComponent252.checkCondition_3401() && !this.reconnect) {
                        buttonWidget.setMessage(Text.literal("Reconnect").setStyle(Style.EMPTY.withColor(Formatting.RED)));
                        this.reconnect = true;
                     } else {
                        try {
                           MinecraftAccess.minecraft.world.disconnect(Text.empty());
                           ConnectScreen.connect(
                              new TitleScreen(), MinecraftAccess.minecraft, ServerAddress.parse(serverInfo.address), serverInfo, false, (CookieStorage)null
                           );
                        } catch (Exception exception) {
                        }
                     }
                  }
               )
               .dimensions(this.width / 2 - 100, i + 24, 200, 20)
               .build()
         );
      }
   }
}
