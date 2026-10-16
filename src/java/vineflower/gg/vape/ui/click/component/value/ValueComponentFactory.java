package gg.vape.ui.click.component.value;

import gg.vape.module.blatant.antibot.AntiBotBooleanValue;
import gg.vape.module.blatant.antibot.AntiBotModeValue;
import gg.vape.module.utility.inventory.HotbarSlotRuleEditorComponent;
import gg.vape.module.utility.inventory.HotbarSlotRuleValue;
import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfileValue;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerProfileValueComponent;
import gg.vape.ui.click.component.DropdownSelectComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.input.BindValueRowComponent;
import gg.vape.unmap.ItemLimitData;
import gg.vape.value.BindValue;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.EntityTargetFilterValue;
import gg.vape.value.ListValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.OptionalLimitEntry;
import gg.vape.value.RandomValue;
import gg.vape.value.SnapshotValueAccessor;
import gg.vape.value.StringMapValue;
import gg.vape.value.Value;
import gg.vape.value.ValueSnapshot;

public class ValueComponentFactory {
   public static GuiComponent createValueComponent(Value<?, ?> value, boolean snapshotEditor, ValueComponentMode componentMode) {
      GuiComponent component = value.getBoundComponent();
      if (value instanceof BooleanValue) {
         component = new BooleanToggleComponent((BooleanValue)value);
      } else if (value instanceof AntiBotModeValue) {
         component = new ColorValueDropdownComponent((AntiBotModeValue)value);
      } else if (value instanceof AntiBotBooleanValue) {
         component = new AntiBotBooleanValueOptionRow((AntiBotBooleanValue)value);
      } else if (value instanceof ModeValue) {
         ModeValue modeValue = (ModeValue)value;
         if (!modeValue.isBindable() && !modeValue.getName().equalsIgnoreCase("Mace type")) {
            component = new DropdownSelectComponent(modeValue);
            if (componentMode == ValueComponentMode.STANDALONE) {
               ((DropdownSelectComponent)component).setHighlightedStyle(true);
            }
         } else {
            component = new ModeToggleComponent(modeValue);
         }
      } else if (value instanceof NumberValue) {
         component = new NumberSliderComponent((NumberValue)value);
      } else if (value instanceof RandomValue) {
         component = new RandomRangeSliderComponent((RandomValue)value);
      } else if (value instanceof ColorValue) {
         component = new ColorValueEditorComponent((ColorValue)value);
      } else if (value instanceof ListValue) {
         ListValueComponent listValueComponent = new ListValueComponent((ListValue)value);
         if (componentMode == ValueComponentMode.STANDALONE) {
            listValueComponent.setMode(ValueComponentMode.STANDALONE);
         }

         component = listValueComponent;
         ListValue listValue = (ListValue)value;
         BooleanValue parentToggle;
         if (listValue.getParent() instanceof BooleanValue
            && (parentToggle = (BooleanValue)listValue.getParent()).getTerminalDependentValue() != null
            && parentToggle.getTerminalDependentValue().equals(listValue)) {
            component = null;
         }
      } else if (value instanceof HotbarSlotRuleValue) {
         if (!snapshotEditor) {
            HotbarSlotRuleValue hotbarSlotRuleValue = (HotbarSlotRuleValue)value;
            if (hotbarSlotRuleValue.getEditor() != null) {
               component = hotbarSlotRuleValue.getEditor();
               component.setVisible(true);
            } else {
               component = new HotbarSlotRuleEditorComponent(hotbarSlotRuleValue);
            }
         }
      } else if (value instanceof EntityTargetFilterValue) {
         component = (GuiComponent)(snapshotEditor
            ? new EntityTargetFilterPopupComponent((EntityTargetFilterValue)value)
            : new EntityTargetFilterComponent((EntityTargetFilterValue)value));
      } else if (value instanceof StringMapValue) {
         component = new StringMapValueComponent((StringMapValue)value);
      } else if (value instanceof BindValue) {
         component = new BindValueRowComponent((BindValue)value);
      } else if (value instanceof InventoryCleanerProfileValue) {
         component = new InventoryCleanerProfileValueComponent((InventoryCleanerProfileValue)value);
      }

      return component;
   }

   public static GuiComponent createMainValueComponent(Value<?, ?> value) {
      return createValueComponent(value, false, ValueComponentMode.MAIN);
   }

   public static Value createSnapshotProxyValue(ValueSnapshot valueSnapshot) {
      Value sourceValue = valueSnapshot.getSourceValue();
      Value proxyValue = sourceValue.copyValueDefinition();
      proxyValue.setDefaultValue(sourceValue.getDefaultValue());
      proxyValue.setValue(valueSnapshot.getValue());
      proxyValue.setAccessor(new SnapshotValueAccessor(valueSnapshot, proxyValue));
      return proxyValue;
   }

   public static Object getComparableListEntryText(Object entry) {
      return !(entry instanceof OptionalLimitEntry) && !(entry instanceof ItemLimitData) ? null : entry.toString();
   }

   public static GuiComponent createMainValueComponent(Value<?, ?> value, boolean snapshotEditor) {
      return createValueComponent(value, snapshotEditor, ValueComponentMode.MAIN);
   }
}
