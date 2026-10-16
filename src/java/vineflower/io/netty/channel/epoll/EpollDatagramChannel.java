package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;

public final class EpollDatagramChannel extends AbstractEpollChannel implements DatagramChannel {
   private static final ChannelMetadata METADATA = new ChannelMetadata(true);
   private static final String EXPECTED_TYPES = " (expected: "
      + StringUtil.simpleClassName(DatagramPacket.class)
      + ", "
      + StringUtil.simpleClassName(AddressedEnvelope.class)
      + '<'
      + StringUtil.simpleClassName(ByteBuf.class)
      + ", "
      + StringUtil.simpleClassName(InetSocketAddress.class)
      + ">, "
      + StringUtil.simpleClassName(ByteBuf.class)
      + ')';
   private volatile InetSocketAddress local;
   private volatile InetSocketAddress remote;
   private volatile boolean connected;
   private final EpollDatagramChannelConfig config = new EpollDatagramChannelConfig(this);

   public EpollDatagramChannel() {
      super(Native.socketDgramFd(), 1);
   }

   @Override
   public ChannelMetadata metadata() {
      return METADATA;
   }

   @Override
   public boolean isActive() {
      return this.fd != -1 && (this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) && this.isRegistered() || this.active);
   }

   @Override
   public boolean isConnected() {
      return this.connected;
   }

   @Override
   public ChannelFuture joinGroup(InetAddress multicastAddress) {
      return this.joinGroup(multicastAddress, this.newPromise());
   }

   @Override
   public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
      try {
         return this.joinGroup(multicastAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), null, promise);
      } catch (SocketException var4) {
         promise.setFailure(var4);
         return promise;
      }
   }

   @Override
   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
      return this.joinGroup(multicastAddress, networkInterface, this.newPromise());
   }

   @Override
   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
      return this.joinGroup(multicastAddress.getAddress(), networkInterface, null, promise);
   }

   @Override
   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
      return this.joinGroup(multicastAddress, networkInterface, source, this.newPromise());
   }

   @Override
   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
      if (multicastAddress == null) {
         throw new NullPointerException("multicastAddress");
      } else if (networkInterface == null) {
         throw new NullPointerException("networkInterface");
      } else {
         promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
         return promise;
      }
   }

   @Override
   public ChannelFuture leaveGroup(InetAddress multicastAddress) {
      return this.leaveGroup(multicastAddress, this.newPromise());
   }

   @Override
   public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
      try {
         return this.leaveGroup(multicastAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), null, promise);
      } catch (SocketException var4) {
         promise.setFailure(var4);
         return promise;
      }
   }

   @Override
   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
      return this.leaveGroup(multicastAddress, networkInterface, this.newPromise());
   }

   @Override
   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
      return this.leaveGroup(multicastAddress.getAddress(), networkInterface, null, promise);
   }

   @Override
   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
      return this.leaveGroup(multicastAddress, networkInterface, source, this.newPromise());
   }

   @Override
   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
      if (multicastAddress == null) {
         throw new NullPointerException("multicastAddress");
      } else if (networkInterface == null) {
         throw new NullPointerException("networkInterface");
      } else {
         promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
         return promise;
      }
   }

   @Override
   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
      return this.block(multicastAddress, networkInterface, sourceToBlock, this.newPromise());
   }

   @Override
   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
      if (multicastAddress == null) {
         throw new NullPointerException("multicastAddress");
      } else if (sourceToBlock == null) {
         throw new NullPointerException("sourceToBlock");
      } else if (networkInterface == null) {
         throw new NullPointerException("networkInterface");
      } else {
         promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
         return promise;
      }
   }

   @Override
   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
      return this.block(multicastAddress, sourceToBlock, this.newPromise());
   }

   @Override
   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
      try {
         return this.block(multicastAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), sourceToBlock, promise);
      } catch (Throwable var5) {
         promise.setFailure(var5);
         return promise;
      }
   }

   @Override
   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
      return new EpollDatagramChannel.EpollDatagramChannelUnsafe();
   }

   protected InetSocketAddress localAddress0() {
      return this.local;
   }

   protected InetSocketAddress remoteAddress0() {
      return this.remote;
   }

   @Override
   protected void doBind(SocketAddress localAddress) throws Exception {
      InetSocketAddress addr = (InetSocketAddress)localAddress;
      checkResolvable(addr);
      Native.bind(this.fd, addr.getAddress(), addr.getPort());
      this.local = Native.localAddress(this.fd);
      this.active = true;
   }

   @Override
   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
      while (true) {
         Object msg = in.current();
         if (msg == null) {
            this.clearEpollOut();
         } else {
            try {
               boolean done = false;

               for (int i = this.config().getWriteSpinCount() - 1; i >= 0; i--) {
                  if (this.doWriteMessage(msg)) {
                     done = true;
                     break;
                  }
               }

               if (done) {
                  in.remove();
                  continue;
               }

               this.setEpollOut();
            } catch (IOException var5) {
               in.remove(var5);
               continue;
            }
         }

         return;
      }
   }

   private boolean doWriteMessage(Object msg) throws IOException {
      ByteBuf data;
      InetSocketAddress remoteAddress;
      if (msg instanceof AddressedEnvelope) {
         AddressedEnvelope<ByteBuf, InetSocketAddress> envelope = (AddressedEnvelope<ByteBuf, InetSocketAddress>)msg;
         data = envelope.content();
         remoteAddress = envelope.recipient();
      } else {
         data = (ByteBuf)msg;
         remoteAddress = null;
      }

      int dataLen = data.readableBytes();
      if (dataLen == 0) {
         return true;
      } else {
         if (remoteAddress == null) {
            remoteAddress = this.remote;
            if (remoteAddress == null) {
               throw new NotYetConnectedException();
            }
         }

         int writtenBytes;
         if (data.hasMemoryAddress()) {
            long memoryAddress = data.memoryAddress();
            writtenBytes = Native.sendToAddress(
               this.fd, memoryAddress, data.readerIndex(), data.writerIndex(), remoteAddress.getAddress(), remoteAddress.getPort()
            );
         } else {
            ByteBuffer nioData = data.internalNioBuffer(data.readerIndex(), data.readableBytes());
            writtenBytes = Native.sendTo(this.fd, nioData, nioData.position(), nioData.limit(), remoteAddress.getAddress(), remoteAddress.getPort());
         }

         return writtenBytes > 0;
      }
   }

   @Override
   protected Object filterOutboundMessage(Object msg) {
      if (msg instanceof DatagramPacket) {
         DatagramPacket packet = (DatagramPacket)msg;
         ByteBuf content = packet.content();
         return content.hasMemoryAddress() ? msg : new DatagramPacket(this.newDirectBuffer(packet, content), packet.recipient());
      } else if (msg instanceof ByteBuf) {
         ByteBuf buf = (ByteBuf)msg;
         return buf.hasMemoryAddress() ? msg : this.newDirectBuffer(buf);
      } else {
         if (msg instanceof AddressedEnvelope) {
            AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope<Object, SocketAddress>)msg;
            if (e.content() instanceof ByteBuf && (e.recipient() == null || e.recipient() instanceof InetSocketAddress)) {
               ByteBuf content = (ByteBuf)e.content();
               if (content.hasMemoryAddress()) {
                  return e;
               }

               return new DefaultAddressedEnvelope<>(this.newDirectBuffer(e, content), (InetSocketAddress)e.recipient());
            }
         }

         throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
      }
   }

   public EpollDatagramChannelConfig config() {
      return this.config;
   }

   @Override
   protected void doDisconnect() throws Exception {
      this.connected = false;
   }

   static final class DatagramSocketAddress extends InetSocketAddress {
      private static final long serialVersionUID = 1348596211215015739L;
      final int receivedAmount;

      DatagramSocketAddress(String addr, int port, int receivedAmount) {
         super(addr, port);
         this.receivedAmount = receivedAmount;
      }
   }

   final class EpollDatagramChannelUnsafe extends AbstractEpollChannel.AbstractEpollUnsafe {
      private RecvByteBufAllocator.Handle allocHandle;

      // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void connect(SocketAddress remote, SocketAddress local, ChannelPromise channelPromise) {
         boolean success = false;

         try {
            boolean var10 = false /* VF: Semaphore variable */;

            try {
               var10 = true;
               InetSocketAddress cause = (InetSocketAddress)remote;
               if (local != null) {
                  InetSocketAddress localAddress = (InetSocketAddress)local;
                  EpollDatagramChannel.this.doBind(localAddress);
               }

               AbstractEpollChannel.checkResolvable(cause);
               EpollDatagramChannel.this.remote = cause;
               EpollDatagramChannel.this.local = Native.localAddress(EpollDatagramChannel.this.fd);
               success = true;
               var10 = false;
            } finally {
               if (var10) {
                  EpollDatagramChannel.this.doClose();
               }
            }

            channelPromise.setSuccess();
            EpollDatagramChannel.this.connected = true;
         } catch (Throwable var12) {
            channelPromise.setFailure(var12);
         }
      }

      // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      void epollInReady() {
         DatagramChannelConfig config = EpollDatagramChannel.this.config();
         RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
         if (allocHandle == null) {
            this.allocHandle = allocHandle = config.getRecvByteBufAllocator().newHandle();
         }

         assert EpollDatagramChannel.this.eventLoop().inEventLoop();

         ChannelPipeline pipeline = EpollDatagramChannel.this.pipeline();

         try {
            ByteBuf data;
            while (true) {
               data = null;
               boolean var16 = false /* VF: Semaphore variable */;

               try {
                  var16 = true;
                  data = allocHandle.allocate(config.getAllocator());
                  int t = data.writerIndex();
                  EpollDatagramChannel.DatagramSocketAddress remoteAddress;
                  if (data.hasMemoryAddress()) {
                     remoteAddress = Native.recvFromAddress(EpollDatagramChannel.this.fd, data.memoryAddress(), t, data.capacity());
                  } else {
                     ByteBuffer nioData = data.internalNioBuffer(t, data.writableBytes());
                     remoteAddress = Native.recvFrom(EpollDatagramChannel.this.fd, nioData, nioData.position(), nioData.limit());
                  }

                  if (remoteAddress == null) {
                     var16 = false;
                     break;
                  }

                  int readBytes = remoteAddress.receivedAmount;
                  data.writerIndex(data.writerIndex() + readBytes);
                  allocHandle.record(readBytes);
                  this.readPending = false;
                  pipeline.fireChannelRead(new DatagramPacket(data, (InetSocketAddress)this.localAddress(), remoteAddress));
                  data = null;
                  var16 = false;
                  continue;
               } catch (Throwable var17) {
                  pipeline.fireChannelReadComplete();
                  pipeline.fireExceptionCaught(var17);
                  var16 = false;
               } finally {
                  if (var16) {
                     if (data != null) {
                        data.release();
                     }
                  }
               }

               if (data != null) {
                  data.release();
               }
            }

            if (data != null) {
               data.release();
            }
         } finally {
            if (!EpollDatagramChannel.this.config().isAutoRead() && !this.readPending) {
               EpollDatagramChannel.this.clearEpollIn();
            }
         }
      }
   }
}
