package io.netty.channel.udt.nio;

import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.SocketChannelUDT;
import io.netty.channel.ChannelMetadata;
import java.util.List;

public class NioUdtByteAcceptorChannel extends NioUdtAcceptorChannel {
   private static final ChannelMetadata METADATA = new ChannelMetadata(false);

   public NioUdtByteAcceptorChannel() {
      super(TypeUDT.STREAM);
   }

   @Override
   protected int doReadMessages(List<Object> buf) throws Exception {
      SocketChannelUDT channelUDT = this.javaChannel().accept();
      if (channelUDT == null) {
         return 0;
      } else {
         buf.add(new NioUdtByteConnectorChannel(this, channelUDT));
         return 1;
      }
   }

   @Override
   public ChannelMetadata metadata() {
      return METADATA;
   }
}
