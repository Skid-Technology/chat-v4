/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.tutorial.TutorialNextPromptPanel;
import gg.vape.ui.click.component.GuiClickListener;

public class TutorialNextPromptStartNextClickHandler
implements GuiClickListener {
    final TutorialNextPromptPanel var_gg_vape_tutorial_TutorialNextPromptPanel_V;

    @Override
    public void onPrimaryClick() {
        Vape.INSTANCE.getTutorialManager().startNextPage();
    }

    public TutorialNextPromptStartNextClickHandler(TutorialNextPromptPanel tutorialNextPromptPanel) {
        this.var_gg_vape_tutorial_TutorialNextPromptPanel_V = tutorialNextPromptPanel;
    }
}

