package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent086 extends CancellableEvent implements Event {
   private double gamma;

   public void setGamma_1040(double d) {
      this.gamma = d;
   }

   public EventComponent086(double d) {
      this.gamma = d;
   }

   public double getGamma_1041() {
      return this.gamma;
   }
}
