/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial.selector;

import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.page.ModulesTutorialPage;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.module.ModuleComponent;
import java.util.ArrayList;

public class ModulesTutorialModuleValuesSelector
extends TutorialTargetSelector<ModuleComponent> {
    private ModuleComponent selectedModule;
    private final static String targetModuleName = "AutoClicker";
    private final ModulesTutorialPage tutorialPage;

    private boolean matchesModule(ModuleComponent moduleComponent) {
        if (moduleComponent.getModule().getName().equals(targetModuleName)) {
            this.selectedModule = moduleComponent;
            return true;
        }
        return false;
    }

    @Override
    public boolean matches(ModuleComponent moduleComponent) {
        return this.matchesModule(moduleComponent);
    }

    public ModulesTutorialModuleValuesSelector(ModulesTutorialPage modulesTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = modulesTutorialPage;
        this.selectedModule = null;
    }

    @Override
    public ArrayList<GuiComponent> findTargets(GuiComponent guiComponent) {
        if (this.getTargetType().isInstance(guiComponent)) {
            ArrayList<GuiComponent> arrayList = super.findTargets(guiComponent);
            if (arrayList != null && this.selectedModule != null) {
                return new ArrayList<GuiComponent>(this.selectedModule.getValueComponents());
            }
            return arrayList;
        }
        return null;
    }

    @Override
    public boolean matches(GuiComponent guiComponent) {
        return this.matches((ModuleComponent)guiComponent);
    }
}

