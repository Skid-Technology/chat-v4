/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.bytecode.stackmap;

import gg.vape.shaded.javassist.bytecode.stackmap.TypeData;

public interface TypeTag {
    final public static String TOP_TYPE = "*top*";
    final public static TypeData.BasicType TOP = new TypeData.BasicType("*top*", 0, ' ');
    final public static TypeData.BasicType INTEGER = new TypeData.BasicType("int", 1, 'I');
    final public static TypeData.BasicType FLOAT = new TypeData.BasicType("float", 2, 'F');
    final public static TypeData.BasicType DOUBLE = new TypeData.BasicType("double", 3, 'D');
    final public static TypeData.BasicType LONG = new TypeData.BasicType("long", 4, 'J');
}

