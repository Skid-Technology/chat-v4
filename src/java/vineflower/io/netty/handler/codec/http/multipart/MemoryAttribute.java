package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelException;
import io.netty.handler.codec.http.HttpConstants;
import java.io.IOException;

public class MemoryAttribute extends AbstractMemoryHttpData implements Attribute {
   public MemoryAttribute(String name) {
      super(name, HttpConstants.DEFAULT_CHARSET, 0L);
   }

   public MemoryAttribute(String name, String value) throws IOException {
      super(name, HttpConstants.DEFAULT_CHARSET, 0L);
      this.setValue(value);
   }

   @Override
   public InterfaceHttpData.HttpDataType getHttpDataType() {
      return InterfaceHttpData.HttpDataType.Attribute;
   }

   @Override
   public String getValue() {
      return this.getByteBuf().toString(this.charset);
   }

   @Override
   public void setValue(String value) throws IOException {
      if (value == null) {
         throw new NullPointerException("value");
      } else {
         byte[] bytes = value.getBytes(this.charset.name());
         ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
         if (this.definedSize > 0L) {
            this.definedSize = buffer.readableBytes();
         }

         this.setContent(buffer);
      }
   }

   @Override
   public void addContent(ByteBuf buffer, boolean last) throws IOException {
      int localsize = buffer.readableBytes();
      if (this.definedSize > 0L && this.definedSize < this.size + localsize) {
         this.definedSize = this.size + localsize;
      }

      super.addContent(buffer, last);
   }

   @Override
   public int hashCode() {
      return this.getName().hashCode();
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof Attribute)) {
         return false;
      } else {
         Attribute attribute = (Attribute)o;
         return this.getName().equalsIgnoreCase(attribute.getName());
      }
   }

   public int compareTo(InterfaceHttpData other) {
      if (!(other instanceof Attribute)) {
         throw new ClassCastException("Cannot compare " + this.getHttpDataType() + " with " + other.getHttpDataType());
      } else {
         return this.compareTo((Attribute)other);
      }
   }

   public int compareTo(Attribute o) {
      return this.getName().compareToIgnoreCase(o.getName());
   }

   @Override
   public String toString() {
      return this.getName() + '=' + this.getValue();
   }

   @Override
   public Attribute copy() {
      MemoryAttribute attr = new MemoryAttribute(this.getName());
      attr.setCharset(this.getCharset());
      ByteBuf content = this.content();
      if (content != null) {
         try {
            attr.setContent(content.copy());
         } catch (IOException var4) {
            throw new ChannelException(var4);
         }
      }

      return attr;
   }

   @Override
   public Attribute duplicate() {
      MemoryAttribute attr = new MemoryAttribute(this.getName());
      attr.setCharset(this.getCharset());
      ByteBuf content = this.content();
      if (content != null) {
         try {
            attr.setContent(content.duplicate());
         } catch (IOException var4) {
            throw new ChannelException(var4);
         }
      }

      return attr;
   }

   @Override
   public Attribute retain() {
      super.retain();
      return this;
   }

   @Override
   public Attribute retain(int increment) {
      super.retain(increment);
      return this;
   }
}
