/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

public class RenderBatchStateFlags {
    final public boolean cullFaceEnabled;
    final public boolean blendEnabled;
    final public boolean depthTestEnabled;
    final public boolean depthWriteEnabled;

    public RenderBatchStateFlags(boolean cullFaceEnabled, boolean blendEnabled, boolean depthTestEnabled, boolean depthWriteEnabled) {
        this.cullFaceEnabled = cullFaceEnabled;
        this.blendEnabled = blendEnabled;
        this.depthTestEnabled = depthTestEnabled;
        this.depthWriteEnabled = depthWriteEnabled;
    }
}

