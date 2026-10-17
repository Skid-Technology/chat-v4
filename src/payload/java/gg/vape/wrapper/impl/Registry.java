package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRegistry;
import gg.vape.wrapper.Wrapper;
import java.util.Optional;
import java.util.stream.Stream;

public class Registry extends Wrapper {
   public Object t(int n) {
      return MRegistry.p(vapeInstance.getMappingsMapperCompat().CV, this.I, n);
   }

   public int K(Object object) {
      return MRegistry.e(vapeInstance.getMappingsMapperCompat().CV, this.I, object);
   }

   public ResourceLocation W(Object object) {
      return new ResourceLocation(MRegistry.F(vapeInstance.getMappingsMapperCompat().CV, this.I, object));
   }

   public Optional<Holder> t(ResourceLocation resourceLocation) {
      Object object = this.B(resourceLocation);
      return object == null ? Optional.empty() : Optional.of(this.J(object));
   }

   public Object B(ResourceLocation resourceLocation) {
      return MRegistry.d(vapeInstance.getMappingsMapperCompat().CV, this.I, resourceLocation.getObject());
   }

   public Stream o() {
      return MRegistry.h(vapeInstance.getMappingsMapperCompat().CV, this.getObject());
   }

   public Holder J(Object object) {
      return new Holder(MRegistry.S(vapeInstance.getMappingsMapperCompat().CV, this.I, object));
   }

   public Registry(Object object) {
      super(object);
   }
}
