package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpHeaders;
import java.net.URI;
import java.util.List;

public class WebSocketClientProtocolHandler extends WebSocketProtocolHandler {
   private final WebSocketClientHandshaker handshaker;
   private final boolean handleCloseFrames;

   public WebSocketClientProtocolHandler(
      URI webSocketURL,
      WebSocketVersion version,
      String subprotocol,
      boolean allowExtensions,
      HttpHeaders customHeaders,
      int maxFramePayloadLength,
      boolean handleCloseFrames
   ) {
      this(
         WebSocketClientHandshakerFactory.newHandshaker(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength),
         handleCloseFrames
      );
   }

   public WebSocketClientProtocolHandler(
      URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength
   ) {
      this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, true);
   }

   public WebSocketClientProtocolHandler(WebSocketClientHandshaker handshaker, boolean handleCloseFrames) {
      this.handshaker = handshaker;
      this.handleCloseFrames = handleCloseFrames;
   }

   public WebSocketClientProtocolHandler(WebSocketClientHandshaker handshaker) {
      this(handshaker, true);
   }

   @Override
   protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
      if (this.handleCloseFrames && frame instanceof CloseWebSocketFrame) {
         ctx.close();
      } else {
         super.decode(ctx, frame, out);
      }
   }

   @Override
   public void handlerAdded(ChannelHandlerContext ctx) {
      ChannelPipeline cp = ctx.pipeline();
      if (cp.get(WebSocketClientProtocolHandshakeHandler.class) == null) {
         ctx.pipeline()
            .addBefore(ctx.name(), WebSocketClientProtocolHandshakeHandler.class.getName(), new WebSocketClientProtocolHandshakeHandler(this.handshaker));
      }
   }

   public static enum ClientHandshakeStateEvent {
      HANDSHAKE_ISSUED,
      HANDSHAKE_COMPLETE;
   }
}
