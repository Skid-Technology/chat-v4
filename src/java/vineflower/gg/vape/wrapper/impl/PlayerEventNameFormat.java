package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class PlayerEventNameFormat extends Wrapper {
   public String getUrl() {
      return vapeInstance.getMappingsMapperCompat().resourcePackSendPacket.getUrl(this.I);
   }

   public void setUrl(String url) {
      vapeInstance.getMappingsMapperCompat().resourcePackSendPacket.setUrl(this.I, url);
   }

   public PlayerEventNameFormat(Object handle) {
      super(handle);
   }

   public String getHash() {
      return vapeInstance.getMappingsMapperCompat().resourcePackSendPacket.getHash(this.I);
   }
}
