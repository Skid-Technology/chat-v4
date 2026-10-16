/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Handle;
import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import gg.vape.shaded.org.objectweb.asm.commons.AnnotationRemapper;
import gg.vape.shaded.org.objectweb.asm.commons.Remapper;

public class MethodRemapper
extends MethodVisitor {
    final protected Remapper remapper;

    public MethodRemapper(MethodVisitor methodVisitor, Remapper remapper) {
        this(589824, methodVisitor, remapper);
    }

    protected MethodRemapper(int api, MethodVisitor methodVisitor, Remapper remapper) {
        super(api, methodVisitor);
        this.remapper = remapper;
    }

    public AnnotationVisitor visitAnnotationDefault() {
        AnnotationVisitor annotationVisitor = super.visitAnnotationDefault();
        return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(null, annotationVisitor);
    }

    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(descriptor, annotationVisitor);
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(descriptor, annotationVisitor);
    }

    public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitParameterAnnotation(parameter, this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(descriptor, annotationVisitor);
    }

    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        super.visitFrame(type, numLocal, this.remapFrameTypes(numLocal, local), numStack, this.remapFrameTypes(numStack, stack));
    }

    private Object[] remapFrameTypes(int numTypes, Object[] frameTypes) {
        if (frameTypes == null) {
            return frameTypes;
        }
        Object[] remappedFrameTypes = null;
        for (int i = 0; i < numTypes; ++i) {
            if (!(frameTypes[i] instanceof String)) continue;
            if (remappedFrameTypes == null) {
                remappedFrameTypes = new Object[numTypes];
                System.arraycopy(frameTypes, 0, remappedFrameTypes, 0, numTypes);
            }
            remappedFrameTypes[i] = this.remapper.mapType((String)frameTypes[i]);
        }
        return remappedFrameTypes == null ? frameTypes : remappedFrameTypes;
    }

    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        super.visitFieldInsn(opcode, this.remapper.mapType(owner), this.remapper.mapFieldName(owner, name, descriptor), this.remapper.mapDesc(descriptor));
    }

    public void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
        if (this.api < 327680 && (opcodeAndSource & 0x100) == 0) {
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
            return;
        }
        super.visitMethodInsn(opcodeAndSource, this.remapper.mapType(owner), this.remapper.mapMethodName(owner, name, descriptor), this.remapper.mapMethodDesc(descriptor), isInterface);
    }

    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object ... bootstrapMethodArguments) {
        Object[] remappedBootstrapMethodArguments = new Object[bootstrapMethodArguments.length];
        for (int i = 0; i < bootstrapMethodArguments.length; ++i) {
            remappedBootstrapMethodArguments[i] = this.remapper.mapValue(bootstrapMethodArguments[i]);
        }
        super.visitInvokeDynamicInsn(this.remapper.mapInvokeDynamicMethodName(name, descriptor), this.remapper.mapMethodDesc(descriptor), (Handle)this.remapper.mapValue(bootstrapMethodHandle), remappedBootstrapMethodArguments);
    }

    public void visitTypeInsn(int opcode, String type) {
        super.visitTypeInsn(opcode, this.remapper.mapType(type));
    }

    public void visitLdcInsn(Object value) {
        super.visitLdcInsn(this.remapper.mapValue(value));
    }

    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        super.visitMultiANewArrayInsn(this.remapper.mapDesc(descriptor), numDimensions);
    }

    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitInsnAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(descriptor, annotationVisitor);
    }

    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        super.visitTryCatchBlock(start, end, handler, type == null ? null : this.remapper.mapType(type));
    }

    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitTryCatchAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(descriptor, annotationVisitor);
    }

    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
        super.visitLocalVariable(name, this.remapper.mapDesc(descriptor), this.remapper.mapSignature(signature, true), start, end, index);
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(descriptor, annotationVisitor);
    }

    @Deprecated
    protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor annotationVisitor) {
        return new AnnotationRemapper(this.api, null, annotationVisitor, this.remapper);
    }

    protected AnnotationVisitor createAnnotationRemapper(String descriptor, AnnotationVisitor annotationVisitor) {
        return new AnnotationRemapper(this.api, descriptor, annotationVisitor, this.remapper).orDeprecatedValue(this.createAnnotationRemapper(annotationVisitor));
    }
}

