package aethereal.network;

import aethereal.lib.javassist.JavassistComponent122;
import aethereal.lib.javassist.JavassistComponent123;
import aethereal.lib.javassist.JavassistComponent132;
import aethereal.lib.javassist.JavassistContract134;
import aethereal.lib.websocket.WebsocketComponent153;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javassist.CtClass;

public class NetworkComponent180 {
   private ServerSocket serverSocketField1;
   private JavassistComponent123 javassistComponent123Field2;
   protected JavassistContract134 javassistContract134Field3;
   private static final byte[] byteValueArrayField4 = new byte[]{13, 10};
   private static final int integerField5 = 1;
   private static final int integerField6 = 2;
   private static final int integerField7 = 3;
   private static final int integerField8 = 4;
   private static final int integerField9 = 5;
   public String stringField10 = null;
   public String stringField11 = null;

   public static void performAction_2506(String[] strings) throws IOException {
      if (strings.length == 1) {
         NetworkComponent180 networkComponent180 = new NetworkComponent180(strings[0]);
         networkComponent180.performAction_2493();
      } else {
         System.err.println("Usage: java javassist.tools.web.Webserver <port number>");
      }
   }

   public NetworkComponent180(String string) throws IOException {
      this(Integer.parseInt(string));
   }

   public NetworkComponent180(int i) throws IOException {
      this.serverSocketField1 = new ServerSocket(i);
      this.javassistComponent123Field2 = null;
      this.javassistContract134Field3 = null;
   }

   public void setJavassistComponent123Field2_2495(JavassistComponent123 javassistComponent123) {
      this.javassistComponent123Field2 = javassistComponent123;
   }

   public void performAction_2496(JavassistComponent123 javassistComponent123, JavassistContract134 javassistContract134) throws JavassistComponent122, JavassistComponent132 {
      this.javassistComponent123Field2 = javassistComponent123;
      this.javassistContract134Field3 = javassistContract134;
      javassistContract134.performAction_1247(this.javassistComponent123Field2);
   }

   public void getServerSocketField1_2509() throws IOException {
      this.serverSocketField1.close();
   }

   public void getOut_2507(String string) {
      System.out.println(string);
   }

   public void performAction_2508(String string, String string1) {
      System.out.print(string);
      System.out.print("");
      System.out.println(string1);
   }

   public void performAction_2504(String string, String string1, String string2) {
      System.out.print(string);
      System.out.print("");
      System.out.print(string1);
      System.out.print("");
      System.out.println(string2);
   }

   public void performAction_2494(String string) {
      System.out.print("    ");
      System.out.println(string);
   }

   public void performAction_2493() {
      System.err.println("ready to service...");

      while (true) {
         try {
            NetworkComponent179 networkComponent179 = new NetworkComponent179(this, this.serverSocketField1.accept());
            networkComponent179.start();
         } catch (IOException iOException) {
            this.getOut_2507(iOException.toString());
         }
      }
   }

   final void performAction_2505(Socket socket) throws IOException {
      BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
      String string = this.computeValue_2498(bufferedInputStream);
      this.performAction_2504(socket.getInetAddress().getHostName(), new Date().toString(), string);

      while (this.computeValue_2497(bufferedInputStream) > 0) {
      }

      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

      try {
         this.performAction_2499(bufferedInputStream, bufferedOutputStream, string);
      } catch (WebsocketComponent153 websocketComponent153) {
         this.performAction_2501(bufferedOutputStream, websocketComponent153);
      }

      bufferedOutputStream.flush();
      bufferedInputStream.close();
      bufferedOutputStream.close();
      socket.close();
   }

   private String computeValue_2498(InputStream inputStream) throws IOException {
      StringBuffer stringBuffer = new StringBuffer();

      while (true) {
         int i = inputStream.read();
         if (i < 0 || i == 13) {
            inputStream.read();
            return stringBuffer.toString();
         }

         stringBuffer.append((char)i);
      }
   }

   private int computeValue_2497(InputStream inputStream) throws IOException {
      int i = 0;

      while (true) {
         int j = inputStream.read();
         if (j < 0 || j == 13) {
            inputStream.read();
            return i;
         }

         i++;
      }
   }

   public void performAction_2499(InputStream inputStream1, OutputStream outputStream, String string) throws WebsocketComponent153, IOException {
      if (!string.startsWith("GET /")) {
         throw new WebsocketComponent153();
      }

      String string1 = string.substring(5, string.indexOf(32, 5));
      String string2 = string1;
      byte b;
      if (string2.endsWith(".class")) {
         b = 2;
      } else if (string2.endsWith(".html") || string2.endsWith(".htm")) {
         b = 1;
      } else if (string2.endsWith(".gif")) {
         b = 3;
      } else if (string2.endsWith(".jpg")) {
         b = 4;
      } else {
         b = 5;
      }

      int i = string2.length();
      if (b != 2 || !this.checkCondition_2502(outputStream, string2, i)) {
         this.performAction_2503(string2, i);
         if (this.stringField11 != null) {
            string2 = this.stringField11 + string2;
         }

         if (File.separatorChar != '/') {
            string2 = string2.replace('/', File.separatorChar);
         }

         File file = new File(string2);
         if (file.canRead()) {
            this.performAction_2500(outputStream, file.length(), b);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] e = new byte[8192];

            while (true) {
               int k = fileInputStream.read(e);
               if (k <= 0) {
                  fileInputStream.close();
                  return;
               }

               outputStream.write(e, 0, k);
            }
         } else {
            InputStream inputStream;
            if (b == 2 && (inputStream = this.getClass().getResourceAsStream("/" + string1)) != null) {
               ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
               byte[] c = new byte[8192];

               while (true) {
                  int j = inputStream.read(c);
                  if (j <= 0) {
                     byte[] d = byteArrayOutputStream.toByteArray();
                     this.performAction_2500(outputStream, d.length, 2);
                     outputStream.write(d);
                     inputStream.close();
                     return;
                  }

                  byteArrayOutputStream.write(c, 0, j);
               }
            } else {
               throw new WebsocketComponent153();
            }
         }
      }
   }

   private void performAction_2503(String string, int i) throws WebsocketComponent153 {
      for (int j = 0; j < i; j++) {
         char c = string.charAt(j);
         if (!Character.isJavaIdentifierPart(c) && c != '.' && c != '/') {
            throw new WebsocketComponent153();
         }
      }

      if (string.indexOf("..") >= 0) {
         throw new WebsocketComponent153();
      }
   }

   private boolean checkCondition_2502(OutputStream outputStream, String string, int i) throws IOException, WebsocketComponent153 {
      if (this.javassistComponent123Field2 == null) {
         return false;
      }

      String string1 = string.substring(0, i - 6).replace('/', '.');

      try {
         if (this.javassistContract134Field3 != null) {
            this.javassistContract134Field3.performAction_1248(this.javassistComponent123Field2, string1);
         }

         CtClass ctClass = this.javassistComponent123Field2.computeValue_1235(string1);
         byte[] b = ctClass.toBytecode();
         if (this.stringField10 != null) {
            ctClass.detach();
         }

         this.performAction_2500(outputStream, b.length, 2);
         outputStream.write(b);
         return true;
      } catch (Exception exception) {
         throw new WebsocketComponent153(exception);
      }
   }

   private void performAction_2500(OutputStream outputStream, long l, int i) throws IOException {
      outputStream.write("HTTP/1.0 200 OK".getBytes());
      outputStream.write(byteValueArrayField4);
      outputStream.write("Content-Length: ".getBytes());
      outputStream.write(Long.toString(l).getBytes());
      outputStream.write(byteValueArrayField4);
      if (i == 2) {
         outputStream.write("Content-Type: application/octet-stream".getBytes());
      } else if (i == 1) {
         outputStream.write("Content-Type: text/html".getBytes());
      } else if (i == 3) {
         outputStream.write("Content-Type: image/gif".getBytes());
      } else if (i == 4) {
         outputStream.write("Content-Type: image/jpg".getBytes());
      } else if (i == 5) {
         outputStream.write("Content-Type: text/plain".getBytes());
      }

      outputStream.write(byteValueArrayField4);
      outputStream.write(byteValueArrayField4);
   }

   private void performAction_2501(OutputStream outputStream, WebsocketComponent153 websocketComponent153) throws IOException {
      this.performAction_2494("bad request: " + websocketComponent153.toString());
      outputStream.write("HTTP/1.0 400 Bad Request".getBytes());
      outputStream.write(byteValueArrayField4);
      outputStream.write(byteValueArrayField4);
      outputStream.write("<H1>Bad Request</H1>".getBytes());
   }
}
