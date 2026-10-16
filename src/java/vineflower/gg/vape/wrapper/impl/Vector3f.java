package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class Vector3f extends Wrapper {
   public float getZ() {
      return vapeInstance.getMappingsMapperCompat().vector3f.getZ(this.I);
   }

   public static Vector3f create(float x, float y, float z) {
      return new Vector3f(vapeInstance.getMappingsMapperCompat().vector3f.newInstance(x, y, z));
   }

   public Quaternion rotationDegrees(float degrees) {
      return ForgeVersion.MC_1_20_6.d()
         ? Quaternion.fromAxisAngle(this, degrees, true)
         : new Quaternion(vapeInstance.getMappingsMapperCompat().vector3f.rotationDegrees(this.I, degrees));
   }

   public float getX() {
      return vapeInstance.getMappingsMapperCompat().vector3f.getX(this.I);
   }

   public Vector3f(Object handle) {
      super(handle);
   }

   public float getY() {
      return vapeInstance.getMappingsMapperCompat().vector3f.getY(this.I);
   }
}
