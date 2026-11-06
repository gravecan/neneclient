package aethereal.lib.json;

import org.json.JSONArray;

public class JsonComponent135 {
   private final JSONArray delegate;

   public JsonComponent135() {
      this.delegate = new JSONArray();
   }

   public JsonComponent135(String string) {
      this.delegate = new JSONArray(string);
   }

   public JsonComponent135(JSONArray jSONArray) {
      this.delegate = jSONArray;
   }

   public JSONArray getDelegate_1251() {
      return this.delegate;
   }

   public int getDelegate_1250() {
      return this.delegate.length();
   }

   public JsonComponent136 computeValue_1252(int i) {
      return new JsonComponent136(this.delegate.getJSONObject(i));
   }

   public String computeValue_1249(int i) {
      Object object = this.delegate.opt(i);
      return object == null ? null : String.valueOf(object);
   }

   public JsonComponent135 computeValue_1253(Object object) {
      this.delegate.put(object instanceof JsonComponent136 jsonComponent136 ? jsonComponent136.getDelegate_1262() : object);
      return this;
   }

   public String computeValue_1254(int i) {
      return this.delegate.toString(i);
   }
}
