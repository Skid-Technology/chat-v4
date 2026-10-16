/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.input;

import gg.vape.input.InputEventDispatcher;
import gg.vape.input.InputEventHandler;

public class InputFocusLostHandlerPayload
implements InputEventHandler {
    private static String marker;

    public static void setMarker(String marker) {
        InputFocusLostHandlerPayload.marker = marker;
    }

    public static String getMarker() {
        return marker;
    }

    @Override
    public boolean handle(long windowHandle, long focusState) {
        InputEventDispatcher.getInstance().getFocusState().markUnfocused();
        return false;
    }
}

