package gg.vape.module.render.hud;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventRender3D;
import gg.vape.mapping.MappedClasses;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.MathUtil;
import gg.vape.utils.render.BufferedRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.ColorValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockReaderBridge;
import gg.vape.wrapper.impl.BlockState;
import gg.vape.wrapper.impl.EntityFishHook;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class BlockOverlayHudModule extends HudModule {
   private final ColorValue overlayColor = ColorValue.create(this, "Overlay Color", new Color(255, 0, 0, 95));
   private final ColorValue outlineColor = ColorValue.create(this, "Outline Color", new Color(255, 0, 0, 200));

   private void drawOutline(AxisAlignedBB axisAlignedBB, Color color) {
      if (GuiRenderPrimitives.d()) {
         BufferedRenderPrimitives.drawLine3D(
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMinZ(),
            2.0F,
            color
         );
         BufferedRenderPrimitives.drawLine3D(
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMinZ(),
            2.0F,
            color
         );
         BufferedRenderPrimitives.drawLine3D(
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMaxZ(),
            2.0F,
            color
         );
         BufferedRenderPrimitives.drawLine3D(
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMaxZ(),
            2.0F,
            color
         );
         BufferedRenderPrimitives.drawLine3D(
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMaxZ(),
            2.0F,
            color
         );
         BufferedRenderPrimitives.drawLine3D(
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMaxZ(),
            2.0F,
            color
         );
         BufferedRenderPrimitives.drawLine3D(
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMaxZ(),
            2.0F,
            color
         );
         BufferedRenderPrimitives.drawLine3D(
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMaxZ(),
            2.0F,
            color
         );
         BufferedRenderPrimitives.drawLine3D(
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMinZ(),
            2.0F,
            color
         );
         BufferedRenderPrimitives.drawLine3D(
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMinZ(),
            2.0F,
            color
         );
         BufferedRenderPrimitives.drawLine3D(
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMaxZ(),
            2.0F,
            color
         );
         BufferedRenderPrimitives.drawLine3D(
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMaxZ(),
            2.0F,
            color
         );
      } else {
         OpenGlBackendHolder.backend.setColor(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
         GL11.glBegin(1);
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ());
         GL11.glEnd();
      }
   }

   private void drawFilledBox(AxisAlignedBB axisAlignedBB, Color color) {
      if (GuiRenderPrimitives.d()) {
         BufferedRenderPrimitives.fillQuad(
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMinZ(),
            color
         );
         BufferedRenderPrimitives.fillQuad(
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMaxZ(),
            color
         );
         BufferedRenderPrimitives.fillQuad(
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMinZ(),
            color
         );
         BufferedRenderPrimitives.fillQuad(
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMaxZ(),
            color
         );
         BufferedRenderPrimitives.fillQuad(
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMinX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMaxZ(),
            color
         );
         BufferedRenderPrimitives.fillQuad(
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMinZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMinY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMaxZ(),
            axisAlignedBB.getMaxX(),
            axisAlignedBB.getMaxY(),
            axisAlignedBB.getMinZ(),
            color
         );
      } else {
         OpenGlBackendHolder.backend.setColor(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
         GL11.glBegin(7);
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ());
         GL11.glVertex3d(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ());
         GL11.glEnd();
      }
   }

   private AxisAlignedBB resolveTargetBounds(RayTraceResult target, WorldClient world) {
      Block block = target.Z$src$Lgg_vape_wrapper_impl_Block_$6x2c9a();
      if (ForgeVersion.MC_1_7_10.B()) {
         return block.M(world, target.g(), target.T(), target.a$src$I$8nuo9d());
      } else {
         BlockPos position = BlockPos.D(MathUtil.floor((float)target.g()), MathUtil.floor((float)target.T()), MathUtil.floor((float)target.a$src$I$8nuo9d()));
         BlockState state = world.getBlockState(position);
         if (!ForgeVersion.MC_1_16_5.d()) {
            return ForgeVersion.MC_1_12_2.L() ? block.Y(state, world, position) : block.Q(world, position);
         } else if (!state.isInstance(MappedClasses.Fj)) {
            return block.Q(world, position);
         } else {
            BlockReaderBridge blockReader = new BlockReaderBridge(state);
            EntityFishHook shape = blockReader.getShape(
               Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().l().x$src$Lgg_vape_wrapper_impl_BlockReader_$120g8sh(), position
            );
            return shape.o() ? null : shape.n();
         }
      }
   }

   @EventHandler
   public void onRender3D(EventRender3D event) {
      RayTraceResult target = RotationManager.INSTANCE.getNormalReachRayTrace();
      if (!target.isNull()) {
         if (target.getTypeOfHit().equals(RayTraceResult_type.block())) {
            Block block = target.Z$src$Lgg_vape_wrapper_impl_Block_$6x2c9a();
            if (block != null && !block.isNull()) {
               WorldClient world = Minecraft.theWorld();
               AxisAlignedBB targetBounds = this.resolveTargetBounds(target, world);
               if (targetBounds != null) {
                  RenderUtil.d();
                  OpenGlBackendHolder.backend.enableCapability(3042);
                  OpenGlBackendHolder.backend.enableCapability(2929);
                  GL11.glBlendFunc(770, 771);
                  OpenGlBackendHolder.backend.enableCapability(2848);
                  OpenGlBackendHolder.backend.setLineWidth(2.0F);
                  OpenGlBackendHolder.backend.disableCapability(3553);
                  GlStateManager.depthMask(false);
                  double cameraX;
                  double cameraY;
                  double cameraZ;
                  if (ForgeVersion.MC_1_20_6.d()) {
                     cameraX = RenderManager.getInterpolatedRenderPosX();
                     cameraY = RenderManager.getInterpolatedRenderPosY();
                     cameraZ = RenderManager.getInterpolatedRenderPosZ();
                  } else {
                     RenderManager renderManager = Minecraft.D();
                     cameraX = renderManager.getRenderPosX();
                     cameraY = renderManager.getRenderPosY();
                     cameraZ = renderManager.getRenderPosZ();
                  }

                  if (ForgeVersion.MC_1_16_5.d()) {
                     OpenGlBackendHolder.backend.translate((float)target.g(), (float)target.T(), (float)target.a$src$I$8nuo9d());
                  }

                  AxisAlignedBB renderBounds = targetBounds.expand(0.002F, 0.002F, 0.002F).A(-cameraX, -cameraY, -cameraZ);
                  RenderUtils.g();
                  this.drawFilledBox(renderBounds, this.overlayColor.getMutableColor());
                  OpenGlBackendHolder.backend.setLineWidth(2.0F);
                  this.drawOutline(renderBounds, this.outlineColor.getMutableColor());
                  GlStateManager.depthMask(true);
                  RenderUtils.f();
                  OpenGlBackendHolder.backend.enableCapability(3553);
                  if (ForgeVersion.MC_1_21_6.d()) {
                     OpenGlBackendHolder.backend.disableCapability(3042);
                  }

                  OpenGlBackendHolder.backend.disableCapability(2848);
                  RenderUtil.Y();
               }
            }
         }
      }
   }

   public BlockOverlayHudModule() {
      super("Block Overlay", HudModuleGroup.GAME, "block_overlay");
      this.setSuffix("Highlights the block you're hovering by the specified color");
      this.addValue(this.overlayColor, this.outlineColor);
   }
}
