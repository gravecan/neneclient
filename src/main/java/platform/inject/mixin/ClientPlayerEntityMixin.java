package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.core.MinecraftAccess;
import aethereal.event.EventComponent070;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent085;
import aethereal.event.EventComponent091;
import aethereal.event.EventComponent095;
import aethereal.event.EventComponent098;
import aethereal.event.PushEvent;
import aethereal.util.UtilComponent277;
import aethereal.util.UtilComponent280;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.function.Predicate;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import platform.inject.accessors.InputAccessor;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
   @Inject(method = "tick", at = @At("HEAD"))
   private void tick(CallbackInfo callbackInfo) {
      EventBus.post(new EventComponent074());
   }

   @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;tick()V"))
   private void tickMovement(Input input) {
      input.tick();
      PlayerInput playerInput = input.playerInput;
      Vec2f vec2f = ((InputAccessor)input).getMovementVector();
      EventComponent091 eventComponent091 = new EventComponent091(vec2f.y, vec2f.x, playerInput.jump(), playerInput.sneak());
      EventBus.post(eventComponent091);
      UtilComponent280.onEventComponent091_3588(eventComponent091);
      ((InputAccessor)input).setMovementVector(new Vec2f(eventComponent091.getStrafe_1063(), eventComponent091.getForward_1062()));
      input.playerInput = new PlayerInput(
         eventComponent091.getForward_1062() > 0.0F,
         eventComponent091.getForward_1062() < 0.0F,
         eventComponent091.getStrafe_1063() > 0.0F,
         eventComponent091.getStrafe_1063() < 0.0F,
         eventComponent091.isJump_1059(),
         eventComponent091.isSneak_1064(),
         playerInput.sprint()
      );
   }

   @Inject(method = "sendMovementPackets", at = @At("HEAD"))
   private void onSendMovementPackets(CallbackInfo callbackInfo) {
      ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity)this;
      EventBus.post(
         new EventComponent095(
            clientPlayerEntity.getX(),
            clientPlayerEntity.getY(),
            clientPlayerEntity.getZ(),
            clientPlayerEntity.getYaw(),
            clientPlayerEntity.getPitch(),
            clientPlayerEntity.isOnGround(),
            clientPlayerEntity.isSneaking(),
            clientPlayerEntity.isSprinting()
         )
      );
   }

   @Redirect(method = "tickMovementInput", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
   private float pitchAi(ClientPlayerEntity clientPlayerEntity) {
      return UtilComponent277.getFloatValueField3_3571();
   }

   @Redirect(method = "tickMovementInput", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
   private float yawAi(ClientPlayerEntity clientPlayerEntity) {
      return UtilComponent277.getFloatValueField2_3570();
   }

   @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
   private void onDropSelectedItem(boolean bl, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      EventComponent085 eventComponent085 = new EventComponent085(MinecraftAccess.minecraft.player.getInventory().getSelectedSlot());
      EventBus.post(eventComponent085);
      if (eventComponent085.checkCondition_0596()) {
         callbackInfoReturnable.cancel();
      }
   }

   @Redirect(method = "applyMovementSpeedFactors", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
   private boolean onUsingItemSlowdown(ClientPlayerEntity clientPlayerEntity) {
      if (!clientPlayerEntity.isUsingItem()) {
         return clientPlayerEntity.isUsingItem() && clientPlayerEntity.getVehicle() == null;
      }

      EventComponent070 eventComponent070 = new EventComponent070();
      EventBus.post(eventComponent070);
      return clientPlayerEntity.isUsingItem() && clientPlayerEntity.getVehicle() == null && !eventComponent070.checkCondition_0596();
   }

   @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
   public void removePushOutFromBlocks(double d, double e, CallbackInfo callbackInfo) {
      PushEvent pushEvent = new PushEvent(PushEvent.State318.BLOCKS);
      EventBus.post(pushEvent);
      if (pushEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }

   @WrapOperation(
      method = "getCrosshairTarget",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/projectile/ProjectileUtil;raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;"
      )
   )
   private static EntityHitResult a_rayTrace(
      Entity entity, Vec3d vec3d, Vec3d vec3d1, Box box, Predicate<Entity> predicate, double d, Operation<EntityHitResult> operation
   ) {
      EventComponent098 eventComponent098 = new EventComponent098();
      EventBus.post(eventComponent098);
      return eventComponent098.checkCondition_0596() ? null : (EntityHitResult)operation.call(new Object[]{entity, vec3d, vec3d1, box, predicate, d});
   }
}
