/*
 * Decompiled with CFR 0.152.
 */
package net.java.games.input;

import java.lang.reflect.Method;
import net.java.games.input.ButtonUsage;
import net.java.games.input.GenericDesktopUsage;
import net.java.games.input.KeyboardUsage;
import net.java.games.input.Usage;

final class UsagePage {
    private final static UsagePage[] map = new UsagePage[255];
    final public static UsagePage UNDEFINED = new UsagePage(0);
    final public static UsagePage GENERIC_DESKTOP = new UsagePage(1, GenericDesktopUsage.class);
    final public static UsagePage SIMULATION = new UsagePage(2);
    final public static UsagePage VR = new UsagePage(3);
    final public static UsagePage SPORT = new UsagePage(4);
    final public static UsagePage GAME = new UsagePage(5);
    final public static UsagePage KEYBOARD_OR_KEYPAD = new UsagePage(7, KeyboardUsage.class);
    final public static UsagePage LEDS = new UsagePage(8);
    final public static UsagePage BUTTON = new UsagePage(9, ButtonUsage.class);
    final public static UsagePage ORDINAL = new UsagePage(10);
    final public static UsagePage TELEPHONY = new UsagePage(11);
    final public static UsagePage CONSUMER = new UsagePage(12);
    final public static UsagePage DIGITIZER = new UsagePage(13);
    final public static UsagePage PID = new UsagePage(15);
    final public static UsagePage UNICODE = new UsagePage(16);
    final public static UsagePage ALPHANUMERIC_DISPLAY = new UsagePage(20);
    final public static UsagePage POWER_DEVICE = new UsagePage(132);
    final public static UsagePage BATTERY_SYSTEM = new UsagePage(133);
    final public static UsagePage BAR_CODE_SCANNER = new UsagePage(140);
    final public static UsagePage SCALE = new UsagePage(141);
    final public static UsagePage CAMERACONTROL = new UsagePage(144);
    final public static UsagePage ARCADE = new UsagePage(145);
    private final Class usage_class;
    private final int usage_page_id;

    public static final UsagePage map(int page_id) {
        if (page_id < 0 || page_id >= map.length) {
            return null;
        }
        return map[page_id];
    }

    private UsagePage(int page_id, Class usage_class) {
        UsagePage.map[page_id] = this;
        this.usage_class = usage_class;
        this.usage_page_id = page_id;
    }

    private UsagePage(int page_id) {
        this(page_id, null);
    }

    public final String toString() {
        return "UsagePage (0x" + Integer.toHexString(this.usage_page_id) + ")";
    }

    public final Usage mapUsage(int usage_id) {
        if (this.usage_class == null) {
            return null;
        }
        try {
            Method map_method = this.usage_class.getMethod("map", Integer.TYPE);
            Object result = map_method.invoke(null, new Integer(usage_id));
            return (Usage)result;
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }
}

