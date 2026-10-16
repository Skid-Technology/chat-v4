package gg.vape.shaded.javassist;

import gg.vape.shaded.javassist.bytecode.BadBytecode;
import gg.vape.shaded.javassist.bytecode.CodeAttribute;
import gg.vape.shaded.javassist.bytecode.CodeIterator;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.MethodInfo;
import gg.vape.shaded.javassist.convert.TransformAccessArrayField;
import gg.vape.shaded.javassist.convert.TransformAfter;
import gg.vape.shaded.javassist.convert.TransformBefore;
import gg.vape.shaded.javassist.convert.TransformCall;
import gg.vape.shaded.javassist.convert.TransformCallToStatic;
import gg.vape.shaded.javassist.convert.TransformFieldAccess;
import gg.vape.shaded.javassist.convert.TransformNew;
import gg.vape.shaded.javassist.convert.TransformNewClass;
import gg.vape.shaded.javassist.convert.TransformReadField;
import gg.vape.shaded.javassist.convert.TransformWriteField;
import gg.vape.shaded.javassist.convert.Transformer;

public class CodeConverter {
   protected Transformer transformers = null;

   public void replaceNew(CtClass newClass, CtClass calledClass, String calledMethod) {
      this.transformers = new TransformNew(this.transformers, newClass.getName(), calledClass.getName(), calledMethod);
   }

   public void replaceNew(CtClass oldClass, CtClass newClass) {
      this.transformers = new TransformNewClass(this.transformers, oldClass.getName(), newClass.getName());
   }

   public void redirectFieldAccess(CtField field, CtClass newClass, String newFieldname) {
      this.transformers = new TransformFieldAccess(this.transformers, field, newClass.getName(), newFieldname);
   }

   public void replaceFieldRead(CtField field, CtClass calledClass, String calledMethod) {
      this.transformers = new TransformReadField(this.transformers, field, calledClass.getName(), calledMethod);
   }

   public void replaceFieldWrite(CtField field, CtClass calledClass, String calledMethod) {
      this.transformers = new TransformWriteField(this.transformers, field, calledClass.getName(), calledMethod);
   }

   public void replaceArrayAccess(CtClass calledClass, CodeConverter.ArrayAccessReplacementMethodNames names) throws NotFoundException {
      this.transformers = new TransformAccessArrayField(this.transformers, calledClass.getName(), names);
   }

   public void redirectMethodCall(CtMethod origMethod, CtMethod substMethod) throws CannotCompileException {
      String d1 = origMethod.getMethodInfo2().getDescriptor();
      String d2 = substMethod.getMethodInfo2().getDescriptor();
      if (!d1.equals(d2)) {
         throw new CannotCompileException("signature mismatch: " + substMethod.getLongName());
      } else {
         int mod1 = origMethod.getModifiers();
         int mod2 = substMethod.getModifiers();
         if (Modifier.isStatic(mod1) == Modifier.isStatic(mod2)
            && (!Modifier.isPrivate(mod1) || Modifier.isPrivate(mod2))
            && origMethod.getDeclaringClass().isInterface() == substMethod.getDeclaringClass().isInterface()) {
            this.transformers = new TransformCall(this.transformers, origMethod, substMethod);
         } else {
            throw new CannotCompileException("invoke-type mismatch " + substMethod.getLongName());
         }
      }
   }

   public void redirectMethodCall(String oldMethodName, CtMethod newMethod) throws CannotCompileException {
      this.transformers = new TransformCall(this.transformers, oldMethodName, newMethod);
   }

   public void redirectMethodCallToStatic(CtMethod origMethod, CtMethod staticMethod) {
      this.transformers = new TransformCallToStatic(this.transformers, origMethod, staticMethod);
   }

   public void insertBeforeMethod(CtMethod origMethod, CtMethod beforeMethod) throws CannotCompileException {
      try {
         this.transformers = new TransformBefore(this.transformers, origMethod, beforeMethod);
      } catch (NotFoundException var4) {
         throw new CannotCompileException(var4);
      }
   }

   public void insertAfterMethod(CtMethod origMethod, CtMethod afterMethod) throws CannotCompileException {
      try {
         this.transformers = new TransformAfter(this.transformers, origMethod, afterMethod);
      } catch (NotFoundException var4) {
         throw new CannotCompileException(var4);
      }
   }

   protected void doit(CtClass clazz, MethodInfo minfo, ConstPool cp) throws CannotCompileException {
      CodeAttribute codeAttr = minfo.getCodeAttribute();
      if (codeAttr != null && this.transformers != null) {
         for (Transformer t = this.transformers; t != null; t = t.getNext()) {
            t.initialize(cp, clazz, minfo);
         }

         CodeIterator iterator = codeAttr.iterator();

         while (iterator.hasNext()) {
            try {
               int pos = iterator.next();

               for (Transformer var12 = this.transformers; var12 != null; var12 = var12.getNext()) {
                  pos = var12.transform(clazz, pos, iterator, cp);
               }
            } catch (BadBytecode var11) {
               throw new CannotCompileException(var11);
            }
         }

         int locals = 0;
         int stack = 0;

         for (Transformer var13 = this.transformers; var13 != null; var13 = var13.getNext()) {
            int s = var13.extraLocals();
            if (s > locals) {
               locals = s;
            }

            s = var13.extraStack();
            if (s > stack) {
               stack = s;
            }
         }

         for (Transformer var14 = this.transformers; var14 != null; var14 = var14.getNext()) {
            var14.clean();
         }

         if (locals > 0) {
            codeAttr.setMaxLocals(codeAttr.getMaxLocals() + locals);
         }

         if (stack > 0) {
            codeAttr.setMaxStack(codeAttr.getMaxStack() + stack);
         }

         try {
            minfo.rebuildStackMapIf6(clazz.getClassPool(), clazz.getClassFile2());
         } catch (BadBytecode var10) {
            throw new CannotCompileException(var10.getMessage(), var10);
         }
      }
   }

   public interface ArrayAccessReplacementMethodNames {
      String byteOrBooleanRead();

      String byteOrBooleanWrite();

      String charRead();

      String charWrite();

      String doubleRead();

      String doubleWrite();

      String floatRead();

      String floatWrite();

      String intRead();

      String intWrite();

      String longRead();

      String longWrite();

      String objectRead();

      String objectWrite();

      String shortRead();

      String shortWrite();
   }

   public static class DefaultArrayAccessReplacementMethodNames implements CodeConverter.ArrayAccessReplacementMethodNames {
      @Override
      public String byteOrBooleanRead() {
         return "arrayReadByteOrBoolean";
      }

      @Override
      public String byteOrBooleanWrite() {
         return "arrayWriteByteOrBoolean";
      }

      @Override
      public String charRead() {
         return "arrayReadChar";
      }

      @Override
      public String charWrite() {
         return "arrayWriteChar";
      }

      @Override
      public String doubleRead() {
         return "arrayReadDouble";
      }

      @Override
      public String doubleWrite() {
         return "arrayWriteDouble";
      }

      @Override
      public String floatRead() {
         return "arrayReadFloat";
      }

      @Override
      public String floatWrite() {
         return "arrayWriteFloat";
      }

      @Override
      public String intRead() {
         return "arrayReadInt";
      }

      @Override
      public String intWrite() {
         return "arrayWriteInt";
      }

      @Override
      public String longRead() {
         return "arrayReadLong";
      }

      @Override
      public String longWrite() {
         return "arrayWriteLong";
      }

      @Override
      public String objectRead() {
         return "arrayReadObject";
      }

      @Override
      public String objectWrite() {
         return "arrayWriteObject";
      }

      @Override
      public String shortRead() {
         return "arrayReadShort";
      }

      @Override
      public String shortWrite() {
         return "arrayWriteShort";
      }
   }
}
