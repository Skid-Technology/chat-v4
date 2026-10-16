package io.netty.buffer;

import io.netty.util.Recycler;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

final class PooledHeapByteBuf extends PooledByteBuf<byte[]> {
   private static final Recycler<PooledHeapByteBuf> RECYCLER = new Recycler<PooledHeapByteBuf>() {
      protected PooledHeapByteBuf newObject(Recycler.Handle handle) {
         return new PooledHeapByteBuf(handle, 0);
      }
   };

   static PooledHeapByteBuf newInstance(int maxCapacity) {
      PooledHeapByteBuf buf = RECYCLER.get();
      buf.setRefCnt(1);
      buf.maxCapacity(maxCapacity);
      return buf;
   }

   private PooledHeapByteBuf(Recycler.Handle recyclerHandle, int maxCapacity) {
      super(recyclerHandle, maxCapacity);
   }

   @Override
   public boolean isDirect() {
      return false;
   }

   @Override
   protected byte _getByte(int index) {
      return this.memory[this.idx(index)];
   }

   @Override
   protected short _getShort(int index) {
      index = this.idx(index);
      return (short)(this.memory[index] << 8 | this.memory[index + 1] & 255);
   }

   @Override
   protected int _getUnsignedMedium(int index) {
      index = this.idx(index);
      return (this.memory[index] & 0xFF) << 16 | (this.memory[index + 1] & 0xFF) << 8 | this.memory[index + 2] & 0xFF;
   }

   @Override
   protected int _getInt(int index) {
      index = this.idx(index);
      return (this.memory[index] & 0xFF) << 24 | (this.memory[index + 1] & 0xFF) << 16 | (this.memory[index + 2] & 0xFF) << 8 | this.memory[index + 3] & 0xFF;
   }

   @Override
   protected long _getLong(int index) {
      index = this.idx(index);
      return (this.memory[index] & 255L) << 56
         | (this.memory[index + 1] & 255L) << 48
         | (this.memory[index + 2] & 255L) << 40
         | (this.memory[index + 3] & 255L) << 32
         | (this.memory[index + 4] & 255L) << 24
         | (this.memory[index + 5] & 255L) << 16
         | (this.memory[index + 6] & 255L) << 8
         | this.memory[index + 7] & 255L;
   }

   @Override
   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
      this.checkDstIndex(index, length, dstIndex, dst.capacity());
      if (dst.hasMemoryAddress()) {
         PlatformDependent.copyMemory(this.memory, this.idx(index), dst.memoryAddress() + dstIndex, length);
      } else if (dst.hasArray()) {
         this.getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
      } else {
         dst.setBytes(dstIndex, this.memory, this.idx(index), length);
      }

      return this;
   }

   @Override
   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
      this.checkDstIndex(index, length, dstIndex, dst.length);
      System.arraycopy(this.memory, this.idx(index), dst, dstIndex, length);
      return this;
   }

   @Override
   public ByteBuf getBytes(int index, ByteBuffer dst) {
      this.checkIndex(index);
      dst.put(this.memory, this.idx(index), Math.min(this.capacity() - index, dst.remaining()));
      return this;
   }

   @Override
   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
      this.checkIndex(index, length);
      out.write(this.memory, this.idx(index), length);
      return this;
   }

   @Override
   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
      return this.getBytes(index, out, length, false);
   }

   private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
      this.checkIndex(index, length);
      index = this.idx(index);
      ByteBuffer tmpBuf;
      if (internal) {
         tmpBuf = this.internalNioBuffer();
      } else {
         tmpBuf = ByteBuffer.wrap(this.memory);
      }

      return out.write((ByteBuffer)((Buffer)tmpBuf).clear().position(index).limit(index + length));
   }

   @Override
   public int readBytes(GatheringByteChannel out, int length) throws IOException {
      this.checkReadableBytes(length);
      int readBytes = this.getBytes(this.readerIndex, out, length, true);
      this.readerIndex += readBytes;
      return readBytes;
   }

   @Override
   protected void _setByte(int index, int value) {
      this.memory[this.idx(index)] = (byte)value;
   }

   @Override
   protected void _setShort(int index, int value) {
      index = this.idx(index);
      this.memory[index] = (byte)(value >>> 8);
      this.memory[index + 1] = (byte)value;
   }

   @Override
   protected void _setMedium(int index, int value) {
      index = this.idx(index);
      this.memory[index] = (byte)(value >>> 16);
      this.memory[index + 1] = (byte)(value >>> 8);
      this.memory[index + 2] = (byte)value;
   }

   @Override
   protected void _setInt(int index, int value) {
      index = this.idx(index);
      this.memory[index] = (byte)(value >>> 24);
      this.memory[index + 1] = (byte)(value >>> 16);
      this.memory[index + 2] = (byte)(value >>> 8);
      this.memory[index + 3] = (byte)value;
   }

   @Override
   protected void _setLong(int index, long value) {
      index = this.idx(index);
      this.memory[index] = (byte)(value >>> 56);
      this.memory[index + 1] = (byte)(value >>> 48);
      this.memory[index + 2] = (byte)(value >>> 40);
      this.memory[index + 3] = (byte)(value >>> 32);
      this.memory[index + 4] = (byte)(value >>> 24);
      this.memory[index + 5] = (byte)(value >>> 16);
      this.memory[index + 6] = (byte)(value >>> 8);
      this.memory[index + 7] = (byte)value;
   }

   @Override
   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
      this.checkSrcIndex(index, length, srcIndex, src.capacity());
      if (src.hasMemoryAddress()) {
         PlatformDependent.copyMemory(src.memoryAddress() + srcIndex, this.memory, this.idx(index), length);
      } else if (src.hasArray()) {
         this.setBytes(index, src.array(), src.arrayOffset() + srcIndex, length);
      } else {
         src.getBytes(srcIndex, this.memory, this.idx(index), length);
      }

      return this;
   }

   @Override
   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
      this.checkSrcIndex(index, length, srcIndex, src.length);
      System.arraycopy(src, srcIndex, this.memory, this.idx(index), length);
      return this;
   }

   @Override
   public ByteBuf setBytes(int index, ByteBuffer src) {
      int length = src.remaining();
      this.checkIndex(index, length);
      src.get(this.memory, this.idx(index), length);
      return this;
   }

   @Override
   public int setBytes(int index, InputStream in, int length) throws IOException {
      this.checkIndex(index, length);
      return in.read(this.memory, this.idx(index), length);
   }

   @Override
   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
      this.checkIndex(index, length);
      index = this.idx(index);

      try {
         return in.read((ByteBuffer)((Buffer)this.internalNioBuffer()).clear().position(index).limit(index + length));
      } catch (ClosedChannelException var5) {
         return -1;
      }
   }

   @Override
   public ByteBuf copy(int index, int length) {
      this.checkIndex(index, length);
      ByteBuf copy = this.alloc().heapBuffer(length, this.maxCapacity());
      copy.writeBytes(this.memory, this.idx(index), length);
      return copy;
   }

   @Override
   public int nioBufferCount() {
      return 1;
   }

   @Override
   public ByteBuffer[] nioBuffers(int index, int length) {
      return new ByteBuffer[]{this.nioBuffer(index, length)};
   }

   @Override
   public ByteBuffer nioBuffer(int index, int length) {
      this.checkIndex(index, length);
      index = this.idx(index);
      ByteBuffer buf = ByteBuffer.wrap(this.memory, index, length);
      return buf.slice();
   }

   @Override
   public ByteBuffer internalNioBuffer(int index, int length) {
      this.checkIndex(index, length);
      index = this.idx(index);
      return (ByteBuffer)((Buffer)this.internalNioBuffer()).clear().position(index).limit(index + length);
   }

   @Override
   public boolean hasArray() {
      return true;
   }

   @Override
   public byte[] array() {
      return this.memory;
   }

   @Override
   public int arrayOffset() {
      return this.offset;
   }

   @Override
   public boolean hasMemoryAddress() {
      return false;
   }

   @Override
   public long memoryAddress() {
      throw new UnsupportedOperationException();
   }

   protected ByteBuffer newInternalNioBuffer(byte[] memory) {
      return ByteBuffer.wrap(memory);
   }

   @Override
   protected Recycler<?> recycler() {
      return RECYCLER;
   }
}
