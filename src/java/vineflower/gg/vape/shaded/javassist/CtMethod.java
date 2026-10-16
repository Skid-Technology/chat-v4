package gg.vape.shaded.javassist;

import gg.vape.shaded.javassist.bytecode.BadBytecode;
import gg.vape.shaded.javassist.bytecode.Bytecode;
import gg.vape.shaded.javassist.bytecode.CodeAttribute;
import gg.vape.shaded.javassist.bytecode.CodeIterator;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.Descriptor;
import gg.vape.shaded.javassist.bytecode.MethodInfo;

public final class CtMethod extends CtBehavior {
   protected String cachedStringRep = null;

   CtMethod(MethodInfo minfo, CtClass declaring) {
      super(declaring, minfo);
   }

   public CtMethod(CtClass returnType, String mname, CtClass[] parameters, CtClass declaring) {
      this(null, declaring);
      ConstPool cp = declaring.getClassFile2().getConstPool();
      String desc = Descriptor.ofMethod(returnType, parameters);
      this.methodInfo = new MethodInfo(cp, mname, desc);
      this.setModifiers(1025);
   }

   public CtMethod(CtMethod src, CtClass declaring, ClassMap map) throws CannotCompileException {
      this(null, declaring);
      this.copy(src, false, map);
   }

   public static CtMethod make(String src, CtClass declaring) throws CannotCompileException {
      return CtNewMethod.make(src, declaring);
   }

   public static CtMethod make(MethodInfo minfo, CtClass declaring) throws CannotCompileException {
      if (declaring.getClassFile2().getConstPool() != minfo.getConstPool()) {
         throw new CannotCompileException("bad declaring class");
      } else {
         return new CtMethod(minfo, declaring);
      }
   }

   @Override
   public int hashCode() {
      return this.getStringRep().hashCode();
   }

   @Override
   void nameReplaced() {
      this.cachedStringRep = null;
   }

   final String getStringRep() {
      if (this.cachedStringRep == null) {
         this.cachedStringRep = this.methodInfo.getName() + Descriptor.getParamDescriptor(this.methodInfo.getDescriptor());
      }

      return this.cachedStringRep;
   }

   @Override
   public boolean equals(Object obj) {
      return obj != null && obj instanceof CtMethod && ((CtMethod)obj).getStringRep().equals(this.getStringRep());
   }

   @Override
   public String getLongName() {
      return this.getDeclaringClass().getName() + "." + this.getName() + Descriptor.toString(this.getSignature());
   }

   @Override
   public String getName() {
      return this.methodInfo.getName();
   }

   public void setName(String newname) {
      this.declaringClass.checkModify();
      this.methodInfo.setName(newname);
   }

   public CtClass getReturnType() throws NotFoundException {
      return this.getReturnType0();
   }

   @Override
   public boolean isEmpty() {
      CodeAttribute ca = this.getMethodInfo2().getCodeAttribute();
      if (ca == null) {
         return (this.getModifiers() & 1024) != 0;
      } else {
         CodeIterator it = ca.iterator();

         try {
            return it.hasNext() && it.byteAt(it.next()) == 177 && !it.hasNext();
         } catch (BadBytecode var4) {
            return false;
         }
      }
   }

   public void setBody(CtMethod src, ClassMap map) throws CannotCompileException {
      setBody0(src.declaringClass, src.methodInfo, this.declaringClass, this.methodInfo, map);
   }

   public void setWrappedBody(CtMethod mbody, CtMethod.ConstParameter constParam) throws CannotCompileException {
      this.declaringClass.checkModify();
      CtClass clazz = this.getDeclaringClass();

      CtClass[] params;
      CtClass retType;
      try {
         params = this.getParameterTypes();
         retType = this.getReturnType();
      } catch (NotFoundException var8) {
         throw new CannotCompileException(var8);
      }

      Bytecode code = CtNewWrappedMethod.makeBody(clazz, clazz.getClassFile2(), mbody, params, retType, constParam);
      CodeAttribute cattr = code.toCodeAttribute();
      this.methodInfo.setCodeAttribute(cattr);
      this.methodInfo.setAccessFlags(this.methodInfo.getAccessFlags() & -1025);
   }

   public static class ConstParameter {
      public static CtMethod.ConstParameter integer(int i) {
         return new CtMethod.IntConstParameter(i);
      }

      public static CtMethod.ConstParameter integer(long i) {
         return new CtMethod.LongConstParameter(i);
      }

      public static CtMethod.ConstParameter string(String s) {
         return new CtMethod.StringConstParameter(s);
      }

      ConstParameter() {
      }

      int compile(Bytecode code) throws CannotCompileException {
         return 0;
      }

      String descriptor() {
         return defaultDescriptor();
      }

      static String defaultDescriptor() {
         return "([Ljava/lang/Object;)Ljava/lang/Object;";
      }

      String constDescriptor() {
         return defaultConstDescriptor();
      }

      static String defaultConstDescriptor() {
         return "([Ljava/lang/Object;)V";
      }
   }

   static class IntConstParameter extends CtMethod.ConstParameter {
      int param;

      IntConstParameter(int i) {
         this.param = i;
      }

      @Override
      int compile(Bytecode code) throws CannotCompileException {
         code.addIconst(this.param);
         return 1;
      }

      @Override
      String descriptor() {
         return "([Ljava/lang/Object;I)Ljava/lang/Object;";
      }

      @Override
      String constDescriptor() {
         return "([Ljava/lang/Object;I)V";
      }
   }

   static class LongConstParameter extends CtMethod.ConstParameter {
      long param;

      LongConstParameter(long l) {
         this.param = l;
      }

      @Override
      int compile(Bytecode code) throws CannotCompileException {
         code.addLconst(this.param);
         return 2;
      }

      @Override
      String descriptor() {
         return "([Ljava/lang/Object;J)Ljava/lang/Object;";
      }

      @Override
      String constDescriptor() {
         return "([Ljava/lang/Object;J)V";
      }
   }

   static class StringConstParameter extends CtMethod.ConstParameter {
      String param;

      StringConstParameter(String s) {
         this.param = s;
      }

      @Override
      int compile(Bytecode code) throws CannotCompileException {
         code.addLdc(this.param);
         return 1;
      }

      @Override
      String descriptor() {
         return "([Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;";
      }

      @Override
      String constDescriptor() {
         return "([Ljava/lang/Object;Ljava/lang/String;)V";
      }
   }
}
