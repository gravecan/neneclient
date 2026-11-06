package aethereal.util;

import aethereal.core.CoreContract035;
import aethereal.lib.javassist.JavassistComponent125;

public class UtilComponent257 implements CoreContract035 {
   public static int computeValue_3462(int i, JavassistComponent125 javassistComponent125) {
      int j = javassistComponent125.computeValue_1238(i);
      return i + (j != 201 && j != 200 ? javassistComponent125.computeValue_1236(i + 1) : javassistComponent125.computeValue_1237(i + 1));
   }

   public static boolean checkCondition_3463(int i) {
      return i >= 153 && i <= 168 || i == 198 || i == 199 || i == 201 || i == 200;
   }

   public static boolean checkCondition_3460(int i) {
      return i == 167 || i == 200;
   }

   public static boolean checkCondition_3459(int i) {
      return i == 168 || i == 201;
   }

   public static boolean checkCondition_3461(int i) {
      return i >= 172 && i <= 177;
   }
}
