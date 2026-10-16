package gg.vape.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.Arrays;
import java.util.List;

public class ZeusFrameDecoder extends ByteToMessageDecoder {
   private static final String LENGTH_ERROR_MESSAGE = "length wider than 21-bit";
   private final byte[] lengthPrefixBytes = new byte[3];

   private static CorruptedFrameException propagateCorruptedFrame(CorruptedFrameException corruptedFrameException) {
      return corruptedFrameException;
   }

   @Override
   protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
      if (!channelHandlerContext.channel().isActive()) {
         byteBuf.skipBytes(byteBuf.readableBytes());
      } else {
         byteBuf.markReaderIndex();
         byte[] byArray = this.lengthPrefixBytes;
         Arrays.fill(byArray, (byte)0);
         byte[] byArray2 = new byte[3];

         for (int i = 0; i < byArray2.length; i++) {
            if (!byteBuf.isReadable()) {
               byteBuf.resetReaderIndex();
               return;
            }

            byArray2[i] = byteBuf.readByte();
            if (byArray2[i] >= 0) {
               ZeusPacketBuffer zeusPacketBuffer = new ZeusPacketBuffer(Unpooled.wrappedBuffer(byArray2));

               try {
                  int n = zeusPacketBuffer.readVarInt();
                  if (byteBuf.readableBytes() < n) {
                     byteBuf.resetReaderIndex();
                     return;
                  }

                  list.add(byteBuf.readBytes(n));
               } finally {
                  zeusPacketBuffer.releaseBuffer();
               }

               return;
            }
         }

         throw new CorruptedFrameException("length wider than 21-bit");
      }
   }
}
