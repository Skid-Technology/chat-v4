/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.none;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.config.PublicProfileSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.friend.ui.EnemySettingsFrame;
import gg.vape.friend.ui.OnlineActivitySettingsFrame;
import gg.vape.friend.ui.OnlineCombatStatsSettingsFrame;
import gg.vape.friend.ui.OnlineFriendsFrame;
import gg.vape.friend.ui.OnlinePlayerPreviewSettingsFrame;
import gg.vape.friend.ui.OnlineRadarSettingsFrame;
import gg.vape.input.KeyboardInput;
import gg.vape.input.MouseInput;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.blatant.invwalk.InvWalkSettingsState;
import gg.vape.module.none.ClientSettingsBooleanValue;
import gg.vape.module.none.ClientSettingsGuiBindBendable;
import gg.vape.module.none.TextGuiSettingsFrame;
import gg.vape.module.utility.inventory.HotbarSlotRuleItemPickerFrame;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerPopupFrame;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryFilterRuleEditorFrame;
import gg.vape.notification.NotificationManager;
import gg.vape.render.ShaderGroupRenderStateManager;
import gg.vape.tutorial.TutorialFrame;
import gg.vape.tutorial.TutorialState;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.component.AnimatedIconButtonComponent;
import gg.vape.ui.click.component.DropdownSelectComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PopupMenuButtonComponent;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.ToolTips;
import gg.vape.ui.click.component.value.ListValueDropdownLayer;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameStackManager;
import gg.vape.ui.click.frame.PopupFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSectionFrame;
import gg.vape.ui.click.frame.impl.FrameMacros;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrame;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeader;
import gg.vape.ui.click.frame.impl.ModuleSearchFrame;
import gg.vape.ui.click.frame.impl.SessionSpoofFrame;
import gg.vape.ui.click.frame.impl.VisibleModuleListFrame;
import gg.vape.ui.click.frame.impl.hud.ActiveModuleStackFrame;
import gg.vape.ui.click.frame.impl.hud.AnchoredHudModuleConfigFrame;
import gg.vape.ui.click.frame.impl.hud.ArmorStatusHudFrame;
import gg.vape.ui.click.frame.impl.hud.ClockHudFrame;
import gg.vape.ui.click.frame.impl.hud.CompassHudFrame;
import gg.vape.ui.click.frame.impl.hud.CoordinatesHudFrame;
import gg.vape.ui.click.frame.impl.hud.FpsDisplayHudFrame;
import gg.vape.ui.click.frame.impl.hud.HudEditorReturnToMainLayerFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.click.frame.impl.hud.HudModuleFrameBase;
import gg.vape.ui.click.frame.impl.hud.HudModuleOverviewFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleSelectorFrame;
import gg.vape.ui.click.frame.impl.hud.HudSettingsFrameBase;
import gg.vape.ui.click.frame.impl.hud.InventoryOverlaySettingsFrame;
import gg.vape.ui.click.frame.impl.hud.KeystrokesHudFrame;
import gg.vape.ui.click.frame.impl.hud.PotionEffectsHudFrame;
import gg.vape.ui.click.frame.impl.hud.ReachDisplayHudFrame;
import gg.vape.ui.click.frame.impl.hud.ScoreboardHudFrame;
import gg.vape.ui.click.frame.impl.main.ClickGuiFrameManager;
import gg.vape.ui.click.frame.impl.main.ClickGuiLayer;
import gg.vape.ui.click.frame.impl.profile.ProfileSnapshotFrame;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsFrame;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrame;
import gg.vape.ui.click.frame.impl.quickactions.QuickActionsFrame;
import gg.vape.ui.click.frame.impl.target.TargetInfoSettingsFrame;
import gg.vape.ui.font.FontManager;
import gg.vape.ui.font.FontSelector;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.Bendable;
import gg.vape.unmap.ColorUtil;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.MutableColor;
import gg.vape.utils.ThreadBoundExecutor;
import gg.vape.utils.render.BlurRegionRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import java.awt.Point;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientSettings
extends Mod {
    public final BooleanValue showLegitMode;
    private boolean initialized = false;
    public static ClientSettingsSearchFrame settingsSearchFrame;
    public static ClickGuiFrameManager clickGuiFrameManager;
    public final BooleanValue showEnabledCount;
    public final ModeOption floatingSearchBarMode;
    public static GuiComponent activeComponent;
    private float rainbowHue = 0.0f;
    public final BooleanValue showTooltips;
    private double lastGuiScale = -1.0;
    public static boolean moduleSearchActive;
    public final BooleanValue guiBindIndicator;
    public boolean inputEnabled = true;
    public final BooleanValue blurBackground = BooleanValue.create(this, "Blur background", true, "Blur the background of the GUI");
    public static ModuleCategoryFrame legitModuleCategoryFrame;
    private final Set<Frame> framesToUpdate;
    private static RectData lastScreenRect;
    public static FrameStackManager hotbarRuleEditorStack;
    public static ClientSettings INSTANCE;
    public final BooleanValue multiKeybinding = BooleanValue.create(this, "Enable Multi-Keybinding", false, "Allows you to set multiple keys to be held together to toggle modules");
    public final BooleanValue allowSettingKeybinds = BooleanValue.create(this, "Allow setting keybinds", false);
    private FrameStackManager activeStack;
    public final NumberValue rainbowSpeed;
    public final ModeValue language;
    public static ModuleSearchFrame moduleSearchFrame;
    public final ModeOption integratedSearchBarMode;
    private static final List<FrameStackManager> allStacks;
    private static HashSet<Frame> positionedFrames;
    public static FrameStackManager sessionSpoofStack;
    public static boolean framesInitialized;
    public static final ThreadBoundExecutor UI_EXECUTOR;
    public static FrameStackManager inventoryCleanerStack;
    public final ModeOption noSearchBarMode;
    private static ImmutableList<Frame> frameSnapshot;
    public final ModeValue searchBarStyle;
    public static FrameStackManager mainStack;
    public final BooleanValue smoothFont;
    private final BlurRegionRenderer blurRenderer;
    private static final HashMap<Class<?>, Frame> frameCache;
    public static ToolTips activeTooltips;
    private boolean savedChatKeyState;
    public static FrameStackManager publicProfilesStack;
    private Frame pendingFrame;
    public static FrameStackManager hudEditorStack;
    public static FrameStackManager profileSnapshotStack;
    private boolean layoutRefreshPending;
    private static final List<Frame> allFrames;

    private void renderFrames() {
        for (Frame frame : allFrames) {
            if (!frame.V$src$Z$1xhop3l() || !frame.y$src$Z$1f55jvh()) continue;
            frame.q$src$V$1x8c1kv();
        }
    }

    public static Frame findFrameByName(String name) {
        for (Frame frame : allFrames) {
            if (frame.getName() == null || !frame.getName().equalsIgnoreCase(name)) continue;
            return frame;
        }
        return null;
    }

    private void applyStandaloneMode(PublicProfileSettings publicProfileSettings) {
        if (this.activeStack == mainStack || this.activeStack == clickGuiFrameManager) {
            if (publicProfileSettings.centralGuiStyle.isSelected()) {
                if (this.activeStack == clickGuiFrameManager) {
                    return;
                }
                if (clickGuiFrameManager.getMainFrame() == null) {
                    clickGuiFrameManager = new ClickGuiFrameManager();
                }
                this.switchFrameStack(clickGuiFrameManager);
            } else {
                if (this.activeStack == mainStack) {
                    return;
                }
                this.switchFrameStack(mainStack);
            }
        }
    }

    public static void positionFrameIfNeeded(Frame frame) {
        boolean overlapsExistingFrame;
        if (frameSnapshot == null) {
            return;
        }
        if (!frameSnapshot.contains(frame)) {
            return;
        }
        if (positionedFrames.contains(frame)) {
            return;
        }
        if (!frame.J$src$Z$1eqdghz() || !frame.l$src$Z$193vdc5()) {
            return;
        }
        double candidateX = 32.0;
        double candidateY = 32.0;
        do {
            overlapsExistingFrame = false;
            for (Frame otherFrame : frameSnapshot) {
                RectData bounds;
                if (!ClientSettings.INSTANCE.activeStack.Y().contains(otherFrame) || otherFrame.equals(frame) || !otherFrame.V$src$Z$1xhop3l() || !(bounds = otherFrame.getBounds().y(2.0, 4.0)).J(candidateX, candidateY)) continue;
                overlapsExistingFrame = true;
            }
            if (!((candidateX += 2.0) + frame.A() > (double)Minecraft.G().getScaledWidth())) continue;
            candidateX = 32.0;
            candidateY += 2.0;
        } while (overlapsExistingFrame);
        frame.K(candidateX);
        frame.S(candidateY);
        frame.l$src$V$1mibm4x();
        positionedFrames.add(frame);
    }

    @Deprecated
    public static void setFrameVisibility(Class<? extends Frame> frameClass, boolean animate) {
        Frame frame = ClientSettings.getFrame(frameClass);
        if (frame != null) {
            frame.t(!frame.V$src$Z$1xhop3l(), animate);
            frame.U();
        }
    }

    public static void refreshCategoryHeader(Category category) {
        for (Frame frame : allFrames) {
            if (!((ModeSelection)ClientSettings.INSTANCE.searchBarStyle.getValue()).equals(ClientSettings.INSTANCE.integratedSearchBarMode) && frame instanceof ClientSettingsSearchFrame || !(frame instanceof ModuleCategoryFrame) || !((ModuleCategoryFrame)frame).G$src$Lgg_vape_module_Category_$qyt4o7().equals(category)) continue;
            int enabledModuleCount = ((ModuleCategoryFrame)frame).A$src$I$wwnvku();
            if (!(frame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() instanceof ModuleCategoryFrameHeader)) continue;
            ((ModuleCategoryFrameHeader)frame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc()).y(enabledModuleCount);
        }
    }

    public boolean handleSearchShortcut(EventKeyPress eventKeyPress) {
        int keyCode = eventKeyPress.getKey();
        boolean keyDown = eventKeyPress.isDown();
        if ((KeyboardInput.isKeyDown(163) || KeyboardInput.isKeyDown(162)) && keyCode == 70 && keyDown) {
            ClickGuiFrameManager clickGuiFrameManager;
            if (this.activeStack instanceof ClickGuiFrameManager && (clickGuiFrameManager = (ClickGuiFrameManager)this.activeStack).getMainFrame() != null) {
                return clickGuiFrameManager.getMainFrame().clearActiveSearch();
            }
            if (!this.noSearchBarMode.isSelected()) {
                if (this.floatingSearchBarMode.isSelected()) {
                    moduleSearchFrame.n$src$Lgg_vape_ui_click_frame_impl_ModuleSearchFrameHe$xia8v2().s();
                    moduleSearchFrame.n$src$Lgg_vape_ui_click_frame_impl_ModuleSearchFrameHe$xia8v2().A$src$Lgg_vape_ui_click_component_input_ModuleSearchIn$1efzz7n().requestFocus();
                } else if (this.integratedSearchBarMode.isSelected()) {
                    settingsSearchFrame.getHeader().getSearchInput().setText("");
                    settingsSearchFrame.rebuildContent();
                    settingsSearchFrame.getHeader().focusSearchInput();
                }
            }
            return true;
        }
        return false;
    }

    public static boolean canReceiveInput(GuiComponent guiComponent) {
        return activeComponent == null || activeComponent instanceof TextInputComponentBase || activeComponent instanceof DropdownSelectComponent || activeComponent instanceof PopupMenuButtonComponent || activeComponent.equals(guiComponent) || activeComponent.f().contains(guiComponent);
    }

    @Override
    public boolean k() {
        return false;
    }

    @Override
    public void toggle() {
        super.toggle();
        if (Minecraft.currentScreen().getObject() != null && !Minecraft.currentScreen().isInstance(MappedClasses.u5)) {
            return;
        }
        if (this.isEnabled()) {
            Minecraft.R();
            this.savedChatKeyState = Minecraft.gameSettings().m$src$Z$1s8ei5l();
            Minecraft.gameSettings().P(false);
            this.inputEnabled = false;
        } else {
            Minecraft.gameSettings().P(this.savedChatKeyState);
            Vape.debugLog("Gui Closed 2");
            this.inputEnabled = true;
        }
    }

    private static void onSearchBarStyleChanged(ModeValue ignoredValue) {
        settingsSearchFrame.resetSearchAsync();
    }

    public void renderFramesAndNotifications() {
        this.updateFrames();
        NotificationManager notificationManager = Vape.INSTANCE.getNotificationManager();
        notificationManager.renderNotifications();
    }

    public void handleMouseButton(int button) {
        RenderUtils.C();
        this.dispatchMouseButton(button);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleMouseButton(int button, int mouseX, int mouseY) {
        RenderUtils.C(mouseX, mouseY);
        try {
            this.dispatchMouseButton(button);
        }
        finally {
            RenderUtils.C();
        }
    }

    private void dispatchMouseButton(int button) {
        TutorialFrame tutorialFrame;
        MousePosition mousePosition = RenderUtils.h();
        GuiMouseEvent guiMouseEvent = new GuiMouseEvent(mousePosition.O, mousePosition.H, MouseButton.A(button));
        if (guiMouseEvent.fire()) {
            return;
        }
        if (activeComponent != null) {
            boolean capturesMouseInput = !(activeComponent instanceof TextInputComponentBase);
            activeComponent.dispatchMouseEvent(guiMouseEvent);
            if (capturesMouseInput) {
                return;
            }
        }
        CopyOnWriteArrayList<Frame> framesInInputOrder = new CopyOnWriteArrayList<Frame>(this.activeStack.Y());
        Collections.reverse(framesInInputOrder);
        if (!(this.activeStack instanceof ClickGuiFrameManager)) {
            for (Frame frame : framesInInputOrder) {
                if (!frame.V$src$Z$1xhop3l() || !frame.t$src$Lgg_vape_ui_click_component_gui_TextButton_$p5ute3().V$src$Z$1xhop3l() || !frame.t$src$Lgg_vape_ui_click_component_gui_TextButton_$p5ute3().w$src$Z$e457mb()) continue;
                frame.t$src$Lgg_vape_ui_click_component_gui_TextButton_$p5ute3().dispatchPrimaryClick();
                return;
            }
        }
        if ((tutorialFrame = ClientSettings.getFrame(TutorialFrame.class)) != null && tutorialFrame.V$src$Z$1xhop3l() && tutorialFrame.G(guiMouseEvent.getX(), guiMouseEvent.getY())) {
            tutorialFrame.e(guiMouseEvent);
            tutorialFrame.U();
            tutorialFrame.dispatchMouseEvent(guiMouseEvent);
            return;
        }
        boolean listenerConsumedEvent = false;
        block1: for (Frame frame : framesInInputOrder) {
            if (!frame.V$src$Z$1xhop3l() || this.activeStack instanceof ClickGuiFrameManager && ((ClickGuiFrameManager)this.activeStack).isMainLayerHudFrame(frame)) continue;
            for (GuiMouseListener guiMouseListener : frame.getGlobalMouseListeners()) {
                if (guiMouseListener.Q(new Point(guiMouseEvent.getX(), guiMouseEvent.getY()))) {
                    listenerConsumedEvent = true;
                    break;
                }
                guiMouseListener.g(new Point(guiMouseEvent.getX(), guiMouseEvent.getY()), guiMouseEvent.getAction().G());
            }
            for (GuiMouseListener guiMouseListener : frame.getMouseListeners()) {
                if (!guiMouseListener.Q(new Point(guiMouseEvent.getX(), guiMouseEvent.getY()))) continue;
                listenerConsumedEvent = true;
                continue block1;
            }
        }
        if (!listenerConsumedEvent) {
            for (Frame frame : framesInInputOrder) {
                if (!frame.V$src$Z$1xhop3l()) continue;
                boolean managedByClickGui = this.activeStack instanceof ClickGuiFrameManager && ((ClickGuiFrameManager)this.activeStack).isMainLayerHudFrame(frame);
                boolean bl = managedByClickGui;
                if (!managedByClickGui) {
                    frame.e(guiMouseEvent);
                }
                if (frame.G(guiMouseEvent.getX(), guiMouseEvent.getY())) {
                    if (managedByClickGui) {
                        if (guiMouseEvent.getAction() != MouseButton.LEFT_CLICK) break;
                        clickGuiFrameManager.showLayer(ClickGuiLayer.OVERLAYS);
                        frame.U();
                        frame.dispatchMouseEvent(guiMouseEvent);
                        break;
                    }
                    if (frame instanceof HudModuleConfigFrameBase) {
                        HudModuleConfigFrameBase hudModuleConfigFrameBase = (HudModuleConfigFrameBase)frame;
                        if (!hudModuleConfigFrameBase.isHudEditorContext()) continue;
                    }
                    frame.U();
                    frame.dispatchMouseEvent(guiMouseEvent);
                    break;
                }
                if (!(frame instanceof HudModuleFrameBase)) continue;
                HudModuleFrameBase hudModuleFrameBase = (HudModuleFrameBase)frame;
                hudModuleFrameBase.setHudEditorSelected(false);
            }
        }
    }

    public void openGui() {
        this.setInputEnabled(false);
    }

    @EventHandler
    public void onMouseButton(EventMouseButton eventMouseButton) {
        if (!this.inputEnabled) {
            eventMouseButton.setCancelled(true);
        }
    }

    public JsonArray serializeFrameStates() {
        JsonArray jsonArray = new JsonArray();
        for (Frame frame : ClientSettings.getAllFrames()) {
            if (frame.getName().startsWith("sidecar_")) continue;
            jsonArray.add(frame.Z());
        }
        return jsonArray;
    }

    public static void queueFrameOpen(Frame frame) {
        if (!frame.d$src$Z$1lx9d06()) {
            return;
        }
        ClientSettings.INSTANCE.pendingFrame = frame;
    }

    public void requestFrameLayoutRefresh() {
        this.layoutRefreshPending = true;
    }

    public boolean isInputEnabled() {
        return this.inputEnabled;
    }

    public void updateGuiScale() {
        Vape.INSTANCE.getClientSettings().guiColor.applyConfiguredColorTransform();
        double currentGuiScale = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        if (this.lastGuiScale != currentGuiScale && this.lastGuiScale != -1.0) {
            FontManager fontManager = Vape.INSTANCE.getFontManager();
            for (Map<Integer, SmoothFontRenderer> map : fontManager.n().values()) {
                for (SmoothFontRenderer smoothFontRenderer : map.values()) {
                    smoothFontRenderer.f();
                }
            }
            this.refreshFrameLayouts();
        }
        this.lastGuiScale = currentGuiScale;
    }

    public static void refreshModuleCategoryHeaders() {
        try {
            if (allFrames == null) {
                return;
            }
            for (Frame frame : allFrames) {
                if (frame == null || !(frame instanceof ModuleCategoryFrame) || frame instanceof ClientSettingsSearchFrame) continue;
                int enabledModuleCount = ((ModuleCategoryFrame)frame).A$src$I$wwnvku();
                if (!(frame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() instanceof ModuleCategoryFrameHeader)) continue;
                ((ModuleCategoryFrameHeader)frame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc()).y(enabledModuleCount);
            }
        }
        catch (Throwable t) {
            Vape.logThrowable(t);
        }
    }

    public static void removePopup(PopupFrame popupFrame) {
        FrameStackManager frameStackManager = INSTANCE.getActiveStack();
        UI_EXECUTOR.execute(() -> ClientSettings.removePopupFromStack(frameStackManager, popupFrame));
        popupFrame.Q$src$Lgg_vape_ui_click_frame_Frame_$1y8ivjg().s$src$Ljava_util_ArrayList_$1a2240q().remove(popupFrame);
    }

    public void syncRainbowHue() {
        MutableColor mutableColor = Vape.INSTANCE.getClientSettings().guiColor.getMutableColor();
        float[] fArray = new float[3];
        Color.RGBtoHSB(mutableColor.getRed(), mutableColor.getGreen(), mutableColor.getBlue(), fArray);
        this.rainbowHue = fArray[0];
    }

    public void loadFrameStates(JsonArray jsonArray) {
        if (jsonArray == null || jsonArray.size() == 0) {
            return;
        }
        JsonElement first = jsonArray.get(0);
        JsonArray targetArray = first != null && first.isJsonArray() ? first.getAsJsonArray() : jsonArray;
        for (int i = 0; i < targetArray.size(); ++i) {
            try {
                JsonObject jsonObject;
                JsonElement jsonElement = targetArray.get(i);
                if (jsonElement == null || !jsonElement.isJsonObject() || jsonElement.isJsonNull() || (jsonObject = jsonElement.getAsJsonObject()).get("title") == null || jsonObject.get("title").isJsonNull()) continue;
                for (Frame frame : ClientSettings.getAllFrames()) {
                    if (frame == null || !frame.getName().equals(jsonObject.get("title").getAsString())) continue;
                    frame.t(jsonObject);
                }
                continue;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static void removeFramePopups(Frame frame) {
        FrameStackManager frameStackManager = INSTANCE.getActiveStack();
        UI_EXECUTOR.execute(() -> ClientSettings.removePopupsFromStack(frame, frameStackManager));
        frame.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa().s$src$Ljava_util_ArrayList_$1a2240q().clear();
    }

    public static ArrayList<PopupFrame> getCurrentPopups() {
        ArrayList<PopupFrame> popups = new ArrayList<PopupFrame>();
        for (Frame frame : ClientSettings.INSTANCE.activeStack.Y()) {
            popups.addAll(frame.s$src$Ljava_util_ArrayList_$1a2240q());
        }
        return popups;
    }

    public void renderHudFrames() {
        int previousAlphaFunction = OpenGlBackendHolder.backend.getIntegerState(3009);
        float previousAlphaReference = OpenGlBackendHolder.backend.getFloat(3010);
        OpenGlBackendHolder.backend.setAlphaFunction(516, 0.0f);
        if (INSTANCE.isInputEnabled()) {
            OpenGlBackendHolder.backend.pushMatrix();
            double guiScale = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
            OpenGlBackendHolder.backend.scale(guiScale, guiScale, guiScale);
            if (INSTANCE.isInputEnabled()) {
                this.renderFrames();
                NotificationManager notificationManager = Vape.INSTANCE.getNotificationManager();
                notificationManager.renderNotifications();
            }
            OpenGlBackendHolder.backend.popMatrix();
        }
        OpenGlBackendHolder.backend.setAlphaFunction(previousAlphaFunction, previousAlphaReference);
    }

    public static void toggleCategoryFrame(String categoryName) {
        Frame frame = null;
        for (Frame frame2 : allFrames) {
            if (!(frame2 instanceof ModuleCategoryFrame) || !((ModuleCategoryFrame)frame2).L$src$Ljava_lang_String_$ahld16().equalsIgnoreCase(categoryName)) continue;
            frame2.setVisible(!frame2.V$src$Z$1xhop3l());
            frame = frame2;
        }
        if (frame != null) {
            frame.U();
        }
    }

    @Override
    public void onFinishModuleInitialization() {
    }

    private static void registerFrame(Frame frame, FrameStackManager ... stacks) {
        allFrames.add(frame);
        for (FrameStackManager stack : stacks) {
            stack.q(frame);
            if (allStacks.contains(stack)) continue;
            allStacks.add(stack);
        }
    }

    private void onStandaloneModeChanged(ModeValue modeValue) {
        this.updateStandaloneState();
    }

    private void removeClosedPopups() {
        ArrayList<PopupFrame> closedPopups = new ArrayList<PopupFrame>();
        for (Frame frame : this.activeStack.Y()) {
            for (PopupFrame popupFrame : frame.s$src$Ljava_util_ArrayList_$1a2240q()) {
                if (!popupFrame.c$src$Z$1kex42k()) continue;
                closedPopups.add(popupFrame);
            }
        }
        for (PopupFrame popupFrame : closedPopups) {
            popupFrame.Q$src$Lgg_vape_ui_click_frame_Frame_$1y8ivjg().s$src$Ljava_util_ArrayList_$1a2240q().remove(popupFrame);
            this.activeStack.m(popupFrame);
        }
        if (activeComponent instanceof DropdownSelectComponent) {
            ((DropdownSelectComponent)activeComponent).closePopup();
        }
    }

    private void updateFrames() {
        this.activeStack.A();
        if (this.layoutRefreshPending) {
            for (Frame frame : this.activeStack.Y()) {
                frame.l$src$V$1mibm4x();
            }
            this.layoutRefreshPending = false;
        }
        ArrayList<Frame> activeFrames = new ArrayList<Frame>(this.activeStack.Y());
        for (Frame frame : activeFrames) {
            frame.A$src$V$4ceaf0();
        }
        this.syncRainbowHue();
        CopyOnWriteArrayList<Frame> copyOnWriteArrayList = new CopyOnWriteArrayList<Frame>(activeFrames);
        CopyOnWriteArrayList<Frame> forwardRenderOrder = new CopyOnWriteArrayList<Frame>(activeFrames);
        Collections.reverse(copyOnWriteArrayList);
        MouseInput.getState().prepareFrame();
        for (Frame frame : forwardRenderOrder) {
            if (frame instanceof TutorialFrame) continue;
            this.renderFrame(frame, copyOnWriteArrayList);
        }
        TutorialFrame tutorialFrame = ClientSettings.getFrame(TutorialFrame.class);
        if (tutorialFrame != null) {
            if (Vape.INSTANCE.getTutorialManager() != null) {
                Vape.INSTANCE.getTutorialManager().updateVisibility();
            }
            if (tutorialFrame.V$src$Z$1xhop3l()) {
                if (Vape.INSTANCE.getTutorialManager() != null && (Vape.INSTANCE.getTutorialManager().getState() == TutorialState.WELCOME || Vape.INSTANCE.getTutorialManager().getState() == TutorialState.COMPLETED_ALL || Vape.INSTANCE.getTutorialManager().getState() == TutorialState.COMPLETED_TUTORIAL)) {
                    tutorialFrame.j(false);
                }
                this.renderFrame(tutorialFrame, copyOnWriteArrayList);
            }
        }
        MouseInput.getState().resetScrollDelta();
        if (activeComponent instanceof DropdownSelectComponent || activeComponent instanceof PopupMenuButtonComponent) {
            boolean previousClipState = activeComponent.isShowDisabledOverlay();
            activeComponent.setShowDisabledOverlay(false);
            activeComponent.c();
            activeComponent.setShowDisabledOverlay(previousClipState);
            if (activeComponent.getParentFrameComponent() != null && activeComponent.getParentFrameComponent().k$src$Z$if6xeb()) {
                activeComponent.getParentFrameComponent().M();
            }
        }
        for (Frame frame : forwardRenderOrder) {
            if (frame.V$src$Z$1xhop3l() && frame.a$src$Z$1f30q5a() && !(this.activeStack instanceof ClickGuiFrameManager)) {
                frame.t$src$Lgg_vape_ui_click_component_gui_TextButton_$p5ute3().setVisible(true);
                frame.t$src$Lgg_vape_ui_click_component_gui_TextButton_$p5ute3().c();
                if (!frame.t$src$Lgg_vape_ui_click_component_gui_TextButton_$p5ute3().t()) continue;
                frame.t$src$Lgg_vape_ui_click_component_gui_TextButton_$p5ute3().J();
                continue;
            }
            frame.t$src$Lgg_vape_ui_click_component_gui_TextButton_$p5ute3().setVisible(false);
        }
        if (activeTooltips != null) {
            activeTooltips.c();
        }
    }

    private static void removePopupsFromStack(Frame frame, FrameStackManager frameStackManager) {
        for (PopupFrame popupFrame : frame.s$src$Ljava_util_ArrayList_$1a2240q()) {
            frameStackManager.m(popupFrame);
        }
    }

    private static void registerHudFrames() {
        ClientSettings.registerFrame(new CompassHudFrame().Y(Minecraft.J() / 4 - 154, 38.0), hudEditorStack, mainStack);
        ClientSettings.registerFrame(new KeystrokesHudFrame().Y(40.0, 40.0), hudEditorStack, mainStack);
        ClientSettings.registerFrame(new ArmorStatusHudFrame().Y(40.0, 150.0), hudEditorStack, mainStack);
        ClientSettings.registerFrame(new ClockHudFrame().Y(Minecraft.J() / 2 - 90, 10.0), hudEditorStack, mainStack);
        ClientSettings.registerFrame(new PotionEffectsHudFrame().Y(100.0, 150.0), hudEditorStack, mainStack);
        ClientSettings.registerFrame(new FpsDisplayHudFrame().Y(140.0, 40.0), hudEditorStack, mainStack);
        ClientSettings.registerFrame(new CoordinatesHudFrame().Y(140.0, 70.0), hudEditorStack, mainStack);
        ClientSettings.registerFrame(new ReachDisplayHudFrame().Y(140.0, 110.0), hudEditorStack, mainStack);
        if (ForgeVersion.MC_1_20_6.v()) {
            ClientSettings.registerFrame(new ScoreboardHudFrame(), hudEditorStack, mainStack);
        }
    }

    public boolean isMainGuiStack() {
        FrameStackManager frameStackManager = this.getActiveStack();
        return frameStackManager.equals(mainStack) || frameStackManager instanceof ClickGuiFrameManager;
    }

    private static void removePopupFromStack(FrameStackManager frameStackManager, PopupFrame popupFrame) {
        frameStackManager.m(popupFrame);
    }

    public Color getAccentColor() {
        if (Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled()) {
            this.rainbowHue = (float)((double)this.rainbowHue - 0.03);
            if (this.rainbowHue <= 0.0f) {
                this.rainbowHue = 1.0f - -this.rainbowHue;
            }
            return ColorUtil.createReadableHsbColor(this.rainbowHue, 0.9f, 1.0f);
        }
        return Vape.INSTANCE.getClientSettings().guiColor.getMutableColor();
    }

    public static void initializeFrames() {
        for (FrameStackManager frameStackManager : allStacks) {
            frameStackManager.Y().clear();
        }
        allFrames.clear();
        Vape.INSTANCE.initializeRender();
        ClientSettings.registerHudFrames();
        ClientSettings.registerFrame(new ActiveModuleStackFrame(), hudEditorStack, mainStack);
        moduleSearchFrame = new ModuleSearchFrame();
        ClientSettings.registerFrame(moduleSearchFrame, mainStack);
        settingsSearchFrame = new ClientSettingsSearchFrame();
        ClientSettings.registerFrame(settingsSearchFrame, mainStack);
        ClientSettings.registerFrame(new ClientSettingsFrame(), mainStack);
        ClientSettings.registerFrame(new ClientSettingsSectionFrame(), mainStack);
        legitModuleCategoryFrame = new ModuleCategoryFrame(Category.COMBAT);
        ClientSettings.registerFrame(legitModuleCategoryFrame, mainStack);
        Vape vape = Vape.INSTANCE;
        if (vape.isFeatureDisabled()) {
            ClientSettings.registerFrame(new ModuleCategoryFrame(Category.OTHER), mainStack);
        }
        ClientSettings.registerFrame(new ModuleCategoryFrame(Category.RENDER), mainStack);
        ClientSettings.registerFrame(new ModuleCategoryFrame(Category.UTILITY), mainStack);
        ClientSettings.registerFrame(new ModuleCategoryFrame(Category.WORLD), mainStack);
        ClientSettings.registerFrame(new ModuleCategoryFrame(Category.INVENTORY), mainStack);
        ClientSettings.registerFrame(new ModuleCategoryFrame(Category.NETWORK), mainStack);
        ClientSettings.registerFrame(new VisibleModuleListFrame(), mainStack);
        ClientSettings.registerFrame(new ProfilesSettingsFrame(), mainStack);
        ClientSettings.registerFrame(new FrameMacros(), mainStack);
        ClientSettings.registerFrame(new OnlineFriendsFrame(), mainStack);
        ClientSettings.registerFrame(new QuickActionsFrame(), mainStack);
        ClientSettings.registerFrame(new OnlinePlayerPreviewSettingsFrame(), mainStack);
        ClientSettings.registerFrame(new TextGuiSettingsFrame(), mainStack);
        ClientSettings.registerFrame(new OnlineCombatStatsSettingsFrame(), mainStack);
        ClientSettings.registerFrame(new OnlineRadarSettingsFrame(), mainStack);
        ClientSettings.registerFrame(new InventoryOverlaySettingsFrame(), mainStack);
        ClientSettings.registerFrame(new TargetInfoSettingsFrame(), mainStack);
        ClientSettings.registerFrame(new OnlineActivitySettingsFrame(), mainStack);
        ClientSettings.registerFrame(new EnemySettingsFrame(), mainStack);
        ClientSettings.registerFrame(new HotbarSlotRuleItemPickerFrame(), hotbarRuleEditorStack);
        ClientSettings.registerFrame(ClientSettings.getFrame(HotbarSlotRuleItemPickerFrame.class).getItemListFrame(), hotbarRuleEditorStack);
        ClientSettings.registerFrame(new PublicProfilesFrame(), publicProfilesStack);
        ClientSettings.registerFrame(new HudModuleSelectorFrame(), hudEditorStack);
        ClientSettings.registerFrame(ClientSettings.getFrame(HudModuleSelectorFrame.class).getModuleListPanel(), hudEditorStack);
        ClientSettings.registerFrame(new HudModuleOverviewFrame(), hudEditorStack);
        ClientSettings.registerFrame(ClientSettings.getFrame(HudModuleOverviewFrame.class).getModuleList(), hudEditorStack);
        ClientSettings.registerFrame(new HudEditorReturnToMainLayerFrame(), hudEditorStack);
        ClientSettings.registerFrame(new HudModuleConfigFrame(), hudEditorStack);
        ClientSettings.registerFrame(new SessionSpoofFrame(), sessionSpoofStack);
        ClientSettings.registerFrame(new ProfileSnapshotFrame(), profileSnapshotStack);
        ClientSettings.registerFrame(new InventoryCleanerPopupFrame(), inventoryCleanerStack);
        ClientSettings.registerFrame(new InventoryFilterRuleEditorFrame(), inventoryCleanerStack);
        ClientSettings.registerFrame(new TutorialFrame(), mainStack);
        frameSnapshot = ImmutableList.copyOf(allFrames);
        ClientSettings.refreshModuleCategoryHeaders();
        VisibleModuleListFrame.e();
        framesInitialized = true;
    }

    @EventHandler
    public void processPendingFrame(EventPreRenderTick ignoredEvent) {
        if (this.pendingFrame != null) {
            this.activeStack.v(this.pendingFrame);
            this.pendingFrame.s$src$Ljava_util_ArrayList_$1a2240q().forEach(PopupFrame::A$src$V$4ceaf0);
            this.pendingFrame = null;
        }
    }

    public void refreshFrameLayouts() {
        for (FrameStackManager object : allStacks) {
            for (Frame frame : object.Y()) {
                if (!frame.n$src$Z$1fa61uz()) continue;
                frame.j(true);
                frame.H(true);
            }
        }
        if (clickGuiFrameManager != null) {
            for (Frame frame : clickGuiFrameManager.Y()) {
                if (!frame.n$src$Z$1fa61uz()) continue;
                frame.j(true);
                frame.H(true);
            }
        }
        ClientSettings.getFrame(ModuleSearchFrame.class).p();
        ClientSettings.getFrame(HudEditorReturnToMainLayerFrame.class).centerAtTop();
    }

    @Deprecated
    public static void showFrame(Class<? extends Frame> frameClass) {
        ClientSettings.setFrameVisibility(frameClass, true);
    }

    public static <T extends PopupFrame> T createPopup(GuiComponent owner, GuiComponent content, Class<T> popupClass) {
        try {
            Constructor<T> constructor = popupClass.getConstructor(GuiComponent.class, GuiComponent.class);
            PopupFrame popupFrame = (PopupFrame)constructor.newInstance(owner, content);
            ClientSettings.INSTANCE.activeStack.Y().add(popupFrame);
            owner.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa().s$src$Ljava_util_ArrayList_$1a2240q().add(popupFrame);
            return (T)popupFrame;
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
            return null;
        }
    }

    public static void closeListDropdowns() {
        if (INSTANCE == null || ClientSettings.INSTANCE.activeStack == null || ClientSettings.INSTANCE.activeStack.Y() == null) {
            return;
        }
        try {
            for (Frame frame : ClientSettings.INSTANCE.activeStack.Y()) {
                if (!(frame instanceof ListValueDropdownLayer)) continue;
                ((ListValueDropdownLayer)frame).refreshContents();
            }
        }
        catch (Throwable t) {
            Vape.logThrowable(t);
        }
    }

    public void renderGui() {
        try {
            GuiRenderPrimitives.Y();
            RenderUtils.g();
            RenderUtils.C();
            if (!GuiRenderPrimitives.d()) {
                if (this.blurBackground.getEffectiveValue().booleanValue()) {
                    ShaderGroupRenderStateManager.getInstance().enable();
                } else {
                    ShaderGroupRenderStateManager.getInstance().disable();
                }
            } else if (this.blurBackground.getEffectiveValue().booleanValue()) {
                int screenWidth = Minecraft.J();
                int screenHeight = Minecraft.h();
                this.blurRenderer.setDimensions(screenWidth, screenHeight);
                this.blurRenderer.renderBlur(0, 0, 16.0f, 0.0f);
            }
            RectData screenBounds = new RectData(0.0, 0.0, Minecraft.J(), Minecraft.h());
            if (lastScreenRect != null && (screenBounds.e() != lastScreenRect.e() || screenBounds.R() != lastScreenRect.R())) {
                this.refreshFrameLayouts();
            }
            lastScreenRect = screenBounds;
            OpenGlBackendHolder.backend.pushMatrix();
            double guiScale = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
            OpenGlBackendHolder.backend.scale(guiScale, guiScale, guiScale);
            try {
                UI_EXECUTOR.runPending();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            this.renderFramesAndNotifications();
            OpenGlBackendHolder.backend.popMatrix();
            RenderUtils.f();
            GuiRenderPrimitives.D();
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
        OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static <T extends Frame> T getFrame(Class<T> frameClass) {
        Frame frame = frameCache.get(frameClass);
        if (frame != null) {
            return (T)frame;
        }
        for (Frame candidate : allFrames) {
            if (!candidate.getClass().getCanonicalName().equals(frameClass.getCanonicalName())) continue;
            frameCache.put(frameClass, candidate);
            return (T)candidate;
        }
        return null;
    }

    private void renderFrame(Frame frame, List<Frame> reverseRenderOrder) {
        if (!frame.V$src$Z$1xhop3l()) {
            return;
        }
        frame.c();
        if (this.activeStack instanceof ClickGuiFrameManager && ((ClickGuiFrameManager)this.activeStack).isMainLayerHudFrame(frame)) {
            if (frame instanceof HudModuleFrameBase) {
                HudModuleFrameBase hudModuleFrameBase = (HudModuleFrameBase)frame;
                boolean hasOverlay = frame.t();
                if (hasOverlay) {
                    boolean isFrontmostOverlay = true;
                    for (Frame otherFrame : reverseRenderOrder) {
                        if (otherFrame.equals(frame)) break;
                        if (!otherFrame.V$src$Z$1xhop3l() || !otherFrame.t()) continue;
                        isFrontmostOverlay = false;
                    }
                    hudModuleFrameBase.setFrontmostOverlay(isFrontmostOverlay);
                } else {
                    hudModuleFrameBase.setFrontmostOverlay(false);
                }
            }
            return;
        }
        if (frame.t()) {
            boolean isFrontmostOverlay = true;
            for (Frame otherFrame : reverseRenderOrder) {
                if (otherFrame.equals(frame)) break;
                if (!otherFrame.V$src$Z$1xhop3l() || !otherFrame.t()) continue;
                isFrontmostOverlay = false;
            }
            if (isFrontmostOverlay) {
                frame.J();
            }
        }
    }

    public static void markFramePositioned(Frame frame) {
        positionedFrames.add(frame);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.disableBlurShader();
        KeyBinding keyBinding = Minecraft.gameSettings().y$src$Lgg_vape_wrapper_impl_KeyBinding_$1hvjjoh();
        if (keyBinding != null && keyBinding.getObject() != null) {
            InvWalkSettingsState.setPressed(keyBinding, false);
        }
    }

    public void renderHudOverlay() {
        if (this.inputEnabled) {
            this.renderHudFrames();
            if (GuiRenderPrimitives.d()) {
                RenderBatchManager.getInstance().flushGuiBatches(0.0f);
            }
        }
    }

    private void updateStandaloneState() {
        PublicProfileSettings publicProfileSettings = Vape.INSTANCE.getPublicProfileSettings();
        if (publicProfileSettings.guiStyle.isPersistenceSuppressed()) {
            return;
        }
        UI_EXECUTOR.execute(() -> this.applyStandaloneMode(publicProfileSettings));
    }

    public void arrangeFrameGrid() {
        double x = 32.0;
        double y = 32.0;
        double rowHeight = 0.0;
        for (Frame frame : frameSnapshot) {
            if (!frame.J$src$Z$1eqdghz() || !frame.l$src$Z$193vdc5()) continue;
            if (x + frame.A() > (double)Minecraft.G().getScaledWidth()) {
                x = 24.0;
                y += rowHeight + 8.0;
                rowHeight = 0.0;
            }
            if (frame.L() > rowHeight) {
                rowHeight = frame.L();
            }
            frame.K(x);
            frame.S(y);
            frame.l$src$V$1mibm4x();
            x += frame.A() + 2.0;
        }
    }

    public static void clearPositionedFrames() {
        positionedFrames.clear();
    }

    public void replaceFrame(Frame frame, Frame replacement) {
        this.activeStack.R(frame, replacement);
    }

    @Override
    protected Bendable C$src$Lgg_vape_unmap_Bendable_$1we4j6l() {
        return new ClientSettingsGuiBindBendable(this);
    }

    public static List<Frame> getAllFrames() {
        return allFrames;
    }

    public ClientSettings() {
        super("GUI", 161, 0, Category.NONE, "Shift click any module button to bind it to a key.\n(You can shift click this to bind the GUI)\nHold TAB to view modules binds.");
        this.guiBindIndicator = BooleanValue.create(this, "GUI bind indicator", true, "Displays a message indicating your GUI keybind upon injecting.\nI.E. \"Press RSHIFT to open GUI\"");
        this.smoothFont = new ClientSettingsBooleanValue((Object)this, "Smooth Font", true);
        this.showTooltips = BooleanValue.create(this, "Show tooltips", true, "Toggles visibility of these");
        this.rainbowSpeed = NumberValue.create(this, "Rainbow speed", "#.#", "", 0.1, 1.0, 10.0, 0.1, "Adjusts the speed of rainbow values");
        this.language = (ModeValue)ModeValue.create((Object)this, "Language", FontSelector.j, FontSelector.j, FontSelector.S, FontSelector.c, FontSelector.a, FontSelector.P).setResettable(false);
        this.showLegitMode = BooleanValue.create(this, "Show legit mode", true, "Shows the button to switch to the legit mod menu");
        this.showEnabledCount = BooleanValue.create(this, "Show enabled count", true, "Shows the number of enabled modules in the standalone gui");
        this.blurRenderer = new BlurRegionRenderer(0, 0);
        this.activeStack = mainStack;
        this.framesToUpdate = new HashSet<Frame>(allFrames);
        INSTANCE = this;
        this.floatingSearchBarMode = new ModeOption("Floating", 0.8);
        this.integratedSearchBarMode = new ModeOption("Integrated", 0.8);
        this.noSearchBarMode = new ModeOption("None", 0.8);
        this.searchBarStyle = ModeValue.create((Object)this, "Search bar style", "Switch between search bar styles", (ModeSelection)this.floatingSearchBarMode, this.integratedSearchBarMode, this.noSearchBarMode, this.floatingSearchBarMode);
        PublicProfileSettings publicProfileSettings = Vape.INSTANCE.getPublicProfileSettings();
        publicProfileSettings.notifications.addDependentValues(publicProfileSettings.toggleAlerts);
        publicProfileSettings.notifications.addDependentValues(publicProfileSettings.settingToggleAlerts);
        publicProfileSettings.notifications.addDependentValues(publicProfileSettings.profileSwitchNotifications);
        publicProfileSettings.notifications.addDependentValues(publicProfileSettings.friendNotifications);
        this.searchBarStyle.addChangeListener(ClientSettings::onSearchBarStyleChanged);
        this.addValue(this.blurBackground, this.multiKeybinding, this.guiBindIndicator, this.showTooltips, this.language, this.allowSettingKeybinds, this.rainbowSpeed, this.searchBarStyle);
    }

    public void disableBlurShader() {
        if (Vape.INSTANCE.getModManager().getMod(ClientSettings.class).blurBackground.getEffectiveValue().booleanValue()) {
            ShaderGroupRenderStateManager.getInstance().disable();
        }
    }

    public void switchFrameStack(FrameStackManager frameStackManager) {
        activeComponent = null;
        this.removeClosedPopups();
        activeTooltips = null;
        FrameStackManager frameStackManager2 = this.activeStack;
        if (frameStackManager2 instanceof ClickGuiFrameManager) {
            ClickGuiFrameManager clickGuiFrameManager = (ClickGuiFrameManager)frameStackManager2;
            if (clickGuiFrameManager.getMainFrame() != null) {
                clickGuiFrameManager.getMainFrame().closeActiveOverlay();
            }
            clickGuiFrameManager.closeSidecar();
        }
        for (Frame frame : allFrames) {
            if (!(frame instanceof HudModuleFrameBase)) continue;
            HudModuleFrameBase hudModuleFrameBase = (HudModuleFrameBase)frame;
            hudModuleFrameBase.setHudEditorSelected(false);
            hudModuleFrameBase.hideEditorControls();
            AnchoredHudModuleConfigFrame<AnimatedIconButtonComponent> anchoredHudModuleConfigFrame = hudModuleFrameBase.getAnchoredSettingsFrameInternal();
            if (frameStackManager2 != null) {
                frameStackManager2.m(anchoredHudModuleConfigFrame);
            }
            frameStackManager.m(anchoredHudModuleConfigFrame);
        }
        this.activeStack = frameStackManager;
        for (Frame frame : frameStackManager.Y()) {
            if (!frame.n$src$Z$1fa61uz()) continue;
            frame.b$src$V$1f3kin7();
        }
    }

    public void setInputEnabled(boolean inputEnabled) {
        this.inputEnabled = inputEnabled;
    }

    public FrameStackManager getActiveStack() {
        return this.activeStack;
    }

    public void onTick() {
        if (!this.initialized) {
            this.initialized = true;
            Vape.INSTANCE.getPublicProfileSettings().guiStyle.addChangeListener(this::onStandaloneModeChanged);
            this.updateStandaloneState();
        }
        try {
            if (Minecraft.currentScreen().getObject() != null) {
                if (Minecraft.currentScreen().isInstance(MappedClasses.u5) && this.getBind().areBoundInputsDown() && this.inputEnabled) {
                    this.toggle();
                    this.switchFrameStack(sessionSpoofStack);
                } else if (this.inputEnabled || !this.getBind().areBoundInputsDown()) {
                    // empty if block
                }
                if (this.activeStack.equals(sessionSpoofStack) && !Minecraft.currentScreen().isInstance(MappedClasses.u5)) {
                    this.switchFrameStack(mainStack);
                }
            } else if (!this.inputEnabled) {
                if (ForgeVersion.MC_1_16_5.d()) {
                    if (Minecraft.s().Z()) {
                        this.toggle();
                        return;
                    }
                } else if (Minecraft.a()) {
                    this.toggle();
                    return;
                }
            }
            if (activeComponent != null && !activeComponent.V$src$Z$1xhop3l()) {
                activeComponent = null;
            }
            this.framesToUpdate.clear();
            this.framesToUpdate.addAll(allFrames);
            this.framesToUpdate.addAll(this.activeStack.Y());
            if (ClientSettings.INSTANCE.inputEnabled) {
                for (Frame frame : allFrames) {
                    if (!(frame instanceof HudSettingsFrameBase)) continue;
                    this.framesToUpdate.add(frame);
                }
            }
            for (Frame frame : this.framesToUpdate) {
                try {
                    frame.T$src$V$1wse0de();
                }
                catch (Exception exception) {
                    Vape.debugLog("" + frame.getName());
                    Vape.logThrowable(exception);
                }
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    @Override
    public void u(EventKeyPress eventKeyPress) {
        super.u(eventKeyPress);
        if (!this.inputEnabled) {
            if (eventKeyPress.getKey() == 27 && eventKeyPress.isDown()) {
                ClickGuiFrameManager clickGuiFrameManager;
                this.inputEnabled = true;
                this.setEnabled(false);
                if (this.activeStack instanceof ClickGuiFrameManager && (clickGuiFrameManager = (ClickGuiFrameManager)this.activeStack).getOverlaySelector() != null && clickGuiFrameManager.getOverlaySelector().V$src$Z$1xhop3l()) {
                    clickGuiFrameManager.showLayer(ClickGuiLayer.MAIN);
                }
                if (!this.getActiveStack().equals(sessionSpoofStack)) {
                    Minecraft.F$src$V$aoypvc();
                }
            }
            eventKeyPress.setCancelled(true);
        }
    }

    public static boolean isHudEditorStack() {
        return INSTANCE.getActiveStack().equals(hudEditorStack);
    }

    static {
        allFrames = new ArrayList<Frame>();
        allStacks = new ArrayList<FrameStackManager>();
        mainStack = new FrameStackManager();
        hudEditorStack = new FrameStackManager();
        publicProfilesStack = new FrameStackManager();
        clickGuiFrameManager = new ClickGuiFrameManager();
        hotbarRuleEditorStack = new FrameStackManager();
        sessionSpoofStack = new FrameStackManager();
        profileSnapshotStack = new FrameStackManager();
        inventoryCleanerStack = new FrameStackManager();
        UI_EXECUTOR = new ThreadBoundExecutor();
        positionedFrames = new HashSet();
        frameCache = new HashMap();
    }
}

