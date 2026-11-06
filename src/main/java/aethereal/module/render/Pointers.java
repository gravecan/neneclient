package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent189;
import aethereal.setting.BooleanSetting;
import aethereal.setting.FloatSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.util.UtilComponent277;
import aethereal.util.UtilComponent279;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3x2fStack;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

@ModuleRegister(name = "Pointers", description = "Points rays in the direction of players", category = Category.Render)
public class Pointers extends Module {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Visual Settings",
      new BooleanSetting("Filter by friends", false),
      new BooleanSetting("Trace to Player", true),
      new BooleanSetting("Navigation arrow", true)
   );
   private final FloatSetting floatSettingField2 = new FloatSetting("Arrow size", 7.0F, 5.0F, 15.0F, 1.0F);
   private final FloatSetting floatSettingField3 = new FloatSetting("Offset from center", 30.0F, 20.0F, 50.0F, 1.0F);
   private float floatValueField4;

   public Pointers() {
      this.addSettings(this.multiSelectSettingField1, this.floatSettingField2, this.floatSettingField3);
   }

   @EventTarget
   public void onDrawEvent_2262(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1002() && this.multiSelectSettingField1.computeValue_2852("Trace to Player").getCurrentValue()) {
         Camera camera = minecraft.gameRenderer.getCamera();
         Vec3d vec3d = camera.getCameraPos();
         Vec3d vec3d1 = new Vec3d(0.0, 0.0, 27.0)
            .rotateX((float)(-Math.toRadians(camera.getPitch())))
            .rotateY((float)(-Math.toRadians(camera.getYaw())))
            .add(vec3d);
         Matrix4f matrix4f = drawEvent.getStack2_1004().peek().getPositionMatrix();
         ArrayList arrayList = new ArrayList();

         for (Entity entity : minecraft.world.getEntities()) {
            if (entity instanceof ClientPlayerEntity clientPlayerEntity && clientPlayerEntity != minecraft.player && clientPlayerEntity.isAlive()) {
               Vec3d vec3d2 = UtilComponent279.computeValue_3585(clientPlayerEntity, drawEvent.getTickNeneClient_0997())
                  .add(0.0, clientPlayerEntity.getHeight() / 2.0F, 0.0);
               boolean bl = NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(clientPlayerEntity.getName().getString());
               if (!this.multiSelectSettingField1.computeValue_2852("Filter by friends").getCurrentValue() || bl) {
                  float f = bl ? 0.0F : 1.0F;
                  float g = 1.0F;
                  float h = 1.0F;
                  arrayList.add(new float[]{(float)(vec3d1.getX() - vec3d.x), (float)(vec3d1.getY() - vec3d.y), (float)(vec3d1.getZ() - vec3d.z), f, g, h});
                  arrayList.add(new float[]{(float)(vec3d2.getX() - vec3d.x), (float)(vec3d2.getY() - vec3d.y), (float)(vec3d2.getZ() - vec3d.z), f, g, h});
               }
            }
         }

         if (!arrayList.isEmpty()) {
            ByteBuffer byteBuffer = MemoryUtil.memAlloc(arrayList.size() * 20);

            for (float[] j : arrayList) {
               RenderComponent189.performAction_2652(byteBuffer, j[0], j[1], j[2], (int)(j[3] * 255.0F), (int)(j[4] * 255.0F), (int)(j[5] * 255.0F), 255);
            }

            byteBuffer.flip();
            RenderComponent189.performAction_2648(DrawMode.DEBUG_LINES, matrix4f, byteBuffer, arrayList.size());
            MemoryUtil.memFree(byteBuffer);
         }
      }

      if (drawEvent.checkCondition_1001() && this.multiSelectSettingField1.computeValue_2852("Navigation arrow").getCurrentValue()) {
         this.floatValueField4 = UtilComponent279.computeValue_3586(
            this.floatValueField4, this.floatValueField4 + MathHelper.wrapDegrees(UtilComponent277.getFloatValueField2_3570() - this.floatValueField4), 2.0F
         );

         for (Entity entity1 : minecraft.world.getEntities()) {
            if (entity1 instanceof ClientPlayerEntity clientPlayerEntity1 && clientPlayerEntity1 != minecraft.player && clientPlayerEntity1.isAlive()) {
               boolean bl1 = NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(clientPlayerEntity1.getName().getString());
               if (!this.multiSelectSettingField1.computeValue_2852("Filter by friends").getCurrentValue() || bl1) {
                  Vec3d vec3d3 = UtilComponent279.computeValue_3585(clientPlayerEntity1, drawEvent.getTickNeneClient_0997());
                  Vec3d vec3d4 = UtilComponent279.computeValue_3585(minecraft.player, drawEvent.getTickNeneClient_0997());
                  float i = MathHelper.wrapDegrees(
                     (float)Math.toDegrees(Math.atan2(vec3d4.x - vec3d3.getX(), vec3d3.getZ() - vec3d4.z)) - this.floatValueField4
                  );
                  float k = (float)Math.toRadians(i);
                  Matrix3x2fStack matrix3x2fStack = drawEvent.getContext_1000().getMatrices();
                  matrix3x2fStack.pushMatrix();
                  matrix3x2fStack.translate(
                     minecraft.getWindow().getScaledWidth() / 2.0F + (float)Math.sin(k) * this.floatSettingField3.getCurrentValue(),
                     minecraft.getWindow().getScaledHeight() / 2.0F - (float)Math.cos(k) * this.floatSettingField3.getCurrentValue()
                  );
                  matrix3x2fStack.rotate(k);
                  drawEvent.getRenderComponent184Field1_0998()
                     .performAction_2585(
                        matrix3x2fStack,
                        Identifier.of("neneclient", "pictures/pointer.png"),
                        -this.floatSettingField2.getCurrentValue() / 2.0F,
                        -this.floatSettingField2.getCurrentValue() / 2.0F,
                        this.floatSettingField2.getCurrentValue(),
                        this.floatSettingField2.getCurrentValue(),
                        0.0F,
                        bl1 ? RenderComponent183.computeValue_2558(85, 255, 85, 178) : RenderComponent183.computeValue_2558(255, 255, 255, 178)
                     );
                  matrix3x2fStack.popMatrix();
               }
            }
         }
      }
   }
}
