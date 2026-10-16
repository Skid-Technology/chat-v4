package io.netty.channel.sctp.oio;

import com.sun.nio.sctp.Association;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.NotificationHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.oio.AbstractOioMessageChannel;
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
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class OioSctpChannel extends AbstractOioMessageChannel implements SctpChannel {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSctpChannel.class);
   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
   private static final String EXPECTED_TYPE = " (expected: " + StringUtil.simpleClassName(SctpMessage.class) + ')';
   private final com.sun.nio.sctp.SctpChannel ch;
   private final SctpChannelConfig config;
   private final Selector readSelector;
   private final Selector writeSelector;
   private final Selector connectSelector;
   private final NotificationHandler<?> notificationHandler;
   private RecvByteBufAllocator.Handle allocHandle;

   private static com.sun.nio.sctp.SctpChannel openChannel() {
      try {
         return com.sun.nio.sctp.SctpChannel.open();
      } catch (IOException var1) {
         throw new ChannelException("Failed to open a sctp channel.", var1);
      }
   }

   public OioSctpChannel() {
      this(openChannel());
   }

   public OioSctpChannel(com.sun.nio.sctp.SctpChannel ch) {
      this(null, ch);
   }

   public OioSctpChannel(Channel parent, com.sun.nio.sctp.SctpChannel ch) {
      super(parent);
      this.ch = ch;
      boolean success = false;

      try {
         ch.configureBlocking(false);
         this.readSelector = Selector.open();
         this.writeSelector = Selector.open();
         this.connectSelector = Selector.open();
         ch.register(this.readSelector, 1);
         ch.register(this.writeSelector, 4);
         ch.register(this.connectSelector, 8);
         this.config = new OioSctpChannel.OioSctpChannelConfig(this, ch);
         this.notificationHandler = new SctpNotificationHandler(this);
         success = true;
      } catch (Exception var11) {
         throw new ChannelException("failed to initialize a sctp channel", var11);
      } finally {
         try {
            ch.close();
         } catch (IOException var10) {
            logger.warn("Failed to close a sctp channel.", (Throwable)var10);
         }
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
   public SctpChannelConfig config() {
      return this.config;
   }

   @Override
   public boolean isOpen() {
      return this.ch.isOpen();
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected int doReadMessages(List<Object> msgs) throws Exception {
      if (!this.readSelector.isOpen()) {
         return 0;
      } else {
         int readMessages = 0;
         int selectedKeys = this.readSelector.select(1000L);
         boolean keysSelected = selectedKeys > 0;
         if (!keysSelected) {
            return readMessages;
         } else {
            Set<SelectionKey> reableKeys = this.readSelector.selectedKeys();

            try {
               for (SelectionKey ignored : reableKeys) {
                  RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
                  if (allocHandle == null) {
                     this.allocHandle = allocHandle = this.config().getRecvByteBufAllocator().newHandle();
                  }

                  ByteBuf buffer = allocHandle.allocate(this.config().getAllocator());
                  boolean free = true;
                  boolean var24 = false /* VF: Semaphore variable */;

                  int var13;
                  label151: {
                     label150: {
                        try {
                           var24 = true;
                           ByteBuffer var28 = buffer.nioBuffer(buffer.writerIndex(), buffer.writableBytes());
                           MessageInfo messageInfo = this.ch.receive(var28, null, this.notificationHandler);
                           if (messageInfo == null) {
                              var13 = readMessages;
                              var24 = false;
                              break label151;
                           }

                           ((Buffer)var28).flip();
                           msgs.add(new SctpMessage(messageInfo, buffer.writerIndex(buffer.writerIndex() + var28.remaining())));
                           free = false;
                           readMessages++;
                           var24 = false;
                           break label150;
                        } catch (Throwable var25) {
                           PlatformDependent.throwException(var25);
                           var24 = false;
                        } finally {
                           if (var24) {
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
                        continue;
                     }

                     int bytesRead = buffer.readableBytes();
                     allocHandle.record(bytesRead);
                     continue;
                  }

                  int bytesRead = buffer.readableBytes();
                  allocHandle.record(bytesRead);
                  buffer.release();
                  return var13;
               }
            } finally {
               reableKeys.clear();
            }

            return readMessages;
         }
      }
   }

   @Override
   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
      if (this.writeSelector.isOpen()) {
         int size = in.size();
         int selectedKeys = this.writeSelector.select(1000L);
         if (selectedKeys > 0) {
            Set<SelectionKey> writableKeys = this.writeSelector.selectedKeys();
            if (!writableKeys.isEmpty()) {
               Iterator<SelectionKey> writableKeysIt = writableKeys.iterator();
               int written = 0;

               while (written != size) {
                  writableKeysIt.next();
                  writableKeysIt.remove();
                  SctpMessage packet = (SctpMessage)in.current();
                  if (packet == null) {
                     return;
                  }

                  ByteBuf data = packet.content();
                  int dataLen = data.readableBytes();
                  ByteBuffer nioData;
                  if (data.nioBufferCount() != -1) {
                     nioData = data.nioBuffer();
                  } else {
                     nioData = ByteBuffer.allocate(dataLen);
                     data.getBytes(data.readerIndex(), nioData);
                     ((Buffer)nioData).flip();
                  }

                  MessageInfo mi = MessageInfo.createOutgoing(this.association(), null, packet.streamIdentifier());
                  mi.payloadProtocolID(packet.protocolIdentifier());
                  mi.streamNumber(packet.streamIdentifier());
                  this.ch.send(nioData, mi);
                  written++;
                  in.remove();
                  if (!writableKeysIt.hasNext()) {
                     return;
                  }
               }
            }
         }
      }
   }

   @Override
   protected Object filterOutboundMessage(Object msg) throws Exception {
      if (msg instanceof SctpMessage) {
         return msg;
      } else {
         throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPE);
      }
   }

   @Override
   public Association association() {
      try {
         return this.ch.association();
      } catch (IOException var2) {
         return null;
      }
   }

   @Override
   public boolean isActive() {
      return this.isOpen() && this.association() != null;
   }

   @Override
   protected SocketAddress localAddress0() {
      try {
         Iterator<SocketAddress> i = this.ch.getAllLocalAddresses().iterator();
         if (i.hasNext()) {
            return i.next();
         }
      } catch (IOException var2) {
      }

      return null;
   }

   @Override
   public Set<InetSocketAddress> allLocalAddresses() {
      try {
         Set<SocketAddress> allLocalAddresses = this.ch.getAllLocalAddresses();
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
   protected SocketAddress remoteAddress0() {
      try {
         Iterator<SocketAddress> i = this.ch.getRemoteAddresses().iterator();
         if (i.hasNext()) {
            return i.next();
         }
      } catch (IOException var2) {
      }

      return null;
   }

   @Override
   public Set<InetSocketAddress> allRemoteAddresses() {
      try {
         Set<SocketAddress> allLocalAddresses = this.ch.getRemoteAddresses();
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
   protected void doBind(SocketAddress localAddress) throws Exception {
      this.ch.bind(localAddress);
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
      if (localAddress != null) {
         this.ch.bind(localAddress);
      }

      boolean success = false;
      boolean var10 = false /* VF: Semaphore variable */;

      try {
         var10 = true;
         this.ch.connect(remoteAddress);
         boolean finishConnect = false;

         while (!finishConnect) {
            if (this.connectSelector.select(1000L) >= 0) {
               Set<SelectionKey> selectionKeys = this.connectSelector.selectedKeys();
               Iterator i$ = selectionKeys.iterator();

               while (true) {
                  if (i$.hasNext()) {
                     SelectionKey key = (SelectionKey)i$.next();
                     if (!key.isConnectable()) {
                        continue;
                     }

                     selectionKeys.clear();
                     finishConnect = true;
                  }

                  selectionKeys.clear();
                  break;
               }
            }
         }

         success = this.ch.finishConnect();
         var10 = false;
      } finally {
         if (var10) {
            this.doClose();
         }
      }

      if (!success) {
         this.doClose();
      }
   }

   @Override
   protected void doDisconnect() throws Exception {
      this.doClose();
   }

   @Override
   protected void doClose() throws Exception {
      closeSelector("read", this.readSelector);
      closeSelector("write", this.writeSelector);
      closeSelector("connect", this.connectSelector);
      this.ch.close();
   }

   private static void closeSelector(String selectorName, Selector selector) {
      try {
         selector.close();
      } catch (IOException var3) {
         logger.warn("Failed to close a " + selectorName + " selector.", (Throwable)var3);
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
            this.ch.bindAddress(localAddress);
            promise.setSuccess();
         } catch (Throwable var4) {
            promise.setFailure(var4);
         }
      } else {
         this.eventLoop().execute(new Runnable() {
            @Override
            public void run() {
               OioSctpChannel.this.bindAddress(localAddress, promise);
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
            this.ch.unbindAddress(localAddress);
            promise.setSuccess();
         } catch (Throwable var4) {
            promise.setFailure(var4);
         }
      } else {
         this.eventLoop().execute(new Runnable() {
            @Override
            public void run() {
               OioSctpChannel.this.unbindAddress(localAddress, promise);
            }
         });
      }

      return promise;
   }

   private final class OioSctpChannelConfig extends DefaultSctpChannelConfig {
      private OioSctpChannelConfig(OioSctpChannel channel, com.sun.nio.sctp.SctpChannel javaChannel) {
         super(channel, javaChannel);
      }

      @Override
      protected void autoReadCleared() {
         OioSctpChannel.this.setReadPending(false);
      }
   }
}
