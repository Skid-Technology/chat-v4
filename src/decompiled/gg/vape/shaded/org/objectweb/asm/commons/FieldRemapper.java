/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.FieldVisitor;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import gg.vape.shaded.org.objectweb.asm.commons.AnnotationRemapper;
import gg.vape.shaded.org.objectweb.asm.commons.Remapper;

public class FieldRemapper
extends FieldVisitor {
    protected final Remapper remapper;

    public FieldRemapper(FieldVisitor fieldVisitor, Remapper remapper) {
        this(589824, fieldVisitor, remapper);
    }

    protected FieldRemapper(int api, FieldVisitor fieldVisitor, Remapper remapper) {
        super(api, fieldVisitor);
        this.remapper = remapper;
    }

    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? null : this.createAnnotationRemapper(descriptor, annotationVisitor);
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? null : this.createAnnotationRemapper(descriptor, annotationVisitor);
    }

    @Deprecated
    protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor annotationVisitor) {
        return new AnnotationRemapper(this.api, null, annotationVisitor, this.remapper);
    }

    protected AnnotationVisitor createAnnotationRemapper(String descriptor, AnnotationVisitor annotationVisitor) {
        return new AnnotationRemapper(this.api, descriptor, annotationVisitor, this.remapper).orDeprecatedValue(this.createAnnotationRemapper(annotationVisitor));
    }
}

