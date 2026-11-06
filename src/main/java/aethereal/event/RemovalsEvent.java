package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class RemovalsEvent extends CancellableEvent implements Event {
   private final RemovalsEvent.State319 type;

   public RemovalsEvent.State319 getType_1020() {
      return this.type;
   }

   public RemovalsEvent(RemovalsEvent.State319 state319) {
      this.type = state319;
   }

   public enum State319 {
      HURT_CAM,
      SCOREBOARD,
      BOSS_BAR,
      PORTAL,
      FIRE,
      CLIP,
      BREAK_PARTICLES,
      WATER,
      NAUSEA,
      BLINDNESS,
      PUMPKIN,
      WEATHER,
      GLOW,
      DARKNESS,
      BLACK_HEARTS;
   }
}
