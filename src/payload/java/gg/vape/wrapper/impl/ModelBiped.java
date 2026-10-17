package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MModelBiped;

public class ModelBiped extends ModelBipedSkeletonBridge {
   public ModelBiped(Object object) {
      super(object);
   }

   public ModelRenderer getBipedLeftArm() {
      return new ModelRenderer(MModelBiped.j(vapeInstance.getMappingsMapperCompat().Ct, this.I));
   }

   public ModelRenderer getBipedLeftLeg() {
      return new ModelRenderer(MModelBiped.k(vapeInstance.getMappingsMapperCompat().Ct, this.I));
   }

   public ModelRenderer getBipedRightLeg() {
      return new ModelRenderer(MModelBiped.J(vapeInstance.getMappingsMapperCompat().Ct, this.I));
   }

   public ModelRenderer getBipedRightArm() {
      return new ModelRenderer(MModelBiped.R(vapeInstance.getMappingsMapperCompat().Ct, this.I));
   }

   public ModelRenderer Z() {
      return new ModelRenderer(MModelBiped.a(vapeInstance.getMappingsMapperCompat().Ct, this.I));
   }

   public ModelRenderer F() {
      return new ModelRenderer(MModelBiped.x(vapeInstance.getMappingsMapperCompat().Ct, this.I));
   }
}
