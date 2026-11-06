package aethereal.module.misc;

import aethereal.compat.baritone.Baritone;
import aethereal.compat.baritone.BaritoneApi;
import aethereal.compat.baritone.goal.BlockGoal;
import aethereal.compat.baritone.goal.RangeGoal;
import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent074;
import aethereal.handler.HandlerComponent109;
import aethereal.render.RenderComponent183;
import aethereal.setting.ChoiceSetting;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent268;
import aethereal.util.UtilComponent270;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.border.WorldBorder;
import platform.inject.invokers.MinecraftClientInvoker;

@ModuleRegister(name = "Ancient Farmer", description = "Automatically farms ancient debris in flight mode", category = Category.Misc)
public class AncientFarmer extends Module {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Territory search mode", "Search from above", "Search from above", "Search from below");
   private final ExecutorService executorServiceField2 = Executors.newSingleThreadExecutor();
   private final UtilComponent268 utilComponent268Field3 = new UtilComponent268();
   private AncientFarmer.Phase phaseField4 = AncientFarmer.Phase.SEARCH;
   private BlockPos blockPosField5;
   private Box boxField6;

   @Override
   public void onEnable() {
      super.onEnable();
      this.performAction_1712(true);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.performAction_1712(false);
   }

   public AncientFarmer() {
      this.addSettings(this.choiceSettingField1);
   }

   @EventTarget
   public void onEventComponent074_1692(EventComponent074 eventComponent074) {
      AncientFarmer.State339 state339 = Arrays.stream(AncientFarmer.State339.values())
         .filter(state3391 -> !state3391.isCondition_1714())
         .findFirst()
         .orElse(null);
      XRay xRay = NeneClient.getInstance().getProcessors().getModules().getXRayField87_0440();
      boolean bl = state339 == AncientFarmer.State339.TNT && this.phaseField4 != AncientFarmer.Phase.SEARCH;
      if (UtilComponent252.NestedType400.computeValue_3414() == -1) {
      }

      if (state339 != null && !bl) {
         UtilComponent265.performAction_3505("For the module to work " + state339.getDescription_1716() + "!");
         this.toggle();
      } else {
         HandlerComponent109 handlerComponent109 = NeneClient.getInstance()
            .getProcessors()
            .getHandlerComponent106Field14_0673()
            .getHandlerComponent109Field8_1145();
         int i = IntStream.range(0, 9).filter(m -> minecraft.player.getInventory().getStack(m).contains(DataComponentTypes.FOOD)).findFirst().orElse(-1);
         if (!handlerComponent109.checkCondition_1163() && minecraft.player.getHungerManager().getFoodLevel() <= 17 && i != -1) {
            handlerComponent109.performAction_1162(i);
         }

         int j = IntStream.range(0, 9)
            .filter(
               m -> StreamSupport.<StatusEffectInstance>stream(
                     ((PotionContentsComponent)minecraft.player
                           .getInventory()
                           .getStack(m)
                           .getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT))
                        .getEffects()
                        .spliterator(),
                     false
                  )
                  .anyMatch(statusEffectInstance -> statusEffectInstance.getEffectType() == StatusEffects.FIRE_RESISTANCE)
            )
            .findFirst()
            .orElse(-1);
         if (!handlerComponent109.checkCondition_1163()
            && j != -1
            && (
               !minecraft.player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)
                  || minecraft.player.getStatusEffect(StatusEffects.FIRE_RESISTANCE).getDuration() <= 100
            )) {
            handlerComponent109.performAction_1162(j);
         }

         if (handlerComponent109.checkCondition_1163()) {
            BaritoneApi.computeValue_0003().getPrimaryBaritone().computeValue_0010().performAction_0017();
         } else if (!xRay.isEnabled()) {
            xRay.toggle();
         } else {
            Baritone baritone = BaritoneApi.computeValue_0003().getPrimaryBaritone();
            boolean bl1 = this.boxField6 != null && minecraft.player.getBlockPos().isWithinDistance(BlockPos.ofFloored(this.boxField6.getCenter()), 2.5);
            boolean bl2 = !minecraft.world.getEntitiesByClass(TntEntity.class, minecraft.player.getBoundingBox().expand(8.0), tntEntity -> true).isEmpty();
            this.blockPosField5 = null;
            xRay.getListField1_2012().removeIf(blockPos5 -> baritone.computeValue_0009().getSetField1_0012().contains(blockPos5));
            switch (this.phaseField4) {
               case SEARCH:
                  if (!xRay.getListField1_2012().isEmpty()) {
                     UtilComponent265.performAction_3505("We dig up the debris found along the way");
                     this.phaseField4 = AncientFarmer.Phase.MINE;
                  } else if (this.boxField6 == null) {
                     this.executorServiceField2.execute(() -> {
                        if (this.boxField6 == null && minecraft.player.age > 20) {
                           UtilComponent265.performAction_3505("Let's move on to searching for new territory.");
                           this.boxField6 = this.computeValue_1680();
                        }
                     });
                  } else if (bl1) {
                     BaritoneApi.computeValue_0003().getPrimaryBaritone().computeValue_0010().performAction_0016();
                     this.phaseField4 = AncientFarmer.Phase.TNT;
                  } else if (!baritone.computeValue_0010().checkCondition_0015()) {
                     baritone.computeValue_0008().performAction_0011(new BlockGoal(BlockPos.ofFloored(this.boxField6.getCenter())));
                  }
                  break;
               case TNT:
                  double d = minecraft.player.getBlockInteractionRange();
                  BlockPos blockPos = BlockPos.streamOutwards(minecraft.player.getBlockPos(), (int)d, (int)d, (int)d)
                     .filter(blockPos5 -> minecraft.world.getBlockState(blockPos5).isOf(Blocks.TNT))
                     .map(blockPos5 -> blockPos5.toImmutable())
                     .findFirst()
                     .orElse(null);
                  if (bl2) {
                     this.phaseField4 = AncientFarmer.Phase.RETREAT;
                  } else if (blockPos != null) {
                     this.blockPosField5 = blockPos;
                     int k = UtilComponent270.computeValue_3539(Items.FLINT_AND_STEEL, true);
                     if (k != -1) {
                        Vec3d vec3d = minecraft.player.getEyePos();
                        Vec3d vec3d1 = Vec3d.ofCenter(blockPos);
                        Vec3d vec3d2 = Arrays.stream(Direction.values())
                           .map(
                              direction -> vec3d1.add(
                                 direction.getOffsetX() * 0.4499999185117763,
                                 direction.getOffsetY() * 0.4499999185117763,
                                 direction.getOffsetZ() * 0.4499999185117763
                              )
                           )
                           .filter(vec3d5 -> vec3d.distanceTo(vec3d5) <= d)
                           .filter(
                              vec3d5 -> {
                                 BlockHitResult blockHitResult4 = minecraft.world
                                    .raycast(new RaycastContext(vec3d, vec3d5, ShapeType.COLLIDER, FluidHandling.NONE, minecraft.player));
                                 return blockHitResult4.getType() == Type.BLOCK && blockHitResult4.getBlockPos().equals(blockPos);
                              }
                           )
                           .min((vec3d5, vec3d6) -> Double.compare(vec3d.squaredDistanceTo(vec3d5), vec3d.squaredDistanceTo(vec3d6)))
                           .orElse(null);
                        if (vec3d2 != null) {
                           NeneClient.getInstance()
                              .getProcessors()
                              .computeValue_0672()
                              .performAction_1141(UtilComponent250.computeValue_3390(vec3d, vec3d2), 180.0F, 0, 1);
                           if (new UtilComponent250(minecraft.player).computeValue_3389(UtilComponent250.computeValue_3385()) < 1.0
                              && minecraft.player.age % 5 == 0) {
                              minecraft.player.getInventory().setSelectedSlot(k);
                              if (minecraft.crosshairTarget instanceof BlockHitResult blockHitResult) {
                                 BlockHitResult blockHitResult2 = blockHitResult;
                                 if (blockHitResult2.getType() == Type.BLOCK && blockHitResult2.getBlockPos().equals(blockPos)) {
                                    ((MinecraftClientInvoker)minecraft).invokeDoItemUse();
                                 }
                              }
                           }
                        }
                     }
                  } else {
                     Vec3d vec3d3 = minecraft.player.getEyePos();
                     BlockPos blockPos1 = BlockPos.streamOutwards(minecraft.player.getBlockPos(), (int)d, (int)d, (int)d)
                        .filter(blockPos5 -> minecraft.world.getBlockState(blockPos5).isReplaceable())
                        .filter(blockPos5 -> minecraft.world.getBlockState(blockPos5.down()).isSolidBlock(minecraft.world, blockPos5.down()))
                        .filter(
                           blockPos5 -> !minecraft.player
                              .getBoundingBox()
                              .stretch(minecraft.player.getVelocity())
                              .expand(0.10000001882007822)
                              .intersects(new Box(blockPos5))
                        )
                        .filter(
                           blockPos5 -> {
                              Vec3d vec3d5 = new Vec3d(blockPos5.getX() + 0.5, blockPos5.getY(), blockPos5.getZ() + 0.5);
                              if (!(vec3d3.distanceTo(vec3d5) > d) && !(vec3d3.subtract(vec3d5).normalize().y <= 0.0)) {
                                 BlockHitResult blockHitResult4 = minecraft.world
                                    .raycast(new RaycastContext(vec3d3, vec3d5, ShapeType.COLLIDER, FluidHandling.NONE, minecraft.player));
                                 return blockHitResult4.getType() != Type.BLOCK || blockHitResult4.getBlockPos().equals(blockPos5.down());
                              } else {
                                 return false;
                              }
                           }
                        )
                        .map(blockPos5 -> blockPos5.toImmutable())
                        .min(
                           (blockPos5, blockPos6) -> Double.compare(
                              vec3d3.squaredDistanceTo(blockPos5.toCenterPos()), vec3d3.squaredDistanceTo(blockPos6.toCenterPos())
                           )
                        )
                        .orElse(null);
                     this.blockPosField5 = blockPos1;
                     int l = UtilComponent270.computeValue_3539(Items.TNT, true);
                     if (blockPos1 != null) {
                        if (l != -1) {
                           BlockPos blockPos2 = blockPos1.down();
                           NeneClient.getInstance()
                              .getProcessors()
                              .computeValue_0672()
                              .performAction_1141(
                                 UtilComponent250.computeValue_3390(vec3d3, new Vec3d(blockPos2.getX() + 0.5, blockPos2.getY() + 1, blockPos2.getZ() + 0.5)),
                                 180.0F,
                                 0,
                                 1
                              );
                           if (new UtilComponent250(minecraft.player).computeValue_3389(UtilComponent250.computeValue_3385()) < 1.0
                              && minecraft.player.age % 5 == 0) {
                              if (minecraft.player.getInventory().getSelectedSlot() != l) {
                                 minecraft.player.getInventory().setSelectedSlot(l);
                              }

                              if (minecraft.crosshairTarget instanceof BlockHitResult blockHitResult1) {
                                 BlockHitResult blockHitResult3 = blockHitResult1;
                                 if (blockHitResult3.getType() == Type.BLOCK
                                    && blockHitResult3.getBlockPos().equals(blockPos2)
                                    && blockHitResult3.getSide() == Direction.UP) {
                                    ((MinecraftClientInvoker)minecraft).invokeDoItemUse();
                                 }
                              }
                           }
                        }
                     } else if (!baritone.computeValue_0010().checkCondition_0015()) {
                        BlockPos blockPos3 = minecraft.player.getBlockPos();
                        BlockPos blockPos4 = BlockPos.streamOutwards(blockPos3, 16, 8, 16)
                           .filter(blockPos6 -> !blockPos6.equals(blockPos3))
                           .filter(blockPos5 -> minecraft.world.getBlockState(blockPos5.down()).isSolidBlock(minecraft.world, blockPos5.down()))
                           .filter(
                              blockPos5 -> minecraft.world.getBlockState(blockPos5).isReplaceable()
                                 && minecraft.world.getBlockState(blockPos5.up()).isReplaceable()
                           )
                           .filter(
                              blockPos5 -> minecraft.world.getBlockState(blockPos5).getFluidState().isEmpty()
                                 && minecraft.world.getBlockState(blockPos5.up()).getFluidState().isEmpty()
                           )
                           .map(blockPos5 -> blockPos5.toImmutable())
                           .min((blockPos6, blockPos7) -> Double.compare(blockPos3.getSquaredDistance(blockPos6), blockPos3.getSquaredDistance(blockPos7)))
                           .orElse(null);
                        if (blockPos4 != null) {
                           baritone.computeValue_0008().performAction_0011(new BlockGoal(blockPos4));
                        } else {
                           this.phaseField4 = AncientFarmer.Phase.SEARCH;
                        }
                     }
                  }
                  break;
               case RETREAT:
                  List list = minecraft.world.getEntitiesByClass(TntEntity.class, minecraft.player.getBoundingBox().expand(32.0), tntEntity -> true);
                  if (list.isEmpty()) {
                     UtilComponent265.performAction_3505("Waiting for the wreckage and starting to dig up");
                     baritone.computeValue_0010().performAction_0016();
                     this.boxField6 = null;
                     this.phaseField4 = AncientFarmer.Phase.MINE;
                     this.utilComponent268Field3.performAction_3524();
                  } else if (!baritone.computeValue_0010().checkCondition_0015()) {
                     baritone.computeValue_0008()
                        .performAction_0011(new RangeGoal(20.0, list.stream().map(tntEntity -> tntEntity.getBlockPos()).toArray(BlockPos[]::new)));
                  }
                  break;
               case MINE:
                  if (this.utilComponent268Field3.checkCondition_3522(1000L)) {
                     if (!xRay.getListField1_2012().isEmpty()) {
                        if (!baritone.computeValue_0009().checkCondition_0013()) {
                           baritone.computeValue_0009().performAction_0014(Items.ANCIENT_DEBRIS, xRay.getListField1_2012());
                           NeneClient.getInstance()
                              .getBackend()
                              .performAction_0599(
                                 false,
                                 "telegram",
                                 "message",
                                 "⛏️ AncientFarmer - Ancient debris found!\n\n\ud83d\udccd Mining positions: %s\n".formatted(xRay.getListField1_2012().size())
                              );
                        }
                     } else if (!baritone.computeValue_0009().checkCondition_0013()) {
                        this.phaseField4 = AncientFarmer.Phase.SEARCH;
                     }
                  }
            }
         }
      }
   }

   @EventTarget
   public void onDrawEvent_1691(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1002() && this.blockPosField5 != null) {
         drawEvent.getRenderComponent185Field2_0999()
            .performAction_2594(drawEvent.getStack2_1004(), new Box(this.blockPosField5), RenderComponent183.computeValue_2558(230, 90, 70, 150), 1.0F);
      }
   }

   private Box computeValue_1680() {
      int i = (int)((Math.sqrt(minecraft.world.getChunkManager().getLoadedChunkCount()) - 1.0) / 2.0) * 16;
      BlockPos blockPos1 = minecraft.player.getBlockPos();
      WorldBorder worldBorder = minecraft.world.getWorldBorder();
      int j = this.choiceSettingField1.checkCondition_2850("Search from above") ? 90 : 20;
      int k = this.choiceSettingField1.checkCondition_2850("Search from above") ? 120 : 60;
      Box box = null;
      double d = -1.0;
      double e = 0.0;
      Mutable mutable = new Mutable();

      for (int l = blockPos1.getX() - i; l <= blockPos1.getX() + i; l += 8) {
         for (int m = blockPos1.getZ() - i; m <= blockPos1.getZ() + i; m += 8) {
            if (minecraft.world.getChunkManager().isChunkLoaded(l >> 4, m >> 4) && worldBorder.contains(l - 20, m - 20) && worldBorder.contains(l + 20, m + 20)
               )
             {
               for (int n = j; n <= k; n += 8) {
                  int o = 0;
                  int p = 0;
                  boolean bl = false;

                  for (byte b = -20; b <= 20 && !bl; b += 4) {
                     for (byte c = -20; c <= 20 && !bl; c += 4) {
                        for (byte f = -20; f <= 20; f += 4) {
                           mutable.set(l + b, n + c, m + f);
                           if (minecraft.world.getBiome(mutable).matchesKey(BiomeKeys.BASALT_DELTAS)
                              || minecraft.world.getBiome(mutable).matchesKey(BiomeKeys.WARPED_FOREST)) {
                              bl = true;
                              break;
                           }

                           p++;
                           if (!minecraft.world.getBlockState(mutable).isAir() && minecraft.world.getBlockState(mutable).getFluidState().isEmpty()) {
                              o++;
                           }
                        }
                     }
                  }

                  if (!bl && p > 0) {
                     double g = (double)o / p - blockPos1.getSquaredDistance(l, n, m) * 9.99999555911002E-10;
                     BlockPos blockPos;
                     if (g > d
                        && (
                              blockPos = BlockPos.streamOutwards(new BlockPos(l, n, m), 20, 20, 20)
                                 .filter(
                                    blockPos2 -> !minecraft.world.getBlockState(blockPos2).isAir()
                                       && minecraft.world.getBlockState(blockPos2).getFluidState().isEmpty()
                                 )
                                 .map(blockPos2 -> blockPos2.toImmutable())
                                 .findFirst()
                                 .orElse(null)
                           )
                           != null) {
                        d = g;
                        e = (double)o / p;
                        box = new Box(
                           blockPos.getX() - 20, blockPos.getY() - 20, blockPos.getZ() - 20, blockPos.getX() + 20, blockPos.getY() + 20, blockPos.getZ() + 20
                        );
                     }
                  }
               }
            }
         }
      }

      if (box != null) {
         long q = Math.round(e * 100.0);
         Math.round(Math.sqrt(blockPos1.getSquaredDistance(box.getCenter())));
         UtilComponent265.performAction_3505("Success: &c" + q + "%&7, before it &c" + q + "&7 blocks");
      }

      return box;
   }

   public void performAction_1712(boolean bl) {
      BaritoneApi.computeValue_0003().getPrimaryBaritone().computeValue_0010().performAction_0016();
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField1.value = true;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField2.value = true;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField5.value = bl;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField6.value = 60.0F;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField7.value = 1.0;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField8.value = 1.0;
      this.boxField6 = null;
      this.phaseField4 = AncientFarmer.Phase.SEARCH;
   }

   enum Phase {
      SEARCH,
      TNT,
      RETREAT,
      MINE;
   }

   enum State339 {
      FLY("you need to enable flight mode (/fly)", () -> MinecraftAccess.minecraft.player.getAbilities().allowFlying),
      NETHER("must be in Nether", () -> MinecraftAccess.minecraft.world.getRegistryKey() == World.NETHER),
      FOOD("there must be food in the hotbar", itemStack -> itemStack.contains(DataComponentTypes.FOOD)),
      TNT("there must be dynamite in the hotbar", itemStack -> itemStack.isOf(Items.TNT)),
      FLINT_AND_STEEL("there should be flint in the hotbar", itemStack -> itemStack.isOf(Items.FLINT_AND_STEEL)),
      PICKAXE(
         "The hotbar must have a pickaxe with durability greater than 5%",
         itemStack -> Registries.ITEM.getId(itemStack.getItem()).getPath().endsWith("pickaxe")
            && itemStack.getMaxDamage() - itemStack.getDamage() > itemStack.getMaxDamage() * 0.050000008964116646
      ),
      FIRE_RESISTANCE(
         "The hotbar must be fire resistant",
         itemStack -> StreamSupport.<StatusEffectInstance>stream(
               ((PotionContentsComponent)itemStack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT))
                  .getEffects()
                  .spliterator(),
               false
            )
            .anyMatch(statusEffectInstance -> statusEffectInstance.getEffectType() == StatusEffects.FIRE_RESISTANCE)
      );

      private final BooleanSupplier condition;
      private final String description;

      public BooleanSupplier getCondition_1715() {
         return this.condition;
      }

      public String getDescription_1716() {
         return this.description;
      }

      State339(String string1, BooleanSupplier booleanSupplier) {
         this.description = string1;
         this.condition = booleanSupplier;
      }

      State339(String string1, Predicate<ItemStack> predicate) {
         this(string1, () -> IntStream.range(0, 9).anyMatch(j -> predicate.test(MinecraftAccess.minecraft.player.getInventory().getStack(j))));
      }

      public boolean isCondition_1714() {
         return this.condition.getAsBoolean();
      }
   }
}
