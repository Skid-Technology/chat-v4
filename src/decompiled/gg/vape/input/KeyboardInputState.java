/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.input;

import gg.vape.event.impl.EventKeyPress;
import gg.vape.module.none.ClientSettings;
import java.util.HashMap;

public class KeyboardInputState {
    private int lastKey;
    private boolean lastKeyDown;
    private long lastChangeTime;
    private HashMap<Integer, Boolean> keyStates = new HashMap();
    private boolean canceled;

    public boolean isLastKeyDown() {
        return this.lastKeyDown;
    }

    public long getLastChangeTime() {
        return this.lastChangeTime;
    }

    public boolean isKeyDown(int keyCode) {
        return this.keyStates.getOrDefault(keyCode, false);
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    private void dispatchChange(int keyCode, boolean keyDown) {
        this.lastChangeTime = System.nanoTime();
        this.lastKeyDown = keyDown;
        this.lastKey = keyCode;
        EventKeyPress event = new EventKeyPress(keyCode, keyDown);
        event.fire();
        this.canceled = event.isCanceled();
        if (!ClientSettings.INSTANCE.inputEnabled) {
            this.canceled = true;
        }
    }

    public int getLastKey() {
        return this.lastKey;
    }

    public KeyboardInputState() {
        this.lastChangeTime = System.nanoTime();
    }

    public void setKeyState(int keyCode, boolean keyDown) {
        boolean previousState = this.keyStates.getOrDefault(keyCode, false);
        if (previousState != keyDown) {
            this.dispatchChange(keyCode, keyDown);
        }
        this.keyStates.put(keyCode, keyDown);
    }

    public void releaseKey(int keyCode) {
        this.keyStates.put(keyCode, false);
    }
}

