package aethereal.network;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkComponent178 extends ClassLoader {
   private String host;
   private int integerField1;

   public static void performAction_2492(String[] strings) throws Throwable {
      if (strings.length >= 3) {
         NetworkComponent178 networkComponent178 = new NetworkComponent178(strings[0], Integer.parseInt(strings[1]));
         String[] strings1 = new String[strings.length - 3];
         System.arraycopy(strings, 3, strings1, 0, strings.length - 3);
         networkComponent178.performAction_2491(strings[2], strings1);
      } else {
         System.err.println("Usage: java javassist.tools.web.Viewer <host> <port> class [args ...]");
      }
   }

   public NetworkComponent178(String string, int i) {
      this.host = string;
      this.integerField1 = i;
   }

   public String getHost_2488() {
      return this.host;
   }

   public int getIntegerField1_2487() {
      return this.integerField1;
   }

   public void performAction_2491(String string, String[] strings) throws Throwable {
      Class class_ = this.loadClass(string);

      try {
         class_.getDeclaredMethod("main", String[].class).invoke(null, strings);
      } catch (InvocationTargetException invocationTargetException) {
         throw invocationTargetException.getTargetException();
      }
   }

   @Override
   protected synchronized Class<?> loadClass(String string, boolean bl) throws ClassNotFoundException {
      Class class_ = this.findLoadedClass(string);
      if (class_ == null) {
         class_ = this.findClass(string);
      }

      if (class_ == null) {
         throw new ClassNotFoundException(string);
      }

      if (bl) {
         this.resolveClass(class_);
      }

      return class_;
   }

   @Override
   protected Class<?> findClass(String string) throws ClassNotFoundException {
      Class class_ = null;
      if (string.startsWith("java.") || string.startsWith("javax.") || string.equals("javassist.tools.web.Viewer")) {
         class_ = this.findSystemClass(string);
      }

      if (class_ == null) {
         try {
            byte[] b = this.computeValue_2490(string);
            if (b != null) {
               class_ = this.defineClass(string, b, 0, b.length);
            }
         } catch (Exception exception) {
         }
      }

      return class_;
   }

   protected byte[] computeValue_2490(String string) throws Exception {
      URL uRL = new URL("http", this.host, this.integerField1, "/" + string.replace('.', '/') + ".class");
      URLConnection uRLConnection = uRL.openConnection();
      uRLConnection.connect();
      int i = uRLConnection.getContentLength();
      InputStream inputStream = uRLConnection.getInputStream();
      byte[] b;
      if (i <= 0) {
         b = this.computeValue_2489(inputStream);
      } else {
         b = new byte[i];
         int j = 0;

         do {
            int k = inputStream.read(b, j, i - j);
            if (k < 0) {
               inputStream.close();
               throw new IOException("the stream was closed: " + string);
            }

            j += k;
         } while (j < i);
      }

      inputStream.close();
      return b;
   }

   private byte[] computeValue_2489(InputStream inputStream) throws IOException {
      byte[] b = new byte[8192];
      int i = 0;
      int j = 0;

      do {
         i += j;
         if (b.length - i <= 0) {
            byte[] c = new byte[b.length * 2];
            System.arraycopy(b, 0, c, 0, i);
            b = c;
         }

         j = inputStream.read(b, i, b.length - i);
      } while (j >= 0);

      byte[] d = new byte[i];
      System.arraycopy(b, 0, d, 0, i);
      return d;
   }
}
