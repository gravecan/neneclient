package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.setting.FloatSetting;
import java.util.Set;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@ModuleRegister(name = "Auto Eat", description = "Automatically satisfies hunger when it falls", category = Category.Player)
public class AutoEat extends Module {
   private final FloatSetting floatSettingField1 = new FloatSetting("Eat when hungry", 16.0F, 1.0F, 20.0F, 1.0F);

   public AutoEat() {
      this.addSettings(this.floatSettingField1);
   }

   @EventTarget
   public void onEventComponent074_2135(EventComponent074 eventComponent074) {
      this.performAction_2137(this.floatSettingField1.getCurrentValue().intValue());
   }

   public void performAction_2137(int i) {
      int j;
      if (minecraft.player != null && minecraft.player.getHungerManager().getFoodLevel() < i && (j = this.computeValue_2136()) >= 0) {
         NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getHandlerComponent109Field8_1145().performAction_1162(j);
      }
   }

   private int computeValue_2136() {
      Set set = Set.of(Items.CHORUS_FRUIT, Items.PUFFERFISH, Items.DRIED_KELP);
      Set set1 = Set.of(Items.BEEF, Items.PORKCHOP, Items.CHICKEN, Items.MUTTON, Items.RABBIT, Items.COD, Items.SALMON, Items.POTATO);
      int i = -1;
      int j = Integer.MAX_VALUE;

      for (int k = 0; k < 36 && j > 0; k++) {
         ItemStack itemStack = minecraft.player.getInventory().getStack(k);
         if (itemStack.contains(DataComponentTypes.FOOD) && !set.contains(itemStack.getItem()) && !itemStack.contains(DataComponentTypes.CUSTOM_NAME)) {
            byte b;
            if (itemStack.isOf(Items.ENCHANTED_GOLDEN_APPLE)) {
               b = 3;
            } else if (itemStack.isOf(Items.GOLDEN_APPLE)) {
               b = 2;
            } else {
               b = (byte)(set1.contains(itemStack.getItem()) ? 1 : 0);
            }

            byte c = b;
            if (c < j) {
               j = c;
               i = k;
            }
         }
      }

      return i;
   }
}
