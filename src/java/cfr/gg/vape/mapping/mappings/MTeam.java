/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MTeam
extends Mapping {
    private static final String IS_SAME_TEAM_METHOD_NAME = "isSameTeam";
    private final MappingMethod isSameTeamMethod = this.Y("isSameTeam", true, Boolean.TYPE, MappedClasses.Yh);

    public static boolean isSameTeam(MTeam mapping, Object team, Object otherTeam) {
        return mapping.invokeIsSameTeam(team, otherTeam);
    }

    private boolean invokeIsSameTeam(Object team, Object otherTeam) {
        return this.isSameTeamMethod.invokeBoolean(team, otherTeam);
    }

    public MTeam() {
        super(MappedClasses.Yh);
    }
}

