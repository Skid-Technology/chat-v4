/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opencl;

import org.lwjgl.opencl.CL;
import org.lwjgl.opencl.CLDevice;
import org.lwjgl.opencl.CLDeviceCapabilities;
import org.lwjgl.opencl.CLPlatform;
import org.lwjgl.opencl.CLPlatformCapabilities;

public final class CLCapabilities {
    final static boolean CL_APPLE_ContextLoggingFunctions;
    final static long clLogMessagesToSystemLogAPPLE;
    final static long clLogMessagesToStdoutAPPLE;
    final static long clLogMessagesToStderrAPPLE;
    final static boolean CL_APPLE_SetMemObjectDestructor;
    final static long clSetMemObjectDestructorAPPLE;
    final static boolean CL_APPLE_gl_sharing;
    final static long clGetGLContextInfoAPPLE;
    final static boolean OpenCL10;
    final static long clGetPlatformIDs;
    final static long clGetPlatformInfo;
    final static long clGetDeviceIDs;
    final static long clGetDeviceInfo;
    final static long clCreateContext;
    final static long clCreateContextFromType;
    final static long clRetainContext;
    final static long clReleaseContext;
    final static long clGetContextInfo;
    final static long clCreateCommandQueue;
    final static long clRetainCommandQueue;
    final static long clReleaseCommandQueue;
    final static long clGetCommandQueueInfo;
    final static long clCreateBuffer;
    final static long clEnqueueReadBuffer;
    final static long clEnqueueWriteBuffer;
    final static long clEnqueueCopyBuffer;
    final static long clEnqueueMapBuffer;
    final static long clCreateImage2D;
    final static long clCreateImage3D;
    final static long clGetSupportedImageFormats;
    final static long clEnqueueReadImage;
    final static long clEnqueueWriteImage;
    final static long clEnqueueCopyImage;
    final static long clEnqueueCopyImageToBuffer;
    final static long clEnqueueCopyBufferToImage;
    final static long clEnqueueMapImage;
    final static long clGetImageInfo;
    final static long clRetainMemObject;
    final static long clReleaseMemObject;
    final static long clEnqueueUnmapMemObject;
    final static long clGetMemObjectInfo;
    final static long clCreateSampler;
    final static long clRetainSampler;
    final static long clReleaseSampler;
    final static long clGetSamplerInfo;
    final static long clCreateProgramWithSource;
    final static long clCreateProgramWithBinary;
    final static long clRetainProgram;
    final static long clReleaseProgram;
    final static long clBuildProgram;
    final static long clUnloadCompiler;
    final static long clGetProgramInfo;
    final static long clGetProgramBuildInfo;
    final static long clCreateKernel;
    final static long clCreateKernelsInProgram;
    final static long clRetainKernel;
    final static long clReleaseKernel;
    final static long clSetKernelArg;
    final static long clGetKernelInfo;
    final static long clGetKernelWorkGroupInfo;
    final static long clEnqueueNDRangeKernel;
    final static long clEnqueueTask;
    final static long clEnqueueNativeKernel;
    final static long clWaitForEvents;
    final static long clGetEventInfo;
    final static long clRetainEvent;
    final static long clReleaseEvent;
    final static long clEnqueueMarker;
    final static long clEnqueueBarrier;
    final static long clEnqueueWaitForEvents;
    final static long clGetEventProfilingInfo;
    final static long clFlush;
    final static long clFinish;
    final static long clGetExtensionFunctionAddress;
    final static boolean OpenCL10GL;
    final static long clCreateFromGLBuffer;
    final static long clCreateFromGLTexture2D;
    final static long clCreateFromGLTexture3D;
    final static long clCreateFromGLRenderbuffer;
    final static long clGetGLObjectInfo;
    final static long clGetGLTextureInfo;
    final static long clEnqueueAcquireGLObjects;
    final static long clEnqueueReleaseGLObjects;
    final static boolean OpenCL11;
    final static long clCreateSubBuffer;
    final static long clSetMemObjectDestructorCallback;
    final static long clEnqueueReadBufferRect;
    final static long clEnqueueWriteBufferRect;
    final static long clEnqueueCopyBufferRect;
    final static long clCreateUserEvent;
    final static long clSetUserEventStatus;
    final static long clSetEventCallback;
    final static boolean OpenCL12;
    final static long clRetainDevice;
    final static long clReleaseDevice;
    final static long clCreateSubDevices;
    final static long clCreateImage;
    final static long clCreateProgramWithBuiltInKernels;
    final static long clCompileProgram;
    final static long clLinkProgram;
    final static long clUnloadPlatformCompiler;
    final static long clGetKernelArgInfo;
    final static long clEnqueueFillBuffer;
    final static long clEnqueueFillImage;
    final static long clEnqueueMigrateMemObjects;
    final static long clEnqueueMarkerWithWaitList;
    final static long clEnqueueBarrierWithWaitList;
    final static long clSetPrintfCallback;
    final static long clGetExtensionFunctionAddressForPlatform;
    final static boolean OpenCL12GL;
    final static long clCreateFromGLTexture;
    final static boolean CL_EXT_device_fission;
    final static long clRetainDeviceEXT;
    final static long clReleaseDeviceEXT;
    final static long clCreateSubDevicesEXT;
    final static boolean CL_EXT_migrate_memobject;
    final static long clEnqueueMigrateMemObjectEXT;
    final static boolean CL_KHR_gl_event;
    final static long clCreateEventFromGLsyncKHR;
    final static boolean CL_KHR_gl_sharing;
    final static long clGetGLContextInfoKHR;
    final static boolean CL_KHR_icd;
    final static long clIcdGetPlatformIDsKHR;
    final static boolean CL_KHR_subgroups;
    final static long clGetKernelSubGroupInfoKHR;
    final static boolean CL_KHR_terminate_context;
    final static long clTerminateContextKHR;

    private CLCapabilities() {
    }

    public static CLPlatformCapabilities getPlatformCapabilities(CLPlatform platform) {
        platform.checkValid();
        CLPlatformCapabilities caps = (CLPlatformCapabilities)platform.getCapabilities();
        if (caps == null) {
            caps = new CLPlatformCapabilities(platform);
            platform.setCapabilities(caps);
        }
        return caps;
    }

    public static CLDeviceCapabilities getDeviceCapabilities(CLDevice device) {
        device.checkValid();
        CLDeviceCapabilities caps = (CLDeviceCapabilities)device.getCapabilities();
        if (caps == null) {
            caps = new CLDeviceCapabilities(device);
            device.setCapabilities(caps);
        }
        return caps;
    }

    private static boolean isAPPLE_ContextLoggingFunctionsSupported() {
        return clLogMessagesToSystemLogAPPLE != 0L & clLogMessagesToStdoutAPPLE != 0L & clLogMessagesToStderrAPPLE != 0L;
    }

    private static boolean isAPPLE_SetMemObjectDestructorSupported() {
        return clSetMemObjectDestructorAPPLE != 0L;
    }

    private static boolean isAPPLE_gl_sharingSupported() {
        return clGetGLContextInfoAPPLE != 0L;
    }

    private static boolean isCL10Supported() {
        return clGetPlatformIDs != 0L & clGetPlatformInfo != 0L & clGetDeviceIDs != 0L & clGetDeviceInfo != 0L & clCreateContext != 0L & clCreateContextFromType != 0L & clRetainContext != 0L & clReleaseContext != 0L & clGetContextInfo != 0L & clCreateCommandQueue != 0L & clRetainCommandQueue != 0L & clReleaseCommandQueue != 0L & clGetCommandQueueInfo != 0L & clCreateBuffer != 0L & clEnqueueReadBuffer != 0L & clEnqueueWriteBuffer != 0L & clEnqueueCopyBuffer != 0L & clEnqueueMapBuffer != 0L & clCreateImage2D != 0L & clCreateImage3D != 0L & clGetSupportedImageFormats != 0L & clEnqueueReadImage != 0L & clEnqueueWriteImage != 0L & clEnqueueCopyImage != 0L & clEnqueueCopyImageToBuffer != 0L & clEnqueueCopyBufferToImage != 0L & clEnqueueMapImage != 0L & clGetImageInfo != 0L & clRetainMemObject != 0L & clReleaseMemObject != 0L & clEnqueueUnmapMemObject != 0L & clGetMemObjectInfo != 0L & clCreateSampler != 0L & clRetainSampler != 0L & clReleaseSampler != 0L & clGetSamplerInfo != 0L & clCreateProgramWithSource != 0L & clCreateProgramWithBinary != 0L & clRetainProgram != 0L & clReleaseProgram != 0L & clBuildProgram != 0L & clUnloadCompiler != 0L & clGetProgramInfo != 0L & clGetProgramBuildInfo != 0L & clCreateKernel != 0L & clCreateKernelsInProgram != 0L & clRetainKernel != 0L & clReleaseKernel != 0L & clSetKernelArg != 0L & clGetKernelInfo != 0L & clGetKernelWorkGroupInfo != 0L & clEnqueueNDRangeKernel != 0L & clEnqueueTask != 0L & clEnqueueNativeKernel != 0L & clWaitForEvents != 0L & clGetEventInfo != 0L & clRetainEvent != 0L & clReleaseEvent != 0L & clEnqueueMarker != 0L & clEnqueueBarrier != 0L & clEnqueueWaitForEvents != 0L & clGetEventProfilingInfo != 0L & clFlush != 0L & clFinish != 0L & clGetExtensionFunctionAddress != 0L;
    }

    private static boolean isCL10GLSupported() {
        return clCreateFromGLBuffer != 0L & clCreateFromGLTexture2D != 0L & clCreateFromGLTexture3D != 0L & clCreateFromGLRenderbuffer != 0L & clGetGLObjectInfo != 0L & clGetGLTextureInfo != 0L & clEnqueueAcquireGLObjects != 0L & clEnqueueReleaseGLObjects != 0L;
    }

    private static boolean isCL11Supported() {
        return clCreateSubBuffer != 0L & clSetMemObjectDestructorCallback != 0L & clEnqueueReadBufferRect != 0L & clEnqueueWriteBufferRect != 0L & clEnqueueCopyBufferRect != 0L & clCreateUserEvent != 0L & clSetUserEventStatus != 0L & clSetEventCallback != 0L;
    }

    private static boolean isCL12Supported() {
        boolean bl = clRetainDevice != 0L & clReleaseDevice != 0L & clCreateSubDevices != 0L & clCreateImage != 0L & clCreateProgramWithBuiltInKernels != 0L & clCompileProgram != 0L & clLinkProgram != 0L & clUnloadPlatformCompiler != 0L & clGetKernelArgInfo != 0L & clEnqueueFillBuffer != 0L & clEnqueueFillImage != 0L & clEnqueueMigrateMemObjects != 0L & clEnqueueMarkerWithWaitList != 0L & clEnqueueBarrierWithWaitList != 0L;
        if (clSetPrintfCallback == 0L) {
            // empty if block
        }
        return bl & true & clGetExtensionFunctionAddressForPlatform != 0L;
    }

    private static boolean isCL12GLSupported() {
        return clCreateFromGLTexture != 0L;
    }

    private static boolean isEXT_device_fissionSupported() {
        return clRetainDeviceEXT != 0L & clReleaseDeviceEXT != 0L & clCreateSubDevicesEXT != 0L;
    }

    private static boolean isEXT_migrate_memobjectSupported() {
        return clEnqueueMigrateMemObjectEXT != 0L;
    }

    private static boolean isKHR_gl_eventSupported() {
        return clCreateEventFromGLsyncKHR != 0L;
    }

    private static boolean isKHR_gl_sharingSupported() {
        return clGetGLContextInfoKHR != 0L;
    }

    private static boolean isKHR_icdSupported() {
        if (clIcdGetPlatformIDsKHR == 0L) {
            // empty if block
        }
        return true;
    }

    private static boolean isKHR_subgroupsSupported() {
        return clGetKernelSubGroupInfoKHR != 0L;
    }

    private static boolean isKHR_terminate_contextSupported() {
        return clTerminateContextKHR != 0L;
    }

    static {
        clLogMessagesToSystemLogAPPLE = CL.getFunctionAddress("clLogMessagesToSystemLogAPPLE");
        clLogMessagesToStdoutAPPLE = CL.getFunctionAddress("clLogMessagesToStdoutAPPLE");
        clLogMessagesToStderrAPPLE = CL.getFunctionAddress("clLogMessagesToStderrAPPLE");
        clSetMemObjectDestructorAPPLE = CL.getFunctionAddress("clSetMemObjectDestructorAPPLE");
        clGetGLContextInfoAPPLE = CL.getFunctionAddress("clGetGLContextInfoAPPLE");
        clGetPlatformIDs = CL.getFunctionAddress("clGetPlatformIDs");
        clGetPlatformInfo = CL.getFunctionAddress("clGetPlatformInfo");
        clGetDeviceIDs = CL.getFunctionAddress("clGetDeviceIDs");
        clGetDeviceInfo = CL.getFunctionAddress("clGetDeviceInfo");
        clCreateContext = CL.getFunctionAddress("clCreateContext");
        clCreateContextFromType = CL.getFunctionAddress("clCreateContextFromType");
        clRetainContext = CL.getFunctionAddress("clRetainContext");
        clReleaseContext = CL.getFunctionAddress("clReleaseContext");
        clGetContextInfo = CL.getFunctionAddress("clGetContextInfo");
        clCreateCommandQueue = CL.getFunctionAddress("clCreateCommandQueue");
        clRetainCommandQueue = CL.getFunctionAddress("clRetainCommandQueue");
        clReleaseCommandQueue = CL.getFunctionAddress("clReleaseCommandQueue");
        clGetCommandQueueInfo = CL.getFunctionAddress("clGetCommandQueueInfo");
        clCreateBuffer = CL.getFunctionAddress("clCreateBuffer");
        clEnqueueReadBuffer = CL.getFunctionAddress("clEnqueueReadBuffer");
        clEnqueueWriteBuffer = CL.getFunctionAddress("clEnqueueWriteBuffer");
        clEnqueueCopyBuffer = CL.getFunctionAddress("clEnqueueCopyBuffer");
        clEnqueueMapBuffer = CL.getFunctionAddress("clEnqueueMapBuffer");
        clCreateImage2D = CL.getFunctionAddress("clCreateImage2D");
        clCreateImage3D = CL.getFunctionAddress("clCreateImage3D");
        clGetSupportedImageFormats = CL.getFunctionAddress("clGetSupportedImageFormats");
        clEnqueueReadImage = CL.getFunctionAddress("clEnqueueReadImage");
        clEnqueueWriteImage = CL.getFunctionAddress("clEnqueueWriteImage");
        clEnqueueCopyImage = CL.getFunctionAddress("clEnqueueCopyImage");
        clEnqueueCopyImageToBuffer = CL.getFunctionAddress("clEnqueueCopyImageToBuffer");
        clEnqueueCopyBufferToImage = CL.getFunctionAddress("clEnqueueCopyBufferToImage");
        clEnqueueMapImage = CL.getFunctionAddress("clEnqueueMapImage");
        clGetImageInfo = CL.getFunctionAddress("clGetImageInfo");
        clRetainMemObject = CL.getFunctionAddress("clRetainMemObject");
        clReleaseMemObject = CL.getFunctionAddress("clReleaseMemObject");
        clEnqueueUnmapMemObject = CL.getFunctionAddress("clEnqueueUnmapMemObject");
        clGetMemObjectInfo = CL.getFunctionAddress("clGetMemObjectInfo");
        clCreateSampler = CL.getFunctionAddress("clCreateSampler");
        clRetainSampler = CL.getFunctionAddress("clRetainSampler");
        clReleaseSampler = CL.getFunctionAddress("clReleaseSampler");
        clGetSamplerInfo = CL.getFunctionAddress("clGetSamplerInfo");
        clCreateProgramWithSource = CL.getFunctionAddress("clCreateProgramWithSource");
        clCreateProgramWithBinary = CL.getFunctionAddress("clCreateProgramWithBinary");
        clRetainProgram = CL.getFunctionAddress("clRetainProgram");
        clReleaseProgram = CL.getFunctionAddress("clReleaseProgram");
        clBuildProgram = CL.getFunctionAddress("clBuildProgram");
        clUnloadCompiler = CL.getFunctionAddress("clUnloadCompiler");
        clGetProgramInfo = CL.getFunctionAddress("clGetProgramInfo");
        clGetProgramBuildInfo = CL.getFunctionAddress("clGetProgramBuildInfo");
        clCreateKernel = CL.getFunctionAddress("clCreateKernel");
        clCreateKernelsInProgram = CL.getFunctionAddress("clCreateKernelsInProgram");
        clRetainKernel = CL.getFunctionAddress("clRetainKernel");
        clReleaseKernel = CL.getFunctionAddress("clReleaseKernel");
        clSetKernelArg = CL.getFunctionAddress("clSetKernelArg");
        clGetKernelInfo = CL.getFunctionAddress("clGetKernelInfo");
        clGetKernelWorkGroupInfo = CL.getFunctionAddress("clGetKernelWorkGroupInfo");
        clEnqueueNDRangeKernel = CL.getFunctionAddress("clEnqueueNDRangeKernel");
        clEnqueueTask = CL.getFunctionAddress("clEnqueueTask");
        clEnqueueNativeKernel = CL.getFunctionAddress("clEnqueueNativeKernel");
        clWaitForEvents = CL.getFunctionAddress("clWaitForEvents");
        clGetEventInfo = CL.getFunctionAddress("clGetEventInfo");
        clRetainEvent = CL.getFunctionAddress("clRetainEvent");
        clReleaseEvent = CL.getFunctionAddress("clReleaseEvent");
        clEnqueueMarker = CL.getFunctionAddress("clEnqueueMarker");
        clEnqueueBarrier = CL.getFunctionAddress("clEnqueueBarrier");
        clEnqueueWaitForEvents = CL.getFunctionAddress("clEnqueueWaitForEvents");
        clGetEventProfilingInfo = CL.getFunctionAddress("clGetEventProfilingInfo");
        clFlush = CL.getFunctionAddress("clFlush");
        clFinish = CL.getFunctionAddress("clFinish");
        clGetExtensionFunctionAddress = CL.getFunctionAddress("clGetExtensionFunctionAddress");
        clCreateFromGLBuffer = CL.getFunctionAddress("clCreateFromGLBuffer");
        clCreateFromGLTexture2D = CL.getFunctionAddress("clCreateFromGLTexture2D");
        clCreateFromGLTexture3D = CL.getFunctionAddress("clCreateFromGLTexture3D");
        clCreateFromGLRenderbuffer = CL.getFunctionAddress("clCreateFromGLRenderbuffer");
        clGetGLObjectInfo = CL.getFunctionAddress("clGetGLObjectInfo");
        clGetGLTextureInfo = CL.getFunctionAddress("clGetGLTextureInfo");
        clEnqueueAcquireGLObjects = CL.getFunctionAddress("clEnqueueAcquireGLObjects");
        clEnqueueReleaseGLObjects = CL.getFunctionAddress("clEnqueueReleaseGLObjects");
        clCreateSubBuffer = CL.getFunctionAddress("clCreateSubBuffer");
        clSetMemObjectDestructorCallback = CL.getFunctionAddress("clSetMemObjectDestructorCallback");
        clEnqueueReadBufferRect = CL.getFunctionAddress("clEnqueueReadBufferRect");
        clEnqueueWriteBufferRect = CL.getFunctionAddress("clEnqueueWriteBufferRect");
        clEnqueueCopyBufferRect = CL.getFunctionAddress("clEnqueueCopyBufferRect");
        clCreateUserEvent = CL.getFunctionAddress("clCreateUserEvent");
        clSetUserEventStatus = CL.getFunctionAddress("clSetUserEventStatus");
        clSetEventCallback = CL.getFunctionAddress("clSetEventCallback");
        clRetainDevice = CL.getFunctionAddress("clRetainDevice");
        clReleaseDevice = CL.getFunctionAddress("clReleaseDevice");
        clCreateSubDevices = CL.getFunctionAddress("clCreateSubDevices");
        clCreateImage = CL.getFunctionAddress("clCreateImage");
        clCreateProgramWithBuiltInKernels = CL.getFunctionAddress("clCreateProgramWithBuiltInKernels");
        clCompileProgram = CL.getFunctionAddress("clCompileProgram");
        clLinkProgram = CL.getFunctionAddress("clLinkProgram");
        clUnloadPlatformCompiler = CL.getFunctionAddress("clUnloadPlatformCompiler");
        clGetKernelArgInfo = CL.getFunctionAddress("clGetKernelArgInfo");
        clEnqueueFillBuffer = CL.getFunctionAddress("clEnqueueFillBuffer");
        clEnqueueFillImage = CL.getFunctionAddress("clEnqueueFillImage");
        clEnqueueMigrateMemObjects = CL.getFunctionAddress("clEnqueueMigrateMemObjects");
        clEnqueueMarkerWithWaitList = CL.getFunctionAddress("clEnqueueMarkerWithWaitList");
        clEnqueueBarrierWithWaitList = CL.getFunctionAddress("clEnqueueBarrierWithWaitList");
        clSetPrintfCallback = CL.getFunctionAddress("clSetPrintfCallback");
        clGetExtensionFunctionAddressForPlatform = CL.getFunctionAddress("clGetExtensionFunctionAddressForPlatform");
        clCreateFromGLTexture = CL.getFunctionAddress("clCreateFromGLTexture");
        clRetainDeviceEXT = CL.getFunctionAddress("clRetainDeviceEXT");
        clReleaseDeviceEXT = CL.getFunctionAddress("clReleaseDeviceEXT");
        clCreateSubDevicesEXT = CL.getFunctionAddress("clCreateSubDevicesEXT");
        clEnqueueMigrateMemObjectEXT = CL.getFunctionAddress("clEnqueueMigrateMemObjectEXT");
        clCreateEventFromGLsyncKHR = CL.getFunctionAddress("clCreateEventFromGLsyncKHR");
        clGetGLContextInfoKHR = CL.getFunctionAddress("clGetGLContextInfoKHR");
        clIcdGetPlatformIDsKHR = CL.getFunctionAddress("clIcdGetPlatformIDsKHR");
        clGetKernelSubGroupInfoKHR = CL.getFunctionAddress("clGetKernelSubGroupInfoKHR");
        clTerminateContextKHR = CL.getFunctionAddress("clTerminateContextKHR");
        CL_APPLE_ContextLoggingFunctions = CLCapabilities.isAPPLE_ContextLoggingFunctionsSupported();
        CL_APPLE_SetMemObjectDestructor = CLCapabilities.isAPPLE_SetMemObjectDestructorSupported();
        CL_APPLE_gl_sharing = CLCapabilities.isAPPLE_gl_sharingSupported();
        OpenCL10 = CLCapabilities.isCL10Supported();
        OpenCL10GL = CLCapabilities.isCL10GLSupported();
        OpenCL11 = CLCapabilities.isCL11Supported();
        OpenCL12 = CLCapabilities.isCL12Supported();
        OpenCL12GL = CLCapabilities.isCL12GLSupported();
        CL_EXT_device_fission = CLCapabilities.isEXT_device_fissionSupported();
        CL_EXT_migrate_memobject = CLCapabilities.isEXT_migrate_memobjectSupported();
        CL_KHR_gl_event = CLCapabilities.isKHR_gl_eventSupported();
        CL_KHR_gl_sharing = CLCapabilities.isKHR_gl_sharingSupported();
        CL_KHR_icd = CLCapabilities.isKHR_icdSupported();
        CL_KHR_subgroups = CLCapabilities.isKHR_subgroupsSupported();
        CL_KHR_terminate_context = CLCapabilities.isKHR_terminate_contextSupported();
    }
}

