package aethereal.config;

import aethereal.autobuy.AutobuyContract011;
import aethereal.autobuy.ItemType;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;

public class ConfigComponent022 implements AutobuyContract011 {
   private final List<ConfigComponent021> listField1 = new ArrayList<>();

   public List<ConfigComponent021> getListField1_0382() {
      return this.listField1;
   }

   public ConfigComponent022 computeValue_0384(ConfigComponent021 configComponent021) {
      this.listField1.add(configComponent021);
      return this;
   }

   public ConfigComponent022 computeValue_0386(RegistryKey<Enchantment> registryKey) {
      return this.computeValue_0384(new ConfigComponent021(registryKey));
   }

   public ConfigComponent022 computeValue_0387(RegistryKey<Enchantment> registryKey, int i) {
      return this.computeValue_0384(new ConfigComponent021(registryKey, i));
   }

   public ConfigComponent022 computeValue_0380(RegistryKey<Enchantment> registryKey) {
      return this.computeValue_0384(new ConfigComponent021(registryKey, 0, ItemType.DENY));
   }

   public ConfigComponent022 computeValue_0381(RegistryKey<Enchantment> registryKey, int i) {
      return this.computeValue_0384(new ConfigComponent021(registryKey, i, ItemType.DENY));
   }

   public ConfigComponent022 computeValue_0383() {
      return this.computeValue_0380(Enchantments.THORNS)
         .computeValue_0380(Enchantments.KNOCKBACK)
         .computeValue_0380(Enchantments.BINDING_CURSE)
         .computeValue_0380(Enchantments.VANISHING_CURSE);
   }

   @Override
   public boolean checkCondition_0078(ItemStack itemStack) {
      return this.listField1.stream().allMatch(configComponent021 -> configComponent021.checkCondition_0377(itemStack));
   }
}
