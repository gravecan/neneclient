package platform.inject.invokers;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityCollisionPredictionInvoker {
   @Invoker("findCollisionsForMovement")
   static List<VoxelShape> findCollisionsForMovement(Entity entity, World world, List<VoxelShape> list, Box box) {
      throw new AssertionError();
   }

   @Invoker("adjustMovementForCollisions")
   static Vec3d adjustMovementForCollisions(Entity entity, Vec3d vec3d, Box box, World world, List<VoxelShape> list) {
      throw new AssertionError();
   }
}
