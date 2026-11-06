package aethereal.discord;

import java.util.List;

public class DiscordComponent055 {
   private final long clientId;
   private final List<DiscordBuild> preferredBuilds;
   private final boolean reconnect;
   private final int maxReconnectAttempts;
   private final long reconnectBaseDelayMs;
   private final long reconnectMaxDelayMs;
   private final long commandTimeoutMs;
   private final int maxCommandsPerSecond;

   static List<DiscordBuild> computeValue_0845() {
      return List.of(DiscordBuild.STABLE, DiscordBuild.PTB, DiscordBuild.CANARY);
   }

   static boolean checkCondition_0855() {
      return true;
   }

   static int computeValue_0853() {
      return 0;
   }

   static long computeValue_0841() {
      return 1000L;
   }

   static long computeValue_0847() {
      return 10000L;
   }

   static long computeValue_0852() {
      return 10000L;
   }

   static int computeValue_0854() {
      return 0;
   }

   DiscordComponent055(long l, List<DiscordBuild> list, boolean bl, int i, long m, long n, long o, int j) {
      this.clientId = l;
      this.preferredBuilds = list;
      this.reconnect = bl;
      this.maxReconnectAttempts = i;
      this.reconnectBaseDelayMs = m;
      this.reconnectMaxDelayMs = n;
      this.commandTimeoutMs = o;
      this.maxCommandsPerSecond = j;
   }

   public static DiscordComponent055.NestedType302 computeValue_0849() {
      return new DiscordComponent055.NestedType302();
   }

   public long getClientId_0846() {
      return this.clientId;
   }

   public List<DiscordBuild> getPreferredBuilds_0842() {
      return this.preferredBuilds;
   }

   public boolean isReconnect_0843() {
      return this.reconnect;
   }

   public int getMaxReconnectAttempts_0856() {
      return this.maxReconnectAttempts;
   }

   public long getReconnectBaseDelayMs_0850() {
      return this.reconnectBaseDelayMs;
   }

   public long getReconnectMaxDelayMs_0844() {
      return this.reconnectMaxDelayMs;
   }

   public long getCommandTimeoutMs_0848() {
      return this.commandTimeoutMs;
   }

   public int getMaxCommandsPerSecond_0851() {
      return this.maxCommandsPerSecond;
   }

   public static class NestedType302 {
      private long longValueField1;
      private boolean flagField2;
      private List<DiscordBuild> listField3;
      private boolean flagField4;
      private boolean flagField5;
      private boolean flagField6;
      private int integerField7;
      private boolean flagField8;
      private long longValueField9;
      private boolean flagField10;
      private long longValueField11;
      private boolean flagField12;
      private long longValueField13;
      private boolean flagField14;
      private int integerField15;

      NestedType302() {
      }

      public DiscordComponent055.NestedType302 computeValue_0862(long l) {
         this.longValueField1 = l;
         return this;
      }

      public DiscordComponent055.NestedType302 computeValue_0863(List<DiscordBuild> list) {
         this.listField3 = list;
         this.flagField2 = true;
         return this;
      }

      public DiscordComponent055.NestedType302 computeValue_0864(boolean bl) {
         this.flagField5 = bl;
         this.flagField4 = true;
         return this;
      }

      public DiscordComponent055.NestedType302 computeValue_0861(int i) {
         this.integerField7 = i;
         this.flagField6 = true;
         return this;
      }

      public DiscordComponent055.NestedType302 computeValue_0858(long l) {
         this.longValueField9 = l;
         this.flagField8 = true;
         return this;
      }

      public DiscordComponent055.NestedType302 computeValue_0859(long l) {
         this.longValueField11 = l;
         this.flagField10 = true;
         return this;
      }

      public DiscordComponent055.NestedType302 computeValue_0865(long l) {
         this.longValueField13 = l;
         this.flagField12 = true;
         return this;
      }

      public DiscordComponent055.NestedType302 computeValue_0857(int i) {
         this.integerField15 = i;
         this.flagField14 = true;
         return this;
      }

      public DiscordComponent055 computeValue_0860() {
         List list = this.listField3;
         if (!this.flagField2) {
            list = DiscordComponent055.computeValue_0845();
         }

         boolean bl = this.flagField5;
         if (!this.flagField4) {
            bl = DiscordComponent055.checkCondition_0855();
         }

         int i = this.integerField7;
         if (!this.flagField6) {
            i = DiscordComponent055.computeValue_0853();
         }

         long l = this.longValueField9;
         if (!this.flagField8) {
            l = DiscordComponent055.computeValue_0841();
         }

         long m = this.longValueField11;
         if (!this.flagField10) {
            m = DiscordComponent055.computeValue_0847();
         }

         long n = this.longValueField13;
         if (!this.flagField12) {
            n = DiscordComponent055.computeValue_0852();
         }

         int j = this.integerField15;
         if (!this.flagField14) {
            j = DiscordComponent055.computeValue_0854();
         }

         return new DiscordComponent055(this.longValueField1, list, bl, i, l, m, n, j);
      }

      @Override
      public String toString() {
         long l = this.longValueField1;
         List list = this.listField3;
         boolean bl = this.flagField5;
         int i = this.integerField7;
         long m = this.longValueField9;
         long n = this.longValueField11;
         long o = this.longValueField13;
         int j = this.integerField15;
         return "DiscordIPCConfig.DiscordIPCConfigBuilder(clientId="
            + l
            + ", preferredBuilds$value="
            + l
            + ", reconnect$value="
            + list
            + ", maxReconnectAttempts$value="
            + bl
            + ", reconnectBaseDelayMs$value="
            + i
            + ", reconnectMaxDelayMs$value="
            + m
            + ", commandTimeoutMs$value="
            + l
            + ", maxCommandsPerSecond$value="
            + n
            + ")";
      }
   }
}
