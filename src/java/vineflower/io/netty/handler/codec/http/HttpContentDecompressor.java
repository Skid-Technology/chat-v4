package io.netty.handler.codec.http;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;

public class HttpContentDecompressor extends HttpContentDecoder {
   private final boolean strict;

   public HttpContentDecompressor() {
      this(false);
   }

   public HttpContentDecompressor(boolean strict) {
      this.strict = strict;
   }

   @Override
   protected EmbeddedChannel newContentDecoder(String contentEncoding) throws Exception {
      if ("gzip".equalsIgnoreCase(contentEncoding) || "x-gzip".equalsIgnoreCase(contentEncoding)) {
         return new EmbeddedChannel(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
      } else if (!"deflate".equalsIgnoreCase(contentEncoding) && !"x-deflate".equalsIgnoreCase(contentEncoding)) {
         return null;
      } else {
         ZlibWrapper wrapper;
         if (this.strict) {
            wrapper = ZlibWrapper.ZLIB;
         } else {
            wrapper = ZlibWrapper.ZLIB_OR_NONE;
         }

         return new EmbeddedChannel(ZlibCodecFactory.newZlibDecoder(wrapper));
      }
   }
}
