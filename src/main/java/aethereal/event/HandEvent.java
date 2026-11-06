package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class HandEvent extends CancellableEvent implements Event {
   private final HandEvent.State316 phase;

   public HandEvent(HandEvent.State316 state316) {
      this.phase = state316;
   }

   public HandEvent.State316 getPhase_1011() {
      return this.phase;
   }

   public boolean checkCondition_1009() {
      return this.phase == HandEvent.State316.PRE;
   }

   public boolean checkCondition_1010() {
      return this.phase == HandEvent.State316.POST;
   }

   public enum State316 {
      PRE,
      POST;
   }
}
