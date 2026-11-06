package platform.inject.mixin;

import aethereal.mixin.MixinContract158;
import java.util.stream.StreamSupport;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemCooldownManager.class)
public abstract class ItemCooldownManagerMixin implements MixinContract158 {
   @Override
   public void setHealCooldown(int i) {
      ((ItemCooldownManager)this).set(Identifier.of("neneclient", "heal"), i);
   }

   @Inject(method = "getGroup", at = @At("HEAD"), cancellable = true)
   private void getGroup(ItemStack itemStack, CallbackInfoReturnable<Identifier> callbackInfoReturnable) {
      if (itemStack.getItem() == Items.POTION) {
         PotionContentsComponent potionContentsComponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
         boolean bl = potionContentsComponent != null
            && StreamSupport.<StatusEffectInstance>stream(potionContentsComponent.getEffects().spliterator(), false)
               .anyMatch(statusEffectInstance -> statusEffectInstance.getEffectType() == StatusEffects.INSTANT_HEALTH);
         if (bl) {
            callbackInfoReturnable.setReturnValue(Identifier.of("neneclient", "heal"));
         }
      }
   }
}
