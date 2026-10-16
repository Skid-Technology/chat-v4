package io.netty.buffer;

import io.netty.util.internal.StringUtil;

final class PoolChunkList<T> {
   private final PoolArena<T> arena;
   private final PoolChunkList<T> nextList;
   PoolChunkList<T> prevList;
   private final int minUsage;
   private final int maxUsage;
   private PoolChunk<T> head;

   PoolChunkList(PoolArena<T> arena, PoolChunkList<T> nextList, int minUsage, int maxUsage) {
      this.arena = arena;
      this.nextList = nextList;
      this.minUsage = minUsage;
      this.maxUsage = maxUsage;
   }

   boolean allocate(PooledByteBuf<T> buf, int reqCapacity, int normCapacity) {
      if (this.head == null) {
         return false;
      } else {
         PoolChunk<T> cur = this.head;

         do {
            long handle = cur.allocate(normCapacity);
            if (handle >= 0L) {
               cur.initBuf(buf, handle, reqCapacity);
               if (cur.usage() >= this.maxUsage) {
                  this.remove(cur);
                  this.nextList.add(cur);
               }

               return true;
            }

            cur = cur.next;
         } while (cur != null);

         return false;
      }
   }

   void free(PoolChunk<T> chunk, long handle) {
      chunk.free(handle);
      if (chunk.usage() < this.minUsage) {
         this.remove(chunk);
         if (this.prevList == null) {
            assert chunk.usage() == 0;

            this.arena.destroyChunk(chunk);
         } else {
            this.prevList.add(chunk);
         }
      }
   }

   void add(PoolChunk<T> chunk) {
      if (chunk.usage() >= this.maxUsage) {
         this.nextList.add(chunk);
      } else {
         chunk.parent = this;
         if (this.head == null) {
            this.head = chunk;
            chunk.prev = null;
            chunk.next = null;
         } else {
            chunk.prev = null;
            chunk.next = this.head;
            this.head.prev = chunk;
            this.head = chunk;
         }
      }
   }

   private void remove(PoolChunk<T> cur) {
      if (cur == this.head) {
         this.head = cur.next;
         if (this.head != null) {
            this.head.prev = null;
         }
      } else {
         PoolChunk<T> next = cur.next;
         cur.prev.next = next;
         if (next != null) {
            next.prev = cur.prev;
         }
      }
   }

   @Override
   public String toString() {
      if (this.head == null) {
         return "none";
      } else {
         StringBuilder buf = new StringBuilder();
         PoolChunk<T> cur = this.head;

         while (true) {
            buf.append(cur);
            cur = cur.next;
            if (cur == null) {
               return buf.toString();
            }

            buf.append(StringUtil.NEWLINE);
         }
      }
   }
}
