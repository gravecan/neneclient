package aethereal.network;

import aethereal.util.UtilComponent268;

public class ChatModel {
   private UtilComponent268 utilComponent268Field1 = new UtilComponent268();
   private final String roomId;
   private final long room;
   private final String login;
   private final String message;
   private ChatModel.State361 state361Field2;
   private String stringField3;

   public void setUtilComponent268Field1_2353(UtilComponent268 utilComponent268) {
      this.utilComponent268Field1 = utilComponent268;
   }

   public void setState361Field2_2352(ChatModel.State361 state361) {
      this.state361Field2 = state361;
   }

   public void setStringField3_2354(String string) {
      this.stringField3 = string;
   }

   public ChatModel(String string, long l, String string1, String string2) {
      this.roomId = string;
      this.room = l;
      this.login = string1;
      this.message = string2;
   }

   public UtilComponent268 getUtilComponent268Field1_2348() {
      return this.utilComponent268Field1;
   }

   public String getRoomId_2349() {
      return this.roomId;
   }

   public long getRoom_2350() {
      return this.room;
   }

   public String getLogin_2355() {
      return this.login;
   }

   public String getMessage_2347() {
      return this.message;
   }

   public ChatModel.State361 getState361Field2_2351() {
      return this.state361Field2;
   }

   public String getStringField3_2356() {
      return this.stringField3;
   }

   public enum State361 {
      NICKNAME,
      CONFIRM_NICKNAME,
      DELIVERY;
   }
}
