package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public abstract class CharSource implements InputSupplier<Reader> {
   protected CharSource() {
   }

   public abstract Reader openStream() throws IOException;

   @Deprecated
   public final Reader getInput() throws IOException {
      return this.openStream();
   }

   public BufferedReader openBufferedStream() throws IOException {
      Reader reader = this.openStream();
      return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
   }

   public long copyTo(Appendable appendable) throws IOException {
      Preconditions.checkNotNull(appendable);
      Closer closer = Closer.create();

      long var4;
      try {
         Reader reader = closer.register(this.openStream());
         var4 = CharStreams.copy(reader, appendable);
      } catch (Throwable var9) {
         throw closer.rethrow(var9);
      } finally {
         closer.close();
      }

      return var4;
   }

   public long copyTo(CharSink sink) throws IOException {
      Preconditions.checkNotNull(sink);
      Closer closer = Closer.create();

      long var5;
      try {
         Reader reader = closer.register(this.openStream());
         Writer writer = closer.register(sink.openStream());
         var5 = CharStreams.copy(reader, writer);
      } catch (Throwable var10) {
         throw closer.rethrow(var10);
      } finally {
         closer.close();
      }

      return var5;
   }

   public String read() throws IOException {
      Closer closer = Closer.create();

      String var3;
      try {
         Reader reader = closer.register(this.openStream());
         var3 = CharStreams.toString(reader);
      } catch (Throwable var7) {
         throw closer.rethrow(var7);
      } finally {
         closer.close();
      }

      return var3;
   }

   @Nullable
   public String readFirstLine() throws IOException {
      Closer closer = Closer.create();

      String var3;
      try {
         BufferedReader reader = closer.register(this.openBufferedStream());
         var3 = reader.readLine();
      } catch (Throwable var7) {
         throw closer.rethrow(var7);
      } finally {
         closer.close();
      }

      return var3;
   }

   public ImmutableList<String> readLines() throws IOException {
      Closer closer = Closer.create();

      ImmutableList var5;
      try {
         BufferedReader reader = closer.register(this.openBufferedStream());
         List<String> result = Lists.newArrayList();

         String line;
         while ((line = reader.readLine()) != null) {
            result.add(line);
         }

         var5 = ImmutableList.copyOf(result);
      } catch (Throwable var9) {
         throw closer.rethrow(var9);
      } finally {
         closer.close();
      }

      return var5;
   }

   @Beta
   public <T> T readLines(LineProcessor<T> processor) throws IOException {
      Preconditions.checkNotNull(processor);
      Closer closer = Closer.create();

      Object var4;
      try {
         Reader reader = closer.register(this.openStream());
         var4 = CharStreams.readLines(reader, processor);
      } catch (Throwable var8) {
         throw closer.rethrow(var8);
      } finally {
         closer.close();
      }

      return (T)var4;
   }

   public boolean isEmpty() throws IOException {
      Closer closer = Closer.create();

      boolean var3;
      try {
         Reader reader = closer.register(this.openStream());
         var3 = reader.read() == -1;
      } catch (Throwable var7) {
         throw closer.rethrow(var7);
      } finally {
         closer.close();
      }

      return var3;
   }

   public static CharSource concat(Iterable<? extends CharSource> sources) {
      return new CharSource.ConcatenatedCharSource(sources);
   }

   public static CharSource concat(Iterator<? extends CharSource> sources) {
      return concat(ImmutableList.copyOf(sources));
   }

   public static CharSource concat(CharSource... sources) {
      return concat(ImmutableList.copyOf(sources));
   }

   public static CharSource wrap(CharSequence charSequence) {
      return new CharSource.CharSequenceCharSource(charSequence);
   }

   public static CharSource empty() {
      return CharSource.EmptyCharSource.INSTANCE;
   }

   private static class CharSequenceCharSource extends CharSource {
      private static final Splitter LINE_SPLITTER = Splitter.on(Pattern.compile("\r\n|\n|\r"));
      private final CharSequence seq;

      protected CharSequenceCharSource(CharSequence seq) {
         this.seq = Preconditions.checkNotNull(seq);
      }

      @Override
      public Reader openStream() {
         return new CharSequenceReader(this.seq);
      }

      @Override
      public String read() {
         return this.seq.toString();
      }

      @Override
      public boolean isEmpty() {
         return this.seq.length() == 0;
      }

      private Iterable<String> lines() {
         return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
               return new AbstractIterator<String>() {
                  Iterator<String> lines = CharSource.CharSequenceCharSource.LINE_SPLITTER.split(CharSequenceCharSource.this.seq).iterator();

                  protected String computeNext() {
                     if (this.lines.hasNext()) {
                        String next = this.lines.next();
                        if (this.lines.hasNext() || !next.isEmpty()) {
                           return next;
                        }
                     }

                     return this.endOfData();
                  }
               };
            }
         };
      }

      @Override
      public String readFirstLine() {
         Iterator<String> lines = this.lines().iterator();
         return lines.hasNext() ? lines.next() : null;
      }

      @Override
      public ImmutableList<String> readLines() {
         return ImmutableList.copyOf(this.lines());
      }

      @Override
      public <T> T readLines(LineProcessor<T> processor) throws IOException {
         for (String line : this.lines()) {
            if (!processor.processLine(line)) {
               break;
            }
         }

         return processor.getResult();
      }

      @Override
      public String toString() {
         return "CharSource.wrap(" + Ascii.truncate(this.seq, 30, "...") + ")";
      }
   }

   private static final class ConcatenatedCharSource extends CharSource {
      private final Iterable<? extends CharSource> sources;

      ConcatenatedCharSource(Iterable<? extends CharSource> sources) {
         this.sources = Preconditions.checkNotNull(sources);
      }

      @Override
      public Reader openStream() throws IOException {
         return new MultiReader(this.sources.iterator());
      }

      @Override
      public boolean isEmpty() throws IOException {
         for (CharSource source : this.sources) {
            if (!source.isEmpty()) {
               return false;
            }
         }

         return true;
      }

      @Override
      public String toString() {
         return "CharSource.concat(" + this.sources + ")";
      }
   }

   private static final class EmptyCharSource extends CharSource.CharSequenceCharSource {
      private static final CharSource.EmptyCharSource INSTANCE = new CharSource.EmptyCharSource();

      private EmptyCharSource() {
         super("");
      }

      @Override
      public String toString() {
         return "CharSource.empty()";
      }
   }
}
