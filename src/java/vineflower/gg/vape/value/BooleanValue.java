package gg.vape.value;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.unmap.Bendable;
import gg.vape.unmap.ValueBendable;
import org.jetbrains.annotations.Nullable;

public class BooleanValue extends ConditionalValue<Boolean, BooleanValue> {
   private static String[] legacyState;
   @Nullable
   private ListValue compactListValue = null;
   private final String displayName;
   private final boolean bindable;
   private final Bendable bind;

   public BooleanValue(Object owner, String name, boolean defaultValue) {
      this(owner, name, name, defaultValue);
   }

   @Override
   public void parse(String serializedValue) {
      if (!serializedValue.isEmpty()) {
         this.setValue(Boolean.parseBoolean(serializedValue));
      }
   }

   @Override
   public boolean loadJson(JsonObject jsonObject) {
      boolean parsed = super.loadJson(jsonObject);
      JsonArray binds = null;
      if (jsonObject.has("binds") && jsonObject.get("binds").isJsonArray()) {
         binds = jsonObject.getAsJsonArray("binds");
      }

      if (binds != null && this.bind != null) {
         this.bind.loadBoundInputs(binds, false);
      }

      return parsed;
   }

   @Override
   public JsonObject toJson(boolean bl) {
      JsonObject jsonObject = super.toJson(bl);
      if (this.bind != null) {
         jsonObject.add("binds", this.bind.serializeBoundInputs());
      }

      return jsonObject;
   }

   public static void setBooleanLegacyState(String[] state) {
      legacyState = state;
   }

   public ListValue getTerminalDependentValue() {
      return this.compactListValue;
   }

   @Override
   public boolean hasActiveDependentBranch() {
      return this.isEnabled();
   }

   public BooleanValue copyDefinition() {
      return createFull(null, this.getId(), this.getDisplayName(), this.getEffectiveValue(), this.getDescription());
   }

   public BooleanValue copyValueDefinition() {
      return this.copyDefinition();
   }

   public void setCompactListValue(ListValue listValue) {
      this.compactListValue = listValue;
   }

   public static BooleanValue createWithDisplayName(Object owner, String name, String displayName, boolean defaultValue) {
      return createFull(owner, name, displayName, defaultValue, null);
   }

   @Override
   public boolean isDependentValueActive(Value dependentValue) {
      return this.isEnabled();
   }

   public static boolean isOwnerBindable(Object owner) {
      if (owner == null) {
         return false;
      } else {
         if (owner instanceof SubModule) {
            owner = ((SubModule)owner).getParent();
         }

         if (!(owner instanceof Mod)) {
            return false;
         } else {
            Mod mod = (Mod)owner;
            return mod.getCategory() != null && mod.getCategory() != Category.NONE;
         }
      }
   }

   public boolean isBindable() {
      return this.bindable;
   }

   public BooleanValue(Object owner, String name, String displayName, boolean defaultValue) {
      super(owner, name, defaultValue);
      this.displayName = displayName;
      this.bindable = isOwnerBindable(owner);
      this.bind = this.bindable ? new ValueBendable(this, true) : null;
   }

   public Bendable getBind() {
      return this.bind;
   }

   public static BooleanValue create(Object owner, String name, boolean defaultValue, String description) {
      return createFull(owner, name, name, defaultValue, description);
   }

   public Boolean getEffectiveValue() {
      return this.isHidden() ? false : (Boolean)super.getValue();
   }

   public boolean isEnabled() {
      return this.getEffectiveValue();
   }

   public static BooleanValue create(Object owner, String name, boolean defaultValue) {
      return create(owner, name, defaultValue, null);
   }

   public ValueCondition getEnabledCondition() {
      return new ValueCondition().requireTrue(this);
   }

   public static BooleanValue createFull(Object owner, String name, String displayName, boolean defaultValue, String description) {
      BooleanValue booleanValue = new BooleanValue(owner, name, displayName, defaultValue);
      booleanValue.setDescription(description);
      return booleanValue;
   }

   public ValueCondition getDisabledCondition() {
      return new ValueCondition().requireFalse(this);
   }

   public boolean toggleIfValid() {
      boolean nextValue = !this.getEffectiveValue();
      if (this.isChangeValid(nextValue)) {
         this.setValue(nextValue);
         return true;
      } else {
         return false;
      }
   }

   public void toggle() {
      this.setValue(!this.getEffectiveValue());
   }

   public static String[] getBooleanLegacyState() {
      return legacyState;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public Boolean getEffectiveValueCompat() {
      return this.getEffectiveValue();
   }

   static {
      if (getBooleanLegacyState() == null) {
         setBooleanLegacyState(new String[3]);
      }
   }
}
