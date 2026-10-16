/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.unmap;

import gg.vape.runtime.NativeBridge;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class GLUtils {
    private FloatBuffer vertexBuffer;
    private FloatBuffer textureCoordinateBuffer;
    private int primitiveMode = 7;
    private int vertexCount;
    private int textureCoordinateIndex = -1;
    private float[] vertexData;
    private int vertexDataIndex = -1;
    private float[] textureCoordinateData;
    private int componentsPerVertex = 2;

    public void initializeVertexBuffer(int vertexCount, int primitiveMode) {
        this.configureVertexBuffer(vertexCount, primitiveMode, 3);
    }

    public void addVertex2D(float x, float y) {
        this.vertexData[this.vertexDataIndex++] = x;
        this.vertexData[this.vertexDataIndex++] = y;
    }

    public void draw() {
        GL11.glEnableClientState(32884);
        if (this.textureCoordinateData != null) {
            GL11.glEnableClientState(32888);
        }
        gg.vape.wrapper.impl.GL11.f(this.componentsPerVertex, 0, this.vertexBuffer);
        if (this.textureCoordinateData != null) {
            NativeBridge.texCoordPointer(2, 0, this.textureCoordinateBuffer);
        }
        GL11.glDrawArrays(this.primitiveMode, 0, this.vertexCount);
        if (this.textureCoordinateData != null) {
            GL11.glDisableClientState(32888);
        }
        GL11.glDisableClientState(32884);
        this.vertexDataIndex = 0;
        this.textureCoordinateIndex = 0;
    }

    public void addVertex(double x, double y, double z) {
        this.vertexData[this.vertexDataIndex++] = (float)x;
        this.vertexData[this.vertexDataIndex++] = (float)y;
        this.vertexData[this.vertexDataIndex++] = (float)z;
    }

    public void enableTextureCoordinates() {
        this.textureCoordinateData = new float[this.vertexCount * 2];
        this.textureCoordinateBuffer = BufferUtils.createFloatBuffer(this.vertexCount * 2);
    }

    public void uploadAndDraw() {
        this.vertexBuffer.put(this.vertexData);
        this.vertexBuffer.flip();
        if (this.textureCoordinateData != null) {
            this.textureCoordinateBuffer.put(this.textureCoordinateData);
            this.textureCoordinateBuffer.flip();
        }
        this.draw();
    }

    public void reset() {
        this.vertexDataIndex = 0;
        this.textureCoordinateIndex = 0;
        this.vertexBuffer.clear();
    }

    public void addTextureCoordinate(float u, float v) {
        this.textureCoordinateData[this.textureCoordinateIndex++] = u;
        this.textureCoordinateData[this.textureCoordinateIndex++] = v;
    }

    public void configureVertexBuffer(int vertexCount, int primitiveMode, int componentsPerVertex) {
        this.vertexCount = vertexCount;
        this.primitiveMode = primitiveMode;
        this.componentsPerVertex = componentsPerVertex;
        this.vertexData = new float[vertexCount * this.componentsPerVertex];
        this.vertexBuffer = BufferUtils.createFloatBuffer(vertexCount * this.componentsPerVertex);
    }
}

