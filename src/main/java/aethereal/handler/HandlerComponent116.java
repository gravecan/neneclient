package aethereal.handler;

import aethereal.core.EventTarget;
import aethereal.event.PacketEvent;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

@HandlerAnnotation107
public class HandlerComponent116 extends HandlerComponent104 {
   private long longValueField1 = -1L;
   private long longValueField2 = -1L;
   private float floatValueField3 = 20.0F;

   public float getFloatValueField3_1218() {
      return this.floatValueField3;
   }

   @EventTarget
   public void onPacketEvent_1219(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013() && packetEvent.getPacket_1015() instanceof WorldTimeUpdateS2CPacket worldTimeUpdateS2CPacket) {
         long o = System.currentTimeMillis();
         long l = worldTimeUpdateS2CPacket.time();
         if (this.longValueField2 > 0L && l > this.longValueField1) {
            long m = l - this.longValueField1;
            long n = o - this.longValueField2;
            this.floatValueField3 = Math.min(20.0F, (float)m * 1000.0F / (float)n);
         }

         this.longValueField2 = o;
         this.longValueField1 = l;
      }
   }
}
