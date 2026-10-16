package gg.vape.tutorial.selector;

import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.page.ModulesTutorialPage;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.module.ModuleComponent;
import java.util.ArrayList;
import java.util.Arrays;

public class ModulesTutorialValuesExpandButtonSelector extends TutorialTargetSelector<ModuleComponent> {
   private ModuleComponent selectedModule;
   private final ModulesTutorialPage tutorialPage;
   private static final String targetModuleName = "AutoClicker";

   @Override
   public ArrayList<GuiComponent> findTargets(GuiComponent guiComponent) {
      if (this.getTargetType().isInstance(guiComponent)) {
         ArrayList<GuiComponent> arrayList = super.findTargets(guiComponent);
         return arrayList != null && this.selectedModule != null && this.selectedModule.equals(guiComponent)
            ? new ArrayList<>(Arrays.asList(this.selectedModule.getSettingsButton()))
            : arrayList;
      } else {
         return null;
      }
   }

   public ModulesTutorialValuesExpandButtonSelector(ModulesTutorialPage modulesTutorialPage, Class clazz) {
      super(clazz);
      this.tutorialPage = modulesTutorialPage;
      this.selectedModule = null;
   }

   private boolean matchesModule(ModuleComponent moduleComponent) {
      if (moduleComponent.getModule().getName().equals("AutoClicker")) {
         this.selectedModule = moduleComponent;
         return true;
      } else {
         return false;
      }
   }

   public boolean matches(ModuleComponent moduleComponent) {
      return this.matchesModule(moduleComponent);
   }
}
