package io.netty.handler.codec.socks;

public abstract class SocksResponse extends SocksMessage {
   private final SocksResponseType responseType;

   protected SocksResponse(SocksResponseType responseType) {
      super(SocksMessageType.RESPONSE);
      if (responseType == null) {
         throw new NullPointerException("responseType");
      } else {
         this.responseType = responseType;
      }
   }

   public SocksResponseType responseType() {
      return this.responseType;
   }
}
