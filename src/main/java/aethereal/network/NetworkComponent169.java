package aethereal.network;

import aethereal.lib.jsoup.JsoupComponent139;
import aethereal.lib.jsoup.JsoupComponent141;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent279;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NetworkComponent169 {
   private String stringField1 = Integer.toHexString((int)UtilComponent279.computeValue_3582(0.0F, 2.1474836E9F));
   private final NetworkComponent173 networkComponent173Field2;
   private List<ChatModel> listField3;

   public List<ChatModel> getListField3_2375() {
      return this.listField3;
   }

   public void setListField3_2377(List<ChatModel> list) {
      this.listField3 = list;
   }

   public NetworkComponent169(NetworkComponent173 networkComponent173) {
      this.networkComponent173Field2 = Objects.requireNonNull(networkComponent173);
   }

   public void performAction_2374() {
      try {
         List list = this.computeValue_2373();
         if (!list.isEmpty()) {
            List list1 = this.listField3;
            this.listField3 = list;
            JsonParser jsonParser = new JsonParser();
            if (list1 != null) {
               for (ChatModel chatModel : list) {
                  ChatModel chatModel1 = list1.stream()
                     .filter(chatModel3 -> chatModel3.getRoomId_2349().equals(chatModel.getRoomId_2349()))
                     .findFirst()
                     .orElse(null);
                  long l = chatModel1 != null ? chatModel1.getRoom_2350() : 0L;
                  if (chatModel1 != null) {
                     chatModel.setState361Field2_2352(chatModel1.getState361Field2_2351());
                     chatModel.setStringField3_2354(chatModel1.getStringField3_2356());
                     chatModel.setUtilComponent268Field1_2353(chatModel1.getUtilComponent268Field1_2348());
                  }

                  if (chatModel.getRoom_2350() > l) {
                     for (JsonElement jsonElement : jsonParser.parse(
                           this.networkComponent173Field2.computeValue_2442(chatModel.getRoomId_2349()).getResponse_1290()
                        )
                        .getAsJsonObject()
                        .getAsJsonArray("objects")) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if ("chat_node".equalsIgnoreCase(jsonObject.get("type").getAsString())) {
                           for (JsonElement jsonElement1 : jsonObject.getAsJsonObject("data").getAsJsonArray("messages")) {
                              JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
                              if (jsonObject1.get("id").getAsLong() > l
                                 && !jsonObject1.get("author")
                                    .getAsString()
                                    .equals(this.networkComponent173Field2.getDiscordComponent069Field1_2446().getStringField1_0942())
                                 && !jsonObject1.get("author").getAsString().equals("0")) {
                                 String string = JsoupComponent141.computeValue_1310(jsonObject1.get("html").getAsString())
                                    .computeValue_1291(".chat-msg-text")
                                    .stream()
                                    .findFirst()
                                    .map(JsoupComponent139::getDelegate_1297)
                                    .orElse("");
                                 String string1 = string.replaceAll("[^a-zA-Zа-яА-Я]", "").toLowerCase();
                                 NetworkComponent174 networkComponent174 = this.networkComponent173Field2
                                    .getNetworkComponent175Field3_2448()
                                    .computeValue_2471(chatModel.getLogin_2355());
                                 if (networkComponent174 == null
                                    || chatModel.getState361Field2_2351() != ChatModel.State361.NICKNAME
                                    || !string1.equals("отмена") && !string1.equals("otmena")) {
                                    if (chatModel.getState361Field2_2351() == ChatModel.State361.NICKNAME) {
                                       String string2 = string.replaceAll("[^a-zA-Z0-9_]", "");
                                       chatModel.setStringField3_2354(string2);
                                       chatModel.setState361Field2_2352(ChatModel.State361.CONFIRM_NICKNAME);
                                       this.networkComponent173Field2
                                          .performAction_2443(
                                             chatModel.getRoomId_2349(),
                                             "✅ Отлично! Давайте проверим данные:\n\n\ud83d\udcdd Ваш никнейм: "
                                                + string2
                                                + "\n\ud83d\udce6 Количество: "
                                                + (networkComponent174 != null ? networkComponent174.getCount_2458() : 0)
                                                + " шт.\n\n✔️ Всё верно? Напишите «Да» или «+»\n✏️ Нужно исправить? Напишите «Нет» или «-»"
                                          );
                                    } else if (chatModel.getState361Field2_2351() == ChatModel.State361.CONFIRM_NICKNAME) {
                                       if (string1.equals("да") || string1.equals("da") || string.contains("✗")) {
                                          this.networkComponent173Field2
                                             .performAction_2443(
                                                chatModel.getRoomId_2349(),
                                                "✅ Отлично! Данные подтверждены.\n\n\ud83d\udfe2 Для получения валюты зайдите на Анархию: "
                                                   + UtilComponent252.NestedType400.computeValue_3414()
                                                   + "\n\n⚡ У вас есть 20 секунд для получения валюты, иначе потребуется указать никнейм заново."
                                             );
                                          chatModel.setState361Field2_2352(ChatModel.State361.DELIVERY);
                                          chatModel.getUtilComponent268Field1_2348().performAction_3524();
                                       } else if (string1.equals("нет") || string1.equals("net") || string.contains("✓")) {
                                          chatModel.setState361Field2_2352(ChatModel.State361.NICKNAME);
                                          this.networkComponent173Field2
                                             .performAction_2443(chatModel.getRoomId_2349(), "\ud83e\uddf8 Пожалуйста, укажите ваш игровой никнейм:");
                                       }
                                    }
                                 } else {
                                    chatModel.setState361Field2_2352((ChatModel.State361)null);
                                    this.networkComponent173Field2.performAction_2444(networkComponent174.getStringField1_2455());
                                    this.networkComponent173Field2.performAction_2443(chatModel.getRoomId_2349(), "↩️ Заказ отменён, средства возвращены!");
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (Exception exception) {
      }
   }

   private List<ChatModel> computeValue_2373() throws Exception {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", "chat_bookmarks");
      jsonObject.addProperty("id", this.networkComponent173Field2.getDiscordComponent069Field1_2446().getStringField1_0942());
      jsonObject.addProperty("tag", this.stringField1);
      jsonObject.addProperty("data", false);
      JsonArray jsonArray = new JsonArray();
      jsonArray.add(jsonObject);
      ArrayList arrayList = new ArrayList();

      for (JsonElement jsonElement : new JsonParser()
         .parse(this.networkComponent173Field2.computeValue_2451(jsonArray.toString(), String.valueOf(false)).getResponse_1290())
         .getAsJsonObject()
         .getAsJsonArray("objects")) {
         JsonObject jsonObject1 = jsonElement.getAsJsonObject();
         if ("chat_bookmarks".equalsIgnoreCase(jsonObject1.get("type").getAsString())) {
            this.stringField1 = jsonObject1.get("tag").getAsString();

            for (JsoupComponent139 jsoupComponent139 : JsoupComponent141.computeValue_1310(jsonObject1.getAsJsonObject("data").get("html").getAsString())
               .computeValue_1291(".contact-item")) {
               arrayList.add(
                  new ChatModel(
                     jsoupComponent139.checkCondition_1300("data-id") ? jsoupComponent139.computeValue_1304("data-id") : "",
                     jsoupComponent139.checkCondition_1300("data-node-msg") ? Long.parseLong(jsoupComponent139.computeValue_1304("data-node-msg")) : 0L,
                     jsoupComponent139.computeValue_1296(".media-user-name").getDelegate_1297(),
                     jsoupComponent139.computeValue_1296(".contact-item-message").getDelegate_1297()
                  )
               );
            }
         }
      }

      return arrayList;
   }
}
