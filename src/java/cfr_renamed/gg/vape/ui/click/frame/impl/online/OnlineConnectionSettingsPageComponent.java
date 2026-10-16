/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.online;

import gg.vape.ui.click.component.FlowLayoutComponent;

public abstract class OnlineConnectionSettingsPageComponent
extends FlowLayoutComponent {
    private static boolean N0;

    public abstract void s();

    public static boolean N$src$Z$1qjb6jv() {
        boolean bl = OnlineConnectionSettingsPageComponent.a$src$Z$1qtr9tq();
        return true;
    }

    public static void G(boolean bl) {
        N0 = bl;
    }

    public OnlineConnectionSettingsPageComponent() {
        super(104.0);
        this.o(104.0);
        this.setDisabledOverlayColor(OnlineConnectionSettingsPageComponent.J.var_java_awt_Color_t);
    }

    public static boolean a$src$Z$1qtr9tq() {
        return N0;
    }

    static {
        if (OnlineConnectionSettingsPageComponent.a$src$Z$1qtr9tq()) {
            OnlineConnectionSettingsPageComponent.G(true);
        }
    }
}

