package aethereal.friend;

import aethereal.api.ApiAnnotation001;
import aethereal.config.ListProcessor;
import aethereal.core.ObfuscatorRuntime;
import aethereal.lib.json.JsonComponent135;
import aethereal.lib.json.JsonComponent136;
import java.util.ArrayList;
import java.util.List;

public class FriendManager extends ListProcessor<Friend> {
   @ApiAnnotation001
   @Override
   protected List<Friend> computeValue_0041(String string) throws Exception {
      JsonComponent135 jsonComponent135 = new JsonComponent135(string);
      ArrayList arrayList = new ArrayList();

      for (int i = 0; i < jsonComponent135.getDelegate_1250(); i++) {
         arrayList.add(new Friend(jsonComponent135.computeValue_1252(i).computeValue_1260("name")));
      }

      return arrayList;
   }

   @ApiAnnotation001
   @Override
   protected String computeValue_0042(List<Friend> list) throws Exception {
      JsonComponent135 jsonComponent135 = new JsonComponent135();

      for (Friend friend : list) {
         JsonComponent136 jsonComponent136 = new JsonComponent136();
         jsonComponent136.computeValue_1272("name", friend.getName_1103());
         jsonComponent135.computeValue_1253(jsonComponent136);
      }

      return jsonComponent135.computeValue_1254(2);
   }

   @Override
   protected String computeValue_0040() {
      return "friends.json";
   }

   public List<Friend> computeValue_1108() {
      return new ArrayList<>(this.listField3);
   }

   @Override
   public List<Friend> getEntries() {
      return this.computeValue_1108();
   }

   public void performAction_1106(String string) {
      if (!this.checkCondition_1107(string)) {
         this.listField3.add(new Friend(string));
      }
   }

   public void performAction_1111(String string) {
      this.listField3.removeIf(friend -> friend.getName_1103().equalsIgnoreCase(string));
   }

   public boolean checkCondition_1107(String string) {
      return this.listField3.stream().anyMatch(friend -> friend.getName_1103().equalsIgnoreCase(string));
   }

   public void getListField3_1110() {
      this.listField3.clear();
   }

   static {
      ObfuscatorRuntime.registerClass(FriendManager.class, 30);
   }
}
