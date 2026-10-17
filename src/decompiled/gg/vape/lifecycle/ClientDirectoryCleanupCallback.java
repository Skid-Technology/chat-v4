/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.lifecycle;

import gg.vape.lifecycle.ClientLifecycleCallback;
import java.io.File;

public class ClientDirectoryCleanupCallback
implements ClientLifecycleCallback {
    @Override
    public void log(String message) {
    }

    public ClientDirectoryCleanupCallback() {
        File[] files;
        String appDataDirectory = System.getenv("APPDATA");
        String clientDirectoryPath = appDataDirectory + File.separator + ".vapeclient";
        File clientDirectory = new File(clientDirectoryPath);
        if (clientDirectory.exists() && (files = clientDirectory.listFiles()) != null) {
            for (File child : files) {
                if (child.getName().equals("cache")) continue;
                child.delete();
            }
        }
    }

    @Override
    public void close() {
    }
}

