package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;

public final class UnpooledByteBufAllocator extends AbstractByteBufAllocator {
   public static final UnpooledByteBufAllocator DEFAULT = new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());

   public UnpooledByteBufAllocator(boolean preferDirect) {
      super(preferDirect);
   }

   @Override
   protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
      return new UnpooledHeapByteBuf(this, initialCapacity, maxCapacity);
   }

   @Override
   protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
      ByteBuf buf;
      if (PlatformDependent.hasUnsafe()) {
         buf = new UnpooledUnsafeDirectByteBuf(this, initialCapacity, maxCapacity);
      } else {
         buf = new UnpooledDirectByteBuf(this, initialCapacity, maxCapacity);
      }

      return toLeakAwareBuffer(buf);
   }

   @Override
   public boolean isDirectBufferPooled() {
      return false;
   }
}
