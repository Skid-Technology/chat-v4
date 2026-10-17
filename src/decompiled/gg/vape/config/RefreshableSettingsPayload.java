/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.config;

import gg.vape.config.SettingsPayload;

public interface RefreshableSettingsPayload
extends SettingsPayload {
    @Override
    public void initializeDefaults();

    default public void refreshFromCurrentSettings() {
    }
}

