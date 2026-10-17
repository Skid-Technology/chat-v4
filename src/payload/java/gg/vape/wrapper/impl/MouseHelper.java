package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MMouseHelper;
import gg.vape.wrapper.Wrapper;

public class MouseHelper extends Wrapper {
   public void D(boolean bl) {
      MMouseHelper.w(vapeInstance.getMappings().qI, this.I, bl);
   }

   public int getDeltaX() {
      return MMouseHelper.N(vapeInstance.getMappings().qI, this.I);
   }

   public int getDeltaY() {
      return MMouseHelper.J(vapeInstance.getMappings().qI, this.I);
   }

   public MouseHelper(Object object) {
      super(object);
   }

   public int z() {
      return MMouseHelper.x(vapeInstance.getMappings().qI, this.I);
   }

   public void H(int n) {
      MMouseHelper.r(vapeInstance.getMappings().qI, this.I, n);
   }

   public void Q() {
      this.D(!this.x());
   }

   public boolean x() {
      return MMouseHelper.H$src$Z$1w8ze45(vapeInstance.getMappings().qI, this.I);
   }

   public int e() {
      return MMouseHelper.H(vapeInstance.getMappings().qI, this.I);
   }

   public boolean z$src$Z$14t0goe() {
      return MMouseHelper.E(vapeInstance.getMappings().qI, this.I);
   }

   public int R() {
      return MMouseHelper.t(vapeInstance.getMappings().qI, this.I);
   }

   public void a(int n) {
      MMouseHelper.i(vapeInstance.getMappings().qI, this.I, n);
   }

   public long e$src$J$14hgru1() {
      return MMouseHelper.T(vapeInstance.getMappings().qI, this.I);
   }

   public int I() {
      return MMouseHelper.z(vapeInstance.getMappings().qI, this.I);
   }

   public int k(int n, boolean bl) {
      return MMouseHelper.m(vapeInstance.getMappings().qI, this.I, n, bl);
   }

   public int P() {
      return MMouseHelper.M(vapeInstance.getMappings().qI, this.I);
   }
}
