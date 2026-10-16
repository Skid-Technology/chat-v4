/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm;

import gg.vape.shaded.org.objectweb.asm.Type;

abstract class Symbol {
    final static int CONSTANT_CLASS_TAG = 7;
    final static int CONSTANT_FIELDREF_TAG = 9;
    final static int CONSTANT_METHODREF_TAG = 10;
    final static int CONSTANT_INTERFACE_METHODREF_TAG = 11;
    final static int CONSTANT_STRING_TAG = 8;
    final static int CONSTANT_INTEGER_TAG = 3;
    final static int CONSTANT_FLOAT_TAG = 4;
    final static int CONSTANT_LONG_TAG = 5;
    final static int CONSTANT_DOUBLE_TAG = 6;
    final static int CONSTANT_NAME_AND_TYPE_TAG = 12;
    final static int CONSTANT_UTF8_TAG = 1;
    final static int CONSTANT_METHOD_HANDLE_TAG = 15;
    final static int CONSTANT_METHOD_TYPE_TAG = 16;
    final static int CONSTANT_DYNAMIC_TAG = 17;
    final static int CONSTANT_INVOKE_DYNAMIC_TAG = 18;
    final static int CONSTANT_MODULE_TAG = 19;
    final static int CONSTANT_PACKAGE_TAG = 20;
    final static int BOOTSTRAP_METHOD_TAG = 64;
    final static int TYPE_TAG = 128;
    final static int UNINITIALIZED_TYPE_TAG = 129;
    final static int FORWARD_UNINITIALIZED_TYPE_TAG = 130;
    final static int MERGED_TYPE_TAG = 131;
    final int index;
    final int tag;
    final String owner;
    final String name;
    final String value;
    final long data;
    int info;

    Symbol(int index, int tag, String owner, String name, String value, long data) {
        this.index = index;
        this.tag = tag;
        this.owner = owner;
        this.name = name;
        this.value = value;
        this.data = data;
    }

    int getArgumentsAndReturnSizes() {
        if (this.info == 0) {
            this.info = Type.getArgumentsAndReturnSizes(this.value);
        }
        return this.info;
    }
}

