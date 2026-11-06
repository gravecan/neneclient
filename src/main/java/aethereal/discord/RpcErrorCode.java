package aethereal.discord;

public enum RpcErrorCode {
   UNKNOWN_ERROR(1000, "Unknown error"),
   INVALID_PAYLOAD(4000, "Invalid payload"),
   INVALID_COMMAND(4002, "Invalid command"),
   INVALID_EVENT(4004, "Invalid event"),
   INVALID_CHANNEL(4005, "Invalid channel"),
   INVALID_PERMISSIONS(4006, "Invalid permissions"),
   INVALID_CLIENT_ID(4007, "Invalid client ID"),
   INVALID_ORIGIN(4008, "Invalid origin"),
   INVALID_USER(4010, "Invalid user");

   private final int code;
   private final String description;

   public int getCode_0722() {
      return this.code;
   }

   public String getDescription_0724() {
      return this.description;
   }

   RpcErrorCode(int j, String string1) {
      this.code = j;
      this.description = string1;
   }

   public static RpcErrorCode computeValue_0723(int i) {
      for (RpcErrorCode rpcErrorCode : values()) {
         if (rpcErrorCode.code == i) {
            return rpcErrorCode;
         }
      }

      return UNKNOWN_ERROR;
   }
}
