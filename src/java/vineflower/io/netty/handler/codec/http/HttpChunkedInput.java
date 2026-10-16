package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedInput;

public class HttpChunkedInput implements ChunkedInput<HttpContent> {
   private final ChunkedInput<ByteBuf> input;
   private final LastHttpContent lastHttpContent;
   private boolean sentLastChunk;

   public HttpChunkedInput(ChunkedInput<ByteBuf> input) {
      this.input = input;
      this.lastHttpContent = LastHttpContent.EMPTY_LAST_CONTENT;
   }

   public HttpChunkedInput(ChunkedInput<ByteBuf> input, LastHttpContent lastHttpContent) {
      this.input = input;
      this.lastHttpContent = lastHttpContent;
   }

   @Override
   public boolean isEndOfInput() throws Exception {
      return this.input.isEndOfInput() ? this.sentLastChunk : false;
   }

   @Override
   public void close() throws Exception {
      this.input.close();
   }

   public HttpContent readChunk(ChannelHandlerContext ctx) throws Exception {
      if (this.input.isEndOfInput()) {
         if (this.sentLastChunk) {
            return null;
         } else {
            this.sentLastChunk = true;
            return this.lastHttpContent;
         }
      } else {
         ByteBuf buf = this.input.readChunk(ctx);
         return new DefaultHttpContent(buf);
      }
   }
}
