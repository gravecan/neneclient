package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.EventComponent078;
import aethereal.event.PotionEvent;
import aethereal.event.RemovalsEvent;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
   @Inject(method = "syncWorldEvent", at = @At("HEAD"))
   private void onSyncWorldEvent(Entity entity, int i, BlockPos blockPos, int j, CallbackInfo callbackInfo) {
      if (i == 2002) {
         EventBus.post(new PotionEvent(PotionEvent.State317.PARTICLES, j, blockPos));
      }
   }

   @Inject(method = "handleBlockUpdate", at = @At("HEAD"))
   private void onHandleBlockUpdate(BlockPos blockPos, BlockState blockState, int i, CallbackInfo callbackInfo) {
      ClientWorld clientWorld = (ClientWorld)this;
      BlockState blockState1 = clientWorld.getBlockState(blockPos);
      if (blockState1 != blockState) {
         EventBus.post(new EventComponent078(blockPos.toImmutable(), blockState1, blockState));
      }
   }

   @Inject(method = "addBlockBreakParticles", at = @At("HEAD"), cancellable = true)
   private void onAddBlockBreakParticles(BlockPos blockPos, BlockState blockState, CallbackInfo callbackInfo) {
      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.BREAK_PARTICLES);
      EventBus.post(removalsEvent);
      if (removalsEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "spawnBlockBreakingParticle", at = @At("HEAD"), cancellable = true)
   private void onSpawnBlockBreakingParticle(BlockPos blockPos, Direction direction, CallbackInfo callbackInfo) {
      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.BREAK_PARTICLES);
      EventBus.post(removalsEvent);
      if (removalsEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }
}
