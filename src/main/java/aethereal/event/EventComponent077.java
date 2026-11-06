package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.entity.Entity;

public class EventComponent077 extends CancellableEvent implements Event {
   private final Entity entity;

   public Entity getEntity_1021() {
      return this.entity;
   }

   public EventComponent077(Entity entity) {
      this.entity = entityx;
   }
}
