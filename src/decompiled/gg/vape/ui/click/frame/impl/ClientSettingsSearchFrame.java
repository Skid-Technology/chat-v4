/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.friend.ui.OnlineFriendsFrame;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.ColorDividerComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.module.ModuleComponent;
import gg.vape.ui.click.frame.ClickGuiQuickActionsComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameNavigationButtonComponent;
import gg.vape.ui.click.frame.ModuleCategoryNavigationButtonComponent;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrameClassOpenClickHandler;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrameHeader;
import gg.vape.ui.click.frame.impl.ClientSettingsSectionFrame;
import gg.vape.ui.click.frame.impl.FrameMacros;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrame;
import gg.vape.ui.click.frame.impl.profile.ProfilesFrameNavigationButtonComponent;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsFrame;
import gg.vape.ui.click.frame.impl.quickactions.QuickActionsFrame;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class ClientSettingsSearchFrame
extends ModuleCategoryFrame {
    private static final double MENU_WIDTH = 110.0;
    private final ClientSettingsSearchFrameHeader header;
    private final List<GuiComponent> defaultMenuComponents = new ArrayList<GuiComponent>();

    @Override
    public String getName() {
        return "GUI";
    }

    @Override
    public double A() {
        return 110.0;
    }

    private void addDefaultMenuComponent(GuiComponent component) {
        this.h(component, new Object[0]);
        this.defaultMenuComponents.add(component);
    }

    public void rebuildContent() {
        this.removeMarkedChildren();
        ModeSelection searchBarStyle = (ModeSelection)ClientSettings.INSTANCE.searchBarStyle.getValue();
        String query = this.header.getSearchInput().getText();
        if (!searchBarStyle.equals(ClientSettings.INSTANCE.integratedSearchBarMode) || query.isEmpty()) {
            this.populateDefaultMenu();
            return;
        }
        String normalizedQuery = StringUtils.y(query);
        for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
            String normalizedModuleName;
            boolean matchesQuery;
            if (mod.getCategory().equals(Category.NONE) || !(matchesQuery = (normalizedModuleName = StringUtils.y(mod.getName())).contains(normalizedQuery))) continue;
            ModuleComponent moduleComponent = new ModuleComponent(this, mod);
            this.h(moduleComponent, new Object[0]);
            moduleComponent.buildValueComponents();
        }
    }

    @Override
    public void dispatchMouseEvent(GuiMouseEvent event) {
        QuickActionsFrame quickActionsFrame = ClientSettings.getFrame(QuickActionsFrame.class);
        int transitionState = quickActionsFrame.Q$src$I$1o5zb27();
        if (transitionState == 3 || transitionState == 4) {
            return;
        }
        if (quickActionsFrame.V$src$Z$1xhop3l()) {
            quickActionsFrame.w(3);
            quickActionsFrame.U();
            return;
        }
        super.dispatchMouseEvent(event);
    }

    public ClientSettingsSearchFrame() {
        super(Category.NONE);
        this.setDisabledOverlayColor(ClientSettingsSearchFrame.J.r);
        this.K(32.0);
        this.S(32.0);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.header = new ClientSettingsSearchFrameHeader(this);
        this.Y(this.header);
        this.rebuildContent();
        this.L(false, true);
    }

    public ClientSettingsSearchFrameHeader getHeader() {
        return this.header;
    }

    @Override
    public void u() {
        try {
            ClientSettingsFrame settingsFrame = ClientSettings.getFrame(ClientSettingsFrame.class);
            ClientSettingsSectionFrame settingsSectionFrame = ClientSettings.getFrame(ClientSettingsSectionFrame.class);
            this.synchronizeWithSettingsFrames(settingsFrame, settingsSectionFrame);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private void synchronizeWithSettingsFrames(ClientSettingsFrame settingsFrame, ClientSettingsSectionFrame settingsSectionFrame) {
        boolean settingsVisible = settingsFrame.V$src$Z$1xhop3l();
        boolean settingsSectionVisible = settingsSectionFrame.V$src$Z$1xhop3l();
        if (!settingsVisible && !settingsSectionVisible) {
            if (!this.V$src$Z$1xhop3l()) {
                this.t(true, false);
            }
            return;
        }
        this.t(false, false);
        Frame visibleSettingsFrame = settingsVisible ? settingsFrame : settingsSectionFrame;
        this.M(visibleSettingsFrame.G$src$D$1b2f02a(), visibleSettingsFrame.n());
    }

    @Override
    public void J() {
        QuickActionsFrame quickActionsFrame = ClientSettings.getFrame(QuickActionsFrame.class);
        if (quickActionsFrame.V$src$Z$1xhop3l()) {
            return;
        }
        super.J();
    }

    private void populateDefaultMenu() {
        if (!this.defaultMenuComponents.isEmpty()) {
            for (GuiComponent component : this.defaultMenuComponents) {
                this.h(component, new Object[0]);
            }
            return;
        }
        this.addDefaultMenuComponent(new ColorDividerComponent(ClientSettingsSearchFrame.J.m));
        this.addDefaultMenuComponent(new ModuleCategoryNavigationButtonComponent("Combat", Category.COMBAT.getIconKey()));
        this.addDefaultMenuComponent(new ModuleCategoryNavigationButtonComponent("Render", Category.RENDER.getIconKey()));
        this.addDefaultMenuComponent(new ModuleCategoryNavigationButtonComponent("Utility", Category.UTILITY.getIconKey()));
        this.addDefaultMenuComponent(new ModuleCategoryNavigationButtonComponent("World", Category.WORLD.getIconKey()));
        this.addDefaultMenuComponent(new ModuleCategoryNavigationButtonComponent("Inventory", Category.INVENTORY.getIconKey()));
        this.addDefaultMenuComponent(new ModuleCategoryNavigationButtonComponent("Network", Category.NETWORK.getIconKey()));
        if (Vape.INSTANCE.isFeatureDisabled()) {
            this.addDefaultMenuComponent(new ModuleCategoryNavigationButtonComponent("Other", "other").Q(1));
        }
        this.addDefaultMenuComponent(new SpacerComponent(1.0, 2.0));
        this.addDefaultMenuComponent(new SimpleTextLabelComponent("  MISC", 0.625));
        this.addDefaultMenuComponent(new SpacerComponent(1.0, 2.0));
        this.addDefaultMenuComponent(new ColorDividerComponent(ClientSettingsSearchFrame.J.m));
        this.addDefaultMenuComponent(new FrameNavigationButtonComponent("Friends", null, OnlineFriendsFrame.class).addClickListener(new ClientSettingsSearchFrameClassOpenClickHandler(this, OnlineFriendsFrame.class)));
        this.addDefaultMenuComponent(new ProfilesFrameNavigationButtonComponent().addClickListener(new ClientSettingsSearchFrameClassOpenClickHandler(this, ProfilesSettingsFrame.class)));
        this.addDefaultMenuComponent(new FrameNavigationButtonComponent("Macros", null, FrameMacros.class).addClickListener(new ClientSettingsSearchFrameClassOpenClickHandler(this, FrameMacros.class)));
        this.addDefaultMenuComponent(new ColorDividerComponent(ClientSettingsSearchFrame.J.m));
        this.addDefaultMenuComponent(new ClickGuiQuickActionsComponent());
    }

    @Override
    public void t(JsonObject frameState) {
        super.t(frameState);
        this.rebuildContent();
        ClientSettings.refreshModuleCategoryHeaders();
    }

    @Override
    public void v() {
    }

    public void resetSearchAsync() {
        ClientSettings.UI_EXECUTOR.execute(this::resetSearch);
    }

    private void resetSearch() {
        this.header.getSearchInput().setText("");
        this.rebuildContent();
    }

    @Override
    public void Y() {
    }
}

