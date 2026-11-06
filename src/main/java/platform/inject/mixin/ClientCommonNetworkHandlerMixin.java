package platform.inject.mixin;

import aethereal.core.NeneClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket.Status;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonNetworkHandler.class)
public class ClientCommonNetworkHandlerMixin {
   @Inject(method = "onResourcePackSend", at = @At("HEAD"), cancellable = true)
   private void onResourcePackSend(ResourcePackSendS2CPacket resourcePackSendS2CPacket, CallbackInfo callbackInfo) {
      if (NeneClient.getInstance().getProcessors().getModules().getNoServerPack_0413().isEnabled()) {
         ClientCommonNetworkHandler clientCommonNetworkHandler = (ClientCommonNetworkHandler)this;
         clientCommonNetworkHandler.sendPacket(new ResourcePackStatusC2SPacket(resourcePackSendS2CPacket.id(), Status.DECLINED));
         callbackInfo.cancel();
      }
   }
}
