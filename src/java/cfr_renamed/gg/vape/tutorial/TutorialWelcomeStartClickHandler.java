/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.tutorial.TutorialWelcomePanel;
import gg.vape.ui.click.component.GuiClickListener;

class TutorialWelcomeStartClickHandler
implements GuiClickListener {
    final TutorialWelcomePanel var_gg_vape_tutorial_TutorialWelcomePanel_F;

    TutorialWelcomeStartClickHandler(TutorialWelcomePanel tutorialWelcomePanel) {
        this.var_gg_vape_tutorial_TutorialWelcomePanel_F = tutorialWelcomePanel;
    }

    @Override
    public void onPrimaryClick() {
        Vape.INSTANCE.getTutorialManager().startTutorial();
    }
}

