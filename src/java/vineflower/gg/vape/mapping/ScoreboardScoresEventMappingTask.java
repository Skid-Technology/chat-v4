package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventScoreboardScores;

public class ScoreboardScoresEventMappingTask extends JavassistMappingTask {
   private static final String SCORES_RETURN_EXPRESSION = "$event.getScores();";

   @Override
   public void transform() {
      EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Vape.INSTANCE.getMappings().scoreboard.listPlayerScoresMethod, EventScoreboardScores.class);
      eventInjectionSpec.setReturnExpression("$event.getScores();");
      this.registerEventInjection(eventInjectionSpec);
   }

   public ScoreboardScoresEventMappingTask() {
      super(MappedClasses.F6);
   }
}
