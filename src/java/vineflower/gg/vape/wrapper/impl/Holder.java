package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MHolder;
import gg.vape.wrapper.Wrapper;
import java.util.Optional;

public class Holder extends Wrapper {
   public boolean F(ResourceKey jy_12) {
      return MHolder.q(vapeInstance.getMappingsMapperCompat().hN, this.I, jy_12.getObject());
   }

   public String Z() {
      return MHolder.F(vapeInstance.getMappingsMapperCompat().hN, this.I);
   }

   public static Holder A(Object object) {
      return new Holder(MHolder.f(vapeInstance.getMappingsMapperCompat().hN, object));
   }

   public Holder(Object object) {
      super(object);
   }

   public Object N() {
      return MHolder.E(vapeInstance.getMappingsMapperCompat().hN, this.I);
   }

   public Optional f() {
      return (Optional)MHolder.m(vapeInstance.getMappingsMapperCompat().hN, this.I);
   }
}
