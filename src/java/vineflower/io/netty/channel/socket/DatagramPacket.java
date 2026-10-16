package io.netty.channel.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.DefaultAddressedEnvelope;
import java.net.InetSocketAddress;

public final class DatagramPacket extends DefaultAddressedEnvelope<ByteBuf, InetSocketAddress> implements ByteBufHolder {
   public DatagramPacket(ByteBuf data, InetSocketAddress recipient) {
      super(data, recipient);
   }

   public DatagramPacket(ByteBuf data, InetSocketAddress recipient, InetSocketAddress sender) {
      super(data, recipient, sender);
   }

   public DatagramPacket copy() {
      return new DatagramPacket(this.content().copy(), this.recipient(), this.sender());
   }

   public DatagramPacket duplicate() {
      return new DatagramPacket(this.content().duplicate(), this.recipient(), this.sender());
   }

   public DatagramPacket retain() {
      super.retain();
      return this;
   }

   public DatagramPacket retain(int increment) {
      super.retain(increment);
      return this;
   }
}
