/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.manager.client;

import gg.vape.Vape;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.config.GlobalSettingsPayload;
import gg.vape.config.SettingsDataType;
import gg.vape.value.BooleanValue;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.List;

public class GlobalSettingsController {
    private boolean firstRun = false;
    private GlobalSettingsPayload settings;
    private boolean loadFailed = false;
    private final BooleanValue cacheData = BooleanValue.create(null, "Cache data", false, "Caches data locally to improve load time (%appdata%/.vapeclient)");

    public GlobalSettingsController() {
        this.cacheData.addChangeListener(v -> this.saveCacheToFile(v.getEffectiveValue()));
    }

    public boolean isFirstRun() {
        return this.firstRun;
    }

    public BooleanValue getCacheData() {
        return this.cacheData;
    }

    public void save() {
        boolean enabled = this.cacheData.getEffectiveValue();
        if (this.settings != null) {
            this.settings.setCacheEnabled(enabled);
            this.settings.setFirstRun(false);
        }
        this.saveCacheToFile(enabled);
        try {
            if (this.settings != null) {
                ApiServices.getInstance().getSettingsApi().saveSettings(SettingsDataType.GLOBAL, this.settings);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void load() {
        try {
            ApiResponse apiResponse = ApiServices.getInstance().getSettingsApi().loadSettings(SettingsDataType.GLOBAL);
            this.loadFailed = false;
            if (apiResponse == null || !apiResponse.isSuccessful()) {
                this.settings = new GlobalSettingsPayload();
                this.settings.initializeDefaults();
            } else {
                this.settings = (GlobalSettingsPayload)apiResponse.getData();
            }
        }
        catch (Exception exception) {
            if (this.settings == null) {
                this.settings = new GlobalSettingsPayload();
            }
            this.settings.initializeDefaults();
            this.loadFailed = true;
        }
        this.firstRun = this.settings.isFirstRun();
        Boolean cachedPreference = this.readCacheFromFile();
        if (cachedPreference != null) {
            this.cacheData.setPersistenceSuppressed(true);
            this.cacheData.setValue(cachedPreference);
            this.cacheData.setPersistenceSuppressed(false);
            this.settings.setCacheEnabled(cachedPreference);
        } else {
            this.cacheData.setValue(this.settings.isCacheEnabled());
        }
    }

    private void saveCacheToFile(boolean enabled) {
        try {
            List<String> lines;
            File clientDir;
            String appData = System.getenv("APPDATA");
            if (appData == null) {
                appData = System.getProperty("user.home");
            }
            if (!(clientDir = new File(appData + File.separator + ".vapeclient")).exists()) {
                clientDir.mkdirs();
            }
            File cacheFile = new File(clientDir, "cache");
            String credentials = "";
            if (cacheFile.exists() && (lines = Files.readAllLines(cacheFile.toPath(), StandardCharsets.UTF_8)).size() > 1) {
                credentials = "\n" + String.join((CharSequence)"\n", lines.subList(1, lines.size()));
            }
            String content = (enabled ? "1" : "0") + credentials + "\n";
            Files.write(cacheFile.toPath(), content.getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        }
        catch (Throwable t) {
            Vape.logThrowable(t);
        }
    }

    private Boolean readCacheFromFile() {
        try {
            List<String> lines;
            File cacheFile;
            String appData = System.getenv("APPDATA");
            if (appData == null) {
                appData = System.getProperty("user.home");
            }
            if ((cacheFile = new File(appData + File.separator + ".vapeclient" + File.separator + "cache")).exists() && cacheFile.isFile() && !(lines = Files.readAllLines(cacheFile.toPath(), StandardCharsets.UTF_8)).isEmpty()) {
                String firstLine = lines.get(0).trim();
                return "1".equals(firstLine) || "true".equalsIgnoreCase(firstLine);
            }
        }
        catch (Throwable t) {
            Vape.logThrowable(t);
        }
        return null;
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }
}

