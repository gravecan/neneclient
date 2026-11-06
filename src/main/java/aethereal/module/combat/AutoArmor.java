package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.handler.HandlerComponent108;
import aethereal.setting.BooleanSetting;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.BundleItemSelectedC2SPacket;
import net.minecraft.registry.RegistryKeys;

@ModuleRegister(name = "Auto Armor", description = "Automatically equips the best armor from inventory and bags", category = Category.Combat)
public class AutoArmor extends Module implements MinecraftAccess {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Not in motion", true);
   private final EquipmentSlot[] equipmentSlotArrayField2 = new EquipmentSlot[]{
      EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
   };
   private int integerField3;

   public AutoArmor() {
      this.addSettings(this.booleanSettingField1);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.integerField3 = 0;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.integerField3 = 0;
   }

   @EventTarget
   public void onEventComponent074_1564(EventComponent074 eventComponent074) {
      if (HandlerComponent108.getINVENTORY_1158().getListField1_1177().isEmpty()) {
         this.integerField3++;
         boolean bl = this.checkCondition_1565(minecraft.player.getEquippedStack(EquipmentSlot.HEAD))
            || this.checkCondition_1565(minecraft.player.getEquippedStack(EquipmentSlot.CHEST))
            || this.checkCondition_1565(minecraft.player.getEquippedStack(EquipmentSlot.LEGS))
            || this.checkCondition_1565(minecraft.player.getEquippedStack(EquipmentSlot.FEET));
         if (!bl) {
            if (this.integerField3 % 2 != 0) {
               return;
            }

            if (this.booleanSettingField1.getCurrentValue() && minecraft.player.getVelocity().horizontalLengthSquared() > 9.99999713651348E-5) {
               return;
            }
         }

         int i = 0;

         while (i < this.equipmentSlotArrayField2.length && !this.checkCondition_1566(this.equipmentSlotArrayField2[i], i)) {
            i++;
         }
      }
   }

   private boolean checkCondition_1566(EquipmentSlot equipmentSlot, int i) {
      ItemStack itemStack = minecraft.player.getEquippedStack(equipmentSlot);
      boolean bl = this.checkCondition_1565(itemStack);
      double d = bl ? this.computeValue_1567(itemStack) + 20 : this.computeValue_1563(itemStack);
      int j = -1;
      int k = -1;

      for (int l = 0; l < 36; l++) {
         ItemStack itemStack1 = minecraft.player.getInventory().getStack(l);
         if (this.checkCondition_1568(itemStack1, equipmentSlot)) {
            double e = bl ? this.computeValue_1567(itemStack1) : this.computeValue_1563(itemStack1);
            if (e > d) {
               d = e;
               j = l;
               k = -1;
            }
         }

         BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)itemStack1.get(DataComponentTypes.BUNDLE_CONTENTS);
         if (bundleContentsComponent != null) {
            for (int m = 0; m < bundleContentsComponent.size(); m++) {
               ItemStack itemStack2 = bundleContentsComponent.get(m);
               if (this.checkCondition_1568(itemStack2, equipmentSlot)) {
                  double f = bl ? this.computeValue_1567(itemStack2) : this.computeValue_1563(itemStack2);
                  if (f > d) {
                     d = f;
                     j = l;
                     k = m;
                  }
               }
            }
         }
      }

      if (j == -1) {
         return false;
      }

      if (k != -1) {
         minecraft.player.networkHandler.sendPacket(new BundleItemSelectedC2SPacket(j < 9 ? 36 + j : j, k));
      }

      HandlerComponent108.getINVENTORY_1158().performAction_1175(j, i, 1);
      return true;
   }

   private boolean checkCondition_1565(ItemStack itemStack) {
      return !itemStack.isEmpty() && itemStack.getMaxDamage() > 0 && this.computeValue_1567(itemStack) < 41;
   }

   private boolean checkCondition_1568(ItemStack itemStack, EquipmentSlot equipmentSlot) {
      EquippableComponent equippableComponent;
      return !itemStack.isEmpty()
         && (equippableComponent = (EquippableComponent)itemStack.get(DataComponentTypes.EQUIPPABLE)) != null
         && equippableComponent.slot() == equipmentSlot
         && (equipmentSlot != EquipmentSlot.CHEST || !itemStack.isOf(Items.ELYTRA));
   }

   private int computeValue_1567(ItemStack itemStack) {
      return itemStack.getMaxDamage() - itemStack.getDamage();
   }

   private double computeValue_1563(ItemStack itemStack) {
      if (itemStack.isEmpty()) {
         return 0.0;
      }

      double d = EnchantmentHelper.getLevel(
         minecraft.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.PROTECTION), itemStack
      );
      AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
      if (attributeModifiersComponent != null) {
         for (Entry entry : attributeModifiersComponent.modifiers()) {
            if (entry.attribute() == EntityAttributes.ARMOR || entry.attribute() == EntityAttributes.ARMOR_TOUGHNESS) {
               d += entry.modifier().value();
            }
         }
      }

      return d;
   }
}
