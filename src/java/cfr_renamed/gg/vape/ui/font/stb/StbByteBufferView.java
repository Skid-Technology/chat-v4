/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.font.stb;

public final class StbByteBufferView {
    public byte[] var_byte_arr_f;
    public int var_int_y;
    public int var_int_C;
    public int var_int_h;

    public StbByteBufferView() {
        this(null, 0, 0);
    }

    public StbByteBufferView(byte[] data, int offset, int length) {
        this.var_byte_arr_f = data;
        this.var_int_C = offset;
        this.var_int_h = 0;
        this.var_int_y = length;
    }

    public StbByteBufferView k() {
        StbByteBufferView copy = new StbByteBufferView();
        copy.var_byte_arr_f = this.var_byte_arr_f;
        copy.var_int_C = this.var_int_C;
        copy.var_int_h = this.var_int_h;
        copy.var_int_y = this.var_int_y;
        return copy;
    }
}

