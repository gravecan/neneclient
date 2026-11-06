package aethereal.setting;

import aethereal.config.SettingsPersistence;
import aethereal.ui.element.SettingElement;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Setting<Value> {
   private final Value defaultValue;
   private Value value;
   private final String name;
   private Supplier<Boolean> visibility = () -> true;
   private Consumer<Value> onChange = object1 -> {};
   private boolean persistChanges = true;

   public abstract SettingElement<?> createElement();

   public Supplier<Boolean> getVisibility() {
      return this.visibility;
   }

   public Consumer<Value> getOnChange() {
      return this.onChange;
   }

   public Value getDefaultValue() {
      return this.defaultValue;
   }

   public Value getValue() {
      return this.value;
   }

   public String getName() {
      return this.name;
   }

   public boolean shouldPersistChanges() {
      return this.persistChanges;
   }

   public Setting(String string, Value object) {
      this.name = string;
      this.defaultValue = (Value)object;
      this.value = (Value)object;
   }

   public <T extends Setting<Value>> T withoutPersistence() {
      this.persistChanges = false;
      return (T)this;
   }

   public Setting<Value> setValue(Value object) {
      if (Objects.equals(this.value, object)) {
         return this;
      }

      this.value = (Value)object;
      if (this.persistChanges) {
         SettingsPersistence.performAction_0527();
      }

      this.onChange.accept((Value)object);
      return this;
   }

   public <T extends Setting<Value>> T withOnChange(Consumer<Value> consumer) {
      this.onChange = consumer;
      return (T)this;
   }

   public void reset() {
      this.setValue(this.defaultValue);
   }

   public <T extends Setting<Value>> T withVisibility(Supplier<Boolean> supplier) {
      this.visibility = supplier;
      return (T)this;
   }

   public Value getCurrentValue() {
      return this.value;
   }
}
