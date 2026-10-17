package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.hud.HudModule;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.GuiComponentContract;
import gg.vape.ui.click.component.input.BindValueRowComponent;
import gg.vape.ui.click.component.value.ValueComponentFactory;
import gg.vape.value.Value;

public class HudModuleListEntryToggleClickListener implements GuiClickListener {
   private static final String KEYBIND_LABEL = "Keybind";
   private final HudModule module;
   private final HudModuleListEntry listEntry;

   @Override
   public void onPrimaryClick() {
      HudModuleConfigFrame hudModuleConfigFrame = ClientSettings.getFrame(HudModuleConfigFrame.class);
      if (hudModuleConfigFrame != null) {
         hudModuleConfigFrame.setSelectedModule(this.module);
         hudModuleConfigFrame.removeMarkedChildren();

         for (Value<?, ?> value : this.module.getValues()) {
            GuiComponent guiComponent = ValueComponentFactory.createMainValueComponent(value);
            if (guiComponent != null) {
               if (value.getParent() != null) {
                  guiComponent.setDisabledOverlayColor(GuiComponentContract.J.r);
               } else {
                  guiComponent.setDisabledOverlayColor(GuiComponentContract.J.i);
               }

               hudModuleConfigFrame.h(guiComponent);
            }
         }

         if (this.module.shouldShowKeybindSetting()) {
            hudModuleConfigFrame.h(new BindValueRowComponent("Keybind", this.module.getBind()));
         }

         this.listEntry.getSettingsButton().setVisible(true);
         hudModuleConfigFrame.setVisible(true);
         hudModuleConfigFrame.U();
         hudModuleConfigFrame.t(hudModuleConfigFrame.L());
         hudModuleConfigFrame.beginOpening();
         hudModuleConfigFrame.l$src$V$1mibm4x();
      }
   }

   public HudModuleListEntryToggleClickListener(HudModuleListEntry hudModuleListEntry, HudModule hudModule) {
      this.listEntry = hudModuleListEntry;
      this.module = hudModule;
   }
}
