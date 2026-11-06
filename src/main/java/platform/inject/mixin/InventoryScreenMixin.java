package platform.inject.mixin;

import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.render.Animations;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends HandledScreen<PlayerScreenHandler> {
   @Unique
   private ButtonWidget button;
   @Unique
   private boolean neneclient$invPushed;

   public InventoryScreenMixin(PlayerScreenHandler playerScreenHandler, PlayerInventory playerInventory, Text text) {
      super(playerScreenHandler, playerInventory, text);
   }

   public void init() {
      super.init();
      this.button = (ButtonWidget)this.addDrawableChild(
         ButtonWidget.builder(Text.literal("Throw everything away"), this::onDropAllClick)
            .dimensions((this.width - 100) / 2, (this.height - this.backgroundHeight) / 2 - 24, 100, 20)
            .build()
      );
   }

   public void renderBackground(DrawContext drawContext, int i, int j, float f) {
      drawContext.getMatrices().pushMatrix();
      drawContext.getMatrices().identity();
      drawContext.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
      drawContext.getMatrices().popMatrix();
      Animations animations = NeneClient.getInstance() != null
            && NeneClient.getInstance().getProcessors() != null
            && NeneClient.getInstance().getProcessors().getModules() != null
         ? NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502()
         : null;
      boolean bl = animations != null
         && animations.isEnabled()
         && animations.getMultiSelectSettingField1_2544().computeValue_2852("Opening inventory").getCurrentValue();
      if (bl) {
         float g = animations.getAnimationField6_2543().getFloatValueField4_2530();
         drawContext.getMatrices().pushMatrix();
         drawContext.getMatrices().translate(this.width / 2.0F, this.height / 2.0F);
         drawContext.getMatrices().scale(g, g);
         drawContext.getMatrices().translate(-this.width / 2.0F, -this.height / 2.0F);
      }

      this.drawBackground(drawContext, f, i, j);
      if (bl) {
         drawContext.getMatrices().popMatrix();
      }
   }

   @WrapOperation(
      method = "drawBackground",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;drawEntity(Lnet/minecraft/client/gui/DrawContext;IIIIIFFFLnet/minecraft/entity/LivingEntity;)V"
      )
   )
   private void wrapDrawEntity(
      DrawContext drawContext, int i, int j, int k, int l, int m, float f, float g, float h, LivingEntity livingEntity, Operation<Void> operation
   ) {
      Animations animations = NeneClient.getInstance() != null
            && NeneClient.getInstance().getProcessors() != null
            && NeneClient.getInstance().getProcessors().getModules() != null
         ? NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502()
         : null;
      if (animations != null
         && animations.isEnabled()
         && animations.getMultiSelectSettingField1_2544().computeValue_2852("Opening inventory").getCurrentValue()) {
         float n = animations.getAnimationField6_2543().getFloatValueField4_2530();
         if (!(n < 0.01F)) {
            float o = this.width / 2.0F;
            float p = this.height / 2.0F;
            int q = Math.round(o + (i - o) * n);
            int r = Math.round(p + (j - p) * n);
            int s = Math.round(o + (k - o) * n);
            int t = Math.round(p + (l - p) * n);
            int u = Math.max(1, Math.round(m * n));
            operation.call(new Object[]{drawContext, q, r, s, t, u, f, g, h, livingEntity});
         }
      } else {
         operation.call(new Object[]{drawContext, i, j, k, l, m, f, g, h, livingEntity});
      }
   }

   @Inject(method = "render", at = @At("HEAD"))
   private void headRender(DrawContext drawContext, int i, int j, float g, CallbackInfo callbackInfo) {
      Animations animations = NeneClient.getInstance() != null
            && NeneClient.getInstance().getProcessors() != null
            && NeneClient.getInstance().getProcessors().getModules() != null
         ? NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502()
         : null;
      if (animations != null
         && animations.isEnabled()
         && animations.getMultiSelectSettingField1_2544().computeValue_2852("Opening inventory").getCurrentValue()) {
         float f = animations.getAnimationField6_2543().getFloatValueField4_2530();
         drawContext.getMatrices().pushMatrix();
         drawContext.getMatrices().translate(this.width / 2.0F, this.height / 2.0F);
         drawContext.getMatrices().scale(f, f);
         drawContext.getMatrices().translate(-this.width / 2.0F, -this.height / 2.0F);
         this.neneclient$invPushed = true;
      } else {
         this.neneclient$invPushed = false;
      }

      if (this.button != null) {
         this.button.active = ((PlayerScreenHandler)this.handler).slots.stream().anyMatch(this::hasStack);
      }
   }

   @Inject(method = "render", at = @At("RETURN"))
   private void render(DrawContext drawContext, int i, int j, float f, CallbackInfo callbackInfo) {
      if (this.neneclient$invPushed) {
         drawContext.getMatrices().popMatrix();
         this.neneclient$invPushed = false;
      }
   }

   @Unique
   private boolean hasStack(Slot slot) {
      return slot.hasStack() && !slot.getStack().isEmpty();
   }

   @Unique
   private void onDropAllClick(ButtonWidget buttonWidget) {
      for (Slot slot : ((PlayerScreenHandler)this.handler).slots) {
         if (this.hasStack(slot) && this.button.active) {
            MinecraftAccess.minecraft
               .interactionManager
               .clickSlot(((PlayerScreenHandler)this.handler).syncId, slot.id, 1, SlotActionType.THROW, MinecraftAccess.minecraft.player);
         }
      }
   }
}
