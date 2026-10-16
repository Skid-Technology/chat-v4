package io.netty.channel.group;

import io.netty.channel.Channel;
import java.util.Set;

public interface ChannelGroup extends Set<Channel>, Comparable<ChannelGroup> {
   String name();

   ChannelGroupFuture write(Object var1);

   ChannelGroupFuture write(Object var1, ChannelMatcher var2);

   ChannelGroup flush();

   ChannelGroup flush(ChannelMatcher var1);

   ChannelGroupFuture writeAndFlush(Object var1);

   @Deprecated
   ChannelGroupFuture flushAndWrite(Object var1);

   ChannelGroupFuture writeAndFlush(Object var1, ChannelMatcher var2);

   @Deprecated
   ChannelGroupFuture flushAndWrite(Object var1, ChannelMatcher var2);

   ChannelGroupFuture disconnect();

   ChannelGroupFuture disconnect(ChannelMatcher var1);

   ChannelGroupFuture close();

   ChannelGroupFuture close(ChannelMatcher var1);

   @Deprecated
   ChannelGroupFuture deregister();

   @Deprecated
   ChannelGroupFuture deregister(ChannelMatcher var1);
}
