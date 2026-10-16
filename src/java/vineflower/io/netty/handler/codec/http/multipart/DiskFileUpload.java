package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class DiskFileUpload extends AbstractDiskHttpData implements FileUpload {
   public static String baseDirectory;
   public static boolean deleteOnExitTemporaryFile = true;
   public static final String prefix = "FUp_";
   public static final String postfix = ".tmp";
   private String filename;
   private String contentType;
   private String contentTransferEncoding;

   public DiskFileUpload(String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size) {
      super(name, charset, size);
      this.setFilename(filename);
      this.setContentType(contentType);
      this.setContentTransferEncoding(contentTransferEncoding);
   }

   @Override
   public InterfaceHttpData.HttpDataType getHttpDataType() {
      return InterfaceHttpData.HttpDataType.FileUpload;
   }

   @Override
   public String getFilename() {
      return this.filename;
   }

   @Override
   public void setFilename(String filename) {
      if (filename == null) {
         throw new NullPointerException("filename");
      } else {
         this.filename = filename;
      }
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

   public int compareTo(InterfaceHttpData o) {
      if (!(o instanceof FileUpload)) {
         throw new ClassCastException("Cannot compare " + this.getHttpDataType() + " with " + o.getHttpDataType());
      } else {
         return this.compareTo((FileUpload)o);
      }
   }

   public int compareTo(FileUpload o) {
      int v = this.getName().compareToIgnoreCase(o.getName());
      return v != 0 ? v : v;
   }

   @Override
   public void setContentType(String contentType) {
      if (contentType == null) {
         throw new NullPointerException("contentType");
      } else {
         this.contentType = contentType;
      }
   }

   @Override
   public String getContentType() {
      return this.contentType;
   }

   @Override
   public String getContentTransferEncoding() {
      return this.contentTransferEncoding;
   }

   @Override
   public void setContentTransferEncoding(String contentTransferEncoding) {
      this.contentTransferEncoding = contentTransferEncoding;
   }

   @Override
   public String toString() {
      return "Content-Disposition: form-data; name=\""
         + this.getName()
         + "\"; "
         + "filename"
         + "=\""
         + this.filename
         + "\"\r\n"
         + "Content-Type"
         + ": "
         + this.contentType
         + (this.charset != null ? "; charset=" + this.charset + "\r\n" : "\r\n")
         + "Content-Length"
         + ": "
         + this.length()
         + "\r\n"
         + "Completed: "
         + this.isCompleted()
         + "\r\nIsInMemory: "
         + this.isInMemory()
         + "\r\nRealFile: "
         + (this.file != null ? this.file.getAbsolutePath() : "null")
         + " DefaultDeleteAfter: "
         + deleteOnExitTemporaryFile;
   }

   @Override
   protected boolean deleteOnExit() {
      return deleteOnExitTemporaryFile;
   }

   @Override
   protected String getBaseDirectory() {
      return baseDirectory;
   }

   @Override
   protected String getDiskFilename() {
      File file = new File(this.filename);
      return file.getName();
   }

   @Override
   protected String getPostfix() {
      return ".tmp";
   }

   @Override
   protected String getPrefix() {
      return "FUp_";
   }

   @Override
   public FileUpload copy() {
      DiskFileUpload upload = new DiskFileUpload(
         this.getName(), this.getFilename(), this.getContentType(), this.getContentTransferEncoding(), this.getCharset(), this.size
      );
      ByteBuf buf = this.content();
      if (buf != null) {
         try {
            upload.setContent(buf.copy());
         } catch (IOException var4) {
            throw new ChannelException(var4);
         }
      }

      return upload;
   }

   @Override
   public FileUpload duplicate() {
      DiskFileUpload upload = new DiskFileUpload(
         this.getName(), this.getFilename(), this.getContentType(), this.getContentTransferEncoding(), this.getCharset(), this.size
      );
      ByteBuf buf = this.content();
      if (buf != null) {
         try {
            upload.setContent(buf.duplicate());
         } catch (IOException var4) {
            throw new ChannelException(var4);
         }
      }

      return upload;
   }

   @Override
   public FileUpload retain(int increment) {
      super.retain(increment);
      return this;
   }

   @Override
   public FileUpload retain() {
      super.retain();
      return this;
   }
}
