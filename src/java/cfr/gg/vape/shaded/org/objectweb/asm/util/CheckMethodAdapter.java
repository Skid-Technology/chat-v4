/*
 * Decompiled with CFR 0.152.
 */
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
import gg.vape.shaded.org.objectweb.asm.util.CheckAnnotationAdapter;
import gg.vape.shaded.org.objectweb.asm.util.CheckClassAdapter;
import gg.vape.shaded.org.objectweb.asm.util.CheckFrameAnalyzer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CheckMethodAdapter
extends MethodVisitor {
    private static final Method[] OPCODE_METHODS = new Method[]{Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INT_INSN, Method.VISIT_INT_INSN, null, null, null, Method.VISIT_VAR_INSN, Method.VISIT_VAR_INSN, Method.VISIT_VAR_INSN, Method.VISIT_VAR_INSN, Method.VISIT_VAR_INSN, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_VAR_INSN, Method.VISIT_VAR_INSN, Method.VISIT_VAR_INSN, Method.VISIT_VAR_INSN, Method.VISIT_VAR_INSN, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, null, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN, Method.VISIT_VAR_INSN, null, null, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_FIELD_INSN, Method.VISIT_FIELD_INSN, Method.VISIT_FIELD_INSN, Method.VISIT_FIELD_INSN, Method.VISIT_METHOD_INSN, Method.VISIT_METHOD_INSN, Method.VISIT_METHOD_INSN, Method.VISIT_METHOD_INSN, null, Method.VISIT_TYPE_INSN, Method.VISIT_INT_INSN, Method.VISIT_TYPE_INSN, Method.VISIT_INSN, Method.VISIT_INSN, Method.VISIT_TYPE_INSN, Method.VISIT_TYPE_INSN, Method.VISIT_INSN, Method.VISIT_INSN, null, null, Method.VISIT_JUMP_INSN, Method.VISIT_JUMP_INSN};
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
        this(methodvisitor, new HashMap<Label, Integer>());
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
        this.referencedLabels = new HashSet<Label>();
        this.handlers = new ArrayList<Label>();
    }

    public CheckMethodAdapter(int access, String name, String descriptor, MethodVisitor methodVisitor, Map<Label, Integer> labelInsnIndices) {
        this(589824, access, name, descriptor, methodVisitor, labelInsnIndices);
        if (this.getClass() != CheckMethodAdapter.class) {
            throw new IllegalStateException();
        }
    }

    protected CheckMethodAdapter(int api, int access, String name, String descriptor, final MethodVisitor methodVisitor, Map<Label, Integer> labelInsnIndices) {
        this(api, new MethodNode(api, access, name, descriptor, null, null){

            @Override
            public void visitEnd() {
                int originalMaxLocals = this.maxLocals;
                int originalMaxStack = this.maxStack;
                boolean checkMaxStackAndLocals = false;
                boolean checkFrames = false;
                if (methodVisitor instanceof MethodWriterWrapper) {
                    MethodWriterWrapper methodWriter = (MethodWriterWrapper)methodVisitor;
                    checkMaxStackAndLocals = !methodWriter.computesMaxs();
                    checkFrames = methodWriter.requiresFrames() && !methodWriter.computesFrames();
                }
                Analyzer analyzer = checkFrames ? new CheckFrameAnalyzer<BasicValue>(new BasicVerifier()) : new Analyzer<BasicValue>(new BasicVerifier());
                try {
                    if (checkMaxStackAndLocals) {
                        analyzer.analyze("dummy", this);
                    } else {
                        analyzer.analyzeAndComputeMaxs("dummy", this);
                    }
                }
                catch (AnalyzerException | IndexOutOfBoundsException e) {
                    this.throwError(analyzer, e);
                }
                if (methodVisitor != null) {
                    this.maxLocals = originalMaxLocals;
                    this.maxStack = originalMaxStack;
                    this.accept(methodVisitor);
                }
            }

            private void throwError(Analyzer<BasicValue> analyzer, Exception e) {
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter((Writer)stringWriter, true);
                CheckClassAdapter.printAnalyzerResult(this, analyzer, printWriter);
                printWriter.close();
                throw new IllegalArgumentException(1.stringConcat$0(e.getMessage(), stringWriter.toString()), e);
            }

            private static /* synthetic */ String stringConcat$0(String string, String string2) {
                return string + " " + string2;
            }
        }, labelInsnIndices);
        this.access = access;
    }

    @Override
    public void visitParameter(String name, int access) {
        if (name != null) {
            CheckMethodAdapter.checkUnqualifiedName(this.version, name, "name");
        }
        CheckClassAdapter.checkAccess(access, 36880);
        super.visitParameter(name, access);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        this.checkVisitEndNotCalled();
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(descriptor, visible));
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        this.checkVisitEndNotCalled();
        int sort = new TypeReference(typeRef).getSort();
        if (sort != 1 && sort != 18 && sort != 20 && sort != 21 && sort != 22 && sort != 23) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$0(Integer.toHexString(sort)));
        }
        CheckClassAdapter.checkTypeRef(typeRef);
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, descriptor, visible));
    }

    private static /* synthetic */ String stringConcat$0(String string) {
        return INVALID_TYPE_REFERENCE + string;
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
        if (visible && this.visibleAnnotableParameterCount > 0 && parameter >= this.visibleAnnotableParameterCount || !visible && this.invisibleAnnotableParameterCount > 0 && parameter >= this.invisibleAnnotableParameterCount) {
            throw new IllegalArgumentException("Invalid parameter index");
        }
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        return new CheckAnnotationAdapter(super.visitParameterAnnotation(parameter, descriptor, visible));
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        this.checkVisitEndNotCalled();
        if (attribute == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(attribute);
    }

    @Override
    public void visitCode() {
        if ((this.access & 0x400) != 0) {
            throw new UnsupportedOperationException("Abstract methods cannot have code");
        }
        this.visitCodeCalled = true;
        super.visitCode();
    }

    @Override
    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        int i;
        int maxNumStack;
        int maxNumLocal;
        if (this.insnCount == this.lastFrameInsnIndex) {
            throw new IllegalStateException("At most one frame can be visited at a given code location.");
        }
        this.lastFrameInsnIndex = this.insnCount;
        switch (type) {
            case -1: 
            case 0: {
                maxNumLocal = Integer.MAX_VALUE;
                maxNumStack = Integer.MAX_VALUE;
                break;
            }
            case 3: {
                maxNumLocal = 0;
                maxNumStack = 0;
                break;
            }
            case 4: {
                maxNumLocal = 0;
                maxNumStack = 1;
                break;
            }
            case 1: 
            case 2: {
                maxNumLocal = 3;
                maxNumStack = 0;
                break;
            }
            default: {
                throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$1(type));
            }
        }
        if (numLocal > maxNumLocal) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$2(numLocal, type));
        }
        if (numStack > maxNumStack) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$3(numStack, type));
        }
        if (type != 2) {
            if (numLocal > 0 && (local == null || local.length < numLocal)) {
                throw new IllegalArgumentException("Array local[] is shorter than numLocal");
            }
            for (i = 0; i < numLocal; ++i) {
                this.checkFrameValue(local[i]);
            }
        }
        if (numStack > 0 && (stack == null || stack.length < numStack)) {
            throw new IllegalArgumentException("Array stack[] is shorter than numStack");
        }
        for (i = 0; i < numStack; ++i) {
            this.checkFrameValue(stack[i]);
        }
        if (type == -1) {
            ++this.numExpandedFrames;
        } else {
            ++this.numCompressedFrames;
        }
        if (this.numExpandedFrames > 0 && this.numCompressedFrames > 0) {
            throw new IllegalArgumentException("Expanded and compressed frames must not be mixed.");
        }
        super.visitFrame(type, numLocal, local, numStack, stack);
    }

    private static /* synthetic */ String stringConcat$1(int n) {
        return "Invalid frame type " + n;
    }

    private static /* synthetic */ String stringConcat$2(int n, int n2) {
        return "Invalid numLocal=" + n + " for frame type " + n2;
    }

    private static /* synthetic */ String stringConcat$3(int n, int n2) {
        return "Invalid numStack=" + n + " for frame type " + n2;
    }

    @Override
    public void visitInsn(int opcode) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        CheckMethodAdapter.checkOpcodeMethod(opcode, Method.VISIT_INSN);
        super.visitInsn(opcode);
        ++this.insnCount;
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        CheckMethodAdapter.checkOpcodeMethod(opcode, Method.VISIT_INT_INSN);
        switch (opcode) {
            case 16: {
                CheckMethodAdapter.checkSignedByte(operand, "Invalid operand");
                break;
            }
            case 17: {
                CheckMethodAdapter.checkSignedShort(operand, "Invalid operand");
                break;
            }
            case 188: {
                if (operand >= 4 && operand <= 11) break;
                throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$4(operand));
            }
            default: {
                throw new AssertionError();
            }
        }
        super.visitIntInsn(opcode, operand);
        ++this.insnCount;
    }

    private static /* synthetic */ String stringConcat$4(int n) {
        return "Invalid operand (must be an array type code T_...): " + n;
    }

    @Override
    public void visitVarInsn(int opcode, int varIndex) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        CheckMethodAdapter.checkOpcodeMethod(opcode, Method.VISIT_VAR_INSN);
        CheckMethodAdapter.checkUnsignedShort(varIndex, INVALID_LOCAL_VARIABLE_INDEX);
        super.visitVarInsn(opcode, varIndex);
        ++this.insnCount;
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        CheckMethodAdapter.checkOpcodeMethod(opcode, Method.VISIT_TYPE_INSN);
        CheckMethodAdapter.checkInternalName(this.version, type, "type");
        if (opcode == 187 && type.charAt(0) == '[') {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$5(type));
        }
        super.visitTypeInsn(opcode, type);
        ++this.insnCount;
    }

    private static /* synthetic */ String stringConcat$5(String string) {
        return "NEW cannot be used to create arrays: " + string;
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        CheckMethodAdapter.checkOpcodeMethod(opcode, Method.VISIT_FIELD_INSN);
        CheckMethodAdapter.checkInternalName(this.version, owner, "owner");
        CheckMethodAdapter.checkUnqualifiedName(this.version, name, "name");
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        super.visitFieldInsn(opcode, owner, name, descriptor);
        ++this.insnCount;
    }

    @Override
    public void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
        if (this.api < 327680 && (opcodeAndSource & 0x100) == 0) {
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
            return;
        }
        int opcode = opcodeAndSource & 0xFFFFFEFF;
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        CheckMethodAdapter.checkOpcodeMethod(opcode, Method.VISIT_METHOD_INSN);
        if (opcode != 183 || !"<init>".equals(name)) {
            CheckMethodAdapter.checkMethodIdentifier(this.version, name, "name");
        }
        CheckMethodAdapter.checkInternalName(this.version, owner, "owner");
        CheckMethodAdapter.checkMethodDescriptor(this.version, descriptor);
        if (opcode == 182 && isInterface) {
            throw new IllegalArgumentException("INVOKEVIRTUAL can't be used with interfaces");
        }
        if (opcode == 185 && !isInterface) {
            throw new IllegalArgumentException("INVOKEINTERFACE can't be used with classes");
        }
        if (opcode == 183 && isInterface && (this.version & 0xFFFF) < 52) {
            throw new IllegalArgumentException("INVOKESPECIAL can't be used with interfaces prior to Java 8");
        }
        super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
        ++this.insnCount;
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object ... bootstrapMethodArguments) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        CheckMethodAdapter.checkMethodIdentifier(this.version, name, "name");
        CheckMethodAdapter.checkMethodDescriptor(this.version, descriptor);
        if (bootstrapMethodHandle.getTag() != 6 && bootstrapMethodHandle.getTag() != 8) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$6(bootstrapMethodHandle.getTag()));
        }
        for (Object bootstrapMethodArgument : bootstrapMethodArguments) {
            this.checkLdcConstant(bootstrapMethodArgument);
        }
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
        ++this.insnCount;
    }

    private static /* synthetic */ String stringConcat$6(int n) {
        return "invalid handle tag " + n;
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        CheckMethodAdapter.checkOpcodeMethod(opcode, Method.VISIT_JUMP_INSN);
        this.checkLabel(label, false, "label");
        super.visitJumpInsn(opcode, label);
        ++this.insnCount;
    }

    @Override
    public void visitLabel(Label label) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        this.checkLabel(label, false, "label");
        if (this.labelInsnIndices.get(label) != null) {
            throw new IllegalStateException("Already visited label");
        }
        this.labelInsnIndices.put(label, this.insnCount);
        super.visitLabel(label);
    }

    @Override
    public void visitLdcInsn(Object value) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        this.checkLdcConstant(value);
        super.visitLdcInsn(value);
        ++this.insnCount;
    }

    @Override
    public void visitIincInsn(int varIndex, int increment) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        CheckMethodAdapter.checkUnsignedShort(varIndex, INVALID_LOCAL_VARIABLE_INDEX);
        CheckMethodAdapter.checkSignedShort(increment, "Invalid increment");
        super.visitIincInsn(varIndex, increment);
        ++this.insnCount;
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label ... labels) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        if (max < min) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$7(max, min));
        }
        this.checkLabel(dflt, false, "default label");
        if (labels == null || labels.length != max - min + 1) {
            throw new IllegalArgumentException("There must be max - min + 1 labels");
        }
        for (int i = 0; i < labels.length; ++i) {
            this.checkLabel(labels[i], false, CheckMethodAdapter.stringConcat$8(i));
        }
        super.visitTableSwitchInsn(min, max, dflt, labels);
        ++this.insnCount;
    }

    private static /* synthetic */ String stringConcat$7(int n, int n2) {
        return "Max = " + n + " must be greater than or equal to min = " + n2;
    }

    private static /* synthetic */ String stringConcat$8(int n) {
        return "label at index " + n;
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        this.checkVisitMaxsNotCalled();
        this.checkVisitCodeCalled();
        this.checkLabel(dflt, false, "default label");
        if (keys == null || labels == null || keys.length != labels.length) {
            throw new IllegalArgumentException("There must be the same number of keys and labels");
        }
        for (int i = 0; i < labels.length; ++i) {
            this.checkLabel(labels[i], false, CheckMethodAdapter.stringConcat$9(i));
        }
        super.visitLookupSwitchInsn(dflt, keys, labels);
        ++this.insnCount;
    }

    private static /* synthetic */ String stringConcat$9(int n) {
        return "label at index " + n;
    }

    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        if (descriptor.charAt(0) != '[') {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$10(descriptor));
        }
        if (numDimensions < 1) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$11(numDimensions));
        }
        if (numDimensions > descriptor.lastIndexOf(91) + 1) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$12(numDimensions));
        }
        super.visitMultiANewArrayInsn(descriptor, numDimensions);
        ++this.insnCount;
    }

    private static /* synthetic */ String stringConcat$10(String string) {
        return "Invalid descriptor (must be an array type descriptor): " + string;
    }

    private static /* synthetic */ String stringConcat$11(int n) {
        return "Invalid dimensions (must be greater than 0): " + n;
    }

    private static /* synthetic */ String stringConcat$12(int n) {
        return "Invalid dimensions (must not be greater than numDimensions(descriptor)): " + n;
    }

    @Override
    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        int sort = new TypeReference(typeRef).getSort();
        if (sort != 67 && sort != 68 && sort != 69 && sort != 70 && sort != 71 && sort != 72 && sort != 73 && sort != 74 && sort != 75) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$13(Integer.toHexString(sort)));
        }
        CheckClassAdapter.checkTypeRef(typeRef);
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        return new CheckAnnotationAdapter(super.visitInsnAnnotation(typeRef, typePath, descriptor, visible));
    }

    private static /* synthetic */ String stringConcat$13(String string) {
        return INVALID_TYPE_REFERENCE + string;
    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        this.checkLabel(start, false, START_LABEL);
        this.checkLabel(end, false, END_LABEL);
        this.checkLabel(handler, false, "handler label");
        if (this.labelInsnIndices.get(start) != null || this.labelInsnIndices.get(end) != null || this.labelInsnIndices.get(handler) != null) {
            throw new IllegalStateException("Try catch blocks must be visited before their labels");
        }
        if (type != null) {
            CheckMethodAdapter.checkInternalName(this.version, type, "type");
        }
        super.visitTryCatchBlock(start, end, handler, type);
        this.handlers.add(start);
        this.handlers.add(end);
    }

    @Override
    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        int sort = new TypeReference(typeRef).getSort();
        if (sort != 66) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$14(Integer.toHexString(sort)));
        }
        CheckClassAdapter.checkTypeRef(typeRef);
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        return new CheckAnnotationAdapter(super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible));
    }

    private static /* synthetic */ String stringConcat$14(String string) {
        return INVALID_TYPE_REFERENCE + string;
    }

    @Override
    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        CheckMethodAdapter.checkUnqualifiedName(this.version, name, "name");
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        if (signature != null) {
            CheckClassAdapter.checkFieldSignature(signature);
        }
        this.checkLabel(start, true, START_LABEL);
        this.checkLabel(end, true, END_LABEL);
        CheckMethodAdapter.checkUnsignedShort(index, INVALID_LOCAL_VARIABLE_INDEX);
        int startInsnIndex = this.labelInsnIndices.get(start);
        int endInsnIndex = this.labelInsnIndices.get(end);
        if (endInsnIndex < startInsnIndex) {
            throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
        }
        super.visitLocalVariable(name, descriptor, signature, start, end, index);
    }

    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        int sort = new TypeReference(typeRef).getSort();
        if (sort != 64 && sort != 65) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$15(Integer.toHexString(sort)));
        }
        CheckClassAdapter.checkTypeRef(typeRef);
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        if (start == null || end == null || index == null || end.length != start.length || index.length != start.length) {
            throw new IllegalArgumentException("Invalid start, end and index arrays (must be non null and of identical length");
        }
        for (int i = 0; i < start.length; ++i) {
            this.checkLabel(start[i], true, START_LABEL);
            this.checkLabel(end[i], true, END_LABEL);
            CheckMethodAdapter.checkUnsignedShort(index[i], INVALID_LOCAL_VARIABLE_INDEX);
            int startInsnIndex = this.labelInsnIndices.get(start[i]);
            int endInsnIndex = this.labelInsnIndices.get(end[i]);
            if (endInsnIndex >= startInsnIndex) continue;
            throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
        }
        return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, descriptor, visible);
    }

    private static /* synthetic */ String stringConcat$15(String string) {
        return INVALID_TYPE_REFERENCE + string;
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        CheckMethodAdapter.checkUnsignedShort(line, "Invalid line number");
        this.checkLabel(start, true, START_LABEL);
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        this.checkVisitCodeCalled();
        this.checkVisitMaxsNotCalled();
        this.visitMaxCalled = true;
        for (Label l : this.referencedLabels) {
            if (this.labelInsnIndices.get(l) != null) continue;
            throw new IllegalStateException("Undefined label used");
        }
        for (int i = 0; i < this.handlers.size(); i += 2) {
            Integer startInsnIndex = this.labelInsnIndices.get(this.handlers.get(i));
            Integer endInsnIndex = this.labelInsnIndices.get(this.handlers.get(i + 1));
            if (endInsnIndex > startInsnIndex) continue;
            throw new IllegalStateException("Empty try catch block handler range");
        }
        CheckMethodAdapter.checkUnsignedShort(maxStack, "Invalid max stack");
        CheckMethodAdapter.checkUnsignedShort(maxLocals, "Invalid max locals");
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
        if (value == Opcodes.TOP || value == Opcodes.INTEGER || value == Opcodes.FLOAT || value == Opcodes.LONG || value == Opcodes.DOUBLE || value == Opcodes.NULL || value == Opcodes.UNINITIALIZED_THIS) {
            return;
        }
        if (value instanceof String) {
            CheckMethodAdapter.checkInternalName(this.version, (String)value, "Invalid stack frame value");
        } else if (value instanceof Label) {
            this.checkLabel((Label)value, false, "label");
        } else {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$16(value));
        }
    }

    private static /* synthetic */ String stringConcat$16(Object object) {
        return "Invalid stack frame value: " + object;
    }

    private static void checkOpcodeMethod(int opcode, Method method) {
        if (opcode < 0 || opcode > 199) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$17(opcode));
        }
        if (OPCODE_METHODS[opcode] != method) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$18(opcode, method));
        }
    }

    private static /* synthetic */ String stringConcat$17(int n) {
        return "Invalid opcode: " + n;
    }

    private static /* synthetic */ String stringConcat$18(int n, Method method) {
        return "Invalid combination of opcode and method: " + n + ", " + (Object)((Object)method);
    }

    private static void checkSignedByte(int value, String message) {
        if (value < -128 || value > 127) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$19(message, value));
        }
    }

    private static /* synthetic */ String stringConcat$19(String string, int n) {
        return string + " (must be a signed byte): " + n;
    }

    private static void checkSignedShort(int value, String message) {
        if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$20(message, value));
        }
    }

    private static /* synthetic */ String stringConcat$20(String string, int n) {
        return string + " (must be a signed short): " + n;
    }

    private static void checkUnsignedShort(int value, String message) {
        if (value < 0 || value > 65535) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$21(message, value));
        }
    }

    private static /* synthetic */ String stringConcat$21(String string, int n) {
        return string + " (must be an unsigned short): " + n;
    }

    static void checkConstant(Object value) {
        if (!(value instanceof Integer || value instanceof Float || value instanceof Long || value instanceof Double || value instanceof String)) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$22(value));
        }
    }

    private static /* synthetic */ String stringConcat$22(Object object) {
        return "Invalid constant: " + object;
    }

    private void checkLdcConstant(Object value) {
        if (value instanceof Type) {
            int sort = ((Type)value).getSort();
            if (sort != 10 && sort != 9 && sort != 11) {
                throw new IllegalArgumentException("Illegal LDC constant value");
            }
            if (sort != 11 && (this.version & 0xFFFF) < 49) {
                throw new IllegalArgumentException("ldc of a constant class requires at least version 1.5");
            }
            if (sort == 11 && (this.version & 0xFFFF) < 51) {
                throw new IllegalArgumentException("ldc of a method type requires at least version 1.7");
            }
        } else if (value instanceof Handle) {
            if ((this.version & 0xFFFF) < 51) {
                throw new IllegalArgumentException("ldc of a Handle requires at least version 1.7");
            }
            Handle handle = (Handle)value;
            int tag = handle.getTag();
            if (tag < 1 || tag > 9) {
                throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$23(tag));
            }
            CheckMethodAdapter.checkInternalName(this.version, handle.getOwner(), "handle owner");
            if (tag <= 4) {
                CheckMethodAdapter.checkDescriptor(this.version, handle.getDesc(), false);
            } else {
                CheckMethodAdapter.checkMethodDescriptor(this.version, handle.getDesc());
            }
            String handleName = handle.getName();
            if (!"<init>".equals(handleName) || tag != 8) {
                CheckMethodAdapter.checkMethodIdentifier(this.version, handleName, "handle name");
            }
        } else if (value instanceof ConstantDynamic) {
            if ((this.version & 0xFFFF) < 55) {
                throw new IllegalArgumentException("ldc of a ConstantDynamic requires at least version 11");
            }
            ConstantDynamic constantDynamic = (ConstantDynamic)value;
            CheckMethodAdapter.checkMethodIdentifier(this.version, constantDynamic.getName(), "constant dynamic name");
            CheckMethodAdapter.checkDescriptor(this.version, constantDynamic.getDescriptor(), false);
            this.checkLdcConstant(constantDynamic.getBootstrapMethod());
            int bootstrapMethodArgumentCount = constantDynamic.getBootstrapMethodArgumentCount();
            for (int i = 0; i < bootstrapMethodArgumentCount; ++i) {
                this.checkLdcConstant(constantDynamic.getBootstrapMethodArgument(i));
            }
        } else {
            CheckMethodAdapter.checkConstant(value);
        }
    }

    private static /* synthetic */ String stringConcat$23(int n) {
        return "invalid handle tag " + n;
    }

    static void checkUnqualifiedName(int version, String name, String message) {
        CheckMethodAdapter.checkIdentifier(version, name, 0, -1, message);
    }

    static void checkIdentifier(int version, String name, int startPos, int endPos, String message) {
        int max;
        if (name == null || (endPos == -1 ? name.length() <= startPos : endPos <= startPos)) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$24(message));
        }
        int n = max = endPos == -1 ? name.length() : endPos;
        if ((version & 0xFFFF) >= 49) {
            int i = startPos;
            while (i < max) {
                if (".;[/".indexOf(name.codePointAt(i)) != -1) {
                    throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$25(message, name));
                }
                i = name.offsetByCodePoints(i, 1);
            }
            return;
        }
        int i = startPos;
        while (i < max) {
            if (i == startPos ? !Character.isJavaIdentifierStart(name.codePointAt(i)) : !Character.isJavaIdentifierPart(name.codePointAt(i))) {
                throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$26(message, name));
            }
            i = name.offsetByCodePoints(i, 1);
        }
    }

    private static /* synthetic */ String stringConcat$24(String string) {
        return INVALID + string + MUST_NOT_BE_NULL_OR_EMPTY;
    }

    private static /* synthetic */ String stringConcat$25(String string, String string2) {
        return INVALID + string + " (must not contain . ; [ or /): " + string2;
    }

    private static /* synthetic */ String stringConcat$26(String string, String string2) {
        return INVALID + string + " (must be a valid Java identifier): " + string2;
    }

    static void checkMethodIdentifier(int version, String name, String message) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$27(message));
        }
        if ((version & 0xFFFF) >= 49) {
            int i = 0;
            while (i < name.length()) {
                if (".;[/<>".indexOf(name.codePointAt(i)) != -1) {
                    throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$28(message, name));
                }
                i = name.offsetByCodePoints(i, 1);
            }
            return;
        }
        int i = 0;
        while (i < name.length()) {
            if (i == 0 ? !Character.isJavaIdentifierStart(name.codePointAt(i)) : !Character.isJavaIdentifierPart(name.codePointAt(i))) {
                throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$29(message, name));
            }
            i = name.offsetByCodePoints(i, 1);
        }
    }

    private static /* synthetic */ String stringConcat$27(String string) {
        return INVALID + string + MUST_NOT_BE_NULL_OR_EMPTY;
    }

    private static /* synthetic */ String stringConcat$28(String string, String string2) {
        return INVALID + string + " (must be a valid unqualified name): " + string2;
    }

    private static /* synthetic */ String stringConcat$29(String string, String string2) {
        return INVALID + string + " (must be a '<init>', '<clinit>' or a valid Java identifier): " + string2;
    }

    static void checkInternalName(int version, String name, String message) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$30(message));
        }
        if (name.charAt(0) == '[') {
            CheckMethodAdapter.checkDescriptor(version, name, false);
        } else {
            CheckMethodAdapter.checkInternalClassName(version, name, message);
        }
    }

    private static /* synthetic */ String stringConcat$30(String string) {
        return INVALID + string + MUST_NOT_BE_NULL_OR_EMPTY;
    }

    private static void checkInternalClassName(int version, String name, String message) {
        try {
            int slashIndex;
            int startIndex = 0;
            while ((slashIndex = name.indexOf(47, startIndex + 1)) != -1) {
                CheckMethodAdapter.checkIdentifier(version, name, startIndex, slashIndex, null);
                startIndex = slashIndex + 1;
            }
            CheckMethodAdapter.checkIdentifier(version, name, startIndex, name.length(), null);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$31(message, name), e);
        }
    }

    private static /* synthetic */ String stringConcat$31(String string, String string2) {
        return INVALID + string + " (must be an internal class name): " + string2;
    }

    static void checkDescriptor(int version, String descriptor, boolean canBeVoid) {
        int endPos = CheckMethodAdapter.checkDescriptor(version, descriptor, 0, canBeVoid);
        if (endPos != descriptor.length()) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$32(descriptor));
        }
    }

    private static /* synthetic */ String stringConcat$32(String string) {
        return INVALID_DESCRIPTOR + string;
    }

    private static int checkDescriptor(int version, String descriptor, int startPos, boolean canBeVoid) {
        if (descriptor == null || startPos >= descriptor.length()) {
            throw new IllegalArgumentException("Invalid type descriptor (must not be null or empty)");
        }
        switch (descriptor.charAt(startPos)) {
            case 'V': {
                if (canBeVoid) {
                    return startPos + 1;
                }
                throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$33(descriptor));
            }
            case 'B': 
            case 'C': 
            case 'D': 
            case 'F': 
            case 'I': 
            case 'J': 
            case 'S': 
            case 'Z': {
                return startPos + 1;
            }
            case '[': {
                int pos;
                for (pos = startPos + 1; pos < descriptor.length() && descriptor.charAt(pos) == '['; ++pos) {
                }
                if (pos < descriptor.length()) {
                    return CheckMethodAdapter.checkDescriptor(version, descriptor, pos, false);
                }
                throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$34(descriptor));
            }
            case 'L': {
                int endPos = descriptor.indexOf(59, startPos);
                if (startPos == -1 || endPos - startPos < 2) {
                    throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$35(descriptor));
                }
                try {
                    CheckMethodAdapter.checkInternalClassName(version, descriptor.substring(startPos + 1, endPos), null);
                }
                catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$36(descriptor), e);
                }
                return endPos + 1;
            }
        }
        throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$37(descriptor));
    }

    private static /* synthetic */ String stringConcat$33(String string) {
        return INVALID_DESCRIPTOR + string;
    }

    private static /* synthetic */ String stringConcat$34(String string) {
        return INVALID_DESCRIPTOR + string;
    }

    private static /* synthetic */ String stringConcat$35(String string) {
        return INVALID_DESCRIPTOR + string;
    }

    private static /* synthetic */ String stringConcat$36(String string) {
        return INVALID_DESCRIPTOR + string;
    }

    private static /* synthetic */ String stringConcat$37(String string) {
        return INVALID_DESCRIPTOR + string;
    }

    static void checkMethodDescriptor(int version, String descriptor) {
        if (descriptor == null || descriptor.length() == 0) {
            throw new IllegalArgumentException("Invalid method descriptor (must not be null or empty)");
        }
        if (descriptor.charAt(0) != '(' || descriptor.length() < 3) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$38(descriptor));
        }
        int pos = 1;
        if (descriptor.charAt(pos) != ')') {
            do {
                if (descriptor.charAt(pos) != 'V') continue;
                throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$39(descriptor));
            } while ((pos = CheckMethodAdapter.checkDescriptor(version, descriptor, pos, false)) < descriptor.length() && descriptor.charAt(pos) != ')');
        }
        if ((pos = CheckMethodAdapter.checkDescriptor(version, descriptor, pos + 1, true)) != descriptor.length()) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$40(descriptor));
        }
    }

    private static /* synthetic */ String stringConcat$38(String string) {
        return INVALID_DESCRIPTOR + string;
    }

    private static /* synthetic */ String stringConcat$39(String string) {
        return INVALID_DESCRIPTOR + string;
    }

    private static /* synthetic */ String stringConcat$40(String string) {
        return INVALID_DESCRIPTOR + string;
    }

    private void checkLabel(Label label, boolean checkVisited, String message) {
        if (label == null) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$41(message));
        }
        if (checkVisited && this.labelInsnIndices.get(label) == null) {
            throw new IllegalArgumentException(CheckMethodAdapter.stringConcat$42(message));
        }
        this.referencedLabels.add(label);
    }

    private static /* synthetic */ String stringConcat$41(String string) {
        return INVALID + string + " (must not be null)";
    }

    private static /* synthetic */ String stringConcat$42(String string) {
        return INVALID + string + " (must be visited first)";
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static enum Method {
        VISIT_INSN,
        VISIT_INT_INSN,
        VISIT_VAR_INSN,
        VISIT_TYPE_INSN,
        VISIT_FIELD_INSN,
        VISIT_METHOD_INSN,
        VISIT_JUMP_INSN;

    }

    static class MethodWriterWrapper
    extends MethodVisitor {
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
            return (this.version & 0xFFFF) >= 51;
        }
    }
}

