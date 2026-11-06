package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.handler.HandlerComponent108;
import aethereal.handler.HandlerComponent117;
import aethereal.setting.BooleanSetting;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent270;
import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import platform.inject.accessors.ItemCooldownEntryAccessor;
import platform.inject.accessors.ItemCooldownManagerAccessor;

@ModuleRegister(name = "Mace Helper", description = "Automates actions when using a mace", category = Category.Combat)
public class MaceHelper extends Module {
   public int integerField1;
   public boolean flagField2;
   public BooleanSetting booleanSettingField3 = new BooleanSetting("Damage Boost", true);
   public BooleanSetting booleanSettingField4 = new BooleanSetting("Auto-switching clubs", false);
   int[] integerArrayField5 = new int[]{-1, -1};

   public BooleanSetting getBooleanSettingField3_1608() {
      return this.booleanSettingField3;
   }

   public BooleanSetting getBooleanSettingField4_1605() {
      return this.booleanSettingField4;
   }

   public int getIntegerField1_1610() {
      return this.integerField1;
   }

   public boolean isFlagField2_1607() {
      return this.flagField2;
   }

   public int[] getIntegerArrayField5_1609() {
      return this.integerArrayField5;
   }

   public MaceHelper() {
      this.addSettings(this.booleanSettingField3, this.booleanSettingField4);
   }

   @EventTarget
   public void onEventComponent074_1606(EventComponent074 eventComponent074) {
      this.integerField1--;
      List list = HandlerComponent108.getUSEABLE_1160().getListField1_1222();
      if (this.integerField1 <= 198 && minecraft.player.isOnGround() || minecraft.player.isTouchingWater() || minecraft.player.age < 5) {
         if (this.flagField2 && list.isEmpty()) {
            if (this.integerArrayField5[1] > 8) {
               HandlerComponent108.getINVENTORY_1158().performAction_1179(this.integerArrayField5[0], this.integerArrayField5[1], 1);
            } else {
               minecraft.player.getInventory().setSelectedSlot(this.integerArrayField5[0]);
            }

            this.flagField2 = false;
            this.integerArrayField5 = new int[]{-1, -1};
         }

         this.integerField1 = 0;
      }

      if (!list.isEmpty() && ((HandlerComponent117.NestedType327)list.getFirst()).getItemStack_1229().getItem() == Items.WIND_CHARGE) {
         this.integerField1 = 200;
      }

      int i = UtilComponent270.computeValue_3539(Items.MACE, true);
      int j = UtilComponent270.computeValue_3539(Items.MACE, false);
      if (this.booleanSettingField4.getCurrentValue() && j != -1) {
         if (!UtilComponent252.NestedType400.checkCondition_3406() || UtilComponent252.checkCondition_3401()) {
            ItemCooldownManagerAccessor itemCooldownManagerAccessor = (ItemCooldownManagerAccessor)minecraft.player.getItemCooldownManager();
            Object object = itemCooldownManagerAccessor.getEntries().get(minecraft.player.getItemCooldownManager().getGroup(Items.MACE.getDefaultStack()));
            if (object == null || ((ItemCooldownEntryAccessor)object).getEndTick() - itemCooldownManagerAccessor.getTick() <= 10) {
               Aura aura = NeneClient.getInstance().getProcessors().getModules().getAuraField3_0402();
               TriggerBot triggerBot = NeneClient.getInstance().getProcessors().getModules().getTriggerBotField4_0418();
               boolean bl = aura.getLivingEntityField2_1528() != null;
               LivingEntity livingEntity = bl ? aura.getLivingEntityField2_1528() : triggerBot.getLivingEntityField6_1669();
               if (livingEntity == null
                  || livingEntity.isBlocking()
                  || minecraft.player.isOnGround()
                  || CombatComponent166.checkCondition_1612()
                  || this.flagField2
                  || minecraft.player.fallDistance <= 0.0
                  || !list.isEmpty()
                  || !HandlerComponent108.getINVENTORY_1158().getListField1_1177().isEmpty()
                  || Math.hypot(
                        livingEntity.getEntityPos().x - minecraft.player.getEntityPos().x, livingEntity.getEntityPos().z - minecraft.player.getEntityPos().z
                     )
                     > 6.0) {
                  return;
               }

               Vec3d vec3d;
               if ((bl ? aura.integerField16 : triggerBot.integerField3) <= 1
                  || (vec3d = CombatComponent166.computeValue_1615(minecraft.player, minecraft.world).orElse(null)) == null
                  || minecraft.player.getY() + minecraft.player.getVelocity().y <= vec3d.getY()
                  || minecraft.player.getY() - vec3d.getY() <= 3.5) {
                  return;
               }

               this.flagField2 = true;
               int[] k = new int[]{minecraft.player.getInventory().getSelectedSlot(), i != -1 ? i : j};
               this.integerArrayField5 = k;
               if (i == -1) {
                  HandlerComponent108.getINVENTORY_1158().performAction_1179(j, minecraft.player.getInventory().getSelectedSlot(), 1);
               } else {
                  minecraft.player.getInventory().setSelectedSlot(i);
               }
            }
         }
      }
   }
}
