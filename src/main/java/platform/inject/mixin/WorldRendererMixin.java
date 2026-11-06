package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.RemovalsEvent;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
   @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
   private void onRenderWeather(FrameGraphBuilder frameGraphBuilder, GpuBufferSlice gpuBufferSlice, CallbackInfo callbackInfo) {
      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.WEATHER);
      EventBus.post(removalsEvent);
      if (removalsEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }
}
