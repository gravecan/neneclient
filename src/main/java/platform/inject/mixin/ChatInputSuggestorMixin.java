package platform.inject.mixin;

import aethereal.command.CommandManager;
import aethereal.core.NeneClient;
import aethereal.module.client.Screenshare;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.screen.ChatInputSuggestor.SuggestionWindow;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ChatInputSuggestor.class)
public abstract class ChatInputSuggestorMixin {
   @Shadow
   @Final
   TextFieldWidget textField;
   @Shadow
   boolean completingSuggestions;
   @Shadow
   private ParseResults<CommandSource> parse;
   @Shadow
   private CompletableFuture<Suggestions> pendingSuggestions;
   @Shadow
   private SuggestionWindow window;

   @Shadow
   protected abstract void showCommandSuggestions();

   @WrapMethod(method = "refresh")
   private void refresh(Operation<Void> operation) {
      try {
         operation.call(new Object[0]);
      } catch (Throwable throwable) {
      }
   }

   @Inject(
      method = "refresh",
      at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;canRead()Z", remap = false),
      cancellable = true,
      locals = LocalCapture.CAPTURE_FAILHARD
   )
   public void onRefresh(CallbackInfo callbackInfo, @Local StringReader stringReader) {
      CommandManager commandManager = NeneClient.getInstance().getProcessors().getCommandManagerField13_0666();
      String string = commandManager.computeValue_0220();
      if (stringReader.canRead(string.length()) && stringReader.getString().startsWith(string, stringReader.getCursor())) {
         if (Screenshare.isActive_1470()) {
            callbackInfo.cancel();
            return;
         }

         String string1 = this.textField.getText();
         int i = this.textField.getCursor();
         StringReader stringReader1 = new StringReader(string1);
         stringReader1.setCursor(string.length());
         this.parse = commandManager.getCommandDispatcherField9_0219().parse(stringReader1, commandManager.getClientCommandSourceField10_0221());
         if (i >= string.length() && (this.window == null || !this.completingSuggestions)) {
            this.pendingSuggestions = commandManager.getCommandDispatcherField9_0219().getCompletionSuggestions(this.parse, i);
            this.pendingSuggestions.thenRun(() -> {
               if (this.pendingSuggestions.isDone()) {
                  this.showCommandSuggestions();
               }
            });
         }

         callbackInfo.cancel();
      }
   }
}
