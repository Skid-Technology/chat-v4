package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

@Beta
public final class Resources {
   private Resources() {
   }

   @Deprecated
   public static InputSupplier<InputStream> newInputStreamSupplier(URL url) {
      return ByteStreams.asInputSupplier(asByteSource(url));
   }

   public static ByteSource asByteSource(URL url) {
      return new Resources.UrlByteSource(url);
   }

   @Deprecated
   public static InputSupplier<InputStreamReader> newReaderSupplier(URL url, Charset charset) {
      return CharStreams.asInputSupplier(asCharSource(url, charset));
   }

   public static CharSource asCharSource(URL url, Charset charset) {
      return asByteSource(url).asCharSource(charset);
   }

   public static byte[] toByteArray(URL url) throws IOException {
      return asByteSource(url).read();
   }

   public static String toString(URL url, Charset charset) throws IOException {
      return asCharSource(url, charset).read();
   }

   public static <T> T readLines(URL url, Charset charset, LineProcessor<T> callback) throws IOException {
      return CharStreams.readLines(newReaderSupplier(url, charset), callback);
   }

   public static List<String> readLines(URL url, Charset charset) throws IOException {
      return readLines(url, charset, new LineProcessor<List<String>>() {
         final List<String> result = Lists.newArrayList();

         @Override
         public boolean processLine(String line) {
            this.result.add(line);
            return true;
         }

         public List<String> getResult() {
            return this.result;
         }
      });
   }

   public static void copy(URL from, OutputStream to) throws IOException {
      asByteSource(from).copyTo(to);
   }

   public static URL getResource(String resourceName) {
      ClassLoader loader = Objects.firstNonNull(Thread.currentThread().getContextClassLoader(), Resources.class.getClassLoader());
      URL url = loader.getResource(resourceName);
      Preconditions.checkArgument(url != null, "resource %s not found.", resourceName);
      return url;
   }

   public static URL getResource(Class<?> contextClass, String resourceName) {
      URL url = contextClass.getResource(resourceName);
      Preconditions.checkArgument(url != null, "resource %s relative to %s not found.", resourceName, contextClass.getName());
      return url;
   }

   private static final class UrlByteSource extends ByteSource {
      private final URL url;

      private UrlByteSource(URL url) {
         this.url = Preconditions.checkNotNull(url);
      }

      @Override
      public InputStream openStream() throws IOException {
         return this.url.openStream();
      }

      @Override
      public String toString() {
         return "Resources.asByteSource(" + this.url + ")";
      }
   }
}
