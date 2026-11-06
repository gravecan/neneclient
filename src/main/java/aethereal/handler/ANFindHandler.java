package aethereal.handler;

import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.ClickEvent;
import aethereal.event.ContainerEvent;
import aethereal.event.EventComponent074;
import aethereal.ui.shader.ShaderComponent231;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent279;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.sync.ItemStackHash;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import platform.inject.accessors.HandledScreenAccessor;

@HandlerAnnotation107
public class ANFindHandler extends HandlerComponent104 implements MinecraftAccess {
   private final List<ANFindHandler.NestedType320> listField1 = List.of(
      new ANFindHandler.NestedType320("Teams x1"),
      new ANFindHandler.NestedType320("Teams x2"),
      new ANFindHandler.NestedType320("Teams x3"),
      new ANFindHandler.NestedType320("Commands x5"),
      new ANFindHandler.NestedType320("Teams x10")
   );
   private boolean flagField2;
   private boolean flagField3;
   private ANFindHandler.Phase phaseField4;
   private int integerField5;

   @EventTarget
   public void onContainerEvent_1116(ContainerEvent containerEvent) {
      this.flagField2 = false;
      if (containerEvent.getPhase_0988() == ContainerEvent.Phase.POST
         && (
            containerEvent.getScreen_0992().getTitle().getString().contains("☬ Select mode:")
               || containerEvent.getScreen_0992().getTitle().getString().contains("☬ Select mode type:")
         )) {
         HandledScreenAccessor handledScreenAccessor = (HandledScreenAccessor)containerEvent.getScreen_0992();
         float f = handledScreenAccessor.getX() + handledScreenAccessor.getBackgroundWidth() - 17;
         float g = handledScreenAccessor.getY() + 5;
         this.flagField2 = UtilComponent279.checkCondition_3576(containerEvent.getMouseX_0986(), containerEvent.getMouseY_0994(), f, g, 10.0F, 10.0F);
         NeneClient.getInstance()
            .getProcessors()
            .getRenderComponent184Field3_0677()
            .performAction_2585(
               containerEvent.getContext_0991().getMatrices(),
               Identifier.of("neneclient", this.flagField2 ? "pictures/minecraft/join_button_hovered.png" : "pictures/minecraft/join_button.png"),
               f,
               g,
               10.0F,
               10.0F,
               0.0F,
               -1
            );
         if (this.flagField2) {
            containerEvent.getContext_0991()
               .drawTooltip(
                  containerEvent.getScreen_0992().getTextRenderer(),
                  List.of(Text.of("Auto-search for anarchy with the least online")),
                  containerEvent.getMouseX_0986(),
                  containerEvent.getMouseY_0994()
               );
         }
      }
   }

   @EventTarget
   public void onClickEvent_1115(ClickEvent clickEvent) {
      if (clickEvent.checkCondition_0983() && this.flagField2) {
         HandledScreen handledScreen = (HandledScreen)minecraft.currentScreen;
         if (handledScreen instanceof HandledScreen handledScreen1
            && (handledScreen1.getTitle().getString().contains("☬ Select mode:") || handledScreen1.getTitle().getString().contains("☬ Select mode type:"))) {
            this.flagField3 = !this.flagField3;
            if (this.flagField3) {
               this.phaseField4 = ANFindHandler.Phase.SELECT_MODE;
               this.integerField5 = 0;
               this.listField1.forEach(nestedType320 -> nestedType320.performAction_1124());
            }

            minecraft.getSoundManager().play(PositionedSoundInstance.ui(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         }
      }
   }

   @EventTarget
   public void onEventComponent074_1117(EventComponent074 eventComponent074) {
      if (this.flagField3) {
         if (minecraft.currentScreen instanceof GenericContainerScreen genericContainerScreen) {
            ANFindHandler.NestedType320 nestedType320 = this.listField1.get(this.integerField5);
            switch (this.phaseField4) {
               case SELECT_MODE:
                  if (this.checkCondition_1122(genericContainerScreen, "Anarchy 1.21.11")) {
                     this.phaseField4 = ANFindHandler.Phase.SELECT_TYPE;
                  }
                  break;
               case SELECT_TYPE:
                  if (this.checkCondition_1122(genericContainerScreen, nestedType320.title)) {
                     this.phaseField4 = ANFindHandler.Phase.COLLECT;
                  }
                  break;
               case COLLECT:
                  this.performAction_1121(genericContainerScreen, nestedType320);
            }
         }

         this.flagField3 = false;
      }
   }

   private void performAction_1121(GenericContainerScreen genericContainerScreen, ANFindHandler.NestedType320 nestedType320) {
      boolean bl = false;
      int i = Integer.MAX_VALUE;
      String string = null;

      for (Slot slot : ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots) {
         String string1 = slot.getStack().getName().getString();
         if (slot.getStack().hasGlint() && string1.contains(nestedType320.title)) {
            bl = true;
         }

         if (string1.contains("Anarchy-")) {
            for (Text text : slot.getStack().getTooltip(TooltipContext.DEFAULT, minecraft.player, TooltipType.BASIC)) {
               Matcher matcher = Pattern.compile("Online mode: (\\d+)").matcher(text.getString());
               if (matcher.find()) {
                  int j = Integer.parseInt(matcher.group(1));
                  if (j < i) {
                     i = j;
                     string = string1.replaceAll("§.", "");
                  }
                  break;
               }
            }
         }
      }

      if (bl && string != null) {
         nestedType320.integerField2 = i;
         nestedType320.stringField1 = string;
         int k = this.integerField5 + 1;
         this.integerField5 = k;
         if (k >= this.listField1.size()) {
            this.performAction_1114();
         } else {
            this.phaseField4 = ANFindHandler.Phase.SELECT_TYPE;
         }
      }
   }

   private void performAction_1114() {
      this.flagField3 = false;
      this.listField1
         .stream()
         .filter(nestedType320 -> nestedType320.stringField1 != null)
         .min(Comparator.comparingInt(nestedType320 -> nestedType320.integerField2))
         .ifPresent(
            nestedType320 -> {
               String string = nestedType320.stringField1.replace("»", "").replace("Anarchy-", "").trim();
               if (!string.isEmpty()) {
                  MutableText mutableText = ShaderComponent231.computeValue_3296("Minimum online by team:\n", -7620097, -11503416, 1, 5.0F);
                  this.listField1
                     .stream()
                     .filter(nestedType3201 -> nestedType3201.stringField1 != null)
                     .forEach(
                        nestedType3201 -> mutableText.append(
                           Text.literal(
                              "§7• §f"
                                 + nestedType3201.title.replace("Teams ", "")
                                 + ": "
                                 + nestedType3201.stringField1.trim()
                                 + " — "
                                 + nestedType3201.integerField2
                                 + "players\n"
                           )
                        )
                     );
                  UtilComponent265.performAction_3505(
                     Text.literal("§a✔ §7Successfully connected to anarchy §a#" + string + "§7, with online §a" + nestedType320.integerField2 + "§7 — ")
                        .append(UtilComponent265.computeValue_3506("§c[More]", mutableText))
                  );
                  minecraft.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(minecraft.player.currentScreenHandler.syncId));
                  minecraft.player.networkHandler.sendChatCommand("an" + string);
               }
            }
         );
   }

   private boolean checkCondition_1122(GenericContainerScreen genericContainerScreen, String string) {
      for (Slot slot : ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots) {
         if (slot.getStack().getName().getString().contains(string)) {
            minecraft.player
               .networkHandler
               .sendPacket(
                  new ClickSlotC2SPacket(
                     ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).syncId,
                     ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getRevision(),
                     (short)slot.id,
                     (byte)0,
                     SlotActionType.PICKUP,
                     Int2ObjectMaps.emptyMap(),
                     ItemStackHash.EMPTY
                  )
               );
            return true;
         }
      }

      return false;
   }

   public static class NestedType320 {
      final String title;
      String stringField1;
      int integerField2;

      public String getTitle_1125() {
         return this.title;
      }

      public String getStringField1_1126() {
         return this.stringField1;
      }

      public int getIntegerField2_1127() {
         return this.integerField2;
      }

      public NestedType320(String string) {
         this.title = string;
      }

      public void performAction_1124() {
         this.stringField1 = null;
         this.integerField2 = -1;
      }
   }

   enum Phase {
      SELECT_MODE,
      SELECT_TYPE,
      COLLECT;
   }
}
