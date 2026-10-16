package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {
   private List<ChannelTrafficShapingHandler.ToSend> messagesQueue = new LinkedList<>();

   public ChannelTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval, long maxTime) {
      super(writeLimit, readLimit, checkInterval, maxTime);
   }

   public ChannelTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval) {
      super(writeLimit, readLimit, checkInterval);
   }

   public ChannelTrafficShapingHandler(long writeLimit, long readLimit) {
      super(writeLimit, readLimit);
   }

   public ChannelTrafficShapingHandler(long checkInterval) {
      super(checkInterval);
   }

   @Override
   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      TrafficCounter trafficCounter = new TrafficCounter(this, ctx.executor(), "ChannelTC" + ctx.channel().hashCode(), this.checkInterval);
      this.setTrafficCounter(trafficCounter);
      trafficCounter.start();
   }

   @Override
   public synchronized void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      if (this.trafficCounter != null) {
         this.trafficCounter.stop();
      }

      for (ChannelTrafficShapingHandler.ToSend toSend : this.messagesQueue) {
         if (toSend.toSend instanceof ByteBuf) {
            ((ByteBuf)toSend.toSend).release();
         }
      }

      this.messagesQueue.clear();
   }

   @Override
   protected synchronized void submitWrite(final ChannelHandlerContext ctx, Object msg, long delay, ChannelPromise promise) {
      if (delay == 0L && this.messagesQueue.isEmpty()) {
         ctx.write(msg, promise);
      } else {
         ChannelTrafficShapingHandler.ToSend newToSend = new ChannelTrafficShapingHandler.ToSend(delay, msg, promise);
         this.messagesQueue.add(newToSend);
         ctx.executor().schedule(new Runnable() {
            @Override
            public void run() {
               ChannelTrafficShapingHandler.this.sendAllValid(ctx);
            }
         }, delay, TimeUnit.MILLISECONDS);
      }
   }

   private synchronized void sendAllValid(ChannelHandlerContext ctx) {
      while (!this.messagesQueue.isEmpty()) {
         ChannelTrafficShapingHandler.ToSend newToSend = this.messagesQueue.remove(0);
         if (newToSend.date > System.currentTimeMillis()) {
            this.messagesQueue.add(0, newToSend);
            break;
         }

         ctx.write(newToSend.toSend, newToSend.promise);
      }

      ctx.flush();
   }

   private static final class ToSend {
      final long date;
      final Object toSend;
      final ChannelPromise promise;

      private ToSend(long delay, Object toSend, ChannelPromise promise) {
         this.date = System.currentTimeMillis() + delay;
         this.toSend = toSend;
         this.promise = promise;
      }
   }
}
