/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.render;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventRender3D;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.render.proj.ArrowProjectile;
import gg.vape.module.render.proj.IProjectile;
import gg.vape.module.render.proj.PotionProjectile;
import gg.vape.module.render.proj.Projectile;
import gg.vape.module.render.trajectories.TrajectoriesProjectileRenderState;
import gg.vape.module.render.trajectories.WideArrowProjectile;
import gg.vape.utils.MathUtil;
import gg.vape.utils.render.BufferedRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.TrajectoriesItemBridge;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class Trajectories
extends Mod {
    private final Projectile snowballProjectile;
    private final ColorValue targetColor;
    private final Projectile eggProjectile;
    private final ArrayList<TrajectoriesProjectileRenderState> renderStates;
    private final ArrowProjectile arrowProjectile;
    private final ColorValue trajectoryColor;
    private boolean entityHitLastFrame;
    private final PotionProjectile potionProjectile;
    private final ColorValue aimingColor = ColorValue.create(this, "Aiming Color", new Color(255, 255, 255));
    private Color currentColor;
    private final BooleanValue ghostBowCharge;
    private final Projectile pearlProjectile;

    private void addLineSegment(double startX, double startY, double startZ, double endX, double endY, double endZ, float lineWidth) {
        this.renderStates.add(new TrajectoriesProjectileRenderState(startX, startY, startZ, endX, endY, endZ, lineWidth));
    }

    public void drawImpactMarker() {
        if (GuiRenderPrimitives.d()) {
            BufferedRenderPrimitives.drawLine3D(-0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, this.currentColor);
            BufferedRenderPrimitives.drawLine3D(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.25f, 1.0f, this.currentColor);
            BufferedRenderPrimitives.drawLine3D(0.0f, 0.0f, -0.25f, 0.0f, 0.0f, 0.0f, 1.0f, this.currentColor);
            BufferedRenderPrimitives.drawLine3D(0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, this.currentColor);
            BufferedRenderPrimitives.drawLine3D(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.25f, 1.0f, this.currentColor);
            BufferedRenderPrimitives.drawLine3D(0.0f, 0.0f, 0.25f, 0.0f, 0.0f, 0.0f, 1.0f, this.currentColor);
        } else {
            GL11.glBegin(1);
            GL11.glVertex3d(-0.25, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.0, -0.25);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.25, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.0, 0.25);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glEnd();
        }
    }

    public Trajectories() {
        super("Trajectories", -16535661, Category.RENDER, "Shows a path of where your projectile will land\nTarget Color will only be used on the cross if there is an entity intersecting it");
        this.arrowProjectile = new WideArrowProjectile();
        this.ghostBowCharge = BooleanValue.create(this, "Ghost Bow Charge", false, "Draws a ghost trajectory of a fully charged\nshot when not pulling back bow");
        this.eggProjectile = new Projectile(Collections.singleton(MappedClasses.l2), new Color(255, 238, 154));
        this.pearlProjectile = new Projectile(Collections.singleton(MappedClasses.Zg), new Color(173, 12, 255));
        this.snowballProjectile = new Projectile(Collections.singleton(MappedClasses.YZ), new Color(255, 255, 255));
        this.potionProjectile = new PotionProjectile();
        this.targetColor = ColorValue.create(this, "Target Color", new Color(0, 0, 255, 255));
        this.trajectoryColor = ColorValue.create(this, "Trajectory Color", new Color(255, 0, 0, 255));
        this.renderStates = new ArrayList();
        this.addValue(this.aimingColor, this.trajectoryColor, this.targetColor, this.ghostBowCharge);
        this.currentColor = this.trajectoryColor.getMutableColor();
    }

    @Nullable
    private IProjectile getProjectileForItem(Item item) {
        if (item.isInstance(MappedClasses.Vl)) {
            return this.arrowProjectile;
        }
        if (item.isInstance(MappedClasses.Vb)) {
            return this.snowballProjectile;
        }
        if (item.isInstance(MappedClasses.ZH)) {
            return this.pearlProjectile;
        }
        if (item.isInstance(MappedClasses.YH)) {
            return this.eggProjectile;
        }
        if (item.isInstance(MappedClasses.o)) {
            return this.potionProjectile;
        }
        if (item.isInstance(MappedClasses.YA)) {
            return this.arrowProjectile;
        }
        return null;
    }

    private static boolean isCollision(RayTraceResult rayTraceResult) {
        return rayTraceResult != null && rayTraceResult.isNotNull() && !RayTraceResult_type.miss().equals(rayTraceResult.getTypeOfHit());
    }

    private void flushRenderStates(@Nullable Color color) {
        if (GuiRenderPrimitives.d()) {
            for (TrajectoriesProjectileRenderState trajectoriesProjectileRenderState : this.renderStates) {
                BufferedRenderPrimitives.drawLine3D(trajectoriesProjectileRenderState.startX, trajectoriesProjectileRenderState.startY, trajectoriesProjectileRenderState.startZ, trajectoriesProjectileRenderState.endX, trajectoriesProjectileRenderState.endY, trajectoriesProjectileRenderState.endZ, trajectoriesProjectileRenderState.lineWidth, color == null ? trajectoriesProjectileRenderState.color : color);
            }
        } else {
            for (TrajectoriesProjectileRenderState trajectoriesProjectileRenderState : this.renderStates) {
                GL11.glVertex3d(trajectoriesProjectileRenderState.startX, trajectoriesProjectileRenderState.startY, trajectoriesProjectileRenderState.startZ);
            }
        }
        this.renderStates.clear();
    }

    @EventHandler
    public void onRender3D(EventRender3D event) {
        double z;
        double y;
        double x;
        double verticalOffset;
        EntityPlayerSP player = event.getThePlayer();
        if (player.isNull()) {
            return;
        }
        WorldClient world = event.getWorld();
        if (world.isNull()) {
            return;
        }
        ItemStack heldStack = this.findHeldProjectile(player);
        if (heldStack == null || heldStack.isNull()) {
            return;
        }
        Item item = heldStack.getItem();
        if (item.isNull()) {
            return;
        }
        IProjectile projectile = this.getProjectileForItem(item);
        if (projectile == null) {
            return;
        }
        boolean bowProjectile = projectile == this.arrowProjectile;
        boolean crossbow = item.isInstance(MappedClasses.YA);
        boolean potionProjectile = projectile == this.potionProjectile;
        float pitchRadians = player.V() / 180.0f * (float)Math.PI;
        float yawRadians = player.J() / 180.0f * (float)Math.PI;
        double initialSpeed = bowProjectile ? 1.0 : 0.4;
        double d = verticalOffset = ForgeVersion.MC_1_16_5.d() ? -1.6 : 0.0;
        if (ForgeVersion.MC_1_21_4.d()) {
            x = RenderManager.getInterpolatedRenderPosX();
            y = RenderManager.getInterpolatedRenderPosY() + (double)player.X() + verticalOffset;
            z = RenderManager.getInterpolatedRenderPosZ();
        } else {
            x = RenderManager.getInterpolatedRenderPosX() - (double)(MathUtil.cos(yawRadians) * 0.16f);
            y = RenderManager.getInterpolatedRenderPosY() + (double)player.X() - (double)0.1f + verticalOffset;
            z = RenderManager.getInterpolatedRenderPosZ() - (double)(MathUtil.sin(yawRadians) * 0.16f);
        }
        double motionX = (double)(-MathUtil.sin(yawRadians) * MathUtil.cos(pitchRadians)) * initialSpeed;
        double motionY = (double)(-MathUtil.sin(pitchRadians)) * initialSpeed;
        double motionZ = (double)(MathUtil.cos(yawRadians) * MathUtil.cos(pitchRadians)) * initialSpeed;
        int useTicks = 40;
        if (crossbow && !this.ghostBowCharge.getEffectiveValue().booleanValue() && !TrajectoriesItemBridge.isCharged(heldStack)) {
            return;
        }
        if (!(this.ghostBowCharge.getEffectiveValue().booleanValue() || player.j$src$I$1in0s92() > 0 || !bowProjectile || crossbow && TrajectoriesItemBridge.isCharged(heldStack))) {
            return;
        }
        if (player.j$src$I$1in0s92() > 0 && bowProjectile || crossbow && TrajectoriesItemBridge.isCharged(heldStack)) {
            useTicks = player.j$src$I$1in0s92();
        }
        int chargeTicks = 72000 - useTicks;
        float chargePower = (float)chargeTicks / 20.0f;
        if ((chargePower = (chargePower * chargePower + chargePower * 2.0f) / 3.0f) < 0.1f) {
            return;
        }
        if (chargePower > 1.0f) {
            chargePower = 1.0f;
        }
        RenderUtils.g();
        RenderUtil.d();
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().B(0.0);
        boolean depthWasEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(2929);
        boolean textureWasEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3553);
        boolean blendWasEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
        if (depthWasEnabled) {
            OpenGlBackendHolder.backend.disableCapability(2929);
        }
        if (textureWasEnabled) {
            OpenGlBackendHolder.backend.disableCapability(3553);
        }
        OpenGlBackendHolder.backend.enableCapability(2848);
        GL11.glBlendFunc(770, 771);
        if (!blendWasEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3042);
        }
        float motionLength = MathUtil.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= (double)motionLength;
        motionY /= (double)motionLength;
        motionZ /= (double)motionLength;
        double velocityScale = (double)(bowProjectile ? chargePower * 2.0f : 1.0f) * 1.5;
        motionX *= velocityScale;
        motionY *= velocityScale;
        motionZ *= velocityScale;
        OpenGlBackendHolder.backend.setLineWidth(1.5f);
        OpenGlBackendHolder.backend.beginPrimitive(3);
        boolean collided = false;
        RayTraceResult hit = new RayTraceResult(null);
        float collisionRadius = projectile.getCollisionRadius();
        float collisionHeight = projectile.getCollisionHeight();
        float halfRadius = collisionRadius / 2.0f;
        if (this.entityHitLastFrame) {
            RenderUtils.w(this.trajectoryColor.getMutableColor());
            this.currentColor = this.trajectoryColor.getMutableColor();
        } else {
            RenderUtils.w(this.aimingColor.getMutableColor());
            this.currentColor = this.aimingColor.getMutableColor();
        }
        this.entityHitLastFrame = false;
        Predicate<RayTraceResult> isCollision = Trajectories::isCollision;
        double previousRenderX = 0.0;
        double previousRenderY = 0.0;
        double previousRenderZ = 0.0;
        boolean hasPreviousVertex = false;
        while (!collided) {
            Vec3 start = Vec3.create(x, y, z);
            Vec3 end = Vec3.create(x + motionX, y + motionY, z + motionZ);
            hit = world.K(start, end, false, bowProjectile, false, player);
            start = Vec3.create(x, y, z);
            end = Vec3.create(x + motionX, y + motionY, z + motionZ);
            if (isCollision.test(hit)) {
                collided = true;
                Vec3 hitPosition = hit.getHitVec();
                end = Vec3.create(hitPosition.getX(), hitPosition.getY(), hitPosition.getZ());
            }
            AxisAlignedBB projectileBounds = AxisAlignedBB.create(x - (double)halfRadius, y, z - (double)halfRadius, x + (double)halfRadius, y + (double)collisionHeight, z + (double)halfRadius);
            double nearestEntityDistance = 0.0;
            List candidates = world.F(player, projectileBounds.addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
            for (Object candidate : candidates) {
                AxisAlignedBB entityBounds;
                RayTraceResult entityHit;
                Entity entity = new Entity(candidate);
                boolean bl = ForgeVersion.MC_1_16_5.d() ? entity.isInstance(MappedClasses.zm) && !entity.isInstance(MappedClasses.uz) && !entity.equals(player) : entity.n$src$Z$fx7gig() && !entity.equals(player);
                boolean canCollide = bl;
                if (!canCollide || !isCollision.test(entityHit = (entityBounds = entity.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().expand(0.3, 0.3, 0.3)).calculateIntercept(start, end))) continue;
                double entityDistance = start.A(entityHit.getHitVec());
                if (nearestEntityDistance != 0.0 && entityDistance >= nearestEntityDistance) continue;
                entityHit.setEntity(entity);
                nearestEntityDistance = entityDistance;
                hit = entityHit;
                collided = true;
                this.entityHitLastFrame = true;
            }
            x += motionX;
            y += motionY;
            z += motionZ;
            float drag = 0.99f;
            float gravity = bowProjectile || potionProjectile ? 0.05f : 0.03f;
            motionX *= (double)drag;
            motionY *= (double)drag;
            motionZ *= (double)drag;
            motionY -= (double)gravity;
            if (GuiRenderPrimitives.d()) {
                double renderX = x - RenderManager.getInterpolatedRenderPosX();
                double renderY = y - RenderManager.getInterpolatedRenderPosY();
                double renderZ = z - RenderManager.getInterpolatedRenderPosZ();
                if (!hasPreviousVertex) {
                    previousRenderX = renderX;
                    previousRenderY = renderY;
                    previousRenderZ = renderZ;
                    hasPreviousVertex = true;
                    continue;
                }
                this.addLineSegment(previousRenderX, previousRenderY, previousRenderZ, renderX, renderY, renderZ, 1.5f);
                previousRenderX = renderX;
                previousRenderY = renderY;
                previousRenderZ = renderZ;
            } else {
                this.addVertex(x - RenderManager.getInterpolatedRenderPosX(), y - RenderManager.getInterpolatedRenderPosY(), z - RenderManager.getInterpolatedRenderPosZ());
            }
            if (!collided && y >= -128.0 && !isCollision.test(hit)) continue;
        }
        this.flushRenderStates(this.currentColor);
        OpenGlBackendHolder.backend.endPrimitive();
        OpenGlBackendHolder.backend.translate(x - RenderManager.getInterpolatedRenderPosX(), y - RenderManager.getInterpolatedRenderPosY(), z - RenderManager.getInterpolatedRenderPosZ());
        if (isCollision.test(hit)) {
            RayTraceResult_type hitType = hit.getTypeOfHit();
            Entity hitEntity = hit.getEntity();
            if (ForgeVersion.MC_1_16_5.v() || RayTraceResult_type.block().equals(hitType)) {
                EnumFacing hitSide = hit.getSideHit();
                if (hitSide.isNotNull()) {
                    switch (hitSide.Y()) {
                        case 2: 
                        case 3: {
                            OpenGlBackendHolder.backend.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                            break;
                        }
                        case 4: 
                        case 5: {
                            OpenGlBackendHolder.backend.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                        }
                    }
                }
            } else if (hitEntity.isNotNull()) {
                AxisAlignedBB entityBounds = hitEntity.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().expand(0.3, 0.3, 0.3);
                double hitX = hit.getHitVec().getX();
                double hitY = hit.getHitVec().getY();
                double hitZ = hit.getHitVec().getZ();
                double xFaceDistance = Math.min(Math.abs(hitX - entityBounds.getMinX()), Math.abs(hitX - entityBounds.getMaxX()));
                double zFaceDistance = Math.min(Math.abs(hitZ - entityBounds.getMinZ()), Math.abs(hitZ - entityBounds.getMaxZ()));
                double yFaceDistance = Math.min(Math.abs(hitY - entityBounds.getMinY()), Math.abs(hitY - entityBounds.getMaxY()));
                double nearestFaceDistance = Math.min(yFaceDistance, Math.min(xFaceDistance, zFaceDistance));
                if (nearestFaceDistance == xFaceDistance) {
                    OpenGlBackendHolder.backend.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                } else if (nearestFaceDistance == zFaceDistance) {
                    OpenGlBackendHolder.backend.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                } else if (nearestFaceDistance == yFaceDistance) {
                    // empty if block
                }
            }
            if (hitEntity.isNotNull()) {
                RenderUtils.w(this.targetColor.getMutableColor());
                this.currentColor = this.targetColor.getMutableColor();
            } else {
                RenderUtils.w(this.aimingColor.getMutableColor());
                this.currentColor = this.aimingColor.getMutableColor();
            }
        }
        this.drawImpactMarker();
        OpenGlBackendHolder.backend.disableCapability(2929);
        if (!blendWasEnabled) {
            OpenGlBackendHolder.backend.disableCapability(3042);
        }
        OpenGlBackendHolder.backend.disableCapability(2848);
        if (depthWasEnabled) {
            OpenGlBackendHolder.backend.enableCapability(2929);
        }
        if (textureWasEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3553);
        }
        if (!blendWasEnabled) {
            OpenGlBackendHolder.backend.disableCapability(3042);
        }
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().O(0.0);
        RenderUtils.f();
        OpenGlBackendHolder.backend.popMatrix();
    }

    @Nullable
    private ItemStack findHeldProjectile(EntityPlayerSP entityPlayerSP) {
        if (ForgeVersion.MC_1_12_2.d()) {
            List<ItemStack> list = Arrays.asList(entityPlayerSP.i(EnumHand.mainHand()), entityPlayerSP.i(EnumHand.offHand()));
            for (ItemStack itemStack : list) {
                IProjectile iProjectile;
                Item item;
                if (itemStack.isNull() || (item = itemStack.getItem()).isNull() || (iProjectile = this.getProjectileForItem(item)) == null) continue;
                return itemStack;
            }
            return null;
        }
        return entityPlayerSP.getHeldItemHand();
    }

    private void addVertex(double x, double y, double z) {
        this.renderStates.add(new TrajectoriesProjectileRenderState(x, y, z));
    }
}

