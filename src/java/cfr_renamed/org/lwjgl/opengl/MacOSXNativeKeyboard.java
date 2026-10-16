/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.awt.event.KeyEvent;
import java.nio.ByteBuffer;
import java.util.HashMap;
import org.lwjgl.opengl.EventQueue;

final class MacOSXNativeKeyboard
extends EventQueue {
    private final byte[] key_states = new byte[256];
    private final ByteBuffer event = ByteBuffer.allocate(18);
    private ByteBuffer window_handle;
    private boolean has_deferred_event;
    private long deferred_nanos;
    private int deferred_key_code;
    private byte deferred_key_state;
    private int deferred_character;
    private HashMap<Short, Integer> nativeToLwjglMap = new HashMap();

    MacOSXNativeKeyboard(ByteBuffer window_handle) {
        super(18);
        this.initKeyboardMappings();
        this.window_handle = window_handle;
    }

    private native void nRegisterKeyListener(ByteBuffer var1);

    private native void nUnregisterKeyListener(ByteBuffer var1);

    private void initKeyboardMappings() {
        this.nativeToLwjglMap.put(29, 11);
        this.nativeToLwjglMap.put(18, 2);
        this.nativeToLwjglMap.put(19, 3);
        this.nativeToLwjglMap.put(20, 4);
        this.nativeToLwjglMap.put(21, 5);
        this.nativeToLwjglMap.put(23, 6);
        this.nativeToLwjglMap.put(22, 7);
        this.nativeToLwjglMap.put(26, 8);
        this.nativeToLwjglMap.put(28, 9);
        this.nativeToLwjglMap.put(25, 10);
        this.nativeToLwjglMap.put(0, 30);
        this.nativeToLwjglMap.put(11, 48);
        this.nativeToLwjglMap.put(8, 46);
        this.nativeToLwjglMap.put(2, 32);
        this.nativeToLwjglMap.put(14, 18);
        this.nativeToLwjglMap.put(3, 33);
        this.nativeToLwjglMap.put(5, 34);
        this.nativeToLwjglMap.put(4, 35);
        this.nativeToLwjglMap.put(34, 23);
        this.nativeToLwjglMap.put(38, 36);
        this.nativeToLwjglMap.put(40, 37);
        this.nativeToLwjglMap.put(37, 38);
        this.nativeToLwjglMap.put(46, 50);
        this.nativeToLwjglMap.put(45, 49);
        this.nativeToLwjglMap.put(31, 24);
        this.nativeToLwjglMap.put(35, 25);
        this.nativeToLwjglMap.put(12, 16);
        this.nativeToLwjglMap.put(15, 19);
        this.nativeToLwjglMap.put(1, 31);
        this.nativeToLwjglMap.put(17, 20);
        this.nativeToLwjglMap.put(32, 22);
        this.nativeToLwjglMap.put(9, 47);
        this.nativeToLwjglMap.put(13, 17);
        this.nativeToLwjglMap.put(7, 45);
        this.nativeToLwjglMap.put(16, 21);
        this.nativeToLwjglMap.put(6, 44);
        this.nativeToLwjglMap.put(42, 43);
        this.nativeToLwjglMap.put(43, 51);
        this.nativeToLwjglMap.put(24, 13);
        this.nativeToLwjglMap.put(33, 26);
        this.nativeToLwjglMap.put(27, 12);
        this.nativeToLwjglMap.put(39, 40);
        this.nativeToLwjglMap.put(30, 27);
        this.nativeToLwjglMap.put(41, 39);
        this.nativeToLwjglMap.put(44, 53);
        this.nativeToLwjglMap.put(47, 52);
        this.nativeToLwjglMap.put(50, 41);
        this.nativeToLwjglMap.put(65, 83);
        this.nativeToLwjglMap.put(67, 55);
        this.nativeToLwjglMap.put(69, 78);
        this.nativeToLwjglMap.put(71, 218);
        this.nativeToLwjglMap.put(75, 181);
        this.nativeToLwjglMap.put(76, 156);
        this.nativeToLwjglMap.put(78, 74);
        this.nativeToLwjglMap.put(81, 141);
        this.nativeToLwjglMap.put(82, 82);
        this.nativeToLwjglMap.put(83, 79);
        this.nativeToLwjglMap.put(84, 80);
        this.nativeToLwjglMap.put(85, 81);
        this.nativeToLwjglMap.put(86, 75);
        this.nativeToLwjglMap.put(87, 76);
        this.nativeToLwjglMap.put(88, 77);
        this.nativeToLwjglMap.put(89, 71);
        this.nativeToLwjglMap.put(91, 72);
        this.nativeToLwjglMap.put(92, 73);
        this.nativeToLwjglMap.put(36, 28);
        this.nativeToLwjglMap.put(48, 15);
        this.nativeToLwjglMap.put(49, 57);
        this.nativeToLwjglMap.put(51, 14);
        this.nativeToLwjglMap.put(53, 1);
        this.nativeToLwjglMap.put(54, 220);
        this.nativeToLwjglMap.put(55, 219);
        this.nativeToLwjglMap.put(56, 42);
        this.nativeToLwjglMap.put(57, 58);
        this.nativeToLwjglMap.put(58, 56);
        this.nativeToLwjglMap.put(59, 29);
        this.nativeToLwjglMap.put(60, 54);
        this.nativeToLwjglMap.put(61, 184);
        this.nativeToLwjglMap.put(62, 157);
        this.nativeToLwjglMap.put(63, 196);
        this.nativeToLwjglMap.put(119, 207);
        this.nativeToLwjglMap.put(122, 59);
        this.nativeToLwjglMap.put(120, 60);
        this.nativeToLwjglMap.put(99, 61);
        this.nativeToLwjglMap.put(118, 62);
        this.nativeToLwjglMap.put(96, 63);
        this.nativeToLwjglMap.put(97, 64);
        this.nativeToLwjglMap.put(98, 65);
        this.nativeToLwjglMap.put(100, 66);
        this.nativeToLwjglMap.put(101, 67);
        this.nativeToLwjglMap.put(109, 68);
        this.nativeToLwjglMap.put(103, 87);
        this.nativeToLwjglMap.put(111, 88);
        this.nativeToLwjglMap.put(105, 100);
        this.nativeToLwjglMap.put(107, 101);
        this.nativeToLwjglMap.put(113, 102);
        this.nativeToLwjglMap.put(106, 103);
        this.nativeToLwjglMap.put(64, 104);
        this.nativeToLwjglMap.put(79, 105);
        this.nativeToLwjglMap.put(80, 113);
        this.nativeToLwjglMap.put(117, 211);
        this.nativeToLwjglMap.put(114, 210);
        this.nativeToLwjglMap.put(115, 199);
        this.nativeToLwjglMap.put(121, 209);
        this.nativeToLwjglMap.put(116, 201);
        this.nativeToLwjglMap.put(123, 203);
        this.nativeToLwjglMap.put(124, 205);
        this.nativeToLwjglMap.put(125, 208);
        this.nativeToLwjglMap.put(126, 200);
        this.nativeToLwjglMap.put(10, 167);
        this.nativeToLwjglMap.put(110, 221);
        this.nativeToLwjglMap.put(297, 146);
    }

    public void register() {
        this.nRegisterKeyListener(this.window_handle);
    }

    public void unregister() {
        this.nUnregisterKeyListener(this.window_handle);
    }

    public void putKeyboardEvent(int key_code, byte state, int character, long nanos, boolean repeat) {
        this.event.clear();
        this.event.putInt(key_code).put(state).putInt(character).putLong(nanos).put(repeat ? (byte)1 : 0);
        this.event.flip();
        this.putEvent(this.event);
    }

    public synchronized void poll(ByteBuffer key_down_buffer) {
        this.flushDeferredEvent();
        int old_position = key_down_buffer.position();
        key_down_buffer.put(this.key_states);
        key_down_buffer.position(old_position);
    }

    public synchronized void copyEvents(ByteBuffer dest) {
        this.flushDeferredEvent();
        super.copyEvents(dest);
    }

    private synchronized void handleKey(int key_code, byte state, int character, long nanos) {
        if (character == 65535) {
            character = 0;
        }
        if (state == 1) {
            boolean repeat = false;
            if (this.has_deferred_event) {
                if (nanos == this.deferred_nanos && this.deferred_key_code == key_code) {
                    this.has_deferred_event = false;
                    repeat = true;
                } else {
                    this.flushDeferredEvent();
                }
            }
            this.putKeyEvent(key_code, state, character, nanos, repeat);
        } else {
            this.flushDeferredEvent();
            this.has_deferred_event = true;
            this.deferred_nanos = nanos;
            this.deferred_key_code = key_code;
            this.deferred_key_state = state;
            this.deferred_character = character;
        }
    }

    private void flushDeferredEvent() {
        if (this.has_deferred_event) {
            this.putKeyEvent(this.deferred_key_code, this.deferred_key_state, this.deferred_character, this.deferred_nanos, false);
            this.has_deferred_event = false;
        }
    }

    public void putKeyEvent(int key_code, byte state, int character, long nanos, boolean repeat) {
        int mapped_code = this.getMappedKeyCode((short)key_code);
        if (mapped_code < 0) {
            System.out.println("Unrecognized keycode: " + key_code);
            return;
        }
        if (this.key_states[mapped_code] == state) {
            repeat = true;
        }
        this.key_states[mapped_code] = state;
        int key_int_char = character & 0xFFFF;
        this.putKeyboardEvent(mapped_code, state, key_int_char, nanos, repeat);
    }

    private int getMappedKeyCode(short key_code) {
        if (this.nativeToLwjglMap.containsKey(key_code)) {
            return this.nativeToLwjglMap.get(key_code);
        }
        return -1;
    }

    public void keyPressed(int key_code, String chars, long nanos) {
        char character = chars == null || chars.length() == 0 ? (char)'\u0000' : chars.charAt(0);
        this.handleKey(key_code, 1, character, nanos);
    }

    public void keyReleased(int key_code, String chars, long nanos) {
        char character = chars == null || chars.length() == 0 ? (char)'\u0000' : chars.charAt(0);
        this.handleKey(key_code, 0, character, nanos);
    }

    public void keyTyped(KeyEvent e) {
    }
}

