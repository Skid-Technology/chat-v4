package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MScorePlayerTeam;
import java.util.Optional;

public class ScorePlayerTeam extends Team {
   public static TextComponent formatNameForTeamText(Team team, ITextComponent playerName) {
      if (ForgeVersion.MC_1_16_5.v()) {
         throw new UnsupportedOperationException("This method is only for versions 1.16.5 and above");
      } else {
         return new TextComponent(MScorePlayerTeam.formatNameForTeam(vapeInstance.getMappings().scorePlayerTeam, team.getObject(), playerName.getObject()));
      }
   }

   public static TextComponentBaseBridge formatNameForTeamMutable(Team team, ITextComponent playerName) {
      if (ForgeVersion.MC_1_20_6.v()) {
         throw new UnsupportedOperationException("This method is only for versions 1.16.5 and above");
      } else {
         return new TextComponentBaseBridge(
            MScorePlayerTeam.formatNameForTeam(vapeInstance.getMappings().scorePlayerTeam, team.getObject(), playerName.getObject())
         );
      }
   }

   private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
      return unsupportedOperationException;
   }

   public String getPrefix() {
      if (ForgeVersion.MC_1_20_6.d()) {
         ITextComponent playerPrefix = new ITextComponent(vapeInstance.getMappings().scorePlayerTeam.getPlayerPrefix(this.getObject()));
         return playerPrefix.isNull() ? "" : playerPrefix.getFormattedText();
      } else {
         return MScorePlayerTeam.getPrefix(vapeInstance.getMappings().scorePlayerTeam, this.getObject());
      }
   }

   public ScorePlayerTeam(Object object) {
      super(object);
   }

   public TextFormatting getColor() {
      Object color = vapeInstance.getMappings().scorePlayerTeam.getColor(this.getObject());
      if (color instanceof Optional) {
         color = ((Optional)color).orElse(null);
      }

      return color == null ? null : new TextFormatting(color);
   }

   public static String formatPlayerName(Team team, String playerName) {
      if (ForgeVersion.MC_1_16_5.d()) {
         TextComponent textComponent = new TextComponent(
            vapeInstance.getMappings().scorePlayerTeam.formatPlayerNameComponent(team.getObject(), ScorePlayerTeamTextComponent.B(playerName).getObject())
         );
         return textComponent.U();
      } else {
         return MScorePlayerTeam.formatPlayerName(vapeInstance.getMappings().scorePlayerTeam, team.getObject(), playerName);
      }
   }
}
