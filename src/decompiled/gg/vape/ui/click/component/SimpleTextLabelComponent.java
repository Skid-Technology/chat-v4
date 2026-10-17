/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import java.awt.Color;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public class SimpleTextLabelComponent
extends GuiComponent {
    private float offsetX;
    @Nullable
    private Supplier<String> textSupplier;
    private float offsetY;
    private Color textColor;
    private boolean bold;
    protected String text;
    private static String[] legacyState;
    protected double fontScale;
    private int extraHeight;

    @Override
    public void F() {
    }

    @Override
    public String getText() {
        return this.text;
    }

    public SimpleTextLabelComponent(String text) {
        this(text, 0.75);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Nullable
    public Supplier<String> getTextSupplier() {
        return this.textSupplier;
    }

    public double getTextHeight() {
        return this.getFontRenderer(this.fontScale).d(this.text);
    }

    public void setExtraHeight(int extraHeight) {
        this.extraHeight = extraHeight;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public double getTextWidth() {
        return this.x();
    }

    @Override
    public double C() {
        SmoothFontRenderer fontRenderer;
        SmoothFontRenderer smoothFontRenderer = fontRenderer = this.bold ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        if (fontRenderer == null) {
            return 0.0;
        }
        if (this.text != null && this.text.contains("\n")) {
            String[] lines = this.text.split("\n");
            return (fontRenderer.d("A") + 2.0) * (double)lines.length + (double)this.extraHeight;
        }
        return fontRenderer.d("A") + (double)this.extraHeight;
    }

    @Override
    public void u() {
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public double x() {
        SmoothFontRenderer fontRenderer;
        if (this.text == null) {
            return 0.0;
        }
        SmoothFontRenderer smoothFontRenderer = fontRenderer = this.bold ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        if (fontRenderer == null) {
            return 0.0;
        }
        if (this.text.contains("\n")) {
            double max = 0.0;
            for (String line : this.text.split("\n")) {
                double w = fontRenderer.N(line);
                if (!(w > max)) continue;
                max = w;
            }
            return max;
        }
        return fontRenderer.N(this.text);
    }

    public void setTextSupplier(@Nullable Supplier<String> textSupplier) {
        this.textSupplier = textSupplier;
        if (textSupplier != null) {
            this.setText(textSupplier.get());
        }
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public double getFontScale() {
        return this.fontScale;
    }

    @Override
    public void I() {
    }

    public static String[] getLegacyState() {
        return legacyState;
    }

    public SimpleTextLabelComponent(String text, double fontScale) {
        this.textColor = SimpleTextLabelComponent.J.h;
        this.offsetX = 5.0f;
        this.offsetY = 0.0f;
        this.extraHeight = 0;
        this.text = text;
        this.fontScale = fontScale;
    }

    public static void setLegacyState(String[] state) {
        legacyState = state;
    }

    public boolean isBold() {
        return this.bold;
    }

    public SimpleTextLabelComponent(String text, double fontScale, Color textColor, boolean bold) {
        this(text, fontScale, textColor);
        this.bold = bold;
    }

    @Override
    public void H() {
        SmoothFontRenderer fontRenderer;
        Supplier<String> currentTextSupplier = this.textSupplier;
        if (currentTextSupplier != null) {
            this.setText(currentTextSupplier.get());
        }
        if (this.text == null || this.text.isEmpty()) {
            return;
        }
        SmoothFontRenderer smoothFontRenderer = fontRenderer = this.bold ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        if (fontRenderer == null) {
            return;
        }
        if (this.text.contains("\n")) {
            String[] lines = this.text.split("\n");
            double lineHeight = fontRenderer.d("A") + 2.0;
            double currentY = this.n() + (double)this.offsetY;
            for (String line : lines) {
                fontRenderer.d(line, this.G$src$D$1b2f02a() + (double)this.offsetX, currentY, this.textColor);
                currentY += lineHeight;
            }
        } else {
            double textHeight = fontRenderer.d("A");
            double textY = this.n() + this.L() / 2.0 - textHeight / 2.0;
            fontRenderer.d(this.text, this.G$src$D$1b2f02a() + (double)this.offsetX, textY + (double)this.offsetY, this.textColor);
        }
    }

    public Color getTextColor() {
        return this.textColor;
    }

    public void setFontScale(double fontScale) {
        this.fontScale = fontScale;
    }

    public SimpleTextLabelComponent(String text, double fontScale, Color textColor) {
        this(text, fontScale);
        this.setTextColor(textColor);
    }

    static {
        SimpleTextLabelComponent.setLegacyState(null);
    }
}

