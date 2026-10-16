package io.netty.handler.codec.http.websocketx;

public class WebSocket07FrameDecoder extends WebSocket08FrameDecoder {
   public WebSocket07FrameDecoder(boolean maskedPayload, boolean allowExtensions, int maxFramePayloadLength) {
      super(maskedPayload, allowExtensions, maxFramePayloadLength);
   }
}
