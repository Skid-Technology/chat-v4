package gg.vape.value;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Mod;
import gg.vape.utils.RotationUtil;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import java.util.Arrays;
import java.util.List;

public class EntityTargetFilterValue extends Value<Boolean[], EntityTargetFilterValue> {
   private final BooleanValue playersValue = BooleanValue.createWithDisplayName(this, "Players" + this.getId(), "Players", true);
   private final BooleanValue ignoreBehindWallsValue;
   private final List<BooleanValue> filterValues;
   private final BooleanValue ignoreNakedValue;
   private final BooleanValue ignoreInvisibleValue;
   private final BooleanValue peacefulValue;
   boolean synchronizingValues;
   private final BooleanValue neutralValue;
   private final BooleanValue mobsValue = BooleanValue.createWithDisplayName(this, "Mobs" + this.getId(), "Mobs", false);

   @Override
   public String getDisplayValue() {
      StringBuilder displayBuilder = new StringBuilder();
      if (this.playersValue.getEffectiveValue()) {
         displayBuilder.append("Players ");
      }

      if (this.mobsValue.getEffectiveValue()) {
         displayBuilder.append("Mobs ");
      }

      if (this.peacefulValue.getEffectiveValue()) {
         displayBuilder.append("Peaceful ");
      }

      if (this.neutralValue.getEffectiveValue()) {
         displayBuilder.append("Neutral ");
      }

      String displayValue;
      if ((displayValue = displayBuilder.toString().trim()).isEmpty()) {
         displayValue = "None";
      }

      if (this.ignoreNakedValue.getEffectiveValue() || this.ignoreInvisibleValue.getEffectiveValue() || this.ignoreBehindWallsValue.getEffectiveValue()) {
         displayBuilder.append(" | ");
         if (this.ignoreNakedValue.getEffectiveValue()) {
            displayBuilder.append("NoNaked ");
         }

         if (this.ignoreInvisibleValue.getEffectiveValue()) {
            displayBuilder.append("NoInvis ");
         }

         if (this.ignoreBehindWallsValue.getEffectiveValue()) {
            displayBuilder.append("NoWalls");
         }

         displayValue = displayBuilder.toString().trim();
      }

      return displayValue;
   }

   public BooleanValue getNeutralValue() {
      return this.neutralValue;
   }

   public List<BooleanValue> getFilterValues() {
      return this.filterValues;
   }

   public EntityTargetFilterValue(Object owner, String name) {
      super(owner, name, new Boolean[7]);
      this.peacefulValue = BooleanValue.createWithDisplayName(this, "Peaceful" + this.getId(), "Peaceful", false);
      this.neutralValue = BooleanValue.createWithDisplayName(this, "Neutral" + this.getId(), "Neutral", false);
      this.ignoreNakedValue = BooleanValue.createWithDisplayName(this, "Ignore Naked" + this.getId(), "Ignore naked", false);
      this.ignoreInvisibleValue = BooleanValue.createWithDisplayName(this, "Ignore invisible" + this.getId(), "Ignore invisible", false);
      this.ignoreBehindWallsValue = BooleanValue.createWithDisplayName(this, "Ignore behind walls" + this.getId(), "Ignore behind walls", false);
      this.filterValues = Arrays.asList(
         this.playersValue,
         this.mobsValue,
         this.peacefulValue,
         this.neutralValue,
         this.ignoreNakedValue,
         this.ignoreInvisibleValue,
         this.ignoreBehindWallsValue
      );

      for (int index = 0; index < this.filterValues.size(); index++) {
         this.getDefaultValue()[index] = this.filterValues.get(index).getDefaultValue();
         this.filterValues.get(index).addChangeListener(this::handleFilterValueChanged);
      }
   }

   public BooleanValue getIgnoreBehindWallsValue() {
      return this.ignoreBehindWallsValue;
   }

   public BooleanValue getPeacefulValue() {
      return this.peacefulValue;
   }

   @Override
   public String toString() {
      StringBuilder serializedFilters = new StringBuilder();

      for (BooleanValue filterValue : this.filterValues) {
         serializedFilters.append(filterValue.getEffectiveValue() ? "1" : "0");
      }

      return serializedFilters.toString();
   }

   public void setFilterValues(Boolean[] filterSnapshot) {
      for (int index = 0; index < this.filterValues.size(); index++) {
         this.filterValues.get(index).setValue(filterSnapshot[index]);
      }

      super.setValue(filterSnapshot);
   }

   public BooleanValue getIgnoreNakedValue() {
      return this.ignoreNakedValue;
   }

   @Override
   public void parse(String serializedFilters) {
      for (int index = 0; index < serializedFilters.length() && this.filterValues.size() > index; index++) {
         this.filterValues.get(index).setValue(serializedFilters.charAt(index) == '1');
      }
   }

   @Override
   public boolean loadJson(JsonObject jsonObject) {
      boolean loaded = super.loadJson(jsonObject);
      this.parse(this.toString());
      return loaded;
   }

   @Override
   public JsonObject toJson(boolean includeValue) {
      JsonObject jsonObject = this.toJson();
      jsonObject.addProperty("value", this.toString());
      return jsonObject;
   }

   public BooleanValue getMobsValue() {
      return this.mobsValue;
   }

   @Override
   public boolean isDefault() {
      return this.filterValues.stream().allMatch(Value::isDefault);
   }

   @Override
   public void reset() {
      super.reset();
      if (this.isResettable()) {
         for (BooleanValue filterValue : this.filterValues) {
            filterValue.reset();
         }
      }
   }

   private void handleFilterValueChanged(BooleanValue changedFilterValue) {
      if (!this.synchronizingValues) {
         this.synchronizingValues = true;
         this.setFilterValues(this.getFilterValueSnapshot());
         this.synchronizingValues = false;
      }
   }

   public BooleanValue getIgnoreInvisibleValue() {
      return this.ignoreInvisibleValue;
   }

   public EntityTargetFilterValue copyDefinition() {
      return new EntityTargetFilterValue(null, this.getId());
   }

   public EntityTargetFilterValue copyValueDefinition() {
      return this.copyDefinition();
   }

   public Boolean[] getFilterValueSnapshot() {
      Boolean[] filterSnapshot = new Boolean[this.filterValues.size()];

      for (int index = 0; index < this.filterValues.size(); index++) {
         filterSnapshot[index] = this.filterValues.get(index).getEffectiveValue();
      }

      return filterSnapshot;
   }

   public BooleanValue getPlayersValue() {
      return this.playersValue;
   }

   public static EntityTargetFilterValue createForModule(Mod module) {
      return new EntityTargetFilterValue(module, "Target Settings " + module.getName());
   }

   public boolean isValidTarget(Entity entity) {
      if (entity.isNull()) {
         return false;
      } else if (ClientSettings.isReservedEntity(entity)) {
         return false;
      } else if (!this.peacefulValue.getEffectiveValue() && entity.isInstance(MappedClasses.zS)) {
         return false;
      } else {
         EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
         if (entity.equals(entityPlayerSP)) {
            return false;
         } else if (!entity.isInstance(MappedClasses.zm)) {
            return false;
         } else if (ForgeVersion.MC_1_7_10.Y() && entity.isInstance(MappedClasses.FT)) {
            return false;
         } else {
            EntityLivingBase entityLivingBase = new EntityLivingBase(entity.getObject());
            if (entityLivingBase.w$src$F$15l9epb() <= 0.0F) {
               return false;
            } else if (this.ignoreInvisibleValue.getEffectiveValue() && RotationUtil.k(entityLivingBase)) {
               return false;
            } else if (this.ignoreBehindWallsValue.getEffectiveValue() && !entityPlayerSP.canEntityBeSeen(entity)) {
               return false;
            } else if (Vape.INSTANCE.getFriendManager().isFriend(entityLivingBase)) {
               return false;
            } else {
               boolean isPlayer = entity.isInstance(MappedClasses.lG);
               Class mobClass = ForgeVersion.MC_1_21_4.d() ? MappedClasses.Yw : MappedClasses.Fr;
               boolean isMobEntity = entity.isInstance(mobClass)
                  || entity.isInstance(MappedClasses.Zo)
                  || entity.isInstance(MappedClasses.ur)
                  || entity.isInstance(MappedClasses.Z8);
               boolean isPeacefulEntity = entity.isInstance(MappedClasses.ZP) || entity.isInstance(MappedClasses.Fb) || entity.isInstance(MappedClasses.qf);
               if (isPlayer) {
                  if (!this.playersValue.getEffectiveValue()) {
                     return false;
                  } else if (Vape.INSTANCE.getEnemyManager().isEnemy(entity.getName())) {
                     return true;
                  } else if (this.ignoreNakedValue.getEffectiveValue() && RotationUtil.b$src$Z$reqs95(entityLivingBase)) {
                     return false;
                  } else {
                     return Vape.INSTANCE.getClientSettings().isTeammate(entityPlayerSP, entityLivingBase)
                        ? false
                        : !Vape.INSTANCE.getClientSettings().isBot(entityLivingBase);
                  }
               } else if (isMobEntity && !this.mobsValue.getEffectiveValue()) {
                  return false;
               } else {
                  return isPeacefulEntity && !this.peacefulValue.getEffectiveValue()
                     ? false
                     : isMobEntity || isPeacefulEntity || this.peacefulValue.getEffectiveValue();
               }
            }
         }
      }
   }
}
