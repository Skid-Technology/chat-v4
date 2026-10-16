/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFlushPromiseNotifier;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.DefaultProgressivePromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ProgressiveFuture;
import io.netty.util.concurrent.ProgressivePromise;
import io.netty.util.concurrent.Promise;

public class DefaultChannelProgressivePromise
extends DefaultProgressivePromise<Void>
implements ChannelProgressivePromise,
ChannelFlushPromiseNotifier.FlushCheckpoint {
    private final Channel channel;
    private long checkpoint;

    public DefaultChannelProgressivePromise(Channel channel) {
        this.channel = channel;
    }

    public DefaultChannelProgressivePromise(Channel channel, EventExecutor executor) {
        super(executor);
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
    public Channel channel() {
        return this.channel;
    }

    @Override
    public ChannelProgressivePromise setSuccess() {
        return this.setSuccess(null);
    }

    @Override
    public ChannelProgressivePromise setSuccess(Void result) {
        super.setSuccess(result);
        return this;
    }

    @Override
    public boolean trySuccess() {
        return this.trySuccess(null);
    }

    @Override
    public ChannelProgressivePromise setFailure(Throwable cause) {
        super.setFailure(cause);
        return this;
    }

    @Override
    public ChannelProgressivePromise setProgress(long progress, long total) {
        super.setProgress(progress, total);
        return this;
    }

    @Override
    public ChannelProgressivePromise addListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.addListener(listener);
        return this;
    }

    @Override
    public ChannelProgressivePromise addListeners(GenericFutureListener<? extends Future<? super Void>> ... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public ChannelProgressivePromise removeListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.removeListener(listener);
        return this;
    }

    @Override
    public ChannelProgressivePromise removeListeners(GenericFutureListener<? extends Future<? super Void>> ... listeners) {
        super.removeListeners(listeners);
        return this;
    }

    @Override
    public ChannelProgressivePromise sync() throws InterruptedException {
        super.sync();
        return this;
    }

    @Override
    public ChannelProgressivePromise syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    @Override
    public ChannelProgressivePromise await() throws InterruptedException {
        super.await();
        return this;
    }

    @Override
    public ChannelProgressivePromise awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    @Override
    public long flushCheckpoint() {
        return this.checkpoint;
    }

    @Override
    public void flushCheckpoint(long checkpoint) {
        this.checkpoint = checkpoint;
    }

    @Override
    public ChannelProgressivePromise promise() {
        return this;
    }

    @Override
    protected void checkDeadLock() {
        if (this.channel().isRegistered()) {
            super.checkDeadLock();
        }
    }

    @Override
    public ProgressivePromise setFailure(Throwable x0) {
        return this.setFailure(x0);
    }

    @Override
    public ProgressivePromise setSuccess(Object x0) {
        return this.setSuccess((Void)x0);
    }

    @Override
    public ProgressivePromise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public ProgressivePromise await() throws InterruptedException {
        return this.await();
    }

    @Override
    public ProgressivePromise syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public ProgressivePromise sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public ProgressivePromise removeListeners(GenericFutureListener[] x0) {
        return this.removeListeners(x0);
    }

    @Override
    public ProgressivePromise removeListener(GenericFutureListener x0) {
        return this.removeListener(x0);
    }

    @Override
    public ProgressivePromise addListeners(GenericFutureListener[] x0) {
        return this.addListeners(x0);
    }

    @Override
    public ProgressivePromise addListener(GenericFutureListener x0) {
        return this.addListener(x0);
    }

    @Override
    public ProgressivePromise setProgress(long x0, long x1) {
        return this.setProgress(x0, x1);
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
    public Promise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public Promise await() throws InterruptedException {
        return this.await();
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
    public Promise setFailure(Throwable x0) {
        return this.setFailure(x0);
    }

    @Override
    public Promise setSuccess(Object x0) {
        return this.setSuccess((Void)x0);
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

    @Override
    public ChannelProgressiveFuture awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public ChannelProgressiveFuture await() throws InterruptedException {
        return this.await();
    }

    @Override
    public ChannelProgressiveFuture syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public ChannelProgressiveFuture sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public ChannelProgressiveFuture removeListeners(GenericFutureListener[] x0) {
        return this.removeListeners(x0);
    }

    @Override
    public ChannelProgressiveFuture removeListener(GenericFutureListener x0) {
        return this.removeListener(x0);
    }

    @Override
    public ChannelProgressiveFuture addListeners(GenericFutureListener[] x0) {
        return this.addListeners(x0);
    }

    @Override
    public ChannelProgressiveFuture addListener(GenericFutureListener x0) {
        return this.addListener(x0);
    }

    @Override
    public ChannelFuture awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public ChannelFuture await() throws InterruptedException {
        return this.await();
    }

    @Override
    public ChannelFuture syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public ChannelFuture sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public ChannelFuture removeListeners(GenericFutureListener[] x0) {
        return this.removeListeners(x0);
    }

    @Override
    public ChannelFuture removeListener(GenericFutureListener x0) {
        return this.removeListener(x0);
    }

    @Override
    public ChannelFuture addListeners(GenericFutureListener[] x0) {
        return this.addListeners(x0);
    }

    @Override
    public ChannelFuture addListener(GenericFutureListener x0) {
        return this.addListener(x0);
    }

    @Override
    public ChannelPromise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public ChannelPromise await() throws InterruptedException {
        return this.await();
    }

    @Override
    public ChannelPromise syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public ChannelPromise sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public ChannelPromise removeListeners(GenericFutureListener[] x0) {
        return this.removeListeners(x0);
    }

    @Override
    public ChannelPromise removeListener(GenericFutureListener x0) {
        return this.removeListener(x0);
    }

    @Override
    public ChannelPromise addListeners(GenericFutureListener[] x0) {
        return this.addListeners(x0);
    }

    @Override
    public ChannelPromise addListener(GenericFutureListener x0) {
        return this.addListener(x0);
    }

    @Override
    public ChannelPromise setFailure(Throwable x0) {
        return this.setFailure(x0);
    }

    @Override
    public ChannelPromise setSuccess() {
        return this.setSuccess();
    }

    @Override
    public ChannelPromise setSuccess(Void x0) {
        return this.setSuccess(x0);
    }

    @Override
    public ChannelPromise promise() {
        return this.promise();
    }
}

