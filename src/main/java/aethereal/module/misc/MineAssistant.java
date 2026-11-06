package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent074;
import aethereal.render.RenderComponent183;
import aethereal.setting.BooleanSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.util.UtilComponent252;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

@ModuleRegister(
   name = "Mine Assistant",
   description = "An assistant that simplifies the extraction of resources in a mine under FunTime/SpookyTime",
   category = Category.Misc
)
public class MineAssistant extends Module implements MinecraftAccess {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Select highlighted ores",
      new BooleanSetting("Diamond", true),
      new BooleanSetting("Redstone", false),
      new BooleanSetting("Iron", false),
      new BooleanSetting("Lapis", false),
      new BooleanSetting("Golden", true),
      new BooleanSetting("Ancient", true),
      new BooleanSetting("Coal", false)
   );
   private final List<MineAssistant.NestedType344> listField2 = Arrays.asList(
      new MineAssistant.NestedType344(Blocks.DIAMOND_ORE, Color.CYAN.getRGB(), "Diamond"),
      new MineAssistant.NestedType344(Blocks.DEEPSLATE_DIAMOND_ORE, Color.CYAN.getRGB(), "Diamond"),
      new MineAssistant.NestedType344(Blocks.REDSTONE_ORE, Color.RED.getRGB(), "Redstone"),
      new MineAssistant.NestedType344(Blocks.DEEPSLATE_REDSTONE_ORE, Color.RED.getRGB(), "Redstone"),
      new MineAssistant.NestedType344(Blocks.IRON_ORE, Color.LIGHT_GRAY.getRGB(), "Iron"),
      new MineAssistant.NestedType344(Blocks.DEEPSLATE_IRON_ORE, Color.LIGHT_GRAY.getRGB(), "Iron"),
      new MineAssistant.NestedType344(Blocks.LAPIS_ORE, Color.BLUE.getRGB(), "Lapis"),
      new MineAssistant.NestedType344(Blocks.DEEPSLATE_LAPIS_ORE, Color.BLUE.getRGB(), "Lapis"),
      new MineAssistant.NestedType344(Blocks.GOLD_ORE, Color.YELLOW.getRGB(), "Golden"),
      new MineAssistant.NestedType344(Blocks.DEEPSLATE_GOLD_ORE, Color.YELLOW.getRGB(), "Golden"),
      new MineAssistant.NestedType344(Blocks.ANCIENT_DEBRIS, new Color(102, 51, 0).getRGB(), "Ancient"),
      new MineAssistant.NestedType344(Blocks.COAL_ORE, Color.DARK_GRAY.getRGB(), "Coal"),
      new MineAssistant.NestedType344(Blocks.DEEPSLATE_COAL_ORE, Color.DARK_GRAY.getRGB(), "Coal"),
      new MineAssistant.NestedType344(Blocks.AIR, -1, null),
      new MineAssistant.NestedType344(Blocks.STONE, -1, null),
      new MineAssistant.NestedType344(Blocks.GRANITE, -1, null),
      new MineAssistant.NestedType344(Blocks.COBBLESTONE, -1, null)
   );
   private Box boxField3;

   public Box getBoxField3_1931() {
      return this.boxField3;
   }

   public MineAssistant() {
      this.addSettings(this.multiSelectSettingField1);
   }

   @EventTarget
   public void onEventComponent074_1933(EventComponent074 eventComponent074) {
      if (UtilComponent252.NestedType400.checkCondition_3406() || UtilComponent252.NestedType403.checkCondition_3420()) {
         this.performAction_1936();
      }
   }

   public void performAction_1936() {
      for (ArmorStandEntity armorStandEntity : minecraft.world
         .getEntitiesByClass(ArmorStandEntity.class, minecraft.player.getBoundingBox().expand(256.0), armorStandEntity2 -> true)) {
         if (armorStandEntity instanceof ArmorStandEntity armorStandEntity1 && armorStandEntity1.getName().getString().contains("Auto-Mine")) {
            if (this.boxField3 != null && !(this.boxField3.getAverageSideLength() <= 15.0)) {
               return;
            }

            int i = (int)Math.floor(armorStandEntity1.getY()) - 2;
            int j = (int)Math.floor(armorStandEntity1.getX());
            int k = (int)Math.floor(armorStandEntity1.getZ());
            int l = j;
            int m = j;
            int n = k;
            int o = k;

            while (this.computeValue_1935(minecraft.world.getBlockState(new BlockPos(l - 2, i, k)).getBlock()) != null) {
               l--;
            }

            while (this.computeValue_1935(minecraft.world.getBlockState(new BlockPos(m + 2, i, k)).getBlock()) != null) {
               m++;
            }

            while (this.computeValue_1935(minecraft.world.getBlockState(new BlockPos(j, i, n - 1)).getBlock()) != null) {
               n--;
            }

            while (this.computeValue_1935(minecraft.world.getBlockState(new BlockPos(j, i, o + 1)).getBlock()) != null) {
               o++;
            }

            this.boxField3 = new Box(l, i + 1, n, m + 1, i - 8, o + 1);
            return;
         }
      }

      this.boxField3 = null;
   }

   @EventTarget
   public void onDrawEvent_1932(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1002() && this.boxField3 != null) {
         for (int i = (int)this.boxField3.minX; i <= (int)this.boxField3.maxX; i++) {
            for (int j = (int)this.boxField3.minY; j <= (int)this.boxField3.maxY; j++) {
               for (int k = (int)this.boxField3.minZ; k <= (int)this.boxField3.maxZ; k++) {
                  BlockPos blockPos = new BlockPos(i, j, k);
                  MineAssistant.NestedType344 nestedType344 = this.computeValue_1935(minecraft.world.getBlockState(blockPos).getBlock());
                  if (nestedType344 != null
                     && nestedType344.getColor_1937() != -1
                     && this.multiSelectSettingField1.computeValue_2852(nestedType344.getName_1938()).getCurrentValue()) {
                     drawEvent.getRenderComponent185Field2_0999()
                        .performAction_2594(
                           drawEvent.getStack2_1004(), new Box(blockPos), RenderComponent183.computeValue_2556(nestedType344.getColor_1937(), 150), 1.0F
                        );
                  }
               }
            }
         }
      }
   }

   public MineAssistant.NestedType344 computeValue_1935(Block block) {
      for (MineAssistant.NestedType344 nestedType344 : this.listField2) {
         if (nestedType344.block == block) {
            return nestedType344;
         }
      }

      return null;
   }

   public static class NestedType344 {
      final Block block;
      final int color;
      final String name;

      public Block getBlock_1939() {
         return this.block;
      }

      public int getColor_1937() {
         return this.color;
      }

      public String getName_1938() {
         return this.name;
      }

      NestedType344(Block block, int i, String string) {
         this.block = blockx;
         this.color = i;
         this.name = string;
      }
   }
}
