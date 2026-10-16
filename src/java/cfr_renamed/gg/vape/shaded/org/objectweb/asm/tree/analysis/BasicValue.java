/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.tree.analysis;

import gg.vape.shaded.org.objectweb.asm.Type;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.Value;

public class BasicValue
implements Value {
    final public static BasicValue UNINITIALIZED_VALUE = new BasicValue(null);
    final public static BasicValue INT_VALUE = new BasicValue(Type.INT_TYPE);
    final public static BasicValue FLOAT_VALUE = new BasicValue(Type.FLOAT_TYPE);
    final public static BasicValue LONG_VALUE = new BasicValue(Type.LONG_TYPE);
    final public static BasicValue DOUBLE_VALUE = new BasicValue(Type.DOUBLE_TYPE);
    final public static BasicValue REFERENCE_VALUE = new BasicValue(Type.getObjectType("java/lang/Object"));
    final public static BasicValue RETURNADDRESS_VALUE = new BasicValue(Type.VOID_TYPE);
    private final Type type;

    public BasicValue(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    public int getSize() {
        return this.type == Type.LONG_TYPE || this.type == Type.DOUBLE_TYPE ? 2 : 1;
    }

    public boolean isReference() {
        return this.type != null && (this.type.getSort() == 10 || this.type.getSort() == 9);
    }

    public boolean equals(Object value) {
        if (value == this) {
            return true;
        }
        if (value instanceof BasicValue) {
            if (this.type == null) {
                return ((BasicValue)value).type == null;
            }
            return this.type.equals(((BasicValue)value).type);
        }
        return false;
    }

    public int hashCode() {
        return this.type == null ? 0 : this.type.hashCode();
    }

    public String toString() {
        if (this == UNINITIALIZED_VALUE) {
            return ".";
        }
        if (this == RETURNADDRESS_VALUE) {
            return "A";
        }
        if (this == REFERENCE_VALUE) {
            return "R";
        }
        return this.type.getDescriptor();
    }
}

