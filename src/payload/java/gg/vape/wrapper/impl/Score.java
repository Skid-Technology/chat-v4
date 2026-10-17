package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MScore;
import gg.vape.wrapper.Wrapper;

public class Score extends Wrapper {
   public Score(Object object) {
      super(object);
   }

   public String getOwner() {
      return MScore.getOwner(vapeInstance.getMappingsMapperCompat().score, this.I);
   }

   public int getScore() {
      return MScore.getScore(vapeInstance.getMappingsMapperCompat().score, this.I);
   }
}
