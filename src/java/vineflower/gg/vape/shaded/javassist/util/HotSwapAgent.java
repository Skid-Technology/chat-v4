package gg.vape.shaded.javassist.util;

import com.sun.tools.attach.VirtualMachine;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.NotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.management.ManagementFactory;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;

public class HotSwapAgent {
   private static Instrumentation instrumentation = null;

   public Instrumentation instrumentation() {
      return instrumentation;
   }

   public static void premain(String agentArgs, Instrumentation inst) throws Throwable {
      agentmain(agentArgs, inst);
   }

   public static void agentmain(String agentArgs, Instrumentation inst) throws Throwable {
      if (!inst.isRedefineClassesSupported()) {
         throw new RuntimeException("this JVM does not support redefinition of classes");
      } else {
         instrumentation = inst;
      }
   }

   public static void redefine(Class<?> oldClass, CtClass newClass) throws NotFoundException, IOException, CannotCompileException {
      Class<?>[] old = new Class[]{oldClass};
      CtClass[] newClasses = new CtClass[]{newClass};
      redefine(old, newClasses);
   }

   public static void redefine(Class<?>[] oldClasses, CtClass[] newClasses) throws NotFoundException, IOException, CannotCompileException {
      startAgent();
      ClassDefinition[] defs = new ClassDefinition[oldClasses.length];

      for (int i = 0; i < oldClasses.length; i++) {
         defs[i] = new ClassDefinition(oldClasses[i], newClasses[i].toBytecode());
      }

      try {
         instrumentation.redefineClasses(defs);
      } catch (ClassNotFoundException var4) {
         throw new NotFoundException(var4.getMessage(), var4);
      } catch (UnmodifiableClassException var5) {
         throw new CannotCompileException(var5.getMessage(), var5);
      }
   }

   private static void startAgent() throws NotFoundException {
      if (instrumentation == null) {
         try {
            File agentJar = createJarFile();
            String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
            String pid = nameOfRunningVM.substring(0, nameOfRunningVM.indexOf(64));
            VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent(agentJar.getAbsolutePath(), null);
            vm.detach();
         } catch (Exception var4) {
            throw new NotFoundException("hotswap agent", var4);
         }

         for (int sec = 0; sec < 10; sec++) {
            if (instrumentation != null) {
               return;
            }

            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var5) {
               Thread.currentThread().interrupt();
               break;
            }
         }

         throw new NotFoundException("hotswap agent (timeout)");
      }
   }

   public static File createAgentJarFile(String fileName) throws IOException, CannotCompileException, NotFoundException {
      return createJarFile(new File(fileName));
   }

   private static File createJarFile() throws IOException, CannotCompileException, NotFoundException {
      File jar = File.createTempFile("agent", ".jar");
      jar.deleteOnExit();
      return createJarFile(jar);
   }

   private static File createJarFile(File jar) throws IOException, CannotCompileException, NotFoundException {
      Manifest manifest = new Manifest();
      Attributes attrs = manifest.getMainAttributes();
      attrs.put(Name.MANIFEST_VERSION, "1.0");
      attrs.put(new Name("Premain-Class"), HotSwapAgent.class.getName());
      attrs.put(new Name("Agent-Class"), HotSwapAgent.class.getName());
      attrs.put(new Name("Can-Retransform-Classes"), "true");
      attrs.put(new Name("Can-Redefine-Classes"), "true");
      JarOutputStream jos = null;

      try {
         jos = new JarOutputStream(new FileOutputStream(jar), manifest);
         String cname = HotSwapAgent.class.getName();
         JarEntry e = new JarEntry(cname.replace('.', '/') + ".class");
         jos.putNextEntry(e);
         ClassPool pool = ClassPool.getDefault();
         CtClass clazz = pool.get(cname);
         jos.write(clazz.toBytecode());
         jos.closeEntry();
      } finally {
         if (jos != null) {
            jos.close();
         }
      }

      return jar;
   }
}
