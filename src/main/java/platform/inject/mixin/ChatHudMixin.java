package platform.inject.mixin;

import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.List;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.gui.hud.ChatHudLine.Visible;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {
   @Shadow
   @Final
   private List<ChatHudLine> messages;

   @Shadow
   private void refresh() {
   }

   @ModifyArgs(
      method = "render(Lnet/minecraft/client/gui/DrawContext;IIIZ)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"),
      require = 0
   )
   private void onRenderLineBackground(Args args, @Local Visible visible) {
      if (NeneClient.getInstance().getProcessors().getModules().getAs_0426().isEnabled()
         && NeneClient.getInstance().getProcessors().getModules().getAs_0426().getBooleanSettingField1_1860().getCurrentValue()
         && ((Integer)args.get(4) & 16777215) == 0) {
         args.set(2, (Integer)args.get(0) + MinecraftAccess.minecraft.textRenderer.getWidth(visible.content()) + 5);
      }
   }

   @Inject(method = "addMessage", at = @At("TAIL"))
   private void onAddMessage(Text text, MessageSignatureData messageSignatureData, MessageIndicator messageIndicator, CallbackInfo callbackInfo) {
      if (this.messages.size() >= 2) {
         ChatHudLine chatHudLine = this.messages.get(0);
         ChatHudLine chatHudLine1 = this.messages.get(1);
         String string = chatHudLine.content().getString();
         String string1 = chatHudLine1.content().getString();
         int i = string1.lastIndexOf(" [x");
         if (i != -1 && string1.endsWith("]")) {
            String string2 = string1.substring(0, i);
            if (string2.equals(string)) {
               this.updateMessage(chatHudLine, text, Integer.parseInt(string1.substring(i + 3, string1.length() - 1)) + 1);
               this.messages.remove(1);
               this.refresh();
            }
         } else {
            if (string1.equals(string)) {
               this.updateMessage(chatHudLine, text, 2);
               this.messages.remove(1);
               this.refresh();
            }
         }
      }
   }

   @Unique
   private void updateMessage(ChatHudLine chatHudLine, Text text, int i) {
      ChatHudLine chatHudLine1 = new ChatHudLine(
         chatHudLine.creationTick(),
         Text.empty().append(text.copy()).append(Text.literal(" [x" + i + "]").formatted(Formatting.GRAY)),
         chatHudLine.signature(),
         chatHudLine.indicator()
      );
      this.messages.set(0, chatHudLine1);
   }
}
