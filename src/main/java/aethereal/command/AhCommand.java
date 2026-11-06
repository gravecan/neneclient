package aethereal.command;

import aethereal.autobuy.AutoBuyEntry;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent268;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.command.CommandSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;

@CommandRegister(name = "ah")
public class AhCommand extends Command implements MinecraftAccess {
   private final List<AhCommand.NestedType292> listField1 = new ArrayList<>();
   private TranslationStorage translationStorageField2;
   private String stringField3;
   private AhCommand.NestedType293 nestedType293Field4;

   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      ((LiteralArgumentBuilder)literalArgumentBuilder.executes(commandContext -> {
         ItemStack itemStack = minecraft.player.getMainHandStack();
         if (!itemStack.isEmpty()) {
            minecraft.player.networkHandler.sendChatCommand("ah search " + this.computeValue_0149(itemStack));
            return 1;
         } else {
            return 1;
         }
      })).then(((LiteralArgumentBuilder)this.computeValue_0173("sell").executes(commandContext -> {
         this.performAction_0141(0.0F);
         return 1;
      })).then(this.computeValue_0162("процент").executes(commandContext -> {
         this.performAction_0141(this.computeValue_0168(commandContext, "процент"));
         return 1;
      })));
   }

   @EventTarget
   public void onPacketEvent_0140(PacketEvent packetEvent) {
      if (this.nestedType293Field4 != null && this.stringField3 == null && packetEvent.checkCondition_1013()) {
         InventoryS2CPacket inventoryS2CPacket = (InventoryS2CPacket)packetEvent.getPacket_1015();
         if (inventoryS2CPacket instanceof InventoryS2CPacket inventoryS2CPacket1 && inventoryS2CPacket1.syncId() != 0) {
            List list = inventoryS2CPacket1.contents();
            List list1 = list.subList(0, Math.max(0, list.size() - 36))
               .stream()
               .filter(
                  itemStack -> this.nestedType293Field4.getItem_0158().checkCondition_0027(itemStack)
                     && itemStack.getTooltip(TooltipContext.DEFAULT, minecraft.player, TooltipType.BASIC)
                        .stream()
                        .noneMatch(text -> text.getString().contains("Нажмите, чтобы забрать"))
               )
               .mapToInt(UtilComponent252.NestedType400::computeValue_3405)
               .filter(k -> k > 0)
               .sorted()
               .boxed()
               .toList();
            if (!list1.isEmpty()) {
               int i = (Integer)list1.get(Math.min(2, list1.size() - 1));
               int j = list1.stream().filter(integer -> integer.intValue() >= i * 0.75).findFirst().orElse(i);
               this.listField1.removeIf(nestedType292 -> nestedType292.getItem_0154() == this.nestedType293Field4.getItem_0158());
               this.listField1.add(new AhCommand.NestedType292(this.nestedType293Field4.getItem_0158(), j, new UtilComponent268()));
               this.performAction_0147(this.nestedType293Field4, j);
            }

            this.nestedType293Field4 = null;
         }
      }
   }

   @EventTarget
   public void onEventComponent074_0139(EventComponent074 eventComponent074) {
      if (this.stringField3 != null) {
         if (minecraft.currentScreen != null) {
            minecraft.player.closeHandledScreen();
         } else {
            minecraft.player.networkHandler.sendChatCommand(this.stringField3);
            this.stringField3 = null;
         }
      }
   }

   private void performAction_0141(float f) {
      ItemStack itemStack = minecraft.player.getMainHandStack();
      AutoBuyEntry autoBuyEntry = NeneClient.getInstance()
         .getProcessors()
         .getAutoBuyManagerField12_0663()
         .getEntries()
         .stream()
         .filter(autoBuyEntry1 -> autoBuyEntry1.checkCondition_0027(itemStack))
         .findFirst()
         .orElse(null);
      if (autoBuyEntry == null) {
         UtilComponent265.performAction_3505("Авто-продажа недоступна для обычных предметов — только для донатных");
      } else {
         AhCommand.NestedType293 nestedType293 = new AhCommand.NestedType293(autoBuyEntry, itemStack.getCount(), f);
         AhCommand.NestedType292 nestedType292 = this.listField1
            .stream()
            .filter(nestedType2921 -> nestedType2921.getItem_0154() == autoBuyEntry && !nestedType2921.getTimer_0155().checkCondition_3522(15000L))
            .findFirst()
            .orElse(null);
         if (nestedType292 != null) {
            this.performAction_0147(nestedType293, nestedType292.getPrice_0153());
         } else {
            minecraft.player.networkHandler.sendChatCommand("ah search " + this.computeValue_0149(itemStack));
            this.nestedType293Field4 = nestedType293;
         }
      }
   }

   private void performAction_0147(AhCommand.NestedType293 nestedType293, int i) {
      long l = Math.max(1L, Math.round(i * (1.0 - nestedType293.getPercent_0157() / 100.0) * Math.max(1, nestedType293.getCount_0156())));
      String string = nestedType293.getItem_0158().getDisplayName_0029();
      UtilComponent265.performAction_3505("Выставляю &c" + string + " &7за &c" + l + " &7(-" + string + "% от " + (int)nestedType293.getPercent_0157() + ")");
      this.stringField3 = "ah sell " + l;
   }

   private String computeValue_0149(ItemStack itemStack) {
      this.translationStorageField2 = this.translationStorageField2 == null
         ? TranslationStorage.load(minecraft.getResourceManager(), List.of("ru_ru"), false)
         : this.translationStorageField2;
      String string = NeneClient.getInstance()
         .getProcessors()
         .getAutoBuyManagerField12_0663()
         .getEntries()
         .stream()
         .filter(autoBuyEntry -> autoBuyEntry.checkCondition_0027(itemStack))
         .findFirst()
         .map(autoBuyEntry -> autoBuyEntry.getDisplayName_0029())
         .orElse(this.translationStorageField2.get(itemStack.getItem().getTranslationKey()));
      return string.replaceAll("\\[\\d+x\\d+]", "")
         .replace("⚡", "")
         .replace("xxx", "")
         .replace("[", "")
         .replace("]", "")
         .replace("★", "")
         .trim()
         .replaceAll("\\s+", "");
   }

   static final class NestedType292 {
      private final AutoBuyEntry item;
      private final int price;
      private final UtilComponent268 timer;

      NestedType292(AutoBuyEntry autoBuyEntry, int i, UtilComponent268 utilComponent268) {
         this.item = autoBuyEntry;
         this.price = i;
         this.timer = utilComponent268;
      }

      public AutoBuyEntry getItem_0154() {
         return this.item;
      }

      public int getPrice_0153() {
         return this.price;
      }

      public UtilComponent268 getTimer_0155() {
         return this.timer;
      }
   }

   static final class NestedType293 {
      private final AutoBuyEntry item;
      private final int count;
      private final float percent;

      NestedType293(AutoBuyEntry autoBuyEntry, int i, float f) {
         this.item = autoBuyEntry;
         this.count = i;
         this.percent = f;
      }

      public AutoBuyEntry getItem_0158() {
         return this.item;
      }

      public int getCount_0156() {
         return this.count;
      }

      public float getPercent_0157() {
         return this.percent;
      }
   }
}
