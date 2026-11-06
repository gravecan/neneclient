package aethereal.discord;

import java.util.Optional;

public enum EventType {
   READY("READY", false),
   ERROR("ERROR", false),
   ACTIVITY_JOIN("ACTIVITY_JOIN", true),
   ACTIVITY_SPECTATE("ACTIVITY_SPECTATE", true),
   ACTIVITY_JOIN_REQUEST("ACTIVITY_JOIN_REQUEST", true);

   private final String value;
   private final boolean subscribable;

   public String getValue_0718() {
      return this.value;
   }

   public boolean isSubscribable_0720() {
      return this.subscribable;
   }

   EventType(String string1, boolean bl) {
      this.value = string1;
      this.subscribable = bl;
   }

   public static Optional<EventType> computeValue_0719(String string) {
      for (EventType eventType : values()) {
         if (eventType.value.equals(string)) {
            return Optional.of(eventType);
         }
      }

      return Optional.empty();
   }
}
