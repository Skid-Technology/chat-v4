/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.bytecode.annotation;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.Descriptor;
import gg.vape.shaded.javassist.bytecode.annotation.AnnotationsWriter;
import gg.vape.shaded.javassist.bytecode.annotation.MemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.MemberValueVisitor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class EnumMemberValue
extends MemberValue {
    int typeIndex;
    int valueIndex;

    public EnumMemberValue(int type, int value, ConstPool cp) {
        super('e', cp);
        this.typeIndex = type;
        this.valueIndex = value;
    }

    public EnumMemberValue(ConstPool cp) {
        super('e', cp);
        this.valueIndex = 0;
        this.typeIndex = 0;
    }

    @Override
    Object getValue(ClassLoader cl, ClassPool cp, Method m) throws ClassNotFoundException {
        try {
            return this.getType(cl).getField(this.getValue()).get(null);
        }
        catch (NoSuchFieldException e) {
            throw new ClassNotFoundException(this.getType() + "." + this.getValue());
        }
        catch (IllegalAccessException e) {
            throw new ClassNotFoundException(this.getType() + "." + this.getValue());
        }
    }

    @Override
    Class<?> getType(ClassLoader cl) throws ClassNotFoundException {
        return EnumMemberValue.loadClass(cl, this.getType());
    }

    @Override
    public void renameClass(String oldname, String newname) {
        String type = this.cp.getUtf8Info(this.typeIndex);
        String newType = Descriptor.rename(type, oldname, newname);
        this.setType(Descriptor.toClassName(newType));
    }

    @Override
    public void renameClass(Map<String, String> classnames) {
        String type = this.cp.getUtf8Info(this.typeIndex);
        String newType = Descriptor.rename(type, classnames);
        this.setType(Descriptor.toClassName(newType));
    }

    public String getType() {
        return Descriptor.toClassName(this.cp.getUtf8Info(this.typeIndex));
    }

    public void setType(String typename) {
        this.typeIndex = this.cp.addUtf8Info(Descriptor.of(typename));
    }

    public String getValue() {
        return this.cp.getUtf8Info(this.valueIndex);
    }

    public void setValue(String name) {
        this.valueIndex = this.cp.addUtf8Info(name);
    }

    public String toString() {
        return this.getType() + "." + this.getValue();
    }

    @Override
    public void write(AnnotationsWriter writer) throws IOException {
        writer.enumConstValue(this.cp.getUtf8Info(this.typeIndex), this.getValue());
    }

    @Override
    public void accept(MemberValueVisitor visitor) {
        visitor.visitEnumMemberValue(this);
    }
}

