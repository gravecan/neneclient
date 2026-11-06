package aethereal.discord;

import aethereal.lib.javassist.JavassistComponent128;
import aethereal.lib.javassist.JavassistComponent129;
import aethereal.lib.javassist.OpCode;
import aethereal.lib.jsoup.JsoupContract137;
import aethereal.lib.log4j.Log4jComponent143;
import aethereal.lib.log4j.Log4jComponent144;
import com.google.gson.JsonObject;
import java.io.EOFException;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.concurrent.locks.ReentrantLock;

public class DiscordComponent042 implements JsoupContract137 {
   private static final Log4jComponent144 log4jComponent144Field1 = Log4jComponent143.computeValue_1311(DiscordComponent042.class);
   private final ReentrantLock reentrantLockField2 = new ReentrantLock();
   private final SocketChannel socketChannelField3 = SocketChannel.open(StandardProtocolFamily.UNIX);
   private volatile boolean flagField4;

   public DiscordComponent042(String string) throws IOException {
      this.socketChannelField3.connect(UnixDomainSocketAddress.of(Path.of(string)));
      this.socketChannelField3.configureBlocking(true);
      log4jComponent144Field1.performAction_1316("Connected to Unix pipe: {}", string);
   }

   @Override
   public boolean checkCondition_0711() {
      return !this.flagField4 && this.socketChannelField3.isOpen() && this.socketChannelField3.isConnected();
   }

   @Override
   public JavassistComponent128 computeValue_0712() throws IOException {
      ByteBuffer byteBuffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
      this.performAction_0714(byteBuffer);
      byteBuffer.flip();
      int i = byteBuffer.getInt();
      int j = byteBuffer.getInt();
      OpCode opCode = OpCode.computeValue_1234(i);
      if (opCode == null) {
         throw new IOException("Unknown opcode: " + i);
      }

      JsonObject jsonObject = null;

      try {
         JavassistComponent129.performAction_1245(j);
         if (j > 0) {
            ByteBuffer byteBuffer1 = ByteBuffer.allocate(j);
            this.performAction_0714(byteBuffer1);
            byteBuffer1.flip();
            byte[] b = new byte[j];
            byteBuffer1.get(b);
            jsonObject = JavassistComponent129.computeValue_1246(b);
         }

         return new JavassistComponent128(opCode, jsonObject);
      } catch (JavassistComponent129.NestedType328 nestedType328) {
         throw new IOException("Invalid frame payload", nestedType328);
      }
   }

   @Override
   public void performAction_0713(JavassistComponent128 javassistComponent128) throws IOException {
      this.performAction_0710();
      this.reentrantLockField2.lock();

      try {
         this.performAction_0710();
         byte[] b = javassistComponent128.computeValue_1242();
         ByteBuffer byteBuffer = ByteBuffer.wrap(b);

         while (byteBuffer.hasRemaining()) {
            this.socketChannelField3.write(byteBuffer);
         }

         this.reentrantLockField2.unlock();
      } catch (Throwable throwable) {
         this.reentrantLockField2.unlock();
         throw throwable;
      }
   }

   @Override
   public void close() throws IOException {
      this.flagField4 = true;
      this.socketChannelField3.close();
      log4jComponent144Field1.performAction_1316("Unix connection closed");
   }

   private void performAction_0714(ByteBuffer byteBuffer) throws IOException {
      while (byteBuffer.hasRemaining()) {
         int i = this.socketChannelField3.read(byteBuffer);
         if (i == -1) {
            throw new EOFException("Unexpected end of stream, needed " + byteBuffer.remaining() + " more bytes");
         }

         if (i == 0) {
            Thread.yield();
         }
      }
   }

   private void performAction_0710() throws IOException {
      if (this.flagField4 || !this.socketChannelField3.isOpen()) {
         throw new IOException("Connection is closed");
      }
   }
}
