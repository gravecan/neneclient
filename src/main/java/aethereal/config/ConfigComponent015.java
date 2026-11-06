package aethereal.config;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;

public class ConfigComponent015 {
   private final RegistryEntry<EntityAttribute> attribute;
   private final Operation expectedOperation;
   private final double expectedAmount;

   public RegistryEntry<EntityAttribute> getAttribute_0339() {
      return this.attribute;
   }

   public Operation getExpectedOperation_0338() {
      return this.expectedOperation;
   }

   public double getExpectedAmount_0337() {
      return this.expectedAmount;
   }

   public ConfigComponent015(RegistryEntry<EntityAttribute> registryEntry, double d, Operation operation) {
      if (registryEntry == null) {
         throw new IllegalArgumentException("Attribute cannot be null");
      }

      this.attribute = registryEntry;
      this.expectedAmount = d;
      this.expectedOperation = operation;
   }

   public boolean checkCondition_0340(RegistryEntry<EntityAttribute> registryEntry, EntityAttributeModifier entityAttributeModifier) {
      return registryEntry.equals(this.attribute)
         && Math.abs(entityAttributeModifier.value() - this.expectedAmount) < 9.9999942618434E-4
         && entityAttributeModifier.operation() == this.expectedOperation;
   }

   public boolean checkCondition_0336(ItemStack itemStack) {
      AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)itemStack.getOrDefault(
         DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT
      );

      for (Entry entry : attributeModifiersComponent.modifiers()) {
         if (this.checkCondition_0340(entry.attribute(), entry.modifier())) {
            return true;
         }
      }

      return false;
   }
}
