package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

public class DefaultSpdyPingFrame implements SpdyPingFrame {
   private int id;

   public DefaultSpdyPingFrame(int id) {
      this.setId(id);
   }

   @Override
   public int id() {
      return this.id;
   }

   @Override
   public SpdyPingFrame setId(int id) {
      this.id = id;
      return this;
   }

   @Override
   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append(StringUtil.simpleClassName(this));
      buf.append(StringUtil.NEWLINE);
      buf.append("--> ID = ");
      buf.append(this.id());
      return buf.toString();
   }
}
