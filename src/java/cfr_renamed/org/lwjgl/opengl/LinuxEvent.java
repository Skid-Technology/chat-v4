/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;

final class LinuxEvent {
    final public static int FocusIn = 9;
    final public static int FocusOut = 10;
    final public static int KeyPress = 2;
    final public static int KeyRelease = 3;
    final public static int ButtonPress = 4;
    final public static int ButtonRelease = 5;
    final public static int MotionNotify = 6;
    final public static int EnterNotify = 7;
    final public static int LeaveNotify = 8;
    final public static int UnmapNotify = 18;
    final public static int MapNotify = 19;
    final public static int Expose = 12;
    final public static int ConfigureNotify = 22;
    final public static int ClientMessage = 33;
    private final ByteBuffer event_buffer = LinuxEvent.createEventBuffer();

    LinuxEvent() {
    }

    private static native ByteBuffer createEventBuffer();

    public void copyFrom(LinuxEvent event) {
        int pos = this.event_buffer.position();
        int event_pos = event.event_buffer.position();
        this.event_buffer.put(event.event_buffer);
        this.event_buffer.position(pos);
        event.event_buffer.position(event_pos);
    }

    public static native int getPending(long var0);

    public void sendEvent(long display, long window, boolean propagate, long event_mask) {
        LinuxEvent.nSendEvent(this.event_buffer, display, window, propagate, event_mask);
    }

    private static native void nSendEvent(ByteBuffer var0, long var1, long var3, boolean var5, long var6);

    public boolean filterEvent(long window) {
        return LinuxEvent.nFilterEvent(this.event_buffer, window);
    }

    private static native boolean nFilterEvent(ByteBuffer var0, long var1);

    public void nextEvent(long display) {
        LinuxEvent.nNextEvent(display, this.event_buffer);
    }

    private static native void nNextEvent(long var0, ByteBuffer var2);

    public int getType() {
        return LinuxEvent.nGetType(this.event_buffer);
    }

    private static native int nGetType(ByteBuffer var0);

    public long getWindow() {
        return LinuxEvent.nGetWindow(this.event_buffer);
    }

    private static native long nGetWindow(ByteBuffer var0);

    public void setWindow(long window) {
        LinuxEvent.nSetWindow(this.event_buffer, window);
    }

    private static native void nSetWindow(ByteBuffer var0, long var1);

    public int getFocusMode() {
        return LinuxEvent.nGetFocusMode(this.event_buffer);
    }

    private static native int nGetFocusMode(ByteBuffer var0);

    public int getFocusDetail() {
        return LinuxEvent.nGetFocusDetail(this.event_buffer);
    }

    private static native int nGetFocusDetail(ByteBuffer var0);

    public long getClientMessageType() {
        return LinuxEvent.nGetClientMessageType(this.event_buffer);
    }

    private static native long nGetClientMessageType(ByteBuffer var0);

    public int getClientData(int index) {
        return LinuxEvent.nGetClientData(this.event_buffer, index);
    }

    private static native int nGetClientData(ByteBuffer var0, int var1);

    public int getClientFormat() {
        return LinuxEvent.nGetClientFormat(this.event_buffer);
    }

    private static native int nGetClientFormat(ByteBuffer var0);

    public long getButtonTime() {
        return LinuxEvent.nGetButtonTime(this.event_buffer);
    }

    private static native long nGetButtonTime(ByteBuffer var0);

    public int getButtonState() {
        return LinuxEvent.nGetButtonState(this.event_buffer);
    }

    private static native int nGetButtonState(ByteBuffer var0);

    public int getButtonType() {
        return LinuxEvent.nGetButtonType(this.event_buffer);
    }

    private static native int nGetButtonType(ByteBuffer var0);

    public int getButtonButton() {
        return LinuxEvent.nGetButtonButton(this.event_buffer);
    }

    private static native int nGetButtonButton(ByteBuffer var0);

    public long getButtonRoot() {
        return LinuxEvent.nGetButtonRoot(this.event_buffer);
    }

    private static native long nGetButtonRoot(ByteBuffer var0);

    public int getButtonXRoot() {
        return LinuxEvent.nGetButtonXRoot(this.event_buffer);
    }

    private static native int nGetButtonXRoot(ByteBuffer var0);

    public int getButtonYRoot() {
        return LinuxEvent.nGetButtonYRoot(this.event_buffer);
    }

    private static native int nGetButtonYRoot(ByteBuffer var0);

    public int getButtonX() {
        return LinuxEvent.nGetButtonX(this.event_buffer);
    }

    private static native int nGetButtonX(ByteBuffer var0);

    public int getButtonY() {
        return LinuxEvent.nGetButtonY(this.event_buffer);
    }

    private static native int nGetButtonY(ByteBuffer var0);

    public long getKeyAddress() {
        return LinuxEvent.nGetKeyAddress(this.event_buffer);
    }

    private static native long nGetKeyAddress(ByteBuffer var0);

    public long getKeyTime() {
        return LinuxEvent.nGetKeyTime(this.event_buffer);
    }

    private static native int nGetKeyTime(ByteBuffer var0);

    public int getKeyType() {
        return LinuxEvent.nGetKeyType(this.event_buffer);
    }

    private static native int nGetKeyType(ByteBuffer var0);

    public int getKeyKeyCode() {
        return LinuxEvent.nGetKeyKeyCode(this.event_buffer);
    }

    private static native int nGetKeyKeyCode(ByteBuffer var0);

    public int getKeyState() {
        return LinuxEvent.nGetKeyState(this.event_buffer);
    }

    private static native int nGetKeyState(ByteBuffer var0);
}

