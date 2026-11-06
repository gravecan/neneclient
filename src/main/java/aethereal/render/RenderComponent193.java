package aethereal.render;

import aethereal.core.MinecraftAccess;
import aethereal.render.pipeline.PipelineComponent196;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class RenderComponent193 implements MinecraftAccess {
   private static final Deque<RenderComponent193.NestedType374> dequeField1 = new ArrayDeque<>();
   private static final Matrix4f matrix4fField2 = new Matrix4f();

   private RenderComponent193() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static void performAction_2681(Matrix3x2f matrix3x2f, float f, float g, float h, float i) {
      PipelineComponent196.performAction_2728();
      float j = minecraft.getWindow().getScaleFactor();
      float k = f;
      float l = g;
      float m = h;
      float n = i;
      if (matrix3x2f != null) {
         float o = matrix3x2f.m00 * f + matrix3x2f.m10 * g + matrix3x2f.m20;
         float p = matrix3x2f.m01 * f + matrix3x2f.m11 * g + matrix3x2f.m21;
         float q = matrix3x2f.m00 * (f + h) + matrix3x2f.m10 * (g + i) + matrix3x2f.m20;
         float r = matrix3x2f.m01 * (f + h) + matrix3x2f.m11 * (g + i) + matrix3x2f.m21;
         k = Math.min(o, q);
         l = Math.min(p, r);
         m = Math.abs(q - o);
         n = Math.abs(r - p);
      }

      RenderComponent193.NestedType374 nestedType374 = new RenderComponent193.NestedType374(
         (int)(k * j), (int)((minecraft.getWindow().getScaledHeight() - l - n) * j), (int)(m * j), (int)(n * j)
      );
      if (!dequeField1.isEmpty()) {
         nestedType374 = nestedType374.computeValue_2684(dequeField1.peek());
      }

      dequeField1.push(nestedType374);
      if (matrix3x2f instanceof Matrix3x2fStack matrix3x2fStack) {
         matrix3x2fStack.pushMatrix();
      }

      performAction_2677(nestedType374);
   }

   public static void performAction_2680(Matrix3x2f matrix3x2f) {
      PipelineComponent196.performAction_2728();
      if (!dequeField1.isEmpty()) {
         dequeField1.pop();
      }

      if (dequeField1.isEmpty()) {
         GL11.glDisable(3089);
      } else {
         performAction_2677(dequeField1.peek());
      }

      if (matrix3x2f instanceof Matrix3x2fStack matrix3x2fStack) {
         matrix3x2fStack.popMatrix();
      }
   }

   public static void performAction_2679(MatrixStack matrixStack, float f, float g, float h, float i) {
      PipelineComponent196.performAction_2728();
      float j = minecraft.getWindow().getScaleFactor();
      RenderComponent193.NestedType374 nestedType374 = new RenderComponent193.NestedType374(
         (int)(f * j), (int)((minecraft.getWindow().getScaledHeight() - g - i) * j), (int)(h * j), (int)(i * j)
      );
      if (!dequeField1.isEmpty()) {
         nestedType374 = nestedType374.computeValue_2684(dequeField1.peek());
      }

      dequeField1.push(nestedType374);
      if (matrixStack != null) {
         matrixStack.push();
      }

      performAction_2677(nestedType374);
   }

   public static void performAction_2678(MatrixStack matrixStack) {
      PipelineComponent196.performAction_2728();
      if (!dequeField1.isEmpty()) {
         dequeField1.pop();
      }

      if (dequeField1.isEmpty()) {
         GL11.glDisable(3089);
      } else {
         performAction_2677(dequeField1.peek());
      }

      if (matrixStack != null) {
         matrixStack.pop();
      }
   }

   private static void performAction_2677(RenderComponent193.NestedType374 nestedType374) {
      GL11.glEnable(3089);
      GL11.glScissor(nestedType374.integerField1, nestedType374.integerField2, nestedType374.integerField3, nestedType374.integerField4);
   }

   static final class NestedType374 {
      final int integerField1;
      final int integerField2;
      final int integerField3;
      final int integerField4;

      NestedType374(int i, int j, int k, int l) {
         this.integerField1 = i;
         this.integerField2 = j;
         this.integerField3 = k;
         this.integerField4 = l;
      }

      public int getIntegerField1_2683() {
         return this.integerField1;
      }

      public int getIntegerField2_2682() {
         return this.integerField2;
      }

      public int getIntegerField3_2685() {
         return this.integerField3;
      }

      public int getIntegerField4_2686() {
         return this.integerField4;
      }

      RenderComponent193.NestedType374 computeValue_2684(RenderComponent193.NestedType374 nestedType3741) {
         int i = Math.max(this.integerField1, nestedType3741.integerField1);
         int j = Math.max(this.integerField2, nestedType3741.integerField2);
         return new RenderComponent193.NestedType374(
            i,
            j,
            Math.max(0, Math.min(this.integerField1 + this.integerField3, nestedType3741.integerField1 + nestedType3741.integerField3) - i),
            Math.max(0, Math.min(this.integerField2 + this.integerField4, nestedType3741.integerField2 + nestedType3741.integerField4) - j)
         );
      }
   }
}
