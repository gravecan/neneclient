package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent083;
import aethereal.event.EventComponent101;
import aethereal.event.RemovalsEvent;
import aethereal.render.Animations;
import aethereal.ui.screen.ScreenComponent218;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(InGameHud.class)
public class InGameHudMixin {
   @Shadow
   @Final
   private MinecraftClient client;
   @Unique
   private boolean hotbarPushed = false;

   @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
   private void cancelCrosshairInGUI(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      MinecraftClient minecraftClient = this.client != null ? this.client : MinecraftClient.getInstance();
      if (minecraftClient == null || !(minecraftClient.currentScreen instanceof ScreenComponent218) && minecraftClient.currentScreen == null) {
         EventComponent083 eventComponent083 = new EventComponent083(drawContext, renderTickCounter.getTickProgress(false));
         EventBus.post(eventComponent083);
         if (eventComponent083.checkCondition_0596()) {
            callbackInfo.cancel();
         }
      } else {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "render", at = @At("HEAD"))
   public void headRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      EventBus.post(new DrawEvent(drawContext, renderTickCounter.getTickProgress(false), DrawEvent.State315.D2D));
   }

   @Inject(method = "renderScoreboardSidebar", at = @At("HEAD"), cancellable = true)
   private void renderScoreboardSidebar(DrawContext drawContext, ScoreboardObjective scoreboardObjective, CallbackInfo callbackInfo) {
      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.SCOREBOARD);
      EventBus.post(removalsEvent);
      if (removalsEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }

   @Redirect(
      method = "renderScoreboardSidebar",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/scoreboard/ScoreboardObjective;getDisplayName()Lnet/minecraft/text/Text;")
   )
   private Text scoreboardTitle(ScoreboardObjective scoreboardObjective) {
      EventComponent101 eventComponent101 = new EventComponent101(scoreboardObjective.getDisplayName());
      EventBus.post(eventComponent101);
      return eventComponent101.getTitle_1099();
   }

   @Redirect(
      method = "renderScoreboardSidebar",
      at = @At(
         value = "INVOKE",
         ordinal = 1,
         target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)V"
      )
   )
   private void scoreboardLine(DrawContext drawContext, TextRenderer textRenderer, Text text, int i, int j, int k, boolean bl) {
      EventComponent101 eventComponent101 = new EventComponent101(text);
      EventBus.post(eventComponent101);
      drawContext.drawText(textRenderer, eventComponent101.getTitle_1099(), i, j, k, bl);
   }

   @Redirect(
      method = "renderScoreboardSidebar",
      at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/client/font/TextRenderer;getWidth(Lnet/minecraft/text/StringVisitable;)I")
   )
   private int scoreboardWidth(TextRenderer textRenderer, StringVisitable stringVisitable) {
      EventComponent101 eventComponent101 = new EventComponent101((Text)stringVisitable);
      EventBus.post(eventComponent101);
      return textRenderer.getWidth(eventComponent101.getTitle_1099());
   }

   @Inject(method = "renderPortalOverlay", at = @At("HEAD"), cancellable = true)
   private void renderPortalOverlay(DrawContext drawContext, float f, CallbackInfo callbackInfo) {
      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.PORTAL);
      EventBus.post(removalsEvent);
      if (removalsEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }

   @ModifyArgs(
      method = "renderMiscOverlays",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;F)V",
         ordinal = 0
      )
   )
   private void onRenderPumpkinOverlay(Args args) {
      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.PUMPKIN);
      EventBus.post(removalsEvent);
      if (removalsEvent.checkCondition_0596()) {
         args.set(2, 0.0F);
      }
   }

   @Inject(method = "renderMainHud", at = @At("HEAD"))
   private void hotbarRiseHead(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      Animations animations = NeneClient.getInstance() != null
            && NeneClient.getInstance().getProcessors() != null
            && NeneClient.getInstance().getProcessors().getModules() != null
         ? NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502()
         : null;
      if (animations != null
         && animations.isEnabled()
         && animations.getMultiSelectSettingField1_2544().computeValue_2852("Raising hotbar").getCurrentValue()
         && animations.getAnimationField5_2546().getFloatValueField4_2530() > 0.0F) {
         float f = animations.getFloatSettingField2_2545() != null ? animations.getFloatSettingField2_2545().getCurrentValue() : 14.0F;
         drawContext.getMatrices().pushMatrix();
         drawContext.getMatrices().translate(0.0F, -f * animations.getAnimationField5_2546().getFloatValueField4_2530());
         this.hotbarPushed = true;
      } else {
         this.hotbarPushed = false;
      }
   }

   @Inject(method = "renderMainHud", at = @At("RETURN"))
   private void hotbarRiseReturn(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      if (this.hotbarPushed) {
         drawContext.getMatrices().popMatrix();
         this.hotbarPushed = false;
      }
   }

   @ModifyArgs(
      method = "renderHotbar",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V",
         ordinal = 1
      )
   )
   private void hotbarSelection(Args args) {
      Animations animations = NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502();
      if (animations.isEnabled()
         && animations.getMultiSelectSettingField1_2544().computeValue_2852("Hotbar slot").getCurrentValue()
         && this.client.player != null) {
         float f = animations.getFloatValueField8_2538();
         int i = this.client.player.getInventory().getSelectedSlot();
         int j = (Integer)args.get(2);
         args.set(2, Math.round(j + (f - i) * 20.0F));
      }
   }
}
