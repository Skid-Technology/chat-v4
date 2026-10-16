package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.ConstantDynamic;
import gg.vape.shaded.org.objectweb.asm.Handle;
import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.Type;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ASMifier extends Printer {
   private static final String USAGE = "Prints the ASM code to generate the given class.\nUsage: ASMifier [-nodebug] <fully qualified class name or class file name>";
   private static final int ACCESS_CLASS = 262144;
   private static final int ACCESS_FIELD = 524288;
   private static final int ACCESS_INNER = 1048576;
   private static final int ACCESS_MODULE = 2097152;
   private static final String ANNOTATION_VISITOR = "annotationVisitor";
   private static final String ANNOTATION_VISITOR0 = "annotationVisitor0 = ";
   private static final String COMMA = "\", \"";
   private static final String END_ARRAY = " });\n";
   private static final String END_PARAMETERS = ");\n\n";
   private static final String NEW_OBJECT_ARRAY = ", new Object[] {";
   private static final String VISIT_END = ".visitEnd();\n";
   private static final List<String> FRAME_TYPES = Collections.unmodifiableList(
      Arrays.asList("Opcodes.TOP", "Opcodes.INTEGER", "Opcodes.FLOAT", "Opcodes.DOUBLE", "Opcodes.LONG", "Opcodes.NULL", "Opcodes.UNINITIALIZED_THIS")
   );
   private static final Map<Integer, String> CLASS_VERSIONS;
   protected final String name;
   protected final int id;
   protected Map<Label, String> labelNames;

   public ASMifier() {
      this(589824, "classWriter", 0);
      if (this.getClass() != ASMifier.class) {
         throw new IllegalStateException();
      }
   }

   protected ASMifier(int api, String visitorVariableName, int annotationVisitorId) {
      super(api);
      this.name = visitorVariableName;
      this.id = annotationVisitorId;
   }

   public static void main(String[] args) throws IOException {
      main(args, new PrintWriter(System.out, true), new PrintWriter(System.err, true));
   }

   static void main(String[] args, PrintWriter output, PrintWriter logger) throws IOException {
      main(
         args,
         "Prints the ASM code to generate the given class.\nUsage: ASMifier [-nodebug] <fully qualified class name or class file name>",
         new ASMifier(),
         output,
         logger
      );
   }

   @Override
   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      String simpleName;
      if (name == null) {
         simpleName = "module-info";
      } else {
         int lastSlashIndex = name.lastIndexOf(47);
         if (lastSlashIndex == -1) {
            simpleName = name;
         } else {
            this.text.add(stringConcat$0(name.substring(0, lastSlashIndex).replace('/', '.')));
            simpleName = name.substring(lastSlashIndex + 1).replaceAll("[-\\(\\)]", "_");
         }
      }

      this.text.add("import org.objectweb.asm.AnnotationVisitor;\n");
      this.text.add("import org.objectweb.asm.Attribute;\n");
      this.text.add("import org.objectweb.asm.ClassReader;\n");
      this.text.add("import org.objectweb.asm.ClassWriter;\n");
      this.text.add("import org.objectweb.asm.ConstantDynamic;\n");
      this.text.add("import org.objectweb.asm.FieldVisitor;\n");
      this.text.add("import org.objectweb.asm.Handle;\n");
      this.text.add("import org.objectweb.asm.Label;\n");
      this.text.add("import org.objectweb.asm.MethodVisitor;\n");
      this.text.add("import org.objectweb.asm.Opcodes;\n");
      this.text.add("import org.objectweb.asm.RecordComponentVisitor;\n");
      this.text.add("import org.objectweb.asm.Type;\n");
      this.text.add("import org.objectweb.asm.TypePath;\n");
      this.text.add(stringConcat$1(simpleName));
      this.text.add("public static byte[] dump () throws Exception {\n\n");
      this.text.add("ClassWriter classWriter = new ClassWriter(0);\n");
      this.text.add("FieldVisitor fieldVisitor;\n");
      this.text.add("RecordComponentVisitor recordComponentVisitor;\n");
      this.text.add("MethodVisitor methodVisitor;\n");
      this.text.add("AnnotationVisitor annotationVisitor0;\n\n");
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("classWriter.visit(");
      String versionString = CLASS_VERSIONS.get(version);
      if (versionString != null) {
         this.stringBuilder.append(versionString);
      } else {
         this.stringBuilder.append(version);
      }

      this.stringBuilder.append(", ");
      this.appendAccessFlags(access | 262144);
      this.stringBuilder.append(", ");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(signature);
      this.stringBuilder.append(", ");
      this.appendConstant(superName);
      this.stringBuilder.append(", ");
      if (interfaces != null && interfaces.length > 0) {
         this.stringBuilder.append("new String[] {");

         for (int i = 0; i < interfaces.length; i++) {
            this.stringBuilder.append(i == 0 ? " " : ", ");
            this.appendConstant(interfaces[i]);
         }

         this.stringBuilder.append(" }");
      } else {
         this.stringBuilder.append("null");
      }

      this.stringBuilder.append(");\n\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitSource(String file, String debug) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("classWriter.visitSource(");
      this.appendConstant(file);
      this.stringBuilder.append(", ");
      this.appendConstant(debug);
      this.stringBuilder.append(");\n\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public Printer visitModule(String name, int flags, String version) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("{\n");
      this.stringBuilder.append("ModuleVisitor moduleVisitor = classWriter.visitModule(");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendAccessFlags(flags | 2097152);
      this.stringBuilder.append(", ");
      this.appendConstant(version);
      this.stringBuilder.append(");\n\n");
      this.text.add(this.stringBuilder.toString());
      ASMifier asmifier = this.createASMifier("moduleVisitor", 0);
      this.text.add(asmifier.getText());
      this.text.add("}\n");
      return asmifier;
   }

   @Override
   public void visitNestHost(String nestHost) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("classWriter.visitNestHost(");
      this.appendConstant(nestHost);
      this.stringBuilder.append(");\n\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitOuterClass(String owner, String name, String descriptor) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("classWriter.visitOuterClass(");
      this.appendConstant(owner);
      this.stringBuilder.append(", ");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(descriptor);
      this.stringBuilder.append(");\n\n");
      this.text.add(this.stringBuilder.toString());
   }

   public ASMifier visitClassAnnotation(String descriptor, boolean visible) {
      return this.visitAnnotation(descriptor, visible);
   }

   public ASMifier visitClassTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      return this.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
   }

   @Override
   public void visitClassAttribute(Attribute attribute) {
      this.visitAttribute(attribute);
   }

   @Override
   public void visitNestMember(String nestMember) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("classWriter.visitNestMember(");
      this.appendConstant(nestMember);
      this.stringBuilder.append(");\n\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitPermittedSubclass(String permittedSubclass) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("classWriter.visitPermittedSubclass(");
      this.appendConstant(permittedSubclass);
      this.stringBuilder.append(");\n\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitInnerClass(String name, String outerName, String innerName, int access) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("classWriter.visitInnerClass(");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(outerName);
      this.stringBuilder.append(", ");
      this.appendConstant(innerName);
      this.stringBuilder.append(", ");
      this.appendAccessFlags(access | 1048576);
      this.stringBuilder.append(");\n\n");
      this.text.add(this.stringBuilder.toString());
   }

   public ASMifier visitRecordComponent(String name, String descriptor, String signature) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("{\n");
      this.stringBuilder.append("recordComponentVisitor = classWriter.visitRecordComponent(");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(descriptor);
      this.stringBuilder.append(", ");
      this.appendConstant(signature);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
      ASMifier asmifier = this.createASMifier("recordComponentVisitor", 0);
      this.text.add(asmifier.getText());
      this.text.add("}\n");
      return asmifier;
   }

   public ASMifier visitField(int access, String name, String descriptor, String signature, Object value) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("{\n");
      this.stringBuilder.append("fieldVisitor = classWriter.visitField(");
      this.appendAccessFlags(access | 524288);
      this.stringBuilder.append(", ");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(descriptor);
      this.stringBuilder.append(", ");
      this.appendConstant(signature);
      this.stringBuilder.append(", ");
      this.appendConstant(value);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
      ASMifier asmifier = this.createASMifier("fieldVisitor", 0);
      this.text.add(asmifier.getText());
      this.text.add("}\n");
      return asmifier;
   }

   public ASMifier visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("{\n");
      this.stringBuilder.append("methodVisitor = classWriter.visitMethod(");
      this.appendAccessFlags(access);
      this.stringBuilder.append(", ");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(descriptor);
      this.stringBuilder.append(", ");
      this.appendConstant(signature);
      this.stringBuilder.append(", ");
      if (exceptions != null && exceptions.length > 0) {
         this.stringBuilder.append("new String[] {");

         for (int i = 0; i < exceptions.length; i++) {
            this.stringBuilder.append(i == 0 ? " " : ", ");
            this.appendConstant(exceptions[i]);
         }

         this.stringBuilder.append(" }");
      } else {
         this.stringBuilder.append("null");
      }

      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
      ASMifier asmifier = this.createASMifier("methodVisitor", 0);
      this.text.add(asmifier.getText());
      this.text.add("}\n");
      return asmifier;
   }

   @Override
   public void visitClassEnd() {
      this.text.add("classWriter.visitEnd();\n\n");
      this.text.add("return classWriter.toByteArray();\n");
      this.text.add("}\n");
      this.text.add("}\n");
   }

   @Override
   public void visitMainClass(String mainClass) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("moduleVisitor.visitMainClass(");
      this.appendConstant(mainClass);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitPackage(String packaze) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("moduleVisitor.visitPackage(");
      this.appendConstant(packaze);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitRequire(String module, int access, String version) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("moduleVisitor.visitRequire(");
      this.appendConstant(module);
      this.stringBuilder.append(", ");
      this.appendAccessFlags(access | 2097152);
      this.stringBuilder.append(", ");
      this.appendConstant(version);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitExport(String packaze, int access, String... modules) {
      this.visitExportOrOpen("moduleVisitor.visitExport(", packaze, access, modules);
   }

   @Override
   public void visitOpen(String packaze, int access, String... modules) {
      this.visitExportOrOpen("moduleVisitor.visitOpen(", packaze, access, modules);
   }

   private void visitExportOrOpen(String visitMethod, String packaze, int access, String... modules) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(visitMethod);
      this.appendConstant(packaze);
      this.stringBuilder.append(", ");
      this.appendAccessFlags(access | 2097152);
      if (modules != null && modules.length > 0) {
         this.stringBuilder.append(", new String[] {");

         for (int i = 0; i < modules.length; i++) {
            this.stringBuilder.append(i == 0 ? " " : ", ");
            this.appendConstant(modules[i]);
         }

         this.stringBuilder.append(" }");
      }

      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitUse(String service) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("moduleVisitor.visitUse(");
      this.appendConstant(service);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitProvide(String service, String... providers) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("moduleVisitor.visitProvide(");
      this.appendConstant(service);
      this.stringBuilder.append(",  new String[] {");

      for (int i = 0; i < providers.length; i++) {
         this.stringBuilder.append(i == 0 ? " " : ", ");
         this.appendConstant(providers[i]);
      }

      this.stringBuilder.append(" });\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitModuleEnd() {
      this.text.add("moduleVisitor.visitEnd();\n");
   }

   @Override
   public void visit(String name, Object value) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("annotationVisitor").append(this.id).append(".visit(");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(value);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitEnum(String name, String descriptor, String value) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("annotationVisitor").append(this.id).append(".visitEnum(");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(descriptor);
      this.stringBuilder.append(", ");
      this.appendConstant(value);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   public ASMifier visitAnnotation(String name, String descriptor) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("{\n").append("AnnotationVisitor annotationVisitor").append(this.id + 1).append(" = annotationVisitor");
      this.stringBuilder.append(this.id).append(".visitAnnotation(");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(descriptor);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
      ASMifier asmifier = this.createASMifier("annotationVisitor", this.id + 1);
      this.text.add(asmifier.getText());
      this.text.add("}\n");
      return asmifier;
   }

   public ASMifier visitArray(String name) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("{\n");
      this.stringBuilder.append("AnnotationVisitor annotationVisitor").append(this.id + 1).append(" = annotationVisitor");
      this.stringBuilder.append(this.id).append(".visitArray(");
      this.appendConstant(name);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
      ASMifier asmifier = this.createASMifier("annotationVisitor", this.id + 1);
      this.text.add(asmifier.getText());
      this.text.add("}\n");
      return asmifier;
   }

   @Override
   public void visitAnnotationEnd() {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("annotationVisitor").append(this.id).append(".visitEnd();\n");
      this.text.add(this.stringBuilder.toString());
   }

   public ASMifier visitRecordComponentAnnotation(String descriptor, boolean visible) {
      return this.visitAnnotation(descriptor, visible);
   }

   public ASMifier visitRecordComponentTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      return this.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
   }

   @Override
   public void visitRecordComponentAttribute(Attribute attribute) {
      this.visitAttribute(attribute);
   }

   @Override
   public void visitRecordComponentEnd() {
      this.visitMemberEnd();
   }

   public ASMifier visitFieldAnnotation(String descriptor, boolean visible) {
      return this.visitAnnotation(descriptor, visible);
   }

   public ASMifier visitFieldTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      return this.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
   }

   @Override
   public void visitFieldAttribute(Attribute attribute) {
      this.visitAttribute(attribute);
   }

   @Override
   public void visitFieldEnd() {
      this.visitMemberEnd();
   }

   @Override
   public void visitParameter(String parameterName, int access) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitParameter(");
      if (parameterName == null) {
         this.stringBuilder.append("null");
      } else {
         appendString(this.stringBuilder, parameterName);
      }

      this.stringBuilder.append(", ");
      this.appendAccessFlags(access);
      this.text.add(this.stringBuilder.append(");\n").toString());
   }

   public ASMifier visitAnnotationDefault() {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("{\n").append("annotationVisitor0 = ").append(this.name).append(".visitAnnotationDefault();\n");
      this.text.add(this.stringBuilder.toString());
      ASMifier asmifier = this.createASMifier("annotationVisitor", 0);
      this.text.add(asmifier.getText());
      this.text.add("}\n");
      return asmifier;
   }

   public ASMifier visitMethodAnnotation(String descriptor, boolean visible) {
      return this.visitAnnotation(descriptor, visible);
   }

   public ASMifier visitMethodTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      return this.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
   }

   public ASMifier visitAnnotableParameterCount(int parameterCount, boolean visible) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitAnnotableParameterCount(").append(parameterCount).append(", ").append(visible).append(");\n");
      this.text.add(this.stringBuilder.toString());
      return this;
   }

   public ASMifier visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("{\n").append("annotationVisitor0 = ").append(this.name).append(".visitParameterAnnotation(").append(parameter).append(", ");
      this.appendConstant(descriptor);
      this.stringBuilder.append(", ").append(visible).append(");\n");
      this.text.add(this.stringBuilder.toString());
      ASMifier asmifier = this.createASMifier("annotationVisitor", 0);
      this.text.add(asmifier.getText());
      this.text.add("}\n");
      return asmifier;
   }

   @Override
   public void visitMethodAttribute(Attribute attribute) {
      this.visitAttribute(attribute);
   }

   @Override
   public void visitCode() {
      this.text.add(stringConcat$2(this.name));
   }

   @Override
   public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
      this.stringBuilder.setLength(0);
      switch (type) {
         case -1:
         case 0:
            this.declareFrameTypes(numLocal, local);
            this.declareFrameTypes(numStack, stack);
            if (type == -1) {
               this.stringBuilder.append(this.name).append(".visitFrame(Opcodes.F_NEW, ");
            } else {
               this.stringBuilder.append(this.name).append(".visitFrame(Opcodes.F_FULL, ");
            }

            this.stringBuilder.append(numLocal).append(", new Object[] {");
            this.appendFrameTypes(numLocal, local);
            this.stringBuilder.append("}, ").append(numStack).append(", new Object[] {");
            this.appendFrameTypes(numStack, stack);
            this.stringBuilder.append('}');
            break;
         case 1:
            this.declareFrameTypes(numLocal, local);
            this.stringBuilder.append(this.name).append(".visitFrame(Opcodes.F_APPEND,").append(numLocal).append(", new Object[] {");
            this.appendFrameTypes(numLocal, local);
            this.stringBuilder.append("}, 0, null");
            break;
         case 2:
            this.stringBuilder.append(this.name).append(".visitFrame(Opcodes.F_CHOP,").append(numLocal).append(", null, 0, null");
            break;
         case 3:
            this.stringBuilder.append(this.name).append(".visitFrame(Opcodes.F_SAME, 0, null, 0, null");
            break;
         case 4:
            this.declareFrameTypes(1, stack);
            this.stringBuilder.append(this.name).append(".visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {");
            this.appendFrameTypes(1, stack);
            this.stringBuilder.append('}');
            break;
         default:
            throw new IllegalArgumentException();
      }

      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitInsn(int opcode) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitInsn(").append(OPCODES[opcode]).append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitIntInsn(int opcode, int operand) {
      this.stringBuilder.setLength(0);
      this.stringBuilder
         .append(this.name)
         .append(".visitIntInsn(")
         .append(OPCODES[opcode])
         .append(", ")
         .append(opcode == 188 ? TYPES[operand] : Integer.toString(operand))
         .append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitVarInsn(int opcode, int varIndex) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitVarInsn(").append(OPCODES[opcode]).append(", ").append(varIndex).append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitTypeInsn(int opcode, String type) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitTypeInsn(").append(OPCODES[opcode]).append(", ");
      this.appendConstant(type);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitFieldInsn(").append(OPCODES[opcode]).append(", ");
      this.appendConstant(owner);
      this.stringBuilder.append(", ");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(descriptor);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitMethodInsn(").append(OPCODES[opcode]).append(", ");
      this.appendConstant(owner);
      this.stringBuilder.append(", ");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(descriptor);
      this.stringBuilder.append(", ");
      this.stringBuilder.append(isInterface ? "true" : "false");
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitInvokeDynamicInsn(");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(descriptor);
      this.stringBuilder.append(", ");
      this.appendConstant(bootstrapMethodHandle);
      this.stringBuilder.append(", new Object[]{");

      for (int i = 0; i < bootstrapMethodArguments.length; i++) {
         this.appendConstant(bootstrapMethodArguments[i]);
         if (i != bootstrapMethodArguments.length - 1) {
            this.stringBuilder.append(", ");
         }
      }

      this.stringBuilder.append("});\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitJumpInsn(int opcode, Label label) {
      this.stringBuilder.setLength(0);
      this.declareLabel(label);
      this.stringBuilder.append(this.name).append(".visitJumpInsn(").append(OPCODES[opcode]).append(", ");
      this.appendLabel(label);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitLabel(Label label) {
      this.stringBuilder.setLength(0);
      this.declareLabel(label);
      this.stringBuilder.append(this.name).append(".visitLabel(");
      this.appendLabel(label);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitLdcInsn(Object value) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitLdcInsn(");
      this.appendConstant(value);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitIincInsn(int varIndex, int increment) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitIincInsn(").append(varIndex).append(", ").append(increment).append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
      this.stringBuilder.setLength(0);

      for (Label label : labels) {
         this.declareLabel(label);
      }

      this.declareLabel(dflt);
      this.stringBuilder.append(this.name).append(".visitTableSwitchInsn(").append(min).append(", ").append(max).append(", ");
      this.appendLabel(dflt);
      this.stringBuilder.append(", new Label[] {");

      for (int i = 0; i < labels.length; i++) {
         this.stringBuilder.append(i == 0 ? " " : ", ");
         this.appendLabel(labels[i]);
      }

      this.stringBuilder.append(" });\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
      this.stringBuilder.setLength(0);

      for (Label label : labels) {
         this.declareLabel(label);
      }

      this.declareLabel(dflt);
      this.stringBuilder.append(this.name).append(".visitLookupSwitchInsn(");
      this.appendLabel(dflt);
      this.stringBuilder.append(", new int[] {");

      for (int i = 0; i < keys.length; i++) {
         StringBuilder var8 = this.stringBuilder;
         StringBuilder var10000;
         String var10001;
         if (i == 0) {
            var10000 = var8;
            var10001 = " ";
         } else {
            var10000 = var8;
            var10001 = ", ";
         }

         var10000.append(var10001).append(keys[i]);
      }

      this.stringBuilder.append(" }, new Label[] {");

      for (int i = 0; i < labels.length; i++) {
         StringBuilder var9 = this.stringBuilder;
         StringBuilder var12;
         String var13;
         if (i == 0) {
            var12 = var9;
            var13 = " ";
         } else {
            var12 = var9;
            var13 = ", ";
         }

         var12.append(var13);
         this.appendLabel(labels[i]);
      }

      this.stringBuilder.append(" });\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitMultiANewArrayInsn(");
      this.appendConstant(descriptor);
      this.stringBuilder.append(", ").append(numDimensions).append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   public ASMifier visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      return this.visitTypeAnnotation("visitInsnAnnotation", typeRef, typePath, descriptor, visible);
   }

   @Override
   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
      this.stringBuilder.setLength(0);
      this.declareLabel(start);
      this.declareLabel(end);
      this.declareLabel(handler);
      this.stringBuilder.append(this.name).append(".visitTryCatchBlock(");
      this.appendLabel(start);
      this.stringBuilder.append(", ");
      this.appendLabel(end);
      this.stringBuilder.append(", ");
      this.appendLabel(handler);
      this.stringBuilder.append(", ");
      this.appendConstant(type);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   public ASMifier visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      return this.visitTypeAnnotation("visitTryCatchAnnotation", typeRef, typePath, descriptor, visible);
   }

   @Override
   public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitLocalVariable(");
      this.appendConstant(name);
      this.stringBuilder.append(", ");
      this.appendConstant(descriptor);
      this.stringBuilder.append(", ");
      this.appendConstant(signature);
      this.stringBuilder.append(", ");
      this.appendLabel(start);
      this.stringBuilder.append(", ");
      this.appendLabel(end);
      this.stringBuilder.append(", ").append(index).append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public Printer visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("{\n").append("annotationVisitor0 = ").append(this.name).append(".visitLocalVariableAnnotation(").append(typeRef);
      if (typePath == null) {
         this.stringBuilder.append(", null, ");
      } else {
         this.stringBuilder.append(", TypePath.fromString(\"").append(typePath).append("\"), ");
      }

      this.stringBuilder.append("new Label[] {");

      for (int i = 0; i < start.length; i++) {
         StringBuilder var9 = this.stringBuilder;
         StringBuilder var10000;
         String var10001;
         if (i == 0) {
            var10000 = var9;
            var10001 = " ";
         } else {
            var10000 = var9;
            var10001 = ", ";
         }

         var10000.append(var10001);
         this.appendLabel(start[i]);
      }

      this.stringBuilder.append(" }, new Label[] {");

      for (int i = 0; i < end.length; i++) {
         StringBuilder var10 = this.stringBuilder;
         StringBuilder var15;
         String var17;
         if (i == 0) {
            var15 = var10;
            var17 = " ";
         } else {
            var15 = var10;
            var17 = ", ";
         }

         var15.append(var17);
         this.appendLabel(end[i]);
      }

      this.stringBuilder.append(" }, new int[] {");

      for (int i = 0; i < index.length; i++) {
         StringBuilder var11 = this.stringBuilder;
         StringBuilder var16;
         String var18;
         if (i == 0) {
            var16 = var11;
            var18 = " ";
         } else {
            var16 = var11;
            var18 = ", ";
         }

         var16.append(var18).append(index[i]);
      }

      this.stringBuilder.append(" }, ");
      this.appendConstant(descriptor);
      this.stringBuilder.append(", ").append(visible).append(");\n");
      this.text.add(this.stringBuilder.toString());
      ASMifier asmifier = this.createASMifier("annotationVisitor", 0);
      this.text.add(asmifier.getText());
      this.text.add("}\n");
      return asmifier;
   }

   @Override
   public void visitLineNumber(int line, Label start) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitLineNumber(").append(line).append(", ");
      this.appendLabel(start);
      this.stringBuilder.append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitMaxs(int maxStack, int maxLocals) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitMaxs(").append(maxStack).append(", ").append(maxLocals).append(");\n");
      this.text.add(this.stringBuilder.toString());
   }

   @Override
   public void visitMethodEnd() {
      this.visitMemberEnd();
   }

   public ASMifier visitAnnotation(String descriptor, boolean visible) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("{\n").append("annotationVisitor0 = ").append(this.name).append(".visitAnnotation(");
      this.appendConstant(descriptor);
      this.stringBuilder.append(", ").append(visible).append(");\n");
      this.text.add(this.stringBuilder.toString());
      ASMifier asmifier = this.createASMifier("annotationVisitor", 0);
      this.text.add(asmifier.getText());
      this.text.add("}\n");
      return asmifier;
   }

   public ASMifier visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      return this.visitTypeAnnotation("visitTypeAnnotation", typeRef, typePath, descriptor, visible);
   }

   public ASMifier visitTypeAnnotation(String method, int typeRef, TypePath typePath, String descriptor, boolean visible) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("{\n").append("annotationVisitor0 = ").append(this.name).append('.').append(method).append('(').append(typeRef);
      if (typePath == null) {
         this.stringBuilder.append(", null, ");
      } else {
         this.stringBuilder.append(", TypePath.fromString(\"").append(typePath).append("\"), ");
      }

      this.appendConstant(descriptor);
      this.stringBuilder.append(", ").append(visible).append(");\n");
      this.text.add(this.stringBuilder.toString());
      ASMifier asmifier = this.createASMifier("annotationVisitor", 0);
      this.text.add(asmifier.getText());
      this.text.add("}\n");
      return asmifier;
   }

   public void visitAttribute(Attribute attribute) {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append("// ATTRIBUTE ").append(attribute.type).append('\n');
      if (attribute instanceof ASMifierSupport) {
         if (this.labelNames == null) {
            this.labelNames = new HashMap<>();
         }

         this.stringBuilder.append("{\n");
         ((ASMifierSupport)attribute).asmify(this.stringBuilder, "attribute", this.labelNames);
         this.stringBuilder.append(this.name).append(".visitAttribute(attribute);\n");
         this.stringBuilder.append("}\n");
      }

      this.text.add(this.stringBuilder.toString());
   }

   private void visitMemberEnd() {
      this.stringBuilder.setLength(0);
      this.stringBuilder.append(this.name).append(".visitEnd();\n");
      this.text.add(this.stringBuilder.toString());
   }

   protected ASMifier createASMifier(String visitorVariableName, int annotationVisitorId) {
      return new ASMifier(this.api, visitorVariableName, annotationVisitorId);
   }

   private void appendAccessFlags(int accessFlags) {
      boolean isEmpty = true;
      if ((accessFlags & 1) != 0) {
         this.stringBuilder.append("ACC_PUBLIC");
         isEmpty = false;
      }

      if ((accessFlags & 2) != 0) {
         this.stringBuilder.append("ACC_PRIVATE");
         isEmpty = false;
      }

      if ((accessFlags & 4) != 0) {
         this.stringBuilder.append("ACC_PROTECTED");
         isEmpty = false;
      }

      if ((accessFlags & 16) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_FINAL");
         isEmpty = false;
      }

      if ((accessFlags & 8) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_STATIC");
         isEmpty = false;
      }

      if ((accessFlags & 32) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         if ((accessFlags & 262144) == 0) {
            if ((accessFlags & 2097152) == 0) {
               this.stringBuilder.append("ACC_SYNCHRONIZED");
            } else {
               this.stringBuilder.append("ACC_TRANSITIVE");
            }
         } else {
            this.stringBuilder.append("ACC_SUPER");
         }

         isEmpty = false;
      }

      if ((accessFlags & 64) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         if ((accessFlags & 524288) == 0) {
            if ((accessFlags & 2097152) == 0) {
               this.stringBuilder.append("ACC_BRIDGE");
            } else {
               this.stringBuilder.append("ACC_STATIC_PHASE");
            }
         } else {
            this.stringBuilder.append("ACC_VOLATILE");
         }

         isEmpty = false;
      }

      if ((accessFlags & 128) != 0 && (accessFlags & 786432) == 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_VARARGS");
         isEmpty = false;
      }

      if ((accessFlags & 128) != 0 && (accessFlags & 524288) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_TRANSIENT");
         isEmpty = false;
      }

      if ((accessFlags & 256) != 0 && (accessFlags & 786432) == 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_NATIVE");
         isEmpty = false;
      }

      if ((accessFlags & 16384) != 0 && (accessFlags & 1835008) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_ENUM");
         isEmpty = false;
      }

      if ((accessFlags & 8192) != 0 && (accessFlags & 1310720) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_ANNOTATION");
         isEmpty = false;
      }

      if ((accessFlags & 1024) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_ABSTRACT");
         isEmpty = false;
      }

      if ((accessFlags & 512) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_INTERFACE");
         isEmpty = false;
      }

      if ((accessFlags & 2048) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_STRICT");
         isEmpty = false;
      }

      if ((accessFlags & 4096) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_SYNTHETIC");
         isEmpty = false;
      }

      if ((accessFlags & 131072) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_DEPRECATED");
         isEmpty = false;
      }

      if ((accessFlags & 65536) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         this.stringBuilder.append("ACC_RECORD");
         isEmpty = false;
      }

      if ((accessFlags & 32768) != 0) {
         if (!isEmpty) {
            this.stringBuilder.append(" | ");
         }

         if ((accessFlags & 262144) == 0) {
            this.stringBuilder.append("ACC_MANDATED");
         } else {
            this.stringBuilder.append("ACC_MODULE");
         }

         isEmpty = false;
      }

      if (isEmpty) {
         this.stringBuilder.append('0');
      }
   }

   protected void appendConstant(Object value) {
      if (value == null) {
         this.stringBuilder.append("null");
      } else if (value instanceof String) {
         appendString(this.stringBuilder, (String)value);
      } else if (value instanceof Type) {
         this.stringBuilder.append("Type.getType(\"");
         this.stringBuilder.append(((Type)value).getDescriptor());
         this.stringBuilder.append("\")");
      } else if (value instanceof Handle) {
         this.stringBuilder.append("new Handle(");
         Handle handle = (Handle)value;
         this.stringBuilder.append("Opcodes.").append(HANDLE_TAG[handle.getTag()]).append(", \"");
         this.stringBuilder.append(handle.getOwner()).append("\", \"");
         this.stringBuilder.append(handle.getName()).append("\", \"");
         this.stringBuilder.append(handle.getDesc()).append("\", ");
         this.stringBuilder.append(handle.isInterface()).append(')');
      } else if (value instanceof ConstantDynamic) {
         this.stringBuilder.append("new ConstantDynamic(\"");
         ConstantDynamic constantDynamic = (ConstantDynamic)value;
         this.stringBuilder.append(constantDynamic.getName()).append("\", \"");
         this.stringBuilder.append(constantDynamic.getDescriptor()).append("\", ");
         this.appendConstant(constantDynamic.getBootstrapMethod());
         this.stringBuilder.append(", new Object[] {");
         int bootstrapMethodArgumentCount = constantDynamic.getBootstrapMethodArgumentCount();

         for (int i = 0; i < bootstrapMethodArgumentCount; i++) {
            this.appendConstant(constantDynamic.getBootstrapMethodArgument(i));
            if (i != bootstrapMethodArgumentCount - 1) {
               this.stringBuilder.append(", ");
            }
         }

         this.stringBuilder.append("})");
      } else if (value instanceof Byte) {
         this.stringBuilder.append("new Byte((byte)").append(value).append(')');
      } else if (value instanceof Boolean) {
         this.stringBuilder.append((Boolean)value ? "Boolean.TRUE" : "Boolean.FALSE");
      } else if (value instanceof Short) {
         this.stringBuilder.append("new Short((short)").append(value).append(')');
      } else if (value instanceof Character) {
         this.stringBuilder.append("new Character((char)").append(((Character)value).charValue()).append(')');
      } else if (value instanceof Integer) {
         this.stringBuilder.append("new Integer(").append(value).append(')');
      } else if (value instanceof Float) {
         this.stringBuilder.append("new Float(\"").append(value).append("\")");
      } else if (value instanceof Long) {
         this.stringBuilder.append("new Long(").append(value).append("L)");
      } else if (value instanceof Double) {
         this.stringBuilder.append("new Double(\"").append(value).append("\")");
      } else if (value instanceof byte[]) {
         byte[] byteArray = (byte[])value;
         this.stringBuilder.append("new byte[] {");

         for (int ix = 0; ix < byteArray.length; ix++) {
            StringBuilder var5 = this.stringBuilder;
            StringBuilder var10000;
            String var10001;
            if (ix == 0) {
               var10000 = var5;
               var10001 = "";
            } else {
               var10000 = var5;
               var10001 = ",";
            }

            var10000.append(var10001).append(byteArray[ix]);
         }

         this.stringBuilder.append('}');
      } else if (value instanceof boolean[]) {
         boolean[] booleanArray = (boolean[])value;
         this.stringBuilder.append("new boolean[] {");

         for (int ix = 0; ix < booleanArray.length; ix++) {
            StringBuilder var6 = this.stringBuilder;
            StringBuilder var30;
            String var37;
            if (ix == 0) {
               var30 = var6;
               var37 = "";
            } else {
               var30 = var6;
               var37 = ",";
            }

            var30.append(var37).append(booleanArray[ix]);
         }

         this.stringBuilder.append('}');
      } else if (value instanceof short[]) {
         short[] shortArray = (short[])value;
         this.stringBuilder.append("new short[] {");

         for (int ix = 0; ix < shortArray.length; ix++) {
            StringBuilder var7 = this.stringBuilder;
            StringBuilder var31;
            String var38;
            if (ix == 0) {
               var31 = var7;
               var38 = "";
            } else {
               var31 = var7;
               var38 = ",";
            }

            var31.append(var38).append("(short)").append(shortArray[ix]);
         }

         this.stringBuilder.append('}');
      } else if (value instanceof char[]) {
         char[] charArray = (char[])value;
         this.stringBuilder.append("new char[] {");

         for (int ix = 0; ix < charArray.length; ix++) {
            StringBuilder var8 = this.stringBuilder;
            StringBuilder var32;
            String var39;
            if (ix == 0) {
               var32 = var8;
               var39 = "";
            } else {
               var32 = var8;
               var39 = ",";
            }

            var32.append(var39).append("(char)").append(charArray[ix]);
         }

         this.stringBuilder.append('}');
      } else if (value instanceof int[]) {
         int[] intArray = (int[])value;
         this.stringBuilder.append("new int[] {");

         for (int ix = 0; ix < intArray.length; ix++) {
            StringBuilder var9 = this.stringBuilder;
            StringBuilder var33;
            String var40;
            if (ix == 0) {
               var33 = var9;
               var40 = "";
            } else {
               var33 = var9;
               var40 = ",";
            }

            var33.append(var40).append(intArray[ix]);
         }

         this.stringBuilder.append('}');
      } else if (value instanceof long[]) {
         long[] longArray = (long[])value;
         this.stringBuilder.append("new long[] {");

         for (int ix = 0; ix < longArray.length; ix++) {
            StringBuilder var10 = this.stringBuilder;
            StringBuilder var34;
            String var41;
            if (ix == 0) {
               var34 = var10;
               var41 = "";
            } else {
               var34 = var10;
               var41 = ",";
            }

            var34.append(var41).append(longArray[ix]).append('L');
         }

         this.stringBuilder.append('}');
      } else if (value instanceof float[]) {
         float[] floatArray = (float[])value;
         this.stringBuilder.append("new float[] {");

         for (int ix = 0; ix < floatArray.length; ix++) {
            StringBuilder var11 = this.stringBuilder;
            StringBuilder var35;
            String var42;
            if (ix == 0) {
               var35 = var11;
               var42 = "";
            } else {
               var35 = var11;
               var42 = ",";
            }

            var35.append(var42).append(floatArray[ix]).append('f');
         }

         this.stringBuilder.append('}');
      } else if (value instanceof double[]) {
         double[] doubleArray = (double[])value;
         this.stringBuilder.append("new double[] {");

         for (int ix = 0; ix < doubleArray.length; ix++) {
            StringBuilder var12 = this.stringBuilder;
            StringBuilder var36;
            String var43;
            if (ix == 0) {
               var36 = var12;
               var43 = "";
            } else {
               var36 = var12;
               var43 = ",";
            }

            var36.append(var43).append(doubleArray[ix]).append('d');
         }

         this.stringBuilder.append('}');
      }
   }

   private void declareFrameTypes(int numTypes, Object[] frameTypes) {
      for (int i = 0; i < numTypes; i++) {
         if (frameTypes[i] instanceof Label) {
            this.declareLabel((Label)frameTypes[i]);
         }
      }
   }

   private void appendFrameTypes(int numTypes, Object[] frameTypes) {
      for (int i = 0; i < numTypes; i++) {
         if (i > 0) {
            this.stringBuilder.append(", ");
         }

         if (frameTypes[i] instanceof String) {
            this.appendConstant(frameTypes[i]);
         } else if (frameTypes[i] instanceof Integer) {
            this.stringBuilder.append(FRAME_TYPES.get((Integer)frameTypes[i]));
         } else {
            this.appendLabel((Label)frameTypes[i]);
         }
      }
   }

   protected void declareLabel(Label label) {
      if (this.labelNames == null) {
         this.labelNames = new HashMap<>();
      }

      String labelName = this.labelNames.get(label);
      if (labelName == null) {
         labelName = stringConcat$3(this.labelNames.size());
         this.labelNames.put(label, labelName);
         this.stringBuilder.append("Label ").append(labelName).append(" = new Label();\n");
      }
   }

   protected void appendLabel(Label label) {
      this.stringBuilder.append(this.labelNames.get(label));
   }

   static {
      HashMap<Integer, String> classVersions = new HashMap<>();
      classVersions.put(196653, "V1_1");
      classVersions.put(46, "V1_2");
      classVersions.put(47, "V1_3");
      classVersions.put(48, "V1_4");
      classVersions.put(49, "V1_5");
      classVersions.put(50, "V1_6");
      classVersions.put(51, "V1_7");
      classVersions.put(52, "V1_8");
      classVersions.put(53, "V9");
      classVersions.put(54, "V10");
      classVersions.put(55, "V11");
      classVersions.put(56, "V12");
      classVersions.put(57, "V13");
      classVersions.put(58, "V14");
      classVersions.put(59, "V15");
      classVersions.put(60, "V16");
      classVersions.put(61, "V17");
      classVersions.put(62, "V18");
      classVersions.put(63, "V19");
      classVersions.put(64, "V20");
      classVersions.put(65, "V21");
      classVersions.put(66, "V22");
      classVersions.put(67, "V23");
      classVersions.put(68, "V24");
      CLASS_VERSIONS = Collections.unmodifiableMap(classVersions);
   }
}
