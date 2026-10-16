/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

public enum TutorialState {
    WELCOME,
    INDEX,
    IN_TUTORIAL,
    COMPLETED_TUTORIAL,
    COMPLETED_ALL,
    FINISHED;

    private final static TutorialState[] var_gg_vape_tutorial_TutorialState_arr_p;

    static {
        String[] stringArray = new String[]{"WELCOME", "INDEX", "COMPLETED_TUTORIAL", "IN_TUTORIAL", "COMPLETED_ALL", "FINISHED"};
        var_gg_vape_tutorial_TutorialState_arr_p = new TutorialState[]{WELCOME, INDEX, IN_TUTORIAL, COMPLETED_TUTORIAL, COMPLETED_ALL, FINISHED};
    }
}

