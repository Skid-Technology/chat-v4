package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import javax.annotation.Nullable;

@Beta
public final class Funnels {
   private Funnels() {
   }

   public static Funnel<byte[]> byteArrayFunnel() {
      return Funnels.ByteArrayFunnel.INSTANCE;
   }

   public static Funnel<CharSequence> unencodedCharsFunnel() {
      return Funnels.UnencodedCharsFunnel.INSTANCE;
   }

   public static Funnel<CharSequence> stringFunnel(Charset charset) {
      return new Funnels.StringCharsetFunnel(charset);
   }

   public static Funnel<Integer> integerFunnel() {
      return Funnels.IntegerFunnel.INSTANCE;
   }

   public static <E> Funnel<Iterable<? extends E>> sequentialFunnel(Funnel<E> elementFunnel) {
      return new Funnels.SequentialFunnel<>(elementFunnel);
   }

   public static Funnel<Long> longFunnel() {
      return Funnels.LongFunnel.INSTANCE;
   }

   public static OutputStream asOutputStream(PrimitiveSink sink) {
      return new Funnels.SinkAsStream(sink);
   }

   private static enum ByteArrayFunnel implements Funnel<byte[]> {
      INSTANCE;

      public void funnel(byte[] from, PrimitiveSink into) {
         into.putBytes(from);
      }

      @Override
      public String toString() {
         return "Funnels.byteArrayFunnel()";
      }
   }

   private static enum IntegerFunnel implements Funnel<Integer> {
      INSTANCE;

      public void funnel(Integer from, PrimitiveSink into) {
         into.putInt(from);
      }

      @Override
      public String toString() {
         return "Funnels.integerFunnel()";
      }
   }

   private static enum LongFunnel implements Funnel<Long> {
      INSTANCE;

      public void funnel(Long from, PrimitiveSink into) {
         into.putLong(from);
      }

      @Override
      public String toString() {
         return "Funnels.longFunnel()";
      }
   }

   private static class SequentialFunnel<E> implements Funnel<Iterable<? extends E>>, Serializable {
      private final Funnel<E> elementFunnel;

      SequentialFunnel(Funnel<E> elementFunnel) {
         this.elementFunnel = Preconditions.checkNotNull(elementFunnel);
      }

      public void funnel(Iterable<? extends E> from, PrimitiveSink into) {
         for (E e : from) {
            this.elementFunnel.funnel(e, into);
         }
      }

      @Override
      public String toString() {
         return "Funnels.sequentialFunnel(" + this.elementFunnel + ")";
      }

      @Override
      public boolean equals(@Nullable Object o) {
         if (o instanceof Funnels.SequentialFunnel) {
            Funnels.SequentialFunnel<?> funnel = (Funnels.SequentialFunnel<?>)o;
            return this.elementFunnel.equals(funnel.elementFunnel);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Funnels.SequentialFunnel.class.hashCode() ^ this.elementFunnel.hashCode();
      }
   }

   private static class SinkAsStream extends OutputStream {
      final PrimitiveSink sink;

      SinkAsStream(PrimitiveSink sink) {
         this.sink = Preconditions.checkNotNull(sink);
      }

      @Override
      public void write(int b) {
         this.sink.putByte((byte)b);
      }

      @Override
      public void write(byte[] bytes) {
         this.sink.putBytes(bytes);
      }

      @Override
      public void write(byte[] bytes, int off, int len) {
         this.sink.putBytes(bytes, off, len);
      }

      @Override
      public String toString() {
         return "Funnels.asOutputStream(" + this.sink + ")";
      }
   }

   private static class StringCharsetFunnel implements Funnel<CharSequence>, Serializable {
      private final Charset charset;

      StringCharsetFunnel(Charset charset) {
         this.charset = Preconditions.checkNotNull(charset);
      }

      public void funnel(CharSequence from, PrimitiveSink into) {
         into.putString(from, this.charset);
      }

      @Override
      public String toString() {
         return "Funnels.stringFunnel(" + this.charset.name() + ")";
      }

      @Override
      public boolean equals(@Nullable Object o) {
         if (o instanceof Funnels.StringCharsetFunnel) {
            Funnels.StringCharsetFunnel funnel = (Funnels.StringCharsetFunnel)o;
            return this.charset.equals(funnel.charset);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Funnels.StringCharsetFunnel.class.hashCode() ^ this.charset.hashCode();
      }

      Object writeReplace() {
         return new Funnels.StringCharsetFunnel.SerializedForm(this.charset);
      }

      private static class SerializedForm implements Serializable {
         private final String charsetCanonicalName;
         private static final long serialVersionUID = 0L;

         SerializedForm(Charset charset) {
            this.charsetCanonicalName = charset.name();
         }

         private Object readResolve() {
            return Funnels.stringFunnel(Charset.forName(this.charsetCanonicalName));
         }
      }
   }

   private static enum UnencodedCharsFunnel implements Funnel<CharSequence> {
      INSTANCE;

      public void funnel(CharSequence from, PrimitiveSink into) {
         into.putUnencodedChars(from);
      }

      @Override
      public String toString() {
         return "Funnels.unencodedCharsFunnel()";
      }
   }
}
