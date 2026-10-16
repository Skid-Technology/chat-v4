package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;

public final class UnknownSocksResponse extends SocksResponse {
   public UnknownSocksResponse() {
      super(SocksResponseType.UNKNOWN);
   }

   @Override
   public void encodeAsByteBuf(ByteBuf byteBuf) {
   }
}
