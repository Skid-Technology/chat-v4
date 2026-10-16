/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.GuardedBy
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

final class ListenerCallQueue<L>
implements Runnable {
    private final static Logger logger = Logger.getLogger(ListenerCallQueue.class.getName());
    private final L listener;
    private final Executor executor;
    @GuardedBy(value="this")
    private final Queue<Callback<L>> waitQueue = Queues.newArrayDeque();
    @GuardedBy(value="this")
    private boolean isThreadScheduled;

    ListenerCallQueue(L listener, Executor executor) {
        this.listener = Preconditions.checkNotNull(listener);
        this.executor = Preconditions.checkNotNull(executor);
    }

    synchronized void add(Callback<L> callback) {
        this.waitQueue.add(callback);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void execute() {
        boolean scheduleTaskRunner = false;
        ListenerCallQueue listenerCallQueue = this;
        synchronized (listenerCallQueue) {
            if (!this.isThreadScheduled) {
                this.isThreadScheduled = true;
                scheduleTaskRunner = true;
            }
        }
        if (scheduleTaskRunner) {
            try {
                this.executor.execute(this);
            }
            catch (RuntimeException e) {
                ListenerCallQueue listenerCallQueue2 = this;
                synchronized (listenerCallQueue2) {
                    this.isThreadScheduled = false;
                }
                logger.log(Level.SEVERE, "Exception while running callbacks for " + this.listener + " on " + this.executor, e);
                throw e;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        boolean stillRunning = true;
        block10: while (true) {
            try {
                while (true) {
                    Callback<L> nextToRun;
                    ListenerCallQueue listenerCallQueue = this;
                    synchronized (listenerCallQueue) {
                        Preconditions.checkState(this.isThreadScheduled);
                        nextToRun = this.waitQueue.poll();
                        if (nextToRun == null) {
                            this.isThreadScheduled = false;
                            stillRunning = false;
                            break block10;
                        }
                    }
                    try {
                        nextToRun.call(this.listener);
                        continue block10;
                    }
                    catch (RuntimeException e) {
                        logger.log(Level.SEVERE, "Exception while executing callback: " + this.listener + "." + ((Callback)nextToRun).methodCall, e);
                        continue;
                    }
                    break;
                }
            }
            catch (Throwable throwable) {
                if (stillRunning) {
                    ListenerCallQueue listenerCallQueue = this;
                    synchronized (listenerCallQueue) {
                        this.isThreadScheduled = false;
                    }
                }
                throw throwable;
            }
        }
    }

    static abstract class Callback<L> {
        private final String methodCall;

        Callback(String methodCall) {
            this.methodCall = methodCall;
        }

        abstract void call(L var1);

        void enqueueOn(Iterable<ListenerCallQueue<L>> queues) {
            for (ListenerCallQueue<L> queue : queues) {
                queue.add(this);
            }
        }
    }
}

