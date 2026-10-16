package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MActiveRenderInfo;
import gg.vape.wrapper.Wrapper;

public class ActiveRenderInfo extends Wrapper {
   public Entity W() {
      return new Entity(MActiveRenderInfo.Z(vapeInstance.getMappings().hi, this.I));
   }

   public void g(Vec3 vec3) {
      MActiveRenderInfo.P(vapeInstance.getMappings().hi, this.I, vec3.getObject());
   }

   public Quaternion G() {
      return new Quaternion(MActiveRenderInfo.d(vapeInstance.getMappings().hi, this.I));
   }

   public float Z() {
      return MActiveRenderInfo.q(vapeInstance.getMappings().hi, this.I);
   }

   public float x() {
      return MActiveRenderInfo.A(vapeInstance.getMappings().hi, this.I);
   }

   public Vec3 o() {
      return new Vec3(MActiveRenderInfo.p(vapeInstance.getMappings().hi, this.I));
   }

   public ActiveRenderInfo(Object object) {
      super(object);
   }

   public BlockReader x$src$Lgg_vape_wrapper_impl_BlockReader_$120g8sh() {
      return new BlockReader(MActiveRenderInfo.i(vapeInstance.getMappings().hi, this.I));
   }
}
