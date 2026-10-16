/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.api.ApiHttpStatusException;
import gg.vape.api.ApiPermissiveX509ExtendedTrustManager;
import gg.vape.api.ApiResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Function;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class ApiHttpClient {
    private static boolean opaqueState;
    private final static DateFormat API_DATE_FORMAT;
    final public static Gson GSON;
    private final static SSLSocketFactory SSL_SOCKET_FACTORY;
    private final static String[] ENABLED_PROTOCOLS;
    private final static int MAX_RETRIES = 3;
    private final static int CONNECT_TIMEOUT = 20000;
    private final static int READ_TIMEOUT = 25000;

    @Nullable
    @Contract(value="null -> null")
    public static synchronized Date parseApiDate(@Nullable String dateText) throws ParseException {
        if (dateText == null || dateText.isEmpty()) {
            return null;
        }
        try {
            return API_DATE_FORMAT.parse(dateText);
        }
        catch (ParseException e) {
            String[] fallbacks = new String[]{"yyyy-MM-dd'T'HH:mm:ssXXX", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss"};
            for (String pattern : fallbacks) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    return sdf.parse(dateText);
                }
                catch (ParseException parseException) {
                }
            }
            throw e;
        }
    }

    @Nullable
    @Contract(value="null -> null")
    public static synchronized String formatApiDate(@Nullable Date date) {
        if (date == null) {
            return null;
        }
        return API_DATE_FORMAT.format(date);
    }

    private static String[] resolveEnabledProtocols() {
        try {
            SSLContext probe = SSLContext.getInstance("TLS");
            probe.init(null, null, null);
            SSLSocket probeSocket = (SSLSocket)probe.getSocketFactory().createSocket();
            for (String proto : probeSocket.getSupportedProtocols()) {
                if (!"TLSv1.3".equals(proto)) continue;
                probeSocket.close();
                return new String[]{"TLSv1.3", "TLSv1.2"};
            }
            probeSocket.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return new String[]{"TLSv1.2"};
    }

    private static <R> R request(String url, String httpMethod, Class<R> responseType, @Nullable Object body) throws Exception {
        return (R)ApiHttpClient.withConnection(url, connection -> ApiHttpClient.executeJsonRequest(body, httpMethod, url, responseType, connection));
    }

    private static <T> T withConnection(String url, Function<HttpURLConnection, T> requestHandler) throws Exception {
        Exception lastException = null;
        for (int attempt = 0; attempt < 3; ++attempt) {
            try {
                return ApiHttpClient.withConnectionAttempt(url, requestHandler);
            }
            catch (Exception e) {
                lastException = e;
                Throwable cause = e;
                while (cause.getCause() != null) {
                    cause = cause.getCause();
                }
                if ((cause instanceof SocketTimeoutException || cause instanceof ConnectException || cause instanceof IOException) && attempt < 2) {
                    try {
                        Thread.sleep(1000L * (long)(attempt + 1));
                    }
                    catch (InterruptedException interruptedException) {}
                    continue;
                }
                throw e;
            }
        }
        throw lastException;
    }

    private static <T> T withConnectionAttempt(String url, Function<HttpURLConnection, T> requestHandler) throws Exception {
        try {
            URL targetUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)targetUrl.openConnection();
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(25000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            if (connection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConnection = (HttpsURLConnection)connection;
                if (SSL_SOCKET_FACTORY != null) {
                    httpsConnection.setSSLSocketFactory(SSL_SOCKET_FACTORY);
                }
            }
            return requestHandler.apply(connection);
        }
        catch (Throwable error) {
            throw new Exception(error);
        }
    }

    private static Object readJsonResponse(Class responseType, HttpURLConnection connection) {
        try {
            return GSON.fromJson((Reader)new InputStreamReader(connection.getInputStream()), responseType);
        }
        catch (IOException error) {
            throw new RuntimeException(error);
        }
    }

    public static boolean opaquePredicate() {
        boolean state = ApiHttpClient.getOpaqueState();
        return false;
    }

    public static <R> ApiResponse<R> getApiResponse(String url, Function<JsonElement, R> dataParser) throws Exception {
        return ApiResponse.fromJson(ApiHttpClient.get(url, JsonObject.class), dataParser);
    }

    public static void setOpaqueState(boolean state) {
        opaqueState = state;
    }

    public static <R> ApiResponse<R> postApiResponse(String url, Object body, Function<JsonElement, R> dataParser) throws Exception {
        return ApiResponse.fromJson(ApiHttpClient.request(url, "POST", JsonObject.class, body), dataParser);
    }

    public static <R> R post(String url, Class<R> responseType, Object body) throws Exception {
        return ApiHttpClient.request(url, "POST", responseType, body);
    }

    public static <R> ApiResponse<R> deleteApiResponse(String url, Object body, Function<JsonElement, R> dataParser) throws Exception {
        return ApiResponse.fromJson(ApiHttpClient.request(url, "DELETE", JsonObject.class, body), dataParser);
    }

    public static <R> R delete(String url, Class<R> responseType) throws Exception {
        return ApiHttpClient.request(url, "DELETE", responseType, null);
    }

    public static boolean getOpaqueState() {
        return opaqueState;
    }

    private static Throwable preserveThrowable(Throwable error) {
        return error;
    }

    private static Object executeJsonRequest(Object body, String httpMethod, String url, Class responseType, HttpURLConnection connection) {
        try {
            int statusCode;
            block13: {
                byte[] bodyBytes;
                String jsonBody = body != null ? GSON.toJson(body) : null;
                byte[] byArray = bodyBytes = jsonBody != null ? jsonBody.getBytes(StandardCharsets.UTF_8) : null;
                if (jsonBody != null) {
                    connection.setDoOutput(true);
                }
                connection.setRequestMethod(httpMethod);
                if (jsonBody != null) {
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("charset", "utf-8");
                    connection.setRequestProperty("Content-Length", Integer.toString(bodyBytes.length));
                    DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                    Throwable pendingError = null;
                    try {
                        output.write(bodyBytes);
                        if (output == null) break block13;
                    }
                    catch (Throwable writeError) {
                        try {
                            pendingError = writeError;
                            throw writeError;
                        }
                        catch (Throwable closeTrigger) {
                            if (output == null) {
                                throw closeTrigger;
                            }
                            if (pendingError == null) {
                                output.close();
                                throw closeTrigger;
                            }
                            try {
                                output.close();
                                throw closeTrigger;
                            }
                            catch (Throwable closeError) {
                                pendingError.addSuppressed(closeError);
                                throw closeTrigger;
                            }
                        }
                    }
                    output.close();
                }
            }
            if ((statusCode = connection.getResponseCode()) == 200) {
                return GSON.fromJson((Reader)new InputStreamReader(connection.getInputStream()), responseType);
            }
            throw new ApiHttpStatusException(url, httpMethod, statusCode);
        }
        catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    public static <R> R get(String url, Class<R> responseType) throws Exception {
        return (R)ApiHttpClient.withConnection(url, connection -> ApiHttpClient.readJsonResponse(responseType, connection));
    }

    public static <R> R delete(String url, Class<R> responseType, Object body) throws Exception {
        return ApiHttpClient.request(url, "DELETE", responseType, body);
    }

    static {
        ENABLED_PROTOCOLS = ApiHttpClient.resolveEnabledProtocols();
        ApiHttpClient.setOpaqueState(true);
        GSON = new GsonBuilder().serializeNulls().create();
        API_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        System.setProperty("https.protocols", String.join((CharSequence)",", ENABLED_PROTOCOLS));
        System.setProperty("jsse.enableSNIExtension", "true");
        TLSSocketFactory factory = null;
        try {
            TrustManager[] trustManagers = new TrustManager[]{new ApiPermissiveX509ExtendedTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, new SecureRandom());
            factory = new TLSSocketFactory(sslContext.getSocketFactory());
        }
        catch (Exception exception) {
            // empty catch block
        }
        SSL_SOCKET_FACTORY = factory;
    }

    private static class TLSSocketFactory
    extends SSLSocketFactory {
        private final SSLSocketFactory delegate;

        public TLSSocketFactory(SSLSocketFactory delegate) {
            this.delegate = delegate;
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return this.delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return this.delegate.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket() throws IOException {
            return this.configure(this.delegate.createSocket(), null);
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return this.configure(this.delegate.createSocket(s, host, port, autoClose), host);
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            return this.configure(this.delegate.createSocket(host, port), host);
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            return this.configure(this.delegate.createSocket(host, port, localHost, localPort), host);
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return this.configure(this.delegate.createSocket(host, port), host != null ? host.getHostName() : null);
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return this.configure(this.delegate.createSocket(address, port, localAddress, localPort), address != null ? address.getHostName() : null);
        }

        private Socket configure(Socket socket, String host) {
            if (socket instanceof SSLSocket) {
                SSLSocket ssl = (SSLSocket)socket;
                try {
                    ssl.setEnabledProtocols(ENABLED_PROTOCOLS);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (host != null && !host.isEmpty()) {
                    try {
                        SSLParameters params = ssl.getSSLParameters();
                        params.setServerNames(Collections.singletonList(new SNIHostName(host)));
                        ssl.setSSLParameters(params);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
            return socket;
        }
    }
}

