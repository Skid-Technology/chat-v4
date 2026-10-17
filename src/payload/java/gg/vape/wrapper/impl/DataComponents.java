package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MDataComponents;
import gg.vape.wrapper.Wrapper;

public class DataComponents extends Wrapper {
   public DataComponents(Object object) {
      super(object);
   }

   public static DataComponentType E() {
      return new DataComponentType(MDataComponents.E(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType V() {
      return new DataComponentType(MDataComponents.m(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType l() {
      return new DataComponentType(MDataComponents.T(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType X() {
      return new DataComponentType(MDataComponents.e(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType p() {
      return new DataComponentType(MDataComponents.L(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType k() {
      return new DataComponentType(MDataComponents.F(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType d() {
      return new DataComponentType(MDataComponents.a(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType F() {
      return new DataComponentType(MDataComponents.y(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType T() {
      return new DataComponentType(MDataComponents.M(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType o() {
      return new DataComponentType(MDataComponents.h(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType R() {
      return new DataComponentType(MDataComponents.O(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType t() {
      return new DataComponentType(MDataComponents.I(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType O() {
      return new DataComponentType(MDataComponents.z(vapeInstance.getMappingsMapperCompat().q5));
   }

   public static DataComponentType I() {
      return new DataComponentType(MDataComponents.b(vapeInstance.getMappingsMapperCompat().q5));
   }
}
