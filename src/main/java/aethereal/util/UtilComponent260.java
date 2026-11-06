package aethereal.util;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;

public class UtilComponent260 {
   public static final int integerField1 = 0;
   public static final int integerField2 = 1;
   public static final int integerField3 = 2;
   public static final int integerField4 = 8;
   public static final int integerField5 = 16;
   public static final int integerField6 = 32;
   private static final int integerField7 = 76;
   private static final byte byteValueField8 = 61;
   private static final byte byteValueField9 = 10;
   private static final String stringField10 = "US-ASCII";
   private static final byte byteValueField11 = -5;
   private static final byte[] byteValueArrayField12 = new byte[]{
      65,
      66,
      67,
      68,
      69,
      70,
      71,
      72,
      73,
      74,
      75,
      76,
      77,
      78,
      79,
      80,
      81,
      82,
      83,
      84,
      85,
      86,
      87,
      88,
      89,
      90,
      97,
      98,
      99,
      100,
      101,
      102,
      103,
      104,
      105,
      106,
      107,
      108,
      109,
      110,
      111,
      112,
      113,
      114,
      115,
      116,
      117,
      118,
      119,
      120,
      121,
      122,
      48,
      49,
      50,
      51,
      52,
      53,
      54,
      55,
      56,
      57,
      43,
      47
   };
   private static final byte[] byteValueArrayField13 = new byte[]{
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -5,
      -5,
      -9,
      -9,
      -5,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -5,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      62,
      -9,
      -9,
      -9,
      63,
      52,
      53,
      54,
      55,
      56,
      57,
      58,
      59,
      60,
      61,
      -9,
      -9,
      -9,
      -1,
      -9,
      -9,
      -9,
      0,
      1,
      2,
      3,
      4,
      5,
      6,
      7,
      8,
      9,
      10,
      11,
      12,
      13,
      14,
      15,
      16,
      17,
      18,
      19,
      20,
      21,
      22,
      23,
      24,
      25,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      26,
      27,
      28,
      29,
      30,
      31,
      32,
      33,
      34,
      35,
      36,
      37,
      38,
      39,
      40,
      41,
      42,
      43,
      44,
      45,
      46,
      47,
      48,
      49,
      50,
      51,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9
   };
   private static final byte[] byteValueArrayField14 = new byte[]{
      65,
      66,
      67,
      68,
      69,
      70,
      71,
      72,
      73,
      74,
      75,
      76,
      77,
      78,
      79,
      80,
      81,
      82,
      83,
      84,
      85,
      86,
      87,
      88,
      89,
      90,
      97,
      98,
      99,
      100,
      101,
      102,
      103,
      104,
      105,
      106,
      107,
      108,
      109,
      110,
      111,
      112,
      113,
      114,
      115,
      116,
      117,
      118,
      119,
      120,
      121,
      122,
      48,
      49,
      50,
      51,
      52,
      53,
      54,
      55,
      56,
      57,
      45,
      95
   };
   private static final byte[] byteValueArrayField15 = new byte[]{
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -5,
      -5,
      -9,
      -9,
      -5,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -5,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      62,
      -9,
      -9,
      52,
      53,
      54,
      55,
      56,
      57,
      58,
      59,
      60,
      61,
      -9,
      -9,
      -9,
      -1,
      -9,
      -9,
      -9,
      0,
      1,
      2,
      3,
      4,
      5,
      6,
      7,
      8,
      9,
      10,
      11,
      12,
      13,
      14,
      15,
      16,
      17,
      18,
      19,
      20,
      21,
      22,
      23,
      24,
      25,
      -9,
      -9,
      -9,
      -9,
      63,
      -9,
      26,
      27,
      28,
      29,
      30,
      31,
      32,
      33,
      34,
      35,
      36,
      37,
      38,
      39,
      40,
      41,
      42,
      43,
      44,
      45,
      46,
      47,
      48,
      49,
      50,
      51,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9
   };
   private static final byte[] byteValueArrayField16 = new byte[]{
      45,
      48,
      49,
      50,
      51,
      52,
      53,
      54,
      55,
      56,
      57,
      65,
      66,
      67,
      68,
      69,
      70,
      71,
      72,
      73,
      74,
      75,
      76,
      77,
      78,
      79,
      80,
      81,
      82,
      83,
      84,
      85,
      86,
      87,
      88,
      89,
      90,
      95,
      97,
      98,
      99,
      100,
      101,
      102,
      103,
      104,
      105,
      106,
      107,
      108,
      109,
      110,
      111,
      112,
      113,
      114,
      115,
      116,
      117,
      118,
      119,
      120,
      121,
      122
   };
   private static final byte[] byteValueArrayField17 = new byte[]{
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -5,
      -5,
      -9,
      -9,
      -5,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -5,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      0,
      -9,
      -9,
      1,
      2,
      3,
      4,
      5,
      6,
      7,
      8,
      9,
      10,
      -9,
      -9,
      -9,
      -1,
      -9,
      -9,
      -9,
      11,
      12,
      13,
      14,
      15,
      16,
      17,
      18,
      19,
      20,
      21,
      22,
      23,
      24,
      25,
      26,
      27,
      28,
      29,
      30,
      31,
      32,
      33,
      34,
      35,
      36,
      -9,
      -9,
      -9,
      -9,
      37,
      -9,
      38,
      39,
      40,
      41,
      42,
      43,
      44,
      45,
      46,
      47,
      48,
      49,
      50,
      51,
      52,
      53,
      54,
      55,
      56,
      57,
      58,
      59,
      60,
      61,
      62,
      63,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9,
      -9
   };
   static final boolean flagField18 = !UtilComponent260.class.desiredAssertionStatus();

   private static final byte[] computeValue_3494(int i) {
      if ((i & 16) == 16) {
         return byteValueArrayField14;
      } else {
         return (i & 32) == 32 ? byteValueArrayField16 : byteValueArrayField12;
      }
   }

   public static final byte[] computeValue_3492(int i) {
      if ((i & 16) == 16) {
         return byteValueArrayField15;
      } else {
         return (i & 32) == 32 ? byteValueArrayField17 : byteValueArrayField13;
      }
   }

   private UtilComponent260() {
   }

   public static byte[] computeValue_3499(byte[] b, byte[] c, int i, int j) {
      computeValue_3497(c, 0, i, b, 0, j);
      return b;
   }

   private static byte[] computeValue_3497(byte[] b, int i, int j, byte[] c, int k, int l) {
      byte[] d = computeValue_3494(l);
      int m = (j > 0 ? b[i] << 24 >>> 8 : 0) | (j > 1 ? b[i + 1] << 24 >>> 16 : 0) | (j > 2 ? b[i + 2] << 24 >>> 24 : 0);
      switch (j) {
         case 1:
            c[k] = d[m >>> 18];
            c[k + 1] = d[m >>> 12 & 63];
            c[k + 2] = 61;
            c[k + 3] = 61;
            break;
         case 2:
            c[k] = d[m >>> 18];
            c[k + 1] = d[m >>> 12 & 63];
            c[k + 2] = d[m >>> 6 & 63];
            c[k + 3] = 61;
            break;
         case 3:
            c[k] = d[m >>> 18];
            c[k + 1] = d[m >>> 12 & 63];
            c[k + 2] = d[m >>> 6 & 63];
            c[k + 3] = d[m & 63];
      }

      return c;
   }

   public static String computeValue_3493(byte[] b) {
      String string = null;

      try {
         string = computeValue_3495(b, 0, b.length, 0);
      } catch (IOException iOException) {
         assert false : iOException.getMessage();
      }

      assert string != null;
      return string;
   }

   public static String computeValue_3495(byte[] b, int i, int j, int k) throws IOException {
      byte[] c = computeValue_3496(b, i, j, k);

      try {
         return new String(c, "US-ASCII");
      } catch (UnsupportedEncodingException unsupportedEncodingException) {
         return new String(c);
      }
   }

   public static byte[] computeValue_3496(byte[] b, int i, int j, int k) throws IOException {
      if (b == null) {
         throw new IllegalArgumentException("Cannot serialize a null array.");
      }

      if (i < 0) {
         throw new IllegalArgumentException("Cannot have negative offset: " + i);
      }

      if (j < 0) {
         throw new IllegalArgumentException("Cannot have length offset: " + j);
      }

      if (i + j > b.length) {
         throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", i, j, b.length));
      }

      if ((k & 2) != 0) {
         ByteArrayOutputStream byteArrayOutputStream = null;
         GZIPOutputStream gZIPOutputStream = null;
         UtilComponent260.NestedType407 nestedType407 = null;

         try {
            try {
               byteArrayOutputStream = new ByteArrayOutputStream();
               nestedType407 = new UtilComponent260.NestedType407(byteArrayOutputStream, 1 | k);
               gZIPOutputStream = new GZIPOutputStream(nestedType407);
               gZIPOutputStream.write(b, i, j);
               gZIPOutputStream.close();
               if (gZIPOutputStream != null) {
                  try {
                     gZIPOutputStream.close();
                  } catch (Exception exception3) {
                  }
               }

               if (nestedType407 != null) {
                  try {
                     nestedType407.close();
                  } catch (Exception exception2) {
                  }
               }

               if (byteArrayOutputStream != null) {
                  try {
                     byteArrayOutputStream.close();
                  } catch (Exception exception1) {
                  }
               }

               return byteArrayOutputStream.toByteArray();
            } catch (Throwable throwable) {
               if (gZIPOutputStream != null) {
                  try {
                     gZIPOutputStream.close();
                  } catch (Exception exception5) {
                     if (nestedType407 != null) {
                        try {
                           nestedType407.close();
                        } catch (Exception exception4) {
                           if (byteArrayOutputStream != null) {
                              try {
                                 byteArrayOutputStream.close();
                              } catch (Exception exception) {
                                 throw throwable;
                              }
                           }

                           throw throwable;
                        }
                     }

                     if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                     }

                     throw throwable;
                  }
               }

               if (nestedType407 != null) {
                  nestedType407.close();
               }

               if (byteArrayOutputStream != null) {
                  byteArrayOutputStream.close();
               }

               throw throwable;
            }
         } catch (IOException iOException) {
            throw iOException;
         }
      } else {
         boolean bl = (k & 8) != 0;
         int l = j / 3 * 4 + (j % 3 > 0 ? 4 : 0);
         if (bl) {
            l += l / 76;
         }

         byte[] c = new byte[l];
         byte d = 0;
         int m = 0;
         int n = j - 2;
         byte e = 0;

         while (d < n) {
            computeValue_3497(b, d + i, 3, c, m, k);
            e += 4;
            if (bl && e >= 76) {
               c[m + 4] = 10;
               m++;
               e = 0;
            }

            d += 3;
            m += 4;
         }

         if (d < j) {
            computeValue_3497(b, d + i, j - d, c, m, k);
            m += 4;
         }

         if (m <= c.length - 1) {
            byte[] f = new byte[m];
            System.arraycopy(c, 0, f, 0, m);
            return f;
         } else {
            return c;
         }
      }
   }

   public static int computeValue_3498(byte[] b, int i, byte[] c, int j, int k) {
      if (b == null) {
         throw new IllegalArgumentException("Source array was null.");
      }

      if (c == null) {
         throw new IllegalArgumentException("Destination array was null.");
      }

      if (i < 0 || i + 3 >= b.length) {
         throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", b.length, i));
      }

      if (j >= 0 && j + 2 < c.length) {
         byte[] d = computeValue_3492(k);
         if (b[i + 2] == 61) {
            c[j] = (byte)(((d[b[i]] & 255) << 18 | (d[b[i + 1]] & 255) << 12) >>> 16);
            return 1;
         } else if (b[i + 3] == 61) {
            int m = (d[b[i]] & 255) << 18 | (d[b[i + 1]] & 255) << 12 | (d[b[i + 2]] & 255) << 6;
            c[j] = (byte)(m >>> 16);
            c[j + 1] = (byte)(m >>> 8);
            return 2;
         } else {
            int l = (d[b[i]] & 255) << 18 | (d[b[i + 1]] & 255) << 12 | (d[b[i + 2]] & 255) << 6 | d[b[i + 3]] & 255;
            c[j] = (byte)(l >> 16);
            c[j + 1] = (byte)(l >> 8);
            c[j + 2] = (byte)l;
            return 3;
         }
      } else {
         throw new IllegalArgumentException(
            String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", c.length, j)
         );
      }
   }

   public static class NestedType407 extends FilterOutputStream {
      private boolean flagField1;
      private int integerField2;
      private byte[] byteValueArrayField3;
      private int integerField4;
      private int integerField5;
      private boolean flagField6;
      private byte[] byteValueArrayField7;
      private boolean flagField8;
      private int options;
      private byte[] byteValueArrayField9;

      public NestedType407(OutputStream outputStream) {
         this(outputStream, 1);
      }

      public NestedType407(OutputStream outputStream, int i) {
         super(outputStream);
         this.flagField6 = (i & 8) != 0;
         this.flagField1 = (i & 1) != 0;
         this.integerField4 = this.flagField1 ? 3 : 4;
         this.byteValueArrayField3 = new byte[this.integerField4];
         this.integerField2 = 0;
         this.integerField5 = 0;
         this.flagField8 = false;
         this.byteValueArrayField7 = new byte[4];
         this.options = i;
         this.byteValueArrayField9 = UtilComponent260.computeValue_3492(i);
      }

      @Override
      public void write(int i) throws IOException {
         if (this.flagField8) {
            this.out.write(i);
         } else if (this.flagField1) {
            byte[] c = this.byteValueArrayField3;
            int l = this.integerField2++;
            c[l] = (byte)i;
            if (this.integerField2 >= this.integerField4) {
               this.out.write(UtilComponent260.computeValue_3499(this.byteValueArrayField7, this.byteValueArrayField3, this.integerField4, this.options));
               this.integerField5 += 4;
               if (this.flagField6 && this.integerField5 >= 76) {
                  this.out.write(10);
                  this.integerField5 = 0;
               }

               this.integerField2 = 0;
            }
         } else if (this.byteValueArrayField9[i & 127] > -5) {
            byte[] b = this.byteValueArrayField3;
            int j = this.integerField2++;
            b[j] = (byte)i;
            if (this.integerField2 >= this.integerField4) {
               int k = UtilComponent260.computeValue_3498(this.byteValueArrayField3, 0, this.byteValueArrayField7, 0, this.options);
               this.out.write(this.byteValueArrayField7, 0, k);
               this.integerField2 = 0;
            }
         } else {
            if (this.byteValueArrayField9[i & 127] != -5) {
               throw new IOException("Invalid character in Base64 data.");
            }
         }
      }

      @Override
      public void write(byte[] b, int i, int j) throws IOException {
         if (this.flagField8) {
            this.out.write(b, i, j);
         } else {
            for (int k = 0; k < j; k++) {
               this.write(b[i + k]);
            }
         }
      }

      public void performAction_3500() throws IOException {
         if (this.integerField2 > 0) {
            if (this.flagField1) {
               this.out.write(UtilComponent260.computeValue_3499(this.byteValueArrayField7, this.byteValueArrayField3, this.integerField2, this.options));
               this.integerField2 = 0;
            } else {
               throw new IOException("Base64 input not properly padded.");
            }
         }
      }

      @Override
      public void close() throws IOException {
         this.performAction_3500();
         super.close();
         this.byteValueArrayField3 = null;
         this.out = null;
      }
   }
}
