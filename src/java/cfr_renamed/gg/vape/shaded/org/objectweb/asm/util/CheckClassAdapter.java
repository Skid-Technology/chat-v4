/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.ClassReader;
import gg.vape.shaded.org.objectweb.asm.ClassVisitor;
import gg.vape.shaded.org.objectweb.asm.ClassWriter;
import gg.vape.shaded.org.objectweb.asm.FieldVisitor;
import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.ModuleVisitor;
import gg.vape.shaded.org.objectweb.asm.RecordComponentVisitor;
import gg.vape.shaded.org.objectweb.asm.Type;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import gg.vape.shaded.org.objectweb.asm.TypeReference;
import gg.vape.shaded.org.objectweb.asm.tree.ClassNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;
import gg.vape.shaded.org.objectweb.asm.tree.TryCatchBlockNode;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.Analyzer;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.AnalyzerException;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.BasicValue;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.Frame;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.SimpleVerifier;
import gg.vape.shaded.org.objectweb.asm.util.CheckAnnotationAdapter;
import gg.vape.shaded.org.objectweb.asm.util.CheckFieldAdapter;
import gg.vape.shaded.org.objectweb.asm.util.CheckMethodAdapter;
import gg.vape.shaded.org.objectweb.asm.util.CheckModuleAdapter;
import gg.vape.shaded.org.objectweb.asm.util.CheckRecordComponentAdapter;
import gg.vape.shaded.org.objectweb.asm.util.Textifier;
import gg.vape.shaded.org.objectweb.asm.util.TraceMethodVisitor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CheckClassAdapter
extends ClassVisitor {
    private final static String USAGE = "Verifies the given class.\nUsage: CheckClassAdapter <fully qualified class name or class file name>";
    private final static String ERROR_AT = ": error at index ";
    private boolean checkDataFlow;
    private int version;
    private boolean visitCalled;
    private boolean visitModuleCalled;
    private boolean visitSourceCalled;
    private boolean visitOuterClassCalled;
    private boolean visitNestHostCalled;
    private String nestMemberPackageName;
    private boolean visitEndCalled;
    private Map<Label, Integer> labelInsnIndices = new HashMap<Label, Integer>();

    public CheckClassAdapter(ClassVisitor classVisitor) {
        this(classVisitor, true);
    }

    public CheckClassAdapter(ClassVisitor classVisitor, boolean checkDataFlow) {
        this(589824, classVisitor, checkDataFlow);
        if (this.getClass() != CheckClassAdapter.class) {
            throw new IllegalStateException();
        }
    }

    protected CheckClassAdapter(int api, ClassVisitor classVisitor, boolean checkDataFlow) {
        super(api, classVisitor);
        this.checkDataFlow = checkDataFlow;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (this.visitCalled) {
            throw new IllegalStateException("visit must be called only once");
        }
        this.visitCalled = true;
        this.checkState();
        CheckClassAdapter.checkAccess(access, 259633);
        if (name == null) {
            throw new IllegalArgumentException("Illegal class name (null)");
        }
        if (!name.endsWith("package-info") && !name.endsWith("module-info")) {
            CheckMethodAdapter.checkInternalName(version, name, "class name");
        }
        if ("java/lang/Object".equals(name)) {
            if (superName != null) {
                throw new IllegalArgumentException("The super class name of the Object class must be 'null'");
            }
        } else if (name.endsWith("module-info")) {
            if (superName != null) {
                throw new IllegalArgumentException("The super class name of a module-info class must be 'null'");
            }
        } else {
            CheckMethodAdapter.checkInternalName(version, superName, "super class name");
        }
        if (signature != null) {
            CheckClassAdapter.checkClassSignature(signature);
        }
        if ((access & 0x200) != 0 && !"java/lang/Object".equals(superName)) {
            throw new IllegalArgumentException("The super class name of interfaces must be 'java/lang/Object'");
        }
        if (interfaces != null) {
            for (int i = 0; i < interfaces.length; ++i) {
                CheckMethodAdapter.checkInternalName(version, interfaces[i], CheckClassAdapter.stringConcat$0(i));
            }
        }
        this.version = version;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    private static String stringConcat$0(int n) {
        return "interface name at index " + n;
    }

    @Override
    public void visitSource(String file, String debug) {
        this.checkState();
        if (this.visitSourceCalled) {
            throw new IllegalStateException("visitSource can be called only once.");
        }
        this.visitSourceCalled = true;
        super.visitSource(file, debug);
    }

    @Override
    public ModuleVisitor visitModule(String name, int access, String version) {
        this.checkState();
        if (this.visitModuleCalled) {
            throw new IllegalStateException("visitModule can be called only once.");
        }
        this.visitModuleCalled = true;
        CheckClassAdapter.checkFullyQualifiedName(this.version, name, "module name");
        CheckClassAdapter.checkAccess(access, 36896);
        CheckModuleAdapter checkModuleAdapter = new CheckModuleAdapter(this.api, super.visitModule(name, access, version), (access & 0x20) != 0);
        checkModuleAdapter.classVersion = this.version;
        return checkModuleAdapter;
    }

    @Override
    public void visitNestHost(String nestHost) {
        this.checkState();
        CheckMethodAdapter.checkInternalName(this.version, nestHost, "nestHost");
        if (this.visitNestHostCalled) {
            throw new IllegalStateException("visitNestHost can be called only once.");
        }
        if (this.nestMemberPackageName != null) {
            throw new IllegalStateException("visitNestHost and visitNestMember are mutually exclusive.");
        }
        this.visitNestHostCalled = true;
        super.visitNestHost(nestHost);
    }

    @Override
    public void visitNestMember(String nestMember) {
        this.checkState();
        CheckMethodAdapter.checkInternalName(this.version, nestMember, "nestMember");
        if (this.visitNestHostCalled) {
            throw new IllegalStateException("visitMemberOfNest and visitNestHost are mutually exclusive.");
        }
        String packageName = CheckClassAdapter.packageName(nestMember);
        if (this.nestMemberPackageName == null) {
            this.nestMemberPackageName = packageName;
        } else if (!this.nestMemberPackageName.equals(packageName)) {
            throw new IllegalStateException(CheckClassAdapter.stringConcat$1(nestMember, this.nestMemberPackageName));
        }
        super.visitNestMember(nestMember);
    }

    private static String stringConcat$1(String string, String string2) {
        return "nest member " + string + " should be in the package " + string2;
    }

    @Override
    public void visitPermittedSubclass(String permittedSubclass) {
        this.checkState();
        CheckMethodAdapter.checkInternalName(this.version, permittedSubclass, "permittedSubclass");
        super.visitPermittedSubclass(permittedSubclass);
    }

    @Override
    public void visitOuterClass(String owner, String name, String descriptor) {
        this.checkState();
        if (this.visitOuterClassCalled) {
            throw new IllegalStateException("visitOuterClass can be called only once.");
        }
        this.visitOuterClassCalled = true;
        if (owner == null) {
            throw new IllegalArgumentException("Illegal outer class owner");
        }
        if (descriptor != null) {
            CheckMethodAdapter.checkMethodDescriptor(this.version, descriptor);
        }
        super.visitOuterClass(owner, name, descriptor);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        this.checkState();
        CheckMethodAdapter.checkInternalName(this.version, name, "class name");
        if (outerName != null) {
            CheckMethodAdapter.checkInternalName(this.version, outerName, "outer class name");
        }
        if (innerName != null) {
            int startIndex;
            for (startIndex = 0; startIndex < innerName.length() && Character.isDigit(innerName.charAt(startIndex)); ++startIndex) {
            }
            if (startIndex == 0 || startIndex < innerName.length()) {
                CheckMethodAdapter.checkIdentifier(this.version, innerName, startIndex, -1, "inner class name");
            }
        }
        CheckClassAdapter.checkAccess(access, 30239);
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public RecordComponentVisitor visitRecordComponent(String name, String descriptor, String signature) {
        this.checkState();
        CheckMethodAdapter.checkUnqualifiedName(this.version, name, "record component name");
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        if (signature != null) {
            CheckClassAdapter.checkFieldSignature(signature);
        }
        return new CheckRecordComponentAdapter(this.api, super.visitRecordComponent(name, descriptor, signature));
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        this.checkState();
        CheckClassAdapter.checkAccess(access, 184543);
        CheckMethodAdapter.checkUnqualifiedName(this.version, name, "field name");
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        if (signature != null) {
            CheckClassAdapter.checkFieldSignature(signature);
        }
        if (value != null) {
            CheckMethodAdapter.checkConstant(value);
        }
        return new CheckFieldAdapter(this.api, super.visitField(access, name, descriptor, signature, value));
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        CheckMethodAdapter checkMethodAdapter;
        this.checkState();
        CheckClassAdapter.checkMethodAccess(this.version, access, 171519);
        if (!"<init>".equals(name) && !"<clinit>".equals(name)) {
            CheckMethodAdapter.checkMethodIdentifier(this.version, name, "method name");
        }
        CheckMethodAdapter.checkMethodDescriptor(this.version, descriptor);
        if (signature != null) {
            CheckClassAdapter.checkMethodSignature(signature);
        }
        if (exceptions != null) {
            for (int i = 0; i < exceptions.length; ++i) {
                CheckMethodAdapter.checkInternalName(this.version, exceptions[i], CheckClassAdapter.stringConcat$2(i));
            }
        }
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (this.checkDataFlow) {
            if (this.cv instanceof ClassWriter) {
                methodVisitor = new CheckMethodAdapter.MethodWriterWrapper(this.api, this.version, (ClassWriter)this.cv, methodVisitor);
            }
            checkMethodAdapter = new CheckMethodAdapter(this.api, access, name, descriptor, methodVisitor, this.labelInsnIndices);
        } else {
            checkMethodAdapter = new CheckMethodAdapter(this.api, methodVisitor, this.labelInsnIndices);
        }
        checkMethodAdapter.version = this.version;
        return checkMethodAdapter;
    }

    private static String stringConcat$2(int n) {
        return "exception name at index " + n;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        this.checkState();
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(descriptor, visible));
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        this.checkState();
        int sort = new TypeReference(typeRef).getSort();
        if (sort != 0 && sort != 17 && sort != 16) {
            throw new IllegalArgumentException(CheckClassAdapter.stringConcat$3(Integer.toHexString(sort)));
        }
        CheckClassAdapter.checkTypeRef(typeRef);
        CheckMethodAdapter.checkDescriptor(this.version, descriptor, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, descriptor, visible));
    }

    private static String stringConcat$3(String string) {
        return "Invalid type reference sort 0x" + string;
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        this.checkState();
        if (attribute == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(attribute);
    }

    @Override
    public void visitEnd() {
        this.checkState();
        this.visitEndCalled = true;
        super.visitEnd();
    }

    private void checkState() {
        if (!this.visitCalled) {
            throw new IllegalStateException("Cannot visit member before visit has been called.");
        }
        if (this.visitEndCalled) {
            throw new IllegalStateException("Cannot visit member after visitEnd has been called.");
        }
    }

    static void checkAccess(int access, int possibleAccess) {
        if ((access & ~possibleAccess) != 0) {
            throw new IllegalArgumentException(CheckClassAdapter.stringConcat$4(access));
        }
        int publicProtectedPrivate = 7;
        if (Integer.bitCount(access & publicProtectedPrivate) > 1) {
            throw new IllegalArgumentException(CheckClassAdapter.stringConcat$5(access));
        }
        if (Integer.bitCount(access & 0x410) > 1) {
            throw new IllegalArgumentException(CheckClassAdapter.stringConcat$6(access));
        }
    }

    private static String stringConcat$4(int n) {
        return "Invalid access flags: " + n;
    }

    private static String stringConcat$5(int n) {
        return "public, protected and private are mutually exclusive: " + n;
    }

    private static String stringConcat$6(int n) {
        return "final and abstract are mutually exclusive: " + n;
    }

    private static void checkMethodAccess(int version, int access, int possibleAccess) {
        CheckClassAdapter.checkAccess(access, possibleAccess);
        if ((version & 0xFFFF) < 61 && Integer.bitCount(access & 0xC00) > 1) {
            throw new IllegalArgumentException(CheckClassAdapter.stringConcat$7(access));
        }
    }

    private static String stringConcat$7(int n) {
        return "strictfp and abstract are mutually exclusive: " + n;
    }

    static void checkFullyQualifiedName(int version, String name, String source) {
        try {
            int dotIndex;
            int startIndex = 0;
            while ((dotIndex = name.indexOf(46, startIndex + 1)) != -1) {
                CheckMethodAdapter.checkIdentifier(version, name, startIndex, dotIndex, null);
                startIndex = dotIndex + 1;
            }
            CheckMethodAdapter.checkIdentifier(version, name, startIndex, name.length(), null);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(CheckClassAdapter.stringConcat$8(source, name), e);
        }
    }

    private static String stringConcat$8(String string, String string2) {
        return "Invalid " + string + " (must be a fully qualified name): " + string2;
    }

    public static void checkClassSignature(String signature) {
        int pos = 0;
        if (CheckClassAdapter.getChar(signature, 0) == '<') {
            pos = CheckClassAdapter.checkTypeParameters(signature, pos);
        }
        pos = CheckClassAdapter.checkClassTypeSignature(signature, pos);
        while (CheckClassAdapter.getChar(signature, pos) == 'L') {
            pos = CheckClassAdapter.checkClassTypeSignature(signature, pos);
        }
        if (pos != signature.length()) {
            throw new IllegalArgumentException(CheckClassAdapter.stringConcat$9(signature, pos));
        }
    }

    private static String stringConcat$9(String string, int n) {
        return string + ERROR_AT + n;
    }

    public static void checkMethodSignature(String signature) {
        int pos = 0;
        if (CheckClassAdapter.getChar(signature, 0) == '<') {
            pos = CheckClassAdapter.checkTypeParameters(signature, pos);
        }
        pos = CheckClassAdapter.checkChar('(', signature, pos);
        while ("ZCBSIFJDL[T".indexOf(CheckClassAdapter.getChar(signature, pos)) != -1) {
            pos = CheckClassAdapter.checkJavaTypeSignature(signature, pos);
        }
        pos = CheckClassAdapter.getChar(signature, pos = CheckClassAdapter.checkChar(')', signature, pos)) == 'V' ? ++pos : CheckClassAdapter.checkJavaTypeSignature(signature, pos);
        while (CheckClassAdapter.getChar(signature, pos) == '^') {
            if (CheckClassAdapter.getChar(signature, ++pos) == 'L') {
                pos = CheckClassAdapter.checkClassTypeSignature(signature, pos);
                continue;
            }
            pos = CheckClassAdapter.checkTypeVariableSignature(signature, pos);
        }
        if (pos != signature.length()) {
            throw new IllegalArgumentException(CheckClassAdapter.stringConcat$10(signature, pos));
        }
    }

    private static String stringConcat$10(String string, int n) {
        return string + ERROR_AT + n;
    }

    public static void checkFieldSignature(String signature) {
        int pos = CheckClassAdapter.checkReferenceTypeSignature(signature, 0);
        if (pos != signature.length()) {
            throw new IllegalArgumentException(CheckClassAdapter.stringConcat$11(signature, pos));
        }
    }

    private static String stringConcat$11(String string, int n) {
        return string + ERROR_AT + n;
    }

    private static int checkTypeParameters(String signature, int startPos) {
        int pos = startPos;
        pos = CheckClassAdapter.checkChar('<', signature, pos);
        pos = CheckClassAdapter.checkTypeParameter(signature, pos);
        while (CheckClassAdapter.getChar(signature, pos) != '>') {
            pos = CheckClassAdapter.checkTypeParameter(signature, pos);
        }
        return pos + 1;
    }

    private static int checkTypeParameter(String signature, int startPos) {
        int pos = startPos;
        pos = CheckClassAdapter.checkSignatureIdentifier(signature, pos);
        if ("L[T".indexOf(CheckClassAdapter.getChar(signature, pos = CheckClassAdapter.checkChar(':', signature, pos))) != -1) {
            pos = CheckClassAdapter.checkReferenceTypeSignature(signature, pos);
        }
        while (CheckClassAdapter.getChar(signature, pos) == ':') {
            pos = CheckClassAdapter.checkReferenceTypeSignature(signature, pos + 1);
        }
        return pos;
    }

    private static int checkReferenceTypeSignature(String signature, int pos) {
        switch (CheckClassAdapter.getChar(signature, pos)) {
            case 'L': {
                return CheckClassAdapter.checkClassTypeSignature(signature, pos);
            }
            case '[': {
                return CheckClassAdapter.checkJavaTypeSignature(signature, pos + 1);
            }
        }
        return CheckClassAdapter.checkTypeVariableSignature(signature, pos);
    }

    private static int checkClassTypeSignature(String signature, int startPos) {
        int pos = startPos;
        pos = CheckClassAdapter.checkChar('L', signature, pos);
        pos = CheckClassAdapter.checkSignatureIdentifier(signature, pos);
        while (CheckClassAdapter.getChar(signature, pos) == '/') {
            pos = CheckClassAdapter.checkSignatureIdentifier(signature, pos + 1);
        }
        if (CheckClassAdapter.getChar(signature, pos) == '<') {
            pos = CheckClassAdapter.checkTypeArguments(signature, pos);
        }
        while (CheckClassAdapter.getChar(signature, pos) == '.') {
            if (CheckClassAdapter.getChar(signature, pos = CheckClassAdapter.checkSignatureIdentifier(signature, pos + 1)) != '<') continue;
            pos = CheckClassAdapter.checkTypeArguments(signature, pos);
        }
        return CheckClassAdapter.checkChar(';', signature, pos);
    }

    private static int checkTypeArguments(String signature, int startPos) {
        int pos = startPos;
        pos = CheckClassAdapter.checkChar('<', signature, pos);
        pos = CheckClassAdapter.checkTypeArgument(signature, pos);
        while (CheckClassAdapter.getChar(signature, pos) != '>') {
            pos = CheckClassAdapter.checkTypeArgument(signature, pos);
        }
        return pos + 1;
    }

    private static int checkTypeArgument(String signature, int startPos) {
        int pos = startPos;
        char c = CheckClassAdapter.getChar(signature, pos);
        if (c == '*') {
            return pos + 1;
        }
        if (c == '+' || c == '-') {
            ++pos;
        }
        return CheckClassAdapter.checkReferenceTypeSignature(signature, pos);
    }

    private static int checkTypeVariableSignature(String signature, int startPos) {
        int pos = startPos;
        pos = CheckClassAdapter.checkChar('T', signature, pos);
        pos = CheckClassAdapter.checkSignatureIdentifier(signature, pos);
        return CheckClassAdapter.checkChar(';', signature, pos);
    }

    private static int checkJavaTypeSignature(String signature, int startPos) {
        int pos = startPos;
        switch (CheckClassAdapter.getChar(signature, pos)) {
            case 'B': 
            case 'C': 
            case 'D': 
            case 'F': 
            case 'I': 
            case 'J': 
            case 'S': 
            case 'Z': {
                return pos + 1;
            }
        }
        return CheckClassAdapter.checkReferenceTypeSignature(signature, pos);
    }

    private static int checkSignatureIdentifier(String signature, int startPos) {
        int pos = startPos;
        while (pos < signature.length() && ".;[/<>:".indexOf(signature.codePointAt(pos)) == -1) {
            pos = signature.offsetByCodePoints(pos, 1);
        }
        if (pos == startPos) {
            throw new IllegalArgumentException(CheckClassAdapter.stringConcat$12(signature, startPos));
        }
        return pos;
    }

    private static String stringConcat$12(String string, int n) {
        return string + ": identifier expected at index " + n;
    }

    private static int checkChar(char c, String signature, int pos) {
        if (CheckClassAdapter.getChar(signature, pos) == c) {
            return pos + 1;
        }
        throw new IllegalArgumentException(CheckClassAdapter.stringConcat$13(signature, c, pos));
    }

    private static String stringConcat$13(String string, char c, int n) {
        return string + ": '" + c + "' expected at index " + n;
    }

    private static char getChar(String string, int pos) {
        return pos < string.length() ? string.charAt(pos) : (char)'\u0000';
    }

    static void checkTypeRef(int typeRef) {
        int mask = 0;
        switch (typeRef >>> 24) {
            case 0: 
            case 1: 
            case 22: {
                mask = -65536;
                break;
            }
            case 19: 
            case 20: 
            case 21: 
            case 64: 
            case 65: 
            case 67: 
            case 68: 
            case 69: 
            case 70: {
                mask = -16777216;
                break;
            }
            case 16: 
            case 17: 
            case 18: 
            case 23: 
            case 66: {
                mask = -256;
                break;
            }
            case 71: 
            case 72: 
            case 73: 
            case 74: 
            case 75: {
                mask = -16776961;
            }
        }
        if (mask == 0 || (typeRef & ~mask) != 0) {
            throw new IllegalArgumentException(CheckClassAdapter.stringConcat$14(Integer.toHexString(typeRef)));
        }
    }

    private static String stringConcat$14(String string) {
        return "Invalid type reference 0x" + string;
    }

    private static String packageName(String name) {
        int index = name.lastIndexOf(47);
        if (index == -1) {
            return "";
        }
        return name.substring(0, index);
    }

    public static void main(String[] args) throws IOException {
        CheckClassAdapter.main(args, new PrintWriter(System.err, true));
    }

    static void main(String[] args, PrintWriter logger) throws IOException {
        ClassReader classReader;
        block6: {
            block5: {
                if (args.length != 1) {
                    logger.println(USAGE);
                    return;
                }
                if (!args[0].endsWith(".class")) break block5;
                FileInputStream inputStream = new FileInputStream(args[0]);
                try {
                    classReader = new ClassReader(inputStream);
                }
                catch (Throwable throwable) {
                    try {
                        ((InputStream)inputStream).close();
                    }
                    catch (Throwable throwable2) {
                    }
                    throw throwable;
                }
                ((InputStream)inputStream).close();
                break block6;
            }
            classReader = new ClassReader(args[0]);
        }
        CheckClassAdapter.verify(classReader, false, logger);
    }

    public static void verify(ClassReader classReader, boolean printResults, PrintWriter printWriter) {
        CheckClassAdapter.verify(classReader, null, printResults, printWriter);
    }

    public static void verify(ClassReader classReader, ClassLoader loader, boolean printResults, PrintWriter printWriter) {
        ClassNode classNode = new ClassNode();
        classReader.accept(new CheckClassAdapter(589824, classNode, false){}, 2);
        Type syperType = classNode.superName == null ? null : Type.getObjectType(classNode.superName);
        List<MethodNode> methods = classNode.methods;
        ArrayList<Type> interfaces = new ArrayList<Type>();
        for (String interfaceName : classNode.interfaces) {
            interfaces.add(Type.getObjectType(interfaceName));
        }
        for (MethodNode method : methods) {
            SimpleVerifier verifier = new SimpleVerifier(Type.getObjectType(classNode.name), syperType, interfaces, (classNode.access & 0x200) != 0);
            Analyzer<BasicValue> analyzer = new Analyzer<BasicValue>(verifier);
            if (loader != null) {
                verifier.setClassLoader(loader);
            }
            try {
                analyzer.analyze(classNode.name, method);
            }
            catch (AnalyzerException e) {
                e.printStackTrace(printWriter);
            }
            if (!printResults) continue;
            CheckClassAdapter.printAnalyzerResult(method, analyzer, printWriter);
        }
        printWriter.flush();
    }

    static void printAnalyzerResult(MethodNode method, Analyzer<BasicValue> analyzer, PrintWriter printWriter) {
        Textifier textifier = new Textifier();
        TraceMethodVisitor traceMethodVisitor = new TraceMethodVisitor(textifier);
        printWriter.println(CheckClassAdapter.stringConcat$15(method.name, method.desc));
        for (int i = 0; i < method.instructions.size(); ++i) {
            method.instructions.get(i).accept(traceMethodVisitor);
            StringBuilder stringBuilder = new StringBuilder();
            Frame<BasicValue> frame = analyzer.getFrames()[i];
            if (frame == null) {
                stringBuilder.append('?');
            } else {
                int j;
                for (j = 0; j < frame.getLocals(); ++j) {
                    stringBuilder.append(CheckClassAdapter.getUnqualifiedName(frame.getLocal(j).toString())).append(' ');
                }
                stringBuilder.append(" : ");
                for (j = 0; j < frame.getStackSize(); ++j) {
                    stringBuilder.append(CheckClassAdapter.getUnqualifiedName(frame.getStack(j).toString())).append(' ');
                }
            }
            while (stringBuilder.length() < method.maxStack + method.maxLocals + 1) {
                stringBuilder.append(' ');
            }
            printWriter.print(Integer.toString(i + 100000).substring(1));
            printWriter.print(CheckClassAdapter.stringConcat$16(stringBuilder, textifier.text.get(textifier.text.size() - 1)));
        }
        for (TryCatchBlockNode tryCatchBlock : method.tryCatchBlocks) {
            tryCatchBlock.accept(traceMethodVisitor);
            printWriter.print(CheckClassAdapter.stringConcat$17(textifier.text.get(textifier.text.size() - 1)));
        }
        printWriter.println();
    }

    private static String stringConcat$15(String string, String string2) {
        return string + string2;
    }

    private static String stringConcat$16(StringBuilder stringBuilder, Object object) {
        return " " + stringBuilder + " : " + object;
    }

    private static String stringConcat$17(Object object) {
        return " " + object;
    }

    private static String getUnqualifiedName(String name) {
        int lastBracketIndex;
        int lastSlashIndex = name.lastIndexOf(47);
        if (lastSlashIndex == -1) {
            return name;
        }
        int endIndex = name.length();
        if (name.charAt(endIndex - 1) == ';') {
            --endIndex;
        }
        if ((lastBracketIndex = name.lastIndexOf(91)) == -1) {
            return name.substring(lastSlashIndex + 1, endIndex);
        }
        return CheckClassAdapter.stringConcat$18(name.substring(0, lastBracketIndex + 1), name.substring(lastSlashIndex + 1, endIndex));
    }

    private static String stringConcat$18(String string, String string2) {
        return string + string2;
    }
}

