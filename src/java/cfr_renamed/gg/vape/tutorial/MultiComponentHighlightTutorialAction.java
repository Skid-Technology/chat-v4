/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.tutorial.ClassTutorialTargetSelector;
import gg.vape.tutorial.HighlightTutorialAction;
import gg.vape.tutorial.MultiComponentHighlightAdvanceClickListener;
import gg.vape.tutorial.TutorialActionComponent;
import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MultiComponentHighlightTutorialAction
extends HighlightTutorialAction {
    private final static String var_java_lang_String_g = "start ";
    private final TutorialTargetSelector targetSelector;
    private final Supplier<GuiComponent> searchRootSupplier;
    private GuiComponent searchRoot;

    public GuiComponent getSearchRoot() {
        if (this.searchRoot != null) {
            return this.searchRoot;
        }
        if (this.searchRootSupplier != null) {
            return this.searchRootSupplier.get();
        }
        return null;
    }

    @Override
    public boolean isTargetReady() {
        GuiComponent root = this.getSearchRoot();
        if (root == null || !root.V$src$Z$1xhop3l()) {
            return false;
        }
        if (this.getHighlightedComponents().isEmpty()) {
            this.findTargetsRecursive(root, this.getHighlightedComponents());
        }
        return !this.getHighlightedComponents().isEmpty();
    }

    private void findTargetsRecursive(GuiComponent component, List<GuiComponent> result) {
        if (component == null) {
            return;
        }
        ArrayList<GuiComponent> targets = this.targetSelector.findTargets(component);
        if (targets != null && !targets.isEmpty()) {
            for (GuiComponent t : targets) {
                if (t == null || result.contains(t)) continue;
                result.add(t);
            }
        }
        if (component.f() != null) {
            for (GuiComponent child : component.f()) {
                this.findTargetsRecursive(child, result);
            }
        }
    }

    @Override
    public RectData getHighlightBounds() {
        GuiComponent root;
        if (this.getHighlightedComponents().isEmpty() && (root = this.getSearchRoot()) != null) {
            this.findTargetsRecursive(root, this.getHighlightedComponents());
        }
        if (this.getHighlightedComponents().isEmpty()) {
            return new RectData(0.0, 0.0, 0.0, 0.0);
        }
        GuiComponent guiComponent = null;
        GuiComponent guiComponent2 = null;
        for (int i = 0; i < this.getHighlightedComponents().size(); ++i) {
            GuiComponent guiComponent3 = this.getHighlightedComponents().get(i);
            if (!guiComponent3.V$src$Z$1xhop3l() && !this.includesInactiveTargets()) continue;
            guiComponent3.c();
            if (guiComponent == null) {
                guiComponent = guiComponent3;
            }
            guiComponent2 = guiComponent3;
        }
        if (guiComponent == null) {
            return new RectData(0.0, 0.0, 0.0, 0.0);
        }
        return new RectData(guiComponent.G$src$D$1b2f02a(), guiComponent.n(), guiComponent.A(), guiComponent2.n() + guiComponent2.L() - guiComponent.n());
    }

    @Override
    public void start() {
        Vape.debugLog(var_java_lang_String_g + this);
        GuiComponent root = this.getSearchRoot();
        if (root == null) {
            return;
        }
        this.getHighlightedComponents().clear();
        this.findTargetsRecursive(root, this.getHighlightedComponents());
        if (this.includesHiddenComponents()) {
            this.getComponent().getActionButton().setVisible(false);
            MultiComponentHighlightAdvanceClickListener multiComponentHighlightAdvanceClickListener = new MultiComponentHighlightAdvanceClickListener(this);
            for (GuiComponent guiComponent : this.getHighlightedComponents()) {
                this.registerMouseListener(guiComponent, multiComponentHighlightAdvanceClickListener);
            }
        }
    }

    public MultiComponentHighlightTutorialAction(Supplier<GuiComponent> searchRootSupplier, Class clazz, String string, String string2, boolean bl) {
        this(searchRootSupplier, (TutorialTargetSelector)new ClassTutorialTargetSelector(clazz, clazz), string, string2, bl);
    }

    public MultiComponentHighlightTutorialAction(Supplier<GuiComponent> searchRootSupplier, TutorialTargetSelector tutorialTargetSelector, String string, String string2, boolean bl) {
        super(new TutorialActionComponent(string, string2), bl);
        this.targetSelector = tutorialTargetSelector;
        this.searchRootSupplier = searchRootSupplier;
        this.searchRoot = null;
    }

    public MultiComponentHighlightTutorialAction(GuiComponent guiComponent, Class clazz, String string, String string2, boolean bl) {
        this(guiComponent, (TutorialTargetSelector)new ClassTutorialTargetSelector(clazz, clazz), string, string2, bl);
    }

    public MultiComponentHighlightTutorialAction(GuiComponent guiComponent, TutorialTargetSelector tutorialTargetSelector, String string, String string2, boolean bl) {
        super(new TutorialActionComponent(string, string2), bl);
        this.targetSelector = tutorialTargetSelector;
        this.searchRootSupplier = null;
        this.searchRoot = guiComponent;
    }
}

