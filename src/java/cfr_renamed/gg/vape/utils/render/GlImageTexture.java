/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

import gg.vape.mapping.MappedClasses;
import gg.vape.unmap.ImageParser;
import gg.vape.unmap.ImageParser$Format;
import gg.vape.utils.render.GlPixelStoreState;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.GlStateManager;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

public class GlImageTexture {
    private final static int GL_TEXTURE_2D = 3553;
    private final static int GL_TEXTURE_WRAP_S = 10242;
    private final static int GL_TEXTURE_WRAP_T = 10243;
    private final static int GL_CLAMP = 10496;
    private final static int GL_CLAMP_TO_EDGE = 33071;
    public int width;
    public int minFilter = 9729;
    public float maxU = 1.0f;
    public float minU = 0.0f;
    final public int defaultWrapMode;
    public int height;
    public float minV = 0.0f;
    public int textureId;
    public int wrapT = 33071;
    public int magFilter = 9728;
    public float maxV = 1.0f;
    private static int lastBoundTextureId = 0;
    public int wrapS = 10496;
    private final int textureTarget;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public GlImageTexture(InputStream inputStream, int requestedFilter, int wrapMode, ImageParser$Format format) throws IOException {
        this.defaultWrapMode = 10497;
        this.textureTarget = 3553;
        try {
            int effectiveMinFilter = requestedFilter;
            int effectiveMagFilter = requestedFilter == 9987 ? 9729 : requestedFilter;
            boolean tracksTextureCapability = !GuiRenderPrimitives.d();
            boolean textureEnabled = !tracksTextureCapability || GL11.glIsEnabled(3553);
            int previousTextureId = GlStateManager.p();
            if (tracksTextureCapability && !textureEnabled) {
                GlStateManager.enableTexture2D();
            }
            if (requestedFilter == 9987) {
                int textureUnitCount = GL11.glGetInteger(33307);
                if (textureUnitCount < 3) {
                    effectiveMagFilter = 9729;
                    effectiveMinFilter = 9729;
                }
                if (MappedClasses.Y2 != null) {
                    ContextCapabilities capabilities = GLContext.getCapabilities();
                    if (!capabilities.GL_ARB_framebuffer_object) {
                        effectiveMagFilter = 9729;
                        effectiveMinFilter = 9729;
                    }
                }
            }
            ImageParser parser = new ImageParser(inputStream);
            this.width = parser.getWidth();
            this.height = parser.getHeight();
            int bytesPerPixel = 4;
            ByteBuffer pixels = BufferUtils.createByteBuffer(4 * this.width * this.height);
            parser.decode(pixels, this.width * 4, format);
            pixels.flip();
            this.textureId = GlStateManager.F();
            this.bind();
            GlPixelStoreState.reset();
            GL11.glPixelStorei(3317, 1);
            GL11.glTexParameteri(3553, 10241, effectiveMinFilter);
            GL11.glTexParameteri(3553, 10240, effectiveMagFilter);
            int compatibleWrapMode = GlImageTexture.normalizeTextureParameterValue(10242, wrapMode);
            GL11.glTexParameteri(3553, 10242, compatibleWrapMode);
            GL11.glTexParameteri(3553, 10243, compatibleWrapMode);
            if (effectiveMinFilter == 9987) {
                GL11.glTexParameteri(3553, 33084, 0);
                GL11.glTexParameteri(3553, 33085, 1);
            }
            GL11.glTexImage2D(3553, 0, 6408, this.width, this.height, 0, 6408, 5121, pixels);
            if (effectiveMinFilter == 9987) {
                GL30.glGenerateMipmap(3553);
            }
            GlStateManager.bindTexture(previousTextureId);
            if (tracksTextureCapability && !textureEnabled) {
                GlStateManager.disableTexture2D();
            }
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    public void noOp() {
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    public GlImageTexture(int textureId) {
        this.defaultWrapMode = 10497;
        this.textureTarget = 3553;
        this.textureId = textureId;
    }

    public void setParameter(int parameter, int value) {
        GL11.glTexParameteri(3553, parameter, GlImageTexture.normalizeTextureParameterValue(parameter, value));
    }

    static int normalizeTextureParameterValue(int parameter, int value) {
        if ((parameter == 10242 || parameter == 10243) && value == 10496) {
            return 33071;
        }
        return value;
    }

    public static GlImageTexture create(int width, int height, ByteBuffer pixels, int format, int filter, int wrapMode) {
        int previousTextureId = GlStateManager.p();
        GlImageTexture texture = new GlImageTexture();
        texture.height = height;
        texture.width = width;
        GL11.glBindTexture(3553, texture.textureId);
        GL11.glPixelStorei(3312, 0);
        GL11.glPixelStorei(3313, 0);
        GL11.glPixelStorei(3314, 0);
        GL11.glPixelStorei(32878, 0);
        GL11.glPixelStorei(3315, 0);
        GL11.glPixelStorei(3316, 0);
        GL11.glPixelStorei(32877, 0);
        GL11.glPixelStorei(3317, 4);
        boolean usesMipmaps = filter == 9987 || filter == 9985 || filter == 9986 || filter == 9984;
        boolean mipmapFilter = usesMipmaps;
        if (usesMipmaps) {
            texture.setParameter(10240, 9729);
            texture.setParameter(10241, filter);
            texture.setParameter(10242, wrapMode);
            texture.setParameter(10243, wrapMode);
            int maximumMipmapLevel = (int)Math.floor(Math.log(Math.max(width, height)) / Math.log(2.0));
            GL11.glTexParameteri(3553, 33084, 0);
            GL11.glTexParameteri(3553, 33085, maximumMipmapLevel);
            GL11.glTexParameterf(3553, 34049, -0.4f);
            GL11.glTexParameterf(3553, 33082, 0.0f);
            GL11.glTexParameterf(3553, 33083, maximumMipmapLevel);
            texture.upload(format, width, height, format, pixels);
            GL30.glGenerateMipmap(3553);
            GlStateManager.bindTexture(previousTextureId);
            return texture;
        }
        texture.setParameter(10240, filter);
        texture.setParameter(10241, filter);
        texture.setParameter(10242, wrapMode);
        texture.setParameter(10243, wrapMode);
        texture.upload(format, width, height, format, pixels);
        GlStateManager.bindTexture(previousTextureId);
        return texture;
    }

    public GlImageTexture(int textureId, int width, int height, float minU, float minV, float maxU, float maxV) {
        this.defaultWrapMode = 10497;
        this.textureTarget = 3553;
        this.textureId = textureId;
        this.width = width;
        this.height = height;
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
    }

    public void bind(int textureUnit) {
        if (!GuiRenderPrimitives.d()) {
            this.bind();
            return;
        }
        GL13.glActiveTexture(33984 + textureUnit);
        GlStateManager.bindTexture(this.textureId);
    }

    public void bind() {
        if (GuiRenderPrimitives.d()) {
            this.bind(0);
        } else {
            GlStateManager.bindTexture(this.textureId);
            lastBoundTextureId = this.textureId;
        }
    }

    public GlImageTexture(InputStream inputStream) throws IOException {
        this(inputStream, 9729, ImageParser$Format.RGBA);
    }

    public GlImageTexture(InputStream inputStream, int filter, ImageParser$Format format) throws IOException {
        this(inputStream, filter, 33071, format);
    }

    public void delete() {
        GL11.glDeleteTextures(this.textureId);
    }

    public void upload(int internalFormat, int width, int height, int pixelFormat, ByteBuffer pixels) {
        GL11.glTexImage2D(3553, 0, internalFormat, width, height, 0, pixelFormat, 5121, pixels);
    }

    public GlImageTexture() {
        this.defaultWrapMode = 10497;
        this.textureTarget = 3553;
        this.textureId = GL11.glGenTextures();
    }
}

