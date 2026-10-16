/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.HttpMethod;
import java.util.HashMap;
import java.util.Map;

public final class RtspMethods {
    final public static HttpMethod OPTIONS = HttpMethod.OPTIONS;
    final public static HttpMethod DESCRIBE = new HttpMethod("DESCRIBE");
    final public static HttpMethod ANNOUNCE = new HttpMethod("ANNOUNCE");
    final public static HttpMethod SETUP = new HttpMethod("SETUP");
    final public static HttpMethod PLAY = new HttpMethod("PLAY");
    final public static HttpMethod PAUSE = new HttpMethod("PAUSE");
    final public static HttpMethod TEARDOWN = new HttpMethod("TEARDOWN");
    final public static HttpMethod GET_PARAMETER = new HttpMethod("GET_PARAMETER");
    final public static HttpMethod SET_PARAMETER = new HttpMethod("SET_PARAMETER");
    final public static HttpMethod REDIRECT = new HttpMethod("REDIRECT");
    final public static HttpMethod RECORD = new HttpMethod("RECORD");
    private final static Map<String, HttpMethod> methodMap = new HashMap<String, HttpMethod>();

    public static HttpMethod valueOf(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        if ((name = name.trim().toUpperCase()).isEmpty()) {
            throw new IllegalArgumentException("empty name");
        }
        HttpMethod result = methodMap.get(name);
        if (result != null) {
            return result;
        }
        return new HttpMethod(name);
    }

    private RtspMethods() {
    }

    static {
        methodMap.put(DESCRIBE.toString(), DESCRIBE);
        methodMap.put(ANNOUNCE.toString(), ANNOUNCE);
        methodMap.put(GET_PARAMETER.toString(), GET_PARAMETER);
        methodMap.put(OPTIONS.toString(), OPTIONS);
        methodMap.put(PAUSE.toString(), PAUSE);
        methodMap.put(PLAY.toString(), PLAY);
        methodMap.put(RECORD.toString(), RECORD);
        methodMap.put(REDIRECT.toString(), REDIRECT);
        methodMap.put(SETUP.toString(), SETUP);
        methodMap.put(SET_PARAMETER.toString(), SET_PARAMETER);
        methodMap.put(TEARDOWN.toString(), TEARDOWN);
    }
}

