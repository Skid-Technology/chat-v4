package gg.vape.runtime.model;

import java.util.List;

public class DetachedStringTree {
   private String rootValue;
   List<DetachedStringTreeNode> nodes;
   private static int[] controlFlowMarker;

   public static int[] getControlFlowMarker() {
      return controlFlowMarker;
   }

   public static void setControlFlowMarker(int[] marker) {
      controlFlowMarker = marker;
   }

   static {
      if (getControlFlowMarker() == null) {
         setControlFlowMarker(new int[4]);
      }
   }
}
