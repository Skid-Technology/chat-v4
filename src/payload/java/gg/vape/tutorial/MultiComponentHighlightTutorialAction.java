package gg.vape.tutorial;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MultiComponentHighlightTutorialAction extends HighlightTutorialAction {
   private static final String g = "start ";
   private final TutorialTargetSelector targetSelector;
   private final Supplier<GuiComponent> searchRootSupplier;
   private GuiComponent searchRoot;

   public GuiComponent getSearchRoot() {
      if (this.searchRoot != null) {
         return this.searchRoot;
      } else {
         return this.searchRootSupplier != null ? this.searchRootSupplier.get() : null;
      }
   }

   private void findTargetsRecursive(GuiComponent component, List<GuiComponent> result) {
      if (component != null) {
         ArrayList<GuiComponent> targets = this.targetSelector.findTargets(component);
         if (targets != null && !targets.isEmpty()) {
            for (GuiComponent t : targets) {
               if (t != null && !result.contains(t)) {
                  result.add(t);
               }
            }
         }

         if (component.f() != null) {
            for (GuiComponent child : component.f()) {
               this.findTargetsRecursive(child, result);
            }
         }
      }
   }

   @Override
   public boolean isTargetReady() {
      GuiComponent root = this.getSearchRoot();
      if (root != null && root.V$src$Z$1xhop3l()) {
         if (this.getHighlightedComponents().isEmpty()) {
            this.findTargetsRecursive(root, this.getHighlightedComponents());
         }

         boolean hasVisible = false;

         for (GuiComponent comp : this.getHighlightedComponents()) {
            if (comp.V$src$Z$1xhop3l() || this.includesInactiveTargets()) {
               hasVisible = true;
               break;
            }
         }

         if (!hasVisible) {
            this.getHighlightedComponents().clear();
            this.findTargetsRecursive(root, this.getHighlightedComponents());

            for (GuiComponent compx : this.getHighlightedComponents()) {
               if (compx.V$src$Z$1xhop3l() || this.includesInactiveTargets()) {
                  hasVisible = true;
                  break;
               }
            }
         }

         if (hasVisible && this.requiresClick()) {
            MultiComponentHighlightAdvanceClickListener listener = new MultiComponentHighlightAdvanceClickListener(this);

            for (GuiComponent compxx : this.getHighlightedComponents()) {
               if (this.getMouseListener(compxx) == null) {
                  this.registerMouseListener(compxx, listener);
               }
            }
         }

         return hasVisible;
      } else {
         return false;
      }
   }

   @Override
   public RectData getHighlightBounds() {
      if (this.getHighlightedComponents().isEmpty()) {
         GuiComponent root = this.getSearchRoot();
         if (root != null) {
            this.findTargetsRecursive(root, this.getHighlightedComponents());
         }
      }

      if (this.getHighlightedComponents().isEmpty()) {
         return new RectData(0.0, 0.0, 0.0, 0.0);
      } else {
         GuiComponent first = null;
         GuiComponent last = null;

         for (int i = 0; i < this.getHighlightedComponents().size(); i++) {
            GuiComponent comp = this.getHighlightedComponents().get(i);
            if (comp.V$src$Z$1xhop3l() || this.includesInactiveTargets()) {
               comp.F();
               comp.c();
               if (first == null) {
                  first = comp;
               }

               last = comp;
            }
         }

         if (first == null) {
            GuiComponent root = this.getSearchRoot();
            if (root != null) {
               this.getHighlightedComponents().clear();
               this.findTargetsRecursive(root, this.getHighlightedComponents());

               for (int ix = 0; ix < this.getHighlightedComponents().size(); ix++) {
                  GuiComponent comp = this.getHighlightedComponents().get(ix);
                  if (comp.V$src$Z$1xhop3l() || this.includesInactiveTargets()) {
                     comp.F();
                     comp.c();
                     if (first == null) {
                        first = comp;
                     }

                     last = comp;
                  }
               }
            }
         }

         return first == null ? new RectData(0.0, 0.0, 0.0, 0.0) : new RectData(first.G$src$D$1b2f02a(), first.n(), first.A(), last.n() + last.L() - first.n());
      }
   }

   @Override
   public void start() {
      Vape.debugLog("start " + this);
      this.getComponent().getActionButton().setVisible(!this.requiresClick());
      GuiComponent root = this.getSearchRoot();
      if (root != null) {
         this.getHighlightedComponents().clear();
         this.findTargetsRecursive(root, this.getHighlightedComponents());
         if (this.requiresClick()) {
            MultiComponentHighlightAdvanceClickListener listener = new MultiComponentHighlightAdvanceClickListener(this);

            for (GuiComponent comp : this.getHighlightedComponents()) {
               this.registerMouseListener(comp, listener);
            }
         }
      }
   }

   public MultiComponentHighlightTutorialAction(Supplier<GuiComponent> searchRootSupplier, Class clazz, String string, String string2, boolean bl) {
      this(searchRootSupplier, new ClassTutorialTargetSelector(clazz, clazz), string, string2, bl);
   }

   public MultiComponentHighlightTutorialAction(
      Supplier<GuiComponent> searchRootSupplier, TutorialTargetSelector tutorialTargetSelector, String string, String string2, boolean bl
   ) {
      super(new TutorialActionComponent(string, string2), bl);
      this.targetSelector = tutorialTargetSelector;
      this.searchRootSupplier = searchRootSupplier;
      this.searchRoot = null;
   }

   public MultiComponentHighlightTutorialAction(GuiComponent guiComponent, Class clazz, String string, String string2, boolean bl) {
      this(guiComponent, new ClassTutorialTargetSelector(clazz, clazz), string, string2, bl);
   }

   public MultiComponentHighlightTutorialAction(
      GuiComponent guiComponent, TutorialTargetSelector tutorialTargetSelector, String string, String string2, boolean bl
   ) {
      super(new TutorialActionComponent(string, string2), bl);
      this.targetSelector = tutorialTargetSelector;
      this.searchRootSupplier = null;
      this.searchRoot = guiComponent;
   }
}
