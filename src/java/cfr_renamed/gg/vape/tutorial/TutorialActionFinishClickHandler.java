/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.tutorial.TutorialPage;
import gg.vape.ui.click.component.GuiClickListener;

class TutorialActionFinishClickHandler
implements GuiClickListener {
    final TutorialPage var_gg_vape_tutorial_TutorialPage_w;

    TutorialActionFinishClickHandler(TutorialPage tutorialPage) {
        this.var_gg_vape_tutorial_TutorialPage_w = tutorialPage;
    }

    @Override
    public void onPrimaryClick() {
        Vape.INSTANCE.getTutorialManager().completeCurrentPage();
    }
}

