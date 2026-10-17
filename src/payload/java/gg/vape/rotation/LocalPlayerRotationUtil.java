package gg.vape.rotation;

import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.RenderMatrix4f;
import gg.vape.wrapper.impl.EntityRenderer;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.Matrix4f;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;

public class LocalPlayerRotationUtil {
   public static void updateProjectionMatrix(float partialTicks) {
      if (!Minecraft.D().getActiveRenderInfo().isNull()) {
         EntityRenderer renderer = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf();
         GameSettings settings = Minecraft.gameSettings();
         Matrix4f projectionMatrix = renderer.l(renderer.l(), partialTicks, true);
         if (!projectionMatrix.isNull()) {
            MatrixStack matrixStack = MatrixStack.A();
            renderer.B(matrixStack, partialTicks);
            if (settings.k()) {
               renderer.Z(matrixStack, partialTicks);
            }

            Matrix4f viewMatrix;
            if ((viewMatrix = matrixStack.F().getMatrix()).isNull()) {
               BufferedGuiRenderPrimitives.projectionMatrix = projectionMatrix.m$src$Lgg_vape_utils_render_RenderMatrix4f_$1hodrum();
               BufferedGuiRenderPrimitives.viewMatrix = new RenderMatrix4f().setIdentity();
            } else {
               projectionMatrix.a(viewMatrix);
               BufferedGuiRenderPrimitives.projectionMatrix = projectionMatrix.m$src$Lgg_vape_utils_render_RenderMatrix4f_$1hodrum();
               BufferedGuiRenderPrimitives.viewMatrix = new RenderMatrix4f().setIdentity();
            }
         }
      }
   }

   public static void resetProjectionMatrix() {
      float left = 0.0F;
      float right = Minecraft.p().I() / 2.0F;
      float bottom = Minecraft.p().R() / 2.0F;
      float top = 0.0F;
      float nearPlane = -21000.0F;
      float farPlane = 21000.0F;
      BufferedGuiRenderPrimitives.projectionMatrix = new RenderMatrix4f().setIdentity().setOrthographic(left, right, bottom, top, farPlane, nearPlane);
      BufferedGuiRenderPrimitives.viewMatrix = new RenderMatrix4f().setIdentity();
   }
}
