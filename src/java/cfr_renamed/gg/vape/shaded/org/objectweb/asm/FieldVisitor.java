/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.Constants;
import gg.vape.shaded.org.objectweb.asm.TypePath;

public abstract class FieldVisitor {
    final protected int api;
    protected FieldVisitor fv;

    protected FieldVisitor(int api) {
        this(api, null);
    }

    protected FieldVisitor(int api, FieldVisitor fieldVisitor) {
        if (api != 589824 && api != 524288 && api != 458752 && api != 393216 && api != 327680 && api != 262144 && api != 0x10A0000) {
            throw new IllegalArgumentException(FieldVisitor.stringConcat$0(api));
        }
        if (api == 0x10A0000) {
            Constants.checkAsmExperimental(this);
        }
        this.api = api;
        this.fv = fieldVisitor;
    }

    private static String stringConcat$0(int n) {
        return "Unsupported api " + n;
    }

    public FieldVisitor getDelegate() {
        return this.fv;
    }

    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (this.fv != null) {
            return this.fv.visitAnnotation(descriptor, visible);
        }
        return null;
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        if (this.api < 327680) {
            throw new UnsupportedOperationException("This feature requires ASM5");
        }
        if (this.fv != null) {
            return this.fv.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
        }
        return null;
    }

    public void visitAttribute(Attribute attribute) {
        if (this.fv != null) {
            this.fv.visitAttribute(attribute);
        }
    }

    public void visitEnd() {
        if (this.fv != null) {
            this.fv.visitEnd();
        }
    }
}

