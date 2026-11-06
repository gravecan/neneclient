package aethereal.network;

import aethereal.lib.misc.MiscContract151;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;

public class NetworkComponent168 extends Authenticator {
   static final int integerField1 = 5;
   static NetworkComponent168.Contract362 contract362Field2;
   MiscContract151 auth;
   int integerField3 = 0;

   NetworkComponent168() {
   }

   NetworkComponent168(MiscContract151 miscContract151) {
      this.auth = miscContract151;
   }

   @Override
   public final PasswordAuthentication getPasswordAuthentication() {
      NetworkComponent168 networkComponent1681 = contract362Field2.computeValue_2372(this);
      if (networkComponent1681 == null) {
         return null;
      } else {
         networkComponent1681.integerField3++;
         if (networkComponent1681.integerField3 <= 5 && networkComponent1681.auth != null) {
            MiscContract151.NestedType334 nestedType334 = new MiscContract151.NestedType334(
               this.getRequestingURL(), this.getRequestorType(), this.getRequestingPrompt()
            );
            return networkComponent1681.auth.computeValue_1324(nestedType334);
         } else {
            return null;
         }
      }
   }

   static {
      try {
         contract362Field2 = (NetworkComponent168.Contract362)Class.forName("org.jsoup.helper.RequestAuthHandler").getConstructor().newInstance();
      } catch (ClassNotFoundException classNotFoundException) {
         contract362Field2 = new NetworkComponent168.NestedType363();
      } catch (Exception exception) {
         throw new IllegalStateException(exception);
      }
   }

   interface Contract362 {
      void performAction_2371(MiscContract151 miscContract151, HttpURLConnection httpURLConnection);

      void performAction_2370();

      NetworkComponent168 computeValue_2372(NetworkComponent168 networkComponent168);
   }

   static class NestedType363 implements NetworkComponent168.Contract362 {
      static ThreadLocal<NetworkComponent168> threadLocalField1 = new ThreadLocal<>();

      @Override
      public void performAction_2371(MiscContract151 miscContract151, HttpURLConnection httpURLConnection) {
         threadLocalField1.set(new NetworkComponent168(miscContract151));
      }

      @Override
      public void performAction_2370() {
         threadLocalField1.remove();
      }

      @Override
      public NetworkComponent168 computeValue_2372(NetworkComponent168 networkComponent168) {
         return threadLocalField1.get();
      }

      static {
         Authenticator.setDefault(new NetworkComponent168());
      }
   }
}
