package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent085 extends CancellableEvent implements Event {
   private int slot;

   public EventComponent085(int i) {
      this.slot = i;
   }

   public int getSlot_1039() {
      return this.slot;
   }
}
