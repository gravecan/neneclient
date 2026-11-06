package aethereal.network;

import aethereal.lib.jsoup.Connection_2;
import aethereal.lib.jsoup.JsoupComponent139;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NetworkComponent175 {
   private final List<String> listField1 = new ArrayList<>();
   private List<NetworkComponent174> listField2;
   private final NetworkComponent173 networkComponent173Field3;

   public List<NetworkComponent174> getListField2_2464() {
      return this.listField2;
   }

   public NetworkComponent175(NetworkComponent173 networkComponent173) {
      this.networkComponent173Field3 = Objects.requireNonNull(networkComponent173);
   }

   public void performAction_2463() {
      List list = this.computeValue_2460();
      if (list != null) {
         List list1 = this.listField2;
         this.listField2 = list;
         if (list1 == null) {
            list.forEach(networkComponent1742 -> this.listField1.add(networkComponent1742.getStringField1_2455()));
         } else if (this.networkComponent173Field3.getNetworkComponent169Field2_2447().getListField3_2375() != null) {
            for (NetworkComponent174 networkComponent174 : list1) {
               if (list.stream()
                  .noneMatch(networkComponent1743 -> networkComponent1743.getStringField1_2455().equals(networkComponent174.getStringField1_2455()))) {
                  this.networkComponent173Field3
                     .getNetworkComponent169Field2_2447()
                     .getListField3_2375()
                     .stream()
                     .filter(chatModel -> chatModel.getLogin_2355().equalsIgnoreCase(networkComponent174.getBuyerName_2459()))
                     .filter(chatModel -> chatModel.getState361Field2_2351() != null)
                     .findFirst()
                     .ifPresent(chatModel -> chatModel.setState361Field2_2352((ChatModel.State361)null));
               }
            }

            for (NetworkComponent174 networkComponent1741 : list) {
               if (!this.listField1.contains(networkComponent1741.getStringField1_2455())) {
                  this.listField1.add(networkComponent1741.getStringField1_2455());
                  this.networkComponent173Field3
                     .getNetworkComponent169Field2_2447()
                     .getListField3_2375()
                     .stream()
                     .filter(chatModel -> chatModel.getLogin_2355().equalsIgnoreCase(networkComponent1741.getBuyerName_2459()))
                     .filter(chatModel -> chatModel.getState361Field2_2351() == null)
                     .findFirst()
                     .ifPresent(
                        chatModel -> {
                           if (networkComponent1741.getCount_2458()
                              < this.networkComponent173Field3.getDeliver_2441().getFloatSettingField3_1927().getCurrentValue()) {
                              this.networkComponent173Field3.performAction_2444(networkComponent1741.getStringField1_2455());
                              this.networkComponent173Field3
                                 .performAction_2443(
                                    chatModel.getRoomId_2349(),
                                    "❌ Минимальная сумма заказа — "
                                       + this.networkComponent173Field3.getDeliver_2441().getFloatSettingField3_1927().getCurrentValue().intValue()
                                       + "кк. Средства возвращены, приносим извинения!"
                                 );
                           } else {
                              this.networkComponent173Field3
                                 .performAction_2443(
                                    chatModel.getRoomId_2349(),
                                    "\ud83d\udc4b Здравствуйте! Это бот выдачи валюты от — NeneClient!\n\n\ud83e\uddf8 Для начала выдачи, пожалуйста, укажите ваш игровой никнейм:\n\nℹ️ Если заказ был создан по ошибке, напишите «Отмена» для возврата средств."
                                 );
                              chatModel.setState361Field2_2352(ChatModel.State361.NICKNAME);
                           }
                        }
                     );
               }
            }
         }
      }
   }

   public NetworkComponent174 computeValue_2471(String string) {
      return this.listField2 == null
         ? null
         : this.listField2.stream().filter(networkComponent174 -> networkComponent174.getBuyerName_2459().equalsIgnoreCase(string)).findFirst().orElse(null);
   }

   private List<NetworkComponent174> computeValue_2460() {
      ArrayList arrayList = new ArrayList();

      try {
         Connection_2.NestedType330 nestedType330 = this.networkComponent173Field3.computeValue_2450("https://funpay.com/orders/trade");

         for (JsoupComponent139 jsoupComponent139 : nestedType330.computeValue_1288().computeValue_1291(".tc-item")) {
            try {
               String string = Objects.requireNonNull(jsoupComponent139.computeValue_1301(".tc-status")).getDelegate_1298();
               String string1 = Objects.requireNonNull(jsoupComponent139.computeValue_1301(".order-desc .text-muted")).getDelegate_1298();
               if (string.equalsIgnoreCase("Оплачен") && string1.contains("Валюта")) {
                  String string2 = Objects.requireNonNull(jsoupComponent139.computeValue_1301(".tc-order")).getDelegate_1298();
                  String string3 = Objects.requireNonNull(jsoupComponent139.computeValue_1301(".order-desc > div")).getDelegate_1298();
                  String string4 = Objects.requireNonNull(jsoupComponent139.computeValue_1301(".media-user-name > span")).getDelegate_1298();
                  double d = Double.parseDouble(Objects.requireNonNull(jsoupComponent139.computeValue_1301(".tc-price")).getDelegate_1299().trim());
                  String string5 = Objects.requireNonNull(jsoupComponent139.computeValue_1301(".tc-price > span")).getDelegate_1298();
                  int i = 1;
                  String[] strings = string3.split(",");
                  if (strings.length > 1) {
                     String string6 = strings[strings.length - 1].trim();
                     if (string6.contains("шт.")) {
                        i = Integer.parseInt(string6.split("шт.")[0].trim());
                     }
                  }

                  if (Math.abs(d / i - this.networkComponent173Field3.getDeliver_2441().getFloatSettingField2_1926().getCurrentValue().floatValue())
                     <= 0.010000003747573418) {
                     arrayList.add(new NetworkComponent174(string2, string1, string3, string4, d, string5, i));
                  }
               }
            } catch (Exception exception) {
            }
         }

         return arrayList;
      } catch (Exception exception1) {
         return null;
      }
   }
}
