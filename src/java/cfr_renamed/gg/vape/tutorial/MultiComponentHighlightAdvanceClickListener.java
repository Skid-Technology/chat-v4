/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.tutorial.MultiComponentHighlightTutorialAction;
import gg.vape.tutorial.TutorialPage;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

class MultiComponentHighlightAdvanceClickListener
implements GuiMouseListener {
    final MultiComponentHighlightTutorialAction var_gg_vape_tutorial_MultiComponentHighlightTutorialAction_o;

    @Override
    public void I(Point point) {
    }

    MultiComponentHighlightAdvanceClickListener(MultiComponentHighlightTutorialAction multiComponentHighlightTutorialAction) {
        this.var_gg_vape_tutorial_MultiComponentHighlightTutorialAction_o = multiComponentHighlightTutorialAction;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (mouseClickButton != MouseClickButton.LEFT_CLICK) {
            return;
        }
        TutorialPage tutorialPage = this.var_gg_vape_tutorial_MultiComponentHighlightTutorialAction_o.getPage();
        if (tutorialPage.getCurrentAction() != null && tutorialPage.getCurrentAction().equals(this.var_gg_vape_tutorial_MultiComponentHighlightTutorialAction_o)) {
            Vape.INSTANCE.getTutorialManager().getCurrentPage().advanceToNextAction();
        }
    }
}

