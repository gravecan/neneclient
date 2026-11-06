package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent078;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.util.UtilComponent268;
import aethereal.util.UtilComponent284;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.entity.boss.BossBar.Color;
import net.minecraft.entity.boss.BossBar.Style;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector2f;
import platform.inject.accessors.BossBarHudAccessor;

@ModuleRegister(name = "Structures", description = "Displays the time until the trap and seam structures disappear", category = Category.Player)
public class Structures extends Module {
   private final List<Block> allowedBlocks = Arrays.asList(
      Blocks.QUARTZ_BLOCK,
      Blocks.DEAD_TUBE_CORAL_BLOCK,
      Blocks.INFESTED_MOSSY_STONE_BRICKS,
      Blocks.PURPUR_PILLAR,
      Blocks.END_STONE_BRICKS,
      Blocks.NETHER_BRICKS,
      Blocks.GILDED_BLACKSTONE,
      Blocks.PRISMARINE_BRICKS,
      Blocks.ICE,
      Blocks.NETHER_WART_BLOCK,
      Blocks.RESPAWN_ANCHOR,
      Blocks.NETHERITE_BLOCK,
      Blocks.WHITE_STAINED_GLASS,
      Blocks.BLACK_CONCRETE,
      Blocks.POLISHED_BASALT
   );
   private final List<Structures.NestedType352> listField1 = new ObjectArrayList();
   private final Set<BlockPos> setField2 = new ObjectOpenHashSet();
   private int integerField3;

   @EventTarget
   public void onEventComponent078_2095(EventComponent078 eventComponent078) {
      if (this.allowedBlocks.contains(eventComponent078.getState_1022().getBlock())
         && !this.allowedBlocks.contains(eventComponent078.getOldState_1024().getBlock())) {
         this.setField2.add(eventComponent078.getPos_1023());
         this.integerField3 = 2;
      }
   }

   @EventTarget
   public void onEventComponent074_2094(EventComponent074 eventComponent074) {
      this.listField1.removeIf(nestedType352 -> {
         if (nestedType352.getUtilComponent268Field2_2100().checkCondition_3522(nestedType352.getType_2099().getCooldown_2109())) {
            nestedType352.performAction_2098();
            return true;
         } else {
            return false;
         }
      });
      this.listField1
         .forEach(
            nestedType352 -> {
               nestedType352.getAnimationField1_2104()
                  .performAction_2528(
                     !nestedType352.getUtilComponent268Field2_2100().checkCondition_3522(nestedType352.getType_2099().getCooldown_2109() - 200L)
                  );
               float f = Math.max(
                  0.0F, (float)(nestedType352.getType_2099().getCooldown_2109() - nestedType352.getUtilComponent268Field2_2100().computeValue_3521()) / 1000.0F
               );
               if (nestedType352.getBox_2101().contains(minecraft.player.getBlockPos())) {
                  nestedType352.performAction_2103(f / ((float)nestedType352.getType_2099().getCooldown_2109() / 1000.0F), f);
               } else {
                  nestedType352.performAction_2098();
               }
            }
         );
      if (this.integerField3 > 0) {
         int i = this.integerField3 - 1;
         this.integerField3 = i;
         if (i == 0) {
            BlockBox blockBox = (BlockBox)BlockBox.encompassPositions(this.setField2).orElse(null);
            if (blockBox != null) {
               for (Structures.State353 state353 : Structures.State353.values()) {
                  if (state353.checkCondition_2106(blockBox, this.setField2)) {
                     this.listField1.add(new Structures.NestedType352(state353, blockBox));
                     break;
                  }
               }
            }

            this.setField2.clear();
         }
      }
   }

   @EventTarget
   public void onDrawEvent_2093(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001()) {
         for (Structures.NestedType352 nestedType352 : this.listField1) {
            String string = String.format(
               Locale.US,
               "%.1f",
               Math.max(
                  0.0F, (float)(nestedType352.getType_2099().getCooldown_2109() - nestedType352.getUtilComponent268Field2_2100().computeValue_3521()) / 1000.0F
               )
            );
            nestedType352.getAnimationField1_2104()
               .performAction_2535(0.0F, 1.0F, 0.35F, RenderComponent186.contract372Field9, drawEvent.getTickNeneClient_0997());
            BlockPos blockPos = nestedType352.getBox_2101().getCenter();
            Vector2f vector2f = UtilComponent284.computeValue_3615(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
            float f = RenderComponent188.renderComponent187Field4.computeValue_2628(Text.literal(string), 7.5F);
            float g = RenderComponent188.renderComponent187Field4.computeValue_2622(7.5F);
            float h = f + 4.0F;
            float i = 14.0F + h;
            float j = vector2f.x() - i / 2.0F;
            float k = vector2f.y() - 6.0F;
            int l = (int)(120.0F * nestedType352.getAnimationField1_2104().getFloatValueField4_2530());
            drawEvent.getRenderComponent184Field1_0998()
               .performAction_2577(drawEvent.getContext_1000().getMatrices(), j, k, 12.0F, 12.0F, 0.0F, RenderComponent183.computeValue_2558(0, 0, 0, l));
            drawEvent.getRenderComponent185Field2_0999()
               .performAction_2592(
                  drawEvent.getContext_1000(),
                  nestedType352.getType_2099().getItem_2110().getDefaultStack(),
                  j + 1.5F,
                  k + 1.5F,
                  0,
                  nestedType352.getAnimationField1_2104().getFloatValueField4_2530(),
                  0.55F,
                  false
               );
            float m = j + 12.0F + 2.0F;
            drawEvent.getRenderComponent184Field1_0998()
               .performAction_2577(drawEvent.getContext_1000().getMatrices(), m, k, h, 12.0F, 0.0F, RenderComponent183.computeValue_2558(0, 0, 0, l));
            RenderComponent188.renderComponent187Field4
               .performAction_2630(
                  drawEvent.getContext_1000().getMatrices(),
                  string,
                  m + (h - f) / 2.0F,
                  k + (12.0F - g) / 2.0F + 0.5F,
                  7.5F,
                  RenderComponent183.computeValue_2549(-1, nestedType352.getAnimationField1_2104().getFloatValueField4_2530())
               );
         }
      }
   }

   @Override
   public void onDisable() {
      this.listField1.forEach(nestedType352 -> nestedType352.performAction_2098());
      this.setField2.clear();
      this.listField1.clear();
      this.integerField3 = 0;
      super.onDisable();
   }

   static class NestedType352 implements MinecraftAccess {
      private final Animation animationField1 = new Animation();
      private final UtilComponent268 utilComponent268Field2 = new UtilComponent268();
      private final Structures.State353 type;
      private final BlockBox box;
      private ClientBossBar clientBossBarField3;

      public Animation getAnimationField1_2104() {
         return this.animationField1;
      }

      public UtilComponent268 getUtilComponent268Field2_2100() {
         return this.utilComponent268Field2;
      }

      public Structures.State353 getType_2099() {
         return this.type;
      }

      public BlockBox getBox_2101() {
         return this.box;
      }

      public ClientBossBar getClientBossBarField3_2102() {
         return this.clientBossBarField3;
      }

      public NestedType352(Structures.State353 state353, BlockBox blockBox) {
         this.type = state353;
         this.box = blockBox;
         this.utilComponent268Field2.performAction_3524();
      }

      public void performAction_2103(float f, float g) {
         MutableText mutableText = Text.literal(String.format(Locale.US, "%s — %.1f sec", this.type.getDisplayName_2105(), g));
         if (this.clientBossBarField3 == null) {
            this.clientBossBarField3 = new ClientBossBar(UUID.randomUUID(), mutableText, f, Color.WHITE, Style.PROGRESS, false, false, false);
            ((BossBarHudAccessor)minecraft.inGameHud.getBossBarHud()).getBossBars().put(this.clientBossBarField3.getUuid(), this.clientBossBarField3);
         } else {
            this.clientBossBarField3.setName(mutableText);
            this.clientBossBarField3.setPercent(f);
         }
      }

      public void performAction_2098() {
         if (this.clientBossBarField3 != null) {
            ((BossBarHudAccessor)minecraft.inGameHud.getBossBarHud()).getBossBars().remove(this.clientBossBarField3.getUuid());
            this.clientBossBarField3 = null;
         }
      }
   }

   public enum State353 {
      DRAGON_TRAPKA("Dragon Trap", new int[][]{{7, 7, 7}, {7, 7, 6}}, true, 30000L, Items.NETHERITE_SCRAP),
      TRAPKA("Trap", new int[][]{{5, 5, 5}, {5, 5, 4}, {5, 6, 5}, {5, 6, 4}}, true, 15000L, Items.NETHERITE_SCRAP),
      DRAGON_PLAST("Dragon Plast", new int[][]{{7, 7, 2}, {7, 7, 1}}, true, 20000L, Items.DRIED_KELP),
      PLAST("Plast", new int[][]{{5, 5, 2}, {5, 5, 1}}, true, 20000L, Items.DRIED_KELP),
      GARMOSHKA("Plast", new int[][]{{5, 5, 5}, {5, 6, 5}}, false, 20000L, Items.DRIED_KELP);

      private final String displayName;
      private final int[][] dimensions;
      private final boolean hollow;
      private final long cooldown;
      private final Item item;

      State353(final String string1, final int[][] j, final boolean bl, final long l, final Item item) {
         this.displayName = string1;
         this.dimensions = j;
         this.hollow = bl;
         this.cooldown = l;
         this.item = itemx;
      }

      public String getDisplayName_2105() {
         return this.displayName;
      }

      public int[][] getDimensions_2107() {
         return this.dimensions;
      }

      public boolean isHollow_2108() {
         return this.hollow;
      }

      public long getCooldown_2109() {
         return this.cooldown;
      }

      public Item getItem_2110() {
         return this.item;
      }

      public boolean checkCondition_2106(BlockBox blockBox, Set<BlockPos> set) {
         int[] i = new int[]{blockBox.getBlockCountX(), blockBox.getBlockCountY(), blockBox.getBlockCountZ()};
         Arrays.sort(i);

         for (int[] j : this.dimensions) {
            int[] k = (int[])j.clone();
            Arrays.sort(k);
            if (Arrays.equals(i, k)) {
               return this.hollow
                  == set.stream()
                     .noneMatch(
                        blockPos -> blockPos.getX() > blockBox.getMinX()
                           && blockPos.getX() < blockBox.getMaxX()
                           && blockPos.getY() > blockBox.getMinY()
                           && blockPos.getY() < blockBox.getMaxY()
                           && blockPos.getZ() > blockBox.getMinZ()
                           && blockPos.getZ() < blockBox.getMaxZ()
                     );
            }
         }

         return false;
      }
   }
}
