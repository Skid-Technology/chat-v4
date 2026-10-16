package gg.vape.shaded.javassist;

import gg.vape.shaded.javassist.bytecode.AccessFlag;
import gg.vape.shaded.javassist.bytecode.AnnotationsAttribute;
import gg.vape.shaded.javassist.bytecode.AttributeInfo;
import gg.vape.shaded.javassist.bytecode.Bytecode;
import gg.vape.shaded.javassist.bytecode.ClassFile;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.Descriptor;
import gg.vape.shaded.javassist.bytecode.FieldInfo;
import gg.vape.shaded.javassist.bytecode.SignatureAttribute;
import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.Javac;
import gg.vape.shaded.javassist.compiler.SymbolTable;
import gg.vape.shaded.javassist.compiler.ast.ASTree;
import gg.vape.shaded.javassist.compiler.ast.DoubleConst;
import gg.vape.shaded.javassist.compiler.ast.IntConst;
import gg.vape.shaded.javassist.compiler.ast.StringL;

public class CtField extends CtMember {
   static final String javaLangString = "java.lang.String";
   protected FieldInfo fieldInfo;

   public CtField(CtClass type, String name, CtClass declaring) throws CannotCompileException {
      this(Descriptor.of(type), name, declaring);
   }

   public CtField(CtField src, CtClass declaring) throws CannotCompileException {
      this(src.fieldInfo.getDescriptor(), src.fieldInfo.getName(), declaring);
      FieldInfo fi = this.fieldInfo;
      fi.setAccessFlags(src.fieldInfo.getAccessFlags());
      ConstPool cp = fi.getConstPool();

      for (AttributeInfo ainfo : src.fieldInfo.getAttributes()) {
         fi.addAttribute(ainfo.copy(cp, null));
      }
   }

   private CtField(String typeDesc, String name, CtClass clazz) throws CannotCompileException {
      super(clazz);
      ClassFile cf = clazz.getClassFile2();
      if (cf == null) {
         throw new CannotCompileException("bad declaring class: " + clazz.getName());
      } else {
         this.fieldInfo = new FieldInfo(cf.getConstPool(), name, typeDesc);
      }
   }

   CtField(FieldInfo fi, CtClass clazz) {
      super(clazz);
      this.fieldInfo = fi;
   }

   @Override
   public String toString() {
      return this.getDeclaringClass().getName() + "." + this.getName() + ":" + this.fieldInfo.getDescriptor();
   }

   @Override
   protected void extendToString(StringBuilder buffer) {
      buffer.append(' ');
      buffer.append(this.getName());
      buffer.append(' ');
      buffer.append(this.fieldInfo.getDescriptor());
   }

   protected ASTree getInitAST() {
      return null;
   }

   CtField.Initializer getInit() {
      ASTree tree = this.getInitAST();
      return tree == null ? null : CtField.Initializer.byExpr(tree);
   }

   public static CtField make(String src, CtClass declaring) throws CannotCompileException {
      Javac compiler = new Javac(declaring);

      try {
         CtMember obj = compiler.compile(src);
         if (obj instanceof CtField) {
            return (CtField)obj;
         }
      } catch (CompileError var4) {
         throw new CannotCompileException(var4);
      }

      throw new CannotCompileException("not a field");
   }

   public FieldInfo getFieldInfo() {
      this.declaringClass.checkModify();
      return this.fieldInfo;
   }

   public FieldInfo getFieldInfo2() {
      return this.fieldInfo;
   }

   @Override
   public CtClass getDeclaringClass() {
      return super.getDeclaringClass();
   }

   @Override
   public String getName() {
      return this.fieldInfo.getName();
   }

   public void setName(String newName) {
      this.declaringClass.checkModify();
      this.fieldInfo.setName(newName);
   }

   @Override
   public int getModifiers() {
      return AccessFlag.toModifier(this.fieldInfo.getAccessFlags());
   }

   @Override
   public void setModifiers(int mod) {
      this.declaringClass.checkModify();
      this.fieldInfo.setAccessFlags(AccessFlag.of(mod));
   }

   @Override
   public boolean hasAnnotation(String typeName) {
      FieldInfo fi = this.getFieldInfo2();
      AnnotationsAttribute ainfo = (AnnotationsAttribute)fi.getAttribute("RuntimeInvisibleAnnotations");
      AnnotationsAttribute ainfo2 = (AnnotationsAttribute)fi.getAttribute("RuntimeVisibleAnnotations");
      return CtClassType.hasAnnotationType(typeName, this.getDeclaringClass().getClassPool(), ainfo, ainfo2);
   }

   @Override
   public Object getAnnotation(Class<?> clz) throws ClassNotFoundException {
      FieldInfo fi = this.getFieldInfo2();
      AnnotationsAttribute ainfo = (AnnotationsAttribute)fi.getAttribute("RuntimeInvisibleAnnotations");
      AnnotationsAttribute ainfo2 = (AnnotationsAttribute)fi.getAttribute("RuntimeVisibleAnnotations");
      return CtClassType.getAnnotationType(clz, this.getDeclaringClass().getClassPool(), ainfo, ainfo2);
   }

   @Override
   public Object[] getAnnotations() throws ClassNotFoundException {
      return this.getAnnotations(false);
   }

   @Override
   public Object[] getAvailableAnnotations() {
      try {
         return this.getAnnotations(true);
      } catch (ClassNotFoundException var2) {
         throw new RuntimeException("Unexpected exception", var2);
      }
   }

   private Object[] getAnnotations(boolean ignoreNotFound) throws ClassNotFoundException {
      FieldInfo fi = this.getFieldInfo2();
      AnnotationsAttribute ainfo = (AnnotationsAttribute)fi.getAttribute("RuntimeInvisibleAnnotations");
      AnnotationsAttribute ainfo2 = (AnnotationsAttribute)fi.getAttribute("RuntimeVisibleAnnotations");
      return CtClassType.toAnnotationType(ignoreNotFound, this.getDeclaringClass().getClassPool(), ainfo, ainfo2);
   }

   @Override
   public String getSignature() {
      return this.fieldInfo.getDescriptor();
   }

   @Override
   public String getGenericSignature() {
      SignatureAttribute sa = (SignatureAttribute)this.fieldInfo.getAttribute("Signature");
      return sa == null ? null : sa.getSignature();
   }

   @Override
   public void setGenericSignature(String sig) {
      this.declaringClass.checkModify();
      this.fieldInfo.addAttribute(new SignatureAttribute(this.fieldInfo.getConstPool(), sig));
   }

   public CtClass getType() throws NotFoundException {
      return Descriptor.toCtClass(this.fieldInfo.getDescriptor(), this.declaringClass.getClassPool());
   }

   public void setType(CtClass clazz) {
      this.declaringClass.checkModify();
      this.fieldInfo.setDescriptor(Descriptor.of(clazz));
   }

   public Object getConstantValue() {
      int index = this.fieldInfo.getConstantValue();
      if (index == 0) {
         return null;
      } else {
         ConstPool cp = this.fieldInfo.getConstPool();
         switch (cp.getTag(index)) {
            case 3:
               int value = cp.getIntegerInfo(index);
               if ("Z".equals(this.fieldInfo.getDescriptor())) {
                  return value != 0;
               }

               return value;
            case 4:
               return cp.getFloatInfo(index);
            case 5:
               return cp.getLongInfo(index);
            case 6:
               return cp.getDoubleInfo(index);
            case 7:
            default:
               throw new RuntimeException("bad tag: " + cp.getTag(index) + " at " + index);
            case 8:
               return cp.getStringInfo(index);
         }
      }
   }

   @Override
   public byte[] getAttribute(String name) {
      AttributeInfo ai = this.fieldInfo.getAttribute(name);
      return ai == null ? null : ai.get();
   }

   @Override
   public void setAttribute(String name, byte[] data) {
      this.declaringClass.checkModify();
      this.fieldInfo.addAttribute(new AttributeInfo(this.fieldInfo.getConstPool(), name, data));
   }

   static class ArrayInitializer extends CtField.Initializer {
      CtClass type;
      int size;

      ArrayInitializer(CtClass t, int s) {
         this.type = t;
         this.size = s;
      }

      private void addNewarray(Bytecode code) {
         if (this.type.isPrimitive()) {
            code.addNewarray(((CtPrimitiveType)this.type).getArrayType(), this.size);
         } else {
            code.addAnewarray(this.type, this.size);
         }
      }

      @Override
      int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
         code.addAload(0);
         this.addNewarray(code);
         code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
         return 2;
      }

      @Override
      int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
         this.addNewarray(code);
         code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
         return 1;
      }
   }

   static class CodeInitializer extends CtField.CodeInitializer0 {
      private String expression;

      CodeInitializer(String expr) {
         this.expression = expr;
      }

      @Override
      void compileExpr(Javac drv) throws CompileError {
         drv.compileExpr(this.expression);
      }

      @Override
      int getConstantValue(ConstPool cp, CtClass type) {
         try {
            ASTree t = Javac.parseExpr(this.expression, new SymbolTable());
            return this.getConstantValue2(cp, type, t);
         } catch (CompileError var4) {
            return 0;
         }
      }
   }

   abstract static class CodeInitializer0 extends CtField.Initializer {
      abstract void compileExpr(Javac var1) throws CompileError;

      @Override
      int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
         try {
            code.addAload(0);
            this.compileExpr(drv);
            code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
            return code.getMaxStack();
         } catch (CompileError var7) {
            throw new CannotCompileException(var7);
         }
      }

      @Override
      int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
         try {
            this.compileExpr(drv);
            code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
            return code.getMaxStack();
         } catch (CompileError var6) {
            throw new CannotCompileException(var6);
         }
      }

      int getConstantValue2(ConstPool cp, CtClass type, ASTree tree) {
         if (type.isPrimitive()) {
            if (tree instanceof IntConst) {
               long value = ((IntConst)tree).get();
               if (type == CtClass.doubleType) {
                  return cp.addDoubleInfo(value);
               }

               if (type == CtClass.floatType) {
                  return cp.addFloatInfo((float)value);
               }

               if (type == CtClass.longType) {
                  return cp.addLongInfo(value);
               }

               if (type != CtClass.voidType) {
                  return cp.addIntegerInfo((int)value);
               }
            } else if (tree instanceof DoubleConst) {
               double valuex = ((DoubleConst)tree).get();
               if (type == CtClass.floatType) {
                  return cp.addFloatInfo((float)valuex);
               }

               if (type == CtClass.doubleType) {
                  return cp.addDoubleInfo(valuex);
               }
            }
         } else if (tree instanceof StringL && type.getName().equals("java.lang.String")) {
            return cp.addStringInfo(((StringL)tree).get());
         }

         return 0;
      }
   }

   static class DoubleInitializer extends CtField.Initializer {
      double value;

      DoubleInitializer(double v) {
         this.value = v;
      }

      @Override
      void check(String desc) throws CannotCompileException {
         if (!desc.equals("D")) {
            throw new CannotCompileException("type mismatch");
         }
      }

      @Override
      int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
         code.addAload(0);
         code.addLdc2w(this.value);
         code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
         return 3;
      }

      @Override
      int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
         code.addLdc2w(this.value);
         code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
         return 2;
      }

      @Override
      int getConstantValue(ConstPool cp, CtClass type) {
         return type == CtClass.doubleType ? cp.addDoubleInfo(this.value) : 0;
      }
   }

   static class FloatInitializer extends CtField.Initializer {
      float value;

      FloatInitializer(float v) {
         this.value = v;
      }

      @Override
      void check(String desc) throws CannotCompileException {
         if (!desc.equals("F")) {
            throw new CannotCompileException("type mismatch");
         }
      }

      @Override
      int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
         code.addAload(0);
         code.addFconst(this.value);
         code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
         return 3;
      }

      @Override
      int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
         code.addFconst(this.value);
         code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
         return 2;
      }

      @Override
      int getConstantValue(ConstPool cp, CtClass type) {
         return type == CtClass.floatType ? cp.addFloatInfo(this.value) : 0;
      }
   }

   public abstract static class Initializer {
      public static CtField.Initializer constant(int i) {
         return new CtField.IntInitializer(i);
      }

      public static CtField.Initializer constant(boolean b) {
         return new CtField.IntInitializer(b ? 1 : 0);
      }

      public static CtField.Initializer constant(long l) {
         return new CtField.LongInitializer(l);
      }

      public static CtField.Initializer constant(float l) {
         return new CtField.FloatInitializer(l);
      }

      public static CtField.Initializer constant(double d) {
         return new CtField.DoubleInitializer(d);
      }

      public static CtField.Initializer constant(String s) {
         return new CtField.StringInitializer(s);
      }

      public static CtField.Initializer byParameter(int nth) {
         CtField.ParamInitializer i = new CtField.ParamInitializer();
         i.nthParam = nth;
         return i;
      }

      public static CtField.Initializer byNew(CtClass objectType) {
         CtField.NewInitializer i = new CtField.NewInitializer();
         i.objectType = objectType;
         i.stringParams = null;
         i.withConstructorParams = false;
         return i;
      }

      public static CtField.Initializer byNew(CtClass objectType, String[] stringParams) {
         CtField.NewInitializer i = new CtField.NewInitializer();
         i.objectType = objectType;
         i.stringParams = stringParams;
         i.withConstructorParams = false;
         return i;
      }

      public static CtField.Initializer byNewWithParams(CtClass objectType) {
         CtField.NewInitializer i = new CtField.NewInitializer();
         i.objectType = objectType;
         i.stringParams = null;
         i.withConstructorParams = true;
         return i;
      }

      public static CtField.Initializer byNewWithParams(CtClass objectType, String[] stringParams) {
         CtField.NewInitializer i = new CtField.NewInitializer();
         i.objectType = objectType;
         i.stringParams = stringParams;
         i.withConstructorParams = true;
         return i;
      }

      public static CtField.Initializer byCall(CtClass methodClass, String methodName) {
         CtField.MethodInitializer i = new CtField.MethodInitializer();
         i.objectType = methodClass;
         i.methodName = methodName;
         i.stringParams = null;
         i.withConstructorParams = false;
         return i;
      }

      public static CtField.Initializer byCall(CtClass methodClass, String methodName, String[] stringParams) {
         CtField.MethodInitializer i = new CtField.MethodInitializer();
         i.objectType = methodClass;
         i.methodName = methodName;
         i.stringParams = stringParams;
         i.withConstructorParams = false;
         return i;
      }

      public static CtField.Initializer byCallWithParams(CtClass methodClass, String methodName) {
         CtField.MethodInitializer i = new CtField.MethodInitializer();
         i.objectType = methodClass;
         i.methodName = methodName;
         i.stringParams = null;
         i.withConstructorParams = true;
         return i;
      }

      public static CtField.Initializer byCallWithParams(CtClass methodClass, String methodName, String[] stringParams) {
         CtField.MethodInitializer i = new CtField.MethodInitializer();
         i.objectType = methodClass;
         i.methodName = methodName;
         i.stringParams = stringParams;
         i.withConstructorParams = true;
         return i;
      }

      public static CtField.Initializer byNewArray(CtClass type, int size) throws NotFoundException {
         return new CtField.ArrayInitializer(type.getComponentType(), size);
      }

      public static CtField.Initializer byNewArray(CtClass type, int[] sizes) {
         return new CtField.MultiArrayInitializer(type, sizes);
      }

      public static CtField.Initializer byExpr(String source) {
         return new CtField.CodeInitializer(source);
      }

      static CtField.Initializer byExpr(ASTree source) {
         return new CtField.PtreeInitializer(source);
      }

      void check(String desc) throws CannotCompileException {
      }

      abstract int compile(CtClass var1, String var2, Bytecode var3, CtClass[] var4, Javac var5) throws CannotCompileException;

      abstract int compileIfStatic(CtClass var1, String var2, Bytecode var3, Javac var4) throws CannotCompileException;

      int getConstantValue(ConstPool cp, CtClass type) {
         return 0;
      }
   }

   static class IntInitializer extends CtField.Initializer {
      int value;

      IntInitializer(int v) {
         this.value = v;
      }

      @Override
      void check(String desc) throws CannotCompileException {
         char c = desc.charAt(0);
         if (c != 'I' && c != 'S' && c != 'B' && c != 'C' && c != 'Z') {
            throw new CannotCompileException("type mismatch");
         }
      }

      @Override
      int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
         code.addAload(0);
         code.addIconst(this.value);
         code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
         return 2;
      }

      @Override
      int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
         code.addIconst(this.value);
         code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
         return 1;
      }

      @Override
      int getConstantValue(ConstPool cp, CtClass type) {
         return cp.addIntegerInfo(this.value);
      }
   }

   static class LongInitializer extends CtField.Initializer {
      long value;

      LongInitializer(long v) {
         this.value = v;
      }

      @Override
      void check(String desc) throws CannotCompileException {
         if (!desc.equals("J")) {
            throw new CannotCompileException("type mismatch");
         }
      }

      @Override
      int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
         code.addAload(0);
         code.addLdc2w(this.value);
         code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
         return 3;
      }

      @Override
      int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
         code.addLdc2w(this.value);
         code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
         return 2;
      }

      @Override
      int getConstantValue(ConstPool cp, CtClass type) {
         return type == CtClass.longType ? cp.addLongInfo(this.value) : 0;
      }
   }

   static class MethodInitializer extends CtField.NewInitializer {
      String methodName;

      @Override
      int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
         code.addAload(0);
         code.addAload(0);
         int stacksize;
         if (this.stringParams == null) {
            stacksize = 2;
         } else {
            stacksize = this.compileStringParameter(code) + 2;
         }

         if (this.withConstructorParams) {
            stacksize += CtNewWrappedMethod.compileParameterList(code, parameters, 1);
         }

         String typeDesc = Descriptor.of(type);
         String mDesc = this.getDescriptor() + typeDesc;
         code.addInvokestatic(this.objectType, this.methodName, mDesc);
         code.addPutfield(Bytecode.THIS, name, typeDesc);
         return stacksize;
      }

      private String getDescriptor() {
         String desc3 = "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)";
         if (this.stringParams == null) {
            return this.withConstructorParams ? "(Ljava/lang/Object;[Ljava/lang/Object;)" : "(Ljava/lang/Object;)";
         } else {
            return this.withConstructorParams ? "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)" : "(Ljava/lang/Object;[Ljava/lang/String;)";
         }
      }

      @Override
      int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
         int stacksize = 1;
         String desc;
         if (this.stringParams == null) {
            desc = "()";
         } else {
            desc = "([Ljava/lang/String;)";
            stacksize += this.compileStringParameter(code);
         }

         String typeDesc = Descriptor.of(type);
         code.addInvokestatic(this.objectType, this.methodName, desc + typeDesc);
         code.addPutstatic(Bytecode.THIS, name, typeDesc);
         return stacksize;
      }
   }

   static class MultiArrayInitializer extends CtField.Initializer {
      CtClass type;
      int[] dim;

      MultiArrayInitializer(CtClass t, int[] d) {
         this.type = t;
         this.dim = d;
      }

      @Override
      void check(String desc) throws CannotCompileException {
         if (desc.charAt(0) != '[') {
            throw new CannotCompileException("type mismatch");
         }
      }

      @Override
      int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
         code.addAload(0);
         int s = code.addMultiNewarray(type, this.dim);
         code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
         return s + 1;
      }

      @Override
      int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
         int s = code.addMultiNewarray(type, this.dim);
         code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
         return s;
      }
   }

   static class NewInitializer extends CtField.Initializer {
      CtClass objectType;
      String[] stringParams;
      boolean withConstructorParams;

      @Override
      int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
         code.addAload(0);
         code.addNew(this.objectType);
         code.add(89);
         code.addAload(0);
         int stacksize;
         if (this.stringParams == null) {
            stacksize = 4;
         } else {
            stacksize = this.compileStringParameter(code) + 4;
         }

         if (this.withConstructorParams) {
            stacksize += CtNewWrappedMethod.compileParameterList(code, parameters, 1);
         }

         code.addInvokespecial(this.objectType, "<init>", this.getDescriptor());
         code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
         return stacksize;
      }

      private String getDescriptor() {
         String desc3 = "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)V";
         if (this.stringParams == null) {
            return this.withConstructorParams ? "(Ljava/lang/Object;[Ljava/lang/Object;)V" : "(Ljava/lang/Object;)V";
         } else {
            return this.withConstructorParams ? "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)V" : "(Ljava/lang/Object;[Ljava/lang/String;)V";
         }
      }

      @Override
      int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
         code.addNew(this.objectType);
         code.add(89);
         int stacksize = 2;
         String desc;
         if (this.stringParams == null) {
            desc = "()V";
         } else {
            desc = "([Ljava/lang/String;)V";
            stacksize += this.compileStringParameter(code);
         }

         code.addInvokespecial(this.objectType, "<init>", desc);
         code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
         return stacksize;
      }

      protected final int compileStringParameter(Bytecode code) throws CannotCompileException {
         int nparam = this.stringParams.length;
         code.addIconst(nparam);
         code.addAnewarray("java.lang.String");

         for (int j = 0; j < nparam; j++) {
            code.add(89);
            code.addIconst(j);
            code.addLdc(this.stringParams[j]);
            code.add(83);
         }

         return 4;
      }
   }

   static class ParamInitializer extends CtField.Initializer {
      int nthParam;

      @Override
      int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
         if (parameters != null && this.nthParam < parameters.length) {
            code.addAload(0);
            int nth = nthParamToLocal(this.nthParam, parameters, false);
            int s = code.addLoad(nth, type) + 1;
            code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
            return s;
         } else {
            return 0;
         }
      }

      static int nthParamToLocal(int nth, CtClass[] params, boolean isStatic) {
         CtClass longType = CtClass.longType;
         CtClass doubleType = CtClass.doubleType;
         int k;
         if (isStatic) {
            k = 0;
         } else {
            k = 1;
         }

         for (int i = 0; i < nth; i++) {
            CtClass type = params[i];
            if (type != longType && type != doubleType) {
               k++;
            } else {
               k += 2;
            }
         }

         return k;
      }

      @Override
      int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
         return 0;
      }
   }

   static class PtreeInitializer extends CtField.CodeInitializer0 {
      private ASTree expression;

      PtreeInitializer(ASTree expr) {
         this.expression = expr;
      }

      @Override
      void compileExpr(Javac drv) throws CompileError {
         drv.compileExpr(this.expression);
      }

      @Override
      int getConstantValue(ConstPool cp, CtClass type) {
         return this.getConstantValue2(cp, type, this.expression);
      }
   }

   static class StringInitializer extends CtField.Initializer {
      String value;

      StringInitializer(String v) {
         this.value = v;
      }

      @Override
      int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
         code.addAload(0);
         code.addLdc(this.value);
         code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
         return 2;
      }

      @Override
      int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
         code.addLdc(this.value);
         code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
         return 1;
      }

      @Override
      int getConstantValue(ConstPool cp, CtClass type) {
         return type.getName().equals("java.lang.String") ? cp.addStringInfo(this.value) : 0;
      }
   }
}
