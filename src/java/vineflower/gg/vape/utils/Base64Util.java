package gg.vape.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {
   private static boolean decodeTableInitialized;
   private static int[] decodeTable;
   private static final char[] BASE64_ALPHABET;

   public static String encodeBase64(byte[] byArray) {
      int n = byArray.length;
      byte[] byArray2 = new byte[byArray.length * 4];
      int n2 = 0;
      int n3 = -6;
      int n4 = 0;

      for (int i = 0; i < n; i++) {
         n2 = (n2 << 8) + byArray[i];

         for (n3 += 8; n3 >= 0; n3 -= 6) {
            byArray2[n4++] = (byte)BASE64_ALPHABET[n2 >> n3 & 63];
         }
      }

      if (n3 > -6) {
         byArray2[n4++] = (byte)BASE64_ALPHABET[n2 << 8 >> n3 + 8 & 63];
      }

      while (n4 % 4 != 0) {
         byArray2[n4++] = 61;
      }

      byArray2[n4] = 0;
      byte[] byArray3 = new byte[n4];
      System.arraycopy(byArray2, 0, byArray3, 0, n4);
      return new String(byArray3);
   }

   public static byte[] decodeBase64(String string) {
      byte[] byArray = new byte[string.length() + 1];
      int n2 = 0;
      if (!decodeTableInitialized) {
         decodeTable = new int[256];

         for (int n = 0; n < 256; n++) {
            decodeTable[n] = -1;
         }

         int var7 = 0;

         while (var7 < 64) {
            decodeTable[BASE64_ALPHABET[var7]] = var7++;
         }

         decodeTableInitialized = true;
      }

      int n = 0;
      int n3 = -8;

      char c;
      for (int i = 0; i < string.length() && decodeTable[c = string.charAt(i)] != -1; i++) {
         n = (n << 6) + decodeTable[c];
         n3 += 6;
         if (n3 >= 0) {
            byArray[n2++] = (byte)(n >> n3 & 0xFF);
            n3 -= 8;
         }
      }

      byArray[n2] = 0;
      return byArray;
   }

   public static String encodeUtf8Base64(String string) {
      return new String(Base64.getEncoder().encode(string.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
   }

   public static String decodeUtf8Base64(String string) {
      return new String(Base64.getDecoder().decode(string.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
   }

   private static String decodeUtf8Bytes(byte[] bytes) {
      int outputLength = 0;
      int inputLength = bytes.length;
      char[] characters = new char[inputLength];

      for (int index = 0; index < inputLength; index++) {
         int currentByte = 255 & bytes[index];
         if (currentByte < 192) {
            characters[outputLength++] = (char)currentByte;
         } else if (currentByte < 224) {
            char character = (char)((char)(currentByte & 31) << 6);
            int var12 = bytes[++index];
            character = (char)(character | (char)(var12 & 63));
            characters[outputLength++] = character;
         } else if (index < inputLength - 2) {
            char character = (char)((char)(currentByte & 15) << '\f');
            int var13 = bytes[++index];
            character = (char)(character | (char)(var13 & 63) << 6);
            var13 = bytes[++index];
            character = (char)(character | (char)(var13 & 63));
            characters[outputLength++] = character;
         }
      }

      return new String(characters, 0, outputLength);
   }

   static {
      try {
         String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
         BASE64_ALPHABET = string.toCharArray();
      } catch (Exception var1) {
         throw new ExceptionInInitializerError(var1);
      }
   }
}
