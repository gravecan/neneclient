package platform.inject.mixin;

import aethereal.core.NeneClient;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Session.class)
public class SessionMixin {
   @ModifyReturnValue(method = "getUsername", at = @At("RETURN"))
   private String username(String string) {
      return NeneClient.getInstance() != null && NeneClient.getInstance().getProcessors().getProxyManagerField2_0675().getSelected_2367() != null
         ? NeneClient.getInstance().getProcessors().getProxyManagerField2_0675().getSelected_2367().getName_2359()
         : string;
   }
}
