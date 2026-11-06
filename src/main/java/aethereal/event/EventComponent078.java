package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class EventComponent078 extends CancellableEvent implements Event {
   private final BlockPos pos;
   private final BlockState oldState;
   private final BlockState state;

   public EventComponent078(BlockPos blockPos, BlockState blockState, BlockState blockState1) {
      this.pos = blockPos;
      this.oldState = blockState;
      this.state = blockState1;
   }

   public BlockPos getPos_1023() {
      return this.pos;
   }

   public BlockState getOldState_1024() {
      return this.oldState;
   }

   public BlockState getState_1022() {
      return this.state;
   }
}
