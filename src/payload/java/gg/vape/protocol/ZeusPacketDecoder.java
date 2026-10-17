package gg.vape.protocol;

import gg.vape.protocol.packet.ZeusSerializablePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class ZeusPacketDecoder extends ByteToMessageDecoder {
   private final ZeusPacketDirection packetDirection;
   private final boolean failOnEmptyPacket;
   private static final String EMPTY_PACKET_MESSAGE = "Read packet with no bytes";

   private static Exception propagateException(Exception exception) {
      return exception;
   }

   @Override
   protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
      if (byteBuf.readableBytes() == 0) {
         if (this.failOnEmptyPacket) {
            throw new RuntimeException("Read packet with no bytes");
         }
      } else {
         ZeusProtocolState zeusProtocolState = channelHandlerContext.channel().attr(ZeusProtocolConstants.Q).get();
         int n = ZeusPacketBufferUtil.readVarInt(byteBuf);
         ZeusSerializablePacket zeusSerializablePacket = zeusProtocolState.createPacket(this.packetDirection, n);

         try {
            zeusSerializablePacket.S(new ZeusPacketBuffer(byteBuf));
            list.add(zeusSerializablePacket);
         } catch (Exception var8) {
            if (this.failOnEmptyPacket) {
               var8.printStackTrace();
            }

            throw var8;
         }
      }
   }

   public ZeusPacketDecoder(ZeusPacketDirection zeusPacketDirection, boolean bl) {
      this.packetDirection = zeusPacketDirection;
      this.failOnEmptyPacket = bl;
   }
}
