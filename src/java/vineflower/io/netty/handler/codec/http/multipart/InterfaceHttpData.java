package io.netty.handler.codec.http.multipart;

import io.netty.util.ReferenceCounted;

public interface InterfaceHttpData extends Comparable<InterfaceHttpData>, ReferenceCounted {
   String getName();

   InterfaceHttpData.HttpDataType getHttpDataType();

   public static enum HttpDataType {
      Attribute,
      FileUpload,
      InternalAttribute;
   }
}
