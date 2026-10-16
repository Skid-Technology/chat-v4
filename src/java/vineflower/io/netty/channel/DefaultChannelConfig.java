package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.nio.AbstractNioByteChannel;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DefaultChannelConfig implements ChannelConfig {
   private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = AdaptiveRecvByteBufAllocator.DEFAULT;
   private static final MessageSizeEstimator DEFAULT_MSG_SIZE_ESTIMATOR = DefaultMessageSizeEstimator.DEFAULT;
   private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
   protected final Channel channel;
   private volatile ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
   private volatile RecvByteBufAllocator rcvBufAllocator = DEFAULT_RCVBUF_ALLOCATOR;
   private volatile MessageSizeEstimator msgSizeEstimator = DEFAULT_MSG_SIZE_ESTIMATOR;
   private volatile int connectTimeoutMillis = 30000;
   private volatile int maxMessagesPerRead;
   private volatile int writeSpinCount = 16;
   private volatile boolean autoRead = true;
   private volatile boolean autoClose = true;
   private volatile int writeBufferHighWaterMark = 65536;
   private volatile int writeBufferLowWaterMark = 32768;

   public DefaultChannelConfig(Channel channel) {
      if (channel == null) {
         throw new NullPointerException("channel");
      } else {
         this.channel = channel;
         if (!(channel instanceof ServerChannel) && !(channel instanceof AbstractNioByteChannel)) {
            this.maxMessagesPerRead = 1;
         } else {
            this.maxMessagesPerRead = 16;
         }
      }
   }

   @Override
   public Map<ChannelOption<?>, Object> getOptions() {
      return this.getOptions(
         null,
         ChannelOption.CONNECT_TIMEOUT_MILLIS,
         ChannelOption.MAX_MESSAGES_PER_READ,
         ChannelOption.WRITE_SPIN_COUNT,
         ChannelOption.ALLOCATOR,
         ChannelOption.AUTO_READ,
         ChannelOption.AUTO_CLOSE,
         ChannelOption.RCVBUF_ALLOCATOR,
         ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK,
         ChannelOption.WRITE_BUFFER_LOW_WATER_MARK,
         ChannelOption.MESSAGE_SIZE_ESTIMATOR
      );
   }

   protected Map<ChannelOption<?>, Object> getOptions(Map<ChannelOption<?>, Object> result, ChannelOption<?>... options) {
      if (result == null) {
         result = new IdentityHashMap<>();
      }

      for (ChannelOption<?> o : options) {
         result.put(o, this.getOption(o));
      }

      return result;
   }

   @Override
   public boolean setOptions(Map<ChannelOption<?>, ?> options) {
      if (options == null) {
         throw new NullPointerException("options");
      } else {
         boolean setAllOptions = true;

         for (Entry<ChannelOption<?>, ?> e : options.entrySet()) {
            if (!this.setOption(e.getKey(), e.getValue())) {
               setAllOptions = false;
            }
         }

         return setAllOptions;
      }
   }

   @Override
   public <T> T getOption(ChannelOption<T> option) {
      if (option == null) {
         throw new NullPointerException("option");
      } else if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
         return (T)this.getConnectTimeoutMillis();
      } else if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
         return (T)this.getMaxMessagesPerRead();
      } else if (option == ChannelOption.WRITE_SPIN_COUNT) {
         return (T)this.getWriteSpinCount();
      } else if (option == ChannelOption.ALLOCATOR) {
         return (T)this.getAllocator();
      } else if (option == ChannelOption.RCVBUF_ALLOCATOR) {
         return (T)this.getRecvByteBufAllocator();
      } else if (option == ChannelOption.AUTO_READ) {
         return (T)this.isAutoRead();
      } else if (option == ChannelOption.AUTO_CLOSE) {
         return (T)this.isAutoClose();
      } else if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
         return (T)this.getWriteBufferHighWaterMark();
      } else if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
         return (T)this.getWriteBufferLowWaterMark();
      } else {
         return (T)(option == ChannelOption.MESSAGE_SIZE_ESTIMATOR ? this.getMessageSizeEstimator() : null);
      }
   }

   @Override
   public <T> boolean setOption(ChannelOption<T> option, T value) {
      this.validate(option, value);
      if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
         this.setConnectTimeoutMillis((Integer)value);
      } else if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
         this.setMaxMessagesPerRead((Integer)value);
      } else if (option == ChannelOption.WRITE_SPIN_COUNT) {
         this.setWriteSpinCount((Integer)value);
      } else if (option == ChannelOption.ALLOCATOR) {
         this.setAllocator((ByteBufAllocator)value);
      } else if (option == ChannelOption.RCVBUF_ALLOCATOR) {
         this.setRecvByteBufAllocator((RecvByteBufAllocator)value);
      } else if (option == ChannelOption.AUTO_READ) {
         this.setAutoRead((Boolean)value);
      } else if (option == ChannelOption.AUTO_CLOSE) {
         this.setAutoClose((Boolean)value);
      } else if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
         this.setWriteBufferHighWaterMark((Integer)value);
      } else if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
         this.setWriteBufferLowWaterMark((Integer)value);
      } else {
         if (option != ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
            return false;
         }

         this.setMessageSizeEstimator((MessageSizeEstimator)value);
      }

      return true;
   }

   protected <T> void validate(ChannelOption<T> option, T value) {
      if (option == null) {
         throw new NullPointerException("option");
      } else {
         option.validate(value);
      }
   }

   @Override
   public int getConnectTimeoutMillis() {
      return this.connectTimeoutMillis;
   }

   @Override
   public ChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
      if (connectTimeoutMillis < 0) {
         throw new IllegalArgumentException(String.format("connectTimeoutMillis: %d (expected: >= 0)", connectTimeoutMillis));
      } else {
         this.connectTimeoutMillis = connectTimeoutMillis;
         return this;
      }
   }

   @Override
   public int getMaxMessagesPerRead() {
      return this.maxMessagesPerRead;
   }

   @Override
   public ChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
      if (maxMessagesPerRead <= 0) {
         throw new IllegalArgumentException("maxMessagesPerRead: " + maxMessagesPerRead + " (expected: > 0)");
      } else {
         this.maxMessagesPerRead = maxMessagesPerRead;
         return this;
      }
   }

   @Override
   public int getWriteSpinCount() {
      return this.writeSpinCount;
   }

   @Override
   public ChannelConfig setWriteSpinCount(int writeSpinCount) {
      if (writeSpinCount <= 0) {
         throw new IllegalArgumentException("writeSpinCount must be a positive integer.");
      } else {
         this.writeSpinCount = writeSpinCount;
         return this;
      }
   }

   @Override
   public ByteBufAllocator getAllocator() {
      return this.allocator;
   }

   @Override
   public ChannelConfig setAllocator(ByteBufAllocator allocator) {
      if (allocator == null) {
         throw new NullPointerException("allocator");
      } else {
         this.allocator = allocator;
         return this;
      }
   }

   @Override
   public RecvByteBufAllocator getRecvByteBufAllocator() {
      return this.rcvBufAllocator;
   }

   @Override
   public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
      if (allocator == null) {
         throw new NullPointerException("allocator");
      } else {
         this.rcvBufAllocator = allocator;
         return this;
      }
   }

   @Override
   public boolean isAutoRead() {
      return this.autoRead;
   }

   @Override
   public ChannelConfig setAutoRead(boolean autoRead) {
      boolean oldAutoRead = this.autoRead;
      this.autoRead = autoRead;
      if (autoRead && !oldAutoRead) {
         this.channel.read();
      } else if (!autoRead && oldAutoRead) {
         this.autoReadCleared();
      }

      return this;
   }

   protected void autoReadCleared() {
   }

   @Override
   public boolean isAutoClose() {
      return this.autoClose;
   }

   @Override
   public ChannelConfig setAutoClose(boolean autoClose) {
      this.autoClose = autoClose;
      return this;
   }

   @Override
   public int getWriteBufferHighWaterMark() {
      return this.writeBufferHighWaterMark;
   }

   @Override
   public ChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
      if (writeBufferHighWaterMark < this.getWriteBufferLowWaterMark()) {
         throw new IllegalArgumentException(
            "writeBufferHighWaterMark cannot be less than writeBufferLowWaterMark (" + this.getWriteBufferLowWaterMark() + "): " + writeBufferHighWaterMark
         );
      } else if (writeBufferHighWaterMark < 0) {
         throw new IllegalArgumentException("writeBufferHighWaterMark must be >= 0");
      } else {
         this.writeBufferHighWaterMark = writeBufferHighWaterMark;
         return this;
      }
   }

   @Override
   public int getWriteBufferLowWaterMark() {
      return this.writeBufferLowWaterMark;
   }

   @Override
   public ChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
      if (writeBufferLowWaterMark > this.getWriteBufferHighWaterMark()) {
         throw new IllegalArgumentException(
            "writeBufferLowWaterMark cannot be greater than writeBufferHighWaterMark (" + this.getWriteBufferHighWaterMark() + "): " + writeBufferLowWaterMark
         );
      } else if (writeBufferLowWaterMark < 0) {
         throw new IllegalArgumentException("writeBufferLowWaterMark must be >= 0");
      } else {
         this.writeBufferLowWaterMark = writeBufferLowWaterMark;
         return this;
      }
   }

   @Override
   public MessageSizeEstimator getMessageSizeEstimator() {
      return this.msgSizeEstimator;
   }

   @Override
   public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
      if (estimator == null) {
         throw new NullPointerException("estimator");
      } else {
         this.msgSizeEstimator = estimator;
         return this;
      }
   }
}
