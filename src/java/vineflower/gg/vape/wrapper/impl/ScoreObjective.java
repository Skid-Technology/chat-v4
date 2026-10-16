package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MScoreObjective;
import gg.vape.wrapper.Wrapper;

public class ScoreObjective extends Wrapper {
   public TextComponent getFormattedDisplayName() {
      return new TextComponent(MScoreObjective.getFormattedDisplayName(vapeInstance.getMappingsMapperCompat().scoreObjective, this.I));
   }

   public ITextComponent getDisplayNameComponent() {
      return new ITextComponent(MScoreObjective.getDisplayNameComponent(vapeInstance.getMappingsMapperCompat().scoreObjective, this.I));
   }

   public Scoreboard getScoreboard() {
      return new Scoreboard(MScoreObjective.getScoreboard(vapeInstance.getMappingsMapperCompat().scoreObjective, this.I));
   }

   public String getDisplayNameText() {
      return ForgeVersion.MC_1_16_5.d()
         ? this.getDisplayNameComponent().getFormattedText()
         : MScoreObjective.getDisplayNameText(vapeInstance.getMappingsMapperCompat().scoreObjective, this.I);
   }

   public ScoreObjective(Object object) {
      super(object);
   }
}
