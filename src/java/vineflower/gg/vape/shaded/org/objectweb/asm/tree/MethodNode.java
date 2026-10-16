package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.ClassVisitor;
import gg.vape.shaded.org.objectweb.asm.ConstantDynamic;
import gg.vape.shaded.org.objectweb.asm.Handle;
import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.Type;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import java.util.ArrayList;
import java.util.List;

public class MethodNode extends MethodVisitor {
   public int access;
   public String name;
   public String desc;
   public String signature;
   public List<String> exceptions;
   public List<ParameterNode> parameters;
   public List<AnnotationNode> visibleAnnotations;
   public List<AnnotationNode> invisibleAnnotations;
   public List<TypeAnnotationNode> visibleTypeAnnotations;
   public List<TypeAnnotationNode> invisibleTypeAnnotations;
   public List<Attribute> attrs;
   public Object annotationDefault;
   public int visibleAnnotableParameterCount;
   public List<AnnotationNode>[] visibleParameterAnnotations;
   public int invisibleAnnotableParameterCount;
   public List<AnnotationNode>[] invisibleParameterAnnotations;
   public InsnList instructions;
   public List<TryCatchBlockNode> tryCatchBlocks;
   public int maxStack;
   public int maxLocals;
   public List<LocalVariableNode> localVariables;
   public List<LocalVariableAnnotationNode> visibleLocalVariableAnnotations;
   public List<LocalVariableAnnotationNode> invisibleLocalVariableAnnotations;
   private boolean visited;

   public MethodNode() {
      this(589824);
      if (this.getClass() != MethodNode.class) {
         throw new IllegalStateException();
      }
   }

   public MethodNode(int api) {
      super(api);
      this.instructions = new InsnList();
   }

   public MethodNode(int access, String name, String descriptor, String signature, String[] exceptions) {
      this(589824, access, name, descriptor, signature, exceptions);
      if (this.getClass() != MethodNode.class) {
         throw new IllegalStateException();
      }
   }

   public MethodNode(int api, int access, String name, String descriptor, String signature, String[] exceptions) {
      super(api);
      this.access = access;
      this.name = name;
      this.desc = descriptor;
      this.signature = signature;
      this.exceptions = Util.asArrayList(exceptions);
      if ((access & 1024) == 0) {
         this.localVariables = new ArrayList<>(5);
      }

      this.tryCatchBlocks = new ArrayList<>();
      this.instructions = new InsnList();
   }

   @Override
   public void visitParameter(String name, int access) {
      if (this.parameters == null) {
         this.parameters = new ArrayList<>(5);
      }

      this.parameters.add(new ParameterNode(name, access));
   }

   @Override
   public AnnotationVisitor visitAnnotationDefault() {
      return new AnnotationNode(new ArrayList<Object>(0) {
         @Override
         public boolean add(Object o) {
            MethodNode.this.annotationDefault = o;
            return super.add(o);
         }
      });
   }

   @Override
   public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      AnnotationNode annotation = new AnnotationNode(descriptor);
      if (visible) {
         this.visibleAnnotations = Util.add(this.visibleAnnotations, annotation);
      } else {
         this.invisibleAnnotations = Util.add(this.invisibleAnnotations, annotation);
      }

      return annotation;
   }

   @Override
   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
      if (visible) {
         this.visibleTypeAnnotations = Util.add(this.visibleTypeAnnotations, typeAnnotation);
      } else {
         this.invisibleTypeAnnotations = Util.add(this.invisibleTypeAnnotations, typeAnnotation);
      }

      return typeAnnotation;
   }

   @Override
   public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
      if (visible) {
         this.visibleAnnotableParameterCount = parameterCount;
      } else {
         this.invisibleAnnotableParameterCount = parameterCount;
      }
   }

   @Override
   public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
      AnnotationNode annotation = new AnnotationNode(descriptor);
      if (visible) {
         if (this.visibleParameterAnnotations == null) {
            int params = Type.getArgumentCount(this.desc);
            this.visibleParameterAnnotations = new List[params];
         }

         this.visibleParameterAnnotations[parameter] = Util.add(this.visibleParameterAnnotations[parameter], annotation);
      } else {
         if (this.invisibleParameterAnnotations == null) {
            int params = Type.getArgumentCount(this.desc);
            this.invisibleParameterAnnotations = new List[params];
         }

         this.invisibleParameterAnnotations[parameter] = Util.add(this.invisibleParameterAnnotations[parameter], annotation);
      }

      return annotation;
   }

   @Override
   public void visitAttribute(Attribute attribute) {
      this.attrs = Util.add(this.attrs, attribute);
   }

   @Override
   public void visitCode() {
   }

   @Override
   public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
      this.instructions
         .add(new FrameNode(type, numLocal, local == null ? null : this.getLabelNodes(local), numStack, stack == null ? null : this.getLabelNodes(stack)));
   }

   @Override
   public void visitInsn(int opcode) {
      this.instructions.add(new InsnNode(opcode));
   }

   @Override
   public void visitIntInsn(int opcode, int operand) {
      this.instructions.add(new IntInsnNode(opcode, operand));
   }

   @Override
   public void visitVarInsn(int opcode, int varIndex) {
      this.instructions.add(new VarInsnNode(opcode, varIndex));
   }

   @Override
   public void visitTypeInsn(int opcode, String type) {
      this.instructions.add(new TypeInsnNode(opcode, type));
   }

   @Override
   public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
      this.instructions.add(new FieldInsnNode(opcode, owner, name, descriptor));
   }

   @Override
   public void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
      if (this.api < 327680 && (opcodeAndSource & 256) == 0) {
         super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
      } else {
         int opcode = opcodeAndSource & -257;
         this.instructions.add(new MethodInsnNode(opcode, owner, name, descriptor, isInterface));
      }
   }

   @Override
   public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      this.instructions.add(new InvokeDynamicInsnNode(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments));
   }

   @Override
   public void visitJumpInsn(int opcode, Label label) {
      this.instructions.add(new JumpInsnNode(opcode, this.getLabelNode(label)));
   }

   @Override
   public void visitLabel(Label label) {
      this.instructions.add(this.getLabelNode(label));
   }

   @Override
   public void visitLdcInsn(Object value) {
      this.instructions.add(new LdcInsnNode(value));
   }

   @Override
   public void visitIincInsn(int varIndex, int increment) {
      this.instructions.add(new IincInsnNode(varIndex, increment));
   }

   @Override
   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
      this.instructions.add(new TableSwitchInsnNode(min, max, this.getLabelNode(dflt), this.getLabelNodes(labels)));
   }

   @Override
   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
      this.instructions.add(new LookupSwitchInsnNode(this.getLabelNode(dflt), keys, this.getLabelNodes(labels)));
   }

   @Override
   public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
      this.instructions.add(new MultiANewArrayInsnNode(descriptor, numDimensions));
   }

   @Override
   public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      AbstractInsnNode currentInsn = this.instructions.getLast();

      while (currentInsn.getOpcode() == -1) {
         currentInsn = currentInsn.getPrevious();
      }

      TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
      if (visible) {
         currentInsn.visibleTypeAnnotations = Util.add(currentInsn.visibleTypeAnnotations, typeAnnotation);
      } else {
         currentInsn.invisibleTypeAnnotations = Util.add(currentInsn.invisibleTypeAnnotations, typeAnnotation);
      }

      return typeAnnotation;
   }

   @Override
   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
      TryCatchBlockNode tryCatchBlock = new TryCatchBlockNode(this.getLabelNode(start), this.getLabelNode(end), this.getLabelNode(handler), type);
      this.tryCatchBlocks = Util.add(this.tryCatchBlocks, tryCatchBlock);
   }

   @Override
   public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      TryCatchBlockNode tryCatchBlock = this.tryCatchBlocks.get((typeRef & 16776960) >> 8);
      TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
      if (visible) {
         tryCatchBlock.visibleTypeAnnotations = Util.add(tryCatchBlock.visibleTypeAnnotations, typeAnnotation);
      } else {
         tryCatchBlock.invisibleTypeAnnotations = Util.add(tryCatchBlock.invisibleTypeAnnotations, typeAnnotation);
      }

      return typeAnnotation;
   }

   @Override
   public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
      LocalVariableNode localVariable = new LocalVariableNode(name, descriptor, signature, this.getLabelNode(start), this.getLabelNode(end), index);
      this.localVariables = Util.add(this.localVariables, localVariable);
   }

   @Override
   public AnnotationVisitor visitLocalVariableAnnotation(
      int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible
   ) {
      LocalVariableAnnotationNode localVariableAnnotation = new LocalVariableAnnotationNode(
         typeRef, typePath, this.getLabelNodes(start), this.getLabelNodes(end), index, descriptor
      );
      if (visible) {
         this.visibleLocalVariableAnnotations = Util.add(this.visibleLocalVariableAnnotations, localVariableAnnotation);
      } else {
         this.invisibleLocalVariableAnnotations = Util.add(this.invisibleLocalVariableAnnotations, localVariableAnnotation);
      }

      return localVariableAnnotation;
   }

   @Override
   public void visitLineNumber(int line, Label start) {
      this.instructions.add(new LineNumberNode(line, this.getLabelNode(start)));
   }

   @Override
   public void visitMaxs(int maxStack, int maxLocals) {
      this.maxStack = maxStack;
      this.maxLocals = maxLocals;
   }

   @Override
   public void visitEnd() {
   }

   protected LabelNode getLabelNode(Label label) {
      if (!(label.info instanceof LabelNode)) {
         label.info = new LabelNode();
      }

      return (LabelNode)label.info;
   }

   private LabelNode[] getLabelNodes(Label[] labels) {
      LabelNode[] labelNodes = new LabelNode[labels.length];
      int i = 0;

      for (int n = labels.length; i < n; i++) {
         labelNodes[i] = this.getLabelNode(labels[i]);
      }

      return labelNodes;
   }

   private Object[] getLabelNodes(Object[] objects) {
      Object[] labelNodes = new Object[objects.length];
      int i = 0;

      for (int n = objects.length; i < n; i++) {
         Object o = objects[i];
         if (o instanceof Label) {
            o = this.getLabelNode((Label)o);
         }

         labelNodes[i] = o;
      }

      return labelNodes;
   }

   public void check(int api) {
      if (api == 262144) {
         if (this.parameters != null && !this.parameters.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }

         if (this.visibleTypeAnnotations != null && !this.visibleTypeAnnotations.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }

         if (this.invisibleTypeAnnotations != null && !this.invisibleTypeAnnotations.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }

         if (this.tryCatchBlocks != null) {
            for (int i = this.tryCatchBlocks.size() - 1; i >= 0; i--) {
               TryCatchBlockNode tryCatchBlock = this.tryCatchBlocks.get(i);
               if (tryCatchBlock.visibleTypeAnnotations != null && !tryCatchBlock.visibleTypeAnnotations.isEmpty()) {
                  throw new UnsupportedClassVersionException();
               }

               if (tryCatchBlock.invisibleTypeAnnotations != null && !tryCatchBlock.invisibleTypeAnnotations.isEmpty()) {
                  throw new UnsupportedClassVersionException();
               }
            }
         }

         for (int i = this.instructions.size() - 1; i >= 0; i--) {
            AbstractInsnNode insn = this.instructions.get(i);
            if (insn.visibleTypeAnnotations != null && !insn.visibleTypeAnnotations.isEmpty()) {
               throw new UnsupportedClassVersionException();
            }

            if (insn.invisibleTypeAnnotations != null && !insn.invisibleTypeAnnotations.isEmpty()) {
               throw new UnsupportedClassVersionException();
            }

            if (insn instanceof MethodInsnNode) {
               boolean isInterface = ((MethodInsnNode)insn).itf;
               if (isInterface != (insn.opcode == 185)) {
                  throw new UnsupportedClassVersionException();
               }
            } else if (insn instanceof LdcInsnNode) {
               Object value = ((LdcInsnNode)insn).cst;
               if (value instanceof Handle || value instanceof Type && ((Type)value).getSort() == 11) {
                  throw new UnsupportedClassVersionException();
               }
            }
         }

         if (this.visibleLocalVariableAnnotations != null && !this.visibleLocalVariableAnnotations.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }

         if (this.invisibleLocalVariableAnnotations != null && !this.invisibleLocalVariableAnnotations.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }
      }

      if (api < 458752) {
         for (int i = this.instructions.size() - 1; i >= 0; i--) {
            AbstractInsnNode insnx = this.instructions.get(i);
            if (insnx instanceof LdcInsnNode) {
               Object value = ((LdcInsnNode)insnx).cst;
               if (value instanceof ConstantDynamic) {
                  throw new UnsupportedClassVersionException();
               }
            }
         }
      }
   }

   public void accept(ClassVisitor classVisitor) {
      String[] exceptionsArray = this.exceptions == null ? null : this.exceptions.toArray(new String[0]);
      MethodVisitor methodVisitor = classVisitor.visitMethod(this.access, this.name, this.desc, this.signature, exceptionsArray);
      if (methodVisitor != null) {
         this.accept(methodVisitor);
      }
   }

   public void accept(MethodVisitor methodVisitor) {
      if (this.parameters != null) {
         int i = 0;

         for (int n = this.parameters.size(); i < n; i++) {
            this.parameters.get(i).accept(methodVisitor);
         }
      }

      if (this.annotationDefault != null) {
         AnnotationVisitor annotationVisitor = methodVisitor.visitAnnotationDefault();
         AnnotationNode.accept(annotationVisitor, null, this.annotationDefault);
         if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
         }
      }

      if (this.visibleAnnotations != null) {
         int i = 0;

         for (int n = this.visibleAnnotations.size(); i < n; i++) {
            AnnotationNode annotation = this.visibleAnnotations.get(i);
            annotation.accept(methodVisitor.visitAnnotation(annotation.desc, true));
         }
      }

      if (this.invisibleAnnotations != null) {
         int i = 0;

         for (int n = this.invisibleAnnotations.size(); i < n; i++) {
            AnnotationNode annotation = this.invisibleAnnotations.get(i);
            annotation.accept(methodVisitor.visitAnnotation(annotation.desc, false));
         }
      }

      if (this.visibleTypeAnnotations != null) {
         int i = 0;

         for (int n = this.visibleTypeAnnotations.size(); i < n; i++) {
            TypeAnnotationNode typeAnnotation = this.visibleTypeAnnotations.get(i);
            typeAnnotation.accept(methodVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         int i = 0;

         for (int n = this.invisibleTypeAnnotations.size(); i < n; i++) {
            TypeAnnotationNode typeAnnotation = this.invisibleTypeAnnotations.get(i);
            typeAnnotation.accept(methodVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, false));
         }
      }

      if (this.visibleAnnotableParameterCount > 0) {
         methodVisitor.visitAnnotableParameterCount(this.visibleAnnotableParameterCount, true);
      }

      if (this.visibleParameterAnnotations != null) {
         int i = 0;

         for (int n = this.visibleParameterAnnotations.length; i < n; i++) {
            List<AnnotationNode> parameterAnnotations = this.visibleParameterAnnotations[i];
            if (parameterAnnotations != null) {
               int j = 0;

               for (int m = parameterAnnotations.size(); j < m; j++) {
                  AnnotationNode annotation = parameterAnnotations.get(j);
                  annotation.accept(methodVisitor.visitParameterAnnotation(i, annotation.desc, true));
               }
            }
         }
      }

      if (this.invisibleAnnotableParameterCount > 0) {
         methodVisitor.visitAnnotableParameterCount(this.invisibleAnnotableParameterCount, false);
      }

      if (this.invisibleParameterAnnotations != null) {
         int i = 0;

         for (int nx = this.invisibleParameterAnnotations.length; i < nx; i++) {
            List<AnnotationNode> parameterAnnotations = this.invisibleParameterAnnotations[i];
            if (parameterAnnotations != null) {
               int j = 0;

               for (int m = parameterAnnotations.size(); j < m; j++) {
                  AnnotationNode annotation = parameterAnnotations.get(j);
                  annotation.accept(methodVisitor.visitParameterAnnotation(i, annotation.desc, false));
               }
            }
         }
      }

      if (this.visited) {
         this.instructions.resetLabels();
      }

      if (this.attrs != null) {
         int i = 0;

         for (int nxx = this.attrs.size(); i < nxx; i++) {
            methodVisitor.visitAttribute(this.attrs.get(i));
         }
      }

      if (this.instructions.size() > 0) {
         methodVisitor.visitCode();
         if (this.tryCatchBlocks != null) {
            int i = 0;

            for (int nxx = this.tryCatchBlocks.size(); i < nxx; i++) {
               this.tryCatchBlocks.get(i).updateIndex(i);
               this.tryCatchBlocks.get(i).accept(methodVisitor);
            }
         }

         this.instructions.accept(methodVisitor);
         if (this.localVariables != null) {
            int i = 0;

            for (int nxx = this.localVariables.size(); i < nxx; i++) {
               this.localVariables.get(i).accept(methodVisitor);
            }
         }

         if (this.visibleLocalVariableAnnotations != null) {
            int i = 0;

            for (int nxx = this.visibleLocalVariableAnnotations.size(); i < nxx; i++) {
               this.visibleLocalVariableAnnotations.get(i).accept(methodVisitor, true);
            }
         }

         if (this.invisibleLocalVariableAnnotations != null) {
            int i = 0;

            for (int nxx = this.invisibleLocalVariableAnnotations.size(); i < nxx; i++) {
               this.invisibleLocalVariableAnnotations.get(i).accept(methodVisitor, false);
            }
         }

         methodVisitor.visitMaxs(this.maxStack, this.maxLocals);
         this.visited = true;
      }

      methodVisitor.visitEnd();
   }
}
