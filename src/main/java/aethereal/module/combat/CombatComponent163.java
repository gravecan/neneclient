package aethereal.module.combat;

import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent074;
import aethereal.handler.HandlerAnnotation107;
import aethereal.handler.HandlerComponent104;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.util.UtilComponent284;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2f;

@HandlerAnnotation107
public class CombatComponent163 extends HandlerComponent104 implements MinecraftAccess {
   private final Animation animationField1 = new Animation();
   private LivingEntity livingEntityField2;

   public Animation getAnimationField1_1485() {
      return this.animationField1;
   }

   @EventTarget
   public void onDrawEvent_1483(DrawEvent drawEvent) {
      this.animationField1.performAction_2535(0.0F, 1.0F, 0.25F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      float f = this.animationField1.getFloatValueField4_2530();
      if (drawEvent.checkCondition_1001() && this.livingEntityField2 != null && f > 0.0F) {
         Vec3d vec3d = this.computeValue_1486(this.livingEntityField2, drawEvent.getTickNeneClient_0997());
         Vector2f vector2f = UtilComponent284.computeValue_3615(vec3d.x, vec3d.y, vec3d.z);
         if (!UtilComponent284.checkCondition_3618(vector2f)) {
            return;
         }

         float g = (float)minecraft.player.getEyePos().distanceTo(vec3d);
         float h = (float)Math.max(28.0, 40.0 - g * 0.7000002488091963) * (1.2F - 0.2F * f);
         float i = (float)Math.sin(System.currentTimeMillis() / 820.0) * 350.0F;
         drawEvent.getRenderComponent184Field1_0998()
            .getTexturePipeline_2566()
            .performAction_2816(
               Identifier.of("neneclient", "pictures/marker.png"),
               vector2f.x,
               vector2f.y,
               h,
               h,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               new int[]{RenderComponent183.computeValue_2549(-1, f * 0.8F)},
               new float[]{0.0F, 0.0F, 0.0F, 0.0F},
               1.0F,
               i
            );
      }
   }

   @EventTarget
   public void onEventComponent074_1484(EventComponent074 eventComponent074) {
      ProjectileHelper projectileHelper = NeneClient.getInstance().getProcessors().getModules().getProjectileHelperField9_0415();
      LivingEntity livingEntity = null;
      if (projectileHelper.isEnabled() && projectileHelper.checkCondition_1627()) {
         livingEntity = projectileHelper.computeValue_1619();
      }

      boolean bl = livingEntity != null;
      if (bl) {
         this.livingEntityField2 = livingEntity;
      }

      this.animationField1.performAction_2528(bl);
      if (!bl && this.animationField1.getFloatValueField1_2527() <= 0.0F) {
         this.livingEntityField2 = null;
      }
   }

   private Vec3d computeValue_1486(LivingEntity livingEntity, float f) {
      return new Vec3d(
         MathHelper.lerp(f, livingEntity.lastRenderX, livingEntity.getX()),
         MathHelper.lerp(f, livingEntity.lastRenderY, livingEntity.getY()) + livingEntity.getHeight() / 2.0,
         MathHelper.lerp(f, livingEntity.lastRenderZ, livingEntity.getZ())
      );
   }
}
