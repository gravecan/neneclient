package aethereal.module.misc;

import aethereal.config.ConfigComponent020;
import aethereal.config.ConfigComponent022;
import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.ContainerEvent;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent075;
import aethereal.event.PacketEvent;
import aethereal.handler.HandlerComponent108;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.KeybindSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent268;
import aethereal.util.UtilComponent270;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import platform.inject.accessors.ClickSlotC2SPacketAccessor;
import platform.inject.accessors.HandledScreenAccessor;
import platform.inject.accessors.ItemCooldownEntryAccessor;
import platform.inject.accessors.ItemCooldownManagerAccessor;
import platform.inject.accessors.ScreenHandlerSlotUpdateS2CPacketAccessor;

@ModuleRegister(name = "Server Assistant", description = "Помощник, упрощающий работу с сервером и игровыми механиками", category = Category.Misc)
public class ServerAssistant extends Module implements MinecraftAccess {
   private List<ServerAssistant.NestedType346> listField1;
   private final ChoiceSetting choiceSettingField2 = new ChoiceSetting("Целевой сервер помощи", "FunTime", "FunTime", "SpookyTime", "HolyWorld");
   private final MultiSelectSetting multiSelectSettingField3 = new MultiSelectSetting(
         "Элементы помощи", new BooleanSetting("Аукционный ассистент", true), new BooleanSetting("Сортировать по цене", true)
      )
      .withVisibility(() -> this.choiceSettingField2.checkCondition_2850("FunTime") || this.choiceSettingField2.checkCondition_2850("SpookyTime"));
   final MultiSelectSetting multiSelectSettingField4 = new MultiSelectSetting(
         "Фильтр брони по",
         new BooleanSetting("Защите", false),
         new BooleanSetting("Аншип", true),
         new BooleanSetting("Починке", true),
         new BooleanSetting("Подводной ходьбе", true)
      )
      .withVisibility(
         () -> this.multiSelectSettingField3.computeValue_2852("Аукционный ассистент").getCurrentValue()
            && (this.choiceSettingField2.checkCondition_2850("FunTime") || this.choiceSettingField2.checkCondition_2850("SpookyTime"))
      );
   final MultiSelectSetting multiSelectSettingField5 = new MultiSelectSetting(
         "Фильтр меча по",
         new BooleanSetting("Остроте", false),
         new BooleanSetting("Детекции", true),
         new BooleanSetting("Вампиризму", true),
         new BooleanSetting("Окислению", true),
         new BooleanSetting("Яду", true)
      )
      .withVisibility(
         () -> this.multiSelectSettingField3.computeValue_2852("Аукционный ассистент").getCurrentValue()
            && (this.choiceSettingField2.checkCondition_2850("FunTime") || this.choiceSettingField2.checkCondition_2850("SpookyTime"))
      );
   final MultiSelectSetting multiSelectSettingField6 = new MultiSelectSetting(
         "Фильтр кирки по",
         new BooleanSetting("Эффективности", false),
         new BooleanSetting("Удаче", true),
         new BooleanSetting("Магнит", true),
         new BooleanSetting("Починке", true)
      )
      .withVisibility(
         () -> this.multiSelectSettingField3.computeValue_2852("Аукционный ассистент").getCurrentValue()
            && (this.choiceSettingField2.checkCondition_2850("FunTime") || this.choiceSettingField2.checkCondition_2850("SpookyTime"))
      );
   private final KeybindSetting keybindSettingField7 = this.computeValue_1967("Трапка", Items.NETHERITE_SCRAP, "FunTime", "SpookyTime");
   private final KeybindSetting keybindSettingField8 = this.computeValue_1967("Снежок заморозка", Items.SNOWBALL, "FunTime", "SpookyTime");
   private final KeybindSetting keybindSettingField9 = this.computeValue_1967("Пласт", Items.DRIED_KELP, "FunTime", "SpookyTime");
   private final KeybindSetting keybindSettingField10 = this.computeValue_1967("Дезориентация", Items.ENDER_EYE, "FunTime", "SpookyTime");
   private final KeybindSetting keybindSettingField11 = this.computeValue_1967("Явная пыль", Items.SUGAR, "FunTime", "SpookyTime");
   private final KeybindSetting keybindSettingField12 = this.computeValue_1967("Заряд ветра", Items.WIND_CHARGE, "FunTime", "SpookyTime");
   private final KeybindSetting keybindSettingField13 = this.computeValue_1967("Огненный заряд", Items.FIRE_CHARGE, "FunTime", "SpookyTime");
   private final KeybindSetting keybindSettingField14 = this.computeValue_1967("Божья аура", Items.PHANTOM_MEMBRANE, "FunTime", "SpookyTime");
   private final KeybindSetting keybindSettingField15 = this.computeValue_1967("Взрывная штучка", Items.FIRE_CHARGE, "HolyWorld");
   private final KeybindSetting keybindSettingField16 = this.computeValue_1967("Взрывная палочка", Items.BLAZE_ROD, "HolyWorld");
   private final KeybindSetting keybindSettingField17 = this.computeValue_1967("Взрывная трапка", Items.PRISMARINE_SHARD, "HolyWorld");
   private final KeybindSetting keybindSettingField18 = this.computeValue_1967("Стан", Items.NETHER_STAR, "HolyWorld");
   private final KeybindSetting keybindSettingField19 = this.computeValue_1967("Ком снега", Items.SNOWBALL, "HolyWorld");
   private final BooleanSetting booleanSettingField20 = new BooleanSetting("Авто-божья аура", false)
      .withVisibility(() -> this.choiceSettingField2.checkCondition_2850("FunTime") || this.choiceSettingField2.checkCondition_2850("SpookyTime"));
   private final ServerAssistant.NestedType345 nestedType345Field21 = new ServerAssistant.NestedType345();
   private final UtilComponent268 utilComponent268Field22 = new UtilComponent268();
   private Slot slotField23 = null;
   private int[] integerArrayField24 = null;
   private int integerField25 = -1;

   public List<ServerAssistant.NestedType346> getListField1_1962() {
      return this.listField1;
   }

   public ServerAssistant() {
      this.addSettings(
         this.choiceSettingField2,
         this.multiSelectSettingField3,
         this.multiSelectSettingField4,
         this.multiSelectSettingField5,
         this.multiSelectSettingField6,
         this.keybindSettingField7,
         this.keybindSettingField8,
         this.keybindSettingField9,
         this.keybindSettingField10,
         this.keybindSettingField11,
         this.keybindSettingField13,
         this.keybindSettingField12,
         this.keybindSettingField14,
         this.keybindSettingField15,
         this.keybindSettingField16,
         this.keybindSettingField17,
         this.keybindSettingField18,
         this.keybindSettingField19,
         this.booleanSettingField20
      );
   }

   @EventTarget
   public void onEventComponent074_1959(EventComponent074 eventComponent074) {
      if (this.booleanSettingField20.getCurrentValue()
         && (UtilComponent252.NestedType400.checkCondition_3406() || UtilComponent252.NestedType403.checkCondition_3420())) {
         boolean bl = minecraft.player
            .getStatusEffects()
            .stream()
            .anyMatch(
               statusEffectInstance -> statusEffectInstance.getEffectType() == StatusEffects.WEAKNESS
                  && statusEffectInstance.getAmplifier() >= 1
                  && statusEffectInstance.getDuration() / 20.0 >= 15.0
            );
         if (UtilComponent270.computeValue_3526(Items.PHANTOM_MEMBRANE) != -1
            && bl
            && !minecraft.player.getItemCooldownManager().isCoolingDown(Items.PHANTOM_MEMBRANE.getDefaultStack())
            && this.utilComponent268Field22.checkCondition_3522(5000L)
            && minecraft.player.getAbsorptionAmount() <= 3.0F) {
            HandlerComponent108.getUSEABLE_1160().performAction_1225(Items.PHANTOM_MEMBRANE.getDefaultStack());
            this.utilComponent268Field22.performAction_3524();
         }
      }
   }

   @EventTarget
   public void onEventComponent075_1960(EventComponent075 eventComponent075) {
      ItemStack itemStack = eventComponent075.getStack_1007();
      int i = UtilComponent252.NestedType400.computeValue_3405(itemStack);
      if (i > 0 && itemStack.getCount() > 1) {
         List list = eventComponent075.getLines_1006();

         for (int j = 0; j < list.size(); j++) {
            if (((Text)list.get(j)).getString().contains("$ Ценa: ")) {
               list.add(
                  j + 1,
                  Text.literal("$")
                     .formatted(Formatting.GREEN)
                     .append(Text.literal(" Цена за штуку: ").formatted(Formatting.WHITE))
                     .append(Text.literal(String.format(Locale.US, "%,d", i)).formatted(Formatting.GREEN))
               );
               return;
            }
         }
      }
   }

   @EventTarget
   public void onContainerEvent_1958(ContainerEvent containerEvent) {
      HandledScreenAccessor handledScreenAccessor = (HandledScreenAccessor)containerEvent.getScreen_0992();
      if (handledScreenAccessor instanceof GenericContainerScreen) {
         var handledScreenAccessor1 = (HandledScreenAccessor & GenericContainerScreen)handledScreenAccessor;
         ToIntFunction toIntFunction = UtilComponent252.NestedType400.checkCondition_3406()
            ? UtilComponent252.NestedType400::computeValue_3405
            : UtilComponent252.NestedType403::computeValue_3419;
         if (containerEvent.getPhase_0988() == ContainerEvent.Phase.TITLE) {
            if (containerEvent.getTitle_0990().getString().contains("Хранилище")) {
               long l = handledScreenAccessor1.getScreenHandler()
                  .slots
                  .stream()
                  .mapToLong(slot2 -> (long)Math.max(toIntFunction.applyAsInt(slot2.getStack()), 0) * Math.max(slot2.getStack().getCount(), 1))
                  .sum();
               String string = " - "
                  + Stream.of(Map.entry(1000000000L, "ккк"), Map.entry(1000000L, "кк"), Map.entry(1000L, "к"))
                     .filter(entry -> l >= (Long)entry.getKey())
                     .map(entry -> String.format(Locale.US, "%.0f%s", (double)(l / (Long)entry.getKey()), entry.getValue()))
                     .findFirst()
                     .orElseGet(() -> String.valueOf(l));
               MutableText mutableText = Text.empty();
               containerEvent.getTitle_0990().visit((style, string2) -> {
                  mutableText.append(Text.literal(string2).setStyle(style));
                  if (string2.contains("Хранилище")) {
                     mutableText.append(Text.literal(string).setStyle(style));
                  }

                  return Optional.empty();
               }, Style.EMPTY);
               containerEvent.setTitle_0993(mutableText);
               return;
            }

            return;
         }

         if (handledScreenAccessor1.getScreenHandler().slots.size() >= 90) {
            ArrayList arrayList = new ArrayList<>(containerEvent.getSlots_0987().subList(0, containerEvent.getSlots_0987().size() - 36));
            boolean bl = arrayList.stream()
               .anyMatch(slot2 -> toIntFunction.applyAsInt(slot2.getStack()) >= 0 && this.nestedType345Field21.checkCondition_1978(slot2.getStack()));
            if (containerEvent.getPhase_0988() == ContainerEvent.Phase.POST
               && this.multiSelectSettingField3.computeValue_2852("Аукционный ассистент").getCurrentValue()) {
               if (this.slotField23 == null) {
                  Slot slot1 = null;
                  int j = Integer.MAX_VALUE;

                  for (Slot slot : arrayList) {
                     ItemStack itemStack = slot.getStack();
                     int i = toIntFunction.applyAsInt(itemStack);
                     if ((!bl || this.nestedType345Field21.checkCondition_1978(itemStack)) && i >= 0 && i < j) {
                        j = i;
                        slot1 = slot;
                     }
                  }

                  this.slotField23 = slot1;
                  return;
               }

               HandledScreenAccessor handledScreenAccessor2 = handledScreenAccessor1;
               float f = (float)((Math.sin((float)(System.currentTimeMillis() % 100000L) / 1000.0F * 10.0F) + 1.0) * 0.5);
               NeneClient.getInstance()
                  .getProcessors()
                  .getRenderComponent184Field3_0677()
                  .performAction_2574(
                     containerEvent.getContext_0991(),
                     handledScreenAccessor2.getX() + this.slotField23.x,
                     handledScreenAccessor2.getY() + this.slotField23.y,
                     16.0F,
                     16.0F,
                     RenderComponent183.computeValue_2558(0, 255, 0, (int)(25.0F + 175.0F * f))
                  );
            }
         }
      }
   }

   @EventTarget
   public void onPacketEvent_1961(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013()) {
         GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packetEvent.getPacket_1015();
         if (gameMessageS2CPacket instanceof GameMessageS2CPacket gameMessageS2CPacket1
            && UtilComponent252.NestedType400.checkCondition_3406()
            && gameMessageS2CPacket1.content().getString().equals("На этой анархии этот предмет не работает")) {
            this.integerField25 = UtilComponent252.NestedType400.computeValue_3414();
         }

         InventoryS2CPacket inventoryS2CPacket1 = (InventoryS2CPacket)packetEvent.getPacket_1015();
         if (inventoryS2CPacket1 instanceof InventoryS2CPacket inventoryS2CPacket
            && this.multiSelectSettingField3.computeValue_2852("Сортировать по цене").getCurrentValue()
            && (UtilComponent252.NestedType400.checkCondition_3406() || UtilComponent252.NestedType403.checkCondition_3420())) {
            List list = inventoryS2CPacket.contents();
            int i = list.size() > 36 ? list.size() - 36 : list.size();
            this.integerArrayField24 = null;
            if (i >= 44 && inventoryS2CPacket.syncId() != 0) {
               ToIntFunction toIntFunction = UtilComponent252.NestedType400.checkCondition_3406()
                  ? UtilComponent252.NestedType400::computeValue_3405
                  : UtilComponent252.NestedType403::computeValue_3419;
               boolean bl = list.subList(0, i)
                  .stream()
                  .anyMatch(itemStack1 -> toIntFunction.applyAsInt(itemStack1) >= 0 && this.nestedType345Field21.checkCondition_1978(itemStack1));
               int[] j = new int[i];

               for (int k = 0; k < i; k++) {
                  ItemStack itemStack = (ItemStack)list.get(k);
                  int l = !itemStack.isEmpty() && (!bl || this.nestedType345Field21.checkCondition_1978(itemStack)) ? toIntFunction.applyAsInt(itemStack) : -1;
                  j[k] = l < 0 ? Integer.MAX_VALUE : l;
               }

               Integer[] integers = IntStream.range(0, i).boxed().sorted(Comparator.comparingInt(integer -> j[integer])).toArray(Integer[]::new);
               ArrayList arrayList = new ArrayList(list);
               this.integerArrayField24 = new int[i];

               for (int n = 0; n < i; n++) {
                  this.integerArrayField24[n] = integers[n];
                  list.set(n, (ItemStack)arrayList.get(integers[n]));
               }
            }
         }

         ScreenHandlerSlotUpdateS2CPacketAccessor screenHandlerSlotUpdateS2CPacketAccessor = (ScreenHandlerSlotUpdateS2CPacketAccessor)packetEvent.getPacket_1015();
         if (screenHandlerSlotUpdateS2CPacketAccessor instanceof ScreenHandlerSlotUpdateS2CPacket) {
            ScreenHandlerSlotUpdateS2CPacketAccessor screenHandlerSlotUpdateS2CPacketAccessor1 = screenHandlerSlotUpdateS2CPacketAccessor;
            if (this.integerArrayField24 != null
               && screenHandlerSlotUpdateS2CPacketAccessor1.getSyncId() != 0
               && screenHandlerSlotUpdateS2CPacketAccessor1.getSyncId() == minecraft.player.currentScreenHandler.syncId
               && screenHandlerSlotUpdateS2CPacketAccessor1.getSlot() >= 0
               && screenHandlerSlotUpdateS2CPacketAccessor1.getSlot() < this.integerArrayField24.length) {
               for (int m = 0; m < this.integerArrayField24.length; m++) {
                  if (this.integerArrayField24[m] == screenHandlerSlotUpdateS2CPacketAccessor1.getSlot()) {
                     screenHandlerSlotUpdateS2CPacketAccessor1.setSlot(m);
                     break;
                  }
               }
            }

            this.slotField23 = null;
         }
      }

      if (packetEvent.checkCondition_1012()
         && packetEvent.getPacket_1015() instanceof ClickSlotC2SPacketAccessor clickSlotC2SPacketAccessor
         && this.integerArrayField24 != null
         && clickSlotC2SPacketAccessor.getSyncId() != 0
         && clickSlotC2SPacketAccessor.getSyncId() == minecraft.player.currentScreenHandler.syncId
         && clickSlotC2SPacketAccessor.getSlot() >= 0
         && clickSlotC2SPacketAccessor.getSlot() < this.integerArrayField24.length) {
         clickSlotC2SPacketAccessor.setSlot((short)this.integerArrayField24[clickSlotC2SPacketAccessor.getSlot()]);
      }
   }

   private KeybindSetting computeValue_1967(String string, Item item, String... strings) {
      KeybindSetting keybindSetting = new KeybindSetting(string, -1)
         .computeValue_2847(
            () -> {
               var itemCooldownManagerAccessor = (ItemCooldownManagerAccessor & ItemCooldownManager)minecraft.player.getItemCooldownManager();
               if (((ItemCooldownManager)itemCooldownManagerAccessor).isCoolingDown(item.getDefaultStack())) {
                  ItemCooldownManagerAccessor itemCooldownManagerAccessor1 = itemCooldownManagerAccessor;
                  Object object = itemCooldownManagerAccessor1.getEntries().get(Registries.ITEM.getId(item));
                  Locale locale = Locale.US;
                  Object[] objects = new Object[]{
                     object != null ? Math.max(((ItemCooldownEntryAccessor)object).getEndTick() - itemCooldownManagerAccessor1.getTick(), 0) / 20.0F : 0.0F
                  };
                  UtilComponent265.performAction_3505("&c" + string + "&7 - имеет задержку &c" + String.format(locale, "%.1fс", objects));
               } else {
                  if (UtilComponent270.computeValue_3526(item) == -1) {
                     UtilComponent265.performAction_3505("&c" + string + "&7 - нет в инвентаре");
                  } else {
                     HandlerComponent108.getUSEABLE_1160().performAction_1225(item.getDefaultStack());
                  }
               }
            }
         )
         .withVisibility(() -> {
            Stream stream = Arrays.stream(strings);
            return Arrays.stream(strings).anyMatch(string1 -> choiceSetting.checkCondition_2850(string1));
         });
      if (this.listField1 == null) {
         this.listField1 = new ArrayList<>();
      }

      this.listField1.add(new ServerAssistant.NestedType346(new Animation(), keybindSetting, item));
      return keybindSetting;
   }

   public class NestedType345 {
      public boolean checkCondition_1978(ItemStack itemStack) {
         if (itemStack.contains(DataComponentTypes.EQUIPPABLE)) {
            return this.checkCondition_1980(itemStack);
         } else if (itemStack.contains(DataComponentTypes.WEAPON)) {
            return this.checkCondition_1977(itemStack);
         } else {
            return Registries.ITEM.getId(itemStack.getItem()).getPath().endsWith("pickaxe") ? this.checkCondition_1979(itemStack) : true;
         }
      }

      private boolean checkCondition_1980(ItemStack itemStack) {
         if (itemStack.contains(DataComponentTypes.EQUIPPABLE)) {
            ConfigComponent022 configComponent022 = new ConfigComponent022();
            if (ServerAssistant.this.multiSelectSettingField4.computeValue_2852("Защите").getCurrentValue()) {
               configComponent022.computeValue_0387(Enchantments.UNBREAKING, 4);
               configComponent022.computeValue_0387(Enchantments.PROTECTION, 5);
            }

            if (ServerAssistant.this.multiSelectSettingField4.computeValue_2852("Аншип").getCurrentValue()) {
               configComponent022.computeValue_0380(Enchantments.THORNS);
            }

            if (ServerAssistant.this.multiSelectSettingField4.computeValue_2852("Починке").getCurrentValue()) {
               configComponent022.computeValue_0387(Enchantments.MENDING, 1);
            }

            if (ServerAssistant.this.multiSelectSettingField4.computeValue_2852("Подводной ходьбе").getCurrentValue()
               && itemStack.get(DataComponentTypes.EQUIPPABLE) != null
               && ((EquippableComponent)itemStack.get(DataComponentTypes.EQUIPPABLE)).slot() == EquipmentSlot.FEET) {
               configComponent022.computeValue_0387(Enchantments.DEPTH_STRIDER, 1);
            }

            return configComponent022.checkCondition_0078(itemStack);
         } else {
            return true;
         }
      }

      private boolean checkCondition_1977(ItemStack itemStack) {
         if (itemStack.contains(DataComponentTypes.WEAPON)) {
            ConfigComponent022 configComponent022 = new ConfigComponent022().computeValue_0381(Enchantments.KNOCKBACK, 2);
            if (ServerAssistant.this.multiSelectSettingField5.computeValue_2852("Остроте").getCurrentValue()) {
               configComponent022.computeValue_0387(Enchantments.SHARPNESS, 6);
            }

            configComponent022.computeValue_0381(Enchantments.KNOCKBACK, 1);
            if (configComponent022.checkCondition_0078(itemStack)) {
               ConfigComponent020 configComponent020 = new ConfigComponent020();
               configComponent020.computeValue_0369("Нестабильность ");
               configComponent020.computeValue_0369("Нестабильный ");
               if (ServerAssistant.this.multiSelectSettingField5.computeValue_2852("Детекции").getCurrentValue()) {
                  configComponent020.computeValue_0374("Детекция", 2);
               }

               if (ServerAssistant.this.multiSelectSettingField5.computeValue_2852("Вампиризму").getCurrentValue()) {
                  configComponent020.computeValue_0374("Вампиризм", 2);
               }

               if (ServerAssistant.this.multiSelectSettingField5.computeValue_2852("Окислению").getCurrentValue()) {
                  configComponent020.computeValue_0374("Окисление", 2);
               }

               if (ServerAssistant.this.multiSelectSettingField5.computeValue_2852("Яду").getCurrentValue()) {
                  configComponent020.computeValue_0374("Яд", 3);
               }

               return configComponent020.checkCondition_0078(itemStack);
            } else {
               return false;
            }
         } else {
            return false;
         }
      }

      private boolean checkCondition_1979(ItemStack itemStack) {
         if (Registries.ITEM.getId(itemStack.getItem()).getPath().endsWith("pickaxe")) {
            if (ServerAssistant.this.multiSelectSettingField6.computeValue_2852("Починке").getCurrentValue()
               && !new ConfigComponent022().computeValue_0387(Enchantments.MENDING, 1).checkCondition_0078(itemStack)) {
               return false;
            }

            ConfigComponent020 configComponent020 = new ConfigComponent020();
            if (ServerAssistant.this.multiSelectSettingField6.computeValue_2852("Удаче").getCurrentValue()) {
               configComponent020.computeValue_0374("Удача", 5);
            }

            if (ServerAssistant.this.multiSelectSettingField6.computeValue_2852("Эффективности").getCurrentValue()) {
               configComponent020.computeValue_0374("Эффективность", 4);
            }

            if (ServerAssistant.this.multiSelectSettingField6.computeValue_2852("Магнит").getCurrentValue()) {
               configComponent020.computeValue_0373("Магнит");
            }

            return configComponent020.checkCondition_0078(itemStack);
         } else {
            return false;
         }
      }
   }

   public static final class NestedType346 {
      private final Animation animationUtil;
      private final KeybindSetting bind;
      private final Item item;

      public NestedType346(Animation animation, KeybindSetting keybindSetting, Item item) {
         this.animationUtil = animation;
         this.bind = keybindSetting;
         this.item = itemx;
      }

      public Animation getAnimationUtil_1981() {
         return this.animationUtil;
      }

      public KeybindSetting getBind_1982() {
         return this.bind;
      }

      public Item getItem_1983() {
         return this.item;
      }
   }
}
