package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;

public class ContainerEvent extends CancellableEvent implements Event {
   private final HandledScreen<?> screen;
   private final ScreenHandler handler;
   private final DrawContext context;
   private final List<Slot> slots;
   private final int mouseX;
   private final int mouseY;
   private final ContainerEvent.Phase phase;
   private Text title;

   public HandledScreen<?> getScreen_0992() {
      return this.screen;
   }

   public ScreenHandler getHandler_0989() {
      return this.handler;
   }

   public DrawContext getContext_0991() {
      return this.context;
   }

   public List<Slot> getSlots_0987() {
      return this.slots;
   }

   public int getMouseX_0986() {
      return this.mouseX;
   }

   public int getMouseY_0994() {
      return this.mouseY;
   }

   public ContainerEvent.Phase getPhase_0988() {
      return this.phase;
   }

   public void setTitle_0993(Text text) {
      this.title = text;
   }

   public Text getTitle_0990() {
      return this.title;
   }

   public ContainerEvent(HandledScreen<?> handledScreen, DrawContext drawContext, int i, int j, ContainerEvent.Phase phase) {
      this.screen = handledScreen;
      this.handler = handledScreen.getScreenHandler();
      this.slots = handledScreen.getScreenHandler().slots;
      this.context = drawContext;
      this.mouseX = i;
      this.mouseY = j;
      this.phase = phasex;
      this.title = handledScreen.getTitle();
   }

   public ContainerEvent(HandledScreen<?> handledScreen, Text text) {
      this.screen = handledScreen;
      this.handler = handledScreen.getScreenHandler();
      this.slots = handledScreen.getScreenHandler().slots;
      this.context = null;
      this.mouseX = 0;
      this.mouseY = 0;
      this.phase = ContainerEvent.Phase.TITLE;
      this.title = text;
   }

   public enum Phase {
      PRE,
      POST,
      TITLE;
   }
}
