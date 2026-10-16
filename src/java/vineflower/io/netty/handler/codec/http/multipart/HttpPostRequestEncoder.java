package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.stream.ChunkedInput;
import io.netty.util.internal.ThreadLocalRandom;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class HttpPostRequestEncoder implements ChunkedInput<HttpContent> {
   private static final Map<Pattern, String> percentEncodings = new HashMap<>();
   private final HttpDataFactory factory;
   private final HttpRequest request;
   private final Charset charset;
   private boolean isChunked;
   private final List<InterfaceHttpData> bodyListDatas;
   final List<InterfaceHttpData> multipartHttpDatas;
   private final boolean isMultipart;
   String multipartDataBoundary;
   String multipartMixedBoundary;
   private boolean headerFinalized;
   private final HttpPostRequestEncoder.EncoderMode encoderMode;
   private boolean isLastChunk;
   private boolean isLastChunkSent;
   private FileUpload currentFileUpload;
   private boolean duringMixedMode;
   private long globalBodySize;
   private ListIterator<InterfaceHttpData> iterator;
   private ByteBuf currentBuffer;
   private InterfaceHttpData currentData;
   private boolean isKey = true;

   public HttpPostRequestEncoder(HttpRequest request, boolean multipart) throws HttpPostRequestEncoder.ErrorDataEncoderException {
      this(new DefaultHttpDataFactory(16384L), request, multipart, HttpConstants.DEFAULT_CHARSET, HttpPostRequestEncoder.EncoderMode.RFC1738);
   }

   public HttpPostRequestEncoder(HttpDataFactory factory, HttpRequest request, boolean multipart) throws HttpPostRequestEncoder.ErrorDataEncoderException {
      this(factory, request, multipart, HttpConstants.DEFAULT_CHARSET, HttpPostRequestEncoder.EncoderMode.RFC1738);
   }

   public HttpPostRequestEncoder(
      HttpDataFactory factory, HttpRequest request, boolean multipart, Charset charset, HttpPostRequestEncoder.EncoderMode encoderMode
   ) throws HttpPostRequestEncoder.ErrorDataEncoderException {
      if (factory == null) {
         throw new NullPointerException("factory");
      } else if (request == null) {
         throw new NullPointerException("request");
      } else if (charset == null) {
         throw new NullPointerException("charset");
      } else if (request.getMethod() != HttpMethod.POST) {
         throw new HttpPostRequestEncoder.ErrorDataEncoderException("Cannot create a Encoder if not a POST");
      } else {
         this.request = request;
         this.charset = charset;
         this.factory = factory;
         this.bodyListDatas = new ArrayList<>();
         this.isLastChunk = false;
         this.isLastChunkSent = false;
         this.isMultipart = multipart;
         this.multipartHttpDatas = new ArrayList<>();
         this.encoderMode = encoderMode;
         if (this.isMultipart) {
            this.initDataMultipart();
         }
      }
   }

   public void cleanFiles() {
      this.factory.cleanRequestHttpDatas(this.request);
   }

   public boolean isMultipart() {
      return this.isMultipart;
   }

   private void initDataMultipart() {
      this.multipartDataBoundary = getNewMultipartDelimiter();
   }

   private void initMixedMultipart() {
      this.multipartMixedBoundary = getNewMultipartDelimiter();
   }

   private static String getNewMultipartDelimiter() {
      return Long.toHexString(ThreadLocalRandom.current().nextLong()).toLowerCase();
   }

   public List<InterfaceHttpData> getBodyListAttributes() {
      return this.bodyListDatas;
   }

   public void setBodyHttpDatas(List<InterfaceHttpData> datas) throws HttpPostRequestEncoder.ErrorDataEncoderException {
      if (datas == null) {
         throw new NullPointerException("datas");
      } else {
         this.globalBodySize = 0L;
         this.bodyListDatas.clear();
         this.currentFileUpload = null;
         this.duringMixedMode = false;
         this.multipartHttpDatas.clear();

         for (InterfaceHttpData data : datas) {
            this.addBodyHttpData(data);
         }
      }
   }

   public void addBodyAttribute(String name, String value) throws HttpPostRequestEncoder.ErrorDataEncoderException {
      if (name == null) {
         throw new NullPointerException("name");
      } else {
         String svalue = value;
         if (value == null) {
            svalue = "";
         }

         Attribute data = this.factory.createAttribute(this.request, name, svalue);
         this.addBodyHttpData(data);
      }
   }

   public void addBodyFileUpload(String name, File file, String contentType, boolean isText) throws HttpPostRequestEncoder.ErrorDataEncoderException {
      if (name == null) {
         throw new NullPointerException("name");
      } else if (file == null) {
         throw new NullPointerException("file");
      } else {
         String scontentType = contentType;
         String contentTransferEncoding = null;
         if (contentType == null) {
            if (isText) {
               scontentType = "text/plain";
            } else {
               scontentType = "application/octet-stream";
            }
         }

         if (!isText) {
            contentTransferEncoding = HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value();
         }

         FileUpload fileUpload = this.factory.createFileUpload(this.request, name, file.getName(), scontentType, contentTransferEncoding, null, file.length());

         try {
            fileUpload.setContent(file);
         } catch (IOException var9) {
            throw new HttpPostRequestEncoder.ErrorDataEncoderException(var9);
         }

         this.addBodyHttpData(fileUpload);
      }
   }

   public void addBodyFileUploads(String name, File[] file, String[] contentType, boolean[] isText) throws HttpPostRequestEncoder.ErrorDataEncoderException {
      if (file.length != contentType.length && file.length != isText.length) {
         throw new NullPointerException("Different array length");
      } else {
         for (int i = 0; i < file.length; i++) {
            this.addBodyFileUpload(name, file[i], contentType[i], isText[i]);
         }
      }
   }

   public void addBodyHttpData(InterfaceHttpData data) throws HttpPostRequestEncoder.ErrorDataEncoderException {
      if (this.headerFinalized) {
         throw new HttpPostRequestEncoder.ErrorDataEncoderException("Cannot add value once finalized");
      } else if (data == null) {
         throw new NullPointerException("data");
      } else {
         this.bodyListDatas.add(data);
         if (!this.isMultipart) {
            if (data instanceof Attribute) {
               Attribute attribute = (Attribute)data;

               try {
                  String key = this.encodeAttribute(attribute.getName(), this.charset);
                  String value = this.encodeAttribute(attribute.getValue(), this.charset);
                  Attribute newattribute = this.factory.createAttribute(this.request, key, value);
                  this.multipartHttpDatas.add(newattribute);
                  this.globalBodySize = this.globalBodySize + newattribute.getName().length() + 1 + newattribute.length() + 1L;
               } catch (IOException var7) {
                  throw new HttpPostRequestEncoder.ErrorDataEncoderException(var7);
               }
            } else if (data instanceof FileUpload) {
               FileUpload fileUpload = (FileUpload)data;
               String key = this.encodeAttribute(fileUpload.getName(), this.charset);
               String value = this.encodeAttribute(fileUpload.getFilename(), this.charset);
               Attribute newattribute = this.factory.createAttribute(this.request, key, value);
               this.multipartHttpDatas.add(newattribute);
               this.globalBodySize = this.globalBodySize + newattribute.getName().length() + 1 + newattribute.length() + 1L;
            }
         } else {
            if (data instanceof Attribute) {
               if (this.duringMixedMode) {
                  InternalAttribute internal = new InternalAttribute(this.charset);
                  internal.addValue("\r\n--" + this.multipartMixedBoundary + "--");
                  this.multipartHttpDatas.add(internal);
                  this.multipartMixedBoundary = null;
                  this.currentFileUpload = null;
                  this.duringMixedMode = false;
               }

               InternalAttribute internal = new InternalAttribute(this.charset);
               if (!this.multipartHttpDatas.isEmpty()) {
                  internal.addValue("\r\n");
               }

               internal.addValue("--" + this.multipartDataBoundary + "\r\n");
               Attribute attribute = (Attribute)data;
               internal.addValue("Content-Disposition: form-data; name=\"" + attribute.getName() + "\"\r\n");
               Charset localcharset = attribute.getCharset();
               if (localcharset != null) {
                  internal.addValue("Content-Type: text/plain; charset=" + localcharset + "\r\n");
               }

               internal.addValue("\r\n");
               this.multipartHttpDatas.add(internal);
               this.multipartHttpDatas.add(data);
               this.globalBodySize = this.globalBodySize + attribute.length() + internal.size();
            } else if (data instanceof FileUpload) {
               FileUpload fileUpload = (FileUpload)data;
               InternalAttribute internalx = new InternalAttribute(this.charset);
               if (!this.multipartHttpDatas.isEmpty()) {
                  internalx.addValue("\r\n");
               }

               boolean localMixed;
               if (this.duringMixedMode) {
                  if (this.currentFileUpload != null && this.currentFileUpload.getName().equals(fileUpload.getName())) {
                     localMixed = true;
                  } else {
                     internalx.addValue("--" + this.multipartMixedBoundary + "--");
                     this.multipartHttpDatas.add(internalx);
                     this.multipartMixedBoundary = null;
                     internalx = new InternalAttribute(this.charset);
                     internalx.addValue("\r\n");
                     localMixed = false;
                     this.currentFileUpload = fileUpload;
                     this.duringMixedMode = false;
                  }
               } else if (this.currentFileUpload != null && this.currentFileUpload.getName().equals(fileUpload.getName())) {
                  this.initMixedMultipart();
                  InternalAttribute pastAttribute = (InternalAttribute)this.multipartHttpDatas.get(this.multipartHttpDatas.size() - 2);
                  this.globalBodySize = this.globalBodySize - pastAttribute.size();
                  StringBuilder replacement = new StringBuilder(
                     139
                        + this.multipartDataBoundary.length()
                        + this.multipartMixedBoundary.length() * 2
                        + fileUpload.getFilename().length()
                        + fileUpload.getName().length()
                  );
                  replacement.append("--");
                  replacement.append(this.multipartDataBoundary);
                  replacement.append("\r\n");
                  replacement.append("Content-Disposition");
                  replacement.append(": ");
                  replacement.append("form-data");
                  replacement.append("; ");
                  replacement.append("name");
                  replacement.append("=\"");
                  replacement.append(fileUpload.getName());
                  replacement.append("\"\r\n");
                  replacement.append("Content-Type");
                  replacement.append(": ");
                  replacement.append("multipart/mixed");
                  replacement.append("; ");
                  replacement.append("boundary");
                  replacement.append('=');
                  replacement.append(this.multipartMixedBoundary);
                  replacement.append("\r\n\r\n");
                  replacement.append("--");
                  replacement.append(this.multipartMixedBoundary);
                  replacement.append("\r\n");
                  replacement.append("Content-Disposition");
                  replacement.append(": ");
                  replacement.append("attachment");
                  replacement.append("; ");
                  replacement.append("filename");
                  replacement.append("=\"");
                  replacement.append(fileUpload.getFilename());
                  replacement.append("\"\r\n");
                  pastAttribute.setValue(replacement.toString(), 1);
                  pastAttribute.setValue("", 2);
                  this.globalBodySize = this.globalBodySize + pastAttribute.size();
                  localMixed = true;
                  this.duringMixedMode = true;
               } else {
                  localMixed = false;
                  this.currentFileUpload = fileUpload;
                  this.duringMixedMode = false;
               }

               if (localMixed) {
                  internalx.addValue("--" + this.multipartMixedBoundary + "\r\n");
                  internalx.addValue("Content-Disposition: attachment; filename=\"" + fileUpload.getFilename() + "\"\r\n");
               } else {
                  internalx.addValue("--" + this.multipartDataBoundary + "\r\n");
                  internalx.addValue(
                     "Content-Disposition: form-data; name=\"" + fileUpload.getName() + "\"; " + "filename" + "=\"" + fileUpload.getFilename() + "\"\r\n"
                  );
               }

               internalx.addValue("Content-Type: " + fileUpload.getContentType());
               String contentTransferEncoding = fileUpload.getContentTransferEncoding();
               if (contentTransferEncoding != null && contentTransferEncoding.equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) {
                  internalx.addValue("\r\nContent-Transfer-Encoding: " + HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value() + "\r\n\r\n");
               } else if (fileUpload.getCharset() != null) {
                  internalx.addValue("; charset=" + fileUpload.getCharset() + "\r\n\r\n");
               } else {
                  internalx.addValue("\r\n\r\n");
               }

               this.multipartHttpDatas.add(internalx);
               this.multipartHttpDatas.add(data);
               this.globalBodySize = this.globalBodySize + fileUpload.length() + internalx.size();
            }
         }
      }
   }

   public HttpRequest finalizeRequest() throws HttpPostRequestEncoder.ErrorDataEncoderException {
      if (!this.headerFinalized) {
         if (this.isMultipart) {
            InternalAttribute internal = new InternalAttribute(this.charset);
            if (this.duringMixedMode) {
               internal.addValue("\r\n--" + this.multipartMixedBoundary + "--");
            }

            internal.addValue("\r\n--" + this.multipartDataBoundary + "--\r\n");
            this.multipartHttpDatas.add(internal);
            this.multipartMixedBoundary = null;
            this.currentFileUpload = null;
            this.duringMixedMode = false;
            this.globalBodySize = this.globalBodySize + internal.size();
         }

         this.headerFinalized = true;
         HttpHeaders headers = this.request.headers();
         List contentTypes = headers.getAll("Content-Type");
         List transferEncoding = headers.getAll("Transfer-Encoding");
         if (contentTypes != null) {
            headers.remove("Content-Type");

            for (String contentType : contentTypes) {
               String lowercased = contentType.toLowerCase();
               if (!lowercased.startsWith("multipart/form-data") && !lowercased.startsWith("application/x-www-form-urlencoded")) {
                  headers.add("Content-Type", contentType);
               }
            }
         }

         if (this.isMultipart) {
            String value = "multipart/form-data; boundary=" + this.multipartDataBoundary;
            headers.add("Content-Type", value);
         } else {
            headers.add("Content-Type", "application/x-www-form-urlencoded");
         }

         long realSize = this.globalBodySize;
         if (this.isMultipart) {
            this.iterator = this.multipartHttpDatas.listIterator();
         } else {
            realSize--;
            this.iterator = this.multipartHttpDatas.listIterator();
         }

         headers.set("Content-Length", String.valueOf(realSize));
         if (realSize <= 8096L && !this.isMultipart) {
            HttpContent chunk = this.nextChunk();
            if (this.request instanceof FullHttpRequest) {
               FullHttpRequest fullRequest = (FullHttpRequest)this.request;
               ByteBuf chunkContent = chunk.content();
               if (fullRequest.content() != chunkContent) {
                  fullRequest.content().clear().writeBytes(chunkContent);
                  chunkContent.release();
               }

               return fullRequest;
            } else {
               return new HttpPostRequestEncoder.WrappedFullHttpRequest(this.request, chunk);
            }
         } else {
            this.isChunked = true;
            if (transferEncoding != null) {
               headers.remove("Transfer-Encoding");

               for (String v : transferEncoding) {
                  if (!v.equalsIgnoreCase("chunked")) {
                     headers.add("Transfer-Encoding", v);
                  }
               }
            }

            HttpHeaders.setTransferEncodingChunked(this.request);
            return new HttpPostRequestEncoder.WrappedHttpRequest(this.request);
         }
      } else {
         throw new HttpPostRequestEncoder.ErrorDataEncoderException("Header already encoded");
      }
   }

   public boolean isChunked() {
      return this.isChunked;
   }

   private String encodeAttribute(String s, Charset charset) throws HttpPostRequestEncoder.ErrorDataEncoderException {
      if (s == null) {
         return "";
      } else {
         try {
            String encoded = URLEncoder.encode(s, charset.name());
            if (this.encoderMode == HttpPostRequestEncoder.EncoderMode.RFC3986) {
               for (Entry<Pattern, String> entry : percentEncodings.entrySet()) {
                  String replacement = entry.getValue();
                  encoded = entry.getKey().matcher(encoded).replaceAll(replacement);
               }
            }

            return encoded;
         } catch (UnsupportedEncodingException var7) {
            throw new HttpPostRequestEncoder.ErrorDataEncoderException(charset.name(), var7);
         }
      }
   }

   private ByteBuf fillByteBuf() {
      int length = this.currentBuffer.readableBytes();
      if (length > 8096) {
         ByteBuf slice = this.currentBuffer.slice(this.currentBuffer.readerIndex(), 8096);
         this.currentBuffer.skipBytes(8096);
         return slice;
      } else {
         ByteBuf slice = this.currentBuffer;
         this.currentBuffer = null;
         return slice;
      }
   }

   private HttpContent encodeNextChunkMultipart(int sizeleft) throws HttpPostRequestEncoder.ErrorDataEncoderException {
      if (this.currentData == null) {
         return null;
      } else {
         ByteBuf buffer;
         if (this.currentData instanceof InternalAttribute) {
            buffer = ((InternalAttribute)this.currentData).toByteBuf();
            this.currentData = null;
         } else {
            if (this.currentData instanceof Attribute) {
               try {
                  buffer = ((Attribute)this.currentData).getChunk(sizeleft);
               } catch (IOException var5) {
                  throw new HttpPostRequestEncoder.ErrorDataEncoderException(var5);
               }
            } else {
               try {
                  buffer = ((HttpData)this.currentData).getChunk(sizeleft);
               } catch (IOException var4) {
                  throw new HttpPostRequestEncoder.ErrorDataEncoderException(var4);
               }
            }

            if (buffer.capacity() == 0) {
               this.currentData = null;
               return null;
            }
         }

         if (this.currentBuffer == null) {
            this.currentBuffer = buffer;
         } else {
            this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, buffer);
         }

         if (this.currentBuffer.readableBytes() < 8096) {
            this.currentData = null;
            return null;
         } else {
            buffer = this.fillByteBuf();
            return new DefaultHttpContent(buffer);
         }
      }
   }

   private HttpContent encodeNextChunkUrlEncoded(int sizeleft) throws HttpPostRequestEncoder.ErrorDataEncoderException {
      if (this.currentData == null) {
         return null;
      } else {
         int size = sizeleft;
         if (this.isKey) {
            String key = this.currentData.getName();
            ByteBuf buffer = Unpooled.wrappedBuffer(key.getBytes());
            this.isKey = false;
            if (this.currentBuffer == null) {
               this.currentBuffer = Unpooled.wrappedBuffer(buffer, Unpooled.wrappedBuffer("=".getBytes()));
               size = sizeleft - (buffer.readableBytes() + 1);
            } else {
               this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, buffer, Unpooled.wrappedBuffer("=".getBytes()));
               size = sizeleft - (buffer.readableBytes() + 1);
            }

            if (this.currentBuffer.readableBytes() >= 8096) {
               buffer = this.fillByteBuf();
               return new DefaultHttpContent(buffer);
            }
         }

         ByteBuf bufferx;
         try {
            bufferx = ((HttpData)this.currentData).getChunk(size);
         } catch (IOException var5) {
            throw new HttpPostRequestEncoder.ErrorDataEncoderException(var5);
         }

         ByteBuf delimiter = null;
         if (bufferx.readableBytes() < size) {
            this.isKey = true;
            delimiter = this.iterator.hasNext() ? Unpooled.wrappedBuffer("&".getBytes()) : null;
         }

         if (bufferx.capacity() == 0) {
            this.currentData = null;
            if (this.currentBuffer == null) {
               this.currentBuffer = delimiter;
            } else if (delimiter != null) {
               this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, delimiter);
            }

            if (this.currentBuffer.readableBytes() >= 8096) {
               bufferx = this.fillByteBuf();
               return new DefaultHttpContent(bufferx);
            } else {
               return null;
            }
         } else {
            if (this.currentBuffer == null) {
               if (delimiter != null) {
                  this.currentBuffer = Unpooled.wrappedBuffer(bufferx, delimiter);
               } else {
                  this.currentBuffer = bufferx;
               }
            } else if (delimiter != null) {
               this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, bufferx, delimiter);
            } else {
               this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, bufferx);
            }

            if (this.currentBuffer.readableBytes() < 8096) {
               this.currentData = null;
               this.isKey = true;
               return null;
            } else {
               bufferx = this.fillByteBuf();
               return new DefaultHttpContent(bufferx);
            }
         }
      }
   }

   @Override
   public void close() throws Exception {
   }

   public HttpContent readChunk(ChannelHandlerContext ctx) throws Exception {
      return this.isLastChunkSent ? null : this.nextChunk();
   }

   private HttpContent nextChunk() throws HttpPostRequestEncoder.ErrorDataEncoderException {
      if (this.isLastChunk) {
         this.isLastChunkSent = true;
         return LastHttpContent.EMPTY_LAST_CONTENT;
      } else {
         int size = 8096;
         if (this.currentBuffer != null) {
            size -= this.currentBuffer.readableBytes();
         }

         if (size <= 0) {
            ByteBuf buffer = this.fillByteBuf();
            return new DefaultHttpContent(buffer);
         } else {
            if (this.currentData != null) {
               if (this.isMultipart) {
                  HttpContent chunk = this.encodeNextChunkMultipart(size);
                  if (chunk != null) {
                     return chunk;
                  }
               } else {
                  HttpContent chunk = this.encodeNextChunkUrlEncoded(size);
                  if (chunk != null) {
                     return chunk;
                  }
               }

               size = 8096 - this.currentBuffer.readableBytes();
            }

            if (!this.iterator.hasNext()) {
               this.isLastChunk = true;
               ByteBuf buffer = this.currentBuffer;
               this.currentBuffer = null;
               return new DefaultHttpContent(buffer);
            } else {
               while (size > 0 && this.iterator.hasNext()) {
                  this.currentData = this.iterator.next();
                  HttpContent chunk;
                  if (this.isMultipart) {
                     chunk = this.encodeNextChunkMultipart(size);
                  } else {
                     chunk = this.encodeNextChunkUrlEncoded(size);
                  }

                  if (chunk != null) {
                     return chunk;
                  }

                  size = 8096 - this.currentBuffer.readableBytes();
               }

               this.isLastChunk = true;
               if (this.currentBuffer == null) {
                  this.isLastChunkSent = true;
                  return LastHttpContent.EMPTY_LAST_CONTENT;
               } else {
                  ByteBuf buffer = this.currentBuffer;
                  this.currentBuffer = null;
                  return new DefaultHttpContent(buffer);
               }
            }
         }
      }
   }

   @Override
   public boolean isEndOfInput() throws Exception {
      return this.isLastChunkSent;
   }

   static {
      percentEncodings.put(Pattern.compile("\\*"), "%2A");
      percentEncodings.put(Pattern.compile("\\+"), "%20");
      percentEncodings.put(Pattern.compile("%7E"), "~");
   }

   public static enum EncoderMode {
      RFC1738,
      RFC3986;
   }

   public static class ErrorDataEncoderException extends Exception {
      private static final long serialVersionUID = 5020247425493164465L;

      public ErrorDataEncoderException() {
      }

      public ErrorDataEncoderException(String msg) {
         super(msg);
      }

      public ErrorDataEncoderException(Throwable cause) {
         super(cause);
      }

      public ErrorDataEncoderException(String msg, Throwable cause) {
         super(msg, cause);
      }
   }

   private static final class WrappedFullHttpRequest extends HttpPostRequestEncoder.WrappedHttpRequest implements FullHttpRequest {
      private final HttpContent content;

      private WrappedFullHttpRequest(HttpRequest request, HttpContent content) {
         super(request);
         this.content = content;
      }

      @Override
      public FullHttpRequest setProtocolVersion(HttpVersion version) {
         super.setProtocolVersion(version);
         return this;
      }

      @Override
      public FullHttpRequest setMethod(HttpMethod method) {
         super.setMethod(method);
         return this;
      }

      @Override
      public FullHttpRequest setUri(String uri) {
         super.setUri(uri);
         return this;
      }

      @Override
      public FullHttpRequest copy() {
         DefaultFullHttpRequest copy = new DefaultFullHttpRequest(this.getProtocolVersion(), this.getMethod(), this.getUri(), this.content().copy());
         copy.headers().set(this.headers());
         copy.trailingHeaders().set(this.trailingHeaders());
         return copy;
      }

      public FullHttpRequest duplicate() {
         DefaultFullHttpRequest duplicate = new DefaultFullHttpRequest(this.getProtocolVersion(), this.getMethod(), this.getUri(), this.content().duplicate());
         duplicate.headers().set(this.headers());
         duplicate.trailingHeaders().set(this.trailingHeaders());
         return duplicate;
      }

      @Override
      public FullHttpRequest retain(int increment) {
         this.content.retain(increment);
         return this;
      }

      @Override
      public FullHttpRequest retain() {
         this.content.retain();
         return this;
      }

      @Override
      public ByteBuf content() {
         return this.content.content();
      }

      @Override
      public HttpHeaders trailingHeaders() {
         return this.content instanceof LastHttpContent ? ((LastHttpContent)this.content).trailingHeaders() : HttpHeaders.EMPTY_HEADERS;
      }

      @Override
      public int refCnt() {
         return this.content.refCnt();
      }

      @Override
      public boolean release() {
         return this.content.release();
      }

      @Override
      public boolean release(int decrement) {
         return this.content.release(decrement);
      }
   }

   private static class WrappedHttpRequest implements HttpRequest {
      private final HttpRequest request;

      WrappedHttpRequest(HttpRequest request) {
         this.request = request;
      }

      @Override
      public HttpRequest setProtocolVersion(HttpVersion version) {
         this.request.setProtocolVersion(version);
         return this;
      }

      @Override
      public HttpRequest setMethod(HttpMethod method) {
         this.request.setMethod(method);
         return this;
      }

      @Override
      public HttpRequest setUri(String uri) {
         this.request.setUri(uri);
         return this;
      }

      @Override
      public HttpMethod getMethod() {
         return this.request.getMethod();
      }

      @Override
      public String getUri() {
         return this.request.getUri();
      }

      @Override
      public HttpVersion getProtocolVersion() {
         return this.request.getProtocolVersion();
      }

      @Override
      public HttpHeaders headers() {
         return this.request.headers();
      }

      @Override
      public DecoderResult getDecoderResult() {
         return this.request.getDecoderResult();
      }

      @Override
      public void setDecoderResult(DecoderResult result) {
         this.request.setDecoderResult(result);
      }
   }
}
