/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.protobuf.CodedOutputStream
 */
package io.netty.handler.codec.protobuf;

import com.google.protobuf.CodedOutputStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.OutputStream;

@ChannelHandler.Sharable
public class ProtobufVarint32LengthFieldPrepender
extends MessageToByteEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        int bodyLen = msg.readableBytes();
        int headerLen = CodedOutputStream.computeRawVarint32Size((int)bodyLen);
        out.ensureWritable(headerLen + bodyLen);
        CodedOutputStream headerOut = CodedOutputStream.newInstance((OutputStream)new ByteBufOutputStream(out), (int)headerLen);
        headerOut.writeRawVarint32(bodyLen);
        headerOut.flush();
        out.writeBytes(msg, msg.readerIndex(), bodyLen);
    }

    @Override
    protected void encode(ChannelHandlerContext x0, Object x1, ByteBuf x2) throws Exception {
        this.encode(x0, (ByteBuf)x1, x2);
    }
}

