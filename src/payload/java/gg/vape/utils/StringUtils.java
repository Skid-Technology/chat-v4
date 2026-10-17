package gg.vape.utils;

import gg.vape.input.KeyboardCodeUtil;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

public class StringUtils {
   private static final Pattern l = Pattern.compile("\\s+" + StringUtils.g.pattern() + "$");
   private static final Pattern g = Pattern.compile("(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})");
   private static Pattern F = Pattern.compile("(\\.)(#+)(\\d*)");
   private static final Pattern j = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

   public static String p(String string) {
      Matcher matcher = F.matcher(string);
      StringBuffer stringBuffer = new StringBuffer();

      while (matcher.find()) {
         String string2 = matcher.group(1);
         String string3 = matcher.group(2);
         String string4 = matcher.group(3);
         String string5 = string2 + "0" + (string3.length() > 1 && !string4.isEmpty() ? "0" : "") + string3.substring(1) + string4;
         matcher.appendReplacement(stringBuffer, string5);
      }

      matcher.appendTail(stringBuffer);
      return stringBuffer.toString();
   }

   public static String y(String string) {
      return string == null ? "" : string.toLowerCase().replace(" ", "").replace("-", "");
   }

   public static int Q(String string, String string2) {
      string = string.toLowerCase();
      string2 = string2.toLowerCase();
      int n2 = string.length();
      int n3 = string2.length();
      int[][] nArray = new int[n2 + 1][n3 + 1];
      int n = 0;

      while (n <= n2) {
         nArray[n][0] = n++;
      }

      n = 0;

      while (n <= n3) {
         nArray[0][n] = n++;
      }

      for (int var13 = 1; var13 <= n2; var13++) {
         char c = string.charAt(var13 - 1);

         for (int i = 1; i <= n3; i++) {
            char c2 = string2.charAt(i - 1);
            int n4 = c == c2 ? 0 : 1;
            nArray[var13][i] = Math.min(Math.min(nArray[var13 - 1][i] + 1, nArray[var13][i - 1] + 1), nArray[var13 - 1][i - 1] + n4);
         }
      }

      return nArray[n2][n3];
   }

   public static boolean n(String string) {
      try {
         UUID.fromString(string);
         return true;
      } catch (IllegalArgumentException var2) {
         return false;
      }
   }

   public static String l(String string) {
      return j.matcher(string).replaceAll("");
   }

   public static boolean K(@Nullable String string, @Nullable String string2) {
      return string != null && string2 != null && string.toLowerCase().contains(string2.toLowerCase());
   }

   public static String b(String string, String string2, String string3) {
      return string.replaceFirst("(?s)" + string2 + "(?!.*?" + string2 + ")", string3);
   }

   public static String Y(int n) {
      return n >= 0 ? KeyboardCodeUtil.getVirtualKeyName(n) : "M" + (101 + n);
   }

   public static String t(String string) {
      Pattern pattern = Pattern.compile("§[0-9a-jmnpqstu]");
      Matcher matcher = pattern.matcher(string);
      return matcher.replaceAll("");
   }

   public static String Y(String string, String string2, String string3) {
      if (string != null && string2 != null && string3 != null) {
         Pattern pattern = Pattern.compile(Pattern.quote(string2), 2);
         Matcher matcher = pattern.matcher(string);
         return matcher.replaceAll(string3);
      } else {
         return string;
      }
   }

   public static String q(List<Integer> list) {
      StringBuilder stringBuilder = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
         int n = list.get(i);
         if (n != 0) {
            stringBuilder.append(Y(n));
            if (i != list.size() - 1) {
               stringBuilder.append(" + ");
            }
         }
      }

      return stringBuilder.toString();
   }

   public static String U(String string, String string2, String string3) {
      if (string != null && string2 != null && string3 != null) {
         Pattern pattern = Pattern.compile("(?<=(?:\\b|\\u00A7[0-9A-FK-OR]))" + Pattern.quote(string2) + "(?=(?:\\b|\\u00A7[0-9A-FK-OR]))", 2);
         Matcher matcher = pattern.matcher(string);
         return matcher.replaceAll(string3);
      } else {
         return string;
      }
   }

   public static String Q(CharSequence charSequence) {
      return l.matcher(charSequence).replaceAll("");
   }

   private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
      return illegalArgumentException;
   }
}
