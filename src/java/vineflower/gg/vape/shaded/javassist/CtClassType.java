package gg.vape.shaded.javassist;

import gg.vape.shaded.javassist.bytecode.AccessFlag;
import gg.vape.shaded.javassist.bytecode.AnnotationsAttribute;
import gg.vape.shaded.javassist.bytecode.AttributeInfo;
import gg.vape.shaded.javassist.bytecode.BadBytecode;
import gg.vape.shaded.javassist.bytecode.Bytecode;
import gg.vape.shaded.javassist.bytecode.ClassFile;
import gg.vape.shaded.javassist.bytecode.CodeAttribute;
import gg.vape.shaded.javassist.bytecode.CodeIterator;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.ConstantAttribute;
import gg.vape.shaded.javassist.bytecode.Descriptor;
import gg.vape.shaded.javassist.bytecode.EnclosingMethodAttribute;
import gg.vape.shaded.javassist.bytecode.FieldInfo;
import gg.vape.shaded.javassist.bytecode.InnerClassesAttribute;
import gg.vape.shaded.javassist.bytecode.MethodInfo;
import gg.vape.shaded.javassist.bytecode.ParameterAnnotationsAttribute;
import gg.vape.shaded.javassist.bytecode.SignatureAttribute;
import gg.vape.shaded.javassist.bytecode.annotation.Annotation;
import gg.vape.shaded.javassist.bytecode.annotation.AnnotationImpl;
import gg.vape.shaded.javassist.compiler.AccessorMaker;
import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.Javac;
import gg.vape.shaded.javassist.expr.ExprEditor;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

class CtClassType extends CtClass {
   ClassPool classPool;
   boolean wasChanged;
   private boolean wasFrozen;
   boolean wasPruned;
   boolean gcConstPool;
   ClassFile classfile;
   byte[] rawClassfile;
   private Reference<CtMember.Cache> memberCache;
   private AccessorMaker accessors;
   private FieldInitLink fieldInitializers;
   private Map<CtMethod, String> hiddenMethods;
   private int uniqueNumberSeed;
   private boolean doPruning;
   private int getCount;
   private static final int GET_THRESHOLD = 2;

   CtClassType(String name, ClassPool cp) {
      super(name);
      this.doPruning = ClassPool.doPruning;
      this.classPool = cp;
      this.wasChanged = this.wasFrozen = this.wasPruned = this.gcConstPool = false;
      this.classfile = null;
      this.rawClassfile = null;
      this.memberCache = null;
      this.accessors = null;
      this.fieldInitializers = null;
      this.hiddenMethods = null;
      this.uniqueNumberSeed = 0;
      this.getCount = 0;
   }

   CtClassType(InputStream ins, ClassPool cp) throws IOException {
      this((String)null, cp);
      this.classfile = new ClassFile(new DataInputStream(ins));
      this.qualifiedName = this.classfile.getName();
   }

   CtClassType(ClassFile cf, ClassPool cp) {
      this((String)null, cp);
      this.classfile = cf;
      this.qualifiedName = this.classfile.getName();
   }

   @Override
   protected void extendToString(StringBuilder buffer) {
      if (this.wasChanged) {
         buffer.append("changed ");
      }

      if (this.wasFrozen) {
         buffer.append("frozen ");
      }

      if (this.wasPruned) {
         buffer.append("pruned ");
      }

      buffer.append(Modifier.toString(this.getModifiers()));
      buffer.append(" class ");
      buffer.append(this.getName());

      try {
         CtClass ext = this.getSuperclass();
         if (ext != null) {
            String name = ext.getName();
            if (!name.equals("java.lang.Object")) {
               buffer.append(" extends ").append(ext.getName());
            }
         }
      } catch (NotFoundException var4) {
         buffer.append(" extends ??");
      }

      try {
         CtClass[] intf = this.getInterfaces();
         if (intf.length > 0) {
            buffer.append(" implements ");
         }

         for (int i = 0; i < intf.length; i++) {
            buffer.append(intf[i].getName());
            buffer.append(", ");
         }
      } catch (NotFoundException var5) {
         buffer.append(" extends ??");
      }

      CtMember.Cache memCache = this.getMembers();
      this.exToString(buffer, " fields=", memCache.fieldHead(), memCache.lastField());
      this.exToString(buffer, " constructors=", memCache.consHead(), memCache.lastCons());
      this.exToString(buffer, " methods=", memCache.methodHead(), memCache.lastMethod());
   }

   private void exToString(StringBuilder buffer, String msg, CtMember head, CtMember tail) {
      buffer.append(msg);

      while (head != tail) {
         head = head.next();
         buffer.append(head);
         buffer.append(", ");
      }
   }

   @Override
   public AccessorMaker getAccessorMaker() {
      if (this.accessors == null) {
         this.accessors = new AccessorMaker(this);
      }

      return this.accessors;
   }

   @Override
   public ClassFile getClassFile2() {
      return this.getClassFile3(true);
   }

   public ClassFile getClassFile3(boolean doCompress) {
      ClassFile cfile = this.classfile;
      if (cfile != null) {
         return cfile;
      } else {
         if (doCompress) {
            this.classPool.compress();
         }

         byte[] rcfile;
         synchronized (this) {
            cfile = this.classfile;
            if (cfile != null) {
               return cfile;
            }

            rcfile = this.rawClassfile;
         }

         if (rcfile != null) {
            ClassFile cf;
            try {
               cf = new ClassFile(new DataInputStream(new ByteArrayInputStream(rcfile)));
            } catch (IOException var20) {
               throw new RuntimeException(var20.toString(), var20);
            }

            this.getCount = 2;
            synchronized (this) {
               this.rawClassfile = null;
               return this.setClassFile(cf);
            }
         } else {
            InputStream fin = null;

            ClassFile var6;
            try {
               fin = this.classPool.openClassfile(this.getName());
               if (fin == null) {
                  throw new NotFoundException(this.getName());
               }

               fin = new BufferedInputStream(fin);
               ClassFile cf = new ClassFile(new DataInputStream(fin));
               if (!cf.getName().equals(this.qualifiedName)) {
                  throw new RuntimeException(
                     "cannot find " + this.qualifiedName + ": " + cf.getName() + " found in " + this.qualifiedName.replace('.', '/') + ".class"
                  );
               }

               var6 = this.setClassFile(cf);
            } catch (NotFoundException var21) {
               throw new RuntimeException(var21.toString(), var21);
            } catch (IOException var22) {
               throw new RuntimeException(var22.toString(), var22);
            } finally {
               if (fin != null) {
                  try {
                     fin.close();
                  } catch (IOException var18) {
                  }
               }
            }

            return var6;
         }
      }
   }

   @Override
   final void incGetCounter() {
      this.getCount++;
   }

   @Override
   void compress() {
      if (this.getCount < 2) {
         if (!this.isModified() && ClassPool.releaseUnmodifiedClassFile) {
            this.removeClassFile();
         } else if (this.isFrozen() && !this.wasPruned) {
            this.saveClassFile();
         }
      }

      this.getCount = 0;
   }

   private synchronized void saveClassFile() {
      if (this.classfile != null && this.hasMemberCache() == null) {
         ByteArrayOutputStream barray = new ByteArrayOutputStream();
         DataOutputStream out = new DataOutputStream(barray);

         try {
            this.classfile.write(out);
            barray.close();
            this.rawClassfile = barray.toByteArray();
            this.classfile = null;
         } catch (IOException var4) {
         }
      }
   }

   private synchronized void removeClassFile() {
      if (this.classfile != null && !this.isModified() && this.hasMemberCache() == null) {
         this.classfile = null;
      }
   }

   private synchronized ClassFile setClassFile(ClassFile cf) {
      if (this.classfile == null) {
         this.classfile = cf;
      }

      return this.classfile;
   }

   @Override
   public ClassPool getClassPool() {
      return this.classPool;
   }

   void setClassPool(ClassPool cp) {
      this.classPool = cp;
   }

   @Override
   public URL getURL() throws NotFoundException {
      URL url = this.classPool.find(this.getName());
      if (url == null) {
         throw new NotFoundException(this.getName());
      } else {
         return url;
      }
   }

   @Override
   public boolean isModified() {
      return this.wasChanged;
   }

   @Override
   public boolean isFrozen() {
      return this.wasFrozen;
   }

   @Override
   public void freeze() {
      this.wasFrozen = true;
   }

   @Override
   void checkModify() throws RuntimeException {
      if (this.isFrozen()) {
         String msg = this.getName() + " class is frozen";
         if (this.wasPruned) {
            msg = msg + " and pruned";
         }

         throw new RuntimeException(msg);
      } else {
         this.wasChanged = true;
      }
   }

   @Override
   public void defrost() {
      this.checkPruned("defrost");
      this.wasFrozen = false;
   }

   @Override
   public boolean subtypeOf(CtClass clazz) throws NotFoundException {
      String cname = clazz.getName();
      if (this != clazz && !this.getName().equals(cname)) {
         ClassFile file = this.getClassFile2();
         String supername = file.getSuperclass();
         if (supername != null && supername.equals(cname)) {
            return true;
         } else {
            String[] ifs = file.getInterfaces();
            int num = ifs.length;

            for (int i = 0; i < num; i++) {
               if (ifs[i].equals(cname)) {
                  return true;
               }
            }

            if (supername != null && this.classPool.get(supername).subtypeOf(clazz)) {
               return true;
            } else {
               for (int var8 = 0; var8 < num; var8++) {
                  if (this.classPool.get(ifs[var8]).subtypeOf(clazz)) {
                     return true;
                  }
               }

               return false;
            }
         }
      } else {
         return true;
      }
   }

   @Override
   public void setName(String name) throws RuntimeException {
      String oldname = this.getName();
      if (!name.equals(oldname)) {
         this.classPool.checkNotFrozen(name);
         ClassFile cf = this.getClassFile2();
         super.setName(name);
         cf.setName(name);
         this.nameReplaced();
         this.classPool.classNameChanged(oldname, this);
      }
   }

   @Override
   public String getGenericSignature() {
      SignatureAttribute sa = (SignatureAttribute)this.getClassFile2().getAttribute("Signature");
      return sa == null ? null : sa.getSignature();
   }

   @Override
   public void setGenericSignature(String sig) {
      ClassFile cf = this.getClassFile();
      SignatureAttribute sa = new SignatureAttribute(cf.getConstPool(), sig);
      cf.addAttribute(sa);
   }

   @Override
   public void replaceClassName(ClassMap classnames) throws RuntimeException {
      String oldClassName = this.getName();
      String newClassName = classnames.get(Descriptor.toJvmName(oldClassName));
      if (newClassName != null) {
         newClassName = Descriptor.toJavaName(newClassName);
         this.classPool.checkNotFrozen(newClassName);
      }

      super.replaceClassName(classnames);
      ClassFile cf = this.getClassFile2();
      cf.renameClass(classnames);
      this.nameReplaced();
      if (newClassName != null) {
         super.setName(newClassName);
         this.classPool.classNameChanged(oldClassName, this);
      }
   }

   @Override
   public void replaceClassName(String oldname, String newname) throws RuntimeException {
      String thisname = this.getName();
      if (thisname.equals(oldname)) {
         this.setName(newname);
      } else {
         super.replaceClassName(oldname, newname);
         this.getClassFile2().renameClass(oldname, newname);
         this.nameReplaced();
      }
   }

   @Override
   public boolean isInterface() {
      return Modifier.isInterface(this.getModifiers());
   }

   @Override
   public boolean isAnnotation() {
      return Modifier.isAnnotation(this.getModifiers());
   }

   @Override
   public boolean isEnum() {
      return Modifier.isEnum(this.getModifiers());
   }

   @Override
   public int getModifiers() {
      ClassFile cf = this.getClassFile2();
      int acc = cf.getAccessFlags();
      acc = AccessFlag.clear(acc, 32);
      int inner = cf.getInnerAccessFlags();
      if (inner != -1) {
         if ((inner & 8) != 0) {
            acc |= 8;
         }

         if ((inner & 1) != 0) {
            acc |= 1;
         } else {
            acc &= -2;
            if ((inner & 4) != 0) {
               acc |= 4;
            } else if ((inner & 2) != 0) {
               acc |= 2;
            }
         }
      }

      return AccessFlag.toModifier(acc);
   }

   @Override
   public CtClass[] getNestedClasses() throws NotFoundException {
      ClassFile cf = this.getClassFile2();
      InnerClassesAttribute ica = (InnerClassesAttribute)cf.getAttribute("InnerClasses");
      if (ica == null) {
         return new CtClass[0];
      } else {
         String thisName = cf.getName() + "$";
         int n = ica.tableLength();
         List<CtClass> list = new ArrayList<>(n);

         for (int i = 0; i < n; i++) {
            String name = ica.innerClass(i);
            if (name != null && name.startsWith(thisName) && name.lastIndexOf(36) < thisName.length()) {
               list.add(this.classPool.get(name));
            }
         }

         return list.toArray(new CtClass[list.size()]);
      }
   }

   @Override
   public void setModifiers(int mod) {
      this.checkModify();
      updateInnerEntry(mod, this.getName(), this, true);
      ClassFile cf = this.getClassFile2();
      cf.setAccessFlags(AccessFlag.of(mod & -9));
   }

   private static void updateInnerEntry(int newMod, String name, CtClass clazz, boolean outer) {
      ClassFile cf = clazz.getClassFile2();
      InnerClassesAttribute ica = (InnerClassesAttribute)cf.getAttribute("InnerClasses");
      if (ica != null) {
         int mod = newMod & -9;
         int i = ica.find(name);
         if (i >= 0) {
            int isStatic = ica.accessFlags(i) & 8;
            if (isStatic != 0 || !Modifier.isStatic(newMod)) {
               clazz.checkModify();
               ica.setAccessFlags(i, AccessFlag.of(mod) | isStatic);
               String outName = ica.outerClass(i);
               if (outName != null && outer) {
                  try {
                     CtClass parent = clazz.getClassPool().get(outName);
                     updateInnerEntry(mod, name, parent, false);
                  } catch (NotFoundException var11) {
                     throw new RuntimeException("cannot find the declaring class: " + outName);
                  }
               }

               return;
            }
         }
      }

      if (Modifier.isStatic(newMod)) {
         throw new RuntimeException("cannot change " + Descriptor.toJavaName(name) + " into a static class");
      }
   }

   @Override
   public boolean hasAnnotation(String annotationName) {
      ClassFile cf = this.getClassFile2();
      AnnotationsAttribute ainfo = (AnnotationsAttribute)cf.getAttribute("RuntimeInvisibleAnnotations");
      AnnotationsAttribute ainfo2 = (AnnotationsAttribute)cf.getAttribute("RuntimeVisibleAnnotations");
      return hasAnnotationType(annotationName, this.getClassPool(), ainfo, ainfo2);
   }

   @Deprecated
   static boolean hasAnnotationType(Class<?> clz, ClassPool cp, AnnotationsAttribute a1, AnnotationsAttribute a2) {
      return hasAnnotationType(clz.getName(), cp, a1, a2);
   }

   static boolean hasAnnotationType(String annotationTypeName, ClassPool cp, AnnotationsAttribute a1, AnnotationsAttribute a2) {
      Annotation[] anno1;
      if (a1 == null) {
         anno1 = null;
      } else {
         anno1 = a1.getAnnotations();
      }

      Annotation[] anno2;
      if (a2 == null) {
         anno2 = null;
      } else {
         anno2 = a2.getAnnotations();
      }

      if (anno1 != null) {
         for (int i = 0; i < anno1.length; i++) {
            if (anno1[i].getTypeName().equals(annotationTypeName)) {
               return true;
            }
         }
      }

      if (anno2 != null) {
         for (int ix = 0; ix < anno2.length; ix++) {
            if (anno2[ix].getTypeName().equals(annotationTypeName)) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public Object getAnnotation(Class<?> clz) throws ClassNotFoundException {
      ClassFile cf = this.getClassFile2();
      AnnotationsAttribute ainfo = (AnnotationsAttribute)cf.getAttribute("RuntimeInvisibleAnnotations");
      AnnotationsAttribute ainfo2 = (AnnotationsAttribute)cf.getAttribute("RuntimeVisibleAnnotations");
      return getAnnotationType(clz, this.getClassPool(), ainfo, ainfo2);
   }

   static Object getAnnotationType(Class<?> clz, ClassPool cp, AnnotationsAttribute a1, AnnotationsAttribute a2) throws ClassNotFoundException {
      Annotation[] anno1;
      if (a1 == null) {
         anno1 = null;
      } else {
         anno1 = a1.getAnnotations();
      }

      Annotation[] anno2;
      if (a2 == null) {
         anno2 = null;
      } else {
         anno2 = a2.getAnnotations();
      }

      String typeName = clz.getName();
      if (anno1 != null) {
         for (int i = 0; i < anno1.length; i++) {
            if (anno1[i].getTypeName().equals(typeName)) {
               return toAnnoType(anno1[i], cp);
            }
         }
      }

      if (anno2 != null) {
         for (int ix = 0; ix < anno2.length; ix++) {
            if (anno2[ix].getTypeName().equals(typeName)) {
               return toAnnoType(anno2[ix], cp);
            }
         }
      }

      return null;
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
         throw new RuntimeException("Unexpected exception ", var2);
      }
   }

   private Object[] getAnnotations(boolean ignoreNotFound) throws ClassNotFoundException {
      ClassFile cf = this.getClassFile2();
      AnnotationsAttribute ainfo = (AnnotationsAttribute)cf.getAttribute("RuntimeInvisibleAnnotations");
      AnnotationsAttribute ainfo2 = (AnnotationsAttribute)cf.getAttribute("RuntimeVisibleAnnotations");
      return toAnnotationType(ignoreNotFound, this.getClassPool(), ainfo, ainfo2);
   }

   static Object[] toAnnotationType(boolean ignoreNotFound, ClassPool cp, AnnotationsAttribute a1, AnnotationsAttribute a2) throws ClassNotFoundException {
      Annotation[] anno1;
      int size1;
      if (a1 == null) {
         anno1 = null;
         size1 = 0;
      } else {
         anno1 = a1.getAnnotations();
         size1 = anno1.length;
      }

      Annotation[] anno2;
      int size2;
      if (a2 == null) {
         anno2 = null;
         size2 = 0;
      } else {
         anno2 = a2.getAnnotations();
         size2 = anno2.length;
      }

      if (!ignoreNotFound) {
         Object[] result = new Object[size1 + size2];

         for (int i = 0; i < size1; i++) {
            result[i] = toAnnoType(anno1[i], cp);
         }

         for (int j = 0; j < size2; j++) {
            result[j + size1] = toAnnoType(anno2[j], cp);
         }

         return result;
      } else {
         List<Object> annotations = new ArrayList<>();

         for (int i = 0; i < size1; i++) {
            try {
               annotations.add(toAnnoType(anno1[i], cp));
            } catch (ClassNotFoundException var12) {
            }
         }

         for (int j = 0; j < size2; j++) {
            try {
               annotations.add(toAnnoType(anno2[j], cp));
            } catch (ClassNotFoundException var11) {
            }
         }

         return annotations.toArray();
      }
   }

   static Object[][] toAnnotationType(
      boolean ignoreNotFound, ClassPool cp, ParameterAnnotationsAttribute a1, ParameterAnnotationsAttribute a2, MethodInfo minfo
   ) throws ClassNotFoundException {
      int numParameters = 0;
      if (a1 != null) {
         numParameters = a1.numParameters();
      } else if (a2 != null) {
         numParameters = a2.numParameters();
      } else {
         numParameters = Descriptor.numOfParameters(minfo.getDescriptor());
      }

      Object[][] result = new Object[numParameters][];

      for (int i = 0; i < numParameters; i++) {
         Annotation[] anno1;
         int size1;
         if (a1 == null) {
            anno1 = null;
            size1 = 0;
         } else {
            anno1 = a1.getAnnotations()[i];
            size1 = anno1.length;
         }

         Annotation[] anno2;
         int size2;
         if (a2 == null) {
            anno2 = null;
            size2 = 0;
         } else {
            anno2 = a2.getAnnotations()[i];
            size2 = anno2.length;
         }

         if (!ignoreNotFound) {
            result[i] = new Object[size1 + size2];

            for (int j = 0; j < size1; j++) {
               result[i][j] = toAnnoType(anno1[j], cp);
            }

            for (int j = 0; j < size2; j++) {
               result[i][j + size1] = toAnnoType(anno2[j], cp);
            }
         } else {
            List<Object> annotations = new ArrayList<>();

            for (int j = 0; j < size1; j++) {
               try {
                  annotations.add(toAnnoType(anno1[j], cp));
               } catch (ClassNotFoundException var16) {
               }
            }

            for (int j = 0; j < size2; j++) {
               try {
                  annotations.add(toAnnoType(anno2[j], cp));
               } catch (ClassNotFoundException var15) {
               }
            }

            result[i] = annotations.toArray();
         }
      }

      return result;
   }

   private static Object toAnnoType(Annotation anno, ClassPool cp) throws ClassNotFoundException {
      try {
         ClassLoader cl = cp.getClassLoader();
         return anno.toAnnotationType(cl, cp);
      } catch (ClassNotFoundException var8) {
         ClassLoader cl2 = cp.getClass().getClassLoader();

         try {
            return anno.toAnnotationType(cl2, cp);
         } catch (ClassNotFoundException var7) {
            try {
               Class<?> clazz = cp.get(anno.getTypeName()).toClass();
               return AnnotationImpl.make(clazz.getClassLoader(), clazz, cp, anno);
            } catch (Throwable var6) {
               throw new ClassNotFoundException(anno.getTypeName());
            }
         }
      }
   }

   @Override
   public boolean subclassOf(CtClass superclass) {
      if (superclass == null) {
         return false;
      } else {
         String superName = superclass.getName();
         CtClass curr = this;

         try {
            while (curr != null) {
               if (curr.getName().equals(superName)) {
                  return true;
               }

               curr = curr.getSuperclass();
            }
         } catch (Exception var5) {
         }

         return false;
      }
   }

   @Override
   public CtClass getSuperclass() throws NotFoundException {
      String supername = this.getClassFile2().getSuperclass();
      return supername == null ? null : this.classPool.get(supername);
   }

   @Override
   public void setSuperclass(CtClass clazz) throws CannotCompileException {
      this.checkModify();
      if (this.isInterface()) {
         this.addInterface(clazz);
      } else {
         this.getClassFile2().setSuperclass(clazz.getName());
      }
   }

   @Override
   public CtClass[] getInterfaces() throws NotFoundException {
      String[] ifs = this.getClassFile2().getInterfaces();
      int num = ifs.length;
      CtClass[] ifc = new CtClass[num];

      for (int i = 0; i < num; i++) {
         ifc[i] = this.classPool.get(ifs[i]);
      }

      return ifc;
   }

   @Override
   public void setInterfaces(CtClass[] list) {
      this.checkModify();
      String[] ifs;
      if (list == null) {
         ifs = new String[0];
      } else {
         int num = list.length;
         ifs = new String[num];

         for (int i = 0; i < num; i++) {
            ifs[i] = list[i].getName();
         }
      }

      this.getClassFile2().setInterfaces(ifs);
   }

   @Override
   public void addInterface(CtClass anInterface) {
      this.checkModify();
      if (anInterface != null) {
         this.getClassFile2().addInterface(anInterface.getName());
      }
   }

   @Override
   public CtClass getDeclaringClass() throws NotFoundException {
      ClassFile cf = this.getClassFile2();
      InnerClassesAttribute ica = (InnerClassesAttribute)cf.getAttribute("InnerClasses");
      if (ica == null) {
         return null;
      } else {
         String name = this.getName();
         int n = ica.tableLength();

         for (int i = 0; i < n; i++) {
            if (name.equals(ica.innerClass(i))) {
               String outName = ica.outerClass(i);
               if (outName != null) {
                  return this.classPool.get(outName);
               }

               EnclosingMethodAttribute ema = (EnclosingMethodAttribute)cf.getAttribute("EnclosingMethod");
               if (ema != null) {
                  return this.classPool.get(ema.className());
               }
            }
         }

         return null;
      }
   }

   @Override
   public CtBehavior getEnclosingBehavior() throws NotFoundException {
      ClassFile cf = this.getClassFile2();
      EnclosingMethodAttribute ema = (EnclosingMethodAttribute)cf.getAttribute("EnclosingMethod");
      if (ema == null) {
         return null;
      } else {
         CtClass enc = this.classPool.get(ema.className());
         String name = ema.methodName();
         if ("<init>".equals(name)) {
            return enc.getConstructor(ema.methodDescriptor());
         } else {
            return (CtBehavior)("<clinit>".equals(name) ? enc.getClassInitializer() : enc.getMethod(name, ema.methodDescriptor()));
         }
      }
   }

   @Override
   public CtClass makeNestedClass(String name, boolean isStatic) {
      if (!isStatic) {
         throw new RuntimeException("sorry, only nested static class is supported");
      } else {
         this.checkModify();
         CtClass c = this.classPool.makeNestedClass(this.getName() + "$" + name);
         ClassFile cf = this.getClassFile2();
         ClassFile cf2 = c.getClassFile2();
         InnerClassesAttribute ica = (InnerClassesAttribute)cf.getAttribute("InnerClasses");
         if (ica == null) {
            ica = new InnerClassesAttribute(cf.getConstPool());
            cf.addAttribute(ica);
         }

         ica.append(c.getName(), this.getName(), name, cf2.getAccessFlags() & -33 | 8);
         cf2.addAttribute(ica.copy(cf2.getConstPool(), null));
         return c;
      }
   }

   private void nameReplaced() {
      CtMember.Cache cache = this.hasMemberCache();
      if (cache != null) {
         CtMember mth = cache.methodHead();
         CtMember tail = cache.lastMethod();

         while (mth != tail) {
            mth = mth.next();
            mth.nameReplaced();
         }
      }
   }

   protected CtMember.Cache hasMemberCache() {
      return this.memberCache != null ? this.memberCache.get() : null;
   }

   protected synchronized CtMember.Cache getMembers() {
      CtMember.Cache cache = null;
      if (this.memberCache == null || (cache = this.memberCache.get()) == null) {
         cache = new CtMember.Cache(this);
         this.makeFieldCache(cache);
         this.makeBehaviorCache(cache);
         this.memberCache = new WeakReference<>(cache);
      }

      return cache;
   }

   private void makeFieldCache(CtMember.Cache cache) {
      for (FieldInfo finfo : this.getClassFile3(false).getFields()) {
         cache.addField(new CtField(finfo, this));
      }
   }

   private void makeBehaviorCache(CtMember.Cache cache) {
      for (MethodInfo minfo : this.getClassFile3(false).getMethods()) {
         if (minfo.isMethod()) {
            cache.addMethod(new CtMethod(minfo, this));
         } else {
            cache.addConstructor(new CtConstructor(minfo, this));
         }
      }
   }

   @Override
   public CtField[] getFields() {
      List<CtMember> alist = new ArrayList<>();
      getFields(alist, this);
      return alist.toArray(new CtField[alist.size()]);
   }

   private static void getFields(List<CtMember> alist, CtClass cc) {
      if (cc != null) {
         try {
            getFields(alist, cc.getSuperclass());
         } catch (NotFoundException var7) {
         }

         try {
            CtClass[] ifs = cc.getInterfaces();

            for (CtClass ctc : ifs) {
               getFields(alist, ctc);
            }
         } catch (NotFoundException var8) {
         }

         CtMember.Cache memCache = ((CtClassType)cc).getMembers();
         CtMember field = memCache.fieldHead();
         CtMember tail = memCache.lastField();

         while (field != tail) {
            field = field.next();
            if (!Modifier.isPrivate(field.getModifiers())) {
               alist.add(field);
            }
         }
      }
   }

   @Override
   public CtField getField(String name, String desc) throws NotFoundException {
      CtField f = this.getField2(name, desc);
      return this.checkGetField(f, name, desc);
   }

   private CtField checkGetField(CtField f, String name, String desc) throws NotFoundException {
      if (f == null) {
         String msg = "field: " + name;
         if (desc != null) {
            msg = msg + " type " + desc;
         }

         throw new NotFoundException(msg + " in " + this.getName());
      } else {
         return f;
      }
   }

   @Override
   CtField getField2(String name, String desc) {
      CtField df = this.getDeclaredField2(name, desc);
      if (df != null) {
         return df;
      } else {
         try {
            CtClass[] ifs = this.getInterfaces();

            for (CtClass ctc : ifs) {
               CtField f = ctc.getField2(name, desc);
               if (f != null) {
                  return f;
               }
            }

            CtClass s = this.getSuperclass();
            if (s != null) {
               return s.getField2(name, desc);
            }
         } catch (NotFoundException var10) {
         }

         return null;
      }
   }

   @Override
   public CtField[] getDeclaredFields() {
      CtMember.Cache memCache = this.getMembers();
      CtMember field = memCache.fieldHead();
      CtMember tail = memCache.lastField();
      int num = CtMember.Cache.count(field, tail);
      CtField[] cfs = new CtField[num];

      for (int i = 0; field != tail; cfs[i++] = (CtField)field) {
         field = field.next();
      }

      return cfs;
   }

   @Override
   public CtField getDeclaredField(String name) throws NotFoundException {
      return this.getDeclaredField(name, null);
   }

   @Override
   public CtField getDeclaredField(String name, String desc) throws NotFoundException {
      CtField f = this.getDeclaredField2(name, desc);
      return this.checkGetField(f, name, desc);
   }

   private CtField getDeclaredField2(String name, String desc) {
      CtMember.Cache memCache = this.getMembers();
      CtMember field = memCache.fieldHead();
      CtMember tail = memCache.lastField();

      while (field != tail) {
         field = field.next();
         if (field.getName().equals(name) && (desc == null || desc.equals(field.getSignature()))) {
            return (CtField)field;
         }
      }

      return null;
   }

   @Override
   public CtBehavior[] getDeclaredBehaviors() {
      CtMember.Cache memCache = this.getMembers();
      CtMember cons = memCache.consHead();
      CtMember consTail = memCache.lastCons();
      int cnum = CtMember.Cache.count(cons, consTail);
      CtMember mth = memCache.methodHead();
      CtMember mthTail = memCache.lastMethod();
      int mnum = CtMember.Cache.count(mth, mthTail);
      CtBehavior[] cb = new CtBehavior[cnum + mnum];

      int i;
      for (i = 0; cons != consTail; cb[i++] = (CtBehavior)cons) {
         cons = cons.next();
      }

      while (mth != mthTail) {
         mth = mth.next();
         cb[i++] = (CtBehavior)mth;
      }

      return cb;
   }

   @Override
   public CtConstructor[] getConstructors() {
      CtMember.Cache memCache = this.getMembers();
      CtMember cons = memCache.consHead();
      CtMember consTail = memCache.lastCons();
      int n = 0;
      CtMember mem = cons;

      while (mem != consTail) {
         mem = mem.next();
         if (isPubCons((CtConstructor)mem)) {
            n++;
         }
      }

      CtConstructor[] result = new CtConstructor[n];
      int i = 0;
      mem = cons;

      while (mem != consTail) {
         mem = mem.next();
         CtConstructor cc = (CtConstructor)mem;
         if (isPubCons(cc)) {
            result[i++] = cc;
         }
      }

      return result;
   }

   private static boolean isPubCons(CtConstructor cons) {
      return !Modifier.isPrivate(cons.getModifiers()) && cons.isConstructor();
   }

   @Override
   public CtConstructor getConstructor(String desc) throws NotFoundException {
      CtMember.Cache memCache = this.getMembers();
      CtMember cons = memCache.consHead();
      CtMember consTail = memCache.lastCons();

      while (cons != consTail) {
         cons = cons.next();
         CtConstructor cc = (CtConstructor)cons;
         if (cc.getMethodInfo2().getDescriptor().equals(desc) && cc.isConstructor()) {
            return cc;
         }
      }

      return super.getConstructor(desc);
   }

   @Override
   public CtConstructor[] getDeclaredConstructors() {
      CtMember.Cache memCache = this.getMembers();
      CtMember cons = memCache.consHead();
      CtMember consTail = memCache.lastCons();
      int n = 0;
      CtMember mem = cons;

      while (mem != consTail) {
         mem = mem.next();
         CtConstructor cc = (CtConstructor)mem;
         if (cc.isConstructor()) {
            n++;
         }
      }

      CtConstructor[] result = new CtConstructor[n];
      int i = 0;
      mem = cons;

      while (mem != consTail) {
         mem = mem.next();
         CtConstructor cc = (CtConstructor)mem;
         if (cc.isConstructor()) {
            result[i++] = cc;
         }
      }

      return result;
   }

   @Override
   public CtConstructor getClassInitializer() {
      CtMember.Cache memCache = this.getMembers();
      CtMember cons = memCache.consHead();
      CtMember consTail = memCache.lastCons();

      while (cons != consTail) {
         cons = cons.next();
         CtConstructor cc = (CtConstructor)cons;
         if (cc.isClassInitializer()) {
            return cc;
         }
      }

      return null;
   }

   @Override
   public CtMethod[] getMethods() {
      Map<String, CtMember> h = new HashMap<>();
      getMethods0(h, this);
      return h.values().toArray(new CtMethod[h.size()]);
   }

   private static void getMethods0(Map<String, CtMember> h, CtClass cc) {
      try {
         CtClass[] ifs = cc.getInterfaces();

         for (CtClass ctc : ifs) {
            getMethods0(h, ctc);
         }
      } catch (NotFoundException var8) {
      }

      try {
         CtClass s = cc.getSuperclass();
         if (s != null) {
            getMethods0(h, s);
         }
      } catch (NotFoundException var7) {
      }

      if (cc instanceof CtClassType) {
         CtMember.Cache memCache = ((CtClassType)cc).getMembers();
         CtMember mth = memCache.methodHead();
         CtMember mthTail = memCache.lastMethod();

         while (mth != mthTail) {
            mth = mth.next();
            if (!Modifier.isPrivate(mth.getModifiers())) {
               h.put(((CtMethod)mth).getStringRep(), mth);
            }
         }
      }
   }

   @Override
   public CtMethod getMethod(String name, String desc) throws NotFoundException {
      CtMethod m = getMethod0(this, name, desc);
      if (m != null) {
         return m;
      } else {
         throw new NotFoundException(name + "(..) is not found in " + this.getName());
      }
   }

   private static CtMethod getMethod0(CtClass cc, String name, String desc) {
      if (cc instanceof CtClassType) {
         CtMember.Cache memCache = ((CtClassType)cc).getMembers();
         CtMember mth = memCache.methodHead();
         CtMember mthTail = memCache.lastMethod();

         while (mth != mthTail) {
            mth = mth.next();
            if (mth.getName().equals(name) && ((CtMethod)mth).getMethodInfo2().getDescriptor().equals(desc)) {
               return (CtMethod)mth;
            }
         }
      }

      try {
         CtClass s = cc.getSuperclass();
         if (s != null) {
            CtMethod m = getMethod0(s, name, desc);
            if (m != null) {
               return m;
            }
         }
      } catch (NotFoundException var9) {
      }

      try {
         CtClass[] ifs = cc.getInterfaces();

         for (CtClass ctc : ifs) {
            CtMethod m = getMethod0(ctc, name, desc);
            if (m != null) {
               return m;
            }
         }
      } catch (NotFoundException var10) {
      }

      return null;
   }

   @Override
   public CtMethod[] getDeclaredMethods() {
      CtMember.Cache memCache = this.getMembers();
      CtMember mth = memCache.methodHead();
      CtMember mthTail = memCache.lastMethod();
      List<CtMember> methods = new ArrayList<>();

      while (mth != mthTail) {
         mth = mth.next();
         methods.add(mth);
      }

      return methods.toArray(new CtMethod[methods.size()]);
   }

   @Override
   public CtMethod[] getDeclaredMethods(String name) throws NotFoundException {
      CtMember.Cache memCache = this.getMembers();
      CtMember mth = memCache.methodHead();
      CtMember mthTail = memCache.lastMethod();
      List<CtMember> methods = new ArrayList<>();

      while (mth != mthTail) {
         mth = mth.next();
         if (mth.getName().equals(name)) {
            methods.add(mth);
         }
      }

      return methods.toArray(new CtMethod[methods.size()]);
   }

   @Override
   public CtMethod getDeclaredMethod(String name) throws NotFoundException {
      CtMember.Cache memCache = this.getMembers();
      CtMember mth = memCache.methodHead();
      CtMember mthTail = memCache.lastMethod();

      while (mth != mthTail) {
         mth = mth.next();
         if (mth.getName().equals(name)) {
            return (CtMethod)mth;
         }
      }

      throw new NotFoundException(name + "(..) is not found in " + this.getName());
   }

   @Override
   public CtMethod getDeclaredMethod(String name, CtClass[] params) throws NotFoundException {
      String desc = Descriptor.ofParameters(params);
      CtMember.Cache memCache = this.getMembers();
      CtMember mth = memCache.methodHead();
      CtMember mthTail = memCache.lastMethod();

      while (mth != mthTail) {
         mth = mth.next();
         if (mth.getName().equals(name) && ((CtMethod)mth).getMethodInfo2().getDescriptor().startsWith(desc)) {
            return (CtMethod)mth;
         }
      }

      throw new NotFoundException(name + "(..) is not found in " + this.getName());
   }

   @Override
   public void addField(CtField f, String init) throws CannotCompileException {
      this.addField(f, CtField.Initializer.byExpr(init));
   }

   @Override
   public void addField(CtField f, CtField.Initializer init) throws CannotCompileException {
      this.checkModify();
      if (f.getDeclaringClass() != this) {
         throw new CannotCompileException("cannot add");
      } else {
         if (init == null) {
            init = f.getInit();
         }

         if (init != null) {
            init.check(f.getSignature());
            int mod = f.getModifiers();
            if (Modifier.isStatic(mod) && Modifier.isFinal(mod)) {
               try {
                  ConstPool cp = this.getClassFile2().getConstPool();
                  int index = init.getConstantValue(cp, f.getType());
                  if (index != 0) {
                     f.getFieldInfo2().addAttribute(new ConstantAttribute(cp, index));
                     init = null;
                  }
               } catch (NotFoundException var6) {
               }
            }
         }

         this.getMembers().addField(f);
         this.getClassFile2().addField(f.getFieldInfo2());
         if (init != null) {
            FieldInitLink fil = new FieldInitLink(f, init);
            FieldInitLink link = this.fieldInitializers;
            if (link == null) {
               this.fieldInitializers = fil;
            } else {
               while (link.next != null) {
                  link = link.next;
               }

               link.next = fil;
            }
         }
      }
   }

   @Override
   public void removeField(CtField f) throws NotFoundException {
      this.checkModify();
      FieldInfo fi = f.getFieldInfo2();
      ClassFile cf = this.getClassFile2();
      if (cf.getFields().remove(fi)) {
         this.getMembers().remove(f);
         this.gcConstPool = true;
      } else {
         throw new NotFoundException(f.toString());
      }
   }

   @Override
   public CtConstructor makeClassInitializer() throws CannotCompileException {
      CtConstructor clinit = this.getClassInitializer();
      if (clinit != null) {
         return clinit;
      } else {
         this.checkModify();
         ClassFile cf = this.getClassFile2();
         Bytecode code = new Bytecode(cf.getConstPool(), 0, 0);
         this.modifyClassConstructor(cf, code, 0, 0);
         return this.getClassInitializer();
      }
   }

   @Override
   public void addConstructor(CtConstructor c) throws CannotCompileException {
      this.checkModify();
      if (c.getDeclaringClass() != this) {
         throw new CannotCompileException("cannot add");
      } else {
         this.getMembers().addConstructor(c);
         this.getClassFile2().addMethod(c.getMethodInfo2());
      }
   }

   @Override
   public void removeConstructor(CtConstructor m) throws NotFoundException {
      this.checkModify();
      MethodInfo mi = m.getMethodInfo2();
      ClassFile cf = this.getClassFile2();
      if (cf.getMethods().remove(mi)) {
         this.getMembers().remove(m);
         this.gcConstPool = true;
      } else {
         throw new NotFoundException(m.toString());
      }
   }

   @Override
   public void addMethod(CtMethod m) throws CannotCompileException {
      this.checkModify();
      if (m.getDeclaringClass() != this) {
         throw new CannotCompileException("bad declaring class");
      } else {
         int mod = m.getModifiers();
         if ((this.getModifiers() & 512) != 0) {
            if (Modifier.isProtected(mod) || Modifier.isPrivate(mod)) {
               throw new CannotCompileException("an interface method must be public: " + m.toString());
            }

            m.setModifiers(mod | 1);
         }

         this.getMembers().addMethod(m);
         this.getClassFile2().addMethod(m.getMethodInfo2());
         if ((mod & 1024) != 0) {
            this.setModifiers(this.getModifiers() | 1024);
         }
      }
   }

   @Override
   public void removeMethod(CtMethod m) throws NotFoundException {
      this.checkModify();
      MethodInfo mi = m.getMethodInfo2();
      ClassFile cf = this.getClassFile2();
      if (cf.getMethods().remove(mi)) {
         this.getMembers().remove(m);
         this.gcConstPool = true;
      } else {
         throw new NotFoundException(m.toString());
      }
   }

   @Override
   public byte[] getAttribute(String name) {
      AttributeInfo ai = this.getClassFile2().getAttribute(name);
      return ai == null ? null : ai.get();
   }

   @Override
   public void setAttribute(String name, byte[] data) {
      this.checkModify();
      ClassFile cf = this.getClassFile2();
      cf.addAttribute(new AttributeInfo(cf.getConstPool(), name, data));
   }

   @Override
   public void instrument(CodeConverter converter) throws CannotCompileException {
      this.checkModify();
      ClassFile cf = this.getClassFile2();
      ConstPool cp = cf.getConstPool();
      List<MethodInfo> methods = cf.getMethods();

      for (MethodInfo minfo : methods.toArray(new MethodInfo[methods.size()])) {
         converter.doit(this, minfo, cp);
      }
   }

   @Override
   public void instrument(ExprEditor editor) throws CannotCompileException {
      this.checkModify();
      ClassFile cf = this.getClassFile2();
      List<MethodInfo> methods = cf.getMethods();

      for (MethodInfo minfo : methods.toArray(new MethodInfo[methods.size()])) {
         editor.doit(this, minfo);
      }
   }

   @Override
   public void prune() {
      if (!this.wasPruned) {
         this.wasPruned = this.wasFrozen = true;
         this.getClassFile2().prune();
      }
   }

   @Override
   public void rebuildClassFile() {
      this.gcConstPool = true;
   }

   @Override
   public void toBytecode(DataOutputStream out) throws CannotCompileException, IOException {
      try {
         if (this.isModified()) {
            this.checkPruned("toBytecode");
            ClassFile cf = this.getClassFile2();
            if (this.gcConstPool) {
               cf.compact();
               this.gcConstPool = false;
            }

            this.modifyClassConstructor(cf);
            this.modifyConstructors(cf);
            if (debugDump != null) {
               this.dumpClassFile(cf);
            }

            cf.write(out);
            out.flush();
            this.fieldInitializers = null;
            if (this.doPruning) {
               cf.prune();
               this.wasPruned = true;
            }
         } else {
            this.classPool.writeClassfile(this.getName(), out);
         }

         this.getCount = 0;
         this.wasFrozen = true;
      } catch (NotFoundException var3) {
         throw new CannotCompileException(var3);
      } catch (IOException var4) {
         throw new CannotCompileException(var4);
      }
   }

   private void dumpClassFile(ClassFile cf) throws IOException {
      DataOutputStream dump = this.makeFileOutput(debugDump);

      try {
         cf.write(dump);
      } finally {
         dump.close();
      }
   }

   private void checkPruned(String method) {
      if (this.wasPruned) {
         throw new RuntimeException(method + "(): " + this.getName() + " was pruned.");
      }
   }

   @Override
   public boolean stopPruning(boolean stop) {
      boolean prev = !this.doPruning;
      this.doPruning = !stop;
      return prev;
   }

   private void modifyClassConstructor(ClassFile cf) throws CannotCompileException, NotFoundException {
      if (this.fieldInitializers != null) {
         Bytecode code = new Bytecode(cf.getConstPool(), 0, 0);
         Javac jv = new Javac(code, this);
         int stacksize = 0;
         boolean doInit = false;

         for (FieldInitLink fi = this.fieldInitializers; fi != null; fi = fi.next) {
            CtField f = fi.field;
            if (Modifier.isStatic(f.getModifiers())) {
               doInit = true;
               int s = fi.init.compileIfStatic(f.getType(), f.getName(), code, jv);
               if (stacksize < s) {
                  stacksize = s;
               }
            }
         }

         if (doInit) {
            this.modifyClassConstructor(cf, code, stacksize, 0);
         }
      }
   }

   private void modifyClassConstructor(ClassFile cf, Bytecode code, int stacksize, int localsize) throws CannotCompileException {
      MethodInfo m = cf.getStaticInitializer();
      if (m == null) {
         code.add(177);
         code.setMaxStack(stacksize);
         code.setMaxLocals(localsize);
         m = new MethodInfo(cf.getConstPool(), "<clinit>", "()V");
         m.setAccessFlags(8);
         m.setCodeAttribute(code.toCodeAttribute());
         cf.addMethod(m);
         CtMember.Cache cache = this.hasMemberCache();
         if (cache != null) {
            cache.addConstructor(new CtConstructor(m, this));
         }
      } else {
         CodeAttribute codeAttr = m.getCodeAttribute();
         if (codeAttr == null) {
            throw new CannotCompileException("empty <clinit>");
         }

         try {
            CodeIterator it = codeAttr.iterator();
            int pos = it.insertEx(code.get());
            it.insert(code.getExceptionTable(), pos);
            int maxstack = codeAttr.getMaxStack();
            if (maxstack < stacksize) {
               codeAttr.setMaxStack(stacksize);
            }

            int maxlocals = codeAttr.getMaxLocals();
            if (maxlocals < localsize) {
               codeAttr.setMaxLocals(localsize);
            }
         } catch (BadBytecode var12) {
            throw new CannotCompileException(var12);
         }
      }

      try {
         m.rebuildStackMapIf6(this.classPool, cf);
      } catch (BadBytecode var11) {
         throw new CannotCompileException(var11);
      }
   }

   private void modifyConstructors(ClassFile cf) throws CannotCompileException, NotFoundException {
      if (this.fieldInitializers != null) {
         ConstPool cp = cf.getConstPool();

         for (MethodInfo minfo : cf.getMethods()) {
            if (minfo.isConstructor()) {
               CodeAttribute codeAttr = minfo.getCodeAttribute();
               if (codeAttr != null) {
                  try {
                     Bytecode init = new Bytecode(cp, 0, codeAttr.getMaxLocals());
                     CtClass[] params = Descriptor.getParameterTypes(minfo.getDescriptor(), this.classPool);
                     int stacksize = this.makeFieldInitializer(init, params);
                     insertAuxInitializer(codeAttr, init, stacksize);
                     minfo.rebuildStackMapIf6(this.classPool, cf);
                  } catch (BadBytecode var10) {
                     throw new CannotCompileException(var10);
                  }
               }
            }
         }
      }
   }

   private static void insertAuxInitializer(CodeAttribute codeAttr, Bytecode initializer, int stacksize) throws BadBytecode {
      CodeIterator it = codeAttr.iterator();
      int index = it.skipSuperConstructor();
      if (index < 0) {
         index = it.skipThisConstructor();
         if (index >= 0) {
            return;
         }
      }

      int pos = it.insertEx(initializer.get());
      it.insert(initializer.getExceptionTable(), pos);
      int maxstack = codeAttr.getMaxStack();
      if (maxstack < stacksize) {
         codeAttr.setMaxStack(stacksize);
      }
   }

   private int makeFieldInitializer(Bytecode code, CtClass[] parameters) throws CannotCompileException, NotFoundException {
      int stacksize = 0;
      Javac jv = new Javac(code, this);

      try {
         jv.recordParams(parameters, false);
      } catch (CompileError var8) {
         throw new CannotCompileException(var8);
      }

      for (FieldInitLink fi = this.fieldInitializers; fi != null; fi = fi.next) {
         CtField f = fi.field;
         if (!Modifier.isStatic(f.getModifiers())) {
            int s = fi.init.compile(f.getType(), f.getName(), code, parameters, jv);
            if (stacksize < s) {
               stacksize = s;
            }
         }
      }

      return stacksize;
   }

   Map<CtMethod, String> getHiddenMethods() {
      if (this.hiddenMethods == null) {
         this.hiddenMethods = new Hashtable<>();
      }

      return this.hiddenMethods;
   }

   int getUniqueNumber() {
      return this.uniqueNumberSeed++;
   }

   @Override
   public String makeUniqueName(String prefix) {
      Map<Object, CtClassType> table = new HashMap<>();
      this.makeMemberList(table);
      Set<Object> keys = table.keySet();
      String[] methods = new String[keys.size()];
      keys.toArray(methods);
      if (notFindInArray(prefix, methods)) {
         return prefix;
      } else {
         int i = 100;

         while (i <= 999) {
            String name = prefix + i++;
            if (notFindInArray(name, methods)) {
               return name;
            }
         }

         throw new RuntimeException("too many unique name");
      }
   }

   private static boolean notFindInArray(String prefix, String[] values) {
      int len = values.length;

      for (int i = 0; i < len; i++) {
         if (values[i].startsWith(prefix)) {
            return false;
         }
      }

      return true;
   }

   private void makeMemberList(Map<Object, CtClassType> table) {
      int mod = this.getModifiers();
      if (Modifier.isAbstract(mod) || Modifier.isInterface(mod)) {
         try {
            CtClass[] ifs = this.getInterfaces();

            for (CtClass ic : ifs) {
               if (ic != null && ic instanceof CtClassType) {
                  ((CtClassType)ic).makeMemberList(table);
               }
            }
         } catch (NotFoundException var9) {
         }
      }

      try {
         CtClass s = this.getSuperclass();
         if (s != null && s instanceof CtClassType) {
            ((CtClassType)s).makeMemberList(table);
         }
      } catch (NotFoundException var8) {
      }

      for (MethodInfo minfo : this.getClassFile2().getMethods()) {
         table.put(minfo.getName(), this);
      }

      for (FieldInfo finfo : this.getClassFile2().getFields()) {
         table.put(finfo.getName(), this);
      }
   }
}
