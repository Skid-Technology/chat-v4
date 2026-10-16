package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Beta
@GwtCompatible
public final class CharEscaperBuilder {
   private final Map<Character, String> map;
   private int max = -1;

   public CharEscaperBuilder() {
      this.map = new HashMap<>();
   }

   public CharEscaperBuilder addEscape(char c, String r) {
      this.map.put(c, Preconditions.checkNotNull(r));
      if (c > this.max) {
         this.max = c;
      }

      return this;
   }

   public CharEscaperBuilder addEscapes(char[] cs, String r) {
      Preconditions.checkNotNull(r);

      for (char c : cs) {
         this.addEscape(c, r);
      }

      return this;
   }

   public char[][] toArray() {
      char[][] result = new char[this.max + 1][];

      for (Entry<Character, String> entry : this.map.entrySet()) {
         result[entry.getKey()] = entry.getValue().toCharArray();
      }

      return result;
   }

   public Escaper toEscaper() {
      return new CharEscaperBuilder.CharArrayDecorator(this.toArray());
   }

   private static class CharArrayDecorator extends CharEscaper {
      private final char[][] replacements;
      private final int replaceLength;

      CharArrayDecorator(char[][] replacements) {
         this.replacements = replacements;
         this.replaceLength = replacements.length;
      }

      @Override
      public String escape(String s) {
         int slen = s.length();

         for (int index = 0; index < slen; index++) {
            char c = s.charAt(index);
            if (c < this.replacements.length && this.replacements[c] != null) {
               return this.escapeSlow(s, index);
            }
         }

         return s;
      }

      @Override
      protected char[] escape(char c) {
         return c < this.replaceLength ? this.replacements[c] : null;
      }
   }
}
