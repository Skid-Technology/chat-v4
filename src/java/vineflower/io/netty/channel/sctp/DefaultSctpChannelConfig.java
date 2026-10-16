package io.netty.channel.sctp;

import com.sun.nio.sctp.SctpStandardSocketOptions;
import com.sun.nio.sctp.SctpStandardSocketOptions.InitMaxStreams;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.util.Map;

public class DefaultSctpChannelConfig extends DefaultChannelConfig implements SctpChannelConfig {
   private final com.sun.nio.sctp.SctpChannel javaChannel;

   public DefaultSctpChannelConfig(SctpChannel channel, com.sun.nio.sctp.SctpChannel javaChannel) {
      super(channel);
      if (javaChannel == null) {
         throw new NullPointerException("javaChannel");
      } else {
         this.javaChannel = javaChannel;
         if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
            try {
               this.setSctpNoDelay(true);
            } catch (Exception var4) {
            }
         }
      }
   }

   @Override
   public Map<ChannelOption<?>, Object> getOptions() {
      return this.getOptions(
         super.getOptions(), SctpChannelOption.SO_RCVBUF, SctpChannelOption.SO_SNDBUF, SctpChannelOption.SCTP_NODELAY, SctpChannelOption.SCTP_INIT_MAXSTREAMS
      );
   }

   @Override
   public <T> T getOption(ChannelOption<T> option) {
      if (option == SctpChannelOption.SO_RCVBUF) {
         return (T)this.getReceiveBufferSize();
      } else if (option == SctpChannelOption.SO_SNDBUF) {
         return (T)this.getSendBufferSize();
      } else {
         return (T)(option == SctpChannelOption.SCTP_NODELAY ? this.isSctpNoDelay() : super.getOption(option));
      }
   }

   @Override
   public <T> boolean setOption(ChannelOption<T> option, T value) {
      this.validate(option, value);
      if (option == SctpChannelOption.SO_RCVBUF) {
         this.setReceiveBufferSize((Integer)value);
      } else if (option == SctpChannelOption.SO_SNDBUF) {
         this.setSendBufferSize((Integer)value);
      } else if (option == SctpChannelOption.SCTP_NODELAY) {
         this.setSctpNoDelay((Boolean)value);
      } else {
         if (option != SctpChannelOption.SCTP_INIT_MAXSTREAMS) {
            return super.setOption(option, value);
         }

         this.setInitMaxStreams((InitMaxStreams)value);
      }

      return true;
   }

   @Override
   public boolean isSctpNoDelay() {
      try {
         return this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_NODELAY);
      } catch (IOException var2) {
         throw new ChannelException(var2);
      }
   }

   @Override
   public SctpChannelConfig setSctpNoDelay(boolean sctpNoDelay) {
      try {
         this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_NODELAY, sctpNoDelay);
         return this;
      } catch (IOException var3) {
         throw new ChannelException(var3);
      }
   }

   @Override
   public int getSendBufferSize() {
      try {
         return this.javaChannel.getOption(SctpStandardSocketOptions.SO_SNDBUF);
      } catch (IOException var2) {
         throw new ChannelException(var2);
      }
   }

   @Override
   public SctpChannelConfig setSendBufferSize(int sendBufferSize) {
      try {
         this.javaChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, sendBufferSize);
         return this;
      } catch (IOException var3) {
         throw new ChannelException(var3);
      }
   }

   @Override
   public int getReceiveBufferSize() {
      try {
         return this.javaChannel.getOption(SctpStandardSocketOptions.SO_RCVBUF);
      } catch (IOException var2) {
         throw new ChannelException(var2);
      }
   }

   @Override
   public SctpChannelConfig setReceiveBufferSize(int receiveBufferSize) {
      try {
         this.javaChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, receiveBufferSize);
         return this;
      } catch (IOException var3) {
         throw new ChannelException(var3);
      }
   }

   @Override
   public InitMaxStreams getInitMaxStreams() {
      try {
         return this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS);
      } catch (IOException var2) {
         throw new ChannelException(var2);
      }
   }

   @Override
   public SctpChannelConfig setInitMaxStreams(InitMaxStreams initMaxStreams) {
      try {
         this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, initMaxStreams);
         return this;
      } catch (IOException var3) {
         throw new ChannelException(var3);
      }
   }

   @Override
   public SctpChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
      super.setConnectTimeoutMillis(connectTimeoutMillis);
      return this;
   }

   @Override
   public SctpChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
      super.setMaxMessagesPerRead(maxMessagesPerRead);
      return this;
   }

   @Override
   public SctpChannelConfig setWriteSpinCount(int writeSpinCount) {
      super.setWriteSpinCount(writeSpinCount);
      return this;
   }

   @Override
   public SctpChannelConfig setAllocator(ByteBufAllocator allocator) {
      super.setAllocator(allocator);
      return this;
   }

   @Override
   public SctpChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
      super.setRecvByteBufAllocator(allocator);
      return this;
   }

   @Override
   public SctpChannelConfig setAutoRead(boolean autoRead) {
      super.setAutoRead(autoRead);
      return this;
   }

   @Override
   public SctpChannelConfig setAutoClose(boolean autoClose) {
      super.setAutoClose(autoClose);
      return this;
   }

   @Override
   public SctpChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
      super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
      return this;
   }

   @Override
   public SctpChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
      super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
      return this;
   }

   @Override
   public SctpChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
      super.setMessageSizeEstimator(estimator);
      return this;
   }
}
