package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.AmbienceEvent;
import aethereal.event.RemovalsEvent;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WeatherRendering;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticlesMode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.Precipitation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeatherRendering.class)
public class WeatherRenderingMixin {
   @Inject(method = "addParticlesAndSound", at = @At("HEAD"), cancellable = true)
   private void onAddParticlesAndSound(ClientWorld clientWorld, Camera camera, int i, ParticlesMode particlesMode, int j, CallbackInfo callbackInfo) {
      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.WEATHER);
      EventBus.post(removalsEvent);
      if (removalsEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }

   @ModifyReturnValue(method = "getPrecipitationAt", at = @At("RETURN"))
   private Precipitation onGetPrecipitationAt(Precipitation precipitation, World world, BlockPos blockPos) {
      AmbienceEvent.NestedType313 nestedType313 = new AmbienceEvent.NestedType313(AmbienceEvent.NestedType313.State417.PRECIPITATION, precipitation);
      EventBus.post(nestedType313);
      return nestedType313.getValue2_0966();
   }
}
