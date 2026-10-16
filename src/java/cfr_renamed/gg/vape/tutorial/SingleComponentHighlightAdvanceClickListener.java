/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.tutorial.SingleComponentHighlightTutorialAction;
import gg.vape.tutorial.TutorialPage;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

public class SingleComponentHighlightAdvanceClickListener
implements GuiMouseListener {
    final SingleComponentHighlightTutorialAction var_gg_vape_tutorial_SingleComponentHighlightTutorialAction_H;

    @Override
    public void I(Point point) {
    }

    public SingleComponentHighlightAdvanceClickListener(SingleComponentHighlightTutorialAction singleComponentHighlightTutorialAction) {
        this.var_gg_vape_tutorial_SingleComponentHighlightTutorialAction_H = singleComponentHighlightTutorialAction;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (mouseClickButton != MouseClickButton.LEFT_CLICK) {
            return;
        }
        TutorialPage tutorialPage = this.var_gg_vape_tutorial_SingleComponentHighlightTutorialAction_H.getPage();
        if (tutorialPage.getCurrentAction() != null && tutorialPage.getCurrentAction().equals(this.var_gg_vape_tutorial_SingleComponentHighlightTutorialAction_H)) {
            Vape.INSTANCE.getTutorialManager().getCurrentPage().advanceToNextAction();
        }
    }
}

