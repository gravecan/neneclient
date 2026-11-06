package platform.inject.mixin;

import aethereal.util.UtilComponent264;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.font.Alignment;
import net.minecraft.client.font.DrawnTextConsumer;
import net.minecraft.client.font.DrawnTextConsumer.Transformation;
import net.minecraft.text.OrderedText;
import org.joml.Matrix3x2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/client/gui/hud/ChatHud$Hud")
public class ChatHudHudMixin {
   @WrapOperation(
      method = "text",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/font/DrawnTextConsumer;text(Lnet/minecraft/client/font/Alignment;IILnet/minecraft/client/font/DrawnTextConsumer$Transformation;Lnet/minecraft/text/OrderedText;)V"
      )
   )
   private void neneclient$slideIn(
      DrawnTextConsumer drawnTextConsumer, Alignment alignment, int i, int j, Transformation transformation, OrderedText orderedText, Operation<Void> operation
   ) {
      float f = UtilComponent264.lineProgress;
      if (!(f >= 1.0F) && transformation != null) {
         Matrix3x2f matrix3x2f = new Matrix3x2f(transformation.pose()).translate(-(1.0F - f) * 8.0F, 0.0F);
         operation.call(new Object[]{drawnTextConsumer, alignment, i, j, transformation.withPose(matrix3x2f), orderedText});
      } else {
         operation.call(new Object[]{drawnTextConsumer, alignment, i, j, transformation, orderedText});
      }
   }
}
