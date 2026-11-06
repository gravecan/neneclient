package aethereal.discord;

import aethereal.lib.javassist.JavassistComponent128;
import aethereal.lib.javassist.JavassistComponent129;
import aethereal.lib.javassist.OpCode;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

final class DiscordComponent061 {
   private DiscordComponent061() {
   }

   static JavassistComponent128 computeValue_0892(InputStream inputStream) throws IOException {
      byte[] b = inputStream.readNBytes(8);
      if (b.length < 8) {
         throw new IOException("Unexpected end of stream");
      }

      ByteBuffer byteBuffer = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN);
      OpCode opCode = OpCode.computeValue_1234(byteBuffer.getInt());
      int i = byteBuffer.getInt();
      byte[] c = i > 0 ? inputStream.readNBytes(i) : new byte[0];
      return new JavassistComponent128(opCode, JavassistComponent129.computeValue_1246(c));
   }
}
