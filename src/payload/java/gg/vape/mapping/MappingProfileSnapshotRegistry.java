package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.utils.StringPair;
import java.util.ArrayList;

public class MappingProfileSnapshotRegistry {
   private static int X = 0;
   private static int H = 0;
   private static ArrayList<MappingMemberSnapshot> k = new ArrayList<>();
   private static int p = 0;
   private static ArrayList<StringPair> W = new ArrayList<>();
   private static ArrayList<MappingMemberSnapshot> y = new ArrayList<>();
   private static int[] O;
   private static int v = 0;
   private static int N = 0;

   public static void h() {
   }

   public static void Y() {
      W.clear();
      v = 0;
   }

   public static void X(MappingMethod mappingMethod) {
      if (Vape.INSTANCE.isMappingsRemapped() || Vape.INSTANCE.isForgeRemapInactive()) {
         MappingMemberSnapshot mappingMemberSnapshot = new MappingMemberSnapshot(
            mappingMethod.getMappingOwner(),
            mappingMethod.getOwnerClass(),
            mappingMethod.getOriginalName(),
            mappingMethod.getDeclaredReturnType(),
            mappingMethod.getDeclaredParameterTypes(),
            mappingMethod.getRuntimeName(),
            mappingMethod.getResolvedReturnType(),
            mappingMethod.getResolvedParameterTypes(),
            mappingMethod.isMappedMember()
         );
         k.add(mappingMemberSnapshot);
         H++;
      }
   }

   public static void y(int[] nArray) {
      O = nArray;
   }

   public static void j() {
      N++;
   }

   public static int[] T() {
      return O;
   }

   public static void y() {
      p = 0;
      N = 0;
      H = 0;
      X = 0;
   }

   public static void i() {
      v++;
   }

   public static void n(MappingField mappingField) {
      if (Vape.INSTANCE.isMappingsRemapped() || Vape.INSTANCE.isForgeRemapInactive()) {
         MappingMemberSnapshot mappingMemberSnapshot = new MappingMemberSnapshot(
            mappingField.getMappingOwner(),
            mappingField.getOwnerClass(),
            mappingField.getOriginalName(),
            mappingField.getDeclaredFieldType(),
            null,
            mappingField.getRuntimeName(),
            mappingField.getResolvedFieldType(),
            null,
            mappingField.isMappedMember()
         );
         y.add(mappingMemberSnapshot);
         p++;
      }
   }

   public static void X() {
   }

   public static void O() {
      X++;
   }

   public static void v(String string, String string2) {
      W.add(new StringPair(string, string2));
   }

   static {
      y(new int[5]);
   }
}
