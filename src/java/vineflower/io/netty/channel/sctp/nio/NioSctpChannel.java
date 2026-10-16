package io.netty.channel.sctp.nio;

import com.sun.nio.sctp.Association;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.NotificationHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.sctp.DefaultSctpChannelConfig;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.sctp.SctpChannelConfig;
import io.netty.channel.sctp.SctpMessage;
import io.netty.channel.sctp.SctpNotificationHandler;
import io.netty.channel.sctp.SctpServerChannel;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class NioSctpChannel extends AbstractNioMessageChannel implements SctpChannel {
   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioSctpChannel.class);
   private final SctpChannelConfig config;
   private final NotificationHandler<?> notificationHandler;
   private RecvByteBufAllocator.Handle allocHandle;

   private static com.sun.nio.sctp.SctpChannel newSctpChannel() {
      try {
         return com.sun.nio.sctp.SctpChannel.open();
      } catch (IOException var1) {
         throw new ChannelException("Failed to open a sctp channel.", var1);
      }
   }

   public NioSctpChannel() {
      this(newSctpChannel());
   }

   public NioSctpChannel(com.sun.nio.sctp.SctpChannel sctpChannel) {
      this(null, sctpChannel);
   }

   public NioSctpChannel(Channel parent, com.sun.nio.sctp.SctpChannel sctpChannel) {
      super(parent, sctpChannel, 1);

      try {
         sctpChannel.configureBlocking(false);
         this.config = new NioSctpChannel.NioSctpChannelConfig(this, sctpChannel);
         this.notificationHandler = new SctpNotificationHandler(this);
      } catch (IOException var6) {
         try {
            sctpChannel.close();
         } catch (IOException var5) {
            if (logger.isWarnEnabled()) {
               logger.warn("Failed to close a partially initialized sctp channel.", (Throwable)var5);
            }
         }

         throw new ChannelException("Failed to enter non-blocking mode.", var6);
      }
   }

   @Override
   public InetSocketAddress localAddress() {
      return (InetSocketAddress)super.localAddress();
   }

   @Override
   public InetSocketAddress remoteAddress() {
      return (InetSocketAddress)super.remoteAddress();
   }

   @Override
   public SctpServerChannel parent() {
      return (SctpServerChannel)super.parent();
   }

   @Override
   public ChannelMetadata metadata() {
      return METADATA;
   }

   @Override
   public Association association() {
      try {
         return this.javaChannel().association();
      } catch (IOException var2) {
         return null;
      }
   }

   @Override
   public Set<InetSocketAddress> allLocalAddresses() {
      try {
         Set<SocketAddress> allLocalAddresses = this.javaChannel().getAllLocalAddresses();
         Set<InetSocketAddress> addresses = new LinkedHashSet<>(allLocalAddresses.size());

         for (SocketAddress socketAddress : allLocalAddresses) {
            addresses.add((InetSocketAddress)socketAddress);
         }

         return addresses;
      } catch (Throwable var5) {
         return Collections.emptySet();
      }
   }

   @Override
   public SctpChannelConfig config() {
      return this.config;
   }

   @Override
   public Set<InetSocketAddress> allRemoteAddresses() {
      try {
         Set<SocketAddress> allLocalAddresses = this.javaChannel().getRemoteAddresses();
         Set<InetSocketAddress> addresses = new HashSet<>(allLocalAddresses.size());

         for (SocketAddress socketAddress : allLocalAddresses) {
            addresses.add((InetSocketAddress)socketAddress);
         }

         return addresses;
      } catch (Throwable var5) {
         return Collections.emptySet();
      }
   }

   protected com.sun.nio.sctp.SctpChannel javaChannel() {
      return (com.sun.nio.sctp.SctpChannel)super.javaChannel();
   }

   @Override
   public boolean isActive() {
      com.sun.nio.sctp.SctpChannel ch = this.javaChannel();
      return ch.isOpen() && this.association() != null;
   }

   @Override
   protected SocketAddress localAddress0() {
      try {
         Iterator<SocketAddress> i = this.javaChannel().getAllLocalAddresses().iterator();
         if (i.hasNext()) {
            return i.next();
         }
      } catch (IOException var2) {
      }

      return null;
   }

   @Override
   protected SocketAddress remoteAddress0() {
      try {
         Iterator<SocketAddress> i = this.javaChannel().getRemoteAddresses().iterator();
         if (i.hasNext()) {
            return i.next();
         }
      } catch (IOException var2) {
      }

      return null;
   }

   @Override
   protected void doBind(SocketAddress localAddress) throws Exception {
      this.javaChannel().bind(localAddress);
   }

   @Override
   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
      if (localAddress != null) {
         this.javaChannel().bind(localAddress);
      }

      boolean success = false;

      try {
         boolean connected = this.javaChannel().connect(remoteAddress);
         if (!connected) {
            this.selectionKey().interestOps(8);
         }

         success = true;
         return connected;
      } finally {
         if (!success) {
            this.doClose();
         }
      }
   }

   @Override
   protected void doFinishConnect() throws Exception {
      if (!this.javaChannel().finishConnect()) {
         throw new Error();
      }
   }

   @Override
   protected void doDisconnect() throws Exception {
      this.doClose();
   }

   @Override
   protected void doClose() throws Exception {
      this.javaChannel().close();
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected int doReadMessages(List<Object> buf) throws Exception {
      com.sun.nio.sctp.SctpChannel ch = this.javaChannel();
      RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
      if (allocHandle == null) {
         this.allocHandle = allocHandle = this.config().getRecvByteBufAllocator().newHandle();
      }

      ByteBuf buffer = allocHandle.allocate(this.config().getAllocator());
      boolean free = true;
      boolean var15 = false /* VF: Semaphore variable */;

      byte var20;
      label70: {
         label76: {
            int pos;
            try {
               var15 = true;
               ByteBuffer cause = buffer.internalNioBuffer(buffer.writerIndex(), buffer.writableBytes());
               pos = cause.position();
               MessageInfo var19 = ch.receive(cause, null, this.notificationHandler);
               if (var19 == null) {
                  var20 = 0;
                  var15 = false;
                  break label70;
               }

               buf.add(new SctpMessage(var19, buffer.writerIndex(buffer.writerIndex() + cause.position() - pos)));
               free = false;
               var20 = 1;
               var15 = false;
               break label76;
            } catch (Throwable var16) {
               PlatformDependent.throwException(var16);
               pos = -1;
               var15 = false;
            } finally {
               if (var15) {
                  int bytesRead = buffer.readableBytes();
                  allocHandle.record(bytesRead);
                  if (free) {
                     buffer.release();
                  }
               }
            }

            int bytesRead = buffer.readableBytes();
            allocHandle.record(bytesRead);
            if (free) {
               buffer.release();
            }

            return pos;
         }

         int bytesRead = buffer.readableBytes();
         allocHandle.record(bytesRead);
         return var20;
      }

      int bytesRead = buffer.readableBytes();
      allocHandle.record(bytesRead);
      buffer.release();
      return var20;
   }

   @Override
   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
      SctpMessage packet = (SctpMessage)msg;
      ByteBuf data = packet.content();
      int dataLen = data.readableBytes();
      if (dataLen == 0) {
         return true;
      } else {
         ByteBufAllocator alloc = this.alloc();
         boolean needsCopy = data.nioBufferCount() != 1;
         if (!needsCopy && !data.isDirect() && alloc.isDirectBufferPooled()) {
            needsCopy = true;
         }

         ByteBuffer nioData;
         if (!needsCopy) {
            nioData = data.nioBuffer();
         } else {
            data = alloc.directBuffer(dataLen).writeBytes(data);
            nioData = data.nioBuffer();
         }

         MessageInfo mi = MessageInfo.createOutgoing(this.association(), null, packet.streamIdentifier());
         mi.payloadProtocolID(packet.protocolIdentifier());
         mi.streamNumber(packet.streamIdentifier());
         int writtenBytes = this.javaChannel().send(nioData, mi);
         return writtenBytes > 0;
      }
   }

   @Override
   protected final Object filterOutboundMessage(Object msg) throws Exception {
      if (msg instanceof SctpMessage) {
         SctpMessage m = (SctpMessage)msg;
         ByteBuf buf = m.content();
         return buf.isDirect() && buf.nioBufferCount() == 1 ? m : new SctpMessage(m.protocolIdentifier(), m.streamIdentifier(), this.newDirectBuffer(m, buf));
      } else {
         throw new UnsupportedOperationException(
            "unsupported message type: " + StringUtil.simpleClassName(msg) + " (expected: " + StringUtil.simpleClassName(SctpMessage.class)
         );
      }
   }

   @Override
   public ChannelFuture bindAddress(InetAddress localAddress) {
      return this.bindAddress(localAddress, this.newPromise());
   }

   @Override
   public ChannelFuture bindAddress(final InetAddress localAddress, final ChannelPromise promise) {
      if (this.eventLoop().inEventLoop()) {
         try {
            this.javaChannel().bindAddress(localAddress);
            promise.setSuccess();
         } catch (Throwable var4) {
            promise.setFailure(var4);
         }
      } else {
         this.eventLoop().execute(new Runnable() {
            @Override
            public void run() {
               NioSctpChannel.this.bindAddress(localAddress, promise);
            }
         });
      }

      return promise;
   }

   @Override
   public ChannelFuture unbindAddress(InetAddress localAddress) {
      return this.unbindAddress(localAddress, this.newPromise());
   }

   @Override
   public ChannelFuture unbindAddress(final InetAddress localAddress, final ChannelPromise promise) {
      if (this.eventLoop().inEventLoop()) {
         try {
            this.javaChannel().unbindAddress(localAddress);
            promise.setSuccess();
         } catch (Throwable var4) {
            promise.setFailure(var4);
         }
      } else {
         this.eventLoop().execute(new Runnable() {
            @Override
            public void run() {
               NioSctpChannel.this.unbindAddress(localAddress, promise);
            }
         });
      }

      return promise;
   }

   private final class NioSctpChannelConfig extends DefaultSctpChannelConfig {
      private NioSctpChannelConfig(NioSctpChannel channel, com.sun.nio.sctp.SctpChannel javaChannel) {
         super(channel, javaChannel);
      }

      @Override
      protected void autoReadCleared() {
         NioSctpChannel.this.setReadPending(false);
      }
   }
}
