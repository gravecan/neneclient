package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent188;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent268;
import aethereal.util.UtilComponent284;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.chunk.BlockEntityTickInvoker;
import org.joml.Vector2f;
import platform.inject.accessors.WorldAccessor;

@ModuleRegister(name = "Warden ESP", description = "Displays chests in the Varden city with a respawn timer", category = Category.Render)
public class WardenESP extends Module {
   private static final Pattern patternField1 = Pattern.compile("(\\d{2}):(\\d{2})");
   private static final Identifier CHEST_TEXTURE = Identifier.of("neneclient", "pictures/minecraft/chest.png");
   private final List<WardenESP.NestedType360> listField2 = new ArrayList<>();
   private List<BlockPos> cachedChests = List.of();
   private long chestScanTick = Long.MIN_VALUE;
   private long armorStandScanTick = Long.MIN_VALUE;

   @EventTarget
   public void onDrawEvent_2320(DrawEvent drawEvent) {
      if (minecraft.world.getRegistryKey().getValue().toString().equals("minecraft:overworld")) {
         List list = this.computeValue_2327();
         if (drawEvent.checkCondition_1001()) {
            long l = minecraft.world.getTime();
            if (this.armorStandScanTick != Long.MIN_VALUE && l - this.armorStandScanTick < 10L) {
               this.performAction_2322(drawEvent, list);
               return;
            }

            this.armorStandScanTick = l;

            for (ArmorStandEntity armorStandEntity : minecraft.world
               .getEntitiesByClass(ArmorStandEntity.class, minecraft.player.getBoundingBox().expand(256.0), armorStandEntity2 -> true)) {
               if (armorStandEntity instanceof ArmorStandEntity armorStandEntity1) {
                  Matcher matcher = patternField1.matcher(armorStandEntity1.getName().getString());
                  if (matcher.find()) {
                     int i = Integer.parseInt(matcher.group(1));
                     int j = Integer.parseInt(matcher.group(2));
                     long m = (i * 60L + j) * 1000L;
                     BlockPos blockPos = this.computeValue_2324(list, armorStandEntity1.getBlockPos());
                     if (blockPos != null) {
                        WardenESP.NestedType360 nestedType360 = this.computeValue_2326(blockPos);
                        if (nestedType360 != null) {
                           nestedType360.performAction_2330(m);
                        } else {
                           this.listField2.add(new WardenESP.NestedType360(blockPos, m));
                        }
                     }
                  }
               }
            }

            this.listField2.removeIf(nestedType3601 -> nestedType3601.computeValue_2332() <= 0L);
            this.performAction_2322(drawEvent, list);
         }

         if (drawEvent.checkCondition_1002()) {
            this.performAction_2317(drawEvent, list);
         }
      }
   }

   private void performAction_2322(DrawEvent drawEvent, List<BlockPos> list) {
      int i = RenderComponent183.computeValue_2558(11, 11, 13, 179);

      for (BlockPos blockPos : list) {
         WardenESP.NestedType360 nestedType360 = this.computeValue_2326(blockPos);
         if (nestedType360 != null) {
            Vector2f vector2f = UtilComponent284.computeValue_3615(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5);
            if (UtilComponent284.checkCondition_3618(vector2f)) {
               int j = (int)(nestedType360.computeValue_2332() / 1000L);
               MutableText mutableText = Text.literal(String.format(Locale.US, "%02d:%02d", j / 60, j % 60));
               float f = 16.5F + RenderComponent188.renderComponent187Field5.computeValue_2628(mutableText, 6.5F);
               float g = vector2f.x() - f / 2.0F;
               float h = vector2f.y() - 6.0F;
               drawEvent.getRenderComponent184Field1_0998().performAction_2580(drawEvent.getStack_1005(), g, h, f - 0.5F, 12.0F, 3.5F, i, 1.0F, i, 6.0F);
               drawEvent.getRenderComponent184Field1_0998()
                  .performAction_2585(drawEvent.getStack_1005(), CHEST_TEXTURE, g + 3.0F, h + 0.5F + 2.0F, 7.0F, 7.0F, 0.0F, -1);
               RenderComponent188.renderComponent187Field5
                  .performAction_2634(
                     drawEvent.getStack_1005(),
                     mutableText,
                     g + 3.0F + 8.0F + 2.0F,
                     h + (12.0F - RenderComponent188.renderComponent187Field5.computeValue_2622(6.5F)) / 2.0F - 0.5F,
                     6.5F
                  );
            }
         }
      }
   }

   private void performAction_2317(DrawEvent drawEvent, List<BlockPos> list) {
      for (BlockPos blockPos : list) {
         if (this.computeValue_2326(blockPos) == null) {
            drawEvent.getRenderComponent185Field2_0999()
               .performAction_2594(
                  drawEvent.getStack2_1004(),
                  new Box(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1),
                  RenderComponent183.computeValue_2558(255, 100, 100, 255),
                  1.0F
               );
         }
      }
   }

   private BlockPos computeValue_2324(List<BlockPos> list, BlockPos blockPos) {
      for (BlockPos blockPos1 : list) {
         if (blockPos.getX() == blockPos1.getX() && blockPos.getZ() == blockPos1.getZ()) {
            return blockPos1;
         }
      }

      return null;
   }

   private WardenESP.NestedType360 computeValue_2326(BlockPos blockPos) {
      int i = UtilComponent252.NestedType400.computeValue_3414();

      for (WardenESP.NestedType360 nestedType360 : this.listField2) {
         if (nestedType360.getChestPos_2331().equals(blockPos) && nestedType360.getIntegerField2_2333() == i) {
            return nestedType360;
         }
      }

      return null;
   }

   public long computeValue_2325(BlockPos blockPos) {
      for (ArmorStandEntity armorStandEntity : minecraft.world
         .getEntitiesByClass(ArmorStandEntity.class, minecraft.player.getBoundingBox().expand(256.0), armorStandEntity2 -> true)) {
         if (armorStandEntity instanceof ArmorStandEntity armorStandEntity1
            && armorStandEntity1.getBlockPos().getX() == blockPos.getX()
            && armorStandEntity1.getBlockPos().getZ() == blockPos.getZ()) {
            Matcher matcher = patternField1.matcher(armorStandEntity1.getName().getString());
            if (matcher.find()) {
               return (Integer.parseInt(matcher.group(1)) * 60L + Integer.parseInt(matcher.group(2))) * 1000L;
            }
         }
      }

      WardenESP.NestedType360 nestedType360 = this.computeValue_2326(blockPos);
      return nestedType360 == null ? -1L : nestedType360.computeValue_2332();
   }

   public List<BlockPos> computeValue_2321() {
      ArrayList arrayList = new ArrayList();

      for (BlockEntityTickInvoker blockEntityTickInvoker : ((WorldAccessor)minecraft.world).getBlockEntityTickers()) {
         BlockPos blockPos = blockEntityTickInvoker.getPos();
         BlockEntity blockEntity;
         BlockEntityType blockEntityType;
         if (!blockEntityTickInvoker.isRemoved()
            && blockPos.getY() >= -60
            && blockPos.getY() <= -35
            && blockPos.getX() >= -2070
            && blockPos.getX() <= -1921
            && blockPos.getZ() >= -2076
            && blockPos.getZ() <= -1929
            && (blockEntity = minecraft.world.getBlockEntity(blockPos)) != null
            && ((blockEntityType = blockEntity.getType()) == BlockEntityType.CHEST || blockEntityType == BlockEntityType.TRAPPED_CHEST)) {
            arrayList.add(blockPos);
         }
      }

      return arrayList;
   }

   private List<BlockPos> computeValue_2327() {
      long l = minecraft.world.getTime();
      if (this.chestScanTick == Long.MIN_VALUE || l - this.chestScanTick >= 10L) {
         this.chestScanTick = l;
         this.cachedChests = this.computeValue_2321();
      }

      return this.cachedChests;
   }

   public static class NestedType360 {
      private final BlockPos chestPos;
      private long current;
      private final UtilComponent268 utilComponent268Field1 = new UtilComponent268();
      private int integerField2 = UtilComponent252.NestedType400.computeValue_3414();

      public UtilComponent268 getUtilComponent268Field1_2328() {
         return this.utilComponent268Field1;
      }

      public BlockPos getChestPos_2331() {
         return this.chestPos;
      }

      public long getCurrent_2329() {
         return this.current;
      }

      public int getIntegerField2_2333() {
         return this.integerField2;
      }

      public NestedType360(BlockPos blockPos, long l) {
         this.chestPos = blockPos;
         this.current = l;
         this.utilComponent268Field1.performAction_3524();
      }

      public void performAction_2330(long l) {
         if (Math.abs(l / 1000L - this.computeValue_2332() / 1000L) > 5L) {
            this.current = l;
            this.utilComponent268Field1.performAction_3524();
            this.integerField2 = UtilComponent252.NestedType400.computeValue_3414();
         }
      }

      public long computeValue_2332() {
         return Math.max(0L, this.current - this.utilComponent268Field1.computeValue_3521());
      }
   }
}
