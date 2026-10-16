/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.bytes;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@ChannelHandler.Sharable
public class ByteArrayEncoder
extends MessageToMessageEncoder<byte[]> {
    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
        out.add(Unpooled.wrappedBuffer(msg));
    }

    @Override
    protected void encode(ChannelHandlerContext x0, Object x1, List x2) throws Exception {
        this.encode(x0, (byte[])x1, (List<Object>)x2);
    }
}

