/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTextComponentString;
import gg.vape.wrapper.impl.ITextComponent;

public class TextComponentString
extends ITextComponent {
    public TextComponentString(Object handle) {
        super(handle);
    }

    public String getText() {
        return MTextComponentString.getText(TextComponentString.vapeInstance.getMappingsMapperCompat().textComponentString, this.getObject());
    }

    public static TextComponentString create(String text) {
        return new TextComponentString(MTextComponentString.create(TextComponentString.vapeInstance.getMappingsMapperCompat().textComponentString, text));
    }

    public void setText(String text) {
        MTextComponentString.setText(TextComponentString.vapeInstance.getMappingsMapperCompat().textComponentString, this.getObject(), text);
    }
}

