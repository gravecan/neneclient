package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent080;
import aethereal.event.EventComponent084;
import aethereal.event.EventComponent091;
import aethereal.event.PacketEvent;
import aethereal.setting.BooleanSetting;
import aethereal.setting.FloatSetting;
import aethereal.util.UtilComponent277;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@ModuleRegister(name = "Free Camera", description = "Allows you to freely move the camera while the player remains in place", category = Category.Movement)
public class FreeCamera extends Module implements MinecraftAccess {
   private final FloatSetting floatSettingField1 = new FloatSetting("Travel speed XZ", 1.0F, 0.1F, 5.0F, 0.1F);
   private final FloatSetting floatSettingField2 = new FloatSetting("Movement speed Y", 1.0F, 0.1F, 5.0F, 0.1F);
   private final BooleanSetting booleanSettingField3 = new BooleanSetting("Freeze packages in-flight", true);
   private Vec3d vec3dField4;
   private Vec3d vec3dField5;
   private Vec3d vec3dField6;
   private float floatValueField7;
   private float floatValueField8;
   private boolean flagField9;
   private boolean flagField10;

   public FreeCamera() {
      this.addSettings(this.booleanSettingField3, this.floatSettingField1, this.floatSettingField2);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (minecraft.player == null) {
         this.performAction_2043(true);
         this.toggle();
      } else {
         this.vec3dField5 = minecraft.player.getEyePos();
         this.vec3dField4 = minecraft.player.getEyePos();
         if (this.booleanSettingField3.getCurrentValue()) {
            this.vec3dField6 = minecraft.player.getEntityPos();
         }

         this.performAction_2043(false);
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.performAction_2043(true);
   }

   @EventTarget
   public void onEventComponent080_2040(EventComponent080 eventComponent080) {
      if (this.vec3dField4 != null && minecraft.player.isAlive()) {
         if (minecraft.options.getPerspective() != Perspective.FIRST_PERSON) {
            minecraft.options.setPerspective(Perspective.FIRST_PERSON);
         }

         Vec3d vec3d = this.vec3dField5 != null ? this.vec3dField5 : this.vec3dField4;
         Vec3d vec3d1 = new Vec3d(
            vec3d.x + (this.vec3dField4.x - vec3d.x) * minecraft.getRenderTickCounter().getTickProgress(false),
            vec3d.y + (this.vec3dField4.y - vec3d.y) * minecraft.getRenderTickCounter().getTickProgress(false),
            vec3d.z + (this.vec3dField4.z - vec3d.z) * minecraft.getRenderTickCounter().getTickProgress(false)
         );
         eventComponent080.setPosition_1030(vec3d1);
         eventComponent080.performAction_0597(true);
      }
   }

   @EventTarget
   public void onEventComponent084_2041(EventComponent084 eventComponent084) {
      if (this.vec3dField4 != null && minecraft.player.isAlive()) {
         eventComponent084.setHitResultField1_1038(
            minecraft.world
               .raycast(
                  new RaycastContext(
                     this.vec3dField4,
                     this.vec3dField4
                        .add(minecraft.player.getRotationVec(eventComponent084.getTickNeneClient_1036()).multiply(minecraft.player.getBlockInteractionRange())),
                     ShapeType.OUTLINE,
                     FluidHandling.NONE,
                     minecraft.player
                  )
               )
         );
         eventComponent084.performAction_0597(true);
      }
   }

   @EventTarget
   public void onEventComponent074_2038(EventComponent074 eventComponent074) {
      if (this.vec3dField4 != null && minecraft.player.isAlive()) {
         if (minecraft.currentScreen == null) {
            float f;
            if (minecraft.options.forwardKey.isPressed()) {
               f = 1.0F;
            } else {
               f = minecraft.options.backKey.isPressed() ? -1.0F : 0.0F;
            }

            this.floatValueField7 = f;
            float g;
            if (InputUtil.isKeyPressed(minecraft.getWindow(), 65)) {
               g = 1.0F;
            } else {
               g = InputUtil.isKeyPressed(minecraft.getWindow(), 68) ? -1.0F : 0.0F;
            }

            this.floatValueField8 = g;
            this.flagField9 = minecraft.options.jumpKey.isPressed();
            this.flagField10 = minecraft.options.sneakKey.isPressed();
         } else {
            this.performAction_2043(false);
         }

         if (this.booleanSettingField3.getCurrentValue() && !minecraft.player.isOnGround()) {
            minecraft.player.setVelocity(0.0, 0.0, 0.0);
            if (this.vec3dField6 != null) {
               minecraft.player.setPosition(this.vec3dField6.x, this.vec3dField6.y, this.vec3dField6.z);
            }
         }

         this.vec3dField5 = this.vec3dField4;
         this.vec3dField4 = this.vec3dField4
            .add(
               (
                     this.floatValueField7 * -Math.sin(Math.toRadians(UtilComponent277.getFloatValueField2_3570()))
                        + this.floatValueField8 * Math.cos(Math.toRadians(UtilComponent277.getFloatValueField2_3570()))
                  )
                  * this.floatSettingField1.getCurrentValue().floatValue(),
               (this.flagField9 ? this.floatSettingField2.getCurrentValue().floatValue() : 0.0)
                  - (this.flagField10 ? this.floatSettingField2.getCurrentValue().floatValue() : 0.0),
               (
                     this.floatValueField7 * Math.cos(Math.toRadians(UtilComponent277.getFloatValueField2_3570()))
                        + this.floatValueField8 * Math.sin(Math.toRadians(UtilComponent277.getFloatValueField2_3570()))
                  )
                  * this.floatSettingField1.getCurrentValue().floatValue()
            );
      }
   }

   @EventTarget
   public void onEventComponent091_2042(EventComponent091 eventComponent091) {
      if (this.vec3dField4 != null && minecraft.player.isAlive()) {
         if (minecraft.currentScreen != null) {
            eventComponent091.setForward_1065(0.0F);
            eventComponent091.setStrafe_1060(0.0F);
            eventComponent091.setJump_1066(false);
            eventComponent091.setSneak_1061(false);
            return;
         }

         float f;
         if (InputUtil.isKeyPressed(minecraft.getWindow(), 265)) {
            f = 1.0F;
         } else {
            f = InputUtil.isKeyPressed(minecraft.getWindow(), 264) ? -1.0F : 0.0F;
         }

         eventComponent091.setForward_1065(f);
         float g;
         if (InputUtil.isKeyPressed(minecraft.getWindow(), 262)) {
            g = -1.0F;
         } else {
            g = InputUtil.isKeyPressed(minecraft.getWindow(), 263) ? 1.0F : 0.0F;
         }

         eventComponent091.setStrafe_1060(g);
         eventComponent091.setJump_1066(false);
         eventComponent091.setSneak_1061(false);
      }
   }

   @EventTarget
   public void onPacketEvent_2039(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1012()
         && this.booleanSettingField3.getCurrentValue()
         && this.vec3dField4 != null
         && !minecraft.player.isOnGround()
         && minecraft.player.isAlive()
         && (packetEvent.getPacket_1015() instanceof PlayerInputC2SPacket || packetEvent.getPacket_1015() instanceof ClientCommandC2SPacket)) {
         packetEvent.performAction_0597(true);
      }
   }

   private void performAction_2043(boolean bl) {
      if (bl) {
         this.vec3dField4 = null;
         this.vec3dField5 = null;
         this.vec3dField6 = null;
      } else {
         this.floatValueField8 = 0.0F;
         this.floatValueField7 = 0.0F;
         this.flagField10 = false;
         this.flagField9 = false;
      }
   }
}
