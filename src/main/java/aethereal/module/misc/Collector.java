package aethereal.module.misc;

import aethereal.config.ConfigComponent020;
import aethereal.config.ConfigComponent022;
import aethereal.config.ConfigComponent026;
import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.ContainerEvent;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent091;
import aethereal.event.EventComponent093;
import aethereal.event.PacketEvent;
import aethereal.render.Animation;
import aethereal.setting.ActionSetting;
import aethereal.ui.screen.ScreenComponent224;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent268;
import aethereal.util.UtilComponent279;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

@ModuleRegister(name = "Collector", description = "Автоматически собирает нужный инвентарь на FunTime", category = Category.Misc)
public class Collector extends Module {
   private final ActionSetting actionSettingField1 = new ActionSetting("Запустить работу", () -> {
      if (!this.isEnabled()) {
         this.toggle();
      }

      this.performAction_1898();
   });
   private final ActionSetting actionSettingField2 = new ActionSetting(
      "Открыть редактор", () -> minecraft.setScreen(new ScreenComponent224(Text.literal(""), 0))
   );
   private final List<Collector.NestedType343> listField3 = NeneClient.getInstance().getProcessors().getAutobuyComponent009Field16_0665().getEntries();
   private final List<Collector.NestedType342> listField4 = new ArrayList<>();
   private final UtilComponent268 utilComponent268Field5 = new UtilComponent268();
   private final UtilComponent268 utilComponent268Field6 = new UtilComponent268();
   private Collector.NestedType343 nestedType343Field7;
   private Collector.NestedType342 nestedType342Field8;
   private int integerField9;

   public List<Collector.NestedType343> getListField3_1875() {
      return this.listField3;
   }

   public Collector() {
      this.addSettings(this.actionSettingField1, this.actionSettingField2);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      UtilComponent265.performAction_3505("Модуль ищет самые дешевые лоты среди тех которые есть, имейте это ввиду, и будьте осторожны!");
   }

   private void performAction_1898() {
      int i = this.nestedType343Field7 == null ? 0 : this.listField3.indexOf(this.nestedType343Field7) + 1;
      this.nestedType342Field8 = null;
      this.listField4.clear();
      IntStream intStream = IntStream.range(i, this.listField3.size());
      List list = this.listField3;
      this.nestedType343Field7 = intStream.mapToObj(list::get)
         .filter(nestedType343 -> nestedType343.isFlagField5_1925())
         .filter(nestedType343 -> this.computeValue_1884(nestedType343) < this.computeValue_1887(nestedType343, false))
         .findFirst()
         .orElse(null);
      if (this.nestedType343Field7 != null) {
         UtilComponent265.performAction_3505("Переходим к сбору предмета: " + this.nestedType343Field7.getName_1924());
      } else {
         UtilComponent265.performAction_3505("Все предметы собраны, работа завершена");
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.nestedType343Field7 = null;
   }

   @EventTarget
   public void onEventComponent074_1870(EventComponent074 eventComponent074) {
      if (this.nestedType343Field7 != null) {
         if (minecraft.player.getInventory().getEmptySlot() == -1) {
            UtilComponent265.performAction_3505("Автоматическое отключение: нет свободных слотов в инвентаре, освободите место");
            this.performAction_1897();
            this.toggle();
            return;
         }

         this.integerField9++;
         if (!(minecraft.currentScreen instanceof GenericContainerScreen)
            && minecraft.player.age >= 200
            && this.utilComponent268Field5.checkCondition_3523(1000L, 300L)) {
            minecraft.player.networkHandler.sendChatCommand("ah search " + this.nestedType343Field7.getName_1924());
            this.utilComponent268Field5.performAction_3524();
            this.integerField9 = 0;
         }
      }
   }

   @EventTarget
   public void onContainerEvent_1869(ContainerEvent containerEvent) {
      if (this.nestedType343Field7 != null && containerEvent.getPhase_0988() != ContainerEvent.Phase.PRE) {
         String string = containerEvent.getScreen_0992().getTitle().getString().replaceAll("§.", "").toLowerCase().trim();
         if (string.contains(this.nestedType343Field7.getName_1924().toLowerCase())) {
            if (this.utilComponent268Field5.checkCondition_3523(300L, 80L)
               && this.computeValue_1884(this.nestedType343Field7) >= this.computeValue_1887(this.nestedType343Field7, false)) {
               UtilComponent265.performAction_3505("Предмет " + this.nestedType343Field7.getName_1924() + " приобретен, перехожу к следующему");
               this.performAction_1898();
               this.performAction_1897();
               return;
            }

            if (this.utilComponent268Field6.checkCondition_3523(1000L, 150L)) {
               if (this.utilComponent268Field5.checkCondition_3523(this.nestedType343Field7.isFlagField6_1914() ? 400L : 550L, 120L)) {
                  if (this.nestedType343Field7.isFlagField6_1914()) {
                     Matcher matcher = Pattern.compile("(\\d+)/(\\d+)").matcher(string);
                     if (matcher.find()) {
                        int i = Integer.parseInt(matcher.group(1));
                        int j = Math.min(4, Integer.parseInt(matcher.group(2)));
                        if (this.nestedType342Field8 == null) {
                           if (this.listField4.stream().noneMatch(nestedType342 -> nestedType342.getPage_1900() == i)) {
                              containerEvent.getSlots_0987()
                                 .stream()
                                 .filter(slot2 -> this.checkCondition_1874(slot2.getStack()))
                                 .forEach(
                                    slot2 -> this.listField4
                                       .add(new Collector.NestedType342(i, slot2.id, UtilComponent252.NestedType400.computeValue_3405(slot2.getStack())))
                                 );
                           }

                           if (i < j) {
                              this.performAction_1881(containerEvent, "следующая страница");
                           } else {
                              this.nestedType342Field8 = this.listField4
                                 .stream()
                                 .min(Comparator.comparingInt(nestedType342 -> nestedType342.getPrice_1901()))
                                 .orElse(null);
                              if (this.nestedType342Field8 == null) {
                                 this.performAction_1897();
                              }
                           }
                        } else if (i == this.nestedType342Field8.getPage_1900()) {
                           Slot slot = containerEvent.getSlots_0987()
                              .stream()
                              .filter(
                                 slot2 -> this.checkCondition_1874(slot2.getStack())
                                    && UtilComponent252.NestedType400.computeValue_3405(slot2.getStack()) == this.nestedType342Field8.getPrice_1901()
                              )
                              .findFirst()
                              .orElse(null);
                           if (slot == null) {
                              slot = containerEvent.getSlots_0987()
                                 .stream()
                                 .filter(slot2 -> this.checkCondition_1874(slot2.getStack()))
                                 .min(Comparator.comparingInt(slot2 -> UtilComponent252.NestedType400.computeValue_3405(slot2.getStack())))
                                 .orElse(null);
                           }

                           if (slot != null) {
                              minecraft.interactionManager
                                 .clickSlot(containerEvent.getHandler_0989().syncId, slot.id, 0, SlotActionType.QUICK_MOVE, minecraft.player);
                              this.utilComponent268Field5.performAction_3524();
                           } else {
                              UtilComponent265.performAction_3505("Оффер пропал, пересканирую");
                              this.nestedType342Field8 = null;
                              this.listField4.clear();
                              this.performAction_1897();
                           }
                        } else {
                           this.performAction_1881(containerEvent, i < this.nestedType342Field8.getPage_1900() ? "следующая страница" : "предыдущая страница");
                        }
                     }
                  } else {
                     List list = containerEvent.getSlots_0987().stream().filter(slot2 -> this.checkCondition_1874(slot2.getStack())).toList();
                     int m = list.stream().mapToInt(slot2 -> UtilComponent252.NestedType400.computeValue_3405(slot2.getStack())).min().orElse(0);
                     List list1 = list.stream()
                        .filter(slot2 -> UtilComponent252.NestedType400.computeValue_3405(slot2.getStack()) <= Math.round(m * 2))
                        .filter(
                           slot2 -> slot2.getStack().getCount()
                              <= this.computeValue_1887(this.nestedType343Field7, true) - this.computeValue_1884(this.nestedType343Field7)
                        )
                        .toList();
                     Slot slot1 = list1.stream()
                        .filter(
                           slot2 -> slot2.getStack().getCount()
                              >= this.computeValue_1887(this.nestedType343Field7, false) - this.computeValue_1884(this.nestedType343Field7)
                        )
                        .min(Comparator.comparingInt(slot2 -> UtilComponent252.NestedType400.computeValue_3405(slot2.getStack())))
                        .orElse(null);
                     if (slot1 == null) {
                        slot1 = list1.stream()
                           .min(Comparator.comparingInt(slot2 -> UtilComponent252.NestedType400.computeValue_3405(slot2.getStack())))
                           .orElse(null);
                     }

                     if (slot1 != null) {
                        minecraft.interactionManager
                           .clickSlot(containerEvent.getHandler_0989().syncId, slot1.id, 0, SlotActionType.QUICK_MOVE, minecraft.player);
                        this.utilComponent268Field5.performAction_3524();
                     } else {
                        Matcher matcher1 = Pattern.compile("(\\d+)/(\\d+)").matcher(string);
                        if (matcher1.find()) {
                           int k = Integer.parseInt(matcher1.group(1));
                           int l = Integer.parseInt(matcher1.group(2));
                           if (l == 1) {
                              UtilComponent265.performAction_3505("Пропускаем предмет " + this.nestedType343Field7.getName_1924() + ", ибо нету подходящего");
                              this.performAction_1898();
                              this.performAction_1897();
                              return;
                           }

                           this.performAction_1881(containerEvent, k == 1 ? "следующая страница" : "предыдущая страница");
                        }
                     }
                  }

                  this.utilComponent268Field5.performAction_3524();
                  return;
               }

               return;
            }

            return;
         }

         if (string.contains("подтверждение покупки") || string.contains("подозрительная цена!") || string.contains("подозрительная цена: ")) {
            if (this.utilComponent268Field5.checkCondition_3523(200L, 90L)) {
               this.performAction_1881(containerEvent, "[Кyпить]");
               this.utilComponent268Field5.performAction_3524();
               return;
            }

            return;
         }

         this.performAction_1897();
      }
   }

   @EventTarget
   public void onPacketEvent_1871(PacketEvent packetEvent) {
      if (this.nestedType343Field7 != null && packetEvent.checkCondition_1013()) {
         GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packetEvent.getPacket_1015();
         if (gameMessageS2CPacket instanceof GameMessageS2CPacket gameMessageS2CPacket1) {
            String string = gameMessageS2CPacket1.content().getString();
            if (string.toLowerCase().contains("[✘] Ошибка! Этот товар уже Купили!")) {
               this.nestedType342Field8 = null;
               this.listField4.clear();
               return;
            }

            if (string.contains("[✘] Ошибка! У Вас не хватает Монет!")) {
               UtilComponent265.performAction_3505("Автоматическое отключение из-за нехватки баланса на аккаунте");
               this.toggle();
               return;
            }

            if (string.contains("Данная команда недоступна в режиме AFK")) {
               NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getMiscComponent167Field4_1155().setIntegerField1_1806(10);
            }
         }

         if (packetEvent.getPacket_1015() instanceof OpenScreenS2CPacket && !(minecraft.currentScreen instanceof GenericContainerScreen)) {
            if (this.integerField9 >= 8) {
               int i = (int)(
                  UtilComponent279.computeValue_3582(0.0F, 100.0F) <= 50.0F
                     ? UtilComponent279.computeValue_3582(205.0F, 231.0F)
                     : UtilComponent279.computeValue_3582(305.0F, 325.0F)
               );
               minecraft.player.networkHandler.sendChatMessage("/an" + i);
               UtilComponent265.performAction_3505("Обнаружили замедление аукциона, переходим на " + i + " анархию");
            }

            this.utilComponent268Field6.performAction_3524();
         }
      }
   }

   @EventTarget
   public void onEventComponent091_1872(EventComponent091 eventComponent091) {
      if (this.nestedType343Field7 != null && minecraft.currentScreen instanceof HandledScreen) {
         eventComponent091.setForward_1065(0.0F);
         eventComponent091.setStrafe_1060(0.0F);
      }
   }

   @EventTarget
   public void onEventComponent093_1873(EventComponent093 eventComponent093) {
      if (this.nestedType343Field7 != null && minecraft.currentScreen instanceof HandledScreen) {
         eventComponent093.performAction_0597(true);
      }

      if (eventComponent093.getKey_1068() == 256 && this.nestedType343Field7 != null) {
         this.nestedType343Field7 = null;
         UtilComponent265.performAction_3505("Работа модуля была принудительно завершена");
      }
   }

   private void performAction_1881(ContainerEvent containerEvent, String string) {
      containerEvent.getSlots_0987()
         .stream()
         .filter(slot -> slot.getStack().getName().getString().toLowerCase().contains(string.toLowerCase()))
         .findFirst()
         .ifPresent(
            slot -> minecraft.interactionManager.clickSlot(containerEvent.getHandler_0989().syncId, slot.id, 0, SlotActionType.QUICK_MOVE, minecraft.player)
         );
   }

   private void performAction_1897() {
      if (minecraft.currentScreen instanceof GenericContainerScreen) {
         minecraft.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(minecraft.player.currentScreenHandler.syncId));
         minecraft.player.closeScreen();
      }
   }

   private boolean checkCondition_1874(ItemStack itemStack) {
      if (!itemStack.isEmpty() && this.nestedType343Field7.checkCondition_1908(itemStack)) {
         IntStream intStream = IntStream.range(0, 40);
         PlayerInventory playerInventory = minecraft.player.getInventory();
         ItemStack itemStack1 = intStream.<ItemStack>mapToObj(playerInventory::getStack)
            .filter(itemStack2 -> !itemStack2.isEmpty() && this.nestedType343Field7.checkCondition_1908(itemStack2))
            .findFirst()
            .orElse(ItemStack.EMPTY);
         if ((
               itemStack1.isEmpty()
                  || (
                        !(this.nestedType343Field7.getItem_1906() instanceof PotionItem)
                           || Objects.equals(itemStack.get(DataComponentTypes.POTION_CONTENTS), itemStack1.get(DataComponentTypes.POTION_CONTENTS))
                     )
                     && itemStack.getName().getString().trim().equalsIgnoreCase(itemStack1.getName().getString().trim())
            )
            && !itemStack.getTooltip(TooltipContext.DEFAULT, minecraft.player, TooltipType.BASIC)
               .stream()
               .anyMatch(text -> text.getString().contains("➥ Нажмите, чтобы забрать"))
            && UtilComponent252.NestedType400.computeValue_3405(itemStack) > 0) {
            if (this.nestedType343Field7.getItem_1906() == Items.TOTEM_OF_UNDYING && itemStack.hasGlint()) {
               return false;
            } else if (this.nestedType343Field7.getItem_1906() == Items.ELYTRA && itemStack.get(DataComponentTypes.DAMAGE) != null) {
               return (432 - (Integer)itemStack.get(DataComponentTypes.DAMAGE)) / 432.0F >= 0.5F;
            } else {
               return this.nestedType343Field7.getItem_1906().getDefaultStack().contains(DataComponentTypes.EQUIPPABLE)
                     && itemStack.get(DataComponentTypes.DAMAGE) != null
                     && itemStack.getMaxDamage() > 0
                  ? this.nestedType343Field7.getConfigComponent022Field2_1911() != null
                        && !this.nestedType343Field7
                           .getConfigComponent022Field2_1911()
                           .getListField1_0382()
                           .stream()
                           .noneMatch(
                              configComponent021 -> configComponent021.checkCondition_0348()
                                 && !configComponent021.checkCondition_0344()
                                 && configComponent021.getKey_0378().equals(Enchantments.MENDING)
                           )
                     || (float)(itemStack.getMaxDamage() - (Integer)itemStack.get(DataComponentTypes.DAMAGE)) / itemStack.getMaxDamage() >= 0.7F
                  : true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private int computeValue_1887(Collector.NestedType343 nestedType343, boolean bl) {
      return Math.max(1, bl ? nestedType343.getCount_1909() + Math.round(nestedType343.getCount_1909() * 0.2F) : nestedType343.getCount_1909());
   }

   private int computeValue_1884(Collector.NestedType343 nestedType343) {
      IntStream intStream = IntStream.range(0, 40);
      PlayerInventory playerInventory = minecraft.player.getInventory();
      return intStream.<ItemStack>mapToObj(playerInventory::getStack)
         .filter(itemStack -> !itemStack.isEmpty() && nestedType343.checkCondition_1908(itemStack))
         .mapToInt(itemStack -> itemStack.getCount())
         .sum();
   }

   static final class NestedType342 {
      private final int page;
      private final int slotId;
      private final int price;

      NestedType342(int i, int j, int k) {
         this.page = i;
         this.slotId = j;
         this.price = k;
      }

      public int getPage_1900() {
         return this.page;
      }

      public int getSlotId_1899() {
         return this.slotId;
      }

      public int getPrice_1901() {
         return this.price;
      }
   }

   public static class NestedType343 {
      private ConfigComponent020 configComponent020Field1;
      private ConfigComponent022 configComponent022Field2;
      private ConfigComponent026 configComponent026Field3;
      private final Animation animationField4 = new Animation();
      private final Item item;
      private final String name;
      private boolean flagField5;
      private boolean flagField6;
      private int count;
      private int integerField7;

      public void setCount_1916(int i) {
         this.count = i;
      }

      public ConfigComponent020 getConfigComponent020Field1_1902() {
         return this.configComponent020Field1;
      }

      public ConfigComponent022 getConfigComponent022Field2_1911() {
         return this.configComponent022Field2;
      }

      public ConfigComponent026 getConfigComponent026Field3_1912() {
         return this.configComponent026Field3;
      }

      public Animation getAnimationField4_1923() {
         return this.animationField4;
      }

      public Item getItem_1906() {
         return this.item;
      }

      public String getName_1924() {
         return this.name;
      }

      public boolean isFlagField5_1925() {
         return this.flagField5;
      }

      public boolean isFlagField6_1914() {
         return this.flagField6;
      }

      public int getCount_1909() {
         return this.count;
      }

      public int getIntegerField7_1910() {
         return this.integerField7;
      }

      private NestedType343(Item item, int i, String string) {
         this.item = itemx;
         this.name = string;
         this.count = i;
      }

      public static Collector.NestedType343 computeValue_1921(Item item, int i, String string) {
         return new Collector.NestedType343(itemx, i, string);
      }

      public boolean checkCondition_1907() {
         return this.item.getMaxCount() > 1 || this.item == Items.TOTEM_OF_UNDYING || this.item instanceof SplashPotionItem || this.item instanceof PotionItem;
      }

      public int computeValue_1905() {
         if (this.item instanceof PotionItem) {
            return 16;
         } else {
            return this.item != Items.TOTEM_OF_UNDYING && !(this.item instanceof SplashPotionItem) ? this.item.getMaxCount() : 6;
         }
      }

      public Collector.NestedType343 computeValue_1918(ConfigComponent022 configComponent022) {
         this.configComponent022Field2 = configComponent022;
         return this;
      }

      public Collector.NestedType343 computeValue_1917(ConfigComponent020 configComponent020) {
         this.configComponent020Field1 = configComponent020;
         return this;
      }

      public Collector.NestedType343 computeValue_1920(ConfigComponent026 configComponent026) {
         this.configComponent026Field3 = configComponent026;
         return this;
      }

      public Collector.NestedType343 computeValue_1922(boolean bl) {
         this.flagField5 = bl;
         return this;
      }

      public Collector.NestedType343 computeValue_1904(boolean bl) {
         this.flagField6 = bl;
         return this;
      }

      public Collector.NestedType343 computeValue_1915(int i) {
         this.integerField7 = i;
         return this;
      }

      public boolean checkCondition_1908(ItemStack itemStack) {
         return itemStack.isOf(this.item)
            && (this.configComponent020Field1 == null || this.configComponent020Field1.checkCondition_0078(itemStack))
            && (this.configComponent022Field2 == null || this.configComponent022Field2.checkCondition_0078(itemStack))
            && (this.configComponent026Field3 == null || this.configComponent026Field3.checkCondition_0078(itemStack));
      }

      public ItemStack computeValue_1903() {
         ItemStack itemStack = new ItemStack(this.item, this.count);
         if (this.integerField7 != 0) {
            List list = this.configComponent026Field3 == null
               ? List.of()
               : this.configComponent026Field3
                  .getListField1_0519()
                  .stream()
                  .map(
                     configComponent025 -> new StatusEffectInstance(
                        configComponent025.getEffect_0515(),
                        configComponent025.getRequiredDuration_0518(),
                        Math.max(0, configComponent025.getRequiredLevel_0513() - 1)
                     )
                  )
                  .toList();
            itemStack.set(
               DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(this.integerField7), list, Optional.empty())
            );
         }

         return itemStack;
      }

      public Collector.NestedType343 computeValue_1913() {
         return computeValue_1921(this.item, this.count, this.name)
            .computeValue_1917(this.configComponent020Field1)
            .computeValue_1918(this.configComponent022Field2)
            .computeValue_1920(this.configComponent026Field3)
            .computeValue_1922(this.flagField5)
            .computeValue_1904(this.flagField6)
            .computeValue_1915(this.integerField7);
      }
   }
}
