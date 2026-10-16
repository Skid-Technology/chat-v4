/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.FriendModel;
import gg.vape.friend.FriendRequestModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;
import java.util.List;

public class InitialOnlineFriendStateEvent
extends OnlineEvent {
    private final List<FriendRequestModel> var_java_util_List_gg_vape_friend_FriendRequestModel__C;
    private final List<FriendRequestModel> var_java_util_List_gg_vape_friend_FriendRequestModel__K;
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_Z;
    private final List<FriendModel> var_java_util_List_gg_vape_friend_FriendModel__g = new ArrayList<FriendModel>();

    public List<FriendRequestModel> z() {
        return this.var_java_util_List_gg_vape_friend_FriendRequestModel__K;
    }

    public static void b(GuiComponent[] upArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_Z = upArray;
    }

    public static GuiComponent[] X() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_Z;
    }

    public InitialOnlineFriendStateEvent(ZeusClient oZ, List<FriendModel> list, List<FriendRequestModel> list2, List<FriendRequestModel> list3) {
        super(oZ);
        this.var_java_util_List_gg_vape_friend_FriendRequestModel__K = new ArrayList<FriendRequestModel>();
        this.var_java_util_List_gg_vape_friend_FriendRequestModel__C = new ArrayList<FriendRequestModel>();
        this.var_java_util_List_gg_vape_friend_FriendModel__g.addAll(list);
        this.var_java_util_List_gg_vape_friend_FriendRequestModel__K.addAll(list2);
        this.var_java_util_List_gg_vape_friend_FriendRequestModel__C.addAll(list3);
    }

    public List<FriendModel> q() {
        return this.var_java_util_List_gg_vape_friend_FriendModel__g;
    }

    public List<FriendRequestModel> Z() {
        return this.var_java_util_List_gg_vape_friend_FriendRequestModel__C;
    }

    static {
        if (InitialOnlineFriendStateEvent.X() != null) {
            InitialOnlineFriendStateEvent.b(new GuiComponent[5]);
        }
    }
}

