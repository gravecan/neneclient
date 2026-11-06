package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.PushEvent;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldBorder.class)
public class WorldBorderMixin {
   @Inject(method = "asVoxelShape", at = @At("HEAD"), cancellable = true)
   private void asVoxelShape(CallbackInfoReturnable<VoxelShape> callbackInfoReturnable) {
      PushEvent pushEvent = new PushEvent(PushEvent.State318.WORLD_BORDER);
      EventBus.post(pushEvent);
      if (pushEvent.checkCondition_0596()) {
         callbackInfoReturnable.setReturnValue(VoxelShapes.empty());
      }
   }
}
