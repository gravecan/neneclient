package aethereal.util;

import aethereal.core.MinecraftAccess;
import net.minecraft.client.render.Camera;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import platform.inject.invokers.GameRendererInvoker;

public class UtilComponent284 implements MinecraftAccess {
   private UtilComponent284() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static Vector2f computeValue_3615(double d, double e, double f) {
      Camera camera = minecraft.gameRenderer.getCamera();
      Quaternionf quaternionf = RotationAxis.POSITIVE_Y.rotationDegrees(-camera.getYaw());
      Quaternionf quaternionf1 = RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch());
      Quaternionf quaternionf2 = quaternionf.mul(quaternionf1, new Quaternionf());
      Quaternionf quaternionf3 = quaternionf2.conjugate(new Quaternionf());
      Vec3d vec3d = camera.getCameraPos();
      Vector3f vector3f = new Vector3f((float)(vec3d.x - d), (float)(vec3d.y - e), (float)(vec3d.z - f));
      vector3f.rotate(quaternionf3);
      return computeValue_3619(
         vector3f, ((GameRendererInvoker)minecraft.gameRenderer).invokeGetFov(camera, minecraft.getRenderTickCounter().getTickProgress(false), true)
      );
   }

   private static Vector2f computeValue_3619(Vector3f vector3f, double d) {
      float f = minecraft.getWindow().getFramebufferWidth() / minecraft.getWindow().getFramebufferHeight();
      float g = f;
      double e = minecraft.getWindow().getScaledHeight() / 2.0F / (vector3f.z * Math.tan(Math.toRadians(d / 2.0)));
      double h = e * f / g;
      return vector3f.z < 0.0F
         ? new Vector2f(
            (float)(-vector3f.x() * h + minecraft.getWindow().getScaledWidth() / 2.0F),
            (float)(minecraft.getWindow().getScaledHeight() / 2.0F - vector3f.y() * e)
         )
         : new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
   }

   public static float[] computeValue_3616(Box box) {
      float f = Float.MAX_VALUE;
      float g = Float.MAX_VALUE;
      float h = -Float.MAX_VALUE;
      float i = -Float.MAX_VALUE;

      for (int j = 0; j < 8; j++) {
         double d = (j & 1) == 0 ? box.minX : box.maxX;
         double e = (j & 2) == 0 ? box.minY : box.maxY;
         double k = (j & 4) == 0 ? box.minZ : box.maxZ;
         Vector2f vector2f = computeValue_3615(d, e, k);
         if (vector2f.x() == Float.MAX_VALUE) {
            return null;
         }

         f = Math.min(f, vector2f.x());
         g = Math.min(g, vector2f.y());
         h = Math.max(h, vector2f.x());
         i = Math.max(i, vector2f.y());
      }

      return !(h <= f) && !(i <= g) ? new float[]{f, g, h, i} : null;
   }

   public static boolean checkCondition_3618(Vector2f vector2f) {
      return vector2f.x() != Float.MAX_VALUE
         && vector2f.y() != Float.MAX_VALUE
         && vector2f.x() >= 0.0F
         && vector2f.y() >= 0.0F
         && vector2f.x() <= minecraft.getWindow().getScaledWidth()
         && vector2f.y() <= minecraft.getWindow().getScaledHeight();
   }

   public static int computeValue_3617(Identifier identifier) {
      if (identifier == null) {
         return 0;
      }

      try {
         AbstractTexture abstractTexture = minecraft.getTextureManager().getTexture(identifier);
         if (abstractTexture != null && abstractTexture.getGlTexture() instanceof GlTexture glTexture) {
            return glTexture.getGlId();
         }
      } catch (Throwable throwable) {
      }

      return 0;
   }
}
