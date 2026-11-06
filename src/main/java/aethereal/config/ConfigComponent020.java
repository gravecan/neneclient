package aethereal.config;

import aethereal.autobuy.AutobuyContract011;
import aethereal.autobuy.ItemType;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public class ConfigComponent020 implements AutobuyContract011 {
   private final List<ConfigComponent019> listField1 = new ArrayList<>();

   public List<ConfigComponent019> getListField1_0370() {
      return this.listField1;
   }

   public void performAction_0372(ConfigComponent019 configComponent019) {
      this.listField1.add(configComponent019);
   }

   public ConfigComponent020 computeValue_0373(String string) {
      this.listField1.add(new ConfigComponent019(string));
      return this;
   }

   public ConfigComponent020 computeValue_0374(String string, int i) {
      this.listField1.add(new ConfigComponent019(string, i));
      return this;
   }

   public ConfigComponent020 computeValue_0375(String string, int i, boolean bl) {
      ConfigComponent019 configComponent019 = new ConfigComponent019(string, i);
      configComponent019.performAction_0347(bl);
      this.listField1.add(configComponent019);
      return this;
   }

   public ConfigComponent020 computeValue_0369(String string) {
      this.listField1.add(new ConfigComponent019(string, 0, ItemType.DENY));
      return this;
   }

   public ConfigComponent020 computeValue_0371() {
      return this.computeValue_0369("Jumper").computeValue_0369("Unstable");
   }

   @Override
   public boolean checkCondition_0078(ItemStack itemStack) {
      return this.listField1.stream().allMatch(configComponent019 -> configComponent019.checkCondition_0365(itemStack));
   }
}
