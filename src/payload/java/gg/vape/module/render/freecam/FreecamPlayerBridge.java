package gg.vape.module.render.freecam;

import gg.vape.mapping.mappings.MFreecamPlayerBridge;
import gg.vape.wrapper.Wrapper;

public class FreecamPlayerBridge extends Wrapper {
   public void setCloakZ(double z) {
      MFreecamPlayerBridge.A(vapeInstance.getMappings().w, this.I, z);
   }

   public double getPreviousCloakZ() {
      return MFreecamPlayerBridge.Z(vapeInstance.getMappings().w, this.I);
   }

   public void setPreviousCloakZ(double z) {
      MFreecamPlayerBridge.d(vapeInstance.getMappings().w, this.I, z);
   }

   public double getPreviousCloakY() {
      return MFreecamPlayerBridge.m(vapeInstance.getMappings().w, this.I);
   }

   public double getCloakY() {
      return MFreecamPlayerBridge.Y(vapeInstance.getMappings().w, this.I);
   }

   public double getPreviousCloakX() {
      return MFreecamPlayerBridge.C(vapeInstance.getMappings().w, this.I);
   }

   public void setCloakX(double x) {
      MFreecamPlayerBridge.c(vapeInstance.getMappings().w, this.I, x);
   }

   public double getCloakX() {
      return MFreecamPlayerBridge.T(vapeInstance.getMappings().w, this.I);
   }

   public void setPreviousCloakY(double y) {
      MFreecamPlayerBridge.E(vapeInstance.getMappings().w, this.I, y);
   }

   public void setCloakY(double y) {
      MFreecamPlayerBridge.n(vapeInstance.getMappings().w, this.I, y);
   }

   public void resetBobbingState() {
      MFreecamPlayerBridge.m$src$V$n8prsk(vapeInstance.getMappings().w, this.I);
   }

   public void setPreviousCloakX(double x) {
      MFreecamPlayerBridge.y(vapeInstance.getMappings().w, this.I, x);
   }

   public FreecamPlayerBridge(Object object) {
      super(object);
   }

   public double getCloakZ() {
      return MFreecamPlayerBridge.l(vapeInstance.getMappings().w, this.I);
   }
}
