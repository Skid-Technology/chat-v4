package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import java.net.SocketAddress;
import java.util.List;

public class SpdyFrameCodec extends ByteToMessageDecoder implements SpdyFrameDecoderDelegate, ChannelOutboundHandler {
   private static final SpdyProtocolException INVALID_FRAME = new SpdyProtocolException("Received invalid frame");
   private final SpdyFrameDecoder spdyFrameDecoder;
   private final SpdyFrameEncoder spdyFrameEncoder;
   private final SpdyHeaderBlockDecoder spdyHeaderBlockDecoder;
   private final SpdyHeaderBlockEncoder spdyHeaderBlockEncoder;
   private SpdyHeadersFrame spdyHeadersFrame;
   private SpdySettingsFrame spdySettingsFrame;
   private ChannelHandlerContext ctx;

   public SpdyFrameCodec(SpdyVersion version) {
      this(version, 8192, 16384, 6, 15, 8);
   }

   public SpdyFrameCodec(SpdyVersion version, int maxChunkSize, int maxHeaderSize, int compressionLevel, int windowBits, int memLevel) {
      this(
         version,
         maxChunkSize,
         SpdyHeaderBlockDecoder.newInstance(version, maxHeaderSize),
         SpdyHeaderBlockEncoder.newInstance(version, compressionLevel, windowBits, memLevel)
      );
   }

   protected SpdyFrameCodec(SpdyVersion version, int maxChunkSize, SpdyHeaderBlockDecoder spdyHeaderBlockDecoder, SpdyHeaderBlockEncoder spdyHeaderBlockEncoder) {
      this.spdyFrameDecoder = new SpdyFrameDecoder(version, this, maxChunkSize);
      this.spdyFrameEncoder = new SpdyFrameEncoder(version);
      this.spdyHeaderBlockDecoder = spdyHeaderBlockDecoder;
      this.spdyHeaderBlockEncoder = spdyHeaderBlockEncoder;
   }

   @Override
   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      super.handlerAdded(ctx);
      this.ctx = ctx;
      ctx.channel().closeFuture().addListener(new ChannelFutureListener() {
         public void operationComplete(ChannelFuture future) throws Exception {
            SpdyFrameCodec.this.spdyHeaderBlockDecoder.end();
            SpdyFrameCodec.this.spdyHeaderBlockEncoder.end();
         }
      });
   }

   @Override
   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
      this.spdyFrameDecoder.decode(in);
   }

   @Override
   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      ctx.bind(localAddress, promise);
   }

   @Override
   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      ctx.connect(remoteAddress, localAddress, promise);
   }

   @Override
   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      ctx.disconnect(promise);
   }

   @Override
   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      ctx.close(promise);
   }

   @Override
   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      ctx.deregister(promise);
   }

   @Override
   public void read(ChannelHandlerContext ctx) throws Exception {
      ctx.read();
   }

   @Override
   public void flush(ChannelHandlerContext ctx) throws Exception {
      ctx.flush();
   }

   @Override
   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      if (msg instanceof SpdyDataFrame) {
         SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
         ByteBuf frame = this.spdyFrameEncoder.encodeDataFrame(ctx.alloc(), spdyDataFrame.streamId(), spdyDataFrame.isLast(), spdyDataFrame.content());
         spdyDataFrame.release();
         ctx.write(frame, promise);
      } else if (msg instanceof SpdySynStreamFrame) {
         SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
         ByteBuf headerBlock = this.spdyHeaderBlockEncoder.encode(spdySynStreamFrame);

         ByteBuf frame;
         try {
            frame = this.spdyFrameEncoder
               .encodeSynStreamFrame(
                  ctx.alloc(),
                  spdySynStreamFrame.streamId(),
                  spdySynStreamFrame.associatedStreamId(),
                  spdySynStreamFrame.priority(),
                  spdySynStreamFrame.isLast(),
                  spdySynStreamFrame.isUnidirectional(),
                  headerBlock
               );
         } finally {
            headerBlock.release();
         }

         ctx.write(frame, promise);
      } else if (msg instanceof SpdySynReplyFrame) {
         SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
         ByteBuf headerBlock = this.spdyHeaderBlockEncoder.encode(spdySynReplyFrame);

         ByteBuf frame;
         try {
            frame = this.spdyFrameEncoder.encodeSynReplyFrame(ctx.alloc(), spdySynReplyFrame.streamId(), spdySynReplyFrame.isLast(), headerBlock);
         } finally {
            headerBlock.release();
         }

         ctx.write(frame, promise);
      } else if (msg instanceof SpdyRstStreamFrame) {
         SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
         ByteBuf frame = this.spdyFrameEncoder.encodeRstStreamFrame(ctx.alloc(), spdyRstStreamFrame.streamId(), spdyRstStreamFrame.status().code());
         ctx.write(frame, promise);
      } else if (msg instanceof SpdySettingsFrame) {
         SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
         ByteBuf frame = this.spdyFrameEncoder.encodeSettingsFrame(ctx.alloc(), spdySettingsFrame);
         ctx.write(frame, promise);
      } else if (msg instanceof SpdyPingFrame) {
         SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
         ByteBuf frame = this.spdyFrameEncoder.encodePingFrame(ctx.alloc(), spdyPingFrame.id());
         ctx.write(frame, promise);
      } else if (msg instanceof SpdyGoAwayFrame) {
         SpdyGoAwayFrame spdyGoAwayFrame = (SpdyGoAwayFrame)msg;
         ByteBuf frame = this.spdyFrameEncoder.encodeGoAwayFrame(ctx.alloc(), spdyGoAwayFrame.lastGoodStreamId(), spdyGoAwayFrame.status().code());
         ctx.write(frame, promise);
      } else if (msg instanceof SpdyHeadersFrame) {
         SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
         ByteBuf headerBlock = this.spdyHeaderBlockEncoder.encode(spdyHeadersFrame);

         ByteBuf frame;
         try {
            frame = this.spdyFrameEncoder.encodeHeadersFrame(ctx.alloc(), spdyHeadersFrame.streamId(), spdyHeadersFrame.isLast(), headerBlock);
         } finally {
            headerBlock.release();
         }

         ctx.write(frame, promise);
      } else {
         if (!(msg instanceof SpdyWindowUpdateFrame)) {
            throw new UnsupportedMessageTypeException(msg);
         }

         SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame)msg;
         ByteBuf frame = this.spdyFrameEncoder.encodeWindowUpdateFrame(ctx.alloc(), spdyWindowUpdateFrame.streamId(), spdyWindowUpdateFrame.deltaWindowSize());
         ctx.write(frame, promise);
      }
   }

   @Override
   public void readDataFrame(int streamId, boolean last, ByteBuf data) {
      SpdyDataFrame spdyDataFrame = new DefaultSpdyDataFrame(streamId, data);
      spdyDataFrame.setLast(last);
      this.ctx.fireChannelRead(spdyDataFrame);
   }

   @Override
   public void readSynStreamFrame(int streamId, int associatedToStreamId, byte priority, boolean last, boolean unidirectional) {
      SpdySynStreamFrame spdySynStreamFrame = new DefaultSpdySynStreamFrame(streamId, associatedToStreamId, priority);
      spdySynStreamFrame.setLast(last);
      spdySynStreamFrame.setUnidirectional(unidirectional);
      this.spdyHeadersFrame = spdySynStreamFrame;
   }

   @Override
   public void readSynReplyFrame(int streamId, boolean last) {
      SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
      spdySynReplyFrame.setLast(last);
      this.spdyHeadersFrame = spdySynReplyFrame;
   }

   @Override
   public void readRstStreamFrame(int streamId, int statusCode) {
      SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, statusCode);
      this.ctx.fireChannelRead(spdyRstStreamFrame);
   }

   @Override
   public void readSettingsFrame(boolean clearPersisted) {
      this.spdySettingsFrame = new DefaultSpdySettingsFrame();
      this.spdySettingsFrame.setClearPreviouslyPersistedSettings(clearPersisted);
   }

   @Override
   public void readSetting(int id, int value, boolean persistValue, boolean persisted) {
      this.spdySettingsFrame.setValue(id, value, persistValue, persisted);
   }

   @Override
   public void readSettingsEnd() {
      Object frame = this.spdySettingsFrame;
      this.spdySettingsFrame = null;
      this.ctx.fireChannelRead(frame);
   }

   @Override
   public void readPingFrame(int id) {
      SpdyPingFrame spdyPingFrame = new DefaultSpdyPingFrame(id);
      this.ctx.fireChannelRead(spdyPingFrame);
   }

   @Override
   public void readGoAwayFrame(int lastGoodStreamId, int statusCode) {
      SpdyGoAwayFrame spdyGoAwayFrame = new DefaultSpdyGoAwayFrame(lastGoodStreamId, statusCode);
      this.ctx.fireChannelRead(spdyGoAwayFrame);
   }

   @Override
   public void readHeadersFrame(int streamId, boolean last) {
      this.spdyHeadersFrame = new DefaultSpdyHeadersFrame(streamId);
      this.spdyHeadersFrame.setLast(last);
   }

   @Override
   public void readWindowUpdateFrame(int streamId, int deltaWindowSize) {
      SpdyWindowUpdateFrame spdyWindowUpdateFrame = new DefaultSpdyWindowUpdateFrame(streamId, deltaWindowSize);
      this.ctx.fireChannelRead(spdyWindowUpdateFrame);
   }

   @Override
   public void readHeaderBlock(ByteBuf headerBlock) {
      try {
         this.spdyHeaderBlockDecoder.decode(headerBlock, this.spdyHeadersFrame);
      } catch (Exception var6) {
         this.ctx.fireExceptionCaught(var6);
      } finally {
         headerBlock.release();
      }
   }

   @Override
   public void readHeaderBlockEnd() {
      Object frame = null;

      try {
         this.spdyHeaderBlockDecoder.endHeaderBlock(this.spdyHeadersFrame);
         frame = this.spdyHeadersFrame;
         this.spdyHeadersFrame = null;
      } catch (Exception var3) {
         this.ctx.fireExceptionCaught(var3);
      }

      if (frame != null) {
         this.ctx.fireChannelRead(frame);
      }
   }

   @Override
   public void readFrameError(String message) {
      this.ctx.fireExceptionCaught(INVALID_FRAME);
   }
}
