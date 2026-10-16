package com.google.common.hash;

import java.nio.charset.Charset;

abstract class AbstractHasher implements Hasher {
   @Override
   public final Hasher putBoolean(boolean b) {
      return this.putByte((byte)(b ? 1 : 0));
   }

   @Override
   public final Hasher putDouble(double d) {
      return this.putLong(Double.doubleToRawLongBits(d));
   }

   @Override
   public final Hasher putFloat(float f) {
      return this.putInt(Float.floatToRawIntBits(f));
   }

   @Override
   public Hasher putUnencodedChars(CharSequence charSequence) {
      int i = 0;

      for (int len = charSequence.length(); i < len; i++) {
         this.putChar(charSequence.charAt(i));
      }

      return this;
   }

   @Override
   public Hasher putString(CharSequence charSequence, Charset charset) {
      return this.putBytes(charSequence.toString().getBytes(charset));
   }
}
