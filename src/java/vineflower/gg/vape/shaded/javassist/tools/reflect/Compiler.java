package gg.vape.shaded.javassist.tools.reflect;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.CtClass;
import java.io.PrintStream;

public class Compiler {
   public static void main(String[] args) throws Exception {
      if (args.length == 0) {
         help(System.err);
      } else {
         CompiledClass[] entries = new CompiledClass[args.length];
         int n = parse(args, entries);
         if (n < 1) {
            System.err.println("bad parameter.");
         } else {
            processClasses(entries, n);
         }
      }
   }

   private static void processClasses(CompiledClass[] entries, int n) throws Exception {
      Reflection implementor = new Reflection();
      ClassPool pool = ClassPool.getDefault();
      implementor.start(pool);

      for (int i = 0; i < n; i++) {
         CtClass c = pool.get(entries[i].classname);
         if (entries[i].metaobject == null && entries[i].classobject == null) {
            System.err.println(c.getName() + ": not reflective");
         } else {
            String metaobj;
            if (entries[i].metaobject == null) {
               metaobj = "gg.vape.shaded.javassist.tools.reflect.Metaobject";
            } else {
               metaobj = entries[i].metaobject;
            }

            String classobj;
            if (entries[i].classobject == null) {
               classobj = "gg.vape.shaded.javassist.tools.reflect.ClassMetaobject";
            } else {
               classobj = entries[i].classobject;
            }

            if (!implementor.makeReflective(c, pool.get(metaobj), pool.get(classobj))) {
               System.err.println("Warning: " + c.getName() + " is reflective.  It was not changed.");
            }

            System.err.println(c.getName() + ": " + metaobj + ", " + classobj);
         }
      }

      for (int ix = 0; ix < n; ix++) {
         implementor.onLoad(pool, entries[ix].classname);
         pool.get(entries[ix].classname).writeFile();
      }
   }

   private static int parse(String[] args, CompiledClass[] result) {
      int n = -1;

      for (int i = 0; i < args.length; i++) {
         String a = args[i];
         if (a.equals("-m")) {
            if (n < 0 || i + 1 > args.length) {
               return -1;
            }

            result[n].metaobject = args[++i];
         } else if (a.equals("-c")) {
            if (n < 0 || i + 1 > args.length) {
               return -1;
            }

            result[n].classobject = args[++i];
         } else {
            if (a.charAt(0) == '-') {
               return -1;
            }

            CompiledClass cc = new CompiledClass();
            cc.classname = a;
            cc.metaobject = null;
            cc.classobject = null;
            result[++n] = cc;
         }
      }

      return n + 1;
   }

   private static void help(PrintStream out) {
      out.println("Usage: java javassist.tools.reflect.Compiler");
      out.println("            (<class> [-m <metaobject>] [-c <class metaobject>])+");
   }
}
