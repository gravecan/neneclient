package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent091;
import aethereal.event.PacketEvent;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.util.UtilComponent277;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityDamageS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@ModuleRegister(name = "Velocity", description = "Prevents players from knocking you back", category = Category.Combat)
public class Velocity extends Module {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Anti-knockback mode", "Legitim", "Normal", "Legitim");
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Jump into legit", true)
      .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("Legitim"));
   private final BooleanSetting booleanSettingField3 = new BooleanSetting("Legitim", true)
      .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("Legitim"));
   private Vec3d vec3dField4 = Vec3d.ZERO;
   private int integerField5;

   public Velocity() {
      this.addSettings(this.choiceSettingField1, this.booleanSettingField2, this.booleanSettingField3);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.vec3dField4 = Vec3d.ZERO;
      this.integerField5 = 0;
   }

   @EventTarget
   public void onPacketEvent_1678(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013() && minecraft.player != null) {
         EntityDamageS2CPacket entityDamageS2CPacket = (EntityDamageS2CPacket)packetEvent.getPacket_1015();
         if (entityDamageS2CPacket instanceof EntityDamageS2CPacket entityDamageS2CPacket1 && entityDamageS2CPacket1.entityId() == minecraft.player.getId()) {
            DamageSource damageSource = entityDamageS2CPacket1.createDamageSource(minecraft.world);
            boolean bl = damageSource.getAttacker() instanceof PlayerEntity;
            this.integerField5 = bl ? minecraft.player.age : 0;
            if (!bl) {
               this.vec3dField4 = Vec3d.ZERO;
            }
         }

         EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket = (EntityVelocityUpdateS2CPacket)packetEvent.getPacket_1015();
         if (entityVelocityUpdateS2CPacket instanceof EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket1
            && entityVelocityUpdateS2CPacket1.getEntityId() == minecraft.player.getId()) {
            if (!this.choiceSettingField1.checkCondition_2850("Normal")) {
               this.vec3dField4 = new Vec3d(entityVelocityUpdateS2CPacket1.getVelocity().x, 0.0, entityVelocityUpdateS2CPacket1.getVelocity().z);
            } else {
               packetEvent.performAction_0597(true);
            }
         }
      }
   }

   @EventTarget
   public void onEventComponent091_1679(EventComponent091 eventComponent091) {
      if (minecraft.player.hurtTime <= 0) {
         this.vec3dField4 = Vec3d.ZERO;
      } else if (this.choiceSettingField1.checkCondition_2850("Legitim")
         && this.vec3dField4.lengthSquared() != 0.0
         && minecraft.player.age - this.integerField5 <= 10) {
         double d = MathHelper.wrapDegrees(
            Math.toDegrees(Math.atan2(-this.vec3dField4.z, -this.vec3dField4.x)) - 90.0 - UtilComponent277.getFloatValueField2_3570()
         );
         if (!this.booleanSettingField3.getCurrentValue() || !minecraft.options.forwardKey.isPressed() || !(Math.abs(d) >= 140.0)) {
            float f;
            if (!(d <= -45.0) && !(d >= 45.0)) {
               f = 1.0F;
            } else {
               f = !(d > 135.0) && !(d < -135.0) ? 0.0F : -1.0F;
            }

            eventComponent091.setForward_1065(f);
            float g;
            if (!(d < 45.0) && !(d > 135.0)) {
               g = -1.0F;
            } else {
               g = !(d > -45.0) && !(d < -135.0) ? 1.0F : 0.0F;
            }

            eventComponent091.setStrafe_1060(g);
            eventComponent091.setJump_1066(this.booleanSettingField2.getCurrentValue() && minecraft.player.isOnGround());
         }
      }
   }
}
