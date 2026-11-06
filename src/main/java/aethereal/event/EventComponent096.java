package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent096 extends CancellableEvent implements Event {
   private boolean inPortal;

   public void setInPortal_1092(boolean bl) {
      this.inPortal = bl;
   }

   public EventComponent096(boolean bl) {
      this.inPortal = bl;
   }

   public boolean isInPortal_1091() {
      return this.inPortal;
   }
}
