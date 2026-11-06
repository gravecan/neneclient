package aethereal.config;

import aethereal.autobuy.ItemType;
import aethereal.core.MinecraftAccess;
import aethereal.render.Animation;
import aethereal.util.UtilComponent279;
import java.util.stream.Collectors;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;

public class ConfigComponent019 implements ConfigContract017 {
   private final Animation animationField1 = new Animation();
   private final String description;
   private ItemType type;
   private int integerField2;

   @Override
   public Animation computeValue_0351() {
      return this.animationField1;
   }

   public String getDescription_0367() {
      return this.description;
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
      this.integerField2 = i;
   }

   @Override
   public int computeValue_0354() {
      return this.integerField2;
   }

   public ConfigComponent019(String string) {
      this(string, 0, ItemType.itemTypeField1);
   }

   public ConfigComponent019(String string, int i) {
      this(string, i, ItemType.itemTypeField1);
   }

   public ConfigComponent019(String string, int i, ItemType itemType) {
      this.description = string;
      this.type = itemType;
      this.integerField2 = itemType == ItemType.itemTypeField1 && i > 0 ? Math.min(this.computeValue_0366(), i) : i;
   }

   @Override
   public String computeValue_0346() {
      return this.description;
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
      return this.integerField2 > 0;
   }

   @Override
   public boolean checkCondition_0344() {
      return this.type == ItemType.DENY;
   }

   @Override
   public void performAction_0352(int i) {
      if (this.type == ItemType.itemTypeField1 && this.integerField2 != 0) {
         this.integerField2 = Math.max(1, Math.min(this.computeValue_0366(), this.integerField2 + i));
      }
   }

   public boolean checkCondition_0365(ItemStack itemStack) {
      if (this.type == ItemType.OFF) {
         return true;
      }

      String string = itemStack.getTooltip(TooltipContext.DEFAULT, MinecraftAccess.minecraft.player, TooltipType.BASIC)
         .stream()
         .skip(1L)
         .map(text -> text.getString().replaceAll("§.", "").toLowerCase().replaceAll("\\s+", "").trim())
         .collect(Collectors.joining(""));
      String string1 = this.description.replaceAll("§.", "").toLowerCase().replaceAll("\\s+", "").trim();
      if (this.type == ItemType.DENY) {
         return !string.contains(string1);
      }

      if (!string.contains(string1)) {
         return false;
      }

      if (this.integerField2 == 0) {
         return true;
      }

      String string2 = string.substring(string.indexOf(string1) + string1.length()).trim();
      int j = string2.indexOf(32);
      int i;
      if (string2.isEmpty()) {
         i = 0;
      } else {
         i = UtilComponent279.computeValue_3578(j > 0 ? string2.substring(0, j) : string2);
      }

      int k = i;
      return Math.max(1, k) >= this.integerField2;
   }

   @Override
   public String computeValue_0343() {
      return this.integerField2 == 0 ? this.description : this.description + " " + UtilComponent279.computeValue_3584(this.integerField2 - 1);
   }

   private int computeValue_0366() {
      return !"Oxidation".equals(this.description) && !"Vampirism".equals(this.description) ? 3 : 2;
   }
}
