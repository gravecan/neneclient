package aethereal.config;

import aethereal.autobuy.AutobuyContract011;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public class ConfigComponent024 implements AutobuyContract011 {
   private final List<ConfigComponent023> listField1 = new ArrayList<>();

   public List<ConfigComponent023> getListField1_0509() {
      return this.listField1;
   }

   public ConfigComponent024 computeValue_0510(ConfigComponent023 configComponent023) {
      this.listField1.add(configComponent023);
      return this;
   }

   public ConfigComponent024 computeValue_0511(String string) {
      return this.computeValue_0510(new ConfigComponent023(string));
   }

   @Override
   public boolean checkCondition_0078(ItemStack itemStack) {
      return this.listField1.stream().allMatch(configComponent023 -> configComponent023.checkCondition_0508(itemStack));
   }
}
