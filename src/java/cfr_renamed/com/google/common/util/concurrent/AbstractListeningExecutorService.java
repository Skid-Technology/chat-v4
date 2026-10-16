/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import javax.annotation.Nullable;

@Beta
public abstract class AbstractListeningExecutorService
extends AbstractExecutorService
implements ListeningExecutorService {
    protected final <T> ListenableFutureTask<T> newTaskFor(Runnable runnable, T value) {
        return ListenableFutureTask.create(runnable, value);
    }

    protected final <T> ListenableFutureTask<T> newTaskFor(Callable<T> callable) {
        return ListenableFutureTask.create(callable);
    }

    @Override
    public ListenableFuture<?> submit(Runnable task) {
        return (ListenableFuture)super.submit(task);
    }

    @Override
    public <T> ListenableFuture<T> submit(Runnable task, @Nullable T result) {
        return (ListenableFuture)super.submit(task, result);
    }

    @Override
    public <T> ListenableFuture<T> submit(Callable<T> task) {
        return (ListenableFuture)super.submit(task);
    }

    public Future submit(Callable x0) {
        return this.submit(x0);
    }

    public Future submit(Runnable x0, Object x1) {
        return this.submit(x0, x1);
    }

    public Future submit(Runnable x0) {
        return this.submit(x0);
    }

    protected RunnableFuture newTaskFor(Callable x0) {
        return this.newTaskFor(x0);
    }

    protected RunnableFuture newTaskFor(Runnable x0, Object x1) {
        return this.newTaskFor(x0, x1);
    }
}

