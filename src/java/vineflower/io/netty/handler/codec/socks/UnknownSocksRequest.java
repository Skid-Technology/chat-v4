package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;

public final class UnknownSocksRequest extends SocksRequest {
   public UnknownSocksRequest() {
      super(SocksRequestType.UNKNOWN);
   }

   @Override
   public void encodeAsByteBuf(ByteBuf byteBuf) {
   }
}
