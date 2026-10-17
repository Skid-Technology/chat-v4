package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiClickListener;

public class ModuleCategoryFrameHeaderCollapseToggleClickHandler implements GuiClickListener {
   final ModuleCategoryFrame o;
   final ModuleCategoryFrameHeader u;

   @Override
   public void onPrimaryClick() {
      this.o.w();
   }

   public ModuleCategoryFrameHeaderCollapseToggleClickHandler(ModuleCategoryFrameHeader moduleCategoryFrameHeader, ModuleCategoryFrame moduleCategoryFrame) {
      this.u = moduleCategoryFrameHeader;
      this.o = moduleCategoryFrame;
   }
}
