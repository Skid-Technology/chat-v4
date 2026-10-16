package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Beta
public final class CharStreams {
   private static final int BUF_SIZE = 2048;

   private CharStreams() {
   }

   @Deprecated
   public static InputSupplier<StringReader> newReaderSupplier(String value) {
      return asInputSupplier(CharSource.wrap(value));
   }

   @Deprecated
   public static InputSupplier<InputStreamReader> newReaderSupplier(InputSupplier<? extends InputStream> in, Charset charset) {
      return asInputSupplier(ByteStreams.asByteSource(in).asCharSource(charset));
   }

   @Deprecated
   public static OutputSupplier<OutputStreamWriter> newWriterSupplier(OutputSupplier<? extends OutputStream> out, Charset charset) {
      return asOutputSupplier(ByteStreams.asByteSink(out).asCharSink(charset));
   }

   @Deprecated
   public static <W extends Appendable & Closeable> void write(CharSequence from, OutputSupplier<W> to) throws IOException {
      asCharSink(to).write(from);
   }

   @Deprecated
   public static <R extends Readable & Closeable, W extends Appendable & Closeable> long copy(InputSupplier<R> from, OutputSupplier<W> to) throws IOException {
      return asCharSource(from).copyTo(asCharSink(to));
   }

   @Deprecated
   public static <R extends Readable & Closeable> long copy(InputSupplier<R> from, Appendable to) throws IOException {
      return asCharSource(from).copyTo(to);
   }

   public static long copy(Readable from, Appendable to) throws IOException {
      Preconditions.checkNotNull(from);
      Preconditions.checkNotNull(to);
      CharBuffer buf = CharBuffer.allocate(2048);
      long total = 0L;

      while (from.read(buf) != -1) {
         ((Buffer)buf).flip();
         to.append(buf);
         total += buf.remaining();
         ((Buffer)buf).clear();
      }

      return total;
   }

   public static String toString(Readable r) throws IOException {
      return toStringBuilder(r).toString();
   }

   @Deprecated
   public static <R extends Readable & Closeable> String toString(InputSupplier<R> supplier) throws IOException {
      return asCharSource(supplier).read();
   }

   private static StringBuilder toStringBuilder(Readable r) throws IOException {
      StringBuilder sb = new StringBuilder();
      copy(r, sb);
      return sb;
   }

   @Deprecated
   public static <R extends Readable & Closeable> String readFirstLine(InputSupplier<R> supplier) throws IOException {
      return asCharSource(supplier).readFirstLine();
   }

   @Deprecated
   public static <R extends Readable & Closeable> List<String> readLines(InputSupplier<R> supplier) throws IOException {
      Closer closer = Closer.create();

      List var3;
      try {
         R r = (R)closer.register(supplier.getInput());
         var3 = readLines(r);
      } catch (Throwable var7) {
         throw closer.rethrow(var7);
      } finally {
         closer.close();
      }

      return var3;
   }

   public static List<String> readLines(Readable r) throws IOException {
      List<String> result = new ArrayList<>();
      LineReader lineReader = new LineReader(r);

      String line;
      while ((line = lineReader.readLine()) != null) {
         result.add(line);
      }

      return result;
   }

   public static <T> T readLines(Readable readable, LineProcessor<T> processor) throws IOException {
      Preconditions.checkNotNull(readable);
      Preconditions.checkNotNull(processor);
      LineReader lineReader = new LineReader(readable);

      String line;
      while ((line = lineReader.readLine()) != null && processor.processLine(line)) {
      }

      return processor.getResult();
   }

   @Deprecated
   public static <R extends Readable & Closeable, T> T readLines(InputSupplier<R> supplier, LineProcessor<T> callback) throws IOException {
      Preconditions.checkNotNull(supplier);
      Preconditions.checkNotNull(callback);
      Closer closer = Closer.create();

      Object var4;
      try {
         R r = (R)closer.register(supplier.getInput());
         var4 = readLines(r, callback);
      } catch (Throwable var8) {
         throw closer.rethrow(var8);
      } finally {
         closer.close();
      }

      return (T)var4;
   }

   @Deprecated
   public static InputSupplier<Reader> join(Iterable<? extends InputSupplier<? extends Reader>> suppliers) {
      Preconditions.checkNotNull(suppliers);
      Iterable<CharSource> sources = Iterables.transform(suppliers, new Function<InputSupplier<? extends Reader>, CharSource>() {
         public CharSource apply(InputSupplier<? extends Reader> input) {
            return CharStreams.asCharSource(input);
         }
      });
      return asInputSupplier(CharSource.concat(sources));
   }

   @Deprecated
   public static InputSupplier<Reader> join(InputSupplier<? extends Reader>... suppliers) {
      return join(Arrays.asList(suppliers));
   }

   public static void skipFully(Reader reader, long n) throws IOException {
      Preconditions.checkNotNull(reader);

      while (n > 0L) {
         long amt = reader.skip(n);
         if (amt == 0L) {
            if (reader.read() == -1) {
               throw new EOFException();
            }

            n--;
         } else {
            n -= amt;
         }
      }
   }

   public static Writer nullWriter() {
      return CharStreams.NullWriter.INSTANCE;
   }

   public static Writer asWriter(Appendable target) {
      return (Writer)(target instanceof Writer ? (Writer)target : new AppendableWriter(target));
   }

   static Reader asReader(final Readable readable) {
      Preconditions.checkNotNull(readable);
      return readable instanceof Reader ? (Reader)readable : new Reader() {
         @Override
         public int read(char[] cbuf, int off, int len) throws IOException {
            return this.read(CharBuffer.wrap(cbuf, off, len));
         }

         @Override
         public int read(CharBuffer target) throws IOException {
            return readable.read(target);
         }

         @Override
         public void close() throws IOException {
            if (readable instanceof Closeable) {
               ((Closeable)readable).close();
            }
         }
      };
   }

   @Deprecated
   public static CharSource asCharSource(final InputSupplier<? extends Readable> supplier) {
      Preconditions.checkNotNull(supplier);
      return new CharSource() {
         @Override
         public Reader openStream() throws IOException {
            return CharStreams.asReader(supplier.getInput());
         }

         @Override
         public String toString() {
            return "CharStreams.asCharSource(" + supplier + ")";
         }
      };
   }

   @Deprecated
   public static CharSink asCharSink(final OutputSupplier<? extends Appendable> supplier) {
      Preconditions.checkNotNull(supplier);
      return new CharSink() {
         @Override
         public Writer openStream() throws IOException {
            return CharStreams.asWriter(supplier.getOutput());
         }

         @Override
         public String toString() {
            return "CharStreams.asCharSink(" + supplier + ")";
         }
      };
   }

   static <R extends Reader> InputSupplier<R> asInputSupplier(CharSource source) {
      return Preconditions.checkNotNull(source);
   }

   static <W extends Writer> OutputSupplier<W> asOutputSupplier(CharSink sink) {
      return Preconditions.checkNotNull(sink);
   }

   private static final class NullWriter extends Writer {
      private static final CharStreams.NullWriter INSTANCE = new CharStreams.NullWriter();

      @Override
      public void write(int c) {
      }

      @Override
      public void write(char[] cbuf) {
         Preconditions.checkNotNull(cbuf);
      }

      @Override
      public void write(char[] cbuf, int off, int len) {
         Preconditions.checkPositionIndexes(off, off + len, cbuf.length);
      }

      @Override
      public void write(String str) {
         Preconditions.checkNotNull(str);
      }

      @Override
      public void write(String str, int off, int len) {
         Preconditions.checkPositionIndexes(off, off + len, str.length());
      }

      @Override
      public Writer append(CharSequence csq) {
         Preconditions.checkNotNull(csq);
         return this;
      }

      @Override
      public Writer append(CharSequence csq, int start, int end) {
         Preconditions.checkPositionIndexes(start, end, csq.length());
         return this;
      }

      @Override
      public Writer append(char c) {
         return this;
      }

      @Override
      public void flush() {
      }

      @Override
      public void close() {
      }

      @Override
      public String toString() {
         return "CharStreams.nullWriter()";
      }
   }
}
