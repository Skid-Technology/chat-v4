/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.module.utility.inventory.cleaner.ComparisonOperator;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterCondition;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionType;
import gg.vape.module.utility.inventory.cleaner.NumericFilterCondition;
import gg.vape.wrapper.impl.ItemStack;

public class QuantityFilterCondition
implements NumericFilterCondition<QuantityFilterCondition> {
    private int amount = 1;
    private ComparisonOperator operator = ComparisonOperator.EQUALS;

    @Override
    public QuantityFilterCondition parseValue(String value) throws NumberFormatException {
        return this.parseAmount(value);
    }

    public QuantityFilterCondition parseAmount(String value) {
        this.amount = Integer.parseInt(value);
        return this;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public boolean matches(ItemStack itemStack) {
        if (itemStack.isNull()) {
            return false;
        }
        return this.operator.compare(itemStack.t(), this.amount);
    }

    public QuantityFilterCondition() {
    }

    @Override
    public QuantityFilterCondition withOperator(ComparisonOperator operator) {
        this.operator = operator;
        return this;
    }

    public QuantityFilterCondition withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public ComparisonOperator getOperator() {
        return this.operator;
    }

    @Override
    public InventoryFilterConditionType getType() {
        return InventoryFilterConditionType.QUANTITY;
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = NumericFilterCondition.super.toJson();
        jsonObject.addProperty("amount", this.amount);
        return jsonObject;
    }

    public QuantityFilterCondition(int amount, ComparisonOperator operator) {
        this.amount = amount;
        this.operator = operator;
    }

    public QuantityFilterCondition(JsonObject jsonObject) {
        this.amount = jsonObject.get("amount").getAsInt();
        this.operator = ComparisonOperator.fromName(jsonObject.get("operator").getAsString());
    }

    @Override
    public QuantityFilterCondition copy() {
        return new QuantityFilterCondition(this.amount, this.operator);
    }

    @Override
    public String getValueText() {
        return String.valueOf(this.amount);
    }

    @Override
    public InventoryFilterCondition parseValue(String string) throws NumberFormatException {
        return this.parseValue(string);
    }

    @Override
    public InventoryFilterCondition withOperator(ComparisonOperator comparisonOperator) {
        return this.withOperator(comparisonOperator);
    }

    @Override
    public InventoryFilterCondition copy() {
        return this.copy();
    }
}

