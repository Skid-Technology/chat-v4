package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityRemovalReason;
import gg.vape.wrapper.Wrapper;

public class EntityRemovalReason extends Wrapper {
   public static EntityRemovalReason U() {
      return new EntityRemovalReason(MEntityRemovalReason.U(vapeInstance.getMappingsMapperCompat().h));
   }

   public static EntityRemovalReason X() {
      return new EntityRemovalReason(MEntityRemovalReason.N(vapeInstance.getMappingsMapperCompat().h));
   }

   public static EntityRemovalReason c() {
      return new EntityRemovalReason(MEntityRemovalReason.Z(vapeInstance.getMappingsMapperCompat().h));
   }

   public EntityRemovalReason(Object object) {
      super(object);
   }

   public static EntityRemovalReason K() {
      return new EntityRemovalReason(MEntityRemovalReason.k(vapeInstance.getMappingsMapperCompat().h));
   }

   public static EntityRemovalReason P() {
      return new EntityRemovalReason(MEntityRemovalReason.i(vapeInstance.getMappingsMapperCompat().h));
   }
}
