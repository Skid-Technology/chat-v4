/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.bytecode.annotation;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.annotation.AnnotationsWriter;
import gg.vape.shaded.javassist.bytecode.annotation.MemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.MemberValueVisitor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Map;

public class ArrayMemberValue
extends MemberValue {
    MemberValue type;
    MemberValue[] values;

    public ArrayMemberValue(ConstPool cp) {
        super('[', cp);
        this.type = null;
        this.values = null;
    }

    public ArrayMemberValue(MemberValue t, ConstPool cp) {
        super('[', cp);
        this.type = t;
        this.values = null;
    }

    @Override
    Object getValue(ClassLoader cl, ClassPool cp, Method method) throws ClassNotFoundException {
        Class<?> clazz;
        if (this.values == null) {
            throw new ClassNotFoundException("no array elements found: " + method.getName());
        }
        int size = this.values.length;
        if (this.type == null) {
            clazz = method.getReturnType().getComponentType();
            if (clazz == null || size > 0) {
                throw new ClassNotFoundException("broken array type: " + method.getName());
            }
        } else {
            clazz = this.type.getType(cl);
        }
        Object a = Array.newInstance(clazz, size);
        for (int i = 0; i < size; ++i) {
            Array.set(a, i, this.values[i].getValue(cl, cp, method));
        }
        return a;
    }

    @Override
    Class<?> getType(ClassLoader cl) throws ClassNotFoundException {
        if (this.type == null) {
            throw new ClassNotFoundException("no array type specified");
        }
        Object a = Array.newInstance(this.type.getType(cl), 0);
        return a.getClass();
    }

    @Override
    public void renameClass(String oldname, String newname) {
        if (this.type != null) {
            this.type.renameClass(oldname, newname);
        }
        if (this.values != null) {
            for (MemberValue value : this.values) {
                value.renameClass(oldname, newname);
            }
        }
    }

    @Override
    public void renameClass(Map<String, String> classnames) {
        if (this.type != null) {
            this.type.renameClass(classnames);
        }
        if (this.values != null) {
            for (MemberValue value : this.values) {
                value.renameClass(classnames);
            }
        }
    }

    public MemberValue getType() {
        return this.type;
    }

    public MemberValue[] getValue() {
        return this.values;
    }

    public void setValue(MemberValue[] elements) {
        this.values = elements;
        if (elements != null && elements.length > 0) {
            this.type = elements[0];
        }
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append('{');
        if (this.values != null) {
            for (int i = 0; i < this.values.length; ++i) {
                buf.append(this.values[i].toString());
                if (i + 1 >= this.values.length) continue;
                buf.append(", ");
            }
        }
        buf.append('}');
        return buf.toString();
    }

    @Override
    public void write(AnnotationsWriter writer) throws IOException {
        int num = this.values == null ? 0 : this.values.length;
        writer.arrayValue(num);
        for (int i = 0; i < num; ++i) {
            this.values[i].write(writer);
        }
    }

    @Override
    public void accept(MemberValueVisitor visitor) {
        visitor.visitArrayMemberValue(this);
    }
}

