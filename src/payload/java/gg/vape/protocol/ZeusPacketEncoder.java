package gg.vape.protocol;

import gg.vape.protocol.packet.ZeusSerializablePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ZeusPacketEncoder extends MessageToByteEncoder<ZeusSerializablePacket> {
   private static final String UNKNOWN_PACKET_MESSAGE = "Failed to recognize packet ";
   private final ZeusPacketDirection packetDirection;
   private final boolean failOnUnknownPacket;
   private static boolean diagnosticsEnabled;

   public static void setDiagnosticsEnabled(boolean enabled) {
      diagnosticsEnabled = enabled;
   }

   private static Exception propagateException(Exception exception) {
      return exception;
   }

   public ZeusPacketEncoder(ZeusPacketDirection zeusPacketDirection, boolean bl) {
      this.packetDirection = zeusPacketDirection;
      this.failOnUnknownPacket = bl;
   }

   public static boolean X() {
      boolean diagnostics = isDiagnosticsEnabled();
      return true;
   }

   public static boolean isDiagnosticsEnabled() {
      return diagnosticsEnabled;
   }

   protected void encode(ChannelHandlerContext context, ZeusSerializablePacket packet, ByteBuf output) throws Exception {
      this.encodePacket(context, packet, output);
   }

   protected void encodePacket(ChannelHandlerContext channelHandlerContext, ZeusSerializablePacket zeusSerializablePacket, ByteBuf byteBuf) throws Exception {
      ZeusProtocolState zeusProtocolState = channelHandlerContext.channel().attr(ZeusProtocolConstants.Q).get();
      int packetId = zeusProtocolState.getPacketId(this.packetDirection, zeusSerializablePacket);
      if (packetId == -1) {
         if (this.failOnUnknownPacket) {
            throw new RuntimeException(UNKNOWN_PACKET_MESSAGE + zeusSerializablePacket.getClass().getName());
         }
      } else {
         ZeusPacketBuffer zeusPacketBuffer = new ZeusPacketBuffer(byteBuf);
         zeusPacketBuffer.writeVarInt(packetId);

         try {
            zeusSerializablePacket.o(zeusPacketBuffer);
         } catch (Exception var8) {
            if (this.failOnUnknownPacket) {
               var8.printStackTrace();
            }

            throw var8;
         }
      }
   }

   static {
      setDiagnosticsEnabled(false);
   }
}
