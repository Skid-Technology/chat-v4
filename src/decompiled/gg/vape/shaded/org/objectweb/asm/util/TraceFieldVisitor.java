/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.FieldVisitor;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import gg.vape.shaded.org.objectweb.asm.util.Printer;
import gg.vape.shaded.org.objectweb.asm.util.TraceAnnotationVisitor;

public final class TraceFieldVisitor
extends FieldVisitor {
    public final Printer p;

    public TraceFieldVisitor(Printer printer) {
        this(null, printer);
    }

    public TraceFieldVisitor(FieldVisitor fieldVisitor, Printer printer) {
        super(589824, fieldVisitor);
        this.p = printer;
    }

    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        Printer annotationPrinter = this.p.visitFieldAnnotation(descriptor, visible);
        return new TraceAnnotationVisitor(super.visitAnnotation(descriptor, visible), annotationPrinter);
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        Printer annotationPrinter = this.p.visitFieldTypeAnnotation(typeRef, typePath, descriptor, visible);
        return new TraceAnnotationVisitor(super.visitTypeAnnotation(typeRef, typePath, descriptor, visible), annotationPrinter);
    }

    public void visitAttribute(Attribute attribute) {
        this.p.visitFieldAttribute(attribute);
        super.visitAttribute(attribute);
    }

    public void visitEnd() {
        this.p.visitFieldEnd();
        super.visitEnd();
    }
}

