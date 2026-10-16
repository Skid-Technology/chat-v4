/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.DirectionAxis;
import gg.vape.wrapper.impl.DirectionVector;
import gg.vape.wrapper.impl.EnumFacing;

public class Direction
extends EnumFacing {
    public DirectionVector Q$src$Lgg_vape_wrapper_impl_DirectionVector_$l2h44r() {
        return new DirectionVector(Direction.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MDirection_Q.g(this.I));
    }

    public Direction(Object object) {
        super(object);
    }

    public DirectionAxis n() {
        return new DirectionAxis(Direction.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MDirection_Q.J(this.I));
    }

    public int F() {
        return Direction.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MDirection_Q.z(this.I);
    }

    public static Direction i(double d, double d2, double d3) {
        return new Direction(Direction.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MDirection_Q.w(d, d2, d3));
    }

    public int Q() {
        return Direction.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MDirection_Q.o(this.I);
    }

    public int S() {
        return Direction.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MDirection_Q.S(this.I);
    }
}

