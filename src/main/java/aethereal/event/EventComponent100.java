package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent100 extends CancellableEvent implements Event {
   public float yaw;
   public float pitch;

   public EventComponent100(float f, float g) {
      this.yaw = f;
      this.pitch = g;
   }

   public void setYaw_1098(float f) {
      this.yaw = f;
   }

   public void setPitch_1095(float f) {
      this.pitch = f;
   }

   public float getYaw_1096() {
      return this.yaw;
   }

   public float getPitch_1097() {
      return this.pitch;
   }
}
