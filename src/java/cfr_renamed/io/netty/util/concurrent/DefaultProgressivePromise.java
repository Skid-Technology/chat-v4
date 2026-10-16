/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ProgressiveFuture;
import io.netty.util.concurrent.ProgressivePromise;
import io.netty.util.concurrent.Promise;

public class DefaultProgressivePromise<V>
extends DefaultPromise<V>
implements ProgressivePromise<V> {
    public DefaultProgressivePromise(EventExecutor executor) {
        super(executor);
    }

    protected DefaultProgressivePromise() {
    }

    @Override
    public ProgressivePromise<V> setProgress(long progress, long total) {
        if (total < 0L) {
            total = -1L;
            if (progress < 0L) {
                throw new IllegalArgumentException("progress: " + progress + " (expected: >= 0)");
            }
        } else if (progress < 0L || progress > total) {
            throw new IllegalArgumentException("progress: " + progress + " (expected: 0 <= progress <= total (" + total + "))");
        }
        if (this.isDone()) {
            throw new IllegalStateException("complete already");
        }
        this.notifyProgressiveListeners(progress, total);
        return this;
    }

    @Override
    public boolean tryProgress(long progress, long total) {
        if (total < 0L) {
            total = -1L;
            if (progress < 0L || this.isDone()) {
                return false;
            }
        } else if (progress < 0L || progress > total || this.isDone()) {
            return false;
        }
        this.notifyProgressiveListeners(progress, total);
        return true;
    }

    @Override
    public ProgressivePromise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener) {
        super.addListener((GenericFutureListener)listener);
        return this;
    }

    @Override
    public ProgressivePromise<V> addListeners(GenericFutureListener<? extends Future<? super V>> ... listeners) {
        super.addListeners((GenericFutureListener[])listeners);
        return this;
    }

    @Override
    public ProgressivePromise<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
        super.removeListener((GenericFutureListener)listener);
        return this;
    }

    @Override
    public ProgressivePromise<V> removeListeners(GenericFutureListener<? extends Future<? super V>> ... listeners) {
        super.removeListeners((GenericFutureListener[])listeners);
        return this;
    }

    @Override
    public ProgressivePromise<V> sync() throws InterruptedException {
        super.sync();
        return this;
    }

    @Override
    public ProgressivePromise<V> syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    @Override
    public ProgressivePromise<V> await() throws InterruptedException {
        super.await();
        return this;
    }

    @Override
    public ProgressivePromise<V> awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    @Override
    public ProgressivePromise<V> setSuccess(V result) {
        super.setSuccess(result);
        return this;
    }

    @Override
    public ProgressivePromise<V> setFailure(Throwable cause) {
        super.setFailure(cause);
        return this;
    }

    @Override
    public Promise setFailure(Throwable x0) {
        return this.setFailure(x0);
    }

    @Override
    public Promise setSuccess(Object x0) {
        return this.setSuccess(x0);
    }

    @Override
    public Promise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public Promise await() throws InterruptedException {
        return this.await();
    }

    @Override
    public Promise syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public Promise sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public Promise removeListeners(GenericFutureListener[] x0) {
        return this.removeListeners(x0);
    }

    @Override
    public Promise removeListener(GenericFutureListener x0) {
        return this.removeListener(x0);
    }

    @Override
    public Promise addListeners(GenericFutureListener[] x0) {
        return this.addListeners(x0);
    }

    @Override
    public Promise addListener(GenericFutureListener x0) {
        return this.addListener(x0);
    }

    @Override
    public Future awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public Future await() throws InterruptedException {
        return this.await();
    }

    @Override
    public Future syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public Future sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public Future removeListeners(GenericFutureListener[] x0) {
        return this.removeListeners(x0);
    }

    @Override
    public Future removeListener(GenericFutureListener x0) {
        return this.removeListener(x0);
    }

    @Override
    public Future addListeners(GenericFutureListener[] x0) {
        return this.addListeners(x0);
    }

    @Override
    public Future addListener(GenericFutureListener x0) {
        return this.addListener(x0);
    }

    @Override
    public ProgressiveFuture awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public ProgressiveFuture await() throws InterruptedException {
        return this.await();
    }

    @Override
    public ProgressiveFuture syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public ProgressiveFuture sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public ProgressiveFuture removeListeners(GenericFutureListener[] x0) {
        return this.removeListeners(x0);
    }

    @Override
    public ProgressiveFuture removeListener(GenericFutureListener x0) {
        return this.removeListener(x0);
    }

    @Override
    public ProgressiveFuture addListeners(GenericFutureListener[] x0) {
        return this.addListeners(x0);
    }

    @Override
    public ProgressiveFuture addListener(GenericFutureListener x0) {
        return this.addListener(x0);
    }
}

