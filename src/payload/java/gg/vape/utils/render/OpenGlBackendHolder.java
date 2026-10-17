package gg.vape.utils.render;

public class OpenGlBackendHolder {
   public static OpenGlBackend backend = (OpenGlBackend)(GuiRenderPrimitives.d() ? new BufferedOpenGlBackend() : new LegacyOpenGlBackend());
}
