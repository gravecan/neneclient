package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.EventComponent073;
import net.minecraft.text.TextVisitFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TextVisitFactory.class)
public class TextVisitFactoryMixin {
   @ModifyArg(
      method = "visitFormatted",
      index = 0,
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/text/TextVisitFactory;visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z",
         ordinal = 0
      )
   )
   private static String visitFormatted(String string) {
      EventComponent073 eventComponent073 = new EventComponent073(string);
      EventBus.post(eventComponent073);
      return eventComponent073.getText_0995();
   }
}
