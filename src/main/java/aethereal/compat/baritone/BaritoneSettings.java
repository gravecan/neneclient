package aethereal.compat.baritone;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;

public final class BaritoneSettings {
   public final BaritoneSettings.SettingValue<Boolean> settingValueField1 = new BaritoneSettings.SettingValue<>(true);
   public final BaritoneSettings.SettingValue<Boolean> settingValueField2 = new BaritoneSettings.SettingValue<>(true);
   public final BaritoneSettings.SettingValue<Boolean> settingValueField3 = new BaritoneSettings.SettingValue<>(false);
   public final BaritoneSettings.SettingValue<Boolean> settingValueField4 = new BaritoneSettings.SettingValue<>(false);
   public final BaritoneSettings.SettingValue<Boolean> settingValueField5 = new BaritoneSettings.SettingValue<>(false);
   public final BaritoneSettings.SettingValue<Float> settingValueField6 = new BaritoneSettings.SettingValue<>(60.0F);
   public final BaritoneSettings.SettingValue<Double> settingValueField7 = new BaritoneSettings.SettingValue<>(1.0);
   public final BaritoneSettings.SettingValue<Double> settingValueField8 = new BaritoneSettings.SettingValue<>(1.0);
   public final BaritoneSettings.SettingValue<Integer> settingValueField9 = new BaritoneSettings.SettingValue<>(3);
   public final BaritoneSettings.SettingValue<List<Block>> settingValueField10 = new BaritoneSettings.SettingValue<>(new ArrayList<>());

   public static final class SettingValue<T> {
      public T value;

      public SettingValue(T object) {
         this.value = (T)object;
      }
   }
}
