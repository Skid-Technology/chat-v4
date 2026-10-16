/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import java.nio.charset.Charset;

final class HttpPostBodyUtil {
    final public static int chunkSize = 8096;
    final public static String CONTENT_DISPOSITION = "Content-Disposition";
    final public static String NAME = "name";
    final public static String FILENAME = "filename";
    final public static String FORM_DATA = "form-data";
    final public static String ATTACHMENT = "attachment";
    final public static String FILE = "file";
    final public static String MULTIPART_MIXED = "multipart/mixed";
    final public static Charset ISO_8859_1 = CharsetUtil.ISO_8859_1;
    final public static Charset US_ASCII = CharsetUtil.US_ASCII;
    final public static String DEFAULT_BINARY_CONTENT_TYPE = "application/octet-stream";
    final public static String DEFAULT_TEXT_CONTENT_TYPE = "text/plain";

    private HttpPostBodyUtil() {
    }

    static int findNonWhitespace(String sb, int offset) {
        int result;
        for (result = offset; result < sb.length() && Character.isWhitespace(sb.charAt(result)); ++result) {
        }
        return result;
    }

    static int findWhitespace(String sb, int offset) {
        int result;
        for (result = offset; result < sb.length() && !Character.isWhitespace(sb.charAt(result)); ++result) {
        }
        return result;
    }

    static int findEndOfString(String sb) {
        int result;
        for (result = sb.length(); result > 0 && Character.isWhitespace(sb.charAt(result - 1)); --result) {
        }
        return result;
    }

    static class SeekAheadOptimize {
        byte[] bytes;
        int readerIndex;
        int pos;
        int origPos;
        int limit;
        ByteBuf buffer;

        SeekAheadOptimize(ByteBuf buffer) throws SeekAheadNoBackArrayException {
            if (!buffer.hasArray()) {
                throw new SeekAheadNoBackArrayException();
            }
            this.buffer = buffer;
            this.bytes = buffer.array();
            this.readerIndex = buffer.readerIndex();
            this.origPos = this.pos = buffer.arrayOffset() + this.readerIndex;
            this.limit = buffer.arrayOffset() + buffer.writerIndex();
        }

        void setReadPosition(int minus) {
            this.pos -= minus;
            this.readerIndex = this.getReadPosition(this.pos);
            this.buffer.readerIndex(this.readerIndex);
        }

        int getReadPosition(int index) {
            return index - this.origPos + this.readerIndex;
        }

        void clear() {
            this.buffer = null;
            this.bytes = null;
            this.limit = 0;
            this.pos = 0;
            this.readerIndex = 0;
        }
    }

    static class SeekAheadNoBackArrayException
    extends Exception {
        private final static long serialVersionUID = -630418804938699495L;

        SeekAheadNoBackArrayException() {
        }
    }

    public static enum TransferEncodingMechanism {
        BIT7("7bit"),
        BIT8("8bit"),
        BINARY("binary");

        private final String value;

        private TransferEncodingMechanism(String value) {
            this.value = value;
        }

        private TransferEncodingMechanism() {
            this.value = this.name();
        }

        public String value() {
            return this.value;
        }

        public String toString() {
            return this.value;
        }
    }
}

