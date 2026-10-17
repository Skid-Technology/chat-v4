/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module;

import gg.vape.Vape;
import gg.vape.module.Mod;

public class DelayedModuleToggleTask
implements Runnable {
    private final boolean repeatUntilDisabled;
    private final Mod module;
    private final long delayMillis;
    private boolean running = true;

    public DelayedModuleToggleTask(Mod module, long delayMillis, boolean repeatUntilDisabled) {
        this.module = module;
        this.delayMillis = delayMillis;
        this.repeatUntilDisabled = repeatUntilDisabled;
    }

    public boolean isRunning() {
        return this.running;
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(this.delayMillis);
                if (!this.module.isEnabled()) continue;
                this.module.onScheduledAction();
            }
            catch (Exception exception) {
                // empty catch block
            }
        } while (!Vape.INSTANCE.isEnabled() && this.repeatUntilDisabled && this.running);
    }
}

