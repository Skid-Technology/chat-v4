/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.PresenceState;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEventPayloadBase;
import gg.vape.ui.click.component.GuiComponent;

public class FriendPresenceStateEvent
extends OnlineEventPayloadBase {
    private final PresenceState var_gg_vape_protocol_PresenceState_q;
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_g;

    public PresenceState O() {
        return this.var_gg_vape_protocol_PresenceState_q;
    }

    public static GuiComponent[] a_up_arr_f() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_g;
    }

    public static void u(GuiComponent[] guiComponentArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_g = guiComponentArray;
    }

    public FriendPresenceStateEvent(ZeusClient zeusClient, UserModel userModel, PresenceState presenceState) {
        super(zeusClient, userModel);
        this.var_gg_vape_protocol_PresenceState_q = presenceState;
    }

    static {
        if (FriendPresenceStateEvent.a_up_arr_f() == null) {
            FriendPresenceStateEvent.u(new GuiComponent[2]);
        }
    }
}

