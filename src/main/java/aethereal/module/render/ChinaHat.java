package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent189;
import aethereal.setting.ColorSetting;
import aethereal.ui.shader.ShaderComponent231;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

@ModuleRegister(name = "China Hat", description = "Places a Chinese hat on your character's head", category = Category.Render)
public class ChinaHat extends Module {
   private final ColorSetting colorSettingField1 = new ColorSetting("Hat visualization color", RenderComponent183.computeValue_2558(133, 156, 255, 255));

   public ChinaHat() {
      this.addSettings(this.colorSettingField1);
   }

   @EventTarget
   public void onDrawEvent_2236(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1002() && minecraft.world != null && minecraft.player != null) {
         Camera camera = minecraft.gameRenderer.getCamera();
         Vec3d vec3d = camera.getCameraPos();
         ArrayList arrayList = new ArrayList();
         long l = System.currentTimeMillis();

         for (PlayerEntity playerEntity : minecraft.world
            .getEntitiesByClass(PlayerEntity.class, minecraft.player.getBoundingBox().expand(64.0), playerEntity1 -> true)) {
            boolean bl = NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(playerEntity.getName().getString());
            if ((playerEntity == minecraft.player || bl)
               && (playerEntity != minecraft.player || !minecraft.options.getPerspective().isFirstPerson())
               && playerEntity.isAlive()) {
               int i = playerEntity == minecraft.player ? this.colorSettingField1.getCurrentValue() : RenderComponent183.computeValue_2558(0, 155, 0, 255);
               Vec3d vec3d1 = playerEntity.getEntityPos();
               float f = (float)Math.toRadians(playerEntity.getHeadYaw());
               float g = (float)Math.toRadians(playerEntity.getPitch());
               float h = -MathHelper.sin(f);
               float j = MathHelper.cos(f);
               float k = MathHelper.cos(f);
               float m = MathHelper.sin(f);
               float n = MathHelper.cos(g);
               float o = MathHelper.sin(g);
               float p = (float)(playerEntity.getEyePos().y - vec3d1.y) + (playerEntity.getInventory().getStack(39).isEmpty() ? 0.25F : 0.31F);
               float q = (float)(vec3d1.x - vec3d.x);
               float r = (float)(vec3d1.y - vec3d.y);
               float s = (float)(vec3d1.z - vec3d.z);
               float t = (float)(playerEntity.getBoundingBox().maxX - playerEntity.getBoundingBox().minX);
               short u = 180;

               for (int v = 0; v < u; v++) {
                  float w = (float)(v * Math.PI * 2.0 / u);
                  float x = (float)((v + 1) * Math.PI * 2.0 / u);
                  int y = ShaderComponent231.computeValue_3294(3, v * 8, i, RenderComponent183.computeValue_2552(i, 0.5F), l);
                  float[] z = computeValue_2237(q, r + p, s, k, m, h, j, n, o, MathHelper.sin(w) * t, 0.0F, MathHelper.cos(w) * t);
                  float[] ba = computeValue_2237(q, r + p, s, k, m, h, j, n, o, MathHelper.sin(x) * t, 0.0F, MathHelper.cos(x) * t);
                  float[] bb = computeValue_2237(q, r + p, s, k, m, h, j, n, o, 0.0F, 0.3F, 0.0F);
                  arrayList.add(computeValue_2238(z, y));
                  arrayList.add(computeValue_2238(ba, y));
                  arrayList.add(computeValue_2238(bb, y));
                  float bc = t - 0.02F;
                  float[] bd = computeValue_2237(q, r + p, s, k, m, h, j, n, o, MathHelper.sin(w) * t, 0.0F, MathHelper.cos(w) * t);
                  float[] be = computeValue_2237(q, r + p, s, k, m, h, j, n, o, MathHelper.sin(x) * t, 0.0F, MathHelper.cos(x) * t);
                  float[] bf = computeValue_2237(q, r + p, s, k, m, h, j, n, o, MathHelper.sin(x) * bc, 0.0F, MathHelper.cos(x) * bc);
                  float[] bg = computeValue_2237(q, r + p, s, k, m, h, j, n, o, MathHelper.sin(w) * bc, 0.0F, MathHelper.cos(w) * bc);
                  arrayList.add(computeValue_2238(bd, y));
                  arrayList.add(computeValue_2238(be, y));
                  arrayList.add(computeValue_2238(bf, y));
                  arrayList.add(computeValue_2238(bd, y));
                  arrayList.add(computeValue_2238(bf, y));
                  arrayList.add(computeValue_2238(bg, y));
               }
            }
         }

         if (!arrayList.isEmpty()) {
            Matrix4f matrix4f = drawEvent.getStack2_1004().peek().getPositionMatrix();
            ByteBuffer byteBuffer = MemoryUtil.memAlloc(arrayList.size() * 20);

            for (float[] bh : arrayList) {
               RenderComponent189.performAction_2652(byteBuffer, bh[0], bh[1], bh[2], (int)bh[3], (int)bh[4], (int)bh[5], (int)bh[6]);
            }

            byteBuffer.flip();
            RenderComponent189.performAction_2648(DrawMode.TRIANGLES, matrix4f, byteBuffer, arrayList.size());
            MemoryUtil.memFree(byteBuffer);
         }
      }
   }

   private static float[] computeValue_2237(float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, float p, float q) {
      float r = o * i + (q * m + p * n) * k;
      float s = p * m - q * n;
      float t = o * j + (q * m + p * n) * l;
      return new float[]{f + r, g + s, h + t};
   }

   private static float[] computeValue_2238(float[] f, int i) {
      int[] j = RenderComponent183.computeValue_2555(i);
      return new float[]{f[0], f[1], f[2], j[0], j[1], j[2], j[3]};
   }
}
