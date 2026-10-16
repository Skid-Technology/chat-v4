/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.FieldVisitor;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import gg.vape.shaded.org.objectweb.asm.TypeReference;
import gg.vape.shaded.org.objectweb.asm.util.CheckAnnotationAdapter;
import gg.vape.shaded.org.objectweb.asm.util.CheckClassAdapter;
import gg.vape.shaded.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckFieldAdapter
extends FieldVisitor {
    private boolean visitEndCalled;

    public CheckFieldAdapter(FieldVisitor fieldVisitor) {
        this(589824, fieldVisitor);
        if (this.getClass() != CheckFieldAdapter.class) {
            throw new IllegalStateException();
        }
    }

    protected CheckFieldAdapter(int api, FieldVisitor fieldVisitor) {
        super(api, fieldVisitor);
    }

    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        this.checkVisitEndNotCalled();
        CheckMethodAdapter.checkDescriptor(49, descriptor, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(descriptor, visible));
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        this.checkVisitEndNotCalled();
        int sort = new TypeReference(typeRef).getSort();
        if (sort != 19) {
            throw new IllegalArgumentException(CheckFieldAdapter.stringConcat$0(Integer.toHexString(sort)));
        }
        CheckClassAdapter.checkTypeRef(typeRef);
        CheckMethodAdapter.checkDescriptor(49, descriptor, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, descriptor, visible));
    }

    private static /* synthetic */ String stringConcat$0(String string) {
        return "Invalid type reference sort 0x" + string;
    }

    public void visitAttribute(Attribute attribute) {
        this.checkVisitEndNotCalled();
        if (attribute == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(attribute);
    }

    public void visitEnd() {
        this.checkVisitEndNotCalled();
        this.visitEndCalled = true;
        super.visitEnd();
    }

    private void checkVisitEndNotCalled() {
        if (this.visitEndCalled) {
            throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
        }
    }
}

