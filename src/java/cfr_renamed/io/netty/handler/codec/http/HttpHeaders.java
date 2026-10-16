/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaderDateFormat;
import io.netty.handler.codec.http.HttpHeaderEntity;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class HttpHeaders
implements Iterable<Map.Entry<String, String>> {
    private final static byte[] HEADER_SEPERATOR = new byte[]{58, 32};
    private final static byte[] CRLF = new byte[]{13, 10};
    private final static CharSequence CONTENT_LENGTH_ENTITY = HttpHeaders.newEntity("Content-Length");
    private final static CharSequence CONNECTION_ENTITY = HttpHeaders.newEntity("Connection");
    private final static CharSequence CLOSE_ENTITY = HttpHeaders.newEntity("close");
    private final static CharSequence KEEP_ALIVE_ENTITY = HttpHeaders.newEntity("keep-alive");
    private final static CharSequence HOST_ENTITY = HttpHeaders.newEntity("Host");
    private final static CharSequence DATE_ENTITY = HttpHeaders.newEntity("Date");
    private final static CharSequence EXPECT_ENTITY = HttpHeaders.newEntity("Expect");
    private final static CharSequence CONTINUE_ENTITY = HttpHeaders.newEntity("100-continue");
    private final static CharSequence TRANSFER_ENCODING_ENTITY = HttpHeaders.newEntity("Transfer-Encoding");
    private final static CharSequence CHUNKED_ENTITY = HttpHeaders.newEntity("chunked");
    private final static CharSequence SEC_WEBSOCKET_KEY1_ENTITY = HttpHeaders.newEntity("Sec-WebSocket-Key1");
    private final static CharSequence SEC_WEBSOCKET_KEY2_ENTITY = HttpHeaders.newEntity("Sec-WebSocket-Key2");
    private final static CharSequence SEC_WEBSOCKET_ORIGIN_ENTITY = HttpHeaders.newEntity("Sec-WebSocket-Origin");
    private final static CharSequence SEC_WEBSOCKET_LOCATION_ENTITY = HttpHeaders.newEntity("Sec-WebSocket-Location");
    final public static HttpHeaders EMPTY_HEADERS = new HttpHeaders(){

        @Override
        public String get(String name) {
            return null;
        }

        @Override
        public List<String> getAll(String name) {
            return Collections.emptyList();
        }

        @Override
        public List<Map.Entry<String, String>> entries() {
            return Collections.emptyList();
        }

        @Override
        public boolean contains(String name) {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Set<String> names() {
            return Collections.emptySet();
        }

        @Override
        public HttpHeaders add(String name, Object value) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders add(String name, Iterable<?> values) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders set(String name, Object value) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders set(String name, Iterable<?> values) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders remove(String name) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public HttpHeaders clear() {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public Iterator<Map.Entry<String, String>> iterator() {
            return this.entries().iterator();
        }
    };

    public static boolean isKeepAlive(HttpMessage message) {
        String connection = message.headers().get(CONNECTION_ENTITY);
        if (connection != null && HttpHeaders.equalsIgnoreCase(CLOSE_ENTITY, connection)) {
            return false;
        }
        if (message.getProtocolVersion().isKeepAliveDefault()) {
            return !HttpHeaders.equalsIgnoreCase(CLOSE_ENTITY, connection);
        }
        return HttpHeaders.equalsIgnoreCase(KEEP_ALIVE_ENTITY, connection);
    }

    public static void setKeepAlive(HttpMessage message, boolean keepAlive) {
        HttpHeaders h = message.headers();
        if (message.getProtocolVersion().isKeepAliveDefault()) {
            if (keepAlive) {
                h.remove(CONNECTION_ENTITY);
            } else {
                h.set(CONNECTION_ENTITY, (Object)CLOSE_ENTITY);
            }
        } else if (keepAlive) {
            h.set(CONNECTION_ENTITY, (Object)KEEP_ALIVE_ENTITY);
        } else {
            h.remove(CONNECTION_ENTITY);
        }
    }

    public static String getHeader(HttpMessage message, String name) {
        return message.headers().get(name);
    }

    public static String getHeader(HttpMessage message, CharSequence name) {
        return message.headers().get(name);
    }

    public static String getHeader(HttpMessage message, String name, String defaultValue) {
        return HttpHeaders.getHeader(message, (CharSequence)name, defaultValue);
    }

    public static String getHeader(HttpMessage message, CharSequence name, String defaultValue) {
        String value = message.headers().get(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static void setHeader(HttpMessage message, String name, Object value) {
        message.headers().set(name, value);
    }

    public static void setHeader(HttpMessage message, CharSequence name, Object value) {
        message.headers().set(name, value);
    }

    public static void setHeader(HttpMessage message, String name, Iterable<?> values) {
        message.headers().set(name, values);
    }

    public static void setHeader(HttpMessage message, CharSequence name, Iterable<?> values) {
        message.headers().set(name, values);
    }

    public static void addHeader(HttpMessage message, String name, Object value) {
        message.headers().add(name, value);
    }

    public static void addHeader(HttpMessage message, CharSequence name, Object value) {
        message.headers().add(name, value);
    }

    public static void removeHeader(HttpMessage message, String name) {
        message.headers().remove(name);
    }

    public static void removeHeader(HttpMessage message, CharSequence name) {
        message.headers().remove(name);
    }

    public static void clearHeaders(HttpMessage message) {
        message.headers().clear();
    }

    public static int getIntHeader(HttpMessage message, String name) {
        return HttpHeaders.getIntHeader(message, (CharSequence)name);
    }

    public static int getIntHeader(HttpMessage message, CharSequence name) {
        String value = HttpHeaders.getHeader(message, name);
        if (value == null) {
            throw new NumberFormatException("header not found: " + name);
        }
        return Integer.parseInt(value);
    }

    public static int getIntHeader(HttpMessage message, String name, int defaultValue) {
        return HttpHeaders.getIntHeader(message, (CharSequence)name, defaultValue);
    }

    public static int getIntHeader(HttpMessage message, CharSequence name, int defaultValue) {
        String value = HttpHeaders.getHeader(message, name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ignored) {
            return defaultValue;
        }
    }

    public static void setIntHeader(HttpMessage message, String name, int value) {
        message.headers().set(name, (Object)value);
    }

    public static void setIntHeader(HttpMessage message, CharSequence name, int value) {
        message.headers().set(name, (Object)value);
    }

    public static void setIntHeader(HttpMessage message, String name, Iterable<Integer> values) {
        message.headers().set(name, values);
    }

    public static void setIntHeader(HttpMessage message, CharSequence name, Iterable<Integer> values) {
        message.headers().set(name, values);
    }

    public static void addIntHeader(HttpMessage message, String name, int value) {
        message.headers().add(name, (Object)value);
    }

    public static void addIntHeader(HttpMessage message, CharSequence name, int value) {
        message.headers().add(name, (Object)value);
    }

    public static Date getDateHeader(HttpMessage message, String name) throws ParseException {
        return HttpHeaders.getDateHeader(message, (CharSequence)name);
    }

    public static Date getDateHeader(HttpMessage message, CharSequence name) throws ParseException {
        String value = HttpHeaders.getHeader(message, name);
        if (value == null) {
            throw new ParseException("header not found: " + name, 0);
        }
        return HttpHeaderDateFormat.get().parse(value);
    }

    public static Date getDateHeader(HttpMessage message, String name, Date defaultValue) {
        return HttpHeaders.getDateHeader(message, (CharSequence)name, defaultValue);
    }

    public static Date getDateHeader(HttpMessage message, CharSequence name, Date defaultValue) {
        String value = HttpHeaders.getHeader(message, name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return HttpHeaderDateFormat.get().parse(value);
        }
        catch (ParseException ignored) {
            return defaultValue;
        }
    }

    public static void setDateHeader(HttpMessage message, String name, Date value) {
        HttpHeaders.setDateHeader(message, (CharSequence)name, value);
    }

    public static void setDateHeader(HttpMessage message, CharSequence name, Date value) {
        if (value != null) {
            message.headers().set(name, (Object)HttpHeaderDateFormat.get().format(value));
        } else {
            message.headers().set(name, null);
        }
    }

    public static void setDateHeader(HttpMessage message, String name, Iterable<Date> values) {
        message.headers().set(name, values);
    }

    public static void setDateHeader(HttpMessage message, CharSequence name, Iterable<Date> values) {
        message.headers().set(name, values);
    }

    public static void addDateHeader(HttpMessage message, String name, Date value) {
        message.headers().add(name, (Object)value);
    }

    public static void addDateHeader(HttpMessage message, CharSequence name, Date value) {
        message.headers().add(name, (Object)value);
    }

    public static long getContentLength(HttpMessage message) {
        String value = HttpHeaders.getHeader(message, CONTENT_LENGTH_ENTITY);
        if (value != null) {
            return Long.parseLong(value);
        }
        long webSocketContentLength = HttpHeaders.getWebSocketContentLength(message);
        if (webSocketContentLength >= 0L) {
            return webSocketContentLength;
        }
        throw new NumberFormatException("header not found: Content-Length");
    }

    public static long getContentLength(HttpMessage message, long defaultValue) {
        String contentLength = message.headers().get(CONTENT_LENGTH_ENTITY);
        if (contentLength != null) {
            try {
                return Long.parseLong(contentLength);
            }
            catch (NumberFormatException ignored) {
                return defaultValue;
            }
        }
        long webSocketContentLength = HttpHeaders.getWebSocketContentLength(message);
        if (webSocketContentLength >= 0L) {
            return webSocketContentLength;
        }
        return defaultValue;
    }

    private static int getWebSocketContentLength(HttpMessage message) {
        HttpResponse res;
        HttpHeaders h = message.headers();
        if (message instanceof HttpRequest) {
            HttpRequest req = (HttpRequest)message;
            if (HttpMethod.GET.equals(req.getMethod()) && h.contains(SEC_WEBSOCKET_KEY1_ENTITY) && h.contains(SEC_WEBSOCKET_KEY2_ENTITY)) {
                return 8;
            }
        } else if (message instanceof HttpResponse && (res = (HttpResponse)message).getStatus().code() == 101 && h.contains(SEC_WEBSOCKET_ORIGIN_ENTITY) && h.contains(SEC_WEBSOCKET_LOCATION_ENTITY)) {
            return 16;
        }
        return -1;
    }

    public static void setContentLength(HttpMessage message, long length) {
        message.headers().set(CONTENT_LENGTH_ENTITY, (Object)length);
    }

    public static String getHost(HttpMessage message) {
        return message.headers().get(HOST_ENTITY);
    }

    public static String getHost(HttpMessage message, String defaultValue) {
        return HttpHeaders.getHeader(message, HOST_ENTITY, defaultValue);
    }

    public static void setHost(HttpMessage message, String value) {
        message.headers().set(HOST_ENTITY, (Object)value);
    }

    public static void setHost(HttpMessage message, CharSequence value) {
        message.headers().set(HOST_ENTITY, (Object)value);
    }

    public static Date getDate(HttpMessage message) throws ParseException {
        return HttpHeaders.getDateHeader(message, DATE_ENTITY);
    }

    public static Date getDate(HttpMessage message, Date defaultValue) {
        return HttpHeaders.getDateHeader(message, DATE_ENTITY, defaultValue);
    }

    public static void setDate(HttpMessage message, Date value) {
        if (value != null) {
            message.headers().set(DATE_ENTITY, (Object)HttpHeaderDateFormat.get().format(value));
        } else {
            message.headers().set(DATE_ENTITY, null);
        }
    }

    public static boolean is100ContinueExpected(HttpMessage message) {
        if (!(message instanceof HttpRequest)) {
            return false;
        }
        if (message.getProtocolVersion().compareTo(HttpVersion.HTTP_1_1) < 0) {
            return false;
        }
        String value = message.headers().get(EXPECT_ENTITY);
        if (value == null) {
            return false;
        }
        if (HttpHeaders.equalsIgnoreCase(CONTINUE_ENTITY, value)) {
            return true;
        }
        return message.headers().contains(EXPECT_ENTITY, CONTINUE_ENTITY, true);
    }

    public static void set100ContinueExpected(HttpMessage message) {
        HttpHeaders.set100ContinueExpected(message, true);
    }

    public static void set100ContinueExpected(HttpMessage message, boolean set) {
        if (set) {
            message.headers().set(EXPECT_ENTITY, (Object)CONTINUE_ENTITY);
        } else {
            message.headers().remove(EXPECT_ENTITY);
        }
    }

    static void validateHeaderName(CharSequence headerName) {
        if (headerName == null) {
            throw new NullPointerException("Header names cannot be null");
        }
        for (int index = 0; index < headerName.length(); ++index) {
            char character = headerName.charAt(index);
            if (character > '\u007f') {
                throw new IllegalArgumentException("Header name cannot contain non-ASCII characters: " + headerName);
            }
            switch (character) {
                case '\t': 
                case '\n': 
                case '\u000b': 
                case '\f': 
                case '\r': 
                case ' ': 
                case ',': 
                case ':': 
                case ';': 
                case '=': {
                    throw new IllegalArgumentException("Header name cannot contain the following prohibited characters: =,;: \\t\\r\\n\\v\\f: " + headerName);
                }
            }
        }
    }

    static void validateHeaderValue(CharSequence headerValue) {
        if (headerValue == null) {
            throw new NullPointerException("Header values cannot be null");
        }
        int state = 0;
        block19: for (int index = 0; index < headerValue.length(); ++index) {
            char character = headerValue.charAt(index);
            switch (character) {
                case '\u000b': {
                    throw new IllegalArgumentException("Header value contains a prohibited character '\\v': " + headerValue);
                }
                case '\f': {
                    throw new IllegalArgumentException("Header value contains a prohibited character '\\f': " + headerValue);
                }
            }
            switch (state) {
                case 0: {
                    switch (character) {
                        case '\r': {
                            state = 1;
                            break;
                        }
                        case '\n': {
                            state = 2;
                        }
                    }
                    continue block19;
                }
                case 1: {
                    switch (character) {
                        case '\n': {
                            state = 2;
                            continue block19;
                        }
                    }
                    throw new IllegalArgumentException("Only '\\n' is allowed after '\\r': " + headerValue);
                }
                case 2: {
                    switch (character) {
                        case '\t': 
                        case ' ': {
                            state = 0;
                            continue block19;
                        }
                    }
                    throw new IllegalArgumentException("Only ' ' and '\\t' are allowed after '\\n': " + headerValue);
                }
            }
        }
        if (state != 0) {
            throw new IllegalArgumentException("Header value must not end with '\\r' or '\\n':" + headerValue);
        }
    }

    public static boolean isTransferEncodingChunked(HttpMessage message) {
        return message.headers().contains(TRANSFER_ENCODING_ENTITY, CHUNKED_ENTITY, true);
    }

    public static void removeTransferEncodingChunked(HttpMessage m) {
        List<String> values = m.headers().getAll(TRANSFER_ENCODING_ENTITY);
        if (values.isEmpty()) {
            return;
        }
        Iterator<String> valuesIt = values.iterator();
        while (valuesIt.hasNext()) {
            String value = valuesIt.next();
            if (!HttpHeaders.equalsIgnoreCase(value, CHUNKED_ENTITY)) continue;
            valuesIt.remove();
        }
        if (values.isEmpty()) {
            m.headers().remove(TRANSFER_ENCODING_ENTITY);
        } else {
            m.headers().set(TRANSFER_ENCODING_ENTITY, values);
        }
    }

    public static void setTransferEncodingChunked(HttpMessage m) {
        HttpHeaders.addHeader(m, TRANSFER_ENCODING_ENTITY, (Object)CHUNKED_ENTITY);
        HttpHeaders.removeHeader(m, CONTENT_LENGTH_ENTITY);
    }

    public static boolean isContentLengthSet(HttpMessage m) {
        return m.headers().contains(CONTENT_LENGTH_ENTITY);
    }

    public static boolean equalsIgnoreCase(CharSequence name1, CharSequence name2) {
        if (name1 == name2) {
            return true;
        }
        if (name1 == null || name2 == null) {
            return false;
        }
        int nameLen = name1.length();
        if (nameLen != name2.length()) {
            return false;
        }
        for (int i = nameLen - 1; i >= 0; --i) {
            char c2;
            char c1 = name1.charAt(i);
            if (c1 == (c2 = name2.charAt(i))) continue;
            if (c1 >= 'A' && c1 <= 'Z') {
                c1 = (char)(c1 + 32);
            }
            if (c2 >= 'A' && c2 <= 'Z') {
                c2 = (char)(c2 + 32);
            }
            if (c1 == c2) continue;
            return false;
        }
        return true;
    }

    static int hash(CharSequence name) {
        if (name instanceof HttpHeaderEntity) {
            return ((HttpHeaderEntity)name).hash();
        }
        int h = 0;
        for (int i = name.length() - 1; i >= 0; --i) {
            char c = name.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c = (char)(c + 32);
            }
            h = 31 * h + c;
        }
        if (h > 0) {
            return h;
        }
        if (h == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }
        return -h;
    }

    static void encode(HttpHeaders headers, ByteBuf buf) {
        if (headers instanceof DefaultHttpHeaders) {
            ((DefaultHttpHeaders)headers).encode(buf);
        } else {
            for (Map.Entry header : headers) {
                HttpHeaders.encode((CharSequence)header.getKey(), (CharSequence)header.getValue(), buf);
            }
        }
    }

    static void encode(CharSequence key, CharSequence value, ByteBuf buf) {
        if (!HttpHeaders.encodeAscii(key, buf)) {
            buf.writeBytes(HEADER_SEPERATOR);
        }
        if (!HttpHeaders.encodeAscii(value, buf)) {
            buf.writeBytes(CRLF);
        }
    }

    public static boolean encodeAscii(CharSequence seq, ByteBuf buf) {
        if (seq instanceof HttpHeaderEntity) {
            return ((HttpHeaderEntity)seq).encode(buf);
        }
        HttpHeaders.encodeAscii0(seq, buf);
        return false;
    }

    static void encodeAscii0(CharSequence seq, ByteBuf buf) {
        int length = seq.length();
        for (int i = 0; i < length; ++i) {
            buf.writeByte((byte)seq.charAt(i));
        }
    }

    public static CharSequence newEntity(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        return new HttpHeaderEntity(name);
    }

    public static CharSequence newNameEntity(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        return new HttpHeaderEntity(name, HEADER_SEPERATOR);
    }

    public static CharSequence newValueEntity(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        return new HttpHeaderEntity(name, CRLF);
    }

    protected HttpHeaders() {
    }

    public abstract String get(String var1);

    public String get(CharSequence name) {
        return this.get(name.toString());
    }

    public abstract List<String> getAll(String var1);

    public List<String> getAll(CharSequence name) {
        return this.getAll(name.toString());
    }

    public abstract List<Map.Entry<String, String>> entries();

    public abstract boolean contains(String var1);

    public boolean contains(CharSequence name) {
        return this.contains(name.toString());
    }

    public abstract boolean isEmpty();

    public abstract Set<String> names();

    public abstract HttpHeaders add(String var1, Object var2);

    public HttpHeaders add(CharSequence name, Object value) {
        return this.add(name.toString(), value);
    }

    public abstract HttpHeaders add(String var1, Iterable<?> var2);

    public HttpHeaders add(CharSequence name, Iterable<?> values) {
        return this.add(name.toString(), values);
    }

    public HttpHeaders add(HttpHeaders headers) {
        if (headers == null) {
            throw new NullPointerException("headers");
        }
        for (Map.Entry e : headers) {
            this.add((String)e.getKey(), e.getValue());
        }
        return this;
    }

    public abstract HttpHeaders set(String var1, Object var2);

    public HttpHeaders set(CharSequence name, Object value) {
        return this.set(name.toString(), value);
    }

    public abstract HttpHeaders set(String var1, Iterable<?> var2);

    public HttpHeaders set(CharSequence name, Iterable<?> values) {
        return this.set(name.toString(), values);
    }

    public HttpHeaders set(HttpHeaders headers) {
        if (headers == null) {
            throw new NullPointerException("headers");
        }
        this.clear();
        for (Map.Entry e : headers) {
            this.add((String)e.getKey(), e.getValue());
        }
        return this;
    }

    public abstract HttpHeaders remove(String var1);

    public HttpHeaders remove(CharSequence name) {
        return this.remove(name.toString());
    }

    public abstract HttpHeaders clear();

    public boolean contains(String name, String value, boolean ignoreCaseValue) {
        List<String> values = this.getAll(name);
        if (values.isEmpty()) {
            return false;
        }
        for (String v : values) {
            if (!(ignoreCaseValue ? HttpHeaders.equalsIgnoreCase(v, value) : v.equals(value))) continue;
            return true;
        }
        return false;
    }

    public boolean contains(CharSequence name, CharSequence value, boolean ignoreCaseValue) {
        return this.contains(name.toString(), value.toString(), ignoreCaseValue);
    }

    public static final class Values {
        final public static String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
        final public static String BASE64 = "base64";
        final public static String BINARY = "binary";
        final public static String BOUNDARY = "boundary";
        final public static String BYTES = "bytes";
        final public static String CHARSET = "charset";
        final public static String CHUNKED = "chunked";
        final public static String CLOSE = "close";
        final public static String COMPRESS = "compress";
        final public static String CONTINUE = "100-continue";
        final public static String DEFLATE = "deflate";
        final public static String GZIP = "gzip";
        final public static String IDENTITY = "identity";
        final public static String KEEP_ALIVE = "keep-alive";
        final public static String MAX_AGE = "max-age";
        final public static String MAX_STALE = "max-stale";
        final public static String MIN_FRESH = "min-fresh";
        final public static String MULTIPART_FORM_DATA = "multipart/form-data";
        final public static String MUST_REVALIDATE = "must-revalidate";
        final public static String NO_CACHE = "no-cache";
        final public static String NO_STORE = "no-store";
        final public static String NO_TRANSFORM = "no-transform";
        final public static String NONE = "none";
        final public static String ONLY_IF_CACHED = "only-if-cached";
        final public static String PRIVATE = "private";
        final public static String PROXY_REVALIDATE = "proxy-revalidate";
        final public static String PUBLIC = "public";
        final public static String QUOTED_PRINTABLE = "quoted-printable";
        final public static String S_MAXAGE = "s-maxage";
        final public static String TRAILERS = "trailers";
        final public static String UPGRADE = "Upgrade";
        final public static String WEBSOCKET = "WebSocket";

        private Values() {
        }
    }

    public static final class Names {
        final public static String ACCEPT = "Accept";
        final public static String ACCEPT_CHARSET = "Accept-Charset";
        final public static String ACCEPT_ENCODING = "Accept-Encoding";
        final public static String ACCEPT_LANGUAGE = "Accept-Language";
        final public static String ACCEPT_RANGES = "Accept-Ranges";
        final public static String ACCEPT_PATCH = "Accept-Patch";
        final public static String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
        final public static String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
        final public static String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
        final public static String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
        final public static String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
        final public static String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
        final public static String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
        final public static String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
        final public static String AGE = "Age";
        final public static String ALLOW = "Allow";
        final public static String AUTHORIZATION = "Authorization";
        final public static String CACHE_CONTROL = "Cache-Control";
        final public static String CONNECTION = "Connection";
        final public static String CONTENT_BASE = "Content-Base";
        final public static String CONTENT_ENCODING = "Content-Encoding";
        final public static String CONTENT_LANGUAGE = "Content-Language";
        final public static String CONTENT_LENGTH = "Content-Length";
        final public static String CONTENT_LOCATION = "Content-Location";
        final public static String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
        final public static String CONTENT_MD5 = "Content-MD5";
        final public static String CONTENT_RANGE = "Content-Range";
        final public static String CONTENT_TYPE = "Content-Type";
        final public static String COOKIE = "Cookie";
        final public static String DATE = "Date";
        final public static String ETAG = "ETag";
        final public static String EXPECT = "Expect";
        final public static String EXPIRES = "Expires";
        final public static String FROM = "From";
        final public static String HOST = "Host";
        final public static String IF_MATCH = "If-Match";
        final public static String IF_MODIFIED_SINCE = "If-Modified-Since";
        final public static String IF_NONE_MATCH = "If-None-Match";
        final public static String IF_RANGE = "If-Range";
        final public static String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        final public static String LAST_MODIFIED = "Last-Modified";
        final public static String LOCATION = "Location";
        final public static String MAX_FORWARDS = "Max-Forwards";
        final public static String ORIGIN = "Origin";
        final public static String PRAGMA = "Pragma";
        final public static String PROXY_AUTHENTICATE = "Proxy-Authenticate";
        final public static String PROXY_AUTHORIZATION = "Proxy-Authorization";
        final public static String RANGE = "Range";
        final public static String REFERER = "Referer";
        final public static String RETRY_AFTER = "Retry-After";
        final public static String SEC_WEBSOCKET_KEY1 = "Sec-WebSocket-Key1";
        final public static String SEC_WEBSOCKET_KEY2 = "Sec-WebSocket-Key2";
        final public static String SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location";
        final public static String SEC_WEBSOCKET_ORIGIN = "Sec-WebSocket-Origin";
        final public static String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
        final public static String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
        final public static String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
        final public static String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";
        final public static String SERVER = "Server";
        final public static String SET_COOKIE = "Set-Cookie";
        final public static String SET_COOKIE2 = "Set-Cookie2";
        final public static String TE = "TE";
        final public static String TRAILER = "Trailer";
        final public static String TRANSFER_ENCODING = "Transfer-Encoding";
        final public static String UPGRADE = "Upgrade";
        final public static String USER_AGENT = "User-Agent";
        final public static String VARY = "Vary";
        final public static String VIA = "Via";
        final public static String WARNING = "Warning";
        final public static String WEBSOCKET_LOCATION = "WebSocket-Location";
        final public static String WEBSOCKET_ORIGIN = "WebSocket-Origin";
        final public static String WEBSOCKET_PROTOCOL = "WebSocket-Protocol";
        final public static String WWW_AUTHENTICATE = "WWW-Authenticate";

        private Names() {
        }
    }
}

