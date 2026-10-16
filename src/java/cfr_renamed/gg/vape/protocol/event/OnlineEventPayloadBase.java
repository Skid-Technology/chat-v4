/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;
import gg.vape.ui.click.component.GuiComponent;

public abstract class OnlineEventPayloadBase
extends OnlineEvent {
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_z;
    private final UserModel var_gg_vape_friend_UserModel_L;

    public static GuiComponent[] A() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_z;
    }

    public UserModel f() {
        return this.var_gg_vape_friend_UserModel_L;
    }

    public OnlineEventPayloadBase(ZeusClient zeusClient, UserModel userModel) {
        super(zeusClient);
        this.var_gg_vape_friend_UserModel_L = userModel;
    }

    public static void V(GuiComponent[] guiComponentArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_z = guiComponentArray;
    }

    static {
        if (OnlineEventPayloadBase.A() == null) {
            OnlineEventPayloadBase.V(new GuiComponent[5]);
        }
    }
}

