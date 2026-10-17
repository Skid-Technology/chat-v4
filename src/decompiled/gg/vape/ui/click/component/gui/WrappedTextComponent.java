/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.component.gui;

import gg.vape.Vape;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WrappedTextComponent
extends SimpleTextLabelComponent {
    private double measuredWidth = 0.0;
    private boolean wrappingEnabled = true;
    private boolean bold;
    private double textOffsetX = 0.0;
    private double wrapWidth = 0.0;
    private List<String> wrappedLines;
    private double measuredHeight = 0.0;
    private boolean centered = false;

    @Override
    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setWrapWidth(double wrapWidth) {
        this.wrapWidth = wrapWidth;
        this.wrappedLines = null;
    }

    private void renderWrappedLines() {
        SmoothFontRenderer fontRenderer = this.bold ? Vape.INSTANCE.getFontManager().W(this.fontScale, true) : Vape.INSTANCE.getFontManager().E(this.fontScale, false);
        SmoothFontRenderer smoothFontRenderer = fontRenderer;
        if (fontRenderer == null) {
            return;
        }
        double currentY = this.n();
        double maxWidth = 0.0;
        List<String> lines = this.getWrappedLines();
        if (lines != null) {
            for (String line : lines) {
                if (line == null || line.isEmpty()) continue;
                if (this.centered) {
                    fontRenderer.v(line, this.G$src$D$1b2f02a() + this.textOffsetX, currentY, this.getTextColor());
                } else {
                    fontRenderer.d(line, this.G$src$D$1b2f02a() + this.textOffsetX, currentY, this.getTextColor());
                }
                currentY += fontRenderer.d(line);
                double lineWidth = fontRenderer.N(line);
                if (!(lineWidth > maxWidth)) continue;
                maxWidth = lineWidth;
            }
        }
        this.measuredWidth = maxWidth;
        this.measuredHeight = currentY - this.n();
    }

    public List<String> getWrappedLines() {
        if (this.wrappedLines == null) {
            if (this.text == null || this.text.isEmpty()) {
                this.wrappedLines = new ArrayList<String>();
                this.measuredWidth = 0.0;
                this.measuredHeight = 0.0;
            } else {
                SmoothFontRenderer fontRenderer = this.bold ? Vape.INSTANCE.getFontManager().W(this.fontScale, true) : Vape.INSTANCE.getFontManager().E(this.fontScale, false);
                this.wrappedLines = this.wrapLines(Arrays.asList(this.text.split("\n")), fontRenderer);
                double maxWidth = 0.0;
                double totalHeight = 0.0;
                if (fontRenderer != null) {
                    for (String line : this.wrappedLines) {
                        if (line == null || line.isEmpty()) continue;
                        double w = fontRenderer.N(line);
                        if (w > maxWidth) {
                            maxWidth = w;
                        }
                        totalHeight += fontRenderer.d(line);
                    }
                }
                this.measuredWidth = maxWidth;
                this.measuredHeight = totalHeight;
            }
        }
        return this.wrappedLines;
    }

    private ArrayList<String> wrapLines(List<String> sourceLines, SmoothFontRenderer fontRenderer) {
        ArrayList<String> wrappedResult = new ArrayList<String>();
        if (fontRenderer == null) {
            if (sourceLines != null) {
                wrappedResult.addAll(sourceLines);
            }
            return wrappedResult;
        }
        double maxWrap = this.getWrapWidth();
        if (maxWrap <= 0.0) {
            maxWrap = 108.0;
        }
        if (sourceLines != null) {
            for (String sourceLine : sourceLines) {
                if (sourceLine == null || sourceLine.isEmpty()) continue;
                String[] words = sourceLine.split(" ");
                StringBuilder currentLine = new StringBuilder();
                for (String word : words) {
                    if (currentLine.length() == 0) {
                        currentLine.append(word);
                        continue;
                    }
                    String testLine = currentLine + " " + word;
                    if (fontRenderer.N(testLine) <= maxWrap) {
                        currentLine.append(" ").append(word);
                        continue;
                    }
                    wrappedResult.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                }
                if (currentLine.length() <= 0) continue;
                wrappedResult.add(currentLine.toString());
            }
        }
        if (wrappedResult.isEmpty() && sourceLines != null) {
            wrappedResult.addAll(sourceLines);
        }
        return wrappedResult;
    }

    public void setWrappingEnabled(boolean wrappingEnabled) {
        this.wrappingEnabled = wrappingEnabled;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        this.wrappedLines = null;
    }

    @Override
    public void c() {
        super.c();
        this.renderWrappedLines();
    }

    public WrappedTextComponent(String text, double fontScale, Color color, boolean bold, double textOffsetX) {
        super(text, fontScale);
        this.setTextColor(color);
        this.bold = bold;
        this.textOffsetX = textOffsetX;
    }

    @Override
    public double A() {
        if (this.wrappedLines == null) {
            this.getWrappedLines();
        }
        return this.measuredWidth > 0.0 ? this.measuredWidth : super.A();
    }

    @Override
    public double C() {
        if (this.wrappedLines == null) {
            this.getWrappedLines();
        }
        return this.measuredHeight;
    }

    public WrappedTextComponent(String text, double fontScale, Color color, boolean bold) {
        super(text, fontScale);
        this.setTextColor(color);
        this.bold = bold;
    }

    @Override
    public void H() {
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public double getWrapWidth() {
        if (this.wrapWidth > 0.0) {
            return this.wrapWidth;
        }
        double width = this.getExplicitWidth() > 0.0 ? this.getExplicitWidth() : (this.A() > 0.0 ? this.A() : 120.0);
        return Math.max(20.0, width - this.textOffsetX - 6.0);
    }

    public WrappedTextComponent(String text, double fontScale) {
        super(text, fontScale);
    }
}

