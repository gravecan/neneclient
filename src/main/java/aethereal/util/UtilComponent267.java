package aethereal.util;

import aethereal.lib.jsoup.Connection_2;
import aethereal.lib.jsoup.JsoupComponent140;
import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class UtilComponent267 {
   private static final Map<String, List<String>> mapField1 = Collections.unmodifiableMap(new HashMap<>());
   private static final String stringField2 = "; ";
   private static final String stringField3 = "Cookie";
   private static final String stringField4 = "Cookie2";

   UtilComponent267() {
   }

   static void performAction_3513(JsoupComponent140.NestedType332 nestedType332, HttpURLConnection httpURLConnection) throws IOException {
      LinkedHashSet linkedHashSet = computeValue_3511(nestedType332);
      HashSet hashSet = null;
      Map map = nestedType332.getCookieManagerField1_1306().get(computeValue_3514(nestedType332.getUrl_1305()), mapField1);

      for (Entry entry : map.entrySet()) {
         List list = (List)entry.getValue();
         if (list != null && list.size() != 0) {
            String string = (String)entry.getKey();
            if ("Cookie".equals(string)) {
               linkedHashSet.addAll(list);
            } else if ("Cookie2".equals(string)) {
               if (hashSet == null) {
                  hashSet = new HashSet();
               }

               hashSet.addAll(list);
            }
         }
      }

      if (linkedHashSet.size() > 0) {
         httpURLConnection.addRequestProperty("Cookie", UtilComponent253.computeValue_3447(linkedHashSet, "; "));
      }

      if (hashSet != null && hashSet.size() > 0) {
         httpURLConnection.addRequestProperty("Cookie2", UtilComponent253.computeValue_3447(hashSet, "; "));
      }
   }

   private static LinkedHashSet<String> computeValue_3511(JsoupComponent140.NestedType332 nestedType332) {
      return new LinkedHashSet<>();
   }

   private static LinkedHashSet<String> computeValue_3510(Connection_2.NestedType329 nestedType329) {
      LinkedHashSet linkedHashSet = new LinkedHashSet();

      for (Entry entry : nestedType329.getCookies_1287().entrySet()) {
         linkedHashSet.add((String)entry.getKey() + "=" + (String)entry.getValue());
      }

      return linkedHashSet;
   }

   static URI computeValue_3514(URL uRL) throws IOException {
      try {
         return uRL.toURI();
      } catch (URISyntaxException uRISyntaxException) {
         MalformedURLException malformedURLException = new MalformedURLException(uRISyntaxException.getMessage());
         malformedURLException.initCause(uRISyntaxException);
         throw malformedURLException;
      }
   }

   static void performAction_3512(
      JsoupComponent140.NestedType332 nestedType332, JsoupComponent140.NestedType333 nestedType333, URL uRL, Map<String, List<String>> map
   ) throws IOException {
      CookieManager cookieManager = nestedType332.getCookieManagerField1_1306();
      URI uRI = computeValue_3514(uRL);
      cookieManager.put(uRI, map);

      for (Entry entry : map.entrySet()) {
         String string = (String)entry.getKey();
         List list = (List)entry.getValue();
         if (string.equalsIgnoreCase("Set-Cookie")) {
            for (String string1 : list) {
               if (string1 != null) {
                  UtilComponent255 utilComponent255 = new UtilComponent255(string1);
                  String string2 = utilComponent255.computeValue_3456("=").trim();
                  String string3 = utilComponent255.computeValue_3457(";").trim();
                  nestedType333.performAction_1307(string2, string3);
               }
            }
         }
      }
   }
}
