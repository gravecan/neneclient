package aethereal.config;

import net.minecraft.item.ItemStack;

public class ConfigComponent023 {
   private final String value;

   public String getValue_0507() {
      return this.value;
   }

   public ConfigComponent023(String string) {
      this.value = string;
   }

   public boolean checkCondition_0508(ItemStack itemStack) {
      String string = itemStack.getComponents().toString();
      return string.replaceAll("§.", "")
         .toLowerCase()
         .replaceAll("\\s+", "")
         .trim()
         .contains(this.value.replaceAll("§.", "").toLowerCase().replaceAll("\\s+", "").trim());
   }
}
