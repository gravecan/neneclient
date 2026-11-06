package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent094 extends CancellableEvent implements Event {
   public double yaw;
   public double pitch;

   public EventComponent094(double d, double e) {
      this.yaw = d;
      this.pitch = e;
   }

   public double getYaw_1073() {
      return this.yaw;
   }

   public double getPitch_1072() {
      return this.pitch;
   }
}
