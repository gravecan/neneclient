package aethereal.lib.jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

public final class Connection_2 {
   private final Connection delegate;
   private final Map<String, String> formData = new HashMap<>();

   private Connection_2(String string) {
      this.delegate = Jsoup.connect(string);
   }

   public static Connection_2 computeValue_1283(String string) {
      return new Connection_2(string);
   }

   public Connection_2 computeValue_1284(String string, String string1) {
      this.delegate.cookie(string, string1);
      return this;
   }

   public Connection_2 computeValue_1285(Map<String, String> map) {
      map.forEach(this.delegate::cookie);
      return this;
   }

   public Connection_2 computeValue_1277(Map<String, String> map) {
      map.forEach(this.delegate::header);
      return this;
   }

   public Connection_2 computeValue_1282(Connection_2.State331 state331) {
      this.delegate.method(Method.valueOf(state331.name()));
      return this;
   }

   public Connection_2 computeValue_1278(Map<String, String> map) {
      this.formData.putAll(map);
      return this;
   }

   public Connection_2 computeValue_1276(String string, String string1) {
      this.formData.put(string, string1);
      return this;
   }

   public Connection_2 computeValue_1286(boolean bl) {
      this.delegate.followRedirects(bl);
      return this;
   }

   public Connection_2 computeValue_1281(int i) {
      this.delegate.timeout(i);
      return this;
   }

   public Connection_2.NestedType330 computeValue_1279() throws IOException {
      this.formData.forEach(this.delegate::data);
      Response response = this.delegate.execute();
      return new Connection_2.NestedType330(response);
   }

   public JsoupComponent138 computeValue_1280() throws IOException {
      return new JsoupComponent138(this.delegate.get());
   }

   public static class NestedType329 {
      private final Map<String, String> cookies = new HashMap<>();

      public Map<String, String> getCookies_1287() {
         return this.cookies;
      }
   }

   public static class NestedType330 {
      private final Response response;

      NestedType330(Response response) {
         this.response = responsex;
      }

      public Map<String, String> getResponse_1289() {
         return this.response.cookies();
      }

      public String getResponse_1290() {
         return this.response.body();
      }

      public JsoupComponent138 computeValue_1288() throws IOException {
         return new JsoupComponent138(this.response.parse());
      }
   }

   public enum State331 {
      GET,
      POST;
   }
}
