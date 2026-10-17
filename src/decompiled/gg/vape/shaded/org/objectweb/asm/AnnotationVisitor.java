/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm;

import gg.vape.shaded.org.objectweb.asm.Constants;

public abstract class AnnotationVisitor {
    protected final int api;
    protected AnnotationVisitor av;

    protected AnnotationVisitor(int api) {
        this(api, null);
    }

    protected AnnotationVisitor(int api, AnnotationVisitor annotationVisitor) {
        if (api != 589824 && api != 524288 && api != 458752 && api != 393216 && api != 327680 && api != 262144 && api != 0x10A0000) {
            throw new IllegalArgumentException(AnnotationVisitor.stringConcat$0(api));
        }
        if (api == 0x10A0000) {
            Constants.checkAsmExperimental(this);
        }
        this.api = api;
        this.av = annotationVisitor;
    }

    private static /* synthetic */ String stringConcat$0(int n) {
        return "Unsupported api " + n;
    }

    public AnnotationVisitor getDelegate() {
        return this.av;
    }

    public void visit(String name, Object value) {
        if (this.av != null) {
            this.av.visit(name, value);
        }
    }

    public void visitEnum(String name, String descriptor, String value) {
        if (this.av != null) {
            this.av.visitEnum(name, descriptor, value);
        }
    }

    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
        if (this.av != null) {
            return this.av.visitAnnotation(name, descriptor);
        }
        return null;
    }

    public AnnotationVisitor visitArray(String name) {
        if (this.av != null) {
            return this.av.visitArray(name);
        }
        return null;
    }

    public void visitEnd() {
        if (this.av != null) {
            this.av.visitEnd();
        }
    }
}

