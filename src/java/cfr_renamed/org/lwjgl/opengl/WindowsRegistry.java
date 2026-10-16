/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;

final class WindowsRegistry {
    final static int HKEY_CLASSES_ROOT = 1;
    final static int HKEY_CURRENT_USER = 2;
    final static int HKEY_LOCAL_MACHINE = 3;
    final static int HKEY_USERS = 4;

    WindowsRegistry() {
    }

    static String queryRegistrationKey(int root_key, String subkey, String value) throws LWJGLException {
        switch (root_key) {
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid enum: " + root_key);
            }
        }
        return WindowsRegistry.nQueryRegistrationKey(root_key, subkey, value);
    }

    private static native String nQueryRegistrationKey(int var0, String var1, String var2) throws LWJGLException;

    static {
        Sys.initialize();
    }
}

