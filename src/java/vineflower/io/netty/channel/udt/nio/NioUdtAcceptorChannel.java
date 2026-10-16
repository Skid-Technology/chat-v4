package io.netty.channel.udt.nio;

import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.ServerSocketChannelUDT;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.udt.DefaultUdtServerChannelConfig;
import io.netty.channel.udt.UdtServerChannel;
import io.netty.channel.udt.UdtServerChannelConfig;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public abstract class NioUdtAcceptorChannel extends AbstractNioMessageChannel implements UdtServerChannel {
   protected static final InternalLogger logger = InternalLoggerFactory.getInstance(NioUdtAcceptorChannel.class);
   private final UdtServerChannelConfig config;

   protected NioUdtAcceptorChannel(ServerSocketChannelUDT channelUDT) {
      super(null, channelUDT, 16);

      try {
         channelUDT.configureBlocking(false);
         this.config = new DefaultUdtServerChannelConfig(this, channelUDT, true);
      } catch (Exception var5) {
         try {
            channelUDT.close();
         } catch (Exception var4) {
            if (logger.isWarnEnabled()) {
               logger.warn("Failed to close channel.", (Throwable)var4);
            }
         }

         throw new ChannelException("Failed to configure channel.", var5);
      }
   }

   protected NioUdtAcceptorChannel(TypeUDT type) {
      this(NioUdtProvider.newAcceptorChannelUDT(type));
   }

   public UdtServerChannelConfig config() {
      return this.config;
   }

   @Override
   protected void doBind(SocketAddress localAddress) throws Exception {
      this.javaChannel().socket().bind(localAddress, this.config.getBacklog());
   }

   @Override
   protected void doClose() throws Exception {
      this.javaChannel().close();
   }

   @Override
   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
      throw new UnsupportedOperationException();
   }

   @Override
   protected void doDisconnect() throws Exception {
      throw new UnsupportedOperationException();
   }

   @Override
   protected void doFinishConnect() throws Exception {
      throw new UnsupportedOperationException();
   }

   @Override
   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
      throw new UnsupportedOperationException();
   }

   @Override
   protected final Object filterOutboundMessage(Object msg) throws Exception {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean isActive() {
      return this.javaChannel().socket().isBound();
   }

   protected ServerSocketChannelUDT javaChannel() {
      return (ServerSocketChannelUDT)super.javaChannel();
   }

   @Override
   protected SocketAddress localAddress0() {
      return this.javaChannel().socket().getLocalSocketAddress();
   }

   @Override
   public InetSocketAddress localAddress() {
      return (InetSocketAddress)super.localAddress();
   }

   @Override
   public InetSocketAddress remoteAddress() {
      return null;
   }

   @Override
   protected SocketAddress remoteAddress0() {
      return null;
   }
}
