/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

import gg.vape.unmap.GLUtils;
import gg.vape.utils.render.GlFramebuffer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ItemIconRenderBackend;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ItemIcon
implements ItemIconRenderBackend {
    GlFramebuffer framebuffer;
    private GLUtils texturedQuadBuffer = new GLUtils();

    @Override
    public void capture(ItemStack itemStack, float scale) {
        int framebufferWidth = 32;
        int framebufferHeight = 32;
        boolean scissorEnabled = GL11.glIsEnabled(3089);
        boolean textureEnabled = GL11.glIsEnabled(3553);
        boolean depthTestEnabled = GL11.glIsEnabled(2929);
        if (scissorEnabled) {
            GL11.glDisable(3089);
        }
        if (!textureEnabled) {
            GlStateManager.enableTexture2D();
        }
        if (!depthTestEnabled) {
            GL11.glEnable(2929);
        }
        GlStateManager.depthMask(true);
        FloatBuffer previousClearColor = BufferUtils.createFloatBuffer(16);
        gg.vape.wrapper.impl.GL11.G(3106, previousClearColor);
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().B(1.0);
        ByteBuffer viewportBytes = ByteBuffer.allocateDirect(64);
        viewportBytes.order(ByteOrder.nativeOrder());
        IntBuffer viewport = viewportBytes.asIntBuffer();
        gg.vape.wrapper.impl.GL11.X(2978, viewport);
        GL11.glPushMatrix();
        this.framebuffer = new GlFramebuffer(framebufferWidth, framebufferHeight, true);
        this.framebuffer.bind(true);
        this.framebuffer.bindColorTexture();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear(16384);
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, 16.0, 16.0, 0.0, -1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        GL11.glEnable(32826);
        GL11.glPushMatrix();
        GuiRenderPrimitives.g(itemStack, scale, 0.0, 0.0, true);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glDisable(32826);
        this.framebuffer.restorePreviousTexture();
        this.framebuffer.unbind();
        GL11.glPopMatrix();
        GL11.glClearColor(previousClearColor.get(0), previousClearColor.get(1), previousClearColor.get(2), previousClearColor.get(3));
        GL11.glViewport(viewport.get(0), viewport.get(1), viewport.get(2), viewport.get(3));
        if (!depthTestEnabled) {
            GL11.glDisable(2929);
        }
        if (scissorEnabled) {
            GL11.glEnable(3089);
        }
        if (!textureEnabled) {
            GlStateManager.disableTexture2D();
        }
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
    }

    public ItemIcon() {
        this.texturedQuadBuffer.configureVertexBuffer(8, 7, 2);
        this.texturedQuadBuffer.enableTextureCoordinates();
    }

    @Override
    public void render(float x, float y, int width, int height, float opacity) {
        GL11.glEnable(2903);
        boolean textureEnabled = GL11.glIsEnabled(3553);
        boolean lightingEnabled = GL11.glIsEnabled(2896);
        boolean alphaTestEnabled = GL11.glIsEnabled(3008);
        boolean blendEnabled = GL11.glIsEnabled(3042);
        if (!textureEnabled) {
            GlStateManager.enableTexture2D();
        }
        if (lightingEnabled) {
            GlStateManager.disableLighting();
        }
        if (lightingEnabled) {
            GlStateManager.disableLighting();
        }
        if (!alphaTestEnabled) {
            GlStateManager.enableAlpha();
        }
        if (!blendEnabled) {
            GlStateManager.enableBlend();
        }
        this.framebuffer.bindColorTexture();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, opacity);
        GL11.glPushMatrix();
        float minU = 0.0f;
        float maxV = 1.0f;
        float maxU = 1.0f;
        float minV = 0.0f;
        GL11.glBegin(7);
        GL11.glTexCoord2f(maxU, maxV);
        GL11.glVertex2f(x + (float)width, y);
        GL11.glTexCoord2f(minU, maxV);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(minU, minV);
        GL11.glVertex2f(x, y + (float)height);
        GL11.glTexCoord2f(maxU, minV);
        GL11.glVertex2f(x + (float)width, y + (float)height);
        GL11.glEnd();
        GL11.glPopMatrix();
        this.framebuffer.restorePreviousTexture();
        if (!textureEnabled) {
            GlStateManager.disableTexture2D();
        }
        if (lightingEnabled) {
            GlStateManager.enableLighting();
        }
        if (alphaTestEnabled) {
            GlStateManager.enableAlpha();
        }
        if (blendEnabled) {
            GlStateManager.enableBlend();
        }
    }

    @Override
    public void dispose() {
        this.framebuffer.delete();
        this.framebuffer = null;
    }
}

