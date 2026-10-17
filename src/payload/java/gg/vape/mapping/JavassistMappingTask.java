package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.asm.transform.ClassTransformer;
import gg.vape.event.EventBus;
import gg.vape.event.IEvent;
import gg.vape.mapping.expr.MethodCallInjectionExprEditor;
import gg.vape.mapping.expr.MethodCallLineCaptureExprEditor;
import gg.vape.mapping.expr.MethodCallReplacementExprEditor;
import gg.vape.runtime.ClassBytecodeCache;
import javassist.CannotCompileException;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.compiler.CompileError;
import javassist.expr.MethodCall;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.LaunchClassLoader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class JavassistMappingTask implements MappingTask {
   private CtClass Y;
   private static int T;
   protected Class E;
   static Set<Class> a = new HashSet<>();
   private boolean k;
   private static final MappingClassBytecodeResolver V = new MappingClassBytecodeResolver();
   static int m = 0;
   private boolean i;
   protected byte[] t;
   static ClassPool e = new BytecodeClassPool(V);
   protected byte[] B;

   public void H(MappingMethod mappingMethod, InjectionParameterSpec... injectionParameterSpecArray) {
      try {
         CtBehavior ctBehavior = this.F(mappingMethod);

         for (InjectionParameterSpec injectionParameterSpec : injectionParameterSpecArray) {
            String string = "v_param" + injectionParameterSpec.parameterIndex;
            ctBehavior.addLocalVariable(string, this.i(injectionParameterSpec.parameterType));
            ctBehavior.insertBefore("{ " + string + " = $" + injectionParameterSpec.parameterIndex + "; }");
         }
      } catch (Exception var9) {
         Vape.logThrowable(var9);
      }
   }

   public String c(MappingMethod mappingMethod, Class clazz, String string) {
      return this.H(this.F(mappingMethod), clazz, string, "", "");
   }

   public String z(Class<?> clazz, String string, String string2, String string3, String string4) {
      String string5 = "";
      string5 = string5 + clazz.getName() + " " + string + " = new " + clazz.getName() + "(" + string2 + ");\n";
      string5 = string5 + "if(" + string + "." + EventBus.getFireMethod(clazz).getName() + "()) { return " + string3 + "; } " + string4;
      int n = ClassTransformer.opaquePredicate();
      if (n == 0) {
         IEvent.class.isAssignableFrom(clazz);
         GuiComponent.setLegacyComponentState(new GuiComponent[4]);
         return string5;
      } else {
         boolean bl5 = IEvent.class.isAssignableFrom(clazz);
         boolean bl4 = bl5 && InsertedCallbackEventMarker.class.isAssignableFrom(clazz);
         if (bl4) {
            Method method = EventBus.findEventListenersAccessor((Class<? extends IEvent>)clazz);
            string5 = "if(" + clazz.getName() + "#" + method.getName() + "()." + EventBus.getHasListenersMethod().getName() + "()) { " + string5 + "}";
         }

         return "{ " + string5 + " }";
      }
   }

   public String I(MappingMethod mappingMethod, Class clazz, String string, String string2, String string3) {
      return this.m(this.F(mappingMethod), clazz, string, string2, string3);
   }

   @Override
   public abstract void transform();

   public static int O$src$I$5vfrz4() {
      return T;
   }

   public String m(CtBehavior ctBehavior, Class clazz, String string, String string2, String string3) {
      return this.P(ctBehavior, clazz, string, false, string2, string3);
   }

   @Override
   public void rollback() {
      byte[] byArray = this.t;
      Class clazz = this.E;
      int n = ClassBytecodeCache.setClassBytecode(clazz, byArray);
      int n2 = ClassTransformer.opaquePredicate();
      if (GuiComponent.getLegacyComponentState() == null) {
         ClassTransformer.setTransformerState(++n2);
      }
   }

   public static void q(ClassPath classPath, Class clazz) {
      a.add(clazz);
      e.insertClassPath(classPath);
   }

   private static Exception b(Exception exception) {
      return exception;
   }

   public void Y(String string) {
      int n = ClassTransformer.getTransformerState();

      try {
         File file = new File(string);
         boolean bl = file.getParentFile().exists();
         if (n == 0 && !bl) {
            bl = file.getParentFile().mkdirs();
         }

         FileOutputStream fileOutputStream = new FileOutputStream(file);
         fileOutputStream.write(this.B);
         fileOutputStream.close();
      } catch (Exception var6) {
         Vape.logThrowable(var6);
      }
   }

   private void dumpFailedCommit(int errorCode) {
      try {
         File directory = new File(System.getProperty("java.io.tmpdir"), "VapeChat");
         if (!directory.exists()) {
            directory.mkdirs();
         }

         String className = this.E.getName().replace('.', '_');
         File originalFile = new File(directory, className + "-error-" + errorCode + "-original.class");
         File transformedFile = new File(directory, className + "-error-" + errorCode + "-transformed.class");
         FileOutputStream originalOutput = new FileOutputStream(originalFile);
         originalOutput.write(this.t);
         originalOutput.close();
         FileOutputStream transformedOutput = new FileOutputStream(transformedFile);
         transformedOutput.write(this.B);
         transformedOutput.close();
         Vape.debugLog("Failed mapping commit bytecode dumped to " + directory.getAbsolutePath());
      } catch (Exception var8) {
         Vape.logThrowable(var8);
      }
   }

   public String k(MappingMethod mappingMethod, Class clazz, String string) {
      return this.m(this.F(mappingMethod), clazz, string, "", "");
   }

   private <T> T J(MappingTaskCallable<T> mappingTaskCallable, Throwable throwable) {
      String string = throwable.getMessage();
      if (string.endsWith(": failed to resolve types")) {
         return this.J(mappingTaskCallable, this.h(throwable));
      } else {
         String[] stringArray = string.split(" ");
         String string2 = stringArray[stringArray.length - 1];
         return this.v(mappingTaskCallable, string2);
      }
   }

   public CtBehavior F(MappingMethod mappingMethod) {
      try {
         return (CtBehavior)(mappingMethod.getOriginalName().equals("<init>")
            ? this.Y.getConstructor(mappingMethod.getDescriptor())
            : this.Y.getMethod(mappingMethod.getResolvedName(), mappingMethod.getDescriptor()));
      } catch (NotFoundException var3) {
         Vape.logThrowable(var3);
         return null;
      }
   }

   public static void p(Class clazz) {
      V.O(clazz);
      if (!Vape.INSTANCE.isForgeAbsent()) {
         ClassBytecodeCache.getClassBytecode(clazz, true);
         String string = clazz.getName();
         LaunchClassLoader launchClassLoader = LaunchClassLoader.getLaunchClassLoader();
         if (launchClassLoader.supportsLegacyClassCache()) {
            Set set = launchClassLoader.getClassLoaderExceptions();
            set.remove(string);
            launchClassLoader.cachedClasses().put(string, clazz);
         }

         if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            p(clazz.getSuperclass());
         }
      }
   }

   public static void X(int n) {
      T = n;
   }

   public String q(MappingMethod mappingMethod, Class clazz, String string, String string2, String string3) {
      return this.H(this.F(mappingMethod), clazz, string, string2, string3);
   }

   @Override
   public Class getTargetClass() {
      return this.E;
   }

   public CtClass x() {
      try {
         this.Y = e.makeClass(new ByteArrayInputStream(this.t), false);
         return this.Y;
      } catch (IOException var2) {
         Vape.logThrowable(var2);
         return null;
      }
   }

   public void registerEventInjection(EventInjectionSpec eventInjectionSpec) {
      this.J(() -> this.insertEventInjectionCode(eventInjectionSpec));
   }

   public JavassistMappingTask(Class clazz) {
      this.E = clazz;
   }

   public String j(MappingMethod mappingMethod, Class clazz, String string, String string2) {
      return this.m(this.F(mappingMethod), clazz, string, string2, "");
   }

   private Throwable h(Throwable throwable) {
      Throwable throwable2 = throwable.getCause();
      return throwable2 == null ? throwable : this.h(throwable2);
   }

   public void E(MappingMethod mappingMethod) {
      try {
         CtClass ctClass = ClassPool.getDefault().get("java.lang.Exception");
         this.F(mappingMethod).addCatch("{ return; }", ctClass);
      } catch (Exception var3) {
         Vape.logThrowable(var3);
      }
   }

   public void k(MappingMethod mappingMethod, MappingMethod mappingMethod2, String string, int n) {
      try {
         CtBehavior ctBehavior = this.F(mappingMethod);
         AtomicInteger atomicInteger = new AtomicInteger();
         ctBehavior.instrument(new MethodCallLineCaptureExprEditor(this, mappingMethod2, atomicInteger));
         ctBehavior.insertAt(atomicInteger.get() + n, string);
      } catch (Exception var7) {
         Vape.logThrowable(var7);
      }
   }

   protected static boolean c(MethodCall methodCall, MappingMethod mappingMethod) {
      return methodCall.getMethodName().equals(mappingMethod.getResolvedName())
         && methodCall.getSignature().equals(mappingMethod.getDescriptor())
         && methodCall.getClassName().equals(mappingMethod.getOwnerClass().getName());
   }

   public static int U() {
      int n = O$src$I$5vfrz4();
      return 0;
   }

   private String lambda$makeAndInsertEvent$0(Class clazz, String string, CtBehavior ctBehavior, String string2, String string3, boolean bl) throws CannotCompileException, NotFoundException {
      p(clazz);
      String string4 = "";
      string4 = "event" + ++m;
      String string5 = string.replace("$event", string4);
      ctBehavior.getDeclaringClass().defrost();
      ctBehavior.addLocalVariable(string4, this.i(clazz));
      String string10 = this.z(clazz, string4, string2, string3, string5);
      if (bl) {
         ctBehavior.insertBefore(string10);
      } else {
         ctBehavior.insertAfter(string10);
      }

      this.Y.defrost();
      return string4;
   }

   public String O(MappingMethod mappingMethod, Class clazz, String string, String string2) {
      return this.H(this.F(mappingMethod), clazz, string, string2, "");
   }

   @Override
   public void prepare() {
      if (this.E == null) {
         throw new IllegalStateException("Mapping task target class is null: " + this.getClass().getName());
      } else {
         this.t = ClassBytecodeCache.getClassBytecode(this.E, true);
         if (this.t != null && this.t.length != 0) {
            V.O(this.E.getClassLoader());
            if (!Vape.INSTANCE.isForgeAbsent()) {
               LaunchClassLoader launchClassLoader = LaunchClassLoader.getLaunchClassLoader();
               if (launchClassLoader.supportsLegacyClassCache()) {
                  launchClassLoader.cachedClasses().put(this.E.getName(), this.E);
               }
            }

            if (this.x() == null) {
               throw new IllegalStateException("Javassist could not parse bytecode for " + this.E.getName());
            } else {
               this.Y.defrost();
               q(new LoaderClassPath(this.E.getClassLoader()), this.E);
            }
         } else {
            throw new IllegalStateException("Could not capture bytecode for " + this.E.getName());
         }
      }
   }

   public void k(
      MappingMethod mappingMethod, MappingMethod mappingMethod2, String string, boolean bl, boolean bl2, InjectionParameterSpec... injectionParameterSpecArray
   ) {
      try {
         CtBehavior ctBehavior = this.F(mappingMethod);
         StringBuilder stringBuilder = new StringBuilder();
         if (bl2) {
            stringBuilder.append("if(");
         }

         stringBuilder.append(string + "(");

         for (int i = 0; i < injectionParameterSpecArray.length; i++) {
            String string2 = "v_param" + injectionParameterSpecArray[i].parameterIndex;
            if (i < injectionParameterSpecArray.length - 1) {
               stringBuilder.append(string2 + ", ");
            } else {
               stringBuilder.append(string2);
            }
         }

         if (bl2) {
            stringBuilder.append(")) { return; } ");
         } else {
            stringBuilder.append(");");
         }

         String string3 = stringBuilder.toString();
         String string2 = bl ? "{" + string3 + " $_ = $proceed($$);}" : "{$_ = $proceed($$);" + string3 + "}";
         ctBehavior.instrument(new MethodCallInjectionExprEditor(this, mappingMethod2, string2));
      } catch (Exception var11) {
         Vape.logThrowable(var11);
      }
   }

   public <T> T J(MappingTaskCallable<T> mappingTaskCallable) {
      return this.x(mappingTaskCallable, false);
   }

   @Override
   public int commit() {
      int n = ClassTransformer.opaquePredicate();
      if (n != 0) {
         if (this.i) {
            return -2;
         } else if (this.B == null) {
            return 0;
         } else {
            Class clazz = this.E;
            byte[] byArray = this.B;
            int n3 = ClassBytecodeCache.setClassBytecode(clazz, byArray);
            if (n3 != 0) {
               this.dumpFailedCommit(n3);
            }

            if (n3 == 0) {
               this.k = true;
            }

            return n3;
         }
      } else {
         Class clazzx = this.E;
         byte[] byArrayx = this.B;
         int n4 = ClassBytecodeCache.setClassBytecode(clazzx, byArrayx);
         if (n4 != 0) {
            this.dumpFailedCommit(n4);
         }

         return n4;
      }
   }

   @Override
   public void serialize() {
      try {
         this.B = this.Y.toBytecode();
      } catch (Exception var2) {
         Vape.logThrowable(var2);
      }
   }

   private <T> T x(MappingTaskCallable<T> mappingTaskCallable, boolean bl) {
      try {
         return mappingTaskCallable.c();
      } catch (CannotCompileException var5) {
         if (bl) {
            this.i = true;
            throw new RuntimeException(var5);
         } else {
            Throwable throwable = this.h(var5);
            if (!(throwable instanceof CompileError) && !(throwable instanceof NotFoundException)) {
               this.i = true;
               return null;
            } else {
               return this.J(mappingTaskCallable, var5);
            }
         }
      } catch (NotFoundException var6) {
         return bl ? null : this.J(mappingTaskCallable, var6);
      }
   }

   public void T(MappingMethod mappingMethod, MappingMethod mappingMethod2, String string, boolean bl, InjectionParameterSpec[] injectionParameterSpecArray) {
      try {
         CtBehavior ctBehavior = this.F(mappingMethod);
         StringBuilder stringBuilder = new StringBuilder();
         stringBuilder.append(string + "(");

         for (InjectionParameterSpec injectionParameterSpec : injectionParameterSpecArray) {
            String string2 = "v_param" + injectionParameterSpec.parameterIndex;
            stringBuilder.append(string2 + ", ");
         }

         stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
         stringBuilder.append(", $$");
         stringBuilder.append(");");
         String string3 = stringBuilder.toString();
         String string4 = bl ? "{" + string3 + " $_ = $proceed($$);}" : "{$_ = $proceed($$);" + string3 + "}";
         ctBehavior.instrument(new MethodCallReplacementExprEditor(this, mappingMethod2, string4));
      } catch (Exception var13) {
         Vape.logThrowable(var13);
      }
   }

   public CtClass i(Class clazz) throws NotFoundException {
      return e.get(clazz.getName());
   }

   public void l(MappingMethod mappingMethod) {
      CtBehavior ctBehavior = this.F(mappingMethod);

      try {
         ctBehavior.insertBefore("System.out.println(\"test\");");
      } catch (CannotCompileException var4) {
         Vape.logThrowable(var4);
      }
   }

   public String P(CtBehavior ctBehavior, Class clazz, String string, boolean bl, String string2, String string3) {
      return ctBehavior == null ? null : this.J(() -> this.lambda$makeAndInsertEvent$0(clazz, string3, ctBehavior, string, string2, bl));
   }

   private Object insertEventInjectionCode(EventInjectionSpec eventInjectionSpec) throws CannotCompileException, NotFoundException {
      p(eventInjectionSpec.getEventClass());
      CtBehavior ctBehavior = this.F(eventInjectionSpec.getMappingMethod());
      if (ctBehavior == null) {
         return null;
      } else {
         ctBehavior.getDeclaringClass().defrost();
         if (eventInjectionSpec.isInsertBefore()) {
            ctBehavior.insertBefore(eventInjectionSpec.buildInjectionCode());
         } else {
            ctBehavior.insertAfter(eventInjectionSpec.buildInjectionCode());
         }

         this.Y.defrost();
         return null;
      }
   }

   public String H(CtBehavior ctBehavior, Class clazz, String string, String string2, String string3) {
      return this.P(ctBehavior, clazz, string, true, string2, string3);
   }

   public void z() {
   }

   private <T> T v(MappingTaskCallable<T> mappingTaskCallable, String string) {
      try {
         Class<?> clazz = Class.forName(string);
         if (a.contains(clazz)) {
            p(clazz);
            return this.x(mappingTaskCallable, true);
         } else {
            p(clazz);
            return this.J(mappingTaskCallable);
         }
      } catch (ClassNotFoundException var4) {
         return null;
      }
   }

   @Override
   public boolean isApplied() {
      return this.k;
   }

   static {
      X(122);
   }
}
