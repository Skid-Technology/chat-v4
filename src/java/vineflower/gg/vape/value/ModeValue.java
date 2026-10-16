package gg.vape.value;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gg.vape.module.Mod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.unmap.Bendable;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.unmap.ValueBendable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModeValue extends ConditionalValue<ModeSelection, ModeValue> {
   private static GuiComponent[] legacyGuiState;
   private static final String JSON_VALUE_PROPERTY = "value";
   private final Map<Value<?, ?>, ArrayList<ModeSelection>> activeModesByDependentValue = new HashMap<>();
   private final String displayName;
   private final ModeSelection[] modes;
   private Bendable bind;
   private boolean bindable = false;

   public boolean isBindable() {
      return this.bindable;
   }

   public ModeValue setBindable(boolean bindable) {
      if (bindable && !BooleanValue.isOwnerBindable(this.getOwner())) {
         this.bindable = false;
         this.bind = null;
         return this;
      } else {
         this.bindable = bindable;
         if (bindable && this.bind == null) {
            this.bind = new ValueBendable(this, true);
         }

         return this;
      }
   }

   public Bendable getBind() {
      return this.bind;
   }

   public static void setLegacyGuiState(GuiComponent[] state) {
      legacyGuiState = state;
   }

   public static ModeValue create(Object owner, String name, ModeSelection defaultMode, ModeSelection... modes) {
      return create(owner, name, name, defaultMode, modes);
   }

   public static ModeValue createConfigured(
      Object owner, String name, String displayName, String description, ModeSelection defaultMode, int legacyIndex, ModeSelection[] modes
   ) {
      ModeValue modeValue = new ModeValue(owner, name, displayName, defaultMode, modes);
      modeValue.setDescription(description);

      for (ModeSelection mode : modes) {
         mode.attachToMode(modeValue);
      }

      return modeValue;
   }

   @Override
   public String getDisplayValue() {
      return this.getValue().toString();
   }

   public int getSelectedIndex() {
      for (int index = 0; index < this.getModes().length; index++) {
         if (this.getValue().equals(this.getModes()[index])) {
            return index;
         }
      }

      return 0;
   }

   public static GuiComponent[] getLegacyGuiState() {
      return legacyGuiState;
   }

   @Override
   public boolean hasActiveDependentBranch() {
      return true;
   }

   public static ModeValue createWithDescriptionAndLegacyIndex(
      Object owner, String name, String description, ModeSelection defaultMode, int legacyIndex, ModeSelection... modes
   ) {
      return createConfigured(owner, name, name, description, defaultMode, legacyIndex, modes);
   }

   public void addActiveMode(Value dependentValue, ModeOption modeOption) {
      this.addDependentValues(dependentValue);
      if (!this.activeModesByDependentValue.containsKey(dependentValue)) {
         this.activeModesByDependentValue.put(dependentValue, new ArrayList<>());
      }

      ArrayList<ModeSelection> activeModes = this.activeModesByDependentValue.get(dependentValue);
      activeModes.add(modeOption);
   }

   public ModeValue copyDefinition() {
      return new ModeValue(null, this.getId(), this.getName(), this.getValue(), this.getModes()).setBindable(this.bindable);
   }

   public ModeValue copyValueDefinition() {
      return this.copyDefinition();
   }

   @Override
   public String getName() {
      return this.displayName;
   }

   public void setValue(ModeSelection newMode) {
      if (!this.getValue().equals(newMode)) {
         if (this.getOwner() != null && newMode instanceof SubModuleValue && this.getValue() instanceof SubModuleValue) {
            this.switchSubModule((SubModuleValue)this.getValue(), (SubModuleValue)newMode);
         }

         super.setValue(newMode);
      }
   }

   public ModeSelection[] getModes() {
      return this.modes;
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
   public JsonObject toJson(boolean includeValue) {
      JsonObject jsonObject = this.toJson();
      if (this.getValue() != null) {
         jsonObject.addProperty(JSON_VALUE_PROPERTY, this.getValue().getSerializedName());
      }

      if (this.bind != null) {
         jsonObject.add("binds", this.bind.serializeBoundInputs());
      }

      return jsonObject;
   }

   @Override
   public boolean isDependentValueActive(Value dependentValue) {
      if (this.activeModesByDependentValue.containsKey(dependentValue)) {
         ArrayList<ModeSelection> activeModes = this.activeModesByDependentValue.get(dependentValue);
         return activeModes.contains(this.getValue());
      } else {
         return false;
      }
   }

   private void switchSubModule(SubModuleValue previousMode, SubModuleValue nextMode) {
      if (!this.isPersistenceSuppressed()) {
         Object parent = previousMode.getInstance().getParent();
         ((Mod)parent).p(previousMode, nextMode);
      }
   }

   public static ModeValue create(Object owner, String name, String description, ModeSelection defaultMode, ModeSelection... modes) {
      return createConfigured(owner, name, name, description, defaultMode, 1, modes);
   }

   public static ModeValue create(Object owner, String name, String displayName, String description, ModeSelection defaultMode, ModeSelection... modes) {
      return createConfigured(owner, name, displayName, description, defaultMode, 1, modes);
   }

   public void setSelectedIndex(int index) {
      this.setValue(this.getModes()[index]);
   }

   public void cycle() {
      ModeSelection[] modes = this.getModes();
      if (modes.length != 0) {
         int nextIndex = this.getSelectedIndex() + 1;
         if (nextIndex >= modes.length) {
            nextIndex = 0;
         }

         this.setValue(modes[nextIndex]);
      }
   }

   public ModeValue(Object owner, String name, String displayName, ModeSelection defaultMode, ModeSelection[] modes) {
      super(owner, name, defaultMode);
      this.displayName = displayName;
      this.modes = modes;
      this.bindable = false;
      this.bind = null;
      if (owner instanceof Mod) {
         Mod mod = (Mod)owner;

         for (ModeSelection mode : modes) {
            if (mode instanceof SubModuleValue) {
               SubModuleValue subModuleMode = (SubModuleValue)mode;

               for (Value<?, ?> dependentValue : subModuleMode.getInstance().getAllValues()) {
                  mod.addValue(dependentValue);
                  this.addActiveMode(dependentValue, subModuleMode);
               }
            }
         }
      }
   }

   @Override
   public void parse(String serializedMode) {
      ModeValue owningModeValue = this.getValue().getMode();
      if (owningModeValue != null) {
         ModeSelection parsedMode = ModeSelection.findBySerializedName(owningModeValue, serializedMode);
         if (parsedMode != null) {
            this.setValue(parsedMode);
         }
      }
   }

   public void addModeDependentValues(ModeOption modeOption, Value... values) {
      this.addDependentValues(values);

      for (Value dependentValue : values) {
         this.addActiveMode(dependentValue, modeOption);
      }
   }

   static {
      setLegacyGuiState(null);
   }
}
