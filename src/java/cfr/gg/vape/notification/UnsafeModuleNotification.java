/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.notification;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.module.Mod;
import gg.vape.notification.AbstractNotification;
import gg.vape.notification.INotification;
import gg.vape.notification.NotificationType;
import gg.vape.notification.TextNotificationContent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.BlurRegionRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import java.awt.Desktop;
import java.net.URI;
import org.lwjgl.opengl.GL11;

public class UnsafeModuleNotification
extends AbstractNotification {
    private Mod pendingModule;
    private final BlurRegionRenderer blurRenderer = new BlurRegionRenderer(0, 0);
    private boolean closing;

    public UnsafeModuleNotification(Mod pendingModule) {
        super(NotificationType.WARNING, "Unsafe module", new TextNotificationContent(""), 0.0, 0.0);
        this.pendingModule = pendingModule;
    }

    public void setPendingModule(Mod pendingModule) {
        this.pendingModule = pendingModule;
    }

    public Mod getPendingModule() {
        return this.pendingModule;
    }

    private double getRenderX() {
        return this.getCurrentX() + 3.0 + (double)Minecraft.J() / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() / 2.0;
    }

    private double getRenderY() {
        return this.getCurrentY() + (double)Minecraft.h() / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() / 2.0;
    }

    @Override
    public double getWidth() {
        return 158.0;
    }

    @Override
    public double getHeight() {
        return 106.0;
    }

    public RectData getBounds() {
        return new RectData(this.getRenderX(), this.getRenderY(), this.getWidth(), this.getHeight());
    }

    public RectData getUnderstoodBounds() {
        return new RectData(this.getRenderX() + 24.0, this.getRenderY() + 81.0, 46.0, 16.5);
    }

    public RectData getViewDocsBounds() {
        return new RectData(this.getRenderX() + 76.0, this.getRenderY() + 81.0, 44.0, 16.5);
    }

    @Override
    public boolean shouldRemove() {
        return this.getCurrentX() >= 1.0;
    }

    @Override
    public void dismiss() {
        this.closing = true;
        this.setTargetX(5.0);
    }

    @Override
    public void render() {
        float inverseScale = (float)(1.0 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor());
        boolean blendingEnabled = GL11.glIsEnabled(3042);
        float scale = 1.0f / inverseScale;
        if (GuiRenderPrimitives.d()) {
            OpenGlBackendHolder.backend.scale(scale, scale, scale);
        }
        double renderX = this.getRenderX();
        double renderY = this.getRenderY();
        double width = this.getWidth();
        double height = this.getHeight();
        boolean cardHovered = this.getBounds().Z(RenderUtils.h());
        this.blurRenderer.setDimensions((int)width * 2, (int)height * 2);
        this.blurRenderer.renderBlur((int)renderX, (int)renderY, 20.0f, 3.0f);
        Color backgroundColor = cardHovered ? new Color(18, 18, 20, 220) : new Color(15, 15, 18, 200);
        Color borderColor = cardHovered ? new Color(60, 60, 65, 255) : new Color(48, 48, 52, 255);
        GuiRenderPrimitives.e(renderX, renderY, width, height, backgroundColor, true, 4.0f, 1.0f);
        GuiRenderPrimitives.P(renderX, renderY, width, height, borderColor, 4.0f, 1.0f, 1.0f);
        ImageRenderer.drawResWithShadow(Color.WHITE, (float)renderX - 4.0f, (float)renderY - 6.0f, "noti_warning", 1.0f, true);
        Vape.INSTANCE.getFontManager().W(0.95, true).d(this.getTitle(), renderX + 24.0, renderY + 8.0, Color.WHITE);
        SmoothFontRenderer bodyFont = Vape.INSTANCE.getFontManager().W(0.85, false);
        Color textColor = new Color(175, 175, 180);
        bodyFont.d("This module is unsafe to use and", renderX + 24.0, renderY + 23.0, textColor);
        bodyFont.d("is generally detected by", renderX + 24.0, renderY + 33.5, textColor);
        bodyFont.d("anticheats regardless of settings", renderX + 24.0, renderY + 44.0, textColor);
        bodyFont.d("Click understood to allow", renderX + 24.0, renderY + 57.5, textColor);
        bodyFont.d("enabling unsafe modules", renderX + 24.0, renderY + 68.0, textColor);
        RectData understoodBounds = this.getUnderstoodBounds();
        RectData viewDocsBounds = this.getViewDocsBounds();
        boolean understoodHovered = understoodBounds.Z(RenderUtils.h());
        boolean viewDocsHovered = viewDocsBounds.Z(RenderUtils.h());
        double uX = understoodBounds.o();
        double uY = understoodBounds.W();
        double uW = understoodBounds.e();
        double uH = understoodBounds.R();
        Color uBg = understoodHovered ? new Color(58, 60, 66, 240) : new Color(42, 44, 48, 220);
        Color uBorder = understoodHovered ? new Color(75, 78, 86, 255) : new Color(52, 54, 60, 200);
        GuiRenderPrimitives.e(uX, uY, uW, uH, uBg, true, 3.5f, 1.0f);
        GuiRenderPrimitives.P(uX, uY, uW, uH, uBorder, 3.5f, 1.0f, 1.0f);
        double vX = viewDocsBounds.o();
        double vY = viewDocsBounds.W();
        double vW = viewDocsBounds.e();
        double vH = viewDocsBounds.R();
        Color vBg = viewDocsHovered ? new Color(58, 60, 66, 240) : new Color(42, 44, 48, 220);
        Color vBorder = viewDocsHovered ? new Color(75, 78, 86, 255) : new Color(52, 54, 60, 200);
        GuiRenderPrimitives.e(vX, vY, vW, vH, vBg, true, 3.5f, 1.0f);
        GuiRenderPrimitives.P(vX, vY, vW, vH, vBorder, 3.5f, 1.0f, 1.0f);
        SmoothFontRenderer btnFont = Vape.INSTANCE.getFontManager().W(0.82, true);
        String uText = "Understood";
        double uTextX = uX + (uW - btnFont.N(uText)) / 2.0;
        double uTextY = uY + (uH - 7.5) / 2.0;
        btnFont.d(uText, uTextX, uTextY, understoodHovered ? Color.WHITE : new Color(225, 225, 225));
        String vText = "View docs";
        double vTextX = vX + (vW - btnFont.N(vText)) / 2.0;
        double vTextY = vY + (vH - 7.5) / 2.0;
        btnFont.d(vText, vTextX, vTextY, viewDocsHovered ? Color.WHITE : new Color(225, 225, 225));
        if (GuiRenderPrimitives.d()) {
            OpenGlBackendHolder.backend.scale(inverseScale, inverseScale, inverseScale);
        }
        OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        if (blendingEnabled) {
            GL11.glEnable(3042);
        }
    }

    @Override
    public void handleClick(double mouseX, double mouseY) {
        if (this.getUnderstoodBounds().J(mouseX, mouseY)) {
            this.handleUnderstood();
            return;
        }
        if (this.getViewDocsBounds().J(mouseX, mouseY)) {
            this.handleViewDocs();
            return;
        }
    }

    public void handleUnderstood() {
        if (Vape.INSTANCE.getPublicProfileSettings() != null) {
            Vape.INSTANCE.getPublicProfileSettings().setUnsafeModulesUnderstood(true);
        }
        if (Vape.INSTANCE.getNotificationManager() != null) {
            Vape.INSTANCE.getNotificationManager().getNotifications().removeIf(n -> n instanceof UnsafeModuleNotification);
        }
        if (this.pendingModule != null) {
            this.pendingModule.setEnabled(true, true);
        }
    }

    public void handleViewDocs() {
        UnsafeModuleNotification.openDocs();
    }

    public static void openDocs() {
        String url = "https://docs.vape.gg/guides/general/anti-cheats";
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
                return;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
        }
        catch (Throwable t) {
            Vape.logThrowable(t);
        }
    }

    public static void show(Mod mod) {
        if (Vape.INSTANCE.getNotificationManager() == null) {
            return;
        }
        for (INotification notification : Vape.INSTANCE.getNotificationManager().getNotifications()) {
            if (!(notification instanceof UnsafeModuleNotification)) continue;
            ((UnsafeModuleNotification)notification).setPendingModule(mod);
            return;
        }
        UnsafeModuleNotification notification = new UnsafeModuleNotification(mod);
        Vape.INSTANCE.getNotificationManager().enqueue(notification, true);
    }
}

