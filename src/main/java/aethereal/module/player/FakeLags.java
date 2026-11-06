package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent077;
import aethereal.event.PacketEvent;
import aethereal.render.RenderComponent183;
import aethereal.setting.BooleanSetting;
import aethereal.setting.FloatSetting;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.math.Vec3d;
import platform.inject.accessors.ClientConnectionAccessor;

@ModuleRegister(name = "Fake Lags", description = "Delays sending packets, simulating server lag", category = Category.Player)
public class FakeLags extends Module implements MinecraftAccess {
   private final FloatSetting floatSettingField1 = new FloatSetting("Simulation delay", 20.0F, 1.0F, 40.0F, 1.0F);
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Display service position", false);
   private final Queue<Packet<?>> queueField3 = new ConcurrentLinkedQueue<>();
   private int integerField4;
   private int integerField5;
   private Vec3d vec3dField6;

   public FakeLags() {
      this.addSettings(this.floatSettingField1, this.booleanSettingField2);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.queueField3.clear();
      this.integerField4 = 0;
      this.integerField5 = 0;
      this.vec3dField6 = null;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.performAction_2197();
      this.vec3dField6 = null;
   }

   @EventTarget
   public void onEventComponent077_2194(EventComponent077 eventComponent077) {
      this.integerField5 = 2;
      this.performAction_2197();
   }

   @EventTarget
   public void onPacketEvent_2193(PacketEvent packetEvent) {
      if (minecraft.player != null) {
         if (packetEvent.checkCondition_1013()) {
            EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket = (EntityVelocityUpdateS2CPacket)packetEvent.getPacket_1015();
            if (entityVelocityUpdateS2CPacket instanceof EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket1) {
               if (entityVelocityUpdateS2CPacket1.getEntityId() == minecraft.player.getId()) {
                  this.performAction_2197();
               }
            }
         } else {
            if (packetEvent.checkCondition_1012()) {
               if (this.integerField5 <= 0 && !this.checkCondition_2195(packetEvent.getPacket_1015())) {
                  this.queueField3.offer(packetEvent.getPacket_1015());
                  packetEvent.performAction_0597(true);
               } else {
                  this.performAction_2197();
               }
            }
         }
      }
   }

   @EventTarget
   public void onEventComponent074_2192(EventComponent074 eventComponent074) {
      if (this.integerField5 > 0) {
         this.integerField5--;
      }

      int i = this.integerField4 + 1;
      this.integerField4 = i;
      if (i >= this.floatSettingField1.getCurrentValue().intValue() && !this.queueField3.isEmpty()) {
         this.performAction_2197();
         this.integerField4 = 0;
      }
   }

   @EventTarget
   public void onDrawEvent_2191(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1002() && this.booleanSettingField2.getCurrentValue() && this.vec3dField6 != null) {
         drawEvent.getRenderComponent185Field2_0999()
            .performAction_2594(
               drawEvent.getStack2_1004(),
               minecraft.player.getBoundingBox().offset(this.vec3dField6.subtract(minecraft.player.getEntityPos())),
               RenderComponent183.computeValue_2558(255, 255, 255, 200),
               0.75F
            );
      }
   }

   private boolean checkCondition_2195(Packet<?> packet) {
      return packet instanceof PlayerInteractEntityC2SPacket
         || packet instanceof ChatMessageC2SPacket
         || packet instanceof UpdateSelectedSlotC2SPacket
         || packet instanceof HandSwingC2SPacket
         || packet instanceof PlayerInteractBlockC2SPacket
         || packet instanceof PlayerInteractItemC2SPacket
         || packet instanceof ClickSlotC2SPacket;
   }

   private void performAction_2197() {
      ClientConnectionAccessor clientConnectionAccessor = (ClientConnectionAccessor)minecraft.player.networkHandler.getConnection();
      this.queueField3.forEach(packet -> clientConnectionAccessor.sendWithoutEvent((Packet<?>)packet, null, true));
      this.queueField3.clear();
      this.vec3dField6 = minecraft.player.getEntityPos();
   }
}
