package aethereal.discord;

import aethereal.lib.javassist.JavassistComponent128;
import aethereal.lib.jsoup.JsoupContract137;
import aethereal.lib.log4j.Log4jComponent143;
import aethereal.lib.log4j.Log4jComponent144;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.locks.ReentrantLock;

public class DiscordComponent043 implements JsoupContract137 {
   private static final Log4jComponent144 log4jComponent144Field1 = Log4jComponent143.computeValue_1311(DiscordComponent043.class);
   private static final int integerField2 = 50;
   final RandomAccessFile randomAccessFileField3;
   volatile boolean flagField4;
   private final ReentrantLock reentrantLockField5 = new ReentrantLock();
   private final InputStream inputStreamField6 = new InputStream() {
      @Override
      public int read() throws IOException {
         byte[] b = new byte[1];
         int i = this.read(b, 0, 1);
         return i == -1 ? -1 : b[0] & 0xFF;
      }

      @Override
      public int read(byte[] b, int i, int j) throws IOException {
         if (b == null) {
            throw new NullPointerException("b is marked non-null but is null");
         }

         if (j == 0) {
            return 0;
         }

         while (!DiscordComponent043.this.flagField4) {
            try {
               long l = DiscordComponent043.this.randomAccessFileField3.length();
               if (l > 0L) {
                  return DiscordComponent043.this.randomAccessFileField3.read(b, i, j);
               }

               try {
                  Thread.sleep(50L);
               } catch (InterruptedException interruptedException) {
                  Thread.currentThread().interrupt();
                  throw new InterruptedIOException("Read interrupted");
               }
            } catch (IOException iOException) {
               if (DiscordComponent043.this.flagField4) {
                  return -1;
               }

               throw iOException;
            }
         }

         return -1;
      }
   };
   private final OutputStream outputStreamField7 = new OutputStream() {
      @Override
      public void write(int i) throws IOException {
         DiscordComponent043.this.randomAccessFileField3.write(i);
      }

      @Override
      public void write(byte[] b, int i, int j) throws IOException {
         if (b == null) {
            throw new NullPointerException("b is marked non-null but is null");
         }

         DiscordComponent043.this.randomAccessFileField3.write(b, i, j);
      }
   };

   public DiscordComponent043(String string) throws IOException {
      this.randomAccessFileField3 = new RandomAccessFile(string, "rw");
      log4jComponent144Field1.performAction_1316("Connected to Windows pipe: {}", string);
   }

   @Override
   public boolean checkCondition_0711() {
      return !this.flagField4;
   }

   @Override
   public JavassistComponent128 computeValue_0712() throws IOException {
      return DiscordComponent061.computeValue_0892(this.inputStreamField6);
   }

   @Override
   public void performAction_0713(JavassistComponent128 javassistComponent128) throws IOException {
      this.performAction_0715();
      this.reentrantLockField5.lock();

      try {
         this.performAction_0715();
         DiscordComponent062.performAction_0893(this.outputStreamField7, javassistComponent128);
      } finally {
         this.reentrantLockField5.unlock();
      }
   }

   @Override
   public void close() throws IOException {
      if (!this.flagField4) {
         this.flagField4 = true;
         this.randomAccessFileField3.close();
         log4jComponent144Field1.performAction_1316("Windows connection closed");
      }
   }

   private void performAction_0715() throws IOException {
      if (this.flagField4) {
         throw new IOException("Connection is closed");
      }
   }
}
