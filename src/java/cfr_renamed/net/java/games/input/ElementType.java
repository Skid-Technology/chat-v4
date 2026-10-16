/*
 * Decompiled with CFR 0.152.
 */
package net.java.games.input;

final class ElementType {
    private final static ElementType[] map = new ElementType[514];
    final public static ElementType INPUT_MISC = new ElementType(1);
    final public static ElementType INPUT_BUTTON = new ElementType(2);
    final public static ElementType INPUT_AXIS = new ElementType(3);
    final public static ElementType INPUT_SCANCODES = new ElementType(4);
    final public static ElementType OUTPUT = new ElementType(129);
    final public static ElementType FEATURE = new ElementType(257);
    final public static ElementType COLLECTION = new ElementType(513);
    private final int type_id;

    public static final ElementType map(int type_id) {
        if (type_id < 0 || type_id >= map.length) {
            return null;
        }
        return map[type_id];
    }

    private ElementType(int type_id) {
        ElementType.map[type_id] = this;
        this.type_id = type_id;
    }

    public final String toString() {
        return "ElementType (0x" + Integer.toHexString(this.type_id) + ")";
    }
}

