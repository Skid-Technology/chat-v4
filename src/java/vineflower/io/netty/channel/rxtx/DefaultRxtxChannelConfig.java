package io.netty.channel.rxtx;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import java.util.Map;

final class DefaultRxtxChannelConfig extends DefaultChannelConfig implements RxtxChannelConfig {
   private volatile int baudrate = 115200;
   private volatile boolean dtr;
   private volatile boolean rts;
   private volatile RxtxChannelConfig.Stopbits stopbits = RxtxChannelConfig.Stopbits.STOPBITS_1;
   private volatile RxtxChannelConfig.Databits databits = RxtxChannelConfig.Databits.DATABITS_8;
   private volatile RxtxChannelConfig.Paritybit paritybit = RxtxChannelConfig.Paritybit.NONE;
   private volatile int waitTime;
   private volatile int readTimeout = 1000;

   DefaultRxtxChannelConfig(RxtxChannel channel) {
      super(channel);
   }

   @Override
   public Map<ChannelOption<?>, Object> getOptions() {
      return this.getOptions(
         super.getOptions(),
         RxtxChannelOption.BAUD_RATE,
         RxtxChannelOption.DTR,
         RxtxChannelOption.RTS,
         RxtxChannelOption.STOP_BITS,
         RxtxChannelOption.DATA_BITS,
         RxtxChannelOption.PARITY_BIT,
         RxtxChannelOption.WAIT_TIME
      );
   }

   @Override
   public <T> T getOption(ChannelOption<T> option) {
      if (option == RxtxChannelOption.BAUD_RATE) {
         return (T)this.getBaudrate();
      } else if (option == RxtxChannelOption.DTR) {
         return (T)this.isDtr();
      } else if (option == RxtxChannelOption.RTS) {
         return (T)this.isRts();
      } else if (option == RxtxChannelOption.STOP_BITS) {
         return (T)this.getStopbits();
      } else if (option == RxtxChannelOption.DATA_BITS) {
         return (T)this.getDatabits();
      } else if (option == RxtxChannelOption.PARITY_BIT) {
         return (T)this.getParitybit();
      } else if (option == RxtxChannelOption.WAIT_TIME) {
         return (T)this.getWaitTimeMillis();
      } else {
         return (T)(option == RxtxChannelOption.READ_TIMEOUT ? this.getReadTimeout() : super.getOption(option));
      }
   }

   @Override
   public <T> boolean setOption(ChannelOption<T> option, T value) {
      this.validate(option, value);
      if (option == RxtxChannelOption.BAUD_RATE) {
         this.setBaudrate((Integer)value);
      } else if (option == RxtxChannelOption.DTR) {
         this.setDtr((Boolean)value);
      } else if (option == RxtxChannelOption.RTS) {
         this.setRts((Boolean)value);
      } else if (option == RxtxChannelOption.STOP_BITS) {
         this.setStopbits((RxtxChannelConfig.Stopbits)value);
      } else if (option == RxtxChannelOption.DATA_BITS) {
         this.setDatabits((RxtxChannelConfig.Databits)value);
      } else if (option == RxtxChannelOption.PARITY_BIT) {
         this.setParitybit((RxtxChannelConfig.Paritybit)value);
      } else if (option == RxtxChannelOption.WAIT_TIME) {
         this.setWaitTimeMillis((Integer)value);
      } else {
         if (option != RxtxChannelOption.READ_TIMEOUT) {
            return super.setOption(option, value);
         }

         this.setReadTimeout((Integer)value);
      }

      return true;
   }

   @Override
   public RxtxChannelConfig setBaudrate(int baudrate) {
      this.baudrate = baudrate;
      return this;
   }

   @Override
   public RxtxChannelConfig setStopbits(RxtxChannelConfig.Stopbits stopbits) {
      this.stopbits = stopbits;
      return this;
   }

   @Override
   public RxtxChannelConfig setDatabits(RxtxChannelConfig.Databits databits) {
      this.databits = databits;
      return this;
   }

   @Override
   public RxtxChannelConfig setParitybit(RxtxChannelConfig.Paritybit paritybit) {
      this.paritybit = paritybit;
      return this;
   }

   @Override
   public int getBaudrate() {
      return this.baudrate;
   }

   @Override
   public RxtxChannelConfig.Stopbits getStopbits() {
      return this.stopbits;
   }

   @Override
   public RxtxChannelConfig.Databits getDatabits() {
      return this.databits;
   }

   @Override
   public RxtxChannelConfig.Paritybit getParitybit() {
      return this.paritybit;
   }

   @Override
   public boolean isDtr() {
      return this.dtr;
   }

   @Override
   public RxtxChannelConfig setDtr(boolean dtr) {
      this.dtr = dtr;
      return this;
   }

   @Override
   public boolean isRts() {
      return this.rts;
   }

   @Override
   public RxtxChannelConfig setRts(boolean rts) {
      this.rts = rts;
      return this;
   }

   @Override
   public int getWaitTimeMillis() {
      return this.waitTime;
   }

   @Override
   public RxtxChannelConfig setWaitTimeMillis(int waitTimeMillis) {
      if (waitTimeMillis < 0) {
         throw new IllegalArgumentException("Wait time must be >= 0");
      } else {
         this.waitTime = waitTimeMillis;
         return this;
      }
   }

   @Override
   public RxtxChannelConfig setReadTimeout(int readTimeout) {
      if (readTimeout < 0) {
         throw new IllegalArgumentException("readTime must be >= 0");
      } else {
         this.readTimeout = readTimeout;
         return this;
      }
   }

   @Override
   public int getReadTimeout() {
      return this.readTimeout;
   }

   @Override
   public RxtxChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
      super.setConnectTimeoutMillis(connectTimeoutMillis);
      return this;
   }

   @Override
   public RxtxChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
      super.setMaxMessagesPerRead(maxMessagesPerRead);
      return this;
   }

   @Override
   public RxtxChannelConfig setWriteSpinCount(int writeSpinCount) {
      super.setWriteSpinCount(writeSpinCount);
      return this;
   }

   @Override
   public RxtxChannelConfig setAllocator(ByteBufAllocator allocator) {
      super.setAllocator(allocator);
      return this;
   }

   @Override
   public RxtxChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
      super.setRecvByteBufAllocator(allocator);
      return this;
   }

   @Override
   public RxtxChannelConfig setAutoRead(boolean autoRead) {
      super.setAutoRead(autoRead);
      return this;
   }

   @Override
   public RxtxChannelConfig setAutoClose(boolean autoClose) {
      super.setAutoClose(autoClose);
      return this;
   }

   @Override
   public RxtxChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
      super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
      return this;
   }

   @Override
   public RxtxChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
      super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
      return this;
   }

   @Override
   public RxtxChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
      super.setMessageSizeEstimator(estimator);
      return this;
   }
}
