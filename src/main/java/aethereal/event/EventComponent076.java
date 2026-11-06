package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent076 extends CancellableEvent implements Event {
   private final boolean willLand;

   public EventComponent076(boolean bl) {
      this.willLand = bl;
   }

   public boolean isWillLand_1008() {
      return this.willLand;
   }
}
