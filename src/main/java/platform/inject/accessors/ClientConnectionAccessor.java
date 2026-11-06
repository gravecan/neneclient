package platform.inject.accessors;

import io.netty.channel.ChannelFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClientConnection.class)
public interface ClientConnectionAccessor {
   @Invoker("sendImmediately")
   void sendWithoutEvent(Packet<?> packet, ChannelFutureListener channelFutureListener, boolean bl);

   @Accessor("packetListener")
   void setPacketListener(PacketListener packetListener);
}
