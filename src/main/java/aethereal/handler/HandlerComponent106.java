package aethereal.handler;

import aethereal.api.ApiAnnotation001;
import aethereal.config.PersistentProcessor;
import aethereal.core.ObfuscatorRuntime;
import aethereal.lib.jsoup.JsoupComponent142;
import aethereal.lib.log4j.Log4jComponent146;
import aethereal.lib.log4j.Log4jComponent147;
import aethereal.module.combat.CombatComponent163;
import aethereal.module.combat.CombatComponent164;
import aethereal.module.misc.MiscComponent167;
import aethereal.network.NetworkComponent172;

public class HandlerComponent106 extends PersistentProcessor {
   private static final Log4jComponent147 log4jComponent147Field1 = Log4jComponent146.computeValue_1319(HandlerComponent106.class);
   private final CombatComponent163 combatComponent163Field2 = new CombatComponent163();
   private final ANFindHandler aNFindHandlerField3 = new ANFindHandler();
   private final MiscComponent167 miscComponent167Field4 = new MiscComponent167();
   private final HandlerComponent111 handlerComponent111Field5 = new HandlerComponent111();
   private final HandlerComponent114 handlerComponent114Field6 = new HandlerComponent114();
   private final HandlerComponent116 handlerComponent116Field7 = new HandlerComponent116();
   private final HandlerComponent109 handlerComponent109Field8 = new HandlerComponent109();
   private final JsoupComponent142 jsoupComponent142Field9 = new JsoupComponent142();
   private final NetworkComponent172 networkComponent172Field10 = new NetworkComponent172();

   @ApiAnnotation001
   @Override
   public void load() {
   }

   public HandlerComponent110 computeValue_1146() {
      return HandlerComponent108.getINVENTORY_1158();
   }

   public HandlerComponent117 computeValue_1151() {
      return HandlerComponent108.getUSEABLE_1160();
   }

   public HandlerComponent115 computeValue_1149() {
      return HandlerComponent108.getSTOP_1159();
   }

   public CombatComponent164 computeValue_1154() {
      return HandlerComponent108.getAURA_1161();
   }

   public CombatComponent163 getCombatComponent163Field2_1153() {
      return this.combatComponent163Field2;
   }

   public ANFindHandler getANFindHandlerField3_1144() {
      return this.aNFindHandlerField3;
   }

   public MiscComponent167 getMiscComponent167Field4_1155() {
      return this.miscComponent167Field4;
   }

   public HandlerComponent111 getHandlerComponent111Field5_1147() {
      return this.handlerComponent111Field5;
   }

   public HandlerComponent114 getHandlerComponent114Field6_1148() {
      return this.handlerComponent114Field6;
   }

   public HandlerComponent116 getHandlerComponent116Field7_1150() {
      return this.handlerComponent116Field7;
   }

   public HandlerComponent109 getHandlerComponent109Field8_1145() {
      return this.handlerComponent109Field8;
   }

   public JsoupComponent142 getJsoupComponent142Field9_1152() {
      return this.jsoupComponent142Field9;
   }

   public NetworkComponent172 getNetworkComponent172Field10_1156() {
      return this.networkComponent172Field10;
   }

   @Override
   public void save() {
   }

   static {
      ObfuscatorRuntime.registerClass(HandlerComponent106.class, 31);
   }
}
