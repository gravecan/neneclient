package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.EventComponent076;
import aethereal.event.EventComponent081;
import aethereal.event.EventComponent092;
import aethereal.event.PushEvent;
import aethereal.mixin.MixinContract160;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import platform.inject.accessors.LivingEntityGravityInvoker;
import platform.inject.invokers.EntityMovementInvoker;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements MixinContract160 {
   @Inject(method = "consumeItem", at = @At("HEAD"))
   private void consumeItem(CallbackInfo callbackInfo) {
      LivingEntity livingEntity = (LivingEntity)this;
      if (livingEntity instanceof ClientPlayerEntity && livingEntity.isUsingItem()) {
         EventBus.post(new EventComponent081(livingEntity.getActiveItem().copy()));
      }
   }

   @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
   public void jump(CallbackInfo callbackInfo) {
      LivingEntity livingEntity = (LivingEntity)this;
      if (livingEntity instanceof ClientPlayerEntity) {
         EventComponent092 eventComponent092 = new EventComponent092(livingEntity);
         EventBus.post(eventComponent092);
         if (eventComponent092.checkCondition_0596()) {
            callbackInfo.cancel();
         }
      }
   }

   @Inject(method = "isPushable", at = @At("HEAD"), cancellable = true)
   private void removePushFromEntity(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      LivingEntity livingEntity = (LivingEntity)this;
      if (livingEntity instanceof ClientPlayerEntity) {
         PushEvent pushEvent = new PushEvent(PushEvent.State318.ENTITIES);
         EventBus.post(pushEvent);
         if (pushEvent.checkCondition_0596()) {
            callbackInfoReturnable.setReturnValue(false);
         }
      }
   }

   @Inject(method = "travelMidAir", at = @At("TAIL"))
   private void travelMidAir(Vec3d vec3d2, CallbackInfo callbackInfo) {
      EntityMovementInvoker entityMovementInvoker = (EntityMovementInvoker)this;
      if (entityMovementInvoker instanceof ClientPlayerEntity) {
         double d = ((LivingEntityGravityInvoker)this).getGravityInvoker();
         double e = (((Entity)this).getVelocity().y - d) * 0.98;
         Vec3d vec3d = new Vec3d(0.0, e, 0.0);
         Vec3d vec3d1 = entityMovementInvoker.getAdjustMovementForCollisions(vec3d);
         boolean bl = e < 0.0 && vec3d1.y != e;
         EventBus.post(new EventComponent076(bl));
      }
   }
}
