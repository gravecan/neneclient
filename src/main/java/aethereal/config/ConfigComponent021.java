package aethereal.config;

import aethereal.autobuy.ItemType;
import aethereal.core.MinecraftAccess;
import aethereal.render.Animation;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.text.Text;

public class ConfigComponent021 implements ConfigContract017 {
   private final Animation animationField1 = new Animation();
   private final RegistryKey<Enchantment> key;
   private ItemType type;
   private int requiredLevel;

   @Override
   public Animation computeValue_0351() {
      return this.animationField1;
   }

   public RegistryKey<Enchantment> getKey_0378() {
      return this.key;
   }

   @Override
   public void performAction_0353(ItemType itemType) {
      this.type = itemType;
   }

   @Override
   public ItemType computeValue_0350() {
      return this.type;
   }

   @Override
   public void performAction_0345(int i) {
      this.requiredLevel = i;
   }

   @Override
   public int computeValue_0354() {
      return this.requiredLevel;
   }

   public ConfigComponent021(RegistryKey<Enchantment> registryKey) {
      this(registryKey, 0, ItemType.itemTypeField1);
   }

   public ConfigComponent021(RegistryKey<Enchantment> registryKey, int i) {
      this(registryKey, i, ItemType.itemTypeField1);
   }

   public ConfigComponent021(RegistryKey<Enchantment> registryKey, int i, ItemType itemType) {
      this.key = registryKey;
      this.type = itemType;
      this.requiredLevel = itemType == ItemType.itemTypeField1 ? this.computeValue_0379(i) : i;
   }

   @Override
   public String computeValue_0346() {
      return this.key.getValue().toString();
   }

   @Override
   public boolean checkCondition_0348() {
      return this.type != ItemType.OFF;
   }

   @Override
   public void performAction_0347(boolean bl) {
      this.type = bl ? ItemType.itemTypeField1 : ItemType.OFF;
   }

   @Override
   public boolean checkCondition_0349() {
      return this.requiredLevel > 0;
   }

   @Override
   public boolean checkCondition_0344() {
      return this.type == ItemType.DENY;
   }

   @Override
   public void performAction_0352(int i) {
      if (this.type == ItemType.itemTypeField1 && this.requiredLevel != 0) {
         this.requiredLevel = Math.max(1, this.computeValue_0379(this.requiredLevel + i));
      }
   }

   public boolean checkCondition_0377(ItemStack itemStack) {
      if (this.type == ItemType.OFF) {
         return true;
      }

      Reference reference = MinecraftAccess.minecraft.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(this.key);
      ItemEnchantmentsComponent itemEnchantmentsComponent = (ItemEnchantmentsComponent)itemStack.getOrDefault(
         DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT
      );
      int i = itemEnchantmentsComponent.getLevel(reference);
      int j = this.requiredLevel == 0 ? 1 : this.requiredLevel;
      return this.type == ItemType.DENY ? i < j : !itemEnchantmentsComponent.isEmpty() && i >= j;
   }

   @Override
   public String computeValue_0343() {
      return Text.translatable("enchantment." + this.key.getValue().getNamespace() + "." + this.key.getValue().getPath().replace("/", ".")).getString()
         + (this.requiredLevel == 0 ? "" : " " + this.requiredLevel);
   }

   private int computeValue_0379(int i) {
      if (i <= 0) {
         return 0;
      }

      if (MinecraftAccess.minecraft.world != null
         && MinecraftAccess.minecraft.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).contains(this.key)) {
         int j;
         if (this.key.equals(Enchantments.SHARPNESS)) {
            j = 7;
         } else {
            j = !this.key.equals(Enchantments.PROTECTION) && !this.key.equals(Enchantments.UNBREAKING)
               ? ((Enchantment)MinecraftAccess.minecraft.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(this.key).comp_349())
                  .getMaxLevel()
               : 5;
         }

         return Math.max(1, Math.min(j, i));
      } else {
         return i;
      }
   }
}
