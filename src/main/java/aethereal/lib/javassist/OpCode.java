package aethereal.lib.javassist;

public enum OpCode {
   HANDSHAKE(0),
   FRAME(1),
   CLOSE(2),
   PING(3),
   PONG(4);

   private final int code;

   OpCode(int j) {
      this.code = j;
   }

   public int getCode_1233() {
      return this.code;
   }

   public static OpCode computeValue_1234(int i) {
      for (OpCode opCode : values()) {
         if (opCode.code == i) {
            return opCode;
         }
      }

      return null;
   }
}
