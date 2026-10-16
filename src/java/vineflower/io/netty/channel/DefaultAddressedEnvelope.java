package io.netty.channel;

import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;
import java.net.SocketAddress;

public class DefaultAddressedEnvelope<M, A extends SocketAddress> implements AddressedEnvelope<M, A> {
   private final M message;
   private final A sender;
   private final A recipient;

   public DefaultAddressedEnvelope(M message, A recipient, A sender) {
      if (message == null) {
         throw new NullPointerException("message");
      } else {
         this.message = message;
         this.sender = sender;
         this.recipient = recipient;
      }
   }

   public DefaultAddressedEnvelope(M message, A recipient) {
      this(message, recipient, null);
   }

   @Override
   public M content() {
      return this.message;
   }

   @Override
   public A sender() {
      return this.sender;
   }

   @Override
   public A recipient() {
      return this.recipient;
   }

   @Override
   public int refCnt() {
      return this.message instanceof ReferenceCounted ? ((ReferenceCounted)this.message).refCnt() : 1;
   }

   public AddressedEnvelope<M, A> retain() {
      ReferenceCountUtil.retain(this.message);
      return this;
   }

   public AddressedEnvelope<M, A> retain(int increment) {
      ReferenceCountUtil.retain(this.message, increment);
      return this;
   }

   @Override
   public boolean release() {
      return ReferenceCountUtil.release(this.message);
   }

   @Override
   public boolean release(int decrement) {
      return ReferenceCountUtil.release(this.message, decrement);
   }

   @Override
   public String toString() {
      return this.sender != null
         ? StringUtil.simpleClassName(this) + '(' + this.sender + " => " + this.recipient + ", " + this.message + ')'
         : StringUtil.simpleClassName(this) + "(=> " + this.recipient + ", " + this.message + ')';
   }
}
