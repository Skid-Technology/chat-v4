/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.gui.WrappedTextComponent;
import java.awt.Color;
import java.util.List;

public class CollapsiblePanelComponent
extends PanelComponent {
    private TextLabel toggleLabel;
    private FlowLayoutComponent contentLayout;
    private String text;
    private PanelComponent togglePanel;
    private WrappedTextComponent expandedText;
    private boolean collapsed;
    private TruncatedTextComponent collapsedText;

    @Override
    public double L() {
        if (this.text == null || this.text.isEmpty()) {
            return this.contentLayout.L();
        }
        return this.collapsed ? this.getFontRenderer(0.8).d(this.text) : this.expandedText.L() + 2.0 + this.contentLayout.L();
    }

    private void toggleCollapsed() {
        this.collapsed = !this.collapsed;
        this.toggleLabel.setLabelText(this.collapsed ? "...more" : "...less");
    }

    public CollapsiblePanelComponent(String text, double width) {
        super(width, 0.0);
        this.collapsedText = new TruncatedTextComponent("", "", this.A() - 10.0, 0.8f, CollapsiblePanelComponent.J.var_java_awt_Color_A, false);
        this.collapsed = true;
        this.togglePanel = new PanelComponent(this.A(), 0.0);
        this.text = text != null ? text : "";
        this.toggleLabel = new TextLabel("...more", 1.0);
        this.expandedText = new WrappedTextComponent(this.text, 0.8, CollapsiblePanelComponent.J.var_java_awt_Color_Z, false);
        this.expandedText.setWrapWidth(width);
        this.contentLayout = new FlowLayoutComponent(width);
        this.setShowDisabledOverlay(false);
        this.togglePanel.setShowDisabledOverlay(false);
        this.contentLayout.setShowDisabledOverlay(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.togglePanel.h(this.toggleLabel, new Object[0]);
        this.addChildren(this.togglePanel, this.contentLayout);
        this.toggleLabel.setFontScale(0.8);
        this.toggleLabel.setUppercase(false);
        this.toggleLabel.setTextColor(Color.WHITE);
        this.toggleLabel.o(30.0);
        this.collapsedText.setTextColor(CollapsiblePanelComponent.J.var_java_awt_Color_Z);
        if (this.text.isEmpty()) {
            this.collapsed = false;
            this.toggleLabel.setVisible(false);
        }
        this.toggleLabel.addClickListener(this::toggleCollapsed);
    }

    public FlowLayoutComponent getContentLayout() {
        return this.contentLayout;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }

    @Override
    public void dispatchMouseEvent(GuiMouseEvent mouseEvent) {
        super.dispatchMouseEvent(mouseEvent);
    }

    @Override
    public void c() {
        this.collapsedText.K(this.G$src$D$1b2f02a());
        this.collapsedText.S(this.n());
        this.contentLayout.setVisible(!this.collapsed);
        this.expandedText.setText(this.text != null ? this.text : "");
        this.expandedText.setWrapWidth(this.A() - 20.0);
        this.expandedText.o(this.A() - 20.0);
        List<String> wrappedLines = this.expandedText.getWrappedLines();
        if (wrappedLines == null || wrappedLines.isEmpty()) {
            this.collapsedText.setText("");
            this.collapsedText.setMaxWidth(this.A() - 20.0);
            this.collapsedText.o(0.0);
            this.toggleLabel.setVisible(false);
            super.c();
            return;
        }
        if (wrappedLines.size() <= 1 && (this.text == null || this.text.isEmpty())) {
            this.toggleLabel.setVisible(false);
        } else {
            this.toggleLabel.setVisible(wrappedLines.size() > 1);
        }
        String firstLine = wrappedLines.get(0);
        this.collapsedText.setText(firstLine);
        this.collapsedText.setMaxWidth(this.A() - 20.0);
        this.collapsedText.o(wrappedLines.size() > 1 ? this.expandedText.getFontRenderer(0.8).N(firstLine) : this.expandedText.A() - 20.0);
        this.toggleLabel.setUseAlternateFont(true);
        if (this.collapsed) {
            this.toggleLabel.K(this.G$src$D$1b2f02a() + this.collapsedText.A() + 2.0);
            this.toggleLabel.S(this.n());
            this.togglePanel.setExplicitHeight(this.collapsedText.L());
        } else {
            String lastLine = wrappedLines.get(wrappedLines.size() - 1);
            double lastLineWidth = this.expandedText.getFontRenderer(0.8).N(lastLine);
            this.toggleLabel.K(this.G$src$D$1b2f02a() + lastLineWidth + 3.0);
            this.toggleLabel.S(this.n() + this.expandedText.L() - this.collapsedText.L());
            this.togglePanel.setExplicitHeight(this.expandedText.L());
        }
        this.toggleLabel.Y(this.collapsedText.L());
        super.c();
        if (this.collapsed) {
            this.collapsedText.c();
        } else {
            this.expandedText.K(this.G$src$D$1b2f02a());
            this.expandedText.S(this.n());
            this.expandedText.c();
        }
    }
}

