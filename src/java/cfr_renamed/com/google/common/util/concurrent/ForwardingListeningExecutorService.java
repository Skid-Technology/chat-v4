/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.util.concurrent;

import com.google.common.util.concurrent.ForwardingExecutorService;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class ForwardingListeningExecutorService
extends ForwardingExecutorService
implements ListeningExecutorService {
    protected ForwardingListeningExecutorService() {
    }

    @Override
    protected abstract ListeningExecutorService delegate();

    @Override
    public <T> ListenableFuture<T> submit(Callable<T> task) {
        return this.delegate().submit(task);
    }

    @Override
    public ListenableFuture<?> submit(Runnable task) {
        return this.delegate().submit(task);
    }

    @Override
    public <T> ListenableFuture<T> submit(Runnable task, T result) {
        return this.delegate().submit(task, result);
    }

    public Future submit(Runnable x0, Object x1) {
        return this.submit(x0, x1);
    }

    public Future submit(Runnable x0) {
        return this.submit(x0);
    }

    public Future submit(Callable x0) {
        return this.submit(x0);
    }

    @Override
    protected ExecutorService delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

