package gg.vape.utils;

import gg.vape.value.RandomValue;
import java.util.Random;

public class RandomUtil {
   private static final Random Y = new Random();

   public static int Y(int n, int n2) {
      int n3 = n2 - n;
      return n3 <= 0 ? n : Y.nextInt(n3) + n + 1;
   }

   public static int i(RandomValue randomValue) {
      return Y(randomValue.getMinimumIntCompat(), randomValue.getMaximumIntCompat());
   }
}
