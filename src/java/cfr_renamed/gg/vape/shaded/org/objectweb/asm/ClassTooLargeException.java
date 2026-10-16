/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm;

public final class ClassTooLargeException
extends IndexOutOfBoundsException {
    private final static long serialVersionUID = 160715609518896765L;
    private final String className;
    private final int constantPoolCount;

    public ClassTooLargeException(String className, int constantPoolCount) {
        super(ClassTooLargeException.stringConcat$0(className));
        this.className = className;
        this.constantPoolCount = constantPoolCount;
    }

    private static String stringConcat$0(String string) {
        return "Class too large: " + string;
    }

    public String getClassName() {
        return this.className;
    }

    public int getConstantPoolCount() {
        return this.constantPoolCount;
    }
}

