package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.ModuleVisitor;
import java.util.List;

public class ModuleProvideNode {
   public String service;
   public List<String> providers;

   public ModuleProvideNode(String service, List<String> providers) {
      this.service = service;
      this.providers = providers;
   }

   public void accept(ModuleVisitor moduleVisitor) {
      moduleVisitor.visitProvide(this.service, this.providers.toArray(new String[0]));
   }
}
