package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class Quaternion extends Wrapper {
   public static Quaternion fromAxisAngle(Vector3f axis, float angle, boolean degrees) {
      if (degrees) {
         angle *= (float) (Math.PI / 180.0);
      }

      float halfAngleSin = (float)Math.sin(angle / 2.0F);
      float x = axis.getX() * halfAngleSin;
      float y = axis.getY() * halfAngleSin;
      float z = axis.getZ() * halfAngleSin;
      float w = (float)Math.cos(angle / 2.0F);
      return new Quaternion(vapeInstance.getMappingsMapperCompat().quaternion.newFromComponents(x, y, z, w));
   }

   public float getZ() {
      return vapeInstance.getMappingsMapperCompat().quaternion.getZ(this.I);
   }

   public static Quaternion fromEulerAngles(float xAngle, float yAngle, float zAngle, boolean degrees) {
      if (ForgeVersion.MC_1_20_6.d()) {
         if (degrees) {
            xAngle *= (float) (Math.PI / 180.0);
            yAngle *= (float) (Math.PI / 180.0);
            zAngle *= (float) (Math.PI / 180.0);
         }

         float sinHalfX = (float)Math.sin(0.5F * xAngle);
         float cosHalfX = (float)Math.cos(0.5F * xAngle);
         float sinHalfY = (float)Math.sin(0.5F * yAngle);
         float cosHalfY = (float)Math.cos(0.5F * yAngle);
         float sinHalfZ = (float)Math.sin(0.5F * zAngle);
         float cosHalfZ = (float)Math.cos(0.5F * zAngle);
         float x = sinHalfX * cosHalfY * cosHalfZ + cosHalfX * sinHalfY * sinHalfZ;
         float y = cosHalfX * sinHalfY * cosHalfZ - sinHalfX * cosHalfY * sinHalfZ;
         float z = sinHalfX * sinHalfY * cosHalfZ + cosHalfX * cosHalfY * sinHalfZ;
         float w = cosHalfX * cosHalfY * cosHalfZ - sinHalfX * sinHalfY * sinHalfZ;
         return new Quaternion(vapeInstance.getMappingsMapperCompat().quaternion.newFromComponents(x, y, z, w));
      } else {
         return new Quaternion(vapeInstance.getMappingsMapperCompat().quaternion.newFromEulerAngles(xAngle, yAngle, zAngle, degrees));
      }
   }

   public float getX() {
      return vapeInstance.getMappingsMapperCompat().quaternion.getX(this.I);
   }

   public float getW() {
      return vapeInstance.getMappingsMapperCompat().quaternion.getW(this.I);
   }

   public Quaternion(Object handle) {
      super(handle);
   }

   public float getY() {
      return vapeInstance.getMappingsMapperCompat().quaternion.getY(this.I);
   }
}
