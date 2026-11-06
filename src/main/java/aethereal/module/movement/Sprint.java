package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import net.minecraft.entity.effect.StatusEffects;

@ModuleRegister(name = "Sprint", description = "Automatically turns on sprint when moving", category = Category.Movement)
public class Sprint extends Module {
   @EventTarget
   public void onEventComponent074_2089(EventComponent074 eventComponent074) {
      minecraft.player
         .setSprinting(
            minecraft.player.input.getMovementInput().y > 0.0F
               && !minecraft.player.hasStatusEffect(StatusEffects.BLINDNESS)
               && (minecraft.player.getAbilities().invulnerable || minecraft.player.getHungerManager().getFoodLevel() > 6)
         );
   }
}
