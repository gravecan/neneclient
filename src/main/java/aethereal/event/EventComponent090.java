package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent090 extends CancellableEvent implements Event {
   private final int slot;

   public EventComponent090(int i) {
      this.slot = i;
   }

   public int getSlot_1058() {
      return this.slot;
   }
}
