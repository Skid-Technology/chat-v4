package gg.vape.ui.click.frame.impl;

import gg.vape.Vape;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.module.ModuleComponent;
import java.util.concurrent.CopyOnWriteArrayList;

public class VisibleModuleListFrame extends ModuleCategoryFrame {
   private static VisibleModuleListFrame U2;

   public VisibleModuleListFrame() {
      super(Category.FAVORITES);
      U2 = this;
   }

   @Override
   public void s$src$V$1a2f6mi() {
      this.HF = new ModuleCategoryFrameHeader(this, "newfavorites", "Favorites");
      this.Y(this.HF);
   }

   public static VisibleModuleListFrame U$src$Lgg_vape_ui_click_frame_impl_VisibleModuleListFr$168oq2s() {
      return U2;
   }

   @Override
   public void v() {
   }

   public static void e() {
      U2.removeMarkedChildren();

      try {
         for (Mod mod : new CopyOnWriteArrayList<>(Vape.INSTANCE.getModuleProfileMetadataCodec().getSelectedModules())) {
            ModuleComponent moduleComponent = new ModuleComponent(U2, mod);
            U2.h(moduleComponent);
            moduleComponent.buildValueComponents();
         }
      } catch (Exception var4) {
         Vape.logThrowable(var4);
      }
   }

   @Override
   public void Y() {
   }

   public static ModuleComponent A(Mod mod) {
      for (GuiComponent guiComponent : U2.f()) {
         if (guiComponent instanceof ModuleComponent && ((ModuleComponent)guiComponent).getModule().equals(mod)) {
            return (ModuleComponent)guiComponent;
         }
      }

      return null;
   }
}
