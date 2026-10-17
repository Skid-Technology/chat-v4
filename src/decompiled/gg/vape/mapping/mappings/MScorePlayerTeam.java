/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.mapping.mappings.MScoreboard;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Optional;

public class MScorePlayerTeam
extends Mapping {
    private MappingField prefixField;
    private MappingMethod formatPlayerNameMethod;
    private MappingMethod formatNameForTeamMethod;
    private MappingField playerPrefixField;
    private MappingField colorField;

    private String formatPlayerName(Object team, String playerName) {
        return (String)this.formatPlayerNameMethod.invokeObject(null, team, playerName);
    }

    public static Object formatNameForTeam(MScorePlayerTeam mapping, Object team, Object playerName) {
        return mapping.formatNameForTeam(team, playerName);
    }

    public Object getPlayerPrefix(Object team) {
        return this.playerPrefixField.getObject(team);
    }

    public Object getColor(Object team) {
        return this.colorField.getObject(team);
    }

    public static String getPrefix(MScorePlayerTeam mapping, Object team) {
        return mapping.getPrefix(team);
    }

    private String getPrefix(Object team) {
        return (String)this.prefixField.getObject(team);
    }

    public static String formatPlayerName(MScorePlayerTeam mapping, Object team, String playerName) {
        return mapping.formatPlayerName(team, playerName);
    }

    public MScorePlayerTeam() {
        this(MScoreboard.getScoreboardControlFlowState());
    }

    private MScorePlayerTeam(int[] nArray) {
        super(MappedClasses.u6);
        if (nArray != null) {
            MScorePlayerTeam mScorePlayerTeam;
            String string;
            boolean bl;
            Class<String> clazz;
            MScorePlayerTeam mScorePlayerTeam2;
            String string2;
            boolean bl2;
            Class<String> clazz2;
            Class[] classArray;
            if (ForgeVersion.MC_1_16_5.d()) {
                classArray = new Class[]{MappedClasses.Yh, MappedClasses.Yr};
                clazz2 = MappedClasses.YO;
                bl2 = Wrapper.isNativeAvailable;
                string2 = "func_237500_a_";
                mScorePlayerTeam2 = this;
                this.formatPlayerNameMethod = mScorePlayerTeam2.registerStaticMethod(string2, bl2, clazz2, classArray);
            } else {
                classArray = new Class[]{MappedClasses.Yh, String.class};
                clazz2 = String.class;
                bl2 = true;
                string2 = "formatPlayerName";
                mScorePlayerTeam2 = this;
                this.formatPlayerNameMethod = mScorePlayerTeam2.registerStaticMethod(string2, bl2, clazz2, classArray);
            }
            if (ForgeVersion.MC_1_20_6.d()) {
                clazz = MappedClasses.Yr;
                bl = true;
                string = "playerPrefix";
                mScorePlayerTeam = this;
                this.playerPrefixField = mScorePlayerTeam.J(string, bl, clazz);
                Class clazz22 = ForgeVersion.MC_26_2.d() ? Optional.class : MappedClasses.l5;
                boolean bl22 = true;
                String string22 = "color";
                MScorePlayerTeam mScorePlayerTeam22 = this;
                this.colorField = this.J(string22, bl22, clazz22);
            } else {
                clazz = String.class;
                bl = true;
                string = ForgeVersion.c() >= 23 ? "prefix" : "namePrefixSPT";
                mScorePlayerTeam = this;
                this.prefixField = mScorePlayerTeam.J(string, bl, clazz);
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                classArray = new Class[]{MappedClasses.Yh, MappedClasses.Yr};
                Class clazz3 = MappedClasses.YO;
                string = "formatNameForTeam";
                mScorePlayerTeam = this;
                this.formatNameForTeamMethod = ((MappingMethodBuilder)((MappingMethodBuilder)mScorePlayerTeam.methodBuilder(string, clazz3, classArray).setTypeForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.uM)).setStaticMember(true)).buildMethod();
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MScoreboard.setScoreboardControlFlowState(new int[4]);
            }
            return;
        }
        Class[] classArray = new Class[]{MappedClasses.Yh, String.class};
        Class<String> clazz = String.class;
        boolean bl = true;
        String string = "formatPlayerName";
        MScorePlayerTeam mScorePlayerTeam = this;
        this.formatPlayerNameMethod = mScorePlayerTeam.registerStaticMethod(string, bl, clazz, classArray);
        if (ForgeVersion.MC_1_20_6.d()) {
            Class clazz3 = MappedClasses.Yr;
            boolean bl3 = true;
            String string3 = "playerPrefix";
            MScorePlayerTeam mScorePlayerTeam3 = this;
            this.playerPrefixField = this.J(string3, bl3, clazz3);
            Class clazz4 = ForgeVersion.MC_26_2.d() ? Optional.class : MappedClasses.l5;
            boolean bl4 = true;
            String string4 = "color";
            MScorePlayerTeam mScorePlayerTeam4 = this;
            this.colorField = this.J(string4, bl4, clazz4);
        }
        Class<String> clazz5 = String.class;
        boolean bl5 = true;
        String string5 = ForgeVersion.c() >= 23 ? "prefix" : "namePrefixSPT";
        MScorePlayerTeam mScorePlayerTeam5 = this;
        this.prefixField = this.J(string5, bl5, clazz5);
        if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray2 = new Class[]{MappedClasses.Yh, MappedClasses.Yr};
            Class clazz6 = MappedClasses.YO;
            String string6 = "formatNameForTeam";
            MScorePlayerTeam mScorePlayerTeam6 = this;
            this.formatNameForTeamMethod = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string6, clazz6, classArray2).setTypeForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.uM)).setStaticMember(true)).buildMethod();
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MScoreboard.setScoreboardControlFlowState(new int[4]);
        }
    }

    private Object formatNameForTeam(Object team, Object playerName) {
        return this.formatNameForTeamMethod.invokeObject(null, team, playerName);
    }

    public Object formatPlayerNameComponent(Object team, Object playerName) {
        return this.formatPlayerNameMethod.invokeObject(null, team, playerName);
    }
}

