/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MTextComponent;
import gg.vape.wrapper.impl.ITextComponent;
import gg.vape.wrapper.impl.ScorePlayerTeam;
import gg.vape.wrapper.impl.ScorePlayerTeamTextComponent;
import gg.vape.wrapper.impl.Style;

public class TextComponent
extends ITextComponent {
    public static TextComponent p(Object object) {
        return new TextComponent(MTextComponent.n(TextComponent.vapeInstance.getMappingsMapperCompat().RA, object));
    }

    public TextComponent(Object object) {
        super(object);
    }

    public String U() {
        return (String)MTextComponent.s(TextComponent.vapeInstance.getMappingsMapperCompat().RA, this.I);
    }

    public Style w() {
        return new Style(MTextComponent.B(TextComponent.vapeInstance.getMappingsMapperCompat().RA, this.I));
    }

    public TextComponent(ScorePlayerTeam team, String playerName) {
        super(new TextComponent(Vape.INSTANCE.getMappingsMapperCompat().scorePlayerTeam.formatPlayerNameComponent(team.getObject(), ScorePlayerTeamTextComponent.B(playerName).getObject())).getObject());
    }
}

