package gg.vape.shaded.javassist.compiler;

import gg.vape.shaded.javassist.compiler.ast.Declarator;
import java.util.HashMap;

public final class SymbolTable extends HashMap<String, Declarator> {
   private static final long serialVersionUID = 1L;
   private SymbolTable parent;

   public SymbolTable() {
      this(null);
   }

   public SymbolTable(SymbolTable p) {
      this.parent = p;
   }

   public SymbolTable getParent() {
      return this.parent;
   }

   public Declarator lookup(String name) {
      Declarator found = this.get(name);
      return found == null && this.parent != null ? this.parent.lookup(name) : found;
   }

   public void append(String name, Declarator value) {
      this.put(name, value);
   }
}
