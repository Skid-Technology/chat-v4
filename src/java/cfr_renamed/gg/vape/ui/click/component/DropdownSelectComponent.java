/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 *  org.jetbrains.annotations.UnmodifiableView
 */
package gg.vape.ui.click.component;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.DescribedOption;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.DropdownPopupCloseClickHandler;
import gg.vape.ui.click.component.DropdownSelectOptionComponent;
import gg.vape.ui.click.component.DropdownSelectionListener;
import gg.vape.ui.click.component.FilledSpacerComponent;
import gg.vape.ui.click.component.FocusableComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.ModeEntryTextFormatter;
import gg.vape.ui.click.component.OptionTextFormatter;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.input.BindableInputComponent;
import gg.vape.ui.click.frame.PopupFrame;
import gg.vape.ui.font.FontOption;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.unmap.PropertyContainer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.value.ModeValue;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public class DropdownSelectComponent<T>
extends GuiComponent
implements FocusableComponent {
    @Nullable
    private ModeValue modeValue;
    private BindableInputComponent bindComponent;
    private final Map<String, List<T>> optionsByGroup = new LinkedHashMap<String, List<T>>();
    private boolean disabled;
    private final OptionTextFormatter<T> optionFormatter;
    private T selectedValue;
    private boolean pressed;
    @Nullable
    private PopupFrame popupFrame;
    private final List<DropdownSelectionListener> selectionListeners = new ArrayList<DropdownSelectionListener>();
    private boolean highlightedStyle;
    private final String label;
    private boolean inputSuppressed;
    private final ColorAnimation borderAnimation;
    private final float arrowSize = 2.0f;
    private final PanelComponent optionsPanel;
    private boolean showLabelPrefix;
    private static String[] legacyState;
    @Nullable
    private String emptyText;

    @Override
    public double x() {
        return 50.0;
    }

    @Override
    public void c() {
        PopupFrame popupFrame;
        super.c();
        boolean shouldShowBind = false;
        if (this.bindComponent != null && this.modeValue != null && this.modeValue.isBindable()) {
            shouldShowBind = ClientSettings.INSTANCE.allowSettingKeybinds.isEnabled() && (this.w$src$Z$e457mb() || this.bindComponent.isCapturing() || this.modeValue.getBind() != null && this.modeValue.getBind().hasValidBinding());
            this.bindComponent.setVisible(shouldShowBind);
        }
        if ((popupFrame = this.popupFrame) != null) {
            this.positionPopup();
            this.optionsPanel.H(true);
            this.optionsPanel.c();
        }
    }

    @Override
    public void u() {
        if (this.pressed && !this.w$src$Z$e457mb() && !this.isExpanded()) {
            this.borderAnimation.J();
            this.pressed = false;
        }
    }

    @Override
    public void H() {
        double backgroundX;
        String displayText;
        Object currentValue;
        this.onDisable();
        SmoothFontRenderer fontRenderer = this.getFontRenderer(0.85);
        Object object = currentValue = this.modeValue != null ? this.modeValue.getValue() : this.selectedValue;
        String string = currentValue != null ? this.optionFormatter.format(this.modeValue != null ? this.modeValue.getValue() : this.selectedValue) : (displayText = this.emptyText != null ? this.emptyText : "");
        if (this.showLabelPrefix && !this.label.isEmpty()) {
            displayText = this.label + " - " + displayText;
        }
        double textHeight = fontRenderer.d(displayText);
        double centeredTextY = this.n() + this.L() / 2.0 - textHeight / 2.0;
        this.getClass();
        double textY = centeredTextY + 0.625;
        double arrowY = this.n() + this.L() / 2.0 - (double)(this.arrowSize / 2.0f);
        double dropdownHeight = this.L();
        this.getClass();
        double backgroundHeight = dropdownHeight - 5.0;
        if (this.isExpanded()) {
            backgroundHeight += this.optionsPanel.L();
        }
        if (this.highlightedStyle) {
            backgroundX = this.G$src$D$1b2f02a() + this.getHorizontalInset();
            double backgroundY = this.n();
            this.getClass();
            GuiRenderPrimitives.e(backgroundX, backgroundY + 2.5 + 0.5, this.A() - this.getHorizontalInset() - 8.0 + 2.0, backgroundHeight - 1.0, this.disabled ? DropdownSelectComponent.J.var_java_awt_Color_S.darker() : DropdownSelectComponent.J.var_java_awt_Color_S, false, 2.0f, 1.0f);
        } else {
            backgroundX = this.G$src$D$1b2f02a() + this.getHorizontalInset();
            double backgroundY = this.n();
            this.getClass();
            GuiRenderPrimitives.e(backgroundX, backgroundY + 2.5 + 0.5, this.A() - this.getHorizontalInset() - 8.0 + 2.0, backgroundHeight - 1.0, this.disabled ? DropdownSelectComponent.J.var_java_awt_Color_l.darker() : DropdownSelectComponent.J.var_java_awt_Color_i, false, 2.0f, 1.0f);
            double borderX = this.G$src$D$1b2f02a() + this.getHorizontalInset();
            double borderY = this.n();
            this.getClass();
            GuiRenderPrimitives.P(borderX, borderY + 2.5, this.A() - this.getHorizontalInset() - 8.0 + 2.0, backgroundHeight - 1.0, this.borderAnimation.getInterpolatedColor(), 3.0f, 0.75f, 1.0f);
        }
        fontRenderer.d(displayText, this.G$src$D$1b2f02a() + this.getHorizontalInset() + 5.0, textY, this.disabled ? DropdownSelectComponent.J.var_java_awt_Color_h : DropdownSelectComponent.J.var_java_awt_Color_Z);
        Color arrowColor = DropdownSelectComponent.J.var_java_awt_Color_W;
        float rightEdgeX = (float)(this.G$src$D$1b2f02a() + this.A());
        this.getClass();
        ImageRenderer.drawImage(arrowColor, rightEdgeX - 15.0f, (float)arrowY, this.isExpanded() ? "upcollapse" : "downexpand", this.arrowSize, this.arrowSize, false);
        if (this.bindComponent != null && this.bindComponent.V$src$Z$1xhop3l()) {
            this.bindComponent.K((double)rightEdgeX - 30.0);
            this.bindComponent.S(arrowY - 1.0);
        }
    }

    @Nullable
    public PopupFrame getPopupFrame() {
        return this.popupFrame;
    }

    public void addGroupedOptions(String groupName, T ... options) {
        for (T option : options) {
            this.addGroupedOption(groupName, option);
        }
    }

    public static void setLegacyState(String[] state) {
        legacyState = state;
    }

    public void onSelectionChanged() {
    }

    @Override
    public void I() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void togglePopup() {
        OptionTextFormatter<T> optionTextFormatter;
        PopupFrame popupFrame = this.popupFrame;
        OptionTextFormatter<T> optionTextFormatter2 = optionTextFormatter = this.optionFormatter;
        synchronized (optionTextFormatter2) {
            if (popupFrame != null) {
                this.popupFrame = null;
                ClientSettings.activeComponent = null;
                ClientSettings.removePopup(popupFrame);
            } else {
                this.optionsPanel.removeMarkedChildren();
                this.optionsPanel.setExplicitWidth(this.A() - this.getHorizontalInset() - 8.0 + 2.0);
                this.optionsPanel.setCornerRadius(3.0f);
                for (Map.Entry<String, List<T>> entry : this.optionsByGroup.entrySet()) {
                    String groupName = entry.getKey();
                    List<T> options = entry.getValue();
                    if (options.isEmpty()) continue;
                    if (groupName != null) {
                        SimpleTextLabelComponent groupLabel = new SimpleTextLabelComponent(groupName, 0.8);
                        groupLabel.setBold(true);
                        groupLabel.setTextColor(DropdownSelectComponent.J.var_java_awt_Color_A);
                        groupLabel.o(groupLabel.getTextWidth() * 1.3);
                        double dividerWidth = (this.optionsPanel.A() - groupLabel.A()) / 2.0;
                        this.optionsPanel.h(new FilledSpacerComponent(dividerWidth, groupLabel.L(), dividerWidth - 4.0, 0.5, DropdownSelectComponent.J.var_java_awt_Color_y), "widthwrap");
                        this.optionsPanel.h(groupLabel, "widthwrap");
                        this.optionsPanel.h(new FilledSpacerComponent(dividerWidth, groupLabel.L(), dividerWidth - 4.0, 0.5, DropdownSelectComponent.J.var_java_awt_Color_y), "wrap");
                    }
                    for (T option : options) {
                        String optionText = this.optionFormatter.format(option);
                        DropdownSelectOptionComponent optionComponent = new DropdownSelectOptionComponent(optionText, 0.85);
                        if (option instanceof DescribedOption) {
                            DescribedOption describedOption = (DescribedOption)option;
                            optionComponent.w(describedOption.getDescription());
                        }
                        if (option instanceof FontOption) {
                            FontOption fontOption = (FontOption)option;
                            optionComponent.setFontOption(fontOption);
                        }
                        if (option instanceof ModeOption) {
                            ModeOption modeOption = (ModeOption)option;
                            optionComponent.setShowNewBadge(modeOption.getProperty(PropertyContainer.NEW_BADGE));
                            optionComponent.setShowBetaBadge(modeOption.getProperty(PropertyContainer.BETA_BADGE));
                        }
                        optionComponent.o(this.optionsPanel.A() - 1.0);
                        optionComponent.Y(12.0);
                        optionComponent.setClickListener(() -> this.selectOption(option));
                        this.optionsPanel.h(optionComponent, "wrap");
                    }
                }
                this.optionsPanel.h(new SpacerComponent(0.0, 0.5), "wrap");
                this.optionsPanel.setExplicitHeight(Math.min(this.optionsPanel.d$src$D$ibccpu(), this.optionsPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y()));
                this.optionsPanel.H(true);
                this.popupFrame = ClientSettings.createPopup(this, this.optionsPanel, PopupFrame.class);
                this.popupFrame.addGlobalMouseListener(new DropdownPopupCloseClickHandler(this));
                ClientSettings.activeComponent = this;
                this.positionPopup();
            }
        }
    }

    private static List createUngroupedOptionList(String ignoredGroupName) {
        return new ArrayList();
    }

    public void addOption(T option) {
        this.optionsByGroup.computeIfAbsent(null, DropdownSelectComponent::createUngroupedOptionList).add(option);
    }

    private static List createGroupedOptionList(String ignoredGroupName) {
        return new ArrayList();
    }

    @Nullable
    public String getEmptyText() {
        return this.emptyText;
    }

    public boolean isExpanded() {
        return this.popupFrame != null;
    }

    public static String[] getLegacyState() {
        return legacyState;
    }

    public boolean isHighlightedStyle() {
        return this.highlightedStyle;
    }

    public void setSelectedValue(T selectedValue) {
        this.selectedValue = selectedValue;
        if (selectedValue instanceof DescribedOption) {
            DescribedOption describedOption = (DescribedOption)selectedValue;
            this.w(describedOption.getDescription());
        } else {
            this.w(null);
        }
    }

    private void positionPopup() {
        PopupFrame popupFrame = this.popupFrame;
        if (popupFrame != null) {
            popupFrame.K(this.G$src$D$1b2f02a() + this.getHorizontalInset());
            popupFrame.S(this.n() + 17.0);
        }
    }

    @Override
    public void F() {
        if (!this.pressed) {
            this.borderAnimation.J();
        }
        this.pressed = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void closePopup() {
        OptionTextFormatter<T> optionTextFormatter;
        OptionTextFormatter<T> optionTextFormatter2 = optionTextFormatter = this.optionFormatter;
        synchronized (optionTextFormatter2) {
            if (this.popupFrame != null) {
                ClientSettings.removePopup(this.popupFrame);
                this.popupFrame = null;
                if (ClientSettings.activeComponent == this) {
                    ClientSettings.activeComponent = null;
                }
            }
        }
    }

    public void setShowLabelPrefix(boolean showLabelPrefix) {
        this.showLabelPrefix = showLabelPrefix;
    }

    @Override
    public boolean isDisabled() {
        return this.disabled;
    }

    public String getLabel() {
        return this.label;
    }

    public @UnmodifiableView List<T> getOptions() {
        ArrayList<T> options = new ArrayList<T>();
        for (List<T> groupOptions : this.optionsByGroup.values()) {
            options.addAll(groupOptions);
        }
        return options;
    }

    public DropdownSelectComponent(String label, OptionTextFormatter<T> optionFormatter, T ... options) {
        this(label, optionFormatter, Arrays.asList(options));
    }

    public void setHighlightedStyle(boolean highlightedStyle) {
        this.highlightedStyle = highlightedStyle;
    }

    public T getSelectedValue() {
        return this.selectedValue;
    }

    public DropdownSelectComponent<T> addSelectionListener(DropdownSelectionListener selectionListener) {
        this.selectionListeners.add(selectionListener);
        return this;
    }

    public void addGroupedOption(String groupName, T option) {
        this.optionsByGroup.computeIfAbsent(groupName, DropdownSelectComponent::createGroupedOptionList).add(option);
    }

    public boolean isShowLabelPrefix() {
        return this.showLabelPrefix;
    }

    @Override
    public double C() {
        return 20.0;
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public DropdownSelectComponent(ModeValue modeValue) {
        this(modeValue.getName(), ModeEntryTextFormatter.DEFAULT, (Object[])modeValue.getModes());
        this.modeValue = modeValue;
        if (modeValue.isBindable()) {
            this.bindComponent = new BindableInputComponent(modeValue.getBind());
            this.addChildren(this.bindComponent);
            this.bindComponent.setVisible(false);
        }
        this.bindValue(modeValue);
    }

    public void addOptions(T ... options) {
        for (T option : options) {
            this.addOption(option);
        }
    }

    private void selectOption(T option) {
        this.selectedValue = option;
        this.onSelectionChanged();
        this.togglePopup();
        if (this.modeValue != null) {
            this.modeValue.setValue((ModeSelection)this.selectedValue);
            this.bindValue(this.modeValue);
        }
        for (DropdownSelectionListener selectionListener : this.selectionListeners) {
            selectionListener.onSelectionChanged();
        }
        if (this.modeValue != null && !this.modeValue.getDependentValues().isEmpty()) {
            this.getParentFrameComponent().l$src$V$1mibm4x();
        }
    }

    public DropdownSelectComponent(String label, OptionTextFormatter<T> optionFormatter, List<T> options) {
        this.getClass();
        this.borderAnimation = new ColorAnimation(0.15, DropdownSelectComponent.J.var_java_awt_Color_l, DropdownSelectComponent.J.var_java_awt_Color_y);
        this.showLabelPrefix = true;
        this.highlightedStyle = false;
        this.label = label;
        this.optionFormatter = optionFormatter;
        this.o(110.0);
        this.optionsByGroup.put(null, options);
        this.optionsPanel = new PanelComponent(110.0, 20.0);
        this.optionsPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.optionsPanel.t(120.0);
        this.optionsPanel.setShowDisabledOverlay(false);
        this.optionsPanel.setDisabledOverlayColor(DropdownSelectComponent.J.var_java_awt_Color_R);
        this.optionsPanel.I(true);
        this.setPropagateMouseEvents(true);
    }

    public void setEmptyText(@Nullable String emptyText) {
        this.emptyText = emptyText;
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
        if (this.inputSuppressed) {
            return;
        }
        PopupFrame popupFrame = this.popupFrame;
        if (popupFrame != null) {
            if (popupFrame.t()) {
                popupFrame.dispatchMouseEvent(mouseEvent);
            }
            if (!this.disabled && !popupFrame.t()) {
                this.togglePopup();
            }
            return;
        }
        if (!this.disabled) {
            this.togglePopup();
        }
    }

    static {
        DropdownSelectComponent.setLegacyState(new String[2]);
    }
}

