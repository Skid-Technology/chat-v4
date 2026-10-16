/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.input;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.OpenGLPackageAccess;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.InputImplementation;

public class Keyboard {
    final public static int EVENT_SIZE = 18;
    final public static int CHAR_NONE = 0;
    final public static int KEY_NONE = 0;
    final public static int KEY_ESCAPE = 1;
    final public static int KEY_1 = 2;
    final public static int KEY_2 = 3;
    final public static int KEY_3 = 4;
    final public static int KEY_4 = 5;
    final public static int KEY_5 = 6;
    final public static int KEY_6 = 7;
    final public static int KEY_7 = 8;
    final public static int KEY_8 = 9;
    final public static int KEY_9 = 10;
    final public static int KEY_0 = 11;
    final public static int KEY_MINUS = 12;
    final public static int KEY_EQUALS = 13;
    final public static int KEY_BACK = 14;
    final public static int KEY_TAB = 15;
    final public static int KEY_Q = 16;
    final public static int KEY_W = 17;
    final public static int KEY_E = 18;
    final public static int KEY_R = 19;
    final public static int KEY_T = 20;
    final public static int KEY_Y = 21;
    final public static int KEY_U = 22;
    final public static int KEY_I = 23;
    final public static int KEY_O = 24;
    final public static int KEY_P = 25;
    final public static int KEY_LBRACKET = 26;
    final public static int KEY_RBRACKET = 27;
    final public static int KEY_RETURN = 28;
    final public static int KEY_LCONTROL = 29;
    final public static int KEY_A = 30;
    final public static int KEY_S = 31;
    final public static int KEY_D = 32;
    final public static int KEY_F = 33;
    final public static int KEY_G = 34;
    final public static int KEY_H = 35;
    final public static int KEY_J = 36;
    final public static int KEY_K = 37;
    final public static int KEY_L = 38;
    final public static int KEY_SEMICOLON = 39;
    final public static int KEY_APOSTROPHE = 40;
    final public static int KEY_GRAVE = 41;
    final public static int KEY_LSHIFT = 42;
    final public static int KEY_BACKSLASH = 43;
    final public static int KEY_Z = 44;
    final public static int KEY_X = 45;
    final public static int KEY_C = 46;
    final public static int KEY_V = 47;
    final public static int KEY_B = 48;
    final public static int KEY_N = 49;
    final public static int KEY_M = 50;
    final public static int KEY_COMMA = 51;
    final public static int KEY_PERIOD = 52;
    final public static int KEY_SLASH = 53;
    final public static int KEY_RSHIFT = 54;
    final public static int KEY_MULTIPLY = 55;
    final public static int KEY_LMENU = 56;
    final public static int KEY_SPACE = 57;
    final public static int KEY_CAPITAL = 58;
    final public static int KEY_F1 = 59;
    final public static int KEY_F2 = 60;
    final public static int KEY_F3 = 61;
    final public static int KEY_F4 = 62;
    final public static int KEY_F5 = 63;
    final public static int KEY_F6 = 64;
    final public static int KEY_F7 = 65;
    final public static int KEY_F8 = 66;
    final public static int KEY_F9 = 67;
    final public static int KEY_F10 = 68;
    final public static int KEY_NUMLOCK = 69;
    final public static int KEY_SCROLL = 70;
    final public static int KEY_NUMPAD7 = 71;
    final public static int KEY_NUMPAD8 = 72;
    final public static int KEY_NUMPAD9 = 73;
    final public static int KEY_SUBTRACT = 74;
    final public static int KEY_NUMPAD4 = 75;
    final public static int KEY_NUMPAD5 = 76;
    final public static int KEY_NUMPAD6 = 77;
    final public static int KEY_ADD = 78;
    final public static int KEY_NUMPAD1 = 79;
    final public static int KEY_NUMPAD2 = 80;
    final public static int KEY_NUMPAD3 = 81;
    final public static int KEY_NUMPAD0 = 82;
    final public static int KEY_DECIMAL = 83;
    final public static int KEY_F11 = 87;
    final public static int KEY_F12 = 88;
    final public static int KEY_F13 = 100;
    final public static int KEY_F14 = 101;
    final public static int KEY_F15 = 102;
    final public static int KEY_F16 = 103;
    final public static int KEY_F17 = 104;
    final public static int KEY_F18 = 105;
    final public static int KEY_KANA = 112;
    final public static int KEY_F19 = 113;
    final public static int KEY_CONVERT = 121;
    final public static int KEY_NOCONVERT = 123;
    final public static int KEY_YEN = 125;
    final public static int KEY_NUMPADEQUALS = 141;
    final public static int KEY_CIRCUMFLEX = 144;
    final public static int KEY_AT = 145;
    final public static int KEY_COLON = 146;
    final public static int KEY_UNDERLINE = 147;
    final public static int KEY_KANJI = 148;
    final public static int KEY_STOP = 149;
    final public static int KEY_AX = 150;
    final public static int KEY_UNLABELED = 151;
    final public static int KEY_NUMPADENTER = 156;
    final public static int KEY_RCONTROL = 157;
    final public static int KEY_SECTION = 167;
    final public static int KEY_NUMPADCOMMA = 179;
    final public static int KEY_DIVIDE = 181;
    final public static int KEY_SYSRQ = 183;
    final public static int KEY_RMENU = 184;
    final public static int KEY_FUNCTION = 196;
    final public static int KEY_PAUSE = 197;
    final public static int KEY_HOME = 199;
    final public static int KEY_UP = 200;
    final public static int KEY_PRIOR = 201;
    final public static int KEY_LEFT = 203;
    final public static int KEY_RIGHT = 205;
    final public static int KEY_END = 207;
    final public static int KEY_DOWN = 208;
    final public static int KEY_NEXT = 209;
    final public static int KEY_INSERT = 210;
    final public static int KEY_DELETE = 211;
    final public static int KEY_CLEAR = 218;
    final public static int KEY_LMETA = 219;
    final public static int KEY_LWIN = 219;
    final public static int KEY_RMETA = 220;
    final public static int KEY_RWIN = 220;
    final public static int KEY_APPS = 221;
    final public static int KEY_POWER = 222;
    final public static int KEY_SLEEP = 223;
    final public static int KEYBOARD_SIZE = 256;
    private final static int BUFFER_SIZE = 50;
    private final static String[] keyName = new String[256];
    private final static Map<String, Integer> keyMap = new HashMap<String, Integer>(253);
    private static int counter;
    private final static int keyCount;
    private static boolean created;
    private static boolean repeat_enabled;
    private final static ByteBuffer keyDownBuffer;
    private static ByteBuffer readBuffer;
    private static KeyEvent current_event;
    private static KeyEvent tmp_event;
    private static boolean initialized;
    private static InputImplementation implementation;

    private Keyboard() {
    }

    private static void initialize() {
        if (initialized) {
            return;
        }
        Sys.initialize();
        initialized = true;
    }

    private static void create(InputImplementation impl) throws LWJGLException {
        if (created) {
            return;
        }
        if (!initialized) {
            Keyboard.initialize();
        }
        implementation = impl;
        implementation.createKeyboard();
        created = true;
        readBuffer = ByteBuffer.allocate(900);
        Keyboard.reset();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void create() throws LWJGLException {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            if (!Display.isCreated()) {
                throw new IllegalStateException("Display must be created.");
            }
            Keyboard.create(OpenGLPackageAccess.createImplementation());
        }
    }

    private static void reset() {
        readBuffer.limit(0);
        for (int i = 0; i < keyDownBuffer.remaining(); ++i) {
            keyDownBuffer.put(i, 0);
        }
        Keyboard.current_event.reset();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isCreated() {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            return created;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void destroy() {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            if (!created) {
                return;
            }
            created = false;
            implementation.destroyKeyboard();
            Keyboard.reset();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void poll() {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            if (!created) {
                throw new IllegalStateException("Keyboard must be created before you can poll the device");
            }
            implementation.pollKeyboard(keyDownBuffer);
            Keyboard.read();
        }
    }

    private static void read() {
        readBuffer.compact();
        implementation.readKeyboard(readBuffer);
        readBuffer.flip();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isKeyDown(int key) {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            if (!created) {
                throw new IllegalStateException("Keyboard must be created before you can query key state");
            }
            return keyDownBuffer.get(key) != 0;
        }
    }

    public static synchronized String getKeyName(int key) {
        return keyName[key];
    }

    public static synchronized int getKeyIndex(String keyName) {
        Integer ret = keyMap.get(keyName);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getNumKeyboardEvents() {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            if (!created) {
                throw new IllegalStateException("Keyboard must be created before you can read events");
            }
            int old_position = readBuffer.position();
            int num_events = 0;
            while (Keyboard.readNext(tmp_event) && (!tmp_event.repeat || repeat_enabled)) {
                ++num_events;
            }
            readBuffer.position(old_position);
            return num_events;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean next() {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            boolean result;
            if (!created) {
                throw new IllegalStateException("Keyboard must be created before you can read events");
            }
            while ((result = Keyboard.readNext(current_event)) && current_event.repeat && !repeat_enabled) {
            }
            return result;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void enableRepeatEvents(boolean enable) {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            repeat_enabled = enable;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean areRepeatEventsEnabled() {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            return repeat_enabled;
        }
    }

    private static boolean readNext(KeyEvent event) {
        if (readBuffer.hasRemaining()) {
            event.key = Keyboard.readBuffer.getInt() & 0xFF;
            event.state = Keyboard.readBuffer.get() != 0;
            event.character = Keyboard.readBuffer.getInt();
            event.nanos = Keyboard.readBuffer.getLong();
            event.repeat = Keyboard.readBuffer.get() == 1;
            return true;
        }
        return false;
    }

    public static int getKeyCount() {
        return keyCount;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static char getEventCharacter() {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            return (char)current_event.character;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getEventKey() {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            return current_event.key;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean getEventKeyState() {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            return current_event.state;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long getEventNanoseconds() {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            return current_event.nanos;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isRepeatEvent() {
        Object object = OpenGLPackageAccess.global_lock;
        synchronized (object) {
            return current_event.repeat;
        }
    }

    static {
        Field[] fields = Keyboard.class.getFields();
        try {
            for (Field field : fields) {
                String name;
                if (!Modifier.isStatic(field.getModifiers()) || !Modifier.isPublic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers()) || !field.getType().equals(Integer.TYPE) || !field.getName().startsWith("KEY_") || field.getName().endsWith("WIN")) continue;
                int key = field.getInt(null);
                Keyboard.keyName[key] = name = field.getName().substring(4);
                keyMap.put(name, key);
                ++counter;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        keyCount = counter;
        keyDownBuffer = BufferUtils.createByteBuffer(256);
        current_event = new KeyEvent();
        tmp_event = new KeyEvent();
    }

    private static final class KeyEvent {
        private int character;
        private int key;
        private boolean state;
        private long nanos;
        private boolean repeat;

        private KeyEvent() {
        }

        private void reset() {
            this.character = 0;
            this.key = 0;
            this.state = false;
            this.repeat = false;
        }
    }
}

