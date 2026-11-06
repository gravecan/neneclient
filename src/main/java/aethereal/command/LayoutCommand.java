package aethereal.command;

import aethereal.core.EventTarget;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.util.UtilComponent265;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.command.CommandSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;
import platform.inject.accessors.SlotAccessor;

@CommandRegister(name = "layout")
public class LayoutCommand extends Command {
   private final List<LayoutCommand.NestedType296> listField1 = new ArrayList<>();
   private final List<LayoutCommand.NestedType296> listField2 = new ArrayList<>();
   private int integerField3;

   public List<LayoutCommand.NestedType296> getListField1_0266() {
      return this.listField1;
   }

   private void performAction_0285() {
      NeneClient.getInstance().getProcessors().getModules().performAction_0395("default");
   }

   private void performAction_0280(List<LayoutCommand.NestedType296> list) {
      this.listField2.clear();
      this.listField2.addAll(list);
      this.integerField3 = 0;
   }

   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(
                        ((LiteralArgumentBuilder)this.computeValue_0173("save").executes(commandContext -> {
                           UtilComponent265.performAction_3505("Usage: .layout save <name>");
                           return 1;
                        })).then(this.computeValue_0163("Name").executes(commandContext -> {
                           String string = this.computeValue_0170(commandContext, "Name");
                           List list = this.listField1;
                           list.removeIf(nestedType296 -> nestedType296.getName_0292().equalsIgnoreCase(string));

                           for (Slot slot : minecraft.player.playerScreenHandler.slots) {
                              if (slot.inventory == minecraft.player.getInventory() && !slot.getStack().isEmpty()) {
                                 list.add(new LayoutCommand.NestedType296(string, slot.getStack().copy(), slot.id));
                              }
                           }

                           this.performAction_0285();
                           UtilComponent265.performAction_3505("&c layout" + string + "&7saved.");
                           return 1;
                        }))
                     ))
                     .then(
                        ((LiteralArgumentBuilder)this.computeValue_0173("load").executes(commandContext -> {
                              UtilComponent265.performAction_3505("Usage: .layout load <name>");
                              return 1;
                           }))
                           .then(
                              this.computeValue_0163("Name")
                                 .suggests(this.computeValue_0175(this::getListField1_0266, nestedType296 -> nestedType296.getName_0292()))
                                 .executes(commandContext -> {
                                    String string = this.computeValue_0170(commandContext, "Name");
                                    List list = this.listField1
                                       .stream()
                                       .filter(nestedType296 -> nestedType296.getName_0292().equalsIgnoreCase(string))
                                       .toList();
                                    if (list.isEmpty()) {
                                       UtilComponent265.performAction_3505("&c layout" + string + "&7not found");
                                       return 1;
                                    } else {
                                       this.performAction_0280(list);
                                       return 1;
                                    }
                                 })
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)this.computeValue_0173("remove").executes(commandContext -> {
                           UtilComponent265.performAction_3505("Usage: .layout remove <name>");
                           return 1;
                        }))
                        .then(
                           this.computeValue_0163("Name")
                              .suggests(this.computeValue_0175(this::getListField1_0266, nestedType296 -> nestedType296.getName_0292()))
                              .executes(commandContext -> {
                                 String string = this.computeValue_0170(commandContext, "Name");
                                 List list = this.listField1;
                                 if (list.stream().noneMatch(nestedType296 -> nestedType296.getName_0292().equalsIgnoreCase(string))) {
                                    UtilComponent265.performAction_3505("&c layout" + string + "&7not found");
                                    return 1;
                                 } else {
                                    list.removeIf(nestedType296 -> nestedType296.getName_0292().equalsIgnoreCase(string));
                                    this.performAction_0285();
                                    UtilComponent265.performAction_3505("&c layout" + string + "&7deleted");
                                    return 1;
                                 }
                              })
                        )
                  ))
               .then(
                  this.computeValue_0173("list")
                     .executes(
                        commandContext -> {
                           List list = this.listField1;
                           if (list.isEmpty()) {
                              UtilComponent265.performAction_3505("The list of layouts is empty.");
                              return 1;
                           } else {
                              UtilComponent265.performAction_3505("Saved layouts:");
                              list.stream()
                                 .map(nestedType296 -> nestedType296.getName_0292())
                                 .distinct()
                                 .forEach(string -> UtilComponent265.performAction_3505("— &c" + string));
                              return 1;
                           }
                        }
                     )
               ))
            .then(this.computeValue_0173("clear").executes(commandContext -> {
               this.listField1.clear();
               this.performAction_0285();
               UtilComponent265.performAction_3505("Layouts have been cleared.");
               return 1;
            })))
         .executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .layout <save|load|remove|list|clear> <name>");
            return 1;
         });
   }

   @EventTarget
   public void onEventComponent074_0275(EventComponent074 eventComponent074) {
      if (!this.listField2.isEmpty()) {
         int i = this.integerField3--;
         if (i > 1) {
            return;
         }

         this.integerField3 = 2;
         DefaultedList defaultedList = minecraft.player.playerScreenHandler.slots;
         ArrayList arrayList = new ArrayList();

         for (LayoutCommand.NestedType296 nestedType296 : this.listField2) {
            Slot slot = (Slot)defaultedList.get(nestedType296.getSlotId_0294());
            Item item = nestedType296.getStack_0293().getItem();
            if (slot.getStack().getItem() != item) {
               DefaultedList defaultedList1 = defaultedList;
               Slot slot1 = defaultedList1.stream()
                  .filter(slot2 -> ((SlotAccessor)slot2).getInventory() == minecraft.player.getInventory())
                  .filter(slot2 -> slot2.getStack().getItem() == item)
                  .filter(
                     slot2 -> this.listField2
                        .stream()
                        .noneMatch(
                           nestedType2961 -> nestedType2961.getSlotId_0294() == slot2.id
                              && nestedType2961.getStack_0293().getItem() == slot2.getStack().getItem()
                        )
                  )
                  .findFirst()
                  .orElse(null);
               if (slot1 != null) {
                  int j = slot1.id != 36 && slot.id != 36 ? 0 : (slot1.id != 37 && slot.id != 37 ? 1 : 2);
                  minecraft.interactionManager.clickSlot(minecraft.player.playerScreenHandler.syncId, slot1.id, j, SlotActionType.SWAP, minecraft.player);
                  minecraft.interactionManager.clickSlot(minecraft.player.playerScreenHandler.syncId, slot.id, j, SlotActionType.SWAP, minecraft.player);
                  minecraft.interactionManager.clickSlot(minecraft.player.playerScreenHandler.syncId, slot1.id, j, SlotActionType.SWAP, minecraft.player);
                  if (this.listField2
                     .stream()
                     .noneMatch(
                        nestedType2961 -> ((Slot)defaultedList.get(nestedType2961.getSlotId_0294())).getStack().getItem()
                           != nestedType2961.getStack_0293().getItem()
                     )) {
                     minecraft.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(minecraft.player.playerScreenHandler.syncId));
                     this.listField2.clear();
                     return;
                  }

                  return;
               }

               String string = nestedType296.getStack_0293().getName().getString();
               if (!arrayList.contains(string)) {
                  arrayList.add(string);
               }
            }
         }

         if (arrayList.isEmpty()) {
            UtilComponent265.performAction_3505("The layout is laid out.");
         } else {
            UtilComponent265.performAction_3505("The layout is laid out, there is not enough:");
            Iterator iterator = arrayList.iterator();

            while (iterator.hasNext()) {
               UtilComponent265.performAction_3505("- &c" + (String)iterator.next());
            }
         }

         this.listField2.clear();
      }
   }

   public static class NestedType296 {
      private final String name;
      private final ItemStack stack;
      private final int slotId;

      public NestedType296(String string, ItemStack itemStack, int i) {
         this.name = string;
         this.stack = itemStack;
         this.slotId = i;
      }

      public String getName_0292() {
         return this.name;
      }

      public ItemStack getStack_0293() {
         return this.stack;
      }

      public int getSlotId_0294() {
         return this.slotId;
      }
   }
}
