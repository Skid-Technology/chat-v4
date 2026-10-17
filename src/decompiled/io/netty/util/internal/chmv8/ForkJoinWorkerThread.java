/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.internal.chmv8;

import io.netty.util.internal.chmv8.ForkJoinPool;

public class ForkJoinWorkerThread
extends Thread {
    final ForkJoinPool pool;
    final ForkJoinPool.WorkQueue workQueue;

    protected ForkJoinWorkerThread(ForkJoinPool pool) {
        super("aForkJoinWorkerThread");
        this.pool = pool;
        this.workQueue = pool.registerWorker(this);
    }

    public ForkJoinPool getPool() {
        return this.pool;
    }

    public int getPoolIndex() {
        return this.workQueue.poolIndex >>> 1;
    }

    protected void onStart() {
    }

    protected void onTermination(Throwable exception) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public void run() {
        Throwable exception = null;
        this.onStart();
        this.pool.runWorker(this.workQueue);
        try {
            this.onTermination(exception);
        }
        catch (Throwable ex) {
            exception = ex;
        }
        finally {
            this.pool.deregisterWorker(this, exception);
        }
        catch (Throwable ex) {
            try {
                exception = ex;
            }
            catch (Throwable throwable) {
                try {
                    this.onTermination(exception);
                }
                catch (Throwable ex2) {
                    exception = ex2;
                }
                finally {
                    this.pool.deregisterWorker(this, exception);
                }
                throw throwable;
            }
            try {
                this.onTermination(exception);
            }
            catch (Throwable ex3) {
                if (exception == null) {
                    exception = ex3;
                }
            }
            finally {
                this.pool.deregisterWorker(this, exception);
            }
        }
    }
}

