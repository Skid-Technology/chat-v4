package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.ClassWriter;
import gg.vape.shaded.org.objectweb.asm.ConstantDynamic;
import gg.vape.shaded.org.objectweb.asm.Handle;
import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.Opcodes;
import gg.vape.shaded.org.objectweb.asm.Type;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import gg.vape.shaded.org.objectweb.asm.TypeReference;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.Analyzer;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.AnalyzerException;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.BasicValue;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.BasicVerifier;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CheckMethodAdapter extends MethodVisitor {
   private static final CheckMethodAdapter.Method[] OPCODE_METHODS = new CheckMethodAdapter.Method[]{
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INT_INSN,
      CheckMethodAdapter.Method.VISIT_INT_INSN,
      null,
      null,
      null,
      CheckMethodAdapter.Method.VISIT_VAR_INSN,
      CheckMethodAdapter.Method.VISIT_VAR_INSN,
      CheckMethodAdapter.Method.VISIT_VAR_INSN,
      CheckMethodAdapter.Method.VISIT_VAR_INSN,
      CheckMethodAdapter.Method.VISIT_VAR_INSN,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_VAR_INSN,
      CheckMethodAdapter.Method.VISIT_VAR_INSN,
      CheckMethodAdapter.Method.VISIT_VAR_INSN,
      CheckMethodAdapter.Method.VISIT_VAR_INSN,
      CheckMethodAdapter.Method.VISIT_VAR_INSN,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      null,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_VAR_INSN,
      null,
      null,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_FIELD_INSN,
      CheckMethodAdapter.Method.VISIT_FIELD_INSN,
      CheckMethodAdapter.Method.VISIT_FIELD_INSN,
      CheckMethodAdapter.Method.VISIT_FIELD_INSN,
      CheckMethodAdapter.Method.VISIT_METHOD_INSN,
      CheckMethodAdapter.Method.VISIT_METHOD_INSN,
      CheckMethodAdapter.Method.VISIT_METHOD_INSN,
      CheckMethodAdapter.Method.VISIT_METHOD_INSN,
      null,
      CheckMethodAdapter.Method.VISIT_TYPE_INSN,
      CheckMethodAdapter.Method.VISIT_INT_INSN,
      CheckMethodAdapter.Method.VISIT_TYPE_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_TYPE_INSN,
      CheckMethodAdapter.Method.VISIT_TYPE_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      CheckMethodAdapter.Method.VISIT_INSN,
      null,
      null,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN,
      CheckMethodAdapter.Method.VISIT_JUMP_INSN
   };
   private static final String INVALID = "Invalid ";
   private static final String INVALID_DESCRIPTOR = "Invalid descriptor: ";
   private static final String INVALID_TYPE_REFERENCE = "Invalid type reference sort 0x";
   private static final String INVALID_LOCAL_VARIABLE_INDEX = "Invalid local variable index";
   private static final String MUST_NOT_BE_NULL_OR_EMPTY = " (must not be null or empty)";
   private static final String START_LABEL = "start label";
   private static final String END_LABEL = "end label";
   public int version;
   private int access;
   private int visibleAnnotableParameterCount;
   private int invisibleAnnotableParameterCount;
   private boolean visitCodeCalled;
   private boolean visitMaxCalled;
   private boolean visitEndCalled;
   private int insnCount;
   private final Map<Label, Integer> labelInsnIndices;
   private Set<Label> referencedLabels;
   private int lastFrameInsnIndex = -1;
   private int numExpandedFrames;
   private int numCompressedFrames;
   private List<Label> handlers;

   public CheckMethodAdapter(MethodVisitor methodvisitor) {
      this(methodvisitor, new HashMap<>());
   }

   public CheckMethodAdapter(MethodVisitor methodVisitor, Map<Label, Integer> labelInsnIndices) {
      this(589824, methodVisitor, labelInsnIndices);
      if (this.getClass() != CheckMethodAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckMethodAdapter(int api, MethodVisitor methodVisitor, Map<Label, Integer> labelInsnIndices) {
      super(api, methodVisitor);
      this.labelInsnIndices = labelInsnIndices;
      this.referencedLabels = new HashSet<>();
      this.handlers = new ArrayList<>();
   }

   public CheckMethodAdapter(int access, String name, String descriptor, MethodVisitor methodVisitor, Map<Label, Integer> labelInsnIndices) {
      this(589824, access, name, descriptor, methodVisitor, labelInsnIndices);
      if (this.getClass() != CheckMethodAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckMethodAdapter(int api, int access, String name, String descriptor, final MethodVisitor methodVisitor, Map<Label, Integer> labelInsnIndices) {
      this(
         api,
         new MethodNode(api, access, name, descriptor, null, null) {
            @Override
            public void visitEnd() {
               int originalMaxLocals = this.maxLocals;
               int originalMaxStack = this.maxStack;
               boolean checkMaxStackAndLocals = false;
               boolean checkFrames = false;
               if (methodVisitor instanceof CheckMethodAdapter.MethodWriterWrapper) {
                  CheckMethodAdapter.MethodWriterWrapper methodWriter = (CheckMethodAdapter.MethodWriterWrapper)methodVisitor;
                  checkMaxStackAndLocals = !methodWriter.computesMaxs();
                  checkFrames = methodWriter.requiresFrames() && !methodWriter.computesFrames();
               }

               Analyzer<BasicValue> analyzer = (Analyzer<BasicValue>)(checkFrames
                  ? new CheckFrameAnalyzer<>(new BasicVerifier())
                  : new Analyzer<>(new BasicVerifier()));

               try {
                  if (checkMaxStackAndLocals) {
                     analyzer.analyze("dummy", this);
                  } else {
                     analyzer.analyzeAndComputeMaxs("dummy", this);
                  }
               } catch (AnalyzerException | IndexOutOfBoundsException var7) {
                  this.throwError(analyzer, var7);
               }

               if (methodVisitor != null) {
                  this.maxLocals = originalMaxLocals;
                  this.maxStack = originalMaxStack;
                  this.accept(methodVisitor);
               }
            }

            private void throwError(Analyzer<BasicValue> analyzer, Exception e) {
               StringWriter stringWriter = new StringWriter();
               PrintWriter printWriter = new PrintWriter(stringWriter, true);
               CheckClassAdapter.printAnalyzerResult(this, analyzer, printWriter);
               printWriter.close();
               throw new IllegalArgumentException(stringConcat$0(e.getMessage(), stringWriter.toString()), e);
            }
         },
         labelInsnIndices
      );
      this.access = access;
   }

   @Override
   public void visitParameter(String name, int access) {
      if (name != null) {
         checkUnqualifiedName(this.version, name, "name");
      }

      CheckClassAdapter.checkAccess(access, 36880);
      super.visitParameter(name, access);
   }

   @Override
   public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      this.checkVisitEndNotCalled();
      checkDescriptor(this.version, descriptor, false);
      return new CheckAnnotationAdapter(super.visitAnnotation(descriptor, visible));
   }

   @Override
   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      this.checkVisitEndNotCalled();
      int sort = new TypeReference(typeRef).getSort();
      if (sort != 1 && sort != 18 && sort != 20 && sort != 21 && sort != 22 && sort != 23) {
         throw new IllegalArgumentException(stringConcat$0(Integer.toHexString(sort)));
      } else {
         CheckClassAdapter.checkTypeRef(typeRef);
         checkDescriptor(this.version, descriptor, false);
         return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, descriptor, visible));
      }
   }

   @Override
   public AnnotationVisitor visitAnnotationDefault() {
      this.checkVisitEndNotCalled();
      return new CheckAnnotationAdapter(super.visitAnnotationDefault(), false);
   }

   @Override
   public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
      this.checkVisitEndNotCalled();
      if (visible) {
         this.visibleAnnotableParameterCount = parameterCount;
      } else {
         this.invisibleAnnotableParameterCount = parameterCount;
      }

      super.visitAnnotableParameterCount(parameterCount, visible);
   }

   @Override
   public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
      this.checkVisitEndNotCalled();
      if ((!visible || this.visibleAnnotableParameterCount <= 0 || parameter < this.visibleAnnotableParameterCount)
         && (visible || this.invisibleAnnotableParameterCount <= 0 || parameter < this.invisibleAnnotableParameterCount)) {
         checkDescriptor(this.version, descriptor, false);
         return new CheckAnnotationAdapter(super.visitParameterAnnotation(parameter, descriptor, visible));
      } else {
         throw new IllegalArgumentException("Invalid parameter index");
      }
   }

   @Override
   public void visitAttribute(Attribute attribute) {
      this.checkVisitEndNotCalled();
      if (attribute == null) {
         throw new IllegalArgumentException("Invalid attribute (must not be null)");
      } else {
         super.visitAttribute(attribute);
      }
   }

   @Override
   public void visitCode() {
      if ((this.access & 1024) != 0) {
         throw new UnsupportedOperationException("Abstract methods cannot have code");
      } else {
         this.visitCodeCalled = true;
         super.visitCode();
      }
   }

   @Override
   public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
      if (this.insnCount == this.lastFrameInsnIndex) {
         throw new IllegalStateException("At most one frame can be visited at a given code location.");
      } else {
         this.lastFrameInsnIndex = this.insnCount;
         int maxNumLocal;
         int maxNumStack;
         switch (type) {
            case -1:
            case 0:
               maxNumLocal = Integer.MAX_VALUE;
               maxNumStack = Integer.MAX_VALUE;
               break;
            case 1:
            case 2:
               maxNumLocal = 3;
               maxNumStack = 0;
               break;
            case 3:
               maxNumLocal = 0;
               maxNumStack = 0;
               break;
            case 4:
               maxNumLocal = 0;
               maxNumStack = 1;
               break;
            default:
               throw new IllegalArgumentException(stringConcat$1(type));
         }

         if (numLocal > maxNumLocal) {
            throw new IllegalArgumentException(stringConcat$2(numLocal, type));
         } else if (numStack > maxNumStack) {
            throw new IllegalArgumentException(stringConcat$3(numStack, type));
         } else {
            if (type != 2) {
               if (numLocal > 0 && (local == null || local.length < numLocal)) {
                  throw new IllegalArgumentException("Array local[] is shorter than numLocal");
               }

               for (int i = 0; i < numLocal; i++) {
                  this.checkFrameValue(local[i]);
               }
            }

            if (numStack <= 0 || stack != null && stack.length >= numStack) {
               for (int i = 0; i < numStack; i++) {
                  this.checkFrameValue(stack[i]);
               }

               if (type == -1) {
                  this.numExpandedFrames++;
               } else {
                  this.numCompressedFrames++;
               }

               if (this.numExpandedFrames > 0 && this.numCompressedFrames > 0) {
                  throw new IllegalArgumentException("Expanded and compressed frames must not be mixed.");
               } else {
                  super.visitFrame(type, numLocal, local, numStack, stack);
               }
            } else {
               throw new IllegalArgumentException("Array stack[] is shorter than numStack");
            }
         }
      }
   }

   @Override
   public void visitInsn(int opcode) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      checkOpcodeMethod(opcode, CheckMethodAdapter.Method.VISIT_INSN);
      super.visitInsn(opcode);
      this.insnCount++;
   }

   @Override
   public void visitIntInsn(int opcode, int operand) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      checkOpcodeMethod(opcode, CheckMethodAdapter.Method.VISIT_INT_INSN);
      switch (opcode) {
         case 16:
            checkSignedByte(operand, "Invalid operand");
            break;
         case 17:
            checkSignedShort(operand, "Invalid operand");
            break;
         case 188:
            if (operand < 4 || operand > 11) {
               throw new IllegalArgumentException(stringConcat$4(operand));
            }
            break;
         default:
            throw new AssertionError();
      }

      super.visitIntInsn(opcode, operand);
      this.insnCount++;
   }

   @Override
   public void visitVarInsn(int opcode, int varIndex) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      checkOpcodeMethod(opcode, CheckMethodAdapter.Method.VISIT_VAR_INSN);
      checkUnsignedShort(varIndex, "Invalid local variable index");
      super.visitVarInsn(opcode, varIndex);
      this.insnCount++;
   }

   @Override
   public void visitTypeInsn(int opcode, String type) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      checkOpcodeMethod(opcode, CheckMethodAdapter.Method.VISIT_TYPE_INSN);
      checkInternalName(this.version, type, "type");
      if (opcode == 187 && type.charAt(0) == '[') {
         throw new IllegalArgumentException(stringConcat$5(type));
      } else {
         super.visitTypeInsn(opcode, type);
         this.insnCount++;
      }
   }

   @Override
   public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      checkOpcodeMethod(opcode, CheckMethodAdapter.Method.VISIT_FIELD_INSN);
      checkInternalName(this.version, owner, "owner");
      checkUnqualifiedName(this.version, name, "name");
      checkDescriptor(this.version, descriptor, false);
      super.visitFieldInsn(opcode, owner, name, descriptor);
      this.insnCount++;
   }

   @Override
   public void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
      if (this.api < 327680 && (opcodeAndSource & 256) == 0) {
         super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
      } else {
         int opcode = opcodeAndSource & -257;
         this.checkVisitCodeCalled();
         this.checkVisitMaxsNotCalled();
         checkOpcodeMethod(opcode, CheckMethodAdapter.Method.VISIT_METHOD_INSN);
         if (opcode != 183 || !"<init>".equals(name)) {
            checkMethodIdentifier(this.version, name, "name");
         }

         checkInternalName(this.version, owner, "owner");
         checkMethodDescriptor(this.version, descriptor);
         if (opcode == 182 && isInterface) {
            throw new IllegalArgumentException("INVOKEVIRTUAL can't be used with interfaces");
         } else if (opcode == 185 && !isInterface) {
            throw new IllegalArgumentException("INVOKEINTERFACE can't be used with classes");
         } else if (opcode == 183 && isInterface && (this.version & 65535) < 52) {
            throw new IllegalArgumentException("INVOKESPECIAL can't be used with interfaces prior to Java 8");
         } else {
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
            this.insnCount++;
         }
      }
   }

   @Override
   public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      checkMethodIdentifier(this.version, name, "name");
      checkMethodDescriptor(this.version, descriptor);
      if (bootstrapMethodHandle.getTag() != 6 && bootstrapMethodHandle.getTag() != 8) {
         throw new IllegalArgumentException(stringConcat$6(bootstrapMethodHandle.getTag()));
      } else {
         for (Object bootstrapMethodArgument : bootstrapMethodArguments) {
            this.checkLdcConstant(bootstrapMethodArgument);
         }

         super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
         this.insnCount++;
      }
   }

   @Override
   public void visitJumpInsn(int opcode, Label label) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      checkOpcodeMethod(opcode, CheckMethodAdapter.Method.VISIT_JUMP_INSN);
      this.checkLabel(label, false, "label");
      super.visitJumpInsn(opcode, label);
      this.insnCount++;
   }

   @Override
   public void visitLabel(Label label) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      this.checkLabel(label, false, "label");
      if (this.labelInsnIndices.get(label) != null) {
         throw new IllegalStateException("Already visited label");
      } else {
         this.labelInsnIndices.put(label, this.insnCount);
         super.visitLabel(label);
      }
   }

   @Override
   public void visitLdcInsn(Object value) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      this.checkLdcConstant(value);
      super.visitLdcInsn(value);
      this.insnCount++;
   }

   @Override
   public void visitIincInsn(int varIndex, int increment) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      checkUnsignedShort(varIndex, "Invalid local variable index");
      checkSignedShort(increment, "Invalid increment");
      super.visitIincInsn(varIndex, increment);
      this.insnCount++;
   }

   @Override
   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      if (max < min) {
         throw new IllegalArgumentException(stringConcat$7(max, min));
      } else {
         this.checkLabel(dflt, false, "default label");
         if (labels != null && labels.length == max - min + 1) {
            for (int i = 0; i < labels.length; i++) {
               this.checkLabel(labels[i], false, stringConcat$8(i));
            }

            super.visitTableSwitchInsn(min, max, dflt, labels);
            this.insnCount++;
         } else {
            throw new IllegalArgumentException("There must be max - min + 1 labels");
         }
      }
   }

   @Override
   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
      this.checkVisitMaxsNotCalled();
      this.checkVisitCodeCalled();
      this.checkLabel(dflt, false, "default label");
      if (keys != null && labels != null && keys.length == labels.length) {
         for (int i = 0; i < labels.length; i++) {
            this.checkLabel(labels[i], false, stringConcat$9(i));
         }

         super.visitLookupSwitchInsn(dflt, keys, labels);
         this.insnCount++;
      } else {
         throw new IllegalArgumentException("There must be the same number of keys and labels");
      }
   }

   @Override
   public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      checkDescriptor(this.version, descriptor, false);
      if (descriptor.charAt(0) != '[') {
         throw new IllegalArgumentException(stringConcat$10(descriptor));
      } else if (numDimensions < 1) {
         throw new IllegalArgumentException(stringConcat$11(numDimensions));
      } else if (numDimensions > descriptor.lastIndexOf(91) + 1) {
         throw new IllegalArgumentException(stringConcat$12(numDimensions));
      } else {
         super.visitMultiANewArrayInsn(descriptor, numDimensions);
         this.insnCount++;
      }
   }

   @Override
   public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      int sort = new TypeReference(typeRef).getSort();
      if (sort != 67 && sort != 68 && sort != 69 && sort != 70 && sort != 71 && sort != 72 && sort != 73 && sort != 74 && sort != 75) {
         throw new IllegalArgumentException(stringConcat$13(Integer.toHexString(sort)));
      } else {
         CheckClassAdapter.checkTypeRef(typeRef);
         checkDescriptor(this.version, descriptor, false);
         return new CheckAnnotationAdapter(super.visitInsnAnnotation(typeRef, typePath, descriptor, visible));
      }
   }

   @Override
   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      this.checkLabel(start, false, "start label");
      this.checkLabel(end, false, "end label");
      this.checkLabel(handler, false, "handler label");
      if (this.labelInsnIndices.get(start) == null && this.labelInsnIndices.get(end) == null && this.labelInsnIndices.get(handler) == null) {
         if (type != null) {
            checkInternalName(this.version, type, "type");
         }

         super.visitTryCatchBlock(start, end, handler, type);
         this.handlers.add(start);
         this.handlers.add(end);
      } else {
         throw new IllegalStateException("Try catch blocks must be visited before their labels");
      }
   }

   @Override
   public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      int sort = new TypeReference(typeRef).getSort();
      if (sort != 66) {
         throw new IllegalArgumentException(stringConcat$14(Integer.toHexString(sort)));
      } else {
         CheckClassAdapter.checkTypeRef(typeRef);
         checkDescriptor(this.version, descriptor, false);
         return new CheckAnnotationAdapter(super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible));
      }
   }

   @Override
   public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      checkUnqualifiedName(this.version, name, "name");
      checkDescriptor(this.version, descriptor, false);
      if (signature != null) {
         CheckClassAdapter.checkFieldSignature(signature);
      }

      this.checkLabel(start, true, "start label");
      this.checkLabel(end, true, "end label");
      checkUnsignedShort(index, "Invalid local variable index");
      int startInsnIndex = this.labelInsnIndices.get(start);
      int endInsnIndex = this.labelInsnIndices.get(end);
      if (endInsnIndex < startInsnIndex) {
         throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
      } else {
         super.visitLocalVariable(name, descriptor, signature, start, end, index);
      }
   }

   @Override
   public AnnotationVisitor visitLocalVariableAnnotation(
      int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible
   ) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      int sort = new TypeReference(typeRef).getSort();
      if (sort != 64 && sort != 65) {
         throw new IllegalArgumentException(stringConcat$15(Integer.toHexString(sort)));
      } else {
         CheckClassAdapter.checkTypeRef(typeRef);
         checkDescriptor(this.version, descriptor, false);
         if (start != null && end != null && index != null && end.length == start.length && index.length == start.length) {
            for (int i = 0; i < start.length; i++) {
               this.checkLabel(start[i], true, "start label");
               this.checkLabel(end[i], true, "end label");
               checkUnsignedShort(index[i], "Invalid local variable index");
               int startInsnIndex = this.labelInsnIndices.get(start[i]);
               int endInsnIndex = this.labelInsnIndices.get(end[i]);
               if (endInsnIndex < startInsnIndex) {
                  throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
               }
            }

            return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, descriptor, visible);
         } else {
            throw new IllegalArgumentException("Invalid start, end and index arrays (must be non null and of identical length");
         }
      }
   }

   @Override
   public void visitLineNumber(int line, Label start) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      checkUnsignedShort(line, "Invalid line number");
      this.checkLabel(start, true, "start label");
      super.visitLineNumber(line, start);
   }

   @Override
   public void visitMaxs(int maxStack, int maxLocals) {
      this.checkVisitCodeCalled();
      this.checkVisitMaxsNotCalled();
      this.visitMaxCalled = true;

      for (Label l : this.referencedLabels) {
         if (this.labelInsnIndices.get(l) == null) {
            throw new IllegalStateException("Undefined label used");
         }
      }

      for (int i = 0; i < this.handlers.size(); i += 2) {
         Integer startInsnIndex = this.labelInsnIndices.get(this.handlers.get(i));
         Integer endInsnIndex = this.labelInsnIndices.get(this.handlers.get(i + 1));
         if (endInsnIndex <= startInsnIndex) {
            throw new IllegalStateException("Empty try catch block handler range");
         }
      }

      checkUnsignedShort(maxStack, "Invalid max stack");
      checkUnsignedShort(maxLocals, "Invalid max locals");
      super.visitMaxs(maxStack, maxLocals);
   }

   @Override
   public void visitEnd() {
      this.checkVisitEndNotCalled();
      this.visitEndCalled = true;
      super.visitEnd();
   }

   private void checkVisitCodeCalled() {
      if (!this.visitCodeCalled) {
         throw new IllegalStateException("Cannot visit instructions before visitCode has been called.");
      }
   }

   private void checkVisitMaxsNotCalled() {
      if (this.visitMaxCalled) {
         throw new IllegalStateException("Cannot visit instructions after visitMaxs has been called.");
      }
   }

   private void checkVisitEndNotCalled() {
      if (this.visitEndCalled) {
         throw new IllegalStateException("Cannot visit elements after visitEnd has been called.");
      }
   }

   private void checkFrameValue(Object value) {
      if (value != Opcodes.TOP
         && value != Opcodes.INTEGER
         && value != Opcodes.FLOAT
         && value != Opcodes.LONG
         && value != Opcodes.DOUBLE
         && value != Opcodes.NULL
         && value != Opcodes.UNINITIALIZED_THIS) {
         if (value instanceof String) {
            checkInternalName(this.version, (String)value, "Invalid stack frame value");
         } else {
            if (!(value instanceof Label)) {
               throw new IllegalArgumentException(stringConcat$16(value));
            }

            this.checkLabel((Label)value, false, "label");
         }
      }
   }

   private static void checkOpcodeMethod(int opcode, CheckMethodAdapter.Method method) {
      if (opcode >= 0 && opcode <= 199) {
         if (OPCODE_METHODS[opcode] != method) {
            throw new IllegalArgumentException(stringConcat$18(opcode, method));
         }
      } else {
         throw new IllegalArgumentException(stringConcat$17(opcode));
      }
   }

   private static void checkSignedByte(int value, String message) {
      if (value < -128 || value > 127) {
         throw new IllegalArgumentException(stringConcat$19(message, value));
      }
   }

   private static void checkSignedShort(int value, String message) {
      if (value < -32768 || value > 32767) {
         throw new IllegalArgumentException(stringConcat$20(message, value));
      }
   }

   private static void checkUnsignedShort(int value, String message) {
      if (value < 0 || value > 65535) {
         throw new IllegalArgumentException(stringConcat$21(message, value));
      }
   }

   static void checkConstant(Object value) {
      if (!(value instanceof Integer) && !(value instanceof Float) && !(value instanceof Long) && !(value instanceof Double) && !(value instanceof String)) {
         throw new IllegalArgumentException(stringConcat$22(value));
      }
   }

   private void checkLdcConstant(Object value) {
      if (value instanceof Type) {
         int sort = ((Type)value).getSort();
         if (sort != 10 && sort != 9 && sort != 11) {
            throw new IllegalArgumentException("Illegal LDC constant value");
         }

         if (sort != 11 && (this.version & 65535) < 49) {
            throw new IllegalArgumentException("ldc of a constant class requires at least version 1.5");
         }

         if (sort == 11 && (this.version & 65535) < 51) {
            throw new IllegalArgumentException("ldc of a method type requires at least version 1.7");
         }
      } else if (value instanceof Handle) {
         if ((this.version & 65535) < 51) {
            throw new IllegalArgumentException("ldc of a Handle requires at least version 1.7");
         }

         Handle handle = (Handle)value;
         int tag = handle.getTag();
         if (tag < 1 || tag > 9) {
            throw new IllegalArgumentException(stringConcat$23(tag));
         }

         checkInternalName(this.version, handle.getOwner(), "handle owner");
         if (tag <= 4) {
            checkDescriptor(this.version, handle.getDesc(), false);
         } else {
            checkMethodDescriptor(this.version, handle.getDesc());
         }

         String handleName = handle.getName();
         if (!"<init>".equals(handleName) || tag != 8) {
            checkMethodIdentifier(this.version, handleName, "handle name");
         }
      } else if (value instanceof ConstantDynamic) {
         if ((this.version & 65535) < 55) {
            throw new IllegalArgumentException("ldc of a ConstantDynamic requires at least version 11");
         }

         ConstantDynamic constantDynamic = (ConstantDynamic)value;
         checkMethodIdentifier(this.version, constantDynamic.getName(), "constant dynamic name");
         checkDescriptor(this.version, constantDynamic.getDescriptor(), false);
         this.checkLdcConstant(constantDynamic.getBootstrapMethod());
         int bootstrapMethodArgumentCount = constantDynamic.getBootstrapMethodArgumentCount();

         for (int i = 0; i < bootstrapMethodArgumentCount; i++) {
            this.checkLdcConstant(constantDynamic.getBootstrapMethodArgument(i));
         }
      } else {
         checkConstant(value);
      }
   }

   static void checkUnqualifiedName(int version, String name, String message) {
      checkIdentifier(version, name, 0, -1, message);
   }

   static void checkIdentifier(int version, String name, int startPos, int endPos, String message) {
      if (name != null && (endPos == -1 ? name.length() > startPos : endPos > startPos)) {
         int max = endPos == -1 ? name.length() : endPos;
         if ((version & 65535) >= 49) {
            for (int i = startPos; i < max; i = name.offsetByCodePoints(i, 1)) {
               if (".;[/".indexOf(name.codePointAt(i)) != -1) {
                  throw new IllegalArgumentException(stringConcat$25(message, name));
               }
            }
         } else {
            for (int ix = startPos; ix < max; ix = name.offsetByCodePoints(ix, 1)) {
               if (ix == startPos ? !Character.isJavaIdentifierStart(name.codePointAt(ix)) : !Character.isJavaIdentifierPart(name.codePointAt(ix))) {
                  throw new IllegalArgumentException(stringConcat$26(message, name));
               }
            }
         }
      } else {
         throw new IllegalArgumentException(stringConcat$24(message));
      }
   }

   static void checkMethodIdentifier(int version, String name, String message) {
      if (name != null && name.length() != 0) {
         if ((version & 65535) >= 49) {
            for (int i = 0; i < name.length(); i = name.offsetByCodePoints(i, 1)) {
               if (".;[/<>".indexOf(name.codePointAt(i)) != -1) {
                  throw new IllegalArgumentException(stringConcat$28(message, name));
               }
            }
         } else {
            for (int ix = 0; ix < name.length(); ix = name.offsetByCodePoints(ix, 1)) {
               if (ix == 0 ? !Character.isJavaIdentifierStart(name.codePointAt(ix)) : !Character.isJavaIdentifierPart(name.codePointAt(ix))) {
                  throw new IllegalArgumentException(stringConcat$29(message, name));
               }
            }
         }
      } else {
         throw new IllegalArgumentException(stringConcat$27(message));
      }
   }

   static void checkInternalName(int version, String name, String message) {
      if (name != null && name.length() != 0) {
         if (name.charAt(0) == '[') {
            checkDescriptor(version, name, false);
         } else {
            checkInternalClassName(version, name, message);
         }
      } else {
         throw new IllegalArgumentException(stringConcat$30(message));
      }
   }

   private static void checkInternalClassName(int version, String name, String message) {
      try {
         int startIndex = 0;

         int slashIndex;
         while ((slashIndex = name.indexOf(47, startIndex + 1)) != -1) {
            checkIdentifier(version, name, startIndex, slashIndex, null);
            startIndex = slashIndex + 1;
         }

         checkIdentifier(version, name, startIndex, name.length(), null);
      } catch (IllegalArgumentException var5) {
         throw new IllegalArgumentException(stringConcat$31(message, name), var5);
      }
   }

   static void checkDescriptor(int version, String descriptor, boolean canBeVoid) {
      int endPos = checkDescriptor(version, descriptor, 0, canBeVoid);
      if (endPos != descriptor.length()) {
         throw new IllegalArgumentException(stringConcat$32(descriptor));
      }
   }

   private static int checkDescriptor(int version, String descriptor, int startPos, boolean canBeVoid) {
      if (descriptor != null && startPos < descriptor.length()) {
         switch (descriptor.charAt(startPos)) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z':
               return startPos + 1;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
               throw new IllegalArgumentException(stringConcat$37(descriptor));
            case 'L':
               int endPos = descriptor.indexOf(59, startPos);
               if (startPos != -1 && endPos - startPos >= 2) {
                  try {
                     checkInternalClassName(version, descriptor.substring(startPos + 1, endPos), null);
                  } catch (IllegalArgumentException var7) {
                     throw new IllegalArgumentException(stringConcat$36(descriptor), var7);
                  }

                  return endPos + 1;
               }

               throw new IllegalArgumentException(stringConcat$35(descriptor));
            case 'V':
               if (canBeVoid) {
                  return startPos + 1;
               }

               throw new IllegalArgumentException(stringConcat$33(descriptor));
            case '[':
               int pos = startPos + 1;

               while (pos < descriptor.length() && descriptor.charAt(pos) == '[') {
                  pos++;
               }

               if (pos < descriptor.length()) {
                  return checkDescriptor(version, descriptor, pos, false);
               } else {
                  throw new IllegalArgumentException(stringConcat$34(descriptor));
               }
         }
      } else {
         throw new IllegalArgumentException("Invalid type descriptor (must not be null or empty)");
      }
   }

   static void checkMethodDescriptor(int version, String descriptor) {
      if (descriptor != null && descriptor.length() != 0) {
         if (descriptor.charAt(0) == '(' && descriptor.length() >= 3) {
            int pos = 1;
            if (descriptor.charAt(pos) != ')') {
               do {
                  if (descriptor.charAt(pos) == 'V') {
                     throw new IllegalArgumentException(stringConcat$39(descriptor));
                  }

                  pos = checkDescriptor(version, descriptor, pos, false);
               } while (pos < descriptor.length() && descriptor.charAt(pos) != ')');
            }

            pos = checkDescriptor(version, descriptor, pos + 1, true);
            if (pos != descriptor.length()) {
               throw new IllegalArgumentException(stringConcat$40(descriptor));
            }
         } else {
            throw new IllegalArgumentException(stringConcat$38(descriptor));
         }
      } else {
         throw new IllegalArgumentException("Invalid method descriptor (must not be null or empty)");
      }
   }

   private void checkLabel(Label label, boolean checkVisited, String message) {
      if (label == null) {
         throw new IllegalArgumentException(stringConcat$41(message));
      } else if (checkVisited && this.labelInsnIndices.get(label) == null) {
         throw new IllegalArgumentException(stringConcat$42(message));
      } else {
         this.referencedLabels.add(label);
      }
   }

   private static enum Method {
      VISIT_INSN,
      VISIT_INT_INSN,
      VISIT_VAR_INSN,
      VISIT_TYPE_INSN,
      VISIT_FIELD_INSN,
      VISIT_METHOD_INSN,
      VISIT_JUMP_INSN;
   }

   static class MethodWriterWrapper extends MethodVisitor {
      private final int version;
      private final ClassWriter owner;

      MethodWriterWrapper(int api, int version, ClassWriter owner, MethodVisitor methodWriter) {
         super(api, methodWriter);
         this.version = version;
         this.owner = owner;
      }

      boolean computesMaxs() {
         return this.owner.hasFlags(1) || this.owner.hasFlags(2);
      }

      boolean computesFrames() {
         return this.owner.hasFlags(2);
      }

      boolean requiresFrames() {
         return (this.version & 65535) >= 51;
      }
   }
}
