package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;

@Beta
public final class ByteStreams {
   private static final int BUF_SIZE = 4096;
   private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream() {
      @Override
      public void write(int b) {
      }

      @Override
      public void write(byte[] b) {
         Preconditions.checkNotNull(b);
      }

      @Override
      public void write(byte[] b, int off, int len) {
         Preconditions.checkNotNull(b);
      }

      @Override
      public String toString() {
         return "ByteStreams.nullOutputStream()";
      }
   };

   private ByteStreams() {
   }

   @Deprecated
   public static InputSupplier<ByteArrayInputStream> newInputStreamSupplier(byte[] b) {
      return asInputSupplier(ByteSource.wrap(b));
   }

   @Deprecated
   public static InputSupplier<ByteArrayInputStream> newInputStreamSupplier(byte[] b, int off, int len) {
      return asInputSupplier(ByteSource.wrap(b).slice(off, len));
   }

   @Deprecated
   public static void write(byte[] from, OutputSupplier<? extends OutputStream> to) throws IOException {
      asByteSink(to).write(from);
   }

   @Deprecated
   public static long copy(InputSupplier<? extends InputStream> from, OutputSupplier<? extends OutputStream> to) throws IOException {
      return asByteSource(from).copyTo(asByteSink(to));
   }

   @Deprecated
   public static long copy(InputSupplier<? extends InputStream> from, OutputStream to) throws IOException {
      return asByteSource(from).copyTo(to);
   }

   @Deprecated
   public static long copy(InputStream from, OutputSupplier<? extends OutputStream> to) throws IOException {
      return asByteSink(to).writeFrom(from);
   }

   public static long copy(InputStream from, OutputStream to) throws IOException {
      Preconditions.checkNotNull(from);
      Preconditions.checkNotNull(to);
      byte[] buf = new byte[4096];
      long total = 0L;

      while (true) {
         int r = from.read(buf);
         if (r == -1) {
            return total;
         }

         to.write(buf, 0, r);
         total += r;
      }
   }

   public static long copy(ReadableByteChannel from, WritableByteChannel to) throws IOException {
      Preconditions.checkNotNull(from);
      Preconditions.checkNotNull(to);
      ByteBuffer buf = ByteBuffer.allocate(4096);
      long total = 0L;

      while (from.read(buf) != -1) {
         ((Buffer)buf).flip();

         while (buf.hasRemaining()) {
            total += to.write(buf);
         }

         ((Buffer)buf).clear();
      }

      return total;
   }

   public static byte[] toByteArray(InputStream in) throws IOException {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      copy(in, out);
      return out.toByteArray();
   }

   static byte[] toByteArray(InputStream in, int expectedSize) throws IOException {
      byte[] bytes = new byte[expectedSize];
      int remaining = expectedSize;

      while (remaining > 0) {
         int off = expectedSize - remaining;
         int read = in.read(bytes, off, remaining);
         if (read == -1) {
            return Arrays.copyOf(bytes, off);
         }

         remaining -= read;
      }

      int b = in.read();
      if (b == -1) {
         return bytes;
      } else {
         ByteStreams.FastByteArrayOutputStream out = new ByteStreams.FastByteArrayOutputStream();
         out.write(b);
         copy(in, out);
         byte[] result = new byte[bytes.length + out.size()];
         System.arraycopy(bytes, 0, result, 0, bytes.length);
         out.writeTo(result, bytes.length);
         return result;
      }
   }

   @Deprecated
   public static byte[] toByteArray(InputSupplier<? extends InputStream> supplier) throws IOException {
      return asByteSource(supplier).read();
   }

   public static ByteArrayDataInput newDataInput(byte[] bytes) {
      return newDataInput(new ByteArrayInputStream(bytes));
   }

   public static ByteArrayDataInput newDataInput(byte[] bytes, int start) {
      Preconditions.checkPositionIndex(start, bytes.length);
      return newDataInput(new ByteArrayInputStream(bytes, start, bytes.length - start));
   }

   public static ByteArrayDataInput newDataInput(ByteArrayInputStream byteArrayInputStream) {
      return new ByteStreams.ByteArrayDataInputStream(Preconditions.checkNotNull(byteArrayInputStream));
   }

   public static ByteArrayDataOutput newDataOutput() {
      return newDataOutput(new ByteArrayOutputStream());
   }

   public static ByteArrayDataOutput newDataOutput(int size) {
      Preconditions.checkArgument(size >= 0, "Invalid size: %s", size);
      return newDataOutput(new ByteArrayOutputStream(size));
   }

   public static ByteArrayDataOutput newDataOutput(ByteArrayOutputStream byteArrayOutputSteam) {
      return new ByteStreams.ByteArrayDataOutputStream(Preconditions.checkNotNull(byteArrayOutputSteam));
   }

   public static OutputStream nullOutputStream() {
      return NULL_OUTPUT_STREAM;
   }

   public static InputStream limit(InputStream in, long limit) {
      return new ByteStreams.LimitedInputStream(in, limit);
   }

   @Deprecated
   public static long length(InputSupplier<? extends InputStream> supplier) throws IOException {
      return asByteSource(supplier).size();
   }

   @Deprecated
   public static boolean equal(InputSupplier<? extends InputStream> supplier1, InputSupplier<? extends InputStream> supplier2) throws IOException {
      return asByteSource(supplier1).contentEquals(asByteSource(supplier2));
   }

   public static void readFully(InputStream in, byte[] b) throws IOException {
      readFully(in, b, 0, b.length);
   }

   public static void readFully(InputStream in, byte[] b, int off, int len) throws IOException {
      int read = read(in, b, off, len);
      if (read != len) {
         throw new EOFException("reached end of stream after reading " + read + " bytes; " + len + " bytes expected");
      }
   }

   public static void skipFully(InputStream in, long n) throws IOException {
      long toSkip = n;

      while (n > 0L) {
         long amt = in.skip(n);
         if (amt == 0L) {
            if (in.read() == -1) {
               long skipped = toSkip - n;
               throw new EOFException("reached end of stream after skipping " + skipped + " bytes; " + toSkip + " bytes expected");
            }

            n--;
         } else {
            n -= amt;
         }
      }
   }

   @Deprecated
   public static <T> T readBytes(InputSupplier<? extends InputStream> supplier, ByteProcessor<T> processor) throws IOException {
      Preconditions.checkNotNull(supplier);
      Preconditions.checkNotNull(processor);
      Closer closer = Closer.create();

      Object var4;
      try {
         InputStream in = closer.register(supplier.getInput());
         var4 = readBytes(in, processor);
      } catch (Throwable var8) {
         throw closer.rethrow(var8);
      } finally {
         closer.close();
      }

      return (T)var4;
   }

   public static <T> T readBytes(InputStream input, ByteProcessor<T> processor) throws IOException {
      Preconditions.checkNotNull(input);
      Preconditions.checkNotNull(processor);
      byte[] buf = new byte[4096];

      int read;
      do {
         read = input.read(buf);
      } while (read != -1 && processor.processBytes(buf, 0, read));

      return processor.getResult();
   }

   @Deprecated
   public static HashCode hash(InputSupplier<? extends InputStream> supplier, HashFunction hashFunction) throws IOException {
      return asByteSource(supplier).hash(hashFunction);
   }

   public static int read(InputStream in, byte[] b, int off, int len) throws IOException {
      Preconditions.checkNotNull(in);
      Preconditions.checkNotNull(b);
      if (len < 0) {
         throw new IndexOutOfBoundsException("len is negative");
      } else {
         int total = 0;

         while (total < len) {
            int result = in.read(b, off + total, len - total);
            if (result == -1) {
               break;
            }

            total += result;
         }

         return total;
      }
   }

   @Deprecated
   public static InputSupplier<InputStream> slice(InputSupplier<? extends InputStream> supplier, long offset, long length) {
      return asInputSupplier(asByteSource(supplier).slice(offset, length));
   }

   @Deprecated
   public static InputSupplier<InputStream> join(Iterable<? extends InputSupplier<? extends InputStream>> suppliers) {
      Preconditions.checkNotNull(suppliers);
      Iterable<ByteSource> sources = Iterables.transform(suppliers, new Function<InputSupplier<? extends InputStream>, ByteSource>() {
         public ByteSource apply(InputSupplier<? extends InputStream> input) {
            return ByteStreams.asByteSource(input);
         }
      });
      return asInputSupplier(ByteSource.concat(sources));
   }

   @Deprecated
   public static InputSupplier<InputStream> join(InputSupplier<? extends InputStream>... suppliers) {
      return join(Arrays.asList(suppliers));
   }

   @Deprecated
   public static ByteSource asByteSource(final InputSupplier<? extends InputStream> supplier) {
      Preconditions.checkNotNull(supplier);
      return new ByteSource() {
         @Override
         public InputStream openStream() throws IOException {
            return supplier.getInput();
         }

         @Override
         public String toString() {
            return "ByteStreams.asByteSource(" + supplier + ")";
         }
      };
   }

   @Deprecated
   public static ByteSink asByteSink(final OutputSupplier<? extends OutputStream> supplier) {
      Preconditions.checkNotNull(supplier);
      return new ByteSink() {
         @Override
         public OutputStream openStream() throws IOException {
            return supplier.getOutput();
         }

         @Override
         public String toString() {
            return "ByteStreams.asByteSink(" + supplier + ")";
         }
      };
   }

   static <S extends InputStream> InputSupplier<S> asInputSupplier(ByteSource source) {
      return Preconditions.checkNotNull(source);
   }

   static <S extends OutputStream> OutputSupplier<S> asOutputSupplier(ByteSink sink) {
      return Preconditions.checkNotNull(sink);
   }

   private static class ByteArrayDataInputStream implements ByteArrayDataInput {
      final DataInput input;

      ByteArrayDataInputStream(ByteArrayInputStream byteArrayInputStream) {
         this.input = new DataInputStream(byteArrayInputStream);
      }

      @Override
      public void readFully(byte[] b) {
         try {
            this.input.readFully(b);
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      @Override
      public void readFully(byte[] b, int off, int len) {
         try {
            this.input.readFully(b, off, len);
         } catch (IOException var5) {
            throw new IllegalStateException(var5);
         }
      }

      @Override
      public int skipBytes(int n) {
         try {
            return this.input.skipBytes(n);
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      @Override
      public boolean readBoolean() {
         try {
            return this.input.readBoolean();
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      @Override
      public byte readByte() {
         try {
            return this.input.readByte();
         } catch (EOFException var2) {
            throw new IllegalStateException(var2);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      @Override
      public int readUnsignedByte() {
         try {
            return this.input.readUnsignedByte();
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      @Override
      public short readShort() {
         try {
            return this.input.readShort();
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      @Override
      public int readUnsignedShort() {
         try {
            return this.input.readUnsignedShort();
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      @Override
      public char readChar() {
         try {
            return this.input.readChar();
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      @Override
      public int readInt() {
         try {
            return this.input.readInt();
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      @Override
      public long readLong() {
         try {
            return this.input.readLong();
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      @Override
      public float readFloat() {
         try {
            return this.input.readFloat();
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      @Override
      public double readDouble() {
         try {
            return this.input.readDouble();
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      @Override
      public String readLine() {
         try {
            return this.input.readLine();
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      @Override
      public String readUTF() {
         try {
            return this.input.readUTF();
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }
   }

   private static class ByteArrayDataOutputStream implements ByteArrayDataOutput {
      final DataOutput output;
      final ByteArrayOutputStream byteArrayOutputSteam;

      ByteArrayDataOutputStream(ByteArrayOutputStream byteArrayOutputSteam) {
         this.byteArrayOutputSteam = byteArrayOutputSteam;
         this.output = new DataOutputStream(byteArrayOutputSteam);
      }

      @Override
      public void write(int b) {
         try {
            this.output.write(b);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      @Override
      public void write(byte[] b) {
         try {
            this.output.write(b);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      @Override
      public void write(byte[] b, int off, int len) {
         try {
            this.output.write(b, off, len);
         } catch (IOException var5) {
            throw new AssertionError(var5);
         }
      }

      @Override
      public void writeBoolean(boolean v) {
         try {
            this.output.writeBoolean(v);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      @Override
      public void writeByte(int v) {
         try {
            this.output.writeByte(v);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      @Override
      public void writeBytes(String s) {
         try {
            this.output.writeBytes(s);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      @Override
      public void writeChar(int v) {
         try {
            this.output.writeChar(v);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      @Override
      public void writeChars(String s) {
         try {
            this.output.writeChars(s);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      @Override
      public void writeDouble(double v) {
         try {
            this.output.writeDouble(v);
         } catch (IOException var4) {
            throw new AssertionError(var4);
         }
      }

      @Override
      public void writeFloat(float v) {
         try {
            this.output.writeFloat(v);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      @Override
      public void writeInt(int v) {
         try {
            this.output.writeInt(v);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      @Override
      public void writeLong(long v) {
         try {
            this.output.writeLong(v);
         } catch (IOException var4) {
            throw new AssertionError(var4);
         }
      }

      @Override
      public void writeShort(int v) {
         try {
            this.output.writeShort(v);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      @Override
      public void writeUTF(String s) {
         try {
            this.output.writeUTF(s);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      @Override
      public byte[] toByteArray() {
         return this.byteArrayOutputSteam.toByteArray();
      }
   }

   private static final class FastByteArrayOutputStream extends ByteArrayOutputStream {
      private FastByteArrayOutputStream() {
      }

      void writeTo(byte[] b, int off) {
         System.arraycopy(this.buf, 0, b, off, this.count);
      }
   }

   private static final class LimitedInputStream extends FilterInputStream {
      private long left;
      private long mark = -1L;

      LimitedInputStream(InputStream in, long limit) {
         super(in);
         Preconditions.checkNotNull(in);
         Preconditions.checkArgument(limit >= 0L, "limit must be non-negative");
         this.left = limit;
      }

      @Override
      public int available() throws IOException {
         return (int)Math.min((long)this.in.available(), this.left);
      }

      @Override
      public synchronized void mark(int readLimit) {
         this.in.mark(readLimit);
         this.mark = this.left;
      }

      @Override
      public int read() throws IOException {
         if (this.left == 0L) {
            return -1;
         } else {
            int result = this.in.read();
            if (result != -1) {
               this.left--;
            }

            return result;
         }
      }

      @Override
      public int read(byte[] b, int off, int len) throws IOException {
         if (this.left == 0L) {
            return -1;
         } else {
            len = (int)Math.min((long)len, this.left);
            int result = this.in.read(b, off, len);
            if (result != -1) {
               this.left -= result;
            }

            return result;
         }
      }

      @Override
      public synchronized void reset() throws IOException {
         if (!this.in.markSupported()) {
            throw new IOException("Mark not supported");
         } else if (this.mark == -1L) {
            throw new IOException("Mark not set");
         } else {
            this.in.reset();
            this.left = this.mark;
         }
      }

      @Override
      public long skip(long n) throws IOException {
         n = Math.min(n, this.left);
         long skipped = this.in.skip(n);
         this.left -= skipped;
         return skipped;
      }
   }
}
