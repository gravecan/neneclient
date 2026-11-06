package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.core.MinecraftAccess;
import aethereal.event.EventComponent079;
import aethereal.event.RemovalsEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
   @Shadow
   private Box boundingBox;

   @Inject(method = "getBoundingBox", at = @At("HEAD"), cancellable = true)
   public final void getBoundingBox(CallbackInfoReturnable<Box> callbackInfoReturnable) {
      if (MinecraftAccess.minecraft != null && MinecraftAccess.minecraft.world != null) {
         EventComponent079 eventComponent079 = new EventComponent079(this.boundingBox, (Entity)this);
         EventBus.post(eventComponent079);
         callbackInfoReturnable.setReturnValue(eventComponent079.getBox_1026());
      }
   }

   @Inject(method = "isGlowing", at = @At("RETURN"), cancellable = true)
   private void onIsGlowing(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      if ((Boolean)callbackInfoReturnable.getReturnValue()) {
         RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.GLOW);
         EventBus.post(removalsEvent);
         if (removalsEvent.checkCondition_0596()) {
            callbackInfoReturnable.setReturnValue(false);
         }
      }
   }
}
