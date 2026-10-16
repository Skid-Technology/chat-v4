package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.entity.RenderEntityContext;
import gg.vape.module.render.entity.RenderEntityContextCache;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.MathUtil;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class OnlineRadarPreviewComponent extends GuiComponent {
   private final OnlineRadarSettingsFrame settingsFrame;
   static final boolean assertionsDisabled = !OnlineRadarPreviewComponent.class.desiredAssertionStatus();

   public OnlineRadarPreviewComponent(OnlineRadarSettingsFrame settingsFrame) {
      this.settingsFrame = settingsFrame;
   }

   private static double getPreviewDistance(OnlineRadarPreviewState previewState) {
      return ((RenderEntityContext)previewState.getValue()).getDistance();
   }

   private void renderRadar(boolean accountForHeader) {
      SmoothFontRenderer fontRenderer = Vape.INSTANCE.getFontManager().Y(1.0);
      OnlineRadarSettings settings = this.settingsFrame.getSettings();
      double radarX = this.G$src$D$1b2f02a();
      boolean headerVisible = this.settingsFrame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() != null
         && this.settingsFrame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().V$src$Z$1xhop3l();
      double radarY = (float)(
         (float)this.n() - (accountForHeader && headerVisible ? this.settingsFrame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() : -2.0)
      );
      EntityPlayerSP localPlayer = Minecraft.thePlayer();
      WorldClient world = Minecraft.theWorld();
      if (!world.isNull() && !localPlayer.isNull()) {
         List players = world.X();
         if (settings.radarMode.getValue() == settings.twoDimensionalRadarMode) {
            boolean blendEnabled = GL11.glIsEnabled(3042);
            double radarSize = settings.radarSize.getValue();
            double radarRadius = radarSize / 2.0;
            double radarScale = settings.radarScale.getValue();
            double dotSize = settings.dotSize.getValue();
            boolean squareRadar = settings.radarStyle.getValue() == settings.squareRadarStyle;
            if (settings.showBackground.getEffectiveValue()) {
               if (squareRadar) {
                  GuiRenderPrimitives.e(
                     radarX, radarY, radarSize, radarSize, this.settingsFrame.applyDefaultEditorAlpha(new Color(-1877995504, true)), false, 3.0F, 1.0F
                  );
               } else {
                  GuiRenderPrimitives.V(radarX, radarY, radarSize, 1.0, this.settingsFrame.applyDefaultEditorAlpha(new Color(0, 0, 0, 128)));
               }
            }

            if (settings.showCross.getEffectiveValue()) {
               Color crossColor = this.settingsFrame.applyDefaultEditorAlpha(new Color(-10132123, true));
               float verticalLeft = (float)((radarX -= 0.5) + radarSize / 2.0 - 0.5);
               float verticalTop = (float)((radarY -= 0.5) + 0.5);
               float verticalRight = (float)(radarX + radarSize / 2.0 + 0.5);
               float verticalBottom = (float)(radarY + radarSize - 0.5);
               GuiRenderPrimitives.y(verticalLeft, verticalTop, verticalRight - verticalLeft, verticalBottom - verticalTop, crossColor);
               float horizontalLeft = (float)(radarX + 0.5);
               float horizontalTop = (float)(radarY + radarSize / 2.0 - 0.5);
               float horizontalRight = (float)(radarX + radarSize - 0.5);
               float horizontalBottom = (float)(radarY + radarSize / 2.0 + 0.5);
               GuiRenderPrimitives.y(horizontalLeft, horizontalTop, horizontalRight - horizontalLeft, horizontalBottom - horizontalTop, crossColor);
            }

            if (settings.showBackground.getEffectiveValue() && squareRadar) {
               if (Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled()) {
                  GuiRenderPrimitives.e(
                     radarX + 1.0,
                     radarY + 0.5,
                     2.0,
                     1.5,
                     this.settingsFrame.applyDefaultEditorAlpha(Vape.INSTANCE.getClientSettings().guiColor.getMutableColor()),
                     false,
                     1.0F,
                     1.0F
                  );
                  Color guiColor = Vape.INSTANCE.getClientSettings().guiColor.getMutableColor();
                  float[] hsb = new float[3];
                  Color.RGBtoHSB(guiColor.getRed(), guiColor.getGreen(), guiColor.getBlue(), hsb);
                  float hue = hsb[0];
                  Color rainbowColor = guiColor;

                  for (float offset = 2.0F; offset < radarSize - 2.0; offset++) {
                     rainbowColor = ColorUtil.createReadableHsbColor(hue, 0.9F, 1.0F);
                     hue = (float)(hue + 0.005);
                     GuiRenderPrimitives.C(radarX + offset, radarY + 0.1, 1.0, 1.75, this.settingsFrame.applyDefaultEditorAlpha(rainbowColor));
                  }

                  GuiRenderPrimitives.e(
                     radarX + radarSize - 3.0, radarY + 0.5, 2.0, 1.5, this.settingsFrame.applyDefaultEditorAlpha(rainbowColor), false, 1.0F, 1.0F
                  );
               } else {
                  GuiRenderPrimitives.e(
                     radarX + 1.0,
                     radarY + 0.5,
                     radarSize - 2.0,
                     1.5,
                     this.settingsFrame.applyDefaultEditorAlpha(Vape.INSTANCE.getClientSettings().guiColor.getMutableColor()),
                     false,
                     1.0F,
                     1.0F
                  );
               }

               MutableColor borderColor = new MutableColor(J.r);
               borderColor.withAlpha(100);
               GuiRenderPrimitives.P(radarX, radarY, radarSize, radarSize, this.settingsFrame.applyDefaultEditorAlpha(borderColor), 3.0F, 1.0F, 1.0F);
            }

            for (Object playerObject : players) {
               if (playerObject != localPlayer.getObject()) {
                  EntityPlayer player = new EntityPlayer(playerObject);
                  if (!Vape.INSTANCE.getClientSettings().isBot(player)) {
                     RenderEntityContext renderContext = RenderEntityContextCache.getOrCreate(player, localPlayer);
                     double localX = localPlayer.M() + (localPlayer.z() - localPlayer.M()) * Minecraft.getTimer().renderPartialTicks();
                     double localZ = localPlayer.m$src$D$fwnne5()
                        + (localPlayer.h() - localPlayer.m$src$D$fwnne5()) * Minecraft.getTimer().renderPartialTicks();
                     double playerX = player.M() + (player.z() - player.M()) * Minecraft.getTimer().renderPartialTicks();
                     double playerZ = player.m$src$D$fwnne5() + (player.h() - player.m$src$D$fwnne5()) * Minecraft.getTimer().renderPartialTicks();
                     double relativeX = playerX - localX;
                     double relativeZ = playerZ - localZ;
                     double radarCenterX = radarX + radarRadius;
                     double radarCenterY = radarY + radarRadius;
                     float yawCosine = (float)Math.cos(localPlayer.J() * (Math.PI / 180.0));
                     float yawSine = (float)Math.sin(localPlayer.J() * (Math.PI / 180.0));
                     double rotatedX = -(relativeX * yawCosine + relativeZ * yawSine) * radarScale;
                     double rotatedY = -(relativeZ * yawCosine - relativeX * yawSine) * radarScale;
                     double dotY;
                     double dotX;
                     if (squareRadar) {
                        dotX = radarCenterX
                           + (
                              !settings.clampRadar.getEffectiveValue()
                                 ? rotatedX
                                 : MathUtil.clamp(rotatedX, -radarRadius + dotSize / 2.0, radarRadius - dotSize / 2.0)
                           );
                        dotY = radarCenterY
                           + (
                              !settings.clampRadar.getEffectiveValue()
                                 ? rotatedY
                                 : MathUtil.clamp(rotatedY, -radarRadius + dotSize / 2.0, radarRadius - dotSize / 2.0)
                           );
                        dotY = Math.max(dotY, radarY + 4.0);
                     } else {
                        if (settings.clampRadar.getEffectiveValue() && Math.sqrt(rotatedX * rotatedX + rotatedY * rotatedY) > radarRadius) {
                           float angle = (float)Math.atan2(rotatedY, rotatedX);
                           rotatedX = (float)((radarRadius - 0.5) * Math.cos(angle));
                           rotatedY = (float)((radarRadius - 0.5) * Math.sin(angle));
                        }

                        dotX = radarCenterX + rotatedX;
                        dotY = radarCenterY + rotatedY;
                     }

                     Color color = Color.WHITE;
                     if (settings.colorMode.getValue() == settings.customColorMode) {
                        color = settings.customColor.getMutableColor();
                     } else if (settings.colorMode.getValue() == settings.teamColorMode) {
                        MutableColor mutableColor = renderContext.getRenderColor(true);
                        if (mutableColor != null) {
                           color = mutableColor;
                        }
                     } else if (settings.colorMode.getValue() == settings.relationshipColorMode) {
                        color = renderContext.isAttackable() ? settings.friendlyColor.getMutableColor() : settings.enemyColor.getMutableColor();
                     }

                     if (renderContext.isFriend() && Vape.INSTANCE.getFriendManager().recolorVisuals.getEffectiveValue()) {
                        color = Vape.INSTANCE.getFriendManager().friendColor.getMutableColor();
                     }

                     if (renderContext.isEnemy() && Vape.INSTANCE.getEnemyManager().useColor.getEffectiveValue()) {
                        color = Vape.INSTANCE.getEnemyManager().enemyColor.getMutableColor();
                     }

                     if (settings.dotStyle.getValue() == settings.squareDotStyle) {
                        RenderUtils.M(
                           dotX - dotSize / 2.0,
                           dotY - dotSize / 2.0,
                           dotX + dotSize / 2.0,
                           dotY + dotSize / 2.0,
                           0.5,
                           this.settingsFrame.applyDefaultEditorAlpha(color),
                           this.settingsFrame.applyDefaultEditorAlpha(new Color(1342177280, true))
                        );
                     } else {
                        double dotRadius = dotSize / 2.0;
                        GuiRenderPrimitives.V(dotX - dotRadius, dotY - dotRadius, dotSize, 0.5, this.settingsFrame.applyDefaultEditorAlpha(color));
                        GuiRenderPrimitives.m(
                           (float)(dotX - dotRadius),
                           (float)(dotY - dotRadius),
                           (float)dotSize,
                           1.0F,
                           0.75F,
                           this.settingsFrame.applyDefaultEditorAlpha(new Color(-16777216, true))
                        );
                     }
                  }
               }
            }

            if (blendEnabled) {
               GlStateManager.enableBlend();
            }
         } else {
            List<OnlineRadarPreviewState> previewStates = new ArrayList<>();
            ArrayList renderedPlayerObjects = new ArrayList();
            int maxDistance = settings.maxDistance.getValue().intValue();

            for (Object playerObjectx : players) {
               if (MappedClasses.Yl.isAssignableFrom(playerObjectx.getClass())
                  && playerObjectx != localPlayer.getObject()
                  && !renderedPlayerObjects.contains(playerObjectx)) {
                  EntityPlayer player = new EntityPlayer(playerObjectx);
                  if (!Vape.INSTANCE.getClientSettings().isBot(player) && (maxDistance == 0 || localPlayer.getDistanceToEntity(player) <= maxDistance)) {
                     previewStates.add(OnlineRadarPreviewState.create(player, RenderEntityContextCache.getOrCreate(player, localPlayer)));
                     renderedPlayerObjects.add(playerObjectx);
                  }
               }
            }

            if (previewStates.isEmpty()) {
               if (!ClientSettings.INSTANCE.inputEnabled && HudModuleConfigFrameBase.isHudEditorContext()) {
                  String[][] placeholderPlayers = new String[][]{{"Player1", "§a72m"}, {"Player2", "§e45m"}, {"Player3", "§c18m"}};
                  int rowOffset = 0;

                  for (String[] placeholderPlayer : placeholderPlayers) {
                     if (settings.showBackground.getEffectiveValue()) {
                        GuiRenderPrimitives.C(
                           radarX, radarY + rowOffset, this.A(), 10.0, this.settingsFrame.applyDefaultEditorAlpha(new Color(20, 20, 20, 180))
                        );
                        GuiRenderPrimitives.C(
                           radarX, radarY + rowOffset + 9.5, this.A(), 0.5, this.settingsFrame.applyDefaultEditorAlpha(new Color(25, 25, 25, 65))
                        );
                     }

                     fontRenderer.g(
                        placeholderPlayer[0], radarX + 1.0, radarY + 2.0 + rowOffset, this.settingsFrame.applyDefaultEditorAlpha(Color.WHITE).getRGB()
                     );
                     fontRenderer.g(
                        placeholderPlayer[1],
                        radarX + this.A() - fontRenderer.N(placeholderPlayer[1]) - 1.0,
                        radarY + 2.0 + rowOffset,
                        this.settingsFrame.applyDefaultEditorAlpha(Color.WHITE).getRGB()
                     );
                     rowOffset += 10;
                  }
               }

               return;
            }

            previewStates.sort(Comparator.comparingDouble(OnlineRadarPreviewComponent::getPreviewDistance));
            int maxShown = settings.maxShown.getValue().intValue();
            int hiddenPlayerCount = 0;
            if (settings.maxShown.getValue() != 0.0 && previewStates.size() > maxShown) {
               hiddenPlayerCount = previewStates.size() - maxShown;
               previewStates = previewStates.subList(0, maxShown);
            }

            int rowOffset = 0;

            for (OnlineRadarPreviewState previewState : previewStates) {
               EntityPlayer player = (EntityPlayer)previewState.getKey();
               RenderEntityContext renderContextx = (RenderEntityContext)previewState.getValue();
               if (!assertionsDisabled && renderContextx == null) {
                  throw new AssertionError();
               }

               int distance = (int)renderContextx.getDistance();
               String distanceColorCode = distance > 100 ? "a" : (distance > 50 ? "e" : "c");
               String distanceText = String.format("§%s%dm", distanceColorCode, distance);
               if (settings.showBackground.getEffectiveValue()) {
                  GuiRenderPrimitives.C(radarX, radarY + rowOffset, this.A(), 10.0, this.settingsFrame.applyDefaultEditorAlpha(new Color(20, 20, 20, 180)));
                  GuiRenderPrimitives.C(radarX, radarY + rowOffset + 9.5, this.A(), 0.5, this.settingsFrame.applyDefaultEditorAlpha(new Color(25, 25, 25, 65)));
               }

               String playerName = renderContextx.getName();
               Color nameColor = Vape.INSTANCE.getFriendManager().isFriend(playerName)
                  ? new Color(Vape.INSTANCE.getFriendManager().friendColor.toRgb())
                  : (Vape.INSTANCE.getEnemyManager().isEnemy(playerName) ? new Color(Vape.INSTANCE.getEnemyManager().enemyColor.toRgb()) : Color.WHITE);
               fontRenderer.g(playerName, radarX + 1.0, radarY + 2.0 + rowOffset, this.settingsFrame.applyDefaultEditorAlpha(nameColor).getRGB());
               fontRenderer.g(
                  distanceText,
                  radarX + this.A() - fontRenderer.N(distanceText) - 1.0,
                  radarY + 2.0 + rowOffset,
                  this.settingsFrame.applyDefaultEditorAlpha(Color.WHITE).getRGB()
               );
               rowOffset += 10;
            }

            if (hiddenPlayerCount > 0) {
               if (settings.showBackground.getEffectiveValue()) {
                  GuiRenderPrimitives.C(radarX, radarY + rowOffset, this.A(), 10.0, this.settingsFrame.applyDefaultEditorAlpha(new Color(20, 20, 20, 180)));
                  GuiRenderPrimitives.C(radarX, radarY + rowOffset + 9.5, this.A(), 0.5, this.settingsFrame.applyDefaultEditorAlpha(new Color(25, 25, 25, 65)));
               }

               fontRenderer.g(
                  hiddenPlayerCount + " more...", radarX + 1.0, radarY + 2.0 + rowOffset, this.settingsFrame.applyDefaultEditorAlpha(Color.WHITE).getRGB()
               );
            }
         }
      }
   }

   @Override
   public double C() {
      return 0.0;
   }

   @Override
   public void I() {
      this.renderRadar(true);
   }

   @Override
   public double x() {
      return 0.0;
   }

   @Override
   public void u() {
   }

   @Override
   public void F() {
   }

   @Override
   public void g(GuiMouseEvent guiMouseEvent) {
   }

   @Override
   public void H() {
      this.renderRadar(false);
   }
}
