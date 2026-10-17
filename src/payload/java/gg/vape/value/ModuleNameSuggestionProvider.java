package gg.vape.value;

import gg.vape.Vape;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public class ModuleNameSuggestionProvider extends AbstractListValueSuggestionProvider {
   private List<String> cachedModuleNames;
   private boolean excludeOtherCategory;
   private static GuiComponent[] legacyComponents;

   public ModuleNameSuggestionProvider(boolean excludeOtherCategory) {
      this.excludeOtherCategory = excludeOtherCategory;
   }

   public static GuiComponent[] getLegacyComponents() {
      return legacyComponents;
   }

   public static void setLegacyComponents(GuiComponent[] components) {
      legacyComponents = components;
   }

   public ModuleNameSuggestionProvider() {
      this(false);
   }

   @Override
   public @UnmodifiableView List<String> getValues() {
      if (this.cachedModuleNames == null) {
         this.cachedModuleNames = new ArrayList<>();

         for (Mod module : Vape.INSTANCE.getModManager().collectMods()) {
            if (module.getCategory() != Category.NONE && (!this.excludeOtherCategory || module.getCategory() != Category.OTHER)) {
               this.cachedModuleNames.add(module.getName());
            }
         }
      }

      return this.cachedModuleNames;
   }

   static {
      if (getLegacyComponents() != null) {
         setLegacyComponents(new GuiComponent[3]);
      }
   }
}
