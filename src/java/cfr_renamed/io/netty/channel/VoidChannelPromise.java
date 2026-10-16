/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.AbstractFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import java.util.concurrent.TimeUnit;

final class VoidChannelPromise
extends AbstractFuture<Void>
implements ChannelPromise {
    private final Channel channel;
    private final boolean fireException;

    VoidChannelPromise(Channel channel, boolean fireException) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        this.channel = channel;
        this.fireException = fireException;
    }

    @Override
    public VoidChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        VoidChannelPromise.fail();
        return this;
    }

    @Override
    public VoidChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>> ... listeners) {
        VoidChannelPromise.fail();
        return this;
    }

    @Override
    public VoidChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        return this;
    }

    @Override
    public VoidChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>> ... listeners) {
        return this;
    }

    @Override
    public VoidChannelPromise await() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return this;
    }

    @Override
    public boolean await(long timeout, TimeUnit unit) {
        VoidChannelPromise.fail();
        return false;
    }

    @Override
    public boolean await(long timeoutMillis) {
        VoidChannelPromise.fail();
        return false;
    }

    @Override
    public VoidChannelPromise awaitUninterruptibly() {
        VoidChannelPromise.fail();
        return this;
    }

    @Override
    public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
        VoidChannelPromise.fail();
        return false;
    }

    @Override
    public boolean awaitUninterruptibly(long timeoutMillis) {
        VoidChannelPromise.fail();
        return false;
    }

    @Override
    public Channel channel() {
        return this.channel;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean setUncancellable() {
        return true;
    }

    @Override
    public boolean isCancellable() {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public Throwable cause() {
        return null;
    }

    @Override
    public VoidChannelPromise sync() {
        VoidChannelPromise.fail();
        return this;
    }

    @Override
    public VoidChannelPromise syncUninterruptibly() {
        VoidChannelPromise.fail();
        return this;
    }

    @Override
    public VoidChannelPromise setFailure(Throwable cause) {
        this.fireException(cause);
        return this;
    }

    @Override
    public VoidChannelPromise setSuccess() {
        return this;
    }

    @Override
    public boolean tryFailure(Throwable cause) {
        this.fireException(cause);
        return false;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean trySuccess() {
        return false;
    }

    private static void fail() {
        throw new IllegalStateException("void future");
    }

    @Override
    public VoidChannelPromise setSuccess(Void result) {
        return this;
    }

    @Override
    public boolean trySuccess(Void result) {
        return false;
    }

    @Override
    public Void getNow() {
        return null;
    }

    private void fireException(Throwable cause) {
        if (this.fireException && this.channel.isRegistered()) {
            this.channel.pipeline().fireExceptionCaught(cause);
        }
    }

    @Override
    public Object getNow() {
        return this.getNow();
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

    public ChannelPromise removeListeners(GenericFutureListener[] x0) {
        return this.removeListeners(x0);
    }

    public ChannelPromise removeListener(GenericFutureListener x0) {
        return this.removeListener(x0);
    }

    public ChannelPromise addListeners(GenericFutureListener[] x0) {
        return this.addListeners(x0);
    }

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

    public ChannelFuture removeListeners(GenericFutureListener[] x0) {
        return this.removeListeners(x0);
    }

    public ChannelFuture removeListener(GenericFutureListener x0) {
        return this.removeListener(x0);
    }

    public ChannelFuture addListeners(GenericFutureListener[] x0) {
        return this.addListeners(x0);
    }

    public ChannelFuture addListener(GenericFutureListener x0) {
        return this.addListener(x0);
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
    public boolean trySuccess(Object x0) {
        return this.trySuccess((Void)x0);
    }

    @Override
    public Promise setSuccess(Object x0) {
        return this.setSuccess((Void)x0);
    }
}

