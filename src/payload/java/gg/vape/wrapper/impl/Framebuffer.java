package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MFramebuffer;
import gg.vape.wrapper.Wrapper;

public class Framebuffer extends Wrapper {
   public Framebuffer(Object object) {
      super(object);
   }

   public void createFramebuffer(int n, int n2) {
      if (ForgeVersion.MC_1_16_5.d()) {
         MFramebuffer.w(vapeInstance.getMappings().R9, this.I, n, n2, false);
      } else {
         MFramebuffer.G(vapeInstance.getMappings().R9, this.I, n, n2);
      }
   }

   public void unbindFramebuffer() {
      MFramebuffer.m(vapeInstance.getMappings().R9, this.I);
   }

   public void unbindFramebufferTexture() {
      MFramebuffer.D(vapeInstance.getMappings().R9, this.I);
   }

   public void setDepthBuffer(int n) {
      MFramebuffer.depthBuffer(vapeInstance.getMappings().R9, this.I, n);
   }

   public void x() {
      MFramebuffer.e(vapeInstance.getMappings().R9, this.I);
   }

   public static Framebuffer create(int n, int n2, boolean bl) {
      return ForgeVersion.MC_1_17.d()
         ? new Framebuffer(MFramebuffer.create(vapeInstance.getMappings().R9, n, n2, true))
         : new Framebuffer(MFramebuffer.create(vapeInstance.getMappings().R9, n, n2, bl));
   }

   public int getDepthBuffer() {
      return MFramebuffer.L(vapeInstance.getMappings().R9, this.I);
   }

   public void createBindFramebuffer(int n, int n2) {
      MFramebuffer.f(vapeInstance.getMappings().R9, this.I, n, n2);
   }

   public void bindFramebuffer(boolean bl) {
      MFramebuffer.bindFramebuffer(vapeInstance.getMappings().R9, this.I, bl);
   }

   public void bindFramebufferTexture() {
      MFramebuffer.l(vapeInstance.getMappings().R9, this.I);
   }
}
