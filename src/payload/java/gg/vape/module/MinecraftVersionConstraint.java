package gg.vape.module;

import gg.vape.wrapper.impl.ForgeVersion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinecraftVersionConstraint {
   public final ForgeVersion D;
   public final MinecraftVersionComparisonOperator s;

   public static List<MinecraftVersionConstraint> o(List<MinecraftVersionConstraint> list) {
      ArrayList<MinecraftVersionConstraint> arrayList = new ArrayList<>();

      for (MinecraftVersionConstraint minecraftVersionConstraint : list) {
         if (!minecraftVersionConstraint.y()) {
            arrayList.add(minecraftVersionConstraint);
         }
      }

      return arrayList;
   }

   public ForgeVersion L() {
      return this.D;
   }

   public MinecraftVersionConstraint(ForgeVersion forgeVersion, MinecraftVersionComparisonOperator minecraftVersionComparisonOperator) {
      this.D = forgeVersion;
      this.s = minecraftVersionComparisonOperator;
   }

   public MinecraftVersionComparisonOperator l() {
      return this.s;
   }

   public boolean y() {
      return this.s.N(ForgeVersion.c(), this.D.i());
   }

   public static List<MinecraftVersionConstraint> L(MinecraftVersionConstraint... minecraftVersionConstraintArray) {
      return o(Arrays.asList(minecraftVersionConstraintArray));
   }
}
