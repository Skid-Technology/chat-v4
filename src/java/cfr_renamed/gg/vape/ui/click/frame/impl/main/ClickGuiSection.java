/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.main;

public enum ClickGuiSection {
    MODULES("Modules"),
    FRIENDS("Friends"),
    PROFILES("Profiles");

    private final static ClickGuiSection[] var_gg_vape_ui_click_frame_impl_main_ClickGuiSection_arr_R;
    private final String var_java_lang_String_a;

    public String A() {
        return this.var_java_lang_String_a;
    }

    private ClickGuiSection(String string2) {
        this.var_java_lang_String_a = string2;
    }

    static {
        String[] stringArray = new String[]{"FRIENDS", "PROFILES", "Profiles", "Modules", "Friends", "MODULES"};
        var_gg_vape_ui_click_frame_impl_main_ClickGuiSection_arr_R = new ClickGuiSection[]{MODULES, FRIENDS, PROFILES};
    }
}

