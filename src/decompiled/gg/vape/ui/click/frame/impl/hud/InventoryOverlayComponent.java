/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.InventoryOverlaySettingsFrame;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.BlurRegionRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ItemIconRenderer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.InventoryPlayer;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;

public class InventoryOverlayComponent
extends GuiComponent {
    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 120);
    private static final Color SLOT_COLOR = new Color(0, 0, 0, 55);
    private static final int COLUMN_COUNT = 9;
    private static final int INVENTORY_ROW_COUNT = 3;
    private static final int SLOT_SIZE = 16;
    private static final int SLOT_SPACING = 18;
    private static final int CONTENT_INSET = 2;
    private static final double WIDTH = 164.0;
    private final InventoryOverlaySettingsFrame settingsFrame;
    private final BlurRegionRenderer backgroundBlur = new BlurRegionRenderer(0, 0);

    public InventoryOverlayComponent(InventoryOverlaySettingsFrame settingsFrame) {
        this.settingsFrame = settingsFrame;
        this.o(164.0);
        this.Y(this.calculateHeight(3));
        this.setShowDisabledOverlay(false);
    }

    private double calculateHeight(int rowCount) {
        return 4.0 + (double)(rowCount - 1) * 18.0 + 16.0;
    }

    @Override
    public double L() {
        return this.calculateHeight(this.settingsFrame.shouldShowHotbar() ? 4 : 3);
    }

    private void renderInventory() {
        EntityPlayerSP player;
        if (this.settingsFrame.shouldRenderBackground()) {
            this.backgroundBlur.setDimensions((int)this.A() * 2, (int)this.L() * 2);
            this.backgroundBlur.renderBlur((int)this.G$src$D$1b2f02a(), (int)this.n(), 6.0f, 4.0f);
            GuiRenderPrimitives.B(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.settingsFrame.applyDefaultEditorAlpha(BACKGROUND_COLOR), 3.0f);
        }
        InventoryPlayer inventory = (player = Minecraft.thePlayer()).isNotNull() ? player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6() : null;
        int rowCount = this.settingsFrame.shouldShowHotbar() ? 4 : 3;
        for (int row = 0; row < rowCount; ++row) {
            for (int column = 0; column < 9; ++column) {
                int slotIndex;
                ItemStack itemStack;
                double slotX = this.G$src$D$1b2f02a() + 2.0 + (double)(column * 18);
                double slotY = this.n() + 2.0 + (double)(row * 18);
                if (this.settingsFrame.shouldRenderBackground()) {
                    GuiRenderPrimitives.B(slotX, slotY, 16.0, 16.0, this.settingsFrame.applyDefaultEditorAlpha(SLOT_COLOR), 1.5f);
                }
                if (inventory == null || inventory.isNull() || (itemStack = inventory.c(slotIndex = row < 3 ? 9 + row * 9 + column : column)).isNull()) continue;
                ItemIconRenderer.renderItemStack(itemStack, (float)slotX, (float)slotY, 16, 16, this.settingsFrame.getEditorOpacity());
                this.renderStackCount(itemStack, slotX, slotY);
            }
        }
    }

    private void renderStackCount(ItemStack itemStack, double slotX, double slotY) {
        if (itemStack.t() <= 1) {
            return;
        }
        String countText = String.valueOf(itemStack.t());
        SmoothFontRenderer fontRenderer = this.getAlternateFontRenderer(0.65);
        double countX = slotX + 16.0 - fontRenderer.N(countText) - 1.0;
        double countY = slotY + 16.0 - fontRenderer.d(countText);
        fontRenderer.v(countText, countX, countY, this.settingsFrame.getEditorForegroundColor());
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public void I() {
        this.renderInventory();
    }

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public void u() {
    }

    @Override
    public void F() {
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void H() {
        this.renderInventory();
    }
}

