/*
 * Decompiled with CFR 0.152.
 */
package net.java.games.input;

public final class Version {
    private final static String apiVersion = "2.0.5";
    private final static String buildNumber = "1088";
    private final static String antBuildNumberToken = "@BUILD_NUMBER@";
    private final static String antAPIVersionToken = "@API_VERSION@";

    private Version() {
    }

    public static String getVersion() {
        String version = "Unversioned";
        if (!antAPIVersionToken.equals(apiVersion)) {
            version = apiVersion;
        }
        if (!antBuildNumberToken.equals(buildNumber)) {
            version = version + "-b1088";
        }
        return version;
    }
}

