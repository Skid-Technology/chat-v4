/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.datas;

import gg.vape.utils.MathUtil;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.Vec3i;
import java.util.Objects;

public class BlockCoordinate {
    private final int var_int_B;
    private final int var_int_F;
    private final int var_int_o;

    public Vec3 P() {
        return Vec3.create(this.var_int_o, this.var_int_B, this.var_int_F);
    }

    public BlockCoordinate F(int n, int n2, int n3) {
        return new BlockCoordinate(this.B() + n, this.E() + n2, this.A() + n3);
    }

    public int hashCode() {
        return Objects.hash(this.var_int_o, this.var_int_B, this.var_int_F);
    }

    public int E() {
        return this.var_int_B;
    }

    public BlockCoordinate(int n, int n2, int n3) {
        this.var_int_o = n;
        this.var_int_B = n2;
        this.var_int_F = n3;
    }

    public int A() {
        return this.var_int_F;
    }

    public BlockPos E$src$Lgg_vape_wrapper_impl_BlockPos_$1bb1czr() {
        return BlockPos.create(this.var_int_o, this.var_int_B, this.var_int_F);
    }

    public BlockCoordinate i(EnumFacing enumFacing) {
        Vec3i directionVector = enumFacing.getDirectionVector();
        return new BlockCoordinate(this.B() + directionVector.getX(), this.E() + directionVector.getY(), this.A() + directionVector.getZ());
    }

    public BlockCoordinate(double d, double d2, double d3) {
        this.var_int_o = MathUtil.floor(d);
        this.var_int_B = MathUtil.floor(d2);
        this.var_int_F = MathUtil.floor(d3);
    }

    public BlockData O() {
        return new BlockData(this.var_int_o, this.var_int_B, this.var_int_F);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        BlockCoordinate blockCoordinate = (BlockCoordinate)object;
        return this.var_int_o == blockCoordinate.var_int_o && this.var_int_B == blockCoordinate.var_int_B && this.var_int_F == blockCoordinate.var_int_F;
    }

    public String toString() {
        return "BlockLocation{x=" + this.var_int_o + ", y=" + this.var_int_B + ", z=" + this.var_int_F + '}';
    }

    public int B() {
        return this.var_int_o;
    }

    public BlockCoordinate(BlockData blockData) {
        this.var_int_o = blockData.D();
        this.var_int_B = blockData.B();
        this.var_int_F = blockData.G();
    }
}

