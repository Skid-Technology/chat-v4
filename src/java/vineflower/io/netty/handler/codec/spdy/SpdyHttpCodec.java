package io.netty.handler.codec.spdy;

import io.netty.channel.CombinedChannelDuplexHandler;

public final class SpdyHttpCodec extends CombinedChannelDuplexHandler<SpdyHttpDecoder, SpdyHttpEncoder> {
   public SpdyHttpCodec(SpdyVersion version, int maxContentLength) {
      super(new SpdyHttpDecoder(version, maxContentLength), new SpdyHttpEncoder(version));
   }

   public SpdyHttpCodec(SpdyVersion version, int maxContentLength, boolean validateHttpHeaders) {
      super(new SpdyHttpDecoder(version, maxContentLength, validateHttpHeaders), new SpdyHttpEncoder(version));
   }
}
