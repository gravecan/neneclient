package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

public class EventComponent089 extends CancellableEvent implements Event {
   private MatrixStack matrix;
   private VertexConsumerProvider vertexConsumerProvider;
   private PlayerEntity player;
   private ModelWithHead model;

   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof EventComponent089 eventComponent0891)) {
         return false;
      } else if (eventComponent0891.checkCondition_1053(this) && super.equals(object)) {
         MatrixStack matrixStack = this.getMatrix_1049();
         MatrixStack matrixStack1 = eventComponent0891.getMatrix_1049();
         if (matrixStack == null) {
            if (matrixStack1 != null) {
               return false;
            }
         } else if (!matrixStack.equals(matrixStack1)) {
            return false;
         }

         VertexConsumerProvider vertexConsumerProviderx = this.getVertexConsumerProvider_1052();
         VertexConsumerProvider vertexConsumerProvider1 = eventComponent0891.getVertexConsumerProvider_1052();
         if (vertexConsumerProviderx == null) {
            if (vertexConsumerProvider1 != null) {
               return false;
            }
         } else if (!vertexConsumerProviderx.equals(vertexConsumerProvider1)) {
            return false;
         }

         PlayerEntity playerEntity = this.getPlayer_1050();
         PlayerEntity playerEntity1 = eventComponent0891.getPlayer_1050();
         if (playerEntity == null) {
            if (playerEntity1 != null) {
               return false;
            }
         } else if (!playerEntity.equals(playerEntity1)) {
            return false;
         }

         ModelWithHead modelWithHead = this.getModel_1051();
         ModelWithHead modelWithHead1 = eventComponent0891.getModel_1051();
         return modelWithHead == null ? modelWithHead1 == null : modelWithHead.equals(modelWithHead1);
      } else {
         return false;
      }
   }

   protected boolean checkCondition_1053(Object object) {
      return object instanceof EventComponent089;
   }

   @Override
   public int hashCode() {
      int i = super.hashCode();
      MatrixStack matrixStack = this.getMatrix_1049();
      int j = i * 59 + (matrixStack == null ? 43 : matrixStack.hashCode());
      VertexConsumerProvider vertexConsumerProviderx = this.getVertexConsumerProvider_1052();
      int k = j * 59 + (vertexConsumerProviderx == null ? 43 : vertexConsumerProviderx.hashCode());
      PlayerEntity playerEntity = this.getPlayer_1050();
      int l = k * 59 + (playerEntity == null ? 43 : playerEntity.hashCode());
      ModelWithHead modelWithHead = this.getModel_1051();
      return l * 59 + (modelWithHead == null ? 43 : modelWithHead.hashCode());
   }

   public EventComponent089(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, PlayerEntity playerEntity, ModelWithHead modelWithHead) {
      this.matrix = matrixStack;
      this.vertexConsumerProvider = vertexConsumerProviderx;
      this.player = playerEntity;
      this.model = modelWithHead;
   }

   public void setMatrix_1056(MatrixStack matrixStack) {
      this.matrix = matrixStack;
   }

   public void setVertexConsumerProvider_1057(VertexConsumerProvider vertexConsumerProvider) {
      this.vertexConsumerProvider = vertexConsumerProviderx;
   }

   public void setPlayer_1054(PlayerEntity playerEntity) {
      this.player = playerEntity;
   }

   public void setModel_1055(ModelWithHead modelWithHead) {
      this.model = modelWithHead;
   }

   @Override
   public String toString() {
      return "HeadFeatureEvent(matrix="
         + this.getMatrix_1049()
         + ", vertexConsumerProvider="
         + this.getVertexConsumerProvider_1052()
         + ", player="
         + this.getPlayer_1050()
         + ", model="
         + this.getModel_1051()
         + ")";
   }

   public MatrixStack getMatrix_1049() {
      return this.matrix;
   }

   public VertexConsumerProvider getVertexConsumerProvider_1052() {
      return this.vertexConsumerProvider;
   }

   public PlayerEntity getPlayer_1050() {
      return this.player;
   }

   public ModelWithHead getModel_1051() {
      return this.model;
   }
}
