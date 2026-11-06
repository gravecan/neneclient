package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.FloatSetting;
import aethereal.ui.screen.ScreenComponent218;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent268;
import aethereal.util.UtilComponent270;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.sync.ItemStackHash;
import platform.inject.invokers.ClientPlayerInteractionManagerInvoker;

@ModuleRegister(name = "Server Joiner", description = "Автоматически подключается к указанному серверу", category = Category.Misc)
public class ServerJoiner extends Module implements MinecraftAccess {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Выберите сервер", "SpookyTime", "SpookyTime", "ReallyWorld");
   private final FloatSetting floatSettingField2 = new FloatSetting("Укажите номер грифа (1-54)", 1.0F, 1.0F, 54.0F, 1.0F)
      .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("ReallyWorld"));
   private final UtilComponent268 utilComponent268Field3 = new UtilComponent268();
   private int integerField4 = -1;

   public ChoiceSetting getChoiceSettingField1_1988() {
      return this.choiceSettingField1;
   }

   public FloatSetting getFloatSettingField2_1984() {
      return this.floatSettingField2;
   }

   public UtilComponent268 getUtilComponent268Field3_1992() {
      return this.utilComponent268Field3;
   }

   public int getIntegerField4_1987() {
      return this.integerField4;
   }

   public ServerJoiner() {
      this.addSettings(this.choiceSettingField1, this.floatSettingField2);
   }

   @EventTarget
   public void onEventComponent074_1990(EventComponent074 eventComponent074) {
      if (!(minecraft.currentScreen instanceof ScreenComponent218)) {
         if (this.choiceSettingField1.checkCondition_2850("SpookyTime")) {
            if (UtilComponent252.computeValue_3399().contains("Хаб")) {
               int k = UtilComponent270.computeValue_3539(Items.COMPASS, true);
               if (k >= 0 && k <= 8 && minecraft.currentScreen == null) {
                  minecraft.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(k));
                  ((ClientPlayerInteractionManagerInvoker)minecraft.interactionManager)
                     .invokeSendSequencedPacket(
                        minecraft.world,
                        l -> new PlayerInteractItemC2SPacket(minecraft.player.getActiveHand(), l, minecraft.player.getYaw(), minecraft.player.getPitch())
                     );
               }

               if (this.integerField4 != -1) {
                  minecraft.player
                     .networkHandler
                     .sendPacket(
                        new ClickSlotC2SPacket(this.integerField4, 0, (short)13, (byte)0, SlotActionType.PICKUP, Int2ObjectMaps.emptyMap(), ItemStackHash.EMPTY)
                     );
                  this.integerField4 = -1;
                  return;
               }

               return;
            }

            if (!UtilComponent252.computeValue_3399().isEmpty() && !UtilComponent252.computeValue_3399().contains("Режим: Хаб # ")) {
               UtilComponent265.performAction_3505("Вы находитесь не в хабе SpookyTime, а значит модуль выключается!");
               this.toggle();
               return;
            }

            return;
         }

         if (this.choiceSettingField1.checkCondition_2850("ReallyWorld")) {
            if (UtilComponent252.computeValue_3399().isEmpty()) {
               int i = UtilComponent270.computeValue_3539(Items.COMPASS, true);
               if (i >= 0 && i <= 8 && minecraft.currentScreen == null) {
                  minecraft.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(i));
                  ((ClientPlayerInteractionManagerInvoker)minecraft.interactionManager)
                     .invokeSendSequencedPacket(
                        minecraft.world,
                        l -> new PlayerInteractItemC2SPacket(minecraft.player.getActiveHand(), l, minecraft.player.getYaw(), minecraft.player.getPitch())
                     );
               }

               GenericContainerScreen genericContainerScreen = (GenericContainerScreen)minecraft.currentScreen;
               if (genericContainerScreen instanceof GenericContainerScreen genericContainerScreen1) {
                  GenericContainerScreenHandler genericContainerScreenHandler = (GenericContainerScreenHandler)genericContainerScreen1.getScreenHandler();
                  if (genericContainerScreen1.getTitle().getString().contains("» Выбор сервера")) {
                     minecraft.player
                        .networkHandler
                        .sendPacket(
                           new ClickSlotC2SPacket(
                              genericContainerScreenHandler.syncId,
                              genericContainerScreenHandler.getRevision(),
                              (short)21,
                              (byte)0,
                              SlotActionType.PICKUP,
                              Int2ObjectMaps.emptyMap(),
                              ItemStackHash.EMPTY
                           )
                        );
                  }

                  for (int j = 0; j < genericContainerScreenHandler.getRows() * 9; j++) {
                     Slot slot = (Slot)genericContainerScreenHandler.slots.get(j);
                     if (slot.getStack().getName().getString().contains("ГРИФ #" + this.floatSettingField2.getCurrentValue().intValue() + " (1.16.5+)")
                        && genericContainerScreen1.getTitle().getString().contains("Выбор мира грифа ")) {
                        if (this.utilComponent268Field3.checkCondition_3522(5500L)) {
                           minecraft.player
                              .networkHandler
                              .sendPacket(
                                 new ClickSlotC2SPacket(
                                    genericContainerScreenHandler.syncId,
                                    genericContainerScreenHandler.getRevision(),
                                    (short)slot.id,
                                    (byte)0,
                                    SlotActionType.PICKUP,
                                    Int2ObjectMaps.emptyMap(),
                                    ItemStackHash.EMPTY
                                 )
                              );
                           this.utilComponent268Field3.performAction_3524();
                           return;
                        }

                        return;
                     }
                  }

                  return;
               }

               return;
            }

            UtilComponent265.performAction_3505("Вы находитесь не в лобби ReallyWorld, а значит модуль выключается!");
            this.toggle();
         }
      }
   }

   @EventTarget
   public void onPacketEvent_1991(PacketEvent packetEvent) {
      if (this.choiceSettingField1.checkCondition_2850("SpookyTime") && packetEvent.checkCondition_1013()) {
         OpenScreenS2CPacket openScreenS2CPacket = (OpenScreenS2CPacket)packetEvent.getPacket_1015();
         if (openScreenS2CPacket instanceof OpenScreenS2CPacket openScreenS2CPacket1) {
            if (openScreenS2CPacket1.getName().getString().contains("☫ Выберите режим:")) {
               this.integerField4 = openScreenS2CPacket1.getSyncId();
            }

            packetEvent.performAction_0597(true);
         }
      }
   }
}
