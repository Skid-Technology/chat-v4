package io.netty.handler.codec.http;

import io.netty.handler.codec.DecoderResult;

public class DefaultHttpObject implements HttpObject {
   private DecoderResult decoderResult = DecoderResult.SUCCESS;

   protected DefaultHttpObject() {
   }

   @Override
   public DecoderResult getDecoderResult() {
      return this.decoderResult;
   }

   @Override
   public void setDecoderResult(DecoderResult decoderResult) {
      if (decoderResult == null) {
         throw new NullPointerException("decoderResult");
      } else {
         this.decoderResult = decoderResult;
      }
   }
}
