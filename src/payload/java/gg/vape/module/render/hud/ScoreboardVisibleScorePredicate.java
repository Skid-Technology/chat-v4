package gg.vape.module.render.hud;

import com.google.common.base.Predicate;
import gg.vape.wrapper.impl.Score;

class ScoreboardVisibleScorePredicate implements Predicate<Score> {
   public boolean apply(Score score) {
      return score.getOwner() != null && !score.getOwner().startsWith("#");
   }
}
