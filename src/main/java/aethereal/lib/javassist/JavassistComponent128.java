package aethereal.lib.javassist;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public final class JavassistComponent128 {
   private static final Gson GSON = new Gson();
   private final OpCode opCode;
   private final JsonObject data;

   public JavassistComponent128(OpCode opCode, JsonObject jsonObject) {
      this.opCode = opCodex;
      this.data = jsonObject;
   }

   public OpCode getOpCode_1243() {
      return this.opCode;
   }

   public JsonObject getData_1244() {
      return this.data;
   }

   public byte[] computeValue_1242() {
      byte[] b = this.data == null ? new byte[0] : GSON.toJson(this.data).getBytes(StandardCharsets.UTF_8);
      ByteBuffer byteBuffer = ByteBuffer.allocate(8 + b.length).order(ByteOrder.LITTLE_ENDIAN);
      byteBuffer.putInt(this.opCode.getCode_1233());
      byteBuffer.putInt(b.length);
      byteBuffer.put(b);
      return byteBuffer.array();
   }
}
