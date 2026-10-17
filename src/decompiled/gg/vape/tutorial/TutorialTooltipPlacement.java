/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

public enum TutorialTooltipPlacement {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT;

    private static final TutorialTooltipPlacement[] E;

    static {
        String[] stringArray = new String[]{"LEFT", "TOP", "BOTTOM", "RIGHT"};
        E = new TutorialTooltipPlacement[]{TOP, BOTTOM, LEFT, RIGHT};
    }
}

