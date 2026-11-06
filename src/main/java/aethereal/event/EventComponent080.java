package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.util.math.Vec3d;

public class EventComponent080 extends CancellableEvent implements Event {
   private Vec3d position;

   public void setPosition_1030(Vec3d vec3d) {
      this.position = vec3d;
   }

   public Vec3d getPosition_1029() {
      return this.position;
   }

   public EventComponent080(Vec3d vec3d) {
      this.position = vec3d;
   }
}
