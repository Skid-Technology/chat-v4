package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

class MultiComponentHighlightAdvanceClickListener implements GuiMouseListener {
   final MultiComponentHighlightTutorialAction o;

   @Override
   public void I(Point point) {
   }

   MultiComponentHighlightAdvanceClickListener(MultiComponentHighlightTutorialAction multiComponentHighlightTutorialAction) {
      this.o = multiComponentHighlightTutorialAction;
   }

   @Override
   public void g(Point point, MouseClickButton mouseClickButton) {
      if (mouseClickButton == MouseClickButton.LEFT_CLICK) {
         TutorialPage tutorialPage = this.o.getPage();
         if (tutorialPage.getCurrentAction() != null && tutorialPage.getCurrentAction().equals(this.o)) {
            Vape.INSTANCE.getTutorialManager().getCurrentPage().advanceToNextAction();
         }
      }
   }
}
