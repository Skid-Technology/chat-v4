package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;

abstract class SpdyHeaderBlockDecoder {
   static SpdyHeaderBlockDecoder newInstance(SpdyVersion spdyVersion, int maxHeaderSize) {
      return new SpdyHeaderBlockZlibDecoder(spdyVersion, maxHeaderSize);
   }

   abstract void decode(ByteBuf var1, SpdyHeadersFrame var2) throws Exception;

   abstract void endHeaderBlock(SpdyHeadersFrame var1) throws Exception;

   abstract void end();
}
