package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.EventComponent075;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin {
   @Inject(method = "getTooltipFromItem", at = @At("RETURN"))
   private static void getTooltipFromItem(MinecraftClient minecraftClient, ItemStack itemStack, CallbackInfoReturnable<List<Text>> callbackInfoReturnable) {
      EventBus.post(new EventComponent075(itemStack, (List<Text>)callbackInfoReturnable.getReturnValue()));
   }
}
