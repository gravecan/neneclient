package aethereal.module.client;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.setting.StringSetting;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@ModuleRegister(name = "Discord Presence", description = "Shows NeNe Client as your Discord activity", category = Category.Client)
public final class DiscordPresence extends Module {
   private final StringSetting applicationId = new StringSetting("Discord App ID", "");
   private final AtomicBoolean running = new AtomicBoolean();
   private ScheduledExecutorService worker;

   public DiscordPresence() {
      this.addSettings(this.applicationId);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.running.set(true);
      this.worker = Executors.newSingleThreadScheduledExecutor(runnable -> {
         Thread thread = new Thread(runnable, "NeNe-Discord-Presence");
         thread.setDaemon(true);
         return thread;
      });
      this.worker.scheduleWithFixedDelay(this::performAction_1455, 0L, 15L, TimeUnit.SECONDS);
   }

   @Override
   public void onDisable() {
      this.running.set(false);
      if (this.worker != null) {
         this.worker.shutdownNow();
      }

      this.worker = null;
      super.onDisable();
   }

   private void performAction_1455() {
      String string = this.applicationId.getCurrentValue() == null ? "" : this.applicationId.getCurrentValue().trim();
      if (this.running.get() && !string.isEmpty()) {
         for (int i = 0; i < 10 && this.running.get(); i++) {
            try (DiscordPresence.NestedType336 nestedType336 = computeValue_1456(i)) {
               if (nestedType336 != null) {
                  nestedType336.performAction_1459(0, "{\"v\":1,\"client_id\":\"" + computeValue_1457(string) + "\"}");
                  nestedType336.performAction_1460();
                  nestedType336.performAction_1459(
                     1,
                     "{\"cmd\":\"SET_ACTIVITY\",\"args\":{\"pid\":"
                        + ProcessHandle.current().pid()
                        + ",\"activity\":{\"details\":\"Playing NeNe Client\",\"state\":\"Minecraft\",\"type\":0}},\"nonce\":\"nene-1\"}"
                  );
                  nestedType336.performAction_1460();
                  return;
               }
            } catch (Exception exception) {
            }
         }
      }
   }

   private static DiscordPresence.NestedType336 computeValue_1456(int i) throws IOException {
      String string = System.getProperty("os.name", "").toLowerCase();
      if (string.contains("win")) {
         Path path1 = Paths.get("\\\\.\\pipe\\discord-ipc-" + i);
         if (!Files.exists(path1)) {
            return null;
         }

         FileChannel fileChannel = FileChannel.open(path1, StandardOpenOption.READ, StandardOpenOption.WRITE);
         return new DiscordPresence.NestedType336(fileChannel, fileChannel);
      } else {
         Path path = Paths.get(System.getenv().getOrDefault("XDG_RUNTIME_DIR", "/tmp"), "discord-ipc-" + i);
         SocketChannel socketChannel = SocketChannel.open(StandardProtocolFamily.UNIX);
         socketChannel.connect(UnixDomainSocketAddress.of(path));
         return new DiscordPresence.NestedType336(socketChannel, socketChannel);
      }
   }

   private static String computeValue_1457(String string) {
      return string.replace("\\", "\\\\").replace("\"", "\\\"");
   }

   private record NestedType336(ReadableByteChannel input, WritableByteChannel output) implements AutoCloseable {
      void performAction_1459(int i, String string) throws IOException {
         byte[] b = string.getBytes(StandardCharsets.UTF_8);
         ByteBuffer byteBuffer = ByteBuffer.allocate(8 + b.length).order(ByteOrder.LITTLE_ENDIAN);
         byteBuffer.putInt(b.length).putInt(i).put(b).flip();

         while (byteBuffer.hasRemaining()) {
            this.output.write(byteBuffer);
         }
      }

      void performAction_1460() throws IOException {
         ByteBuffer byteBuffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
         this.performAction_1458(byteBuffer);
         byteBuffer.flip();
         int i = byteBuffer.getInt();
         byteBuffer.getInt();
         if (i >= 0 && i <= 1048576) {
            this.performAction_1458(ByteBuffer.allocate(i));
         } else {
            throw new IOException("Invalid Discord IPC frame");
         }
      }

      private void performAction_1458(ByteBuffer byteBuffer) throws IOException {
         while (byteBuffer.hasRemaining()) {
            if (this.input.read(byteBuffer) < 0) {
               throw new IOException("Discord IPC closed");
            }
         }
      }

      @Override
      public void close() throws IOException {
         this.input.close();
      }
   }
}
