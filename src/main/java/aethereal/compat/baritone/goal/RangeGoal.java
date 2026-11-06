package aethereal.compat.baritone.goal;

import net.minecraft.util.math.BlockPos;

public record RangeGoal(double range, BlockPos[] positions) implements Goal {
}
