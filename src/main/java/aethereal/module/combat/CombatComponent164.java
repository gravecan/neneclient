package aethereal.module.combat;

import aethereal.config.ThemeInfo;
import aethereal.core.ClientTickEvent;
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
import aethereal.render.RenderComponent189;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

@HandlerAnnotation107
public class CombatComponent164 extends HandlerComponent104 implements MinecraftAccess {
   private final Vector3f[] vector3fArrayField1 = new Vector3f[]{
      new Vector3f(0.0F, 1.5F, 0.0F),
      new Vector3f(0.0F, -1.5F, 0.0F),
      new Vector3f(1.0F, 0.0F, 0.0F),
      new Vector3f(-1.0F, 0.0F, 0.0F),
      new Vector3f(0.0F, 0.0F, 1.0F),
      new Vector3f(0.0F, 0.0F, -1.0F)
   };
   private final int[][] integerArrayField2 = new int[][]{{0, 4, 2}, {0, 3, 4}, {0, 5, 3}, {0, 2, 5}, {1, 2, 4}, {1, 4, 3}, {1, 3, 5}, {1, 5, 2}};
   private final float[] floatValueArrayField3 = new float[]{1.0F, 0.8F, 0.6F, 0.9F, 0.7F, 0.5F, 0.4F, 0.6F};
   private final Animation animationField4 = new Animation();
   private LivingEntity livingEntityField5;
   private static boolean a_logged = false;
   private static boolean a_errorLogged = false;

   public Animation getAnimationField4_1539() {
      return this.animationField4;
   }

   @EventTarget
   public void onDrawEvent_1537(DrawEvent drawEvent) {
      this.animationField4.performAction_2535(0.0F, 1.0F, 0.2F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      float f = (float)(System.currentTimeMillis() % 360000L) / 2.5F + this.animationField4.getFloatValueField4_2530();
      if (drawEvent.checkCondition_1002() && this.livingEntityField5 != null && drawEvent.getStack2_1004() != null) {
         if (!a_logged) {
            a_logged = true;
            System.out.println("[NeneClient] AuraHandler D3D draw start, anim=" + this.animationField4.getFloatValueField4_2530());
         }

         try {
            float g = this.animationField4.getFloatValueField4_2530();
            if (g > 0.0F) {
               int i = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
               Vec3d vec3d = this.computeValue_1540(this.livingEntityField5, drawEvent.getTickNeneClient_0997());
               float h = this.livingEntityField5.getWidth() * 1.5F;
               float j = 1.25F - 0.5F * g;
               MatrixStack matrixStack = drawEvent.getStack2_1004();
               if (NeneClient.getInstance().getProcessors().getModules().getAuraField3_0402().getChoiceSettingField15_1517().checkCondition_2850("Circle")) {
                  this.performAction_1545(matrixStack, vec3d, RenderComponent183.computeValue_2549(i, g));
               } else {
                  this.performAction_1543(matrixStack, vec3d, h, j, f, RenderComponent183.computeValue_2549(i, g));
                  this.performAction_1544(matrixStack, vec3d, h, j, f, RenderComponent183.computeValue_2549(i, g * 0.2F), g);
               }
            }
         } catch (Throwable throwable) {
            if (!a_errorLogged) {
               a_errorLogged = true;
               System.err.println("[NeneClient] AuraHandler draw error:");
               throwable.printStackTrace();
            }
         }
      }
   }

   @EventTarget
   public void performAction_1536(ClientTickEvent clientTickEvent) {
      if (minecraft.player != null) {
         NeneClient.getInstance().getProcessors().getModules().getAuraField3_0402().integerField16++;
      }
   }

   @EventTarget
   public void onEventComponent074_1538(EventComponent074 eventComponent074) {
      Aura aura = NeneClient.getInstance().getProcessors().getModules().getAuraField3_0402();
      TriggerBot triggerBot = NeneClient.getInstance().getProcessors().getModules().getTriggerBotField4_0418();
      LivingEntity livingEntity = aura.isEnabled() && aura.checkCondition_1512() ? aura.getLivingEntityField2_1528() : null;
      if (livingEntity == null && triggerBot.isEnabled() && triggerBot.checkCondition_1659()) {
         livingEntity = triggerBot.getLivingEntityField6_1669();
      }

      boolean bl = livingEntity != null && this.livingEntityField5 != null && livingEntity != this.livingEntityField5;
      boolean bl1 = livingEntity != null && !bl;
      if (bl1) {
         this.livingEntityField5 = livingEntity;
      }

      this.animationField4.performAction_2528(bl1);
      if (!bl1 && this.animationField4.getFloatValueField1_2527() <= 0.0F) {
         this.livingEntityField5 = bl ? livingEntity : null;
      }
   }

   private void performAction_1543(MatrixStack matrixStack, Vec3d vec3d, float f, float g, float h, int i) {
      Vec3d vec3d1 = minecraft.gameRenderer.getCamera().getCameraPos();
      Vec3d vec3d2 = vec3d.add(0.0, this.livingEntityField5.getHeight() / 2.0, 0.0);
      ByteBuffer byteBuffer = MemoryUtil.memAlloc(18 * this.integerArrayField2.length * 3 * 16);
      Matrix4f matrix4f = new Matrix4f(matrixStack.peek().getPositionMatrix());

      for (byte b = 0; b < 360; b += 20) {
         float j = (float)Math.toRadians(b + h * 0.3F);
         float k = (float)Math.sin(j) * f * g;
         float l = (float)Math.cos(j) * f * g;
         float m = 0.1F + this.livingEntityField5.getHeight() * Math.abs((float)Math.sin(b));
         Vec3d vec3d3 = vec3d.add(k, m, l);
         Matrix4f matrix4f1 = new Matrix4f(matrix4f);
         matrix4f1.translate((float)(vec3d3.getX() - vec3d1.x), (float)(vec3d3.getY() - vec3d1.y), (float)(vec3d3.getZ() - vec3d1.z));
         matrix4f1.rotate(
            new Quaternionf()
               .rotationTo(
                  new Vector3f(0.0F, 1.0F, 0.0F),
                  new Vector3f((float)(vec3d2.x - vec3d3.getX()), (float)(vec3d2.y - vec3d3.getY()), (float)(vec3d2.z - vec3d3.getZ())).normalize()
               )
         );
         matrix4f1.scale(0.1F, 0.1F, 0.1F);
         int[] n = RenderComponent183.computeValue_2555(i);

         for (int o = 0; o < this.integerArrayField2.length; o++) {
            int[] p = this.integerArrayField2[o];
            float q = this.floatValueArrayField3[o];
            int r = n[3] << 24 | Math.min(255, (int)(n[0] * q)) << 16 | Math.min(255, (int)(n[1] * q)) << 8 | Math.min(255, (int)(n[2] * q));

            for (int s = 0; s < 3; s++) {
               Vector3f vector3f = this.vector3fArrayField1[p[s]];
               Vector3f vector3f1 = new Vector3f(vector3f).mulPosition(matrix4f1);
               RenderComponent189.performAction_2652(byteBuffer, vector3f1.x, vector3f1.y, vector3f1.z, r >> 16 & 0xFF, r >> 8 & 0xFF, r & 0xFF, r >> 24 & 0xFF);
            }
         }
      }

      byteBuffer.flip();
      RenderComponent189.performAction_2649(DrawMode.TRIANGLES, new Matrix4f(), byteBuffer, byteBuffer.remaining() / 16, true);
      MemoryUtil.memFree(byteBuffer);
   }

   private void performAction_1545(MatrixStack matrixStack, Vec3d vec3d, int i) {
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      Vec3d vec3d1 = minecraft.gameRenderer.getCamera().getCameraPos();
      float f = this.livingEntityField5.getHeight() + 0.15F;
      float g = this.livingEntityField5.getWidth() * 0.8F;
      double d = System.currentTimeMillis() % 1750.0;
      boolean bl = d > 875.0;
      double e = d / 875.0;
      double h = bl ? e - 1.0 : 1.0 - e;
      double j = h < 0.5 ? 2.0 * h * h : 1.0 - Math.pow(-2.0 * h + 2.0, 2.0) / 2.0;
      float k = (float)(vec3d.getY() - vec3d1.y + f * j);
      float l = (float)(f * 0.8000001435473696 * Math.min(j, 1.0 - j) * (bl ? -1.0 : 1.0));
      int[] m = RenderComponent183.computeValue_2555(i);
      int n = m[0];
      int o = m[1];
      int p = m[2];
      int q = m[3];
      double r = vec3d.getX() - vec3d1.x;
      double s = vec3d.getZ() - vec3d1.z;
      ByteBuffer byteBuffer = MemoryUtil.memAlloc(11584);

      for (int t = 0; t <= 360; t++) {
         double u = Math.toRadians(t);
         float w = (float)(r + Math.cos(u) * g);
         float x = (float)(s + Math.sin(u) * g);
         RenderComponent189.performAction_2652(byteBuffer, w, k, x, n, o, p, (int)(q * 0.55F));
         RenderComponent189.performAction_2652(byteBuffer, w, k + l, x, n, o, p, 0);
      }

      byteBuffer.flip();
      RenderComponent189.performAction_2648(DrawMode.TRIANGLE_STRIP, matrix4f, byteBuffer, byteBuffer.remaining() / 16);
      MemoryUtil.memFree(byteBuffer);
      ByteBuffer byteBuffer1 = MemoryUtil.memAlloc(11520);

      for (int y = 0; y < 360; y++) {
         double v = Math.toRadians(y);
         double z = Math.toRadians(y + 1);
         RenderComponent189.performAction_2652(byteBuffer1, (float)(r + Math.cos(v) * g), k, (float)(s + Math.sin(v) * g), n, o, p, q);
         RenderComponent189.performAction_2652(byteBuffer1, (float)(r + Math.cos(z) * g), k, (float)(s + Math.sin(z) * g), n, o, p, q);
      }

      byteBuffer1.flip();
      RenderComponent189.performAction_2648(DrawMode.DEBUG_LINES, matrix4f, byteBuffer1, byteBuffer1.remaining() / 16);
      MemoryUtil.memFree(byteBuffer1);
   }

   private void performAction_1544(MatrixStack matrixStack, Vec3d vec3d, float f, float g, float h, int i, float j) {
      int[] k = RenderComponent183.computeValue_2555(i);
      Identifier identifier = Identifier.of("neneclient", "pictures/bloom.png");
      this.performAction_1542(matrixStack, vec3d, f, g, h, 1.5F * j, k[0], k[1], k[2], k[3], identifier);
      this.performAction_1542(matrixStack, vec3d, f, g, h, 0.6F * j, k[0], k[1], k[2], k[3], identifier);
   }

   private void performAction_1542(MatrixStack matrixStack, Vec3d vec3d, float f, float g, float h, float i, int j, int k, int l, int m, Identifier identifier) {
      AbstractTexture abstractTexture = minecraft.getTextureManager().getTexture(identifier);
      if (abstractTexture != null && abstractTexture.getGlTexture() != null) {
         GpuTextureView gpuTextureView = RenderSystem.getDevice().createTextureView(abstractTexture.getGlTexture());
         if (gpuTextureView != null) {
            try {
               this.performAction_1541(matrixStack, vec3d, f, g, h, i, j, k, l, m, gpuTextureView);
            } finally {
               gpuTextureView.close();
            }
         }
      }
   }

   private void performAction_1541(
      MatrixStack matrixStack, Vec3d vec3d, float f, float g, float h, float i, int j, int k, int l, int m, GpuTextureView gpuTextureView
   ) {
      Quaternionf quaternionf = minecraft.gameRenderer.getCamera().getRotation();
      Vec3d vec3d1 = minecraft.gameRenderer.getCamera().getCameraPos();
      float n = i / 2.0F;
      ByteBuffer byteBuffer = MemoryUtil.memAlloc(2592);
      Matrix4f matrix4f = new Matrix4f(matrixStack.peek().getPositionMatrix());

      for (byte b = 0; b < 360; b += 20) {
         float o = (float)Math.toRadians(b + h * 0.3F);
         float p = (float)Math.sin(o) * f * g;
         float q = (float)Math.cos(o) * f * g;
         float r = 0.1F + this.livingEntityField5.getHeight() * Math.abs((float)Math.sin(b));
         Matrix4f matrix4f1 = new Matrix4f(matrix4f);
         matrix4f1.translate((float)(vec3d.getX() + p - vec3d1.x), (float)(vec3d.getY() + r - vec3d1.y), (float)(vec3d.getZ() + q - vec3d1.z));
         matrix4f1.rotate(quaternionf);
         Vector3f vector3f = new Vector3f(-n, -n, 0.0F).mulPosition(matrix4f1);
         Vector3f vector3f1 = new Vector3f(-n, n, 0.0F).mulPosition(matrix4f1);
         Vector3f vector3f2 = new Vector3f(n, n, 0.0F).mulPosition(matrix4f1);
         Vector3f vector3f3 = new Vector3f(n, -n, 0.0F).mulPosition(matrix4f1);
         RenderComponent189.performAction_2651(byteBuffer, vector3f.x, vector3f.y, vector3f.z, 0.0F, 0.0F, j, k, l, m);
         RenderComponent189.performAction_2651(byteBuffer, vector3f1.x, vector3f1.y, vector3f1.z, 0.0F, 1.0F, j, k, l, m);
         RenderComponent189.performAction_2651(byteBuffer, vector3f2.x, vector3f2.y, vector3f2.z, 1.0F, 1.0F, j, k, l, m);
         RenderComponent189.performAction_2651(byteBuffer, vector3f.x, vector3f.y, vector3f.z, 0.0F, 0.0F, j, k, l, m);
         RenderComponent189.performAction_2651(byteBuffer, vector3f2.x, vector3f2.y, vector3f2.z, 1.0F, 1.0F, j, k, l, m);
         RenderComponent189.performAction_2651(byteBuffer, vector3f3.x, vector3f3.y, vector3f3.z, 1.0F, 0.0F, j, k, l, m);
      }

      byteBuffer.flip();
      RenderComponent189.performAction_2646(gpuTextureView, new Matrix4f(), byteBuffer, byteBuffer.remaining() / 24, true);
      MemoryUtil.memFree(byteBuffer);
   }

   private Vec3d computeValue_1540(LivingEntity livingEntity, float f) {
      return new Vec3d(
         MathHelper.lerp(f, livingEntity.lastX, livingEntity.getX()),
         MathHelper.lerp(f, livingEntity.lastY, livingEntity.getY()),
         MathHelper.lerp(f, livingEntity.lastZ, livingEntity.getZ())
      );
   }
}
