package aethereal.util;

import aethereal.lib.log4j.Log4jComponent144;
import aethereal.lib.log4j.Log4jComponent150;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

public class UtilComponent261 {
   private static final Log4jComponent144 log4jComponent144Field1 = Log4jComponent150.getLog4jComponent144Field1_1323();
   private static Method methodField2 = null;
   private static Object objectField3 = null;

   private UtilComponent261() {
   }

   @Deprecated
   public static String computeValue_3501(String string) {
      if (string == null) {
         return null;
      }

      byte[] b = string.getBytes(Charset.defaultCharset());
      if (methodField2 != null) {
         try {
            return (String)methodField2.invoke(objectField3, b);
         } catch (Exception exception) {
            throw new UtilComponent276("Unable to encode String", exception);
         }
      } else {
         throw new UtilComponent276("No Encoder, unable to encode string");
      }
   }

   static {
      try {
         Class class_ = UtilComponent275.computeValue_3564("java.util.Base64");
         Class class_x = UtilComponent275.computeValue_3564("java.util.Base64.Encoder");
         Method method = class_.getMethod("getEncoder");
         objectField3 = method.invoke(null);
         methodField2 = class_x.getMethod("encodeToString", byte[].class);
      } catch (Exception exception1) {
         try {
            Class class_xx = UtilComponent275.computeValue_3564("javax.xml.bind.DataTypeConverter");
            methodField2 = class_xx.getMethod("printBase64Binary");
         } catch (Exception exception) {
            log4jComponent144Field1.performAction_1315("Unable to create a Base64 Encoder", exception);
         }
      }
   }
}
