package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.entity.LivingEntity;

public class EventComponent092 extends CancellableEvent implements Event {
   private final LivingEntity livingEntity;

   public EventComponent092(LivingEntity livingEntity) {
      this.livingEntity = livingEntityx;
   }

   public LivingEntity getLivingEntity_1067() {
      return this.livingEntity;
   }
}
