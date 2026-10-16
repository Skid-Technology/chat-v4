package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
public final class Escapers {
   private static final Escaper NULL_ESCAPER = new CharEscaper() {
      @Override
      public String escape(String string) {
         return Preconditions.checkNotNull(string);
      }

      @Override
      protected char[] escape(char c) {
         return null;
      }
   };

   private Escapers() {
   }

   public static Escaper nullEscaper() {
      return NULL_ESCAPER;
   }

   public static Escapers.Builder builder() {
      return new Escapers.Builder();
   }

   static UnicodeEscaper asUnicodeEscaper(Escaper escaper) {
      Preconditions.checkNotNull(escaper);
      if (escaper instanceof UnicodeEscaper) {
         return (UnicodeEscaper)escaper;
      } else if (escaper instanceof CharEscaper) {
         return wrap((CharEscaper)escaper);
      } else {
         throw new IllegalArgumentException("Cannot create a UnicodeEscaper from: " + escaper.getClass().getName());
      }
   }

   public static String computeReplacement(CharEscaper escaper, char c) {
      return stringOrNull(escaper.escape(c));
   }

   public static String computeReplacement(UnicodeEscaper escaper, int cp) {
      return stringOrNull(escaper.escape(cp));
   }

   private static String stringOrNull(char[] in) {
      return in == null ? null : new String(in);
   }

   private static UnicodeEscaper wrap(final CharEscaper escaper) {
      return new UnicodeEscaper() {
         @Override
         protected char[] escape(int cp) {
            if (cp < 65536) {
               return escaper.escape((char)cp);
            } else {
               char[] surrogateChars = new char[2];
               Character.toChars(cp, surrogateChars, 0);
               char[] hiChars = escaper.escape(surrogateChars[0]);
               char[] loChars = escaper.escape(surrogateChars[1]);
               if (hiChars == null && loChars == null) {
                  return null;
               } else {
                  int hiCount = hiChars != null ? hiChars.length : 1;
                  int loCount = loChars != null ? loChars.length : 1;
                  char[] output = new char[hiCount + loCount];
                  if (hiChars != null) {
                     for (int n = 0; n < hiChars.length; n++) {
                        output[n] = hiChars[n];
                     }
                  } else {
                     output[0] = surrogateChars[0];
                  }

                  if (loChars != null) {
                     for (int n = 0; n < loChars.length; n++) {
                        output[hiCount + n] = loChars[n];
                     }
                  } else {
                     output[hiCount] = surrogateChars[1];
                  }

                  return output;
               }
            }
         }
      };
   }

   @Beta
   public static final class Builder {
      private final Map<Character, String> replacementMap = new HashMap<>();
      private char safeMin = 0;
      private char safeMax = '\uffff';
      private String unsafeReplacement = null;

      private Builder() {
      }

      public Escapers.Builder setSafeRange(char safeMin, char safeMax) {
         this.safeMin = safeMin;
         this.safeMax = safeMax;
         return this;
      }

      public Escapers.Builder setUnsafeReplacement(@Nullable String unsafeReplacement) {
         this.unsafeReplacement = unsafeReplacement;
         return this;
      }

      public Escapers.Builder addEscape(char c, String replacement) {
         Preconditions.checkNotNull(replacement);
         this.replacementMap.put(c, replacement);
         return this;
      }

      public Escaper build() {
         return new ArrayBasedCharEscaper(this.replacementMap, this.safeMin, this.safeMax) {
            private final char[] replacementChars = Builder.this.unsafeReplacement != null ? Builder.this.unsafeReplacement.toCharArray() : null;

            @Override
            protected char[] escapeUnsafe(char c) {
               return this.replacementChars;
            }
         };
      }
   }
}
