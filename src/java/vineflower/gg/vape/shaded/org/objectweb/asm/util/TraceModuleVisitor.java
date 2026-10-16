package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.ModuleVisitor;

public final class TraceModuleVisitor extends ModuleVisitor {
   public final Printer p;

   public TraceModuleVisitor(Printer printer) {
      this(null, printer);
   }

   public TraceModuleVisitor(ModuleVisitor moduleVisitor, Printer printer) {
      super(589824, moduleVisitor);
      this.p = printer;
   }

   @Override
   public void visitMainClass(String mainClass) {
      this.p.visitMainClass(mainClass);
      super.visitMainClass(mainClass);
   }

   @Override
   public void visitPackage(String packaze) {
      this.p.visitPackage(packaze);
      super.visitPackage(packaze);
   }

   @Override
   public void visitRequire(String module, int access, String version) {
      this.p.visitRequire(module, access, version);
      super.visitRequire(module, access, version);
   }

   @Override
   public void visitExport(String packaze, int access, String... modules) {
      this.p.visitExport(packaze, access, modules);
      super.visitExport(packaze, access, modules);
   }

   @Override
   public void visitOpen(String packaze, int access, String... modules) {
      this.p.visitOpen(packaze, access, modules);
      super.visitOpen(packaze, access, modules);
   }

   @Override
   public void visitUse(String use) {
      this.p.visitUse(use);
      super.visitUse(use);
   }

   @Override
   public void visitProvide(String service, String... providers) {
      this.p.visitProvide(service, providers);
      super.visitProvide(service, providers);
   }

   @Override
   public void visitEnd() {
      this.p.visitModuleEnd();
      super.visitEnd();
   }
}
