package io.netty.handler.stream;

import io.netty.channel.ChannelHandlerContext;

public interface ChunkedInput<B> {
   boolean isEndOfInput() throws Exception;

   void close() throws Exception;

   B readChunk(ChannelHandlerContext var1) throws Exception;
}
