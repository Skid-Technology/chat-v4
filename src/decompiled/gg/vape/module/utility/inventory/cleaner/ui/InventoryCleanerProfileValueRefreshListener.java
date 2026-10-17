/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.ProfileChangeEvent;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerProfileValueComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.module.ModuleComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrame;

public class InventoryCleanerProfileValueRefreshListener
implements EventListener {
    @EventHandler
    public void H(ProfileChangeEvent profileChangeEvent) {
        try {
            ClientSettingsSearchFrame searchFrame;
            if (ClientSettings.mainStack != null && ClientSettings.mainStack.Y() != null) {
                for (Frame frame : ClientSettings.mainStack.Y()) {
                    ModuleCategoryFrame moduleCategoryFrame;
                    if (!(frame instanceof ModuleCategoryFrame) || (moduleCategoryFrame = (ModuleCategoryFrame)frame).f() == null) continue;
                    for (GuiComponent guiComponent : moduleCategoryFrame.f()) {
                        ModuleComponent moduleComponent;
                        if (!(guiComponent instanceof ModuleComponent) || (moduleComponent = (ModuleComponent)guiComponent).getValueComponents() == null) continue;
                        for (GuiComponent guiComponent2 : moduleComponent.getValueComponents()) {
                            if (!(guiComponent2 instanceof InventoryCleanerProfileValueComponent)) continue;
                            InventoryCleanerProfileValueComponent inventoryCleanerProfileValueComponent = (InventoryCleanerProfileValueComponent)guiComponent2;
                            try {
                                inventoryCleanerProfileValueComponent.populate();
                            }
                            catch (Throwable t) {
                                Vape.logThrowable(t);
                            }
                        }
                    }
                }
            }
            if ((searchFrame = ClientSettings.getFrame(ClientSettingsSearchFrame.class)) != null) {
                searchFrame.resetSearchAsync();
            }
        }
        catch (Throwable t) {
            Vape.logThrowable(t);
        }
    }
}

