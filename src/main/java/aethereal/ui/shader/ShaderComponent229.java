package aethereal.ui.shader;

import aethereal.core.MinecraftAccess;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix3x2f;

public class ShaderComponent229 implements MinecraftAccess {
   private final List<Object> listField1 = new ArrayList<>();

   public List<Object> getListField1_3283() {
      return this.listField1;
   }

   public void performAction_3285(MatrixStack matrixStack) {
      this.performAction_3284();
   }

   public void performAction_3286(Matrix3x2f matrix3x2f) {
      this.performAction_3284();
   }

   public void performAction_3284() {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient != null && minecraftClient.gameRenderer != null) {
         try {
            minecraftClient.gameRenderer.renderBlur();
         } catch (Throwable throwable) {
         }
      }
   }
}
