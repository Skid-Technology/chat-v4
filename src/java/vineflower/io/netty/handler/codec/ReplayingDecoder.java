package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Signal;
import io.netty.util.internal.RecyclableArrayList;
import io.netty.util.internal.StringUtil;
import java.util.List;

public abstract class ReplayingDecoder<S> extends ByteToMessageDecoder {
   static final Signal REPLAY = Signal.valueOf(ReplayingDecoder.class.getName() + ".REPLAY");
   private final ReplayingDecoderBuffer replayable = new ReplayingDecoderBuffer();
   private S state;
   private int checkpoint = -1;

   protected ReplayingDecoder() {
      this(null);
   }

   protected ReplayingDecoder(S initialState) {
      this.state = initialState;
   }

   protected void checkpoint() {
      this.checkpoint = this.internalBuffer().readerIndex();
   }

   protected void checkpoint(S state) {
      this.checkpoint();
      this.state(state);
   }

   protected S state() {
      return this.state;
   }

   protected S state(S newState) {
      S oldState = this.state;
      this.state = newState;
      return oldState;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      RecyclableArrayList out = RecyclableArrayList.newInstance();
      boolean var39 = false /* VF: Semaphore variable */;

      label324: {
         try {
            var39 = true;
            this.replayable.terminate();
            this.callDecode(ctx, this.internalBuffer(), out);
            this.decodeLast(ctx, this.replayable, out);
            var39 = false;
            break label324;
         } catch (Signal var43) {
            var43.expect(REPLAY);
            var39 = false;
         } catch (DecoderException var44) {
            throw var44;
         } catch (Exception var45) {
            throw new DecoderException(var45);
         } finally {
            if (var39) {
               try {
                  if (this.cumulation != null) {
                     this.cumulation.release();
                     this.cumulation = null;
                  }

                  int size = out.size();

                  for (int i = 0; i < size; i++) {
                     ctx.fireChannelRead(out.get(i));
                  }

                  if (size > 0) {
                     ctx.fireChannelReadComplete();
                  }

                  ctx.fireChannelInactive();
               } finally {
                  out.recycle();
               }
            }
         }

         try {
            if (this.cumulation != null) {
               this.cumulation.release();
               this.cumulation = null;
            }

            int size = out.size();

            for (int i = 0; i < size; i++) {
               ctx.fireChannelRead(out.get(i));
            }

            if (size > 0) {
               ctx.fireChannelReadComplete();
            }

            ctx.fireChannelInactive();
            return;
         } finally {
            out.recycle();
         }
      }

      try {
         if (this.cumulation != null) {
            this.cumulation.release();
            this.cumulation = null;
         }

         int size = out.size();

         for (int i = 0; i < size; i++) {
            ctx.fireChannelRead(out.get(i));
         }

         if (size > 0) {
            ctx.fireChannelReadComplete();
         }

         ctx.fireChannelInactive();
      } finally {
         out.recycle();
      }
   }

   @Override
   protected void callDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
      this.replayable.setCumulation(in);

      try {
         while (in.isReadable()) {
            int oldReaderIndex = this.checkpoint = in.readerIndex();
            int outSize = out.size();
            S oldState = this.state;
            int oldInputLength = in.readableBytes();

            try {
               this.decode(ctx, this.replayable, out);
               if (ctx.isRemoved()) {
                  break;
               }

               if (outSize == out.size()) {
                  if (oldInputLength == in.readableBytes() && oldState == this.state) {
                     throw new DecoderException(
                        StringUtil.simpleClassName(this.getClass())
                           + ".decode() must consume the inbound "
                           + "data or change its state if it did not decode anything."
                     );
                  }
                  continue;
               }
            } catch (Signal var10) {
               var10.expect(REPLAY);
               if (!ctx.isRemoved()) {
                  int checkpoint = this.checkpoint;
                  if (checkpoint >= 0) {
                     in.readerIndex(checkpoint);
                  }
               }
               break;
            }

            if (oldReaderIndex == in.readerIndex() && oldState == this.state) {
               throw new DecoderException(
                  StringUtil.simpleClassName(this.getClass())
                     + ".decode() method must consume the inbound data "
                     + "or change its state if it decoded something."
               );
            }

            if (this.isSingleDecode()) {
               break;
            }
         }
      } catch (DecoderException var11) {
         throw var11;
      } catch (Throwable var12) {
         throw new DecoderException(var12);
      }
   }
}
