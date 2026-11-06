package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.handler.HandlerComponent108;
import aethereal.setting.BooleanSetting;
import aethereal.setting.FloatSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.util.UtilComponent268;
import aethereal.util.UtilComponent270;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@ModuleRegister(
   name = "Auto Totem",
   description = "Takes the Immortality Totem into hand when health drops below a specified value",
   category = Category.Combat
)
public class AutoTotem extends Module implements MinecraftAccess {
   private final FloatSetting floatSettingField1 = new FloatSetting("Health threshold", 6.0F, 1.0F, 20.0F, 0.5F);
   private final MultiSelectSetting multiSelectSettingField2 = new MultiSelectSetting(
      "Additional options",
      new BooleanSetting("Return Item", true),
      new BooleanSetting("First the regular totems", true),
      new BooleanSetting("Not while eating", false)
   );
   private final BooleanSetting booleanSettingField3 = new BooleanSetting("Smart Move", true);
   private final UtilComponent268 utilComponent268Field4 = new UtilComponent268();
   private final float[] floatValueArrayField5 = new float[20];
   private int integerField6 = -1;
   public boolean flagField7;

   public AutoTotem() {
      this.addSettings(this.floatSettingField1, this.multiSelectSettingField2, this.booleanSettingField3);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.integerField6 = -1;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.integerField6 = -1;
      this.flagField7 = false;
   }

   @EventTarget
   public void onEventComponent074_1588(EventComponent074 eventComponent074) {
      System.arraycopy(this.floatValueArrayField5, 0, this.floatValueArrayField5, 1, 19);
      this.floatValueArrayField5[0] = minecraft.player.getHealth()
         + (minecraft.player.hasStatusEffect(StatusEffects.ABSORPTION) ? minecraft.player.getAbsorptionAmount() : 0.0F);
      if (!this.multiSelectSettingField2.computeValue_2852("Not while eating").getCurrentValue() || !this.checkCondition_1592()) {
         Boolean boolean_ = this.computeValue_1593();
         this.flagField7 = boolean_ != null ? boolean_ : this.flagField7;
         if (boolean_ != null) {
            if (boolean_) {
               if (!this.utilComponent268Field4.checkCondition_3522(400L) || !this.checkCondition_1590()) {
                  return;
               }
            } else if (!this.checkCondition_1591()) {
               return;
            }

            this.utilComponent268Field4.performAction_3524();
         }
      }
   }

   private boolean checkCondition_1590() {
      ItemStack itemStack = minecraft.player.getOffHandStack();
      boolean bl = this.multiSelectSettingField2.computeValue_2852("First the regular totems").getCurrentValue();
      if (!this.checkCondition_1589(itemStack) || bl && itemStack.hasGlint()) {
         int i = bl ? UtilComponent270.computeValue_3540(Items.TOTEM_OF_UNDYING, false, true) : -1;
         if (i == -1 && !this.checkCondition_1589(itemStack)) {
            i = UtilComponent270.computeValue_3526(Items.TOTEM_OF_UNDYING);
         }

         if (i == -1) {
            return false;
         }

         if (this.multiSelectSettingField2.computeValue_2852("Return Item").getCurrentValue() && this.integerField6 == -1 && !itemStack.isEmpty()) {
            this.integerField6 = i;
         }

         HandlerComponent108.getINVENTORY_1158().performAction_1179(i, 40, 1);
         return true;
      } else {
         return false;
      }
   }

   private boolean checkCondition_1591() {
      ItemStack itemStack = minecraft.player.getOffHandStack();
      if (this.multiSelectSettingField2.computeValue_2852("Return Item").getCurrentValue()
         && this.integerField6 != -1
         && (itemStack.isEmpty() || this.checkCondition_1589(itemStack))) {
         HandlerComponent108.getINVENTORY_1158().performAction_1179(this.integerField6, 40, 1);
      }

      this.integerField6 = -1;
      return false;
   }

   private Boolean computeValue_1593() {
      if (minecraft.player.getItemCooldownManager().isCoolingDown(Items.TOTEM_OF_UNDYING.getDefaultStack())) {
         return false;
      }

      ItemStack itemStack = minecraft.player.getActiveItem();
      if (this.booleanSettingField3.getCurrentValue() && this.checkCondition_1592() && itemStack.getItem().getMaxUseTime(itemStack, minecraft.player) > 5) {
         int i = minecraft.player.getItemUseTimeLeft();
         if (i < 10) {
            return null;
         }

         if (i < 30 && (this.floatValueArrayField5[19] - this.floatValueArrayField5[0]) / 20.0F * i < this.floatValueArrayField5[0]) {
            return null;
         }
      }

      if (this.floatValueArrayField5[0] <= this.floatSettingField1.getCurrentValue()) {
         return true;
      } else {
         return this.floatValueArrayField5[0] >= this.floatSettingField1.getCurrentValue() + 0.5F ? false : null;
      }
   }

   private boolean checkCondition_1592() {
      ItemStack itemStack = minecraft.player.getActiveItem();
      return minecraft.player.isUsingItem() && !itemStack.isEmpty() && itemStack.get(DataComponentTypes.FOOD) != null;
   }

   private boolean checkCondition_1589(ItemStack itemStack) {
      return !itemStack.isEmpty() && itemStack.getItem() == Items.TOTEM_OF_UNDYING;
   }
}
