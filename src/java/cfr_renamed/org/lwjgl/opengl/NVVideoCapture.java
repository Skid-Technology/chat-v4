/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.APIUtil;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public final class NVVideoCapture {
    final public static int GL_VIDEO_BUFFER_NV = 36896;
    final public static int GL_VIDEO_BUFFER_BINDING_NV = 36897;
    final public static int GL_FIELD_UPPER_NV = 36898;
    final public static int GL_FIELD_LOWER_NV = 36899;
    final public static int GL_NUM_VIDEO_CAPTURE_STREAMS_NV = 36900;
    final public static int GL_NEXT_VIDEO_CAPTURE_BUFFER_STATUS_NV = 36901;
    final public static int GL_LAST_VIDEO_CAPTURE_STATUS_NV = 36903;
    final public static int GL_VIDEO_BUFFER_PITCH_NV = 36904;
    final public static int GL_VIDEO_CAPTURE_FRAME_WIDTH_NV = 36920;
    final public static int GL_VIDEO_CAPTURE_FRAME_HEIGHT_NV = 36921;
    final public static int GL_VIDEO_CAPTURE_FIELD_UPPER_HEIGHT_NV = 36922;
    final public static int GL_VIDEO_CAPTURE_FIELD_LOWER_HEIGHT_NV = 36923;
    final public static int GL_VIDEO_CAPTURE_TO_422_SUPPORTED_NV = 36902;
    final public static int GL_VIDEO_COLOR_CONVERSION_MATRIX_NV = 36905;
    final public static int GL_VIDEO_COLOR_CONVERSION_MAX_NV = 36906;
    final public static int GL_VIDEO_COLOR_CONVERSION_MIN_NV = 36907;
    final public static int GL_VIDEO_COLOR_CONVERSION_OFFSET_NV = 36908;
    final public static int GL_VIDEO_BUFFER_INTERNAL_FORMAT_NV = 36909;
    final public static int GL_VIDEO_CAPTURE_SURFACE_ORIGIN_NV = 36924;
    final public static int GL_PARTIAL_SUCCESS_NV = 36910;
    final public static int GL_SUCCESS_NV = 36911;
    final public static int GL_FAILURE_NV = 36912;
    final public static int GL_YCBYCR8_422_NV = 36913;
    final public static int GL_YCBAYCR8A_4224_NV = 36914;
    final public static int GL_Z6Y10Z6CB10Z6Y10Z6CR10_422_NV = 36915;
    final public static int GL_Z6Y10Z6CB10Z6A10Z6Y10Z6CR10Z6A10_4224_NV = 36916;
    final public static int GL_Z4Y12Z4CB12Z4Y12Z4CR12_422_NV = 36917;
    final public static int GL_Z4Y12Z4CB12Z4A12Z4Y12Z4CR12Z4A12_4224_NV = 36918;
    final public static int GL_Z4Y12Z4CB12Z4CR12_444_NV = 36919;
    final public static int GL_NUM_VIDEO_CAPTURE_SLOTS_NV = 8399;
    final public static int GL_UNIQUE_ID_NV = 8398;

    private NVVideoCapture() {
    }

    public static void glBeginVideoCaptureNV(int video_capture_slot) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glBeginVideoCaptureNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVideoCapture.nglBeginVideoCaptureNV(video_capture_slot, function_pointer);
    }

    static native void nglBeginVideoCaptureNV(int var0, long var1);

    public static void glBindVideoCaptureStreamBufferNV(int video_capture_slot, int stream, int frame_region, long offset) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glBindVideoCaptureStreamBufferNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVideoCapture.nglBindVideoCaptureStreamBufferNV(video_capture_slot, stream, frame_region, offset, function_pointer);
    }

    static native void nglBindVideoCaptureStreamBufferNV(int var0, int var1, int var2, long var3, long var5);

    public static void glBindVideoCaptureStreamTextureNV(int video_capture_slot, int stream, int frame_region, int target, int texture) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glBindVideoCaptureStreamTextureNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVideoCapture.nglBindVideoCaptureStreamTextureNV(video_capture_slot, stream, frame_region, target, texture, function_pointer);
    }

    static native void nglBindVideoCaptureStreamTextureNV(int var0, int var1, int var2, int var3, int var4, long var5);

    public static void glEndVideoCaptureNV(int video_capture_slot) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glEndVideoCaptureNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVideoCapture.nglEndVideoCaptureNV(video_capture_slot, function_pointer);
    }

    static native void nglEndVideoCaptureNV(int var0, long var1);

    public static void glGetVideoCaptureNV(int video_capture_slot, int pname, IntBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetVideoCaptureivNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 1);
        NVVideoCapture.nglGetVideoCaptureivNV(video_capture_slot, pname, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetVideoCaptureivNV(int var0, int var1, long var2, long var4);

    public static int glGetVideoCaptureiNV(int video_capture_slot, int pname) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetVideoCaptureivNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        IntBuffer params = APIUtil.getBufferInt(caps);
        NVVideoCapture.nglGetVideoCaptureivNV(video_capture_slot, pname, MemoryUtil.getAddress(params), function_pointer);
        return params.get(0);
    }

    public static void glGetVideoCaptureStreamNV(int video_capture_slot, int stream, int pname, IntBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetVideoCaptureStreamivNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 1);
        NVVideoCapture.nglGetVideoCaptureStreamivNV(video_capture_slot, stream, pname, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetVideoCaptureStreamivNV(int var0, int var1, int var2, long var3, long var5);

    public static int glGetVideoCaptureStreamiNV(int video_capture_slot, int stream, int pname) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetVideoCaptureStreamivNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        IntBuffer params = APIUtil.getBufferInt(caps);
        NVVideoCapture.nglGetVideoCaptureStreamivNV(video_capture_slot, stream, pname, MemoryUtil.getAddress(params), function_pointer);
        return params.get(0);
    }

    public static void glGetVideoCaptureStreamNV(int video_capture_slot, int stream, int pname, FloatBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetVideoCaptureStreamfvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 1);
        NVVideoCapture.nglGetVideoCaptureStreamfvNV(video_capture_slot, stream, pname, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetVideoCaptureStreamfvNV(int var0, int var1, int var2, long var3, long var5);

    public static float glGetVideoCaptureStreamfNV(int video_capture_slot, int stream, int pname) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetVideoCaptureStreamfvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        FloatBuffer params = APIUtil.getBufferFloat(caps);
        NVVideoCapture.nglGetVideoCaptureStreamfvNV(video_capture_slot, stream, pname, MemoryUtil.getAddress(params), function_pointer);
        return params.get(0);
    }

    public static void glGetVideoCaptureStreamNV(int video_capture_slot, int stream, int pname, DoubleBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetVideoCaptureStreamdvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 1);
        NVVideoCapture.nglGetVideoCaptureStreamdvNV(video_capture_slot, stream, pname, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetVideoCaptureStreamdvNV(int var0, int var1, int var2, long var3, long var5);

    public static double glGetVideoCaptureStreamdNV(int video_capture_slot, int stream, int pname) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetVideoCaptureStreamdvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        DoubleBuffer params = APIUtil.getBufferDouble(caps);
        NVVideoCapture.nglGetVideoCaptureStreamdvNV(video_capture_slot, stream, pname, MemoryUtil.getAddress(params), function_pointer);
        return params.get(0);
    }

    public static int glVideoCaptureNV(int video_capture_slot, IntBuffer sequence_num, LongBuffer capture_time) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVideoCaptureNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(sequence_num, 1);
        BufferChecks.checkBuffer(capture_time, 1);
        int __result = NVVideoCapture.nglVideoCaptureNV(video_capture_slot, MemoryUtil.getAddress(sequence_num), MemoryUtil.getAddress(capture_time), function_pointer);
        return __result;
    }

    static native int nglVideoCaptureNV(int var0, long var1, long var3, long var5);

    public static void glVideoCaptureStreamParameterNV(int video_capture_slot, int stream, int pname, IntBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVideoCaptureStreamParameterivNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 16);
        NVVideoCapture.nglVideoCaptureStreamParameterivNV(video_capture_slot, stream, pname, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglVideoCaptureStreamParameterivNV(int var0, int var1, int var2, long var3, long var5);

    public static void glVideoCaptureStreamParameterNV(int video_capture_slot, int stream, int pname, FloatBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVideoCaptureStreamParameterfvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 16);
        NVVideoCapture.nglVideoCaptureStreamParameterfvNV(video_capture_slot, stream, pname, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglVideoCaptureStreamParameterfvNV(int var0, int var1, int var2, long var3, long var5);

    public static void glVideoCaptureStreamParameterNV(int video_capture_slot, int stream, int pname, DoubleBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVideoCaptureStreamParameterdvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 16);
        NVVideoCapture.nglVideoCaptureStreamParameterdvNV(video_capture_slot, stream, pname, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglVideoCaptureStreamParameterdvNV(int var0, int var1, int var2, long var3, long var5);
}

