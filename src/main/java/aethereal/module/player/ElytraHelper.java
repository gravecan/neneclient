package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent091;
import aethereal.event.PacketEvent;
import aethereal.setting.BooleanSetting;
import aethereal.setting.KeybindSetting;
import aethereal.util.UtilComponent270;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;

@ModuleRegister(name = "Elytra Helper", description = "Performs actions with elytra by pressing the assigned key", category = Category.Player)
public class ElytraHelper extends Module implements MinecraftAccess {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Autostart after swap", false);
   private final BooleanSetting booleanSettingField2;
   private final BooleanSetting booleanSettingField3;
   private final KeybindSetting keybindSettingField4;
   private final KeybindSetting keybindSettingField5;
   private boolean flagField6;
   private boolean flagField7;
   private int integerField8;

   public BooleanSetting getBooleanSettingField1_2182() {
      return this.booleanSettingField1;
   }

   public BooleanSetting getBooleanSettingField2_2175() {
      return this.booleanSettingField2;
   }

   public BooleanSetting getBooleanSettingField3_2178() {
      return this.booleanSettingField3;
   }

   public KeybindSetting getKeybindSettingField4_2184() {
      return this.keybindSettingField4;
   }

   public KeybindSetting getKeybindSettingField5_2176() {
      return this.keybindSettingField5;
   }

   public boolean isFlagField6_2187() {
      return this.flagField6;
   }

   public boolean isFlagField7_2185() {
      return this.flagField7;
   }

   public int getIntegerField8_2181() {
      return this.integerField8;
   }

   public ElytraHelper() {
      BooleanSetting booleanSetting = new BooleanSetting("Use /fly when swapping", false);
      BooleanSetting booleanSetting1 = this.booleanSettingField1;
      this.booleanSettingField2 = booleanSetting.withVisibility(booleanSetting1::getCurrentValue);
      BooleanSetting booleanSetting2 = new BooleanSetting("Auto fireworks", false);
      BooleanSetting booleanSetting3 = this.booleanSettingField1;
      this.booleanSettingField3 = booleanSetting2.withVisibility(booleanSetting3::getCurrentValue);
      this.keybindSettingField4 = new KeybindSetting("Fireworks button", -1).computeValue_2847(() -> this.performAction_2190());
      this.keybindSettingField5 = new KeybindSetting("Switch button", -1)
         .computeValue_2847(
            () -> {
               int i = minecraft.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA
                  ? UtilComponent270.computeValue_3533()
                  : UtilComponent270.computeValue_3526(Items.ELYTRA);
               NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().computeValue_1146().performAction_1175(i, 1, 1);
               if (this.booleanSettingField1.getCurrentValue() && UtilComponent270.computeValue_3526(Items.ELYTRA) == i) {
                  this.integerField8 = minecraft.player.age;
                  this.flagField6 = true;
               }
            }
         );
      this.addSettings(this.keybindSettingField5, this.keybindSettingField4, this.booleanSettingField1, this.booleanSettingField2, this.booleanSettingField3);
   }

   @EventTarget
   public void onEventComponent091_2180(EventComponent091 eventComponent091) {
      if (minecraft.player.age < 5) {
         this.flagField6 = false;
      } else if (this.flagField6 && this.checkCondition_2189()) {
         this.onEventComponent091_2183(eventComponent091);
      }
   }

   private boolean checkCondition_2189() {
      if (this.booleanSettingField2.getCurrentValue() && !this.flagField7) {
         if (this.integerField8 + 1 == minecraft.player.age) {
            minecraft.player.networkHandler.sendChatCommand("fly");
         }

         if (minecraft.player.getAbilities().allowFlying && this.integerField8 <= minecraft.player.age && minecraft.player.isOnGround()) {
            double d = minecraft.player.getX();
            double e = minecraft.player.getY() + 0.19999997F;
            double f = minecraft.player.getZ();
            minecraft.player.networkHandler.sendPacket(new PositionAndOnGround(d, e, f, false, minecraft.player.horizontalCollision));
            minecraft.player.setPosition(d, e, f);
            this.integerField8 = minecraft.player.age + 9;
         }

         if (minecraft.player.getAbilities().allowFlying && !minecraft.player.isOnGround()) {
            minecraft.player.getAbilities().flying = true;
            minecraft.player.setVelocity(0.0, 0.0, 0.0);
            minecraft.player.sendAbilitiesUpdate();
            this.flagField6 = false;
         }

         if (!this.checkCondition_2177(15)
            && (!this.checkCondition_2177(3) || minecraft.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA)) {
            return false;
         }

         this.flagField6 = false;
         return false;
      } else {
         return true;
      }
   }

   private void onEventComponent091_2183(EventComponent091 eventComponent091) {
      boolean bl = minecraft.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA;
      if (!minecraft.player.isGliding() && bl) {
         eventComponent091.setJump_1066(minecraft.player.age % 2 == 0);
         this.integerField8 = minecraft.player.age;
         if (!this.booleanSettingField3.getCurrentValue()) {
            this.flagField6 = false;
         }
      }

      if (minecraft.player.isGliding()
         && this.booleanSettingField3.getCurrentValue()
         && !minecraft.player.isTouchingWater()
         && !minecraft.player.isOnGround()
         && this.checkCondition_2177(2)) {
         this.performAction_2190();
         this.flagField6 = false;
      }

      if (this.checkCondition_2177(10)) {
         this.flagField6 = false;
      }
   }

   private void performAction_2190() {
      if (minecraft.player.isGliding()) {
         NeneClient.getInstance()
            .getProcessors()
            .getHandlerComponent106Field14_0673()
            .computeValue_1151()
            .performAction_1225(Items.FIREWORK_ROCKET.getDefaultStack());
      }
   }

   private boolean checkCondition_2177(int i) {
      return this.integerField8 + i < minecraft.player.age;
   }

   @EventTarget
   public void onPacketEvent_2179(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013()) {
         GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packetEvent.getPacket_1015();
         if (gameMessageS2CPacket instanceof GameMessageS2CPacket gameMessageS2CPacket1
            && this.flagField6
            && gameMessageS2CPacket1.content().getString().contains("This command can only be written by donors of higher rank")) {
            this.flagField7 = true;
         }
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.flagField6 = false;
   }
}
