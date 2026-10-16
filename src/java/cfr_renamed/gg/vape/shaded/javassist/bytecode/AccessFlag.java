/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.bytecode;

public class AccessFlag {
    final public static int PUBLIC = 1;
    final public static int PRIVATE = 2;
    final public static int PROTECTED = 4;
    final public static int STATIC = 8;
    final public static int FINAL = 16;
    final public static int SYNCHRONIZED = 32;
    final public static int VOLATILE = 64;
    final public static int BRIDGE = 64;
    final public static int TRANSIENT = 128;
    final public static int VARARGS = 128;
    final public static int NATIVE = 256;
    final public static int INTERFACE = 512;
    final public static int ABSTRACT = 1024;
    final public static int STRICT = 2048;
    final public static int SYNTHETIC = 4096;
    final public static int ANNOTATION = 8192;
    final public static int ENUM = 16384;
    final public static int MANDATED = 32768;
    final public static int SUPER = 32;
    final public static int MODULE = 32768;

    public static int setPublic(int accflags) {
        return accflags & 0xFFFFFFF9 | 1;
    }

    public static int setProtected(int accflags) {
        return accflags & 0xFFFFFFFC | 4;
    }

    public static int setPrivate(int accflags) {
        return accflags & 0xFFFFFFFA | 2;
    }

    public static int setPackage(int accflags) {
        return accflags & 0xFFFFFFF8;
    }

    public static boolean isPublic(int accflags) {
        return (accflags & 1) != 0;
    }

    public static boolean isProtected(int accflags) {
        return (accflags & 4) != 0;
    }

    public static boolean isPrivate(int accflags) {
        return (accflags & 2) != 0;
    }

    public static boolean isPackage(int accflags) {
        return (accflags & 7) == 0;
    }

    public static int clear(int accflags, int clearBit) {
        return accflags & ~clearBit;
    }

    public static int of(int modifier) {
        return modifier;
    }

    public static int toModifier(int accflags) {
        return accflags;
    }
}

