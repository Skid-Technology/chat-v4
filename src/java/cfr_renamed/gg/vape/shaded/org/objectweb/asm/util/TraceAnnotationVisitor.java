/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.util.Printer;

public final class TraceAnnotationVisitor
extends AnnotationVisitor {
    private final Printer printer;

    public TraceAnnotationVisitor(Printer printer) {
        this(null, printer);
    }

    public TraceAnnotationVisitor(AnnotationVisitor annotationVisitor, Printer printer) {
        super(589824, annotationVisitor);
        this.printer = printer;
    }

    public void visit(String name, Object value) {
        this.printer.visit(name, value);
        super.visit(name, value);
    }

    public void visitEnum(String name, String descriptor, String value) {
        this.printer.visitEnum(name, descriptor, value);
        super.visitEnum(name, descriptor, value);
    }

    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
        Printer annotationPrinter = this.printer.visitAnnotation(name, descriptor);
        return new TraceAnnotationVisitor(super.visitAnnotation(name, descriptor), annotationPrinter);
    }

    public AnnotationVisitor visitArray(String name) {
        Printer arrayPrinter = this.printer.visitArray(name);
        return new TraceAnnotationVisitor(super.visitArray(name), arrayPrinter);
    }

    public void visitEnd() {
        this.printer.visitAnnotationEnd();
        super.visitEnd();
    }
}

