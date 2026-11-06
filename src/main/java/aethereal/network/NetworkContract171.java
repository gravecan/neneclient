package aethereal.network;

import aethereal.core.CoreComponent040;
import aethereal.discord.DiscordBuild;
import aethereal.discord.DiscordComponent060;

public interface NetworkContract171 {
   final class NestedType365 implements NetworkContract171 {
   }

   final class NestedType366 implements NetworkContract171 {
      private final CoreComponent040 user;
      private final DiscordBuild build;

      public NestedType366(CoreComponent040 coreComponent040, DiscordBuild discordBuild) {
         this.user = coreComponent040;
         this.build = discordBuild;
      }

      public CoreComponent040 getUser_2430() {
         return this.user;
      }

      public DiscordBuild getBuild_2431() {
         return this.build;
      }
   }

   final class NestedType367 implements NetworkContract171 {
   }

   final class NestedType368 implements NetworkContract171 {
   }

   final class NestedType369 implements NetworkContract171 {
      private final DiscordComponent060 failure;

      public NestedType369(DiscordComponent060 discordComponent060) {
         this.failure = discordComponent060;
      }

      public DiscordComponent060 getFailure_2432() {
         return this.failure;
      }
   }

   final class NestedType370 implements NetworkContract171 {
      private final int attempt;
      private final DiscordComponent060 failure;

      public NestedType370(int i, DiscordComponent060 discordComponent060) {
         this.attempt = i;
         this.failure = discordComponent060;
      }

      public int getAttempt_2434() {
         return this.attempt;
      }

      public DiscordComponent060 getFailure_2433() {
         return this.failure;
      }
   }
}
