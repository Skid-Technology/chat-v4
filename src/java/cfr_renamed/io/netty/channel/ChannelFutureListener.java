/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel;

import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public interface ChannelFutureListener
extends GenericFutureListener<ChannelFuture> {
    final public static ChannelFutureListener CLOSE = new ChannelFutureListener(){

        @Override
        public void operationComplete(ChannelFuture future) {
            future.channel().close();
        }

        @Override
        public void operationComplete(Future x0) throws Exception {
            this.operationComplete((ChannelFuture)x0);
        }
    };
    final public static ChannelFutureListener CLOSE_ON_FAILURE = new ChannelFutureListener(){

        @Override
        public void operationComplete(ChannelFuture future) {
            if (!future.isSuccess()) {
                future.channel().close();
            }
        }

        @Override
        public void operationComplete(Future x0) throws Exception {
            this.operationComplete((ChannelFuture)x0);
        }
    };
    final public static ChannelFutureListener FIRE_EXCEPTION_ON_FAILURE = new ChannelFutureListener(){

        @Override
        public void operationComplete(ChannelFuture future) {
            if (!future.isSuccess()) {
                future.channel().pipeline().fireExceptionCaught(future.cause());
            }
        }

        @Override
        public void operationComplete(Future x0) throws Exception {
            this.operationComplete((ChannelFuture)x0);
        }
    };
}

