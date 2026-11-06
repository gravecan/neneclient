package aethereal.network;

import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.ClickEvent;
import aethereal.event.ContainerEvent;
import aethereal.handler.HandlerAnnotation107;
import aethereal.handler.HandlerComponent104;
import aethereal.util.UtilComponent279;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;
import platform.inject.accessors.HandledScreenAccessor;

@HandlerAnnotation107
public class NetworkComponent172 extends HandlerComponent104 implements MinecraftAccess {
   private boolean flagField1;

   @EventTarget
   public void onContainerEvent_2437(ContainerEvent containerEvent) {
      this.flagField1 = false;
      if (containerEvent.getPhase_0988() == ContainerEvent.Phase.POST && containerEvent.getHandler_0989() instanceof GenericContainerScreenHandler) {
         TranslatableTextContent translatableTextContent = (TranslatableTextContent)containerEvent.getTitle_0990().getContent();
         if (translatableTextContent instanceof TranslatableTextContent translatableTextContent1
            && (translatableTextContent1.getKey().equals("container.chest") || translatableTextContent1.getKey().equals("container.chestDouble"))) {
            HandledScreenAccessor handledScreenAccessor = (HandledScreenAccessor)containerEvent.getScreen_0992();
            float f = handledScreenAccessor.getX() + handledScreenAccessor.getBackgroundWidth() - 17;
            float g = handledScreenAccessor.getY() + 5;
            this.flagField1 = UtilComponent279.checkCondition_3576(containerEvent.getMouseX_0986(), containerEvent.getMouseY_0994(), f, g, 10.0F, 10.0F);
            NeneClient.getInstance()
               .getProcessors()
               .getRenderComponent184Field3_0677()
               .performAction_2585(
                  containerEvent.getContext_0991().getMatrices(),
                  Identifier.of(
                     "neneclient", this.flagField1 ? "pictures/minecraft/distribution_button_hovered.png" : "pictures/minecraft/distribution_button.png"
                  ),
                  f,
                  g,
                  10.0F,
                  10.0F,
                  0.0F,
                  -1
               );
            if (this.flagField1) {
               containerEvent.getContext_0991()
                  .drawTooltip(
                     containerEvent.getScreen_0992().getTextRenderer(),
                     List.of(Text.of("Отсортировать предметы")),
                     containerEvent.getMouseX_0986(),
                     containerEvent.getMouseY_0994()
                  );
            }
         }
      }
   }

   @EventTarget
   public void onClickEvent_2436(ClickEvent clickEvent) {
      if (clickEvent.checkCondition_0983()
         && this.flagField1
         && (
            minecraft.player.currentScreenHandler instanceof GenericContainerScreenHandler
               ? (GenericContainerScreenHandler)minecraft.player.currentScreenHandler
               : null
         ) instanceof GenericContainerScreenHandler genericContainerScreenHandler) {
         List list = genericContainerScreenHandler.slots.subList(0, genericContainerScreenHandler.getRows() * 9);
         ArrayList arrayList = new ArrayList<>(list.stream().map(slot2 -> slot2.getStack().getItem()).filter(item1 -> item1 != Items.AIR).toList());
         DefaultedRegistry defaultedRegistry = Registries.ITEM;
         arrayList.sort(Comparator.comparingInt(item1 -> defaultedRegistry.getRawId(item1)));

         for (int i = 0; i < arrayList.size(); i++) {
            Item item = (Item)arrayList.get(i);
            Slot slot = (Slot)list.get(i);
            if (slot.getStack().getItem() != item) {
               Slot slot1 = list.stream().skip(i + 1).filter(slot2 -> slot2.getStack().getItem() == item).findFirst().orElseThrow();
               minecraft.interactionManager.clickSlot(genericContainerScreenHandler.syncId, slot1.id, 0, SlotActionType.PICKUP, minecraft.player);
               minecraft.interactionManager.clickSlot(genericContainerScreenHandler.syncId, slot.id, 0, SlotActionType.PICKUP, minecraft.player);
               minecraft.interactionManager.clickSlot(genericContainerScreenHandler.syncId, slot1.id, 0, SlotActionType.PICKUP, minecraft.player);
            }
         }
      }
   }
}
