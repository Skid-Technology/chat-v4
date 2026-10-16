/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.io;

import java.io.IOException;

abstract class LineBuffer {
    private StringBuilder line = new StringBuilder();
    private boolean sawReturn;

    LineBuffer() {
    }

    protected void add(char[] cbuf, int off, int len) throws IOException {
        int pos = off;
        if (this.sawReturn && len > 0 && this.finishLine(cbuf[pos] == '\n')) {
            ++pos;
        }
        int start = pos;
        int end = off + len;
        while (pos < end) {
            switch (cbuf[pos]) {
                case '\r': {
                    this.line.append(cbuf, start, pos - start);
                    this.sawReturn = true;
                    if (pos + 1 < end && this.finishLine(cbuf[pos + 1] == '\n')) {
                        ++pos;
                    }
                    start = pos + 1;
                    break;
                }
                case '\n': {
                    this.line.append(cbuf, start, pos - start);
                    this.finishLine(true);
                    start = pos + 1;
                }
            }
            ++pos;
        }
        this.line.append(cbuf, start, off + len - start);
    }

    private boolean finishLine(boolean sawNewline) throws IOException {
        String string;
        String string2;
        LineBuffer lineBuffer;
        LineBuffer lineBuffer2 = this;
        String string3 = this.line.toString();
        if (this.sawReturn) {
            String string4 = string3;
            LineBuffer lineBuffer3 = lineBuffer2;
            if (sawNewline) {
                lineBuffer = lineBuffer3;
                string2 = string4;
                string = "\r\n";
            } else {
                lineBuffer = lineBuffer3;
                string2 = string4;
                string = "\r";
            }
        } else {
            String string5 = string3;
            LineBuffer lineBuffer4 = lineBuffer2;
            if (sawNewline) {
                lineBuffer = lineBuffer4;
                string2 = string5;
                string = "\n";
            } else {
                lineBuffer = lineBuffer4;
                string2 = string5;
                string = "";
            }
        }
        lineBuffer.handleLine(string2, string);
        this.line = new StringBuilder();
        this.sawReturn = false;
        return sawNewline;
    }

    protected void finish() throws IOException {
        if (this.sawReturn || this.line.length() > 0) {
            this.finishLine(false);
        }
    }

    protected abstract void handleLine(String var1, String var2) throws IOException;
}

