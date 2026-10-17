package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.PopupSelectorComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.value.EntityTargetFilterValue;

public class EntityTargetFilterPopupComponent extends PopupSelectorComponent {
   private EntityTargetFilterValue targetFilterValue;
   private final FlowLayoutComponent contentLayout = (FlowLayoutComponent)this.getPopupContent();

   public EntityTargetFilterPopupComponent(EntityTargetFilterValue targetFilterValue) {
      super(new FlowLayoutComponent(50.0));
      this.contentLayout.h(new SpacerComponent(0.0, 2.0));
      this.contentLayout.h(new EntityTargetFilterQuickToggleComponent(targetFilterValue));
      this.contentLayout.h(new BooleanToggleComponent(targetFilterValue.getIgnoreInvisibleValue()));
      this.contentLayout.h(new BooleanToggleComponent(targetFilterValue.getIgnoreNakedValue()));
      this.contentLayout.h(new BooleanToggleComponent(targetFilterValue.getIgnoreBehindWallsValue()));
      this.targetFilterValue = targetFilterValue;
      this.bindValue(targetFilterValue);
      this.Y(20.0);
      this.setExplicitHeight(20.0);
   }

   @Override
   public void H() {
      SmoothFontRenderer fontRenderer = this.getFontRenderer(0.8);
      GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n() + 2.5, this.A() - 5.0, this.L() - 5.0, this.w$src$Z$e457mb() ? J.y : J.l, 2.0F, 0.75F, 1.0F);
      ImageRenderer.drawImage(J.W, (float)(this.G$src$D$1b2f02a() + 8.0), (float)(this.n() + 7.0), "target_single", 6.0F, 6.0F, false);
      fontRenderer.d("Targets", this.G$src$D$1b2f02a() + 18.0, this.n() + 7.0, J.A);
      GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 44.0, this.n() + 8.0, 5.0, 1.0F, J.l);
      String targetSummary = "";
      if (this.targetFilterValue.getPlayersValue().getEffectiveValue()) {
         targetSummary = targetSummary + "Players, ";
      }

      if (this.targetFilterValue.getMobsValue().getEffectiveValue()) {
         targetSummary = targetSummary + "Mobs, ";
      }

      if (this.targetFilterValue.getPeacefulValue().getEffectiveValue()) {
         targetSummary = targetSummary + "Peaceful, ";
      }

      boolean hasExplicitTargets = !targetSummary.isEmpty();
      String ignoredTargetSummary = "";
      if (this.targetFilterValue.getIgnoreInvisibleValue().getEffectiveValue()) {
         ignoredTargetSummary = ignoredTargetSummary + "invisible, ";
      }

      if (this.targetFilterValue.getIgnoreNakedValue().getEffectiveValue()) {
         ignoredTargetSummary = ignoredTargetSummary + "naked, ";
      }

      if (this.targetFilterValue.getIgnoreBehindWallsValue().getEffectiveValue()) {
         ignoredTargetSummary = ignoredTargetSummary + "behind walls, ";
      }

      if (!ignoredTargetSummary.isEmpty()) {
         ignoredTargetSummary = "Ignoring " + ignoredTargetSummary;
      }

      if ((targetSummary = targetSummary + ignoredTargetSummary).endsWith(", ")) {
         targetSummary = targetSummary.substring(0, targetSummary.length() - 2);
      }

      if (!hasExplicitTargets) {
         targetSummary = "None";
      }

      TruncatedTextComponent summaryText = new TruncatedTextComponent(targetSummary, "...", 105.0, 0.8, J.A, false);
      summaryText.renderAt(this.G$src$D$1b2f02a() + 52.0, this.n() + 7.0);
      fontRenderer.d("edit", this.G$src$D$1b2f02a() + this.A() - 20.0, this.n() + 7.0, J.A);
   }
}
