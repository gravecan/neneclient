package aethereal.util;

import aethereal.core.MinecraftAccess;
import java.util.Objects;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import platform.inject.accessors.ClientPlayerEntityAccessor;

public class UtilComponent250 implements MinecraftAccess {
   private float yaw;
   private float pitch;

   public void setYaw_3388(float f) {
      this.yaw = f;
   }

   public void setPitch_3384(float f) {
      this.pitch = f;
   }

   public UtilComponent250() {
   }

   public UtilComponent250(float f, float g) {
      this.yaw = f;
      this.pitch = g;
   }

   public float getYaw_3386() {
      return this.yaw;
   }

   public float getPitch_3387() {
      return this.pitch;
   }

   public UtilComponent250(Entity entity) {
      this.yaw = entity.getYaw();
      this.pitch = entity.getPitch();
   }

   public double computeValue_3389(UtilComponent250 utilComponent2501) {
      if (utilComponent2501 == null) {
         return 0.0;
      }

      double d = MathHelper.wrapDegrees(utilComponent2501.getYaw_3386() - this.yaw);
      double e = MathHelper.wrapDegrees(utilComponent2501.getPitch_3387() - this.pitch);
      return Math.hypot(Math.abs(d), Math.abs(e));
   }

   public static UtilComponent250 computeValue_3383() {
      if (minecraft.player == null) {
         return new UtilComponent250(UtilComponent277.getFloatValueField2_3570(), UtilComponent277.getFloatValueField3_3571());
      }

      float f = minecraft.player.getYaw();
      float g = UtilComponent277.getFloatValueField2_3570();
      return new UtilComponent250(f + MathHelper.wrapDegrees(g - f), UtilComponent277.getFloatValueField3_3571());
   }

   public static UtilComponent250 computeValue_3390(Vec3d vec3d, Vec3d vec3d1) {
      Vec3d vec3d2 = vec3d1.subtract(vec3d);
      double d = Math.sqrt(vec3d2.x * vec3d2.x + vec3d2.z * vec3d2.z);
      float f = (float)Math.toDegrees(Math.atan2(vec3d2.z, vec3d2.x)) - 90.0F;
      float g = (float)(-Math.toDegrees(Math.atan2(vec3d2.y, d)));
      return new UtilComponent250(MathHelper.wrapDegrees(f), MathHelper.clamp(g, -90.0F, 90.0F));
   }

   public static UtilComponent250 computeValue_3385() {
      ClientPlayerEntityAccessor clientPlayerEntityAccessor = (ClientPlayerEntityAccessor)minecraft.player;
      return new UtilComponent250(Objects.requireNonNull(clientPlayerEntityAccessor).getLastYaw(), clientPlayerEntityAccessor.getLastPitch());
   }
}
