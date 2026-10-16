package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MScoreboard;
import gg.vape.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.Collection;

public class Scoreboard extends Wrapper {
   public Collection<Score> getPlayerScores(ScoreObjective objective) {
      Collection scoreHandles = MScoreboard.getPlayerScores(vapeInstance.getMappings().scoreboard, this.I, objective.getObject());
      ArrayList scores = com.google.common.collect.Lists.newArrayList();

      for (Object scoreHandle : scoreHandles) {
         scores.add(new Score(scoreHandle));
      }

      return scores;
   }

   public Scoreboard(Object object) {
      super(object);
   }

   public ScorePlayerTeam getPlayersTeam(String playerName) {
      return new ScorePlayerTeam(MScoreboard.getPlayersTeam(vapeInstance.getMappings().scoreboard, this.I, playerName));
   }
}
