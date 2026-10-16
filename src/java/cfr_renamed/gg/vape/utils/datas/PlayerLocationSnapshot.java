/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.datas;

import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.Vec3;

public class PlayerLocationSnapshot {
    private double var_double_a;
    private double var_double_F;
    private final long var_long_x = System.currentTimeMillis();
    private float var_float_q;
    private float var_float_I;
    private double var_double_u;
    private static String var_java_lang_String_K;

    public PlayerLocationSnapshot(double d, double d2, double d3) {
        this.var_double_F = d;
        this.var_double_a = d2;
        this.var_double_u = d3;
    }

    public static String y() {
        return var_java_lang_String_K;
    }

    public void setYaw(float f) {
        this.var_float_q = f;
    }

    public double distanceTo(Entity entity) {
        return MathUtil.g(entity.double_z() - this.var_double_F, entity.double_N() - this.var_double_a, entity.double_h() - this.var_double_u);
    }

    public void setX(double d) {
        this.var_double_F = d;
    }

    public double getY() {
        return this.var_double_a;
    }

    public double getZ() {
        return this.var_double_u;
    }

    public double getX() {
        return this.var_double_F;
    }

    public double horizontalDistanceTo(PlayerLocationSnapshot playerLocationSnapshot) {
        return MathUtil.g(playerLocationSnapshot.var_double_F - this.var_double_F, playerLocationSnapshot.var_double_u - this.var_double_u);
    }

    public void setY(double d) {
        this.var_double_a = d;
    }

    public static AxisAlignedBB createPlayerBoundingBox(double d, double d2, double d3) {
        return AxisAlignedBB.create(d - 0.3, d2, d3 - 0.3, d + 0.3, d2 + 1.8, d3 + 0.3);
    }

    public static float[] calculateRotations(double d, double d2, double d3, double d4, double d5, double d6) {
        double d8 = d4 - d;
        double d9 = d5 - d2;
        double d7 = d6 - d3;
        double d10 = MathUtil.sqrt(d8 * d8 + d7 * d7);
        float f2 = (float)Math.toDegrees(-Math.atan(d8 / d7));
        float f = (float)(-Math.toDegrees(Math.atan(d9 / d10)));
        if (d7 < 0.0 && d8 < 0.0) {
            f2 = (float)(90.0 + Math.toDegrees(Math.atan(d7 / d8)));
        } else if (d7 < 0.0 && d8 > 0.0) {
            f2 = (float)(-90.0 + Math.toDegrees(Math.atan(d7 / d8)));
        }
        return new float[]{f2, f};
    }

    public void setPitch(float f) {
        this.var_float_I = f;
    }

    public float getPitch() {
        return this.var_float_I;
    }

    public static void U(String string) {
        var_java_lang_String_K = string;
    }

    public void setZ(double d) {
        this.var_double_u = d;
    }

    public double distanceTo(PlayerLocationSnapshot playerLocationSnapshot) {
        return MathUtil.g(playerLocationSnapshot.var_double_F - this.var_double_F, playerLocationSnapshot.var_double_a - this.var_double_a, playerLocationSnapshot.var_double_u - this.var_double_u);
    }

    public float getYaw() {
        return this.var_float_q;
    }

    public static double rayIntersectionDistance(PlayerLocationSnapshot playerLocationSnapshot, PlayerLocationSnapshot playerLocationSnapshot2) {
        Vec3 vec3 = PlayerLocationSnapshot.getEyePosition(playerLocationSnapshot);
        float[] fArray = PlayerLocationSnapshot.calculateRotations(playerLocationSnapshot.getX(), playerLocationSnapshot.getY(), playerLocationSnapshot.getZ(), playerLocationSnapshot2.getX(), playerLocationSnapshot2.getY(), playerLocationSnapshot2.getZ());
        Vec3 vec32 = PlayerLocationSnapshot.directionFromRotation(fArray[0], fArray[1]);
        Vec3 vec33 = vec3.addVector(vec32.getX() * Double.MAX_VALUE, vec32.getY() * Double.MAX_VALUE, vec32.getZ() * Double.MAX_VALUE);
        AxisAlignedBB ts_22 = PlayerLocationSnapshot.createPlayerBoundingBox(playerLocationSnapshot2.getX(), playerLocationSnapshot2.getY(), playerLocationSnapshot2.getZ()).expand(0.1, 0.1, 0.1);
        RayTraceResult jo_12 = ts_22.calculateIntercept(vec3, vec33);
        if (jo_12 != null && jo_12.isNotNull()) {
            return vec3.distanceTo(jo_12.getHitVec());
        }
        return 0.0;
    }

    public double horizontalDistanceTo(Entity entity) {
        return MathUtil.g(entity.double_z() - this.var_double_F, entity.double_h() - this.var_double_u);
    }

    public PlayerLocationSnapshot(double d, double d2, double d3, float f, float f2) {
        this.var_double_F = d;
        this.var_double_a = d2;
        this.var_double_u = d3;
        this.var_float_q = f;
        this.var_float_I = f2;
    }

    public static Vec3 getEyePosition(PlayerLocationSnapshot playerLocationSnapshot) {
        return Vec3.create(playerLocationSnapshot.getX(), playerLocationSnapshot.getY() + 1.62, playerLocationSnapshot.getZ());
    }

    public long getCreatedAt() {
        return this.var_long_x;
    }

    private static Vec3 directionFromRotation(float f, float f2) {
        float f3 = MathUtil.cos(-f * ((float)Math.PI / 180) - (float)Math.PI);
        float f4 = MathUtil.sin(-f * ((float)Math.PI / 180) - (float)Math.PI);
        float f5 = -MathUtil.cos(-f2 * ((float)Math.PI / 180));
        float f6 = MathUtil.sin(-f2 * ((float)Math.PI / 180));
        return Vec3.create(f4 * f5, f6, f3 * f5);
    }

    public void setPosition(double d, double d2, double d3) {
        this.var_double_F = d;
        this.var_double_a = d2;
        this.var_double_u = d3;
    }

    static {
        if (PlayerLocationSnapshot.y() == null) {
            PlayerLocationSnapshot.U("JcdmX");
        }
    }
}

