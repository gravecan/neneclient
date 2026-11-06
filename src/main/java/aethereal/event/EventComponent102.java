package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent102 extends CancellableEvent implements Event {
   private final double horizontal;
   private final double vertical;

   public EventComponent102(double d, double e) {
      this.horizontal = d;
      this.vertical = e;
   }

   public double getHorizontal_1102() {
      return this.horizontal;
   }

   public double getVertical_1101() {
      return this.vertical;
   }
}
