package aethereal.module.misc;

import aethereal.compat.baritone.BaritoneApi;
import aethereal.compat.baritone.goal.BlockGoal;
import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent071;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent091;
import aethereal.event.PacketEvent;
import aethereal.module.render.WardenESP;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent268;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent279;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@ModuleRegister(name = "Auto Warden", description = "Automates Varden farming on Anarchy", category = Category.Misc)
public class AutoWarden extends Module {
   private int integerField1;
   private boolean flagField2;
   private Box boxField3;
   private BlockPos blockPosField4;
   private String stringField5;
   private final List<Integer> listField6 = new ArrayList<>();
   private final Map<BlockPos, Integer> mapField7 = new HashMap<>();
   private final Map<BlockPos, Integer> mapField8 = new HashMap<>();
   private final BooleanSetting booleanSettingField9 = new BooleanSetting("Use speed", false);
   private final BooleanSetting booleanSettingField10 = new BooleanSetting("Report offenders", false);
   private final ChoiceSetting choiceSettingField11 = new ChoiceSetting("Loot priorities", "Average", "Short", "Average", "High");
   private int integerField12 = 1;
   private final UtilComponent268 utilComponent268Field13 = new UtilComponent268();
   private AutoWarden.State340 state340Field14 = AutoWarden.State340.SAVE;

   public List<Integer> getListField6_1742() {
      return this.listField6;
   }

   public AutoWarden() {
      this.addSettings(this.booleanSettingField9, this.booleanSettingField10, this.choiceSettingField11);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      int i = UtilComponent252.NestedType400.computeValue_3414();
      if (i >= 0) {
         this.listField6.remove(Integer.valueOf(i));
         this.listField6.add(0, i);
      }

      this.integerField12 = 1;
      this.state340Field14 = AutoWarden.State340.COLLECTING;
      this.mapField8.clear();
      if (!NeneClient.getInstance().getProcessors().getModules().getWardenESPField41_0501().isEnabled()) {
         NeneClient.getInstance().getProcessors().getModules().getWardenESPField41_0501().toggle();
      }

      UtilComponent265.performAction_3505("Shift + Space - quickly turn off the function");
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField4.value = true;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField9.value = 256;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField6.value = 75.0F;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField1.value = true;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField7.value = 1.0;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField8.value = 1.0;
      this.performAction_1775(true);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField2.value = false;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField3.value = false;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField4.value = false;
      BaritoneApi.getBaritoneSettingsField2_0001().settingValueField9.value = 3;
      this.performAction_1775(false);
   }

   private void performAction_1775(boolean bl) {
      List list = BaritoneApi.getBaritoneSettingsField2_0001().settingValueField10.value;

      for (Block block : Registries.BLOCK) {
         if (block.getDefaultState().isIn(BlockTags.CANDLES)) {
            if (!bl) {
               list.remove(block);
            } else if (!list.contains(block)) {
               list.add(block);
            }
         }
      }
   }

   @EventTarget
   public void onEventComponent074_1738(EventComponent074 eventComponent074) {
      if (minecraft.currentScreen instanceof DeathScreen && minecraft.player.deathTime >= 5) {
         minecraft.player.requestRespawn();
      }

      if (minecraft.player != null && minecraft.world != null) {
         if (minecraft.options.sneakKey.isPressed() && minecraft.options.jumpKey.isPressed()) {
            this.toggle();
         }

         for (WardenEntity wardenEntity : minecraft.world
            .getEntitiesByClass(WardenEntity.class, minecraft.player.getBoundingBox().expand(256.0), wardenEntity2 -> true)) {
            if (wardenEntity instanceof WardenEntity wardenEntity1) {
               this.mapField8.put(wardenEntity1.getBlockPos(), minecraft.player.age + 100);
            }
         }

         this.mapField8.values().removeIf(integer -> minecraft.player.age > integer);
         if (this.stringField5 != null) {
            if (minecraft.player.age >= 20 && minecraft.player.age < 30) {
               minecraft.player.networkHandler.sendChatMessage("/report " + this.stringField5 + "cheat");
               this.stringField5 = null;
            }
         } else if (minecraft.player.age < 5) {
            this.integerField1 = 0;
            this.mapField7.clear();
         } else if (UtilComponent252.NestedType400.computeValue_3414() < 0) {
            if (minecraft.player.age % 100 == 0 && this.computeValue_1732() >= 0 && minecraft.player.age > 300) {
               minecraft.player.networkHandler.sendChatCommand("an" + this.computeValue_1732());
            }

            this.state340Field14 = AutoWarden.State340.SAVE;
         } else {
            if (minecraft.player.age % 100 == 0 && this.checkCondition_1783() && (this.boxField3 == null || !this.checkCondition_1784())) {
               this.performAction_1719();
            }

            if (minecraft.player.hasStatusEffect(StatusEffects.GLOWING) && this.checkCondition_1723(32.0)) {
               this.performAction_1770(false);
            } else if (minecraft.player.hasStatusEffect(StatusEffects.GLOWING) && this.checkCondition_1723(32.0)) {
               this.performAction_1770(false);
            } else {
               switch (this.state340Field14) {
                  case SAVE:
                     this.performAction_1786();
                     break;
                  case TAKE:
                     this.performAction_1776();
                     break;
                  case COLLECTING:
                     this.performAction_1790();
                     break;
                  case ESCAPE:
                     this.performAction_1717();
               }

               if (this.checkCondition_1746() && minecraft.player.age % 15 == 0) {
                  this.performAction_1718();
               }
            }
         }
      }
   }

   @EventTarget
   public void onPacketEvent_1739(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013()) {
         GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packetEvent.getPacket_1015();
         if (gameMessageS2CPacket instanceof GameMessageS2CPacket gameMessageS2CPacket1) {
            if (!gameMessageS2CPacket1.content().getString().contains("Let's remember. ")) {
               return;
            }

            this.flagField2 = true;
            String string = gameMessageS2CPacket1.content().getString();
            if (this.booleanSettingField10.getCurrentValue() && minecraft.player != null && string.contains("Killed You")) {
               StringBuilder stringBuilder = new StringBuilder();

               for (StatusEffectInstance statusEffectInstance : minecraft.player.getStatusEffects()) {
                  stringBuilder.append(((StatusEffect)statusEffectInstance.getEffectType().value()).getName().getString()).append("");
               }

               UtilComponent265.performAction_3505("Effects on death: " + (stringBuilder.isEmpty() ? "No" : stringBuilder.toString().trim()));
               if (!minecraft.player.hasStatusEffect(StatusEffects.GLOWING) && !this.checkCondition_1747(2.0)) {
                  this.stringField5 = string.split("killed you")[1].split(",")[0].trim();
               }
            }
         }
      }
   }

   @EventTarget
   public void onEventComponent071_1737(EventComponent071 eventComponent071) {
      String string = eventComponent071.getSound_0970().getId().getPath();
      if (minecraft.player != null && (string.contains("warden.roar") || string.contains("warden.angry") || string.contains("warden.sonic"))) {
         this.integerField1 = minecraft.player.age + 100;
      }
   }

   @EventTarget
   public void onEventComponent091_1740(EventComponent091 eventComponent091) {
      if (minecraft.player != null) {
         if (!minecraft.player.isOnGround() && !minecraft.player.isClimbing()) {
            eventComponent091.setSneak_1061(false);
         }

         if (this.checkCondition_1746() && minecraft.player.getMainHandStack().isEmpty() && !this.checkCondition_1747(3.0)) {
            int i = minecraft.player.age % 10 <= UtilComponent279.computeValue_3582(3.0F, 8.0F) ? -1 : 1;
            eventComponent091.setForward_1065(i);
            eventComponent091.setStrafe_1060(i);
         }
      }
   }

   private boolean checkCondition_1746() {
      return !minecraft.world.getBlockState(minecraft.player.getBlockPos()).isIn(BlockTags.CANDLES)
            && !minecraft.world.getBlockState(minecraft.player.getBlockPos().down()).isIn(BlockTags.CANDLES)
         ? !this.checkCondition_1782()
            && this.state340Field14 == AutoWarden.State340.COLLECTING
            && this.checkCondition_1783()
            && minecraft.currentScreen == null
            && !this.checkCondition_1785()
            && !NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getHandlerComponent109Field8_1145().checkCondition_1163()
            && this.checkCondition_1780()
         : true;
   }

   private boolean checkCondition_1780() {
      Box box = minecraft.player.getBoundingBox().expand(0.05000000009506496, 0.0, 0.05000000009506496);

      for (BlockPos blockPos : BlockPos.iterate(BlockPos.ofFloored(box.minX, box.minY, box.minZ), BlockPos.ofFloored(box.maxX, box.maxY, box.maxZ))) {
         if (!minecraft.world.getBlockState(blockPos).isAir()) {
            return true;
         }
      }

      return false;
   }

   private boolean checkCondition_1747(double d) {
      for (BlockPos blockPos : NeneClient.getInstance().getProcessors().getModules().getWardenESPField41_0501().computeValue_2321()) {
         if (minecraft.player.squaredDistanceTo(Vec3d.ofCenter(blockPos)) <= d * d) {
            return true;
         }
      }

      return false;
   }

   private int computeValue_1732() {
      return this.listField6.isEmpty() ? -1 : this.listField6.getFirst();
   }

   private boolean checkCondition_1777() {
      return this.computeValue_1732() >= 0 && this.computeValue_1732() == UtilComponent252.NestedType400.computeValue_3414();
   }

   private void performAction_1786() {
      if (this.computeValue_1732() >= 0
         && UtilComponent252.NestedType400.computeValue_3414() != this.computeValue_1732()
         && !UtilComponent252.checkCondition_3401()
         && minecraft.player.age % 5 == 0
         && minecraft.player.age > 5) {
         minecraft.player.networkHandler.sendChatCommand("an" + this.computeValue_1732());
      }

      if (this.checkCondition_1777()) {
         this.performAction_1722();
         this.performAction_1766(this.checkCondition_1791(), true, AutoWarden.State340.TAKE);
      }
   }

   private void performAction_1776() {
      if (this.flagField2 && this.listField6.size() > 1) {
         int i = this.integerField12 + 1;
         this.integerField12 = i;
         if (i >= this.listField6.size()) {
            this.integerField12 = 1;
         }

         this.flagField2 = false;
      }

      this.integerField1 = 0;
      this.mapField7.clear();
      if (minecraft.player.age % 20 == 0) {
         StringBuilder stringBuilder = new StringBuilder("Collecting (possibly not enough) ->");
         if (this.computeValue_1729() < 1) {
            stringBuilder.append("Potion of Invisibility, ");
         }

         if (UtilComponent270.computeValue_3538(Items.GOLDEN_CARROT) < 3) {
            stringBuilder.append("Golden Carrot, ");
         }

         if (this.booleanSettingField9.getCurrentValue() && this.computeValue_1752(this::checkCondition_1744) < 0) {
            stringBuilder.append("Potion of Speed \u200b\u200b");
         }

         if (minecraft.player.age % 200 == 0 && !stringBuilder.isEmpty() && !this.checkCondition_1788()) {
            minecraft.player.closeScreen();
         }
      }

      if (this.checkCondition_1777()) {
         this.performAction_1766(this.checkCondition_1789() || this.checkCondition_1788(), false, AutoWarden.State340.COLLECTING);
      }
   }

   private void performAction_1790() {
      if (!this.checkCondition_1781()) {
         NeneClient.getInstance().getProcessors().getModules().getAutoEatField55_0462().performAction_2137(18);
         if (NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getHandlerComponent109Field8_1145().checkCondition_1163()) {
            if (this.checkCondition_1785()) {
               this.performAction_1718();
            }
         } else if (this.listField6.size() <= 1) {
            if (minecraft.player.age % 20 == 0) {
               UtilComponent265.performAction_3505("ERROR -> .warden list is empty");
            }
         } else {
            if (this.integerField12 >= this.listField6.size()) {
               this.integerField12 = 1;
            }

            int i = this.listField6.get(this.integerField12);
            if (UtilComponent252.NestedType400.computeValue_3414() != i) {
               if (minecraft.player.age % 10 == 0 && minecraft.player.age > 10) {
                  minecraft.player.networkHandler.sendChatCommand("an" + i);
               }
            } else {
               if (minecraft.player.age > 5) {
                  this.performAction_1792();
               }
            }
         }
      }
   }

   private int computeValue_1734(int i) {
      double e = i;
      double d;
      if (this.choiceSettingField11.checkCondition_2850("Short")) {
         d = 1.5;
      } else {
         d = this.choiceSettingField11.checkCondition_2850("High") ? 0.80000014538821 : 1.0;
      }

      return (int)(e * d);
   }

   private boolean checkCondition_1781() {
      boolean bl = this.checkCondition_1782();
      if ((
            bl
               || this.computeValue_1730() > this.computeValue_1734(20)
               || minecraft.player.getHungerManager().getFoodLevel() < 8
               || this.mapField7.values().stream().filter(integer -> integer >= 2).count() >= 3L && minecraft.player.age % 30 == 0
         )
         && minecraft.player.age > 100) {
         if (bl) {
            this.flagField2 = true;
         }

         this.state340Field14 = AutoWarden.State340.ESCAPE;
         return true;
      } else if (!UtilComponent252.checkCondition_3401() && this.computeValue_1730() > this.computeValue_1734(8)) {
         this.state340Field14 = AutoWarden.State340.ESCAPE;
         return true;
      } else {
         int i = UtilComponent252.computeValue_3398();
         if (i >= 0 && i < 7 && !this.checkCondition_1723(14.0) && this.computeValue_1730() > this.computeValue_1734(7)) {
            this.state340Field14 = AutoWarden.State340.ESCAPE;
            return true;
         } else {
            return false;
         }
      }
   }

   private void performAction_1792() {
      StatusEffectInstance statusEffectInstance = minecraft.player.getStatusEffect(StatusEffects.INVISIBILITY);
      boolean bl = minecraft.player.hasStatusEffect(StatusEffects.GLOWING) || statusEffectInstance != null && statusEffectInstance.getDuration() >= 400;
      if (!bl && statusEffectInstance == null && this.computeValue_1729() < 1 && minecraft.player.age % 5 == 0 && !UtilComponent252.checkCondition_3401()) {
         this.state340Field14 = AutoWarden.State340.ESCAPE;
      } else {
         if (!bl) {
            this.performAction_1721();
         }

         if (!this.checkCondition_1783()) {
            if (minecraft.player.age % 50 == 0) {
               minecraft.player.networkHandler.sendChatCommand("home");
            }
         } else if (bl) {
            int i = this.booleanSettingField9.getCurrentValue() && minecraft.player.getStatusEffect(StatusEffects.SPEED) == null
               ? this.computeValue_1752(this::checkCondition_1744)
               : -1;
            if (i < 0) {
               this.performAction_1720();
            } else {
               NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getHandlerComponent109Field8_1145().performAction_1162(i);
            }
         }
      }
   }

   private boolean checkCondition_1782() {
      if (minecraft.player.age >= this.integerField1) {
         return false;
      }

      for (Entity entity : minecraft.world.getEntities()) {
         if (entity instanceof WardenEntity wardenEntity) {
            double d = minecraft.player.squaredDistanceTo(wardenEntity);
            if (d < 900.0 && this.checkCondition_1727(wardenEntity) && (d < 16.0 || this.checkCondition_1764(wardenEntity))) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean checkCondition_1764(WardenEntity wardenEntity) {
      return (minecraft.player.getX() - wardenEntity.getX()) * (wardenEntity.getX() - wardenEntity.lastX)
            + (minecraft.player.getZ() - wardenEntity.getZ()) * (wardenEntity.getZ() - wardenEntity.lastZ)
         > 0.010000003841705648;
   }

   private boolean checkCondition_1727(WardenEntity wardenEntity) {
      double d = Math.toDegrees(Math.atan2(-(minecraft.player.getX() - wardenEntity.getX()), minecraft.player.getZ() - wardenEntity.getZ()));
      return Math.abs(((wardenEntity.getBodyYaw() - d) % 360.0 + 540.0) % 360.0 - 180.0) < 10.0;
   }

   private void performAction_1717() {
      if (this.checkCondition_1777()) {
         this.state340Field14 = AutoWarden.State340.SAVE;
      } else if (this.checkCondition_1782() && UtilComponent252.checkCondition_3401()) {
         this.performAction_1770(true);
      } else {
         BlockPos blockPos = this.computeValue_1773();
         if (!(minecraft.currentScreen instanceof GenericContainerScreen)
            && (
               blockPos == null
                  || NeneClient.getInstance().getProcessors().getModules().getWardenESPField41_0501().computeValue_2325(blockPos) >= 0L
                  || !(minecraft.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(blockPos)) <= 16.0)
            )) {
            if (!UtilComponent252.checkCondition_3401()) {
               this.state340Field14 = AutoWarden.State340.SAVE;
            } else if (this.computeValue_1730() < 23 && !this.checkCondition_1723(2.0) && UtilComponent252.computeValue_3398() > 16 && blockPos != null) {
               this.performAction_1720();
            } else {
               this.performAction_1770(true);
            }
         } else {
            this.performAction_1720();
         }
      }
   }

   private void performAction_1770(boolean bl) {
      this.checkCondition_1787();
      this.performAction_1722();
      BlockPos blockPos = null;
      double d = -1.0;
      int i = minecraft.player.getBlockPos().getY();

      for (byte b = 0; b < 360; b += 30) {
         int j = this.computeValue_1748((int)(minecraft.player.getX() + Math.cos(Math.toRadians(b)) * 25.0));
         int k = this.computeValue_1774((int)(minecraft.player.getZ() + Math.sin(Math.toRadians(b)) * 25.0));
         double e = this.computeValue_1749(j, k, bl);
         if (e > d) {
            d = e;
            blockPos = new BlockPos(j, i, k);
         }
      }

      this.performAction_1757(blockPos);
   }

   private void performAction_1757(BlockPos blockPos) {
      if (blockPos != null && (minecraft.player.age % 10 == 0 || !this.checkCondition_1785() && minecraft.player.age % 5 == 0)) {
         BaritoneApi.computeValue_0003()
            .getPrimaryBaritone()
            .computeValue_0008()
            .performAction_0011(new BlockGoal(new BlockPos(this.computeValue_1748(blockPos.getX()), blockPos.getY(), this.computeValue_1774(blockPos.getZ()))));
      }
   }

   private void performAction_1718() {
      BaritoneApi.computeValue_0003().getPrimaryBaritone().computeValue_0010().performAction_0016();
   }

   private double computeValue_1749(int i, int j, boolean bl) {
      double d = 1.7976922776554316E308;

      for (Entity entity : minecraft.world.getEntities()) {
         if (entity != minecraft.player && (entity instanceof PlayerEntity || bl && entity instanceof WardenEntity)) {
            d = Math.min(d, Math.hypot(entity.getX() - i, entity.getZ() - j));
         }
      }

      return d;
   }

   private int computeValue_1730() {
      int i = 0;

      for (ItemStack itemStack : minecraft.player.getInventory().getMainStacks()) {
         if (!itemStack.isEmpty()) {
            i++;
         }
      }

      return i;
   }

   private boolean checkCondition_1783() {
      return minecraft.world.getRegistryKey().getValue().toString().equals("minecraft:overworld")
         && minecraft.player.getX() <= -1921.0
         && minecraft.player.getX() >= -2070.0
         && minecraft.player.getZ() <= -1929.0
         && minecraft.player.getZ() >= -2076.0;
   }

   private void performAction_1719() {
      this.boxField3 = new Box(-2070.0, minecraft.player.getBlockPos().getY(), -2076.0, -1921.0, minecraft.player.getBlockPos().getY(), -1929.0);
   }

   private boolean checkCondition_1784() {
      return this.boxField3 != null
         && minecraft.player.getX() >= this.boxField3.minX
         && minecraft.player.getX() <= this.boxField3.maxX
         && minecraft.player.getZ() >= this.boxField3.minZ
         && minecraft.player.getZ() <= this.boxField3.maxZ;
   }

   private int computeValue_1748(int i) {
      return this.boxField3 == null ? i : (int)Math.max(this.boxField3.minX + 10.0, Math.min(this.boxField3.maxX - 10.0, i));
   }

   private int computeValue_1774(int i) {
      return this.boxField3 == null ? i : (int)Math.max(this.boxField3.minZ + 10.0, Math.min(this.boxField3.maxZ - 10.0, i));
   }

   private void performAction_1720() {
      if (minecraft.currentScreen instanceof GenericContainerScreen genericContainerScreen) {
         this.performAction_1761(genericContainerScreen);
      } else {
         BlockPos blockPos = this.computeValue_1773();
         if (blockPos == null) {
            blockPos = this.computeValue_1733();
         }

         boolean bl = blockPos != null
            && this.blockPosField4 != null
            && !blockPos.equals(this.blockPosField4)
            && NeneClient.getInstance().getProcessors().getModules().getWardenESPField41_0501().computeValue_2325(this.blockPosField4) > 25000L;
         if (!bl) {
            this.utilComponent268Field13.performAction_3524();
         }

         if (!bl || this.utilComponent268Field13.checkCondition_3522(1000L)) {
            this.blockPosField4 = blockPos;
         }

         BlockPos blockPos1 = this.blockPosField4;
         if (blockPos1 == null && minecraft.player.age % 40 == 0) {
            this.state340Field14 = AutoWarden.State340.ESCAPE;
            this.flagField2 = true;
         }

         long l = NeneClient.getInstance().getProcessors().getModules().getWardenESPField41_0501().computeValue_2325(blockPos1);
         if (blockPos1 != null && l > 1000L && this.checkCondition_1758(blockPos1, 7.0)) {
            BlockPos blockPos2 = this.computeValue_1769(blockPos1);
            if (blockPos2 != null) {
               if (minecraft.player.squaredDistanceTo(Vec3d.ofCenter(blockPos2)) > 2.0) {
                  this.performAction_1757(blockPos2);
               } else {
                  this.performAction_1718();
               }
            }
         } else if (l > 6000L) {
            this.performAction_1757(this.computeValue_1731(blockPos1));
         } else {
            double d = minecraft.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(blockPos1));
            if (d <= 20.0) {
               if (this.mapField7.getOrDefault(blockPos1, 0) < (l >= 0L ? 1 : 3)) {
                  if (this.checkCondition_1759(blockPos1, l >= 0L ? 6 : 1)) {
                     this.mapField7.merge(blockPos1, 1, (integer, integer1) -> Integer.sum(integer, integer1));
                  }
               }
            } else {
               if (d > 10.0) {
                  this.performAction_1722();
               }

               this.performAction_1757(this.computeValue_1769(blockPos1));
            }
         }
      }
   }

   private BlockPos computeValue_1731(BlockPos blockPos) {
      double d = minecraft.player.age / 40 * 2.4000011930854526;
      return new BlockPos(
         this.computeValue_1748(blockPos.getX() + (int)(Math.cos(d) * 10.0)),
         blockPos.getY(),
         this.computeValue_1774(blockPos.getZ() + (int)(Math.sin(d) * 10.0))
      );
   }

   private BlockPos computeValue_1733() {
      WardenESP wardenESP = NeneClient.getInstance().getProcessors().getModules().getWardenESPField41_0501();
      BlockPos blockPos = null;
      long l = 45000L;

      for (BlockPos blockPos1 : wardenESP.computeValue_2321()) {
         long m = wardenESP.computeValue_2325(blockPos1);
         if (m >= 0L && m < l && this.checkCondition_1778(blockPos1) && !this.checkCondition_1772(blockPos1) && !this.checkCondition_1745(blockPos1)) {
            l = m;
            blockPos = blockPos1;
         }
      }

      return blockPos;
   }

   private BlockPos computeValue_1773() {
      WardenESP wardenESP = NeneClient.getInstance().getProcessors().getModules().getWardenESPField41_0501();
      BlockPos blockPos = null;
      byte b = 99;
      double d = 1.7976922776554316E308;

      for (BlockPos blockPos1 : wardenESP.computeValue_2321()) {
         if (this.checkCondition_1778(blockPos1) && !this.checkCondition_1745(blockPos1)) {
            double e = minecraft.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(blockPos1));
            long l = wardenESP.computeValue_2325(blockPos1);
            if ((!this.checkCondition_1772(blockPos1) || l < 0L && e <= 16.0) && (l >= 0L || this.mapField7.getOrDefault(blockPos1, 0) < 3)) {
               byte c = -1;
               if (l < 0L && e <= 25.0) {
                  c = 0;
               } else if (l >= 0L && l <= 5000L && e <= 144.0) {
                  c = 1;
               } else if (l < 0L && e <= 144.0) {
                  c = 2;
               } else if (l >= 0L && l <= 15000L && e <= 625.0) {
                  c = 3;
               } else if (l < 0L) {
                  c = 4;
               }

               if (c >= 0) {
                  double f = Vec3d.ofCenter(blockPos1).y - minecraft.player.getEyeY();
                  double g = blockPos1.getX() + 0.5 - minecraft.player.getX();
                  double h = blockPos1.getZ() + 0.5 - minecraft.player.getZ();
                  double i = g * g + h * h + (f > 0.0 ? 2 : 1) * f * f;
                  if (c < b || c == b && i < d) {
                     b = c;
                     d = i;
                     blockPos = blockPos1;
                  }
               }
            }
         }
      }

      return blockPos;
   }

   private boolean checkCondition_1723(double d) {
      for (Entity entity : minecraft.world.getEntities()) {
         if (entity instanceof PlayerEntity playerEntity && playerEntity != minecraft.player && minecraft.player.squaredDistanceTo(playerEntity) < d * d) {
            return true;
         }
      }

      return false;
   }

   private void performAction_1761(GenericContainerScreen genericContainerScreen) {
      if (this.checkCondition_1785()) {
         this.performAction_1718();
      } else if (minecraft.player.age % 2 == 0) {
         Slot slot = this.computeValue_1763(genericContainerScreen, false, itemStack -> !itemStack.isEmpty() && !this.checkCondition_1768(itemStack));
         if (slot == null) {
            this.checkCondition_1787();
         } else {
            this.performAction_1762(genericContainerScreen, slot, 0, SlotActionType.QUICK_MOVE);
         }
      }
   }

   private BlockPos computeValue_1769(BlockPos blockPos) {
      for (int i = -1; i <= 1; i++) {
         for (int j = -1; j <= 1; j++) {
            if (i != 0 || j != 0) {
               BlockPos blockPos1 = blockPos.add(i, 0, j);
               if (minecraft.world.getBlockState(blockPos1).isAir()
                  && minecraft.world.getBlockState(blockPos1.up()).isAir()
                  && !minecraft.world.getBlockState(blockPos1.down()).isAir()
                  && this.checkCondition_1725(blockPos1, blockPos)) {
                  return blockPos1;
               }
            }
         }
      }

      return minecraft.world.getBlockState(blockPos.up()).isAir()
            && minecraft.world.getBlockState(blockPos.up().up()).isAir()
            && this.checkCondition_1725(blockPos.up(), blockPos)
         ? blockPos.up()
         : null;
   }

   private boolean checkCondition_1725(BlockPos blockPos, BlockPos blockPos1) {
      return this.computeValue_1760(Vec3d.ofCenter(blockPos).add(0.0, minecraft.player.getEyeHeight(minecraft.player.getPose()) - 0.5, 0.0), blockPos1) != null;
   }

   private Vec3d computeValue_1760(Vec3d vec3d, BlockPos blockPos) {
      Vec3d vec3d1 = Vec3d.ofCenter(blockPos);
      Vec3d vec3d2 = null;
      double d = Double.MAX_VALUE;

      for (double e = -0.3999999563044224; e <= 0.41000000193542635; e += 0.4000000009895358) {
         for (double f = -0.3999999563044224; f <= 0.41000000193542635; f += 0.4000000009895358) {
            for (double g = -0.3999999563044224; g <= 0.41000000193542635; g += 0.4000000009895358) {
               Vec3d vec3d3 = vec3d1.add(e, f, g);
               double h = vec3d3.squaredDistanceTo(vec3d1);
               if (h < d
                  && minecraft.world
                     .raycast(new RaycastContext(vec3d, vec3d3, ShapeType.COLLIDER, FluidHandling.NONE, minecraft.player))
                     .getBlockPos()
                     .equals(blockPos)) {
                  d = h;
                  vec3d2 = vec3d3;
               }
            }
         }
      }

      return vec3d2;
   }

   private boolean checkCondition_1772(BlockPos blockPos) {
      for (BlockPos blockPos1 : this.mapField8.keySet()) {
         if (blockPos1.getSquaredDistance(blockPos) < 25.0) {
            return true;
         }
      }

      return false;
   }

   private boolean checkCondition_1745(BlockPos blockPos) {
      for (Entity entity : minecraft.world.getEntities()) {
         if (entity instanceof PlayerEntity playerEntity
            && playerEntity != minecraft.player
            && playerEntity.getEntityPos().squaredDistanceTo(Vec3d.ofCenter(blockPos)) < 20.0
            && Stream.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)
               .anyMatch(equipmentSlot -> playerEntity.getEquippedStack(equipmentSlot).contains(DataComponentTypes.EQUIPPABLE))) {
            return true;
         }
      }

      return false;
   }

   private boolean checkCondition_1758(BlockPos blockPos, double d) {
      for (Entity entity : minecraft.world.getEntities()) {
         if (entity instanceof PlayerEntity playerEntity
            && playerEntity != minecraft.player
            && playerEntity.getEntityPos().squaredDistanceTo(Vec3d.ofCenter(blockPos)) < d * d) {
            return true;
         }
      }

      return false;
   }

   private boolean checkCondition_1778(BlockPos blockPos) {
      return this.computeValue_1769(blockPos) != null;
   }

   private void performAction_1721() {
      int i = this.computeValue_1752(this::checkCondition_1771);
      if (i >= 0 && minecraft.player.age > 20) {
         NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getHandlerComponent109Field8_1145().performAction_1162(i);
      }
   }

   private int computeValue_1752(Predicate<ItemStack> predicate) {
      for (int i = 0; i < 36; i++) {
         if (predicate.test(minecraft.player.getInventory().getStack(i))) {
            return i;
         }
      }

      return -1;
   }

   private void performAction_1766(boolean bl, boolean bl1, AutoWarden.State340 state340) {
      if (!bl) {
         if (this.checkCondition_1787()) {
            this.state340Field14 = state340;
         }
      } else if (minecraft.currentScreen instanceof GenericContainerScreen genericContainerScreen) {
         if (!bl1) {
            this.performAction_1735(genericContainerScreen);
         } else {
            this.performAction_1726(genericContainerScreen);
         }
      } else {
         this.checkCondition_1759(this.computeValue_1765(bl1), 2);
      }
   }

   private boolean checkCondition_1759(BlockPos blockPos, int i) {
      Vec3d vec3d;
      Vec3d vec3d1;
      if (blockPos != null
         && !(minecraft.currentScreen instanceof GenericContainerScreen)
         && (vec3d1 = this.computeValue_1760(vec3d = minecraft.player.getEyePos(), blockPos)) != null) {
         UtilComponent250 utilComponent250 = UtilComponent250.computeValue_3390(vec3d, vec3d1);
         float f = minecraft.player.age + minecraft.getRenderTickCounter().getTickProgress(false);
         float g = (float)((Math.sin(f * 0.31F) * 0.5 + Math.sin(f * 0.73F + 1.1F) * 0.3000000317022817 + Math.sin(f * 1.7F + 2.6F) * 0.1999999860971588) * 8.0);
         NeneClient.getInstance()
            .getProcessors()
            .computeValue_0672()
            .performAction_1141(
               new UtilComponent250(
                  utilComponent250.getYaw_3386() + g, UtilComponent279.computeValue_3577(utilComponent250.getPitch_3387() + g / 4.0F, -90.0F, 90.0F)
               ),
               120.0F,
               1,
               1
            );
         if (minecraft.player.age % i == 0 && !(UtilComponent250.computeValue_3385().computeValue_3389(utilComponent250) > 5.0)) {
            BlockHitResult blockHitResult = minecraft.world
               .raycast(new RaycastContext(vec3d, vec3d1, ShapeType.COLLIDER, FluidHandling.NONE, minecraft.player));
            if (!blockHitResult.getBlockPos().equals(blockPos)) {
               return false;
            }

            minecraft.interactionManager.interactBlock(minecraft.player, Hand.MAIN_HAND, blockHitResult);
            minecraft.player.swingHand(Hand.MAIN_HAND);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean checkCondition_1785() {
      return minecraft.player.getVelocity().horizontalLengthSquared() > 0.002500001077917312;
   }

   private void performAction_1722() {
      if (!minecraft.player.getMainHandStack().isEmpty()) {
         for (int i = 0; i < 9; i++) {
            if (minecraft.player.getInventory().getStack(i).isEmpty()) {
               minecraft.player.getInventory().setSelectedSlot(i);
               return;
            }
         }

         for (int j = 9; j < 36; j++) {
            if (minecraft.player.getInventory().getStack(j).isEmpty()) {
               if (minecraft.player.age % 10 >= 2 && this.checkCondition_1785()) {
                  this.performAction_1718();
               }

               if (minecraft.player.age % 10 == 4) {
                  NeneClient.getInstance()
                     .getProcessors()
                     .getHandlerComponent106Field14_0673()
                     .computeValue_1146()
                     .performAction_1179(minecraft.player.getInventory().getSelectedSlot(), j, 1);
                  return;
               }

               return;
            }
         }
      }
   }

   private boolean checkCondition_1787() {
      if (minecraft.currentScreen instanceof GenericContainerScreen && minecraft.player.age % 2 == 0) {
         minecraft.player.closeHandledScreen();
      }

      return !(minecraft.currentScreen instanceof GenericContainerScreen);
   }

   private void performAction_1726(GenericContainerScreen genericContainerScreen) {
      if (minecraft.player.age % 2 == 0) {
         boolean bl = false;
         boolean bl1 = false;
         int i = 0;

         for (Slot slot : ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots) {
            if (i >= 4) {
               return;
            }

            ItemStack itemStack = slot.getStack();
            if (slot.inventory == minecraft.player.getInventory()
               && !itemStack.isEmpty()
               && (!this.booleanSettingField9.getCurrentValue() || !this.checkCondition_1744(itemStack))) {
               if (!bl && this.checkCondition_1771(itemStack)) {
                  bl = true;
               } else if (!bl1 && itemStack.isOf(Items.GOLDEN_CARROT)) {
                  bl1 = true;
               } else {
                  this.performAction_1762(genericContainerScreen, slot, 0, SlotActionType.QUICK_MOVE);
                  i++;
               }
            }
         }
      }
   }

   private void performAction_1735(GenericContainerScreen genericContainerScreen) {
      if (minecraft.player.age % 2 == 0) {
         ItemStack itemStack = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getCursorStack();
         Predicate predicate = itemStack2 -> itemStack2.isEmpty() || ItemStack.areItemsAndComponentsEqual(itemStack2, itemStack);
         if (!itemStack.isEmpty()) {
            if (!this.checkCondition_1741(itemStack)) {
               this.performAction_1762(genericContainerScreen, this.computeValue_1763(genericContainerScreen, false, predicate), 0, SlotActionType.PICKUP);
            } else {
               this.performAction_1762(genericContainerScreen, this.computeValue_1763(genericContainerScreen, true, predicate), 1, SlotActionType.PICKUP);
            }
         } else {
            this.performAction_1762(
               genericContainerScreen, this.computeValue_1763(genericContainerScreen, false, this::checkCondition_1741), 0, SlotActionType.PICKUP
            );
         }
      }
   }

   private Slot computeValue_1763(GenericContainerScreen genericContainerScreen, boolean bl, Predicate<ItemStack> predicate) {
      for (Slot slot : ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots) {
         if (slot.inventory == minecraft.player.getInventory() == bl && predicate.test(slot.getStack())) {
            return slot;
         }
      }

      return null;
   }

   private void performAction_1762(GenericContainerScreen genericContainerScreen, Slot slot, int i, SlotActionType slotActionType) {
      if (slot != null) {
         minecraft.interactionManager
            .clickSlot(((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId, slot.id, i, slotActionType, minecraft.player);
      }
   }

   private boolean checkCondition_1788() {
      GenericContainerScreen genericContainerScreen = (GenericContainerScreen)minecraft.currentScreen;
      return genericContainerScreen instanceof GenericContainerScreen genericContainerScreen1
         && !((GenericContainerScreenHandler)genericContainerScreen1.getScreenHandler()).getCursorStack().isEmpty();
   }

   private boolean checkCondition_1741(ItemStack itemStack) {
      return !itemStack.isEmpty()
         && (
            this.checkCondition_1771(itemStack) && this.computeValue_1729() < 1
               || itemStack.isOf(Items.GOLDEN_CARROT) && UtilComponent270.computeValue_3538(Items.GOLDEN_CARROT) < 3
               || this.booleanSettingField9.getCurrentValue() && this.checkCondition_1744(itemStack) && this.computeValue_1752(this::checkCondition_1744) < 0
         );
   }

   private boolean checkCondition_1789() {
      return this.computeValue_1729() < 1
         || UtilComponent270.computeValue_3538(Items.GOLDEN_CARROT) < 3
         || this.booleanSettingField9.getCurrentValue() && this.computeValue_1752(this::checkCondition_1744) < 0;
   }

   private int computeValue_1729() {
      int i = 0;

      for (ItemStack itemStack : minecraft.player.getInventory().getMainStacks()) {
         if (this.checkCondition_1771(itemStack)) {
            i++;
         }
      }

      return i;
   }

   private boolean checkCondition_1768(ItemStack itemStack) {
      return this.choiceSettingField11.checkCondition_2850("Short")
         ? false
         : this.checkCondition_1743(itemStack) || this.choiceSettingField11.checkCondition_2850("High") && this.checkCondition_1728(itemStack);
   }

   private boolean checkCondition_1728(ItemStack itemStack) {
      Item item = itemStack.getItem();
      return item instanceof ArrowItem
         || Registries.ITEM.getId(item).getPath().endsWith("axe")
         || Registries.ITEM.getId(item).getPath().endsWith("pickaxe")
         || itemStack.isOf(Items.CHORUS_FRUIT)
         || itemStack.isOf(Items.DISC_FRAGMENT_5)
         || itemStack.isOf(Items.NAUTILUS_SHELL)
         || itemStack.isOf(Items.BOOKSHELF)
         || itemStack.isOf(Items.COOKED_MUTTON)
         || itemStack.isOf(Items.SKELETON_SPAWN_EGG)
         || itemStack.isOf(Items.CREEPER_SPAWN_EGG)
         || itemStack.isOf(Items.ZOMBIE_SPAWN_EGG)
         || itemStack.isOf(Items.VINDICATOR_SPAWN_EGG)
         || itemStack.isOf(Items.PIGLIN_SPAWN_EGG)
         || itemStack.isOf(Items.FIRE_CHARGE)
         || itemStack.isOf(Items.LEATHER)
         || itemStack.isOf(Items.SHULKER_SHELL)
         || itemStack.isOf(Items.EXPERIENCE_BOTTLE)
         || itemStack.isOf(Items.WITHER_ROSE)
         || itemStack.isOf(Items.EMERALD)
         || itemStack.isOf(Items.SUGAR)
         || this.checkCondition_1755(itemStack, "potion-popper")
         || itemStack.contains(DataComponentTypes.JUKEBOX_PLAYABLE)
         || itemStack.isOf(Items.GHAST_TEAR)
         || itemStack.isOf(Items.DRAGON_BREATH)
         || itemStack.isOf(Items.VEX_SPAWN_EGG)
         || itemStack.isOf(Items.ENDERMITE_SPAWN_EGG)
         || itemStack.isOf(Items.CAT_SPAWN_EGG)
         || itemStack.isOf(Items.ENCHANTING_TABLE)
         || itemStack.isOf(Items.DIAMOND_HELMET)
         || itemStack.isOf(Items.DIAMOND_CHESTPLATE)
         || itemStack.isOf(Items.DIAMOND_LEGGINGS)
         || itemStack.isOf(Items.DIAMOND_BOOTS);
   }

   private boolean checkCondition_1743(ItemStack itemStack) {
      Item item = itemStack.getItem();
      return item instanceof ShovelItem
         || item instanceof AxeItem
         || item instanceof BannerItem
         || item instanceof SmithingTemplateItem && !itemStack.isOf(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
         || itemStack.isOf(Items.BLAZE_ROD)
         || itemStack.isOf(Items.ENCHANTED_BOOK)
         || itemStack.isOf(Items.TRIDENT)
         || itemStack.isOf(Items.NAME_TAG)
         || itemStack.isOf(Items.SCULK)
         || itemStack.isOf(Items.SCULK_SENSOR)
         || itemStack.isOf(Items.ENDER_CHEST)
         || itemStack.isOf(Items.REINFORCED_DEEPSLATE)
         || itemStack.isOf(Items.PUFFERFISH)
         || itemStack.isOf(Items.HONEY_BOTTLE)
         || itemStack.isOf(Items.FERMENTED_SPIDER_EYE)
         || itemStack.isOf(Items.ANVIL)
         || itemStack.isOf(Items.COOKED_PORKCHOP);
   }

   private boolean checkCondition_1755(ItemStack itemStack, String string) {
      NbtComponent nbtComponent = (NbtComponent)itemStack.get(DataComponentTypes.CUSTOM_DATA);
      if (nbtComponent == null) {
         return false;
      }

      boolean[] bl = new boolean[]{false};
      nbtComponent.apply(nbtCompound -> bl[0] = string.equals(nbtCompound.getCompoundOrEmpty("PublicBukkitValues").getString("minecraft:ftid", "")));
      return bl[0];
   }

   private boolean checkCondition_1771(ItemStack itemStack) {
      RegistryEntry registryEntry = (RegistryEntry)((PotionContentsComponent)itemStack.getOrDefault(
            DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT
         ))
         .potion()
         .orElse(null);
      return registryEntry != null && (registryEntry.equals(Potions.INVISIBILITY) || registryEntry.equals(Potions.LONG_INVISIBILITY));
   }

   private boolean checkCondition_1744(ItemStack itemStack) {
      if (itemStack.isOf(Items.POTION)) {
         for (StatusEffectInstance statusEffectInstance : ((PotionContentsComponent)itemStack.getOrDefault(
               DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT
            ))
            .getEffects()) {
            if (statusEffectInstance.getEffectType().equals(StatusEffects.SPEED)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private BlockPos computeValue_1765(boolean bl) {
      BlockPos blockPos = minecraft.player.getBlockPos();
      Mutable mutable = new Mutable();

      for (int i = -4; i <= 4; i++) {
         for (int j = -4; j <= 4; j++) {
            for (int k = -4; k <= 4; k++) {
               mutable.set(blockPos.getX() + i, blockPos.getY() + j, blockPos.getZ() + k);
               if (minecraft.world.getBlockState(mutable).isOf(Blocks.CHEST)
                  && this.checkCondition_1779(mutable) == bl
                  && minecraft.world.getBlockState(mutable.up()).isAir()
                  && minecraft.world
                     .raycast(
                        new RaycastContext(minecraft.player.getEyePos(), Vec3d.ofCenter(mutable), ShapeType.COLLIDER, FluidHandling.NONE, minecraft.player)
                     )
                     .getBlockPos()
                     .equals(mutable)) {
                  return mutable.toImmutable();
               }
            }
         }
      }

      return null;
   }

   private boolean checkCondition_1779(BlockPos blockPos) {
      if (minecraft.world.getBlockState(blockPos.down()).isOf(Blocks.HOPPER)) {
         return true;
      }

      BlockState blockState = minecraft.world.getBlockState(blockPos);
      if (blockState.get(Properties.CHEST_TYPE) != ChestType.SINGLE) {
         for (Direction direction : Type.HORIZONTAL) {
            BlockPos blockPos1 = blockPos.offset(direction);
            BlockState blockState1 = minecraft.world.getBlockState(blockPos1);
            if (blockState1.isOf(Blocks.CHEST)
               && blockState1.get(Properties.CHEST_TYPE) != ChestType.SINGLE
               && blockState1.get(Properties.CHEST_TYPE) != blockState.get(Properties.CHEST_TYPE)
               && blockState1.get(Properties.HORIZONTAL_FACING) == blockState.get(Properties.HORIZONTAL_FACING)
               && minecraft.world.getBlockState(blockPos1.down()).isOf(Blocks.HOPPER)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean checkCondition_1791() {
      boolean bl = false;
      boolean bl1 = false;

      for (ItemStack itemStack : minecraft.player.getInventory().getMainStacks()) {
         if (!itemStack.isEmpty() && (!this.booleanSettingField9.getCurrentValue() || !this.checkCondition_1744(itemStack))) {
            if (bl || !this.checkCondition_1771(itemStack)) {
               if (bl1 || !itemStack.isOf(Items.GOLDEN_CARROT)) {
                  return true;
               }

               bl1 = true;
            } else {
               bl = true;
            }
         }
      }

      return false;
   }

   enum State340 {
      SAVE,
      TAKE,
      COLLECTING,
      ESCAPE;
   }
}
