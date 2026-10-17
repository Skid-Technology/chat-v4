package gg.vape.lifecycle;

import java.io.File;

public class ClientDirectoryCleanupCallback implements ClientLifecycleCallback {
   @Override
   public void log(String message) {
   }

   public ClientDirectoryCleanupCallback() {
      String appDataDirectory = System.getenv("APPDATA");
      String clientDirectoryPath = appDataDirectory + File.separator + ".vapeclient";
      File clientDirectory = new File(clientDirectoryPath);
      if (clientDirectory.exists()) {
         File[] files = clientDirectory.listFiles();
         if (files != null) {
            for (File child : files) {
               if (!child.getName().equals("cache")) {
                  child.delete();
               }
            }
         }
      }
   }

   @Override
   public void close() {
   }
}
