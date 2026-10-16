/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL15;

public class FloatBufferObject {
    private int bufferId = GL15.glGenBuffers();

    public void delete() {
        GL15.glDeleteBuffers(this.bufferId);
    }

    public void allocate(long sizeBytes) {
        GL15.glBufferData(34962, sizeBytes, 35048);
    }

    public int getBufferId() {
        return this.bufferId;
    }

    public void bind() {
        GL15.glBindBuffer(34962, this.bufferId);
    }

    public void upload(FloatBuffer floatBuffer) {
        GL15.glBufferSubData(34962, 0L, floatBuffer);
    }

    public void unbind() {
        GL15.glBindBuffer(34962, 0);
    }
}

