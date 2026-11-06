package aethereal.config;

import aethereal.autobuy.AutobuyContract011;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public class ConfigComponent016 implements AutobuyContract011 {
   private final List<ConfigComponent015> listField1 = new ArrayList<>();

   public ConfigComponent016 computeValue_0341(ConfigComponent015 configComponent015) {
      this.listField1.add(configComponent015);
      return this;
   }

   @Override
   public boolean checkCondition_0078(ItemStack itemStack) {
      return this.listField1.stream().allMatch(configComponent015 -> configComponent015.checkCondition_0336(itemStack));
   }
}
