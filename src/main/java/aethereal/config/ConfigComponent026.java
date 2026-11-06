package aethereal.config;

import aethereal.autobuy.AutobuyContract011;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public class ConfigComponent026 implements AutobuyContract011 {
   private final List<ConfigComponent025> listField1 = new ArrayList<>();

   public List<ConfigComponent025> getListField1_0519() {
      return this.listField1;
   }

   public ConfigComponent026 computeValue_0520(ConfigComponent025 configComponent025) {
      this.listField1.add(configComponent025);
      return this;
   }

   @Override
   public boolean checkCondition_0078(ItemStack itemStack) {
      return this.listField1.stream().allMatch(configComponent025 -> configComponent025.checkCondition_0514(itemStack));
   }
}
