/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.unmap;

import gg.vape.utils.render.StencilUtil;
import org.lwjgl.opengl.GL11;

public class Stencil {
    public static int depthFailOperation;
    public static int referenceValue;
    public static int stencilFailOperation;
    public static int depthPassOperation;
    public static int function;
    public static int mask;

    public Stencil(StencilUtil stencilUtil, int function, int referenceValue, int mask, int stencilFailOperation, int depthFailOperation, int depthPassOperation) {
        Stencil.function = function;
        Stencil.referenceValue = referenceValue;
        Stencil.mask = mask;
        Stencil.stencilFailOperation = stencilFailOperation;
        Stencil.depthFailOperation = depthFailOperation;
        Stencil.depthPassOperation = depthPassOperation;
    }

    public void apply() {
        GL11.glStencilFunc(function, referenceValue, mask);
        GL11.glStencilOp(stencilFailOperation, depthFailOperation, depthPassOperation);
    }
}

