package aethereal.discord;

import aethereal.lib.javassist.JavassistComponent128;
import java.io.IOException;
import java.io.OutputStream;

final class DiscordComponent062 {
   private DiscordComponent062() {
   }

   static void performAction_0893(OutputStream outputStream, JavassistComponent128 javassistComponent128) throws IOException {
      outputStream.write(javassistComponent128.computeValue_1242());
      outputStream.flush();
   }
}
