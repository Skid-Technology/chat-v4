/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.tutorial.HighlightTutorialAction;
import gg.vape.tutorial.SingleComponentHighlightAdvanceClickListener;
import gg.vape.tutorial.TutorialActionComponent;
import gg.vape.ui.click.component.GuiComponent;
import java.util.function.Supplier;

public class SingleComponentHighlightTutorialAction
extends HighlightTutorialAction {
    private static final String g = "start ";
    private final Supplier<GuiComponent> componentSupplier;

    public SingleComponentHighlightTutorialAction(Supplier<GuiComponent> componentSupplier, String string, String string2, boolean bl) {
        super(new TutorialActionComponent(string, string2), bl);
        this.componentSupplier = componentSupplier;
    }

    public SingleComponentHighlightTutorialAction(GuiComponent guiComponent, String string, String string2, boolean bl) {
        this(() -> guiComponent, string, string2, bl);
    }

    public GuiComponent getHighlightedComponent() {
        GuiComponent comp;
        if (!this.getHighlightedComponents().isEmpty()) {
            return this.getHighlightedComponents().get(0);
        }
        if (this.componentSupplier != null && (comp = this.componentSupplier.get()) != null) {
            this.getHighlightedComponents().add(comp);
            return comp;
        }
        return null;
    }

    @Override
    public RectData getHighlightBounds() {
        GuiComponent guiComponent = this.getHighlightedComponent();
        if (guiComponent == null) {
            return new RectData(0.0, 0.0, 0.0, 0.0);
        }
        guiComponent.c();
        return new RectData(guiComponent.G$src$D$1b2f02a(), guiComponent.n(), guiComponent.A(), guiComponent.L());
    }

    @Override
    public void start() {
        GuiComponent comp;
        Vape.debugLog(g + this);
        this.getComponent().getActionButton().setVisible(!this.requiresClick());
        if (this.requiresClick() && (comp = this.getHighlightedComponent()) != null) {
            SingleComponentHighlightAdvanceClickListener singleComponentHighlightAdvanceClickListener = new SingleComponentHighlightAdvanceClickListener(this);
            this.registerMouseListener(comp, singleComponentHighlightAdvanceClickListener);
        }
    }

    @Override
    public boolean isTargetReady() {
        GuiComponent comp = this.getHighlightedComponent();
        if (comp != null && this.requiresClick() && this.getMouseListener(comp) == null) {
            SingleComponentHighlightAdvanceClickListener singleComponentHighlightAdvanceClickListener = new SingleComponentHighlightAdvanceClickListener(this);
            this.registerMouseListener(comp, singleComponentHighlightAdvanceClickListener);
        }
        return comp != null && comp.V$src$Z$1xhop3l();
    }
}

