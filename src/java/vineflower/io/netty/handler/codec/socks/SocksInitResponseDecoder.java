package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

public class SocksInitResponseDecoder extends ReplayingDecoder<SocksInitResponseDecoder.State> {
   private static final String name = "SOCKS_INIT_RESPONSE_DECODER";
   private SocksProtocolVersion version;
   private SocksAuthScheme authScheme;
   private SocksResponse msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;

   @Deprecated
   public static String getName() {
      return "SOCKS_INIT_RESPONSE_DECODER";
   }

   public SocksInitResponseDecoder() {
      super(SocksInitResponseDecoder.State.CHECK_PROTOCOL_VERSION);
   }

   @Override
   protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
      switch ((SocksInitResponseDecoder.State)this.state()) {
         case CHECK_PROTOCOL_VERSION:
            this.version = SocksProtocolVersion.valueOf(byteBuf.readByte());
            if (this.version != SocksProtocolVersion.SOCKS5) {
               break;
            }

            this.checkpoint(SocksInitResponseDecoder.State.READ_PREFFERED_AUTH_TYPE);
         case READ_PREFFERED_AUTH_TYPE:
            this.authScheme = SocksAuthScheme.valueOf(byteBuf.readByte());
            this.msg = new SocksInitResponse(this.authScheme);
      }

      ctx.pipeline().remove(this);
      out.add(this.msg);
   }

   static enum State {
      CHECK_PROTOCOL_VERSION,
      READ_PREFFERED_AUTH_TYPE;
   }
}
