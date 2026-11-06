package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import net.minecraft.item.Items;
import platform.inject.accessors.MinecraftClientAccessor;

@ModuleRegister(name = "Fast EXP", description = "Allows you to drop experience very quickly", category = Category.Player)
public class FastEXP extends Module implements MinecraftAccess {
   @EventTarget
   public void onEventComponent074_2198(EventComponent074 eventComponent074) {
      if (minecraft.player.getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE) {
         ((MinecraftClientAccessor)minecraft).setItemUseCooldown(0);
      }
   }
}
