package aethereal.lib.json;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonComponent136 {
   private final JSONObject delegate;

   public JsonComponent136() {
      this.delegate = new JSONObject();
   }

   public JsonComponent136(String string) {
      this.delegate = new JSONObject(string);
   }

   public JsonComponent136(JSONObject jSONObject) {
      this.delegate = jSONObject;
   }

   public JSONObject getDelegate_1262() {
      return this.delegate;
   }

   public JsonComponent136 computeValue_1272(String string, Object object) {
      if (object instanceof JsonComponent136 jsonComponent1361) {
         this.delegate.put(string, jsonComponent1361.delegate);
      } else if (object instanceof JsonComponent135 jsonComponent135) {
         this.delegate.put(string, jsonComponent135.getDelegate_1251());
      } else {
         this.delegate.put(string, object);
      }

      return this;
   }

   public JsonComponent136 computeValue_1273(String string, boolean bl) {
      this.delegate.put(string, bl);
      return this;
   }

   public JsonComponent136 computeValue_1270(String string, double d) {
      this.delegate.put(string, d);
      return this;
   }

   public JsonComponent136 computeValue_1256(String string, Object object) {
      if (object instanceof JsonComponent136 jsonComponent1361) {
         this.delegate.put(string, jsonComponent1361.delegate);
      } else if (object instanceof JsonComponent135 jsonComponent135) {
         this.delegate.put(string, jsonComponent135.getDelegate_1251());
      } else {
         this.delegate.put(string, object);
      }

      return this;
   }

   public String computeValue_1259(int i) {
      return this.delegate.toString(i);
   }

   public String computeValue_1260(String string) {
      return this.delegate.optString(string, null);
   }

   public boolean checkCondition_1258(String string) {
      return this.delegate.has(string);
   }

   public double computeValue_1264(String string) {
      return this.delegate.optDouble(string);
   }

   public float computeValue_1265(String string) {
      return (float)this.delegate.optDouble(string);
   }

   public boolean checkCondition_1261(String string) {
      return this.delegate.optBoolean(string);
   }

   public int computeValue_1266(String string) {
      return this.delegate.optInt(string);
   }

   public JsonComponent136 computeValue_1268(String string) {
      JSONObject jSONObject = this.delegate.optJSONObject(string);
      return jSONObject == null ? null : new JsonComponent136(jSONObject);
   }

   public JsonComponent135 computeValue_1267(String string) {
      JSONArray jSONArray = this.delegate.optJSONArray(string);
      return jSONArray == null ? null : new JsonComponent135(jSONArray);
   }

   public JsonComponent135 computeValue_1255(String string) {
      return this.computeValue_1267(string);
   }

   public Object computeValue_1269(String string) {
      return this.delegate.opt(string);
   }

   public boolean checkCondition_1274(String string, boolean bl) {
      return this.delegate.optBoolean(string, bl);
   }

   public int computeValue_1271(String string, int i) {
      return this.delegate.optInt(string, i);
   }

   public String computeValue_1257(String string, String string1) {
      return this.delegate.optString(string, string1);
   }

   public boolean checkCondition_1275(String string) {
      return this.delegate.optBoolean(string);
   }

   public String computeValue_1263(int i) {
      return this.delegate.toString(i);
   }
}
