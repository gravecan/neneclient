package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

public class EventComponent079 extends CancellableEvent implements Event {
   public Box box;
   public Entity entity;

   public void setBox_1028(Box box) {
      this.box = boxx;
   }

   public void setEntity_1027(Entity entity) {
      this.entity = entityx;
   }

   public EventComponent079(Box box, Entity entity) {
      this.box = boxx;
      this.entity = entityx;
   }

   public Box getBox_1026() {
      return this.box;
   }

   public Entity getEntity_1025() {
      return this.entity;
   }
}
