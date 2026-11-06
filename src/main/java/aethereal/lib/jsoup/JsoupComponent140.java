package aethereal.lib.jsoup;

import java.net.CookieManager;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class JsoupComponent140 {
   private JsoupComponent140() {
   }

   public static class NestedType332 {
      private final URL url;
      private CookieManager cookieManagerField1 = new CookieManager();

      public NestedType332(URL uRL) {
         this.url = uRL;
      }

      public URL getUrl_1305() {
         return this.url;
      }

      public CookieManager getCookieManagerField1_1306() {
         return this.cookieManagerField1;
      }
   }

   public static class NestedType333 {
      private final Map<String, String> cookies = new HashMap<>();

      public void performAction_1307(String string, String string1) {
         this.cookies.put(string, string1);
      }

      public Map<String, String> getCookies_1308() {
         return this.cookies;
      }
   }
}
