package gg.vape.config;

public enum SettingsDataType {
   GLOBAL(SettingsScope.GLOBAL, GlobalSettingsPayload.class),
   ONLINE(SettingsScope.ONLINE, OnlineSettingsPayload.class);

   private final SettingsScope scope;
   private final Class<? extends SettingsPayload> payloadClass;
   private static final SettingsDataType[] ENUM_VALUES = new SettingsDataType[]{GLOBAL, ONLINE};

   public Class<? extends SettingsPayload> getPayloadClass() {
      return this.payloadClass;
   }

   private SettingsDataType(SettingsScope scope, Class<? extends SettingsPayload> payloadClass) {
      this.scope = scope;
      this.payloadClass = payloadClass;
   }

   public SettingsScope getScope() {
      return this.scope;
   }

   static {
      String[] var10000 = new String[]{"ONLINE", "GLOBAL"};
   }
}
