package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;

public final class SocksInitResponse extends SocksResponse {
   private final SocksAuthScheme authScheme;

   public SocksInitResponse(SocksAuthScheme authScheme) {
      super(SocksResponseType.INIT);
      if (authScheme == null) {
         throw new NullPointerException("authScheme");
      } else {
         this.authScheme = authScheme;
      }
   }

   public SocksAuthScheme authScheme() {
      return this.authScheme;
   }

   @Override
   public void encodeAsByteBuf(ByteBuf byteBuf) {
      byteBuf.writeByte(this.protocolVersion().byteValue());
      byteBuf.writeByte(this.authScheme.byteValue());
   }
}
