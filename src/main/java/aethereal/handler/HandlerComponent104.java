package aethereal.handler;

import aethereal.core.EventBus;

public class HandlerComponent104 {
   public HandlerComponent104() {
      if (!this.getClass().isAnnotationPresent(HandlerAnnotation107.class)) {
         throw new IllegalStateException("Handler " + this.getClass().getSimpleName() + "must have @Handler!");
      }

      EventBus.register(this);
   }
}
