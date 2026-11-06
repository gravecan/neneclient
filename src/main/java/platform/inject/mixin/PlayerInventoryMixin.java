package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.EventComponent072;
import aethereal.event.EventComponent090;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
   @Inject(method = "setSelectedSlot", at = @At("HEAD"), cancellable = true)
   private void onSetSelectedSlot(int i, CallbackInfo callbackInfo) {
      EventComponent090 eventComponent090 = new EventComponent090(i);
      EventBus.post(eventComponent090);
      if (eventComponent090.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "setStack", at = @At("HEAD"), cancellable = true)
   private void onSetStack(int i, ItemStack itemStack, CallbackInfo callbackInfo) {
      EventComponent072 eventComponent072 = new EventComponent072(i, itemStack);
      EventBus.post(eventComponent072);
      if (eventComponent072.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }
}
