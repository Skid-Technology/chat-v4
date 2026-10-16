/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventBedBreakerUpdate;
import gg.vape.event.impl.EventPlayerUseItem;
import gg.vape.event.impl.EventPostAttack;
import gg.vape.event.impl.EventPreAttack;
import gg.vape.event.impl.EventWindowClick;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.PlayerUseItemCallback;
import gg.vape.wrapper.impl.ForgeVersion;

public class PlayerControllerMPEventMappingTask
extends JavassistMappingTask {
    final public static boolean var_boolean_U;

    public PlayerControllerMPEventMappingTask() {
        super(MappedClasses.ld);
    }

    @Override
    public void transform() {
        Object object;
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().hj.var_gg_vape_mapping_MappingMethod_c;
        if (ForgeVersion.MC_1_12_2.v()) {
            this.O(mappingMethod, EventPlayerUseItem.class, "$3", "false");
        } else if (ForgeVersion.MC_1_12_2.d()) {
            object = ForgeVersion.MC_1_20_6.d() ? "$1, $2" : "$1, $3";
            this.O(mappingMethod, PlayerUseItemCallback.class, (String)object, MappedClasses.zr.getName() + "." + Vape.INSTANCE.getMappings().enumActionResult.passField.getResolvedName());
        }
        this.c(Vape.INSTANCE.getMappings().hj.var_gg_vape_mapping_MappingMethod_L, EventBedBreakerUpdate.class, "");
        if (!var_boolean_U) {
            object = Vape.INSTANCE.getMappings().hj.var_gg_vape_mapping_MappingMethod_i;
            this.c((MappingMethod)object, EventPreAttack.class, "$2");
            this.k((MappingMethod)object, EventPostAttack.class, "$2");
        }
        this.O(Vape.INSTANCE.getMappings().hj.var_gg_vape_mapping_MappingMethod_I, EventWindowClick.class, "$0", "null");
    }

    static {
        var_boolean_U = ForgeVersion.MC_1_16_5.d() && (Vape.INSTANCE.isForgeAbsent() || Vape.INSTANCE.isVanillaMinecraftPresent());
    }
}

