package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;
import java.util.List;

public class WebSocket00FrameDecoder extends ReplayingDecoder<Void> implements WebSocketFrameDecoder {
   static final int DEFAULT_MAX_FRAME_SIZE = 16384;
   private final long maxFrameSize;
   private boolean receivedClosingHandshake;

   public WebSocket00FrameDecoder() {
      this(16384);
   }

   public WebSocket00FrameDecoder(int maxFrameSize) {
      this.maxFrameSize = maxFrameSize;
   }

   @Override
   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
      if (this.receivedClosingHandshake) {
         in.skipBytes(this.actualReadableBytes());
      } else {
         byte type = in.readByte();
         WebSocketFrame frame;
         if ((type & 128) == 128) {
            frame = this.decodeBinaryFrame(ctx, type, in);
         } else {
            frame = this.decodeTextFrame(ctx, in);
         }

         if (frame != null) {
            out.add(frame);
         }
      }
   }

   private WebSocketFrame decodeBinaryFrame(ChannelHandlerContext ctx, byte type, ByteBuf buffer) {
      long frameSize = 0L;
      int lengthFieldSize = 0;

      byte b;
      do {
         b = buffer.readByte();
         frameSize <<= 7;
         frameSize |= b & 127;
         if (frameSize > this.maxFrameSize) {
            throw new TooLongFrameException();
         }

         if (++lengthFieldSize > 8) {
            throw new TooLongFrameException();
         }
      } while ((b & 128) == 128);

      if (type == -1 && frameSize == 0L) {
         this.receivedClosingHandshake = true;
         return new CloseWebSocketFrame();
      } else {
         ByteBuf payload = ctx.alloc().buffer((int)frameSize);
         buffer.readBytes(payload);
         return new BinaryWebSocketFrame(payload);
      }
   }

   private WebSocketFrame decodeTextFrame(ChannelHandlerContext ctx, ByteBuf buffer) {
      int ridx = buffer.readerIndex();
      int rbytes = this.actualReadableBytes();
      int delimPos = buffer.indexOf(ridx, ridx + rbytes, (byte)-1);
      if (delimPos == -1) {
         if (rbytes > this.maxFrameSize) {
            throw new TooLongFrameException();
         } else {
            return null;
         }
      } else {
         int frameSize = delimPos - ridx;
         if (frameSize > this.maxFrameSize) {
            throw new TooLongFrameException();
         } else {
            ByteBuf binaryData = ctx.alloc().buffer(frameSize);
            buffer.readBytes(binaryData);
            buffer.skipBytes(1);
            int ffDelimPos = binaryData.indexOf(binaryData.readerIndex(), binaryData.writerIndex(), (byte)-1);
            if (ffDelimPos >= 0) {
               throw new IllegalArgumentException("a text frame should not contain 0xFF.");
            } else {
               return new TextWebSocketFrame(binaryData);
            }
         }
      }
   }
}
