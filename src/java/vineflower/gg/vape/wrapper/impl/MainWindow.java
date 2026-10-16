package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MMainWindow;
import gg.vape.wrapper.Wrapper;

public class MainWindow extends Wrapper {
   public MainWindow(Object object) {
      super(object);
   }

   public float i() {
      return MMainWindow.E(vapeInstance.getMappingsMapperCompat().DU, this.I);
   }

   public float q() {
      return MMainWindow.y(vapeInstance.getMappingsMapperCompat().DU, this.I);
   }

   public int x() {
      return MMainWindow.T(vapeInstance.getMappingsMapperCompat().DU, this.I);
   }

   public int o() {
      return MMainWindow.C(vapeInstance.getMappingsMapperCompat().DU, this.I);
   }

   public float r() {
      return MMainWindow.e(vapeInstance.getMappingsMapperCompat().DU, this.I);
   }

   public float A() {
      return MMainWindow.W(vapeInstance.getMappingsMapperCompat().DU, this.I);
   }
}
