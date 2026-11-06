package platform.inject.mixin;

import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugHud.class)
public class DebugHudMixin implements MinecraftAccess {
   @Inject(method = "drawText", at = @At("HEAD"))
   private void onDrawText(DrawContext drawContext, List<String> list, boolean bl, CallbackInfo callbackInfo) {
      this.replaceText(list, bl);
   }

   @Unique
   private List<String> replaceText(List<String> list, boolean bl) {
      if (NeneClient.getInstance().getProcessors().getModules().getFreeCameraField13_0446().isEnabled()) {
         Vec3d vec3d = minecraft.gameRenderer
            .getCamera()
            .getCameraPos()
            .add(Vec3d.fromPolar(minecraft.gameRenderer.getCamera().getPitch(), minecraft.gameRenderer.getCamera().getYaw()).multiply(20.0));
         BlockHitResult blockHitResult = Objects.requireNonNull(minecraft.world)
            .raycast(new RaycastContext(minecraft.gameRenderer.getCamera().getCameraPos(), vec3d, ShapeType.OUTLINE, FluidHandling.NONE, minecraft.player));
         String string = String.format(
            Locale.ROOT,
            "%.3f / %.5f / %.3f",
            minecraft.gameRenderer.getCamera().getCameraPos().x,
            minecraft.gameRenderer.getCamera().getCameraPos().y,
            minecraft.gameRenderer.getCamera().getCameraPos().z
         );
         String string1 = "—";
         if (blockHitResult instanceof BlockHitResult blockHitResult1 && blockHitResult.getType() == Type.BLOCK) {
            BlockPos blockPos = blockHitResult1.getBlockPos();
            string1 = minecraft.world.getBlockState(blockPos).getBlock().getName().getString()
               + " ["
               + blockPos.getX()
               + ", "
               + blockPos.getY()
               + ", "
               + blockPos.getZ()
               + "]";
         }

         for (int j = 0; j < list.size(); j++) {
            String string2 = (String)list.get(j);
            if (bl && string2.contains("XYZ:") && string2.contains(" / ")) {
               string2 = string2.replaceFirst("XYZ:\\s*[\\d.-]+\\s*/\\s*[\\d.-]+\\s*/\\s*[\\d.-]+", "XYZ: " + string);
            }

            if (string2.contains("Targeted Block")) {
               int i = string2.indexOf(58) + 1;
               string2 = string2.substring(0, Math.min(i, string2.length())).trim() + " " + string1;
            }

            list.set(j, string2);
         }

         return list;
      } else {
         return list;
      }
   }
}
