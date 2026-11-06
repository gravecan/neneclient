package aethereal.module.misc;

import aethereal.autobuy.AutoBuyEntry;
import aethereal.core.Category;
import aethereal.core.ClientBackend;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.ContainerEvent;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.setting.ActionSetting;
import aethereal.setting.BooleanSetting;
import aethereal.ui.screen.ScreenComponent224;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent268;
import aethereal.util.UtilComponent279;
import aethereal.util.UtilComponent280;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.sync.ItemStackHash;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import platform.inject.accessors.HandledScreenAccessor;

@ModuleRegister(name = "Auto Buy", description = "Автоматически скупает выбранные предметы по заданной цене", category = Category.Misc)
public class AutoBuy extends Module implements MinecraftAccess {
   private int integerField1;
   private int integerField2;
   private ItemStack itemStackField3;
   private boolean flagField4;
   private boolean flagField5;
   private final BooleanSetting booleanSettingField6 = new BooleanSetting("Авто-перевыставление вещей", false);
   private final ActionSetting actionSettingField7 = new ActionSetting(
      "Открыть редактор", () -> minecraft.setScreen(new ScreenComponent224(Text.literal(""), 1))
   );
   private final List<ItemStack> listField8 = new ArrayList<>();
   private final UtilComponent268 utilComponent268Field9 = new UtilComponent268();
   private int integerField10 = -1;

   public boolean isFlagField4_1851() {
      return this.flagField4;
   }

   public boolean isFlagField5_1852() {
      return this.flagField5;
   }

   public void setFlagField4_1857(boolean bl) {
      this.flagField4 = bl;
   }

   public void setFlagField5_1856(boolean bl) {
      this.flagField5 = bl;
   }

   public AutoBuy() {
      this.addSettings(this.actionSettingField7, this.booleanSettingField6);
   }

   @EventTarget
   public void onEventComponent074_1849(EventComponent074 eventComponent074) {
      if (minecraft.player.age >= 220
         && this.flagField5
         && !(minecraft.currentScreen instanceof GenericContainerScreen)
         && this.flagField4
         && minecraft.player.age % 20 == 0) {
         minecraft.player.networkHandler.sendChatCommand("ah");
         this.flagField5 = false;
      }

      this.integerField1++;
      this.integerField2++;
      GenericContainerScreen genericContainerScreen = (GenericContainerScreen)minecraft.currentScreen;
      if (genericContainerScreen instanceof GenericContainerScreen genericContainerScreen1 && this.flagField4) {
         ScreenHandler screenHandler = genericContainerScreen1.getScreenHandler();
         String string = genericContainerScreen1.getTitle().getString().replaceAll("§.", "").toLowerCase().trim();
         boolean bl = minecraft.player.age % 2 == 0;
         boolean bl1 = this.booleanSettingField6.getCurrentValue() && this.utilComponent268Field9.checkCondition_3522(10000L);
         if (!string.contains("аукцион")) {
            if ((string.contains("подтверждение покупки") || string.contains("подозрительная цена!") || string.contains("подозрительная цена: ")) && bl) {
               this.performAction_1854(screenHandler, 1, SlotActionType.QUICK_MOVE);
            }
         } else {
            boolean bl2 = false;

            for (Slot slot : screenHandler.slots.subList(0, Math.min(45, screenHandler.slots.size()))) {
               ItemStack itemStack = slot.getStack();
               ContainerComponent containerComponent = (ContainerComponent)itemStack.get(DataComponentTypes.CONTAINER);
               AutoBuyEntry autoBuyEntry = NeneClient.getInstance()
                  .getProcessors()
                  .getAutoBuyManagerField12_0663()
                  .getEntries()
                  .stream()
                  .filter(
                     autoBuyEntry1 -> {
                        if (autoBuyEntry1.isFlagField3_0039()) {
                           if (autoBuyEntry1.checkCondition_0027(itemStack)) {
                              if (UtilComponent252.NestedType400.computeValue_3405(itemStack) > autoBuyEntry1.getDoubleValueField2_0038()
                                 && (long)UtilComponent252.NestedType400.computeValue_3405(itemStack) * Math.max(itemStack.getCount(), 1)
                                    <= UtilComponent252.NestedType400.computeValue_3413()) {
                                 return true;
                              }
                           } else if (containerComponent != null) {
                              Stream stream = containerComponent.stream();
                              if (stream.anyMatch(itemStack2 -> autoBuyEntry1.checkCondition_0027(itemStack2))
                                 && UtilComponent252.NestedType400.computeValue_3405(itemStack) > autoBuyEntry1.getDoubleValueField2_0038()) {
                              }
                           }
                        }

                        return false;
                     }
                  )
                  .findFirst()
                  .orElse(null);
               if (autoBuyEntry != null && bl) {
                  bl2 = true;
                  this.itemStackField3 = itemStack.copy();
                  this.performAction_1854(screenHandler, slot.id, SlotActionType.QUICK_MOVE);
                  break;
               }
            }

            if (!bl2 && !bl1 && this.integerField10 == screenHandler.syncId) {
               this.performAction_1854(
                  screenHandler, 49, UtilComponent279.computeValue_3582(0.0F, 100.0F) < 25.0F ? SlotActionType.QUICK_MOVE : SlotActionType.PICKUP
               );
               this.integerField10 = -1;
            }
         }

         if (this.booleanSettingField6.getCurrentValue() && bl1) {
            HandledScreen handledScreen = (HandledScreen)minecraft.currentScreen;
            if (handledScreen instanceof HandledScreen handledScreen1
               && handledScreen1 instanceof GenericContainerScreen
               && !UtilComponent280.checkCondition_3587()) {
               if (string.matches(".*а.*у.*к.*ц.*и.*о.*н.*")) {
                  if (minecraft.player.age % 10 == 0) {
                     this.performAction_1854(handledScreen1.getScreenHandler(), 46, SlotActionType.PICKUP);
                     this.integerField2 = 0;
                  }
               } else if (string.matches(".*х.*р.*а.*н.*и.*л.*и.*щ.*е.*")) {
                  if (this.integerField2 % 20 == 10) {
                     this.performAction_1854(handledScreen1.getScreenHandler(), 52, SlotActionType.PICKUP);
                  } else if (this.integerField2 % 20 == 0 && this.integerField2 > 0) {
                     this.performAction_1854(handledScreen1.getScreenHandler(), 46, SlotActionType.PICKUP);
                     this.utilComponent268Field9.performAction_3524();
                  }
               }

               this.integerField1 = 0;
            }
         }
      }
   }

   @EventTarget
   public void onPacketEvent_1850(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013()) {
         if (this.flagField4) {
            InventoryS2CPacket inventoryS2CPacket = (InventoryS2CPacket)packetEvent.getPacket_1015();
            if (inventoryS2CPacket instanceof InventoryS2CPacket inventoryS2CPacket1 && inventoryS2CPacket1.contents().size() == 90 && this.integerField1 >= 7) {
               int i = (int)(
                  UtilComponent279.computeValue_3582(0.0F, 100.0F) <= 50.0F
                     ? UtilComponent279.computeValue_3582(205.0F, 231.0F)
                     : UtilComponent279.computeValue_3582(305.0F, 325.0F)
               );
               minecraft.player.networkHandler.sendChatCommand("an" + i);
               UtilComponent265.performAction_3505("Обнаружили замедление аукциона, переходим на " + i + " анархию");
               this.integerField1 = 0;
               this.flagField5 = true;
            }

            GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packetEvent.getPacket_1015();
            if (gameMessageS2CPacket instanceof GameMessageS2CPacket gameMessageS2CPacket1
               && this.itemStackField3 != null
               && gameMessageS2CPacket1.content().getString().contains("Вы успешно купили")) {
               if (this.listField8.isEmpty() || !ItemStack.areEqual(this.listField8.getFirst(), this.itemStackField3)) {
                  ClientBackend clientBackend = NeneClient.getInstance().getBackend();
                  Object[] objects = new Object[]{"message", null};
                  Object[] objects1 = new Object[]{
                     this.itemStackField3.getName().getString() + (this.itemStackField3.getCount() > 1 ? " ×" + this.itemStackField3.getCount() : ""),
                     String.format(Locale.US, "%,d", UtilComponent252.NestedType400.computeValue_3405(this.itemStackField3)),
                     String.format(Locale.US, "%,d", UtilComponent252.NestedType400.computeValue_3413())
                  };
                  objects[1] = "\ud83d\uded2 AutoBuy — Успешная покупка!\n\n\ud83d\udce6 Предмет: %s\n\ud83d\udcb0 Цена: %s $\n\ud83d\udcb3 Баланс: %s $\n"
                     .formatted(objects1);
                  clientBackend.performAction_0599(false, "telegram", objects);
                  UtilComponent265.performAction_3505(
                     "Успешно куплен предмет &c"
                        + this.itemStackField3.getName().getString()
                        + " &7за &c"
                        + UtilComponent252.NestedType400.computeValue_3405(this.itemStackField3)
                  );
                  this.listField8.addFirst(this.itemStackField3);
               }

               this.itemStackField3 = null;
            }
         }

         OpenScreenS2CPacket openScreenS2CPacket = (OpenScreenS2CPacket)packetEvent.getPacket_1015();
         if (openScreenS2CPacket instanceof OpenScreenS2CPacket openScreenS2CPacket1) {
            if (!(minecraft.currentScreen instanceof GenericContainerScreen)) {
               this.integerField1 = 0;
            }

            this.integerField10 = openScreenS2CPacket1.getSyncId();
         }

         PlaySoundS2CPacket playSoundS2CPacket = (PlaySoundS2CPacket)packetEvent.getPacket_1015();
         if (playSoundS2CPacket instanceof PlaySoundS2CPacket playSoundS2CPacket1
            && ((SoundEvent)playSoundS2CPacket1.getSound().value()).id().getPath().equals("block.note_block.basedrum")) {
            this.integerField10 = minecraft.player.currentScreenHandler.syncId;
            packetEvent.performAction_0597(true);
         }
      }
   }

   @EventTarget
   public void onContainerEvent_1848(ContainerEvent containerEvent) {
      if (containerEvent.getPhase_0988() == ContainerEvent.Phase.POST) {
         String string = containerEvent.getScreen_0992().getTitle().getString().replaceAll("§.", "").toLowerCase().trim();
         if (string.contains("аукцион")) {
            HandledScreenAccessor handledScreenAccessor = (HandledScreenAccessor)containerEvent.getScreen_0992();
            DrawContext drawContext = containerEvent.getContext_0991();
            int i = handledScreenAccessor.getBackgroundHeight() / 18;
            int j = handledScreenAccessor.getX() - 22;
            int k = handledScreenAccessor.getY() + 3;
            int l = k + i * 18;
            int[][] m = new int[][]{
               {j - 2, k, j + 20, l, -3750202},
               {j, k - 2, j + 18, l + 2, -3750202},
               {j - 1, k - 1, j + 19, k, -3750202},
               {j - 1, l, j + 19, l + 1, -3750202},
               {j, k - 2, j + 18, k - 1, -1},
               {j - 1, k - 1, j, k, -1},
               {j - 2, k, j - 1, l, -1},
               {j, l + 1, j + 18, l + 2, -11184811},
               {j + 18, l, j + 19, l + 1, -11184811},
               {j + 19, k, j + 20, l, -11184811}
            };

            for (int[] n : m) {
               drawContext.fill(n[0], n[1], n[2], n[3], n[4]);
            }

            for (int o = 0; o < i; o++) {
               int p = k + o * 18;
               drawContext.drawGuiTexture(RenderPipelines.GUI_TEXTURED, Identifier.ofVanilla("container/slot"), j, p, 18, 18);
               if (o < this.listField8.size()) {
                  ItemStack itemStack = this.listField8.get(o);
                  NeneClient.getInstance()
                     .getProcessors()
                     .getRenderComponent185Field4_0678()
                     .performAction_2592(drawContext, itemStack, j + 1, p + 1, 0, 1.0F, 1.0F, true);
                  if (UtilComponent279.checkCondition_3576(containerEvent.getMouseX_0986(), containerEvent.getMouseY_0994(), j + 1, p + 1, 16.0F, 16.0F)) {
                     drawContext.fillGradient(j + 1, p + 1, j + 17, p + 17, -2130706433, -2130706433);
                     drawContext.drawItemTooltip(minecraft.textRenderer, itemStack, containerEvent.getMouseX_0986(), containerEvent.getMouseY_0994());
                  }
               }
            }
         }
      }
   }

   private void performAction_1854(ScreenHandler screenHandler, int i, SlotActionType slotActionType) {
      minecraft.player
         .networkHandler
         .sendPacket(
            new ClickSlotC2SPacket(
               screenHandler.syncId, screenHandler.getRevision(), (short)i, (byte)0, slotActionType, Int2ObjectMaps.emptyMap(), ItemStackHash.EMPTY
            )
         );
      this.integerField1 = 0;
   }
}
