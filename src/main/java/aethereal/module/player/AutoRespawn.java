package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import net.minecraft.client.gui.screen.DeathScreen;

@ModuleRegister(name = "Auto Respawn", description = "Automatically revives a character after death", category = Category.Player)
public class AutoRespawn extends Module {
   @EventTarget
   public void onEventComponent074_2148(EventComponent074 eventComponent074) {
      if (minecraft.currentScreen instanceof DeathScreen && minecraft.player.deathTime >= 5) {
         minecraft.player.requestRespawn();
      }
   }
}
