package io.netty.channel.udt.nio;

import com.barchart.udt.SocketUDT;
import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.ChannelUDT;
import com.barchart.udt.nio.KindUDT;
import com.barchart.udt.nio.RendezvousChannelUDT;
import com.barchart.udt.nio.SelectorProviderUDT;
import com.barchart.udt.nio.ServerSocketChannelUDT;
import com.barchart.udt.nio.SocketChannelUDT;
import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtServerChannel;
import java.io.IOException;
import java.nio.channels.spi.SelectorProvider;

public final class NioUdtProvider<T extends UdtChannel> implements ChannelFactory<T> {
   public static final ChannelFactory<UdtServerChannel> BYTE_ACCEPTOR = new NioUdtProvider<>(TypeUDT.STREAM, KindUDT.ACCEPTOR);
   public static final ChannelFactory<UdtChannel> BYTE_CONNECTOR = new NioUdtProvider<>(TypeUDT.STREAM, KindUDT.CONNECTOR);
   public static final SelectorProvider BYTE_PROVIDER = SelectorProviderUDT.STREAM;
   public static final ChannelFactory<UdtChannel> BYTE_RENDEZVOUS = new NioUdtProvider<>(TypeUDT.STREAM, KindUDT.RENDEZVOUS);
   public static final ChannelFactory<UdtServerChannel> MESSAGE_ACCEPTOR = new NioUdtProvider<>(TypeUDT.DATAGRAM, KindUDT.ACCEPTOR);
   public static final ChannelFactory<UdtChannel> MESSAGE_CONNECTOR = new NioUdtProvider<>(TypeUDT.DATAGRAM, KindUDT.CONNECTOR);
   public static final SelectorProvider MESSAGE_PROVIDER = SelectorProviderUDT.DATAGRAM;
   public static final ChannelFactory<UdtChannel> MESSAGE_RENDEZVOUS = new NioUdtProvider<>(TypeUDT.DATAGRAM, KindUDT.RENDEZVOUS);
   private final KindUDT kind;
   private final TypeUDT type;

   public static ChannelUDT channelUDT(Channel channel) {
      if (channel instanceof NioUdtByteAcceptorChannel) {
         return ((NioUdtByteAcceptorChannel)channel).javaChannel();
      } else if (channel instanceof NioUdtByteConnectorChannel) {
         return ((NioUdtByteConnectorChannel)channel).javaChannel();
      } else if (channel instanceof NioUdtByteRendezvousChannel) {
         return ((NioUdtByteRendezvousChannel)channel).javaChannel();
      } else if (channel instanceof NioUdtMessageAcceptorChannel) {
         return ((NioUdtMessageAcceptorChannel)channel).javaChannel();
      } else if (channel instanceof NioUdtMessageConnectorChannel) {
         return ((NioUdtMessageConnectorChannel)channel).javaChannel();
      } else {
         return channel instanceof NioUdtMessageRendezvousChannel ? ((NioUdtMessageRendezvousChannel)channel).javaChannel() : null;
      }
   }

   static ServerSocketChannelUDT newAcceptorChannelUDT(TypeUDT type) {
      try {
         return SelectorProviderUDT.from(type).openServerSocketChannel();
      } catch (IOException var2) {
         throw new ChannelException("failed to open a server socket channel", var2);
      }
   }

   static SocketChannelUDT newConnectorChannelUDT(TypeUDT type) {
      try {
         return SelectorProviderUDT.from(type).openSocketChannel();
      } catch (IOException var2) {
         throw new ChannelException("failed to open a socket channel", var2);
      }
   }

   static RendezvousChannelUDT newRendezvousChannelUDT(TypeUDT type) {
      try {
         return SelectorProviderUDT.from(type).openRendezvousChannel();
      } catch (IOException var2) {
         throw new ChannelException("failed to open a rendezvous channel", var2);
      }
   }

   public static SocketUDT socketUDT(Channel channel) {
      ChannelUDT channelUDT = channelUDT(channel);
      return channelUDT == null ? null : channelUDT.socketUDT();
   }

   private NioUdtProvider(TypeUDT type, KindUDT kind) {
      this.type = type;
      this.kind = kind;
   }

   public KindUDT kind() {
      return this.kind;
   }

   public T newChannel() {
      switch (this.kind) {
         case ACCEPTOR:
            switch (this.type) {
               case DATAGRAM:
                  return (T)(new NioUdtMessageAcceptorChannel());
               case STREAM:
                  return (T)(new NioUdtByteAcceptorChannel());
               default:
                  throw new IllegalStateException("wrong type=" + this.type);
            }
         case CONNECTOR:
            switch (this.type) {
               case DATAGRAM:
                  return (T)(new NioUdtMessageConnectorChannel());
               case STREAM:
                  return (T)(new NioUdtByteConnectorChannel());
               default:
                  throw new IllegalStateException("wrong type=" + this.type);
            }
         case RENDEZVOUS:
            switch (this.type) {
               case DATAGRAM:
                  return (T)(new NioUdtMessageRendezvousChannel());
               case STREAM:
                  return (T)(new NioUdtByteRendezvousChannel());
               default:
                  throw new IllegalStateException("wrong type=" + this.type);
            }
         default:
            throw new IllegalStateException("wrong kind=" + this.kind);
      }
   }

   public TypeUDT type() {
      return this.type;
   }
}
