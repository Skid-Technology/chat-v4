package io.netty.channel.udt.nio;

import com.barchart.udt.TypeUDT;

public class NioUdtMessageRendezvousChannel extends NioUdtMessageConnectorChannel {
   public NioUdtMessageRendezvousChannel() {
      super(NioUdtProvider.newRendezvousChannelUDT(TypeUDT.DATAGRAM));
   }
}
