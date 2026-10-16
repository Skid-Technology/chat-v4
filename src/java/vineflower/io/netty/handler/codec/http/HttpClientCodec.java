package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.PrematureChannelClosureException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

public final class HttpClientCodec extends CombinedChannelDuplexHandler<HttpResponseDecoder, HttpRequestEncoder> {
   private final Queue<HttpMethod> queue = new ArrayDeque<>();
   private boolean done;
   private final AtomicLong requestResponseCounter = new AtomicLong();
   private final boolean failOnMissingResponse;

   public HttpClientCodec() {
      this(4096, 8192, 8192, false);
   }

   public void setSingleDecode(boolean singleDecode) {
      this.inboundHandler().setSingleDecode(singleDecode);
   }

   public boolean isSingleDecode() {
      return this.inboundHandler().isSingleDecode();
   }

   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
      this(maxInitialLineLength, maxHeaderSize, maxChunkSize, false);
   }

   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse) {
      this(maxInitialLineLength, maxHeaderSize, maxChunkSize, failOnMissingResponse, true);
   }

   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse, boolean validateHeaders) {
      this.init(new HttpClientCodec.Decoder(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders), new HttpClientCodec.Encoder());
      this.failOnMissingResponse = failOnMissingResponse;
   }

   private final class Decoder extends HttpResponseDecoder {
      Decoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders) {
         super(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders);
      }

      @Override
      protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
         if (HttpClientCodec.this.done) {
            int readable = this.actualReadableBytes();
            if (readable == 0) {
               return;
            }

            out.add(buffer.readBytes(readable));
         } else {
            int oldSize = out.size();
            super.decode(ctx, buffer, out);
            if (HttpClientCodec.this.failOnMissingResponse) {
               int size = out.size();

               for (int i = oldSize; i < size; i++) {
                  this.decrement(out.get(i));
               }
            }
         }
      }

      private void decrement(Object msg) {
         if (msg != null) {
            if (msg instanceof LastHttpContent) {
               HttpClientCodec.this.requestResponseCounter.decrementAndGet();
            }
         }
      }

      @Override
      protected boolean isContentAlwaysEmpty(HttpMessage msg) {
         int statusCode = ((HttpResponse)msg).getStatus().code();
         if (statusCode == 100) {
            return true;
         } else {
            HttpMethod method = HttpClientCodec.this.queue.poll();
            char firstChar = method.name().charAt(0);
            switch (firstChar) {
               case 'C':
                  if (statusCode == 200 && HttpMethod.CONNECT.equals(method)) {
                     HttpClientCodec.this.done = true;
                     HttpClientCodec.this.queue.clear();
                     return true;
                  }
                  break;
               case 'H':
                  if (HttpMethod.HEAD.equals(method)) {
                     return true;
                  }
            }

            return super.isContentAlwaysEmpty(msg);
         }
      }

      @Override
      public void channelInactive(ChannelHandlerContext ctx) throws Exception {
         super.channelInactive(ctx);
         if (HttpClientCodec.this.failOnMissingResponse) {
            long missingResponses = HttpClientCodec.this.requestResponseCounter.get();
            if (missingResponses > 0L) {
               ctx.fireExceptionCaught(new PrematureChannelClosureException("channel gone inactive with " + missingResponses + " missing response(s)"));
            }
         }
      }
   }

   private final class Encoder extends HttpRequestEncoder {
      private Encoder() {
      }

      @Override
      protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
         if (msg instanceof HttpRequest && !HttpClientCodec.this.done) {
            HttpClientCodec.this.queue.offer(((HttpRequest)msg).getMethod());
         }

         super.encode(ctx, msg, out);
         if (HttpClientCodec.this.failOnMissingResponse && msg instanceof LastHttpContent) {
            HttpClientCodec.this.requestResponseCounter.incrementAndGet();
         }
      }
   }
}
