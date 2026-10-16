/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

final class Constants {
    final static String CONSTANT_VALUE = "ConstantValue";
    final static String CODE = "Code";
    final static String STACK_MAP_TABLE = "StackMapTable";
    final static String EXCEPTIONS = "Exceptions";
    final static String INNER_CLASSES = "InnerClasses";
    final static String ENCLOSING_METHOD = "EnclosingMethod";
    final static String SYNTHETIC = "Synthetic";
    final static String SIGNATURE = "Signature";
    final static String SOURCE_FILE = "SourceFile";
    final static String SOURCE_DEBUG_EXTENSION = "SourceDebugExtension";
    final static String LINE_NUMBER_TABLE = "LineNumberTable";
    final static String LOCAL_VARIABLE_TABLE = "LocalVariableTable";
    final static String LOCAL_VARIABLE_TYPE_TABLE = "LocalVariableTypeTable";
    final static String DEPRECATED = "Deprecated";
    final static String RUNTIME_VISIBLE_ANNOTATIONS = "RuntimeVisibleAnnotations";
    final static String RUNTIME_INVISIBLE_ANNOTATIONS = "RuntimeInvisibleAnnotations";
    final static String RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS = "RuntimeVisibleParameterAnnotations";
    final static String RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS = "RuntimeInvisibleParameterAnnotations";
    final static String RUNTIME_VISIBLE_TYPE_ANNOTATIONS = "RuntimeVisibleTypeAnnotations";
    final static String RUNTIME_INVISIBLE_TYPE_ANNOTATIONS = "RuntimeInvisibleTypeAnnotations";
    final static String ANNOTATION_DEFAULT = "AnnotationDefault";
    final static String BOOTSTRAP_METHODS = "BootstrapMethods";
    final static String METHOD_PARAMETERS = "MethodParameters";
    final static String MODULE = "Module";
    final static String MODULE_PACKAGES = "ModulePackages";
    final static String MODULE_MAIN_CLASS = "ModuleMainClass";
    final static String NEST_HOST = "NestHost";
    final static String NEST_MEMBERS = "NestMembers";
    final static String PERMITTED_SUBCLASSES = "PermittedSubclasses";
    final static String RECORD = "Record";
    final static int ACC_CONSTRUCTOR = 262144;
    final static int F_INSERT = 256;
    final static int LDC_W = 19;
    final static int LDC2_W = 20;
    final static int ILOAD_0 = 26;
    final static int ILOAD_1 = 27;
    final static int ILOAD_2 = 28;
    final static int ILOAD_3 = 29;
    final static int LLOAD_0 = 30;
    final static int LLOAD_1 = 31;
    final static int LLOAD_2 = 32;
    final static int LLOAD_3 = 33;
    final static int FLOAD_0 = 34;
    final static int FLOAD_1 = 35;
    final static int FLOAD_2 = 36;
    final static int FLOAD_3 = 37;
    final static int DLOAD_0 = 38;
    final static int DLOAD_1 = 39;
    final static int DLOAD_2 = 40;
    final static int DLOAD_3 = 41;
    final static int ALOAD_0 = 42;
    final static int ALOAD_1 = 43;
    final static int ALOAD_2 = 44;
    final static int ALOAD_3 = 45;
    final static int ISTORE_0 = 59;
    final static int ISTORE_1 = 60;
    final static int ISTORE_2 = 61;
    final static int ISTORE_3 = 62;
    final static int LSTORE_0 = 63;
    final static int LSTORE_1 = 64;
    final static int LSTORE_2 = 65;
    final static int LSTORE_3 = 66;
    final static int FSTORE_0 = 67;
    final static int FSTORE_1 = 68;
    final static int FSTORE_2 = 69;
    final static int FSTORE_3 = 70;
    final static int DSTORE_0 = 71;
    final static int DSTORE_1 = 72;
    final static int DSTORE_2 = 73;
    final static int DSTORE_3 = 74;
    final static int ASTORE_0 = 75;
    final static int ASTORE_1 = 76;
    final static int ASTORE_2 = 77;
    final static int ASTORE_3 = 78;
    final static int WIDE = 196;
    final static int GOTO_W = 200;
    final static int JSR_W = 201;
    final static int WIDE_JUMP_OPCODE_DELTA = 33;
    final static int ASM_OPCODE_DELTA = 49;
    final static int ASM_IFNULL_OPCODE_DELTA = 20;
    final static int ASM_IFEQ = 202;
    final static int ASM_IFNE = 203;
    final static int ASM_IFLT = 204;
    final static int ASM_IFGE = 205;
    final static int ASM_IFGT = 206;
    final static int ASM_IFLE = 207;
    final static int ASM_IF_ICMPEQ = 208;
    final static int ASM_IF_ICMPNE = 209;
    final static int ASM_IF_ICMPLT = 210;
    final static int ASM_IF_ICMPGE = 211;
    final static int ASM_IF_ICMPGT = 212;
    final static int ASM_IF_ICMPLE = 213;
    final static int ASM_IF_ACMPEQ = 214;
    final static int ASM_IF_ACMPNE = 215;
    final static int ASM_GOTO = 216;
    final static int ASM_JSR = 217;
    final static int ASM_IFNULL = 218;
    final static int ASM_IFNONNULL = 219;
    final static int ASM_GOTO_W = 220;

    private Constants() {
    }

    static void checkAsmExperimental(Object caller) {
        Class<?> callerClass = caller.getClass();
        String internalName = callerClass.getName().replace('.', '/');
        if (!Constants.isWhitelisted(internalName)) {
            Constants.checkIsPreview(callerClass.getClassLoader().getResourceAsStream(Constants.stringConcat$0(internalName)));
        }
    }

    private static String stringConcat$0(String string) {
        return string + ".class";
    }

    static boolean isWhitelisted(String internalName) {
        if (!internalName.startsWith("gg/vape/shaded/org/objectweb/asm/")) {
            return false;
        }
        String member = "(Annotation|Class|Field|Method|Module|RecordComponent|Signature)";
        return internalName.contains("Test$") || Pattern.matches(Constants.stringConcat$1(member), internalName) || Pattern.matches(Constants.stringConcat$2(member), internalName);
    }

    private static String stringConcat$1(String string) {
        return "gg/vape/shaded/org/objectweb/asm/util/Trace" + string + "Visitor(\\$.*)?";
    }

    private static String stringConcat$2(String string) {
        return "gg/vape/shaded/org/objectweb/asm/util/Check" + string + "Adapter(\\$.*)?";
    }

    static void checkIsPreview(InputStream classInputStream) {
        int minorVersion;
        if (classInputStream == null) {
            throw new IllegalStateException("Bytecode not available, can't check class version");
        }
        try {
            DataInputStream callerClassStream = new DataInputStream(classInputStream);
            try {
                callerClassStream.readInt();
                minorVersion = callerClassStream.readUnsignedShort();
            }
            catch (Throwable throwable) {
                try {
                    callerClassStream.close();
                }
                catch (Throwable throwable2) {
                }
                throw throwable;
            }
            callerClassStream.close();
        }
        catch (IOException ioe) {
            throw new IllegalStateException("I/O error, can't check class version", ioe);
        }
        if (minorVersion != 65535) {
            throw new IllegalStateException("ASM10_EXPERIMENTAL can only be used by classes compiled with --enable-preview");
        }
    }
}

