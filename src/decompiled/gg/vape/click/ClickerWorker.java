/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.click;

import gg.vape.Vape;
import gg.vape.click.ClickButton;
import gg.vape.click.ClickEngine;
import gg.vape.input.BadlionKeyBindingEventQueue;
import gg.vape.module.combat.ClickerMod;
import gg.vape.utils.SleepUtil;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClickerWorker
implements Runnable {
    private final AtomicBoolean active = new AtomicBoolean(false);
    private final Object activityMonitor = new Object();
    private boolean started;
    private final ClickerMod clickerModule;

    public ClickerWorker(ClickerMod clickerModule) {
        this.clickerModule = clickerModule;
    }

    public void startOrResume() {
        if (!this.started) {
            this.started = true;
            Thread workerThread = new Thread(this);
            workerThread.start();
        }
        this.resume();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void awaitActivityAndRunClickCycle() {
        ClickEngine clickEngine = this.clickerModule.getClickEngine();
        try {
            Object object = this.activityMonitor;
            synchronized (object) {
                if (!this.active.get()) {
                    try {
                        this.activityMonitor.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            if (clickEngine.getButton() == ClickButton.LEFT && this.clickerModule.isClickCycleBlocked()) {
                return;
            }
            this.clickerModule.runClickCycle();
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    @Override
    public void run() {
        BadlionKeyBindingEventQueue.enterClickerWorker();
        try {
            while (true) {
                SleepUtil.sleep(5L);
                this.awaitActivityAndRunClickCycle();
            }
        }
        catch (Throwable throwable) {
            BadlionKeyBindingEventQueue.leaveClickerWorker();
            throw throwable;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resume() {
        Object object = this.activityMonitor;
        synchronized (object) {
            this.active.set(true);
            this.activityMonitor.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void pause() {
        Object object = this.activityMonitor;
        synchronized (object) {
            this.active.set(false);
        }
    }
}

