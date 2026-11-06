package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.AmbienceEvent;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(World.class)
public class WorldMixin {
   @ModifyReturnValue(method = "getThunderGradient", at = @At("RETURN"))
   private float onGetThunderGradient(float f) {
      AmbienceEvent.NestedType313 nestedType313 = new AmbienceEvent.NestedType313(AmbienceEvent.NestedType313.State417.THUNDER_GRADIENT, f);
      EventBus.post(nestedType313);
      return nestedType313.getValue_0964();
   }
}
