package gg.vape.tutorial;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.ui.click.component.GuiComponent;
import java.util.function.Supplier;

public class SingleComponentHighlightTutorialAction extends HighlightTutorialAction {
   private static final String g = "start ";
   private final Supplier<GuiComponent> componentSupplier;

   public SingleComponentHighlightTutorialAction(Supplier<GuiComponent> componentSupplier, String string, String string2, boolean bl) {
      super(new TutorialActionComponent(string, string2), bl);
      this.componentSupplier = componentSupplier;
   }

   public SingleComponentHighlightTutorialAction(GuiComponent guiComponent, String string, String string2, boolean bl) {
      this(() -> guiComponent, string, string2, bl);
   }

   public GuiComponent getHighlightedComponent() {
      if (!this.getHighlightedComponents().isEmpty()) {
         return this.getHighlightedComponents().get(0);
      } else {
         if (this.componentSupplier != null) {
            GuiComponent comp = this.componentSupplier.get();
            if (comp != null) {
               this.getHighlightedComponents().add(comp);
               return comp;
            }
         }

         return null;
      }
   }

   @Override
   public RectData getHighlightBounds() {
      GuiComponent guiComponent = this.getHighlightedComponent();
      if (guiComponent == null) {
         return new RectData(0.0, 0.0, 0.0, 0.0);
      } else {
         guiComponent.c();
         return new RectData(guiComponent.G$src$D$1b2f02a(), guiComponent.n(), guiComponent.A(), guiComponent.L());
      }
   }

   @Override
   public void start() {
      Vape.debugLog("start " + this);
      GuiComponent comp = this.getHighlightedComponent();
      if (comp != null) {
         if (this.includesHiddenComponents()) {
            SingleComponentHighlightAdvanceClickListener singleComponentHighlightAdvanceClickListener = new SingleComponentHighlightAdvanceClickListener(this);
            this.registerMouseListener(comp, singleComponentHighlightAdvanceClickListener);
            this.getComponent().getActionButton().setVisible(false);
         }
      }
   }

   @Override
   public boolean isTargetReady() {
      GuiComponent comp = this.getHighlightedComponent();
      return comp != null && comp.V$src$Z$1xhop3l();
   }
}
