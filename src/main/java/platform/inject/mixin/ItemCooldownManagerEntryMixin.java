package platform.inject.mixin;

import aethereal.mixin.MixinContract158;
import aethereal.render.Animation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/entity/player/ItemCooldownManager$Entry")
public class ItemCooldownManagerEntryMixin implements MixinContract158 {
   @Unique
   private Animation animation;

   @Override
   public Animation getAnimation() {
      if (this.animation == null) {
         this.animation = new Animation();
      }

      return this.animation;
   }

   @Inject(method = "<init>*", at = @At("TAIL"))
   private void onInit(CallbackInfo callbackInfo) {
      this.animation = new Animation();
   }
}
