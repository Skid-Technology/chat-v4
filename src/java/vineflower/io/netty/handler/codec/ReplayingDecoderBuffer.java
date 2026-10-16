package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.Signal;
import io.netty.util.internal.StringUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

final class ReplayingDecoderBuffer extends ByteBuf {
   private static final Signal REPLAY = ReplayingDecoder.REPLAY;
   private ByteBuf buffer;
   private boolean terminated;
   private SwappedByteBuf swapped;
   static final ReplayingDecoderBuffer EMPTY_BUFFER = new ReplayingDecoderBuffer(Unpooled.EMPTY_BUFFER);

   ReplayingDecoderBuffer() {
   }

   ReplayingDecoderBuffer(ByteBuf buffer) {
      this.setCumulation(buffer);
   }

   void setCumulation(ByteBuf buffer) {
      this.buffer = buffer;
   }

   void terminate() {
      this.terminated = true;
   }

   @Override
   public int capacity() {
      return this.terminated ? this.buffer.capacity() : Integer.MAX_VALUE;
   }

   @Override
   public ByteBuf capacity(int newCapacity) {
      reject();
      return this;
   }

   @Override
   public int maxCapacity() {
      return this.capacity();
   }

   @Override
   public ByteBufAllocator alloc() {
      return this.buffer.alloc();
   }

   @Override
   public boolean isDirect() {
      return this.buffer.isDirect();
   }

   @Override
   public boolean hasArray() {
      return false;
   }

   @Override
   public byte[] array() {
      throw new UnsupportedOperationException();
   }

   @Override
   public int arrayOffset() {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean hasMemoryAddress() {
      return false;
   }

   @Override
   public long memoryAddress() {
      throw new UnsupportedOperationException();
   }

   @Override
   public ByteBuf clear() {
      reject();
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      return this == obj;
   }

   @Override
   public int compareTo(ByteBuf buffer) {
      reject();
      return 0;
   }

   @Override
   public ByteBuf copy() {
      reject();
      return this;
   }

   @Override
   public ByteBuf copy(int index, int length) {
      this.checkIndex(index, length);
      return this.buffer.copy(index, length);
   }

   @Override
   public ByteBuf discardReadBytes() {
      reject();
      return this;
   }

   @Override
   public ByteBuf ensureWritable(int writableBytes) {
      reject();
      return this;
   }

   @Override
   public int ensureWritable(int minWritableBytes, boolean force) {
      reject();
      return 0;
   }

   @Override
   public ByteBuf duplicate() {
      reject();
      return this;
   }

   @Override
   public boolean getBoolean(int index) {
      this.checkIndex(index, 1);
      return this.buffer.getBoolean(index);
   }

   @Override
   public byte getByte(int index) {
      this.checkIndex(index, 1);
      return this.buffer.getByte(index);
   }

   @Override
   public short getUnsignedByte(int index) {
      this.checkIndex(index, 1);
      return this.buffer.getUnsignedByte(index);
   }

   @Override
   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
      this.checkIndex(index, length);
      this.buffer.getBytes(index, dst, dstIndex, length);
      return this;
   }

   @Override
   public ByteBuf getBytes(int index, byte[] dst) {
      this.checkIndex(index, dst.length);
      this.buffer.getBytes(index, dst);
      return this;
   }

   @Override
   public ByteBuf getBytes(int index, ByteBuffer dst) {
      reject();
      return this;
   }

   @Override
   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
      this.checkIndex(index, length);
      this.buffer.getBytes(index, dst, dstIndex, length);
      return this;
   }

   @Override
   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
      reject();
      return this;
   }

   @Override
   public ByteBuf getBytes(int index, ByteBuf dst) {
      reject();
      return this;
   }

   @Override
   public int getBytes(int index, GatheringByteChannel out, int length) {
      reject();
      return 0;
   }

   @Override
   public ByteBuf getBytes(int index, OutputStream out, int length) {
      reject();
      return this;
   }

   @Override
   public int getInt(int index) {
      this.checkIndex(index, 4);
      return this.buffer.getInt(index);
   }

   @Override
   public long getUnsignedInt(int index) {
      this.checkIndex(index, 4);
      return this.buffer.getUnsignedInt(index);
   }

   @Override
   public long getLong(int index) {
      this.checkIndex(index, 8);
      return this.buffer.getLong(index);
   }

   @Override
   public int getMedium(int index) {
      this.checkIndex(index, 3);
      return this.buffer.getMedium(index);
   }

   @Override
   public int getUnsignedMedium(int index) {
      this.checkIndex(index, 3);
      return this.buffer.getUnsignedMedium(index);
   }

   @Override
   public short getShort(int index) {
      this.checkIndex(index, 2);
      return this.buffer.getShort(index);
   }

   @Override
   public int getUnsignedShort(int index) {
      this.checkIndex(index, 2);
      return this.buffer.getUnsignedShort(index);
   }

   @Override
   public char getChar(int index) {
      this.checkIndex(index, 2);
      return this.buffer.getChar(index);
   }

   @Override
   public float getFloat(int index) {
      this.checkIndex(index, 4);
      return this.buffer.getFloat(index);
   }

   @Override
   public double getDouble(int index) {
      this.checkIndex(index, 8);
      return this.buffer.getDouble(index);
   }

   @Override
   public int hashCode() {
      reject();
      return 0;
   }

   @Override
   public int indexOf(int fromIndex, int toIndex, byte value) {
      if (fromIndex == toIndex) {
         return -1;
      } else if (Math.max(fromIndex, toIndex) > this.buffer.writerIndex()) {
         throw REPLAY;
      } else {
         return this.buffer.indexOf(fromIndex, toIndex, value);
      }
   }

   @Override
   public int bytesBefore(byte value) {
      int bytes = this.buffer.bytesBefore(value);
      if (bytes < 0) {
         throw REPLAY;
      } else {
         return bytes;
      }
   }

   @Override
   public int bytesBefore(int length, byte value) {
      int readerIndex = this.buffer.readerIndex();
      return this.bytesBefore(readerIndex, this.buffer.writerIndex() - readerIndex, value);
   }

   @Override
   public int bytesBefore(int index, int length, byte value) {
      int writerIndex = this.buffer.writerIndex();
      if (index >= writerIndex) {
         throw REPLAY;
      } else if (index <= writerIndex - length) {
         return this.buffer.bytesBefore(index, length, value);
      } else {
         int res = this.buffer.bytesBefore(index, writerIndex - index, value);
         if (res < 0) {
            throw REPLAY;
         } else {
            return res;
         }
      }
   }

   @Override
   public int forEachByte(ByteBufProcessor processor) {
      int ret = this.buffer.forEachByte(processor);
      if (ret < 0) {
         throw REPLAY;
      } else {
         return ret;
      }
   }

   @Override
   public int forEachByte(int index, int length, ByteBufProcessor processor) {
      int writerIndex = this.buffer.writerIndex();
      if (index >= writerIndex) {
         throw REPLAY;
      } else if (index <= writerIndex - length) {
         return this.buffer.forEachByte(index, length, processor);
      } else {
         int ret = this.buffer.forEachByte(index, writerIndex - index, processor);
         if (ret < 0) {
            throw REPLAY;
         } else {
            return ret;
         }
      }
   }

   @Override
   public int forEachByteDesc(ByteBufProcessor processor) {
      if (this.terminated) {
         return this.buffer.forEachByteDesc(processor);
      } else {
         reject();
         return 0;
      }
   }

   @Override
   public int forEachByteDesc(int index, int length, ByteBufProcessor processor) {
      if (index + length > this.buffer.writerIndex()) {
         throw REPLAY;
      } else {
         return this.buffer.forEachByteDesc(index, length, processor);
      }
   }

   @Override
   public ByteBuf markReaderIndex() {
      this.buffer.markReaderIndex();
      return this;
   }

   @Override
   public ByteBuf markWriterIndex() {
      reject();
      return this;
   }

   @Override
   public ByteOrder order() {
      return this.buffer.order();
   }

   @Override
   public ByteBuf order(ByteOrder endianness) {
      if (endianness == null) {
         throw new NullPointerException("endianness");
      } else if (endianness == this.order()) {
         return this;
      } else {
         SwappedByteBuf swapped = this.swapped;
         if (swapped == null) {
            this.swapped = swapped = new SwappedByteBuf(this);
         }

         return swapped;
      }
   }

   @Override
   public boolean isReadable() {
      return this.terminated ? this.buffer.isReadable() : true;
   }

   @Override
   public boolean isReadable(int size) {
      return this.terminated ? this.buffer.isReadable(size) : true;
   }

   @Override
   public int readableBytes() {
      return this.terminated ? this.buffer.readableBytes() : Integer.MAX_VALUE - this.buffer.readerIndex();
   }

   @Override
   public boolean readBoolean() {
      this.checkReadableBytes(1);
      return this.buffer.readBoolean();
   }

   @Override
   public byte readByte() {
      this.checkReadableBytes(1);
      return this.buffer.readByte();
   }

   @Override
   public short readUnsignedByte() {
      this.checkReadableBytes(1);
      return this.buffer.readUnsignedByte();
   }

   @Override
   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
      this.checkReadableBytes(length);
      this.buffer.readBytes(dst, dstIndex, length);
      return this;
   }

   @Override
   public ByteBuf readBytes(byte[] dst) {
      this.checkReadableBytes(dst.length);
      this.buffer.readBytes(dst);
      return this;
   }

   @Override
   public ByteBuf readBytes(ByteBuffer dst) {
      reject();
      return this;
   }

   @Override
   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
      this.checkReadableBytes(length);
      this.buffer.readBytes(dst, dstIndex, length);
      return this;
   }

   @Override
   public ByteBuf readBytes(ByteBuf dst, int length) {
      reject();
      return this;
   }

   @Override
   public ByteBuf readBytes(ByteBuf dst) {
      this.checkReadableBytes(dst.writableBytes());
      this.buffer.readBytes(dst);
      return this;
   }

   @Override
   public int readBytes(GatheringByteChannel out, int length) {
      reject();
      return 0;
   }

   @Override
   public ByteBuf readBytes(int length) {
      this.checkReadableBytes(length);
      return this.buffer.readBytes(length);
   }

   @Override
   public ByteBuf readSlice(int length) {
      this.checkReadableBytes(length);
      return this.buffer.readSlice(length);
   }

   @Override
   public ByteBuf readBytes(OutputStream out, int length) {
      reject();
      return this;
   }

   @Override
   public int readerIndex() {
      return this.buffer.readerIndex();
   }

   @Override
   public ByteBuf readerIndex(int readerIndex) {
      this.buffer.readerIndex(readerIndex);
      return this;
   }

   @Override
   public int readInt() {
      this.checkReadableBytes(4);
      return this.buffer.readInt();
   }

   @Override
   public long readUnsignedInt() {
      this.checkReadableBytes(4);
      return this.buffer.readUnsignedInt();
   }

   @Override
   public long readLong() {
      this.checkReadableBytes(8);
      return this.buffer.readLong();
   }

   @Override
   public int readMedium() {
      this.checkReadableBytes(3);
      return this.buffer.readMedium();
   }

   @Override
   public int readUnsignedMedium() {
      this.checkReadableBytes(3);
      return this.buffer.readUnsignedMedium();
   }

   @Override
   public short readShort() {
      this.checkReadableBytes(2);
      return this.buffer.readShort();
   }

   @Override
   public int readUnsignedShort() {
      this.checkReadableBytes(2);
      return this.buffer.readUnsignedShort();
   }

   @Override
   public char readChar() {
      this.checkReadableBytes(2);
      return this.buffer.readChar();
   }

   @Override
   public float readFloat() {
      this.checkReadableBytes(4);
      return this.buffer.readFloat();
   }

   @Override
   public double readDouble() {
      this.checkReadableBytes(8);
      return this.buffer.readDouble();
   }

   @Override
   public ByteBuf resetReaderIndex() {
      this.buffer.resetReaderIndex();
      return this;
   }

   @Override
   public ByteBuf resetWriterIndex() {
      reject();
      return this;
   }

   @Override
   public ByteBuf setBoolean(int index, boolean value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setByte(int index, int value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setBytes(int index, byte[] src) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setBytes(int index, ByteBuffer src) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setBytes(int index, ByteBuf src, int length) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setBytes(int index, ByteBuf src) {
      reject();
      return this;
   }

   @Override
   public int setBytes(int index, InputStream in, int length) {
      reject();
      return 0;
   }

   @Override
   public ByteBuf setZero(int index, int length) {
      reject();
      return this;
   }

   @Override
   public int setBytes(int index, ScatteringByteChannel in, int length) {
      reject();
      return 0;
   }

   @Override
   public ByteBuf setIndex(int readerIndex, int writerIndex) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setInt(int index, int value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setLong(int index, long value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setMedium(int index, int value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setShort(int index, int value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setChar(int index, int value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setFloat(int index, float value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf setDouble(int index, double value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf skipBytes(int length) {
      this.checkReadableBytes(length);
      this.buffer.skipBytes(length);
      return this;
   }

   @Override
   public ByteBuf slice() {
      reject();
      return this;
   }

   @Override
   public ByteBuf slice(int index, int length) {
      this.checkIndex(index, length);
      return this.buffer.slice(index, length);
   }

   @Override
   public int nioBufferCount() {
      return this.buffer.nioBufferCount();
   }

   @Override
   public ByteBuffer nioBuffer() {
      reject();
      return null;
   }

   @Override
   public ByteBuffer nioBuffer(int index, int length) {
      this.checkIndex(index, length);
      return this.buffer.nioBuffer(index, length);
   }

   @Override
   public ByteBuffer[] nioBuffers() {
      reject();
      return null;
   }

   @Override
   public ByteBuffer[] nioBuffers(int index, int length) {
      this.checkIndex(index, length);
      return this.buffer.nioBuffers(index, length);
   }

   @Override
   public ByteBuffer internalNioBuffer(int index, int length) {
      this.checkIndex(index, length);
      return this.buffer.internalNioBuffer(index, length);
   }

   @Override
   public String toString(int index, int length, Charset charset) {
      this.checkIndex(index, length);
      return this.buffer.toString(index, length, charset);
   }

   @Override
   public String toString(Charset charsetName) {
      reject();
      return null;
   }

   @Override
   public String toString() {
      return StringUtil.simpleClassName(this) + '(' + "ridx=" + this.readerIndex() + ", " + "widx=" + this.writerIndex() + ')';
   }

   @Override
   public boolean isWritable() {
      return false;
   }

   @Override
   public boolean isWritable(int size) {
      return false;
   }

   @Override
   public int writableBytes() {
      return 0;
   }

   @Override
   public int maxWritableBytes() {
      return 0;
   }

   @Override
   public ByteBuf writeBoolean(boolean value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeByte(int value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeBytes(byte[] src) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeBytes(ByteBuffer src) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeBytes(ByteBuf src, int length) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeBytes(ByteBuf src) {
      reject();
      return this;
   }

   @Override
   public int writeBytes(InputStream in, int length) {
      reject();
      return 0;
   }

   @Override
   public int writeBytes(ScatteringByteChannel in, int length) {
      reject();
      return 0;
   }

   @Override
   public ByteBuf writeInt(int value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeLong(long value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeMedium(int value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeZero(int length) {
      reject();
      return this;
   }

   @Override
   public int writerIndex() {
      return this.buffer.writerIndex();
   }

   @Override
   public ByteBuf writerIndex(int writerIndex) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeShort(int value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeChar(int value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeFloat(float value) {
      reject();
      return this;
   }

   @Override
   public ByteBuf writeDouble(double value) {
      reject();
      return this;
   }

   private void checkIndex(int index, int length) {
      if (index + length > this.buffer.writerIndex()) {
         throw REPLAY;
      }
   }

   private void checkReadableBytes(int readableBytes) {
      if (this.buffer.readableBytes() < readableBytes) {
         throw REPLAY;
      }
   }

   @Override
   public ByteBuf discardSomeReadBytes() {
      reject();
      return this;
   }

   @Override
   public int refCnt() {
      return this.buffer.refCnt();
   }

   @Override
   public ByteBuf retain() {
      reject();
      return this;
   }

   @Override
   public ByteBuf retain(int increment) {
      reject();
      return this;
   }

   @Override
   public boolean release() {
      reject();
      return false;
   }

   @Override
   public boolean release(int decrement) {
      reject();
      return false;
   }

   @Override
   public ByteBuf unwrap() {
      reject();
      return this;
   }

   private static void reject() {
      throw new UnsupportedOperationException("not a replayable operation");
   }

   static {
      EMPTY_BUFFER.terminate();
   }
}
