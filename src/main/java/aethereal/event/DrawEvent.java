package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent185;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix3x2f;

public class DrawEvent extends CancellableEvent implements Event, MinecraftAccess {
   private final RenderComponent184 renderComponent184Field1 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
   private final RenderComponent185 renderComponent185Field2 = NeneClient.getInstance().getProcessors().getRenderComponent185Field4_0678();
   private final DrawEvent.State315 type;
   private final float tickNeneClient;
   private final Matrix3x2f stack;
   private final MatrixStack stack2;
   private DrawContext context;

   public DrawEvent(MatrixStack matrixStack, float f, DrawEvent.State315 state315) {
      this.stack2 = matrixStack;
      this.stack = null;
      this.tickNeneClient = f;
      this.type = state315;
   }

   public RenderComponent184 getRenderComponent184Field1_0998() {
      return this.renderComponent184Field1;
   }

   public RenderComponent185 getRenderComponent185Field2_0999() {
      return this.renderComponent185Field2;
   }

   public DrawEvent.State315 getType_1003() {
      return this.type;
   }

   public float getTickNeneClient_0997() {
      return this.tickNeneClient;
   }

   public Matrix3x2f getStack_1005() {
      return this.stack;
   }

   public MatrixStack getStack2_1004() {
      return this.stack2;
   }

   public DrawContext getContext_1000() {
      return this.context;
   }

   public DrawEvent(Matrix3x2f matrix3x2f, float f, DrawEvent.State315 state315) {
      this.stack = matrix3x2f;
      this.stack2 = null;
      this.tickNeneClient = f;
      this.type = state315;
   }

   public DrawEvent(DrawContext drawContext, float f, DrawEvent.State315 state315) {
      this.context = drawContext;
      this.stack = drawContext.getMatrices();
      this.stack2 = null;
      this.tickNeneClient = f;
      this.type = state315;
   }

   public boolean checkCondition_1001() {
      return this.type == DrawEvent.State315.D2D;
   }

   public boolean checkCondition_1002() {
      return this.type == DrawEvent.State315.D3D;
   }

   public enum State315 {
      D2D,
      D3D;
   }
}
