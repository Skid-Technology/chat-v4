/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.Vape;
import gg.vape.module.utility.inventory.cleaner.EmptyInventoryFilterCondition;
import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfile;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterAction;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionGroup;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterPreset;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.InventoryItemCategory;
import gg.vape.module.utility.inventory.cleaner.InventoryItemCategoryRegistry;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherPreset;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherPresetRegistry;
import gg.vape.module.utility.inventory.cleaner.ItemInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.SharedInventoryFilterPreset;
import gg.vape.module.utility.inventory.cleaner.SlotInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerPopupFrame;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryFilterPresetOptionComponent;
import gg.vape.module.utility.inventory.cleaner.ui.ItemFilterSelectionComponent;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.PopupMenuButtonComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.impl.profile.ProfileCreateActionButtonComponent;
import gg.vape.wrapper.impl.ItemStack;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class InventoryFilterRuleEditorPanel
extends PanelComponent {
    private final PanelComponent contentPanel;
    private final boolean nestedEditor;
    private final InventoryCleanerProfile profile;
    private final InventoryFilterRule rule;
    private TextButton actionButton;
    @Nullable
    private GlyphIconComponent backButton;

    public InventoryCleanerProfile getProfile() {
        return this.profile;
    }

    public boolean isNestedEditor() {
        return this.nestedEditor;
    }

    private void clearPreset() {
        this.rule.setPreset(null);
        this.renderContent();
    }

    private void createLocalRule() {
        InventoryFilterPreset inventoryFilterPreset = new InventoryFilterPreset(!(this.rule instanceof SlotInventoryFilterRule));
        inventoryFilterPreset.addConditionGroup(InventoryFilterConditionGroup.builder().addCondition(new EmptyInventoryFilterCondition()).build());
        InventoryCleanerPopupFrame.openRuleEditor(this.rule, inventoryFilterPreset, inventoryFilterPreset, true);
    }

    private void editPresetCopy(InventoryFilterPreset inventoryFilterPreset) {
        InventoryCleanerPopupFrame.openRuleEditor(this.rule, inventoryFilterPreset, inventoryFilterPreset.copy(), false);
    }

    private PanelComponent createHeader(double width, String title) {
        PanelComponent panelComponent = new PanelComponent(width, 28.0);
        panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        panelComponent.setShowDisabledOverlay(false);
        panelComponent.h(new SpacerComponent(0.0, 10.0), new Object[0]);
        if (this.nestedEditor) {
            GuiClickListener existingBackClick = this.backButton != null ? (!this.backButton.getClickListeners().isEmpty() ? this.backButton.getClickListeners().get(0) : null) : null;
            this.backButton = new GlyphIconComponent("back-hover@2x", 6.0, 6.0, 10.0, 10.0, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_W, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_f, null);
            if (existingBackClick != null) {
                this.backButton.addClickListener(existingBackClick);
            }
            this.backButton.setCenterVertically(true);
            this.backButton.setCenterHorizontally(true);
            panelComponent.h(new SpacerComponent(8.0, 0.0), "widthwrap");
            panelComponent.h(this.backButton, "widthwrap");
        } else {
            panelComponent.h(new SpacerComponent(18.0, 0.0), "widthwrap");
        }
        WrappingTextLabelComponent titleLabel = new WrappingTextLabelComponent(title, 0.9, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Z);
        titleLabel.o(panelComponent.A() - 36.0);
        titleLabel.setBold(true);
        titleLabel.setAcceptsMouseInput(false);
        panelComponent.h(titleLabel, "widthwrap");
        return panelComponent;
    }

    private String getSelectionName(InventoryFilterRule inventoryFilterRule) {
        ItemStack itemStack = inventoryFilterRule.getItemSelection().getItemStack();
        InventoryItemMatcher inventoryItemMatcher = inventoryFilterRule.getItemSelection().getMatcher();
        if (itemStack != null && !itemStack.isNull()) {
            return itemStack.x();
        }
        if (inventoryItemMatcher != null) {
            return inventoryItemMatcher.getName();
        }
        return "Unknown";
    }

    public InventoryFilterRule getRule() {
        return this.rule;
    }

    private void editLocalPreset(InventoryFilterPreset inventoryFilterPreset) {
        InventoryCleanerPopupFrame.openRuleEditor(this.rule, inventoryFilterPreset, inventoryFilterPreset.copy(), false);
    }

    private void setFilterAction(ItemInventoryFilterRule itemInventoryFilterRule, InventoryFilterAction inventoryFilterAction) {
        itemInventoryFilterRule.setAction(inventoryFilterAction);
        this.renderContent();
    }

    @Nullable
    public GlyphIconComponent getBackButton() {
        return this.backButton;
    }

    public TextButton getActionButton() {
        return this.actionButton;
    }

    private void renderContent() {
        String string;
        this.contentPanel.t$src$V$zbu1jn();
        String headerTitle = this.rule instanceof SlotInventoryFilterRule ? "Slot " + (((SlotInventoryFilterRule)this.rule).getSlot() + 1) : "Filter";
        this.contentPanel.h(this.createHeader(this.A(), headerTitle), new Object[0]);
        this.contentPanel.h(new SpacerComponent(this.A(), 0.0), new Object[0]);
        this.contentPanel.h(new SpacerComponent(9.0, 0.0), "widthwrap");
        this.contentPanel.h(new SpacerComponent(0.0, 5.0), new Object[0]);
        PanelComponent panelComponent = new PanelComponent(32.0, 32.0);
        panelComponent.setOutlineColor(InventoryFilterRuleEditorPanel.J.var_java_awt_Color_y);
        panelComponent.setBorderWidth(0.5f);
        panelComponent.setCornerRadius(4.0f);
        GuiComponent guiComponent = new ItemFilterSelectionComponent(this.rule);
        guiComponent.o(32.0);
        guiComponent.Y(32.0);
        panelComponent.h(guiComponent, new Object[0]);
        this.contentPanel.h(new SpacerComponent((this.A() - panelComponent.A()) / 2.0, 0.0), "widthwrap");
        this.contentPanel.h(panelComponent, new Object[0]);
        this.contentPanel.h(new SpacerComponent(0.0, 5.0), new Object[0]);
        guiComponent = new WrappingTextLabelComponent(this.getSelectionName(this.rule), 1.0, Color.WHITE);
        ((SimpleTextLabelComponent)guiComponent).setBold(true);
        guiComponent.o(this.contentPanel.A());
        this.contentPanel.h(guiComponent, new Object[0]);
        WrappingTextLabelComponent wrappingTextLabelComponent = new WrappingTextLabelComponent("ID: " + this.rule.getItemSelection().getItemName(), 0.75, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_h);
        wrappingTextLabelComponent.setBold(true);
        wrappingTextLabelComponent.o(this.contentPanel.A());
        wrappingTextLabelComponent.setExtraHeight(0);
        this.contentPanel.h(wrappingTextLabelComponent, new Object[0]);
        this.contentPanel.h(new SpacerComponent(0.0, 5.0), new Object[0]);
        SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("Item rule", 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A);
        simpleTextLabelComponent.setBold(true);
        simpleTextLabelComponent.setExtraHeight(5);
        this.contentPanel.h(new PaddedComponent(6.0, 0.0, simpleTextLabelComponent), new Object[0]);
        InventoryFilterPreset inventoryFilterPreset = this.rule.resolvePreset();
        if (inventoryFilterPreset != null) {
            String string2;
            ProfileCreateActionButtonComponent interactiveComponent;
            boolean sharedPreset = inventoryFilterPreset instanceof SharedInventoryFilterPreset;
            ArrayList<SharedInventoryFilterPreset> availablePresets = new ArrayList<SharedInventoryFilterPreset>(this.rule instanceof SlotInventoryFilterRule ? Vape.INSTANCE.getInventoryFilterPresetRegistry().getSlotRulePresets().getAll() : Vape.INSTANCE.getInventoryFilterPresetRegistry().getItemRulePresets().getAll());
            availablePresets.remove(inventoryFilterPreset);
            availablePresets.add(0, (SharedInventoryFilterPreset)inventoryFilterPreset);
            ArrayList<GuiComponent> presetOptions = new ArrayList<GuiComponent>();
            if (sharedPreset) {
                interactiveComponent = new ProfileCreateActionButtonComponent("New Rule", false, true, 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A, "newadd", 0.75, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_B, null);
                interactiveComponent.setHoverBackgroundVisible(false);
                interactiveComponent.setHorizontalInset(8.0);
                interactiveComponent.setClickListener(this::createLocalRule);
                presetOptions.add(interactiveComponent);
                if (!availablePresets.isEmpty()) {
                    presetOptions.add(new SimpleTextLabelComponent("PRESETS", 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_C, true));
                    InventoryItemMatcherPreset inventoryItemMatcherPreset = InventoryItemMatcherPresetRegistry.NO_RULE;
                    InventoryFilterPresetOptionComponent inventoryFilterPresetOptionComponent = new InventoryFilterPresetOptionComponent(inventoryItemMatcherPreset, false);
                    inventoryFilterPresetOptionComponent.setClickListener(this::clearPreset);
                    presetOptions.add(inventoryFilterPresetOptionComponent);
                }
                for (InventoryFilterPreset inventoryFilterPreset2 : availablePresets) {
                    InventoryFilterPresetOptionComponent presetOption = new InventoryFilterPresetOptionComponent(inventoryFilterPreset2, inventoryFilterPreset2.equals(inventoryFilterPreset));
                    presetOption.getEditIcon().setClickListener(() -> this.editPresetCopy(preset));
                    presetOption.setClickListener(() -> this.replacePreset(inventoryFilterPreset, preset));
                    presetOptions.add(presetOption);
                }
            } else {
                interactiveComponent = new ProfileCreateActionButtonComponent("Edit local rule", false, true, 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A, "newedit", 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_B, null);
                interactiveComponent.setHoverBackgroundVisible(false);
                interactiveComponent.setClickListener(() -> this.editLocalPreset(inventoryFilterPreset));
                ProfileCreateActionButtonComponent profileCreateActionButtonComponent = new ProfileCreateActionButtonComponent("Delete local rule", false, true, 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A, "newtrash", 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_B, null);
                profileCreateActionButtonComponent.setHoverBackgroundVisible(false);
                profileCreateActionButtonComponent.setClickListener(this::clearRulePreset);
                ProfileCreateActionButtonComponent profileCreateActionButtonComponent2 = new ProfileCreateActionButtonComponent("Create preset", false, true, 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A, "newadd", 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_B, null);
                profileCreateActionButtonComponent2.setHoverBackgroundVisible(false);
                profileCreateActionButtonComponent2.setClickListener(() -> this.sharePreset(inventoryFilterPreset));
                presetOptions.add(interactiveComponent);
                presetOptions.add(profileCreateActionButtonComponent);
                presetOptions.add(profileCreateActionButtonComponent2);
            }
            PopupMenuButtonComponent popupMenuButtonComponent = new PopupMenuButtonComponent(inventoryFilterPreset.getName(), presetOptions, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Q, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Q, null, 0.0f, 0.0f);
            popupMenuButtonComponent.o(88.0);
            popupMenuButtonComponent.Y(14.0);
            popupMenuButtonComponent.setCenterLabel(false);
            popupMenuButtonComponent.setUseContentInset(false);
            popupMenuButtonComponent.setEnabled(false);
            popupMenuButtonComponent.setDividerColor(null);
            this.contentPanel.h(new SpacerComponent((this.A() - popupMenuButtonComponent.A()) / 2.0, 0.0), "widthwrap");
            this.contentPanel.h(popupMenuButtonComponent, "widthwrap");
            this.contentPanel.h(new SpacerComponent(0.0, 18.0), new Object[0]);
            if (this.rule instanceof SlotInventoryFilterRule) {
                InventoryItemCategory inventoryItemCategory = this.rule.getPriority();
                List<InventoryItemCategory> categories = InventoryItemCategoryRegistry.findCompatible(this.rule.getItemSelection());
                ArrayList<GuiComponent> categoryButtons = new ArrayList<GuiComponent>();
                for (InventoryItemCategory category : categories) {
                    TextButton textButton = new TextButton(category.getName(), 0.75, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_t, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_M, 0.0, 0.0);
                    textButton.w(category.getDisplayName());
                    textButton.setDeriveTextColorFromBackground(false);
                    textButton.setNormalTextColor(InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A);
                    textButton.setCornerRadius(0.0f);
                    categoryButtons.add(textButton);
                    textButton.addClickListener(() -> this.setPriority(category));
                }
                PopupMenuButtonComponent categoryPopup = new PopupMenuButtonComponent(inventoryItemCategory.getName(), categoryButtons, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Q, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Q, null, 0.0f, 0.0f);
                categoryPopup.o(88.0);
                categoryPopup.Y(14.0);
                categoryPopup.setCenterLabel(false);
                categoryPopup.setUseContentInset(false);
                categoryPopup.setEnabled(false);
                categoryPopup.setDividerColor(null);
                SimpleTextLabelComponent priorityLabel = new SimpleTextLabelComponent("Prioritization", 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A);
                priorityLabel.setBold(true);
                priorityLabel.setExtraHeight(5);
                this.contentPanel.h(new PaddedComponent(6.0, 0.0, priorityLabel), new Object[0]);
                this.contentPanel.h(new SpacerComponent((this.A() - categoryPopup.A()) / 2.0, 0.0), "widthwrap");
                this.contentPanel.h(categoryPopup, new Object[0]);
            } else if (this.rule instanceof ItemInventoryFilterRule) {
                ItemInventoryFilterRule itemInventoryFilterRule = (ItemInventoryFilterRule)this.rule;
                InventoryFilterAction selectedAction = itemInventoryFilterRule.getAction();
                ArrayList<GuiComponent> actionButtons = new ArrayList<GuiComponent>();
                for (InventoryFilterAction action : InventoryFilterAction.VALUES) {
                    TextButton textButton = new TextButton(action.getName(), 0.75, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_t, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_M, 0.0, 0.0);
                    textButton.w(action.getDescription());
                    textButton.setDeriveTextColorFromBackground(false);
                    textButton.setNormalTextColor(InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A);
                    textButton.setCornerRadius(0.0f);
                    actionButtons.add(textButton);
                    textButton.addClickListener(() -> this.setFilterAction(itemRule, action));
                }
                PopupMenuButtonComponent actionPopup = new PopupMenuButtonComponent(selectedAction.getName(), actionButtons, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Q, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Q, null, 0.0f, 0.0f);
                actionPopup.o(88.0);
                actionPopup.Y(14.0);
                actionPopup.setCenterLabel(false);
                actionPopup.setUseContentInset(false);
                actionPopup.setEnabled(false);
                actionPopup.setDividerColor(null);
                SimpleTextLabelComponent actionTitle = new SimpleTextLabelComponent("Action", 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A);
                actionTitle.setBold(true);
                actionTitle.setExtraHeight(5);
                this.contentPanel.h(new PaddedComponent(6.0, 0.0, actionTitle), new Object[0]);
                this.contentPanel.h(new SpacerComponent((this.A() - actionPopup.A()) / 2.0, 0.0), "widthwrap");
                this.contentPanel.h(actionPopup, new Object[0]);
            } else {
                this.contentPanel.h(new SpacerComponent(0.0, 25.0), new Object[0]);
            }
            this.contentPanel.h(new SpacerComponent(0.0, 8.0), new Object[0]);
            String string3 = string2 = this.nestedEditor ? "CONFIRM" : "REMOVE";
            GuiClickListener existingAction = this.actionButton != null ? (!this.actionButton.getClickListeners().isEmpty() ? this.actionButton.getClickListeners().get(0) : null) : null;
            this.actionButton = new TextButton(string2, 0.7, this.nestedEditor ? InventoryFilterRuleEditorPanel.J.var_java_awt_Color_B : InventoryFilterRuleEditorPanel.J.var_java_awt_Color_d, this.nestedEditor ? InventoryFilterRuleEditorPanel.J.var_java_awt_Color_O : InventoryFilterRuleEditorPanel.J.var_java_awt_Color_c);
            if (existingAction != null) {
                this.actionButton.addClickListener(existingAction);
            }
            this.actionButton.o(this.nestedEditor ? 56.0 : 68.0);
            this.actionButton.Y(16.0);
            this.actionButton.setNormalTextColor(Color.WHITE);
            this.actionButton.setDeriveTextColorFromBackground(false);
            this.contentPanel.h(new SpacerComponent((this.A() - this.actionButton.A()) / 2.0, 0.0), "widthwrap");
            this.contentPanel.h(this.actionButton, new Object[0]);
            return;
        }
        ArrayList<SharedInventoryFilterPreset> availablePresets = new ArrayList<SharedInventoryFilterPreset>(this.rule instanceof SlotInventoryFilterRule ? Vape.INSTANCE.getInventoryFilterPresetRegistry().getSlotRulePresets().getAll() : Vape.INSTANCE.getInventoryFilterPresetRegistry().getItemRulePresets().getAll());
        ArrayList<GuiComponent> presetOptions = new ArrayList<GuiComponent>();
        ProfileCreateActionButtonComponent interactiveComponent = new ProfileCreateActionButtonComponent("New Rule", false, true, 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A, "newadd", 0.75, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_B, null);
        interactiveComponent.setHoverBackgroundVisible(false);
        interactiveComponent.setHorizontalInset(8.0);
        interactiveComponent.setClickListener(this::createLocalRule);
        presetOptions.add(interactiveComponent);
        if (!availablePresets.isEmpty()) {
            presetOptions.add(new SimpleTextLabelComponent("PRESETS", 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_C, true));
            InventoryItemMatcherPreset inventoryItemMatcherPreset = InventoryItemMatcherPresetRegistry.NO_RULE;
            InventoryFilterPresetOptionComponent inventoryFilterPresetOptionComponent = new InventoryFilterPresetOptionComponent(inventoryItemMatcherPreset, true);
            inventoryFilterPresetOptionComponent.setClickListener(this::clearPreset);
            presetOptions.add(inventoryFilterPresetOptionComponent);
        }
        for (InventoryFilterPreset inventoryFilterPreset3 : availablePresets) {
            InventoryFilterPresetOptionComponent inventoryFilterPresetOptionComponent = new InventoryFilterPresetOptionComponent(inventoryFilterPreset3, inventoryFilterPreset3.equals(inventoryFilterPreset));
            inventoryFilterPresetOptionComponent.getEditIcon().setClickListener(() -> this.editPresetCopy(preset));
            inventoryFilterPresetOptionComponent.setClickListener(() -> this.replacePreset(inventoryFilterPreset, preset));
            presetOptions.add(inventoryFilterPresetOptionComponent);
        }
        PopupMenuButtonComponent presetPopup = new PopupMenuButtonComponent("No rule", presetOptions, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Q, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Q, null, 0.0f, 0.0f);
        presetPopup.o(88.0);
        presetPopup.Y(14.0);
        presetPopup.setCenterLabel(false);
        presetPopup.setUseContentInset(false);
        presetPopup.setEnabled(false);
        presetPopup.setDividerColor(null);
        this.contentPanel.h(new SpacerComponent((this.A() - presetPopup.A()) / 2.0, 0.0), "widthwrap");
        this.contentPanel.h(presetPopup, "widthwrap");
        this.contentPanel.h(new SpacerComponent(0.0, 18.0), new Object[0]);
        if (this.rule instanceof SlotInventoryFilterRule) {
            InventoryItemCategory inventoryItemCategory = this.rule.getPriority();
            List<InventoryItemCategory> list = InventoryItemCategoryRegistry.findCompatible(this.rule.getItemSelection());
            ArrayList<GuiComponent> categoryButtons = new ArrayList<GuiComponent>();
            for (InventoryItemCategory inventoryItemCategory2 : list) {
                TextButton textButton = new TextButton(inventoryItemCategory2.getName(), 0.75, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_t, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_M, 0.0, 0.0);
                textButton.w(inventoryItemCategory2.getDisplayName());
                textButton.setDeriveTextColorFromBackground(false);
                textButton.setNormalTextColor(InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A);
                textButton.setCornerRadius(0.0f);
                categoryButtons.add(textButton);
                textButton.addClickListener(() -> this.setPriority(inventoryItemCategory2));
            }
            PopupMenuButtonComponent categoryPopup = new PopupMenuButtonComponent(inventoryItemCategory.getName(), categoryButtons, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Q, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Q, null, 0.0f, 0.0f);
            categoryPopup.o(88.0);
            categoryPopup.Y(14.0);
            categoryPopup.setCenterLabel(false);
            categoryPopup.setUseContentInset(false);
            categoryPopup.setEnabled(false);
            categoryPopup.setDividerColor(null);
            SimpleTextLabelComponent simpleTextLabelComponent2 = new SimpleTextLabelComponent("Prioritization", 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A);
            simpleTextLabelComponent2.setBold(true);
            simpleTextLabelComponent2.setExtraHeight(5);
            this.contentPanel.h(new PaddedComponent(6.0, 0.0, simpleTextLabelComponent2), new Object[0]);
            this.contentPanel.h(new SpacerComponent((this.A() - categoryPopup.A()) / 2.0, 0.0), "widthwrap");
            this.contentPanel.h(categoryPopup, new Object[0]);
        } else if (this.rule instanceof ItemInventoryFilterRule) {
            ItemInventoryFilterRule itemInventoryFilterRule = (ItemInventoryFilterRule)this.rule;
            InventoryFilterAction inventoryFilterAction = itemInventoryFilterRule.getAction();
            ArrayList<GuiComponent> actionButtons = new ArrayList<GuiComponent>();
            for (InventoryFilterAction inventoryFilterAction2 : InventoryFilterAction.VALUES) {
                TextButton textButton = new TextButton(inventoryFilterAction2.getName(), 0.75, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_t, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_M, 0.0, 0.0);
                textButton.w(inventoryFilterAction2.getDescription());
                textButton.setDeriveTextColorFromBackground(false);
                textButton.setNormalTextColor(InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A);
                textButton.setCornerRadius(0.0f);
                actionButtons.add(textButton);
                textButton.addClickListener(() -> this.setFilterAction(itemRule, inventoryFilterAction2));
            }
            PopupMenuButtonComponent popupMenuButtonComponent8 = new PopupMenuButtonComponent(inventoryFilterAction.getName(), actionButtons, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Q, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_Q, null, 0.0f, 0.0f);
            popupMenuButtonComponent8.o(88.0);
            popupMenuButtonComponent8.Y(14.0);
            popupMenuButtonComponent8.setCenterLabel(false);
            popupMenuButtonComponent8.setUseContentInset(false);
            popupMenuButtonComponent8.setEnabled(false);
            popupMenuButtonComponent8.setDividerColor(null);
            SimpleTextLabelComponent simpleTextLabelComponent3 = new SimpleTextLabelComponent("Action", 0.7, InventoryFilterRuleEditorPanel.J.var_java_awt_Color_A);
            simpleTextLabelComponent3.setBold(true);
            simpleTextLabelComponent3.setExtraHeight(5);
            this.contentPanel.h(new PaddedComponent(6.0, 0.0, simpleTextLabelComponent3), new Object[0]);
            this.contentPanel.h(new SpacerComponent((this.A() - popupMenuButtonComponent8.A()) / 2.0, 0.0), "widthwrap");
            this.contentPanel.h(popupMenuButtonComponent8, new Object[0]);
        } else {
            this.contentPanel.h(new SpacerComponent(0.0, 25.0), new Object[0]);
        }
        this.contentPanel.h(new SpacerComponent(0.0, 8.0), new Object[0]);
        String string4 = string = this.nestedEditor ? "CONFIRM" : "REMOVE";
        GuiClickListener guiClickListener = this.actionButton != null ? (!this.actionButton.getClickListeners().isEmpty() ? this.actionButton.getClickListeners().get(0) : null) : null;
        this.actionButton = new TextButton(string, 0.7, this.nestedEditor ? InventoryFilterRuleEditorPanel.J.var_java_awt_Color_B : InventoryFilterRuleEditorPanel.J.var_java_awt_Color_d, this.nestedEditor ? InventoryFilterRuleEditorPanel.J.var_java_awt_Color_O : InventoryFilterRuleEditorPanel.J.var_java_awt_Color_c);
        if (guiClickListener != null) {
            this.actionButton.addClickListener(guiClickListener);
        }
        this.actionButton.o(this.nestedEditor ? 56.0 : 68.0);
        this.actionButton.Y(16.0);
        this.actionButton.setNormalTextColor(Color.WHITE);
        this.actionButton.setDeriveTextColorFromBackground(false);
        this.contentPanel.h(new SpacerComponent((this.A() - this.actionButton.A()) / 2.0, 0.0), "widthwrap");
        this.contentPanel.h(this.actionButton, new Object[0]);
    }

    private void replacePreset(InventoryFilterPreset inventoryFilterPreset, InventoryFilterPreset inventoryFilterPreset2) {
        if (inventoryFilterPreset != null && inventoryFilterPreset.equals(inventoryFilterPreset2)) {
            InventoryCleanerPopupFrame.openRuleEditor(this.rule, inventoryFilterPreset2, inventoryFilterPreset2.copy(), false);
            return;
        }
        this.rule.setPreset(inventoryFilterPreset2);
        if (this.rule.resolvePreset() != null) {
            this.renderContent();
        }
    }

    private void sharePreset(InventoryFilterPreset inventoryFilterPreset) {
        SharedInventoryFilterPreset sharedInventoryFilterPreset = inventoryFilterPreset.shareForRule(this.rule);
        this.rule.setPreset(sharedInventoryFilterPreset);
        this.renderContent();
    }

    private void clearRulePreset() {
        this.rule.setPreset(null);
        this.renderContent();
    }

    public InventoryFilterRuleEditorPanel(InventoryCleanerProfile inventoryCleanerProfile, InventoryFilterRule inventoryFilterRule, boolean nestedEditor) {
        super(108.0, 182.0);
        this.profile = inventoryCleanerProfile;
        this.rule = inventoryFilterRule;
        this.nestedEditor = nestedEditor;
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.setDisabledOverlayColor(InventoryFilterRuleEditorPanel.J.var_java_awt_Color_H);
        double panelHeight = this.L();
        this.contentPanel = new PanelComponent(this.A(), panelHeight);
        this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.contentPanel.setShowDisabledOverlay(false);
        this.renderContent();
        this.h(this.contentPanel, new Object[0]);
    }

    private void setPriority(InventoryItemCategory inventoryItemCategory) {
        this.rule.setPriorityOverride(inventoryItemCategory);
        this.renderContent();
    }
}

