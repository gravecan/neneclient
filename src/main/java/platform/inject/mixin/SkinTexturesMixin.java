package platform.inject.mixin;

import aethereal.core.NeneClient;
import aethereal.module.misc.StreamerMode;
import net.minecraft.entity.player.SkinTextures;
import net.minecraft.util.Identifier;
import net.minecraft.util.AssetInfo.TextureAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkinTextures.class)
public class SkinTexturesMixin {
   @Inject(method = "comp_1626", at = @At("HEAD"), cancellable = true)
   public void body(CallbackInfoReturnable<TextureAsset> callbackInfoReturnable) {
      if (NeneClient.getInstance() != null && NeneClient.getInstance().getProcessors() != null && NeneClient.getInstance().getProcessors().getModules() != null
         )
       {
         StreamerMode streamerMode = NeneClient.getInstance().getProcessors().getModules().getStreamerModeField7_0439();
         if (streamerMode != null && streamerMode.isEnabled() && streamerMode.getBooleanSettingField1_2003().getCurrentValue()) {
            final Identifier identifier = Identifier.of("neneclient", "pictures/skin.png");
            callbackInfoReturnable.setReturnValue(new TextureAsset() {
               public Identifier comp_3627() {
                  return identifier;
               }

               public Identifier comp_3626() {
                  return identifier;
               }
            });
         }
      }
   }
}
