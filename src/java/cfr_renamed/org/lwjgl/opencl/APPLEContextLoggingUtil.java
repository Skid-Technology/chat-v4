/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opencl;

import java.nio.ByteBuffer;
import org.lwjgl.opencl.CLCapabilities;
import org.lwjgl.opencl.CLContextCallback;
import org.lwjgl.opencl.CallbackUtil;

public final class APPLEContextLoggingUtil {
    final public static CLContextCallback SYSTEM_LOG_CALLBACK;
    final public static CLContextCallback STD_OUT_CALLBACK;
    final public static CLContextCallback STD_ERR_CALLBACK;

    private APPLEContextLoggingUtil() {
    }

    static {
        if (CLCapabilities.CL_APPLE_ContextLoggingFunctions) {
            SYSTEM_LOG_CALLBACK = new CLContextCallback(CallbackUtil.getLogMessageToSystemLogAPPLE()){

                protected void handleMessage(String errinfo, ByteBuffer private_info) {
                    throw new UnsupportedOperationException();
                }
            };
            STD_OUT_CALLBACK = new CLContextCallback(CallbackUtil.getLogMessageToStdoutAPPLE()){

                protected void handleMessage(String errinfo, ByteBuffer private_info) {
                    throw new UnsupportedOperationException();
                }
            };
            STD_ERR_CALLBACK = new CLContextCallback(CallbackUtil.getLogMessageToStderrAPPLE()){

                protected void handleMessage(String errinfo, ByteBuffer private_info) {
                    throw new UnsupportedOperationException();
                }
            };
        } else {
            SYSTEM_LOG_CALLBACK = null;
            STD_OUT_CALLBACK = null;
            STD_ERR_CALLBACK = null;
        }
    }
}

