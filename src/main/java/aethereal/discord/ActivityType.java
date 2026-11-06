package aethereal.discord;

public enum ActivityType {
   PLAYING(0),
   STREAMING(1),
   LISTENING(2),
   WATCHING(3),
   COMPETING(5);

   private final int value;

   public int getValue_0708() {
      return this.value;
   }

   ActivityType(int j) {
      this.value = j;
   }

   public static ActivityType computeValue_0709(int i) {
      for (ActivityType activityType : values()) {
         if (activityType.value == i) {
            return activityType;
         }
      }

      throw new IllegalArgumentException("Unknown activity type: " + i);
   }
}
