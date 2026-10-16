package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ResourceLocationName extends Wrapper {
   public String getFriendlyReport(ResourceLocationKey resourceKey) {
      return vapeInstance.getMappingsMapperCompat().resourceLocationName.getFriendlyReport(this.I, resourceKey.getObject());
   }

   public String getCompleteReport() {
      return vapeInstance.getMappingsMapperCompat().resourceLocationName.getCompleteReport(this.I);
   }

   public ResourceLocationName(Object reportHandle) {
      super(reportHandle);
   }
}
