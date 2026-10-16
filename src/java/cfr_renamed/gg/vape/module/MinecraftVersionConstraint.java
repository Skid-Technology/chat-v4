/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module;

import gg.vape.module.MinecraftVersionComparisonOperator;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinecraftVersionConstraint {
    final public ForgeVersion var_gg_vape_wrapper_impl_ForgeVersion_D;
    final public MinecraftVersionComparisonOperator var_gg_vape_module_MinecraftVersionComparisonOperator_s;

    public static List<MinecraftVersionConstraint> o(List<MinecraftVersionConstraint> list) {
        ArrayList<MinecraftVersionConstraint> arrayList = new ArrayList<MinecraftVersionConstraint>();
        for (MinecraftVersionConstraint minecraftVersionConstraint : list) {
            if (minecraftVersionConstraint.y()) continue;
            arrayList.add(minecraftVersionConstraint);
        }
        return arrayList;
    }

    public ForgeVersion L() {
        return this.var_gg_vape_wrapper_impl_ForgeVersion_D;
    }

    public MinecraftVersionConstraint(ForgeVersion forgeVersion, MinecraftVersionComparisonOperator minecraftVersionComparisonOperator) {
        this.var_gg_vape_wrapper_impl_ForgeVersion_D = forgeVersion;
        this.var_gg_vape_module_MinecraftVersionComparisonOperator_s = minecraftVersionComparisonOperator;
    }

    public MinecraftVersionComparisonOperator l() {
        return this.var_gg_vape_module_MinecraftVersionComparisonOperator_s;
    }

    public boolean y() {
        return this.var_gg_vape_module_MinecraftVersionComparisonOperator_s.N(ForgeVersion.c(), this.var_gg_vape_wrapper_impl_ForgeVersion_D.i());
    }

    public static List<MinecraftVersionConstraint> L(MinecraftVersionConstraint ... minecraftVersionConstraintArray) {
        return MinecraftVersionConstraint.o(Arrays.asList(minecraftVersionConstraintArray));
    }
}

