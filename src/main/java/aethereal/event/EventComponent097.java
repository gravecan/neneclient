package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent097 extends CancellableEvent implements Event {
   private float ratio;

   public EventComponent097(float f) {
      this.ratio = f;
   }

   public void setRatio_1094(float f) {
      this.ratio = f;
   }

   public float getRatio_1093() {
      return this.ratio;
   }
}
