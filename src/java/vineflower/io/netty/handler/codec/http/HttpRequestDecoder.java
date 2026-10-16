package io.netty.handler.codec.http;

public class HttpRequestDecoder extends HttpObjectDecoder {
   public HttpRequestDecoder() {
   }

   public HttpRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
      super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true);
   }

   public HttpRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders) {
      super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true, validateHeaders);
   }

   @Override
   protected HttpMessage createMessage(String[] initialLine) throws Exception {
      return new DefaultHttpRequest(HttpVersion.valueOf(initialLine[2]), HttpMethod.valueOf(initialLine[0]), initialLine[1], this.validateHeaders);
   }

   @Override
   protected HttpMessage createInvalidMessage() {
      return new DefaultHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, "/bad-request", this.validateHeaders);
   }

   @Override
   protected boolean isDecodingRequest() {
      return true;
   }
}
