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
    private static final String g = "start ";
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
    public boolean isTargetReady() {
        GuiComponent root = this.getSearchRoot();
        if (root == null || !root.V$src$Z$1xhop3l()) {
            return false;
        }
        if (this.getHighlightedComponents().isEmpty()) {
            this.findTargetsRecursive(root, this.getHighlightedComponents());
        }
        boolean hasVisible = false;
        for (GuiComponent comp : this.getHighlightedComponents()) {
            if (!comp.V$src$Z$1xhop3l() && !this.includesInactiveTargets()) continue;
            hasVisible = true;
            break;
        }
        if (!hasVisible) {
            this.getHighlightedComponents().clear();
            this.findTargetsRecursive(root, this.getHighlightedComponents());
            for (GuiComponent comp : this.getHighlightedComponents()) {
                if (!comp.V$src$Z$1xhop3l() && !this.includesInactiveTargets()) continue;
                hasVisible = true;
                break;
            }
        }
        if (hasVisible && this.requiresClick()) {
            MultiComponentHighlightAdvanceClickListener listener = new MultiComponentHighlightAdvanceClickListener(this);
            for (GuiComponent comp : this.getHighlightedComponents()) {
                if (this.getMouseListener(comp) != null) continue;
                this.registerMouseListener(comp, listener);
            }
        }
        return hasVisible;
    }

    @Override
    public RectData getHighlightBounds() {
        GuiComponent root;
        GuiComponent root2;
        if (this.getHighlightedComponents().isEmpty() && (root2 = this.getSearchRoot()) != null) {
            this.findTargetsRecursive(root2, this.getHighlightedComponents());
        }
        if (this.getHighlightedComponents().isEmpty()) {
            return new RectData(0.0, 0.0, 0.0, 0.0);
        }
        GuiComponent first = null;
        GuiComponent last = null;
        for (int i = 0; i < this.getHighlightedComponents().size(); ++i) {
            GuiComponent comp = this.getHighlightedComponents().get(i);
            if (!comp.V$src$Z$1xhop3l() && !this.includesInactiveTargets()) continue;
            comp.F();
            comp.c();
            if (first == null) {
                first = comp;
            }
            last = comp;
        }
        if (first == null && (root = this.getSearchRoot()) != null) {
            this.getHighlightedComponents().clear();
            this.findTargetsRecursive(root, this.getHighlightedComponents());
            for (int i = 0; i < this.getHighlightedComponents().size(); ++i) {
                GuiComponent comp = this.getHighlightedComponents().get(i);
                if (!comp.V$src$Z$1xhop3l() && !this.includesInactiveTargets()) continue;
                comp.F();
                comp.c();
                if (first == null) {
                    first = comp;
                }
                last = comp;
            }
        }
        if (first == null) {
            return new RectData(0.0, 0.0, 0.0, 0.0);
        }
        return new RectData(first.G$src$D$1b2f02a(), first.n(), first.A(), last.n() + last.L() - first.n());
    }

    @Override
    public void start() {
        Vape.debugLog(g + this);
        this.getComponent().getActionButton().setVisible(!this.requiresClick());
        GuiComponent root = this.getSearchRoot();
        if (root == null) {
            return;
        }
        this.getHighlightedComponents().clear();
        this.findTargetsRecursive(root, this.getHighlightedComponents());
        if (this.requiresClick()) {
            MultiComponentHighlightAdvanceClickListener listener = new MultiComponentHighlightAdvanceClickListener(this);
            for (GuiComponent comp : this.getHighlightedComponents()) {
                this.registerMouseListener(comp, listener);
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

