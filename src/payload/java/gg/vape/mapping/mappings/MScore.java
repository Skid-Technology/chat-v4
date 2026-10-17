package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MScore extends Mapping {
   private MappingMethod ownerMethod;
   private MappingMethod scoreValueMethod;

   public static int getScore(MScore mapping, Object score) {
      return mapping.getScore(score);
   }

   private String getOwner(Object score) {
      return (String)this.ownerMethod.invokeObject(score);
   }

   public static String getOwner(MScore mapping, Object score) {
      return mapping.getOwner(score);
   }

   public MScore() {
      this(MScoreboard.getScoreboardControlFlowState());
   }

   private MScore(int[] nArray) {
      super(MappedClasses.DX);
      if (nArray != null) {
         if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray = new Class[0];
            Class<String> clazz = String.class;
            boolean bl = true;
            String string = "owner";
            Class clazz2 = MappedClasses.p;
            this.ownerMethod = this.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
         } else {
            Class[] classArray = new Class[0];
            Class<String> clazz = String.class;
            boolean bl = true;
            String string = "getPlayerName";
            this.ownerMethod = this.Y(string, bl, clazz, classArray);
         }

         Class[] classArray = new Class[0];
         Class<Integer> clazz = int.class;
         String string = "getScorePoints";
         this.scoreValueMethod = this.methodBuilder(string, clazz, classArray)
            .setNameForVersion(ForgeVersion.MC_1_20_6.n(), "value")
            .setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getScore")
            .setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.VN)
            .buildMethod();
      } else {
         GuiComponent.setLegacyComponentState(new GuiComponent[3]);
         Class[] classArray = new Class[0];
         Class<String> clazz = String.class;
         boolean bl = true;
         String string = "getPlayerName";
         this.ownerMethod = this.Y(string, bl, clazz, classArray);
         Class[] classArray2 = new Class[0];
         Class<Integer> clazz3 = int.class;
         String string2 = "getScorePoints";
         this.scoreValueMethod = this.methodBuilder(string2, clazz3, classArray2)
            .setNameForVersion(ForgeVersion.MC_1_20_6.n(), "value")
            .setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getScore")
            .setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.VN)
            .buildMethod();
      }
   }

   private int getScore(Object score) {
      return this.scoreValueMethod.invokeInt(score);
   }
}
