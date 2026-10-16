/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.bytecode.annotation;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.annotation.Annotation;
import gg.vape.shaded.javassist.bytecode.annotation.AnnotationImpl;
import gg.vape.shaded.javassist.bytecode.annotation.AnnotationsWriter;
import gg.vape.shaded.javassist.bytecode.annotation.MemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.MemberValueVisitor;
import java.io.IOException;
import java.lang.reflect.Method;

public class AnnotationMemberValue
extends MemberValue {
    Annotation value;

    public AnnotationMemberValue(ConstPool cp) {
        this(null, cp);
    }

    public AnnotationMemberValue(Annotation a, ConstPool cp) {
        super('@', cp);
        this.value = a;
    }

    @Override
    Object getValue(ClassLoader cl, ClassPool cp, Method m) throws ClassNotFoundException {
        return AnnotationImpl.make(cl, this.getType(cl), cp, this.value);
    }

    @Override
    Class<?> getType(ClassLoader cl) throws ClassNotFoundException {
        if (this.value == null) {
            throw new ClassNotFoundException("no type specified");
        }
        return AnnotationMemberValue.loadClass(cl, this.value.getTypeName());
    }

    public Annotation getValue() {
        return this.value;
    }

    public void setValue(Annotation newValue) {
        this.value = newValue;
    }

    public String toString() {
        return this.value.toString();
    }

    @Override
    public void write(AnnotationsWriter writer) throws IOException {
        writer.annotationValue();
        this.value.write(writer);
    }

    @Override
    public void accept(MemberValueVisitor visitor) {
        visitor.visitAnnotationMemberValue(this);
    }
}

