package aethereal.discord;

import aethereal.core.NeneClient;
import aethereal.core.UserProfile;
import java.util.Arrays;

public final class DiscordComponent065 {
   private static final byte[][] source = new byte[7][];
   private static long tailQword;
   private static final String[] cache = new String[6];
   private static volatile boolean[] initialized = new boolean[6];
   private static final int[] getterToSlot = new int[]{2, 0, 3, 1, 4, 6};
   private static final Object updateLock = new Object();

   private DiscordComponent065() {
   }

   public static void performAction_0929(byte[][] b, long l) {
      synchronized (updateLock) {
         byte[][] c = source;
         byte[] d = b[0];
         c[0] = Arrays.copyOf(d, d.length);
         byte[][] e = source;
         byte[] f = b[1];
         e[1] = Arrays.copyOf(f, f.length);
         byte[][] g = source;
         byte[] h = b[2];
         g[2] = Arrays.copyOf(h, h.length);
         byte[][] i = source;
         byte[] j = b[3];
         i[3] = Arrays.copyOf(j, j.length);
         byte[][] k = source;
         byte[] m = b[4];
         k[4] = Arrays.copyOf(m, m.length);
         byte[][] n = source;
         byte[] o = b[5];
         n[5] = Arrays.copyOf(o, o.length);
         byte[][] p = source;
         byte[] q = b[6];
         p[6] = Arrays.copyOf(q, q.length);
         tailQword = l;
      }
   }

   public static String computeValue_0924() {
      return initialized[0] ? cache[0] : computeValue_0922(0);
   }

   public static String computeValue_0925() {
      return initialized[1] ? cache[1] : computeValue_0922(1);
   }

   public static String computeValue_0930() {
      return initialized[2] ? cache[2] : computeValue_0922(2);
   }

   public static String computeValue_0926() {
      return initialized[3] ? cache[3] : computeValue_0922(3);
   }

   public static String computeValue_0931() {
      return initialized[4] ? cache[4] : computeValue_0922(4);
   }

   public static String computeValue_0932() {
      return initialized[5] ? cache[5] : computeValue_0922(5);
   }

   private static synchronized String computeValue_0922(int i) {
      if (!initialized[i]) {
         cache[i] = computeValue_0928(source[getterToSlot[i]]);
         initialized[i] = true;
         initialized = initialized;
      }

      return cache[i];
   }

   private static String computeValue_0928(byte[] b) {
      char[] c = new char[b.length];
      int i = 0;
      int j = 0;

      while (i < b.length && b[i] != 0) {
         int k = i++;
         int l = b[k] & 255;
         if (l > 127) {
            if ((l & 224) == 192) {
               if (i < b.length) {
                  int r = b[++i] & 255;
                  if ((r & 192) == 128 && l != 193 && (l != 192 || r == 128)) {
                     int s = j++;
                     c[s] = (char)((l & 31) << 6 | r & 63);
                  }
               }

               throw computeValue_0927();
            }

            if ((l & 240) == 224 && i + 1 < b.length) {
               int q = i + 1;
               int n = b[i] & 255;
               i = q + 1;
               int o = b[q] & 255;
               if ((n & 192) == 128 && (o & 192) == 128 && (l != 224 || n >= 160)) {
                  int p = j++;
                  c[p] = (char)((l & 15) << 12 | (n & 63) << 6 | o & 63);
               }
            }

            throw computeValue_0927();
         }

         int m = j++;
         c[m] = (char)l;
      }

      return new String(c, 0, j);
   }

   private static IllegalArgumentException computeValue_0927() {
      return new IllegalArgumentException("invalid modified UTF-8");
   }

   public static void performAction_0923(byte[][] b, long l) {
      computeValue_0928(b[5]);
      UserProfile userProfile = new UserProfile(
         computeValue_0928(b[2]), computeValue_0928(b[0]), computeValue_0928(b[3]), computeValue_0928(b[1]), computeValue_0928(b[4]), computeValue_0928(b[6])
      );
      performAction_0929(b, l);
      NeneClient.updateActiveUser(userProfile);
   }

   static {
      source[0] = new byte[0];
      source[1] = new byte[0];
      source[2] = new byte[0];
      source[3] = new byte[0];
      source[4] = new byte[0];
      source[5] = new byte[0];
      source[6] = new byte[0];
   }
}
