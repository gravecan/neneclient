package aethereal.handler;

import aethereal.module.combat.CombatComponent164;

public final class HandlerComponent108 {
   private static final HandlerComponent110 INVENTORY = new HandlerComponent110();
   private static final HandlerComponent117 USEABLE = new HandlerComponent117();
   private static final RotationProcessor ROTATION = new RotationProcessor();
   private static final CombatComponent164 AURA = new CombatComponent164();
   private static final HandlerComponent115 STOP = new HandlerComponent115();

   public static HandlerComponent110 getINVENTORY_1158() {
      return INVENTORY;
   }

   public static HandlerComponent117 getUSEABLE_1160() {
      return USEABLE;
   }

   public static RotationProcessor getROTATION_1157() {
      return ROTATION;
   }

   public static CombatComponent164 getAURA_1161() {
      return AURA;
   }

   public static HandlerComponent115 getSTOP_1159() {
      return STOP;
   }

   private HandlerComponent108() {
   }
}
