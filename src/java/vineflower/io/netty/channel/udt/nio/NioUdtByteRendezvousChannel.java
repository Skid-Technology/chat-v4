package io.netty.channel.udt.nio;

import com.barchart.udt.TypeUDT;

public class NioUdtByteRendezvousChannel extends NioUdtByteConnectorChannel {
   public NioUdtByteRendezvousChannel() {
      super(NioUdtProvider.newRendezvousChannelUDT(TypeUDT.STREAM));
   }
}
