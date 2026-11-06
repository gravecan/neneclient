package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.ContainerEvent;
import aethereal.event.EventComponent093;
import aethereal.mixin.MixinContract161;
import aethereal.module.misc.AutoBuy;
import aethereal.module.player.ItemScroller;
import aethereal.render.Animations;
import aethereal.ui.screen.ScreenComponent225;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.input.KeyInput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import platform.inject.accessors.HandledScreenAccessor;
import platform.inject.accessors.ScreenAccessor;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> {
   @Unique
   private ButtonWidget buttonFold;
   @Unique
   private ButtonWidget buttonTake;
   @Unique
   private ButtonWidget buttonDrop;
   @Shadow
   @Final
   protected T handler;
   @Unique
   private int chestSize;

   @Inject(method = "drawSlot", at = @At("HEAD"))
   private void onDrawSlotHead(DrawContext drawContext, Slot slot, int i, int j, CallbackInfo callbackInfo) {
      Animations animations = NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502();
      if (animations.isEnabled() && animations.getMultiSelectSettingField1_2544().computeValue_2852("Items").getCurrentValue()) {
         boolean bl = slot == ((HandledScreenAccessor)this).getFocusedSlot() && slot.hasStack();
         float f = ((MixinContract161)slot).getAnimation().computeValue_2533(bl ? 1.25F : 1.0F, bl ? 1.25F : 0.75F);
         drawContext.getMatrices().pushMatrix();
         drawContext.getMatrices().translate(slot.x + 8.0F, slot.y + 8.0F);
         drawContext.getMatrices().scale(f, f);
         drawContext.getMatrices().translate(-(slot.x + 8.0F), -(slot.y + 8.0F));
      }
   }

   @Inject(method = "drawSlot", at = @At("RETURN"))
   private void onDrawSlotTail(DrawContext drawContext, Slot slot, int i, int j, CallbackInfo callbackInfo) {
      Animations animations = NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502();
      if (animations.isEnabled() && animations.getMultiSelectSettingField1_2544().computeValue_2852("Items").getCurrentValue()) {
         drawContext.getMatrices().popMatrix();
      }
   }

   @ModifyArg(
      method = "drawForeground",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)V",
         ordinal = 0
      ),
      index = 1
   )
   private Text modifyTitle(Text text) {
      ContainerEvent containerEvent = new ContainerEvent((HandledScreen<?>)this, text);
      EventBus.post(containerEvent);
      return containerEvent.getTitle_0990();
   }

   @Inject(method = "init", at = @At("TAIL"))
   private void onInit(CallbackInfo callbackInfo) {
      if (this.handler instanceof GenericContainerScreenHandler || this.handler instanceof ShulkerBoxScreenHandler) {
         AutoBuy autoBuy = NeneClient.getInstance().getProcessors().getModules().getAutoBuyField54_0425();
         HandledScreenAccessor handledScreenAccessor = (HandledScreenAccessor)this;
         ScreenAccessor screenAccessor = (ScreenAccessor)this;
         int i;
         if ((this.handler instanceof GenericContainerScreenHandler ? (GenericContainerScreenHandler)this.handler : null) instanceof GenericContainerScreenHandler genericContainerScreenHandler
            )
          {
            i = genericContainerScreenHandler.getRows() * 9;
         } else {
            i = this.handler instanceof ShulkerBoxScreenHandler ? 27 : this.chestSize;
         }

         this.chestSize = i;
         this.buttonFold = screenAccessor.invokeAddDrawableChild(
            ButtonWidget.builder(Text.literal("Fold"), buttonWidget -> this.onFoldClick())
               .dimensions(
                  (screenAccessor.getWidth() + handledScreenAccessor.getBackgroundWidth()) / 2 + 5,
                  (screenAccessor.getHeight() - handledScreenAccessor.getBackgroundHeight()) / 2,
                  100,
                  20
               )
               .build()
         );
         this.buttonTake = screenAccessor.invokeAddDrawableChild(
            ButtonWidget.builder(Text.literal("Pick up"), buttonWidget -> this.onTakeClick())
               .dimensions(
                  (screenAccessor.getWidth() + handledScreenAccessor.getBackgroundWidth()) / 2 + 5,
                  (screenAccessor.getHeight() - handledScreenAccessor.getBackgroundHeight()) / 2 + 24,
                  100,
                  20
               )
               .build()
         );
         this.buttonDrop = screenAccessor.invokeAddDrawableChild(
            ButtonWidget.builder(Text.literal("Throw it away"), buttonWidget -> this.onDropClick())
               .dimensions(
                  (screenAccessor.getWidth() + handledScreenAccessor.getBackgroundWidth()) / 2 + 5,
                  (screenAccessor.getHeight() - handledScreenAccessor.getBackgroundHeight()) / 2 + 48,
                  100,
                  20
               )
               .build()
         );
         if (autoBuy.isEnabled()
            && (
               screenAccessor.getTitle().getString().toLowerCase().contains("auction")
                  || screenAccessor.getTitle().getString().toLowerCase().contains("Category: real things")
            )) {
            screenAccessor.invokeAddDrawableChild(
               (T)ButtonWidget.builder(this.statusMessage("AutoBuy", autoBuy.isFlagField4_1851()), buttonWidget -> {
                     autoBuy.setFlagField4_1857(!autoBuy.isFlagField4_1851());
                     buttonWidget.setMessage(this.statusMessage("AutoBuy", autoBuy.isFlagField4_1851()));
                  })
                  .dimensions(screenAccessor.getWidth() / 2 - 50, (screenAccessor.getHeight() + handledScreenAccessor.getBackgroundHeight()) / 2 + 4, 100, 20)
                  .build()
            );
         }
      }
   }

   @Unique
   private Text statusMessage(String string, boolean bl) {
      return Text.literal(string + ": ").append(Text.literal(bl ? "ON" : "OFF").formatted(bl ? Formatting.GREEN : Formatting.RED));
   }

   @Unique
   private boolean hasStack(Slot slot) {
      return slot.hasStack() && !slot.getStack().isEmpty();
   }

   @Unique
   private void onFoldClick() {
      for (int i = this.chestSize; i < this.handler.slots.size(); i++) {
         Slot slot = (Slot)this.handler.slots.get(i);
         if (this.hasStack(slot)) {
            MinecraftAccess.minecraft
               .interactionManager
               .clickSlot(this.handler.syncId, slot.id, 0, SlotActionType.QUICK_MOVE, MinecraftAccess.minecraft.player);
         }
      }
   }

   @Unique
   private void onTakeClick() {
      for (int i = 0; i < this.chestSize; i++) {
         Slot slot = (Slot)this.handler.slots.get(i);
         if (this.hasStack(slot)) {
            MinecraftAccess.minecraft
               .interactionManager
               .clickSlot(this.handler.syncId, slot.id, 0, SlotActionType.QUICK_MOVE, MinecraftAccess.minecraft.player);
         }
      }
   }

   @Unique
   private void onDropClick() {
      for (int i = 0; i < this.chestSize; i++) {
         Slot slot = (Slot)this.handler.slots.get(i);
         if (this.hasStack(slot)) {
            MinecraftAccess.minecraft.interactionManager.clickSlot(this.handler.syncId, slot.id, 1, SlotActionType.THROW, MinecraftAccess.minecraft.player);
         }
      }
   }

   @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
   private void onKeyPressed(KeyInput keyInput, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      int i = keyInput.key();
      int j = keyInput.scancode();
      int k = keyInput.comp_4797();
      EventComponent093 eventComponent093 = new EventComponent093(i, j, 0, k);
      EventBus.post(eventComponent093);
      if (eventComponent093.checkCondition_0596()) {
         callbackInfoReturnable.setReturnValue(true);
      } else {
         Slot slot;
         if (MinecraftAccess.minecraft.options.dropKey != null
            && MinecraftAccess.minecraft.options.dropKey.matchesKey(new KeyInput(i, j, 0))
            && (k & 2) != 0
            && (k & 1) != 0
            && (slot = ((HandledScreenAccessor)this).getFocusedSlot()) != null
            && slot.hasStack()) {
            this.handleDropItems(slot.getStack().getItem());
            callbackInfoReturnable.setReturnValue(true);
         }
      }
   }

   @Unique
   private void handleDropItems(Item item) {
      for (Slot slot : this.handler.slots) {
         ItemStack itemStack = slot.getStack();
         if (!itemStack.isEmpty() && itemStack.getItem() == item) {
            MinecraftAccess.minecraft.interactionManager.clickSlot(this.handler.syncId, slot.id, 1, SlotActionType.THROW, MinecraftAccess.minecraft.player);
         }
      }
   }

   @Inject(method = "onMouseClick", at = @At("HEAD"), cancellable = true)
   private void onMouseClick(Slot slot, int i, int j, SlotActionType slotActionType, CallbackInfo callbackInfo) {
      ScreenComponent225 screenComponent225 = NeneClient.getInstance().getProcessors().getModules().getAutoSwap_0406().getScreenComponent225Field4_1584();
      if (screenComponent225.isFlagField2_3253() && !slot.getStack().isEmpty()) {
         screenComponent225.performAction_3254(screenComponent225.getScreenComponent223Field1_3250(), slot.getStack());
         screenComponent225.performAction_3248(-1);
         screenComponent225.setFlagField2_3251(false);
         MinecraftAccess.minecraft.setScreen(screenComponent225);
         callbackInfo.cancel();
      }
   }

   @Inject(method = "removed", at = @At("HEAD"))
   private void onRemoved(CallbackInfo callbackInfo) {
      ScreenComponent225 screenComponent225 = NeneClient.getInstance().getProcessors().getModules().getAutoSwap_0406().getScreenComponent225Field4_1584();
      if (screenComponent225.isFlagField2_3253()) {
         screenComponent225.setFlagField2_3251(false);
      }
   }

   @Inject(method = "render", at = @At("HEAD"))
   private void onRender(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      if (this.buttonFold != null) {
         this.buttonFold.active = this.handler.slots.subList(this.chestSize, this.handler.slots.size()).stream().anyMatch(this::hasStack);
      }

      if (this.buttonTake != null && this.buttonDrop != null) {
         this.buttonTake.active = this.handler.slots.subList(0, this.chestSize).stream().anyMatch(this::hasStack);
         this.buttonDrop.active = this.handler.slots.subList(0, this.chestSize).stream().anyMatch(this::hasStack);
      }

      EventBus.post(new ContainerEvent((HandledScreen<?>)this, drawContext, i, j, ContainerEvent.Phase.PRE));
      ItemScroller itemScroller = NeneClient.getInstance().getProcessors().getModules().getItemScrollerField73_0475();
      if (itemScroller.isEnabled()
         && ((HandledScreenAccessor)this).getFocusedSlot() != null
         && ((HandledScreenAccessor)this).getFocusedSlot().hasStack()
         && GLFW.glfwGetMouseButton(MinecraftAccess.minecraft.getWindow().getHandle(), 0) == 1
         && GLFW.glfwGetKey(MinecraftAccess.minecraft.getWindow().getHandle(), 340) == 1
         && itemScroller.getUtilComponent268Field2_2200().checkCondition_3522(itemScroller.getFloatSettingField1_2199().getCurrentValue().intValue())) {
         MinecraftAccess.minecraft
            .interactionManager
            .clickSlot(this.handler.syncId, ((HandledScreenAccessor)this).getFocusedSlot().id, 0, SlotActionType.QUICK_MOVE, MinecraftAccess.minecraft.player);
         itemScroller.getUtilComponent268Field2_2200().performAction_3524();
      }
   }

   @Inject(method = "render", at = @At("TAIL"))
   private void onRenderTail(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      EventBus.post(new ContainerEvent((HandledScreen<?>)this, drawContext, i, j, ContainerEvent.Phase.POST));
   }
}
