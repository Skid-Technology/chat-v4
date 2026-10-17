package gg.vape.config;

public interface RefreshableSettingsPayload extends SettingsPayload {
   @Override
   void initializeDefaults();

   default void refreshFromCurrentSettings() {
   }
}
