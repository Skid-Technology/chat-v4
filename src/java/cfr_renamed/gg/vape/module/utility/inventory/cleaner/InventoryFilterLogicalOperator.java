/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.utility.inventory.cleaner;

import gg.vape.unmap.INamed;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public enum InventoryFilterLogicalOperator implements INamed
{
    AND("And"),
    OR("Or");

    private final String displayName;
    final public static List<InventoryFilterLogicalOperator> VALUES;

    private InventoryFilterLogicalOperator(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getName() {
        return this.displayName;
    }

    @Nullable
    public static InventoryFilterLogicalOperator findByName(String name) {
        for (InventoryFilterLogicalOperator operator : VALUES) {
            if (!operator.getName().equalsIgnoreCase(name)) continue;
            return operator;
        }
        return null;
    }

    static {
        VALUES = Arrays.asList(InventoryFilterLogicalOperator.values());
    }
}

