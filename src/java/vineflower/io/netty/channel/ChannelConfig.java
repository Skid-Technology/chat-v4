package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import java.util.Map;

public interface ChannelConfig {
   Map<ChannelOption<?>, Object> getOptions();

   boolean setOptions(Map<ChannelOption<?>, ?> var1);

   <T> T getOption(ChannelOption<T> var1);

   <T> boolean setOption(ChannelOption<T> var1, T var2);

   int getConnectTimeoutMillis();

   ChannelConfig setConnectTimeoutMillis(int var1);

   int getMaxMessagesPerRead();

   ChannelConfig setMaxMessagesPerRead(int var1);

   int getWriteSpinCount();

   ChannelConfig setWriteSpinCount(int var1);

   ByteBufAllocator getAllocator();

   ChannelConfig setAllocator(ByteBufAllocator var1);

   RecvByteBufAllocator getRecvByteBufAllocator();

   ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator var1);

   boolean isAutoRead();

   ChannelConfig setAutoRead(boolean var1);

   @Deprecated
   boolean isAutoClose();

   @Deprecated
   ChannelConfig setAutoClose(boolean var1);

   int getWriteBufferHighWaterMark();

   ChannelConfig setWriteBufferHighWaterMark(int var1);

   int getWriteBufferLowWaterMark();

   ChannelConfig setWriteBufferLowWaterMark(int var1);

   MessageSizeEstimator getMessageSizeEstimator();

   ChannelConfig setMessageSizeEstimator(MessageSizeEstimator var1);
}
