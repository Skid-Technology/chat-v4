package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MScoreObjective extends Mapping {
   private MappingMethod displayNameOrScoreboardMethod;
   private MappingMethod getScoreboardMethod;
   private MappingMethod formattedDisplayNameMethod;

   public static Object getFormattedDisplayName(MScoreObjective mapping, Object objective) {
      return mapping.getFormattedDisplayName(objective);
   }

   public MScoreObjective() {
      this(MScoreboard.getScoreboardControlFlowState());
   }

   private MScoreObjective(int[] nArray) {
      super(MappedClasses.Y);
      if (nArray != null) {
         Class[] classArray = new Class[0];
         Class clazz = MappedClasses.F6;
         boolean bl = true;
         String string = "getScoreboard";
         this.getScoreboardMethod = this.Y(string, bl, clazz, classArray);
         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray2 = new Class[0];
            Class clazz2 = MappedClasses.Yr;
            boolean bl2 = true;
            String string2 = "getDisplayName";
            this.displayNameOrScoreboardMethod = this.Y(string2, bl2, clazz2, classArray2);
            Class[] classArray3 = new Class[0];
            Class clazz3 = MappedClasses.Yr;
            boolean bl3 = true;
            String string3 = "func_237498_g_";
            this.formattedDisplayNameMethod = this.Y(string3, bl3, clazz3, classArray3);
         } else {
            Class[] classArray4 = new Class[0];
            Class<String> clazz4 = String.class;
            boolean bl4 = true;
            String string4 = "getDisplayName";
            this.displayNameOrScoreboardMethod = this.Y(string4, bl4, clazz4, classArray4);
         }
      } else {
         Class[] classArray = new Class[0];
         Class clazz = MappedClasses.F6;
         boolean bl = true;
         String string = "getScoreboard";
         this.displayNameOrScoreboardMethod = this.Y(string, bl, clazz, classArray);
      }
   }

   private Object getDisplayNameComponent(Object objective) {
      return this.displayNameOrScoreboardMethod.invokeObject(objective);
   }

   private Object getFormattedDisplayName(Object objective) {
      return this.formattedDisplayNameMethod.invokeObject(objective);
   }

   public static String getDisplayNameText(MScoreObjective mapping, Object objective) {
      return mapping.getDisplayNameText(objective);
   }

   private String getDisplayNameText(Object objective) {
      return (String)this.displayNameOrScoreboardMethod.invokeObject(objective);
   }

   public static Object getDisplayNameComponent(MScoreObjective mapping, Object objective) {
      return mapping.getDisplayNameComponent(objective);
   }

   private Object getScoreboard(Object objective) {
      return this.getScoreboardMethod.invokeObject(objective);
   }

   public static Object getScoreboard(MScoreObjective mapping, Object objective) {
      return mapping.getScoreboard(objective);
   }
}
