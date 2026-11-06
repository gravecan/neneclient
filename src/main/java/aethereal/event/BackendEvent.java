package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.CoreComponent036;
import aethereal.core.Event;

public class BackendEvent extends CancellableEvent implements Event {
   private final CoreComponent036 packet;
   private final BackendEvent.Phase type;

   public CoreComponent036 getPacket_0974() {
      return this.packet;
   }

   public BackendEvent.Phase getType_0975() {
      return this.type;
   }

   public BackendEvent(CoreComponent036 coreComponent036, BackendEvent.Phase phase) {
      this.packet = coreComponent036;
      this.type = phase;
   }

   public BackendEvent(BackendEvent.Phase phase) {
      this.type = phase;
      this.packet = null;
   }

   public boolean checkCondition_0972() {
      return this.type == BackendEvent.Phase.RECEIVE;
   }

   public boolean checkCondition_0973() {
      return this.type == BackendEvent.Phase.CLOSE;
   }

   public enum Phase {
      RECEIVE,
      CLOSE;
   }
}
