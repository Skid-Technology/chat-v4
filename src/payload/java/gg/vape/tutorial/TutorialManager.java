package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.tutorial.page.HudTutorialPage;
import gg.vape.tutorial.page.ModulesTutorialPage;
import gg.vape.tutorial.page.ProfilesTutorialPage;
import gg.vape.tutorial.page.TextGuiTutorialPage;
import java.util.ArrayList;
import java.util.List;

public class TutorialManager {
   private TutorialFrame tutorialFrame;
   private final List<TutorialPage> pages = new ArrayList<>();
   private static final String allCompleteMessage = "all complete";
   private TutorialState state = TutorialState.WELCOME;
   private TutorialPage currentPage;
   private boolean initialized = false;

   public TutorialFrame getTutorialFrame() {
      if (this.tutorialFrame == null) {
         this.tutorialFrame = ClientSettings.getFrame(TutorialFrame.class);
         if (this.tutorialFrame != null && !this.initialized) {
            this.initialized = true;

            for (TutorialPage page : this.pages) {
               page.setFrame(this.tutorialFrame);
            }

            boolean isCompleted = Vape.INSTANCE.getPublicProfileSettings() != null && Vape.INSTANCE.getPublicProfileSettings().tutorialCompleted.getValue();
            if (isCompleted) {
               this.setState(TutorialState.FINISHED);
            } else if (this.state == TutorialState.WELCOME) {
               this.showWelcomeScreen();
            }
         }
      }

      return this.tutorialFrame;
   }

   public void showWelcomeScreen() {
      TutorialFrame frame = this.getTutorialFrame();
      if (frame != null) {
         frame.t$src$V$zbu1jn();
         frame.addChildren(new TutorialWelcomePanel());
         frame.l$src$V$1mibm4x();
         frame.j(true);
         frame.setVisible(true);
      }
   }

   public void setState(TutorialState tutorialState) {
      if (this.state == null
         || !this.state.equals(TutorialState.FINISHED)
         || tutorialState.equals(TutorialState.INDEX)
         || tutorialState.equals(TutorialState.WELCOME)) {
         this.state = tutorialState;
         if ((tutorialState == TutorialState.FINISHED || tutorialState == TutorialState.COMPLETED_ALL) && Vape.INSTANCE.getPublicProfileSettings() != null) {
            Vape.INSTANCE.getPublicProfileSettings().tutorialCompleted.setValue(true);
            Vape.INSTANCE.saveAndStop();
         }

         switch (tutorialState) {
            case WELCOME:
               this.showWelcomeScreen();
               break;
            case COMPLETED_ALL:
               this.showFinishedScreen();
               break;
            case COMPLETED_TUTORIAL:
               this.showNextPagePrompt();
               break;
            case FINISHED:
               TutorialFrame frame = this.getTutorialFrame();
               if (frame != null) {
                  frame.setVisible(false);
               }
         }
      }
   }

   public void refreshCurrentAction() {
      if (this.getCurrentPage() != null && this.state == TutorialState.IN_TUTORIAL && this.getCurrentPage().getCurrentAction() != null) {
         this.getCurrentPage().getCurrentAction().render();
      }
   }

   public void startTutorial() {
      if (!this.pages.isEmpty()) {
         this.openPage(this.pages.get(0));
      }
   }

   public void startNextPage() {
      TutorialPage next = this.getNextPage();
      if (next != null) {
         this.openPage(next);
      } else {
         this.completeCurrentPage();
      }
   }

   public void showNextPagePrompt() {
      TutorialFrame frame = this.getTutorialFrame();
      if (frame != null) {
         frame.t$src$V$zbu1jn();
         TutorialPage next = this.getNextPage();
         frame.addChildren(new TutorialNextPromptPanel(this.currentPage != null ? this.currentPage.getTitle() : "", next != null ? next.getTitle() : ""));
         frame.l$src$V$1mibm4x();
         frame.j(true);
         frame.setVisible(true);
      }
   }

   public void openPage(TutorialPage tutorialPage) {
      TutorialFrame frame = this.getTutorialFrame();
      this.state = TutorialState.IN_TUTORIAL;
      this.currentPage = tutorialPage;
      if (frame != null) {
         frame.t$src$V$zbu1jn();
         frame.l$src$V$1mibm4x();
      }

      tutorialPage.resetAndStart();
   }

   public TutorialPage getNextPage() {
      if (this.currentPage == null) {
         return null;
      } else {
         int n = -1;

         for (int i = 0; i < this.pages.size(); i++) {
            TutorialPage tutorialPage = this.pages.get(i);
            if (tutorialPage.equals(this.currentPage)) {
               n = i;
            }
         }

         return n != -1 && n + 1 < this.pages.size() ? this.pages.get(n + 1) : null;
      }
   }

   public void updateVisibility() {
      TutorialFrame frame = this.getTutorialFrame();
      if (frame != null) {
         boolean shouldShow = this.shouldShowFrame();
         if (shouldShow != frame.V$src$Z$1xhop3l()) {
            frame.setVisible(shouldShow);
         }
      }
   }

   public TutorialPage getCurrentPage() {
      return this.currentPage;
   }

   public void completeCurrentPage() {
      if (this.getNextPage() == null) {
         Vape.debugLog("all complete");
         this.setState(TutorialState.COMPLETED_ALL);
      } else {
         this.setState(TutorialState.COMPLETED_TUTORIAL);
      }
   }

   private boolean shouldShowFrame() {
      return this.state == TutorialState.IN_TUTORIAL && this.currentPage != null
         ? this.currentPage.getCurrentAction() != null && this.currentPage.getCurrentAction().isTargetReady()
         : this.state != TutorialState.FINISHED;
   }

   public void showFinishedScreen() {
      TutorialFrame frame = this.getTutorialFrame();
      if (frame != null) {
         frame.t$src$V$zbu1jn();
         frame.addChildren(new TutorialFinishedPanel());
         frame.l$src$V$1mibm4x();
         frame.j(true);
         frame.setVisible(true);
      }
   }

   private void registerPage(TutorialPage tutorialPage) {
      this.pages.add(tutorialPage);
   }

   public TutorialState getState() {
      return this.state;
   }

   public TutorialManager() {
      this.registerPage(new ModulesTutorialPage());
      this.registerPage(new ProfilesTutorialPage());
      this.registerPage(new TextGuiTutorialPage());
      this.registerPage(new HudTutorialPage());
   }
}
