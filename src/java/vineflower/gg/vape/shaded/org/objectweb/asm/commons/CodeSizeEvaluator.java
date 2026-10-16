package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.ConstantDynamic;
import gg.vape.shaded.org.objectweb.asm.Handle;
import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.Opcodes;

public class CodeSizeEvaluator extends MethodVisitor implements Opcodes {
   private int minSize;
   private int maxSize;

   public CodeSizeEvaluator(MethodVisitor methodVisitor) {
      this(589824, methodVisitor);
   }

   protected CodeSizeEvaluator(int api, MethodVisitor methodVisitor) {
      super(api, methodVisitor);
   }

   public int getMinSize() {
      return this.minSize;
   }

   public int getMaxSize() {
      return this.maxSize;
   }

   @Override
   public void visitInsn(int opcode) {
      this.minSize++;
      this.maxSize++;
      super.visitInsn(opcode);
   }

   @Override
   public void visitIntInsn(int opcode, int operand) {
      if (opcode == 17) {
         this.minSize += 3;
         this.maxSize += 3;
      } else {
         this.minSize += 2;
         this.maxSize += 2;
      }

      super.visitIntInsn(opcode, operand);
   }

   @Override
   public void visitVarInsn(int opcode, int varIndex) {
      if (varIndex < 4 && opcode != 169) {
         this.minSize++;
         this.maxSize++;
      } else if (varIndex >= 256) {
         this.minSize += 4;
         this.maxSize += 4;
      } else {
         this.minSize += 2;
         this.maxSize += 2;
      }

      super.visitVarInsn(opcode, varIndex);
   }

   @Override
   public void visitTypeInsn(int opcode, String type) {
      this.minSize += 3;
      this.maxSize += 3;
      super.visitTypeInsn(opcode, type);
   }

   @Override
   public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
      this.minSize += 3;
      this.maxSize += 3;
      super.visitFieldInsn(opcode, owner, name, descriptor);
   }

   @Override
   public void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
      if (this.api < 327680 && (opcodeAndSource & 256) == 0) {
         super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
      } else {
         int opcode = opcodeAndSource & -257;
         if (opcode == 185) {
            this.minSize += 5;
            this.maxSize += 5;
         } else {
            this.minSize += 3;
            this.maxSize += 3;
         }

         super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
      }
   }

   @Override
   public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      this.minSize += 5;
      this.maxSize += 5;
      super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
   }

   @Override
   public void visitJumpInsn(int opcode, Label label) {
      this.minSize += 3;
      if (opcode != 167 && opcode != 168) {
         this.maxSize += 8;
      } else {
         this.maxSize += 5;
      }

      super.visitJumpInsn(opcode, label);
   }

   @Override
   public void visitLdcInsn(Object value) {
      if (!(value instanceof Long) && !(value instanceof Double) && (!(value instanceof ConstantDynamic) || ((ConstantDynamic)value).getSize() != 2)) {
         this.minSize += 2;
         this.maxSize += 3;
      } else {
         this.minSize += 3;
         this.maxSize += 3;
      }

      super.visitLdcInsn(value);
   }

   @Override
   public void visitIincInsn(int varIndex, int increment) {
      if (varIndex <= 255 && increment <= 127 && increment >= -128) {
         this.minSize += 3;
         this.maxSize += 3;
      } else {
         this.minSize += 6;
         this.maxSize += 6;
      }

      super.visitIincInsn(varIndex, increment);
   }

   @Override
   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
      this.minSize += 13 + labels.length * 4;
      this.maxSize += 16 + labels.length * 4;
      super.visitTableSwitchInsn(min, max, dflt, labels);
   }

   @Override
   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
      this.minSize += 9 + keys.length * 8;
      this.maxSize += 12 + keys.length * 8;
      super.visitLookupSwitchInsn(dflt, keys, labels);
   }

   @Override
   public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
      this.minSize += 4;
      this.maxSize += 4;
      super.visitMultiANewArrayInsn(descriptor, numDimensions);
   }
}
