package gg.vape.ui.click.frame.impl.target;

import gg.vape.event.EventListener;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.frame.impl.hud.HudSettingsFrameBase;

public class TargetInfoSettingsFrame extends HudSettingsFrameBase implements EventListener {
   private final TargetInfoSettings settings = new TargetInfoSettings();
   private final BooleanToggleComponent hitsComparatorToggle;
   private final BooleanToggleComponent showHoveredToggle = new BooleanToggleComponent(this.settings.showHovered);
   private final TargetInfoPreviewComponent preview;
   private final BooleanToggleComponent damageComparatorToggle = new BooleanToggleComponent(this.settings.damageComparator);
   private final BooleanToggleComponent comboCounterToggle = new BooleanToggleComponent(this.settings.comboCounter);
   private final BooleanToggleComponent potsUsedComparatorToggle;

   public TargetInfoSettingsFrame() {
      super("newtargetinfo", "Target Info");
      this.hitsComparatorToggle = new BooleanToggleComponent(this.settings.hitsComparator);
      this.potsUsedComparatorToggle = new BooleanToggleComponent(this.settings.potsUsedComparator);
      this.addSettings(this.showHoveredToggle, this.damageComparatorToggle, this.comboCounterToggle, this.hitsComparatorToggle, this.potsUsedComparatorToggle);
      this.preview = new TargetInfoPreviewComponent(this);
      this.h(this.preview);
   }

   public TargetInfoSettings getSettings() {
      return this.settings;
   }

   @Override
   public void Y() {
      this.o(100.0);
      this.H(true);
   }

   @Override
   protected void renderHudModeBorder() {
   }

   @Override
   public String getName() {
      return "Target Info";
   }
}
