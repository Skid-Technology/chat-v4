/*
 * Decompiled with CFR 0.152.
 */
package net.java.games.input;

import net.java.games.input.Component;
import net.java.games.input.Usage;

final class GenericDesktopUsage
implements Usage {
    private final static GenericDesktopUsage[] map = new GenericDesktopUsage[255];
    final public static GenericDesktopUsage POINTER = new GenericDesktopUsage(1);
    final public static GenericDesktopUsage MOUSE = new GenericDesktopUsage(2);
    final public static GenericDesktopUsage JOYSTICK = new GenericDesktopUsage(4);
    final public static GenericDesktopUsage GAME_PAD = new GenericDesktopUsage(5);
    final public static GenericDesktopUsage KEYBOARD = new GenericDesktopUsage(6);
    final public static GenericDesktopUsage KEYPAD = new GenericDesktopUsage(7);
    final public static GenericDesktopUsage MULTI_AXIS_CONTROLLER = new GenericDesktopUsage(8);
    final public static GenericDesktopUsage var_net_java_games_input_GenericDesktopUsage_X;
    final public static GenericDesktopUsage var_net_java_games_input_GenericDesktopUsage_Y;
    final public static GenericDesktopUsage var_net_java_games_input_GenericDesktopUsage_Z;
    final public static GenericDesktopUsage RX;
    final public static GenericDesktopUsage RY;
    final public static GenericDesktopUsage RZ;
    final public static GenericDesktopUsage SLIDER;
    final public static GenericDesktopUsage DIAL;
    final public static GenericDesktopUsage WHEEL;
    final public static GenericDesktopUsage HATSWITCH;
    final public static GenericDesktopUsage COUNTED_BUFFER;
    final public static GenericDesktopUsage BYTE_COUNT;
    final public static GenericDesktopUsage MOTION_WAKEUP;
    final public static GenericDesktopUsage START;
    final public static GenericDesktopUsage SELECT;
    final public static GenericDesktopUsage VX;
    final public static GenericDesktopUsage VY;
    final public static GenericDesktopUsage VZ;
    final public static GenericDesktopUsage VBRX;
    final public static GenericDesktopUsage VBRY;
    final public static GenericDesktopUsage VBRZ;
    final public static GenericDesktopUsage VNO;
    final public static GenericDesktopUsage SYSTEM_CONTROL;
    final public static GenericDesktopUsage SYSTEM_POWER_DOWN;
    final public static GenericDesktopUsage SYSTEM_SLEEP;
    final public static GenericDesktopUsage SYSTEM_WAKE_UP;
    final public static GenericDesktopUsage SYSTEM_CONTEXT_MENU;
    final public static GenericDesktopUsage SYSTEM_MAIN_MENU;
    final public static GenericDesktopUsage SYSTEM_APP_MENU;
    final public static GenericDesktopUsage SYSTEM_MENU_HELP;
    final public static GenericDesktopUsage SYSTEM_MENU_EXIT;
    final public static GenericDesktopUsage SYSTEM_MENU;
    final public static GenericDesktopUsage SYSTEM_MENU_RIGHT;
    final public static GenericDesktopUsage SYSTEM_MENU_LEFT;
    final public static GenericDesktopUsage SYSTEM_MENU_UP;
    final public static GenericDesktopUsage SYSTEM_MENU_DOWN;
    final public static GenericDesktopUsage DPAD_UP;
    final public static GenericDesktopUsage DPAD_DOWN;
    final public static GenericDesktopUsage DPAD_RIGHT;
    final public static GenericDesktopUsage DPAD_LEFT;
    private final int usage_id;

    public static final GenericDesktopUsage map(int usage_id) {
        if (usage_id < 0 || usage_id >= map.length) {
            return null;
        }
        return map[usage_id];
    }

    private GenericDesktopUsage(int usage_id) {
        GenericDesktopUsage.map[usage_id] = this;
        this.usage_id = usage_id;
    }

    public final String toString() {
        return "GenericDesktopUsage (0x" + Integer.toHexString(this.usage_id) + ")";
    }

    public final Component.Identifier getIdentifier() {
        if (this == var_net_java_games_input_GenericDesktopUsage_X) {
            return Component.Identifier.Axis.var_net_java_games_input_Component$Identifier$Axis_X;
        }
        if (this == var_net_java_games_input_GenericDesktopUsage_Y) {
            return Component.Identifier.Axis.var_net_java_games_input_Component$Identifier$Axis_Y;
        }
        if (this == var_net_java_games_input_GenericDesktopUsage_Z || this == WHEEL) {
            return Component.Identifier.Axis.var_net_java_games_input_Component$Identifier$Axis_Z;
        }
        if (this == RX) {
            return Component.Identifier.Axis.RX;
        }
        if (this == RY) {
            return Component.Identifier.Axis.RY;
        }
        if (this == RZ) {
            return Component.Identifier.Axis.RZ;
        }
        if (this == SLIDER) {
            return Component.Identifier.Axis.SLIDER;
        }
        if (this == HATSWITCH) {
            return Component.Identifier.Axis.POV;
        }
        if (this == SELECT) {
            return Component.Identifier.Button.SELECT;
        }
        return null;
    }

    static {
        var_net_java_games_input_GenericDesktopUsage_X = new GenericDesktopUsage(48);
        var_net_java_games_input_GenericDesktopUsage_Y = new GenericDesktopUsage(49);
        var_net_java_games_input_GenericDesktopUsage_Z = new GenericDesktopUsage(50);
        RX = new GenericDesktopUsage(51);
        RY = new GenericDesktopUsage(52);
        RZ = new GenericDesktopUsage(53);
        SLIDER = new GenericDesktopUsage(54);
        DIAL = new GenericDesktopUsage(55);
        WHEEL = new GenericDesktopUsage(56);
        HATSWITCH = new GenericDesktopUsage(57);
        COUNTED_BUFFER = new GenericDesktopUsage(58);
        BYTE_COUNT = new GenericDesktopUsage(59);
        MOTION_WAKEUP = new GenericDesktopUsage(60);
        START = new GenericDesktopUsage(61);
        SELECT = new GenericDesktopUsage(62);
        VX = new GenericDesktopUsage(64);
        VY = new GenericDesktopUsage(65);
        VZ = new GenericDesktopUsage(66);
        VBRX = new GenericDesktopUsage(67);
        VBRY = new GenericDesktopUsage(68);
        VBRZ = new GenericDesktopUsage(69);
        VNO = new GenericDesktopUsage(70);
        SYSTEM_CONTROL = new GenericDesktopUsage(128);
        SYSTEM_POWER_DOWN = new GenericDesktopUsage(129);
        SYSTEM_SLEEP = new GenericDesktopUsage(130);
        SYSTEM_WAKE_UP = new GenericDesktopUsage(131);
        SYSTEM_CONTEXT_MENU = new GenericDesktopUsage(132);
        SYSTEM_MAIN_MENU = new GenericDesktopUsage(133);
        SYSTEM_APP_MENU = new GenericDesktopUsage(134);
        SYSTEM_MENU_HELP = new GenericDesktopUsage(135);
        SYSTEM_MENU_EXIT = new GenericDesktopUsage(136);
        SYSTEM_MENU = new GenericDesktopUsage(137);
        SYSTEM_MENU_RIGHT = new GenericDesktopUsage(138);
        SYSTEM_MENU_LEFT = new GenericDesktopUsage(139);
        SYSTEM_MENU_UP = new GenericDesktopUsage(140);
        SYSTEM_MENU_DOWN = new GenericDesktopUsage(141);
        DPAD_UP = new GenericDesktopUsage(144);
        DPAD_DOWN = new GenericDesktopUsage(145);
        DPAD_RIGHT = new GenericDesktopUsage(146);
        DPAD_LEFT = new GenericDesktopUsage(147);
    }
}

