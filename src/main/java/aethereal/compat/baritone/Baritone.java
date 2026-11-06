package aethereal.compat.baritone;

import aethereal.compat.baritone.goal.Goal;
import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public interface Baritone {
   Baritone.PathingBehavior computeValue_0010();

   Baritone.GoalProcess computeValue_0008();

   Baritone.MineProcess computeValue_0009();

   interface GoalProcess {
      void performAction_0011(Goal goal);
   }

   interface MineProcess {
      boolean checkCondition_0013();

      void performAction_0014(Item item, Iterable<BlockPos> iterable);

      Set<BlockPos> getSetField1_0012();
   }

   interface PathingBehavior {
      boolean checkCondition_0015();

      void performAction_0016();

      void performAction_0017();
   }
}
