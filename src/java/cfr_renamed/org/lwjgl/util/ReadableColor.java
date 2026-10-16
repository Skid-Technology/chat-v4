/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.util;

import java.nio.ByteBuffer;
import org.lwjgl.util.Color;

public interface ReadableColor {
    final public static ReadableColor RED = new Color(255, 0, 0);
    final public static ReadableColor ORANGE = new Color(255, 128, 0);
    final public static ReadableColor YELLOW = new Color(255, 255, 0);
    final public static ReadableColor GREEN = new Color(0, 255, 0);
    final public static ReadableColor CYAN = new Color(0, 255, 255);
    final public static ReadableColor BLUE = new Color(0, 0, 255);
    final public static ReadableColor PURPLE = new Color(255, 0, 255);
    final public static ReadableColor WHITE = new Color(255, 255, 255);
    final public static ReadableColor BLACK = new Color(0, 0, 0);
    final public static ReadableColor LTGREY = new Color(192, 192, 192);
    final public static ReadableColor DKGREY = new Color(64, 64, 64);
    final public static ReadableColor GREY = new Color(128, 128, 128);

    public int getRed();

    public int getGreen();

    public int getBlue();

    public int getAlpha();

    public byte getRedByte();

    public byte getGreenByte();

    public byte getBlueByte();

    public byte getAlphaByte();

    public void writeRGBA(ByteBuffer var1);

    public void writeRGB(ByteBuffer var1);

    public void writeABGR(ByteBuffer var1);

    public void writeBGR(ByteBuffer var1);

    public void writeBGRA(ByteBuffer var1);

    public void writeARGB(ByteBuffer var1);
}

