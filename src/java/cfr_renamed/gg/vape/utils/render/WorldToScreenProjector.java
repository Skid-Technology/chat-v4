/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

import gg.vape.utils.datas.FloatPair;
import gg.vape.utils.render.RenderMatrix4f;
import gg.vape.utils.render.RenderVector3f;
import gg.vape.utils.render.RenderVector4f;

public class WorldToScreenProjector {
    public static FloatPair projectToScreen(RenderVector3f worldPosition, RenderMatrix4f modelMatrix, RenderMatrix4f viewMatrix, RenderMatrix4f projectionMatrix, int viewportWidth, int viewportHeight) {
        RenderMatrix4f modelViewMatrix = viewMatrix.multiply(modelMatrix);
        RenderMatrix4f clipMatrix = projectionMatrix.multiply(modelViewMatrix);
        RenderVector4f clipPosition = new RenderVector4f(worldPosition.var_float_x, worldPosition.var_float_y, worldPosition.var_float_z, 1.0f);
        clipMatrix.transform(clipPosition, clipPosition);
        if (clipPosition.var_float_w <= 0.01f) {
            return null;
        }
        float normalizedX = clipPosition.var_float_x / clipPosition.var_float_w;
        float normalizedY = clipPosition.var_float_y / clipPosition.var_float_w;
        float screenX = (normalizedX * 0.5f + 0.5f) * (float)viewportWidth;
        float screenY = (1.0f - (normalizedY * 0.5f + 0.5f)) * (float)viewportHeight;
        return new FloatPair(screenX, screenY);
    }
}

