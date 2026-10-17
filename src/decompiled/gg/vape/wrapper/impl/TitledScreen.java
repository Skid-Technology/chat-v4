/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ITextComponent;
import gg.vape.wrapper.impl.Screen;

public class TitledScreen
extends Screen {
    public String getDisplayedTitle() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return "";
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            ITextComponent title = new ITextComponent(TitledScreen.vapeInstance.getMappingsMapperCompat().titledScreen.getDisplayedTitle(this.I));
            return title.getFormattedText();
        }
        return (String)TitledScreen.vapeInstance.getMappingsMapperCompat().titledScreen.getDisplayedTitle(this.I);
    }

    public TitledScreen(Object titledScreenHandle) {
        super(titledScreenHandle);
    }
}

