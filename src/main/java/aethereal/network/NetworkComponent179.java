package aethereal.network;

import java.io.IOException;
import java.net.Socket;

public class NetworkComponent179 extends Thread {
   NetworkComponent180 networkComponent180Field1;
   Socket socketField2;

   public NetworkComponent179(NetworkComponent180 networkComponent180, Socket socket) {
      this.networkComponent180Field1 = networkComponent180;
      this.socketField2 = socket;
   }

   @Override
   public void run() {
      try {
         this.networkComponent180Field1.performAction_2505(this.socketField2);
      } catch (IOException iOException) {
      }
   }
}
