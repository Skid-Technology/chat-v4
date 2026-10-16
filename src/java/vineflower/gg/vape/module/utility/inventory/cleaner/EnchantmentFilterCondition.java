package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.utils.EnchantmentUtil;
import gg.vape.utils.StringUtils;
import gg.vape.wrapper.impl.Enchantment;
import gg.vape.wrapper.impl.ItemStack;
import java.util.Map;
import java.util.Map.Entry;

public class EnchantmentFilterCondition implements NumericFilterCondition<EnchantmentFilterCondition> {
   private EnchantmentFilterMode mode = EnchantmentFilterMode.HAS;
   private int level = 1;
   private String enchantment;
   private ComparisonOperator operator = ComparisonOperator.EQUALS;

   public EnchantmentFilterCondition parseValue(String value) throws NumberFormatException {
      this.level = Integer.parseInt(value);
      return this;
   }

   public EnchantmentFilterCondition withOperator(ComparisonOperator operator) {
      this.operator = operator;
      return this;
   }

   public EnchantmentFilterCondition copy() {
      return new EnchantmentFilterCondition(this.mode, this.enchantment, this.level, this.operator);
   }

   public EnchantmentFilterMode getMode() {
      return this.mode;
   }

   public EnchantmentFilterCondition withEnchantment(String enchantment) {
      this.enchantment = enchantment;
      return this;
   }

   @Override
   public String getValueText() {
      return String.valueOf(this.level);
   }

   public EnchantmentFilterCondition withLevel(int level) {
      this.level = level;
      return this;
   }

   @Override
   public boolean matches(ItemStack itemStack) {
      if (this.enchantment == null) {
         return false;
      } else if (itemStack.isNull()) {
         return false;
      } else {
         Map<Enchantment, Short> enchantments = EnchantmentUtil.A(itemStack);
         if (enchantments.isEmpty()) {
            return false;
         } else {
            for (Entry<Enchantment, Short> entry : enchantments.entrySet()) {
               Enchantment enchantment = entry.getKey();
               if (StringUtils.Q(enchantment.getTranslatedName(1)).equalsIgnoreCase(this.enchantment)) {
                  if (this.mode == EnchantmentFilterMode.HAS) {
                     return true;
                  }

                  if (this.mode == EnchantmentFilterMode.LEVEL) {
                     return this.operator.compare(entry.getValue(), this.level);
                  }
               }
            }

            return false;
         }
      }
   }

   @Override
   public InventoryFilterConditionType getType() {
      return InventoryFilterConditionType.ENCHANTMENT;
   }

   public EnchantmentFilterCondition() {
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonObject = NumericFilterCondition.super.toJson();
      jsonObject.addProperty("mode", this.mode.getName());
      jsonObject.addProperty("enchantment", this.enchantment);
      jsonObject.addProperty("level", this.level);
      return jsonObject;
   }

   public String getEnchantment() {
      return this.enchantment;
   }

   @Override
   public ComparisonOperator getOperator() {
      return this.operator;
   }

   public EnchantmentFilterCondition(JsonObject jsonObject) {
      this.mode = EnchantmentFilterMode.fromName(ConfigJsonUtils.getString(jsonObject, "mode"));
      this.enchantment = ConfigJsonUtils.getString(jsonObject, "enchantment");
      Integer configuredLevel = ConfigJsonUtils.getInteger(jsonObject, "level");
      this.level = configuredLevel != null ? configuredLevel : 1;
      this.operator = ComparisonOperator.fromName(jsonObject.get("operator").getAsString());
   }

   public EnchantmentFilterCondition withMode(EnchantmentFilterMode mode) {
      this.mode = mode;
      return this;
   }

   public int getLevel() {
      return this.level;
   }

   public EnchantmentFilterCondition(EnchantmentFilterMode mode, String enchantment, int level, ComparisonOperator operator) {
      this.mode = mode;
      this.enchantment = enchantment;
      this.level = level;
      this.operator = operator;
   }
}
