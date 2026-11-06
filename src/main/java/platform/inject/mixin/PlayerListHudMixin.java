package platform.inject.mixin;

import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.render.Animations;
import aethereal.render.RenderComponent183;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {
   @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal = 2), require = 0)
   private void render(Args args, @Local(name = "list") List<PlayerListEntry> list, @Local(name = "w") int i) {
      if (MinecraftAccess.minecraft.player != null
         && i < list.size()
         && MinecraftAccess.minecraft.player.getUuid().equals(((PlayerListEntry)list.get(i)).getProfile().id())) {
         args.set(4, RenderComponent183.computeValue_2558(60, 140, 255, 128));
      }
   }

   @Inject(method = "setVisible", at = @At("HEAD"))
   private void setVisible(boolean bl, CallbackInfo callbackInfo) {
      Animations animations = NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502();
      if (animations.isEnabled() && animations.getMultiSelectSettingField1_2544().computeValue_2852("TAB").getCurrentValue()) {
         animations.getAnimationField4_2542().performAction_2528(bl);
      }
   }

   @Inject(method = "render", at = @At("HEAD"))
   private void headRender(DrawContext drawContext, int i, Scoreboard scoreboard, @Nullable ScoreboardObjective scoreboardObjective, CallbackInfo callbackInfo) {
      Animations animations = NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502();
      if (animations.isEnabled() && animations.getMultiSelectSettingField1_2544().computeValue_2852("TAB").getCurrentValue()) {
         drawContext.getMatrices().pushMatrix();
         drawContext.getMatrices().translate(0.0F, -200.0F * (1.0F - animations.getAnimationField4_2542().getFloatValueField4_2530()));
      }
   }

   @Inject(method = "render", at = @At("RETURN"))
   private void render(DrawContext drawContext, int i, Scoreboard scoreboard, @Nullable ScoreboardObjective scoreboardObjective, CallbackInfo callbackInfo) {
      Animations animations = NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502();
      if (animations.isEnabled() && animations.getMultiSelectSettingField1_2544().computeValue_2852("TAB").getCurrentValue()) {
         drawContext.getMatrices().popMatrix();
      }
   }
}
