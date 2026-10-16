/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

import gg.vape.tutorial.TutorialPage;
import gg.vape.ui.click.component.GuiClickListener;

class TutorialActionNextClickHandler
implements GuiClickListener {
    final TutorialPage var_gg_vape_tutorial_TutorialPage_o;

    @Override
    public void onPrimaryClick() {
        this.var_gg_vape_tutorial_TutorialPage_o.advanceToNextAction();
    }

    TutorialActionNextClickHandler(TutorialPage bF) {
        this.var_gg_vape_tutorial_TutorialPage_o = bF;
    }
}

