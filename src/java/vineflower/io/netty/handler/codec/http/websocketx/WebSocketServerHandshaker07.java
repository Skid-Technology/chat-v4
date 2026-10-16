package io.netty.handler.codec.http.websocketx;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class WebSocketServerHandshaker07 extends WebSocketServerHandshaker {
   public static final String WEBSOCKET_07_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
   private final boolean allowExtensions;

   public WebSocketServerHandshaker07(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength) {
      super(WebSocketVersion.V07, webSocketURL, subprotocols, maxFramePayloadLength);
      this.allowExtensions = allowExtensions;
   }

   @Override
   protected FullHttpResponse newHandshakeResponse(FullHttpRequest req, HttpHeaders headers) {
      FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS);
      if (headers != null) {
         res.headers().add(headers);
      }

      String key = req.headers().get("Sec-WebSocket-Key");
      if (key == null) {
         throw new WebSocketHandshakeException("not a WebSocket request: missing key");
      } else {
         String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
         byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
         String accept = WebSocketUtil.base64(sha1);
         if (logger.isDebugEnabled()) {
            logger.debug("WebSocket version 07 server handshake key: {}, response: {}.", key, accept);
         }

         res.headers().add("Upgrade", "WebSocket".toLowerCase());
         res.headers().add("Connection", "Upgrade");
         res.headers().add("Sec-WebSocket-Accept", accept);
         String subprotocols = req.headers().get("Sec-WebSocket-Protocol");
         if (subprotocols != null) {
            String selectedSubprotocol = this.selectSubprotocol(subprotocols);
            if (selectedSubprotocol == null) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Requested subprotocol(s) not supported: {}", subprotocols);
               }
            } else {
               res.headers().add("Sec-WebSocket-Protocol", selectedSubprotocol);
            }
         }

         return res;
      }
   }

   @Override
   protected WebSocketFrameDecoder newWebsocketDecoder() {
      return new WebSocket07FrameDecoder(true, this.allowExtensions, this.maxFramePayloadLength());
   }

   @Override
   protected WebSocketFrameEncoder newWebSocketEncoder() {
      return new WebSocket07FrameEncoder(false);
   }
}
