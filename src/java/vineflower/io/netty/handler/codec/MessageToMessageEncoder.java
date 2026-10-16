package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.RecyclableArrayList;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

public abstract class MessageToMessageEncoder<I> extends ChannelOutboundHandlerAdapter {
   private final TypeParameterMatcher matcher;

   protected MessageToMessageEncoder() {
      this.matcher = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
   }

   protected MessageToMessageEncoder(Class<? extends I> outboundMessageType) {
      this.matcher = TypeParameterMatcher.get(outboundMessageType);
   }

   public boolean acceptOutboundMessage(Object msg) throws Exception {
      return this.matcher.match(msg);
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      RecyclableArrayList out = null;
      boolean var20 = false /* VF: Semaphore variable */;

      try {
         var20 = true;
         if (this.acceptOutboundMessage(msg)) {
            out = RecyclableArrayList.newInstance();
            I cast = (I)msg;

            try {
               this.encode(ctx, cast, out);
            } finally {
               ReferenceCountUtil.release(msg);
            }

            if (out.isEmpty()) {
               out.recycle();
               out = null;
               throw new EncoderException(StringUtil.simpleClassName(this) + " must produce at least one message.");
            }

            var20 = false;
         } else {
            ctx.write(msg, promise);
            var20 = false;
         }
      } catch (EncoderException var26) {
         throw var26;
      } catch (Throwable var27) {
         throw new EncoderException(var27);
      } finally {
         if (var20) {
            if (out != null) {
               int sizeMinusOne = out.size() - 1;
               if (sizeMinusOne == 0) {
                  ctx.write(out.get(0), promise);
               } else if (sizeMinusOne > 0) {
                  ChannelPromise voidPromise = ctx.voidPromise();
                  boolean isVoidPromise = promise == voidPromise;

                  for (int i = 0; i < sizeMinusOne; i++) {
                     ChannelPromise p;
                     if (isVoidPromise) {
                        p = voidPromise;
                     } else {
                        p = ctx.newPromise();
                     }

                     ctx.write(out.get(i), p);
                  }

                  ctx.write(out.get(sizeMinusOne), promise);
               }

               out.recycle();
            }
         }
      }

      if (out != null) {
         int sizeMinusOne = out.size() - 1;
         if (sizeMinusOne == 0) {
            ctx.write(out.get(0), promise);
         } else if (sizeMinusOne > 0) {
            ChannelPromise voidPromise = ctx.voidPromise();
            boolean isVoidPromise = promise == voidPromise;

            for (int i = 0; i < sizeMinusOne; i++) {
               ChannelPromise p;
               if (isVoidPromise) {
                  p = voidPromise;
               } else {
                  p = ctx.newPromise();
               }

               ctx.write(out.get(i), p);
            }

            ctx.write(out.get(sizeMinusOne), promise);
         }

         out.recycle();
      }
   }

   protected abstract void encode(ChannelHandlerContext var1, I var2, List<Object> var3) throws Exception;
}
