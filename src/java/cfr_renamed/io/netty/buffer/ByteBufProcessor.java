/*
 * Decompiled with CFR 0.152.
 */
package io.netty.buffer;

public interface ByteBufProcessor {
    final public static ByteBufProcessor FIND_NUL = new ByteBufProcessor(){

        @Override
        public boolean process(byte value) throws Exception {
            return value != 0;
        }
    };
    final public static ByteBufProcessor FIND_NON_NUL = new ByteBufProcessor(){

        @Override
        public boolean process(byte value) throws Exception {
            return value == 0;
        }
    };
    final public static ByteBufProcessor FIND_CR = new ByteBufProcessor(){

        @Override
        public boolean process(byte value) throws Exception {
            return value != 13;
        }
    };
    final public static ByteBufProcessor FIND_NON_CR = new ByteBufProcessor(){

        @Override
        public boolean process(byte value) throws Exception {
            return value == 13;
        }
    };
    final public static ByteBufProcessor FIND_LF = new ByteBufProcessor(){

        @Override
        public boolean process(byte value) throws Exception {
            return value != 10;
        }
    };
    final public static ByteBufProcessor FIND_NON_LF = new ByteBufProcessor(){

        @Override
        public boolean process(byte value) throws Exception {
            return value == 10;
        }
    };
    final public static ByteBufProcessor FIND_CRLF = new ByteBufProcessor(){

        @Override
        public boolean process(byte value) throws Exception {
            return value != 13 && value != 10;
        }
    };
    final public static ByteBufProcessor FIND_NON_CRLF = new ByteBufProcessor(){

        @Override
        public boolean process(byte value) throws Exception {
            return value == 13 || value == 10;
        }
    };
    final public static ByteBufProcessor FIND_LINEAR_WHITESPACE = new ByteBufProcessor(){

        @Override
        public boolean process(byte value) throws Exception {
            return value != 32 && value != 9;
        }
    };
    final public static ByteBufProcessor FIND_NON_LINEAR_WHITESPACE = new ByteBufProcessor(){

        @Override
        public boolean process(byte value) throws Exception {
            return value == 32 || value == 9;
        }
    };

    public boolean process(byte var1) throws Exception;
}

