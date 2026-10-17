package gg.vape.ui.click.frame.impl.profile;

import gg.vape.config.ProfileModuleSnapshot;
import gg.vape.config.ProfileSnapshot;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.input.BindableInputComponent;

public class ProfileSnapshotModuleDetailsPanel extends PanelComponent {
   private static final int PANEL_WIDTH = 206;
   private final SimpleTextLabelComponent moduleNameLabel;
   private final PanelComponent headerPanel = new PanelComponent(192.0, 10.0);
   private final TextButton enabledButton;
   private final BindableInputComponent bindInput;
   private ProfileModuleSnapshot selectedModule;
   private final TextButton resetButton;
   private final PanelComponent settingsPanel = new PanelComponent(206.0, 144.0);
   private ProfileSnapshot snapshot;

   public ProfileSnapshotModuleDetailsPanel() {
      super(206.0, 170.0);
      this.moduleNameLabel = new SimpleTextLabelComponent("Module Name");
      this.enabledButton = new TextButton("ON", 0.633, J.l, J.l, 16.0, 10.0);
      this.bindInput = new BindableInputComponent(null, J.A);
      this.resetButton = new TextButton("RESET THIS MODULE", 0.633, J.i, J.i, 52.0, 10.0);
      this.settingsPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.settingsPanel.t(144.0);
      SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("SETTINGS");
      simpleTextLabelComponent.setTextColor(J.h);
      simpleTextLabelComponent.setBold(true);
      this.resetButton.setTransparentBackgroundBorder(J.l);
      this.resetButton.setShowDisabledOverlay(false);
      this.resetButton.setBorderAlpha(0.75F);
      this.moduleNameLabel.setTextColor(J.A);
      this.moduleNameLabel.setOffsetX(0.0F);
      this.moduleNameLabel.setFontScale(1.0);
      this.bindInput.setVisible(false);
      simpleTextLabelComponent.setOffsetX(0.0F);
      this.headerPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
      this.headerPanel.h(this.moduleNameLabel);
      this.headerPanel.h(new SpacerComponent(3.0, 0.0));
      this.headerPanel.h(this.enabledButton);
      this.headerPanel.h(new SpacerComponent(3.0, 0.0));
      this.headerPanel.h(this.bindInput);
      this.headerPanel.h(this.resetButton, "alignright");
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.h(this.headerPanel);
      this.h(new SpacerComponent(0.0, 4.0));
      this.h(simpleTextLabelComponent);
      this.h(this.settingsPanel);
      this.resetButton.addClickListener(this::resetSelectedModule);
   }

   public void setSnapshot(ProfileSnapshot snapshot) {
      this.snapshot = snapshot;
   }

   private void resetSelectedModule() {
      this.snapshot.getGuiBuilder().resetModule(this.selectedModule);
   }

   public void setSelectedModule(ProfileModuleSnapshot moduleSnapshot) {
      this.selectedModule = moduleSnapshot;
      this.moduleNameLabel.setText(moduleSnapshot.getName());
      this.enabledButton.setLabelText(moduleSnapshot.isEnabled() ? "ON" : "OFF");
      if (moduleSnapshot.hasBind()) {
         this.bindInput.setVisible(true);
         this.bindInput.setBendable(moduleSnapshot.getBindSnapshot().getValue());
      } else {
         this.bindInput.setVisible(false);
      }

      this.settingsPanel.removeMarkedChildren();

      for (GuiComponent settingComponent : this.snapshot.getGuiBuilder().getModuleComponents(moduleSnapshot)) {
         this.settingsPanel.h(settingComponent);
      }

      this.H(true);
   }
}
