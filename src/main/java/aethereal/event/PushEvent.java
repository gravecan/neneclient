package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class PushEvent extends CancellableEvent implements Event {
   private final PushEvent.State318 type;

   public PushEvent.State318 getType_1019() {
      return this.type;
   }

   public PushEvent(PushEvent.State318 state318) {
      this.type = state318;
   }

   public enum State318 {
      BLOCKS,
      FLUIDS,
      ENTITIES,
      WORLD_BORDER,
      FISHING_HOOK;
   }
}
