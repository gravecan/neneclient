package aethereal.util;

import aethereal.autobuy.AutoBuyEntry;
import aethereal.core.MinecraftAccess;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntry.Reference;

public class UtilComponent270 implements MinecraftAccess {
   private UtilComponent270() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static ItemStack computeValue_3541(ItemStack itemStack) {
      EquippableComponent equippableComponent = (EquippableComponent)itemStack.get(DataComponentTypes.EQUIPPABLE);
      if (equippableComponent != null && equippableComponent.assetId().isPresent() && itemStack.getMaxDamage() > 0) {
         String string = ((RegistryKey)equippableComponent.assetId().get()).getValue().getPath();
         String string1 = string.substring(string.lastIndexOf(95) + 1);
         if (string1.matches("\\d+")) {
            ItemStack itemStack1 = itemStack.copy();
            itemStack1.setDamage(itemStack1.getMaxDamage() * (100 - Integer.parseInt(string1)) / 100);
            return itemStack1;
         }
      }

      return itemStack;
   }

   public static int computeValue_3538(Item item) {
      int i = 0;

      for (int j = 0; j < 36; j++) {
         ItemStack itemStack = minecraft.player.getInventory().getStack(j);
         if (itemStack.getItem() == item) {
            i += itemStack.getCount();
         }
      }

      return i;
   }

   public static boolean checkCondition_3544(ItemStack itemStack, RegistryKey<Enchantment> registryKey, int i) {
      return ((ItemEnchantmentsComponent)itemStack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT))
            .getLevel(minecraft.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(registryKey))
         >= i;
   }

   public static boolean checkCondition_3542(ItemStack itemStack, String string) {
      return itemStack.getTooltip(TooltipContext.DEFAULT, minecraft.player, TooltipType.BASIC).stream().anyMatch(text -> text.getString().contains(string));
   }

   public static int computeValue_3539(Item item, boolean bl) {
      int i = bl ? 9 : 36;

      for (int j = 0; j < i; j++) {
         ItemStack itemStack = minecraft.player.getInventory().getStack(j);
         if (!itemStack.isEmpty() && itemStack.getItem() == item) {
            return j;
         }
      }

      return -1;
   }

   public static int computeValue_3526(Item item) {
      return computeValue_3539(item, false);
   }

   public static int computeValue_3540(Item item, boolean bl, boolean bl1) {
      int i = bl ? 9 : 36;

      for (int j = 0; j < i; j++) {
         ItemStack itemStack = minecraft.player.getInventory().getStack(j);
         if (!itemStack.isEmpty() && itemStack.getItem() == item && (!bl1 || !itemStack.hasGlint())) {
            return j;
         }
      }

      return -1;
   }

   public static int computeValue_3545(ItemStack itemStack, boolean bl) {
      if (itemStack != null && !itemStack.isEmpty()) {
         int i = bl ? 9 : 36;

         for (int j = 0; j < i; j++) {
            ItemStack itemStack1 = minecraft.player.getInventory().getStack(j);
            if (!itemStack1.isEmpty()
               && itemStack1.getItem() == itemStack.getItem()
               && Objects.equals(itemStack1.get(DataComponentTypes.ATTRIBUTE_MODIFIERS), itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS))) {
               return j;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   public static int computeValue_3527(ItemStack itemStack, boolean bl) {
      AutoBuyEntry autoBuyEntry = Arrays.stream(AutoBuyEntry.values())
         .filter(autoBuyEntry1 -> autoBuyEntry1.checkCondition_0027(itemStack))
         .findFirst()
         .orElse(null);
      if (autoBuyEntry == null) {
         return -1;
      }

      int i = IntStream.range(0, bl ? 9 : 36).filter(j -> autoBuyEntry.checkCondition_0027(minecraft.player.getInventory().getStack(j))).findFirst().orElse(-1);
      return i == -1 && !bl
         ? IntStream.range(0, 36)
            .filter(
               j -> {
                  BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)minecraft.player
                     .getInventory()
                     .getStack(j)
                     .get(DataComponentTypes.BUNDLE_CONTENTS);
                  if (bundleContentsComponent != null) {
                     Stream stream = bundleContentsComponent.stream();
                     if (stream.anyMatch(autoBuyEntry::checkCondition_0027)) {
                        return true;
                     }
                  }

                  return false;
               }
            )
            .findFirst()
            .orElse(-1)
         : i;
   }

   public static int computeValue_3543(ItemStack itemStack, ItemStack itemStack1) {
      AutoBuyEntry autoBuyEntry = Arrays.stream(AutoBuyEntry.values())
         .filter(autoBuyEntry1 -> autoBuyEntry1.checkCondition_0027(itemStack1))
         .findFirst()
         .orElse(null);
      BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)itemStack.get(DataComponentTypes.BUNDLE_CONTENTS);
      return autoBuyEntry != null && bundleContentsComponent != null
         ? IntStream.range(0, bundleContentsComponent.size())
            .filter(i -> autoBuyEntry.checkCondition_0027(bundleContentsComponent.get(i)))
            .findFirst()
            .orElse(-1)
         : -1;
   }

   public static int computeValue_3532(ItemStack itemStack, boolean bl) {
      AutoBuyEntry autoBuyEntry = Arrays.stream(AutoBuyEntry.values())
         .filter(autoBuyEntry1 -> autoBuyEntry1.checkCondition_0027(itemStack))
         .findFirst()
         .orElse(null);
      return autoBuyEntry == null ? 0 : IntStream.range(0, bl ? 9 : 36).mapToObj(i -> minecraft.player.getInventory().getStack(i)).mapToInt(itemStack1 -> {
         BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)itemStack1.get(DataComponentTypes.BUNDLE_CONTENTS);
         if (bundleContentsComponent != null) {
            Stream stream = bundleContentsComponent.stream();
            return stream.filter(autoBuyEntry::checkCondition_0027).mapToInt(ItemStack::getCount).sum();
         } else {
            return autoBuyEntry.checkCondition_0027(itemStack1) ? itemStack1.getCount() : 0;
         }
      }).sum();
   }

   public static int computeValue_3530(Item item) {
      int i = -1;

      for (int j = 0; j < 45; j++) {
         if (j != 40 && minecraft.player.getInventory().getStack(j).getItem() == item) {
            AttributeModifiersComponent attributeModifiersComponent;
            if (minecraft.player.getInventory().getStack(j).contains(DataComponentTypes.ATTRIBUTE_MODIFIERS)
               && (
                     attributeModifiersComponent = (AttributeModifiersComponent)minecraft.player
                        .getInventory()
                        .getStack(j)
                        .get(DataComponentTypes.ATTRIBUTE_MODIFIERS)
                  )
                  != null
               && !attributeModifiersComponent.modifiers().isEmpty()) {
               return j;
            }

            if (i == -1) {
               i = j;
            }
         }
      }

      return i;
   }

   public static int computeValue_3533() {
      Registry registry = minecraft.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
      Reference reference = registry.getOrThrow(Enchantments.PROTECTION);
      int i = -1;
      double d = 0.0;

      for (int j = 0; j < 36; j++) {
         ItemStack itemStack = minecraft.player.getInventory().getStack(j);
         EquippableComponent equippableComponent = (EquippableComponent)itemStack.get(DataComponentTypes.EQUIPPABLE);
         if (equippableComponent != null && equippableComponent.slot() == EquipmentSlot.CHEST && !itemStack.isEmpty() && !itemStack.isOf(Items.ELYTRA)) {
            double e = EnchantmentHelper.getLevel(reference, itemStack);
            AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
            if (attributeModifiersComponent != null) {
               for (Entry entry : attributeModifiersComponent.modifiers()) {
                  RegistryEntry registryEntry = entry.attribute();
                  if (registryEntry == EntityAttributes.ARMOR || registryEntry == EntityAttributes.ARMOR_TOUGHNESS) {
                     e += entry.modifier().value();
                  }
               }
            }

            if (e > d) {
               d = e;
               i = j;
            }
         }
      }

      return i;
   }
}
