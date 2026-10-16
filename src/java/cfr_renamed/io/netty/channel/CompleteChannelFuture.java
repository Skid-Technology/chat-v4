/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.CompleteFuture;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

abstract class CompleteChannelFuture
extends CompleteFuture<Void>
implements ChannelFuture {
    private final Channel channel;

    protected CompleteChannelFuture(Channel channel, EventExecutor executor) {
        super(executor);
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        this.channel = channel;
    }

    @Override
    protected EventExecutor executor() {
        EventExecutor e = super.executor();
        if (e == null) {
            return this.channel().eventLoop();
        }
        return e;
    }

    @Override
    public ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.addListener(listener);
        return this;
    }

    @Override
    public ChannelFuture addListeners(GenericFutureListener<? extends Future<? super Void>> ... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public ChannelFuture removeListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.removeListener(listener);
        return this;
    }

    @Override
    public ChannelFuture removeListeners(GenericFutureListener<? extends Future<? super Void>> ... listeners) {
        super.removeListeners(listeners);
        return this;
    }

    @Override
    public ChannelFuture syncUninterruptibly() {
        return this;
    }

    @Override
    public ChannelFuture sync() throws InterruptedException {
        return this;
    }

    @Override
    public ChannelFuture await() throws InterruptedException {
        return this;
    }

    @Override
    public ChannelFuture awaitUninterruptibly() {
        return this;
    }

    @Override
    public Channel channel() {
        return this.channel;
    }

    @Override
    public Void getNow() {
        return null;
    }

    @Override
    public Future awaitUninterruptibly() {
        return this.awaitUninterruptibly();
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
    public Future await() throws InterruptedException {
        return this.await();
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
    public Object getNow() {
        return this.getNow();
    }
}

