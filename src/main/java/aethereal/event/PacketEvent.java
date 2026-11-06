package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.network.packet.Packet;

public class PacketEvent extends CancellableEvent implements Event {
   private final Packet<?> packet;
   private final PacketEvent.Type type;

   public Packet<?> getPacket_1015() {
      return this.packet;
   }

   public PacketEvent.Type getType_1014() {
      return this.type;
   }

   public PacketEvent(Packet<?> packet, PacketEvent.Type type) {
      this.packet = packetx;
      this.type = typex;
   }

   public boolean checkCondition_1012() {
      return this.type == PacketEvent.Type.SEND;
   }

   public boolean checkCondition_1013() {
      return this.type == PacketEvent.Type.RECEIVE;
   }

   public enum Type {
      SEND,
      RECEIVE;
   }
}
