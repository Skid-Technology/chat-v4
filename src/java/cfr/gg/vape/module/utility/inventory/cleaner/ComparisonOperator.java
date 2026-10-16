/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 *  org.jetbrains.annotations.UnmodifiableView
 */
package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.DescribedOption;
import gg.vape.unmap.INamed;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public enum ComparisonOperator implements INamed,
DescribedOption
{
    EQUALS("equals", "=="),
    NOT_EQUAL("does not equal", "!="),
    GREATER_THAN("greater than", ">"),
    GREATER_THAN_OR_EQUAL("greater or equal to", ">="),
    LESS_THAN("less than", "<"),
    LESS_THAN_OR_EQUAL("less or equal to", "<=");

    public static final @UnmodifiableView List<ComparisonOperator> VALUES;
    private final String symbol;
    private final String displayName;

    @Override
    public String getDescription() {
        return this.symbol;
    }

    public boolean compare(short left, short right) {
        switch (this) {
            case EQUALS: {
                return left == right;
            }
            case NOT_EQUAL: {
                return left != right;
            }
            case GREATER_THAN: {
                return left > right;
            }
            case GREATER_THAN_OR_EQUAL: {
                return left >= right;
            }
            case LESS_THAN: {
                return left < right;
            }
            case LESS_THAN_OR_EQUAL: {
                return left <= right;
            }
        }
        return false;
    }

    public static ComparisonOperator fromNameOrDefault(String name, ComparisonOperator fallback) {
        ComparisonOperator operator = ComparisonOperator.findByName(name);
        return operator == null ? fallback : operator;
    }

    public boolean compare(int left, int right) {
        switch (this) {
            case EQUALS: {
                return left == right;
            }
            case NOT_EQUAL: {
                return left != right;
            }
            case GREATER_THAN: {
                return left > right;
            }
            case GREATER_THAN_OR_EQUAL: {
                return left >= right;
            }
            case LESS_THAN: {
                return left < right;
            }
            case LESS_THAN_OR_EQUAL: {
                return left <= right;
            }
        }
        return false;
    }

    @Nullable
    public static ComparisonOperator findByName(String name) {
        for (ComparisonOperator operator : VALUES) {
            if (!operator.getName().equalsIgnoreCase(name)) continue;
            return operator;
        }
        return null;
    }

    @Override
    public String getName() {
        return this.displayName;
    }

    private ComparisonOperator(String displayName, String symbol) {
        this.displayName = displayName;
        this.symbol = symbol;
    }

    public boolean compare(double left, double right) {
        switch (this) {
            case EQUALS: {
                return left == right;
            }
            case NOT_EQUAL: {
                return left != right;
            }
            case GREATER_THAN: {
                return left > right;
            }
            case GREATER_THAN_OR_EQUAL: {
                return left >= right;
            }
            case LESS_THAN: {
                return left < right;
            }
            case LESS_THAN_OR_EQUAL: {
                return left <= right;
            }
        }
        return false;
    }

    public static ComparisonOperator fromName(String name) {
        return ComparisonOperator.fromNameOrDefault(name, EQUALS);
    }

    static {
        VALUES = Arrays.asList(ComparisonOperator.values());
    }
}

