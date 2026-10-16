package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.ProfileChangeEvent;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.module.ModuleComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrame;

public class InventoryCleanerProfileValueRefreshListener implements EventListener {
   @EventHandler
   public void H(ProfileChangeEvent profileChangeEvent) {
      try {
         if (ClientSettings.mainStack != null && ClientSettings.mainStack.Y() != null) {
            for (Frame frame : ClientSettings.mainStack.Y()) {
               if (frame instanceof ModuleCategoryFrame) {
                  ModuleCategoryFrame moduleCategoryFrame = (ModuleCategoryFrame)frame;
                  if (moduleCategoryFrame.f() != null) {
                     for (GuiComponent guiComponent : moduleCategoryFrame.f()) {
                        if (guiComponent instanceof ModuleComponent) {
                           ModuleComponent moduleComponent = (ModuleComponent)guiComponent;
                           if (moduleComponent.getValueComponents() != null) {
                              for (GuiComponent guiComponent2 : moduleComponent.getValueComponents()) {
                                 if (guiComponent2 instanceof InventoryCleanerProfileValueComponent) {
                                    InventoryCleanerProfileValueComponent inventoryCleanerProfileValueComponent = (InventoryCleanerProfileValueComponent)guiComponent2;

                                    try {
                                       inventoryCleanerProfileValueComponent.populate();
                                    } catch (Throwable var12) {
                                       Vape.logThrowable(var12);
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         ClientSettingsSearchFrame searchFrame = ClientSettings.getFrame(ClientSettingsSearchFrame.class);
         if (searchFrame != null) {
            searchFrame.resetSearchAsync();
         }
      } catch (Throwable var13) {
         Vape.logThrowable(var13);
      }
   }
}
