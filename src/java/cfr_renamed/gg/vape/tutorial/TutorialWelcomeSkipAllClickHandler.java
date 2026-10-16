/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.tutorial.TutorialState;
import gg.vape.tutorial.TutorialWelcomePanel;
import gg.vape.ui.click.component.GuiClickListener;

class TutorialWelcomeSkipAllClickHandler
implements GuiClickListener {
    final TutorialWelcomePanel var_gg_vape_tutorial_TutorialWelcomePanel_F;

    @Override
    public void onPrimaryClick() {
        Vape.INSTANCE.getTutorialManager().setState(TutorialState.COMPLETED_ALL);
    }

    TutorialWelcomeSkipAllClickHandler(TutorialWelcomePanel tutorialWelcomePanel) {
        this.var_gg_vape_tutorial_TutorialWelcomePanel_F = tutorialWelcomePanel;
    }
}

