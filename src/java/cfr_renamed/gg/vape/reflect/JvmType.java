/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.reflect;

import gg.vape.reflect.Type;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class JvmType {
    final public static int VOID = 0;
    final public static int BOOLEAN = 1;
    final public static int CHAR = 2;
    final public static int BYTE = 3;
    final public static int SHORT = 4;
    final public static int INT = 5;
    final public static int FLOAT = 6;
    final public static int LONG = 7;
    final public static int DOUBLE = 8;
    final public static int ARRAY = 9;
    final public static int OBJECT = 10;
    final public static int METHOD = 11;
    final public static JvmType VOID_TYPE = new JvmType(Type.VOID_TYPE);
    final public static JvmType BOOLEAN_TYPE = new JvmType(Type.BOOLEAN_TYPE);
    final public static JvmType CHAR_TYPE = new JvmType(Type.CHAR_TYPE);
    final public static JvmType BYTE_TYPE = new JvmType(Type.BYTE_TYPE);
    final public static JvmType SHORT_TYPE = new JvmType(Type.SHORT_TYPE);
    final public static JvmType INT_TYPE = new JvmType(Type.INT_TYPE);
    final public static JvmType FLOAT_TYPE = new JvmType(Type.FLOAT_TYPE);
    final public static JvmType LONG_TYPE = new JvmType(Type.LONG_TYPE);
    final public static JvmType DOUBLE_TYPE = new JvmType(Type.DOUBLE_TYPE);
    private final Type legacyType;

    private JvmType(Type legacyType) {
        if (legacyType == null) {
            throw new NullPointerException("legacyType");
        }
        this.legacyType = legacyType;
    }

    public static JvmType parse(String descriptor) {
        return JvmType.fromLegacyType(Type.getType(descriptor));
    }

    public static JvmType of(Class<?> type) {
        return JvmType.fromLegacyType(Type.getType(type));
    }

    public static JvmType of(Constructor<?> constructor) {
        return JvmType.fromLegacyType(Type.getType(constructor));
    }

    public static JvmType of(Method method) {
        return JvmType.fromLegacyType(Type.getType(method));
    }

    public static JvmType object(String internalName) {
        return JvmType.fromLegacyType(Type.getObjectType(internalName));
    }

    public static JvmType method(String descriptor) {
        return JvmType.fromLegacyType(Type.getMethodType(descriptor));
    }

    public static JvmType method(JvmType returnType, JvmType ... argumentTypes) {
        Type[] legacyArguments = new Type[argumentTypes.length];
        for (int index = 0; index < argumentTypes.length; ++index) {
            legacyArguments[index] = argumentTypes[index].legacyType;
        }
        return JvmType.fromLegacyType(Type.getMethodType(returnType.legacyType, legacyArguments));
    }

    public static JvmType fromLegacyType(Type type) {
        if (type == Type.VOID_TYPE) {
            return VOID_TYPE;
        }
        if (type == Type.BOOLEAN_TYPE) {
            return BOOLEAN_TYPE;
        }
        if (type == Type.CHAR_TYPE) {
            return CHAR_TYPE;
        }
        if (type == Type.BYTE_TYPE) {
            return BYTE_TYPE;
        }
        if (type == Type.SHORT_TYPE) {
            return SHORT_TYPE;
        }
        if (type == Type.INT_TYPE) {
            return INT_TYPE;
        }
        if (type == Type.FLOAT_TYPE) {
            return FLOAT_TYPE;
        }
        if (type == Type.LONG_TYPE) {
            return LONG_TYPE;
        }
        if (type == Type.DOUBLE_TYPE) {
            return DOUBLE_TYPE;
        }
        return new JvmType(type);
    }

    public Type toLegacyType() {
        return this.legacyType;
    }

    public JvmType elementType() {
        return JvmType.fromLegacyType(this.legacyType.getElementType());
    }

    public JvmType[] argumentTypes() {
        Type[] legacyArguments = this.legacyType.getArgumentTypes();
        JvmType[] arguments = new JvmType[legacyArguments.length];
        for (int index = 0; index < legacyArguments.length; ++index) {
            arguments[index] = JvmType.fromLegacyType(legacyArguments[index]);
        }
        return arguments;
    }

    public JvmType returnType() {
        return JvmType.fromLegacyType(this.legacyType.getReturnType());
    }

    public int sort() {
        return this.legacyType.getSort();
    }

    public int dimensions() {
        return this.legacyType.getDimensions();
    }

    public int size() {
        return this.legacyType.getSize();
    }

    public int argumentsAndReturnSizes() {
        return this.legacyType.getArgumentsAndReturnSizes();
    }

    public String className() {
        return this.legacyType.getClassName();
    }

    public String internalName() {
        return this.legacyType.getInternalName();
    }

    public String descriptor() {
        return this.legacyType.getDescriptor();
    }

    public static String descriptorOf(Class<?> type) {
        return Type.getDescriptor(type);
    }

    public static String descriptorOf(Constructor<?> constructor) {
        return Type.getConstructorDescriptor(constructor);
    }

    public static String descriptorOf(Method method) {
        return Type.getMethodDescriptor(method);
    }

    public static String methodDescriptor(JvmType returnType, JvmType ... argumentTypes) {
        Type[] legacyArguments = new Type[argumentTypes.length];
        for (int index = 0; index < argumentTypes.length; ++index) {
            legacyArguments[index] = argumentTypes[index].legacyType;
        }
        return Type.getMethodDescriptor(returnType.legacyType, legacyArguments);
    }

    public boolean equals(Object other) {
        return other instanceof JvmType && this.descriptor().equals(((JvmType)other).descriptor());
    }

    public int hashCode() {
        return this.descriptor().hashCode();
    }

    public String toString() {
        return this.descriptor();
    }
}

