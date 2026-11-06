package aethereal.compat.baritone;

import aethereal.compat.baritone.goal.Goal;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public final class BaritoneApi {
   private static BaritoneProvider baritoneProviderField1;
   private static final BaritoneSettings baritoneSettingsField2 = new BaritoneSettings();
   private static final Baritone baritoneField3 = new BaritoneApi.StubBaritone();

   private BaritoneApi() {
   }

   public static BaritoneProvider computeValue_0003() {
      return baritoneProviderField1 == null ? () -> baritoneField3 : baritoneProviderField1;
   }

   public static void setBaritoneProviderField1_0004(BaritoneProvider baritoneProvider) {
      baritoneProviderField1 = baritoneProvider;
   }

   public static BaritoneSettings getBaritoneSettingsField2_0001() {
      return baritoneSettingsField2;
   }

   private static final class StubBaritone implements Baritone {
      private final BaritoneApi.StubBaritone.StubPathingBehavior stubPathingBehaviorField1 = new BaritoneApi.StubBaritone.StubPathingBehavior();
      private final BaritoneApi.StubBaritone.StubGoalProcess stubGoalProcessField2 = new BaritoneApi.StubBaritone.StubGoalProcess();
      private final BaritoneApi.StubBaritone.StubMineProcess stubMineProcessField3 = new BaritoneApi.StubBaritone.StubMineProcess();

      public BaritoneApi.StubBaritone.StubPathingBehavior getStubPathingBehaviorField1_0007() {
         return this.stubPathingBehaviorField1;
      }

      public BaritoneApi.StubBaritone.StubGoalProcess getStubGoalProcessField2_0005() {
         return this.stubGoalProcessField2;
      }

      public BaritoneApi.StubBaritone.StubMineProcess getStubMineProcessField3_0006() {
         return this.stubMineProcessField3;
      }

      private static final class StubGoalProcess implements Baritone.GoalProcess {
         @Override
         public void performAction_0011(Goal goal) {
         }
      }

      private static final class StubMineProcess implements Baritone.MineProcess {
         private final Set<BlockPos> setField1 = new HashSet<>();

         @Override
         public boolean checkCondition_0013() {
            return false;
         }

         @Override
         public void performAction_0014(Item item, Iterable<BlockPos> iterable) {
         }

         @Override
         public Set<BlockPos> getSetField1_0012() {
            return this.setField1;
         }
      }

      private static final class StubPathingBehavior implements Baritone.PathingBehavior {
         @Override
         public boolean checkCondition_0015() {
            return false;
         }

         @Override
         public void performAction_0016() {
         }

         @Override
         public void performAction_0017() {
         }
      }
   }
}
