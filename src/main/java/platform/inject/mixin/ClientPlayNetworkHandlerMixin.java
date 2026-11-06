package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent072;
import aethereal.event.EventComponent082;
import aethereal.mixin.MixinContract162;
import aethereal.render.Animation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.network.packet.s2c.play.CooldownUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements MinecraftAccess {
   @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
   private void sendChatMessage(String string, CallbackInfo callbackInfo) {
      if (!NeneClient.getInstance().getProcessors().getModules().getNoCommandsField2_0431().isEnabled()) {
         NeneClient.getInstance().getProcessors().getCommandManagerField13_0666().performAction_0223(string, callbackInfo);
      }
   }

   @Redirect(method = "onGameJoin", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/toast/ToastManager;add(Lnet/minecraft/client/toast/Toast;)V"))
   private void onGameJoin(ToastManager toastManager, Toast toast) {
   }

   @Redirect(
      method = "onRecipeBookAdd",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/toast/RecipeToast;show(Lnet/minecraft/client/toast/ToastManager;Lnet/minecraft/recipe/display/RecipeDisplay;)V"
      )
   )
   private void onRecipeBookAdd(ToastManager toastManager, RecipeDisplay recipeDisplay) {
   }

   @Inject(method = "onCooldownUpdate", at = @At("HEAD"), cancellable = true)
   private void onCooldownUpdate(CooldownUpdateS2CPacket cooldownUpdateS2CPacket, CallbackInfo callbackInfo) {
      EventComponent082 eventComponent082 = new EventComponent082(
         (Item)Registries.ITEM.get(cooldownUpdateS2CPacket.cooldownGroup()), cooldownUpdateS2CPacket.cooldown()
      );
      EventBus.post(eventComponent082);
      if (eventComponent082.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "onScreenHandlerSlotUpdate", at = @At("HEAD"), cancellable = true)
   private void onScreenHandlerSlotUpdate(ScreenHandlerSlotUpdateS2CPacket screenHandlerSlotUpdateS2CPacket, CallbackInfo callbackInfo) {
      EventComponent072 eventComponent072 = new EventComponent072(screenHandlerSlotUpdateS2CPacket.getSlot(), screenHandlerSlotUpdateS2CPacket.getStack());
      EventBus.post(eventComponent072);
      if (eventComponent072.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }

   @ModifyArg(
      method = "onEntityStatusEffect",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;setStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)V"
      ),
      index = 0
   )
   private StatusEffectInstance onEntityStatusEffect(
      StatusEffectInstance statusEffectInstance, @Local(argsOnly = true) EntityStatusEffectS2CPacket entityStatusEffectS2CPacket
   ) {
      if ((minecraft.world == null ? null : minecraft.world.getEntityById(entityStatusEffectS2CPacket.getEntityId())) instanceof LivingEntity livingEntity) {
         MixinContract162 mixinContract162 = (MixinContract162)livingEntity.getStatusEffect(statusEffectInstance.getEffectType());
         if (mixinContract162 != null) {
            ((MixinContract162)statusEffectInstance).setInitialDuration(mixinContract162.getInitialDuration());
            Animation animation = mixinContract162.getAnimation();
            Animation animation1 = ((MixinContract162)statusEffectInstance).getAnimation();
            animation1.setFloatValueField1_2526(animation.getFloatValueField1_2527());
            animation1.setFloatValueField2_2536(animation.getFloatValueField2_2529());
         }
      }

      return statusEffectInstance;
   }
}
