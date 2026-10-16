/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.module.render.freecam.FreecamPlayerBridge;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.PlayerSkin;
import gg.vape.wrapper.impl.ResourceLocation;

public class AbstractClientPlayer
extends EntityPlayer {
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_o;

    public static void R(GuiComponent[] guiComponentArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_o = guiComponentArray;
    }

    public FreecamPlayerBridge getFreecamPlayerBridge() {
        return new FreecamPlayerBridge(AbstractClientPlayer.vapeInstance.getMappings().Dk.getClientAvatarState(this.I));
    }

    public AbstractClientPlayer(Object object) {
        super(object);
    }

    public static GuiComponent[] I() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_o;
    }

    public ResourceLocation O() {
        if (ForgeVersion.MC_1_20_6.d()) {
            return new PlayerSkin(AbstractClientPlayer.vapeInstance.getMappings().Dk.getLocationSkin(this.I)).getTexture();
        }
        return new ResourceLocation(AbstractClientPlayer.vapeInstance.getMappings().Dk.getLocationSkin(this.I));
    }

    static {
        if (AbstractClientPlayer.I() != null) {
            AbstractClientPlayer.R(new GuiComponent[5]);
        }
    }
}

