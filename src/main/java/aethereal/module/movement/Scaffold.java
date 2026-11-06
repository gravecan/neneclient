package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.ClientTickEvent;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent090;
import aethereal.event.EventComponent091;
import aethereal.event.PacketEvent;
import aethereal.handler.HandlerComponent108;
import aethereal.handler.HandlerComponent117;
import aethereal.setting.BooleanSetting;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent277;
import aethereal.util.UtilComponent280;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import platform.inject.invokers.MinecraftClientInvoker;

@ModuleRegister(name = "Scaffold", description = "Automatically places blocks below you", category = Category.Movement)
public class Scaffold extends Module {
   private Scaffold.NestedType350 nestedType350Field1;
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Avoid falling", false);
   private Vec3d vec3dField3 = Vec3d.ZERO;
   private final int[] integerArrayField4 = new int[]{-1, -1, -1};

   public Scaffold() {
      this.addSettings(this.booleanSettingField2);
   }

   @EventTarget
   public void onEventComponent091_2062(EventComponent091 eventComponent091) {
      if (!this.checkCondition_2065()) {
         if (this.computeValue_2055() != null) {
            UtilComponent280.performAction_3592(eventComponent091, UtilComponent277.getFloatValueField2_3570(), 1);
            if (this.booleanSettingField2.getCurrentValue()) {
               NeneClient.getInstance().getProcessors().getModules().getSafeWalkField20_0451().onEventComponent091_2050(eventComponent091);
            }
         }

         if (!this.checkCondition_2063(minecraft.player.getMainHandStack()) && !this.checkCondition_2063(minecraft.player.getOffHandStack())) {
            int i = this.computeValue_2075(true);
            if (i != -1) {
               if (minecraft.player.getInventory().getSelectedSlot() != i
                  && this.integerArrayField4[2] < 7
                  && HandlerComponent108.getINVENTORY_1158().getListField1_1177().isEmpty()) {
                  minecraft.player.getInventory().setSelectedSlot(i);
                  this.integerArrayField4[2] = 9;
               }
            } else {
               int j = this.computeValue_2075(false);
               if (this.integerArrayField4[2] < 5 && j != -1 && HandlerComponent108.getINVENTORY_1158().getListField1_1177().isEmpty()) {
                  if (this.integerArrayField4[1] == -1) {
                     this.integerArrayField4[1] = j;
                  }

                  if (minecraft.player.getInventory().getSelectedSlot() != 5) {
                     minecraft.player.getInventory().setSelectedSlot(5);
                  }

                  HandlerComponent108.getINVENTORY_1158().performAction_1179(j, 5, 1);
                  this.integerArrayField4[2] = 9;
               }
            }
         }
      }
   }

   @EventTarget
   public void onEventComponent090_2061(EventComponent090 eventComponent090) {
      if (this.integerArrayField4[2] > 5 && this.computeValue_2075(false) != -1) {
         eventComponent090.performAction_0597(true);
      }
   }

   @EventTarget
   public void onPacketEvent_2060(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1012() && packetEvent.getPacket_1015() instanceof UpdateSelectedSlotC2SPacket) {
         this.integerArrayField4[2] = 9;
      }
   }

   @EventTarget
   public void onEventComponent074_2059(EventComponent074 eventComponent074) {
      int[] i = this.integerArrayField4;
      i[2]--;
   }

   @EventTarget
   public void performAction_2058(ClientTickEvent clientTickEvent) throws MatchException {
      if (this.computeValue_2055() != null && !this.checkCondition_2065()) {
         if (this.nestedType350Field1 == null || !this.checkCondition_2067(this.nestedType350Field1)) {
            this.nestedType350Field1 = null;

            for (BlockPos blockPos : this.computeValue_2064()) {
               this.nestedType350Field1 = this.computeValue_2069(blockPos);
               if (this.nestedType350Field1 != null) {
                  break;
               }
            }
         }

         if (this.nestedType350Field1 != null) {
            float g = minecraft.player.age + minecraft.getRenderTickCounter().getTickProgress(false);
            float h = (float)(Math.sin(g * 0.40000001611738834) * 3.0 + Math.sin(g * 0.950000126718632 + 1.4000003101900576) * 2.0) / 10.0F;
            float f = (float)(Math.cos(g * 0.5 + 0.7000001047992626) * 0.5 + Math.cos(g * 0.7800000028540086 + 3.099999110838922) * 1.5) / 4.0F;
            UtilComponent250 utilComponent250 = this.computeValue_2066(this.nestedType350Field1);
            utilComponent250.setYaw_3388(utilComponent250.getYaw_3386() + h);
            utilComponent250.setPitch_3384(utilComponent250.getPitch_3387() + f);
            HandlerComponent108.getROTATION_1157().performAction_1141(utilComponent250, 100.0F, 7, 1);
            if (this.checkCondition_2078() && !this.checkCondition_2079() && !HandlerComponent108.getSTOP_1159().checkCondition_1213()) {
               ((MinecraftClientInvoker)minecraft).invokeDoItemUse();
               this.integerArrayField4[2] = 9;
               this.nestedType350Field1 = null;
            }
         }
      }
   }

   private boolean checkCondition_2067(Scaffold.NestedType350 nestedType350) {
      BlockPos blockPos = nestedType350.pos.offset(nestedType350.face);
      return this.checkCondition_2054(minecraft.world.getBlockState(blockPos))
         && this.checkCondition_2077(nestedType350.pos)
         && !this.computeValue_2071(nestedType350.pos, nestedType350.face).isEmpty();
   }

   private List<Vec3d> computeValue_2071(BlockPos blockPos, Direction direction) throws MatchException {
      ArrayList arrayList = new ArrayList();
      Vec3d vec3d = this.computeValue_2053();
      double d = minecraft.player.getBlockInteractionRange();
      Vec3d vec3d1 = new Vec3d(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());
      double[] e = new double[]{0.0, -0.20000000236855192, 0.200000000060146, -0.3500000030268554, 0.3500000598673184, -0.4500000079401218, 0.4499998886196472};

      for (double f : e) {
         for (double g : e) {
            Vec3d vec3d2 = this.computeValue_2072(blockPos, direction, f, g);
            if (vec3d.distanceTo(vec3d2) <= d && vec3d.subtract(vec3d2).normalize().dotProduct(vec3d1) > 0.1000000076546522) {
               BlockHitResult blockHitResult = minecraft.world
                  .raycast(new RaycastContext(vec3d, vec3d2, ShapeType.COLLIDER, FluidHandling.NONE, minecraft.player));
               if (blockHitResult.getType() == Type.MISS) {
                  arrayList.add(vec3d2);
               } else if (blockHitResult instanceof BlockHitResult blockHitResult1 && blockHitResult1.getBlockPos().equals(blockPos)) {
                  arrayList.add(vec3d2);
               }
            }
         }
      }

      return arrayList;
   }

   private List<BlockPos> computeValue_2064() {
      ArrayList arrayList = new ArrayList();
      this.vec3dField3 = this.vec3dField3
         .multiply(0.6000003608875443)
         .add(new Vec3d(minecraft.player.getX() - minecraft.player.lastX, 0.0, minecraft.player.getZ() - minecraft.player.lastZ).multiply(0.200000000060146));

      for (int i = 0; i <= 2; i++) {
         Vec3d vec3d = minecraft.player.getEntityPos().add(this.vec3dField3.multiply(i));
         BlockPos blockPos = BlockPos.ofFloored(vec3d.x, minecraft.player.getY() - 1.0, vec3d.z);
         if (!arrayList.contains(blockPos) && this.checkCondition_2054(minecraft.world.getBlockState(blockPos))) {
            arrayList.add(blockPos);
         }
      }

      arrayList.sort(Comparator.comparingDouble(blockPos1 -> blockPos1.getSquaredDistance(minecraft.player.getEntityPos())));
      return arrayList;
   }

   private boolean checkCondition_2065() {
      List list = HandlerComponent108.getUSEABLE_1160().getListField1_1222();
      return !list.isEmpty() && ((HandlerComponent117.NestedType327)list.getFirst()).getIntegerField3_1232() <= 1;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.nestedType350Field1 = null;
      this.vec3dField3 = Vec3d.ZERO;
      if (minecraft.player != null) {
         this.integerArrayField4[0] = minecraft.player.getInventory().getSelectedSlot();
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (this.integerArrayField4[0] != -1 && minecraft.player != null) {
         minecraft.player.getInventory().setSelectedSlot(this.integerArrayField4[0]);
         if (this.integerArrayField4[1] != -1) {
            HandlerComponent108.getINVENTORY_1158().performAction_1179(this.integerArrayField4[1], 5, 1);
         }
      }

      this.integerArrayField4[0] = -1;
      this.integerArrayField4[1] = -1;
      this.integerArrayField4[2] = -1;
      this.nestedType350Field1 = null;
      this.vec3dField3 = Vec3d.ZERO;
   }

   private int computeValue_2075(boolean bl) {
      int i = bl ? 9 : 36;

      for (int j = 0; j < i; j++) {
         if (this.checkCondition_2063(minecraft.player.getInventory().getStack(j))) {
            return j;
         }
      }

      return -1;
   }

   private boolean checkCondition_2063(ItemStack itemStack) {
      if (!itemStack.isEmpty()) {
         BlockItem blockItem = (BlockItem)itemStack.getItem();
         if (blockItem instanceof BlockItem blockItem1 && blockItem1.getBlock().getDefaultState().isOpaqueFullCube()) {
            return true;
         }
      }

      return false;
   }

   private Hand computeValue_2055() {
      if (this.checkCondition_2063(minecraft.player.getMainHandStack())) {
         return Hand.MAIN_HAND;
      } else {
         return this.checkCondition_2063(minecraft.player.getOffHandStack()) ? Hand.OFF_HAND : null;
      }
   }

   private boolean checkCondition_2054(BlockState blockState) {
      return blockState.isReplaceable() ? true : blockState.getBlock() == Blocks.SNOW && (Integer)blockState.get(SnowBlock.LAYERS) < 8;
   }

   private boolean checkCondition_2077(BlockPos blockPos) {
      BlockState blockState = minecraft.world.getBlockState(blockPos);
      return !blockState.isAir()
         && blockState.getBlock() != Blocks.SNOW
         && !blockState.isReplaceable()
         && !blockState.getCollisionShape(minecraft.world, blockPos).isEmpty();
   }

   private Scaffold.NestedType350 computeValue_2069(BlockPos blockPos) throws MatchException {
      Scaffold.NestedType350 nestedType350 = this.computeValue_2052(blockPos);
      if (nestedType350 != null) {
         return nestedType350;
      }

      int[][] i = new int[][]{
         {-1, 0, 0},
         {1, 0, 0},
         {0, 0, -1},
         {0, 0, 1},
         {-1, 0, -1},
         {1, 0, 1},
         {-1, 0, 1},
         {1, 0, -1},
         {0, -1, 0},
         {-1, -1, 0},
         {1, -1, 0},
         {0, -1, -1},
         {0, -1, 1}
      };
      return Arrays.stream(i)
         .map(j -> blockPos.add(j[0], j[1], j[2]))
         .sorted(Comparator.comparingDouble(blockPos1 -> blockPos1.getSquaredDistance(vec3d)))
         .map(this::computeValue_2052)
         .filter(nestedType3501 -> Objects.nonNull(nestedType3501))
         .findFirst()
         .orElse(null);
   }

   private Scaffold.NestedType350 computeValue_2052(BlockPos blockPos) throws MatchException {
      if (!this.checkCondition_2054(minecraft.world.getBlockState(blockPos))) {
         return null;
      }

      Scaffold.NestedType350 nestedType350 = null;
      int i = 0;

      for (Direction direction : Direction.values()) {
         BlockPos blockPos1 = blockPos.offset(direction);
         if (this.checkCondition_2077(blockPos1)) {
            Direction direction1 = direction.getOpposite();
            List list = this.computeValue_2071(blockPos1, direction1);
            if (list.size() > i) {
               i = list.size();
               nestedType350 = new Scaffold.NestedType350(
                  blockPos1, direction1, new BlockHitResult(this.computeValue_2072(blockPos1, direction1, 0.0, 0.0), direction1, blockPos1, false)
               );
            }
         }
      }

      return nestedType350;
   }

   private UtilComponent250 computeValue_2066(Scaffold.NestedType350 nestedType350) throws MatchException {
      Vec3d vec3d = this.computeValue_2053();
      List list = this.computeValue_2071(nestedType350.pos, nestedType350.face);
      if (list.isEmpty()) {
         return UtilComponent250.computeValue_3390(vec3d, nestedType350.result.getPos());
      }

      Vec3d vec3d1 = list.stream().reduce(Vec3d.ZERO, (vec3d3, vec3d4) -> vec3d3.add(vec3d4)).multiply(1.0 / list.size());
      Vec3d vec3d2 = list.stream().min(Comparator.comparingDouble(vec3d4 -> vec3d4.squaredDistanceTo(vec3d1))).orElse(vec3d1);
      return UtilComponent250.computeValue_3390(vec3d, vec3d2);
   }

   private Vec3d computeValue_2053() {
      Vec3d vec3d = minecraft.player.getEyePos();
      double d = minecraft.player.getVelocity().y;
      return d < 0.0 ? vec3d.add(0.0, d * 0.5, 0.0) : vec3d;
   }

   private Box computeValue_2070(BlockPos blockPos) {
      VoxelShape voxelShape = minecraft.world.getBlockState(blockPos).getCollisionShape(minecraft.world, blockPos);
      return voxelShape.isEmpty() ? new Box(0.0, 0.0, 0.0, 1.0, 1.0, 1.0) : voxelShape.getBoundingBox();
   }

   private Vec3d computeValue_2072(BlockPos blockPos, Direction direction, double d, double e) throws MatchException {
      Box box = this.computeValue_2070(blockPos);
      double f = blockPos.getX() + (box.minX + box.maxX) / 2.0;
      double g = blockPos.getY() + (box.minY + box.maxY) / 2.0;
      double h = blockPos.getZ() + (box.minZ + box.maxZ) / 2.0;
      double i = box.getLengthX();
      double j = box.getLengthY();
      double k = box.getLengthZ();
      switch (Scaffold.NestedType349.integerArrayField1[direction.ordinal()]) {
         case 1:
            return new Vec3d(f + d * i, blockPos.getY() + box.maxY, h + e * k);
         case 2:
            return new Vec3d(f + d * i, blockPos.getY() + box.minY, h + e * k);
         case 3:
            return new Vec3d(f + d * i, g + e * j, blockPos.getZ() + box.minZ);
         case 4:
            return new Vec3d(f + d * i, g + e * j, blockPos.getZ() + box.maxZ);
         case 5:
            return new Vec3d(blockPos.getX() + box.minX, g + e * j, h + d * k);
         case 6:
            return new Vec3d(blockPos.getX() + box.maxX, g + e * j, h + d * k);
         default:
            throw new MatchException((String)null, (Throwable)null);
      }
   }

   private boolean checkCondition_2078() {
      if (!((minecraft.crosshairTarget instanceof BlockHitResult ? (BlockHitResult)minecraft.crosshairTarget : null) instanceof BlockHitResult blockHitResult)) {
         return false;
      } else {
         if (blockHitResult.getType() != Type.BLOCK) {
            return false;
         }

         BlockPos blockPos = this.nestedType350Field1.pos.offset(this.nestedType350Field1.face);
         return blockHitResult.getBlockPos().equals(this.nestedType350Field1.pos) && blockHitResult.getSide() == this.nestedType350Field1.face
            ? true
            : blockHitResult.getBlockPos().equals(blockPos) && this.checkCondition_2054(minecraft.world.getBlockState(blockPos));
      }
   }

   private boolean checkCondition_2079() {
      BlockPos blockPos = this.nestedType350Field1.pos.offset(this.nestedType350Field1.face);
      return !minecraft.world.getEntitiesByClass(Entity.class, new Box(blockPos), entity -> !entity.isSpectator() && entity.isAlive()).isEmpty();
   }

   static class NestedType349 {
      static final int[] integerArrayField1 = new int[Direction.values().length];

      static {
         try {
            integerArrayField1[Direction.UP.ordinal()] = 1;
         } catch (NoSuchFieldError noSuchFieldError5) {
         }

         try {
            integerArrayField1[Direction.DOWN.ordinal()] = 2;
         } catch (NoSuchFieldError noSuchFieldError4) {
         }

         try {
            integerArrayField1[Direction.NORTH.ordinal()] = 3;
         } catch (NoSuchFieldError noSuchFieldError3) {
         }

         try {
            integerArrayField1[Direction.SOUTH.ordinal()] = 4;
         } catch (NoSuchFieldError noSuchFieldError2) {
         }

         try {
            integerArrayField1[Direction.WEST.ordinal()] = 5;
         } catch (NoSuchFieldError noSuchFieldError1) {
         }

         try {
            integerArrayField1[Direction.EAST.ordinal()] = 6;
         } catch (NoSuchFieldError noSuchFieldError) {
         }
      }
   }

   static final class NestedType350 {
      final BlockPos pos;
      final Direction face;
      final BlockHitResult result;

      NestedType350(BlockPos blockPos, Direction direction, BlockHitResult blockHitResult) {
         this.pos = blockPos;
         this.face = direction;
         this.result = blockHitResult;
      }

      public BlockPos getPos_2080() {
         return this.pos;
      }

      public Direction getFace_2081() {
         return this.face;
      }

      public BlockHitResult getResult_2082() {
         return this.result;
      }
   }
}
