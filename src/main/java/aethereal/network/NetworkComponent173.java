package aethereal.network;

import aethereal.discord.DiscordComponent069;
import aethereal.lib.jsoup.Connection_2;
import aethereal.lib.jsoup.JsoupComponent138;
import aethereal.lib.jsoup.JsoupComponent139;
import aethereal.lib.jsoup.JsoupComponent141;
import aethereal.module.misc.FunDeliver;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Map;

public class NetworkComponent173 {
   private final DiscordComponent069 discordComponent069Field1 = new DiscordComponent069();
   private final NetworkComponent169 networkComponent169Field2 = new NetworkComponent169(this);
   private final NetworkComponent175 networkComponent175Field3 = new NetworkComponent175(this);
   private final FunDeliver deliver;

   public DiscordComponent069 getDiscordComponent069Field1_2446() {
      return this.discordComponent069Field1;
   }

   public NetworkComponent169 getNetworkComponent169Field2_2447() {
      return this.networkComponent169Field2;
   }

   public NetworkComponent175 getNetworkComponent175Field3_2448() {
      return this.networkComponent175Field3;
   }

   public FunDeliver getDeliver_2441() {
      return this.deliver;
   }

   public NetworkComponent173(FunDeliver funDeliver) {
      this.deliver = funDeliver;
   }

   public boolean checkCondition_2445() {
      if (this.discordComponent069Field1.getStringField1_0942() != null) {
         return true;
      }

      try {
         Connection_2.NestedType330 nestedType330 = JsoupComponent141.computeValue_1309("https://funpay.com/")
            .computeValue_1284("golden_key", this.deliver.getStringSettingField1_1930().getCurrentValue())
            .computeValue_1281(5000)
            .computeValue_1279();
         Map map = nestedType330.getResponse_1289();
         JsoupComponent138 jsoupComponent138 = nestedType330.computeValue_1288();
         JsoupComponent139 jsoupComponent139 = jsoupComponent138.computeValue_1294(".user-link-name");
         if (jsoupComponent139 == null) {
            return false;
         }

         JsonObject jsonObject = JsonParser.parseString(jsoupComponent138.computeValue_1292().computeValue_1295("data-app-data")).getAsJsonObject();
         this.discordComponent069Field1.setStringField1_0946(jsonObject.get("userId").getAsString());
         this.discordComponent069Field1.setStringField5_0949(jsonObject.get("csrf-token").getAsString());
         this.discordComponent069Field1.setStringField3_0948((String)map.get("PHPSESSID"));
         this.discordComponent069Field1.setStringField4_0945((String)map.get("golden_seal"));
         this.discordComponent069Field1.setStringField2_0941(jsoupComponent139.getDelegate_1298());
         this.networkComponent169Field2.setListField3_2377(null);
         return true;
      } catch (Exception exception) {
         return false;
      }
   }

   public void performAction_2452() {
      this.networkComponent169Field2.performAction_2374();
      this.networkComponent175Field3.performAction_2463();
   }

   public Connection_2.NestedType330 computeValue_2451(String string, String string1) throws Exception {
      return JsoupComponent141.computeValue_1309("https://funpay.com/runner/")
         .computeValue_1285(
            Map.of(
               "golden_key",
               this.deliver.getStringSettingField1_1930().getCurrentValue(),
               "PHPSESSID",
               this.discordComponent069Field1.getStringField3_0944(),
               "golden_seal",
               this.discordComponent069Field1.getStringField4_0947()
            )
         )
         .computeValue_1277(Map.of("accept", "*/*", "content-type", "application/x-www-form-urlencoded; charset=UTF-8", "x-requested-with", "XMLHttpRequest"))
         .computeValue_1282(Connection_2.State331.POST)
         .computeValue_1278(
            Map.of(
               "objects",
               string == null ? "" : string,
               "request",
               string1 == null ? "" : string1,
               "csrf_token",
               this.discordComponent069Field1.getStringField5_0940() == null ? "" : this.discordComponent069Field1.getStringField5_0940()
            )
         )
         .computeValue_1286(true)
         .computeValue_1281(5000)
         .computeValue_1279();
   }

   public Connection_2.NestedType330 computeValue_2450(String string) throws Exception {
      return JsoupComponent141.computeValue_1309(string)
         .computeValue_1285(
            Map.of(
               "golden_key",
               this.deliver.getStringSettingField1_1930().getCurrentValue(),
               "PHPSESSID",
               this.discordComponent069Field1.getStringField3_0944(),
               "golden_seal",
               this.discordComponent069Field1.getStringField4_0947()
            )
         )
         .computeValue_1277(Map.of("accept", "*/*", "x-requested-with", "XMLHttpRequest"))
         .computeValue_1282(Connection_2.State331.POST)
         .computeValue_1286(true)
         .computeValue_1281(5000)
         .computeValue_1279();
   }

   public void performAction_2444(String string) {
      try {
         JsoupComponent139 jsoupComponent139 = JsoupComponent141.computeValue_1309(
               "https://funpay.com/orders/" + (string.startsWith("#") ? string.substring(1) : string) + "/"
            )
            .computeValue_1285(
               Map.of(
                  "golden_key",
                  this.deliver.getStringSettingField1_1930().getCurrentValue(),
                  "PHPSESSID",
                  this.discordComponent069Field1.getStringField3_0944(),
                  "golden_seal",
                  this.discordComponent069Field1.getStringField4_0947()
               )
            )
            .computeValue_1281(5000)
            .computeValue_1280()
            .computeValue_1294(".modal-refund form");
         if (jsoupComponent139 == null) {
            return;
         }

         Connection_2 connection_2 = JsoupComponent141.computeValue_1309(
               jsoupComponent139.computeValue_1304("action").startsWith("http")
                  ? jsoupComponent139.computeValue_1304("action")
                  : "https://funpay.com" + jsoupComponent139.computeValue_1304("action")
            )
            .computeValue_1285(
               Map.of(
                  "golden_key",
                  this.deliver.getStringSettingField1_1930().getCurrentValue(),
                  "PHPSESSID",
                  this.discordComponent069Field1.getStringField3_0944(),
                  "golden_seal",
                  this.discordComponent069Field1.getStringField4_0947()
               )
            )
            .computeValue_1282(Connection_2.State331.POST)
            .computeValue_1286(true)
            .computeValue_1281(5000);
         jsoupComponent139.computeValue_1302("input[type=hidden]")
            .forEach(element -> connection_2.computeValue_1276(element.attr("name"), element.attr("value")));
         connection_2.computeValue_1276("csrf_token", this.discordComponent069Field1.getStringField5_0940()).computeValue_1279();
      } catch (Exception exception) {
      }
   }

   public Connection_2.NestedType330 computeValue_2442(String string) throws Exception {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", "chat_node");
      jsonObject.addProperty("id", string);
      jsonObject.addProperty("tag", "00000000");
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("node", string);
      jsonObject1.addProperty("last_message", -1);
      jsonObject1.addProperty("content", "");
      jsonObject.add("data", jsonObject1);
      JsonArray jsonArray = new JsonArray();
      jsonArray.add(jsonObject);
      return this.computeValue_2451(jsonArray.toString(), String.valueOf(false));
   }

   public void performAction_2443(String string, String string1) {
      try {
         JsonObject jsonObject = new JsonObject();
         jsonObject.addProperty("action", "chat_message");
         JsonObject jsonObject1 = new JsonObject();
         jsonObject1.addProperty("node", string);
         jsonObject1.addProperty("last_message", -1);
         jsonObject1.addProperty("content", string1);
         jsonObject.add("data", jsonObject1);
         JsonObject jsonObject2 = new JsonObject();
         jsonObject2.addProperty("type", "chat_node");
         jsonObject2.addProperty("id", string);
         jsonObject2.addProperty("tag", "00000000");
         JsonObject jsonObject3 = new JsonObject();
         jsonObject3.addProperty("node", string);
         jsonObject3.addProperty("last_message", -1);
         jsonObject3.addProperty("content", "");
         jsonObject2.add("data", jsonObject3);
         JsonArray jsonArray = new JsonArray();
         jsonArray.add(jsonObject2);
         this.computeValue_2451(jsonArray.toString(), jsonObject.toString());
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }
}
