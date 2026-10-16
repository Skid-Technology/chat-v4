package io.netty.handler.codec.http.websocketx;

public class WebSocket13FrameDecoder extends WebSocket08FrameDecoder {
   public WebSocket13FrameDecoder(boolean maskedPayload, boolean allowExtensions, int maxFramePayloadLength) {
      super(maskedPayload, allowExtensions, maxFramePayloadLength);
   }
}
