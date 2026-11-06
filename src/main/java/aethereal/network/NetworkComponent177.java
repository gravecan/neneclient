package aethereal.network;

import aethereal.lib.websocket.WebsocketContract154;
import java.util.regex.Pattern;

public class NetworkComponent177 implements WebsocketContract154 {
   private static final Pattern patternField1 = Pattern.compile("");
   private static final Pattern patternField2 = Pattern.compile(",");
   private final String providedProtocol;

   public NetworkComponent177(String string) {
      if (string == null) {
         throw new IllegalArgumentException();
      }

      this.providedProtocol = string;
   }

   public boolean checkCondition_2486(String string) {
      if ("".equals(this.providedProtocol)) {
         return true;
      }

      String string1 = patternField1.matcher(string).replaceAll("");
      String[] strings = patternField2.split(string1);

      for (String string2 : strings) {
         if (this.providedProtocol.equals(string2)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public String computeValue_1325() {
      return this.providedProtocol;
   }

   @Override
   public WebsocketContract154 computeValue_1326() {
      return new NetworkComponent177(this.computeValue_1325());
   }

   @Override
   public String toString() {
      return this.computeValue_1325();
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         NetworkComponent177 networkComponent1771 = (NetworkComponent177)object;
         return this.providedProtocol.equals(networkComponent1771.providedProtocol);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.providedProtocol.hashCode();
   }
}
