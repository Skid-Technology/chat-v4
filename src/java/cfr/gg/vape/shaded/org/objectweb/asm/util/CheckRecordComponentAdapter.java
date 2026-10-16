/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.RecordComponentVisitor;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import gg.vape.shaded.org.objectweb.asm.TypeReference;
import gg.vape.shaded.org.objectweb.asm.util.CheckAnnotationAdapter;
import gg.vape.shaded.org.objectweb.asm.util.CheckClassAdapter;
import gg.vape.shaded.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckRecordComponentAdapter
extends RecordComponentVisitor {
    private boolean visitEndCalled;

    public CheckRecordComponentAdapter(RecordComponentVisitor recordComponentVisitor) {
        this(589824, recordComponentVisitor);
        if (this.getClass() != CheckRecordComponentAdapter.class) {
            throw new IllegalStateException();
        }
    }

    protected CheckRecordComponentAdapter(int api, RecordComponentVisitor recordComponentVisitor) {
        super(api, recordComponentVisitor);
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
            throw new IllegalArgumentException(CheckRecordComponentAdapter.stringConcat$0(Integer.toHexString(sort)));
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

