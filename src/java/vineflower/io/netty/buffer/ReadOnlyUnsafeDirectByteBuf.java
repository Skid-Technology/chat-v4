package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

final class ReadOnlyUnsafeDirectByteBuf extends ReadOnlyByteBufferBuf {
   private static final boolean NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
   private final long memoryAddress;

   ReadOnlyUnsafeDirectByteBuf(ByteBufAllocator allocator, ByteBuffer buffer) {
      super(allocator, buffer);
      this.memoryAddress = PlatformDependent.directBufferAddress(buffer);
   }

   @Override
   protected byte _getByte(int index) {
      return PlatformDependent.getByte(this.addr(index));
   }

   @Override
   protected short _getShort(int index) {
      short v = PlatformDependent.getShort(this.addr(index));
      return NATIVE_ORDER ? v : Short.reverseBytes(v);
   }

   @Override
   protected int _getUnsignedMedium(int index) {
      long addr = this.addr(index);
      return (PlatformDependent.getByte(addr) & 0xFF) << 16 | (PlatformDependent.getByte(addr + 1L) & 0xFF) << 8 | PlatformDependent.getByte(addr + 2L) & 0xFF;
   }

   @Override
   protected int _getInt(int index) {
      int v = PlatformDependent.getInt(this.addr(index));
      return NATIVE_ORDER ? v : Integer.reverseBytes(v);
   }

   @Override
   protected long _getLong(int index) {
      long v = PlatformDependent.getLong(this.addr(index));
      return NATIVE_ORDER ? v : Long.reverseBytes(v);
   }

   @Override
   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
      this.checkIndex(index, length);
      if (dst == null) {
         throw new NullPointerException("dst");
      } else if (dstIndex >= 0 && dstIndex <= dst.capacity() - length) {
         if (dst.hasMemoryAddress()) {
            PlatformDependent.copyMemory(this.addr(index), dst.memoryAddress() + dstIndex, length);
         } else if (dst.hasArray()) {
            PlatformDependent.copyMemory(this.addr(index), dst.array(), dst.arrayOffset() + dstIndex, length);
         } else {
            dst.setBytes(dstIndex, this, index, length);
         }

         return this;
      } else {
         throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
      }
   }

   @Override
   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
      this.checkIndex(index, length);
      if (dst == null) {
         throw new NullPointerException("dst");
      } else if (dstIndex >= 0 && dstIndex <= dst.length - length) {
         if (length != 0) {
            PlatformDependent.copyMemory(this.addr(index), dst, dstIndex, length);
         }

         return this;
      } else {
         throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", dstIndex, length, dst.length));
      }
   }

   @Override
   public ByteBuf getBytes(int index, ByteBuffer dst) {
      this.checkIndex(index);
      if (dst == null) {
         throw new NullPointerException("dst");
      } else {
         int bytesToCopy = Math.min(this.capacity() - index, dst.remaining());
         ByteBuffer tmpBuf = this.internalNioBuffer();
         ((Buffer)tmpBuf).clear().position(index).limit(index + bytesToCopy);
         dst.put(tmpBuf);
         return this;
      }
   }

   @Override
   public ByteBuf copy(int index, int length) {
      this.checkIndex(index, length);
      ByteBuf copy = this.alloc().directBuffer(length, this.maxCapacity());
      if (length != 0) {
         if (copy.hasMemoryAddress()) {
            PlatformDependent.copyMemory(this.addr(index), copy.memoryAddress(), length);
            copy.setIndex(0, length);
         } else {
            copy.writeBytes(this, index, length);
         }
      }

      return copy;
   }

   private long addr(int index) {
      return this.memoryAddress + index;
   }
}
