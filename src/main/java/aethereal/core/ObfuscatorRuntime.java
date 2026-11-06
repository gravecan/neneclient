package aethereal.core;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;

public class ObfuscatorRuntime {
   private ObfuscatorRuntime() {
   }

   public static void registerClass(Class class_, int i) {
   }

   public static byte[] getRuntimeKey() {
      return new byte[]{
         -123, 60, -1, -35, 82, -10, 47, -70, -112, -15, -61, 8, 12, 42, 1, 110, -31, 44, 47, -68, 87, -128, -39, 27, 35, 61, 37, -20, 122, -80, -43, -90
      };
   }

   public static Lookup lookupClass(String string) throws Exception {
      return MethodHandles.privateLookupIn(Class.forName(string.replace('/', '.')), MethodHandles.lookup());
   }
}
