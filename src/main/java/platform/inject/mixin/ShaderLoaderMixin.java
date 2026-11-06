package platform.inject.mixin;

import aethereal.render.pipeline.PipelineComponent204;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.CompiledRenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.shaders.ShaderType;
import com.mojang.blaze3d.systems.GpuDevice;
import net.minecraft.client.gl.ShaderLoader;
import net.minecraft.client.gl.ShaderSourceGetter;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShaderLoader.class)
public class ShaderLoaderMixin {
   @WrapOperation(
      method = "apply",
      at = @At(
         value = "INVOKE",
         target = "Lcom/mojang/blaze3d/systems/GpuDevice;precompilePipeline(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/client/gl/ShaderSourceGetter;)Lcom/mojang/blaze3d/pipeline/CompiledRenderPipeline;"
      )
   )
   private CompiledRenderPipeline neneclient$wrapPrecompile(
      GpuDevice gpuDevice, RenderPipeline renderPipeline, ShaderSourceGetter shaderSourceGetter, Operation<CompiledRenderPipeline> operation
   ) {
      ShaderSourceGetter shaderSourceGetter1 = (identifier, shaderType) -> {
         String string = PipelineComponent204.computeValue_2814(identifier, shaderType);
         if (string != null) {
            System.out.println("[NeneClient] Precompiling embedded shader: " + identifier + " (" + shaderType + ")");
            return string;
         } else {
            return shaderSourceGetter.get(identifier, shaderType);
         }
      };
      return (CompiledRenderPipeline)operation.call(new Object[]{gpuDevice, renderPipeline, shaderSourceGetter1});
   }

   @Inject(method = "getSource", at = @At("HEAD"), cancellable = true)
   private void neneclient$provideEmbeddedSource(Identifier identifier, ShaderType shaderType, CallbackInfoReturnable<String> callbackInfoReturnable) {
      String string = PipelineComponent204.computeValue_2814(identifier, shaderType);
      if (string != null) {
         System.out.println("[NeneClient] ShaderLoader.getSource provided embedded source for: " + identifier + " (" + shaderType + ")");
         callbackInfoReturnable.setReturnValue(string);
      }
   }
}
