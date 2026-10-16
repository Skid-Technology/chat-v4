package io.netty.handler.codec.http;

import io.netty.util.internal.StringUtil;
import java.util.Map.Entry;

public abstract class DefaultHttpMessage extends DefaultHttpObject implements HttpMessage {
   private HttpVersion version;
   private final HttpHeaders headers;

   protected DefaultHttpMessage(HttpVersion version) {
      this(version, true);
   }

   protected DefaultHttpMessage(HttpVersion version, boolean validate) {
      if (version == null) {
         throw new NullPointerException("version");
      } else {
         this.version = version;
         this.headers = new DefaultHttpHeaders(validate);
      }
   }

   @Override
   public HttpHeaders headers() {
      return this.headers;
   }

   @Override
   public HttpVersion getProtocolVersion() {
      return this.version;
   }

   @Override
   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append(StringUtil.simpleClassName(this));
      buf.append("(version: ");
      buf.append(this.getProtocolVersion().text());
      buf.append(", keepAlive: ");
      buf.append(HttpHeaders.isKeepAlive(this));
      buf.append(')');
      buf.append(StringUtil.NEWLINE);
      this.appendHeaders(buf);
      buf.setLength(buf.length() - StringUtil.NEWLINE.length());
      return buf.toString();
   }

   @Override
   public HttpMessage setProtocolVersion(HttpVersion version) {
      if (version == null) {
         throw new NullPointerException("version");
      } else {
         this.version = version;
         return this;
      }
   }

   void appendHeaders(StringBuilder buf) {
      for (Entry<String, String> e : this.headers()) {
         buf.append(e.getKey());
         buf.append(": ");
         buf.append(e.getValue());
         buf.append(StringUtil.NEWLINE);
      }
   }
}
