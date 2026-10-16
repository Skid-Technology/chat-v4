package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Handle;
import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.TypePath;

public class MethodRemapper extends MethodVisitor {
   protected final Remapper remapper;

   public MethodRemapper(MethodVisitor methodVisitor, Remapper remapper) {
      this(589824, methodVisitor, remapper);
   }

   protected MethodRemapper(int api, MethodVisitor methodVisitor, Remapper remapper) {
      super(api, methodVisitor);
      this.remapper = remapper;
   }

   @Override
   public AnnotationVisitor visitAnnotationDefault() {
      AnnotationVisitor annotationVisitor = super.visitAnnotationDefault();
      return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(null, annotationVisitor);
   }

   @Override
   public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      AnnotationVisitor annotationVisitor = super.visitAnnotation(this.remapper.mapDesc(descriptor), visible);
      return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(descriptor, annotationVisitor);
   }

   @Override
   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
      return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(descriptor, annotationVisitor);
   }

   @Override
   public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
      AnnotationVisitor annotationVisitor = super.visitParameterAnnotation(parameter, this.remapper.mapDesc(descriptor), visible);
      return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(descriptor, annotationVisitor);
   }

   @Override
   public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
      super.visitFrame(type, numLocal, this.remapFrameTypes(numLocal, local), numStack, this.remapFrameTypes(numStack, stack));
   }

   private Object[] remapFrameTypes(int numTypes, Object[] frameTypes) {
      if (frameTypes == null) {
         return frameTypes;
      } else {
         Object[] remappedFrameTypes = null;

         for (int i = 0; i < numTypes; i++) {
            if (frameTypes[i] instanceof String) {
               if (remappedFrameTypes == null) {
                  remappedFrameTypes = new Object[numTypes];
                  System.arraycopy(frameTypes, 0, remappedFrameTypes, 0, numTypes);
               }

               remappedFrameTypes[i] = this.remapper.mapType((String)frameTypes[i]);
            }
         }

         return remappedFrameTypes == null ? frameTypes : remappedFrameTypes;
      }
   }

   @Override
   public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
      super.visitFieldInsn(opcode, this.remapper.mapType(owner), this.remapper.mapFieldName(owner, name, descriptor), this.remapper.mapDesc(descriptor));
   }

   @Override
   public void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
      if (this.api < 327680 && (opcodeAndSource & 256) == 0) {
         super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
      } else {
         super.visitMethodInsn(
            opcodeAndSource,
            this.remapper.mapType(owner),
            this.remapper.mapMethodName(owner, name, descriptor),
            this.remapper.mapMethodDesc(descriptor),
            isInterface
         );
      }
   }

   @Override
   public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      Object[] remappedBootstrapMethodArguments = new Object[bootstrapMethodArguments.length];

      for (int i = 0; i < bootstrapMethodArguments.length; i++) {
         remappedBootstrapMethodArguments[i] = this.remapper.mapValue(bootstrapMethodArguments[i]);
      }

      super.visitInvokeDynamicInsn(
         this.remapper.mapInvokeDynamicMethodName(name, descriptor),
         this.remapper.mapMethodDesc(descriptor),
         (Handle)this.remapper.mapValue(bootstrapMethodHandle),
         remappedBootstrapMethodArguments
      );
   }

   @Override
   public void visitTypeInsn(int opcode, String type) {
      super.visitTypeInsn(opcode, this.remapper.mapType(type));
   }

   @Override
   public void visitLdcInsn(Object value) {
      super.visitLdcInsn(this.remapper.mapValue(value));
   }

   @Override
   public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
      super.visitMultiANewArrayInsn(this.remapper.mapDesc(descriptor), numDimensions);
   }

   @Override
   public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      AnnotationVisitor annotationVisitor = super.visitInsnAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
      return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(descriptor, annotationVisitor);
   }

   @Override
   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
      super.visitTryCatchBlock(start, end, handler, type == null ? null : this.remapper.mapType(type));
   }

   @Override
   public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      AnnotationVisitor annotationVisitor = super.visitTryCatchAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
      return annotationVisitor == null ? annotationVisitor : this.createAnnotationRemapper(descriptor, annotationVisitor);
   }

   @Override
   public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
      super.visitLocalVariable(name, this.remapper.mapDesc(descriptor), this.remapper.mapSignature(signature, true), start, end, index);
   }

   @Override
   public AnnotationVisitor visitLocalVariableAnnotation(
      int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible
   ) {
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
