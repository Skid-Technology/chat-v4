/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.ui.click.MouseButton;

public class GuiMouseEvent
extends Event {
    private MouseButton var_gg_vape_ui_click_MouseButton_A;
    private final static String var_java_lang_String_d;
    private int var_int_V;
    private int var_int_c;
    private static String[] var_java_lang_String_arr_M;
    private final static EventListeners var_gg_vape_event_EventListeners_b;
    private boolean var_boolean_h;

    public GuiMouseEvent(int n, int n2, MouseButton mouseButton) {
        this.var_int_V = n;
        this.var_int_c = n2;
        this.var_gg_vape_ui_click_MouseButton_A = mouseButton;
    }

    public int getX() {
        return this.var_int_V;
    }

    public static void D(String[] stringArray) {
        var_java_lang_String_arr_M = stringArray;
    }

    public static EventListeners getEventListeners() {
        return var_gg_vape_event_EventListeners_b;
    }

    public MouseButton getAction() {
        return this.var_gg_vape_ui_click_MouseButton_A;
    }

    @Override
    public void setCancelled(boolean bl) {
        this.var_boolean_h = bl;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    public static String[] d() {
        return var_java_lang_String_arr_M;
    }

    public boolean isCancelled() {
        return this.var_boolean_h;
    }

    public String toString() {
        return String.format(var_java_lang_String_d, new Object[]{this.getX(), this.getY(), this.getAction()});
    }

    @Override
    public EventListeners getListeners() {
        return var_gg_vape_event_EventListeners_b;
    }

    public int getY() {
        return this.var_int_c;
    }

    static {
        GuiMouseEvent.D(new String[3]);
        var_java_lang_String_d = "[%s, %s]: %s";
        var_gg_vape_event_EventListeners_b = new EventListeners();
    }
}

