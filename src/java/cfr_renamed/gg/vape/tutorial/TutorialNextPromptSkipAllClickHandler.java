/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.tutorial.TutorialNextPromptPanel;
import gg.vape.tutorial.TutorialState;
import gg.vape.ui.click.component.GuiClickListener;

public class TutorialNextPromptSkipAllClickHandler
implements GuiClickListener {
    final TutorialNextPromptPanel var_gg_vape_tutorial_TutorialNextPromptPanel_T;

    @Override
    public void onPrimaryClick() {
        Vape.INSTANCE.getTutorialManager().setState(TutorialState.COMPLETED_ALL);
    }

    public TutorialNextPromptSkipAllClickHandler(TutorialNextPromptPanel tutorialNextPromptPanel) {
        this.var_gg_vape_tutorial_TutorialNextPromptPanel_T = tutorialNextPromptPanel;
    }
}

