/*
 * Decompiled with CFR 0.152.
 */
package net.java.games.input;

import net.java.games.input.Component;
import net.java.games.input.EventQueue;
import net.java.games.input.Rumbler;

public interface Controller {
    public Controller[] getControllers();

    public Type getType();

    public Component[] getComponents();

    public Component getComponent(Component.Identifier var1);

    public Rumbler[] getRumblers();

    public boolean poll();

    public void setEventQueueSize(int var1);

    public EventQueue getEventQueue();

    public PortType getPortType();

    public int getPortNumber();

    public String getName();

    public static final class PortType {
        private final String name;
        final public static PortType UNKNOWN = new PortType("Unknown");
        final public static PortType USB = new PortType("USB port");
        final public static PortType GAME = new PortType("Game port");
        final public static PortType NETWORK = new PortType("Network port");
        final public static PortType SERIAL = new PortType("Serial port");
        final public static PortType I8042 = new PortType("i8042 (PS/2)");
        final public static PortType PARALLEL = new PortType("Parallel port");

        protected PortType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    public static class Type {
        private final String name;
        final public static Type UNKNOWN = new Type("Unknown");
        final public static Type MOUSE = new Type("Mouse");
        final public static Type KEYBOARD = new Type("Keyboard");
        final public static Type FINGERSTICK = new Type("Fingerstick");
        final public static Type GAMEPAD = new Type("Gamepad");
        final public static Type HEADTRACKER = new Type("Headtracker");
        final public static Type RUDDER = new Type("Rudder");
        final public static Type STICK = new Type("Stick");
        final public static Type TRACKBALL = new Type("Trackball");
        final public static Type TRACKPAD = new Type("Trackpad");
        final public static Type WHEEL = new Type("Wheel");

        protected Type(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}

