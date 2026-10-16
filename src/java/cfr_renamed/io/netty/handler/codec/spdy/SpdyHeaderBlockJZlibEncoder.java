/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jcraft.jzlib.Deflater
 *  com.jcraft.jzlib.JZlib
 */
package io.netty.handler.codec.spdy;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.JZlib;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.compression.CompressionException;
import io.netty.handler.codec.spdy.SpdyCodecUtil;
import io.netty.handler.codec.spdy.SpdyHeaderBlockRawEncoder;
import io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyVersion;

class SpdyHeaderBlockJZlibEncoder
extends SpdyHeaderBlockRawEncoder {
    private final Deflater var_com_jcraft_jzlib_Deflater_z = new Deflater();
    private boolean finished;

    SpdyHeaderBlockJZlibEncoder(SpdyVersion version, int compressionLevel, int windowBits, int memLevel) {
        super(version);
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        if (windowBits < 9 || windowBits > 15) {
            throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
        }
        if (memLevel < 1 || memLevel > 9) {
            throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
        }
        int resultCode = this.var_com_jcraft_jzlib_Deflater_z.deflateInit(compressionLevel, windowBits, memLevel, JZlib.W_ZLIB);
        if (resultCode != 0) {
            throw new CompressionException("failed to initialize an SPDY header block deflater: " + resultCode);
        }
        resultCode = this.var_com_jcraft_jzlib_Deflater_z.deflateSetDictionary(SpdyCodecUtil.SPDY_DICT, SpdyCodecUtil.SPDY_DICT.length);
        if (resultCode != 0) {
            throw new CompressionException("failed to set the SPDY dictionary: " + resultCode);
        }
    }

    private void setInput(ByteBuf decompressed) {
        byte[] in = new byte[decompressed.readableBytes()];
        decompressed.readBytes(in);
        this.var_com_jcraft_jzlib_Deflater_z.next_in = in;
        this.var_com_jcraft_jzlib_Deflater_z.next_in_index = 0;
        this.var_com_jcraft_jzlib_Deflater_z.avail_in = in.length;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void encode(ByteBuf compressed) {
        try {
            byte[] out = new byte[(int)Math.ceil((double)this.var_com_jcraft_jzlib_Deflater_z.next_in.length * 1.001) + 12];
            this.var_com_jcraft_jzlib_Deflater_z.next_out = out;
            this.var_com_jcraft_jzlib_Deflater_z.next_out_index = 0;
            this.var_com_jcraft_jzlib_Deflater_z.avail_out = out.length;
            int resultCode = this.var_com_jcraft_jzlib_Deflater_z.deflate(2);
            if (resultCode != 0) {
                throw new CompressionException("compression failure: " + resultCode);
            }
            if (this.var_com_jcraft_jzlib_Deflater_z.next_out_index != 0) {
                compressed.writeBytes(out, 0, this.var_com_jcraft_jzlib_Deflater_z.next_out_index);
            }
        }
        finally {
            this.var_com_jcraft_jzlib_Deflater_z.next_in = null;
            this.var_com_jcraft_jzlib_Deflater_z.next_out = null;
        }
    }

    @Override
    public ByteBuf encode(SpdyHeadersFrame frame) throws Exception {
        if (frame == null) {
            throw new IllegalArgumentException("frame");
        }
        if (this.finished) {
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBuf decompressed = super.encode(frame);
        if (decompressed.readableBytes() == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBuf compressed = decompressed.alloc().buffer();
        this.setInput(decompressed);
        this.encode(compressed);
        return compressed;
    }

    @Override
    public void end() {
        if (this.finished) {
            return;
        }
        this.finished = true;
        this.var_com_jcraft_jzlib_Deflater_z.deflateEnd();
        this.var_com_jcraft_jzlib_Deflater_z.next_in = null;
        this.var_com_jcraft_jzlib_Deflater_z.next_out = null;
    }
}

