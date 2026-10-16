package gg.vape.value;

import gg.vape.ui.click.frame.impl.main.ClickGuiModuleCardRenderState;
import gg.vape.unmap.ModeSelection;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringJoiner;

public final class ModuleValueDisplayFormatter {
   public static List<ClickGuiModuleCardRenderState> buildDescriptorRenderStates(List<ValueDisplayDescriptor> descriptors) {
      List<ValueDisplayDescriptor> displayableDescriptors = filterDisplayableDescriptors(descriptors);
      boolean hasExactlyOneMode = hasExactlyOneDescriptorMode(displayableDescriptors);
      ModeValue subModuleMode = findUniqueDescriptorSubModuleMode(displayableDescriptors);
      List<ClickGuiModuleCardRenderState> renderStates = buildDescriptorRenderStatesInternal(displayableDescriptors, hasExactlyOneMode, subModuleMode, false);
      int textLength = getTextLength(renderStates);
      return textLength < 50 ? buildDescriptorRenderStatesInternal(displayableDescriptors, hasExactlyOneMode, subModuleMode, true) : renderStates;
   }

   private static String formatValue(Value<?, ?> value, boolean hasExactlyOneMode, boolean isPrimaryMode) {
      if (value instanceof BooleanValue) {
         BooleanValue booleanValue = (BooleanValue)value;
         return hasOnlyLimitDependents(booleanValue) ? formatLimitDependents(booleanValue) : booleanValue.getDisplayName();
      } else {
         String displayValue = value.getDisplayValue();
         if ((hasExactlyOneMode || isPrimaryMode) && value instanceof ModeValue) {
            return displayValue;
         } else {
            return displayValue.isEmpty() ? value.getName() : displayValue + " " + value.getName();
         }
      }
   }

   private ModuleValueDisplayFormatter() {
   }

   private static List<Value<?, ?>> filterDisplayableValues(List<Value<?, ?>> values) {
      HashSet<Value> dependentValues = new HashSet<>();
      HashSet<Object> colorOnlyToggles = new HashSet<>();

      for (Value<?, ?> value : values) {
         if (value instanceof BooleanValue) {
            BooleanValue booleanValue = (BooleanValue)value;
            if (hasOnlyLimitDependents(booleanValue)) {
               for (Value dependentValue : booleanValue.getDependentValues()) {
                  dependentValues.add(dependentValue);
               }
            } else if (hasOnlyColorDependents(booleanValue)) {
               colorOnlyToggles.add(value);
            }
         }
      }

      ArrayList<Value<?, ?>> displayableValues = new ArrayList<>();

      for (Value<?, ?> valuex : values) {
         if (!dependentValues.contains(valuex) && !colorOnlyToggles.contains(valuex) && shouldDisplayValue(valuex)) {
            displayableValues.add(valuex);
         }
      }

      return displayableValues;
   }

   private static boolean hasOnlyColorDependents(BooleanValue booleanValue) {
      List<Value> dependentValues = booleanValue.getDependentValues();
      if (dependentValues.isEmpty()) {
         return false;
      } else {
         for (Value value : dependentValues) {
            if (!(value instanceof ColorValue)) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean hasNonEmptyLimitDependent(BooleanValue booleanValue) {
      for (Value value : booleanValue.getDependentValues()) {
         LimitValue limitValue;
         if (value instanceof LimitValue && !((List)(limitValue = (LimitValue)value).getValue()).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   private static String formatDescriptor(ValueDisplayDescriptor descriptor, boolean hasExactlyOneMode, boolean isPrimaryMode, boolean useFullName) {
      Value<?, ?> value = descriptor.getValue();
      if (value instanceof BooleanValue) {
         BooleanValue booleanValue = (BooleanValue)value;
         if (hasOnlyLimitDependents(booleanValue)) {
            return formatLimitDependents(booleanValue);
         } else {
            return useFullName ? descriptor.getFullName() : descriptor.getDisplayName();
         }
      } else {
         String displayValue = value.getDisplayValue();
         if ((hasExactlyOneMode || isPrimaryMode) && value instanceof ModeValue) {
            return displayValue;
         } else {
            String displayName = useFullName ? descriptor.getFullName() : descriptor.getDisplayName();
            return displayValue.isEmpty() ? displayName : displayValue + " " + displayName;
         }
      }
   }

   private static boolean hasExactlyOneModeValue(List<Value<?, ?>> values) {
      int modeCount = 0;

      for (Value<?, ?> value : values) {
         if (value instanceof ModeValue) {
            if (++modeCount > 1) {
               return false;
            }
         }
      }

      return modeCount == 1;
   }

   private static boolean hasExactlyOneDescriptorMode(List<ValueDisplayDescriptor> descriptors) {
      int modeCount = 0;

      for (ValueDisplayDescriptor descriptor : descriptors) {
         if (descriptor.getValue() instanceof ModeValue) {
            if (++modeCount > 1) {
               return false;
            }
         }
      }

      return modeCount == 1;
   }

   public static String formatDescriptorSummary(List<ValueDisplayDescriptor> descriptors) {
      List<ValueDisplayDescriptor> displayableDescriptors = filterDisplayableDescriptors(descriptors);
      boolean hasExactlyOneMode = hasExactlyOneDescriptorMode(displayableDescriptors);
      ModeValue modeValue = findUniqueDescriptorSubModuleMode(displayableDescriptors);
      if (modeValue != null) {
         StringJoiner stringJoiner = new StringJoiner(", ");
         boolean addedValue = false;

         for (ValueDisplayDescriptor descriptor : displayableDescriptors) {
            boolean isPrimaryMode = descriptor.getValue() == modeValue;
            String formattedValue = formatDescriptor(descriptor, hasExactlyOneMode, isPrimaryMode, false);
            if (!formattedValue.isEmpty()) {
               stringJoiner.add(formattedValue);
               addedValue = true;
            }
         }

         if (!addedValue) {
            return "";
         } else {
            String summary = stringJoiner.toString();
            if (summary.length() < 50) {
               StringJoiner fullNameSummary = new StringJoiner(", ");

               for (ValueDisplayDescriptor descriptorx : displayableDescriptors) {
                  boolean isPrimaryMode = descriptorx.getValue() == modeValue;
                  String formattedValue = formatDescriptor(descriptorx, hasExactlyOneMode, isPrimaryMode, true);
                  if (!formattedValue.isEmpty()) {
                     fullNameSummary.add(formattedValue);
                  }
               }

               return fullNameSummary.toString();
            } else {
               return summary;
            }
         }
      } else {
         StringJoiner stringJoiner = new StringJoiner(", ");
         boolean addedValue = false;

         for (ValueDisplayDescriptor descriptorxx : displayableDescriptors) {
            String formattedValue = formatDescriptor(descriptorxx, hasExactlyOneMode, false, false);
            if (!formattedValue.isEmpty()) {
               stringJoiner.add(formattedValue);
               addedValue = true;
            }
         }

         if (!addedValue) {
            return "";
         } else {
            String summary = stringJoiner.toString();
            if (summary.length() < 50) {
               StringJoiner fullNameSummary = new StringJoiner(", ");

               for (ValueDisplayDescriptor descriptorxxx : displayableDescriptors) {
                  String formattedValue = formatDescriptor(descriptorxxx, hasExactlyOneMode, false, true);
                  if (!formattedValue.isEmpty()) {
                     fullNameSummary.add(formattedValue);
                  }
               }

               return fullNameSummary.toString();
            } else {
               return summary;
            }
         }
      }
   }

   public static List<ClickGuiModuleCardRenderState> buildValueRenderStates(List<Value<?, ?>> values) {
      List<Value<?, ?>> displayableValues = filterDisplayableValues(values);
      boolean hasExactlyOneMode = hasExactlyOneModeValue(displayableValues);
      ModeValue subModuleMode = findUniqueSubModuleMode(displayableValues);
      return buildValueRenderStatesInternal(displayableValues, hasExactlyOneMode, subModuleMode);
   }

   private static boolean containsSubModuleMode(ModeValue modeValue) {
      for (ModeSelection modeSelection : modeValue.getModes()) {
         if (modeSelection instanceof SubModuleValue) {
            return true;
         }
      }

      return false;
   }

   public static String formatValueSummary(List<Value<?, ?>> values) {
      List<Value<?, ?>> displayableValues = filterDisplayableValues(values);
      StringJoiner stringJoiner = new StringJoiner(", ");
      boolean hasExactlyOneMode = hasExactlyOneModeValue(displayableValues);
      ModeValue modeValue = findUniqueSubModuleMode(displayableValues);

      for (Value<?, ?> value : displayableValues) {
         String formattedValue = formatValue(value, hasExactlyOneMode, value == modeValue);
         if (!formattedValue.isEmpty()) {
            stringJoiner.add(formattedValue);
         }
      }

      return stringJoiner.toString();
   }

   private static ModeValue findUniqueSubModuleMode(List<Value<?, ?>> values) {
      ModeValue modeValue = null;

      for (Value<?, ?> value : values) {
         if (value instanceof ModeValue && containsSubModuleMode((ModeValue)value)) {
            if (modeValue != null) {
               return null;
            }

            modeValue = (ModeValue)value;
         }
      }

      return modeValue;
   }

   private static List<ClickGuiModuleCardRenderState> buildDescriptorRenderStatesInternal(
      List<ValueDisplayDescriptor> descriptors, boolean hasExactlyOneMode, ModeValue modeValue, boolean useFullNames
   ) {
      ArrayList<ClickGuiModuleCardRenderState> renderStates = new ArrayList<>();
      boolean firstValue = true;
      boolean previousValueHasColor = false;
      if (modeValue != null) {
         for (ValueDisplayDescriptor descriptor : descriptors) {
            if (descriptor.getValue() == modeValue) {
               String formattedValue = formatDescriptor(descriptor, hasExactlyOneMode, true, useFullNames);
               if (!formattedValue.isEmpty() || modeValue.getDisplayColor() != null) {
                  firstValue = false;
                  if (!formattedValue.isEmpty()) {
                     renderStates.add(ClickGuiModuleCardRenderState.j(formattedValue));
                  }

                  Color color;
                  if ((color = modeValue.getDisplayColor()) != null) {
                     renderStates.add(ClickGuiModuleCardRenderState.b(color));
                     previousValueHasColor = true;
                  }
               }
               break;
            }
         }
      }

      for (ValueDisplayDescriptor descriptorx : descriptors) {
         Value<?, ?> value = descriptorx.getValue();
         if (value != modeValue) {
            String formattedValue = formatDescriptor(descriptorx, hasExactlyOneMode, false, useFullNames);
            if (!formattedValue.isEmpty() || value.getDisplayColor() != null) {
               if (!firstValue) {
                  renderStates.add(ClickGuiModuleCardRenderState.j(previousValueHasColor ? " " : ", "));
               }

               firstValue = false;
               if (!formattedValue.isEmpty()) {
                  renderStates.add(ClickGuiModuleCardRenderState.j(formattedValue));
               }

               Color color;
               previousValueHasColor = (color = value.getDisplayColor()) != null;
               if (previousValueHasColor) {
                  renderStates.add(ClickGuiModuleCardRenderState.b(color));
               }
            }
         }
      }

      return renderStates;
   }

   private static int getTextLength(List<ClickGuiModuleCardRenderState> renderStates) {
      int textLength = 0;

      for (ClickGuiModuleCardRenderState renderState : renderStates) {
         if (renderState.n$src$Z$1c2q0zn()) {
            textLength += renderState.n().length();
         }
      }

      return textLength;
   }

   private static ModeValue findUniqueDescriptorSubModuleMode(List<ValueDisplayDescriptor> descriptors) {
      ModeValue modeValue = null;

      for (ValueDisplayDescriptor descriptor : descriptors) {
         Value<?, ?> value = descriptor.getValue();
         if (value instanceof ModeValue && containsSubModuleMode((ModeValue)value)) {
            if (modeValue != null) {
               return null;
            }

            modeValue = (ModeValue)value;
         }
      }

      return modeValue;
   }

   private static List<ClickGuiModuleCardRenderState> buildValueRenderStatesInternal(List<Value<?, ?>> values, boolean hasExactlyOneMode, ModeValue modeValue) {
      ArrayList<ClickGuiModuleCardRenderState> renderStates = new ArrayList<>();
      boolean firstValue = true;
      boolean previousValueHasColor = false;
      if (modeValue != null) {
         String formattedValue = formatValue(modeValue, hasExactlyOneMode, true);
         if (!formattedValue.isEmpty() || modeValue.getDisplayColor() != null) {
            firstValue = false;
            if (!formattedValue.isEmpty()) {
               renderStates.add(ClickGuiModuleCardRenderState.j(formattedValue));
            }

            Color color;
            if ((color = modeValue.getDisplayColor()) != null) {
               renderStates.add(ClickGuiModuleCardRenderState.b(color));
               previousValueHasColor = true;
            }
         }
      }

      for (Value<?, ?> value : values) {
         String formattedValue;
         if (value != modeValue && (!(formattedValue = formatValue(value, hasExactlyOneMode, false)).isEmpty() || value.getDisplayColor() != null)) {
            if (!firstValue) {
               renderStates.add(ClickGuiModuleCardRenderState.j(previousValueHasColor ? " " : ", "));
            }

            firstValue = false;
            if (!formattedValue.isEmpty()) {
               renderStates.add(ClickGuiModuleCardRenderState.j(formattedValue));
            }

            Color color;
            previousValueHasColor = (color = value.getDisplayColor()) != null;
            if (previousValueHasColor) {
               renderStates.add(ClickGuiModuleCardRenderState.b(color));
            }
         }
      }

      return renderStates;
   }

   private static boolean hasOnlyLimitDependents(BooleanValue booleanValue) {
      List<Value> dependentValues = booleanValue.getDependentValues();
      if (dependentValues.isEmpty()) {
         return false;
      } else {
         for (Value value : dependentValues) {
            if (!(value instanceof LimitValue)) {
               return false;
            }
         }

         return true;
      }
   }

   private static List<ValueDisplayDescriptor> filterDisplayableDescriptors(List<ValueDisplayDescriptor> descriptors) {
      ArrayList<ValueDisplayDescriptor> displayableDescriptors = new ArrayList<>();

      for (ValueDisplayDescriptor descriptor : descriptors) {
         if (shouldDisplayValue(descriptor.getValue())) {
            displayableDescriptors.add(descriptor);
         }
      }

      return displayableDescriptors;
   }

   private static boolean shouldDisplayValue(Value<?, ?> value) {
      if (value instanceof EntityTargetFilterValue) {
         return false;
      } else if (!value.areConditionsMet()) {
         return false;
      } else if (value instanceof BooleanValue) {
         return !hasOnlyLimitDependents((BooleanValue)value)
            ? ((BooleanValue)value).getEffectiveValue()
            : ((BooleanValue)value).getEffectiveValue() && hasNonEmptyLimitDependent((BooleanValue)value);
      } else {
         return !value.getDisplayValue().isEmpty() || value.getDisplayColor() != null;
      }
   }

   private static String formatLimitDependents(BooleanValue booleanValue) {
      StringJoiner summary = new StringJoiner(", ");
      HashSet<Value> seenValues = new HashSet<>();

      for (Value value : booleanValue.getDependentValues()) {
         LimitValue limitValue;
         int entryCount;
         if (value instanceof LimitValue && seenValues.add(value) && (entryCount = ((List)(limitValue = (LimitValue)value).getValue()).size()) != 0) {
            summary.add(entryCount + " " + limitValue.getName());
         }
      }

      String formattedSummary = summary.toString();
      return formattedSummary.isEmpty() ? "" : formattedSummary;
   }
}
