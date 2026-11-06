package aethereal.core;

import aethereal.lib.javassist.JavassistComponent122;
import aethereal.lib.javassist.JavassistComponent127;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class CoreComponent031 {
   public static Map<String, CoreComponent031> mapField1 = new HashMap<>();
   private final String stringField2;

   public abstract void performAction_0595(Object[] objects);

   public CoreComponent031(String string) {
      String string1 = UUID.randomUUID().toString();
      mapField1.put(string1, this);
      this.stringField2 = "((javassist.tools.Callback) javassist.tools.Callback.callbacks.get(\"" + string1 + "\")).result(new Object[]{" + string + "});";
   }

   @Override
   public String toString() {
      return this.getStringField2_0591();
   }

   public String getStringField2_0591() {
      return this.stringField2;
   }

   public static void performAction_0592(JavassistComponent127 javassistComponent127, CoreComponent031 coreComponent031) throws JavassistComponent122 {
      javassistComponent127.performAction_1241(coreComponent031.toString());
   }

   public static void performAction_0590(JavassistComponent127 javassistComponent127, CoreComponent031 coreComponent031) throws JavassistComponent122 {
      javassistComponent127.performAction_1240(coreComponent031.toString(), false);
   }

   public static void performAction_0594(JavassistComponent127 javassistComponent127, CoreComponent031 coreComponent031, boolean bl) throws JavassistComponent122 {
      javassistComponent127.performAction_1240(coreComponent031.toString(), bl);
   }

   public static int computeValue_0593(JavassistComponent127 javassistComponent127, CoreComponent031 coreComponent031, int i) throws JavassistComponent122 {
      return javassistComponent127.computeValue_1239(i, coreComponent031.toString());
   }
}
