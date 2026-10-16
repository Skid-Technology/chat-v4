package io.netty.channel.epoll;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.util.NetUtil;
import java.util.Map;

public final class EpollServerSocketChannelConfig extends DefaultChannelConfig implements ServerSocketChannelConfig {
   private final EpollServerSocketChannel channel;
   private volatile int backlog = NetUtil.SOMAXCONN;

   EpollServerSocketChannelConfig(EpollServerSocketChannel channel) {
      super(channel);
      this.channel = channel;
      this.setReuseAddress(true);
   }

   @Override
   public Map<ChannelOption<?>, Object> getOptions() {
      return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG, EpollChannelOption.SO_REUSEPORT);
   }

   @Override
   public <T> T getOption(ChannelOption<T> option) {
      if (option == ChannelOption.SO_RCVBUF) {
         return (T)this.getReceiveBufferSize();
      } else if (option == ChannelOption.SO_REUSEADDR) {
         return (T)this.isReuseAddress();
      } else if (option == ChannelOption.SO_BACKLOG) {
         return (T)this.getBacklog();
      } else {
         return (T)(option == EpollChannelOption.SO_REUSEPORT ? this.isReusePort() : super.getOption(option));
      }
   }

   @Override
   public <T> boolean setOption(ChannelOption<T> option, T value) {
      this.validate(option, value);
      if (option == ChannelOption.SO_RCVBUF) {
         this.setReceiveBufferSize((Integer)value);
      } else if (option == ChannelOption.SO_REUSEADDR) {
         this.setReuseAddress((Boolean)value);
      } else if (option == ChannelOption.SO_BACKLOG) {
         this.setBacklog((Integer)value);
      } else {
         if (option != EpollChannelOption.SO_REUSEPORT) {
            return super.setOption(option, value);
         }

         this.setReusePort((Boolean)value);
      }

      return true;
   }

   @Override
   public boolean isReuseAddress() {
      return Native.isReuseAddress(this.channel.fd) == 1;
   }

   public EpollServerSocketChannelConfig setReuseAddress(boolean reuseAddress) {
      Native.setReuseAddress(this.channel.fd, reuseAddress ? 1 : 0);
      return this;
   }

   @Override
   public int getReceiveBufferSize() {
      return Native.getReceiveBufferSize(this.channel.fd);
   }

   public EpollServerSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
      Native.setReceiveBufferSize(this.channel.fd, receiveBufferSize);
      return this;
   }

   public EpollServerSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
      return this;
   }

   @Override
   public int getBacklog() {
      return this.backlog;
   }

   public EpollServerSocketChannelConfig setBacklog(int backlog) {
      if (backlog < 0) {
         throw new IllegalArgumentException("backlog: " + backlog);
      } else {
         this.backlog = backlog;
         return this;
      }
   }

   public EpollServerSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
      super.setConnectTimeoutMillis(connectTimeoutMillis);
      return this;
   }

   public EpollServerSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
      super.setMaxMessagesPerRead(maxMessagesPerRead);
      return this;
   }

   public EpollServerSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
      super.setWriteSpinCount(writeSpinCount);
      return this;
   }

   public EpollServerSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
      super.setAllocator(allocator);
      return this;
   }

   public EpollServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
      super.setRecvByteBufAllocator(allocator);
      return this;
   }

   public EpollServerSocketChannelConfig setAutoRead(boolean autoRead) {
      super.setAutoRead(autoRead);
      return this;
   }

   public EpollServerSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
      super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
      return this;
   }

   public EpollServerSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
      super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
      return this;
   }

   public EpollServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
      super.setMessageSizeEstimator(estimator);
      return this;
   }

   public boolean isReusePort() {
      return Native.isReusePort(this.channel.fd) == 1;
   }

   public EpollServerSocketChannelConfig setReusePort(boolean reusePort) {
      Native.setReusePort(this.channel.fd, reusePort ? 1 : 0);
      return this;
   }

   @Override
   protected void autoReadCleared() {
      this.channel.clearEpollIn();
   }
}
