/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.ClassVisitor;
import gg.vape.shaded.org.objectweb.asm.RecordComponentVisitor;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import gg.vape.shaded.org.objectweb.asm.tree.AnnotationNode;
import gg.vape.shaded.org.objectweb.asm.tree.TypeAnnotationNode;
import gg.vape.shaded.org.objectweb.asm.tree.UnsupportedClassVersionException;
import gg.vape.shaded.org.objectweb.asm.tree.Util;
import java.util.List;

public class RecordComponentNode
extends RecordComponentVisitor {
    public String name;
    public String descriptor;
    public String signature;
    public List<AnnotationNode> visibleAnnotations;
    public List<AnnotationNode> invisibleAnnotations;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    public List<Attribute> attrs;

    public RecordComponentNode(String name, String descriptor, String signature) {
        this(589824, name, descriptor, signature);
        if (this.getClass() != RecordComponentNode.class) {
            throw new IllegalStateException();
        }
    }

    public RecordComponentNode(int api, String name, String descriptor, String signature) {
        super(api);
        this.name = name;
        this.descriptor = descriptor;
        this.signature = signature;
    }

    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationNode annotation = new AnnotationNode(descriptor);
        if (visible) {
            this.visibleAnnotations = Util.add(this.visibleAnnotations, annotation);
        } else {
            this.invisibleAnnotations = Util.add(this.invisibleAnnotations, annotation);
        }
        return annotation;
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
        if (visible) {
            this.visibleTypeAnnotations = Util.add(this.visibleTypeAnnotations, typeAnnotation);
        } else {
            this.invisibleTypeAnnotations = Util.add(this.invisibleTypeAnnotations, typeAnnotation);
        }
        return typeAnnotation;
    }

    public void visitAttribute(Attribute attribute) {
        this.attrs = Util.add(this.attrs, attribute);
    }

    public void visitEnd() {
    }

    public void check(int api) {
        if (api < 524288) {
            throw new UnsupportedClassVersionException();
        }
    }

    public void accept(ClassVisitor classVisitor) {
        TypeAnnotationNode typeAnnotation;
        AnnotationNode annotation;
        int i;
        int n;
        RecordComponentVisitor recordComponentVisitor = classVisitor.visitRecordComponent(this.name, this.descriptor, this.signature);
        if (recordComponentVisitor == null) {
            return;
        }
        if (this.visibleAnnotations != null) {
            n = this.visibleAnnotations.size();
            for (i = 0; i < n; ++i) {
                annotation = this.visibleAnnotations.get(i);
                annotation.accept(recordComponentVisitor.visitAnnotation(annotation.desc, true));
            }
        }
        if (this.invisibleAnnotations != null) {
            n = this.invisibleAnnotations.size();
            for (i = 0; i < n; ++i) {
                annotation = this.invisibleAnnotations.get(i);
                annotation.accept(recordComponentVisitor.visitAnnotation(annotation.desc, false));
            }
        }
        if (this.visibleTypeAnnotations != null) {
            n = this.visibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                typeAnnotation = this.visibleTypeAnnotations.get(i);
                typeAnnotation.accept(recordComponentVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
            }
        }
        if (this.invisibleTypeAnnotations != null) {
            n = this.invisibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                typeAnnotation = this.invisibleTypeAnnotations.get(i);
                typeAnnotation.accept(recordComponentVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, false));
            }
        }
        if (this.attrs != null) {
            n = this.attrs.size();
            for (i = 0; i < n; ++i) {
                recordComponentVisitor.visitAttribute(this.attrs.get(i));
            }
        }
        recordComponentVisitor.visitEnd();
    }
}

