package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent077;

@ModuleRegister(name = "No Friend Damage", description = "Does not allow you to damage your friends", category = Category.Combat)
public class NoFriendDamage extends Module {
   @EventTarget
   public void onEventComponent077_1616(EventComponent077 eventComponent077) {
      eventComponent077.performAction_0597(
         NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(eventComponent077.getEntity_1021().getName().getString())
      );
   }
}
