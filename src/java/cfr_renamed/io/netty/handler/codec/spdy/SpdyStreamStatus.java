/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.spdy;

public class SpdyStreamStatus
implements Comparable<SpdyStreamStatus> {
    final public static SpdyStreamStatus PROTOCOL_ERROR = new SpdyStreamStatus(1, "PROTOCOL_ERROR");
    final public static SpdyStreamStatus INVALID_STREAM = new SpdyStreamStatus(2, "INVALID_STREAM");
    final public static SpdyStreamStatus REFUSED_STREAM = new SpdyStreamStatus(3, "REFUSED_STREAM");
    final public static SpdyStreamStatus UNSUPPORTED_VERSION = new SpdyStreamStatus(4, "UNSUPPORTED_VERSION");
    final public static SpdyStreamStatus CANCEL = new SpdyStreamStatus(5, "CANCEL");
    final public static SpdyStreamStatus INTERNAL_ERROR = new SpdyStreamStatus(6, "INTERNAL_ERROR");
    final public static SpdyStreamStatus FLOW_CONTROL_ERROR = new SpdyStreamStatus(7, "FLOW_CONTROL_ERROR");
    final public static SpdyStreamStatus STREAM_IN_USE = new SpdyStreamStatus(8, "STREAM_IN_USE");
    final public static SpdyStreamStatus STREAM_ALREADY_CLOSED = new SpdyStreamStatus(9, "STREAM_ALREADY_CLOSED");
    final public static SpdyStreamStatus INVALID_CREDENTIALS = new SpdyStreamStatus(10, "INVALID_CREDENTIALS");
    final public static SpdyStreamStatus FRAME_TOO_LARGE = new SpdyStreamStatus(11, "FRAME_TOO_LARGE");
    private final int code;
    private final String statusPhrase;

    public static SpdyStreamStatus valueOf(int code) {
        if (code == 0) {
            throw new IllegalArgumentException("0 is not a valid status code for a RST_STREAM");
        }
        switch (code) {
            case 1: {
                return PROTOCOL_ERROR;
            }
            case 2: {
                return INVALID_STREAM;
            }
            case 3: {
                return REFUSED_STREAM;
            }
            case 4: {
                return UNSUPPORTED_VERSION;
            }
            case 5: {
                return CANCEL;
            }
            case 6: {
                return INTERNAL_ERROR;
            }
            case 7: {
                return FLOW_CONTROL_ERROR;
            }
            case 8: {
                return STREAM_IN_USE;
            }
            case 9: {
                return STREAM_ALREADY_CLOSED;
            }
            case 10: {
                return INVALID_CREDENTIALS;
            }
            case 11: {
                return FRAME_TOO_LARGE;
            }
        }
        return new SpdyStreamStatus(code, "UNKNOWN (" + code + ')');
    }

    public SpdyStreamStatus(int code, String statusPhrase) {
        if (code == 0) {
            throw new IllegalArgumentException("0 is not a valid status code for a RST_STREAM");
        }
        if (statusPhrase == null) {
            throw new NullPointerException("statusPhrase");
        }
        this.code = code;
        this.statusPhrase = statusPhrase;
    }

    public int code() {
        return this.code;
    }

    public String statusPhrase() {
        return this.statusPhrase;
    }

    public int hashCode() {
        return this.code();
    }

    public boolean equals(Object o) {
        if (!(o instanceof SpdyStreamStatus)) {
            return false;
        }
        return this.code() == ((SpdyStreamStatus)o).code();
    }

    public String toString() {
        return this.statusPhrase();
    }

    @Override
    public int compareTo(SpdyStreamStatus o) {
        return this.code() - o.code();
    }

    @Override
    public int compareTo(Object x0) {
        return this.compareTo((SpdyStreamStatus)x0);
    }
}

