package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.AmbienceEvent;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.world.ClientWorld.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Properties.class)
public class ClientWorldPropertiesMixin {
   @ModifyReturnValue(method = "getTimeOfDay", at = @At("RETURN"))
   private long getTimeOfDay(long l) {
      AmbienceEvent.NestedType312 nestedType312 = new AmbienceEvent.NestedType312(l);
      EventBus.post(nestedType312);
      return nestedType312.getTime_0962();
   }
}
