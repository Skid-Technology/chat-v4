package io.netty.channel.local;

import io.netty.channel.AbstractServerChannel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import java.net.SocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;

public class LocalServerChannel extends AbstractServerChannel {
   private final ChannelConfig config = new DefaultChannelConfig(this);
   private final Queue<Object> inboundBuffer = new ArrayDeque<>();
   private final Runnable shutdownHook = new Runnable() {
      @Override
      public void run() {
         LocalServerChannel.this.unsafe().close(LocalServerChannel.this.unsafe().voidPromise());
      }
   };
   private volatile int state;
   private volatile LocalAddress localAddress;
   private volatile boolean acceptInProgress;

   @Override
   public ChannelConfig config() {
      return this.config;
   }

   public LocalAddress localAddress() {
      return (LocalAddress)super.localAddress();
   }

   public LocalAddress remoteAddress() {
      return (LocalAddress)super.remoteAddress();
   }

   @Override
   public boolean isOpen() {
      return this.state < 2;
   }

   @Override
   public boolean isActive() {
      return this.state == 1;
   }

   @Override
   protected boolean isCompatible(EventLoop loop) {
      return loop instanceof SingleThreadEventLoop;
   }

   @Override
   protected SocketAddress localAddress0() {
      return this.localAddress;
   }

   @Override
   protected void doRegister() throws Exception {
      ((SingleThreadEventExecutor)this.eventLoop()).addShutdownHook(this.shutdownHook);
   }

   @Override
   protected void doBind(SocketAddress localAddress) throws Exception {
      this.localAddress = LocalChannelRegistry.register(this, this.localAddress, localAddress);
      this.state = 1;
   }

   @Override
   protected void doClose() throws Exception {
      if (this.state <= 1) {
         if (this.localAddress != null) {
            LocalChannelRegistry.unregister(this.localAddress);
            this.localAddress = null;
         }

         this.state = 2;
      }
   }

   @Override
   protected void doDeregister() throws Exception {
      ((SingleThreadEventExecutor)this.eventLoop()).removeShutdownHook(this.shutdownHook);
   }

   @Override
   protected void doBeginRead() throws Exception {
      if (!this.acceptInProgress) {
         Queue<Object> inboundBuffer = this.inboundBuffer;
         if (inboundBuffer.isEmpty()) {
            this.acceptInProgress = true;
         } else {
            ChannelPipeline pipeline = this.pipeline();

            while (true) {
               Object m = inboundBuffer.poll();
               if (m == null) {
                  pipeline.fireChannelReadComplete();
                  return;
               }

               pipeline.fireChannelRead(m);
            }
         }
      }
   }

   LocalChannel serve(LocalChannel peer) {
      final LocalChannel child = new LocalChannel(this, peer);
      if (this.eventLoop().inEventLoop()) {
         this.serve0(child);
      } else {
         this.eventLoop().execute(new Runnable() {
            @Override
            public void run() {
               LocalServerChannel.this.serve0(child);
            }
         });
      }

      return child;
   }

   private void serve0(LocalChannel child) {
      this.inboundBuffer.add(child);
      if (this.acceptInProgress) {
         this.acceptInProgress = false;
         ChannelPipeline pipeline = this.pipeline();

         while (true) {
            Object m = this.inboundBuffer.poll();
            if (m == null) {
               pipeline.fireChannelReadComplete();
               break;
            }

            pipeline.fireChannelRead(m);
         }
      }
   }
}
