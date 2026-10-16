package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.ModuleVisitor;
import java.util.HashSet;

public class CheckModuleAdapter extends ModuleVisitor {
   private final boolean isOpen;
   private final CheckModuleAdapter.NameSet requiredModules = new CheckModuleAdapter.NameSet("Modules requires");
   private final CheckModuleAdapter.NameSet exportedPackages = new CheckModuleAdapter.NameSet("Module exports");
   private final CheckModuleAdapter.NameSet openedPackages = new CheckModuleAdapter.NameSet("Module opens");
   private final CheckModuleAdapter.NameSet usedServices = new CheckModuleAdapter.NameSet("Module uses");
   private final CheckModuleAdapter.NameSet providedServices = new CheckModuleAdapter.NameSet("Module provides");
   int classVersion;
   private boolean visitEndCalled;

   public CheckModuleAdapter(ModuleVisitor moduleVisitor, boolean isOpen) {
      this(589824, moduleVisitor, isOpen);
      if (this.getClass() != CheckModuleAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckModuleAdapter(int api, ModuleVisitor moduleVisitor, boolean isOpen) {
      super(api, moduleVisitor);
      this.isOpen = isOpen;
   }

   @Override
   public void visitMainClass(String mainClass) {
      CheckMethodAdapter.checkInternalName(53, mainClass, "module main class");
      super.visitMainClass(mainClass);
   }

   @Override
   public void visitPackage(String packaze) {
      CheckMethodAdapter.checkInternalName(53, packaze, "module package");
      super.visitPackage(packaze);
   }

   @Override
   public void visitRequire(String module, int access, String version) {
      this.checkVisitEndNotCalled();
      CheckClassAdapter.checkFullyQualifiedName(53, module, "required module");
      this.requiredModules.checkNameNotAlreadyDeclared(module);
      CheckClassAdapter.checkAccess(access, 36960);
      if (this.classVersion >= 54 && module.equals("java.base") && (access & 96) != 0) {
         throw new IllegalArgumentException(stringConcat$0(access));
      } else {
         super.visitRequire(module, access, version);
      }
   }

   @Override
   public void visitExport(String packaze, int access, String... modules) {
      this.checkVisitEndNotCalled();
      CheckMethodAdapter.checkInternalName(53, packaze, "package name");
      this.exportedPackages.checkNameNotAlreadyDeclared(packaze);
      CheckClassAdapter.checkAccess(access, 36864);
      if (modules != null) {
         for (String module : modules) {
            CheckClassAdapter.checkFullyQualifiedName(53, module, "module export to");
         }
      }

      super.visitExport(packaze, access, modules);
   }

   @Override
   public void visitOpen(String packaze, int access, String... modules) {
      this.checkVisitEndNotCalled();
      if (this.isOpen) {
         throw new UnsupportedOperationException("An open module can not use open directive");
      } else {
         CheckMethodAdapter.checkInternalName(53, packaze, "package name");
         this.openedPackages.checkNameNotAlreadyDeclared(packaze);
         CheckClassAdapter.checkAccess(access, 36864);
         if (modules != null) {
            for (String module : modules) {
               CheckClassAdapter.checkFullyQualifiedName(53, module, "module open to");
            }
         }

         super.visitOpen(packaze, access, modules);
      }
   }

   @Override
   public void visitUse(String service) {
      this.checkVisitEndNotCalled();
      CheckMethodAdapter.checkInternalName(53, service, "service");
      this.usedServices.checkNameNotAlreadyDeclared(service);
      super.visitUse(service);
   }

   @Override
   public void visitProvide(String service, String... providers) {
      this.checkVisitEndNotCalled();
      CheckMethodAdapter.checkInternalName(53, service, "service");
      this.providedServices.checkNameNotAlreadyDeclared(service);
      if (providers != null && providers.length != 0) {
         for (String provider : providers) {
            CheckMethodAdapter.checkInternalName(53, provider, "provider");
         }

         super.visitProvide(service, providers);
      } else {
         throw new IllegalArgumentException("Providers cannot be null or empty");
      }
   }

   @Override
   public void visitEnd() {
      this.checkVisitEndNotCalled();
      this.visitEndCalled = true;
      super.visitEnd();
   }

   private void checkVisitEndNotCalled() {
      if (this.visitEndCalled) {
         throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
      }
   }

   private static class NameSet {
      private final String type;
      private final HashSet<String> names;

      NameSet(String type) {
         this.type = type;
         this.names = new HashSet<>();
      }

      void checkNameNotAlreadyDeclared(String name) {
         if (!this.names.add(name)) {
            throw new IllegalArgumentException(stringConcat$0(this.type, name));
         }
      }
   }
}
