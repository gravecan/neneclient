package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.handler.HandlerComponent108;
import aethereal.setting.KeybindSetting;
import aethereal.util.UtilComponent270;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

@ModuleRegister(name = "Item Helper", description = "Moves the desired item and returns it back by pressing a key", category = Category.Combat)
public class ItemHelper extends Module implements MinecraftAccess {
   public ItemHelper() {
      List.of(
            new ItemHelper.NestedType338(this, "Enchanted Apple", Items.ENCHANTED_GOLDEN_APPLE),
            new ItemHelper.NestedType338(this, "Golden Apple", Items.GOLDEN_APPLE),
            new ItemHelper.NestedType338(this, "Horus Fruit", Items.CHORUS_FRUIT),
            new ItemHelper.NestedType338(this, "Crossbow", Items.CROSSBOW)
         )
         .forEach(nestedType338 -> this.addSettings(nestedType338.getKeybindSettingField1_1599()));
   }

   final class NestedType338 {
      private final Item item;
      private final KeybindSetting keybindSettingField1;
      private int integerField2 = -1;
      private int integerField3 = -1;

      public Item getItem_1598() {
         return this.item;
      }

      public KeybindSetting getKeybindSettingField1_1599() {
         return this.keybindSettingField1;
      }

      public int getIntegerField2_1601() {
         return this.integerField2;
      }

      public int getIntegerField3_1602() {
         return this.integerField3;
      }

      NestedType338(ItemHelper itemHelper, String string, Item item) {
         this.item = itemx;
         this.keybindSettingField1 = new KeybindSetting(string, -1, 1).computeValue_2847(this::performAction_1603);
      }

      private void performAction_1603() {
         if (this.integerField3 == -1) {
            this.performAction_1600();
         } else {
            this.performAction_1604();
         }
      }

      private void performAction_1600() {
         if (this.integerField3 == -1) {
            int i = UtilComponent270.computeValue_3526(this.item);
            int j = MinecraftAccess.minecraft.player.getInventory().getSelectedSlot();
            if (i != -1 && i != j) {
               this.integerField3 = i;
               this.integerField2 = j;
               HandlerComponent108.getINVENTORY_1158().performAction_1179(i, j, 1);
            }
         }
      }

      private void performAction_1604() {
         if (this.integerField3 != -1) {
            HandlerComponent108.getINVENTORY_1158().performAction_1179(this.integerField3, this.integerField2, 1);
            this.integerField3 = -1;
            this.integerField2 = -1;
         }
      }
   }
}
