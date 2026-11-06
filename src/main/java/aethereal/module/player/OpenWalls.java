package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import java.util.Set;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.BlastFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CartographyTableBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LoomBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SmokerBlock;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@ModuleRegister(name = "Open Walls", description = "Allows you to open vaults through walls", category = Category.Player)
public class OpenWalls extends Module {
   private final Set<Class<?>> setField1 = Set.of(
      AbstractChestBlock.class,
      FurnaceBlock.class,
      CraftingTableBlock.class,
      SpawnerBlock.class,
      ShulkerBoxBlock.class,
      AnvilBlock.class,
      BeaconBlock.class,
      BlastFurnaceBlock.class,
      BrewingStandBlock.class,
      CampfireBlock.class,
      CartographyTableBlock.class,
      GrindstoneBlock.class,
      LecternBlock.class,
      LoomBlock.class,
      SmokerBlock.class,
      StonecutterBlock.class,
      BarrelBlock.class
   );

   public Set<Class<?>> getSetField1_2202() {
      return this.setField1;
   }

   public BlockHitResult computeValue_2203(ClientPlayerEntity clientPlayerEntity) {
      Vec3d vec3d = clientPlayerEntity.getCameraPosVec(1.0F);
      Vec3d vec3d1 = vec3d.add(clientPlayerEntity.getRotationVec(1.0F).multiply(clientPlayerEntity.getBlockInteractionRange()));
      return clientPlayerEntity.getEntityWorld().raycast(new RaycastContext(vec3d, vec3d1, ShapeType.OUTLINE, FluidHandling.NONE, clientPlayerEntity) {
         public VoxelShape getBlockShape(BlockState blockState, BlockView blockView, BlockPos blockPos) {
            for (Class class_ : NeneClient.getInstance().getProcessors().getModules().getOpenWallsField78_0477().getSetField1_2202()) {
               if (class_.isInstance(blockState.getBlock())) {
                  return super.getBlockShape(blockState, blockView, blockPos);
               }
            }

            return VoxelShapes.empty();
         }
      });
   }
}
