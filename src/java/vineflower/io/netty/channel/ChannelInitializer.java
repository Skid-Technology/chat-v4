package io.netty.channel;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

@ChannelHandler.Sharable
public abstract class ChannelInitializer<C extends Channel> extends ChannelInboundHandlerAdapter {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelInitializer.class);

   protected abstract void initChannel(C var1) throws Exception;

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void channelRegistered(ChannelHandlerContext ctx) throws Exception {
      ChannelPipeline pipeline = ctx.pipeline();
      boolean success = false;
      boolean var8 = false /* VF: Semaphore variable */;

      label65: {
         try {
            var8 = true;
            this.initChannel((C)ctx.channel());
            pipeline.remove(this);
            ctx.fireChannelRegistered();
            success = true;
            var8 = false;
            break label65;
         } catch (Throwable var9) {
            logger.warn("Failed to initialize a channel. Closing: " + ctx.channel(), var9);
            var8 = false;
         } finally {
            if (var8) {
               if (pipeline.context(this) != null) {
                  pipeline.remove(this);
               }

               ctx.close();
            }
         }

         if (pipeline.context(this) != null) {
            pipeline.remove(this);
         }

         ctx.close();
         return;
      }

      if (pipeline.context(this) != null) {
         pipeline.remove(this);
      }
   }
}
