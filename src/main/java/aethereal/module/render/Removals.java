package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.RemovalsEvent;
import aethereal.setting.BooleanSetting;
import aethereal.setting.MultiSelectSetting;

@ModuleRegister(name = "Removals", description = "Removes selected visuals and game elements", category = Category.Render)
public class Removals extends Module {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Disabled items",
      new BooleanSetting("Shake on Damage", true),
      new BooleanSetting("Scorboard", false),
      new BooleanSetting("Bossbar", false),
      new BooleanSetting("Portal effect", true),
      new BooleanSetting("Fire", true),
      new BooleanSetting("Camera crop", true),
      new BooleanSetting("Particles of Destruction", false),
      new BooleanSetting("Black Hearts", true),
      new BooleanSetting("Weather Particles", false),
      new BooleanSetting("Water/lava immersion", false),
      new BooleanSetting("Nausea", true),
      new BooleanSetting("Blindness", true),
      new BooleanSetting("Pumpkin", true),
      new BooleanSetting("Glow", true),
      new BooleanSetting("Dark", true)
   );

   public MultiSelectSetting getMultiSelectSettingField1_2294() {
      return this.multiSelectSettingField1;
   }

   public Removals() {
      this.addSettings(this.multiSelectSettingField1);
   }

   @EventTarget
   public void onRemovalsEvent_2293(RemovalsEvent removalsEvent) {
      switch (Removals.NestedType358.integerArrayField1[removalsEvent.getType_1020().ordinal()]) {
         case 1:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Shake on Damage").getCurrentValue());
            break;
         case 2:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Scorboard").getCurrentValue());
            break;
         case 3:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Bossbar").getCurrentValue());
            break;
         case 4:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Portal effect").getCurrentValue());
            break;
         case 5:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Fire").getCurrentValue());
            break;
         case 6:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Camera crop").getCurrentValue());
            break;
         case 7:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Particles of Destruction").getCurrentValue());
            break;
         case 8:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Water/lava immersion").getCurrentValue());
            break;
         case 9:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Nausea").getCurrentValue());
            break;
         case 10:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Blindness").getCurrentValue());
            break;
         case 11:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Pumpkin").getCurrentValue());
            break;
         case 12:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Weather Particles").getCurrentValue());
            break;
         case 13:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Glow").getCurrentValue());
            break;
         case 14:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Dark").getCurrentValue());
            break;
         case 15:
            removalsEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Black Hearts").getCurrentValue());
      }
   }

   static class NestedType358 {
      static final int[] integerArrayField1 = new int[RemovalsEvent.State319.values().length];

      static {
         try {
            integerArrayField1[RemovalsEvent.State319.HURT_CAM.ordinal()] = 1;
         } catch (NoSuchFieldError noSuchFieldError14) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.SCOREBOARD.ordinal()] = 2;
         } catch (NoSuchFieldError noSuchFieldError13) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.BOSS_BAR.ordinal()] = 3;
         } catch (NoSuchFieldError noSuchFieldError12) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.PORTAL.ordinal()] = 4;
         } catch (NoSuchFieldError noSuchFieldError11) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.FIRE.ordinal()] = 5;
         } catch (NoSuchFieldError noSuchFieldError10) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.CLIP.ordinal()] = 6;
         } catch (NoSuchFieldError noSuchFieldError9) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.BREAK_PARTICLES.ordinal()] = 7;
         } catch (NoSuchFieldError noSuchFieldError8) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.WATER.ordinal()] = 8;
         } catch (NoSuchFieldError noSuchFieldError7) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.NAUSEA.ordinal()] = 9;
         } catch (NoSuchFieldError noSuchFieldError6) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.BLINDNESS.ordinal()] = 10;
         } catch (NoSuchFieldError noSuchFieldError5) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.PUMPKIN.ordinal()] = 11;
         } catch (NoSuchFieldError noSuchFieldError4) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.WEATHER.ordinal()] = 12;
         } catch (NoSuchFieldError noSuchFieldError3) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.GLOW.ordinal()] = 13;
         } catch (NoSuchFieldError noSuchFieldError2) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.DARKNESS.ordinal()] = 14;
         } catch (NoSuchFieldError noSuchFieldError1) {
         }

         try {
            integerArrayField1[RemovalsEvent.State319.BLACK_HEARTS.ordinal()] = 15;
         } catch (NoSuchFieldError noSuchFieldError) {
         }
      }
   }
}
