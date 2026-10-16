package io.netty.buffer;

import io.netty.util.ResourceLeak;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;

public abstract class AbstractByteBufAllocator implements ByteBufAllocator {
   private static final int DEFAULT_INITIAL_CAPACITY = 256;
   private static final int DEFAULT_MAX_COMPONENTS = 16;
   private final boolean directByDefault;
   private final ByteBuf emptyBuf;

   protected static ByteBuf toLeakAwareBuffer(ByteBuf buf) {
      switch (ResourceLeakDetector.getLevel()) {
         case SIMPLE:
            ResourceLeak leakx = AbstractByteBuf.leakDetector.open(buf);
            if (leakx != null) {
               buf = new SimpleLeakAwareByteBuf(buf, leakx);
            }
            break;
         case ADVANCED:
         case PARANOID:
            ResourceLeak leak = AbstractByteBuf.leakDetector.open(buf);
            if (leak != null) {
               buf = new AdvancedLeakAwareByteBuf(buf, leak);
            }
      }

      return buf;
   }

   protected AbstractByteBufAllocator() {
      this(false);
   }

   protected AbstractByteBufAllocator(boolean preferDirect) {
      this.directByDefault = preferDirect && PlatformDependent.hasUnsafe();
      this.emptyBuf = new EmptyByteBuf(this);
   }

   @Override
   public ByteBuf buffer() {
      return this.directByDefault ? this.directBuffer() : this.heapBuffer();
   }

   @Override
   public ByteBuf buffer(int initialCapacity) {
      return this.directByDefault ? this.directBuffer(initialCapacity) : this.heapBuffer(initialCapacity);
   }

   @Override
   public ByteBuf buffer(int initialCapacity, int maxCapacity) {
      return this.directByDefault ? this.directBuffer(initialCapacity, maxCapacity) : this.heapBuffer(initialCapacity, maxCapacity);
   }

   @Override
   public ByteBuf ioBuffer() {
      return PlatformDependent.hasUnsafe() ? this.directBuffer(256) : this.heapBuffer(256);
   }

   @Override
   public ByteBuf ioBuffer(int initialCapacity) {
      return PlatformDependent.hasUnsafe() ? this.directBuffer(initialCapacity) : this.heapBuffer(initialCapacity);
   }

   @Override
   public ByteBuf ioBuffer(int initialCapacity, int maxCapacity) {
      return PlatformDependent.hasUnsafe() ? this.directBuffer(initialCapacity, maxCapacity) : this.heapBuffer(initialCapacity, maxCapacity);
   }

   @Override
   public ByteBuf heapBuffer() {
      return this.heapBuffer(256, Integer.MAX_VALUE);
   }

   @Override
   public ByteBuf heapBuffer(int initialCapacity) {
      return this.heapBuffer(initialCapacity, Integer.MAX_VALUE);
   }

   @Override
   public ByteBuf heapBuffer(int initialCapacity, int maxCapacity) {
      if (initialCapacity == 0 && maxCapacity == 0) {
         return this.emptyBuf;
      } else {
         validate(initialCapacity, maxCapacity);
         return this.newHeapBuffer(initialCapacity, maxCapacity);
      }
   }

   @Override
   public ByteBuf directBuffer() {
      return this.directBuffer(256, Integer.MAX_VALUE);
   }

   @Override
   public ByteBuf directBuffer(int initialCapacity) {
      return this.directBuffer(initialCapacity, Integer.MAX_VALUE);
   }

   @Override
   public ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
      if (initialCapacity == 0 && maxCapacity == 0) {
         return this.emptyBuf;
      } else {
         validate(initialCapacity, maxCapacity);
         return this.newDirectBuffer(initialCapacity, maxCapacity);
      }
   }

   @Override
   public CompositeByteBuf compositeBuffer() {
      return this.directByDefault ? this.compositeDirectBuffer() : this.compositeHeapBuffer();
   }

   @Override
   public CompositeByteBuf compositeBuffer(int maxNumComponents) {
      return this.directByDefault ? this.compositeDirectBuffer(maxNumComponents) : this.compositeHeapBuffer(maxNumComponents);
   }

   @Override
   public CompositeByteBuf compositeHeapBuffer() {
      return this.compositeHeapBuffer(16);
   }

   @Override
   public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
      return new CompositeByteBuf(this, false, maxNumComponents);
   }

   @Override
   public CompositeByteBuf compositeDirectBuffer() {
      return this.compositeDirectBuffer(16);
   }

   @Override
   public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
      return new CompositeByteBuf(this, true, maxNumComponents);
   }

   private static void validate(int initialCapacity, int maxCapacity) {
      if (initialCapacity < 0) {
         throw new IllegalArgumentException("initialCapacity: " + initialCapacity + " (expectd: 0+)");
      } else if (initialCapacity > maxCapacity) {
         throw new IllegalArgumentException(String.format("initialCapacity: %d (expected: not greater than maxCapacity(%d)", initialCapacity, maxCapacity));
      }
   }

   protected abstract ByteBuf newHeapBuffer(int var1, int var2);

   protected abstract ByteBuf newDirectBuffer(int var1, int var2);

   @Override
   public String toString() {
      return StringUtil.simpleClassName(this) + "(directByDefault: " + this.directByDefault + ')';
   }
}
