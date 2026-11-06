package aethereal.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent074;
import aethereal.setting.BooleanSetting;
import aethereal.setting.FloatSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.util.UtilComponent279;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.Perspective;

@ModuleRegister(name = "Animations", description = "Animates selected game elements", category = Category.Render)
public class Animations extends Module {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Choose what to animate",
      new BooleanSetting("TAB", true),
      new BooleanSetting("Opening inventory", true),
      new BooleanSetting("Change of perspective", true),
      new BooleanSetting("Raising hotbar", true),
      new BooleanSetting("Hotbar slot", true),
      new BooleanSetting("Appearing of messages", true),
      new BooleanSetting("Items", true)
   );
   private final FloatSetting floatSettingField2 = new FloatSetting("Lifting height", 14.0F, 0.0F, 30.0F, 1.0F)
      .withVisibility(() -> this.multiSelectSettingField1.computeValue_2852("Raising hotbar").getCurrentValue());
   private final BooleanSetting booleanSettingField3 = new BooleanSetting("Ignore blocks", true)
      .withVisibility(() -> this.multiSelectSettingField1.computeValue_2852("Change of perspective").getCurrentValue());
   private final Animation animationField4 = new Animation();
   private final Animation animationField5 = new Animation();
   private final Animation animationField6 = new Animation();
   private final Animation animationField7 = new Animation();
   private float floatValueField8 = -1.0F;

   public MultiSelectSetting getMultiSelectSettingField1_2544() {
      return this.multiSelectSettingField1;
   }

   public FloatSetting getFloatSettingField2_2545() {
      return this.floatSettingField2;
   }

   public BooleanSetting getBooleanSettingField3_2547() {
      return this.booleanSettingField3;
   }

   public Animation getAnimationField4_2542() {
      return this.animationField4;
   }

   public Animation getAnimationField5_2546() {
      return this.animationField5;
   }

   public Animation getAnimationField6_2543() {
      return this.animationField6;
   }

   public Animation getAnimationField7_2548() {
      return this.animationField7;
   }

   public float getFloatValueField8_2538() {
      return this.floatValueField8;
   }

   public Animations() {
      this.addSettings(this.multiSelectSettingField1, this.floatSettingField2, this.booleanSettingField3);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.floatValueField8 = -1.0F;
   }

   @EventTarget
   public void onDrawEvent_2540(DrawEvent drawEvent) {
      this.animationField4.performAction_2535(0.0F, 1.0F, 0.5F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      this.animationField5.performAction_2535(0.0F, 1.0F, 0.45F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      this.animationField6.performAction_2535(0.0F, 1.0F, 0.4F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      this.animationField7.performAction_2535(0.0F, 1.0F, 0.35F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      this.floatValueField8 = this.floatValueField8 < 0.0F
         ? minecraft.player.getInventory().getSelectedSlot()
         : UtilComponent279.computeValue_3586(this.floatValueField8, minecraft.player.getInventory().getSelectedSlot(), 1.25F);
   }

   @EventTarget
   public void onEventComponent074_2541(EventComponent074 eventComponent074) {
      this.animationField6.performAction_2528(minecraft.currentScreen instanceof InventoryScreen);
      this.animationField7.performAction_2528(minecraft.options.getPerspective() != Perspective.FIRST_PERSON);
      this.animationField5.performAction_2528(minecraft.currentScreen instanceof ChatScreen);
   }
}
