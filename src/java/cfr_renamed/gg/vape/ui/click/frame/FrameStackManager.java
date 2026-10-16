/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.PopupFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FrameStackManager {
    private HashMap<Frame, Boolean> var_java_util_HashMap_gg_vape_ui_click_frame_Frame,_java_lang_Boolean__X;
    private static String var_java_lang_String_m;
    private List<Frame> var_java_util_List_gg_vape_ui_click_frame_Frame__I = new ArrayList<Frame>();

    public static String i() {
        return var_java_lang_String_m;
    }

    public void A() {
    }

    public void m(Frame frame) {
        this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.remove(frame);
    }

    public void R(Frame frame, Frame frame2) {
        if (!this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.contains(frame) || !this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.contains(frame2)) {
            return;
        }
        if (frame2 instanceof PopupFrame) {
            int n = this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.indexOf(frame);
            PopupFrame popupFrame = (PopupFrame)frame2;
            ArrayList<PopupFrame> arrayList = popupFrame.Q$src$Lgg_vape_ui_click_frame_Frame_$1y8ivjg().s$src$Ljava_util_ArrayList_$1a2240q();
            for (int i = 0; i < arrayList.size(); ++i) {
                PopupFrame popupFrame2 = arrayList.get(i);
                this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.remove(popupFrame2);
                if (n + i + 1 > this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.size()) {
                    this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.add(popupFrame2);
                    continue;
                }
                this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.add(n + i + 1, popupFrame2);
            }
        } else {
            this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.remove(frame2);
            int n = this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.indexOf(frame);
            this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.add(n + 1, frame2);
        }
    }

    public static void Y(String string) {
        var_java_lang_String_m = string;
    }

    public void q(Frame frame) {
        if (this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.contains(frame)) {
            return;
        }
        this.var_java_util_List_gg_vape_ui_click_frame_Frame__I.add(frame);
    }

    public List<Frame> Y() {
        return this.var_java_util_List_gg_vape_ui_click_frame_Frame__I;
    }

    public void v(Frame frame) {
        if (this.Y().contains(frame)) {
            this.m(frame);
            this.q(frame);
        }
    }

    public FrameStackManager() {
        this.var_java_util_HashMap_gg_vape_ui_click_frame_Frame,_java_lang_Boolean__X = new HashMap();
    }

    static {
        if (FrameStackManager.i() == null) {
            FrameStackManager.Y("uGuaM");
        }
    }
}

