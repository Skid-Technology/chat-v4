package gg.vape.utils;

import java.util.TreeMap;

public class RomanNumeralUtil {
   private static final TreeMap<Integer, String> ROMAN_NUMERALS;

   private static int romanDigitValue(char digit) {
      if (digit == 'I') {
         return 1;
      } else if (digit == 'V') {
         return 5;
      } else if (digit == 'X') {
         return 10;
      } else if (digit == 'L') {
         return 50;
      } else if (digit == 'C') {
         return 100;
      } else if (digit == 'D') {
         return 500;
      } else {
         return digit == 77 ? 1000 : -1;
      }
   }

   private static String decodeUtf8(byte[] bytes) {
      int outputLength = 0;
      int inputLength = bytes.length;
      char[] characters = new char[inputLength];

      for (int index = 0; index < inputLength; index++) {
         int firstByte = 255 & bytes[index];
         if (firstByte < 192) {
            characters[outputLength++] = (char)firstByte;
         } else if (firstByte < 224) {
            char character = (char)((char)(firstByte & 31) << 6);
            byte continuationByte = bytes[++index];
            character = (char)(character | (char)(continuationByte & 63));
            characters[outputLength++] = character;
         } else if (index < inputLength - 2) {
            char character = (char)((char)(firstByte & 15) << '\f');
            byte continuationByte = bytes[++index];
            character = (char)(character | (char)(continuationByte & 63) << 6);
            continuationByte = bytes[++index];
            character = (char)(character | (char)(continuationByte & 63));
            characters[outputLength++] = character;
         }
      }

      return new String(characters, 0, outputLength);
   }

   public static String toRoman(int value) {
      if (value < 0) {
         return String.valueOf(value);
      } else {
         int numeralValue = ROMAN_NUMERALS.floorKey(value);
         return value == numeralValue ? ROMAN_NUMERALS.get(value) : ROMAN_NUMERALS.get(numeralValue) + toRoman(value - numeralValue);
      }
   }

   public static int fromRoman(String string) {
      int value = 0;

      for (int index = 0; index < string.length(); index++) {
         int currentDigit = romanDigitValue(string.charAt(index));
         if (index + 1 < string.length()) {
            int nextDigit = romanDigitValue(string.charAt(index + 1));
            if (currentDigit >= nextDigit) {
               value += currentDigit;
            } else {
               value = value + nextDigit - currentDigit;
               index++;
            }
         } else {
            value += currentDigit;
            index++;
         }
      }

      return value;
   }

   static {
      try {
         String[] stringArray = new String[]{"IV", "CM", "XC", "XL", "IX", "CD"};
         ROMAN_NUMERALS = new TreeMap<>();
         ROMAN_NUMERALS.put(1000, "M");
         ROMAN_NUMERALS.put(900, stringArray[1]);
         ROMAN_NUMERALS.put(500, "D");
         ROMAN_NUMERALS.put(400, stringArray[5]);
         ROMAN_NUMERALS.put(100, "C");
         ROMAN_NUMERALS.put(90, stringArray[2]);
         ROMAN_NUMERALS.put(50, "L");
         ROMAN_NUMERALS.put(40, stringArray[3]);
         ROMAN_NUMERALS.put(10, "X");
         ROMAN_NUMERALS.put(9, stringArray[4]);
         ROMAN_NUMERALS.put(5, "V");
         ROMAN_NUMERALS.put(4, stringArray[0]);
         ROMAN_NUMERALS.put(1, "I");
         ROMAN_NUMERALS.put(0, "");
      } catch (Exception var1) {
         throw new ExceptionInInitializerError(var1);
      }
   }
}
