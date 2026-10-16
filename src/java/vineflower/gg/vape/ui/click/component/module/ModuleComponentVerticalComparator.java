package gg.vape.ui.click.component.module;

import java.util.Comparator;

class ModuleComponentVerticalComparator implements Comparator<ModuleComponent> {
   final ModuleComponent owner;

   ModuleComponentVerticalComparator(ModuleComponent moduleComponent) {
      this.owner = moduleComponent;
   }

   public int compareByY(ModuleComponent first, ModuleComponent second) {
      return (int)first.n() - (int)second.n();
   }

   public int compare(ModuleComponent first, ModuleComponent second) {
      return this.compareByY(first, second);
   }
}
