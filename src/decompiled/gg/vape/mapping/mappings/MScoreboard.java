/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Collection;

public class MScoreboard
extends Mapping {
    public MappingMethod listPlayerScoresMethod;
    private static int[] scoreboardControlFlowState;
    public MappingMethod getPlayersTeamMethod;

    private Collection getPlayerScores(Object scoreboard, Object objective) {
        return (Collection)this.listPlayerScoresMethod.invokeObject(scoreboard, objective);
    }

    public static void setScoreboardControlFlowState(int[] state) {
        scoreboardControlFlowState = state;
    }

    public static Collection getPlayerScores(MScoreboard mapping, Object scoreboard, Object objective) {
        return mapping.getPlayerScores(scoreboard, objective);
    }

    public MScoreboard() {
        this(MScoreboard.getScoreboardControlFlowState());
    }

    private MScoreboard(int[] nArray) {
        super(MappedClasses.F6);
        MScoreboard mScoreboard;
        String string;
        boolean bl;
        Class clazz;
        Class[] classArray;
        int[] nArray2 = nArray;
        if (ForgeVersion.MC_1_7_10.L() && !Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
            classArray = new Class[]{MappedClasses.Y};
            clazz = Collection.class;
            bl = Wrapper.isNativeAvailable;
            string = "func_96534_i";
            mScoreboard = this;
            this.listPlayerScoresMethod = mScoreboard.Y(string, bl, clazz, classArray);
        } else if (ForgeVersion.MC_1_20_6.d()) {
            classArray = new Class[]{MappedClasses.Y};
            clazz = Collection.class;
            bl = true;
            string = "listPlayerScores";
            mScoreboard = this;
            this.listPlayerScoresMethod = mScoreboard.Y(string, bl, clazz, classArray);
        } else if (ForgeVersion.MC_1_17.d()) {
            classArray = new Class[]{MappedClasses.Y};
            clazz = Collection.class;
            bl = Wrapper.isNativeAvailable;
            string = "m_83498_";
            mScoreboard = this;
            this.listPlayerScoresMethod = mScoreboard.Y(string, bl, clazz, classArray);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            classArray = new Class[]{MappedClasses.Y};
            clazz = Collection.class;
            bl = Wrapper.isNativeAvailable;
            string = "getSortedScores";
            mScoreboard = this;
            this.listPlayerScoresMethod = mScoreboard.Y(string, bl, clazz, classArray);
        } else {
            classArray = new Class[]{MappedClasses.Y};
            clazz = Collection.class;
            bl = true;
            string = "getSortedScores";
            mScoreboard = this;
            this.listPlayerScoresMethod = mScoreboard.Y(string, bl, clazz, classArray);
        }
        classArray = new Class[]{String.class};
        clazz = MappedClasses.u6;
        bl = true;
        string = "getPlayersTeam";
        mScoreboard = this;
        this.getPlayersTeamMethod = mScoreboard.Y(string, bl, clazz, classArray);
    }

    private Object getPlayersTeam(Object scoreboard, String playerName) {
        return this.getPlayersTeamMethod.invokeObject(scoreboard, playerName);
    }

    public static int[] getScoreboardControlFlowState() {
        return scoreboardControlFlowState;
    }

    public static Object getPlayersTeam(MScoreboard mapping, Object scoreboard, String playerName) {
        return mapping.getPlayersTeam(scoreboard, playerName);
    }

    static {
        MScoreboard.setScoreboardControlFlowState(new int[2]);
    }
}

