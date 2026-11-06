package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
   @Inject(method = "send*", at = @At("HEAD"), cancellable = true)
   private void send(Packet<?> packet, CallbackInfo callbackInfo) {
      PacketEvent packetEvent = new PacketEvent(packet, PacketEvent.Type.SEND);
      EventBus.post(packetEvent);
      if (packetEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "channelRead0*", at = @At("HEAD"), cancellable = true)
   private void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callbackInfo) {
      PacketEvent packetEvent = new PacketEvent(packet, PacketEvent.Type.RECEIVE);
      EventBus.post(packetEvent);
      if (packetEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }
}
