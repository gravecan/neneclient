package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.ContainerEvent;
import aethereal.event.EventComponent098;
import aethereal.setting.BooleanSetting;
import aethereal.util.UtilComponent268;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

@ModuleRegister(name = "Chest Stealer", description = "Automatically picks up items from an open chest", category = Category.Player)
public class ChestStealer extends Module implements MinecraftAccess {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Ignore entities", true);
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Auto-closing chest", true);
   private final UtilComponent268 utilComponent268Field3 = new UtilComponent268();

   public BooleanSetting getBooleanSettingField1_2165() {
      return this.booleanSettingField1;
   }

   public BooleanSetting getBooleanSettingField2_2161() {
      return this.booleanSettingField2;
   }

   public UtilComponent268 getUtilComponent268Field3_2167() {
      return this.utilComponent268Field3;
   }

   public ChestStealer() {
      this.addSettings(this.booleanSettingField1, this.booleanSettingField2);
   }

   @EventTarget
   public void onEventComponent098_2163(EventComponent098 eventComponent098) {
      if (this.booleanSettingField1.getCurrentValue()) {
         eventComponent098.performAction_0597(true);
      }
   }

   @EventTarget
   public void onContainerEvent_2162(ContainerEvent containerEvent) {
      Slot slot;
      if (containerEvent.getPhase_0988() == ContainerEvent.Phase.POST
         && (containerEvent.getScreen_0992() instanceof GenericContainerScreen || containerEvent.getScreen_0992() instanceof ShulkerBoxScreen)
         && (
               slot = containerEvent.getSlots_0987()
                  .stream()
                  .filter(slot1 -> slot1.inventory != minecraft.player.getInventory())
                  .filter(slot1 -> slot1.hasStack())
                  .findFirst()
                  .orElse(null)
            )
            != null
         && this.utilComponent268Field3.checkCondition_3523(5L, 5L)) {
         minecraft.interactionManager.clickSlot(containerEvent.getHandler_0989().syncId, slot.id, 0, SlotActionType.QUICK_MOVE, minecraft.player);
         boolean bl = containerEvent.getSlots_0987()
            .stream()
            .filter(slot1 -> slot1.inventory != minecraft.player.getInventory())
            .noneMatch(slot2 -> slot2.hasStack() && slot2 != slot);
         if (bl && this.booleanSettingField2.getCurrentValue()) {
            minecraft.player.closeScreen();
         }

         this.utilComponent268Field3.performAction_3524();
      }
   }
}
