package aethereal.network;

import aethereal.api.ApiAnnotation001;
import aethereal.core.ObfuscatorRuntime;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@ApiAnnotation001
public class NetworkComponent176 {
   private byte[] byteValueArrayField1;
   private Gson gsonField2;

   public String computeValue_2474(String string, String string1) {
      Gson gson = this.gsonField2;
      Object object;
      if (string == null || string.isBlank() || (object = gson.fromJson(string, JsonObject.class)) == null) {
         return null;
      } else if (!(object instanceof JsonObject jsonObject)) {
         throw new ClassCastException();
      } else {
         return jsonObject.has(string1) && !jsonObject.get(string1).isJsonNull() ? jsonObject.get(string1).getAsString() : null;
      }
   }

   public JsonElement computeValue_2479(String string, String string1) {
      Gson gson = this.gsonField2;
      if (string == null || string.isBlank()) {
         return null;
      } else if (gson == null) {
         throw new NullPointerException();
      } else {
         Object object = gson.fromJson(string, JsonObject.class);
         if (object == null) {
            return null;
         } else if (!(object instanceof JsonObject jsonObject)) {
            throw new ClassCastException();
         } else if (!jsonObject.has(string1)) {
            return null;
         } else {
            JsonElement jsonElement = jsonObject.get(string1);
            if (jsonElement == null) {
               throw new NullPointerException();
            } else {
               return jsonElement.isJsonNull() ? null : jsonObject.get(string1);
            }
         }
      }
   }

   public List<String> computeValue_2480(String string, String string1) {
      Gson gson = this.gsonField2;
      if (string == null || string.isBlank()) {
         return null;
      } else if (gson == null) {
         throw new NullPointerException();
      } else {
         Object object = gson.fromJson(string, JsonObject.class);
         if (object == null) {
            return null;
         } else if (!(object instanceof JsonObject jsonObject)) {
            throw new ClassCastException();
         } else if (!jsonObject.has(string1)) {
            return null;
         } else {
            JsonElement jsonElement = jsonObject.get(string1);
            if (jsonElement == null) {
               throw new NullPointerException();
            } else if (!jsonElement.isJsonArray()) {
               return null;
            } else {
               JsonArray jsonArray = jsonObject.getAsJsonArray(string1);
               if (jsonArray == null) {
                  throw new NullPointerException();
               } else {
                  Stream stream = StreamSupport.stream(jsonArray.spliterator(), false);
                  Function function = new Function() {
                     @Override
                     public Object apply(Object object2) {
                        return NetworkComponent176.computeValue_2473((JsonElement)object2);
                     }
                  };
                  if (stream == null) {
                     throw new NullPointerException();
                  } else {
                     Stream stream1 = stream.map(function);
                     Collector collector = Collectors.toList();
                     if (stream1 == null) {
                        throw new NullPointerException();
                     } else {
                        Object object1 = stream1.collect(collector);
                        if (object1 == null) {
                           return null;
                        } else if (object1 instanceof List) {
                           return (List<String>)object1;
                        } else {
                           throw new ClassCastException();
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public String computeValue_2481(Object... objects) {
      Gson gson = this.gsonField2;
      if (objects != null && (objects.length & 1) == 0) {
         JsonObject jsonObject = new JsonObject();

         for (byte b = 0; b < objects.length; b += 2) {
            String string = String.valueOf(objects[b]);
            Object object1 = objects[b + 1];
            Object object;
            if (object1 instanceof Number) {
               object = new JsonPrimitive((Number)object1);
            } else {
               object = object1 instanceof Boolean ? new JsonPrimitive((Boolean)object1) : gson.toJsonTree(object1);
            }

            jsonObject.add(string, (JsonElement)object);
         }

         return gson.toJson(jsonObject);
      } else {
         throw new IllegalArgumentException("keyValues must contain even count of elements");
      }
   }

   public String computeValue_2476(String string, String string1) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("id", string);
      jsonObject.add("payload", this.gsonField2.toJsonTree(this.computeValue_2483(string1)));
      return this.gsonField2.toJson(jsonObject);
   }

   public Optional<NetworkComponent176.NestedType371> computeValue_2477(String string) {
      try {
         JsonObject jsonObject = (JsonObject)this.gsonField2.fromJson(string, JsonObject.class);
         return jsonObject.has("id") && jsonObject.has("payload") && !jsonObject.get("id").isJsonNull() && !jsonObject.get("id").getAsString().isBlank()
            ? Optional.of(
               new NetworkComponent176.NestedType371(
                  jsonObject.get("id").isJsonNull() ? null : jsonObject.get("id").getAsString(),
                  this.computeValue_2482(jsonObject.get("payload").getAsString())
               )
            )
            : Optional.empty();
      } catch (Exception exception) {
         return Optional.empty();
      }
   }

   private JsonElement computeValue_2478(Object object) {
      if (object instanceof Number) {
         return new JsonPrimitive((Number)object);
      } else {
         return (JsonElement)(object instanceof Boolean ? new JsonPrimitive((Boolean)object) : this.gsonField2.toJsonTree(object));
      }
   }

   public String computeValue_2483(String string) {
      try {
         byte[] b = this.byteValueArrayField1;
         byte[] c = new byte[12];
         new SecureRandom().nextBytes(c);
         Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
         cipher.init(1, new SecretKeySpec(b, "AES"), new GCMParameterSpec(128, c));
         byte[] d = cipher.doFinal(string.getBytes(StandardCharsets.UTF_8));
         byte[] e = new byte[c.length + d.length];
         System.arraycopy(c, 0, e, 0, c.length);
         System.arraycopy(d, 0, e, c.length, d.length);
         return Base64.getEncoder().encodeToString(e);
      } catch (Exception exception) {
         return null;
      }
   }

   public String computeValue_2482(String string) {
      try {
         byte[] b = this.byteValueArrayField1;
         byte[] c = Base64.getDecoder().decode(string);
         byte[] d = Arrays.copyOfRange(c, 0, 12);
         byte[] e = Arrays.copyOfRange(c, 12, c.length);
         Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
         cipher.init(2, new SecretKeySpec(b, "AES"), new GCMParameterSpec(128, d));
         return new String(cipher.doFinal(e), StandardCharsets.UTF_8);
      } catch (Exception exception) {
         return null;
      }
   }

   public static String computeValue_2473(JsonElement jsonElement) {
      if (jsonElement == null) {
         throw new NullPointerException();
      } else {
         return jsonElement.isJsonNull() ? null : jsonElement.getAsString();
      }
   }

   private void performAction_2475() {
      this.byteValueArrayField1 = HexFormat.of().parseHex("9f3b7c1e6a8d4205b1e7c9a2f4d60837");
      this.gsonField2 = new Gson();
   }

   public NetworkComponent176() {
      this.performAction_2475();
   }

   static {
      ObfuscatorRuntime.registerClass(NetworkComponent176.class, 3);
   }

   public static final class NestedType371 {
      private final String stringField1;
      private final String payload;

      public NestedType371(String string, String string1) {
         this.stringField1 = string;
         this.payload = string1;
      }

      public String getStringField1_2484() {
         return this.stringField1;
      }

      public String getPayload_2485() {
         return this.payload;
      }
   }
}
