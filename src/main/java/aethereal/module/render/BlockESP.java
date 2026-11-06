package aethereal.module.render;

import aethereal.config.ThemeInfo;
import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent074;
import aethereal.render.RenderComponent183;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.chunk.BlockEntityTickInvoker;
import platform.inject.accessors.WorldAccessor;

@ModuleRegister(name = "Block ESP", description = "Highlights the blocks you added via .blockesp", category = Category.Render)
public class BlockESP extends Module {
   private final List<BlockPos> listField1 = new CopyOnWriteArrayList<>();
   private ExecutorService executorServiceField2;
   private int integerField3;

   @Override
   public void onEnable() {
      super.onEnable();
      this.listField1.clear();
      this.executorServiceField2 = Executors.newSingleThreadExecutor();
      this.integerField3 = 0;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.listField1.clear();
      if (this.executorServiceField2 != null) {
         this.executorServiceField2.shutdownNow();
      }
   }

   @EventTarget
   public void onEventComponent074_2223(EventComponent074 eventComponent074) {
      int i = this.integerField3 + 1;
      this.integerField3 = i;
      if (i % 12 == 0) {
         List list = NeneClient.getInstance().getProcessors().getCommandManagerField13_0666().getBlockESPCommandField7_0214().getListField1_0079();
         if (list.isEmpty()) {
            this.listField1.clear();
         } else {
            this.executorServiceField2
               .submit(() -> this.performAction_2227(list.stream().map(nestedType289 -> nestedType289.getBlock_0106()).collect(Collectors.toSet())));
         }
      }
   }

   @EventTarget
   public void onDrawEvent_2222(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1002()) {
         List list = NeneClient.getInstance().getProcessors().getCommandManagerField13_0666().getBlockESPCommandField7_0214().getListField1_0079();
         if (!list.isEmpty()) {
            Map map = list.stream()
               .collect(
                  Collectors.toMap(
                     nestedType289 -> nestedType289.getBlock_0106(), nestedType289 -> nestedType289.getColor_0105(), (integer1, integer) -> integer
                  )
               );

            for (BlockEntityTickInvoker blockEntityTickInvoker : ((WorldAccessor)minecraft.world).getBlockEntityTickers()) {
               if (!blockEntityTickInvoker.isRemoved()) {
                  this.performAction_2226(drawEvent, blockEntityTickInvoker.getPos(), map);
               }
            }

            for (BlockPos blockPos : this.listField1) {
               this.performAction_2226(drawEvent, blockPos, map);
            }
         }
      }
   }

   private void performAction_2226(DrawEvent drawEvent, BlockPos blockPos, Map<Block, Integer> map) {
      Integer integer = (Integer)map.get(minecraft.world.getBlockState(blockPos).getBlock());
      if (integer != null) {
         drawEvent.getRenderComponent185Field2_0999()
            .performAction_2594(
               drawEvent.getStack2_1004(),
               new Box(blockPos),
               integer != -1
                  ? integer
                  : RenderComponent183.computeValue_2556(
                     NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), 140
                  ),
               1.5F
            );
      }
   }

   private void performAction_2227(Set<Block> set) {
      ArrayList arrayList = new ArrayList();
      BlockPos blockPos = minecraft.player.getBlockPos();
      Mutable mutable = new Mutable();
      int i = minecraft.world.getTopYInclusive();

      for (int j = blockPos.getX() - 70; j <= blockPos.getX() + 70; j++) {
         for (int k = blockPos.getZ() - 70; k <= blockPos.getZ() + 70; k++) {
            if (minecraft.world.getChunkManager().isChunkLoaded(j >> 4, k >> 4)) {
               for (int l = minecraft.world.getBottomY(); l < i; l++) {
                  if (set.contains(minecraft.world.getBlockState(mutable.set(j, l, k)).getBlock())) {
                     arrayList.add(mutable.toImmutable());
                  }
               }
            }
         }
      }

      this.listField1.clear();
      this.listField1.addAll(arrayList);
   }
}
