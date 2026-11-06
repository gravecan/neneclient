package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.util.math.BlockPos;

public class PotionEvent extends CancellableEvent implements Event {
   private final PotionEvent.State317 type;
   private final int data;
   private final BlockPos pos;

   public PotionEvent(PotionEvent.State317 state317, int i, BlockPos blockPos) {
      this.type = state317;
      this.data = i;
      this.pos = blockPos;
   }

   public PotionEvent.State317 getType_1017() {
      return this.type;
   }

   public int getData_1018() {
      return this.data;
   }

   public BlockPos getPos_1016() {
      return this.pos;
   }

   public enum State317 {
      PARTICLES;
   }
}
